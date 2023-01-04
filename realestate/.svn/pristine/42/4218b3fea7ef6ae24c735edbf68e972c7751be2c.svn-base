package cn.gtmap.realestate.exchange.core.bo.accessLog;


import cn.gtmap.realestate.common.core.bo.accessnewlog.AccessInfo;
import cn.gtmap.realestate.common.core.bo.accessnewlog.RegisterInfo;
import cn.gtmap.realestate.exchange.util.jaxb.JaxbDateYMDAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0, 2018/12/20
 * @description 日志接入节点信息
 */
@XmlType(
        name = "accessLog",
        propOrder = {"areaCode", "areaName", "accessDate", "remark",
                "registerInfo", "accessInfo"}
)
@XmlRootElement(
        name = "AccessLog"
)
public class AccessLog {
    private String areaCode;
    private String areaName;
    private Date accessDate;
    private String remark;
    private RegisterInfo registerInfo;
    private AccessInfo accessInfo;

    public AccessLog() {
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
    public RegisterInfo getRegisterInfo() {
        return this.registerInfo;
    }

    public void setRegisterInfo(RegisterInfo registerInfo) {
        this.registerInfo = registerInfo;
    }

    @XmlElement(
            name = "AccessInfo"
    )
    public AccessInfo getAccessInfo() {
        return this.accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }
}