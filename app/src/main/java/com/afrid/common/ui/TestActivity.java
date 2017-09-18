package com.afrid.common.ui;

import android.content.Intent;
import android.view.View;

import com.afid.utils.ScanManager;
import com.afid.utils.ZKCManager;
import com.afrid.common.R;
import com.afrid.common.bean.SubReceiptListBean;
import com.smartdevice.aidltestdemo.CaptureActivity;
import com.smartdevice.aidltestdemo.ScannerActivity;
import com.yyyu.baselibrary.template.BaseActivity;
import com.yyyu.baselibrary.utils.MyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class TestActivity extends BaseActivity {

    private static final int REQUEST_CODE = 1001;
    private ZKCManager zkcManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
    }

    @Override
    protected void initView() {
        zkcManager = ZKCManager.getInstance(this);
        zkcManager.bindService();
    }

    @Override
    protected void initListener() {

    }

    public void printText(View view) {
        zkcManager.getPrintManager().printText("yu\nqi\nbiao\n");
    }

    public void printQRCode(View view) {
        zkcManager.getPrintManager().printQRCode("123");
    }

    public void printBarCode(View view) {
        zkcManager.getPrintManager().printBarCode("0123456789");
    }

    public void scanQRCode(View view) {
        zkcManager.getScanManager().toScanAct(this);
    }

    public void printReceipt(View view) {

        List<SubReceiptListBean> subReceiptListBeanList = new ArrayList<>();
        SubReceiptListBean subReceiptListBean1 = new SubReceiptListBean();
        subReceiptListBean1.setTagTypeName("毛巾");
        subReceiptListBean1.setTagNum(10);
        SubReceiptListBean subReceiptListBean2 = new SubReceiptListBean();
        subReceiptListBean2.setTagTypeName("被套");
        subReceiptListBean2.setTagNum(100);
        SubReceiptListBean subReceiptListBean3 = new SubReceiptListBean();
        subReceiptListBean3.setTagTypeName("枕套");
        subReceiptListBean3.setTagNum(100);
        subReceiptListBeanList.add(subReceiptListBean1);
        subReceiptListBeanList.add(subReceiptListBean2);
        subReceiptListBeanList.add(subReceiptListBean3);
        String printStr = buildReceipt("张三", "深圳仓","0011709121544439", subReceiptListBeanList);
        zkcManager.getPrintManager().printText(printStr+printStr);
        //zkcManager.getPrintManager().printBarCode("0011709121544439");
    }

    /**
     * 生成收据
     *
     * @return
     */
    public String buildReceipt(String username, String warehouse,String receiptId
            , List<SubReceiptListBean> subReceiptListBeanList) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Shanghai");
        DATE_FORMAT.setTimeZone(TIME_ZONE);
        String date = DATE_FORMAT.format(Calendar.getInstance(TIME_ZONE).getTime());

        sb.append("\n");
        sb.append("-------------收据单------------\n");
        sb.append("\n");
        sb.append("\n");
        sb.append("流水号："+receiptId+"\n");
        sb.append("日期：" + date+ "\n");
        sb.append("\n");
        sb.append("提交人姓名：" + username+ "\n");
        sb.append("\n");
        sb.append("仓库：" + warehouse + "\n");
        sb.append("\n");

        for (SubReceiptListBean bean : subReceiptListBeanList) {
            sb.append(bean.getTagTypeName() + "----------------------x" + bean.getTagNum()+"\n");
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


    @Override
    protected void onDestroy() {
        zkcManager.unbindService();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ScanManager.REQUEST_CODE_SCAN) {
            MyToast.showLong(TestActivity.this, data.getStringExtra("SCAN_RESULT"));
        }
    }
}
