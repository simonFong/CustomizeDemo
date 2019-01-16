package cn.dlc.customizedemo.myapplication.update;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.app.Activity.RESULT_OK;

/**
 * Created by fengzimin  on  2018/12/12.
 * interface by
 */
public class PermissionFragment extends android.app.Fragment {

    public static final int INSTALL_PERMISS_CODE = 10086;
    private PermissionListener mListener;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 26) {
            startInstallPermissionSettingActivity();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, INSTALL_PERMISS_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INSTALL_PERMISS_CODE) {
            if (resultCode == RESULT_OK) {
                if (mListener != null) {
                    mListener.success();
                }
            } else {
                if (mListener != null) {
                    mListener.failure();
                }
            }
        }
    }

    public interface PermissionListener {
        void success();

        void failure();
    }

    public void setPermissionListener(PermissionListener listener) {
        mListener = listener;
    }
}
