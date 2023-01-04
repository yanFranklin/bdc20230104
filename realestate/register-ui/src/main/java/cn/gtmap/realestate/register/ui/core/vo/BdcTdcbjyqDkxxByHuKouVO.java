package cn.gtmap.realestate.register.ui.core.vo;

import cn.gtmap.realestate.common.core.domain.BdcBdcdjbZdjbxxDO;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * 土地承包经营权、农用地的其他使用权登记信息VO按户
 *
 * @param
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @return
 * @Date 17:16 2020/8/20
 */
public class BdcTdcbjyqDkxxByHuKouVO {

    /**
     * 宗地代码
     */
    @ApiModelProperty(value = "宗地代码")
    private String zddm;
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    /**
     * 宗地特征码
     */
    @ApiModelProperty(value = "宗地特征码")
    private String zdtzm;
    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;
    /**
     * 宗地面积
     */
    @ApiModelProperty(value = "宗地面积")
    private Double zdmj;
    /**
     * 面积单位
     */
    @ApiModelProperty(value = "面积单位")
    private Integer mjdw;
    /**
     * 用途
     */
    @ApiModelProperty(value = "用途")
    private String yt;
    /**
     * 等级
     */
    @ApiModelProperty(value = "等级")
    private Integer dj;
    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private Double jg;
    /**
     * 权利类型
     */
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    /**
     * 权利性质
     */
    @ApiModelProperty(value = "权利性质")
    private Integer qlxz;
    /**
     * 权利设定方式
     */
    @ApiModelProperty(value = "权利设定方式")
    private Integer qlsdfs;
    /**
     * 容积率
     */
    @ApiModelProperty(value = "容积率")
    private Double rjl;
    /**
     * 建筑密度
     */
    @ApiModelProperty(value = "建筑密度")
    private Double jzmd;
    /**
     * 建筑限高
     */
    @ApiModelProperty(value = "建筑限高")
    private Double jzxg;
    /**
     * 宗地四至-东
     */
    @ApiModelProperty(value = "宗地四至—东")
    private String zdszd;
    /**
     * 宗地四至-南
     */
    @ApiModelProperty(value = "宗地四至-南")
    private String zdszn;
    /**
     * 宗地四至-西
     */
    @ApiModelProperty(value = "宗地四至-西")
    private String zdszx;
    /**
     * 宗地四至-北
     */
    @ApiModelProperty(value = "宗地四至-北")
    private String zdszb;
    /**
     * 图幅号
     */
    @ApiModelProperty(value = "图幅号")
    private String tfh;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer zt;
    /**
     * 登记时间
     */
    @ApiModelProperty(value = "登记时间", example = "2018-10-01 12:18:48")
    private Date djsj;
    /**
     * 登簿人
     */
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    /**
     * 附记
     */
    @ApiModelProperty(value = "附记")
    private String fj;
    /**
     * 不动产类型
     */
    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;
    /**
     * 宗地用途2
     */
    @ApiModelProperty(value = "宗地用途2")
    private String zdyt2;
    /**
     * 宗地用途3
     */
    @ApiModelProperty(value = "宗地用途3")
    private String zdyt3;
    @ApiModelProperty(value = "土地使用起始时间", example = "2018-10-01 12:18:48")
    private Date tdsyqssj;
    @ApiModelProperty(value = "土地使用结束时间", example = "2018-10-01 12:18:48")
    private Date tdsyjssj;
    @ApiModelProperty(value = "土地使用起始时间2", example = "2018-10-01 12:18:48")
    private Date tdsyqssj2;
    @ApiModelProperty(value = "土地使用结束时间2", example = "2018-10-01 12:18:48")
    private Date tdsyjssj2;
    @ApiModelProperty(value = "土地使用起始时间3", example = "2018-10-01 12:18:48")
    private Date tdsyqssj3;
    @ApiModelProperty(value = "土地使用结束时间3", example = "2018-10-01 12:18:48")
    private Date tdsyjssj3;

    /**
     * 承包合同面积
     */
    private Double cbhtmj;

    /**
     * 实测面积
     */
    private Double scmj;

    /**
     * 承包起始时间
     */
    private Date cbqssj;

    /**
     * 承包结束时间
     */
    private Date cbjssj;

    /**
     * 承包土地用途
     */
    private String cbtdyt;

