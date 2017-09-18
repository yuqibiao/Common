package com.afrid.common.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 功能：蓝牙相关工具类
 *
 * @author yu
 * @version 1.0
 * @date 2017/6/23
 */

public class BTManager {

    private static Context mContext;

    private BTManager(){

    }

    private static class InstanceHolder{
        private static  BTManager INSTANCE = new BTManager();
    }

    public static  BTManager getInstance(Context context){
        mContext = context;
        return InstanceHolder.INSTANCE;
    }

    public Set<BluetoothDevice> getBoundsDevice(){
        BluetoothAdapter  defaultAdapter = getDefaultAdapter(mContext);
        return defaultAdapter.getBondedDevices();//获取配对成功的蓝牙设备信息
    }

    /**
     * 开始蓝牙扫描(通过广播接受扫描的设备信息)
     *
     */
    public   void startDescovery(){
        BluetoothAdapter  defaultAdapter = getDefaultAdapter(mContext);
        if (defaultAdapter.isDiscovering()) {
            defaultAdapter.cancelDiscovery();//"取消扫描..."
        } else {
            defaultAdapter.startDiscovery();//"开始扫描..."
        }
    }


    /**
     * 得到BluetoothAdapter
     *
     * @param context
     * @return
     */
    private  BluetoothAdapter getDefaultAdapter(Context context) {
        BluetoothAdapter adapter = null;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            adapter = BluetoothAdapter.getDefaultAdapter();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            adapter = bluetoothManager.getAdapter();
        }
        return adapter;
    }

    /**
     * 绑定蓝牙设备
     *
     * @param device
     * @return
     */
    public  boolean bondDevice(BluetoothDevice device) {
        try {
            if(device != null) {
                if(device.getBondState() == 12) {
                    return true;
                } else {
                    Method e = BluetoothDevice.class.getMethod("createBond", new Class[0]);
                    e.invoke(device, new Object[0]);
                    return true;
                }
            } else {
                return false;
            }
        } catch (NoSuchMethodException var2) {
            var2.printStackTrace();
            return false;
        } catch (IllegalAccessException var3) {
            var3.printStackTrace();
            return false;
        } catch (IllegalArgumentException var4) {
            var4.printStackTrace();
            return false;
        } catch (InvocationTargetException var5) {
            var5.printStackTrace();
            return false;
        }
    }

    /**
     * 跳转到系统蓝牙设置界面
     *
     * @param activity
     */
    public  void toSysBTActivity(Activity activity){
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        activity.startActivity(intent);
    }


}
