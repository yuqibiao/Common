package com.afrid.common.bean.json;

import com.afrid.common.bean.SubReceiptListBean;

import java.util.List;

/**
 * 功能：保存收据请求数据
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class SaveReceiptRequest {


    private RequestDataBean requestData;

    public RequestDataBean getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataBean requestData) {
        this.requestData = requestData;
    }

    public static class RequestDataBean {
        private String readerId;
        private int userId;
        private int senderWarehouseId;
        private int receiptWarehouseId;
        private int linenNum;
        private List<SubReceiptListBean> subReceiptList;

        public String getReaderId() {
            return readerId;
        }

        public void setReaderId(String readerId) {
            this.readerId = readerId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getSenderWarehouseId() {
            return senderWarehouseId;
        }

        public void setSenderWarehouseId(int senderWarehouseId) {
            this.senderWarehouseId = senderWarehouseId;
        }

        public int getReceiptWarehouseId() {
            return receiptWarehouseId;
        }

        public void setReceiptWarehouseId(int receiptWarehouseId) {
            this.receiptWarehouseId = receiptWarehouseId;
        }

        public int getLinenNum() {
            return linenNum;
        }

        public void setLinenNum(int linenNum) {
            this.linenNum = linenNum;
        }

        public List<SubReceiptListBean> getSubReceiptList() {
            return subReceiptList;
        }

        public void setSubReceiptList(List<SubReceiptListBean> subReceiptList) {
            this.subReceiptList = subReceiptList;
        }
    }
}
