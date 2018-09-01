package cn.dlc.customizedemo.myapplication.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Weather;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dlc.customizedemo.myapplication.R;

/**
 * 使用了mob接口获取天气
 */
public class WeatherActivity extends AppCompatActivity {

    @BindView(R.id.weather)
    TextView mWeather;
    @BindView(R.id.today_weather)
    Button mTodayWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.today_weather)
    public void onViewClicked() {
        Weather api = (Weather) MobAPI.getAPI(Weather.NAME);
        api.getSupportedCities(new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                Log.e("simon", map.toString());
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {

            }
        });
        api.queryByCityName("东莞", new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                Log.e("simon", map.toString());
                List result = (List) map.get("result");
                Map o = (Map) result.get(0);
                String s = o.toString();
                mWeather.setText(s);
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {

            }
        });

    }
}
