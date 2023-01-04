package cn.gtmap.realestate.etl.core.domian;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.BdcZdQllxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdQlxzDO;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/06/29,1.0
 * @description 大数据局共享实体do
 */
@Table(name = "ytt_bdcqzs")
public class YttBdcqzsDo {

    /**
     * 证书序号
     */
    private String certid;
    /**
     * 业务号
     */
    private String activeid;
    /**
     * 是否有效（是；否）
     */
    private String sfyx;
    /**
     * 缮证时间
     */
    private Date zssj;
    /**
     * 注销时间
     */
    private Date zxsj;
    /**
     * 不动产权证号
     */
    private String certno;
    /**
     * 权利人
     */
    private String qlr;
    /**
     * 共有情况
     */
    private String gyqk;
    /**
     * 坐落
     */
    private String zl;
    /**
     * 不动产单元号
     */
    private String bdcdyh;
    /**
     * 权利类型
     */
    @Zd(tableClass = BdcZdQllxDO.class)
    private String qllx;
    /**
     * 权利性质
     */
    @Zd(tableClass = BdcZdQlxzDO.class)
    private String qlxz;
    /**
     * 用途
     */
    private String yt;
    /**
     * 面积
     */
    private String mj;
    /**
     * 使用期限
     */
    private String syqx;
    /**
     * 权利其他状况
     */
    private String qlqtzk;
    /**
     * 权证附记
     */
    private String qzfj;
    /**
     * 插入时间
     */
    private Date crsj;
    /**
     * 更新时间
     */
    private Date gxsj;
    /**
     * 数据来源
     */
    private String sjly;
    /**
     * 主键ID
     */
    @Id
    private Long id;
    /**
     * 证件类型
     */
    private String zjlx;
    /**
     * 证件号码
     */
    private String zjhm;
    /**
     * 印刷编号
     */
    private String ysbh;
    /**
     * 登记机构
     */
    private String sbzz;
    /**
     * 所属乡镇
     */
    private String ssxz;


    public String getCertid() {
        return certid;
    }

    public void setCertid(String certid) {
        this.certid = certid;
    }


    public String getActiveid() {
        return activeid;
    }

    public void setActiveid(String activeid) {
        this.activeid = activeid;
    }


    public String getSfyx() {
        return sfyx;
    }

    public void setSfyx(String sfyx) {
        this.sfyx = sfyx;
    }


    public Date getZssj() {
        return zssj;
    }

    public void setZssj(Date zssj) {
        this.zssj = zssj;
    }


    public Date getZxsj() {
        return zxsj;
    }

    public void setZxsj(Date zxsj) {
        this.zxsj = zxsj;
    }


    public String getCertno() {
        return certno;
    }

    public void setCertno(String certno) {
        this.certno = certno;
    }


    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }


    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }


    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }


    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }


    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }


    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }


    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }


    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }


    public String getSyqx() {
        return syqx;
    }

    public void setSyqx(String syqx) {
        this.syqx = syqx;
    }


    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }


    public String getQzfj() {
        return qzfj;
    }

    public void setQzfj(String qzfj) {
        this.qzfj = qzfj;
    }


    public Date getCrsj() {
        return crsj;
    }

    public void setCrsj(Date crsj) {
        this.crsj = crsj;
    }


    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }


    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }


    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }


    public String getYsbh() {
        return ysbh;
    }

    public void setYsbh(String ysbh) {
        this.ysbh = ysbh;
    }


    public String getSbzz() {
        return sbzz;
    }

    public void setSbzz(String sbzz) {
        this.sbzz = sbzz;
    }


    public String getSsxz() {
        return ssxz;
    }

    public void setSsxz(String ssxz) {
        this.ssxz = ssxz;
    }

}
