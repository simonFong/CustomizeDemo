package cn.dlc.zizhuyinliaoji.myapplication.map.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixukang   on  2018/5/9.
 */

public class MapUtill {
    /**
     * 打开高德地图
     * @param context
     * @param addressName
     * @param lat
     * @param lon
     */
    public void openGaoDeMap(Context context, String addressName, String lat, String lon) {
        if (isAvilible(context, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.getIntent(
                    "androidamap://navi?sourceApplication=softname&poiname="
                        + addressName
                        + "&lat="
                        + lat
                        + "&lon="
                        + lon
                        + "&dev=0");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    public void openBaiduMap(Context context, String addressName, String lat, String lon) {
        if (isAvilible(context, "com.baidu.BaiduMap")) {//传入指定应用包名  

            try {
                Intent  intent = Intent.getIntent("intent://map/direction?" +
                    "destination=latlng:"+lat+","+lon+"|name:" 
                    + addressName+        //终点
                    "&mode=driving&" +          //导航路线方式
                    "region=" +           //
                    "&src=迪尔西#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent); //启动调用  
            } catch (URISyntaxException e) {

            }
        } else {//未安装  
            //market为路径，id为包名  
            //显示手机上所有的market商店  
            Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager   
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息   
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名   
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中   
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE   
        return packageNames.contains(packageName);
    }
}
