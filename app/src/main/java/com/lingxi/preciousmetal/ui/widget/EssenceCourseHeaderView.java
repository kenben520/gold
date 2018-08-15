package com.lingxi.preciousmetal.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.tifezh.kchartlib.utils.ViewUtil;
import com.lingxi.biz.base.BizResultListener;
import com.lingxi.biz.domain.requestMo.GetLivePeopleCountRequestMo;
import com.lingxi.biz.domain.requestMo.LikeRequestMo;
import com.lingxi.biz.domain.responseMo.EssenceCourseMo;
import com.lingxi.biz.domain.responseMo.GetLivePeopleCountMo;
import com.lingxi.biz.domain.responseMo.LikeResultMo;
import com.lingxi.biz.service.AnalyseTradeService;
import com.lingxi.common.base.GlideApp;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.common.EventBusKeyDefine;
import com.lingxi.preciousmetal.domain.UserInfoBean;
import com.lingxi.preciousmetal.login.LoginHelper;
import com.lingxi.preciousmetal.ui.activity.TeacherDetailActivity;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;
import com.lingxi.preciousmetal.util.utilCode.ObjectUtils;
import com.lingxi.preciousmetal.util.utilCode.TimeUtils;
import com.lingxi.preciousmetal.util.utilCode.ToastUtils;

import org.greenrobot.eventbus.EventBus;

