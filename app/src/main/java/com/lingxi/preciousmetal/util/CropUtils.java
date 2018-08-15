package com.lingxi.preciousmetal.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.lingxi.common.base.BaseApplication;
import com.lingxi.net.basenet.util.SecurityUtils;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhangwei on 2018/4/27.
 */

public class CropUtils {
    private static final String CROPPED_PIC_NAME = "CropImage";
    private final static String CROP_PIC_PATH = "/Android/data/com.lingxi.net.uploadpic/";

    public static void startCrop(String cropPicPath, Object object) {
        UCrop uCrop = UCrop.of(Uri.fromFile(new File(cropPicPath)), Uri.fromFile(new File(getCropPicDir() + SecurityUtils.getMd5(CROPPED_PIC_NAME + System.currentTimeMillis()) + ".jpg")));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
//        options.setCompressionQuality(90);
        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(false);
        options.setCircleDimmedLayer(true);
        options.setMaxBitmapSize(640);
//        options.setCropGridColor(ContextCompat.getColor(getContext(), R.color.black));
        options.setToolbarColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.black));
        options.setStatusBarColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.black));
        options.setRootViewBackgroundColor(ContextCompat.getColor(BaseApplication.getInstance(), R.color.whiteTwo));
        uCrop = uCrop.withOptions(options);
        uCrop.start(BaseApplication.getInstance(), (Fragment) object);
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    public static void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            ToastUtils.showShort(cropError.getMessage());
        } else {
            ToastUtils.showShort("请重新选择图片");
        }
    }

    public static String getCropPicDir() {
        String storageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_REMOVED.equals(storageState)) {
            return null;
        }
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + CROP_PIC_PATH;
        File filedir = new File(path);
        if (filedir != null && !filedir.exists()) {
            filedir.mkdirs();
        }
        return path;
    }
    public static String temp = MyApplication.imageDirectory.getAbsolutePath();

    public static String compress(String path) {
        try {
            String target = createTempImageName(path);
            ImageUtils.compressEx(path, 95, 375, Integer.MAX_VALUE, target, 2048);
            String temppath = target;
            return temppath;
        } catch (IOException e) {
            Log.e("compress", e.getMessage());
        }
        return "";
    }

    private static String createTempImageName(String srcName) {
        return temp + "/" + System.currentTimeMillis() + srcName.substring(srcName.lastIndexOf("."));
    }
}
