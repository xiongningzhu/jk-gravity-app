package com.zng.jk_gravity_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhy.autolayout.AutoRelativeLayout;
import com.zng.jk_gravity_app.R;
import com.zng.jk_gravity_app.adapter.ReceiveAdapter;
import com.zng.jk_gravity_app.base.BaseActivity;
import com.zng.jk_gravity_app.been.RevertBeen;
import com.zng.jk_gravity_app.interfaces.OnRecyclerItemClickerListener;
import com.zng.jk_gravity_app.util.SpaceItemDecoration;
import com.zng.jk_gravity_app.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 详细信息
 * */
public class DetailsMessageActivity extends BaseActivity implements OnRecyclerItemClickerListener {
    RecyclerView recy_answer;
    ReceiveAdapter adapter;
    AutoRelativeLayout left_img_view;
    TextView title_bar_text;
    @Override
    public void initParms(Bundle parms) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_details_message;
    }

    @Override
    public void initView(View view) {
        left_img_view= findViewById(R.id.left_img_view);
        title_bar_text= findViewById(R.id.title_bar_text);
        title_bar_text.setText("详细信息");
        left_img_view.setOnClickListener(this);
        recy_answer= findViewById(R.id.recy_answer);//列表
        init();
    }
    List<RevertBeen> lists = new ArrayList<>();
    public void init(){
        recy_answer.setLayoutManager(new LinearLayoutManager(this));
        recy_answer.addItemDecoration(new SpaceItemDecoration(30));
        adapter=new ReceiveAdapter(this);
        adapter.setOnItemClick(this);
        recy_answer.setAdapter(adapter);
        for(int i=0;i<10;i++){
            RevertBeen been=new RevertBeen();
            been.setName(""+i);
            lists.add(been);
        }
        adapter.setLists(lists);
        adapter.notifyDataSetChanged();

    }
    @Override
    public void doBusiness(Context mContext) {
        setThisStatusBarTextColor(true);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.left_img_view:
                activityFinish(true);
                break;
        }
    }

    @Override
    public void onRecyclerItemClick(View view, int position) {

    }

    @Override
    public void onRecyclerItemLongClick(View view, int position) {

    }
}
