package cn.gtmap.realestate.exchange.core.domain.exchange.old;

import cn.gtmap.realestate.exchange.util.jaxb.JaxbDateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by zdd on 2015/11/19.
 */
@XmlRootElement(name = "Head")
public class HeadModel {

    private String bizMsgID;//业务报文ID
    private String ASID;//接入标准ID
    private String areaCode;//行政区划编码
    private String recType;//业务编码
    private String rightType;//权利类型
    private String regType;//登记类型
    private Date createDate;//创建时间
    private String recFlowID;//业务流水号
    private String regOrgID;//登记机构
    private String parcelID;//宗地/宗海代码
    private String estateNum;//不动产单元号
    private String digitalSign;//数字签名
    private String preEstateNum;//上次不动产单元号
    private String certCount;//不动产权证号码数量
    private String proofCount;//证明号码数量
    private String preCertID;//上次不动产权证号/不动产登记证明号

    @XmlElement(name = "BizMsgID")
    public String getBizMsgID() {
        return bizMsgID;
    }

    public void setBizMsgID(String bizMsgID) {
        this.bizMsgID = bizMsgID;
    }

    @XmlElement(name = "ASID")
    public String getASID() {
        return ASID;
    }

    public void setASID(String ASID) {
        this.ASID = ASID;
    }

    @XmlElement(name = "AreaCode")
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @XmlElement(name = "RecType")
    public String getRecType() {
        return recType;
    }

    public void setRecType(String recType) {
        this.recType = recType;
    }

    @XmlElement(name = "RightType")
    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    @XmlElement(name = "RegType")
    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    @XmlElement(name = "CreateDate")
    @XmlJavaTypeAdapter(JaxbDateAdapter.class)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @XmlElement(name = "RecFlowID")
    public String getRecFlowID() {
        return recFlowID;
    }

    public void setRecFlowID(String recFlowID) {
        this.recFlowID = recFlowID;
    }

    @XmlElement(name = "RegOrgID")
    public String getRegOrgID() {
        return regOrgID;
    }

    public void setRegOrgID(String regOrgID) {
        this.regOrgID = regOrgID;
    }

    @XmlElement(name = "ParcelID")
    public String getParcelID() {
        return parcelID;
    }

    public void setParcelID(String parcelID) {
        this.parcelID = parcelID;
    }

    @XmlElement(name = "EstateNum")
    public String getEstateNum() {
        return estateNum;
    }

    public void setEstateNum(String estateNum) {
        this.estateNum = estateNum;
    }

    @XmlElement(name = "DigitalSign")
    public String getDigitalSign() {
        return digitalSign;
    }

    public void setDigitalSign(String digitalSign) {
        this.digitalSign = digitalSign;
    }

    @XmlElement(name = "PreEstateNum")
    public String getPreEstateNum() {
        return preEstateNum;
    }

    public void setPreEstateNum(String preEstateNum) {
        this.preEstateNum = preEstateNum;
    }

    @XmlElement(name = "CertCount")
    public String getCertCount() {
        return certCount;
    }

    public void setCertCount(String certCount) {
        this.certCount = certCount;
    }

    @XmlElement(name = "ProofCount")
    public String getProofCount() {
        return proofCount;
    }

    public void setProofCount(String proofCount) {
        this.proofCount = proofCount;
    }

    @XmlElement(name = "PreCertID")
    public String getPreCertID() {
        return preCertID;
    }

    public void setPreCertID(String preCertID) {
        this.preCertID = preCertID;
    }

    @Override
    public String toString() {
        return "HeadModel{" +
                "bizMsgID='" + bizMsgID + '\'' +
                ", ASID='" + ASID + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", recType='" + recType + '\'' +
                ", rightType='" + rightType + '\'' +
                ", regType='" + regType + '\'' +
                ", createDate=" + createDate +
                ", recFlowID='" + recFlowID + '\'' +
                ", regOrgID='" + regOrgID + '\'' +
                ", parcelID='" + parcelID + '\'' +
                ", estateNum='" + estateNum + '\'' +
                ", digitalSign='" + digitalSign + '\'' +
                ", preEstateNum='" + preEstateNum + '\'' +
                ", certCount='" + certCount + '\'' +
                ", proofCount='" + proofCount + '\'' +
                ", preCertID='" + preCertID + '\'' +
                '}';
    }
}
