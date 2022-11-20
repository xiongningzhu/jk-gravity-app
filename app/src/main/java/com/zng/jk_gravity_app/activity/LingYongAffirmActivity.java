package com.zng.jk_gravity_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zng.jk_gravity_app.R;
import com.zng.jk_gravity_app.base.BaseActivity;
import com.zng.jk_gravity_app.util.StatusBarUtil;

public class LingYongAffirmActivity extends BaseActivity {
    AutoRelativeLayout left_img_view;
    TextView title_bar_text;
    AutoLinearLayout line_affirm;
    @Override
    public void initParms(Bundle parms) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_lingyong;
    }

    @Override
    public void initView(View view) {
        left_img_view= findViewById(R.id.left_img_view);
        title_bar_text= findViewById(R.id.title_bar_text);
        title_bar_text.setText("领用确认");
        left_img_view.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        setThisStatusBarTextColor(true);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.left_img_view:
                activityFinish(true);
                break;
        }
    }
}

