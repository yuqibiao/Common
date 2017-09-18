package com.afrid.common.ui.fragment;

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
import com.afrid.common.ui.activity.BTDeviceScanActivity;
import com.afrid.common.ui.activity.ScanLinenActivity;
import com.afrid.swingu.utils.SwingUManager;
import com.yyyu.baselibrary.utils.MyLog;
import com.yyyu.baselibrary.utils.MyToast;
import com.yyyu.baselibrary.view.recyclerview.listener.OnRvItemClickListener;

import java.util.List;

import butterknife.BindView;
import rx.Subscription;

/**
 * 功能：库房选择
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class MainFragment extends MyBaseFragment {

    private static final String TAG = "MainFragment";

    @BindView(R.id.rv_warehouse)
    RecyclerView rv_warehouse;

    private APIMethodManager apiMethodManager;
    private WarehouseAdapter warehouseAdapter;
    private List<GetWarehouseReturn.ResultDataBean> resultData;
    private MyApplication application;
    private Subscription subscription;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }


    @Override
    protected void beforeInit() {
        super.beforeInit();
        application = (MyApplication) getActivity().getApplication();
        apiMethodManager = APIMethodManager.getInstance();
    }

    @Override
    protected void initView() {
        rv_warehouse.setLayoutManager(new GridLayoutManager(getContext(), 3));
        warehouseAdapter = new WarehouseAdapter(getContext());
        rv_warehouse.setAdapter(warehouseAdapter);
    }

    @Override
    protected void initListener() {
        rv_warehouse.addOnItemTouchListener(new OnRvItemClickListener(rv_warehouse) {
            @Override
            public void onItemClick(View itemView, int position) {
                if (!SwingUManager.getInstance(getContext()).isConnected()) {
                    MyToast.showShort(getContext(), "手持机未连接！");
                    BTDeviceScanActivity.startAction(getActivity());
                    return;
                }
                application.setCheckWarehouseId(resultData.get(position).getWarehouseId());
                ScanLinenActivity.startAction(getActivity(), resultData.get(position).getWarehouseName());
            }

            @Override
            public void onItemLongClick(View itemView, int position) {

            }
        });
    }

    @Override
    protected void initData() {
        showLoadDialog("数据加载中...");
        super.initData();
        subscription = apiMethodManager.getWarehouse(1, new IRequestCallback<GetWarehouseReturn>() {
            @Override
            public void onSuccess(GetWarehouseReturn result) {
                MyLog.e(TAG, "result===" + result.getMsg());
                int resultCode = result.getResultCode();
                if (resultCode == 200) {
                    resultData = result.getResultData();
                    warehouseAdapter.setDataBeanList(resultData);
                } else if (resultCode == 500) {
                    MyToast.show(getContext(), result.getMsg(), Toast.LENGTH_SHORT);
                }
                hiddenLoadingDialog();
            }

            @Override
            public void onFailure(Throwable throwable) {
                hiddenLoadingDialog();
                MyToast.show(getContext(), "加载失败，请检查你的网络！", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void onDestroy() {
        if (subscription!=null&&!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
