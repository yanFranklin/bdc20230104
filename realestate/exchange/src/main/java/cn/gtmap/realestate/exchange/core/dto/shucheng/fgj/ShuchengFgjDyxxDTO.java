package cn.gtmap.realestate.exchange.core.dto.shucheng.fgj;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.BdcZdDyfsDO;
import cn.gtmap.realestate.common.core.domain.BdcZdQsztDO;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author wangyinghao
 * @Date 2022/4/6
 * @description 舒城县房管局-一手房登记抵押数据DTO
 */
@ApiModel("舒城县房管局-一手房登记抵押数据DTO")
public class ShuchengFgjDyxxDTO {

    @ApiModelProperty("登记证明号")
    private String bdcdjzmh;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdybh;


    @ApiModelProperty(value = "产权证号")
    private String cqzh;

    @ApiModelProperty(value = "产权来源")
    private String cqly;

    @ApiModelProperty(value = "担保范围")
    private String dbfw;

    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date djsj;

    @Zd(tableClass = BdcZdDyfsDO.class)
    @ApiModelProperty(value = "抵押方式")
    private String dyfs;

    @ApiModelProperty(value = "最高债权确定数额")
    private Double dyje;

    @ApiModelProperty(value = "债务履行起始时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dykssj;

    @ApiModelProperty(value = "债务履行结束时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dyjssj;

    @ApiModelProperty(value = "抵押权人")
    private String dyqr;

    @ApiModelProperty(value = "抵押人")
    private String dyr;

    @ApiModelProperty(value = "附记")
    private String fj;

    @Zd(tableClass = BdcZdQsztDO.class)
    @ApiModelProperty(value = "权属状态")
    private String qszt;

    public String getBdcdjzmh() {
        return bdcdjzmh;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getCqly() {
        return cqly;
    }

    public void setCqly(String cqly) {
        this.cqly = cqly;
    }

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDyfs() {
        return dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public Double getDyje() {
        return dyje;
    }

    public void setDyje(Double dyje) {
        this.dyje = dyje;
    }

    public Date getDykssj() {
        return dykssj;
    }

    public void setDykssj(Date dykssj) {
        this.dykssj = dykssj;
    }

    public Date getDyjssj() {
        return dyjssj;
    }

    public void setDyjssj(Date dyjssj) {
        this.dyjssj = dyjssj;
    }

    public String getDyqr() {
        return dyqr;
    }

    public void setDyqr(String dyqr) {
        this.dyqr = dyqr;
    }

    public String getDyr() {
        return dyr;
    }

    public void setDyr(String dyr) {
        this.dyr = dyr;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }
}
