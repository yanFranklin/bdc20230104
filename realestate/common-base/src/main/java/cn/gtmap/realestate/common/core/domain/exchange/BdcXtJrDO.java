package cn.gtmap.realestate.common.core.domain.exchange;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 系统接入实体
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-06 16:31
 **/
@Table(name = "BDC_XT_JR")
public class BdcXtJrDO {

    @Id
    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("权利类型")
    private String bdcqllxdm;

    @ApiModelProperty("权利类型名称")
    private String bdcqllxmc;

    @ApiModelProperty("djlxdm")
    private String bdcdjlxdm;

    @ApiModelProperty("djlxmc")
    private String bdcdjlxmc;

    @ApiModelProperty("不动产类型")
    private Integer bdclx;

    @ApiModelProperty("业务代码")
    private String ywdm;

    @ApiModelProperty("业务服务带啊吗")
    private String ywfwdm;

    @ApiModelProperty("是否多幢-不动产单元房屋类型")
    private Integer sfdz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBdcqllxdm() {
        return bdcqllxdm;
    }

    public void setBdcqllxdm(String bdcqllxdm) {
        this.bdcqllxdm = bdcqllxdm;
    }

    public String getBdcqllxmc() {
        return bdcqllxmc;
    }

    public void setBdcqllxmc(String bdcqllxmc) {
        this.bdcqllxmc = bdcqllxmc;
    }

    public String getBdcdjlxdm() {
        return bdcdjlxdm;
    }

    public void setBdcdjlxdm(String bdcdjlxdm) {
        this.bdcdjlxdm = bdcdjlxdm;
    }

    public String getBdcdjlxmc() {
        return bdcdjlxmc;
    }

    public void setBdcdjlxmc(String bdcdjlxmc) {
        this.bdcdjlxmc = bdcdjlxmc;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getYwdm() {
        return ywdm;
    }

    public void setYwdm(String ywdm) {
        this.ywdm = ywdm;
    }

    public String getYwfwdm() {
        return ywfwdm;
    }

    public void setYwfwdm(String ywfwdm) {
        this.ywfwdm = ywfwdm;
    }

    public Integer getSfdz() {
        return sfdz;
    }

    public void setSfdz(Integer sfdz) {
        this.sfdz = sfdz;
    }

    @Override
    public String toString() {
        return "BdcXtJrDO{" +
                "id='" + id + '\'' +
                ", bdcqllxdm='" + bdcqllxdm + '\'' +
                ", bdcqllxmc='" + bdcqllxmc + '\'' +
                ", bdcdjlxdm='" + bdcdjlxdm + '\'' +
                ", bdcdjlxmc='" + bdcdjlxmc + '\'' +
                ", bdclx=" + bdclx +
                ", ywdm='" + ywdm + '\'' +
                ", ywfwdm='" + ywfwdm + '\'' +
                ", sfdz=" + sfdz +
                '}';
    }
}
