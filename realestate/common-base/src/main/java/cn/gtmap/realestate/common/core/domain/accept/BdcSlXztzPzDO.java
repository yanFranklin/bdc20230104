package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/1/30
 * @description 不动产受理选择台账配置
 */
@Table(name = "BDC_SL_XZTZ_PZ")
@ApiModel(value = "BdcSlXztzPzDO", description = "不动产受理选择台账配置")
public class BdcSlXztzPzDO implements Serializable {
    private static final long serialVersionUID = 2776476282367971126L;
    @Id
    @ApiModelProperty(value = "配置ID")
    private String pzid;
    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;
    @ApiModelProperty(value = "选择台账类型(1:不动产单元信息 2:产权证 3:查封 4:逻辑幢)")
    private String xztzlx;
    @ApiModelProperty(value = "权利类型")
    private String qllx;
    @ApiModelProperty(value = "宗地特征码")
    private String zdtzm;
    @ApiModelProperty(value = "定着物特征码")
    private String dzwtzm;
    @ApiModelProperty(value = "不动产单元房屋类型")
    private String bdcdyfwlx;
    @ApiModelProperty(value = "单元号查询类型（选择不动产单元号table使用）（1:土地及其定着物类型不动产单元信息  2:海域及其定作物类型不动产单元信息 3:构筑物不动产单元信息）")
    private Integer dyhcxlx;
    @ApiModelProperty(value = "不动产类型")
    private String bdclx;

    @ApiModelProperty(value = "证书类型")
    private Integer zslx;

    @ApiModelProperty(value = "预告登记种类,权利类型为预告时使用")
    private String ygdjzl;

    @ApiModelProperty(value = "查询条件字段")
    private String cxtj;

    @ApiModelProperty(value = "项目数据来源")
    private String xmsjly;

    @ApiModelProperty(value = "项目来源")
    private String xmly;

    @ApiModelProperty(value = "定着物用途")
    private String dzwyt;


    public BdcSlXztzPzDO() {
        super();
    }

    public BdcSlXztzPzDO(String pzid, String gzldyid, String xztzlx, String qllx, String zdtzm, String dzwtzm, String bdcdyfwlx, Integer dyhcxlx, String bdclx) {
        this.pzid = pzid;
        this.gzldyid = gzldyid;
        this.xztzlx = xztzlx;
        this.qllx = qllx;
        this.zdtzm = zdtzm;
        this.dzwtzm = dzwtzm;
        this.bdcdyfwlx = bdcdyfwlx;
        this.dyhcxlx = dyhcxlx;
        this.bdclx =bdclx;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPzid() {
        return pzid;
    }

    public void setPzid(String pzid) {
        this.pzid = pzid;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getXztzlx() {
        return xztzlx;
    }

    public void setXztzlx(String xztzlx) {
        this.xztzlx = xztzlx;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getZdtzm() {
        return zdtzm;
    }

    public void setZdtzm(String zdtzm) {
        this.zdtzm = zdtzm;
    }

    public String getDzwtzm() {
        return dzwtzm;
    }

    public void setDzwtzm(String dzwtzm) {
        this.dzwtzm = dzwtzm;
    }

    public String getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(String bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    public Integer getDyhcxlx() {
        return dyhcxlx;
    }

    public void setDyhcxlx(Integer dyhcxlx) {
        this.dyhcxlx = dyhcxlx;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(String ygdjzl) {
        this.ygdjzl = ygdjzl;
    }

    public String getCxtj() {
        return cxtj;
    }

    public void setCxtj(String cxtj) {
        this.cxtj = cxtj;
    }

    public String getXmsjly() {
        return xmsjly;
    }

    public void setXmsjly(String xmsjly) {
        this.xmsjly = xmsjly;
    }

    public String getXmly() {
        return xmly;
    }

    public void setXmly(String xmly) {
        this.xmly = xmly;
    }

    public String getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(String dzwyt) {
        this.dzwyt = dzwyt;
    }

    @Override
    public String toString() {
        return "BdcSlXztzPzDO{" +
                "pzid='" + pzid + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", xztzlx='" + xztzlx + '\'' +
                ", qllx='" + qllx + '\'' +
                ", zdtzm='" + zdtzm + '\'' +
                ", dzwtzm='" + dzwtzm + '\'' +
                ", bdcdyfwlx='" + bdcdyfwlx + '\'' +
                ", dyhcxlx=" + dyhcxlx +
                ", bdclx='" + bdclx + '\'' +
                ", zslx=" + zslx +
                ", ygdjzl='" + ygdjzl + '\'' +
                ", cxtj='" + cxtj + '\'' +
                ", xmsjly='" + xmsjly + '\'' +
                ", xmly='" + xmly + '\'' +
                ", dzwyt='" + dzwyt + '\'' +
                '}';
    }
}
