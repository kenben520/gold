package com.lingxi.preciousmetal.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.common.base.BaseApplication;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.ApplicationUtils;
import com.lingxi.preciousmetal.util.DisplayUtil;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.SPUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.lingxi.preciousmetal.common.CommonParam.SIGNAL_SORT_SET;

/**
 * 指标时间排序
 * Created by zhangwei on 2018/4/23.
 */

public class SignalSortSettingActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.tag_1)
    TextView tag1;
    @BindView(R.id.tag_2)
    TextView tag2;
    @BindView(R.id.tag_3)
    TextView tag3;
    @BindView(R.id.tag_4)
    TextView tag4;
    @BindView(R.id.tag_5)
    TextView tag5;
    @BindView(R.id.tag_6)
    TextView tag6;
    @BindView(R.id.tag_7)
    TextView tag7;
    @BindView(R.id.tag_8)
    TextView tag8;
    @BindView(R.id.tag_9)
    TextView tag9;
    @BindView(R.id.layout_select_signal)
    LinearLayout layoutSelectSignal;
//    List<String> oldSignalList = new ArrayList<>();
    List<String> sortSignalList = new ArrayList<>();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SignalSortSettingActivity.class);
       // context.startActivity(intent);
        Activity activity = (Activity)context;
        activity.startActivityForResult(intent, 100);
    }

//    @Override
//    public void onBackPressed() {
//       // super.onBackPressed();
//        this.setResult(100,null);
//        // 结束SelectCityActivity
//        SignalSortSettingActivity.this.finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal_sort_setting);
        ButterKnife.bind(this);
//        oldSignalList = MyApplication.getSortSignalList();
//        sortSignalList.addAll(oldSignalList);
        initTopBar();
        initView();
    }

    private void initView() {
        updateSelectSignalView();
        updateAllTagView();
    }


    private void updateAllTagView() {
        tag1.setSelected(false);
        tag2.setSelected(false);
        tag3.setSelected(false);
        tag4.setSelected(false);
        tag5.setSelected(false);
        tag6.setSelected(false);
        tag7.setSelected(false);
        tag8.setSelected(false);
        tag9.setSelected(false);
        for (int i = 0; i < sortSignalList.size(); i++) {
            String tag = sortSignalList.get(i);
            switch (tag) {
                case "1":
                    tag1.setSelected(true);
                    break;
                case "2":
                    tag2.setSelected(true);
                    break;
                case "3":
                    tag3.setSelected(true);
                    break;
                case "4":
                    tag4.setSelected(true);
                    break;
                case "5":
                    tag5.setSelected(true);
                    break;
                case "7":
                    tag6.setSelected(true);
                    break;
                case "8":
                    tag7.setSelected(true);
                    break;
                case "10":
                    tag8.setSelected(true);
                    break;
                case "11":
                    tag9.setSelected(true);
                    break;

            }
        }
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("自定义标签");
//        mTopbarView.setBackButtonOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SignalSortSettingActivity.this.setResult(100,null);
//                // 结束SelectCityActivity
//                SignalSortSettingActivity.this.finish();
//            }
//        });

        mTopbarView.setActionTitle("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong("保存成功");
                if(ObjectUtils.isEmpty(sortSignalList))
                {
                    SignalSortSettingActivity.this.setResult(100,null);
                    SignalSortSettingActivity.this.finish();
                    return;
                }
                if(!tag1.isSelected())
                {
                    sortSignalList.add("1");
                }
                if(!tag2.isSelected())
                {
                    sortSignalList.add("2");
                }
                if(!tag3.isSelected())
                {
                    sortSignalList.add("3");
                }
                if(!tag4.isSelected())
                {
                    sortSignalList.add("4");
                }
                if(!tag5.isSelected())
                {
                    sortSignalList.add("5");
                }
                if(!tag6.isSelected())
                {
                    sortSignalList.add("7");
                }
                if(!tag7.isSelected())
                {
                    sortSignalList.add("8");
                }
                if(!tag8.isSelected())
                {
                    sortSignalList.add("10");
                }
                if(!tag9.isSelected())
                {
                    sortSignalList.add("11");
                }
                SPUtils.getInstance().put(SIGNAL_SORT_SET, BaseApplication.getInstance().mCustomJsonConverter.toJson(sortSignalList));
//                oldSignalList.clear();
//                oldSignalList.addAll(sortSignalList);
                MyApplication.getSortSignalList().clear();
                MyApplication.getSortSignalList().addAll(sortSignalList);
                SignalSortSettingActivity.this.setResult(100,null);
                SignalSortSettingActivity.this.finish();
            }
        });
    }

    @OnClick({R.id.tag_1, R.id.tag_2, R.id.tag_3, R.id.tag_4, R.id.tag_5, R.id.tag_6, R.id.tag_7, R.id.tag_8, R.id.tag_9})
    public void onViewClicked(View view) {
        if (view.isSelected()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tag_1:
                sortSignalList.remove("1");
                sortSignalList.add("1");
                tag1.setSelected(true);
                updateSelectSignalView();
                break;
            case R.id.tag_2:
                sortSignalList.remove("2");
                sortSignalList.add("2");
                tag2.setSelected(true);
                updateSelectSignalView();
                break;
            case R.id.tag_3:
                sortSignalList.remove("3");
                sortSignalList.add("3");
                tag3.setSelected(true);
                updateSelectSignalView();
                break;
            case R.id.tag_4:
                sortSignalList.remove("4");
                sortSignalList.add("4");
                tag4.setSelected(true);
                updateSelectSignalView();
                break;
            case R.id.tag_5:
                sortSignalList.remove("5");
                sortSignalList.add("5");
                tag5.setSelected(true);
                updateSelectSignalView();
                break;
            case R.id.tag_6:
                sortSignalList.remove("7");
                sortSignalList.add("7");
                tag6.setSelected(true);
                updateSelectSignalView();
                break;
            case R.id.tag_7:
                sortSignalList.remove("8");
                sortSignalList.add("8");
                tag7.setSelected(true);
                updateSelectSignalView();
                break;
            case R.id.tag_8:
                sortSignalList.remove("10");
                sortSignalList.add("10");
                tag8.setSelected(true);
                updateSelectSignalView();
                break;
            case R.id.tag_9:
                sortSignalList.remove("11");
                sortSignalList.add("11");
                tag9.setSelected(true);
                updateSelectSignalView();
                break;
        }
    }

    @OnClick({R.id.btn_reset})
    public void onViewClicked1(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                sortSignalList.clear();
//                sortSignalList.addAll(oldSignalList);
                updateSelectSignalView();
                updateAllTagView();
                break;
        }
    }

