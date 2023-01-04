package cn.gtmap.realestate.common.core.dto.inquiry.yancheng;

import java.util.Date;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2021/01/08
 * @description 盐城征迁单元证书解锁参数DTO
 */
public class BdcZqJsDTO {
    /**
     * 相关信息主键ID
     */
    private String id;

    /**
     * 解锁原因
     */
    private String jsyy;

    /**
     * 锁定原因
     */
    private String sdyy;

    /**
     * 不动产单元号
     */
    private String bdcdyh;

    /**
     * 解锁人
     */
    private String jsr;

    /**
     * 解锁人ID
     */
    private String jsrid;

    /**
     * 解锁信息
     */
    private Date jssj;

    /**
     * 不动产权证号
     */
    private String bdcqzh;


    public String getSdyy() {
        return sdyy;
    }

    public void setSdyy(String sdyy) {
        this.sdyy = sdyy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsyy() {
        return jsyy;
    }

    public void setJsyy(String jsyy) {
        this.jsyy = jsyy;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getJsr() {
        return jsr;
    }

    public void setJsr(String jsr) {
        this.jsr = jsr;
    }

    public String getJsrid() {
        return jsrid;
    }

    public void setJsrid(String jsrid) {
        this.jsrid = jsrid;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }
}