public class EssenceCourseHeaderView extends LinearLayout {
    ImageView ivUserIcon, iv_like,iv_like_gif;
    TextView title1;
    TextView title2;
    TextView title3;
    TextView title4;
    TextView tvLikeNum, tv_course_name, tv_person_count,tv_tag1;
    View layout_like, bg_like;
    EssenceCourseMo.TeacherBean teacher;
    private final static String TAG = EssenceCourseHeaderView.class.getName();
    LinearLayout layoutGotoTeacher, layout_live_info;
    //    private HomePresenter mHomePresenter;
    private Context mContext;
    Handler mHandler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(this, 2000);
            if(needLoadPeopleCount)
            {
                needLoadPeopleCount=false;
                loadPeopleCount();
            }
        }
    };
    Runnable likeAnimationEnd = new Runnable() {
        @Override
        public void run() {
//            layout_like.setClickable(true);
//            layout_like.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    layout_like.setClickable(false);
//                    like();
//                }
//            });
            iv_like_gif.clearAnimation();
            iv_like_gif.setVisibility(GONE);
            iv_like.setVisibility(VISIBLE);
            mGifPlay=false;
        }
    };

    public EssenceCourseHeaderView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EssenceCourseHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public EssenceCourseHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        initView();
        bindData();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.esscence_course_header_view, this);
        ivUserIcon = findViewById(R.id.iv_user_icon);
        layout_like = findViewById(R.id.layout_like);
        iv_like = findViewById(R.id.iv_like);
        iv_like_gif = findViewById(R.id.iv_like_gif);
        bg_like = findViewById(R.id.bg_like);
        title1 = findViewById(R.id.title1);
        title2 = findViewById(R.id.title2);
        title3 = findViewById(R.id.title3);
        title4 = findViewById(R.id.title4);
        tvLikeNum = findViewById(R.id.tv_like_num);
        tv_tag1 = findViewById(R.id.tv_tag1);
        tv_course_name = findViewById(R.id.tv_course_name);
        tv_person_count = findViewById(R.id.tv_person_count);
        layoutGotoTeacher = findViewById(R.id.layout_goto_teacher);
        layout_live_info = findViewById(R.id.layout_live_info);
        layout_like.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                layout_like.setClickable(false);
                like();
            }
        });
        layoutGotoTeacher.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                TeacherDetailActivity.actionStart(getContext(), "1");
                if (!ObjectUtils.isEmpty(teacher) && !ObjectUtils.isEmpty(teacher.getId())) {
                    TeacherDetailActivity.actionStart(getContext(), teacher.getId(),teacher.getTea_name(),teacher.getIs_online()==1);
                }
            }
        });
    }

    private boolean mLikeClicked = false;
    private boolean mGifPlay = false;

    private void like() {
        boolean isLogin2 = LoginHelper.getInstance().isLogin();
        if (!isLogin2) {
            ToastUtils.showShort("请您登录账号为老师点赞");
            return;
        }
//        ToastUtils.showShort("mGifPlay:"+mGifPlay);
        if(mGifPlay){
            return;
        }
        if (ObjectUtils.isEmpty(teacher)) return;
        int op_status;
        if (mLikeClicked) {
            op_status = 2;
            tvLikeNum.setText((teacher.getTea_total_click()) + "");
            tvLikeNum.setTextColor(getResources().getColor(R.color.gray10));
            iv_like.setImageResource(R.drawable.ic_like7);
            bg_like.setBackground(getResources().getDrawable(R.drawable.bg_circle_black2));
            mLikeClicked=false;
        } else {
            mGifPlay=true;
            op_status = 1;
            tvLikeNum.setText((teacher.getTea_total_click() + 1) + "");
            tvLikeNum.setTextColor(getResources().getColor(R.color.red4));
            iv_like.setImageResource(R.drawable.ic_like8);
            iv_like.setVisibility(GONE);
            iv_like_gif.setVisibility(VISIBLE);
//            iv_like_gif.getAnimation()
            bg_like.setBackground(getResources().getDrawable(R.drawable.bg_circle_r1));
            mLikeClicked=true;
            GlideApp.with(mContext).load(R.drawable.gif_like).into(iv_like_gif);
            mHandler.postDelayed(likeAnimationEnd, 750);
        }


        UserInfoBean userInfoBean = LoginHelper.getInstance().getLoginUserInfo();
        LikeRequestMo liveListRequestMo = new LikeRequestMo(teacher.getId(), userInfoBean.user_id, null, null, op_status);
        AnalyseTradeService.likeCourse(liveListRequestMo, new BizResultListener<LikeResultMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, LikeResultMo likeResultMo) {

            }

            @Override
            public void onSuccess(LikeResultMo likeResultMo) {
                if (likeResultMo.is_click == 1) {//点赞成功
                    EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<Integer>(EventBusKeyDefine.EVENTBUS_TEACHER_LIKE, 1));
//                    mLikeClicked = true;
                } else if (likeResultMo.is_click == 2) {//已经点赞过了
//                    ToastUtils.showShort("今日您已经进行过点赞");
//                    mLikeClicked = true;
                } else if (likeResultMo.is_click == 3) {//取消点赞成功
//                    mLikeClicked = false;
                } else if (likeResultMo.is_click == 4) {//该会员还没点赞无法取消
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
//                ToastUtils.showShort(bizMessage);
            }
        });
    }


    private void bindData() {
    }

    public void loadData() {
        mHandler.postDelayed(r, 2000);
//        mHomePresenter.loadBannerData();
    }

    public void refreshLivePeopleCount(int peopleCount) {
        tv_person_count.setText(peopleCount + "");
    }
    private boolean needLoadPeopleCount=false;
    public void livePeopleCountChange(){
        needLoadPeopleCount=true;
    }

    public void loadPeopleCount() {
        GetLivePeopleCountRequestMo liveListRequestMo = new GetLivePeopleCountRequestMo();
        AnalyseTradeService.getLivePeopleCount(liveListRequestMo, new BizResultListener<GetLivePeopleCountMo>() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void hitCache(boolean hit, GetLivePeopleCountMo showTradeListMo) {

            }

            @Override
            public void onSuccess(GetLivePeopleCountMo model) {
                if (!ObjectUtils.isEmpty(model)) {
                    refreshLivePeopleCount(model.online_cur_num);
                }
            }

            @Override
            public void onFail(int resultCode, int bizCode, String bizMessage) {
            }
        });
    }

    public void refreshData(EssenceCourseMo.TeacherBean teacher) {
        if (teacher.getIs_online() == 1) {
            tv_course_name.setText(teacher.getTitle());
            layout_live_info.setVisibility(VISIBLE);
            tv_tag1.setText("当前课程:");
            title2.setText("直播时间: " + TimeUtils.getDataStr(teacher.getStart_time(), "HH:mm") + "-" + TimeUtils.getDataStr(teacher.getEnd_time(), "HH:mm"));
        }else if (teacher.getIs_online() == 3) {
            tv_course_name.setText(teacher.getTitle());
            layout_live_info.setVisibility(VISIBLE);
            tv_tag1.setText("下一课程:");
            title2.setText("直播时间: " + TimeUtils.getDataStr(teacher.getStart_time(), "HH:mm") + "-" + TimeUtils.getDataStr(teacher.getEnd_time(), "HH:mm"));
        } else {
            layout_live_info.setVisibility(GONE);
        }
        EventBus.getDefault().post(new EventBusKeyDefine.EventBusMsgData<Integer>(EventBusKeyDefine.EVENTBUS_LIVE_STATUS, teacher.getIs_online()));
        this.teacher = teacher;
        title1.setText(teacher.getTea_name());
        tvLikeNum.setText(teacher.getTea_total_click() + "");
        float profit =  teacher.getTea_profit();
        title3.setText(String.format("%.2f",profit));
        title4.setText(teacher.getTea_win_num() + "%");
        tv_person_count.setText(teacher.getOnline_cur_num() + "");

//        GlideUtils.loadImageViewCrop(getContext(), teacher.get(), ivUserIcon);
        GlideUtils.loadRoundImage(getContext(), R.drawable.default_header1, 45, teacher.getTea_img_url(), ivUserIcon);

        if (LoginHelper.getInstance().isLogin() && teacher.getIs_online() == 1) {
            layout_like.setVisibility(VISIBLE);
            tvLikeNum.setVisibility(VISIBLE);
        } else {
            layout_like.setVisibility(INVISIBLE);
            tvLikeNum.setVisibility(INVISIBLE);
        }
        if (LoginHelper.getInstance().isLogin()){
            UserInfoBean userInfoBean=LoginHelper.getInstance().getLoginUserInfo();
            if(!ObjectUtils.isEmpty(teacher.getTeacher_click_hash())&&teacher.getTeacher_click_hash().contains(userInfoBean.user_id))
            {
                mLikeClicked=true;
                tvLikeNum.setTextColor(getResources().getColor(R.color.red4));
                iv_like.setImageResource(R.drawable.ic_like8);
            }
        }
    }

    private int getTitle3MeasureWidth() {
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        title3.measure(spec, spec);
        int measuredWidth = title3.getMeasuredWidth();
        return measuredWidth;
    }

    private int getTitle4MeasureWidth() {
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        title4.measure(spec, spec);
        int measuredWidth = title4.getMeasuredWidth();
        return measuredWidth;
    }

    public void release()
    {
        mHandler.removeCallbacks(r);
        mHandler.removeCallbacks(likeAnimationEnd);
    }
}
