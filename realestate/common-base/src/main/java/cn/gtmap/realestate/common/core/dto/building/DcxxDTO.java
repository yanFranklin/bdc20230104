package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-27
 * @description 房屋户室调查信息
 */
@ApiModel(value = "DcxxDTO", description = "房屋户室调查信息")
public class DcxxDTO {


    /**
     * 不动产单元房屋类型
     */
    @ApiModelProperty(value = "不动产单元房屋类型")
    private String bdcdyfwlx;

    /**
     * 不动产单元主键
     */
    @ApiModelProperty(value = "不动产单元主键")
    private String fwIndex;

    /**
     * 产权来源
     */
    @ApiModelProperty(value = "产权来源")
    private String cqly;

    /**
     * 共有情况
     */
    @ApiModelProperty(value = "共有情况")
    private String gyqk;

    /**
     * 附加说明
     */
    @ApiModelProperty(value = "附加说明")
    private String fjsm;

    /**
     * 调查意见
     */
    @ApiModelProperty(value = "调查意见")
    private String dcyj;

    /**
     * 调查者
     */
    @ApiModelProperty(value = "调查者")
    private String dcz;

    /**
     * 调查时间
     */
    @ApiModelProperty(value = "调查时间",example = "2018-10-01 12:18:21")
    private Date dcsj;

    public String getCqly() {
        return cqly;
    }

    public void setCqly(String cqly) {
        this.cqly = cqly;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getFjsm() {
        return fjsm;
    }

    public void setFjsm(String fjsm) {
        this.fjsm = fjsm;
    }

    public String getDcyj() {
        return dcyj;
    }

    public void setDcyj(String dcyj) {
        this.dcyj = dcyj;
    }

    public String getDcz() {
        return dcz;
    }

    public void setDcz(String dcz) {
        this.dcz = dcz;
    }

    public Date getDcsj() {
        return dcsj;
    }

    public void setDcsj(Date dcsj) {
        this.dcsj = dcsj;
    }

    public String getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(String bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    public String getFwIndex() {
        return fwIndex;
    }

    public void setFwIndex(String fwIndex) {
        this.fwIndex = fwIndex;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DcxxDTO{");
        sb.append("bdcdyfwlx='").append(bdcdyfwlx).append('\'');
        sb.append(", fwIndex='").append(fwIndex).append('\'');
        sb.append(", cqly='").append(cqly).append('\'');
        sb.append(", gyqk='").append(gyqk).append('\'');
        sb.append(", fjsm='").append(fjsm).append('\'');
        sb.append(", dcyj='").append(dcyj).append('\'');
        sb.append(", dcz='").append(dcz).append('\'');
        sb.append(", dcsj=").append(dcsj);
        sb.append('}');
        return sb.toString();
    }
}
