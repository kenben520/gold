package com.lingxi.preciousmetal.ui.fragment.analyzeplate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lingxi.common.base.BaseDialogFragment;
import com.lingxi.preciousmetal.R;

/**
 * Created by zhangwei on 2018/7/5.
 */

public class MyDialogFragment  extends BaseDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.polyv_fragment_question, container);
        return view;
    }
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.theme);
//        builder.setTitle("对话框");
//        View view = inflater.inflate(R.layout.fragment_dialog, container);
//        builder.setView(view);
//        builder.setPositiveButton("确定", null);
//        builder.setNegativeButton("取消", null);
//        return builder.create();
//    }

}
