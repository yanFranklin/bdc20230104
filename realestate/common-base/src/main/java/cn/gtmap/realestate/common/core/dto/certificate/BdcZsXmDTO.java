package cn.gtmap.realestate.common.core.dto.certificate;

import java.util.Date;

/**
 * 证书关联项目信息 DTO
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
public class BdcZsXmDTO {
    /**
     * 工作流实例 ID
     */
    private String gzlslid;
    /**
     * 缮证时间
     */
    private Date szsj;

    /**
     * 不动产单元号
     */
    private String bdcdyh;

    /**
     * 不动产权证号
     */
    private String bdcqzh;


    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public Date getSzsj() {
        return szsj;
    }

    public void setSzsj(Date szsj) {
        this.szsj = szsj;
    }
}
