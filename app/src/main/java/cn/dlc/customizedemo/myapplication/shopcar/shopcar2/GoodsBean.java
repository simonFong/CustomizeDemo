package cn.dlc.customizedemo.myapplication.shopcar.shopcar2;

import java.util.List;

import cn.dlc.customizedemo.myapplication.shopcar.ShopCarGoodsItemBean;

public class GoodsBean {
    public static final int SHOP_DETAIL = 0;
    public static final int GOOD_DETAIL = 1;

    public int itemType;//0:商店标头 1:产品

    public String shopName;
    public String rongId;

    public String img;
    public String title;
    public String type;
    public String price;
    public int num;
    public boolean isSelect = false;
}
