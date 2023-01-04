package cn.gtmap.realestate.common.core.bo.accessnewlog;


import cn.gtmap.realestate.common.util.jaxb.JaxbDateYMDAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2018/12/20
 * @description 日志接入节点信息
 */
@XmlType(
        name = "accessLog",
        propOrder = {"areaCode", "areaName", "accessDate", "remark", "registerInfo",
                "accessInfo", "registerList", "accessList"}
)
@XmlRootElement(
        name = "AccessLog"
)
public class AccessNewLog {
    private String areaCode;
    private String areaName;
    private Date accessDate;
    private String remark;
    private RegisterInfoNew registerInfo;
    private AccessInfoNew accessInfo;
    private RegisterList registerList;
    private AccessList accessList;

    public AccessNewLog() {
    }

    @XmlElement(
            name = "AreaCode",
            nillable = true,
            required = true
    )
    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @XmlElement(
            name = "AreaName",
            nillable = true,
            required = true
    )
    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @XmlElement(
            name = "AccessDate",
            nillable = true,
            required = true
    )
    @XmlJavaTypeAdapter(JaxbDateYMDAdapter.class)
    public Date getAccessDate() {
        return this.accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    @XmlElement(
            name = "Remark",
            nillable = true,
            required = true
    )
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlElement(
            name = "RegisterInfo"
    )
    public RegisterInfoNew getRegisterInfo() {
        return this.registerInfo;
    }

    public void setRegisterInfo(RegisterInfoNew registerInfo) {
        this.registerInfo = registerInfo;
    }

    @XmlElement(
            name = "AccessInfo"
    )
    public AccessInfoNew getAccessInfo() {
        return this.accessInfo;
    }

    public void setAccessInfo(AccessInfoNew accessInfo) {
        this.accessInfo = accessInfo;
    }

    @XmlElement(
            name = "RegisterList"
    )
    public RegisterList getRegisterList() {
        return registerList;
    }

    public void setRegisterList(RegisterList registerList) {
        this.registerList = registerList;
    }

    @XmlElement(
            name = "AccessList"
    )
    public AccessList getAccessList() {
        return accessList;
    }

    public void setAccessList(AccessList accessList) {
        this.accessList = accessList;
    }
}