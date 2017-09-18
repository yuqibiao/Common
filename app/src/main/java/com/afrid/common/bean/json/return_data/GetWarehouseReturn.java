package com.afrid.common.bean.json.return_data;

import java.util.List;

/**
 * 功能：获取用户对应仓库返回数据
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class GetWarehouseReturn {

    private int resultCode;
    private String msg;
    private List<ResultDataBean> resultData;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultDataBean> getResultData() {
        return resultData;
    }

    public void setResultData(List<ResultDataBean> resultData) {
        this.resultData = resultData;
    }

    public static class ResultDataBean {
        private int warehouseId;
        private String warehouseName;
        private long regTime;

        public int getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(int warehouseId) {
            this.warehouseId = warehouseId;
        }

        public String getWarehouseName() {
            return warehouseName;
        }

        public void setWarehouseName(String warehouseName) {
            this.warehouseName = warehouseName;
        }

        public long getRegTime() {
            return regTime;
        }

        public void setRegTime(long regTime) {
            this.regTime = regTime;
        }
    }
}
