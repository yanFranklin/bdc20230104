package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 历史关系模型
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description
 */
public class LsgxModelDTO {

    /**数据主键 不动产为proid*/
    private String id;
    /**上一手业务主键 不动产为proid*/
    private String yid;
    /** 权利类型字典项*/
    private Integer qllx;
    /** 业务的登记时间或者登薄时间*/
    private Date cjsj;
    /** 添加的权利信息明细 json*/
    private String qlxx;
    /** 登记类型字典项*/
    private Integer djlx;
    /** 产权证号*/
    private String bdcqzh;
    /** 权属状态*/
    private Integer qszt;
    /** 登薄人*/
    private String dbr;
    /** 登薄时间*/
    private Date djsj;
    /** 项目信息 */
    private BdcXmDO bdcXmDO;

    /** 不动产单元房屋类型 */
    private Integer bdcdyfwlx;

    /** 权利信息 */
    private BdcQl bdcQl;

    /** 产权的id*/
    private String cqid;

    /**
     * 显示信息
     * 说明：盐城历史关系展示需要按照类似 现势/历史_权利人_登记时间 格式展示，该字段用于拼接展示相关内容
     */
    private String xsxx;

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  锁定状态
      */
    private Integer sdzt;

    public BdcXmDO getBdcXmDO() {
        return bdcXmDO;
    }

    public void setBdcXmDO(BdcXmDO bdcXmDO) {
        this.bdcXmDO = bdcXmDO;
    }

    public BdcQl getBdcQl() {
        return bdcQl;
    }

    public void setBdcQl(BdcQl bdcQl) {
        this.bdcQl = bdcQl;
    }

    public String getXsxx() {
        return xsxx;
    }

    public void setXsxx(String xsxx) {
        this.xsxx = xsxx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYid() {
        return yid;
    }

    public void setYid(String yid) {
        this.yid = yid;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getQlxx() {
        return qlxx;
    }

    public void setQlxx(String qlxx) {
        this.qlxx = qlxx;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getCqid() {
        return cqid;
    }

    public void setCqid(String cqid) {
        this.cqid = cqid;
    }

    public Integer getSdzt() {
        return sdzt;
    }

    public void setSdzt(Integer sdzt) {
        this.sdzt = sdzt;
    }

    /**
     * 获取权属状态汉字
     * @param lsgx 历史关系信息
     * @return 权属状态汉字
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private String getQsztMc(LsgxModelDTO lsgx) {
        if(null == lsgx || null == lsgx.getBdcXmDO() || null == lsgx.getBdcXmDO().getQszt()) {
            return "";
        }

        switch (lsgx.getBdcXmDO().getQszt().intValue()) {
            case 0: return "临时";
            case 1: return "现势";
            case 2: return "历史";
            case 3: return "终止";
            default:
        }
        return "";
    }

    public LsgxModelDTO resolveLsgxXsxx() {
        String djsjStr = this.getBdcXmDO().getDjsj() == null?"": DateFormatUtils.format(this.getBdcXmDO().getDjsj(), "yyyy-MM-dd");
        setXsxx(this.getQsztMc(this)+ "_" + this.bdcqzh + "_" +  djsjStr);
        return this;
    }

    public Integer getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(Integer bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LsgxModelDTO that = (LsgxModelDTO) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }



}
