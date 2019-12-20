package com.simonfong.app2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.simonfong.app2.demo.DemoActivity;
import com.simonfong.app2.drawerlayout.DrawerLayoutActivity;
import com.simonfong.app2.exoplayer.ExoplayerActivity;
import com.simonfong.app2.mqtt.MqttActivity;
import com.simonfong.app2.permissions.PermissionsActivity;
import com.simonfong.app2.picturechoose.PictureChooseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_mqtt)
    Button mBtnMqtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.demo, R.id.btn_mqtt, R.id.btn_exo_player, R.id.picture_choose, R.id.drawerlayout,R.id.permissions})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_exo_player:
                startActivity(new Intent(MainActivity.this, ExoplayerActivity.class));
                break;
            case R.id.btn_mqtt:
                startActivity(new Intent(MainActivity.this, MqttActivity.class));
                break;
            case R.id.demo:
                startActivity(new Intent(MainActivity.this, DemoActivity.class));
                break;
            case R.id.picture_choose:
                startActivity(new Intent(MainActivity.this, PictureChooseActivity.class));
                break;
            case R.id.drawerlayout:
                startActivity(new Intent(MainActivity.this, DrawerLayoutActivity.class));
                break;
            case R.id.permissions:
                startActivity(new Intent(MainActivity.this, PermissionsActivity.class));
                break;
        }
    }
}
