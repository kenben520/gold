package com.lingxi.preciousmetal.ui.fragment.news;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.MessFinanceRequestMo;
import com.lingxi.biz.domain.responseMo.CalendarResultBean;
import com.lingxi.biz.service.UserService;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.domain.CalendarItem;
import com.lingxi.preciousmetal.domain.DataServer;
import com.lingxi.preciousmetal.domain.FilterSection;
import com.lingxi.preciousmetal.ui.adapter.NewsCalendarAdapter;
import com.lingxi.preciousmetal.ui.adapter.SectionAdapter;
import com.lingxi.preciousmetal.util.utilCode.LogUtils;
import com.lingxi.preciousmetal.util.utilCode.ScreenUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NewsCalendarFragment extends Fragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    private CalendarView calendarView;
    private TextView chooseDate;
    private int[] cDate = CalendarUtil.getCurrentDate();
    private CustomPopWindow mListPopWindow,filterPopWindow;
    private ImageView news_calendar_view;
    private ImageView calendar_right,calendar_left;
    private RadioButton calendar_text1,calendar_text2,calendar_text3;
    private List<CalendarResultBean.CalendarBean> listData;
    private NewsCalendarAdapter adapter;
    private String country,calendarType;
    private int status,stars,pageSize,page;
    private long curDate;

    private void loadData(){
        page++;
        MessFinanceRequestMo investmentRequestMo = new MessFinanceRequestMo(country,calendarType,status,stars,curDate,pageSize,page);
        UserService.messFinceApp(investmentRequestMo,new BizResultListener<CalendarResultBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, CalendarResultBean loginResultMo) {

            }

            @Override
            public void onSuccess(CalendarResultBean loginResultMo) {
                LogUtils.i("日历请求成功");
                if (loginResultMo!=null && loginResultMo.finance!=null && loginResultMo.finance.size()>0){
                    listData.addAll(loginResultMo.finance);
                    adapter.notifyDataSetChanged();
                    refreshLayout.finishLoadMore();
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                refreshLayout.finishLoadMore();
                LogUtils.i("日历请求失败");
            }
        });
    }

    RefreshLayout refreshLayout;
    SectionAdapter sectionAdapter;
    List<FilterSection> filterData;

    private void  initData(){
        listData = new ArrayList<>();
        resetParameter();
        initCalendar(TimeUtils.getNowYMD());
    }

    @Override
    public void onViewCreated(@NonNull final View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        calendar_text1 = root.findViewById(R.id.calendar_text1);
        calendar_text2 = root.findViewById(R.id.calendar_text2);
        calendar_text3 = root.findViewById(R.id.calendar_text3);
        RadioGroup group = root.findViewById(R.id.calendar_group);
        group.setOnCheckedChangeListener(this);
        initData();
//        loadData();
        ListView news_mainListView = root.findViewById(R.id.news_main_listview);
        adapter = new NewsCalendarAdapter(getActivity(), listData, R.layout.adapter_news_calendar);
        news_mainListView.setAdapter(adapter);
        news_mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CalendarResultBean.CalendarBean bean = listData.get(position);
//                WebViewActivity.actionStart(getActivity(),bean.uri);
            }
        });
        refreshLayout =  root.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        listData.clear();
                        page = 0;
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
        //触发自动刷新
        refreshLayout.autoRefresh();

        ImageView news_filter_view = root.findViewById(R.id.news_filter_view);
        news_filter_view.setOnClickListener(this);

        news_calendar_view = root.findViewById(R.id.news_calendar_view);
        news_calendar_view.setOnClickListener(this);
    }

    private void initCalendar(Date date1){
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date1);
         curDate = date1.getTime()/1000;

        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String strTime = TimeUtils.getChineseWeek(date1)+"\n"+month+"月"+day+"号";
        calendar_text1.setText(strTime);
        calendar_text1.setTag(curDate);
        calendar_text1.setChecked(true);

        calendar.add(Calendar.DATE, 1);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        strTime = TimeUtils.getChineseWeek(calendar.getTime())+"\n"+month+"月"+day+"号";
        calendar_text2.setText(strTime);
        calendar_text2.setTag(calendar.getTimeInMillis()/1000);

        calendar.add(Calendar.DATE, 1);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        strTime = TimeUtils.getChineseWeek(calendar.getTime())+"\n"+month+"月"+day+"号";
        calendar_text3.setText(strTime);
        calendar_text3.setTag(calendar.getTimeInMillis()/1000);
    }

    public static NewsCalendarFragment newInstance(String text) {
        NewsCalendarFragment fragmentCommon = new NewsCalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_calendar, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int id) {
           int checkedId = group.getCheckedRadioButtonId();
          if (checkedId==R.id.calendar_text1) {
              curDate = (long)calendar_text1.getTag();
          } else  if (checkedId==R.id.calendar_text2){
              curDate =  (long)calendar_text2.getTag();
          } else if (checkedId==R.id.calendar_text3){
              curDate = (long)calendar_text3.getTag();
          }
          page = 0;
          listData.clear();
          adapter.notifyDataSetChanged();
          loadData();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.news_filter_view){
            initWindowFilter();
        } else if(id==R.id.news_calendar_view){
            initWindowCalendar();
        } else  if (id==R.id.calendar_right){
            calendarView.nextMonth();
        } else if (id==R.id.calendar_left){
            calendarView.lastMonth();
        } else if (id==R.id.filter_sure){
            resetParameter();
            StringBuffer countrySbf = new StringBuffer();
            for (FilterSection bean:  filterData){
                if (bean.isHeader){
                    continue;
                }
                CalendarItem item = bean.t;
                boolean isCheck = item.isUserCheck();
                if (item.getType()==1){
                    if (isCheck){
                        countrySbf.append(item.getTypeId()+",");
                    }
                } else if (item.getType()==2){
                    if (isCheck){
                        status = Integer.parseInt(item.getTypeId());
                    }
                } else if (item.getType()==3){
                    if (isCheck){
                        stars = Integer.parseInt(item.getTypeId());
                    }
                } else if (item.getType()==4){
                    if (isCheck){
                        calendarType = item.getTypeId();
                    }
                }
            }
            country = countrySbf.toString();
            filterPopWindow.dissmiss();
            page = 0;
            listData.clear();
            adapter.notifyDataSetChanged();
            loadData();
        } else if (id==R.id.filter_reset){
            for (FilterSection bean:  filterData) {
                if (bean.isHeader){
                    continue;
                }
                CalendarItem item = bean.t;
                item.setUserCheck(false);
            }
            sectionAdapter.notifyDataSetChanged();
        }
    }

    private void resetParameter(){
        country = "-1";
        calendarType = "-1";
        status = -1;
        stars = -1;
        pageSize = 6;
        page = 0;
    }

    private void initWindowCalendar() {
        if (mListPopWindow!=null && mListPopWindow.getPopupWindow().isShowing()){
            return;
        }
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.window_news_calender, null);
        int k = getView().getHeight();
        if (k<500){
            k = 800;
        }
        //创建并显示popWindow
        mListPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView).size(ScreenUtils.getScreenWidth(),k)
                .create().showAsDropDown(news_calendar_view,0,-news_calendar_view.getHeight());
        calendar_right = contentView.findViewById(R.id.calendar_right);
        calendar_right.setOnClickListener(NewsCalendarFragment.this);
        calendar_left= contentView.findViewById(R.id.calendar_left);
        calendar_left.setOnClickListener(NewsCalendarFragment.this);
        final TextView title = contentView.findViewById(R.id.title);
        //当前选中的日期
        chooseDate = contentView.findViewById(R.id.choose_date);
        calendarView = contentView.findViewById(R.id.calendar);

        calendarView
