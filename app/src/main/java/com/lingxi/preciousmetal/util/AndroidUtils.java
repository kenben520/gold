package com.lingxi.preciousmetal.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.util.MPermissionUtils;
import com.lingxi.preciousmetal.domain.Banks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by zhangwei on 2018/4/18.
 */

public class AndroidUtils {
    private static final String TAG = AndroidUtils.class.getSimpleName();

    private AndroidUtils() {
    }

    private static class AndroidUtilHodler {
        private static AndroidUtils Instance = new AndroidUtils();
    }

    public static AndroidUtils getInstance() {
        return AndroidUtilHodler.Instance;
    }

    public void showToast(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void selectImage(final Activity activity, final int requestCode) {
        if (activity == null) return;
        MPermissionUtils.requestPermissionsResult(activity, 0, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                        // max select image amount
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity
                                .MODE_SINGLE);
                        activity.startActivityForResult(intent, requestCode);
                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(activity);
                    }
                });
    }

    public void selectImage(final Fragment activity, final int requestCode) {
        if (activity == null) return;
        MPermissionUtils.requestPermissionsResult(activity, 0, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                new MPermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(getContext(activity), MultiImageSelectorActivity.class);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                        // max select image amount
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity
                                .MODE_SINGLE);
                        activity.startActivityForResult(intent, requestCode);
                    }

                    @Override
                    public void onPermissionDenied() {
                        MPermissionUtils.showTipsDialog(getContext(activity));
                    }
                });
    }

    /**
     * 获取上下文
     */
    private Context getContext(Object object) {
        Context context;
        if (object instanceof android.app.Fragment) {
            context = ((android.app.Fragment) object).getActivity();
        } else if (object instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) object).getActivity();
        } else {
            context = (Activity) object;
        }
        return context;
    }

    public Banks getBanks() {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = BaseApplication.getInstance().getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("banks.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(stringBuilder.toString(), Banks.class);
    }

    public void copyTextToClipboard(String txt, Context mContext) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                    mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(txt);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("text " +
                    "label", txt);
            clipboard.setPrimaryClip(clip);
        }
    }
}
