package com.hongri.recyclerview.utils;

import com.hongri.recyclerview.R;

import java.util.ArrayList;

/**
 * @author：zhongyao on 2016/7/11 17:12
 * @description:
 */
public class DataUtil {

    private static ArrayList<String> mDataHome = new ArrayList<>();
    private static ArrayList<String> mData = new ArrayList<>();
    private static ArrayList<Integer> mImageData = new ArrayList<>();
    private static ArrayList<String> mAndroidArtData = new ArrayList<>();
    private static ArrayList<String> mImageUrls = new ArrayList<>();
    private static ArrayList<String> mImageThumbUrls = new ArrayList<>();
    private static String[] strHome = {"ListView", "GridView", "StaggeredGrid", "MultipleList", "MultipleComplicatedList", "ExpendedList", "GridImageVolleyTest","GridImageLoadPicsTest","GridImageDiskCacheTest","GridImageLoaderTest","PullToRefreshListView","CallBackTest","The Art of Android","ViewFragment","ReboundFragment"};
    private static String[] str = {"Android", "IOS", "C", "Linux", "Ruby", "Javascript", "HTML", "Java", "C#", "Python", "PHP", "C++", ".net"};
    public static int[] images = {R.drawable.icon_landscape_image1, R.drawable.icon_landscape_image2,
            R.drawable.icon_landscape_image3, R.drawable.icon_landscape_image4,
            R.drawable.icon_landscape_image5, R.drawable.icon_landscape_image6,
            R.drawable.icon_landscape_image7, R.drawable.icon_landscape_image8,
            R.drawable.icon_landscape_image9, R.drawable.icon_landscape_image10,
            R.drawable.icon_landscape_image11, R.drawable.icon_landscape_image12};

    public static String[] strAndroidArt = {"Animation","Drawable","GridView","View","CustomeScrollView","LocalBroadcastManager","rebound"};
//    public static int[] points = {-100,100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000};
    public static int[] points = {400,500,600,700,800};
    /**
     * 高清图片链接
     */
    public static String[] imagesUrl = {"http://b.zol-img.com.cn/desk/bizhi/image/5/1440x900/1403767981822.jpg",
            "http://pic1.win4000.com/wallpaper/2/4fcec0bf0fb7f.jpg",
            "http://pic3.bbzhi.com/zhiwubizhi/qingxinlvsezhiwubizhi/dong_zhiwu_165204_9.jpg",
            "http://img2.duitang.com/uploads/item/201211/25/20121125160403_a5feK.jpeg",
            "http://img5.duitang.com/uploads/item/201211/24/20121124225259_NSVuF.jpeg", "http://b.zol-img.com.cn/desk/bizhi/image/5/1440x900/1403767981822.jpg",
            "http://pic1.win4000.com/wallpaper/2/4fcec0bf0fb7f.jpg",
            "http://pic3.bbzhi.com/zhiwubizhi/qingxinlvsezhiwubizhi/dong_zhiwu_165204_9.jpg",
            "http://img2.duitang.com/uploads/item/201211/25/20121125160403_a5feK.jpeg",
            "http://img5.duitang.com/uploads/item/201211/24/20121124225259_NSVuF.jpeg", "http://b.zol-img.com.cn/desk/bizhi/image/5/1440x900/1403767981822.jpg",
            "http://pic1.win4000.com/wallpaper/2/4fcec0bf0fb7f.jpg",
            "http://pic3.bbzhi.com/zhiwubizhi/qingxinlvsezhiwubizhi/dong_zhiwu_165204_9.jpg",
            "http://img2.duitang.com/uploads/item/201211/25/20121125160403_a5feK.jpeg",
            "http://img5.duitang.com/uploads/item/201211/24/20121124225259_NSVuF.jpeg", "http://b.zol-img.com.cn/desk/bizhi/image/5/1440x900/1403767981822.jpg",
            "http://pic1.win4000.com/wallpaper/2/4fcec0bf0fb7f.jpg",
            "http://pic3.bbzhi.com/zhiwubizhi/qingxinlvsezhiwubizhi/dong_zhiwu_165204_9.jpg",
            "http://img2.duitang.com/uploads/item/201211/25/20121125160403_a5feK.jpeg",
            "http://img5.duitang.com/uploads/item/201211/24/20121124225259_NSVuF.jpeg", "http://b.zol-img.com.cn/desk/bizhi/image/5/1440x900/1403767981822.jpg",
            "http://pic1.win4000.com/wallpaper/2/4fcec0bf0fb7f.jpg",
            "http://pic3.bbzhi.com/zhiwubizhi/qingxinlvsezhiwubizhi/dong_zhiwu_165204_9.jpg",
            "http://img2.duitang.com/uploads/item/201211/25/20121125160403_a5feK.jpeg",
            "http://img5.duitang.com/uploads/item/201211/24/20121124225259_NSVuF.jpeg", "http://b.zol-img.com.cn/desk/bizhi/image/5/1440x900/1403767981822.jpg",
            "http://pic1.win4000.com/wallpaper/2/4fcec0bf0fb7f.jpg",
            "http://pic3.bbzhi.com/zhiwubizhi/qingxinlvsezhiwubizhi/dong_zhiwu_165204_9.jpg",
            "http://img2.duitang.com/uploads/item/201211/25/20121125160403_a5feK.jpeg",
            "http://img5.duitang.com/uploads/item/201211/24/20121124225259_NSVuF.jpeg", "http://b.zol-img.com.cn/desk/bizhi/image/5/1440x900/1403767981822.jpg",
            "http://pic1.win4000.com/wallpaper/2/4fcec0bf0fb7f.jpg",
            "http://pic3.bbzhi.com/zhiwubizhi/qingxinlvsezhiwubizhi/dong_zhiwu_165204_9.jpg",
            "http://img2.duitang.com/uploads/item/201211/25/20121125160403_a5feK.jpeg",
            "http://img5.duitang.com/uploads/item/201211/24/20121124225259_NSVuF.jpeg", "http://b.zol-img.com.cn/desk/bizhi/image/5/1440x900/1403767981822.jpg",
            "http://pic1.win4000.com/wallpaper/2/4fcec0bf0fb7f.jpg",
            "http://pic3.bbzhi.com/zhiwubizhi/qingxinlvsezhiwubizhi/dong_zhiwu_165204_9.jpg",
            "http://img2.duitang.com/uploads/item/201211/25/20121125160403_a5feK.jpeg",
            "http://img5.duitang.com/uploads/item/201211/24/20121124225259_NSVuF.jpeg", "http://b.zol-img.com.cn/desk/bizhi/image/5/1440x900/1403767981822.jpg",
            "http://pic1.win4000.com/wallpaper/2/4fcec0bf0fb7f.jpg",
            "http://pic3.bbzhi.com/zhiwubizhi/qingxinlvsezhiwubizhi/dong_zhiwu_165204_9.jpg",
            "http://img2.duitang.com/uploads/item/201211/25/20121125160403_a5feK.jpeg",
            "http://img5.duitang.com/uploads/item/201211/24/20121124225259_NSVuF.jpeg", "http://b.zol-img.com.cn/desk/bizhi/image/5/1440x900/1403767981822.jpg",
            "http://pic1.win4000.com/wallpaper/2/4fcec0bf0fb7f.jpg",
            "http://pic3.bbzhi.com/zhiwubizhi/qingxinlvsezhiwubizhi/dong_zhiwu_165204_9.jpg",
            "http://img2.duitang.com/uploads/item/201211/25/20121125160403_a5feK.jpeg",
            "http://img5.duitang.com/uploads/item/201211/24/20121124225259_NSVuF.jpeg",};

