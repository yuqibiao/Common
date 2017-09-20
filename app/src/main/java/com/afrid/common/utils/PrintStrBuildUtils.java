package com.afrid.common.utils;

import com.afrid.common.bean.SubReceiptListBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * 功能：创建打印内容
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/13
 */

public class PrintStrBuildUtils {

    /**
     * 生成收据
     *
     * @return
     */
    public static String buildReceipt(boolean isRepeat,String username, String warehouse,String receiptId
            , List<SubReceiptListBean> subReceiptListBeanList) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Shanghai");
        DATE_FORMAT.setTimeZone(TIME_ZONE);
        String date = DATE_FORMAT.format(Calendar.getInstance(TIME_ZONE).getTime());

        sb.append("\n");
        if (isRepeat){
            sb.append("-------------盘存单------------\n");
        }else{
            sb.append("--------盘存单（重印）---------\n");
        }
        sb.append("\n");
        sb.append("\n");
        sb.append("流水号："+receiptId+"\n");
        sb.append("" + date+ "\n");
        sb.append("\n");
        sb.append("提交人姓名：" + username+ "\n");
        sb.append("\n");
        sb.append("仓库：" + warehouse + "\n");
        sb.append("\n");

        for (SubReceiptListBean bean : subReceiptListBeanList) {
            String tagTypeName = bean.getTagTypeName();
            sb.append(tagTypeName);
            for (int i = 0; i <8-tagTypeName.length() ; i++) {
                sb.append("　");
            }
            sb.append("--------------x" + bean.getTagNum()+"\n");
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("客户签字________________________\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("------www.arfid.co(阿菲德)------");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }


}
