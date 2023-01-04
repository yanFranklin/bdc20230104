package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.identitycheck;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/2 09:45
 * @description 公安部-身份核查服务
 */
public class PoliceIdentityCheckRequestDTO {

    @ApiModelProperty(value = "调用接口使用的应用名称")
    private String appname;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    private List<PoliceIdentityCheckParamDTO> paramDTOList;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<PoliceIdentityCheckParamDTO> getParamDTOList() {
        return paramDTOList;
    }

    public void setParamDTOList(List<PoliceIdentityCheckParamDTO> paramDTOList) {
        this.paramDTOList = paramDTOList;
    }

    @Override
    public String toString() {
        return "PoliceIdentityCheckRequestDTO{" +
                "appname='" + appname + '\'' +
                ", slbh='" + slbh + '\'' +
                ", paramDTOList=" + paramDTOList +
                '}';
    }
}

