package cn.gtmap.realestate.exchange.core.dto.wwsq.dyxxAndYdyFyxx.request;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2019-11-27
 * @description 分页查询 的 查询条件
 */
@IgnoreCast
public class DyxxAndYdaxxFyRequestDTO {

    private String cqzh;

    private String bdcdjzmh;

    private String cqzhjc;

    private String bdcdjzmhjc;

    private String dyqr;

    /**
     * 抵押权人模糊类型： 1 精确  2 左模糊  3 右模糊 4 全模糊
     */
    private String dyqrmh;

    private String dyqrzjh;

    /**
     * 抵押权人证件号模糊类型： 1 精确  2 左模糊  3 右模糊 4 全模糊
     */
    private String dyqrzjhmh;

    private String djkssj;

    private String djjssj;

    private String qszt;

    private String dyr;

    /**
     * 抵押人模糊类型： 1 精确  2 左模糊  3 右模糊 4 全模糊
     */
    private String dyrmh;

    private String dyrzjh;

    /**
     * 抵押人证件号模糊类型： 1 精确  2 左模糊  3 右模糊 4 全模糊
     */
    private String dyrzjhmh;

    private String sfcf;

    private String sfydy;

    private String zl;

    private String dysw;

    private String xzqdm;
    private String nwslbh;
    private String slsj;

    public String getDysw() {
        return dysw;
    }

    public void setDysw(String dysw) {
        this.dysw = dysw;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getBdcdjzmh() {
        return bdcdjzmh;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
    }

    public String getCqzhjc() {
        return cqzhjc;
    }

    public void setCqzhjc(String cqzhjc) {
        this.cqzhjc = cqzhjc;
    }

    public String getBdcdjzmhjc() {
        return bdcdjzmhjc;
    }

    public void setBdcdjzmhjc(String bdcdjzmhjc) {
        this.bdcdjzmhjc = bdcdjzmhjc;
    }

    public String getDyqr() {
        return dyqr;
    }

    public void setDyqr(String dyqr) {
        this.dyqr = dyqr;
    }

    public String getDyqrzjh() {
        return dyqrzjh;
    }

    public void setDyqrzjh(String dyqrzjh) {
        this.dyqrzjh = dyqrzjh;
    }

    public String getDjkssj() {
        return djkssj;
    }

    public void setDjkssj(String djkssj) {
        this.djkssj = djkssj;
    }

    public String getDjjssj() {
        return djjssj;
    }

    public void setDjjssj(String djjssj) {
        this.djjssj = djjssj;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getDyr() {
        return dyr;
    }

    public void setDyr(String dyr) {
        this.dyr = dyr;
    }

    public String getDyrzjh() {
        return dyrzjh;
    }

    public void setDyrzjh(String dyrzjh) {
        this.dyrzjh = dyrzjh;
    }

    public String getSfcf() {
        return sfcf;
    }

    public void setSfcf(String sfcf) {
        this.sfcf = sfcf;
    }

    public String getSfydy() {
        return sfydy;
    }

    public void setSfydy(String sfydy) {
        this.sfydy = sfydy;
    }

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getNwslbh() {
        return nwslbh;
    }

    public void setNwslbh(String nwslbh) {
        this.nwslbh = nwslbh;
    }

    public String getSlsj() {
        return slsj;
    }

    public void setSlsj(String slsj) {
        this.slsj = slsj;
    }

    public String getDyqrmh() {
        return dyqrmh;
    }

    public void setDyqrmh(String dyqrmh) {
        this.dyqrmh = dyqrmh;
    }

    public String getDyqrzjhmh() {
        return dyqrzjhmh;
    }

    public void setDyqrzjhmh(String dyqrzjhmh) {
        this.dyqrzjhmh = dyqrzjhmh;
    }

    public String getDyrmh() {
        return dyrmh;
    }

    public void setDyrmh(String dyrmh) {
        this.dyrmh = dyrmh;
    }

    public String getDyrzjhmh() {
        return dyrzjhmh;
    }

    public void setDyrzjhmh(String dyrzjhmh) {
        this.dyrzjhmh = dyrzjhmh;
    }

    @Override
    public String toString() {
        return "DyxxAndYdaxxFyRequestDTO{" +
                "cqzh='" + cqzh + '\'' +
                ", bdcdjzmh='" + bdcdjzmh + '\'' +
                ", cqzhjc='" + cqzhjc + '\'' +
                ", bdcdjzmhjc='" + bdcdjzmhjc + '\'' +
                ", dyqr='" + dyqr + '\'' +
                ", dyqrmh='" + dyqrmh + '\'' +
                ", dyqrzjh='" + dyqrzjh + '\'' +
                ", dyqrzjhmh='" + dyqrzjhmh + '\'' +
                ", djkssj='" + djkssj + '\'' +
                ", djjssj='" + djjssj + '\'' +
                ", qszt='" + qszt + '\'' +
                ", dyr='" + dyr + '\'' +
                ", dyrmh='" + dyrmh + '\'' +
                ", dyrzjh='" + dyrzjh + '\'' +
                ", dyrzjhmh='" + dyrzjhmh + '\'' +
                ", sfcf='" + sfcf + '\'' +
                ", sfydy='" + sfydy + '\'' +
                ", zl='" + zl + '\'' +
                ", dysw='" + dysw + '\'' +
                ", xzqdm='" + xzqdm + '\'' +
                ", nwslbh='" + nwslbh + '\'' +
                ", slsj='" + slsj + '\'' +
                '}';
    }
}
