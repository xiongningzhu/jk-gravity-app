package com.zng.jk_gravity_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zng.jk_gravity_app.R;
import com.zng.jk_gravity_app.adapter.RevertAdapter;
import com.zng.jk_gravity_app.base.BaseActivity;
import com.zng.jk_gravity_app.been.RevertBeen;
import com.zng.jk_gravity_app.interfaces.OnRecyclerItemClickerListener;
import com.zng.jk_gravity_app.util.CustomDatePicker;
import com.zng.jk_gravity_app.util.DateFormatUtils;
import com.zng.jk_gravity_app.util.SpaceItemDecoration;
import com.zng.jk_gravity_app.util.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 归还
 * */
public class RevertActvity extends BaseActivity implements OnRecyclerItemClickerListener {
    RecyclerView recy_answer;
    RevertAdapter adapter;
    AutoRelativeLayout left_img_view;
    TextView title_bar_text;
    AutoLinearLayout ll_date,ll_time;
    private CustomDatePicker mDatePicker, mTimerPicker;
    String time="",startdate="",enddate="",contentstartdate="",contentenddate="";
    TextView mTvSelectedDate,mTvSelectedTime;
    SimpleDateFormat dateFormater,dateFormaterTwo;
    Calendar cal,calTwo;
    @Override
    public void initParms(Bundle parms) {
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_revert;
    }

    @Override
    public void initView(View view) {
        left_img_view= findViewById(R.id.left_img_view);
        title_bar_text= findViewById(R.id.title_bar_text);
        title_bar_text.setText("待归还订单");
        left_img_view.setOnClickListener(this);
        recy_answer= findViewById(R.id.recy_answer);//列表
        ll_date=findViewById(R.id.ll_date);
        ll_time=findViewById(R.id.ll_time);

        mTvSelectedDate=findViewById(R.id.mTvSelectedDate);//开始时间
        mTvSelectedTime=findViewById(R.id.mTvSelectedTime);//结束时间
        ll_date.setOnClickListener(this);
        ll_time.setOnClickListener(this);
        init();
        getDay();//初始化时间控件
        getDayTwo();
        initDatePicker();
        initDatePickerTime();
    }
    List<RevertBeen> lists = new ArrayList<>();
    public void init(){
        recy_answer.setLayoutManager(new LinearLayoutManager(this));
        recy_answer.addItemDecoration(new SpaceItemDecoration(30));
        adapter=new RevertAdapter(this);
        adapter.setOnItemClick(this);
        recy_answer.setAdapter(adapter);
       for(int i=0;i<10;i++){
           RevertBeen been=new RevertBeen();
           been.setName("物品"+i);
           lists.add(been);
       }
        adapter.setLists(lists);
        adapter.notifyDataSetChanged();

    }
    @Override
    public void doBusiness(Context mContext) {
        setThisStatusBarTextColor(true);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.left_img_view:
                activityFinish(true);
                break;
            case R.id.ll_date:
                mDatePicker.show(mTvSelectedDate.getText().toString());
                time="0";
                break;
            case R.id.ll_time:
                mTimerPicker.show(mTvSelectedTime.getText().toString());
                time="0";
                break;
        }
    }

    @Override
    public void onRecyclerItemClick(View view, int position) {
        if(view.getId()==R.id.line_guihuan){//归还确认
            startActivity(GuiHuanAffirmActivity.class,true);
        }
    }

    @Override
    public void onRecyclerItemLongClick(View view, int position) {

    }
    //选择滚动年月日
    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2015-01-01 00:00:00", false);
        long endTimestamp = System.currentTimeMillis();
        mTvSelectedDate.setText(dateFormater.format(cal.getTime()) + "");
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        long endTimestamps = DateFormatUtils.str2Long(dateFormater.format(cal.getTime()), false);
        //mTvSelectedDate.setText(DateFormatUtils.long2Str(endTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                contentstartdate=DateFormatUtils.long2Str(timestamp, true);//开始时间
                startdate=DateFormatUtils.long2Str(timestamp, false);//开始时间
                if(getTimeHttp()){
                    mTvSelectedDate.setText(DateFormatUtils.long2Str(timestamp, false));
                   // presenter.getLearnToatal(startdate,enddate,type);
                }
            }
        }, beginTimestamp, endTimestamps);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }
    //选择滚动年月日
    private void initDatePickerTime() {
        long beginTimestamp = DateFormatUtils.str2Long("2015-01-01 00:00:00", false);
        long endTimestamp = System.currentTimeMillis();
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        mTvSelectedTime.setText(dateFormater.format(cal.getTime()));
        long endTimestamps = DateFormatUtils.str2Long(dateFormater.format(cal.getTime()), false);
        //mTvSelectedTime.setText(DateFormatUtils.long2Str(endTimestamp, false));

        // 通过时间戳初始化日期，毫秒级别
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                startdate=mTvSelectedDate.getText().toString();
                enddate=DateFormatUtils.long2Str(timestamp, false);
                if(getTimeHttp()){
                    contentenddate=DateFormatUtils.long2Str(timestamp, true);//结束时间
                    mTvSelectedTime.setText(DateFormatUtils.long2Str(timestamp, false));
                   // presenter.getLearnToatal(startdate,enddate,type);
                }
            }
        }, beginTimestamp, endTimestamps);
        // 不允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(false);
        // 不显示时和分
        mTimerPicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mTimerPicker.setScrollLoop(false);
        // 不允许滚动动画
        mTimerPicker.setCanShowAnim(false);
    }
    protected void onDestroy() {
        super.onDestroy();
        mDatePicker.onDestroy();
        mTimerPicker.onDestroy();
    }
    /**
     * 判断2个时间大小
     * yyyy-MM-dd HH:mm 格式（自己可以修改成想要的时间格式）
     * @param startTime
     * @param endTime
     * @return
     */
    public static int timeCompare(String startTime, String endTime){
        int i=0;
        //注意：传过来的时间格式必须要和这里填入的时间格式相同
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime()<date1.getTime()){
                //结束时间小于开始时间
                i= 1;
            }else if (date2.getTime()==date1.getTime()){
                //开始时间与结束时间相同
                i= 2;
            }else if (date2.getTime()>date1.getTime()){
                //结束时间大于开始时间
                i= 3;
            }
        } catch (Exception e) {

        }
        return  i;
    }
    //获取本月的第一天和最后一天
    public void getDay(){
        dateFormater = new SimpleDateFormat(
                "yyyy-MM-dd");
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.getTime();
    }
    public void getDayTwo(){
        dateFormaterTwo = new SimpleDateFormat(
                "yyyy年MM月dd日");
        calTwo = Calendar.getInstance();
        calTwo.set(Calendar.DAY_OF_MONTH, 1);
        calTwo.getTime();
    }
    //验证是否能请求接口
    public boolean getTimeHttp(){

        if((timeCompare(startdate,enddate)==1)){
            if("0".endsWith(time)){
                showToast("开始时间大于结束时间，请重新选择");
                return false;
            }
        }else if((timeCompare(startdate,enddate)==2)){
            if("0".endsWith(time)){
                showToast("开始时间等于结束时间，请重新选择");
                return false;
            }
        }
        return true;
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
