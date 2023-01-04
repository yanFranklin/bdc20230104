package cn.gtmap.realestate.etl.core.domian.wwsq;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/06/01,1.0
 * @description 外网申请实体do
 */
@Table(name = "gx_ww_swxx")
public class GxWwSwxxDo {

    /**
     * 税务id
     */
    @Id
    private String swid;
    /**
     * 受理编号
     */
    private String slbh;
    /**
     * 申请id
     */
    private String sqid;
    /**
     * 系统税票号码
     */
    private String xtsphm;
    /**
     * 电子税票号码
     */
    private String dzsphm;
    /**
     * 合同编号
     */
    private String htbh;
    /**
     * 凭证种类代码
     */
    private String pzzldm;
    /**
     * 凭证种类名称
     */
    private String pzzlmc;
    /**
     * 票证子规代码
     */
    private String pzzgdm;
    /**
     * 票证子规名称
     */
    private String pzzgmc;
    /**
     * 票证号码
     */
    private String pzhm;
    /**
     * 税款所属期起
     */
    private Date skssqq;
    /**
     * 税款所属期止
     */
    private Date skssqz;
    /**
     * 主管税务机关科分局代码
     */
    private String zgswskfjdm;
    /**
     * 主管税务机关科分局名
     */
    private String zgswskfjmc;
    /**
     * 征收税务机关代码
     */
    private String zsswjgdm;
    /**
     * 征收税务机关名称
     */
    private String zsswjgmc;
    /**
     * 税款所属机关代码
     */
    private String skssswjgdm;
    /**
     * 税款所属机关名称
     */
    private String skssswjgmc;
    /**
     * 扣缴日期
     */
    private Date kjrq;
    /**
     * 备注
     */
    private String bz;
    /**
     * 创建人（user表关联user_guid）
     */
    private String cjruser;
    /**
     * 创建单位（关联部门id，orgid）
     */
    private String cjrorgid;
    /**
     * 创建时间
     */
    private Date cjsj;
    /**
     * 纳税人
     */
    private String nsrmc;
    /**
     * 纳税人识别号
     */
    private String nsrsbh;
    /**
     * 转让方承受方标志（ 0转让方  1承受方
     */
    private String zrfcsfbz;
    /**
     * 合计
     */
    private String hj;
    @Transient
    private List<GxWwSwmxDo> swmx;

    public List<GxWwSwmxDo> getSwmx() {
        return swmx;
    }

    public void setSwmx(List<GxWwSwmxDo> swmx) {
        this.swmx = swmx;
    }

    public String getSwid() {
        return swid;
    }

    public void setSwid(String swid) {
        this.swid = swid;
    }


    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }


    public String getSqid() {
        return sqid;
    }

    public void setSqid(String sqid) {
        this.sqid = sqid;
    }


    public String getXtsphm() {
        return xtsphm;
    }

    public void setXtsphm(String xtsphm) {
        this.xtsphm = xtsphm;
    }


    public String getDzsphm() {
        return dzsphm;
    }

    public void setDzsphm(String dzsphm) {
        this.dzsphm = dzsphm;
    }


    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }


    public String getPzzldm() {
        return pzzldm;
    }

    public void setPzzldm(String pzzldm) {
        this.pzzldm = pzzldm;
    }


    public String getPzzlmc() {
        return pzzlmc;
    }

    public void setPzzlmc(String pzzlmc) {
        this.pzzlmc = pzzlmc;
    }


    public String getPzzgdm() {
        return pzzgdm;
    }

    public void setPzzgdm(String pzzgdm) {
        this.pzzgdm = pzzgdm;
    }


    public String getPzzgmc() {
        return pzzgmc;
    }

    public void setPzzgmc(String pzzgmc) {
        this.pzzgmc = pzzgmc;
    }


    public String getPzhm() {
        return pzhm;
    }

    public void setPzhm(String pzhm) {
        this.pzhm = pzhm;
    }


    public Date getSkssqq() {
        return skssqq;
    }

    public void setSkssqq(Date skssqq) {
        this.skssqq = skssqq;
    }


    public Date getSkssqz() {
        return skssqz;
    }

    public void setSkssqz(Date skssqz) {
        this.skssqz = skssqz;
    }


    public String getZgswskfjdm() {
        return zgswskfjdm;
    }

    public void setZgswskfjdm(String zgswskfjdm) {
        this.zgswskfjdm = zgswskfjdm;
    }


    public String getZgswskfjmc() {
        return zgswskfjmc;
    }

    public void setZgswskfjmc(String zgswskfjmc) {
        this.zgswskfjmc = zgswskfjmc;
    }


    public String getZsswjgdm() {
        return zsswjgdm;
    }

    public void setZsswjgdm(String zsswjgdm) {
        this.zsswjgdm = zsswjgdm;
    }


    public String getZsswjgmc() {
        return zsswjgmc;
    }

    public void setZsswjgmc(String zsswjgmc) {
        this.zsswjgmc = zsswjgmc;
    }


    public String getSkssswjgdm() {
        return skssswjgdm;
    }

    public void setSkssswjgdm(String skssswjgdm) {
        this.skssswjgdm = skssswjgdm;
    }


    public String getSkssswjgmc() {
        return skssswjgmc;
    }

    public void setSkssswjgmc(String skssswjgmc) {
        this.skssswjgmc = skssswjgmc;
    }


    public Date getKjrq() {
        return kjrq;
    }

    public void setKjrq(Date kjrq) {
        this.kjrq = kjrq;
    }


    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }


    public String getCjruser() {
        return cjruser;
    }

    public void setCjruser(String cjruser) {
        this.cjruser = cjruser;
    }


    public String getCjrorgid() {
        return cjrorgid;
    }

    public void setCjrorgid(String cjrorgid) {
        this.cjrorgid = cjrorgid;
    }


    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }


    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }


    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }


    public String getZrfcsfbz() {
        return zrfcsfbz;
    }

    public void setZrfcsfbz(String zrfcsfbz) {
        this.zrfcsfbz = zrfcsfbz;
    }


    public String getHj() {
        return hj;
    }

    public void setHj(String hj) {
        this.hj = hj;
    }

}
