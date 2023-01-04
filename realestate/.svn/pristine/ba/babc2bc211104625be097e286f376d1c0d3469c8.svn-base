package cn.gtmap.realestate.common.core.vo.register.ui;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.init.BdcQlrDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/18
 * @description 不动产证书VO对象
 */
public class BdcZsVO extends BdcZsDO{
    private Integer qllx;

    @ApiModelProperty(value = "权利类型名称")
    private String qllxmc;

    @ApiModelProperty(value = "证书类型名称")
    private String zslxmc;

    @ApiModelProperty(value = "登记机构")
    private String djjg;

    @JsonFormat(pattern = "yyyy年MM月dd日")
    @ApiModelProperty(value = "缮证时间")
    private Date szrq;

    @JsonFormat(pattern = "yyyy")
    @ApiModelProperty(value = "缮证时间_年")
    private Date szrqYear;

    @JsonFormat(pattern = "MM")
    @ApiModelProperty(value = "缮证时间_月")
    private Date szrqMonth;

    @JsonFormat(pattern = "dd")
    @ApiModelProperty(value = "缮证时间_日")
    private Date szrqDay;

    @ApiModelProperty(value = "共有方式名称")
    private String gyfsmc;

    @ApiModelProperty(value = "项目id")
    private String xmid;
    @ApiModelProperty(value = "登记原因")
    private String djyy;

    @ApiModelProperty(value = "证书所在项目的权属状态/证书权属状态")
    private Integer qszt;

    @ApiModelProperty(value = "证书相关的所有的项目信息")
    private List<BdcXmDO> bdcXmDOList;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    @ApiModelProperty("权利人信息")
    private List<BdcQlrDTO> bdcQlrDTOList;

    @ApiModelProperty("义务人信息")
    private List<BdcQlrDTO> bdcYwrDTOList;

    @ApiModelProperty("产权来源")
    private String cqly;

    @ApiModelProperty("权利人联系电话")
    private String lxdh;

    public List<BdcXmDO> getBdcXmDOList() {
        return bdcXmDOList;
    }

    public void setBdcXmDOList(List<BdcXmDO> bdcXmDOList) {
        this.bdcXmDOList = bdcXmDOList;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getQllxmc() {
        return qllxmc;
    }

    public void setQllxmc(String qllxmc) {
        this.qllxmc = qllxmc;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public Date getSzrq() {
        return szrq;
    }

    public void setSzrq(Date szrq) {
        this.szrq = szrq;
    }

    public Date getSzrqYear() {
        return szrqYear;
    }

    public void setSzrqYear(Date szrqYear) {
        this.szrqYear = szrqYear;
    }

    public Date getSzrqMonth() {
        return szrqMonth;
    }

    public void setSzrqMonth(Date szrqMonth) {
        this.szrqMonth = szrqMonth;
    }

    public Date getSzrqDay() {
        return szrqDay;
    }

    public void setSzrqDay(Date szrqDay) {
        this.szrqDay = szrqDay;
    }

    public String getGyfsmc() {
        return gyfsmc;
    }

    public void setGyfsmc(String gyfsmc) {
        this.gyfsmc = gyfsmc;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getZslxmc() {
        return zslxmc;
    }

    public void setZslxmc(String zslxmc) {
        this.zslxmc = zslxmc;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    @Override
    public Integer getQllx() {
        return qllx;
    }

    @Override
    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getCqly() {
        return cqly;
    }

    public void setCqly(String cqly) {
        this.cqly = cqly;
    }

    public List<BdcQlrDTO> getBdcQlrDTOList() {
        return bdcQlrDTOList;
    }

    public void setBdcQlrDTOList(List<BdcQlrDTO> bdcQlrDTOList) {
        this.bdcQlrDTOList = bdcQlrDTOList;
    }

    public List<BdcQlrDTO> getBdcYwrDTOList() {
        return bdcYwrDTOList;
    }

    public void setBdcYwrDTOList(List<BdcQlrDTO> bdcYwrDTOList) {
        this.bdcYwrDTOList = bdcYwrDTOList;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    @Override
    public String toString() {
        return "BdcZsVO{" +
                "qllx=" + qllx +
                ", qllxmc='" + qllxmc + '\'' +
                ", zslxmc='" + zslxmc + '\'' +
                ", djjg='" + djjg + '\'' +
                ", szrq=" + szrq +
                ", szrqYear=" + szrqYear +
                ", szrqMonth=" + szrqMonth +
                ", szrqDay=" + szrqDay +
                ", gyfsmc='" + gyfsmc + '\'' +
                ", xmid='" + xmid + '\'' +
                ", djyy='" + djyy + '\'' +
                ", qszt=" + qszt +
                ", bdcXmDOList=" + bdcXmDOList +
                ", qjgldm='" + qjgldm + '\'' +
                ", bdcQlrDTOList=" + bdcQlrDTOList +
                ", bdcYwrDTOList=" + bdcYwrDTOList +
                ", cqly='" + cqly + '\'' +
                ", lxdh='" + lxdh + '\'' +
                '}';
    }
}
