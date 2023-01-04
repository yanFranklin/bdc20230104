package cn.gtmap.realestate.common.core.domain.accept;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/27
 * @description 受理抵押信息
 */
@Table(name = "BDC_SL_DYAQ")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcSlDyaqDO.class)
@ApiModel(value = "BdcSlDyaqDO", description = "不动产受理抵押信息")
public class BdcSlDyaqDO implements BdcSlQl{

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "抵押方式")
    private Integer dyfs;

    @ApiModelProperty(value = "在建建筑物坐落")
    private String zjjzwzl;

    @ApiModelProperty(value = "在建建筑物抵押范围")
    private String zjjzwdyfw;

    @ApiModelProperty(value = "债务履行起始时间",example = "2018-10-01 12:18:48")
    private Date zwlxqssj;

    @ApiModelProperty(value = "债务履行结束时间",example = "2018-10-01 12:18:48")
    private Date zwlxjssj;

    @ApiModelProperty(value = "最高债权确定事实")
    private String zgzqqdss;

    @ApiModelProperty(value = "最高债权确定数额")
    private Double zgzqqdse;

    @ApiModelProperty(value = "抵押顺位")
    private Integer dysw;

    @ApiModelProperty(value = "贷款方式")
    private String dkfs;

    @ApiModelProperty(value = "被担保主债权数额")
    private Double bdbzzqse;

    @ApiModelProperty(value = "担保范围")
    private String dbfw;

    @ApiModelProperty(value = "房屋评估价格")
    private Double fwpgjg;

    @ApiModelProperty(value = "土地评估价格")
    private Double tdpgjg;

    @ApiModelProperty(value = "房屋抵押价格")
    private Double fwdyjg;

    @ApiModelProperty(value = "土地抵押价格")
    private Double tddyjg;

    @ApiModelProperty(value = "土地抵押面积")
    private Double tddymj;

    @ApiModelProperty(value = "房屋抵押面积")
    private Double fwdymj;

    @ApiModelProperty(value = "是否共同担保")
    private Integer sfgtdb;

    @ApiModelProperty(value = "抵押不动产类型")
    private Integer dybdclx;

    @ApiModelProperty(value = "债务人")
    private String zwr;

    @ApiModelProperty(value = "权利其他状况")
    private String qlqtzk;

    @ApiModelProperty(value = "附记")
    private String fj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getDyfs() {
        return dyfs;
    }

    public void setDyfs(Integer dyfs) {
        this.dyfs = dyfs;
    }

    public String getZjjzwzl() {
        return zjjzwzl;
    }

    public void setZjjzwzl(String zjjzwzl) {
        this.zjjzwzl = zjjzwzl;
    }

    public String getZjjzwdyfw() {
        return zjjzwdyfw;
    }

    public void setZjjzwdyfw(String zjjzwdyfw) {
        this.zjjzwdyfw = zjjzwdyfw;
    }

    public Date getZwlxqssj() {
        return zwlxqssj;
    }

    public void setZwlxqssj(Date zwlxqssj) {
        this.zwlxqssj = zwlxqssj;
    }

    public Date getZwlxjssj() {
        return zwlxjssj;
    }

    public void setZwlxjssj(Date zwlxjssj) {
        this.zwlxjssj = zwlxjssj;
    }

    public String getZgzqqdss() {
        return zgzqqdss;
    }

    public void setZgzqqdss(String zgzqqdss) {
        this.zgzqqdss = zgzqqdss;
    }

    public Double getZgzqqdse() {
        return zgzqqdse;
    }

    public void setZgzqqdse(Double zgzqqdse) {
        this.zgzqqdse = zgzqqdse;
    }

    public Integer getDysw() {
        return dysw;
    }

    public void setDysw(Integer dysw) {
        this.dysw = dysw;
    }

    public String getDkfs() {
        return dkfs;
    }

    public void setDkfs(String dkfs) {
        this.dkfs = dkfs;
    }

    public Double getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(Double bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

    public Double getFwpgjg() {
        return fwpgjg;
    }

    public void setFwpgjg(Double fwpgjg) {
        this.fwpgjg = fwpgjg;
    }

    public Double getTdpgjg() {
        return tdpgjg;
    }

    public void setTdpgjg(Double tdpgjg) {
        this.tdpgjg = tdpgjg;
    }

    public Double getFwdyjg() {
        return fwdyjg;
    }

    public void setFwdyjg(Double fwdyjg) {
        this.fwdyjg = fwdyjg;
    }

    public Double getTddyjg() {
        return tddyjg;
    }

    public void setTddyjg(Double tddyjg) {
        this.tddyjg = tddyjg;
    }

    public Double getTddymj() {
        return tddymj;
    }

    public void setTddymj(Double tddymj) {
        this.tddymj = tddymj;
    }

    public Double getFwdymj() {
        return fwdymj;
    }

    public void setFwdymj(Double fwdymj) {
        this.fwdymj = fwdymj;
    }

    public Integer getSfgtdb() {
        return sfgtdb;
    }

    public void setSfgtdb(Integer sfgtdb) {
        this.sfgtdb = sfgtdb;
    }

    public Integer getDybdclx() {
        return dybdclx;
    }

    public void setDybdclx(Integer dybdclx) {
        this.dybdclx = dybdclx;
    }

    public String getZwr() {
        return zwr;
    }

    public void setZwr(String zwr) {
        this.zwr = zwr;
    }

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }
}
