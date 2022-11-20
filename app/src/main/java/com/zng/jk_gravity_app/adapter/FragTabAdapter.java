package com.zng.jk_gravity_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhy.autolayout.AutoRelativeLayout;
import com.zng.jk_gravity_app.R;
import com.zng.jk_gravity_app.been.ConditionsBeen;
import com.zng.jk_gravity_app.interfaces.OnRecyclerItemClickerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragTabAdapter extends RecyclerView.Adapter<FragTabAdapter.MViewHolder> {
    //1. 类内设置全局变量
    private int be_selected_item = -1;   //被选中的item的编号（用于选中的item，改变背景颜色）
    private RecyclerView mRecyclerView;
    private String selected_name="";//默认选中
    private Map<Integer, Boolean> map = new HashMap<>();

    private List<ConditionsBeen> lists = new ArrayList<>();
    private Context mContext;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;
    private OnRecyclerItemClickerListener onItemClick;

    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;
    private String judje="";

    public String getSelected_name() {
        return selected_name;
    }

    public void setSelected_name(String selected_name) {
        this.selected_name = selected_name;
    }

    public FragTabAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setLists(List<ConditionsBeen> lists) {
        this.lists = lists;
    }

    @Override
    public FragTabAdapter.MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new FragTabAdapter.MViewHolder(VIEW_FOOTER);
        } else if (viewType == TYPE_HEADER) {
            return new FragTabAdapter.MViewHolder(VIEW_HEADER);
        } else {
            return new FragTabAdapter.MViewHolder(getLayout(R.layout.vcountlayout));
        }
    }
    public class MViewHolder extends RecyclerView.ViewHolder {
        TextView text_name;
        AutoRelativeLayout line_bag;
        MViewHolder(View itemView) {
            super(itemView);
            text_name= itemView.findViewById(R.id.text_name);
            line_bag= itemView.findViewById(R.id.line_bag);
        }
    }

    public int getBe_selected_item() {
        return be_selected_item;
    }

    public void setBe_selected_item(int be_selected_item) {
        this.be_selected_item = be_selected_item;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(FragTabAdapter.MViewHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) position--;
            int mposition = position;
            try {
                if (be_selected_item == position) {
                    map.put(position,true);
                } else {
                    map.put(position,false);
                }
                if(map.get(position)){
                    holder.line_bag.setBackgroundColor(Color.parseColor("#D7403F"));
                    holder.text_name.setTextColor(0xccffffff);
                }else {
                    holder.line_bag.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    holder.text_name.setTextColor(0xcc262626);
                }
                holder.text_name.setText(lists.get(mposition).getLabel());
                holder.line_bag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick.onRecyclerItemClick(v, mposition);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }

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

    public String getJudje() {
        return judje;
    }

    public void setJudje(String judje) {
        this.judje = judje;
    }
}

