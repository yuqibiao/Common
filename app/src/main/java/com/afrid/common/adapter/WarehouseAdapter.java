package com.afrid.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afrid.common.R;
import com.afrid.common.bean.json.return_data.GetWarehouseReturn;

import java.util.List;

/**
 * 功能：部门选择数据Adapter
 *
 * @author yu
 * @version 1.0
 * @date 2017/6/27
 */

public class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.DepartmentsViewHolder> {

    private Context mContext;
    private List<GetWarehouseReturn.ResultDataBean> dataBeanList;

    public WarehouseAdapter(Context context) {
        this.mContext = context;
    }

    public WarehouseAdapter(Context context, List<GetWarehouseReturn.ResultDataBean> deptList) {
        this.mContext = context;
        this.dataBeanList = deptList;
    }

    @Override
    public WarehouseAdapter.DepartmentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(mContext).inflate(R.layout.rv_item_warehouse, parent, false);
        DepartmentsViewHolder viewHolder = new DepartmentsViewHolder(item);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WarehouseAdapter.DepartmentsViewHolder holder, int position) {
        holder.getTv_dept_name().setText(dataBeanList.get(position).getWarehouseName());
    }

    @Override
    public int getItemCount() {
        return dataBeanList == null ? 0 : dataBeanList.size();
    }

    public void setDataBeanList(List<GetWarehouseReturn.ResultDataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
        notifyDataSetChanged();
    }

    public static class DepartmentsViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_dept_name;

        public DepartmentsViewHolder(View itemView) {
            super(itemView);
            tv_dept_name = (TextView) itemView.findViewById(R.id.tv_dept_name);
        }

        public TextView getTv_dept_name() {
            return tv_dept_name;
        }
    }


}
