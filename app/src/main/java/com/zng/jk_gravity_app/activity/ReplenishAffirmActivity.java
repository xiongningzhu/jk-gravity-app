package com.zng.jk_gravity_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhy.autolayout.AutoRelativeLayout;
import com.zng.jk_gravity_app.R;
import com.zng.jk_gravity_app.adapter.MainListViewAdapter;
import com.zng.jk_gravity_app.adapter.ReplenishAdapter;
import com.zng.jk_gravity_app.adapter.ReplenishAffirmAdapter;
import com.zng.jk_gravity_app.base.BaseActivity;
import com.zng.jk_gravity_app.been.CategoryBeen;
import com.zng.jk_gravity_app.been.RevertBeen;
import com.zng.jk_gravity_app.interfaces.OnRecyclerItemClickerListener;
import com.zng.jk_gravity_app.util.SpaceItemDecoration;
import com.zng.jk_gravity_app.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * 补货确认
 * */
public class ReplenishAffirmActivity extends BaseActivity implements OnRecyclerItemClickerListener {
    AutoRelativeLayout left_img_view;
    TextView title_bar_text;
    RecyclerView recy_answer;
    ReplenishAffirmAdapter replenadapter;
    CheckBox remember_chex;
    @Override
    public void initParms(Bundle parms) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_replenish_affirm;
    }

    @Override
    public void initView(View view) {
        left_img_view= findViewById(R.id.left_img_view);
        title_bar_text= findViewById(R.id.title_bar_text);
        title_bar_text.setText("补货订单");
        left_img_view.setOnClickListener(this);
        recy_answer= findViewById(R.id.recy_answer);
        remember_chex=findViewById(R.id.remember_chex);
        init();
        remember_chex.setOnClickListener(this);
    }

    List<RevertBeen> lists = new ArrayList<>();
    public void init(){
        recy_answer.setLayoutManager(new LinearLayoutManager(this));
        recy_answer.addItemDecoration(new SpaceItemDecoration(30));
        replenadapter=new ReplenishAffirmAdapter(this);
        recy_answer.setAdapter(replenadapter);
        for(int i=0;i<10;i++){
            RevertBeen been=new RevertBeen();
            been.setName("物品"+i);
            lists.add(been);
        }
        replenadapter.setOnItemClick(this);
        replenadapter.setLists(lists);
        replenadapter.notifyDataSetChanged();

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
            case R.id.remember_chex:
                if(remember_chex.isChecked()){
                    for (int i=0;i<replenadapter.getLists().size();i++){
                        replenadapter.getLists().get(i).setIsclick(true);
                    }
                    replenadapter.notifyDataSetChanged();
                }
                break;
        }
    }
    @Override
    public void onRecyclerItemClick(View view, int position) {
       if(view.getId()==R.id.remember){
            for (int i=0;i<replenadapter.getLists().size();i++){
                if(!replenadapter.getLists().get(i).isIsclick()){
                    remember_chex.setChecked(false);
                    return;
                }else {
                    int posi=replenadapter.getLists().size()-1;
                    if(i==posi){
                        remember_chex.setChecked(true);
                    }
                }
            }
        }else  if(view.getId()==R.id.line_look){
           startActivity(DetailsMessageActivity.class,true);
       }
    }

    @Override
    public void onRecyclerItemLongClick(View view, int position) {

    }
}

