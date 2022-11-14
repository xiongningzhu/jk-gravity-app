package com.zng.jk_gravity_app.interfaces;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

public interface OnRefreshAndLoadMoreListener {

    void OnRefresh(RefreshLayout refreshLayout);

    void LoadMore(RefreshLayout refreshLayout);
}
