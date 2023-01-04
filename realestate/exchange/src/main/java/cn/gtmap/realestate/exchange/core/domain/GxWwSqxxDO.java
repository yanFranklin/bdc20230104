package cn.gtmap.realestate.exchange.core.domain;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 共享外网申请项目信息
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/3/15 10:30
 */
@ApiModel(value = "GxWwSqxxDO", description = "共享外网申请信息")
public class GxWwSqxxDO implements Serializable {
    @Id
    @ApiModelProperty(value = "主键")
    private String sqxxid;
    @ApiModelProperty(value = "项目主键")
    private String xmid;
    @ApiModelProperty(value = "是否分别持证")
    private String sffbcz;
    @ApiModelProperty(value = "交易价格")
    private Double jyjg;
    @ApiModelProperty(value = "被担保主债权数额")
    private Double bdbzzqse;
    @ApiModelProperty(value = "债务履行期限开始日期")
    private Date zwlxqxksrq;
    @ApiModelProperty(value = "债务履行期限结束日期")
    private Date zwlxqxjsrq;
    @ApiModelProperty(value = "评估价值")
    private Double pgjz;
    @ApiModelProperty(value = "抵押范围")
    private String dyfw;
    @ApiModelProperty(value = "抵押方式")
    private String dyfs;
    @ApiModelProperty(value = "贷款方式")
    private String dkfs;
    @ApiModelProperty(value = "不动产价值")
    private Double bdcjz;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "抵押顺位")
    private Double dysw;
    @ApiModelProperty(value = "申请证书版式")
    private String sqzsbs;
    @ApiModelProperty(value = "不动产类型")
    private String bdclx;
    @ApiModelProperty(value = "面积")
    private Double mj;
    @ApiModelProperty(value = "房屋用途")
    private String fwyt;
    @ApiModelProperty(value = "宗地/宗海权利性质")
    private String zdzhqlxz;
    @ApiModelProperty(value = "构筑物类型")
    private String gzwlx;
    @ApiModelProperty(value = "面积单位")
    private String mjdw;
    @ApiModelProperty(value = "申请类型")
    private String sqlx;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    @ApiModelProperty(value = "房产证号")
    private String fczh;
    @ApiModelProperty(value = "土地证号")
    private String tdzh;
    @ApiModelProperty(value = "登记机构名称")
    private String djjgmc;
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    @ApiModelProperty(value = "房产证号是否手填")
    private String fczhsfst;
    @ApiModelProperty(value = "土地证号是否手填")
    private String tdzhsfst;
    @ApiModelProperty(value = "不动产权证号是否手填")
    private String bdcqzhsfst;
    @ApiModelProperty(value = "地籍号")
    private String djh;
    @ApiModelProperty(value = "地籍号是否手填")
    private String djhsfst;
    @ApiModelProperty(value = "是否经济适用房")
    private String sfjjsyf;
    @ApiModelProperty(value = "权籍调查是否通过")
    private String qjdcsftg;
    @ApiModelProperty(value = "权籍调查失败原因")
    private String qjdcsbyy;
    @ApiModelProperty(value = "买卖合同号")
    private String mmhth;
    @ApiModelProperty(value = "房屋面积")
    private Double fwmj;
    @ApiModelProperty(value = "房屋面积")
    private Double tdmj;
    @ApiModelProperty(value = "土地使用开始时间")
    private Date tdsyksqx;
    @ApiModelProperty(value = "土地使用结束时间")
    private Date tdsyjsqx;
    @ApiModelProperty(value = "担保范围")
    private String dbfw;
    @ApiModelProperty(value = "房产抵押面积")
    private Double fwdymj;
    @ApiModelProperty(value = "土地抵押面积")
    private Double tddymj;
    @ApiModelProperty(value = "登记子项")
    private String djzx;
    @ApiModelProperty(value = "总层数")
    private String zcs;
    @ApiModelProperty(value = "房屋性质")
    private String fwxz;
    @ApiModelProperty(value = "房屋结构")
    private String fwjg;
    // 审批系统业务号
    private String spxtywh;

    // 权利类型
    private String qllx;

    private List<BdcSlSqrDO> qlrList;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GxWwSqxxDO{");
        sb.append("sqxxid='").append(sqxxid).append('\'');
        sb.append(", xmid='").append(xmid).append('\'');
        sb.append(", sffbcz='").append(sffbcz).append('\'');
        sb.append(", jyjg=").append(jyjg);
        sb.append(", bdbzzqse=").append(bdbzzqse);
        sb.append(", zwlxqxksrq=").append(zwlxqxksrq);
        sb.append(", zwlxqxjsrq=").append(zwlxqxjsrq);
        sb.append(", pgjz=").append(pgjz);
        sb.append(", dyfw='").append(dyfw).append('\'');
        sb.append(", dyfs='").append(dyfs).append('\'');
        sb.append(", dkfs='").append(dkfs).append('\'');
        sb.append(", bdcjz=").append(bdcjz);
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", dysw=").append(dysw);
        sb.append(", sqzsbs='").append(sqzsbs).append('\'');
        sb.append(", bdclx='").append(bdclx).append('\'');
        sb.append(", mj=").append(mj);
        sb.append(", fwyt='").append(fwyt).append('\'');
        sb.append(", zdzhqlxz='").append(zdzhqlxz).append('\'');
        sb.append(", gzwlx='").append(gzwlx).append('\'');
        sb.append(", mjdw='").append(mjdw).append('\'');
        sb.append(", sqlx='").append(sqlx).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", bdcqzh='").append(bdcqzh).append('\'');
        sb.append(", fczh='").append(fczh).append('\'');
        sb.append(", tdzh='").append(tdzh).append('\'');
        sb.append(", djjgmc='").append(djjgmc).append('\'');
        sb.append(", djjg='").append(djjg).append('\'');
        sb.append(", fczhsfst='").append(fczhsfst).append('\'');
        sb.append(", tdzhsfst='").append(tdzhsfst).append('\'');
        sb.append(", bdcqzhsfst='").append(bdcqzhsfst).append('\'');
        sb.append(", djh='").append(djh).append('\'');
        sb.append(", djhsfst='").append(djhsfst).append('\'');
        sb.append(", sfjjsyf='").append(sfjjsyf).append('\'');
        sb.append(", qjdcsftg='").append(qjdcsftg).append('\'');
        sb.append(", qjdcsbyy='").append(qjdcsbyy).append('\'');
        sb.append(", mmhth='").append(mmhth).append('\'');
        sb.append(", fwmj=").append(fwmj);
        sb.append(", tdmj=").append(tdmj);
        sb.append(", tdsyksqx=").append(tdsyksqx);
        sb.append(", tdsyjsqx=").append(tdsyjsqx);
        sb.append(", dbfw='").append(dbfw).append('\'');
        sb.append(", fwdymj=").append(fwdymj);
        sb.append(", tddymj=").append(tddymj);
        sb.append(", djzx='").append(djzx).append('\'');
        sb.append(", zcs='").append(zcs).append('\'');
        sb.append(", fwxz='").append(fwxz).append('\'');
        sb.append(", fwjg='").append(fwjg).append('\'');
        sb.append(", spxtywh='").append(spxtywh).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getSqxxid() {
        return sqxxid;
    }

    public void setSqxxid(String sqxxid) {
        this.sqxxid = sqxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSffbcz() {
        return sffbcz;
    }

    public void setSffbcz(String sffbcz) {
        this.sffbcz = sffbcz;
    }

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    public Double getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(Double bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public Date getZwlxqxksrq() {
        return zwlxqxksrq;
    }

    public void setZwlxqxksrq(Date zwlxqxksrq) {
        this.zwlxqxksrq = zwlxqxksrq;
    }

    public Date getZwlxqxjsrq() {
        return zwlxqxjsrq;
    }

    public void setZwlxqxjsrq(Date zwlxqxjsrq) {
        this.zwlxqxjsrq = zwlxqxjsrq;
    }

    public Double getPgjz() {
        return pgjz;
    }

    public void setPgjz(Double pgjz) {
        this.pgjz = pgjz;
    }

    public String getDyfw() {
        return dyfw;
    }

    public void setDyfw(String dyfw) {
        this.dyfw = dyfw;
    }

    public String getDyfs() {
        return dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public String getDkfs() {
        return dkfs;
    }

    public void setDkfs(String dkfs) {
        this.dkfs = dkfs;
    }

    public Double getBdcjz() {
        return bdcjz;
    }

    public void setBdcjz(Double bdcjz) {
        this.bdcjz = bdcjz;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getDysw() {
        return dysw;
    }

    public void setDysw(Double dysw) {
        this.dysw = dysw;
    }

    public String getSqzsbs() {
        return sqzsbs;
    }

    public void setSqzsbs(String sqzsbs) {
        this.sqzsbs = sqzsbs;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public Double getMj() {
        return mj;
    }

    public void setMj(Double mj) {
        this.mj = mj;
    }

    public String getFwyt() {
        return fwyt;
    }

    public void setFwyt(String fwyt) {
        this.fwyt = fwyt;
    }

    public String getZdzhqlxz() {
        return zdzhqlxz;
    }

    public void setZdzhqlxz(String zdzhqlxz) {
        this.zdzhqlxz = zdzhqlxz;
    }

    public String getGzwlx() {
        return gzwlx;
    }

    public void setGzwlx(String gzwlx) {
        this.gzwlx = gzwlx;
    }

    public String getMjdw() {
        return mjdw;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    public String getSqlx() {
        return sqlx;
    }

    public void setSqlx(String sqlx) {
        this.sqlx = sqlx;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getFczh() {
        return fczh;
    }

    public void setFczh(String fczh) {
        this.fczh = fczh;
    }

    public String getTdzh() {
        return tdzh;
    }

    public void setTdzh(String tdzh) {
        this.tdzh = tdzh;
    }

    public String getDjjgmc() {
        return djjgmc;
    }

    public void setDjjgmc(String djjgmc) {
        this.djjgmc = djjgmc;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getFczhsfst() {
        return fczhsfst;
    }

    public void setFczhsfst(String fczhsfst) {
        this.fczhsfst = fczhsfst;
    }

    public String getTdzhsfst() {
        return tdzhsfst;
    }

    public void setTdzhsfst(String tdzhsfst) {
        this.tdzhsfst = tdzhsfst;
    }

    public String getBdcqzhsfst() {
        return bdcqzhsfst;
    }

    public void setBdcqzhsfst(String bdcqzhsfst) {
        this.bdcqzhsfst = bdcqzhsfst;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getDjhsfst() {
        return djhsfst;
    }

    public void setDjhsfst(String djhsfst) {
        this.djhsfst = djhsfst;
    }

    public String getSfjjsyf() {
        return sfjjsyf;
    }

    public void setSfjjsyf(String sfjjsyf) {
        this.sfjjsyf = sfjjsyf;
    }

    public String getQjdcsftg() {
        return qjdcsftg;
    }

    public void setQjdcsftg(String qjdcsftg) {
        this.qjdcsftg = qjdcsftg;
    }

    public String getQjdcsbyy() {
        return qjdcsbyy;
    }

    public void setQjdcsbyy(String qjdcsbyy) {
        this.qjdcsbyy = qjdcsbyy;
    }

    public String getMmhth() {
        return mmhth;
    }

    public void setMmhth(String mmhth) {
        this.mmhth = mmhth;
    }

    public Double getFwmj() {
        return fwmj;
    }

    public void setFwmj(Double fwmj) {
        this.fwmj = fwmj;
    }

    public Double getTdmj() {
        return tdmj;
    }

    public void setTdmj(Double tdmj) {
        this.tdmj = tdmj;
    }

    public Date getTdsyksqx() {
        return tdsyksqx;
    }

    public void setTdsyksqx(Date tdsyksqx) {
        this.tdsyksqx = tdsyksqx;
    }

    public Date getTdsyjsqx() {
        return tdsyjsqx;
    }

    public void setTdsyjsqx(Date tdsyjsqx) {
        this.tdsyjsqx = tdsyjsqx;
    }

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

    public Double getFwdymj() {
        return fwdymj;
    }

    public void setFwdymj(Double fwdymj) {
        this.fwdymj = fwdymj;
    }

    public Double getTddymj() {
        return tddymj;
    }

    public void setTddymj(Double tddymj) {
        this.tddymj = tddymj;
    }

    public String getDjzx() {
        return djzx;
    }

    public void setDjzx(String djzx) {
        this.djzx = djzx;
    }

    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getSpxtywh() {
        return spxtywh;
    }

    public void setSpxtywh(String spxtywh) {
        this.spxtywh = spxtywh;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public List<BdcSlSqrDO> getQlrList() {
        return qlrList;
    }

    public void setQlrList(List<BdcSlSqrDO> qlrList) {
        this.qlrList = qlrList;
    }
}
