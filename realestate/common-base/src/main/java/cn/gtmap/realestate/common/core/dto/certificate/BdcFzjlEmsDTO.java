package cn.gtmap.realestate.common.core.dto.certificate;

/**
 *
 * @author <a href="mailto:caolu@gtmap.cn>caolu</a>"
 * @version 1.0, 2022/08/08
 * @description 发证记录所需的ems信息
 */
public class BdcFzjlEmsDTO {
    /**
     * 项目ID
     */
    private String xmid;

    /**
     * 工作流实例id
     */
    private String gzlslid;

    /**
     * 是否合并
     */
    private Boolean sfhb;

    /**
     * 机构id
     */
    private String jgid;

    /**
     * 发证记录DTO
     */
    private BdcFzjlDTO bdcFzjlDTO;


    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public Boolean getSfhb() {
        return sfhb;
    }

    public void setSfhb(Boolean sfhb) {
        this.sfhb = sfhb;
    }

    public String getJgid() {
        return jgid;
    }

    public void setJgid(String jgid) {
        this.jgid = jgid;
    }

    public BdcFzjlDTO getBdcFzjlDTO() {
        return bdcFzjlDTO;
    }

    public void setBdcFzjlDTO(BdcFzjlDTO bdcFzjlDTO) {
        this.bdcFzjlDTO = bdcFzjlDTO;
    }
}