    /**
     * 非高清图片链接
     * @return
     */
    public final static String[] imageThumbUrls = new String[] {
            "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383264_3954.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383264_8243.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383248_3693.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383243_5120.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383242_3127.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383242_9576.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383242_1721.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383219_5806.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383214_7794.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383213_4418.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383213_3557.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383210_8779.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383172_4577.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383166_3407.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383166_2224.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383166_7301.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383165_7197.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383150_8410.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383131_3736.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383130_5094.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383130_7393.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383129_8813.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383100_3554.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383093_7894.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383092_2432.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383092_3071.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383091_3119.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383059_6589.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383059_8814.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383059_2237.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383058_4330.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406383038_3602.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382942_3079.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382942_8125.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382942_4881.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382941_4559.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382941_3845.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382924_8955.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382923_2141.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382923_8437.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382922_6166.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382922_4843.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382905_5804.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382904_3362.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382904_2312.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382904_4960.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382900_2418.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382881_4490.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382881_5935.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382880_3865.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382880_4662.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382879_2553.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382862_5375.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382862_1748.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382861_7618.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382861_8606.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382861_8949.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382841_9821.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382840_6603.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382840_2405.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382840_6354.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382839_5779.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382810_7578.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382810_2436.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382809_3883.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382809_6269.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382808_4179.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382790_8326.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382789_7174.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382789_5170.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382789_4118.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382788_9532.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382767_3184.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382767_4772.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382766_4924.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382766_5762.jpg",
            "http://img.my.csdn.net/uploads/201407/26/1406382765_7341.jpg"
    };

    public static ArrayList<String> getHomeData() {
        if (mDataHome != null && mDataHome.size() > 0) {
            mDataHome.clear();
        }
        for (int i = 0; i < strHome.length; i++) {
            mDataHome.add(strHome[i]);
        }
        return mDataHome;
    }

    public static ArrayList<String> getData() {
        if (mData != null && mData.size() > 0) {
            mData.clear();
        }
        for (int i = 0; i < str.length; i++) {
            mData.add(str[i]);
        }
        return mData;
    }

    public static ArrayList<Integer> getImageData() {
        if (mImageData != null && mImageData.size() > 0) {
            mImageData.clear();
        }
        for (int i = 0; i < images.length; i++) {
            mImageData.add(images[i]);
        }
        return mImageData;
    }

    public static ArrayList<String> getAndroidArtData(){
        if (mAndroidArtData != null && mAndroidArtData.size() > 0){
            mAndroidArtData.clear();
        }
        for (int i = 0;i < strAndroidArt.length; i++){
            mAndroidArtData.add(strAndroidArt[i]);
        }
        return mAndroidArtData;
    }


    /**
     * 获取高清图片urls
     * @return
     */
    public static ArrayList<String> getImageUrls() {
        if (mImageUrls != null && mImageUrls.size() > 0) {
            mImageUrls.clear();
        }
        for (int i = 0; i < imagesUrl.length; i++) {
            mImageUrls.add(imagesUrl[i]);
        }
        return mImageUrls;
    }

    /**
     * 获取非高清图片urls
     * @return
     */
    public static ArrayList<String> getImageThumbUrls(){
        if (mImageThumbUrls!=null && mImageThumbUrls.size() > 0){
            mImageThumbUrls.clear();
        }
        for (int i = 0; i < imageThumbUrls.length; i++) {
            mImageThumbUrls.add(imageThumbUrls[i]);
        }
        return mImageThumbUrls;
    }
}
