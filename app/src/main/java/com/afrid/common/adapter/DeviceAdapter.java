package com.afrid.common.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.afrid.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：扫描蓝牙设备对应Adapter
 *
 * @author yu
 * @version 1.0
 * @date 2017/6/23
 */

public class DeviceAdapter extends BaseAdapter {

    private Context context;
    private List<BluetoothDevice> bluetoothDeviceList;

    public DeviceAdapter(Context context) {
        this.context = context;
        this.bluetoothDeviceList = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return bluetoothDeviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.lv_item_bt_device, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_mac = (TextView) convertView.findViewById(R.id.txt_mac);
            holder.txt_rssi = (TextView) convertView.findViewById(R.id.txt_rssi);
        }

        BluetoothDevice device = bluetoothDeviceList.get(position);
        String name = device.getName();
        String mac = device.getAddress();
        holder.txt_name.setText(name);
        holder.txt_mac.setText(mac);
        return convertView;
    }

    public List<BluetoothDevice> getBluetoothDeviceList() {
        return bluetoothDeviceList;
    }

    public void setBluetoothDeviceList(List<BluetoothDevice> bluetoothDeviceList) {
        this.bluetoothDeviceList = bluetoothDeviceList;
    }

    public void clear() {
        bluetoothDeviceList.clear();
    }

    class ViewHolder {
        TextView txt_name;
        TextView txt_mac;
        TextView txt_rssi;
    }
}
