package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.identitycheck;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/2 15:47
 * @description
 */
public class PoliceIdentityCheckReturnDTO {

    /**
     * 000	核查一致
     * 999	无匹配记录
     * 001~998	核查项不一致
     */
    @ApiModelProperty(value = "核验业务返回码")
    private String CheckCode;

    @ApiModelProperty(value = "核验信息")
    private String CheckCodeDesc;

    @ApiModelProperty(value = "核查源信息")
    private String CheckSourceInfos;

    @ApiModelProperty(value = "数据信息")
    private List<Object> DataInfo;

    public String getCheckCode() {
        return CheckCode;
    }

    public void setCheckCode(String checkCode) {
        CheckCode = checkCode;
    }

    public String getCheckCodeDesc() {
        return CheckCodeDesc;
    }

    public void setCheckCodeDesc(String checkCodeDesc) {
        CheckCodeDesc = checkCodeDesc;
    }

    public String getCheckSourceInfos() {
        return CheckSourceInfos;
    }

    public void setCheckSourceInfos(String checkSourceInfos) {
        CheckSourceInfos = checkSourceInfos;
    }

    public List<Object> getDataInfo() {
        return DataInfo;
    }

    public void setDataInfo(List<Object> dataInfo) {
        DataInfo = dataInfo;
    }

    @Override
    public String toString() {
        return "PoliceIdentityCheckReturnDTO{" +
                "CheckCode='" + CheckCode + '\'' +
                ", CheckCodeDesc='" + CheckCodeDesc + '\'' +
                ", CheckSourceInfos='" + CheckSourceInfos + '\'' +
                ", DataInfo=" + DataInfo +
                '}';
    }
}
