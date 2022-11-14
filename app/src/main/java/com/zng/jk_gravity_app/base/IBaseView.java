package com.diandianfu.shooping.base;


/**
 * To Change The World
 * 2016-09-06 13:48
 * Created by Mr.Wang
 */
public interface IBaseView {

    void toast(String msg);

    void showWaitDialog(String msg);

    void dismissWaitDialog();

    void activityFinish();
}
