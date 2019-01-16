package cn.dlc.customizedemo.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by wuyufeng    on  2018/12/7 0007.
 * 播放语音
 */

public class SoundPlayUtil {
    private static AudioManager mainAudioManager;
    private Context context;
    private static SoundPool soundPool;
    private static int soundID;
    private static float volume;
    // Stream type.
    private static final int streamType = AudioManager.STREAM_ALARM;

    public static void play(Context context, int resId) {

        if (soundPool == null) {
            // AudioManager audio settings for adjusting the volume
            mainAudioManager = (AudioManager)context. getSystemService(Context.AUDIO_SERVICE);

            // Current volumn Index of particular stream type.
            float currentVolumeIndex = (float) mainAudioManager.getStreamVolume(streamType);

            // Get the maximum volume index for a particular stream type.
            float maxVolumeIndex  = (float) mainAudioManager.getStreamMaxVolume(streamType);

            // Volumn (0 --> 1)
            volume = currentVolumeIndex / maxVolumeIndex;
            
            // Suggests an audio stream whose volume should be changed by
            // the hardware volume controls.
            ((Activity)context).setVolumeControlStream(streamType);
            
            // 版本兼容
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                soundPool= new SoundPool.Builder().setMaxStreams(10).build();
            } else {
                //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
                soundPool = new SoundPool(10, AudioManager.STREAM_ALARM, 1);
            }
        }
        try {
            soundPool.stop(soundID); 
        }catch (Exception e){
        }
        soundID = soundPool.load(context, resId, 1);
        // 该方法防止sample not ready错误
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i2) {
                soundPool.play(
                    soundID,  //声音id
                    volume, //左声道
                    volume, //右声道
                    1, //播放优先级【0表示最低优先级】
                    0, //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                    1);//播放速度【1是正常，范围从0~2】一般为1
            }
        });

    }
}
