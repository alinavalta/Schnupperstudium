package schnupperstudium.kryptoapp.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

import schnupperstudium.kryptoapp.R;

public class SelectBluetooth extends AppCompatActivity {

    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bluetooth);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final ListView bluetoothList = findViewById(R.id.bluetooth_list);

        arrayList = refreshList(bluetoothList);
        bluetoothList.setClickable(true);
        bluetoothList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("name", arrayList.get(position));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList = refreshList(bluetoothList);
            }
        });
    }

    private ArrayList<String> refreshList(ListView list){
        Bluetooth bluetooth = new Bluetooth(null,0); //TODO besserer Fix
        bluetooth.bluetoothEnable();
        final ArrayList<String> arrayList = new ArrayList<>();
        final Set<BluetoothDevice> devices = bluetooth.getPairDevices();
        for(BluetoothDevice d : devices) {
            arrayList.add(d.getName());
        }

        if(arrayList.isEmpty()) {
            arrayList.add("es sind derzeit keine Geräte verbunden");
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.bluetooth_list_element, R.id.list_textview,arrayList);
        list.setAdapter(arrayAdapter);
        return arrayList;
    }



}
