package cn.gtmap.realestate.exchange.core.dto.xuancehng.zzdz;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/12/21
 * @description
 */
public class BdcZmxxDTO {

    @ApiModelProperty(value = "业务编号")
    private String ywh;

    @ApiModelProperty(value = "不动产登记证明号")
    private String bdczmh;

    @ApiModelProperty(value = "证号省份简称")
    private String zmsf;

    @ApiModelProperty(value = "证号年份")
    private String zmnf;

    @ApiModelProperty(value = "证号区县")
    private String zmqx;

    @ApiModelProperty(value = "证明流水号")
    private String zmlsh;

    @ApiModelProperty(value = "证明权利类型")
    private String zmql;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "义务人")
    private String ywr;

    @ApiModelProperty(value = "不动产坐落")
    private String zl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权利其他信息")
    private String qt;

    @ApiModelProperty(value = "附记信息")
    private String fj;

    @ApiModelProperty(value = "登记时间")
    private String djsj;

    @ApiModelProperty(value = "登记时间年")
    private String djn;

    @ApiModelProperty(value = "登记时间月")
    private String djy;

    @ApiModelProperty(value = "登记时间日")
    private String djr;

    @ApiModelProperty(value = "证明id")
    private String zmId;

    @ApiModelProperty(value = "抵押物清单")
    private String dywqd;

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getBdczmh() {
        return bdczmh;
    }

    public void setBdczmh(String bdczmh) {
        this.bdczmh = bdczmh;
    }

    public String getZmsf() {
        return zmsf;
    }

    public void setZmsf(String zmsf) {
        this.zmsf = zmsf;
    }

    public String getZmnf() {
        return zmnf;
    }

    public void setZmnf(String zmnf) {
        this.zmnf = zmnf;
    }

    public String getZmqx() {
        return zmqx;
    }

    public void setZmqx(String zmqx) {
        this.zmqx = zmqx;
    }

    public String getZmlsh() {
        return zmlsh;
    }

    public void setZmlsh(String zmlsh) {
        this.zmlsh = zmlsh;
    }

    public String getZmql() {
        return zmql;
    }

    public void setZmql(String zmql) {
        this.zmql = zmql;
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

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public String getDjn() {
        return djn;
    }

    public void setDjn(String djn) {
        this.djn = djn;
    }

    public String getDjy() {
        return djy;
    }

    public void setDjy(String djy) {
        this.djy = djy;
    }

    public String getDjr() {
        return djr;
    }

    public void setDjr(String djr) {
        this.djr = djr;
    }

    public String getZmId() {
        return zmId;
    }

    public void setZmId(String zmId) {
        this.zmId = zmId;
    }

    public String getDywqd() {
        return dywqd;
    }

    public void setDywqd(String dywqd) {
        this.dywqd = dywqd;
    }
}
