package cn.gtmap.realestate.common.core.dto.rules;/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/11/29 13:45
 * @description
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/11/29 13:45
 * @description 不动产规则验证数据信息
 */
@ApiModel(value = "BdcGzYzsjDTO", description = "不动产规则验证数据信息")
public class BdcGzYzsjDTO implements Serializable {
    private static final long serialVersionUID = -3938783218248873125L;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "房产档案号")
    private String fcdah;

    @ApiModelProperty(value = "地籍号")
    private String djh;

    @ApiModelProperty(value = "房屋调查表主键")
    private String fwDcbIndex;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getFcdah() {
        return fcdah;
    }

    public void setFcdah(String fcdah) {
        this.fcdah = fcdah;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    @Override
    public String toString() {
        return "BdcGzYzsjDTO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", fcdah='" + fcdah + '\'' +
                ", djh='" + djh + '\'' +
                ", fwDcbIndex='" + fwDcbIndex + '\'' +
                '}';
    }
}
