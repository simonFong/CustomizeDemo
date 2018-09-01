package cn.dlc.customizedemo.myapplication.addImage.ui;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.imagepicker.loader.ImageLoader;

import cn.dlc.customizedemo.myapplication.R;


/**
 * Created by liuwenzhuo on 2018/3/8.
 */

public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width,
        int height) {
        Glide.with(activity)
            .applyDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.ic_default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL))
            .load(path)
            .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width,
        int height) {
        Glide.with(activity)
            .applyDefaultRequestOptions(RequestOptions.placeholderOf(R.drawable.ic_default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL))
            .load(path)
            .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }
}