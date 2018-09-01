package cn.dlc.customizedemo.myapplication.leakcanary;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by fengzimin  on  2018/09/01.
 * interface by
 */
public class XXXHelper {

    private Context mCtx;
    private TextView mTextView;

    private static XXXHelper ourInstance = null;

    public static XXXHelper getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new XXXHelper(context);
        }
        return ourInstance;
    }

    public void setRetainedTextView(TextView tv){
        this.mTextView = tv;
        mTextView.setText(mCtx.getString(android.R.string.ok));
    }

    private XXXHelper() {
    }

    private XXXHelper(Context context) {
        this.mCtx = context;
    }

}
