
package com.afrid.common.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afrid.common.MyApplication;
import com.afrid.common.R;
import com.afrid.common.bean.json.GetTagInfoRequest;
import com.afrid.swingu.utils.SwingUManager;
import com.yyyu.baselibrary.utils.MyLog;
import com.yyyu.baselibrary.utils.MyToast;
import com.yyyu.baselibrary.view.WhewView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;

/**
 * 布草扫描界面
 */
public class ScanLinenActivity extends MyBaseActivity {

    private static final String TAG = "ScanLinenActivity";

    @BindView(R.id.tv_warehouse)
    TextView tv_warehouse;
    @BindView(R.id.tb_scan)
    Toolbar tb_scan;
    @BindView(R.id.wv_scan)
    WhewView wvScan;

    //存储标签id的set
    private HashSet<String> tagIdSet = new HashSet<>();
    //存储标签id的list
    private List<String> tagIdList;
    private SwingUManager swingUManager;
    private String warehouseName;
    private MyApplication application;

    @Override
    public void beforeInit() {
        super.beforeInit();
        swingUManager = SwingUManager.getInstance(this);
        application = (MyApplication) getApplication().getApplicationContext();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan_linen;
    }

    @Override
    protected void initView() {
        setSupportActionBar(tb_scan);
        getSupportActionBar().setTitle("布草扫描");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        warehouseName = intent.getStringExtra("warehouseName");
        tv_warehouse.setText(warehouseName);
    }

    @Override
    protected void initListener() {
        swingUManager.setOnReadResultListener(new SwingUManager.OnReadResultListener() {
            @Override
            public void onRead(String tagId) {
                boolean isAdd = tagIdSet.add(tagId.substring(4));
                if (isAdd) {
                    MyLog.e(TAG, tagId.substring(4) + "   size" + tagIdSet.size());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //---TODO stop操作
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 开始扫描
     */
    public void startScan(View view) {
        tagIdSet.clear();
        //---判断设备是否为用户绑定的
        if (!application.getReaderIdList().contains(application.getCurrentReaderId())) {
            MyToast.showShort(this, "请连接您自己的手持机！");
            BTDeviceScanActivity.startAction(this);
            return;
        }

        if (!SwingUManager.getInstance(this).isConnected()) {
            MyToast.showShort(this, "手持机未连接！");
            BTDeviceScanActivity.startAction(this);
            return;
        }
        wvScan.start();
        swingUManager.resetReader();
        swingUManager.startReader();
    }

    /**
     * 结束扫描
     */
    public void stopScan(View view) {
        wvScan.stop();
        swingUManager.stopReader();
        tagIdList = new ArrayList<>(tagIdSet);
        GetTagInfoRequest request = new GetTagInfoRequest();
        request.setRequestData(tagIdList);
        ReceiptActivity.startActionForResult(this, mGson.toJson(request), warehouseName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ReceiptActivity.RECEIPT_REQUEST_CODE
                && resultCode == ReceiptActivity.RECEIPT_RESULT_CODE) {
            //正常的提交receipt后清空数据
            tagIdSet.clear();
            tagIdList.clear();
        }
    }

    /**
     * 页面跳转
     *
     * @param activity
     * @param warehouseName 仓库名
     */
    public static void startAction(Activity activity, String warehouseName) {
        Intent intent = new Intent(activity, ScanLinenActivity.class);
        intent.putExtra("warehouseName", warehouseName);
        activity.startActivity(intent);
    }

}
