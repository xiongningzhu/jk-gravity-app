package com.zng.jk_gravity_app.interfaces;

import android.view.View;

public interface OnRecyclerItemStoopingCartClickerListener {
    void onRecyclerItemClick(View view, int position);
    void OnRecyclerItemStoopingItemClick(View view, int position, int mposition);
    void OnRecyclerItemStoopinggroupPositionItemClick(View view, int groupPosition, int position, int mposition);
    void deleteBtn(View view, int groupPosition, int position);
    void addBtn(View view, int groupPosition, int position);
}
