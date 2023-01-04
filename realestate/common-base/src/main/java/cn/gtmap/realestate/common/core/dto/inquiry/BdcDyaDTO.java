package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/9/3 10:35
 * @description
 */
@Api(value = "BdcDyaDTO", description = "不动产抵押信息分页查询")
public class BdcDyaDTO {

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty(value = "权属状态")
    private String qszt;

    @ApiModelProperty("权利id")
    private String qlid;

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("权利人证件号")
    private String qlrzjh;

    @ApiModelProperty("不动产权证号")
    private String bdcqzh;

    @ApiModelProperty("原系统产权证号")
    private String yxtcqzh;

    @ApiModelProperty("义务人")
    private String ywr;

    @ApiModelProperty("义务人证件号")
    private String ywrzjh;

    @ApiModelProperty("登记原因")
    private String djyy;

    @ApiModelProperty("登记机构")
    private String djjg;

    @ApiModelProperty(value = "登记时间", example = "2018-10-01 12:18:48")
    private Date djsj;

    @ApiModelProperty("登簿人")
    private String dbr;

    @ApiModelProperty("原产权证号")
    private String ycqzh;

    @ApiModelProperty("债务履行起始时间")
    private String zwlxqssj;

    @ApiModelProperty(value = "债务履行结束时间")
    private String zwlxjssj;

    @ApiModelProperty("被担保主债权数额")
    private String bdbzzqse;

    @ApiModelProperty(value = "抵押方式")
    private Integer dyfs;

    @ApiModelProperty(value = "状态信息")
    private BdcdyhZtResponseDTO bdcdyZtDTO;

    @ApiModelProperty(value = "权利类型")
    private Integer qllx;

    public String getYxtcqzh() {
        return yxtcqzh;
    }

    public void setYxtcqzh(String yxtcqzh) {
        this.yxtcqzh = yxtcqzh;
    }


    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

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

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public BdcdyhZtResponseDTO getBdcdyZtDTO() {
        return bdcdyZtDTO;
    }

    public void setBdcdyZtDTO(BdcdyhZtResponseDTO bdcdyZtDTO) {
        this.bdcdyZtDTO = bdcdyZtDTO;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public String getZwlxqssj() {
        return zwlxqssj;
    }

    public void setZwlxqssj(String zwlxqssj) {
        this.zwlxqssj = zwlxqssj;
    }

    public String getZwlxjssj() {
        return zwlxjssj;
    }

    public void setZwlxjssj(String zwlxjssj) {
        this.zwlxjssj = zwlxjssj;
    }

    public String getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(String bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public Integer getDyfs() {
        return dyfs;
    }

    public void setDyfs(Integer dyfs) {
        this.dyfs = dyfs;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    @Override
    public String toString() {
        return "BdcDyaDTO{" +
                "xmid='" + xmid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", qszt='" + qszt + '\'' +
                ", qlid='" + qlid + '\'' +
                ", qlr='" + qlr + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", yxtcqzh='" + yxtcqzh + '\'' +
                ", ywr='" + ywr + '\'' +
                ", ywrzjh='" + ywrzjh + '\'' +
                ", djyy='" + djyy + '\'' +
                ", djjg='" + djjg + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", ycqzh='" + ycqzh + '\'' +
                ", zwlxqssj='" + zwlxqssj + '\'' +
                ", zwlxjssj='" + zwlxjssj + '\'' +
                ", bdbzzqse='" + bdbzzqse + '\'' +
                ", dyfs=" + dyfs +
                ", bdcdyZtDTO=" + bdcdyZtDTO +
                ", qllx=" + qllx +
                '}';
    }
}
