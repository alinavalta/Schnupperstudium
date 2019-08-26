package schnupperstudium.kryptoapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import schnupperstudium.kryptoapp.bluetooth.Bluetooth;
import schnupperstudium.kryptoapp.bluetooth.SelectBluetooth;
import schnupperstudium.kryptoapp.crypto.KeyFormatException;
import schnupperstudium.kryptoapp.crypto.SelectAlgorithm;
import schnupperstudium.kryptoapp.view.AlgorithmList;
import schnupperstudium.kryptoapp.view.Cipher;
import schnupperstudium.kryptoapp.view.FragmentAdapter;
import schnupperstudium.kryptoapp.view.FragmentMessage;
import schnupperstudium.kryptoapp.view.Msg;
import schnupperstudium.kryptoapp.view.key;

public class MainActivity extends AppCompatActivity implements FragmentMessage {

    public static final String ON_DECRYPT = "onDecrpyt";
    public static final String ON_ENCRPYT = "onencrpyt";
    public static final String ON_KEYCHANGED = "onKeyChanged";
    public static final String ON_GENKEY = "onGenKey";
    public static final String ON_RCVKEY = "onRcvKey";
    public static final String ON_SENDKEY = "onSendKey";
    public static final String ON_SENDCIPHER = "onSendC";
    public static final String ON_RCVCIPHER = "onRcvC";
    public static final String ON_TRY_DECRYPT = "onTry";
    public static final String ON_CIPHER_CHANGED = "CChangeed";
    public static final String ON_PROCESS = "onProc";
    private static final String ON_SAVE_ALGO = "saveAlgo";
    private static final int WHAT_TRYDECRPYT = 10;
    private static final int WHAT_TRYDECRYPT_FAILED = 11;

