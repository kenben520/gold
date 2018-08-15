package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.ShareUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.io.InputStream;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangwei on 2018/4/25.
 */

public class WebViewActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ProgressBar)
    android.widget.ProgressBar ProgressBar;
    @BindView(R.id.empty_img)
    ImageView emptyImg;
    @BindView(R.id.empty_tips)
    TextView emptyTips;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;
    String url, title;
    private boolean needShare;

    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(IntentParam.URL, url);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String url, String title, boolean needShare) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(IntentParam.URL, url);
        intent.putExtra(IntentParam.TITLE, title);
        intent.putExtra(IntentParam.NEED_SHARE, needShare);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra(IntentParam.URL);
        title = getIntent().getStringExtra(IntentParam.TITLE);
        needShare = getIntent().getBooleanExtra(IntentParam.NEED_SHARE, false);

        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initTopBar();
        initView();
        loadUrl();
    }

    private void loadUrl() {
        ProgressBar.setVisibility(View.VISIBLE);
        webView.loadUrl(url);
    }

    private void initView() {
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadUrl();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setTextZoom(100);
        WebChromeClient chromeClient = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (ConstantUtil.URLCALENDAR.equals(url)) {
                    mTopbarView.setTitle("财经日历");
                    return;
                }  else if (!TextUtils.isEmpty(title)) {
                    if (!webView.getUrl().contains(title)){
                        mTopbarView.setTitle(title);
                    }
                }
            }
        };
        // 设置setWebChromeClient对象
        webView.setWebChromeClient(chromeClient);

        // 如果是金十日历，则用专用client来更改样式
        if(url.equals(ConstantUtil.URLCALENDAR)){
            webView.setWebViewClient(new WebViewClientJin10Calendar());
        } else {
            webView.setWebViewClient(new WebViewClientNormal());
        }

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        if (needShare) {
            mTopbarView.setActionButton(R.drawable.ic_share, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareUtils.share(WebViewActivity.this, title, url);
                }
            });
        }
//        if (TextUtils.isEmpty(title)) {
//            mTopbarView.setTitle("常见问题");
//        } else {
//            mTopbarView.setTitle(title);
//        }
    }

    private class WebViewClientNormal extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            refreshLayout.finishRefresh();
            ProgressBar.setVisibility(View.GONE);
            view.evaluateJavascript("", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
            view.loadUrl(String.format(Locale.CHINA, "javascript:document.body.style.paddingTop='%fpx'; void 0", DensityUtil.px2dp(webView.getPaddingTop())));
        }
    }

    private class WebViewClientJin10Calendar extends WebViewClientNormal {
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if(Build.VERSION.SDK_INT<21){
                String fileName = url.substring(url.lastIndexOf("/")+1);
                if(fileName.equals("style-rili.css")){
                    try {
                        InputStream is = getApplicationContext().getAssets().open("css/style-rili.css");
                        return new WebResourceResponse("text/css", "UTF-8", is);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if(Build.VERSION.SDK_INT>=21){
                String fileName = request.getUrl().getLastPathSegment();
                if(fileName.equals("style-rili.css")){
                    try {
                        InputStream is = getApplicationContext().getAssets().open("css/style-rili.css");
                        return new WebResourceResponse("text/css", "UTF-8", is);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            return super.shouldInterceptRequest(view, request);
        }
    }
}
