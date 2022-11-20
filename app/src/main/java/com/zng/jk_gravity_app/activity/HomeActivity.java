package com.zng.jk_gravity_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.zhy.autolayout.AutoRelativeLayout;
import com.zng.jk_gravity_app.R;
import com.zng.jk_gravity_app.base.BaseActivity;
import com.zng.jk_gravity_app.util.StatusBarUtil;

public class HomeActivity extends BaseActivity {
    AutoRelativeLayout rela_revert,rela_pickup,rela_chazhao_wuliao,rela_buhuo;
    @Override
    public void initParms(Bundle parms) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView(View view) {
        rela_revert=view.findViewById(R.id.rela_revert);
        rela_revert.setOnClickListener(this);
        rela_pickup=view.findViewById(R.id.rela_pickup);
        rela_pickup.setOnClickListener(this);
        rela_chazhao_wuliao=view.findViewById(R.id.rela_chazhao_wuliao);
        rela_chazhao_wuliao.setOnClickListener(this);
        rela_buhuo=view.findViewById(R.id.rela_buhuo);
        rela_buhuo.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        setThisStatusBarTextColor(true);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.rela_revert:
                startActivity(RevertActvity.class,true);
                break;
            case R.id.rela_pickup:
                startActivity(PickUpActivity.class,true);
                break;
            case R.id.rela_chazhao_wuliao:
                startActivity(MatterActivity.class,true);
                break;
            case R.id.rela_buhuo:
                startActivity(ReplenishActivity.class,true);
                break;
        }
    }
}
