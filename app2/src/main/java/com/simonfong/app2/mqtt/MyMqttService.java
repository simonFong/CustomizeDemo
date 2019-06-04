package com.simonfong.app2.mqtt;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.licheedev.myutils.LogPlus;
import com.simonfong.app2.App;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author fengzimin
 * Created   on  2019/05/08.
 * interface by
 * mqtt服务，主要负责初始化mqtt配置，提供mqtt发送，接收方法，并进行接收数据逻辑操作
 */
public class MyMqttService extends Service {

    private static final String TAG = "MyMqttService";
    private String host = "tcp://120.77.72.190:1883";
    private String clientId = "cn.dlc.xiaoyaoyouleshebei" + App.getMacno();

    private String userName = "dlc";
    private String passWord = "123456";
    private static String myTopic = "zsxyylsb/" + App.getMacno();

    private static MqttAndroidClient client;
    private MqttConnectOptions conOpt;
    private Gson mGson;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //初始化
                init();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 启动心跳，30秒发送一次心跳
     */
    private void startHeartbeat() {
        Observable.interval(0, 20, TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                        LogPlus.e("发送心跳");

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 抽取出来的发送函数，供调用
     *
     * @param topic 发送的主题
     * @param msg   发送的消息
     * @param qos   QOS ＝　0/1/2  　最多一次　　最少一次　多次
     */
    public static void publish(String topic, String msg, int qos) {
        //是否保留消息，为true时,后来订阅该主题的仍然收到该消息
        boolean retained = false;
        try {
            client.publish(topic, msg.getBytes(), qos, retained);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        // 服务器地址（协议+地址+端口号）
        String uri = host;
        client = new MqttAndroidClient(this.getApplicationContext(), uri, clientId);
        // 设置MQTT监听并且接受消息
        client.setCallback(mqttCallback);

        conOpt = new MqttConnectOptions();
        // 不清除缓存　为false表示服务器会保留客户端的连接记录，否则每次连接到服务器都会以新的身份接入　　　　
        conOpt.setCleanSession(false);
        //设置自动重连
        conOpt.setAutomaticReconnect(true);
        // 设置超时时间，单位：秒　　这个有默认
        conOpt.setConnectionTimeout(10);
        // mqtt自带实现的心跳包发送间隔，单位：秒　　　这个有默认60
        conOpt.setKeepAliveInterval(20);
        // 用户名
        conOpt.setUserName(userName);
        // 密码
        conOpt.setPassword(passWord.toCharArray());

        boolean doConnect = true;
        String message = "{\"terminal_uid\":\"" + clientId + "掉线\"}";
        String topic = myTopic;
        Integer qos = 2;
        Boolean retained = false;
        if ((!message.equals("")) || (!topic.equals(""))) {
            // 最后的遗嘱 也就是断线之后发送出来的消息,测试发现　收到这个消息的时候已经掉线很久
            try {
                conOpt.setWill(topic, message.getBytes(), qos, retained);
                LogPlus.e(" conOpt.setWill");
            } catch (Exception e) {
                LogPlus.e(e.toString());
                doConnect = false;
                iMqttActionListener.onFailure(null, e);
            }
        }
        if (doConnect) {
            doClientConnection();
        }
    }

    @Override
    public void onDestroy() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    //连接MQTT服务器
    private void doClientConnection() {
        LogPlus.e("doClientConnection");
        if (!client.isConnected() && isConnectIsNomarl()) {
            try {
                client.connect(conOpt, null, iMqttActionListener);
                LogPlus.e(" client.connect");
            } catch (MqttException e) {
                e.printStackTrace();
                LogPlus.e(e.toString());
            }
        }
    }


    // MQTT是否连接成功
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            LogPlus.e("连接成功:Topic+" + myTopic);
            try {
                // 订阅myTopic话题
                String[] topics = {myTopic};
                int[] ints = {0};
                client.subscribe(topics, ints);
                //                client.subscribe(myTopic, 0);
                LogPlus.e("连接成功 ");
                //                startHeartbeat();

            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            arg1.printStackTrace();
            LogPlus.e(" 失败 " + arg1.toString());
            // 连接失败，重连
        }
    };


    // MQTT监听并且接受消息
    private MqttCallback mqttCallback = new MqttCallback() {
        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            try {
                String content = new String(message.getPayload());
                Log.e(TAG, message.getId() + "-->receive message: " + content);
                if (mGson == null) {
                    mGson = new Gson();
                }
                //TODO 接收数据


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {
            LogPlus.e("已发送=" + arg0.toString());
        }

        @Override
        public void connectionLost(Throwable arg0) {
            LogPlus.e("connectionLost----断线");
            // 失去连接，重连
            //先判断客户端是否为空　　然后客户端.connect（x,x）
        }
    };


    // 判断网络是否连接
    public static boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstances().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            LogPlus.e("MQTT当前网络名称：" + name);
            return true;
        } else {
            LogPlus.e("MQTT 没有可用网络");
            return false;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
