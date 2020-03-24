package com.song.test.model;

public class AutoLog {
    int id = 0;
    private String tastCase;
    private String reqType;
    private String reqUrl;
    private String reqData;
    private String expResult;
    private String actResult;
    private int result;
    private String execTime;

    public AutoLog() {
    }

    public AutoLog(int id, String tastCase, String reqType, String reqUrl, String reqData, String expResult, String actResult, int result, String execTime) {
        this.id = id;
        this.tastCase = tastCase;
        this.reqType = reqType;
        this.reqUrl = reqUrl;
        this.reqData = reqData;
        this.expResult = expResult;
        this.actResult = actResult;
        this.result = result;
        this.execTime = execTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTastCase() {
        return tastCase;
    }

    public void setTastCase(String tastCase) {
        this.tastCase = tastCase;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getReqData() {
        return reqData;
    }

    public void setReqData(String reqData) {
        this.reqData = reqData;
    }

    public String getExpResult() {
        return expResult;
    }

    public void setExpResult(String expResult) {
        this.expResult = expResult;
    }

    public String getActResult() {
        return actResult;
    }

    public void setActResult(String actResult) {
        this.actResult = actResult;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    @Override
    public String toString() {
        return "AutoLog{" +
                "id=" + id +
                ", tastCase='" + tastCase + '\'' +
                ", reqType='" + reqType + '\'' +
                ", reqUrl='" + reqUrl + '\'' +
                ", reqData='" + reqData + '\'' +
                ", expResult='" + expResult + '\'' +
                ", actResult='" + actResult + '\'' +
                ", result=" + result +
                ", execTime='" + execTime + '\'' +
                '}';
    }
}
