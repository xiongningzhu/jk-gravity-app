package com.zng.jk_gravity_app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhy.autolayout.AutoLinearLayout;
import com.zng.jk_gravity_app.activity.HomeActivity;
import com.zng.jk_gravity_app.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    AutoLinearLayout linr_button_one,linr_button_three,line_zhanghao,line_face;
    View line_button_one,line_button_three;
    Button btn_define,btn_login;
    AutoLinearLayout line_login,line_close,line_enterface;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        linr_button_one=view.findViewById(R.id.linr_button_one);
        linr_button_three=view.findViewById(R.id.linr_button_three);
        line_zhanghao=view.findViewById(R.id.line_zhanghao);
        line_face=view.findViewById(R.id.line_face);
        line_button_one=view.findViewById(R.id.line_button_one);
        line_button_three=view.findViewById(R.id.line_button_three);
        btn_define=view.findViewById(R.id.btn_define);
        line_login=view.findViewById(R.id.line_login);
        line_close=view.findViewById(R.id.line_close);
        line_enterface=view.findViewById(R.id.line_enterface);
        btn_login=view.findViewById(R.id.btn_login);
        linr_button_one.setOnClickListener(this);
        linr_button_three.setOnClickListener(this);
        btn_define.setOnClickListener(this);
        line_close.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.linr_button_one:
                line_zhanghao.setVisibility(View.VISIBLE);
                line_face.setVisibility(View.GONE);
                line_button_one.setVisibility(View.VISIBLE);
                line_button_three.setVisibility(View.GONE);
                break;
            case R.id.linr_button_three:
                line_zhanghao.setVisibility(View.GONE);
                line_face.setVisibility(View.VISIBLE);
                line_button_one.setVisibility(View.GONE);
                line_button_three.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_define:
                line_login.setVisibility(View.GONE);
                line_enterface.setVisibility(View.VISIBLE);
                break;
            case R.id.line_close:
                line_login.setVisibility(View.VISIBLE);
                line_enterface.setVisibility(View.GONE);
                break;
            case R.id.btn_login:
                startActivity(HomeActivity.class,true);
                break;
        }
    }

}
