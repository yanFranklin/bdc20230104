package cn.gtmap.realestate.common.core.dto.init;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022/10/6
 * @description 删除业务信息DTO对象
 */
public class BdcDeleteYwxxDTO {

    /**
     * 工作流实例ID
     */
    private String gzlslid;
    /**
     * 原因
     */
    private String reason;
    /**
     * 受理状态
     */
    private String slzt;


    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSlzt() {
        return slzt;
    }

    public void setSlzt(String slzt) {
        this.slzt = slzt;
    }

    @Override
    public String toString() {
        return "BdcDeleteYwxxDTO{" +
                "gzlslid='" + gzlslid + '\'' +
                ", reason='" + reason + '\'' +
                ", slzt='" + slzt + '\'' +
                '}';
    }
}
