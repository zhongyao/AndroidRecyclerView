package com.hongri.recyclerview.common;

/**
 * @author：zhongyao on 2016/7/1 10:23
 * @description:
 */
public class APPConstants {

    //线性ListView布局
    public static final int Type_List_Layout = 0;
    //GridView布局
    public static final int Type_Grid_Layout = 1;
    //瀑布流布局
    public static final int Type_Staggered_Layout = 2;
    public static int type = 0;
    //详情默认标题
    public static final String Detail_Fragment_Title_Default = "DetailFragment";
    //列num
    public static final int Column_Nums = 3;


    //代表multiple中item为Image
    public static final int Type_Multiple_Image = 0;
    //代表multiple中item为Text
    public static final int Type_Multiple_Text = 1;
    //代表multiple中item为Image+Text(horizontal)
    public static final int Type_Multiple_ImageText_Horizontal = 2;
    //代表multiple中item为BigImage+Text(Vertical)
    public static final int Type_Multiple_BigImageText_Vertical = 3;
    //代表multiple中item为Text+multipleImage(Vertical)
    public static final int Type_Multiple_TextMultipleImage_Vertical = 4;
    //ExpendedList
    public static final int Type_Expended_List_Text = 5;
    //ExpendedGrid
    public static final int Type_Expended_Grid_Text = 6;

    //是否为MultipleComplicatedList
    public static boolean complicatedList = false;

    //图片url
    public static final String urlImage = "http://img5.imgtn.bdimg.com/it/u=3641278837,1050803462&fm=21&gp=0.jpg";

}
