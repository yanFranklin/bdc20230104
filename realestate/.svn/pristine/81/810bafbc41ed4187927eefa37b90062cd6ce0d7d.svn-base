package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.civil.marriagequery;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/3 10:04
 */
public class CivilMarriageQueryDataDTO {

    @ApiModelProperty(value = "男方出生日期")
    private String birthMan;

    @ApiModelProperty(value = "女方出生日期")
    private String birthWoman;

    @ApiModelProperty(value = "结婚证/离婚证字号")
    private String certNo;

    @ApiModelProperty(value = "男方身份证件号")
    private String certNumMan;

    @ApiModelProperty(value = "女方身份证件号")
    private String certNumWoman;

    @ApiModelProperty(value = "登记机关代码")
    private String deptCode;

    @ApiModelProperty(value = "登记机关名称")
    private String deptName;

    @ApiModelProperty(value = "男方姓名")
    private String nameMan;

    @ApiModelProperty(value = "女方姓名")
    private String nameWoman;

    @ApiModelProperty(value = "男方国籍")
    private String nationMan;

    @ApiModelProperty(value = "女方国籍")
    private String nationWoman;

    @ApiModelProperty(value = "登记日期")
    private String opDate;

    @ApiModelProperty(value = "婚姻登记业务类型")
    private String opType;

    @ApiModelProperty(value = "婚姻登记业务类型中位值")
    private String opTypeText;

    @ApiModelProperty(value = "男方国籍中文")
    private String nationManText;

    @ApiModelProperty(value = "女方国籍中文")
    private String nationWomanText;

    public String getBirthMan() {
        return birthMan;
    }

    @JSONField(name="birth_man")
    public void setBirthMan(String birthMan) {
        this.birthMan = birthMan;
    }

    public String getBirthWoman() {
        return birthWoman;
    }

    @JSONField(name="birth_woman")
    public void setBirthWoman(String birthWoman) {
        this.birthWoman = birthWoman;
    }

    public String getCertNo() {
        return certNo;
    }

    @JSONField(name="cert_no")
    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertNumMan() {
        return certNumMan;
    }

    @JSONField(name="cert_num_man")
    public void setCertNumMan(String certNumMan) {
        this.certNumMan = certNumMan;
    }

    public String getCertNumWoman() {
        return certNumWoman;
    }

    @JSONField(name="cert_num_woman")
    public void setCertNumWoman(String certNumWoman) {
        this.certNumWoman = certNumWoman;
    }

    public String getDeptCode() {
        return deptCode;
    }

    @JSONField(name="dept_code")
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    @JSONField(name="dept_name")
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getNameMan() {
        return nameMan;
    }

    @JSONField(name="name_man")
    public void setNameMan(String nameMan) {
        this.nameMan = nameMan;
    }

    public String getNameWoman() {
        return nameWoman;
    }

    @JSONField(name="name_woman")
    public void setNameWoman(String nameWoman) {
        this.nameWoman = nameWoman;
    }

    public String getNationMan() {
        return nationMan;
    }

    @JSONField(name="nation_man")
    public void setNationMan(String nationMan) {
        this.nationMan = nationMan;
    }

    public String getNationWoman() {
        return nationWoman;
    }

    @JSONField(name="nation_woman")
    public void setNationWoman(String nationWoman) {
        this.nationWoman = nationWoman;
    }

    public String getOpDate() {
        return opDate;
    }

    @JSONField(name="op_date")
    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getOpType() {
        return opType;
    }

    @JSONField(name="op_type")
    public void setOpType(String opType) {
        this.opType = opType;
    }


    public String getOpTypeText() {
        ;
        if(this.opType.equals("IA")){
            return "结婚登记";
        }
        if(this.opType.equals("IB")){
            return  "离婚登记";
        }
        if(this.opType.equals("IC")){
            return  "补领婚姻登记证";
        }
        if(this.opType.equals("ID")){
            return  "撤销婚姻";
        }
        if(this.opType.equals("ICA")){
            return  "补发结婚登记证";
        }
        if(this.opType.equals("ICB")){
            return  "补发离婚登记证";
        }
        return "";
    }

    @JSONField(name="opTypeText")
    public void setOpTypeText(String opTypeText) {
        if(this.opType.equals("IA")){
            this.opTypeText = "结婚登记";
        }
        if(this.opType.equals("IB")){
            this.opTypeText = "离婚登记";
        }
        if(this.opType.equals("IC")){
            this.opTypeText = "补领婚姻登记证";
        }
        if(this.opType.equals("ID")){
            this.opTypeText = "撤销婚姻";
        }
        if(this.opType.equals("ICA")){
            this.opTypeText = "补发结婚登记证";
        }
        if(this.opType.equals("ICB")){
            this.opTypeText = "补发离婚登记证";
        }
        this.opTypeText = "";
    }

    public String getNationManText() {
        return nationManText;
    }

    public void setNationManText(String nationManText) {
        this.nationManText = nationManText;
    }

    public String getNationWomanText() {
        return nationWomanText;
    }

    public void setNationWomanText(String nationWomanText) {
        this.nationWomanText = nationWomanText;
    }

    @Override
    public String toString() {
        return "CivilMarriageQueryDataDTO{" +
                "birthMan='" + birthMan + '\'' +
                ", birthWoman='" + birthWoman + '\'' +
                ", certNo='" + certNo + '\'' +
                ", certNumMan='" + certNumMan + '\'' +
                ", certNumWoman='" + certNumWoman + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", deptName='" + deptName + '\'' +
                ", nameMan='" + nameMan + '\'' +
                ", nameWoman='" + nameWoman + '\'' +
                ", nationMan='" + nationMan + '\'' +
                ", nationWoman='" + nationWoman + '\'' +
                ", opDate='" + opDate + '\'' +
                ", opType='" + opType + '\'' +
                ", opTypeText='" + opTypeText + '\'' +
                ", nationManText='" + nationManText + '\'' +
                ", nationWomanText='" + nationWomanText + '\'' +
                '}';
    }
}
