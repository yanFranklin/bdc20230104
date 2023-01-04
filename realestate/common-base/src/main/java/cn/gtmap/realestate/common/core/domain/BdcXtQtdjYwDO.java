package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2018/12/4
 * @description  不动产与其他登记业务关系表
 */
@Table(name = "BDC_XT_QTDJ_YW")
public class BdcXtQtdjYwDO {
    @Id
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "流程定义id")
    private String gzldyid;
    @ApiModelProperty(value = "业务编码")
    private String ywbm;
    @ApiModelProperty(value = "业务编码名称")
    private String ywmc;
    @ApiModelProperty(value = "权利编码")
    private String qlbm;
    @ApiModelProperty(value = "权利编码名称")
    private String qlmc;
    @ApiModelProperty(value = "目录编码")
    private String mlbm;
    @ApiModelProperty(value = "法定办结时限")
    private Integer fdbjsx;
    @ApiModelProperty(value = "法定办结时限类型")
    private String fdbjsxlx;
    @ApiModelProperty(value = "承诺办结时间")
    private Integer promise;
    @ApiModelProperty(value = "承诺办结时间计量单位")
    private String promisetype;
    @ApiModelProperty(value = "办件类型")
    private String applytype;
    @ApiModelProperty(value = "事项类型")
    private String qlkind;
    @ApiModelProperty(value = "区县代码")
    private String xzdm;
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    /**
     * V3.1新增
     */
    @ApiModelProperty(value = "事项版本号")
    private String ssbbh;

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getYwbm() {
        return ywbm;
    }

    public void setYwbm(String ywbm) {
        this.ywbm = ywbm;
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc;
    }

    public String getQlbm() {
        return qlbm;
    }

    public void setQlbm(String qlbm) {
        this.qlbm = qlbm;
    }

    public String getQlmc() {
        return qlmc;
    }

    public void setQlmc(String qlmc) {
        this.qlmc = qlmc;
    }

    public Integer getFdbjsx() {
        return fdbjsx;
    }

    public void setFdbjsx(Integer fdbjsx) {
        this.fdbjsx = fdbjsx;
    }

    public String getXzdm() {
        return xzdm;
    }

    public void setXzdm(String xzdm) {
        this.xzdm = xzdm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getMlbm() {
        return mlbm;
    }

    public void setMlbm(String mlbm) {
        this.mlbm = mlbm;
    }

    public String getSsbbh() {
        return ssbbh;
    }

    public void setSsbbh(String ssbbh) {
        this.ssbbh = ssbbh;
    }

    public String getFdbjsxlx() {
        return fdbjsxlx;
    }

    public void setFdbjsxlx(String fdbjsxlx) {
        this.fdbjsxlx = fdbjsxlx;
    }

    public String getApplytype() {
        return applytype;
    }

    public void setApplytype(String applytype) {
        this.applytype = applytype;
    }

    public String getQlkind() {
        return qlkind;
    }

    public void setQlkind(String qlkind) {
        this.qlkind = qlkind;
    }

    public Integer getPromise() {
        return promise;
    }

    public void setPromise(Integer promise) {
        this.promise = promise;
    }

    public String getPromisetype() {
        return promisetype;
    }

    public void setPromisetype(String promisetype) {
        this.promisetype = promisetype;
    }

    @Override
    public String toString() {
        return "BdcXtQtdjYwDO{" +
                "id='" + id + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", ywbm='" + ywbm + '\'' +
                ", ywmc='" + ywmc + '\'' +
                ", qlbm='" + qlbm + '\'' +
                ", qlmc='" + qlmc + '\'' +
                ", mlbm='" + mlbm + '\'' +
                ", fdbjsx=" + fdbjsx +
                ", xzdm='" + xzdm + '\'' +
                '}';
    }
}
