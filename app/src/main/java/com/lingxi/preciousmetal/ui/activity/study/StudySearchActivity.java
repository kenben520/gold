package com.lingxi.preciousmetal.ui.activity.study;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.StudySearchRequestMo;
import com.lingxi.biz.domain.responseMo.StudySearchResultBean;
import com.lingxi.biz.service.CommonService;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseListViewActivity;
import com.lingxi.preciousmetal.ui.activity.WebViewActivity;
import com.lingxi.preciousmetal.ui.widget.GridLayoutManagerWrapper;
import com.lingxi.preciousmetal.ui.widget.RecyclerViewDivider;
import com.lingxi.preciousmetal.util.utilCode.SharedPreferencesHelper;
import com.lingxi.preciousmetal.util.utilCode.StringUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudySearchActivity extends BaseListViewActivity {

    @BindView(R.id.history_recyclerView)
    RecyclerView historyRecyclerView;
    @BindView(R.id.search_history_layout)
    LinearLayout searchHistoryLayout;
    @BindView(R.id.search_result_recyclerView)

    RecyclerView searchResultRecyclerView;
    @BindView(R.id.search_result_refreshLayout)
    SmartRefreshLayout searchResultRefreshLayout;
    @BindView(R.id.search_result_layout)
    LinearLayout searchResultLayout;
    @BindView(R.id.search_close_btn)
    TextView searchCloseBtn;
    @BindView(R.id.search_edit_view)
    EditText searchEditView;
    @BindView(R.id.search_clear_btn)
    ImageView searchClearBtn;
    HistoryQuickAdapter historyQuickAdapter;
    @BindView(R.id.clear_all_btn)
    ImageView clearAllBtn;

    private SharedPreferencesHelper sharedPreferencesHelper;
    private List<String> historySearch = new ArrayList<>();
    private SearchResultQuickAdapter searchAdapter;
    private String searchType;

    public static void actionStart(Context context, String searchType) {
        Intent intent = new Intent(context, StudySearchActivity.class);
        intent.putExtra("searchType", searchType);
        context.startActivity(intent);
    }

    public   void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_search);
        ButterKnife.bind(this);
        searchType = getIntent().getStringExtra("searchType");
        showHistoryView();
        initSearchData();
        showSoftInputFromWindow(this,searchEditView);
        searchEditView.addTextChangedListener(textWatcher);
        searchEditView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) searchEditView.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(StudySearchActivity.this.getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    startSearch();
                    return true;
                }
                return false;
            }
        });
    }

    String tempSearch;

    TextWatcher textWatcher = new TextWatcher() {
        // 输入文本之前的状态
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        // 输入文本中的状态
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tempSearch = s.toString(); //temp = s   用于记录当前正在输入文本的个数
        }

        // 输入文本之后的状态
        @Override
        public void afterTextChanged(Editable s) {
            if (tempSearch.length() > 0) {
                searchClearBtn.setVisibility(View.VISIBLE);
            } else {
                searchClearBtn.setVisibility(View.GONE);
                historyQuickAdapter.replaceData(historySearch);
                if (historySearch==null || historySearch.size()<=0){
                    searchHistoryLayout.setVisibility(View.GONE);
                    searchResultLayout.setVisibility(View.GONE);
                } else {
                    searchResultLayout.setVisibility(View.GONE);
                    searchHistoryLayout.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    View notDataView;
    TextView content_empty_textView;

    private void initSearchData(){
        searchAdapter = new SearchResultQuickAdapter();
        searchResultRecyclerView.setAdapter(searchAdapter);
        notDataView = getLayoutInflater().inflate(R.layout.public_content_empty, (ViewGroup) searchResultRecyclerView.getParent(), false);
        content_empty_textView =  notDataView.findViewById(R.id.content_empty_textView);
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        searchResultRecyclerView.addItemDecoration(new RecyclerViewDivider(mContext));
        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                StudySearchResultBean.ArticlesBean item  = searchAdapter.getData().get(position);
                WebViewActivity.actionStart(mContext,item.getLink());
            }
        });
        searchResultRefreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        searchResultRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        searchResultRefreshLayout.finishRefresh();
                        searchResultRefreshLayout.finishLoadMore();
                    }
                });
            }
        });
        searchResultRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        // loadData();
                    }
                });
            }
        });