//                .setSpecifyMap(map)
                .setStartEndDate("2016.1", "2028.12")
                .setDisableStartEndDate("2016.10.10", "2028.10.10")
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .init();
        title.setText(cDate[0] + "年" + cDate[1] + "月");
        chooseDate.setText("当前选中的日期：" + cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");

        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });

        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                String strDate = date.getSolar()[0] + "-" + date.getSolar()[1] + "-" + date.getSolar()[2];
                Date date1 = TimeUtils.string2Date(strDate,new SimpleDateFormat("yyyy-MM-dd"));
                initCalendar(date1);
                listData.clear();
                page = 0;
                adapter.notifyDataSetChanged();
                loadData();
                mListPopWindow.dissmiss();
            }
        });
    }

    private void initWindowFilter(){
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.window_calender_filter, null);
        //创建并显示popWindow
        int k = getView().getHeight();
        if (k<500){
            k = 800;
        }
        filterPopWindow =  new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView).size(ScreenUtils.getScreenWidth(),k)
                .create().showAsDropDown(news_calendar_view,0,-news_calendar_view.getHeight());

        RecyclerView mRecyclerView; mRecyclerView = contentView.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),5));
        contentView.findViewById(R.id.filter_reset).setOnClickListener(NewsCalendarFragment.this);
        contentView.findViewById(R.id.filter_sure).setOnClickListener(NewsCalendarFragment.this);
        if (filterData==null){
            filterData = DataServer.getSampleData();
        }
        sectionAdapter = new SectionAdapter(R.layout.item_section_content, R.layout.def_section_head, filterData);
        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FilterSection section = filterData.get(position);
                if (section.isHeader){
                    return;
                }
                CalendarItem bean = section.t;
                if (bean.getType()==1){
                    //国家多选
                    if (bean.isUserCheck()){
                        bean.setUserCheck(false);
                    }else{
                        bean.setUserCheck(true);
                    }
                } else {
                    //其它都是单选
                    for (FilterSection item:  filterData) {
                        if (item.isHeader) {
                            continue;
                        }
                        CalendarItem cItem = item.t;
                        if (cItem.getType() == bean.getType()) {
                            cItem.setUserCheck(false);
                        }
                    }
                    bean.setUserCheck(true);
                }
                adapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(24, 24, 0, 4);//设置itemView中内容相对边框左，上，右，下距离
            }
        });

        sectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mRecyclerView.setAdapter(sectionAdapter);
    }
}
