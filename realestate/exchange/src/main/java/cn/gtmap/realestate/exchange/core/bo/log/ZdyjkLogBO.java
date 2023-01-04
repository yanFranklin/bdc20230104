package cn.gtmap.realestate.exchange.core.bo.log;

import java.io.Serializable;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-08-23
 * @description  接口管理记录日志实体
 */
public class ZdyjkLogBO implements Serializable {

    private static final long serialVersionUID = 4655386649965332844L;

    /**
     * 调用方
     */
    private String userid;
    /**
     * 调用接口
     */
    private String jkid;
    /**
     * head参数
     * */
    private String head;
    /**
     * body参数
     */
    private String body;
    /**
     * 调用时间
     */
    private long dysj;
    /**
     * 返回值
     */
    private String result;
    /**
     * 接口名称
     */
    private String jkmc;
    /**
     * 接口链接地址
     */
    private String crlj;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getJkid() {
        return jkid;
    }

    public void setJkid(String jkid) {
        this.jkid = jkid;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getDysj() {
        return dysj;
    }

    public void setDysj(long dysj) {
        this.dysj = dysj;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getJkmc() {
        return jkmc;
    }

    public void setJkmc(String jkmc) {
        this.jkmc = jkmc;
    }

    public String getCrlj() {
        return crlj;
    }

    public void setCrlj(String crlj) {
        this.crlj = crlj;
    }

    @Override
    public String toString() {
        return "ZdyjkLogBO{" + "userid='" + userid + '\'' +
                ", jkid='" + jkid + '\'' +
                ", head='" + head + '\'' +
                ", body='" + body + '\'' +
                ", dysj=" + dysj +
                ", result='" + result + '\'' +
                ", jkmc='" + jkmc + '\'' +
                ", crlj='" + crlj + '\'' +
                '}';
    }
}
