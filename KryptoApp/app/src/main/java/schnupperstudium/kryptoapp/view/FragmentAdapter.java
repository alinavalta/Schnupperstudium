package schnupperstudium.kryptoapp.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import schnupperstudium.kryptoapp.R;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private key key;
    private Msg msg;
    private Cipher cipher;
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
        Log.d("Test", "GET Item");
        if (position == 0) {
            key = new key();
            keyMessage = (FragmentMessage) key;
            return key;
        } else if (position == 1){
            msg = new Msg();
            message = (FragmentMessage) msg;
            return msg;
        } else{
            cipher = new Cipher();
            cipherMessage = (FragmentMessage)cipher;
            return cipher;
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
        if(keyMessage == null) {
            Log.d("Bluetooth", "Keymessage NULL");
        }
        keyMessage.onFragmentMessage(tag, msg);
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
}
