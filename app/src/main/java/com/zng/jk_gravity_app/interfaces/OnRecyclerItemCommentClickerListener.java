package com.zng.jk_gravity_app.interfaces;

import android.view.View;

public interface OnRecyclerItemCommentClickerListener {
    void onRecyclerItemClick(View view, int position);
    void onRecyclerTwoConmmentItemClick(View view, int position, int mposition);
    void onRecyclerItemLongClick(View view, int position);
}
