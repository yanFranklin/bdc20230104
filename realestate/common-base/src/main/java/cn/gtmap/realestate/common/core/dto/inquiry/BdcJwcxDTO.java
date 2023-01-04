package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/4
 * @description
 */
@Api(value = "BdcJwcxDTO", description = "纪委查询结果DTO")
public class BdcJwcxDTO {

    @ApiModelProperty("证书id")
    private String zsid;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty(value = "权属状态")
    private String qszt;

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("权利人证件号")
    private String qlrzjh;

    @ApiModelProperty("不动产权证号")
    private String bdcqzh;


    @ApiModelProperty("义务人")
    private String ywr;

    @ApiModelProperty("义务人证件号")
    private String ywrzjh;

    @ApiModelProperty(value = "登记时间")
    private String djsj;

    @ApiModelProperty("建筑面积")
    private Double jzmj;

    @ApiModelProperty(value = "房屋类型")
    private String fwlx;

    @ApiModelProperty("交易价格")
    private Double jyjg;

    @ApiModelProperty("查封情况")
    private String cfqk;

    @ApiModelProperty(value = "抵押情况")
    private String dyqk;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "状态信息")
    private BdcdyhZtResponseDTO bdcdyZtDTO;

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
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

    public String getDjsj() {
        return djsj;
    }

    public void setDjsj(String djsj) {
        this.djsj = djsj;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    public String getCfqk() {
        return cfqk;
    }

    public void setCfqk(String cfqk) {
        this.cfqk = cfqk;
    }

    public String getDyqk() {
        return dyqk;
    }

    public void setDyqk(String dyqk) {
        this.dyqk = dyqk;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public BdcdyhZtResponseDTO getBdcdyZtDTO() {
        return bdcdyZtDTO;
    }

    public void setBdcdyZtDTO(BdcdyhZtResponseDTO bdcdyZtDTO) {
        this.bdcdyZtDTO = bdcdyZtDTO;
    }

    @Override
    public String toString() {
        return "BdcJwcxDTO{" +
                "zsid='" + zsid + '\'' +
                ", zl='" + zl + '\'' +
                ", qszt='" + qszt + '\'' +
                ", qlr='" + qlr + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", ywr='" + ywr + '\'' +
                ", ywrzjh='" + ywrzjh + '\'' +
                ", djsj='" + djsj + '\'' +
                ", jzmj=" + jzmj +
                ", fwlx='" + fwlx + '\'' +
                ", jyjg=" + jyjg +
                ", cfqk='" + cfqk + '\'' +
                ", dyqk='" + dyqk + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdyZtDTO=" + bdcdyZtDTO +
                '}';
    }
}