//        content_empty_textView.setText("抱歉没有找到的放大看");
//        searchAdapter.setEmptyView(notDataView);
    }

    private void showSearchView() {
        searchResultLayout.setVisibility(View.VISIBLE);
        searchHistoryLayout.setVisibility(View.GONE);
        searchAdapter.setNewData(null);
        searchAdapter.notifyDataSetChanged();
//        searchResultRefreshLayout.autoRefresh();
    }

    private void showHistoryView() {
        sharedPreferencesHelper = new SharedPreferencesHelper(mContext, "searchHistory");
        historySearch = sharedPreferencesHelper.getDataList("history");
        if (historySearch==null || historySearch.size()<=0){
            searchHistoryLayout.setVisibility(View.GONE);
            searchResultLayout.setVisibility(View.GONE);
        } else {
            searchResultLayout.setVisibility(View.GONE);
            searchHistoryLayout.setVisibility(View.VISIBLE);
        }
        historyQuickAdapter = new HistoryQuickAdapter();
        historyRecyclerView.setAdapter(historyQuickAdapter);
        historyQuickAdapter.addData(historySearch);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        historyRecyclerView.addItemDecoration(new RecyclerViewDivider(mContext));
//        //触发自动刷新
//        refreshLayout.autoRefresh();
        historyRecyclerView.setLayoutManager(new GridLayoutManagerWrapper(historyRecyclerView.getContext(),3));
        historyQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
             String searchName =   historySearch.get(position);
             searchEditView.setText(searchName);
             searchEditView.setSelection(searchName.length());
             loadData(searchName);
            }
        });
    }

    private void startSearch() {
        String searchStr = searchEditView.getText().toString();
        searchStr = searchStr.replaceAll(" ", "");
        if (TextUtils.isEmpty(searchStr)) {
            ToastUtils.showShort("请输入搜索内容");
            searchEditView.setText("");
            return;
        }
        if (TextUtils.isEmpty(searchType)) {
            ToastUtils.showShort("抱歉不知道搜索类型");
            return;
        }
        int size = historySearch.size();
        if (size > 8) {
            historySearch.remove(size - 1);
        }
        historySearch.add(0, searchStr);
        sharedPreferencesHelper.put("history", historySearch);
        loadData(searchStr);
    }

    public void loadData(final String searchStr) {
        showSearchView();
        StudySearchRequestMo requestMo = new StudySearchRequestMo(searchType, searchStr);
        CommonService.getStudySearchList(requestMo, new BizResultListener<StudySearchResultBean>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, StudySearchResultBean resultMo) {

            }

            @Override
            public void onSuccess(StudySearchResultBean resultMo) {
                if (resultMo != null && resultMo.getArticles() != null && resultMo.getArticles().size()>0) {
                    searchAdapter.addData(resultMo.getArticles());
                    searchAdapter.notifyDataSetChanged();
                    searchResultRefreshLayout.finishRefresh();
                    searchResultRefreshLayout.finishLoadMore();
                } else {
                    content_empty_textView.setText(searchStr);
                    searchAdapter.setEmptyView(notDataView);
                }
                searchResultRefreshLayout.finishLoadMoreWithNoMoreData();
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
                searchResultRefreshLayout.finishRefresh();
                searchResultRefreshLayout.finishLoadMore();
            }
        });
    }

    @OnClick({R.id.search_clear_btn, R.id.search_close_btn,R.id.clear_all_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_clear_btn:
                searchEditView.setText("");
                //searchClearBtn.setVisibility(View.GONE);
                break;
            case R.id.search_close_btn:
                finish();
                break;
            case R.id.clear_all_btn:
                searchResultLayout.setVisibility(View.GONE);
                searchHistoryLayout.setVisibility(View.GONE);
                historySearch.clear();
                historyQuickAdapter.replaceData(historySearch);
                sharedPreferencesHelper.put("history", historySearch);
                break;
        }
    }

    public class SearchResultQuickAdapter extends BaseQuickAdapter<StudySearchResultBean.ArticlesBean, BaseViewHolder> {

        public SearchResultQuickAdapter() {
            super(R.layout.adapter_search_result);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final StudySearchResultBean.ArticlesBean item) {
            TextView search_date_textView = viewHolder.getView(R.id.search_date_textView);
            search_date_textView.setText(TimeUtils.millis2String(item.getAdd_time() * 1000, TimeUtils.FORMAT_MD));

            TextView search_name_textView = viewHolder.getView(R.id.search_name_textView);
            search_name_textView.setText(item.getTitle());
        }
    }

    public class HistoryQuickAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public HistoryQuickAdapter() {
            super(R.layout.adapter_search_history);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final String item) {
            TextView search_history_textView = viewHolder.getView(R.id.search_history_textView);
            search_history_textView.setText(item);
        }
    }

}
