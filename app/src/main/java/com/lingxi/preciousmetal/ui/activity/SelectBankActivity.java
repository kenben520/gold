package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.IntentParam;
import com.lingxi.preciousmetal.domain.BankBean;
import com.lingxi.preciousmetal.domain.Banks;
import com.lingxi.preciousmetal.ui.activity.kefu.KefuLoginActivity;
import com.lingxi.preciousmetal.ui.widget.TopBarView;
import com.lingxi.preciousmetal.util.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Created by zhangwei on 2018/4/23.
 */

public class SelectBankActivity extends TranslucentStatusBarActivity {
    @BindView(R.id.topbar_view)
    TopBarView mTopbarView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private QuickAdapter mAdapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SelectBankActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bank_name);
        ButterKnife.bind(this);
        initTopBar();
        initView();
    }

    private void initView() {
        Banks banks = AndroidUtils.getInstance().getBanks();
        mAdapter = new QuickAdapter();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        final List<BankBean> bankBeans = new ArrayList<>();
        bankBeans.addAll(banks.bins.values());
        mAdapter.replaceData(bankBeans);
    }

    public class QuickAdapter extends BaseQuickAdapter<BankBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_bank);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, final BankBean item) {
            viewHolder.setText(R.id.iv_bank_name, item.bank);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(IntentParam.BANK, item);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    private void initTopBar() {
        mTopbarView.setMode(TopBarView.MODE_BACK);
        mTopbarView.setTitle("选择开户银行");
        mTopbarView.setActionButton(R.drawable.ic_custom_service_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KefuLoginActivity.actionStart(SelectBankActivity.this);
            }
        });
    }
}
