package com.afrid.common.ui.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.afrid.common.MyApplication;
import com.afrid.common.R;
import com.afrid.common.adapter.WarehouseAdapter;
import com.afrid.common.bean.json.return_data.GetWarehouseReturn;
import com.afrid.common.net.APIMethodManager;
import com.afrid.common.net.IRequestCallback;
import com.yyyu.baselibrary.utils.MyLog;
import com.yyyu.baselibrary.utils.MyToast;
import com.yyyu.baselibrary.view.recyclerview.listener.OnRvItemClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * 功能：
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/20
 */

public class WashFactoryChoiceActivity extends  MyBaseActivity{

    private static final String TAG = "WashFactoryChoiceActivi";
    private static final int REQUEST_WASH_FACTORY = 101;

    @BindView(R.id.rv_wash_factory)
    RecyclerView rv_wash_factory;

    private APIMethodManager apiMethodManager;
    private WarehouseAdapter warehouseAdapter;
    private List<GetWarehouseReturn.ResultDataBean> resultData;
    private MyApplication application;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_wash_factory_choice;
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
        apiMethodManager = APIMethodManager.getInstance();
        application = (MyApplication) getApplication();
    }

    @Override
    protected void initView() {
        rv_wash_factory.setLayoutManager(new GridLayoutManager(this ,1));
        warehouseAdapter = new WarehouseAdapter(this);
        rv_wash_factory.setAdapter(warehouseAdapter);
    }

    @Override
    protected void initListener() {
        rv_wash_factory.addOnItemTouchListener(new OnRvItemClickListener(rv_wash_factory) {
            @Override
            public void onItemClick(View itemView, int position) {
               setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onItemLongClick(View itemView, int position) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        showLoadDialog("数据加载中....");
        apiMethodManager.getWashFactory(application.getUser_id(), new IRequestCallback<GetWarehouseReturn>() {
            @Override
            public void onSuccess(GetWarehouseReturn result) {
                MyLog.e(TAG, "result===" + result.getMsg());
                int resultCode = result.getResultCode();
                if (resultCode == 200) {
                    resultData = result.getResultData();
                    warehouseAdapter.setDataBeanList(resultData);
                } else if (resultCode == 500) {
                    MyToast.show(WashFactoryChoiceActivity.this, result.getMsg(), Toast.LENGTH_SHORT);
                }
                hiddenLoadingDialog();
            }

            @Override
            public void onFailure(Throwable throwable) {
                MyToast.showShort(WashFactoryChoiceActivity.this , "网络连接失败！");
                hiddenLoadingDialog();
            }
        });
    }

    public static void startActionForResult(Activity activity){
        Intent intent = new Intent(activity , WashFactoryChoiceActivity.class);
        activity.startActivityForResult(intent , REQUEST_WASH_FACTORY);
    }

}
