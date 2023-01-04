package cn.gtmap.realestate.exchange.core.dto.wwsq.tdqsxxcj.req;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

public class TdqsInfoCollectRequestBodyDTO {
    /**
     * 不动产单元号
     */
    private String bdcqzh;
    /**
     * 不动产权证号
     */
    private String bdcdyh;
    /**
     * 土地地址
     */
    private String tdwzdz;


    /**
     * 交易日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date jyrq;
    /**
     * 土地面积
     */
    private String tdmj;
    /**
     * 合同编号
     */
    private String htbh;
    /**
     * 合同价格
     */
    private String htje;
    /**
     * 电子监管号
     */
    private String dzjgh;
    /**
     * 单价
     */
    private String tddj;
    /**
     * 合同签订日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date htqdrq;
    /**
     * 权属登记日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date qsdjrq;
    /**
     * 丘号
     */
    private String qh;
    /**
     * 行政区划代码
     */
    @JSONField(name = "xzqh_dm")
    private String xzqhdm;
    /**
     * 行政区划名称
     */
    @JSONField(name = "xzqh_mc")
    private String xzqhmc;
    /**
     * 乡镇街道代码
     */
    @JSONField(name = "jdxz_dm")
    private String jdxzdm;
    /**
     * 乡镇街道名称
     */
    @JSONField(name = "jdxz_mc")
    private String jdxzmc;
    /**
     * 经办人姓名
     */
    private String jbrxm;
    /**
     * 经办人电话
     */
    private String jbrdh;
    /**
     * 备注
     */
    private String bz;
    /**
     * 土地出让标记 “0”出让，“1”转让
     */
    private String tdcrzrbz;
    /**
     * 土地出让标记 “0”出让，“1”转让
     */
    private String tdqsbz;

    /**
     * 图片列表
     */
    private List<String> tplb;
    /**
     * 纳税人信息列表
     */
    private List<SrfxxlbDTO> srfxxlb;

    /**
     * 出让方人信息列表
     */
    private List<SrfxxlbDTO> crfxxlb;

    public String getTdqsbz() {
        return tdqsbz;
    }

    public void setTdqsbz(String tdqsbz) {
        this.tdqsbz = tdqsbz;
    }

    public String getTdcrzrbz() {
        return tdcrzrbz;
    }

    public void setTdcrzrbz(String tdcrzrbz) {
        this.tdcrzrbz = tdcrzrbz;
    }

    public List<SrfxxlbDTO> getCrfxxlb() {
        return crfxxlb;
    }

