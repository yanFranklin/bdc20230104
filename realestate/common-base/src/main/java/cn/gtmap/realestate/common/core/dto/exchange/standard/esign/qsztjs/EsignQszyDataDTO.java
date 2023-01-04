package cn.gtmap.realestate.common.core.dto.exchange.standard.esign.qsztjs;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/10/21
 * @description
 */
public class EsignQszyDataDTO {


    /**
     * slbh : 受理编号
     * lsh : 流水号
     * qsrwid : 签署任务id
     * qszt : 签署状态
     * qssbyy :
     * qsrxxList : [{}]
     */

    private String slbh;
    private String lsh;
    private String qsrwid;
    private String qszt;
    private String qssbyy;
    private List<EsignQsztQsrxxDTO> qsrxxList;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getQsrwid() {
        return qsrwid;
    }

    public void setQsrwid(String qsrwid) {
        this.qsrwid = qsrwid;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getQssbyy() {
        return qssbyy;
    }

    public void setQssbyy(String qssbyy) {
        this.qssbyy = qssbyy;
    }

    public List<EsignQsztQsrxxDTO> getQsrxxList() {
        return qsrxxList;
    }

    public void setQsrxxList(List<EsignQsztQsrxxDTO> qsrxxList) {
        this.qsrxxList = qsrxxList;
    }

    @Override
    public String toString() {
        return "EsignQszyDataDTO{" +
                "slbh='" + slbh + '\'' +
                ", lsh='" + lsh + '\'' +
                ", qsrwid='" + qsrwid + '\'' +
                ", qszt='" + qszt + '\'' +
                ", qssbyy='" + qssbyy + '\'' +
                ", qsrxxList=" + qsrxxList +
                '}';
    }
}
