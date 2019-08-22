package schnupperstudium.kryptoapp.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import schnupperstudium.kryptoapp.MainActivity;
import schnupperstudium.kryptoapp.R;


public class Msg extends Fragment implements FragmentMessage{

    public static final String SET_MESSAGE = "setMessage";

    public static final String SET_ALGO = "algo";
    public static final String SET_ATTACK = "attack";

    private  EditText msg_et;
    private Button btn;
    private Button btnProcess;
    private String msg;
    private FragmentMessage fragmentMessage;
    public Msg() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Test", "OnCreate Msg");
        View rootView = inflater.inflate(R.layout.fragment_msg, container, false);
        msg_et = rootView.findViewById(R.id.msg_et);
        btn = rootView.findViewById(R.id.btnEncrypt);
        btnProcess = rootView.findViewById(R.id.btnProcessMsgCipher);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = msg_et.getText().toString();
                fragmentMessage.onFragmentMessage(MainActivity.ON_ENCRPYT, msg);

            }
        });
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = msg_et.getText().toString();
                fragmentMessage.onFragmentMessage(MainActivity.ON_PROCESS, msg);
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        fragmentMessage = (FragmentMessage) context;
    }

    private void setIsAlgorithm(boolean isAlgorithm) {
        if(isAlgorithm) {
            btn.setVisibility(View.VISIBLE);
            btnProcess.setVisibility(View.GONE);
        } else {
            btn.setVisibility(View.GONE);
            btnProcess.setVisibility(View.VISIBLE);
        }
    }

    public EditText getEditText(){
        return msg_et;
    }

    @Override
    public void onFragmentMessage(String tag, String data) {
        if(tag.equals(SET_MESSAGE)) {
            msg_et.setText(data);
        } else if(tag.equals(SET_ALGO)) {
            setIsAlgorithm(true);
        } else if(tag.equals(SET_ATTACK)) {
            setIsAlgorithm(false);
        }
    }
}
