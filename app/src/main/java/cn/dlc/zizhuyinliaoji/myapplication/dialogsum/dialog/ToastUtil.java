package cn.dlc.zizhuyinliaoji.myapplication.dialogsum.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.dlc.zizhuyinliaoji.myapplication.R;


/**
 * Created by wuyufeng on 2018/4/3.
 */

public class ToastUtil {
    public static void showToastCenter(Context ctx,String str){
        Toast toast = Toast.makeText(ctx, str, Toast.LENGTH_SHORT);
        toast.setGravity(0, Gravity.CENTER,Gravity.CENTER);
        toast.show();
    }

    public static void showToastLong(Context ctx, String str) {
        Toast.makeText(ctx, str, Toast.LENGTH_LONG).show();
    }

    public static void showToastShort(Context ctx, String str) {
        Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show();
    }

    public static void showCustomToastCenterShort(Activity activity, boolean isSuccessful, String failReason) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_pay_result,null);

        ImageView img_icon = layout.findViewById(R.id.img_icon);
        TextView tv_content = layout.findViewById(R.id.tv_content);
        if(isSuccessful){
            img_icon.setBackgroundResource(R.mipmap.suceed);
            tv_content.setText(failReason);
        }else {
            img_icon.setBackgroundResource(R.mipmap.a_false);
            tv_content.setText(failReason);
        }

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    
    public static void showCustomToastCenterLong(Activity activity, boolean isSuccessful, String failReason) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_pay_result,null);

        ImageView img_icon = layout.findViewById(R.id.img_icon);
        TextView tv_content = layout.findViewById(R.id.tv_content);
        if(isSuccessful){
            img_icon.setBackgroundResource(R.mipmap.suceed);
            tv_content.setText("支付成功");
        }else {
            img_icon.setBackgroundResource(R.mipmap.a_false);
            tv_content.setText("支付失败");
        }

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    

}