    private String currentKey = "";
    private String currentCipher ="";
    private SelectAlgorithm selectAlgorithm;
    private Toolbar toolbar;
    TabLayout tabLayout;
    private Handler handler;
    FragmentAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Algorithmus wählen");
        selectAlgorithm = new SelectAlgorithm(getApplicationContext());
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlgorithmList dialogFragment = new AlgorithmList();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("List", selectAlgorithm.getNameList());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(), "AlgoListDialog");
            }
        });
        setSupportActionBar(toolbar);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new FragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout = (TabLayout)findViewById(R.id.TabLayout);
        tabLayout.setupWithViewPager(viewPager);

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.d("Main", "Message rcv");
                if(msg.what == WHAT_TRYDECRYPT_FAILED) {
                    Toast.makeText(getApplicationContext(), "Entschlüsselung fehlgeschlagen", Toast.LENGTH_LONG).show();
                    return;
                } else if(msg.what == Bluetooth.CONNCTION_WHAT){
                    Toast.makeText(getApplicationContext(), "verbunden", Toast.LENGTH_LONG).show();
                }
                Bundle bundle = msg.getData();
                if(msg.what == WHAT_TRYDECRPYT) {
                    viewPager.setCurrentItem(1);
                    pagerAdapter.setKeyMessage(key.SET_KEY, bundle.getString(key.SET_KEY));
                    pagerAdapter.setMessage(Msg.SET_MESSAGE, bundle.getString(Msg.SET_MESSAGE));
                    return;
                }
                String string = bundle.getString(Bluetooth.READ_TAG);
                if(msg.what == Bluetooth.KEY_RECIEVED_WHAT){
                    Log.d("Bluetooth", "Handler Message: " + string);
                    pagerAdapter.setKeyMessage(key.SET_KEY, string);
                } else if(msg.what == Bluetooth.CIPHER_RECIEVED_WHAT) {
                    pagerAdapter.setCipherMessage(Cipher.SET_CIPHER, string);
                } else if(msg.what == Bluetooth.SEND_WHAT) {
                    Toast.makeText(getApplicationContext(), "Senden erfolgreich", Toast.LENGTH_LONG).show();
                } else if(msg.what == Bluetooth.SEND_FAILED_WHAT){
                    Toast.makeText(getApplicationContext(), "Senden fehlgeschlagen", Toast.LENGTH_LONG).show();
                } else if(msg.what == Bluetooth.RCV_FAIL_WHAT) {
                    Toast.makeText(getApplicationContext(), "Es konnte keine Verbingung aufgebaut werden", Toast.LENGTH_LONG).show();
                } else if(msg.what == Bluetooth.RCV_CONNECTED_WHAT) {
                    Toast.makeText(getApplicationContext(), "Verbingung wird aufgebaut", Toast.LENGTH_LONG).show();
                }

            }
        };
    }

    @Override
    public void onStart(){
        super.onStart();
        if(toolbar.getTitle().equals("Algorithmus wählen")) {
            toolbar.callOnClick();
        }

    }

    public void setAlgorithmListResult(String name) {
        selectAlgorithm.selectAlgorithm(name);
        toolbar.setTitle(name);
        if(selectAlgorithm.isAlgorithm()){
            pagerAdapter.setMessage(Msg.SET_ALGO,"");
            pagerAdapter.setCipherMessage(Cipher.SET_ALGO, "");
        } else {
            pagerAdapter.setMessage(Msg.SET_ATTACK, "");
            pagerAdapter.setCipherMessage(Cipher.SET_ATTACK, "");
        }
    }
    public void onSentKey(String key) {
        /**final ProgressDialog processDialog = new ProgressDialog(MainActivity.this);
        final String input = key;
        processDialog.setMessage("Loading ...");
        processDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        processDialog.show();
        new Thread(new Runnable() {
            public void run() {
                Dictornary dictornary = new Dictornary(getApplicationContext());
                if(dictornary.isInDictionary(input)) {
                    Toast.makeText(getApplicationContext(), "Vorhanden: " + input, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "existiert nicht", Toast.LENGTH_LONG).show();
                }
                processDialog.dismiss();
            }
        }).start();**/


        if(key == null) {
            Toast.makeText(getApplicationContext(), "Schlüssel ist leer", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, SelectBluetooth.class);
        startActivityForResult(intent, 2);
    }
    public void onRecieveKey() {
        Bluetooth bluetooth = new Bluetooth(handler, Bluetooth.KEY_RECIEVED_WHAT);
        bluetooth.connectAsServer();
    }
    public void onSentCipher(String cipher) {
        if(cipher.length() == 0) {
            Toast.makeText(getApplicationContext(), "Chiffrat ist leer", Toast.LENGTH_LONG).show();
            return;
        }
        currentCipher = cipher;
        Intent intent = new Intent(this, SelectBluetooth.class);
        startActivityForResult(intent, 1);
    }
    public void onRecieveCipher() {
        Bluetooth bluetooth = new Bluetooth(handler, Bluetooth.CIPHER_RECIEVED_WHAT);
        bluetooth.connectAsServer();
    }
    public void onEncrypt(String m) {
        if(m.length() == 0){
            Toast.makeText(getApplicationContext(),"Nachricht ist leer", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            String c = selectAlgorithm.getAlgorithm().encrypt(m,currentKey);
            pagerAdapter.setCipherMessage(Cipher.SET_CIPHER, c);
            tabLayout.getTabAt(2).select();
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }  catch (KeyFormatException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void onDecrpyt(String cipher) {
        if(cipher.length() == 0){
            Toast.makeText(getApplicationContext(),"Chiffrat ist leer", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            String m = selectAlgorithm.getAlgorithm().decrypt(cipher,currentKey);
            pagerAdapter.setMessage(Msg.SET_MESSAGE, m);
            tabLayout.getTabAt(1).select();
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (KeyFormatException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void onGenerateKey() {
        try {
            pagerAdapter.setKeyMessage(key.SET_KEY, selectAlgorithm.getAlgorithm().generateRandomKey());
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onTryDecrypt(String cipher) {
        final ProgressDialog processDialog = new ProgressDialog(MainActivity.this);
        final String input = cipher;
        processDialog.setMessage("Verschlüsselung wird versucht");
        processDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        processDialog.show();
        final TryDecryptThread tryDecryptThread = new TryDecryptThread(handler, cipher, processDialog);
        processDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                tryDecryptThread.interrupt();
            }
        });

        tryDecryptThread.start();
    }

    public void onProcess(String msg) {
        if(selectAlgorithm.getAttacker().process(msg, currentCipher)){
            pagerAdapter.setKeyMessage(key.SET_KEY, selectAlgorithm.getAttacker().getKey());
        } else {
            Toast.makeText(getApplicationContext(), "Es konnte kein Schlüssel ermittelt werden", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if (requestCode == 1) {//Send ciffer
                Bluetooth bluetooth = new Bluetooth(handler, Bluetooth.SEND_WHAT);
                if(!bluetooth.send(data.getStringExtra("name"), currentCipher)){
                   Toast.makeText(getApplicationContext(), "Senden fehlgeschlagen", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == 2) {//Send key
                Bluetooth bluetooth = new Bluetooth(handler, Bluetooth.SEND_WHAT);
                try {
                    String sentKey = selectAlgorithm.getAlgorithmName().getSentKey(currentKey);
                    Log.d("Test", "Send Key: " + sentKey);
                    bluetooth.send(data.getStringExtra("name"), sentKey);
                    Toast.makeText(getApplicationContext(), "Senden fehlgeschlagen", Toast.LENGTH_LONG).show();
                } catch (KeyFormatException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        } else if(resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), "Kein Gerät ausgewählt", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFragmentMessage(String tag, String data) {
        if(tag.equals(ON_ENCRPYT)){//onEncrypt
            onEncrypt(data);
        } else if(tag.equals(ON_KEYCHANGED)) {
            currentKey = data;
        } else if(tag.equals(ON_SENDKEY)) {
            onSentKey(data);
        } else if(tag.equals(ON_RCVKEY)) {
            onRecieveKey();
        }else if(tag.equals(ON_DECRYPT)){
            onDecrpyt(data);
        } else if(tag.equals(ON_GENKEY)) {
            onGenerateKey();
        }  else if(tag.equals(ON_RCVCIPHER)) {
            onRecieveCipher();
        } else if(tag.equals(ON_SENDCIPHER)) {
            onSentCipher(data);
        } else if(tag.equals(ON_PROCESS)) {
            onProcess(data);
        } else if(tag.equals(ON_TRY_DECRYPT)) {
            onTryDecrypt(data);
        } else if(tag.equals(ON_CIPHER_CHANGED)) {
            currentCipher = data;
        }
    }

    private class TryDecryptThread extends Thread {

        private Handler handler;
        private  ProgressDialog dialog;
        private String cipher;
        public TryDecryptThread(Handler handler, String cipher, ProgressDialog progressDialog) {
            this.handler= handler;
            this.cipher = cipher;
            dialog = progressDialog;
        }

        public void run() {
            try {
                if (selectAlgorithm.getAttacker().tryDecrypt(cipher)) {
                    Message msg = handler.obtainMessage(MainActivity.WHAT_TRYDECRPYT);
                    Bundle bundle = new Bundle();
                    bundle.putString(Msg.SET_MESSAGE, selectAlgorithm.getAttacker().getMessage());
                    bundle.putString(key.SET_KEY, selectAlgorithm.getAttacker().getKey());
                    msg.setData(bundle);
                    msg.sendToTarget();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    handler.sendEmptyMessage(WHAT_TRYDECRYPT_FAILED);
                }
            } catch (InterruptedException e) {

            }
        }

    }

}
