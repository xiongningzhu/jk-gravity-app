package com.zng.jk_gravity_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhy.autolayout.AutoRelativeLayout;
import com.zng.jk_gravity_app.R;
import com.zng.jk_gravity_app.adapter.FragTabAdapter;
import com.zng.jk_gravity_app.adapter.ShoopingDayBookAdapter;
import com.zng.jk_gravity_app.base.BaseActivity;
import com.zng.jk_gravity_app.been.ConditionsBeen;
import com.zng.jk_gravity_app.interfaces.OnRecyclerItemClickerListener;
import com.zng.jk_gravity_app.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * 领用取货
 * */
public class PickUpActivity extends BaseActivity implements OnRecyclerItemClickerListener {
    AutoRelativeLayout left_img_view;
    TextView title_bar_text;
    RecyclerView more_table;
    FragTabAdapter tabadapter;
    RecyclerView recy_answer;
    Button btn_affirm;
    @Override
    public void initParms(Bundle parms) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pickup;
    }

    @Override
    public void initView(View view) {
        left_img_view= findViewById(R.id.left_img_view);
        title_bar_text= findViewById(R.id.title_bar_text);
        title_bar_text.setText("商品展示");
        left_img_view.setOnClickListener(this);
        more_table= findViewById(R.id.more_table);
        recy_answer= findViewById(R.id.recy_answer);
        btn_affirm= findViewById(R.id.btn_affirm);
        initTable();
        initShooping();
        btn_affirm.setOnClickListener(this);
    }
    List<ConditionsBeen> lists = new ArrayList<>();
    public void initTable(){
        more_table.setLayoutManager(new LinearLayoutManager(this));
        tabadapter=new FragTabAdapter(this);
        tabadapter.setOnItemClick(this);
        more_table.setAdapter(tabadapter);
        for(int i=0;i<5;i++){
            ConditionsBeen been=new ConditionsBeen();
            been.setLabel("不锈钢材质紧固件");
            tabadapter.setBe_selected_item(0);
            lists.add(been);
        }
        tabadapter.setLists(lists);
        tabadapter.notifyDataSetChanged();
    }
    List<ConditionsBeen> listbeen = new ArrayList<>();
    ShoopingDayBookAdapter dayadapter;
    public void initShooping(){
        dayadapter=new ShoopingDayBookAdapter(PickUpActivity.this);
        recy_answer.setLayoutManager(new GridLayoutManager(PickUpActivity.this, 5));
        recy_answer.setFocusable(false);
        recy_answer.setNestedScrollingEnabled(false);//禁止滑动(false);
        dayadapter.setOnItemClick(this);
        recy_answer.setAdapter(dayadapter);
        for(int i=0;i<20;i++){
            ConditionsBeen been=new ConditionsBeen();
            been.setLabel("https://img0.baidu.com/it/u=2960224696,1699537082&fm=253&fmt=auto&app=138&f=JPEG?w=753&h=500");
            listbeen.add(been);
        }
        dayadapter.setLists(listbeen);
        dayadapter.notifyDataSetChanged();
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
            case R.id.btn_affirm:
                startActivity(ReceiveActivity.class,true);
                break;
        }
    }

    @Override
    public void onRecyclerItemClick(View view, int position) {
        if( view.getId() == R.id.line_bag) {//点击左侧导航栏
            if (tabadapter.getBe_selected_item() != position) {
                tabadapter.setBe_selected_item(position);
                tabadapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRecyclerItemLongClick(View view, int position) {

    }
}
