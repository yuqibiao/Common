package com.afrid.common.bean.json;

/**
 * 功能：登录请求数据
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class LoginRequest {


    private RequestDataBean requestData;

    public RequestDataBean getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataBean requestData) {
        this.requestData = requestData;
    }

    public static class RequestDataBean {

        private String username;
        private String pwd;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
    }
}
