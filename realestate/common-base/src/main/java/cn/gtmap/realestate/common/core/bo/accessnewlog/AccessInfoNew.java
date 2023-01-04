package cn.gtmap.realestate.common.core.bo.accessnewlog;


import cn.gtmap.realestate.common.util.jaxb.JaxbIntegerAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
 * @version 1.0, 2018/12/20
 * @description 具体上报量节点信息
 */
@XmlType(
        name = "accessInfo",
        propOrder = {"totalNum", "firstReg", "transferReg", "changeReg", "logoutReg", "riviseReg",
                "dissentingReg", "advanceReg", "seizeReg", "otherReg", "businessTypeCount"}
)
@XmlRootElement(
        name = "AccessInfo"
)
public class AccessInfoNew {
    private Integer totalNum;
    private Integer firstReg;
    private Integer transferReg;
    private Integer changeReg;
    private Integer logoutReg;
    private Integer riviseReg;
    private Integer dissentingReg;
    private Integer advanceReg;
    private Integer seizeReg;
    private Integer otherReg;
    private Integer businessTypeCount;


    public AccessInfoNew() {
    }

    @XmlAttribute(
            name = "totalNum"
    )
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    @XmlAttribute(
            name = "firstReg"
    )
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getFirstReg() {
        return this.firstReg;
    }

    public void setFirstReg(Integer firstReg) {
        this.firstReg = firstReg;
    }

    @XmlAttribute(
            name = "transferReg"
    )
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getTransferReg() {
        return this.transferReg;
    }

    public void setTransferReg(Integer transferReg) {
        this.transferReg = transferReg;
    }

    @XmlAttribute(
            name = "changeReg"
    )
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getChangeReg() {
        return this.changeReg;
    }

    public void setChangeReg(Integer changeReg) {
        this.changeReg = changeReg;
    }

    @XmlAttribute(
            name = "logoutReg"
    )
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getLogoutReg() {
        return this.logoutReg;
    }

    public void setLogoutReg(Integer logoutReg) {
        this.logoutReg = logoutReg;
    }

    @XmlAttribute(
            name = "riviseReg"
    )
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getRiviseReg() {
        return this.riviseReg;
    }

    public void setRiviseReg(Integer riviseReg) {
        this.riviseReg = riviseReg;
    }

    @XmlAttribute(
            name = "dissentingReg"
    )
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getDissentingReg() {
        return this.dissentingReg;
    }

    public void setDissentingReg(Integer dissentingReg) {
        this.dissentingReg = dissentingReg;
    }

    @XmlAttribute(
            name = "advanceReg"
    )
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getAdvanceReg() {
        return this.advanceReg;
    }

    public void setAdvanceReg(Integer advanceReg) {
        this.advanceReg = advanceReg;
    }

    @XmlAttribute(
            name = "seizeReg"
    )
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getSeizeReg() {
        return this.seizeReg;
    }

    public void setSeizeReg(Integer seizeReg) {
        this.seizeReg = seizeReg;
    }


    @XmlAttribute(
            name = "otherReg"
    )
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getOtherReg() {
        return otherReg;
    }

    public void setOtherReg(Integer otherReg) {
        this.otherReg = otherReg;
    }


    @XmlAttribute(
            name = "businessTypeCount"
    )
    public Integer getBusinessTypeCount() {
        return businessTypeCount;
    }

    public void setBusinessTypeCount(Integer businessTypeCount) {
        this.businessTypeCount = businessTypeCount;
    }
}
