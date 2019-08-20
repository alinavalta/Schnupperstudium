package schnupperstudium.kryptoapp.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import schnupperstudium.kryptoapp.MainActivity;
import schnupperstudium.kryptoapp.R;
import schnupperstudium.kryptoapp.crypto.Algorithm;

public class AlgorithmList extends DialogFragment {

        private String result;

       @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            Bundle bundle = getArguments();
            ArrayList<String> list = bundle.getStringArrayList("List");
            final String[] array = list.toArray(new String[0]);
            Log.d("Main", "OnCreate Dialog");
           builder.setTitle("Algorithmus wählen")
                   .setItems(array, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           ((MainActivity)getActivity()).setAlgorithmListResult(array[which]);
                       }
                   });
            // Create the AlertDialog object and return it
            return builder.create();
        }

        public String getResult() {
           return result;
        }
}
