package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lingxi.biz.domain.responseMo.AnnouncementMo;
import com.lingxi.biz.domain.responseMo.HomeAllResultBean;
import com.lingxi.biz.domain.responseMo.NewRecomBean;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangwei on 2018/4/19.
 */

public class ArticleDetailActivity extends TranslucentStatusBarActivity {

    NewRecomBean.ItemsBean articlesBean;
    HomeAllResultBean.NoticeBean noticeBean;
    AnnouncementMo announcementMo;
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;

    public static void actionStart(Context context, NewRecomBean.ItemsBean articlesBean) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(IntentParam.ARTICLE, articlesBean);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, HomeAllResultBean.NoticeBean articlesBean) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(IntentParam.HOMENOTICE, articlesBean);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, AnnouncementMo articlesBean) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(IntentParam.ANNOUNCEMENT, articlesBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        initTopBar();
        articlesBean = (NewRecomBean.ItemsBean) getIntent().getSerializableExtra(IntentParam.ARTICLE);
        announcementMo = (AnnouncementMo) getIntent().getSerializableExtra(IntentParam.ANNOUNCEMENT);
        noticeBean = (HomeAllResultBean.NoticeBean) getIntent().getSerializableExtra(IntentParam.HOMENOTICE);
        initView();
    }

    private void initView() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
//        mTopbarView.setActionButton(R.drawable.ic_custom_service_white, null);
        if (articlesBean != null) {
            tvTitle.setText(articlesBean.getTitle());
            mTopbarView.setTitle("国际要闻");
            tvTime.setText(articlesBean.getTime());
            tvContent.setText(articlesBean.getTitle());
        } else if (announcementMo != null) {
            tvTitle.setText(announcementMo.title);
            mTopbarView.setTitle("公告详情");
            tvTime.setText(TimeUtils.millis2String(announcementMo.add_time * 1000));
            tvContent.setText(announcementMo.content);
        } else if (noticeBean != null) {
            tvTitle.setText(noticeBean.getTitle());
            mTopbarView.setTitle("公告详情");
            tvTime.setText(TimeUtils.millis2String(noticeBean.getAdd_time() * 1000));
            tvContent.setText(noticeBean.getContent());
        }
    }


    private void initTopBar() {
    }

}
