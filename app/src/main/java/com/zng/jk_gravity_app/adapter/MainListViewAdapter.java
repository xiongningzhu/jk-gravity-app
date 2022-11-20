package com.zng.jk_gravity_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zng.jk_gravity_app.R;
import com.zng.jk_gravity_app.been.CategoryBeen;
import com.zng.jk_gravity_app.interfaces.OnRecyclerItemClickerListener;

import java.util.ArrayList;
import java.util.List;

public class MainListViewAdapter extends RecyclerView.Adapter<MainListViewAdapter.MViewHolder> {

    private RecyclerView mRecyclerView;

    private List<CategoryBeen> lists = new ArrayList<>();
    private Context mContext;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;
    private OnRecyclerItemClickerListener onItemClick;

    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;
    private String judje="";


    public MainListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setLists(List<CategoryBeen> lists) {
        this.lists = lists;
    }

    public List<CategoryBeen> getLists() {
        return lists;
    }

    @Override
    public MainListViewAdapter.MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new MainListViewAdapter.MViewHolder(VIEW_FOOTER);
        } else if (viewType == TYPE_HEADER) {
            return new MainListViewAdapter.MViewHolder(VIEW_HEADER);
        } else {
            return new MainListViewAdapter.MViewHolder(getLayout(R.layout.main_class_item));
        }
    }
    public class MViewHolder extends RecyclerView.ViewHolder {
        TextView text_title;
        RelativeLayout line_item;
        ImageView image_bag,image_bag2;
        MViewHolder(View itemView) {
            super(itemView);
            text_title= itemView.findViewById(R.id.text_title);
            line_item= itemView.findViewById(R.id.line_item);
            image_bag= itemView.findViewById(R.id.image_bag);
            image_bag2= itemView.findViewById(R.id.image_bag2);
        }
    }
    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(MainListViewAdapter.MViewHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) position--;
            final int mposition = position;
            holder.text_title.setText("第"+lists.get(mposition).getName()+"层");
            if(lists.get(mposition).getIsclick()){
                holder.image_bag.setVisibility(View.VISIBLE);
                holder.image_bag2.setVisibility(View.GONE);
                holder.text_title.setTextColor(0xccFFFFFF);
            }else {
                holder.text_title.setTextColor(0xcc000000);
                holder.image_bag.setVisibility(View.GONE);
                holder.image_bag2.setVisibility(View.VISIBLE);
            }
            holder.line_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClick.onRecyclerItemClick(view, mposition);
                }
            });

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




