package com.afrid.common.ui.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.afrid.common.R;
import com.afrid.common.adapter.WarehouseAdapter;
import com.afrid.common.bean.json.return_data.GetWarehouseReturn;
import com.afrid.common.net.APIMethodManager;
import com.afrid.common.net.IRequestCallback;
import com.yyyu.baselibrary.template.BaseDialog;
import com.yyyu.baselibrary.utils.MyLog;
import com.yyyu.baselibrary.utils.MyToast;
import com.yyyu.baselibrary.utils.WindowUtils;
import com.yyyu.baselibrary.view.recyclerview.listener.OnRvItemClickListener;

import java.util.List;

/**
 * 功能：
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/20
 */

public class WashFactoryChoiceDialog extends BaseDialog{

    private static final String TAG = "WashFactoryChoiceDialog";
    
    private RecyclerView rv_wash_factory;
    private APIMethodManager apiMethodManager;
    private int userId;
    private WarehouseAdapter warehouseAdapter;
    private List<GetWarehouseReturn.ResultDataBean> resultData;
    private int warehouseId;

    public WashFactoryChoiceDialog(Context context , int userId) {
        super(context);
        this.userId = userId;
    }

    @Override
    protected WindowManager.LayoutParams getLayoutParams() {
        lp.width = WindowUtils.getSize(mContext)[1] / 2;
        lp.height =  WindowUtils.getSize(mContext)[1]/2;
        return lp;
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
        apiMethodManager = APIMethodManager.getInstance();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_wash_factory_choice;
    }

    @Override
    protected void initView() {
        rv_wash_factory = getView(R.id.rv_wash_factory);
        rv_wash_factory.setLayoutManager(new GridLayoutManager(getContext(), 1));
        warehouseAdapter = new WarehouseAdapter(getContext());
        rv_wash_factory.setAdapter(warehouseAdapter);
    }

    @Override
    protected void initListener() {
        rv_wash_factory.addOnItemTouchListener(new OnRvItemClickListener(rv_wash_factory) {
            @Override
            public void onItemClick(View itemView, int position) {
                warehouseId = resultData.get(position).getWarehouseId();
            }

            @Override
            public void onItemLongClick(View itemView, int position) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        apiMethodManager.getWashFactory(userId, new IRequestCallback<GetWarehouseReturn>() {
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
            }

            @Override
            public void onFailure(Throwable throwable) {
                MyToast.showShort(mContext , "网络连接失败！");
            }
        });
    }

    public int getCheckedWashFactoryId(){

        return warehouseId;
    }

}
