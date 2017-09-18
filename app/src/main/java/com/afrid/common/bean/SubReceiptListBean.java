package com.afrid.common.bean;

import java.util.List;

public class SubReceiptListBean {

    private int tagTypeId;
    private String tagTypeName;
    private int tagNum;
    private List<String> tagIdList;

    public int getTagTypeId() {
        return tagTypeId;
    }

    public void setTagTypeId(int tagTypeId) {
        this.tagTypeId = tagTypeId;
    }

    public String getTagTypeName() {
        return tagTypeName;
    }

    public void setTagTypeName(String tagTypeName) {
        this.tagTypeName = tagTypeName;
    }

    public int getTagNum() {
        return tagNum;
    }

    public void setTagNum(int tagNum) {
        this.tagNum = tagNum;
    }

    public List<String> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<String> tagIdList) {
        this.tagIdList = tagIdList;
    }
}