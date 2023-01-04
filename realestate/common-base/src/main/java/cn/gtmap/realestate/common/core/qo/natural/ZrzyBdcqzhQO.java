package cn.gtmap.realestate.common.core.qo.natural;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsbhmbDO;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/19
 * @description  不动产权证号业务BO实体定义
 */
public class ZrzyBdcqzhQO {
    /**
     * 项目ID
     */
    private String xmid;
    /**
     * 证书ID
     */
    private String zsid;
    /**
     * 证书类型
     */
    private Integer zslx;
    /**
     * 年份
     */
    private String nf;
    /**
     * 区县代码
     */
    private String qxdm;
    /**
     * 区县代码（共用一个流水号区划组合）
     */
    private String qxdms;
    /**
     * 不动产权证号位数
     */
    private Integer bdcqzhws;
    /**
     * 省区市简称
     */
    private String sqsjc;
    /**
     * 所在市县全称
     */
    private String szsxqc;
    /**
     * 省区代码
     */
    private String sqdm;
    /**
     * 顺序号
     */
    private Integer sxh;
    /**
     * 初始顺序号
     */
    private Integer cssxh;
    /**
     * 预留证号开关
     */
    private Integer ylzhkg;
    /**
     * 证书废号开关
     */
    private Integer zsfhkg;
    /**
     * 拼接省区代码开关
     */
    private Integer sqdmkg;
    /**
     * 登记部门代码
     */
    private String djbmdm;
    /**
     * 证号区分部门
     */
    private Integer zhqfbm;
    /**
     * 证号区分区县代码
     */
    private Integer zhqfqxdm;


    public ZrzyBdcqzhQO(){}

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param   bdcXmDO 不动产项目信息实体
     * @param   bdcXtZsbhmbDO 证号模板
     * @return  {BdcBdcqzhBO} 证号业务BO
     * @description  封装证号业务处理BO
     */
    public ZrzyBdcqzhQO(BdcXmDO bdcXmDO, BdcXtZsbhmbDO bdcXtZsbhmbDO) {
        this.qxdm = bdcXmDO.getQxdm();
        this.djbmdm = bdcXmDO.getDjbmdm();

        this.nf = bdcXtZsbhmbDO.getNf();
        this.bdcqzhws = bdcXtZsbhmbDO.getBdcqzhws();
        this.sqsjc = bdcXtZsbhmbDO.getSqsjc();
        this.szsxqc = bdcXtZsbhmbDO.getSzsxqc();
        this.sqdm = bdcXtZsbhmbDO.getSqdm();

        // 初始顺序号设置默认值
        this.cssxh = null == bdcXtZsbhmbDO.getCssxh()? Integer.valueOf(1) : bdcXtZsbhmbDO.getCssxh();
        // 开关项如果数据库没有设置，则设置默认值：否
        this.ylzhkg = null == bdcXtZsbhmbDO.getYlzhkg()? Integer.valueOf(0) : bdcXtZsbhmbDO.getYlzhkg();
        this.zsfhkg = null == bdcXtZsbhmbDO.getZsfhkg()? Integer.valueOf(0) : bdcXtZsbhmbDO.getZsfhkg();
        this.sqdmkg = null == bdcXtZsbhmbDO.getSqdmkg()? Integer.valueOf(0) : bdcXtZsbhmbDO.getSqdmkg();
    }

    public Integer getZhqfbm() {
        return zhqfbm;
    }

    public void setZhqfbm(Integer zhqfbm) {
        this.zhqfbm = zhqfbm;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Integer getBdcqzhws() {
        return bdcqzhws;
    }

    public void setBdcqzhws(Integer bdcqzhws) {
        this.bdcqzhws = bdcqzhws;
    }

    public String getSqsjc() {
        return sqsjc;
    }

    public void setSqsjc(String sqsjc) {
        this.sqsjc = sqsjc;
    }

    public String getSzsxqc() {
        return szsxqc;
    }

    public void setSzsxqc(String szsxqc) {
        this.szsxqc = szsxqc;
    }

    public String getSqdm() {
        return sqdm;
    }

    public void setSqdm(String sqdm) {
        this.sqdm = sqdm;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public Integer getCssxh() {
        return cssxh;
    }

    public void setCssxh(Integer cssxh) {
        this.cssxh = cssxh;
    }

    public Integer getYlzhkg() {
        return ylzhkg;
    }

    public void setYlzhkg(Integer ylzhkg) {
        this.ylzhkg = ylzhkg;
    }

    public Integer getZsfhkg() {
        return zsfhkg;
    }

    public void setZsfhkg(Integer zsfhkg) {
        this.zsfhkg = zsfhkg;
    }

    public Integer getSqdmkg() {
        return sqdmkg;
    }

    public void setSqdmkg(Integer sqdmkg) {
        this.sqdmkg = sqdmkg;
    }

    public String getDjbmdm() {
        return djbmdm;
    }

    public void setDjbmdm(String djbmdm) {
        this.djbmdm = djbmdm;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getZhqfqxdm() {
        return zhqfqxdm;
    }

    public void setZhqfqxdm(Integer zhqfqxdm) {
        this.zhqfqxdm = zhqfqxdm;
    }

    public String getQxdms() {
        return qxdms;
    }

    public void setQxdms(String qxdms) {
        this.qxdms = qxdms;
    }

    @Override
    public String toString() {
        return "BdcBdcqzhBO{" +
                "xmid='" + xmid + '\'' +
                ", zsid='" + zsid + '\'' +
                ", zslx=" + zslx +
                ", nf='" + nf + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", qxdms='" + qxdms + '\'' +
                ", bdcqzhws=" + bdcqzhws +
                ", sqsjc='" + sqsjc + '\'' +
                ", szsxqc='" + szsxqc + '\'' +
                ", sqdm='" + sqdm + '\'' +
                ", sxh=" + sxh +
                ", cssxh=" + cssxh +
                ", ylzhkg=" + ylzhkg +
                ", zsfhkg=" + zsfhkg +
                ", sqdmkg=" + sqdmkg +
                ", djbmdm='" + djbmdm + '\'' +
                ", zhqfbm=" + zhqfbm +
                ", zhqfqxdm=" + zhqfqxdm +
                '}';
    }
}
