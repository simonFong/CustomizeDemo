package com.simonfong.app2.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by fengzimin  on  2019/04/24.
 * interface by
 */
public class MyMqttClient {
    /**
     * 代理服务器ip地址
     */
    public static final String MQTT_BROKER_HOST = "tcp://192.168.123.226:61613";

    /**
     * 客户端唯一标识
     */
    public static final String MQTT_CLIENT_ID = "text_ip_1";

    /**
     * 订阅标识
     */
    public static final String MQTT_TOPIC = "text";

    /**
     *
     */
    public static final String USERNAME = "admin";
    /**
     * 密码
     */
    public static final String PASSWORD = "password";

    private volatile static MqttClient mqttClient;
    private static MqttConnectOptions options;

    public static void main(String... args) {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，
            // MemoryPersistence设置clientid的保存形式，默认为以内存保存
            // 设备id不要太骚气！！！！！！！
            mqttClient = new MqttClient(MQTT_BROKER_HOST, MQTT_CLIENT_ID, new MemoryPersistence());
            // 配置参数信息
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
            // 这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置用户名
            options.setUserName(USERNAME);
            // 设置密码
            options.setPassword(PASSWORD.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            // 连接
            mqttClient.connect(options);
            // 订阅
            mqttClient.subscribe(MQTT_TOPIC);
            // 设置回调
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("connectionLost");
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    System.out.println("Topic: " + s + " Message: " + mqttMessage.toString());
//                    send();

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });

            Observable.interval(0,5, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Long aLong) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
//            PublishSample("","","");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void send() throws MqttException {
        if (mqttClient != null) {
            MqttMessage message = new MqttMessage();
            message.setQos(1);
            message.setPayload("我是服务端".getBytes());

            mqttClient.publish(MQTT_TOPIC, message);

        }

    }

    /**
     * 发布
     */
    public static void PublishSample(String topic, String content,String clientId){
        // 内存存储
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            // 创建客户端
            MqttClient sampleClient = new MqttClient(MQTT_BROKER_HOST, MQTT_CLIENT_ID, persistence);
            // 创建链接参数
            MqttConnectOptions connOpts = new MqttConnectOptions();
            // 在重新启动和重新连接时记住状态
            connOpts.setCleanSession(false);
            // 设置连接的用户名
            connOpts.setUserName(USERNAME);
            connOpts.setPassword(PASSWORD.toCharArray());
            // 建立连接
            sampleClient.connect(connOpts);
            // 创建消息
            MqttMessage message = new MqttMessage("我是服务器".getBytes());
            // 设置消息的服务质量
            message.setQos(1);
            // 发布消息
            sampleClient.publish(MQTT_TOPIC, message);
            // 断开连接
            sampleClient.disconnect();
            // 关闭客户端
            sampleClient.close();
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }

    }

}
