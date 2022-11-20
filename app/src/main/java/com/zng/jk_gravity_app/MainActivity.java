package com.zng.jk_gravity_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zng.jk_gravity_app.base.BaseActivity;
import com.zng.jk_gravity_app.util.StatusBarUtil;

public class MainActivity extends BaseActivity {
    ImageView image_login;
    TextView text_name;
    @Override
    public void initParms(Bundle parms) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        image_login=findViewById(R.id.image_login);
        image_login.setOnClickListener(this);
        text_name=findViewById(R.id.text_name);
        text_name.setText("未登录");
    }

    @Override
    public void doBusiness(Context mContext) {
        setThisStatusBarTextColor(true);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.image_login:
                startActivity(LoginActivity.class,true);
                break;
        }
    }
}