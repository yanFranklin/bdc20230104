package cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 齐缝章
 *
 * @author wangyinghao
 */
public class GmxaDzzzStamSign{
    /**
     * 时间值。取当前时间的毫秒数。
     */
    @ApiModelProperty("时间值。取当前时间的毫秒数。")
    Long timestamp;

    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    String documentName;

    /**
     * 文件系统地址的相对路径
     */
    @ApiModelProperty("文件系统地址的相对路径")
    String documentPath;

    /**
     * 业务系统id
     */
    @ApiModelProperty("业务系统id")
    String siteId;

    /**
     * 用章人
     */
    @ApiModelProperty("用章人")
    String outUserId;

    /**
     * 文档设置的标题
     */
    @ApiModelProperty("文档设置的标题")
    String title;

    /**
     *
     */
    @ApiModelProperty("签章信息")
    List<GmxaDzzzSignatories> signatoriesList;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(String outUserId) {
        this.outUserId = outUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GmxaDzzzSignatories> getSignatoriesList() {
        return signatoriesList;
    }

    public void setSignatoriesList(List<GmxaDzzzSignatories> signatoriesList) {
        this.signatoriesList = signatoriesList;
    }
}
