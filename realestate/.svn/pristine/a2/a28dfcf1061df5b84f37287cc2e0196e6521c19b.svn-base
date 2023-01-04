package cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 坐标定位签章
 * @author wangyinghao
 */
public class GmxaDzzzPositionSignReq {
    /**
     * 时间值。取当前时间的毫秒数。
     */
    @ApiModelProperty("时间值。取当前时间的毫秒数。")
    Long timestamp;

    /**
     * 用章人
     */
    @ApiModelProperty("用章人")
    String outUserId;

    /**
     *
     * 待签章文件。将文件读取为byte数组后使用BASE64编码为String。
     */
    @ApiModelProperty("待签章文件。将文件读取为byte数组后使用BASE64编码为String。")
    String file;

    /**
     * 业务系统id
     */
    @ApiModelProperty("业务系统id")
    String siteId;

    /**
     * 文件上传到电子印章后返回的路径，此参数与file两选其一
     */
    @ApiModelProperty("文件上传到电子印章后返回的路径，此参数与file两选其一")
    String documentPath;

    /**
     * 单位 1
     */
    @ApiModelProperty("单位")
    String positionUnitType;

    /**
     * 该签署用户在签约模板中的编号(从1开始计数)。获取sigerIndex请使用导出模板签署用户签署控件列表接口。
     */
    @ApiModelProperty("签章信息")
    List<GmxaDzzzSignatories> signatoriesList;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(String outUserId) {
        this.outUserId = outUserId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getPositionUnitType() {
        return positionUnitType;
    }

    public void setPositionUnitType(String positionUnitType) {
        this.positionUnitType = positionUnitType;
    }

    public List<GmxaDzzzSignatories> getSignatoriesList() {
        return signatoriesList;
    }

    public void setSignatoriesList(List<GmxaDzzzSignatories> signatoriesList) {
        this.signatoriesList = signatoriesList;
    }
}
