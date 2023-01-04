package cn.gtmap.realestate.common.core.qo.analysis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description
 */
@Api(value = "BdcHeadQO", description = "接口请求头")
public class BdcHeadQO {

    @ApiModelProperty("行政区代码")
    private String districtCode;

    @ApiModelProperty("查询人(代理人)姓名")
    private String clientUserName;

    @ApiModelProperty("查询人(代理人)证件号")
    private String clientUserZjh;

    @ApiModelProperty("查询目的或用途")
    private String cxmd;

    @ApiModelProperty("自助查询或打证机器编号")
    private String machineNO;

    @ApiModelProperty("自助查询或打证机器ip")
    private String machineIp;

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public String getClientUserZjh() {
        return clientUserZjh;
    }

    public void setClientUserZjh(String clientUserZjh) {
        this.clientUserZjh = clientUserZjh;
    }

    public String getCxmd() {
        return cxmd;
    }

    public void setCxmd(String cxmd) {
        this.cxmd = cxmd;
    }

    public String getMachineNO() {
        return machineNO;
    }

    public void setMachineNO(String machineNO) {
        this.machineNO = machineNO;
    }

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }
}
