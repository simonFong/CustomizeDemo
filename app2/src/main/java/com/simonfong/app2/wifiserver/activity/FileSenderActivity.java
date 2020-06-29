package com.simonfong.app2.wifiserver.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.leon.lfilepickerlibrary.LFilePicker;
import com.simonfong.app2.R;
import com.simonfong.app2.wifiserver.BaseActivity;
import com.simonfong.app2.wifiserver.common.Constants;
import com.simonfong.app2.wifiserver.manager.WifiLManager;
import com.simonfong.app2.wifiserver.model.FileTransfer;
import com.simonfong.app2.wifiserver.service.FileSenderService;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;


/**
 * 作者：chenZY
 * 时间：2018/4/3 14:53
 * 描述：https://www.jianshu.com/u/9df45b87cfdf
 * https://github.com/leavesC
 */
public class FileSenderActivity extends BaseActivity {

    public static final String TAG = "FileSenderActivity";

    private static final int CODE_CHOOSE_FILE = 100;

    private FileSenderService fileSenderService;

    private ProgressDialog progressDialog;

    private FileSenderService.OnSendProgressChangListener progressChangListener = new FileSenderService.OnSendProgressChangListener() {

        @Override
        public void onStartComputeMD5() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isCreated()) {
                        progressDialog.setTitle("发送文件");
                        progressDialog.setMessage("正在计算文件的MD5码");
                        progressDialog.setMax(100);
                        progressDialog.setProgress(0);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                }
            });
        }

        @Override
        public void onProgressChanged(final FileTransfer fileTransfer, final long totalTime, final int progress, final double instantSpeed, final long instantRemainingTime, final double averageSpeed, final long averageRemainingTime) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isCreated()) {
                        progressDialog.setTitle("正在发送文件： " + new File(fileTransfer.getFilePath()).getName());
                        if (progress != 100) {
                            progressDialog.setMessage("文件的MD5码：" + fileTransfer.getMd5()
                                    + "\n\n" + "总的传输时间：" + totalTime + " 秒"
                                    + "\n\n" + "瞬时-传输速率：" + (int) instantSpeed + " Kb/s"
                                    + "\n" + "瞬时-预估的剩余完成时间：" + instantRemainingTime + " 秒"
                                    + "\n\n" + "平均-传输速率：" + (int) averageSpeed + " Kb/s"
                                    + "\n" + "平均-预估的剩余完成时间：" + averageRemainingTime + " 秒"
                            );
                        }
                        progressDialog.setProgress(progress);
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                    }
                }
            });
        }

        @Override
        public void onTransferSucceed(FileTransfer fileTransfer) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isCreated()) {
                        progressDialog.setTitle("文件发送成功");
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                    }
                }
            });
        }

        @Override
        public void onTransferFailed(FileTransfer fileTransfer, final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isCreated()) {
                        progressDialog.setTitle("文件发送失败");
                        progressDialog.setMessage("异常信息： " + e.getMessage());
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                    }
                }
            });
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FileSenderService.MyBinder binder = (FileSenderService.MyBinder) service;
            fileSenderService = binder.getService();
            fileSenderService.setProgressChangListener(progressChangListener);
            Log.e(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            fileSenderService = null;
            bindService(FileSenderService.class, serviceConnection);
            Log.e(TAG, "onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_sender);
        initView();
        bindService(FileSenderService.class, serviceConnection);
    }

    private void initView() {
        setTitle("发送文件");
        TextView tv_hint = findViewById(R.id.tv_hint);
        tv_hint.setText(MessageFormat.format("在发送文件前需要先连上文件接收端开启的Wifi热点\n热点名：{0} \n密码：{1}", Constants.AP_SSID, Constants.AP_PASSWORD));
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("发送文件");
        progressDialog.setMax(100);
        progressDialog.setIndeterminate(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fileSenderService != null) {
            fileSenderService.setProgressChangListener(null);
            unbindService(serviceConnection);
        }
        progressDialog.dismiss();
    }

    public void sendFile(View view) {
        if (!Constants.AP_SSID.equals(WifiLManager.getConnectedSSID(this))) {
            showToast("当前连接的Wifi并非文件接收端开启的Wifi热点，请重试");
            return;
        }
        navToChose();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CODE_CHOOSE_FILE && resultCode == RESULT_OK) {
//            List<String> strings = Matisse.obtainPathResult(data);
//            if (strings != null && !strings.isEmpty()) {
//                String path = strings.get(0);
//                sendFile(path);
//            }
//        }
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_FROM_ACTIVITY) {
                //如果是文件选择模式，需要获取选择的所有文件的路径集合
                //List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);//Constant.RESULT_INFO == "paths"
                List<String> list = data.getStringArrayListExtra("paths");
                Toast.makeText(getApplicationContext(), "选中了" + list.size() + "个文件", Toast.LENGTH_SHORT).show();
                //如果是文件夹选择模式，需要获取选择的文件夹路径
                String path = data.getStringExtra("path");
                Toast.makeText(getApplicationContext(), "选中的路径为" + path, Toast.LENGTH_SHORT).show();
                sendFile(list.get(0));
            }
        }
    }

    private void sendFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            FileTransfer fileTransfer = new FileTransfer(file);
            Log.e(TAG, "待发送的文件：" + fileTransfer);
            FileSenderService.startActionTransfer(this, fileTransfer, WifiLManager.getHotspotIpAddress(this));
        }
    }

    int REQUESTCODE_FROM_ACTIVITY = 1000;
    private void navToChose() {
        new LFilePicker()
                .withActivity(FileSenderActivity.this)
                .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                .withStartPath("/storage/emulated/0/Download")//指定初始显示路径
                .withIsGreater(false)//过滤文件大小 小于指定大小的文件
                .withFileSize(500 * 1024)//指定文件大小为500K
                .start();

//        Matisse.from(this)
//                .choose(MimeType.ofAll())
//                .countable(true)
//                .showSingleMediaType(true)
//                .maxSelectable(1)
//                .capture(false)
//                .captureStrategy(new CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".fileprovider"))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .thumbnailScale(0.70f)
//                .imageEngine(new Glide4Engine())
//                .forResult(CODE_CHOOSE_FILE);
    }

}