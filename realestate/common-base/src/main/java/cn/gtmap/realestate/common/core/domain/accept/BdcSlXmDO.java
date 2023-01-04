package cn.gtmap.realestate.common.core.domain.accept;

import cn.gtmap.realestate.common.util.UUIDGenerator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理项目
 */
@Table(name = "BDC_SL_XM")
@ApiModel(value = "BdcSlXmDO", description = "不动产受理项目")
public class BdcSlXmDO implements Serializable {
    @Id
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "基本信息ID")
    private String jbxxid;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "权籍ID")
    private String qjid;
    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @ApiModelProperty(value = "原不动产权证")
    private String ybdcqz;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "权利人")
    private String qlr;
    @ApiModelProperty(value = "抵押金额")
    private Double dyje;
    @ApiModelProperty(value = "抵押方式")
    private Integer dyfs;
    @ApiModelProperty(value = "债务履行起始时间",example = "2018-10-01 12:18:48")
    private Date zwlxqssj;
    @ApiModelProperty(value = "债务履行结束时间",example = "2018-10-01 12:18:48")
    private Date zwlxjssj;
    @ApiModelProperty(value = "在建建筑物坐落")
    private String zjjzwzl;
    @ApiModelProperty(value = "在建建筑物抵押范围")
    private String zjjzwdyfw;
    @ApiModelProperty(value = "最高债权确定事实")
    private String zgzqqdss;
    @ApiModelProperty(value = "担保范围")
    private String dbfw;
    @ApiModelProperty(value = "贷款方式")
    private String dkfs;
    @ApiModelProperty(value = "被担保主债权数额")
    private Double bdbzzqse;
    @ApiModelProperty(value = "房屋评估价格")
    private Double fwpgjg;
    @ApiModelProperty(value = "土地评估价格")
    private Double tdpgjg;
    @ApiModelProperty(value = "预告登记种类")
    private Integer ygdjzl;
    @ApiModelProperty(value = "取得价格/主债权数额")
    private Double qdjg;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    @ApiModelProperty(value = "预告债务履行起始时间",example = "2018-10-01 12:18:48")
    private Date ygzwlxqssj;
    @ApiModelProperty(value = "预告债务履行结束时间",example = "2018-10-01 12:18:48")
    private Date ygzwlxjssj;
    @ApiModelProperty(value = "预告抵押方式")
    private Integer ygdyfs;
    @ApiModelProperty(value = "异议事项")
    private String yysx;
    @ApiModelProperty(value = "使用权（承包方）面积")
    private Double syqmj;
    @ApiModelProperty(value = "林地使用（承包）开始期限",example = "2018-10-01 12:18:48")
    private Date ldsyksqx;
    @ApiModelProperty(value = "林地使用（承包）结束期限",example = "2018-10-01 12:18:48")
    private Date ldsyjsqx;
    @ApiModelProperty(value = "林地所有权性质")
    private Integer ldsyqxz;
    @ApiModelProperty(value = "森林、林木所有权人")
    private String sllmsyqr1;
    @ApiModelProperty(value = "森林、林木使用权人")
    private String sllmsyqr2;
    @ApiModelProperty(value = "主要树种")
    private String zysz;
    @ApiModelProperty(value = "株数")
    private Integer zs;
    @ApiModelProperty(value = "林种")
    private Integer lz;
    @ApiModelProperty(value = "起源")
    private Integer qy;
    @ApiModelProperty(value = "造林年度")
    private Integer zlnd;
    @ApiModelProperty(value = "小地名")
    private String xdm;
    @ApiModelProperty(value = "林班")
    private String lb;
    @ApiModelProperty(value = "小班")
    private String xb;
    @ApiModelProperty(value = "宗地宗海用途")
    private String zdzhyt;
    @ApiModelProperty(value = "宗地宗海面积")
    private Double zdzhmj;
    @ApiModelProperty(value = "需地役坐落")
    private String xdyzl;
    @ApiModelProperty(value = "需地役不动产单元号")
    private String xdybdcdyh;
    @ApiModelProperty(value = "权利人数据来源 1：权籍 2：上一手权利人 3：上一手义务人")
    private Integer qlrsjly;
    @ApiModelProperty(value = "义务人数据来源 1：权籍 2：上一手权利人 3：上一手义务人")
    private Integer ywrsjly;
    @ApiModelProperty(value = "是否生成权利 0：否  1：是")
    private Integer sfscql;
    @ApiModelProperty(value = "权利数据来源 1：权籍 2：上一手  可组合(1,2)")
    private String qlsjly;
    @ApiModelProperty(value = "证书种类   1：证书  2：证明")
    private Integer zszl;
    @ApiModelProperty(value = "证书序号：用于组合发证 分组")
    private Integer zsxh;
    @ApiModelProperty(value = "是否增量初始化业务  0：否  1：是")
    private Integer sfzlcsh;
    @ApiModelProperty(value = "不动产单元房屋类型")
    private Integer bdcdyfwlx;
    @ApiModelProperty(value = "操作人ID")
    private String czrid;
    @ApiModelProperty(value = "操作时间",example = "2018-10-01 12:18:48")
    private Date czsj;
    @ApiModelProperty(value = "是否主房  0：否  1：是")
    private Integer sfzf;
    @ApiModelProperty(value = "是否还原原注销权利  0：否  1：是")
    private Integer sfhyyzxql;
    @ApiModelProperty(value = "是否换证  0：否  1：是")
    private Integer sfhz;
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @ApiModelProperty(value = "是否房查(房屋信息查询)  0：否  1：是")
    private Integer sffc;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "是否分别持证  0：否  1：是")
    private Integer sqfbcz;
    @ApiModelProperty(value = "宗地宗海用途2")
    private String zdzhyt2;
    @ApiModelProperty(value = "宗地宗海用途3")
    private String zdzhyt3;
    @ApiModelProperty(value = "权利性质")
    private String qlxz;
    @ApiModelProperty(value = "审批来源 0: 无审批来源 1:一窗受理 2:交易系统 3:互联网+ 9:其他系统")
    private Integer xmywlx;
    @ApiModelProperty(value = "审批系统业务号")
    private String spxtywh;
    @ApiModelProperty(value = "是否生成证书  0：否  1：是")
    private Integer sfsczs;
    @ApiModelProperty(value = "推送税务时间")
    private Date tsswsj;
    @ApiModelProperty(value = "用途")
    private String yt;
    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;
    @ApiModelProperty(value = "权籍权利人关系ID-用于土地承包经营权，记录承包方与承包宗地关系ID")
    private String qjqlrgxid;

    @ApiModelProperty("定着物面积")
    private Double dzwmj;

    @ApiModelProperty(value = "档案归属地")
    private String dagsd;

    @ApiModelProperty(value = "房屋信息唯一识别码")
    private String fwuuid;


    @ApiModelProperty(value = "定着物用途名称")
    private String dzwytmc;

    public BdcSlXmDO(String czrid) {
        this.xmid = UUIDGenerator.generate16();
        this.sfzlcsh = 0;
        this.czrid = czrid;
        this.czsj = new Date(System.currentTimeMillis());
    }

    public BdcSlXmDO() {
    }

    public String getDagsd() {
        return dagsd;
    }

    public void setDagsd(String dagsd) {
        this.dagsd = dagsd;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(String jbxxid) {
        this.jbxxid = jbxxid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQjid() {
        return qjid;
    }

    public void setQjid(String qjid) {
        this.qjid = qjid;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getYbdcqz() {
        return ybdcqz;
    }

    public void setYbdcqz(String ybdcqz) {
        this.ybdcqz = ybdcqz;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public Double getDyje() {
        return dyje;
    }

    public void setDyje(Double dyje) {
        this.dyje = dyje;
    }

    public Integer getDyfs() {
        return dyfs;
    }

    public void setDyfs(Integer dyfs) {
        this.dyfs = dyfs;
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

    public String getZgzqqdss() {
        return zgzqqdss;
    }

    public void setZgzqqdss(String zgzqqdss) {
        this.zgzqqdss = zgzqqdss;
    }

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
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

    public Integer getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(Integer ygdjzl) {
        this.ygdjzl = ygdjzl;
    }

    public Double getQdjg() {
        return qdjg;
    }

    public void setQdjg(Double qdjg) {
        this.qdjg = qdjg;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public Date getYgzwlxqssj() {
        return ygzwlxqssj;
    }

    public void setYgzwlxqssj(Date ygzwlxqssj) {
        this.ygzwlxqssj = ygzwlxqssj;
    }

    public Date getYgzwlxjssj() {
        return ygzwlxjssj;
    }

    public void setYgzwlxjssj(Date ygzwlxjssj) {
        this.ygzwlxjssj = ygzwlxjssj;
    }

    public Integer getYgdyfs() {
        return ygdyfs;
    }

    public void setYgdyfs(Integer ygdyfs) {
        this.ygdyfs = ygdyfs;
    }

    public String getYysx() {
        return yysx;
    }

    public void setYysx(String yysx) {
        this.yysx = yysx;
    }

    public Double getSyqmj() {
        return syqmj;
    }

    public void setSyqmj(Double syqmj) {
        this.syqmj = syqmj;
    }

    public Date getLdsyksqx() {
        return ldsyksqx;
    }

    public void setLdsyksqx(Date ldsyksqx) {
        this.ldsyksqx = ldsyksqx;
    }

    public Date getLdsyjsqx() {
        return ldsyjsqx;
    }

    public void setLdsyjsqx(Date ldsyjsqx) {
        this.ldsyjsqx = ldsyjsqx;
    }

    public Integer getLdsyqxz() {
        return ldsyqxz;
    }

    public void setLdsyqxz(Integer ldsyqxz) {
        this.ldsyqxz = ldsyqxz;
    }

    public String getSllmsyqr1() {
        return sllmsyqr1;
    }

    public void setSllmsyqr1(String sllmsyqr1) {
        this.sllmsyqr1 = sllmsyqr1;
    }

    public String getSllmsyqr2() {
        return sllmsyqr2;
    }

    public void setSllmsyqr2(String sllmsyqr2) {
        this.sllmsyqr2 = sllmsyqr2;
    }

    public String getZysz() {
        return zysz;
    }

    public void setZysz(String zysz) {
        this.zysz = zysz;
    }

    public Integer getZs() {
        return zs;
    }

    public void setZs(Integer zs) {
        this.zs = zs;
    }

    public Integer getLz() {
        return lz;
    }

    public void setLz(Integer lz) {
        this.lz = lz;
    }

    public Integer getQy() {
        return qy;
    }

    public void setQy(Integer qy) {
        this.qy = qy;
    }

    public Integer getZlnd() {
        return zlnd;
    }

    public void setZlnd(Integer zlnd) {
        this.zlnd = zlnd;
    }

    public String getXdm() {
        return xdm;
    }

    public void setXdm(String xdm) {
        this.xdm = xdm;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getZdzhyt() {
        return zdzhyt;
    }

    public void setZdzhyt(String zdzhyt) {
        this.zdzhyt = zdzhyt;
    }

    public Double getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(Double zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public String getXdyzl() {
        return xdyzl;
    }

    public void setXdyzl(String xdyzl) {
        this.xdyzl = xdyzl;
    }

    public String getXdybdcdyh() {
        return xdybdcdyh;
    }

    public void setXdybdcdyh(String xdybdcdyh) {
        this.xdybdcdyh = xdybdcdyh;
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

    public String getQlsjly() {
        return qlsjly;
    }

    public void setQlsjly(String qlsjly) {
        this.qlsjly = qlsjly;
    }

    public Integer getZszl() {
        return zszl;
    }

    public void setZszl(Integer zszl) {
        this.zszl = zszl;
    }

    public Integer getZsxh() {
        return zsxh;
    }

    public void setZsxh(Integer zsxh) {
        this.zsxh = zsxh;
    }

    public Integer getSfzlcsh() {
        return sfzlcsh;
    }

    public void setSfzlcsh(Integer sfzlcsh) {
        this.sfzlcsh = sfzlcsh;
    }

    public Integer getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(Integer bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public Integer getSfzf() {
        return sfzf;
    }

    public void setSfzf(Integer sfzf) {
        this.sfzf = sfzf;
    }

    public Integer getSfhyyzxql() {
        return sfhyyzxql;
    }

    public void setSfhyyzxql(Integer sfhyyzxql) {
        this.sfhyyzxql = sfhyyzxql;
    }

    public Integer getSfhz() {
        return sfhz;
    }

    public void setSfhz(Integer sfhz) {
        this.sfhz = sfhz;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Integer getSffc() {
        return sffc;
    }

    public void setSffc(Integer sffc) {
        this.sffc = sffc;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getSqfbcz() {
        return sqfbcz;
    }

    public void setSqfbcz(Integer sqfbcz) {
        this.sqfbcz = sqfbcz;
    }

    public String getZdzhyt2() {
        return zdzhyt2;
    }

    public void setZdzhyt2(String zdzhyt2) {
        this.zdzhyt2 = zdzhyt2;
    }

    public String getZdzhyt3() {
        return zdzhyt3;
    }

    public void setZdzhyt3(String zdzhyt3) {
        this.zdzhyt3 = zdzhyt3;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public Integer getXmywlx() {
        return xmywlx;
    }

    public void setXmywlx(Integer xmywlx) {
        this.xmywlx = xmywlx;
    }

    public String getSpxtywh() {
        return spxtywh;
    }

    public void setSpxtywh(String spxtywh) {
        this.spxtywh = spxtywh;
    }

    public Integer getSfsczs() {
        return sfsczs;
    }

    public void setSfsczs(Integer sfsczs) {
        this.sfsczs = sfsczs;
    }

    public Date getTsswsj() {
        return tsswsj;
    }

    public void setTsswsj(Date tsswsj) {
        this.tsswsj = tsswsj;
    }

    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    public String getQjqlrgxid() {
        return qjqlrgxid;
    }

    public void setQjqlrgxid(String qjqlrgxid) {
        this.qjqlrgxid = qjqlrgxid;
    }

    public Double getDzwmj() {
        return dzwmj;
    }

    public void setDzwmj(Double dzwmj) {
        this.dzwmj = dzwmj;
    }

    public String getFwuuid() {
        return fwuuid;
    }

    public void setFwuuid(String fwuuid) {
        this.fwuuid = fwuuid;
    }


    public String getDzwytmc() {
        return dzwytmc;
    }

    public void setDzwytmc(String dzwytmc) {
        this.dzwytmc = dzwytmc;
    }

    @Override
    public String toString() {
        return "BdcSlXmDO{" +
                "xmid='" + xmid + '\'' +
                ", jbxxid='" + jbxxid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", qjid='" + qjid + '\'' +
                ", bdclx=" + bdclx +
                ", qllx=" + qllx +
                ", djxl='" + djxl + '\'' +
                ", ybdcqz='" + ybdcqz + '\'' +
                ", zl='" + zl + '\'' +
                ", qlr='" + qlr + '\'' +
                ", dyje=" + dyje +
                ", dyfs=" + dyfs +
                ", zwlxqssj=" + zwlxqssj +
                ", zwlxjssj=" + zwlxjssj +
                ", zjjzwzl='" + zjjzwzl + '\'' +
                ", zjjzwdyfw='" + zjjzwdyfw + '\'' +
                ", zgzqqdss='" + zgzqqdss + '\'' +
                ", dbfw='" + dbfw + '\'' +
                ", dkfs='" + dkfs + '\'' +
                ", bdbzzqse=" + bdbzzqse +
                ", fwpgjg=" + fwpgjg +
                ", tdpgjg=" + tdpgjg +
                ", ygdjzl=" + ygdjzl +
                ", qdjg=" + qdjg +
                ", gyqk='" + gyqk + '\'' +
                ", ygzwlxqssj=" + ygzwlxqssj +
                ", ygzwlxjssj=" + ygzwlxjssj +
                ", ygdyfs=" + ygdyfs +
                ", yysx='" + yysx + '\'' +
                ", syqmj=" + syqmj +
                ", ldsyksqx=" + ldsyksqx +
                ", ldsyjsqx=" + ldsyjsqx +
                ", ldsyqxz=" + ldsyqxz +
                ", sllmsyqr1='" + sllmsyqr1 + '\'' +
                ", sllmsyqr2='" + sllmsyqr2 + '\'' +
                ", zysz='" + zysz + '\'' +
                ", zs=" + zs +
                ", lz=" + lz +
                ", qy=" + qy +
                ", zlnd=" + zlnd +
                ", xdm='" + xdm + '\'' +
                ", lb='" + lb + '\'' +
                ", xb='" + xb + '\'' +
                ", zdzhyt='" + zdzhyt + '\'' +
                ", zdzhmj=" + zdzhmj +
                ", xdyzl='" + xdyzl + '\'' +
                ", xdybdcdyh='" + xdybdcdyh + '\'' +
                ", qlrsjly=" + qlrsjly +
                ", ywrsjly=" + ywrsjly +
                ", sfscql=" + sfscql +
                ", qlsjly='" + qlsjly + '\'' +
                ", zszl=" + zszl +
                ", zsxh=" + zsxh +
                ", sfzlcsh=" + sfzlcsh +
                ", bdcdyfwlx=" + bdcdyfwlx +
                ", czrid='" + czrid + '\'' +
                ", czsj=" + czsj +
                ", sfzf=" + sfzf +
                ", sfhyyzxql=" + sfhyyzxql +
                ", sfhz=" + sfhz +
                ", djyy='" + djyy + '\'' +
                ", sffc=" + sffc +
                ", bz='" + bz + '\'' +
                ", sqfbcz=" + sqfbcz +
                ", zdzhyt2='" + zdzhyt2 + '\'' +
                ", zdzhyt3='" + zdzhyt3 + '\'' +
                ", qlxz='" + qlxz + '\'' +
                ", xmywlx=" + xmywlx +
                ", spxtywh='" + spxtywh + '\'' +
                ", sfsczs=" + sfsczs +
                ", tsswsj=" + tsswsj +
                ", yt='" + yt + '\'' +
                ", qjgldm='" + qjgldm + '\'' +
                ", qjqlrgxid='" + qjqlrgxid + '\'' +
                ", dzwmj=" + dzwmj +
                ", dagsd='" + dagsd + '\'' +
                ", fwuuid='" + fwuuid + '\'' +
                ", dzwytmc='" + dzwytmc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof BdcSlXmDO)){
            return false;
        }
        BdcSlXmDO bdcSlXmDO = (BdcSlXmDO) o;
        return Objects.equals(xmid, bdcSlXmDO.xmid) && Objects.equals(jbxxid, bdcSlXmDO.jbxxid) && Objects.equals(bdcdyh, bdcSlXmDO.bdcdyh) && Objects.equals(qjid, bdcSlXmDO.qjid) && Objects.equals(bdclx, bdcSlXmDO.bdclx) && Objects.equals(qllx, bdcSlXmDO.qllx) && Objects.equals(djxl, bdcSlXmDO.djxl) && Objects.equals(ybdcqz, bdcSlXmDO.ybdcqz) && Objects.equals(zl, bdcSlXmDO.zl) && Objects.equals(qlr, bdcSlXmDO.qlr) && Objects.equals(dyje, bdcSlXmDO.dyje) && Objects.equals(dyfs, bdcSlXmDO.dyfs) && Objects.equals(zwlxqssj, bdcSlXmDO.zwlxqssj) && Objects.equals(zwlxjssj, bdcSlXmDO.zwlxjssj) && Objects.equals(zjjzwzl, bdcSlXmDO.zjjzwzl) && Objects.equals(zjjzwdyfw, bdcSlXmDO.zjjzwdyfw) && Objects.equals(zgzqqdss, bdcSlXmDO.zgzqqdss) && Objects.equals(dbfw, bdcSlXmDO.dbfw) && Objects.equals(dkfs, bdcSlXmDO.dkfs) && Objects.equals(bdbzzqse, bdcSlXmDO.bdbzzqse) && Objects.equals(fwpgjg, bdcSlXmDO.fwpgjg) && Objects.equals(tdpgjg, bdcSlXmDO.tdpgjg) && Objects.equals(ygdjzl, bdcSlXmDO.ygdjzl) && Objects.equals(qdjg, bdcSlXmDO.qdjg) && Objects.equals(gyqk, bdcSlXmDO.gyqk) && Objects.equals(ygzwlxqssj, bdcSlXmDO.ygzwlxqssj) && Objects.equals(ygzwlxjssj, bdcSlXmDO.ygzwlxjssj) && Objects.equals(ygdyfs, bdcSlXmDO.ygdyfs) && Objects.equals(yysx, bdcSlXmDO.yysx) && Objects.equals(syqmj, bdcSlXmDO.syqmj) && Objects.equals(ldsyksqx, bdcSlXmDO.ldsyksqx) && Objects.equals(ldsyjsqx, bdcSlXmDO.ldsyjsqx) && Objects.equals(ldsyqxz, bdcSlXmDO.ldsyqxz) && Objects.equals(sllmsyqr1, bdcSlXmDO.sllmsyqr1) && Objects.equals(sllmsyqr2, bdcSlXmDO.sllmsyqr2) && Objects.equals(zysz, bdcSlXmDO.zysz) && Objects.equals(zs, bdcSlXmDO.zs) && Objects.equals(lz, bdcSlXmDO.lz) && Objects.equals(qy, bdcSlXmDO.qy) && Objects.equals(zlnd, bdcSlXmDO.zlnd) && Objects.equals(xdm, bdcSlXmDO.xdm) && Objects.equals(lb, bdcSlXmDO.lb) && Objects.equals(xb, bdcSlXmDO.xb) && Objects.equals(zdzhyt, bdcSlXmDO.zdzhyt) && Objects.equals(zdzhmj, bdcSlXmDO.zdzhmj) && Objects.equals(xdyzl, bdcSlXmDO.xdyzl) && Objects.equals(xdybdcdyh, bdcSlXmDO.xdybdcdyh) && Objects.equals(qlrsjly, bdcSlXmDO.qlrsjly) && Objects.equals(ywrsjly, bdcSlXmDO.ywrsjly) && Objects.equals(sfscql, bdcSlXmDO.sfscql) && Objects.equals(qlsjly, bdcSlXmDO.qlsjly) && Objects.equals(zszl, bdcSlXmDO.zszl) && Objects.equals(zsxh, bdcSlXmDO.zsxh) && Objects.equals(sfzlcsh, bdcSlXmDO.sfzlcsh) && Objects.equals(bdcdyfwlx, bdcSlXmDO.bdcdyfwlx) && Objects.equals(czrid, bdcSlXmDO.czrid) && Objects.equals(czsj, bdcSlXmDO.czsj) && Objects.equals(sfzf, bdcSlXmDO.sfzf) && Objects.equals(sfhyyzxql, bdcSlXmDO.sfhyyzxql) && Objects.equals(sfhz, bdcSlXmDO.sfhz) && Objects.equals(djyy, bdcSlXmDO.djyy) && Objects.equals(sffc, bdcSlXmDO.sffc) && Objects.equals(bz, bdcSlXmDO.bz) && Objects.equals(sqfbcz, bdcSlXmDO.sqfbcz) && Objects.equals(zdzhyt2, bdcSlXmDO.zdzhyt2) && Objects.equals(zdzhyt3, bdcSlXmDO.zdzhyt3) && Objects.equals(qlxz, bdcSlXmDO.qlxz) && Objects.equals(xmywlx, bdcSlXmDO.xmywlx) && Objects.equals(spxtywh, bdcSlXmDO.spxtywh) && Objects.equals(sfsczs, bdcSlXmDO.sfsczs) && Objects.equals(tsswsj, bdcSlXmDO.tsswsj) && Objects.equals(yt, bdcSlXmDO.yt) && Objects.equals(qjgldm, bdcSlXmDO.qjgldm) && Objects.equals(qjqlrgxid, bdcSlXmDO.qjqlrgxid) && Objects.equals(dzwmj, bdcSlXmDO.dzwmj) && Objects.equals(dagsd, bdcSlXmDO.dagsd) && Objects.equals(fwuuid, bdcSlXmDO.fwuuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xmid, jbxxid, bdcdyh, qjid, bdclx, qllx, djxl, ybdcqz, zl, qlr, dyje, dyfs, zwlxqssj, zwlxjssj, zjjzwzl, zjjzwdyfw, zgzqqdss, dbfw, dkfs, bdbzzqse, fwpgjg, tdpgjg, ygdjzl, qdjg, gyqk, ygzwlxqssj, ygzwlxjssj, ygdyfs, yysx, syqmj, ldsyksqx, ldsyjsqx, ldsyqxz, sllmsyqr1, sllmsyqr2, zysz, zs, lz, qy, zlnd, xdm, lb, xb, zdzhyt, zdzhmj, xdyzl, xdybdcdyh, qlrsjly, ywrsjly, sfscql, qlsjly, zszl, zsxh, sfzlcsh, bdcdyfwlx, czrid, czsj, sfzf, sfhyyzxql, sfhz, djyy, sffc, bz, sqfbcz, zdzhyt2, zdzhyt3, qlxz, xmywlx, spxtywh, sfsczs, tsswsj, yt, qjgldm, qjqlrgxid, dzwmj, dagsd, fwuuid);
    }
}
