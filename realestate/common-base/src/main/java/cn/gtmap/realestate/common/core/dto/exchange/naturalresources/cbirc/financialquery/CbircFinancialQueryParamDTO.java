package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.cbirc.financialquery;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/3 10:55
 */
public class CbircFinancialQueryParamDTO {

    @ApiModelProperty(value = "查询类型 字典项 默认01")
    private String typeId;

    @ApiModelProperty(value = "机构编码")
    private String certCode;

    public String getTypeId() {
        if(null == typeId || "".equals(typeId)){
            return "01";
        }
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    @Override
    public String toString() {
        return "CbircFinancialQueryParamDTO{" +
                "typeId='" + typeId + '\'' +
                ", certCode='" + certCode + '\'' +
                '}';
    }
}
