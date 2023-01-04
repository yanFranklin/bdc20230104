package cn.gtmap.realestate.common.core.qo.register;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 签字信息查询实体
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 */
public class BdcQzxxQO {
    /**
     * 项目id
     */
    private String xmid;

    /**
     * 工作流实例id
     */
    private String gzlslid;

    /**
     * 受理编号
     */
    private String slbh;

    /**
     * 顺序号
     */
    private List<Integer> sxhs;


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

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<Integer> getSxhs() {
        return sxhs;
    }

    public void setSxhs(List<Integer> sxhs) {
        this.sxhs = sxhs;
    }

    @Override
    public String toString() {
        return "BdcQzxxQO{" +
                "xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", sxhs=" + sxhs +
                '}';
    }
}
