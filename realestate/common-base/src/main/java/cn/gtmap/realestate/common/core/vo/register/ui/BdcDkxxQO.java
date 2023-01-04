package cn.gtmap.realestate.common.core.vo.register.ui;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2020/10/23
 * @description 承包经营权 地块列表查询 QO对象
 */
public class BdcDkxxQO {
    /**
     * 项目ID
     */
    private String xmid;

    /**
     * 工作流实例ID
     */
    private String gzlslid;

    /**
     * 证书ID
     */
    private String zsid;

    /**
     * 受理编号
     */
    private String slbh;


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

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    @Override
    public String toString() {
        return "BdcDkxxQO{" +
                "xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", zsid='" + zsid + '\'' +
                ", slbh='" + slbh + '\'' +
                '}';
    }
}
