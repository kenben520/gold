package com.lingxi.preciousmetal.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.ui.adapter.AppGuideAdapter;
import com.lingxi.preciousmetal.util.utilCode.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lingxi.preciousmetal.common.CommonParam.FIRST_USE;
import static com.lingxi.preciousmetal.common.IntentParam.SKIP_FROM_GUIDE_PAGE;


public class AppGuideActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener, View.OnClickListener {

    @BindView(R.id.ScrollLayout)
    ViewPager ScrollLayout;
    @BindView(R.id.llayout)
    LinearLayout llayout;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.layout_end_guide)
    LinearLayout layoutEndGuide;
    @BindView(R.id.tv_goto_home)
    TextView tvGotoHome;
    private ViewPager viewPage;
    private ImageView[] mImageViews;
    private int mViewCount;
    private int mCurSel;

    private AppGuideAdapter adapter;
    private int[] listId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_app_guide);
        ButterKnife.bind(this);
        this.init();

    }

    private void init() {
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvGotoHome.setOnClickListener(this);
        tvGotoHome.setPaintFlags(Paint. UNDERLINE_TEXT_FLAG);
        tvGotoHome.getPaint().setAntiAlias(true);//这里要加抗锯齿
         layoutEndGuide.setVisibility(View.GONE);
        this.viewPage = (ViewPager) this.findViewById(R.id.ScrollLayout);
        this.viewPage.setOffscreenPageLimit(1);
        int num = 3;
        listId = new int[num];
        String packageName = "";
        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(
                    this.getPackageName(), 0);
            packageName = info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            packageName = "com.lingxi.preciousmetal";
        }

        for (int i = 0; i < num; i++) {
            int resID = getResources().getIdentifier("guide_page_" + (i + 1),
                    "layout", packageName);
            listId[i] = resID;
        }

        adapter = new AppGuideAdapter(listId, this);
        this.viewPage.setAdapter(adapter);
        LinearLayout linearLayout = (LinearLayout) this
                .findViewById(R.id.llayout);
        this.mViewCount = this.listId.length;
        this.mImageViews = new ImageView[this.mViewCount];
        for (int i = 0; i < this.mViewCount; i++) {
            this.mImageViews[i] = (ImageView) linearLayout.getChildAt(i);
            this.mImageViews[i].setEnabled(true);
            this.mImageViews[i].setOnClickListener(this);
            this.mImageViews[i].setTag(i);
        }

        int llayoutcount = linearLayout.getChildCount();
        for (int i = llayoutcount; i > mViewCount; i--) {
            linearLayout.getChildAt(i - 1).setVisibility(View.GONE);
        }
        this.mCurSel = 0;
        this.mImageViews[this.mCurSel].setEnabled(false);
        this.viewPage.setOnPageChangeListener(this);
    }

    private void setCurPoint(int index) {
        if ((index < 0) || (index > (this.mViewCount - 1))
                || (this.mCurSel == index)) {
            return;
        }
        if (index == (this.mViewCount - 1)) {
            for (int i = 0; i < this.mViewCount; i++) {
                this.mImageViews[i].setVisibility(View.GONE);
            }
            this.layoutEndGuide.setVisibility(View.VISIBLE);
        } else {
            for (int i = 0; i < this.mViewCount; i++) {
                this.mImageViews[i].setVisibility(View.VISIBLE);
            }
            this.layoutEndGuide.setVisibility(View.GONE);
        }
        this.mImageViews[this.mCurSel].setEnabled(true);
        this.mImageViews[index].setEnabled(false);
        this.mCurSel = index;
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        this.setCurPoint(arg0);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                SPUtils.getInstance().put(FIRST_USE, false);
                RegisterActivity.actionStart(AppGuideActivity.this,SKIP_FROM_GUIDE_PAGE);
                finish();
                break;
            case R.id.btn_login:
                SPUtils.getInstance().put(FIRST_USE, false);
                LoginActivity.actionStart(AppGuideActivity.this,SKIP_FROM_GUIDE_PAGE);
                finish();
                break;
            case R.id.tv_goto_home:
                SPUtils.getInstance().put(FIRST_USE, false);
                Intent intent = new Intent(AppGuideActivity.this, GoldMainActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                int pos = (Integer) (view.getTag());
                this.setCurPoint(pos);
                this.viewPage.setCurrentItem(pos);
                break;
        }
    }
}

