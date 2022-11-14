package com.diandianfu.shooping.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.diandianfu.shooping.been.FirstCloseBeen;
import com.diandianfu.shooping.been.TbConfig;
import com.diandianfu.shooping.consts.Const;
import com.google.gson.Gson;
import com.diandianfu.shooping.ali.SharedPreferencesUtils;
import com.diandianfu.shooping.ali.SystemUtils;
import com.diandianfu.shooping.httpinfo.OkHttpUtils;
import com.diandianfu.shooping.R;
import com.diandianfu.shooping.utils.AppUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tb.mob.TbManager;
import com.tb.mob.config.TbInitConfig;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BaseApplication extends Application {

    public MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    //    public static final MediaType JSON
//            = MediaType.parse("application/x-www-form-urlencoded");
    //手机品牌
    public String PHONEBRAND = "phoneBand";
    //手机品牌
    public String PHONEIMEI = "phoneimei";
    //手机型号
    public String PHONEMODEL = "phoneModel";
    //Android版本
    public String ANDROIDVER = "androidVer";
    //手机系统
    public String PHONESYS = "phoneSys";
    //app版本
    public String APPCODE = "appCode";
    //app版本号
    public String APPVER = "appVerName";
    //app渠道
    public String APPCHANNEL = "channel";
    //apptoken
    public String TOKEN = "token";

    //----------------------------------------
    //app名称
    public static String APP_NAME = "";
    //app版本name
    public static String APP_VERSION_NAME = "";
    //app版本code
    public static int APP_VERSION_CODE = 0;
    //app渠道
    public static String appchannel = "";
    //IMEI
    public static String getIMEI = "getIMEI";
    //手机品牌
    public static String phoneBand = "phoneBand";
    //手机型号
    public static String phoneModel = "phoneModel";
    //Android版本
    public static String androidVer = "androidVer";
    //手机系统
    public static String phoneSys = "android";
    //点击间隔
    public static int lastClickSecond = 300;
    private static long lastClick = 0;
    private long TimeOut = 1000;
    public static BaseActivity baseActivity;
    public static BaseNoBarActivity basenobarActivity;

    private Map<String, String> stringStringMap;
    private RequestBody body;
    private Request.Builder request;
    public static String LoginId;
    private Gson gson;
    private String jsonData;
    private SharedPreferencesUtils sharedPreferencesUtils;
    public static boolean dialogIsshow = false;
    //判断是否第一次进入
    private boolean firstLaunch;

    private static BaseApplication application;
    OkHttpUtils okHttpUtils = new OkHttpUtils();
    /*
     * 返回application
     * */
    public static BaseApplication getApplication() {
        return application;
    }
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
        application = this;
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
        firstLaunch = (boolean) sharedPreferencesUtils.getParam("firstLaunch", true);
        if(!firstLaunch){
            initQuanXian();
        }
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(FirstCloseBeen message) {
        if (message.message.equals("刷新")) {
            initQuanXian();
        }
    }
    public void  initQuanXian(){

        APP_NAME = AppUtils.getAppName(this);
        APP_VERSION_NAME = AppUtils.getVersionName(this);

        APP_VERSION_CODE = AppUtils.getVersionCode(this);
        appchannel = AppUtils.getAppMetaData(getBaseContext(), "YYYYYY");
        phoneBand = SystemUtils.getDeviceBrand();
        phoneModel = SystemUtils.getSystemModel();
        androidVer = SystemUtils.getSystemVersion();
        if (SystemUtils.getIMEI(this) != null) {
            getIMEI = SystemUtils.getIMEI(this);
        }
        //腾讯bugly
        initBugly();
        if(Const.openguanggao){
            initTb();
        }
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

        CrashReport.initCrashReport(getApplicationContext(), "8dcd9ff3c2", true, strategy);

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
    //SDK初始化
    private void initTb() {
        TbInitConfig config = new TbInitConfig.Builder()
                .appId(TbConfig.appId)//初始化id（平台上申请：应用列表的应用id）
                .initAgain(true)//是否兼容多个广告商
                .directDownload(true)//点击后是否直接下载
                .supportMultiProcess(false)
                .build();
        TbManager.init(this, config, new TbManager.IsInitListener() {
            @Override
            public void onFail(String s) {

            }

            @Override
            public void onSuccess() {

            }
        });
    }
}
