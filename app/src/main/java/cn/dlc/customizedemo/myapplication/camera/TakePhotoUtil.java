package cn.dlc.customizedemo.myapplication.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fengzimin  on  2019/04/16.
 * interface by
 */
public class TakePhotoUtil implements SurfaceHolder.Callback {
    private static final String TAG = "TakePhotoUtil";
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private ImageView mImageView;
    private Context mContext;
    private CameraPreListener listener;
    private int maxLength = 20;


    public void initPhote(Context context, SurfaceView surfaceView1, ImageView imageView) {
        mContext = context;
        mImageView = imageView;
        surfaceHolder = surfaceView1.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(this);
    }

    public void takePhoto() {
        if (camera != null) {
            camera.autoFocus(afcb);
        }
    }

    Camera.AutoFocusCallback afcb = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            // TODO Auto-generated method stub
            if (success) {
                //对焦成功才拍照
                camera.takePicture(null, null, jpeg);
            }
        }
    };

    Camera.PictureCallback jpeg = (data, camera) -> {

        uploadImage(data);
        // TODO Auto-generated method stub
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        if (listener != null) {
            listener.preView(bmp);
        }
        //byte数组转换成Bitmap
        //            mImageView.setImageBitmap(bmp);

        camera.startPreview();
        //需要手动重新startPreview，否则停在拍下的瞬间
    };

    private void uploadImage(byte[] data) {

        File file = mContext.getExternalFilesDir("camera_img");

        File[] files = file.listFiles();

        List<File> fileList = new ArrayList<>();
        if (file.exists() && file.isDirectory()) {
            for (int i = 0; i < files.length; i++) {
                fileList.add(files[i]);
            }
            Collections.sort(fileList, new FileComparator());
        }

        for (File file1 : fileList) {
            Log.e(TAG, "fileName:" + file1.getName());
        }
        if (fileList.size() > maxLength) {
            Log.e(TAG, "delete_fileName:" + fileList.get(fileList.size() - 1));
            fileList.get(fileList.size() - 1).delete();
        }

        //写入数据
        File file2 = new File(file.getPath() + "/" + System.currentTimeMillis() + ".jpg");
        OutputStream output = null;
        try {
            output = new FileOutputStream(file2);

            BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);

            bufferedOutput.write(data);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据时间排序
     */
    class FileComparator implements Comparator<File> {

        @Override
        public int compare(File file1, File file2) {
            if (file1.lastModified() < file2.lastModified()) {
                return 1;// 最后修改的文件在前
            } else {
                return -1;
            }
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera = Camera.open();
        setCamera();
    }

    private void setCamera() {
        try {
            int PreviewWidth = 0;
            int PreviewHeight = 0;
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);//获取窗口的管理器
            Display display = wm.getDefaultDisplay();//获得窗口里面的屏幕
            Camera.Parameters parameters = camera.getParameters();
            // 选择合适的预览尺寸
            List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
            for (int i = 0; i < sizeList.size(); i++) {
                Log.e(TAG, "size" + i + ":" + sizeList.get(i).width + "----" + sizeList.get(i).height);
            }
            // 如果sizeList只有一个我们也没有必要做什么了，因为就他一个别无选择
            if (sizeList.size() > 1) {
                Iterator<Camera.Size> itor = sizeList.iterator();
                while (itor.hasNext()) {
                    Camera.Size cur = itor.next();
                    if (cur.width >= PreviewWidth && cur.height >= PreviewHeight) {
                        PreviewWidth = cur.width;
                        PreviewHeight = cur.height;
                        break;
                    }
                }
            }
            parameters.setPreviewSize(PreviewWidth, PreviewHeight); //获得摄像区域的大小
            //            parameters.setPreviewFrameRate(3);//每秒3帧  每秒从摄像头里面获得3个画面
            parameters.setPictureFormat(PixelFormat.JPEG);//设置照片输出的格式
            parameters.set("jpeg-quality", 30);//设置照片质量
            parameters.setPictureSize(PreviewWidth, PreviewHeight);//设置拍出来的屏幕大小
            //
            camera.setParameters(parameters);//把上面的设置 赋给摄像头
            camera.setPreviewDisplay(surfaceHolder);//把摄像头获得画面显示在SurfaceView控件里面
            camera.startPreview();//开始预览
            if (listener != null) {
                listener.complete();
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception:" + e.getMessage());
            if (listener != null) {
                listener.fail();
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        System.out.println("surfaceDestroyed");
        camera.stopPreview();
        //关闭预览
        camera.release();
        //释放资源
        camera = null;
    }

    public interface CameraPreListener {
        void preView(Bitmap bitmap);

        void complete();

        void fail();
    }

    public void setCameraPreListener(CameraPreListener listener) {
        this.listener = listener;
    }
}
