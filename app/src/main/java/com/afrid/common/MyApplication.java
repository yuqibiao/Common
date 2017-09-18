package com.afrid.common;

import android.app.Application;

import java.util.List;

/**
 * 功能：自定义Application
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class MyApplication extends Application{

    private int user_id;
    private String user_name;
    private List<String> readerIdList;
    private String currentReaderId;
    private int checkWarehouseId;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<String> getReaderIdList() {
        return readerIdList;
    }

    public void setReaderIdList(List<String> readerIdList) {
        this.readerIdList = readerIdList;
    }

    public String getCurrentReaderId() {
        return currentReaderId;
    }

    public void setCurrentReaderId(String currentReaderId) {
        this.currentReaderId = currentReaderId;
    }

    public int getCheckWarehouseId() {
        return checkWarehouseId;
    }

    public void setCheckWarehouseId(int checkWarehouseId) {
        this.checkWarehouseId = checkWarehouseId;
    }
}
