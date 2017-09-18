package com.afrid.common.bean;

/**
 * 功能：
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class WareHouse {

    private Integer warehouseId;
    private String warehouseName;

    public WareHouse() {
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