    public void setCrfxxlb(List<SrfxxlbDTO> crfxxlb) {
        this.crfxxlb = crfxxlb;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getTdwzdz() {
        return tdwzdz;
    }

    public void setTdwzdz(String tdwzdz) {
        this.tdwzdz = tdwzdz;
    }

    public String getTdmj() {
        return tdmj;
    }

    public void setTdmj(String tdmj) {
        this.tdmj = tdmj;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getHtje() {
        return htje;
    }

    public void setHtje(String htje) {
        this.htje = htje;
    }

    public String getDzjgh() {
        return dzjgh;
    }

    public void setDzjgh(String dzjgh) {
        this.dzjgh = dzjgh;
    }

    public String getTddj() {
        return tddj;
    }

    public void setTddj(String tddj) {
        this.tddj = tddj;
    }

    public Date getHtqdrq() {
        return htqdrq;
    }

    public void setHtqdrq(Date htqdrq) {
        this.htqdrq = htqdrq;
    }

    public Date getQsdjrq() {
        return qsdjrq;
    }

    public void setQsdjrq(Date qsdjrq) {
        this.qsdjrq = qsdjrq;
    }

    public String getQh() {
        return qh;
    }

    public void setQh(String qh) {
        this.qh = qh;
    }

    public String getXzqhdm() {
        return xzqhdm;
    }

    public void setXzqhdm(String xzqhdm) {
        this.xzqhdm = xzqhdm;
    }

    public String getXzqhmc() {
        return xzqhmc;
    }

    public void setXzqhmc(String xzqhmc) {
        this.xzqhmc = xzqhmc;
    }

    public String getJdxzdm() {
        return jdxzdm;
    }

    public void setJdxzdm(String jdxzdm) {
        this.jdxzdm = jdxzdm;
    }

    public String getJdxzmc() {
        return jdxzmc;
    }

    public void setJdxzmc(String jdxzmc) {
        this.jdxzmc = jdxzmc;
    }

    public Date getJyrq() {
        return jyrq;
    }

    public void setJyrq(Date jyrq) {
        this.jyrq = jyrq;
    }

    public String getJbrxm() {
        return jbrxm;
    }

    public void setJbrxm(String jbrxm) {
        this.jbrxm = jbrxm;
    }

    public String getJbrdh() {
        return jbrdh;
    }

    public void setJbrdh(String jbrdh) {
        this.jbrdh = jbrdh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public List<String> getTplb() {
        return tplb;
    }

    public void setTplb(List<String> tplb) {
        this.tplb = tplb;
    }

    public List<SrfxxlbDTO> getSrfxxlb() {
        return srfxxlb;
    }

    public void setSrfxxlb(List<SrfxxlbDTO> srfxxlb) {
        this.srfxxlb = srfxxlb;
    }

    @Override
    public String toString() {
        return "TdqsInfoCollectRequestBodyDTO{" +
                "bdcqzh='" + bdcqzh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", tdwzdz='" + tdwzdz + '\'' +
                ", jyrq=" + jyrq +
                ", tdmj='" + tdmj + '\'' +
                ", htbh='" + htbh + '\'' +
                ", htje='" + htje + '\'' +
                ", dzjgh='" + dzjgh + '\'' +
                ", tddj='" + tddj + '\'' +
                ", htqdrq=" + htqdrq +
                ", qsdjrq=" + qsdjrq +
                ", qh='" + qh + '\'' +
                ", xzqhdm='" + xzqhdm + '\'' +
                ", xzqhmc='" + xzqhmc + '\'' +
                ", jdxzdm='" + jdxzdm + '\'' +
                ", jdxzmc='" + jdxzmc + '\'' +
                ", jbrxm='" + jbrxm + '\'' +
                ", jbrdh='" + jbrdh + '\'' +
                ", bz='" + bz + '\'' +
                ", tdcrzrbz='" + tdcrzrbz + '\'' +
                ", tdqsbz='" + tdqsbz + '\'' +
                ", tplb=" + tplb +
                ", srfxxlb=" + srfxxlb +
                ", crfxxlb=" + crfxxlb +
                '}';
    }

    public static class SrfxxlbDTO {
        /**
         * 纳税人名称
         */
        private String nsrmc;
        /**
         * 纳税人类型
         */
        private String nsrlx;
        /**
         * 证件类型代码
         */
        @JSONField(name = "sfzjlx_dm")
        private String sfzjlxdm;
        /**
         * 证件类型名称
         */
        @JSONField(name = "sfzjlx_mc")
        private String sfzjlxmc;

        /**
         * 国籍代码
         */
        @JSONField(name = "gj_dm")
        private String gjdm;

        /**
         * 主产权人标志
         */
        private String zcqrbz;
        /**
         * 身份证件号码
         */
        private String sfzjhm;
        /**
         * 联系电话
         */
        private String lxdh;
        /**
         * 地址
         */
        private String dz;

        public String getNsrmc() {
            return nsrmc;
        }

        public String getGjdm() {
            return gjdm;
        }

        public void setGjdm(String gjdm) {
            this.gjdm = gjdm;
        }

        public String getZcqrbz() {
            return zcqrbz;
        }

        public void setZcqrbz(String zcqrbz) {
            this.zcqrbz = zcqrbz;
        }

        public void setNsrmc(String nsrmc) {
            this.nsrmc = nsrmc;
        }

        public String getNsrlx() {
            return nsrlx;
        }

        public void setNsrlx(String nsrlx) {
            this.nsrlx = nsrlx;
        }

        public String getSfzjlxdm() {
            return sfzjlxdm;
        }

        public void setSfzjlxdm(String sfzjlxdm) {
            this.sfzjlxdm = sfzjlxdm;
        }

        public String getSfzjlxmc() {
            return sfzjlxmc;
        }

        public void setSfzjlxmc(String sfzjlxmc) {
            this.sfzjlxmc = sfzjlxmc;
        }

        public String getSfzjhm() {
            return sfzjhm;
        }

        public void setSfzjhm(String sfzjhm) {
            this.sfzjhm = sfzjhm;
        }

        public String getLxdh() {
            return lxdh;
        }

        public void setLxdh(String lxdh) {
            this.lxdh = lxdh;
        }

        public String getDz() {
            return dz;
        }

        public void setDz(String dz) {
            this.dz = dz;
        }

        @Override
        public String toString() {
            return "SrfxxlbDTO{" +
                    "nsrmc='" + nsrmc + '\'' +
                    ", nsrlx='" + nsrlx + '\'' +
                    ", sfzjlxdm='" + sfzjlxdm + '\'' +
                    ", sfzjlxmc='" + sfzjlxmc + '\'' +
                    ", gjdm='" + gjdm + '\'' +
                    ", zcqrbz='" + zcqrbz + '\'' +
                    ", sfzjhm='" + sfzjhm + '\'' +
                    ", lxdh='" + lxdh + '\'' +
                    ", dz='" + dz + '\'' +
                    '}';
        }
    }
}
