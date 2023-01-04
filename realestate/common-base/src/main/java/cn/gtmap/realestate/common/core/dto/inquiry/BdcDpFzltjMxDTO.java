package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.BdcZdZslxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/06/28/15:57
 * @Description:
 */
@ApiModel(value = "BdcDpFzltjMxDTO", description = "不动产大屏发证量统计明细DTO")
public class BdcDpFzltjMxDTO {
    /**
     * 不动产权证号
     */
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    /**
     * 发证人
     */
    @ApiModelProperty(value = "发证人", hidden = true)
    private String fzr;

    /**
     * 发证时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发证时间", hidden = true, example = "2018-10-01 12:18:48")
    private Date fzsj;
    /**
     * 领证人
     */
    @ApiModelProperty(value = "领证人")
    private String lzr;

    /**
     * 领证时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "领证时间", hidden = true, example = "2018-10-01 12:18:48")
    private Date lzsj;

    /**
     * 不动产权证号简称
     */
    @ApiModelProperty(value = "不动产权证号简称", hidden = true)
    private String bdcqzhjc;
    /**
     * 证书类型
     */
    @ApiModelProperty(value = "证书类型")
    private Integer zslx;

    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;

    /**
     * 权利人
     */
    @ApiModelProperty(value = "权利人")
    private String qlr;
    /**
     * 义务人
     */
    @ApiModelProperty(value = "义务人")
    private String ywr;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量")
    private String sl;

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
    }

    public Date getFzsj() {
        return fzsj;
    }

    public void setFzsj(Date fzsj) {
        this.fzsj = fzsj;
    }

    public String getLzr() {
        return lzr;
    }

    public void setLzr(String lzr) {
        this.lzr = lzr;
    }

    public Date getLzsj() {
        return lzsj;
    }

    public void setLzsj(Date lzsj) {
        this.lzsj = lzsj;
    }

    public String getBdcqzhjc() {
        return bdcqzhjc;
    }

    public void setBdcqzhjc(String bdcqzhjc) {
        this.bdcqzhjc = bdcqzhjc;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    @Override
    public String toString() {
        return "BdcDpFzltjMxDTO{" +
                "bdcqzh='" + bdcqzh + '\'' +
                ", fzr='" + fzr + '\'' +
                ", fzsj=" + fzsj +
                ", lzr='" + lzr + '\'' +
                ", lzsj=" + lzsj +
                ", bdcqzhjc='" + bdcqzhjc + '\'' +
                ", zslx=" + zslx +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", qlr='" + qlr + '\'' +
                ", ywr='" + ywr + '\'' +
                ", sl='" + sl + '\'' +
                '}';
    }
}
