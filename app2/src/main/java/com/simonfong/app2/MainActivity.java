package com.simonfong.app2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.simonfong.app2.mqtt.MqttActivity;

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

    @OnClick(R.id.btn_mqtt)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_mqtt:
                startActivity(new Intent(MainActivity.this, MqttActivity.class));
                break;
        }
    }
}
