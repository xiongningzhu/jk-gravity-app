package com.zng.jk_gravity_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zng.jk_gravity_app.R;
import com.zng.jk_gravity_app.adapter.MainListViewAdapter;
import com.zng.jk_gravity_app.adapter.ReplenishAdapter;
import com.zng.jk_gravity_app.adapter.RevertAdapter;
import com.zng.jk_gravity_app.base.BaseActivity;
import com.zng.jk_gravity_app.been.CategoryBeen;
import com.zng.jk_gravity_app.been.RevertBeen;
import com.zng.jk_gravity_app.interfaces.OnRecyclerItemClickerListener;
import com.zng.jk_gravity_app.util.SpaceItemDecoration;
import com.zng.jk_gravity_app.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 补货
 * */
public class ReplenishActivity extends BaseActivity implements OnRecyclerItemClickerListener {
    AutoRelativeLayout left_img_view;
    TextView title_bar_text;
    RecyclerView more_tablerep,recy_answer;
    ReplenishAdapter replenadapter;
    CheckBox remember_chex;
    AutoLinearLayout line_affirm;
    @Override
    public void initParms(Bundle parms) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_replenish;
    }

    @Override
    public void initView(View view) {
        left_img_view= findViewById(R.id.left_img_view);
        title_bar_text= findViewById(R.id.title_bar_text);
        title_bar_text.setText("补货管理");
        left_img_view.setOnClickListener(this);
        more_tablerep= findViewById(R.id.more_tablerep);
        recy_answer= findViewById(R.id.recy_answer);
        remember_chex=findViewById(R.id.remember_chex);
        line_affirm=findViewById(R.id.line_affirm);
        line_affirm.setOnClickListener(this);
        initTable();
        init();
        remember_chex.setOnClickListener(this);
    }
    MainListViewAdapter adapters;
    List<CategoryBeen> listss = new ArrayList<>();
    public void initTable() {

//        more_tablerep.setFocusable(false);
        more_tablerep.setNestedScrollingEnabled(false);//禁止滑动(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ReplenishActivity.this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        more_tablerep.setLayoutManager(layoutManager);
        adapters=new MainListViewAdapter(ReplenishActivity.this);
        adapters.setOnItemClick(this);
        more_tablerep.setAdapter(adapters);
        for(int i=0;i<6;i++){
            CategoryBeen been=new CategoryBeen();
            int posion=i+1;
            been.setName(""+posion);
            if(i==0){
                been.setIsclick(true);
            }
            listss.add(been);
        }
        adapters.setLists(listss);
        adapters.notifyDataSetChanged();
    }

    List<RevertBeen> lists = new ArrayList<>();
    public void init(){
        recy_answer.setLayoutManager(new LinearLayoutManager(this));
        recy_answer.addItemDecoration(new SpaceItemDecoration(30));
        replenadapter=new ReplenishAdapter(this);
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
            case R.id.line_affirm:
                startActivity(ReplenishAffirmActivity.class,true);
                break;
        }
    }
    @Override
    public void onRecyclerItemClick(View view, int position) {
        if(view.getId()==R.id.line_item){
            for (int i=0;i<adapters.getLists().size();i++){
                adapters.getLists().get(i).setIsclick(false);
            }
            adapters.getLists().get(position).setIsclick(true);
            adapters.notifyDataSetChanged();
        }else if(view.getId()==R.id.remember){
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
        }
    }

    @Override
    public void onRecyclerItemLongClick(View view, int position) {

    }
}
