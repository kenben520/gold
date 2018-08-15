package com.lingxi.preciousmetal.util;

import android.app.Activity;

import com.lingxi.common.util.StringUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by zhangwei on 2018/6/28.
 */

public class ShareUtils {
    public static void share(final Activity context, String tittle, String link) {
        share(context, tittle, "", "", link, null);
    }

    public static void share(final Activity context, String tittle, String content,
                             String imageUrl, String link, final UMShareListener callback) {
//        if (StringUtil.isEmpty(link)) return;
//        if (StringUtil.isEmpty(tittle)) return;
        UMImage image;
        if (StringUtil.isEmpty(imageUrl)) {
            image = new UMImage(context, R.mipmap.app_logo);//网络图片
        } else {
            image = new UMImage(context, imageUrl);//网络图片
        }
        if (StringUtil.isEmpty(content)) {
            content = link;
        }
        UMWeb web = new UMWeb(link);
        web.setTitle(tittle);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(content);//描述
        new ShareAction(context).withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        if(callback!=null){
                            callback.onStart(share_media);
                        }
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        if(callback!=null){
                            callback.onResult(share_media);
                        }else{
                            ToastUtils.showLong("分享成功");
                        }
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        if(callback!=null){
                            callback.onResult(share_media);
                        }else{
                            ToastUtils.showLong("分享失败");
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        if(callback!=null){
                            callback.onStart(share_media);
                        }
                    }
                }).open();
    }
}
