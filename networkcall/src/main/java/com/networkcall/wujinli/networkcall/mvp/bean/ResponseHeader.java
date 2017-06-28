package com.networkcall.wujinli.networkcall.mvp.bean;


/**
 * author: WuJinLi
 * time  : 17/6/21
 * desc  : 具体情况更具接口返回类型进行创建（返回信息头部信息，例如：接口返回信息状态，错误信息提示）
 */
public class ResponseHeader extends BaseEntity {

    private String repcd;

    private String repmsg;

    private String MOBILETOKEN;

    private String UUID;

    private String NONCE;

    private String TIMESTAMP;

    public String getRepcd() {
        return repcd;
    }

    public void setRepcd(String repcd) {
        this.repcd = repcd;
    }

    public String getRepmsg() {
        return repmsg;
    }

    public void setRepmsg(String repmsg) {
        this.repmsg = repmsg;
    }

    public String getMOBILETOKEN() {
        return MOBILETOKEN;
    }

    public void setMOBILETOKEN(String MOBILETOKEN) {
        this.MOBILETOKEN = MOBILETOKEN;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getNONCE() {
        return NONCE;
    }

    public void setNONCE(String NONCE) {
        this.NONCE = NONCE;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }

    public void setTIMESTAMP(String TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }
}
