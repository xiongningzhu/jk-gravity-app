package com.zng.jk_gravity_app.base;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.zng.jk_gravity_app.util.StatusBarUtil;


/**
 * @author XR
 *
 * */

public abstract class  BaseFragment extends Fragment implements View.OnClickListener, IBaseView {

    private View mContextView = null;
    /**
     * [当前页面控件是否可点击]
     */
    public static Boolean fragmentClick = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContextView = inflater.inflate(bindLayout(), container, false);
        initView(mContextView);

//        setThisStatusBarColor(getResources().getColor(R.color.colorWhite));
//        if (!setThisStatusBarTextColor(false)) {
//            setThisStatusBarColor(0x55000000);
//        }
        doBusiness(getActivity());

        return mContextView;
    }
    protected void setThisStatusBarColor(int color) {
        StatusBarUtil.setStatusBarColor(getActivity(), color);
    }
    protected boolean setThisStatusBarTextColor(boolean isblack) {
        return StatusBarUtil.setStatusBarDarkTheme(getActivity(), isblack);
    }
    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(final View view);

    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);


    /** View点击 **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        widgetClick(v);
//        if(fragmentClick){
//            if (BaseApplication.fastClick())
//                widgetClick(v);
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void toast(String msg) {

    }

    @Override
    public void showWaitDialog(String msg) {

    }

    @Override
    public void dismissWaitDialog() {

    }

    @Override
    public void activityFinish() {

    }

}