package com.diandianfu.shooping.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.diandianfu.shooping.ali.SharedPreferencesUtils;
import com.diandianfu.shooping.ali.StatusBarUtil;
import com.diandianfu.shooping.ali.ToastUtils;
import com.diandianfu.shooping.interfaces.OutLoginOnLiscent;
import com.diandianfu.shooping.R;


public abstract class BaseNoBarActivity extends FragmentActivity implements
        View.OnClickListener, OutLoginOnLiscent, IBaseView {


    private SharedPreferencesUtils sharedPreferencesUtils;
    /**
     * 是否禁止旋转屏幕
     **/
    private boolean isAllowScreenRoate = true;
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 当前Activity控件是否可点击
     **/
    public static boolean activityClick = true;

    public Bundle savedInstanceState;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
        try {
            Bundle bundle = getIntent().getExtras();
            initParms(bundle);
            mContextView = LayoutInflater.from(this)
                    .inflate(bindLayout(), null);

            setContentView(mContextView);
            //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的padding
//            StatusBarUtil.setRootViewFitsSystemWindows(this, true);

            if (!isAllowScreenRoate) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            setThisStatusBarColor(getResources().getColor(R.color.colorWhite));

            initView(mContextView);
//            title字体颜色false = 白色
            if (!setThisStatusBarTextColor(false)) {
                setThisStatusBarColor(0x55000000);
            }
            doBusiness(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //埋点
//        MobclickAgent.onResume(this);
//        MobclickAgent.onPause(this);//统计应用时长   父类添加后子类不用重复添加

//        MobclickAgent.onPageStart("BaseActivity");
//        MobclickAgent.onPageEnd("BaseActivity"); //统计页面跳转   每个页面都需要添加

    @Override
    protected void onResume() {
        super.onResume();
//        LogUtils.logDebug("ClassName",getClass().getSimpleName());
       // BaseApplication.baseActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * [初始化Bundle参数]
     *
     * @param parms
     */
    public abstract void initParms(Bundle parms);

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

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if (activityClick) {
//            widgetClick(v);
            if (BaseApplication.fastClick()) {
                widgetClick(v);
            }
        }
    }


    public void showappToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(BaseNoBarActivity.this, msg);
            }
        });
    }

    //显示被顶dialog
    public void showOutLoginDialog(final String msg) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    /**
     * [页面跳转]
     *
     * @param clz
     * @param isanim 动画
     */
    public void startActivity(Class<?> clz, boolean isanim) {
        startActivity(clz, null, isanim);
    }

    /**
     * [页面退出]
     *
     * @param isanim
     */
    public void activityFinish(boolean isanim) {
        if (isanim) {
            this.finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
        } else {
            this.finish();
        }
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle, boolean isanim) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (isanim) {
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        } else {
            startActivity(intent);
        }
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode, boolean isanim) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        if (isanim) {
            startActivityForResult(intent, requestCode);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls,
                                       int requestCode, boolean isanim) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (isanim) {
            startActivityForResult(intent, requestCode);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

//    /**
//     * [防止快速点击]
//     *
//     * @return
//     */
//
//    public long lastClick = 0;
//
//    protected boolean fastClick() {
//        if (System.currentTimeMillis() - lastClick <= BaseApplication.lastClickSecond) {
//            return false;
//        }
//        lastClick = System.currentTimeMillis();
//        return true;
//    }

    protected void setThisStatusBarColor(int color) {
        StatusBarUtil.setStatusBarColor(this, color);
    }

    protected boolean setThisStatusBarTextColor(boolean isblack) {
        return StatusBarUtil.setStatusBarDarkTheme(this, isblack);
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {

        Intent mIntent = new Intent(Intent.ACTION_DIAL);//Intent.ACTION_CALL
        Uri uri = Uri.parse("tel:" + phoneNum);
        mIntent.setData(uri);
        startActivity(mIntent);

    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
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
