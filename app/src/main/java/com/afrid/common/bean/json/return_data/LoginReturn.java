package com.afrid.common.bean.json.return_data;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：登录返回
 *
 * @author yu
 * @version 1.0
 * @date 2017/9/12
 */

public class LoginReturn implements Serializable{

    private int resultCode;
    private String msg;
    private ResultDataBean resultData;

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

    public ResultDataBean getResultData() {
        return resultData;
    }

    public void setResultData(ResultDataBean resultData) {
        this.resultData = resultData;
    }

    public static class ResultDataBean {
        private String user_NAME;
        private int user_ID;
        private List<String> readerIdList;

        public String getUser_NAME() {
            return user_NAME;
        }

        public void setUser_NAME(String user_NAME) {
            this.user_NAME = user_NAME;
        }

        public int getUser_ID() {
            return user_ID;
        }

        public void setUser_ID(int user_ID) {
            this.user_ID = user_ID;
        }


        public List<String> getReaderIdList() {
            return readerIdList;
        }

        public void setReaderIdList(List<String> readerIdList) {
            this.readerIdList = readerIdList;
        }
    }
}