    /**
     * 承包地类等级
     */
    private String dldj;

    /**
     * 是否基本农田
     */
    private String sfjbnt;

    /**
     * 地类
     */
    private String dl;

    /**
     * 备注
     */
    private String bz;

    /**
     * 承包方式
     */
    private String cbfs;

    /**
     * 合同编号
     */
    private String htbh;

    /**
     * 发包方名称
     */
    private String fbfmc;

    /**
     * 发包方代码
     */
    private String fbfdm;

    /**
     * 发包方负责人
     */
    private String fbffzr;

    public Double getCbhtmj() {
        return cbhtmj;
    }

    public void setCbhtmj(Double cbhtmj) {
        this.cbhtmj = cbhtmj;
    }

    public Double getScmj() {
        return scmj;
    }

    public void setScmj(Double scmj) {
        this.scmj = scmj;
    }

    public Date getCbqssj() {
        return cbqssj;
    }

    public void setCbqssj(Date cbqssj) {
        this.cbqssj = cbqssj;
    }

    public Date getCbjssj() {
        return cbjssj;
    }

    public void setCbjssj(Date cbjssj) {
        this.cbjssj = cbjssj;
    }

    public String getCbtdyt() {
        return cbtdyt;
    }

    public void setCbtdyt(String cbtdyt) {
        this.cbtdyt = cbtdyt;
    }

    public String getDldj() {
        return dldj;
    }

    public void setDldj(String dldj) {
        this.dldj = dldj;
    }

    public String getSfjbnt() {
        return sfjbnt;
    }

    public void setSfjbnt(String sfjbnt) {
        this.sfjbnt = sfjbnt;
    }

    public String getDl() {
        return dl;
    }

