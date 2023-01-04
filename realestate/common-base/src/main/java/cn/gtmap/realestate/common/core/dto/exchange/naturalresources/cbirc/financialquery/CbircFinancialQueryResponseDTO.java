package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.cbirc.financialquery;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/3 14:29
 * @description 2.5银保监会-金融许可证查询接口
 */
public class CbircFinancialQueryResponseDTO {

    @ApiModelProperty(value = "机构地址")
    private String address;

    @ApiModelProperty(value = "机构编码")
    private String certCode;

    @ApiModelProperty(value = "流水号")
    private String certFlowNo;

    @ApiModelProperty(value = "英文名称")
    private String englishName;

    @ApiModelProperty(value = "机构全称")
    private String fullName;

    @ApiModelProperty(value = "金融许可证id")
    private String id;

    @ApiModelProperty(value = "监管机构名称")
    private String organName;

    @ApiModelProperty(value = "打印日期")
    private String printDate;

    @ApiModelProperty(value = "经营范围")
    private String scope;

    @ApiModelProperty(value = "设立日期")
    private String setDate;

    @ApiModelProperty(value = "机构简称")
    private String simpleName;

    @ApiModelProperty(value = "查询类型")
    private String typeId;

    @ApiModelProperty(value = "查询类型中文")
    private String typeIdText;

    @ApiModelProperty(value = "法人名称")
    private String frmc;

    public String getAddress() {
        return address;
    }

    @JSONField(name = "address")
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCertCode() {
        return certCode;
    }

    @JSONField(name = "certCode")
    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getCertFlowNo() {
        return certFlowNo;
    }

    @JSONField(name = "certFlowNo")
    public void setCertFlowNo(String certFlowNo) {
        this.certFlowNo = certFlowNo;
    }

    public String getEnglishName() {
        return englishName;
    }

    @JSONField(name = "englishName")
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getFullName() {
        return fullName;
    }

    @JSONField(name = "fullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    @JSONField(name = "id")
    public void setId(String id) {
        this.id = id;
    }

    public String getOrganName() {
        return organName;
    }

    @JSONField(name = "organName")
    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getPrintDate() {
        return printDate;
    }

    @JSONField(name = "printDate")
    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getScope() {
        return scope;
    }

    @JSONField(name = "scope")
    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSetDate() {
        return setDate;
    }

    @JSONField(name = "setDate")
    public void setSetDate(String setDate) {
        this.setDate = setDate;
    }

    public String getSimpleName() {
        return simpleName;
    }

    @JSONField(name = "simpleName")
    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getTypeId() {
        return typeId;
    }

    @JSONField(name = "typeId")
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeIdText() {
        return typeIdText;
    }

    public void setTypeIdText(String typeIdText) {
        this.typeIdText = typeIdText;
    }

    public String getFrmc() {
        return frmc;
    }

    public void setFrmc(String frmc) {
        this.frmc = frmc;
    }

    @Override
    public String toString() {
        return "CbircFinancialQueryResponseDTO{" +
                "address='" + address + '\'' +
                ", certCode='" + certCode + '\'' +
                ", certFlowNo='" + certFlowNo + '\'' +
                ", englishName='" + englishName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", id='" + id + '\'' +
                ", organName='" + organName + '\'' +
                ", printDate='" + printDate + '\'' +
                ", scope='" + scope + '\'' +
                ", setDate='" + setDate + '\'' +
                ", simpleName='" + simpleName + '\'' +
                ", typeId='" + typeId + '\'' +
                ", typeIdText='" + typeIdText + '\'' +
                ", frmc='" + frmc + '\'' +
                '}';
    }
}
