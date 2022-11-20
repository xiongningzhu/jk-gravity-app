package com.zng.jk_gravity_app.been;

public class CategoryBeen {
    private String name;
    private String cate_img;
    private int goods_category_id;
    private Boolean isclick=false;

    public Boolean getIsclick() {
        return isclick;
    }

    public void setIsclick(Boolean isclick) {
        this.isclick = isclick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCate_img() {
        return cate_img;
    }

    public void setCate_img(String cate_img) {
        this.cate_img = cate_img;
    }

    public int getGoods_category_id() {
        return goods_category_id;
    }

    public void setGoods_category_id(int goods_category_id) {
        this.goods_category_id = goods_category_id;
    }
}
