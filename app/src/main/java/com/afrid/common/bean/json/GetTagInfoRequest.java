package com.afrid.common.bean.json;

import java.util.List;

/**
 * 功能：根据标签id得到标签信息的请求
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class GetTagInfoRequest {


    private List<String> requestData;

    public List<String> getRequestData() {
        return requestData;
    }

    public void setRequestData(List<String> requestData) {
        this.requestData = requestData;
    }
}
