package com.zng.jk_gravity_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zng.jk_gravity_app.R;
import com.zng.jk_gravity_app.been.RevertBeen;
import com.zng.jk_gravity_app.interfaces.OnRecyclerItemClickerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RevertAdapter extends RecyclerView.Adapter<RevertAdapter.MViewHolder> {

    private RecyclerView mRecyclerView;

    private List<RevertBeen> lists = new ArrayList<>();
    private RevertBeen been;
    private Context mContext;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;
    private OnRecyclerItemClickerListener onItemClick;
    // 存储勾选框状态的map集合  解决滑动的复用问题
    private Map<Integer, Boolean> map = new HashMap<>();
    private Map<Integer, Boolean> starmap = new HashMap<>();
    private Map<Integer, Boolean> mapLevel = new HashMap<>();
    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;

    public RevertAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setLists(List<RevertBeen> lists) {
        this.lists = lists;
    }
    @Override
    public RevertAdapter.MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new RevertAdapter.MViewHolder(VIEW_FOOTER);
        } else if (viewType == TYPE_HEADER) {
            return new RevertAdapter.MViewHolder(VIEW_HEADER);
        } else {
            return new RevertAdapter.MViewHolder(getLayout(R.layout.revert_item));
        }
    }
    public class MViewHolder extends RecyclerView.ViewHolder {
        TextView text_name,text_title,text_time,text_state,text_wancheng;
        ImageView image_logo_two;
        AutoLinearLayout line_guihuan;
        ImageView mine_user_header_img;
        MViewHolder(View itemView) {
            super(itemView);
            //收藏
            text_name= itemView.findViewById(R.id.text_name);
            line_guihuan= itemView.findViewById(R.id.line_guihuan);
//            text_time= itemView.findViewById(R.id.text_time);
//            mine_user_header_img= itemView.findViewById(R.id.mine_user_header_img);
//            text_state= itemView.findViewById(R.id.text_state);
//            text_wancheng= itemView.findViewById(R.id.text_wancheng);
        }
    }
    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(RevertAdapter.MViewHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) position--;
            final int mposition = position;
            been=lists.get(mposition);
            //昵称
            //holder.text_id.setText(been.getUG_ID()+"");
            holder.text_name.setText(been.getName()+"");
            holder.line_guihuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onRecyclerItemClick(view, mposition);
                }
            });
//            holder.text_title.setText(been.getMobile()+"");
//            holder.text_time.setText(been.getCreatetime()+"");
//            RequestOptions options = new RequestOptions();
//            options.placeholder(R.mipmap.loginlogo);
//            options.error(R.mipmap.loginlogo);
//            Glide.with(mContext).load(been.getAvatar()).apply(options).into(holder.mine_user_header_img);
//            if(been.getIs_status()==0){//状态0未光看  1观看
//                holder.text_state.setText("未观看");
//            }else {
//                holder.text_state.setText("观看");
//            }
//            if(been.getIs_user_level()==0){//状态1已激活  0未激活
//                holder.text_wancheng.setText("未激活");
//            }else {
//                holder.text_wancheng.setText("已激活");
//            }

            //holder.text_money.setText(been.getUG_money()+"");
        }
    }

    @Override
    public int getItemCount() {
        int count = (lists == null ? 0 : lists.size());
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View getLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }
    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() :
                            1;
                }
            });
        }
    }

    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }


    //设置监听
    public void setOnItemClick(OnRecyclerItemClickerListener onItemClick) {
        this.onItemClick = onItemClick;

    }

}




