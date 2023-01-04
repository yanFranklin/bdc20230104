package cn.gtmap.realestate.exchange.core.dto.common;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.BdcZdBdclxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDjlxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDjxlDO;
import cn.gtmap.realestate.common.core.domain.BdcZdQllxDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

public class BdcXmForZxAccessDTO {

    @ApiModelProperty(value = "项目id")
    private String xmid;
    @Zd(tableClass = BdcZdQllxDO.class)
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    @Zd(tableClass = BdcZdDjlxDO.class)
    @ApiModelProperty(value = "登记类型")
    private Integer djlx;
    @Zd(tableClass = BdcZdDjxlDO.class)
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @Zd(tableClass = BdcZdBdclxDO.class)
    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;
    @ApiModelProperty(value = "原产权证号")
    private String bdcqzh;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "原项目id")
    private String yxmid;
    @ApiModelProperty(value = "原不动产单元号")
    private String ybdcdyh;

    @ApiModelProperty(value = "当前项目的登记时间")
    private Date xmDjsj;

    @ApiModelProperty(value = "项目历史关系的主键--用来作为外联项目接入的业务流水号")
    private String gxid;

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    public String getYbdcdyh() {
        return ybdcdyh;
    }

    public void setYbdcdyh(String ybdcdyh) {
        this.ybdcdyh = ybdcdyh;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Date getXmDjsj() {
        return xmDjsj;
    }

    public void setXmDjsj(Date xmDjsj) {
        this.xmDjsj = xmDjsj;
    }

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    /**
     * 外联注销项目去重逻辑，当xmid bdcdyh qllx相同是排除
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BdcXmForZxAccessDTO that = (BdcXmForZxAccessDTO) o;
        return xmid.equals(that.xmid) &&
                qllx.equals(that.qllx) &&
                bdcdyh.equals(that.bdcdyh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xmid);
    }
}
