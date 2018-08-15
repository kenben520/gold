package com.lingxi.preciousmetal.ui.widget;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String pictures = (String)path;
        //GlideUtils.loadImageViewDiskCache(context,pictures,imageView);
//        GlideUtils.loadRoundImage(context,0,5,pictures,imageView);
//        GlideUtils.loadImageViewCache(context,(String) path,imageView);
        GlideUtils.loadImageViewCrop(context, R.drawable.background_gray4, (String) path, imageView);

//        GlideUtils.loadImageView(context,pictures,imageView);
     //   imageView.setBackgroundResource(R.mipmap.home_banner1);

        //Glide 加载图片简单用法
//         EdAdvertInfo bean = (EdAdvertInfo) path;
//        String pictures = UrlConstant.LOGINIP + bean.getCAdvertIco();
//         GlideApp.with(context)
//                .load(pictures)
//                .placeholder(R.drawable.banner3)
//                .skipMemoryCache(true)
//                .error(R.drawable.attendance_banner)
//                .into(imageView);
    }

}