//        kTitles.add(new TimeTitleBean("1分",1));
//        kTitles.add(new TimeTitleBean("5分",2));
//        kTitles.add(new TimeTitleBean("15分",3));
//        kTitles.add(new TimeTitleBean("30分",4));
//        kTitles.add(new TimeTitleBean("1小时",5));
//        kTitles.add(new TimeTitleBean("4小时",7));
//        kTitles.add(new TimeTitleBean("日k",8));
//        kTitles.add(new TimeTitleBean("1周",10));
//        kTitles.add(new TimeTitleBean("1月",11));



    int width = (DisplayUtil.getScreenWidth(BaseApplication.getInstance()) - DisplayUtil.dip2px(BaseApplication.getInstance(), 15)) / 3;

    public void updateSelectSignalView() {
        layoutSelectSignal.removeAllViews();
        LinearLayout rowLinearLayout = null;
        for (int i = 0; i < sortSignalList.size(); i++) {
            if (i % 3 == 0) {
                rowLinearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams rowLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                rowLayoutParams.setMargins(DisplayUtil.dip2px(BaseApplication.getInstance(), 7.5f), 0, DisplayUtil.dip2px(BaseApplication.getInstance(), 7.5f), 0);
                rowLinearLayout.setLayoutParams(rowLayoutParams);
                layoutSelectSignal.addView(rowLinearLayout);
            }
            View itemView = LayoutInflater.from(this).inflate(
                    R.layout.item_signal_sort, null);
            LinearLayout.LayoutParams itemLayoutParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(itemLayoutParams);
            TextView sort_num = itemView.findViewById(R.id.sort_num);
            TextView tag = itemView.findViewById(R.id.tag);
            sort_num.setText((i + 1) + "");
            tag.setText(ApplicationUtils.getTagName(sortSignalList.get(i)));
            rowLinearLayout.addView(itemView);
        }
    }


}
