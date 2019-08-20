package schnupperstudium.kryptoapp.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.EditText;

import schnupperstudium.kryptoapp.R;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private EditText msg_et;
    private EditText key_et;
    private EditText chiffer_et;
    private FragmentMessage cipherMessage;
    private FragmentMessage keyMessage;
    private FragmentMessage message;


    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            key key = new key();
            keyMessage = (FragmentMessage) key;
            return key;
        } else if (position == 1){
            Msg msg = new Msg();
            message = (FragmentMessage) msg;
            return msg;
        } else{
            Cipher chiffer = new Cipher();
            cipherMessage = (FragmentMessage)chiffer;
            return chiffer;
        }
    }
    // This determines the number of tabs
    @Override
    public int getCount() {
        return 3;
    }

    public void setCipherMessage(String tag, String msg) {
        cipherMessage.onFragmentMessage(tag,msg);
    }

    public void setMessage(String tag, String msg) {
        message.onFragmentMessage(tag,msg);
    }

    public void setKeyMessage(String tag, String msg) {
        keyMessage.onFragmentMessage(tag,msg);
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.key);
            case 1:
                return mContext.getString(R.string.msg);
            case 2:
                return mContext.getString(R.string.chiffer);
            default:
                return null;
        }
    }

    public EditText getMsg_et(){
        Log.d("Main", "Adapter " + (msg_et == null));
        return msg_et;
    }
}
