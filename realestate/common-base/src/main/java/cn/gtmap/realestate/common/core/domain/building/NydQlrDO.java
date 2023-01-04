package cn.gtmap.realestate.common.core.domain.building;

import cn.gtmap.realestate.common.core.annotations.Zd;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/11
 * @description 农用地权利人
 */
@Table(name = "nyd_qlr")
public class NydQlrDO {
    /**
     * 主键
     */
    @Id
    @ApiModelProperty(value = "主键")
    private String nydQlrIndex;
    /**
     * 调查主表主键
     */
    @ApiModelProperty(value = "调查主表主键")
    private String djdcbIndex;
    /**
     * 地籍号
     */
    @ApiModelProperty(value = "地籍号")
    private String djh;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer xh;
    /**
     * 权利人名称
     */
    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;
    /**
     * 权利人类型
     */
    @ApiModelProperty(value = "权利人类型")
    private String qlrlx;
    /**
     * 权利人证件类型
     */
    @Zd(tableClass = SZdZjllxDO.class)
    @ApiModelProperty(value = "权利人证件类型")
    private String qlrzjlx;
    /**
     * 权利人证件号
     */
    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;
    /**
     * 权属单位代码
     */
    @ApiModelProperty(value = "权属单位代码")
    private String qsdwdm;
    /**
     * 单位性质
     */

    @ApiModelProperty(value = "单位性质")
    private String dwxz;
    /**
     * 上级主管部门
     */
    @ApiModelProperty(value = "上级主管部门")
    private String sjzgbm;
    /**
     * 面积单位
     */
    @ApiModelProperty(value = "面积单位")
    private String mjdw;
    /**
     * 使用面积
     */
    @ApiModelProperty(value = "使用面积")
    private Double symj;
    /**
     * 独用面积
     */
    @ApiModelProperty(value = "独用面积")
    private Double dymj;
    /**
     * 分摊面积
     */
    @ApiModelProperty(value = "分摊面积")
    private Double ftmj;
    /**
     * 通讯地址
     */
    @ApiModelProperty(value = "通讯地址")
    private String txdz;
    /**
     * 法人代表姓名
     */
    @ApiModelProperty(value = "法人代表姓名")
    private String frdbxm;
    /**
     * 法人代表证件类型
     */
    @Zd(tableClass = SZdZjllxDO.class)
    @ApiModelProperty(value = "法人代表证件类型")
    private String frdbzjlx;
    /**
     * 法人代表证件号
     */
    @ApiModelProperty(value = "法人代表证件号")
    private String frdbzjh;
    /**
     * 法人代表身份证明书
     */
    @ApiModelProperty(value = "法人代表身份证明书")
    private String frdbsfzms;
    /**
     * 法人代表电话号码
     */
    @ApiModelProperty(value = "法人代表电话号码")
    private String frdbdhhm;
    /**
     * 代理人姓名
     */
    @ApiModelProperty(value = "代理人姓名")
    private String dlrxm;
    /**
     * 代理人证件类型
     */
    @Zd(tableClass = SZdZjllxDO.class)
    @ApiModelProperty(value = "代理人证件类型")
    private String dlrzjlx;
    /**
     * 代理人证件号
     */
    @ApiModelProperty(value = "代理人证件号")
    private String dlrzjh;
    /**
     * 代理人身份证明书
     */
    @ApiModelProperty(value = "代理人身份证明书")
    private String dlrsfzms;
    /**
     * 代理人电话号码
     */
    @ApiModelProperty(value = "代理人电话号码")
    private String dlrdhhm;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;
    /**
     * 建立日期
     */
    @ApiModelProperty(value = "建立日期",example = "2018-10-01 12:18:48")
    private Date jlrq;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期",example = "2018-10-01 12:18:48")
    private Date gxrq;
    /**
     * 是否林地使用权人
     */
    @ApiModelProperty(value = "是否林地使用权人")
    private String sfldsyqr;
    /**
     * 是否林木所有权人
     */
    @ApiModelProperty(value = "是否林木所有权人")
    private String sflmsuqr;
    /**
     * 是否林木使用权人
     */
    @ApiModelProperty(value = "是否林木使用权人")
    private String sflmsyqr;

    /**
     * 权利人特征
     */
    @ApiModelProperty(value = "权利人特征")
    private String qlrtz;

    public String getNydQlrIndex() {
        return nydQlrIndex;
    }

    public void setNydQlrIndex(String nydQlrIndex) {
        this.nydQlrIndex = nydQlrIndex;
    }

    public String getDjdcbIndex() {
        return djdcbIndex;
    }

