package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/10/19
 * @description  质检项目信息DTO
 */
public class BdcZjXmxxDTO extends BdcXmDO {

    /**
     * 质检ID
     */
    private String zjid;

    /**
     * 质检人
     */
    private String hdr;
    /**
     * 质检问题
     */
    private String spyj;

    public String getZjid() {
        return zjid;
    }

    public void setZjid(String zjid) {
        this.zjid = zjid;
    }

    public String getHdr() {
        return hdr;
    }

    public void setHdr(String hdr) {
        this.hdr = hdr;
    }

    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj;
    }
}
