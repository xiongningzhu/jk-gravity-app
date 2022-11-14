package com.diandianfu.shooping.base;


/**
 * To Change The World
 * 2016-10-06 15:55
 * Created by Mr.Wang
 */

public interface IBasePresenter {
    void activityFinish();

    void toast(String msg);

    void showWaitDialog(String msg);

    void dismissWaitDialog();
}
