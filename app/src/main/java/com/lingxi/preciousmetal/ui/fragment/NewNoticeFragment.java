package com.lingxi.preciousmetal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.KMarketRequestMo;
import com.lingxi.biz.domain.requestMo.NewNoticeRequestMo;
import com.lingxi.biz.domain.responseMo.KMarketResultBean;
import com.lingxi.biz.domain.responseMo.NewNoticeBean;
import com.lingxi.biz.service.CommonService;
import com.lingxi.biz.service.KMarketService;
import com.lingxi.common.base.BaseFragment;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.MyApplication;
import com.lingxi.preciousmetal.chart.activity.ChartActivity;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.adapter.KMarketAdapter;
import com.lingxi.preciousmetal.ui.adapter.NoticeAdapter;
import com.lingxi.preciousmetal.ui.adapter.base.CommonAdapter;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.lingxi.preciousmetal.util.utilCode.SharedPreferencesHelper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*通知公告
 */
public class NewNoticeFragment extends BaseFragment {

    public static NewNoticeFragment newInstance(int text){
        NewNoticeFragment fragmentCommon=new NewNoticeFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("type",text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    private RefreshLayout refreshLayout;
    private CommonAdapter mAdapter;
    private List<NewNoticeBean.ItemsBean> noticeList;
    private ListView noticeListview;
    private int page = 1;
    private int type =1;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noticeListview = view.findViewById(R.id.notice_listView);
        noticeListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewNoticeBean.ItemsBean item =  noticeList.get(position);
                WebViewActivity.actionStart(mContext, item.getLink());
            }
        });
        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "newNotice");
        type =  getArguments().getInt("type",1);
        noticeList = new ArrayList<>();
        mAdapter = new NoticeAdapter(getActivity(), noticeList, R.layout.adapter_new_notice);
        noticeListview.setAdapter(mAdapter);

        refreshLayout =  view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        page=1;
                        noticeList.clear();
                        mAdapter.notifyDataSetChanged();
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                        initData();
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
                        initData();
                    }
                });
            }
        });
        //触发自动刷新
        refreshLayout.autoRefresh();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (isFirstLoad) {

            }
        }
    }

    private boolean isFirstLoad;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_notice,container,false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initData() {
        NewNoticeRequestMo requestMo =  new NewNoticeRequestMo(20,page,type);
        CommonService.getNewNoticeList(requestMo,new BizResultListener<NewNoticeBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, NewNoticeBean resultBean) {

            }

            @Override
            public void onSuccess(NewNoticeBean resultBean) {
                if (resultBean!=null && resultBean.getItems()!=null && resultBean.getItems().size()>0){
                    page ++;
                    noticeList.addAll(resultBean.getItems());
                    if(noticeList.size()>0){
                       NewNoticeBean.ItemsBean bean = noticeList.get(0);
                        //更新最新的id
                        if (type==1){
                            sharedPreferencesHelper.put("type1Id",bean.getNotice_id());
                        } else if (type==2){
                            sharedPreferencesHelper.put("type2Id",bean.getNotice_id());
                        } else if (type==3){
                            sharedPreferencesHelper.put("type3Id",bean.getNotice_id());
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
            }
        });
    }

}
