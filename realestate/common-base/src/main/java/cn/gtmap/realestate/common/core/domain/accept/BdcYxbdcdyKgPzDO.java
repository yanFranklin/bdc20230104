package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/2/28
 * @description 不动产已选不动产单元开关配置
 */
@Table(name = "BDC_YXBDCDY_KG_PZ")
@ApiModel(value = "BdcYxbdcdyKgPzDO", description = "不动产已选不动产单元开关配置")
public class BdcYxbdcdyKgPzDO implements Serializable {
    private static final long serialVersionUID = -2458599167383431196L;
    @Id
    @ApiModelProperty(value = "配置ID")
    private String pzid;

    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;

    @ApiModelProperty(value = "是否显示权利人数据来源，1：显示")
    private Integer qlrsjly;

    @ApiModelProperty(value = "是否显示义务人数据来源，1：显示")
    private Integer ywrsjly;

    @ApiModelProperty(value = "是否显示是否生成权利，1：显示")
    private Integer sfscql;

    @ApiModelProperty(value = "是否显示权利数据来源，1：显示")
    private Integer qlsjly;

    @ApiModelProperty(value = "是否显示证书种类，1：显示")
    private Integer zszl;

    @ApiModelProperty(value = "证书数量")
    private Integer zssl;

    @ApiModelProperty(value = "证书数量默认值，为空生成多本证,1：生成一本证")
    private Integer zsslmrz;

    @ApiModelProperty(value = "是否显示是否增量初始化")
    private Integer sfzlcsh;

    @ApiModelProperty(value = "是否显示是否主房")
    private Integer sfzf;

    @ApiModelProperty(value = "是否显示是否换证")
    private Integer sfhz;

    @ApiModelProperty(value = "是否显示是否还原原注销权利")
    private Integer sfhyyzxql;

    @ApiModelProperty(value = "是否显示注销原权利")
    private Integer zxyql;

    @ApiModelProperty(value = "是否显示是否外联证书")
    private Integer sfwlzs;

    @ApiModelProperty(value = "是否显示权利类型")
    private Integer qllx;

    @ApiModelProperty(value = "是否显示是否注销")
    private Integer sfzx;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

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

    public Integer getQlrsjly() {
        return qlrsjly;
    }

    public void setQlrsjly(Integer qlrsjly) {
        this.qlrsjly = qlrsjly;
    }

    public Integer getYwrsjly() {
        return ywrsjly;
    }

    public void setYwrsjly(Integer ywrsjly) {
        this.ywrsjly = ywrsjly;
    }

    public Integer getSfscql() {
        return sfscql;
    }

    public void setSfscql(Integer sfscql) {
        this.sfscql = sfscql;
    }

    public Integer getQlsjly() {
        return qlsjly;
    }

    public void setQlsjly(Integer qlsjly) {
        this.qlsjly = qlsjly;
    }

    public Integer getZszl() {
        return zszl;
    }

    public void setZszl(Integer zszl) {
        this.zszl = zszl;
    }

    public Integer getZssl() {
        return zssl;
    }

    public void setZssl(Integer zssl) {
        this.zssl = zssl;
    }

    public Integer getZsslmrz() {
        return zsslmrz;
    }

    public void setZsslmrz(Integer zsslmrz) {
        this.zsslmrz = zsslmrz;
    }

    public Integer getSfzlcsh() {
        return sfzlcsh;
    }

    public void setSfzlcsh(Integer sfzlcsh) {
        this.sfzlcsh = sfzlcsh;
    }

    public Integer getSfzf() {
        return sfzf;
    }

    public void setSfzf(Integer sfzf) {
        this.sfzf = sfzf;
    }

    public Integer getSfhz() {
        return sfhz;
    }

    public void setSfhz(Integer sfhz) {
        this.sfhz = sfhz;
    }

    public Integer getSfhyyzxql() {
        return sfhyyzxql;
    }

    public void setSfhyyzxql(Integer sfhyyzxql) {
        this.sfhyyzxql = sfhyyzxql;
    }

    public Integer getZxyql() {
        return zxyql;
    }

    public void setZxyql(Integer zxyql) {
        this.zxyql = zxyql;
    }

    public Integer getSfwlzs() {
        return sfwlzs;
    }

    public void setSfwlzs(Integer sfwlzs) {
        this.sfwlzs = sfwlzs;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Integer getSfzx() {
        return sfzx;
    }

    public void setSfzx(Integer sfzx) {
        this.sfzx = sfzx;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    @Override
    public String toString() {
        return "BdcYxbdcdyKgPzDO{" +
                "pzid='" + pzid + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", qlrsjly=" + qlrsjly +
                ", ywrsjly=" + ywrsjly +
                ", sfscql=" + sfscql +
                ", qlsjly=" + qlsjly +
                ", zszl=" + zszl +
                ", zssl=" + zssl +
                ", zsslmrz=" + zsslmrz +
                ", sfzlcsh=" + sfzlcsh +
                ", sfzf=" + sfzf +
                ", sfhz=" + sfhz +
                ", sfhyyzxql=" + sfhyyzxql +
                ", zxyql=" + zxyql +
                ", sfwlzs=" + sfwlzs +
                ", qllx=" + qllx +
                ", sfzx=" + sfzx +
                ", djxl='" + djxl + '\'' +
                '}';
    }
}
