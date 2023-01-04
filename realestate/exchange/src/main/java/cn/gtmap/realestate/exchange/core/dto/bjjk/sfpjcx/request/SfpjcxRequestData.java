package cn.gtmap.realestate.exchange.core.dto.bjjk.sfpjcx.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-28
 * @description
 */
@XStreamAlias("data")
public class SfpjcxRequestData {

    // 查询部门名称
    @XStreamAlias("CXBM")
    private String cxbm;

    // 查询部门编号
    @XStreamAlias("CXBMBH")
    private String cxbmbh;

    // 查询人姓名
    @XStreamAlias("CXR")
    private String cxr;

    // 查询人编号/证号
    @XStreamAlias("CXRBH")
    private String cxrbh;

    // 数字签名
    @XStreamAlias("SZQM")
    private String szqm;

    // 查询请求单号
    @XStreamAlias("CXQQDH")
    private String cxqqdh;

    // 案件编号
    @XStreamAlias("AJBH")
    private String ajbh;

    public String getCxbm() {
        return cxbm;
    }


    public void setCxbm(String cxbm) {
        this.cxbm = cxbm;
    }

    public String getCxbmbh() {
        return cxbmbh;
    }

    public void setCxbmbh(String cxbmbh) {
        this.cxbmbh = cxbmbh;
    }

    public String getCxr() {
        return cxr;
    }

    public void setCxr(String cxr) {
        this.cxr = cxr;
    }

    public String getCxrbh() {
        return cxrbh;
    }

    public void setCxrbh(String cxrbh) {
        this.cxrbh = cxrbh;
    }

    public String getSzqm() {
        return szqm;
    }

    public void setSzqm(String szqm) {
        this.szqm = szqm;
    }

    public String getCxqqdh() {
        return cxqqdh;
    }

    public void setCxqqdh(String cxqqdh) {
        this.cxqqdh = cxqqdh;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }
}
