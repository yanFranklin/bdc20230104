package cn.gtmap.realestate.certificate.core.model.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
 * @version 1.0 2019/1/22
 * @description
 */
@Document(indexName = "logindex", type = "log")
public class BdcDzzzLogDO implements Serializable {

    private static final long serialVersionUID = 1739061380621178451L;
    /**
     * 主键
     */
    private String logId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 操作日期
     */
    private Date czrq;

    /**
     * 操作调用方法
     */
    private String controller;

    /**
     * 方法参数
     */
    private String paramJson;

    /**
     * IP
     */
    private String ip;

    /**
     * 机器码
     */
    private String mac;

    /**
     * 计算机名
     */
    private String computerName;

    /**
     * 操作原因
     */
    private String reason;

    /**
     * 不动产权证号
     */
    private String bdcqzh;

    /**
     * 业务号
     */
    private String ywh;

    /**
     * 证照标识
     */
    private String zzbs;

    /**
     * 请求结果标识（0成功，1失败）
     */
    private Integer status;

    /**
     * 请求结果
     */
    private String message;

    /**
     * 单位代码
     */
    private String dwdm;


    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCzrq() {
        return czrq;
    }

    public void setCzrq(Date czrq) {
        this.czrq = czrq;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getZzbs() {
        return zzbs;
    }

    public void setZzbs(String zzbs) {
        this.zzbs = zzbs;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }
}
