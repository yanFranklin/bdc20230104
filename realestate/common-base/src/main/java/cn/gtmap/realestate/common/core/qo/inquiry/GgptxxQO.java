package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @version 1.0, 2022/11/03
 * @description 获取工改平台信息
 */
public class GgptxxQO {

    @ApiModelProperty(value = "文件类型")
    private String appKey;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getShtyxydm() {
        return shtyxydm;
    }

    public void setShtyxydm(String shtyxydm) {
        this.shtyxydm = shtyxydm;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @ApiModelProperty(value = "项目名称")
    private String xmmc;



    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getXmwz() {
        return xmwz;
    }

    public void setXmwz(String xmwz) {
        this.xmwz = xmwz;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }



    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @ApiModelProperty(value = "项目位置")
    private String xmwz;

    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "单位社会统一信用代码")
    private String shtyxydm;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

}
