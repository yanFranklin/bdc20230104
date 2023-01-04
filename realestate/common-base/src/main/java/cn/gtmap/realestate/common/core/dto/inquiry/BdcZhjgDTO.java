package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-05-22
 * @description 查询子系统：综合监管
 */
@Api(value = "BdcZhjgDTO", description = "不动产综合监管分页查询DTO")
public class BdcZhjgDTO {

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("义务人")
    private String ywr;

    @ApiModelProperty("所属区县")
    private String ssqx;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty("权利性质")
    private String qlxz;

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("宗地宗海用途")
    private String zdzhyt;

    @ApiModelProperty("宗地宗海面积（平方米）")
    private String zdzhmj;

    @ApiModelProperty("房屋建筑面积（平方米）")
    private String jzmj;

    @ApiModelProperty("规划用途")
    private String ghyt;

    @ApiModelProperty("交易价格")
    private String jyjg;

    @ApiModelProperty("不动产权证号")
    private String bdcqzh;

    @ApiModelProperty("发证时间")
    private String fzsj;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
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

    public String getSsqx() {
        return ssqx;
    }

    public void setSsqx(String ssqx) {
        this.ssqx = ssqx;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZdzhyt() {
        return zdzhyt;
    }

    public void setZdzhyt(String zdzhyt) {
        this.zdzhyt = zdzhyt;
    }

    public String getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(String zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getJyjg() {
        return jyjg;
    }

    public void setJyjg(String jyjg) {
        this.jyjg = jyjg;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getFzsj() {
        return fzsj;
    }

    public void setFzsj(String fzsj) {
        this.fzsj = fzsj;
    }
}
