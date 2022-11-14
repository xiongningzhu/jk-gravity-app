package com.zng.jk_gravity_app.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zng.jk_gravity_app.ali.OSUtils;
import com.zng.jk_gravity_app.ali.SystemBarTintManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarUtil {
    public static final int TYPE_MIUI = 0;
    public static final int TYPE_FLYME = 1;
    public static final int TYPE_M = 3;

    public StatusBarUtil() {
    }

    public static void setStatusBarColor(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.setStatusBarColor(colorId);
        } else if (Build.VERSION.SDK_INT >= 19) {
            setTranslucentStatus(activity);
            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(activity);
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setStatusBarTintColor(colorId);
        }

    }

    @TargetApi(19)
    public static void setTranslucentStatus(Activity activity) {
        Window window;
        if (Build.VERSION.SDK_INT >= 21) {
            window = activity.getWindow();
            View decorView = window.getDecorView();
            int option = 1280;
            decorView.setSystemUiVisibility(option);
            window.addFlags(-2147483648);
            window.setStatusBarColor(0);
        } else if (Build.VERSION.SDK_INT >= 19) {
            window = activity.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int flagTranslucentStatus = 67108864;
            attributes.flags |= flagTranslucentStatus;
            window.setAttributes(attributes);
        }

    }

    public static void setRootViewFitsSystemWindows(Activity activity, boolean fitSystemWindows) {
        if (Build.VERSION.SDK_INT >= 19) {
            ViewGroup winContent = (ViewGroup)activity.findViewById(16908290);
            if (winContent.getChildCount() > 0) {
                ViewGroup rootView = (ViewGroup)winContent.getChildAt(0);
                if (rootView != null) {
                    rootView.setFitsSystemWindows(fitSystemWindows);
                }
            }
        }

    }

    public static boolean setStatusBarDarkTheme(Activity activity, boolean dark) {
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= 23) {
                return setStatusBarFontIconDark(activity, 3, dark);
            } else if (OSUtils.isMiui()) {
                return setStatusBarFontIconDark(activity, 0, dark);
            } else {
                return OSUtils.isFlyme() ? setStatusBarFontIconDark(activity, 1, dark) : false;
            }
        } else {
            return false;
        }
    }

    public static boolean setStatusBarFontIconDark(Activity activity, int type, boolean dark) {
        switch(type) {
            case 0:
                return setMiuiUI(activity, dark);
            case 1:
                return setFlymeUI(activity, dark);
            case 2:
            case 3:
            default:
                return setCommonUI(activity, dark);
        }
    }

    public static boolean setCommonUI(Activity activity, boolean dark) {
        if (Build.VERSION.SDK_INT >= 23) {
            View decorView = activity.getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (dark) {
                    vis |= 8192;
                } else {
                    vis &= -8193;
                }

                if (decorView.getSystemUiVisibility() != vis) {
                    decorView.setSystemUiVisibility(vis);
                }

                return true;
            }
        }

        return false;
    }

    public static boolean setFlymeUI(Activity activity, boolean dark) {
        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt((Object)null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }

            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            return true;
        } catch (Exception var8) {
            var8.printStackTrace();
            return false;
        }
    }

    public static boolean setMiuiUI(Activity activity, boolean dark) {
        try {
            Window window = activity.getWindow();
            Class<?> clazz = activity.getWindow().getClass();
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getDeclaredMethod("setExtraFlags", Integer.TYPE, Integer.TYPE);
            extraFlagField.setAccessible(true);
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }

            return true;
        } catch (Exception var8) {
            var8.printStackTrace();
            return false;
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }
}
