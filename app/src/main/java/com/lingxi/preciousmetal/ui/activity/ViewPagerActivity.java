package com.lingxi.preciousmetal.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.github.chrisbanes.photoview.PhotoView;
import com.jaeger.library.StatusBarUtil;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.base.BaseActivity;
import com.lingxi.preciousmetal.util.glideutils.GlideUtils;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerActivity extends BaseActivity {
    public List<String> picList = new ArrayList<>();
    public int currentPosition;

    public static void actionStart(Context context, ArrayList<String> picList, int currentPosition) {
        Intent intent = new Intent(context, ViewPagerActivity.class);
        intent.putStringArrayListExtra("picList", picList);
        intent.putExtra("currentPosition", currentPosition);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black));
        setContentView(R.layout.activity_view_pager);
        picList = getIntent().getStringArrayListExtra("picList");
        currentPosition = getIntent().getIntExtra("currentPosition", 0);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new PicPagerAdapter());
        viewPager.setCurrentItem(currentPosition);
    }

    class PicPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return picList.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            GlideUtils.loadImageView(container.getContext(), picList.get(position), photoView);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