    public void setDl(String dl) {
        this.dl = dl;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZdtzm() {
        return zdtzm;
    }

    public void setZdtzm(String zdtzm) {
        this.zdtzm = zdtzm;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getZdmj() {
        return zdmj;
    }

    public void setZdmj(Double zdmj) {
        this.zdmj = zdmj;
    }

    public Integer getMjdw() {
        return mjdw;
    }

    public void setMjdw(Integer mjdw) {
        this.mjdw = mjdw;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public Integer getDj() {
        return dj;
    }

    public void setDj(Integer dj) {
        this.dj = dj;
    }

    public Double getJg() {
        return jg;
    }

    public void setJg(Double jg) {
        this.jg = jg;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Integer getQlxz() {
        return qlxz;
    }

    public void setQlxz(Integer qlxz) {
        this.qlxz = qlxz;
    }

    public Integer getQlsdfs() {
        return qlsdfs;
    }

    public void setQlsdfs(Integer qlsdfs) {
        this.qlsdfs = qlsdfs;
    }

    public Double getRjl() {
        return rjl;
    }

    public void setRjl(Double rjl) {
        this.rjl = rjl;
    }

    public Double getJzmd() {
        return jzmd;
    }

    public void setJzmd(Double jzmd) {
        this.jzmd = jzmd;
    }

    public Double getJzxg() {
        return jzxg;
    }

    public void setJzxg(Double jzxg) {
        this.jzxg = jzxg;
    }

    public String getZdszd() {
        return zdszd;
    }

    public void setZdszd(String zdszd) {
        this.zdszd = zdszd;
    }

    public String getZdszn() {
        return zdszn;
    }

    public void setZdszn(String zdszn) {
        this.zdszn = zdszn;
    }

    public String getZdszx() {
        return zdszx;
    }

    public void setZdszx(String zdszx) {
        this.zdszx = zdszx;
    }

    public String getZdszb() {
        return zdszb;
    }

    public void setZdszb(String zdszb) {
        this.zdszb = zdszb;
    }

    public String getTfh() {
        return tfh;
    }

    public void setTfh(String tfh) {
        this.tfh = tfh;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
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

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getZdyt2() {
        return zdyt2;
    }

    public void setZdyt2(String zdyt2) {
        this.zdyt2 = zdyt2;
    }

    public String getZdyt3() {
        return zdyt3;
    }

    public void setZdyt3(String zdyt3) {
        this.zdyt3 = zdyt3;
    }

    public Date getTdsyqssj() {
        return tdsyqssj;
    }

    public void setTdsyqssj(Date tdsyqssj) {
        this.tdsyqssj = tdsyqssj;
    }

    public Date getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(Date tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
    }

    public Date getTdsyqssj2() {
        return tdsyqssj2;
    }

    public void setTdsyqssj2(Date tdsyqssj2) {
        this.tdsyqssj2 = tdsyqssj2;
    }

    public Date getTdsyjssj2() {
        return tdsyjssj2;
    }

    public void setTdsyjssj2(Date tdsyjssj2) {
        this.tdsyjssj2 = tdsyjssj2;
    }

    public Date getTdsyqssj3() {
        return tdsyqssj3;
    }

    public void setTdsyqssj3(Date tdsyqssj3) {
        this.tdsyqssj3 = tdsyqssj3;
    }

    public Date getTdsyjssj3() {
        return tdsyjssj3;
    }

    public void setTdsyjssj3(Date tdsyjssj3) {
        this.tdsyjssj3 = tdsyjssj3;
    }

    public String getCbfs() {
        return cbfs;
    }

    public void setCbfs(String cbfs) {
        this.cbfs = cbfs;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getFbfmc() {
        return fbfmc;
    }

    public void setFbfmc(String fbfmc) {
        this.fbfmc = fbfmc;
    }

    public String getFbfdm() {
        return fbfdm;
    }

    public void setFbfdm(String fbfdm) {
        this.fbfdm = fbfdm;
    }

    public String getFbffzr() {
        return fbffzr;
    }

    public void setFbffzr(String fbffzr) {
        this.fbffzr = fbffzr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcTdcbjyqDkxxByHuKouVO{");
        sb.append("zddm='").append(zddm).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", zdtzm='").append(zdtzm).append('\'');
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", zdmj=").append(zdmj);
        sb.append(", mjdw=").append(mjdw);
        sb.append(", yt='").append(yt).append('\'');
        sb.append(", dj=").append(dj);
        sb.append(", jg=").append(jg);
        sb.append(", qllx=").append(qllx);
        sb.append(", qlxz=").append(qlxz);
        sb.append(", qlsdfs=").append(qlsdfs);
        sb.append(", rjl=").append(rjl);
        sb.append(", jzmd=").append(jzmd);
        sb.append(", jzxg=").append(jzxg);
        sb.append(", zdszd='").append(zdszd).append('\'');
        sb.append(", zdszn='").append(zdszn).append('\'');
        sb.append(", zdszx='").append(zdszx).append('\'');
        sb.append(", zdszb='").append(zdszb).append('\'');
        sb.append(", tfh='").append(tfh).append('\'');
        sb.append(", zt=").append(zt);
        sb.append(", djsj=").append(djsj);
        sb.append(", dbr='").append(dbr).append('\'');
        sb.append(", fj='").append(fj).append('\'');
        sb.append(", bdclx=").append(bdclx);
        sb.append(", zdyt2='").append(zdyt2).append('\'');
        sb.append(", zdyt3='").append(zdyt3).append('\'');
        sb.append(", tdsyqssj=").append(tdsyqssj);
        sb.append(", tdsyjssj=").append(tdsyjssj);
        sb.append(", tdsyqssj2=").append(tdsyqssj2);
        sb.append(", tdsyjssj2=").append(tdsyjssj2);
        sb.append(", tdsyqssj3=").append(tdsyqssj3);
        sb.append(", tdsyjssj3=").append(tdsyjssj3);
        sb.append(", cbhtmj=").append(cbhtmj);
        sb.append(", scmj=").append(scmj);
        sb.append(", cbqssj=").append(cbqssj);
        sb.append(", cbjssj=").append(cbjssj);
        sb.append(", cbtdyt='").append(cbtdyt).append('\'');
        sb.append(", dldj='").append(dldj).append('\'');
        sb.append(", sfjbnt='").append(sfjbnt).append('\'');
        sb.append(", dl='").append(dl).append('\'');
        sb.append(", bz='").append(bz).append('\'');
        sb.append(", cbfs='").append(cbfs).append('\'');
        sb.append(", htbh='").append(htbh).append('\'');
        sb.append(", fbfmc='").append(fbfmc).append('\'');
        sb.append(", fbfdm='").append(fbfdm).append('\'');
        sb.append(", fbffzr='").append(fbffzr).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
