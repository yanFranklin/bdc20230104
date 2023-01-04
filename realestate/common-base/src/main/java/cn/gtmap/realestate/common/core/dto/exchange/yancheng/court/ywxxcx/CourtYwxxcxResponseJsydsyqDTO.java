package cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class CourtYwxxcxResponseJsydsyqDTO {

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "用途")
    private String yt;

    @ApiModelProperty(value = "使用权面积")
    private String syqmj;

    @ApiModelProperty(value = "使用期限起 格式 yyyy-MM-dd")
    private String syqqssj;

    @ApiModelProperty(value = "使用期限止 格式 yyyy-MM-dd")
    private String syqjssj;

    @ApiModelProperty(value = "权利性质")
    private String qlxz;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "登记机构")
    private String djjg;

    @ApiModelProperty(value = "地区代码")
    private String dqdm;

    @ApiModelProperty(value = "业务号 项目ID")
    private String ywh;

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

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getSyqmj() {
        return syqmj;
    }

    public void setSyqmj(String syqmj) {
        this.syqmj = syqmj;
    }

    public String getSyqqssj() {
        return syqqssj;
    }

    public void setSyqqssj(String syqqssj) {
        this.syqqssj = syqqssj;
    }

    public String getSyqjssj() {
        return syqjssj;
    }

    public void setSyqjssj(String syqjssj) {
        this.syqjssj = syqjssj;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getDqdm() {
        return dqdm;
    }

    public void setDqdm(String dqdm) {
        this.dqdm = dqdm;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }
}
