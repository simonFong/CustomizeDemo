package cn.dlc.zizhuyinliaoji.myapplication.map;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dlc.zizhuyinliaoji.myapplication.map.dialog.MapNavigationDialog;
import cn.dlc.zizhuyinliaoji.myapplication.R;
import cn.dlc.zizhuyinliaoji.myapplication.map.utils.MapUtill;

public class MapActivity extends AppCompatActivity {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    @BindView(R.id.iv_location)
    ImageView mIvLocation;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.daohang_tv)
    TextView mDaohangTv;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        initMapView(savedInstanceState);
    }

    private void initMapView(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @OnClick({R.id.iv_location, R.id.daohang_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_location:
                initLocation();
                break;
            case R.id.daohang_tv:
                MapNavigationDialog mapNavigationDialog = new MapNavigationDialog(this);
                mapNavigationDialog.setOnMapCallBack(new MapNavigationDialog.MapCallBack() {
                    @Override
                    public void baidu() {
                        /**
                         *
                         * poiname 目的地名字
                         * lat lon 目的地的经纬度
                         *
                         */
                        MapUtill map = new MapUtill();
                        map.openBaiduMap(MapActivity.this,"深圳","22.652","113.966");

                    }

                    @Override
                    public void gaode() {
                        MapUtill map = new MapUtill();
                        map.openGaoDeMap(MapActivity.this,"深圳","22.652","113.966");

                    }
                });
                mapNavigationDialog.show();
                break;
        }
    }

    private void initLocation() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType
        // (MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        myLocationStyle.showMyLocation(true);
        //设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Log.e("ds", location.toString());
            }
        });
    }
}