    public void setDjdcbIndex(String djdcbIndex) {
        this.djdcbIndex = djdcbIndex;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    public String getQlrzjlx() {
        return qlrzjlx;
    }

    public void setQlrzjlx(String qlrzjlx) {
        this.qlrzjlx = qlrzjlx;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQsdwdm() {
        return qsdwdm;
    }

    public void setQsdwdm(String qsdwdm) {
        this.qsdwdm = qsdwdm;
    }

    public String getDwxz() {
        return dwxz;
    }

    public void setDwxz(String dwxz) {
        this.dwxz = dwxz;
    }

    public String getSjzgbm() {
        return sjzgbm;
    }

    public void setSjzgbm(String sjzgbm) {
        this.sjzgbm = sjzgbm;
    }

    public String getMjdw() {
        return mjdw;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    public Double getSymj() {
        return symj;
    }

    public void setSymj(Double symj) {
        this.symj = symj;
    }

    public Double getDymj() {
        return dymj;
    }

    public void setDymj(Double dymj) {
        this.dymj = dymj;
    }

    public Double getFtmj() {
        return ftmj;
    }

    public void setFtmj(Double ftmj) {
        this.ftmj = ftmj;
    }

    public String getTxdz() {
        return txdz;
    }

    public void setTxdz(String txdz) {
        this.txdz = txdz;
    }

    public String getFrdbxm() {
        return frdbxm;
    }

    public void setFrdbxm(String frdbxm) {
        this.frdbxm = frdbxm;
    }

    public String getFrdbzjlx() {
        return frdbzjlx;
    }

    public void setFrdbzjlx(String frdbzjlx) {
        this.frdbzjlx = frdbzjlx;
    }

    public String getFrdbzjh() {
        return frdbzjh;
    }

    public void setFrdbzjh(String frdbzjh) {
        this.frdbzjh = frdbzjh;
    }

    public String getFrdbsfzms() {
        return frdbsfzms;
    }

    public void setFrdbsfzms(String frdbsfzms) {
        this.frdbsfzms = frdbsfzms;
    }

    public String getFrdbdhhm() {
        return frdbdhhm;
    }

    public void setFrdbdhhm(String frdbdhhm) {
        this.frdbdhhm = frdbdhhm;
    }

    public String getDlrxm() {
        return dlrxm;
    }

    public void setDlrxm(String dlrxm) {
        this.dlrxm = dlrxm;
    }

    public String getDlrzjlx() {
        return dlrzjlx;
    }

    public void setDlrzjlx(String dlrzjlx) {
        this.dlrzjlx = dlrzjlx;
    }

    public String getDlrzjh() {
        return dlrzjh;
    }

    public void setDlrzjh(String dlrzjh) {
        this.dlrzjh = dlrzjh;
    }

    public String getDlrsfzms() {
        return dlrsfzms;
    }

    public void setDlrsfzms(String dlrsfzms) {
        this.dlrsfzms = dlrsfzms;
    }

    public String getDlrdhhm() {
        return dlrdhhm;
    }

    public void setDlrdhhm(String dlrdhhm) {
        this.dlrdhhm = dlrdhhm;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getJlrq() {
        return jlrq;
    }

    public void setJlrq(Date jlrq) {
        this.jlrq = jlrq;
    }

    public Date getGxrq() {
        return gxrq;
    }

    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }

    public String getSfldsyqr() {
        return sfldsyqr;
    }

    public void setSfldsyqr(String sfldsyqr) {
        this.sfldsyqr = sfldsyqr;
    }

    public String getSflmsuqr() {
        return sflmsuqr;
    }

    public void setSflmsuqr(String sflmsuqr) {
        this.sflmsuqr = sflmsuqr;
    }

    public String getSflmsyqr() {
        return sflmsyqr;
    }

    public void setSflmsyqr(String sflmsyqr) {
        this.sflmsyqr = sflmsyqr;
    }

    public String getQlrtz() {
        return qlrtz;
    }

    public void setQlrtz(String qlrtz) {
        this.qlrtz = qlrtz;
    }

    @Override
    public String toString() {
        return "NydQlrDO{" +
                "nydQlrIndex='" + nydQlrIndex + '\'' +
                ", djdcbIndex='" + djdcbIndex + '\'' +
                ", djh='" + djh + '\'' +
                ", xh=" + xh +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrlx='" + qlrlx + '\'' +
                ", qlrzjlx='" + qlrzjlx + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qsdwdm='" + qsdwdm + '\'' +
                ", dwxz='" + dwxz + '\'' +
                ", sjzgbm='" + sjzgbm + '\'' +
                ", mjdw='" + mjdw + '\'' +
                ", symj=" + symj +
                ", dymj=" + dymj +
                ", ftmj=" + ftmj +
                ", txdz='" + txdz + '\'' +
                ", frdbxm='" + frdbxm + '\'' +
                ", frdbzjlx='" + frdbzjlx + '\'' +
                ", frdbzjh='" + frdbzjh + '\'' +
                ", frdbsfzms='" + frdbsfzms + '\'' +
                ", frdbdhhm='" + frdbdhhm + '\'' +
                ", dlrxm='" + dlrxm + '\'' +
                ", dlrzjlx='" + dlrzjlx + '\'' +
                ", dlrzjh='" + dlrzjh + '\'' +
                ", dlrsfzms='" + dlrsfzms + '\'' +
                ", dlrdhhm='" + dlrdhhm + '\'' +
                ", bz='" + bz + '\'' +
                ", jlrq=" + jlrq +
                ", gxrq=" + gxrq +
                ", sfldsyqr='" + sfldsyqr + '\'' +
                ", sflmsuqr='" + sflmsuqr + '\'' +
                ", sflmsyqr='" + sflmsyqr + '\'' +
                ", qlrtz='" + qlrtz + '\'' +
                '}';
    }
}