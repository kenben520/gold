package com.lingxi.preciousmetal.ui.activity.study;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lingxi.biz.util.ConstantUtil;
import com.lingxi.preciousmetal.base.TranslucentStatusBarActivity;
import com.lingxi.preciousmetal.R;
import com.lingxi.preciousmetal.ui.adapter.KnowledgeTypeAdapter;
import com.lingxi.preciousmetal.ui.widget.TopBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnowledgeCateActivity extends TranslucentStatusBarActivity implements View.OnClickListener {

    private void initTopBar() {
        TopBarView mTopBarView = findViewById(R.id.topbar_view);
        mTopBarView.setMode(TopBarView.MODE_BACK);
        mTopBarView.setTitle("投资课堂");
    }

    private ListView study_listView;
    private List<Map<String, Object>> listMapData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_cate);
        initTopBar();
        study_listView = findViewById(R.id.study_listView);
        listMapData = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("title","基础知识");
        map.put("searchType","base");
        map.put("content","新手必读 带你入门");
        map.put("image",R.drawable.lx_course_home_bg1);
        listMapData.add(map);
        map = new HashMap<>();
        map.put("title",ConstantUtil.fundamentalAnalysis);
        map.put("content","从入门到进阶");
        map.put("searchType","jibenmian");
        map.put("image",R.drawable.lx_course_home_bg2);
        listMapData.add(map);
        map = new HashMap<>();
        map.put("title","技术面分析");
        map.put("content","从进阶到精通");
        map.put("searchType","技术面");
        map.put("image",R.drawable.lx_course_home_bg3);
        listMapData.add(map);
        map = new HashMap<>();
        map.put("title",ConstantUtil.STUDYVIDEO);
        map.put("content","精品教材指导");
        map.put("searchType",ConstantUtil.STUDYVIDEO);
        map.put("image",R.drawable.lx_course_home_bg4);
        listMapData.add(map);
        map = new HashMap<>();
        map.put("title",ConstantUtil.TRADEWORD);
        map.put("content","权威全面的金融名词解析");
        map.put("searchType","words");
        map.put("image",R.drawable.lx_course_home_bg5);
        listMapData.add(map);
        map = new HashMap<>();
        map.put("title", ConstantUtil.TRADEQA);
        map.put("content","你想问的这里都有");
        map.put("searchType","question");
        map.put("image",R.drawable.lx_course_home_bg6);
        listMapData.add(map);

        study_listView.setAdapter(new KnowledgeTypeAdapter(mContext, listMapData, R.layout.adapter_knowledge_type));
        study_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = listMapData.get(position);
                String search = (String) item.get("searchType");
                if (position==0){
                      NewStartActivity.startMyActivity(mContext,1,search);
                } else if (position==2){
                      NewStartActivity.startMyActivity(mContext,2,search);
                } else {
                      Map<String,Object> map = listMapData.get(position);
                      String title = (String)map.get("title");
                      if (ConstantUtil.STUDYVIDEO.equals(title)){
                          StudyVideoActivityNew.actionStart(mContext);
                      } else {
                          StartQAActivity.startMyActivity(mContext,title,search);
                      }
                  }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int type = 0;
//        if (id == R.id.basics_knowledge_view) {
//            type = 1;
//        } else if (id == R.id.technical_nalysis_view) {
//            type = 2;
//        } else if (id == R.id.trading_qa_view) {
//            Intent intent = new Intent(this, StartQAActivity.class);
//            startActivity(intent);
//            return;
//        }
        Intent intent = new Intent(this, NewStartActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
