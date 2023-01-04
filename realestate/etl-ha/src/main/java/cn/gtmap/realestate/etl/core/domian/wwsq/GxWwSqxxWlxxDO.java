package cn.gtmap.realestate.etl.core.domian.wwsq;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/2
 * @description 物流信息
 */
@Table(name = "GX_WW_SQXX_WLXX")
public class GxWwSqxxWlxxDO {

    /**
     * 主键
     */
    @Id
    private String wlid;
    /**
     * 申请信息ID
     */
    private String sqxxid;
    /**
     * 收件人名称
     */
    private String sjrmc;
    /**
     * 收件人联系电话
     */
    private String sjrlxdh;
    /**
     * 收件人所在省
     */
    private String sjrszsheng;
    /**
     * 收件人所在市
     */
    private String sjrszshi;
    /**
     * 收件人所在县
     */
    private String sjrszx;
    /**
     * 收件人详细地址
     */
    private String sjrxxdz;
    /**
     * 领证人名称
     */
    private String lzrmc;
    /**
     * 领证人证件种类
     */
    private String lzrzjzl;
    /**
     * 领证人证件号
     */
    private String lzrzjh;
    /**
     * 领证方式代码
     */
    private String lzfsdm;
    /**
     * 领证方式名称
     */
    private String lzfsmc;

    public String getWlid() {
        return wlid;
    }

    public void setWlid(String wlid) {
        this.wlid = wlid;
    }

    public String getSqxxid() {
        return sqxxid;
    }

    public void setSqxxid(String sqxxid) {
        this.sqxxid = sqxxid;
    }

    public String getSjrmc() {
        return sjrmc;
    }

    public void setSjrmc(String sjrmc) {
        this.sjrmc = sjrmc;
    }

    public String getSjrlxdh() {
        return sjrlxdh;
    }

    public void setSjrlxdh(String sjrlxdh) {
        this.sjrlxdh = sjrlxdh;
    }

    public String getSjrszsheng() {
        return sjrszsheng;
    }

    public void setSjrszsheng(String sjrszsheng) {
        this.sjrszsheng = sjrszsheng;
    }

    public String getSjrszshi() {
        return sjrszshi;
    }

    public void setSjrszshi(String sjrszshi) {
        this.sjrszshi = sjrszshi;
    }

    public String getSjrszx() {
        return sjrszx;
    }

    public void setSjrszx(String sjrszx) {
        this.sjrszx = sjrszx;
    }

    public String getSjrxxdz() {
        return sjrxxdz;
    }

    public void setSjrxxdz(String sjrxxdz) {
        this.sjrxxdz = sjrxxdz;
    }

    public String getLzrmc() {
        return lzrmc;
    }

    public void setLzrmc(String lzrmc) {
        this.lzrmc = lzrmc;
    }

    public String getLzrzjzl() {
        return lzrzjzl;
    }

    public void setLzrzjzl(String lzrzjzl) {
        this.lzrzjzl = lzrzjzl;
    }

    public String getLzrzjh() {
        return lzrzjh;
    }

    public void setLzrzjh(String lzrzjh) {
        this.lzrzjh = lzrzjh;
    }

    public String getLzfsdm() {
        return lzfsdm;
    }

    public void setLzfsdm(String lzfsdm) {
        this.lzfsdm = lzfsdm;
    }

    public String getLzfsmc() {
        return lzfsmc;
    }

    public void setLzfsmc(String lzfsmc) {
        this.lzfsmc = lzfsmc;
    }
}
