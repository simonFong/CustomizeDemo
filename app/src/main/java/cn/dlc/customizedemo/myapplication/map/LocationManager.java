package cn.dlc.customizedemo.myapplication.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.licheedev.myutils.LogPlus;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * Created by wuyufeng    on  2018/7/6 0006.
 * interface by
 */

public class LocationManager {
    private final android.location.LocationManager lm;
    Activity mActivity;
    private AMapLocationClient mLocationClient;
    private AMapLocationListener mLocationListener;
    private LocationCallBack mLocationCallBack;
    public boolean isNoGas = false;
    @SuppressLint("ServiceCast")
    public LocationManager(Activity activity) {
        mActivity = activity;
        lm = (android.location.LocationManager) activity.getSystemService(activity.LOCATION_SERVICE); 
        boolean ok = lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        isNoGas = false;
        if(ok){
            checkPermission();
        }else {
            Toast.makeText(activity, "未开启定位服务,请开启才能正常使用", Toast.LENGTH_SHORT).show();  
            Intent intent = new Intent();  
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivity(intent);
            isNoGas = true;
        }
    }

    public void checkPermission(){
        //先判断是否有定位权限
        AndPermission.with(mActivity)
            .requestCode(100)
            .permission(Permission.LOCATION)
            .callback(listener)
            .rationale(mRationaleListener)
            .start();
    }
    
    //定位权限监听
    PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            if(100==requestCode){//定位
                initLocation();
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

            if(100==requestCode){//定位
                AndPermission.defaultSettingDialog(mActivity, 100)
                    .setTitle("权限申请失败")
                    .setMessage("需定位基本权限,否则您将无法正常使用，请在设置中授权")
                    .setPositiveButton("好，去设置")
                    .setNegativeButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .show();
            }

        }
    };

    //用户拒绝一次权限后，再次申请时检测到已经申请过一次该权限了，允许开发者弹窗说明申请权限的目的，获取用户的同意后再申请权限，避免用户勾选不再提示，导致不能再次申请权限
    private RationaleListener mRationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            if(100==requestCode){
                new AlertDialog.Builder(mActivity)
                    .setTitle("权限提示")
                    .setCancelable(false)
                    .setMessage("没有定位权限,您某些功能将无法正常使用")
                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();// 用户同意继续申请。
                        }
                    })
                    .setNegativeButton("狠心拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel(); // 用户拒绝申请。
                        }
                    }).show();
            }

        }
    };
    

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(mActivity);
        mLocationClient.setLocationOption(getOption());
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        LogPlus.e("aMapLocation ===== " + aMapLocation.toString());
                        //当定位错误码类型为0时定位成功,可在其中解析amapLocation获取相应内容。
                        //aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        //aMapLocation.getLatitude();//获取纬度
                        //aMapLocation.getLongitude();//获取经度
                        //aMapLocation.getAccuracy();//获取精度信息
                        //aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                        //aMapLocation.getCountry();//国家信息
                        //aMapLocation.getProvince();//省信息
                        //aMapLocation.getCity();//城市信息
                        //aMapLocation.getDistrict();//城区信息
                        //aMapLocation.getStreet();//街道信息
                        //aMapLocation.getStreetNum();//街道门牌号信息
                        //aMapLocation.getCityCode();//城市编码
                        //aMapLocation.getAdCode();//地区编码
                        //aMapLocation.getAoiName();//获取当前定位点的AOI信息
                        //aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                        //aMapLocation.getFloor();//获取当前室内定位的楼层
                        //aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                        ////获取定位时间
                        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        //Date date = new Date(aMapLocation.getTime());
                        //df.format(date);

                        Log.e("print", "经纬度: "
                            + aMapLocation.getLongitude()
                            + "--"
                            + aMapLocation.getLatitude()+ "--"
                            + aMapLocation.getCity()+ "--"
                            + aMapLocation.getAdCode());
                        if (mLocationCallBack != null) {
                            mLocationCallBack.locationSuccess(aMapLocation.getCity(),
                                aMapLocation.getAdCode(), aMapLocation.getLatitude(),
                                aMapLocation.getLongitude());
                        }
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        LogPlus.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode()
                            + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                        if (mLocationCallBack != null) {
                            mLocationCallBack.locationFailed(aMapLocation.getErrorCode()
                                + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                        }
                    }
                }
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationClient.startLocation();
    }

    private AMapLocationClientOption getOption() {
        //初始化AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        mLocationOption.setLocationCacheEnable(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        return mLocationOption;
    }

    public interface LocationCallBack {
        void locationSuccess(String city, String adCode, double latitude, double longitude);

        void locationFailed(String tip);
    }

    public void setLocationCallBack(LocationCallBack callBack) {
        mLocationCallBack = callBack;
    }

    /**
     * //停止定位后，本地定位服务并不会被销毁
       //销毁定位客户端，同时销毁本地定位服务。
     */
    public void stopLocation(){
        if(mLocationClient!=null){
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }
}
