package cn.dlc.customizedemo.myapplication.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import cn.dlc.customizedemo.myapplication.R;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by fengzimin  on  2019/04/16.
 * interface by
 */
public class CameraActivity extends BaseCommonActivity {
    @BindView(R.id.surfaceView1)
    SurfaceView mSurfaceView1;
    @BindView(R.id.button1)
    Button mButton1;
    @BindView(R.id.linearLayout1)
    LinearLayout mLinearLayout1;
    @BindView(R.id.imageView1)
    ImageView mImageView1;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_camera;
    }


    private static final String TAG = "MainActivity";
    private TakePhotoUtil mTakePhotoUtil;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查是否获得写入权限，未获得则向用户请求
        if (ActivityCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //未获得，向用户请求
            Log.d(TAG, "无读写权限，开始请求权限。");
            ActivityCompat.requestPermissions(CameraActivity.this, new String[]
                    {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        } else {
            Log.d(TAG, "有读写权限，准备启动相机。");
            //启动照相机
            init();

        }
    }

    private void init() {
        mTakePhotoUtil = new TakePhotoUtil();
        mTakePhotoUtil.initPhote(this, mSurfaceView1, mImageView1);
        mTakePhotoUtil.setCameraPreListener(new TakePhotoUtil.CameraPreListener() {
            @Override
            public void preView(Bitmap bitmap) {
                runOnUiThread(() -> mImageView1.setImageBitmap(bitmap));
            }

            @Override
            public void complete() {
                if (mDisposable != null && !mDisposable.isDisposed()) {
                    mDisposable.dispose();
                }
                //                mDisposable = Observable.interval(2, 10, TimeUnit.SECONDS)
                //                        .compose(CameraActivity.this.bindUntilEvent(ActivityEvent.DESTROY))
                //                        .retry()
                //                        .observeOn(Schedulers.newThread())
                //                        .subscribeOn(Schedulers.newThread())
                //                        .subscribe(aLong -> mTakePhotoUtil.takePhoto());
            }

            @Override
            public void fail() {

            }
        });
    }

    /**
     * 返回用户是否允许权限的结果，并处理
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
        if (requestCode == 200) {
            //用户允许权限
            if (grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "用户已允许权限，准备启动相机。");
                //启动照相机
                init();
            } else {  //用户拒绝
                Log.d(TAG, "用户已拒绝权限，程序终止。");
                Toast.makeText(this, "程序需要相机、写入权限才能运行", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
