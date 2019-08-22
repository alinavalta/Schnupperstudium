package schnupperstudium.kryptoapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import schnupperstudium.kryptoapp.MainActivity;
import schnupperstudium.kryptoapp.R;


public class Cipher extends Fragment implements FragmentMessage{

    public static final String SET_CIPHER = "setCiper";

    public static final String SET_ATTACK = "attack";

    public static final String SET_ALGO = "algo";

    private  EditText et;
    private Button btnDecrypt;
    private Button btnTryDecrpyt;
    private FragmentMessage fragmentMessage;
    public Cipher() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chiffer, container, false);
        et = rootView.findViewById(R.id.chiffer_et);
        btnDecrypt = rootView.findViewById(R.id.btnDecrypt);
        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMessage.onFragmentMessage(MainActivity.ON_DECRYPT, et.getText().toString());
            }
        });
        rootView.findViewById(R.id.btnRcvCipher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMessage.onFragmentMessage(MainActivity.ON_RCVCIPHER, "");
            }
        });
        rootView.findViewById(R.id.btnSendCipher).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMessage.onFragmentMessage(MainActivity.ON_SENDCIPHER, et.getText().toString());
            }
        });

        btnTryDecrpyt = rootView.findViewById(R.id.btnTryDecrypt);
        btnTryDecrpyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMessage.onFragmentMessage(MainActivity.ON_TRY_DECRYPT, et.getText().toString());
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
            btnTryDecrpyt.setVisibility(View.GONE);
            btnDecrypt.setVisibility(View.VISIBLE);
        } else {
            btnTryDecrpyt.setVisibility(View.VISIBLE);
            btnDecrypt.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFragmentMessage(String tag, String data) {
        if(tag.equals(SET_CIPHER)) {
            et.setText(data);
        } else if(tag.equals(SET_ATTACK)) {
            setIsAlgorithm(false);
        } else if(tag.equals(SET_ALGO)){
            setIsAlgorithm(true);
        }
    }
}
