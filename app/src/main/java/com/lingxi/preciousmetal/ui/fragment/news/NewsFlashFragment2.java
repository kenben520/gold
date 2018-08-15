package com.lingxi.preciousmetal.ui.fragment.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.MessQuickRequestMo;
import com.lingxi.biz.domain.responseMo.MessQuickResultBean;
import com.lingxi.biz.service.NewsService;
import com.lingxi.common.base.BaseApplication;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.widget.groupListView.GroupItem;
import com.lingxi.preciousmetal.ui.widget.groupListView.GroupItemDecoration;
import com.lingxi.preciousmetal.ui.widget.groupListView.TopProjectionClickListener;
import com.lingxi.preciousmetal.ui.widget.groupListView.TopProjectionDecoration;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFlashFragment2 extends BaseFragment implements AdapterView.OnItemClickListener {

    private RefreshLayout refreshLayout;
    private int page;
    private int pageSize;
    private List<MessQuickResultBean.ItemsBean> listData;
    private QuickAdapter mAdapter;
    private String topGroupTime;

    public static NewsFlashFragment2 newInstance(String text){
        NewsFlashFragment2 fragmentCommon=new NewsFlashFragment2();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.news_flash_listView);
        topGroupTime = "";
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View timeView = layoutInflater.inflate(R.layout.flash_time_title_layout,null);
        GroupItemDecoration groupItemDecoration = new GroupItemDecoration(getActivity(), timeView, new GroupItemDecoration.DecorationCallback() {
            @Override
            public void setGroup(List<com.lingxi.preciousmetal.ui.widget.groupListView.GroupItem> groupList) {
               //设置分组，例如：
                GroupItem groupItem;
                for(int i=0;i<listData.size();i++){
                    MessQuickResultBean.ItemsBean bean = listData.get(i);
                    String groupTime = TimeUtils.millis2String(bean.getDisplay_time()*1000, TimeUtils.FORMSTMD);
                    if (TextUtils.isEmpty(topGroupTime)|| !groupTime.equals(topGroupTime)){
                        groupItem = new GroupItem(i);
                        topGroupTime = groupTime;
                        groupItem.setData("name",topGroupTime);
                        groupList.add(groupItem);
                    }
                }
            }

            @Override
            public void buildGroupView(View groupView, com.lingxi.preciousmetal.ui.widget.groupListView.GroupItem groupItem) {
                TextView textName =  groupView.findViewById(R.id.flash_time_textView);
                textName.setText(groupItem.getData("name").toString());
            }
        });
//        decoration.isProjectionChange(false);//顶部投影是否随列表滑动而改变
        recyclerView.addItemDecoration(groupItemDecoration);
//        recyclerView.addOnItemTouchListener(new TopProjectionClickListener(decoration, new TopProjectionClickListener.OnTopProjectionClickListener() {
//            @Override
//            public void onTopProjectionClick(View headerView, int position) {
//            Toast.makeText(mContext, "点击了Header，对应position为："+position, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onTopProjectionLongClick(View headerView, int position) {
//               Toast.makeText(mContext, "长按了Header，对应position为："+position, Toast.LENGTH_SHORT).show();
//            }
//        }));

        listData = new ArrayList<>();
        mAdapter = new QuickAdapter();
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mAdapter);
        page = 1;
        pageSize = 20;
        refreshLayout =  view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
            refreshLayout.getLayout().post(new Runnable() {
                @Override
                public void run() {
                    clearListData();
                    mAdapter.notifyDataSetChanged();
                    loadData();
                    refreshLayout.finishRefresh();
                    refreshLayout.setNoMoreData(false);
                }
            });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                });
            }
        });
        refreshLayout.autoRefresh();
    }

    private void clearListData(){
        page = 1;
        pageSize = 10;
        listData.clear();
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_flash2,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public class QuickAdapter extends BaseQuickAdapter<MessQuickResultBean.ItemsBean,BaseViewHolder> {

        public QuickAdapter() {
            super(R.layout.adapter_flash_news);
        }
        @Override
        protected void convert(final BaseViewHolder viewHolder, final MessQuickResultBean.ItemsBean bean) {
            TextView quick_new_time_textView = viewHolder.getView(R.id.quick_new_time_textView);
            quick_new_time_textView.setText(TimeUtils.millis2String(bean.getDisplay_time()*1000,new SimpleDateFormat("HH:mm:ss")));

            TextView quick_new_title_textView = viewHolder.getView(R.id.quick_new_title_textView);
            quick_new_title_textView.setText(bean.getContent_text());
            int red = bean.getRed();
            if (red==1){
                quick_new_title_textView.setTextColor(ContextCompat.getColor(mContext,R.color.chart_red));
            } else {
                quick_new_title_textView.setTextColor(ContextCompat.getColor(mContext,R.color.ff33));
            }

            LinearLayout flash_market_layout = viewHolder.getView(R.id.flash_market_layout);
            LinearLayout flash_value_layout = viewHolder.getView(R.id.flash_value_layout);
            int special = bean.getSpecial();
            if (special==1){
                flash_market_layout.setVisibility(View.VISIBLE);
                flash_value_layout.setVisibility(View.VISIBLE);

                TextView flash_bad_news_textView = viewHolder.getView(R.id.flash_bad_news_textView);
                TextView flash_good_news_textView = viewHolder.getView(R.id.flash_good_news_textView);
                TextView flash_front_value = viewHolder.getView(R.id.flash_front_value);
                TextView flash_expect_value = viewHolder.getView(R.id.flash_expect_value);
                TextView flash_actual_value = viewHolder.getView(R.id.flash_actual_value);

                flash_front_value.setText("前值 : "+bean.getFront());
                flash_expect_value.setText("预期 : "+bean.getExpect());
                flash_actual_value.setText("实际值 : "+bean.getActual());

                if (bean.getOrange_get()==1){
                    flash_good_news_textView.setTextColor(ContextCompat.getColor(mContext,R.color.FFCA731F));
                    flash_good_news_textView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_white_bg_yellow_border_circle));
                    flash_good_news_textView.setText("中性");
                    flash_good_news_textView.setVisibility(View.VISIBLE);
                    flash_bad_news_textView.setVisibility(View.GONE);
                } else {
                    flash_good_news_textView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_white_bg_red_border_circle));

                    String redStr = bean.getRed_get();
                    if (TextUtils.isEmpty(redStr)){
                        flash_good_news_textView.setVisibility(View.GONE);
                    } else {
                        flash_good_news_textView.setVisibility(View.VISIBLE);
                        flash_good_news_textView.setText("利多："+redStr);
                    }
                    String green = bean.getGreen_get();
                    if (TextUtils.isEmpty(green)){
                        flash_bad_news_textView.setVisibility(View.GONE);
                    } else {
                        flash_bad_news_textView.setVisibility(View.VISIBLE);
                        flash_bad_news_textView.setText("利空："+green);
                    }
                }
                if (BaseApplication.kIndexColor==0){
                    flash_good_news_textView.setTextColor(ViewUtil.getKUpColor(mContext));
                    flash_bad_news_textView.setTextColor(ViewUtil.getKLossColor(mContext));
                    flash_good_news_textView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_white_bg_green_border_circle));
                    flash_bad_news_textView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_white_bg_red_border_circle));
                } else {
                    flash_good_news_textView.setTextColor(ViewUtil.getKUpColor(mContext));
                    flash_bad_news_textView.setTextColor(ViewUtil.getKLossColor(mContext));
                    flash_bad_news_textView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_white_bg_green_border_circle));
                    flash_good_news_textView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_white_bg_red_border_circle));
                }
            } else {
                flash_market_layout.setVisibility(View.GONE);
                flash_value_layout.setVisibility(View.GONE);
            }
        }
    }

    private void loadData(){
        MessQuickRequestMo requestMo =  new MessQuickRequestMo(pageSize,page);
        NewsService.newsFlashList(requestMo,new BizResultListener<MessQuickResultBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit,MessQuickResultBean resultMo) {

            }

            @Override
            public void onSuccess(MessQuickResultBean resultMo) {
                List<MessQuickResultBean.ItemsBean> list = resultMo.getItems();
                if (resultMo.getCountpage()>=page){
                  //  flash_time_textView.setText(TimeUtils.millis2String(list.get(0).getDisplay_time()*1000,new SimpleDateFormat(TimeUtils.FORMSTMD)));
                    page++;
                    listData.addAll(list);
                    mAdapter.setNewData(listData);
//                    mAdapter.notifyDataSetChanged();
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                LogUtils.i("要闻请求失败");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
