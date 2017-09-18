package com.smartdevice.aidltestdemo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/4/1.
 */

public class PatternMatcher {
    public static String SHOP_NAME = "[[ #{店名}]]";
    public static String CASHIER_NAME = "#{收银员姓名}";
    public static String TABLE_NAME = "#{牌号}";
    public static String ORDER_NAME = "#{单据号}";
    public static String ORDER_TIME = "#{下单时间}";
    public static String TOTAL_PRICE = "#{总计}";
    public static String CAN_RECEIVER = "#{应收}";
    public static String REAL_RECEIVER = "#{实收}";
    public static String TOTAL_COUNT = "#{总数}";
    public static String PAY = "#{支付方式}";
    public static String CHARGE = "#{找零}";
    public static String VIP_NAME = "#{会员姓名}";
    public static String VIP_NUMBER = "#{会员号}";
    public static String BLANCE = "#{余额}";
    public static String INTEGRAL = "#{积分}";
    public static String SHOP_ADDRESS = "#{店址}";
    public static String ITEM = "#item";
    public static String GOODS_NAME = "#{商品名称}";
    public static String UNIT_PRICE = "#{单价}";
    public static String GOODS_COUNT = "#{数量}";
    public static String SUB_TOTAL = "#{小计}";


    public static boolean replaceString(String pattern, String OriginalStr){
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(OriginalStr);
        return m.find();
    }
}
