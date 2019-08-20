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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Msg.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Msg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Msg extends Fragment implements FragmentMessage{

    public static final String SET_MESSAGE = "setMessage";

    private  EditText msg_et;
    private Button btn;
    private String msg;
    private FragmentMessage fragmentMessage;
    public Msg() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_msg, container, false);
        msg_et = rootView.findViewById(R.id.msg_et);
        btn = rootView.findViewById(R.id.btnEncrypt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = msg_et.getText().toString();
                fragmentMessage.onFragmentMessage(MainActivity.ON_ENCRPYT, msg);

            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        fragmentMessage = (FragmentMessage) context;
    }


    public EditText getEditText(){
        return msg_et;
    }

    @Override
    public void onFragmentMessage(String tag, String data) {
        if(tag.equals(SET_MESSAGE)) {
            msg_et.setText(data);
        }
    }
}
