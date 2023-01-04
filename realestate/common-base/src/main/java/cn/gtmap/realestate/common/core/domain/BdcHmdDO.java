package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.StringJoiner;

/**
 * 不动产黑名单DO
 *
 * @author <a href="mailto:yaoyi@gtmap.cn>yaoyi</a>"
 * @version 1.0, 2021/10/25
 */
@Table(name = "BDC_HMD")
@ApiModel(value = "BdcHmdDO")
public class BdcHmdDO {

    @Id
    @ApiModelProperty(value = "黑名单ID")
    private String hmdid;
    @ApiModelProperty(value = "黑名单主体类别。1：人；2：物；3：证")
    private Integer hmdztlb;
    @ApiModelProperty(value = "创建时间")
    private Date cjsj;
    @ApiModelProperty(value = "创建人")
    private String cjr;
    @ApiModelProperty(value = "创建原因")
    private String cjyy;
    @ApiModelProperty(value = "黑名单状态。1：现势，0：历史")
    private Integer hmdzt;
    @ApiModelProperty(value = "主体证件号")
    private String ztzjh;
    @ApiModelProperty(value = "主体名称")
    private String ztmc;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "工作流定义名称")
    private String gzldymc;
    @ApiModelProperty(value="解锁人")
    private String jsr;
    @ApiModelProperty(value="解锁时间")
    private Date jssj;
    @ApiModelProperty(value="解锁原因")
    private String jsyy;
    @ApiModelProperty(value="锁定类型")
    private Integer sdlx;

    public String getHmdid() {
        return hmdid;
    }

    public void setHmdid(String hmdid) {
        this.hmdid = hmdid;
    }

    public Integer getHmdztlb() {
        return hmdztlb;
    }

    public void setHmdztlb(Integer hmdztlb) {
        this.hmdztlb = hmdztlb;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public String getCjyy() {
        return cjyy;
    }

    public void setCjyy(String cjyy) {
        this.cjyy = cjyy;
    }

    public Integer getHmdzt() {
        return hmdzt;
    }

    public void setHmdzt(Integer hmdzt) {
        this.hmdzt = hmdzt;
    }

    public String getZtzjh() {
        return ztzjh;
    }

    public void setZtzjh(String ztzjh) {
        this.ztzjh = ztzjh;
    }

    public String getZtmc() {
        return ztmc;
    }

    public void setZtmc(String ztmc) {
        this.ztmc = ztmc;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public String getJsr() {
        return jsr;
    }

    public void setJsr(String jsr) {
        this.jsr = jsr;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getJsyy() {
        return jsyy;
    }

    public void setJsyy(String jsyy) {
        this.jsyy = jsyy;
    }

    public Integer getSdlx() {
        return sdlx;
    }

    public void setSdlx(Integer sdlx) {
        this.sdlx = sdlx;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcHmdDO.class.getSimpleName() + "[", "]")
                .add("hmdid='" + hmdid + "'")
                .add("hmdztlb=" + hmdztlb)
                .add("cjsj=" + cjsj)
                .add("cjr='" + cjr + "'")
                .add("cjyy='" + cjyy + "'")
                .add("hmdzt=" + hmdzt)
                .add("ztzjh='" + ztzjh + "'")
                .add("ztmc='" + ztmc + "'")
                .add("bdcdyh='" + bdcdyh + "'")
                .add("bdcqzh='" + bdcqzh + "'")
                .add("bz='" + bz + "'")
                .add("gzldymc='" + gzldymc + "'")
                .add("jsr='" + jsr + "'")
                .add("jssj=" + jssj)
                .add("jsyy='" + jsyy + "'")
                .add("sdlx=" + sdlx)
                .toString();
    }
}
