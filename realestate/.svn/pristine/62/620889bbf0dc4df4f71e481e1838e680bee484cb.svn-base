package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-10-23
 * @description 水电气办理状态接口requestdto
 */
@Api(value = "BdcSdqBlztRequestDTO", description = "水电气办理状态接口dto")
public class BdcSdqBlztRequestDTO {

    @ApiModelProperty(value = "户号")
    private String consno;

    @ApiModelProperty(value = "业务类型")
    private Integer ywlx;

    @ApiModelProperty(value = "办理状态")
    private Integer blzt;

    @ApiModelProperty(value = "推送次数")
    private Integer tscs;

    @ApiModelProperty(value = "工作流")
    private String gzlslid;

    @ApiModelProperty(value = "申请时间",example = "2018-10-01 12:18:48")
    private Date sqsj;

    /**
     * 用于记录水电气审核意见（接口返回相关审核意见信息）
     */
    @ApiModelProperty(value = "水电气审核意见")
    private String sdqshyj;


    public String getConsno() {
        return consno;
    }

    public void setConsno(String consno) {
        this.consno = consno;
    }

    public Integer getYwlx() {
        return ywlx;
    }

    public void setYwlx(Integer ywlx) {
        this.ywlx = ywlx;
    }

    public Integer getBlzt() {
        return blzt;
    }

    public void setBlzt(Integer blzt) {
        this.blzt = blzt;
    }

    public Integer getTscs() {
        return tscs;
    }

    public void setTscs(Integer tscs) {
        this.tscs = tscs;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public Date getSqsj() {
        return sqsj;
    }

    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
    }

    public String getSdqshyj() {
        return sdqshyj;
    }

    public void setSdqshyj(String sdqshyj) {
        this.sdqshyj = sdqshyj;
    }

    @Override
    public String toString() {
        return "BdcSdqBlztRequestDTO{" +
                "consno='" + consno + '\'' +
                ", ywlx=" + ywlx +
                ", blzt=" + blzt +
                ", tscs=" + tscs +
                ", gzlslid='" + gzlslid + '\'' +
                ", sqsj='" + sqsj + '\'' +
                ", sdqshyj='" + sdqshyj + '\'' +
                '}';
    }
}
