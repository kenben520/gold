package com.lingxi.preciousmetal.util.glideutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.lingxi.common.base.GlideApp;
import com.lingxi.preciousmetal.util.ApplicationUtils;

/**
 * Glide 工具类
 * Created by zhangwei on 2018/4/18.
 */

public class GlideUtils {

    /**
     * Glide特点
     * 使用简单
     * 可配置度高，自适应程度高
     * 支持常见图片格式 Jpg png gif webp
     * 支持多种数据源  网络、本地、资源、Assets 等
     * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
     * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
     * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
     * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
     */

    //默认加载
    public static void loadImageView(Context mContext, String path, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            Glide.with(mContext).load(path).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载指定大小
    public static void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).override(width, height).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImageViewSizeCenterInside(Context mContext, int errorimg, String path, int width, int height, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).placeholder(errorimg).override(width, height).error(errorimg).centerInside().into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadImageViewError(Context mContext, String path, ImageView mImageView,int errorImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).error(errorImageView).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //设置加载中以及加载失败图片
    public static void loadImageViewLoding(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).placeholder(lodingImage).error(errorImageView).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置加载中以及加载失败图片并且指定大小
    public static void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView, int lodingImage, int errorImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).override(width, height).placeholder(lodingImage).error(errorImageView).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void preload(Context mContext, String path, int width, int height) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).centerInside().preload(width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置跳过内存缓存
    public static void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).skipMemoryCache(true).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置下载优先级
    public static void loadImageViewPriority(Context mContext, String path, Priority pt, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).priority(pt).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * （6）
     * 获取到Bitmap 高斯模糊
     *
     * @param context
     * @param errorimg
     * @param url
     */
    public static void showImageViewBlur(Context context, int errorimg,
                                         String url, ImageView mImageView) {
        if (context instanceof Activity && !ApplicationUtils.isActivityEnabled(context)) {
            return;
        }
        try {
            GlideApp.with(context).asBitmap().load(url).error(errorimg)
                    // 设置错误图片
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    // 缓存修改过的图片
                    .placeholder(errorimg)
                    .transform(new BlurTransformation(context))// 高斯模糊处理
                    .into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载圆角图片
    public static void loadRoundImage(Context context, int errorimg, int roundDPSize, String url, final ImageView imageView) {
        if (context instanceof Activity && !ApplicationUtils.isActivityEnabled(context)) {
            return;
        }
        try {
//            GlideApp.with(context).load(url).
//                    diskCacheStrategy(DiskCacheStrategy.ALL).
//                    placeholder(errorimg).transform(new GlideRoundTransform(context,roundDPSize))
//                    .into(imageView);
//            GlideApp.get(context).clearMemory();
            RequestOptions myOptions = new RequestOptions()
                    .transform(new GlideRoundTransform(context, roundDPSize));
            GlideApp.with(context)
                    .load(url)
                    .error(errorimg)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(myOptions)
                    .into(imageView);

//            GlideApp.with(context)
//                    .load(url).error(errorimg).diskCacheStrategy(DiskCacheStrategy.NONE)//设置缓存
//                    .placeholder(errorimg)
//                    .centerCrop() //千万不要加，加了就没有圆角效果了
//                    .transform(new GlideRoundTransform(context, roundDPSize))
//                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载圆形图片
    public static void loadCirclePic(final Context context, int errorimg, String url, final ImageView imageView) {
        if (context instanceof Activity && !ApplicationUtils.isActivityEnabled(context)) {
            return;
        }
        try {
            GlideApp.with(context).asBitmap()
                    .load(url)
                    .placeholder(errorimg)
                    .error(errorimg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadCirclePic(final Context context, int errorimg, int resourceId, final ImageView imageView) {
        if (context instanceof Activity && !ApplicationUtils.isActivityEnabled(context)) {
            return;
        }
        try {
            GlideApp.with(context).asBitmap()
                    .load(resourceId)
                    .placeholder(errorimg)
                    .error(errorimg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            imageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载圆形图片 回调
    public static void loadCirclePic(final Context context, int errorimg, String url, final ImageView imageView, final ImageLoaderListener listener) {
        if (context instanceof Activity && !ApplicationUtils.isActivityEnabled(context)) {
            return;
        }
        try {
//            GlideApp.with(context)
//                    .load(url)
//                    .placeholder(errorimg)
//                    .error(errorimg)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
//                    .into(new BitmapImageViewTarget(imageView) {
//                        @Override
//                        protected void setResource(Bitmap resource) {
//                            RoundedBitmapDrawable circularBitmapDrawable =
//                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                            circularBitmapDrawable.setCircular(true);
//                            imageView.setImageDrawable(circularBitmapDrawable);
//                            if (listener != null) {
//                                listener.onComplete(resource);
//                            }
//                        }
//                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ImageLoaderListener {
        public void onComplete(Bitmap bitmap);
    }

    /**
     * 策略解说：
     * <p>
     * all:缓存源资源和转换后的资源
     * <p>
     * none:不作任何磁盘缓存
     * <p>
     * source:缓存源资源
     * <p>
     * result：缓存转换后的资源
     */

    //设置缓存策略
    public static void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置加载动画
    public static void loadImageViewAnim(Context mContext, String path, int anim, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            // GlideApp.with(mContext).load(path).animate(anim).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 会先加载缩略图
     */
    public static void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置动态转换
     * api提供了比如：centerCrop()、fitCenter()等
     */
    public static void loadImageViewCrop(Context mContext, String path, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).centerCrop().into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置动态转换
     * api提供了比如：centerCrop()、fitCenter()等
     */
    public static void loadImageViewCrop(Context mContext, int errorimg, String path, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).placeholder(errorimg).error(errorimg).centerCrop().into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImageViewCenterInside(Context mContext, int errorimg, String path, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(path).placeholder(errorimg).error(errorimg).centerInside().into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置动态转换
     * api提供了比如：centerCrop()、fitCenter()等
     */
    public static void loadImageViewCrop(Context mContext, int resourceId, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(resourceId).centerCrop().into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadImageViewFitCenter(Context mContext, int resourceId, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).load(resourceId).fitCenter().into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //设置动态GIF加载方式
    public static void loadImageViewDynamicGif(Context mContext, String path, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            // GlideApp.with(mContext).load(path).asGif().into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置静态GIF加载方式
    public static void loadImageViewStaticGif(Context mContext, String path, ImageView mImageView) {
        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
            return;
        }
        try {
            GlideApp.with(mContext).asBitmap().load(path).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘

//    //设置监听请求接口
//    public static void loadImageViewListener(Context mContext, String path, ImageView mImageView, RequestListener<String, GlideDrawable> requstlistener) {
//        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
//            return;
//        }
//        try {
//           // GlideApp.with(mContext).load(path).listener(requstlistener).into(mImageView);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排
//
//    //设置要加载的内容
//    public static void loadImageViewContent(Context mContext, String path, SimpleTarget<GlideDrawable> simpleTarget) {
//        if (mContext instanceof Activity && !ApplicationUtils.isActivityEnabled(mContext)) {
//            return;
//        }
//        try {
//            GlideApp.with(mContext).load(path).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(simpleTarget);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //清理磁盘缓存
    public static void GuideClearDiskCache(Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Glide.get(mContext).clearDiskCache();
    }

    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }
}
