package com.zng.jk_gravity_app.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.zng.jk_gravity_app.R;
import com.zng.jk_gravity_app.util.AppUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;



public class BaseApplication extends Application {
    public static int lastClickSecond = 300;
    private static long lastClick = 0;
    private long TimeOut = 1000;
    public static BaseActivity baseActivity;
    //app名称
    public static String APP_NAME = "";
    //app版本name
    public static String APP_VERSION_NAME = "";
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //在Application创建时,读取Application
        APP_NAME = AppUtils.getAppName(this);
        APP_VERSION_NAME = AppUtils.getVersionName(this);
        initBugly();
    }

    public static Timer timer;
    public static boolean isstop = true;
    public static TimerTask timerTask;
    public static CheckCode checkCode;
    /**
     * 初始化腾讯bug管理平台
     */
    private void initBugly() {
        /* Bugly SDK初始化
         * 参数1：上下文对象
         * 参数2：APPID，平台注册时得到,注意替换成你的appId
         * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
         * 注意：如果您之前使用过Bugly SDK，请将以下这句注释掉。
         */
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(APP_NAME);
        strategy.setAppPackageName(APP_VERSION_NAME);
        strategy.setAppReportDelay(10000);                          //Bugly会在启动20s后联网同步数据

        /*  第三个参数为SDK调试模式开关，调试模式的行为特性如下：
            输出详细的Bugly SDK的Log；
            每一条Crash都会被立即上报；
            自定义日志将会在Logcat中输出。
            建议在测试阶段建议设置成true，发布时设置为false。*/

        CrashReport.initCrashReport(getApplicationContext(), "1ed7289131", true, strategy);

        //Bugly.init(getApplicationContext(), "1374455732", false);
    }
    //倒计时(秒)
    public static int second = 60;
    //倒计时类型
    public static int getCodeType = 0;

    //倒计时开始
    public static void startTime() {

        if (isstop) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {

                    if (second > 0) {
                        second--;
                        if (checkCode != null) {
                            checkCode.getcode(second);
                        }
                    } else {
                        stopTime(true);
                    }
                }
            };
            timer.schedule(timerTask, 0, 1000);
            isstop = false;
        }
    }

    /**
     * 重置时间
     *
     * @param suc true 倒计时完成 false 倒计时取消
     */
    public static void stopTime(boolean suc) {

        if (timer != null && timerTask != null) {
            isstop = true;
            timer.cancel();
            timerTask.cancel();
            timer = null;
            timerTask = null;
            second = 60;
            if (suc) {
                if (checkCode != null) {
                    checkCode.suc();
                }
            } else {
                if (checkCode != null) {
                    checkCode.cel();
                }
            }
        }
    }

    public static Map<String, Activity> destoryMap = new HashMap<>();

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */
    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName, activity);
    }

    /**
     * 销毁Activity
     */
    public static void destoryActivity(String activityName) {
        Activity activity = destoryMap.get(activityName);
        if (activity != null) {
            activity.finish();
        }
        destoryMap.remove(activity);
    }

    /**
     * 销毁Activity
     */
    public static void destoryActivity() {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {
            destoryMap.get(key).finish();
        }

        //清除销毁队列
        destoryMap.clear();
    }

    public interface CheckCode {
        /**
         * 倒计时进度 秒
         */
        void getcode(int s);

        /**
         * 倒计时完成
         */
        void suc();

        /**
         * 倒计时取消
         */
        void cel();
    }

    /**
     * [防止快速点击]
     *
     * @return
     */
    public static boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= BaseApplication.lastClickSecond) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 500;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
//    @Override
//    public Resources getResources() {
//        //禁止app字体大小跟随系统字体大小调节
//        Resources resources = super.getResources();
//        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
//            Configuration configuration = resources.getConfiguration();
//            configuration.fontScale = 1.0f;
//            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//        }else {
//            Configuration configuration = resources.getConfiguration();
//            configuration.fontScale =6.0f;
//            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
//        }
//        return resources;
//    }
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_ShOOP_TIME = 100;
    private static long lastShoopClickTime;

    public static boolean isShoopingFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastShoopClickTime) >= MIN_CLICK_ShOOP_TIME) {
            flag = true;
        }
        lastShoopClickTime = curClickTime;
        return flag;
    }

}
