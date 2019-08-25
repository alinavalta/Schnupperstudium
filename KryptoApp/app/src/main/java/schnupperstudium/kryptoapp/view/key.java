package schnupperstudium.kryptoapp.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import schnupperstudium.kryptoapp.MainActivity;
import schnupperstudium.kryptoapp.R;


public class key extends Fragment implements FragmentMessage{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;
    public static final String SET_KEY = "setKey";

    private FragmentMessage fragmentMessage;
    private EditText et;
    //private OnFragmentInteractionListener mListener;

    public key() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_key, container, false);
        et = rootView.findViewById(R.id.key_et);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fragmentMessage.onFragmentMessage(MainActivity.ON_KEYCHANGED, et.getText().toString());
            }
        });
        rootView.findViewById(R.id.btnGenKey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMessage.onFragmentMessage(MainActivity.ON_GENKEY, "");
            }
        });

        rootView.findViewById(R.id.btnSendKey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMessage.onFragmentMessage(MainActivity.ON_SENDKEY, et.getText().toString());
            }
        });

        rootView.findViewById(R.id.btnRcvKey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMessage.onFragmentMessage(MainActivity.ON_RCVKEY, "");
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentMessage = (FragmentMessage) context;
    }

    @Override
    public void onFragmentMessage(String tag, String data) {
        if(tag.equals(SET_KEY)) {
            et.setText(data);
        }
    }
}
