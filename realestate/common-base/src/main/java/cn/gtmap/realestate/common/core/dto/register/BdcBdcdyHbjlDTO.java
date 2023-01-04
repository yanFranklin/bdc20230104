package cn.gtmap.realestate.common.core.dto.register;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description 合并不动产单元上下手关系数据
 */
public class BdcBdcdyHbjlDTO {
    /**
     * 原合并项目ID
     */
    private String yxmid;
    /**
     * 原合并项目权利拆分标识
     */
    private String yqlcfbs;
    /**
     * 当前新项目ID
     */
    private String xmid;
    /**
     * 当前新项目权利拆分标识
     */
    private String qlcfbs;


    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    public String getYqlcfbs() {
        return yqlcfbs;
    }

    public void setYqlcfbs(String yqlcfbs) {
        this.yqlcfbs = yqlcfbs;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlcfbs() {
        return qlcfbs;
    }

    public void setQlcfbs(String qlcfbs) {
        this.qlcfbs = qlcfbs;
    }

    @Override
    public String toString() {
        return "BdcBdcdyHbjlDTO{" +
                "yxmid='" + yxmid + '\'' +
                ", yqlcfbs='" + yqlcfbs + '\'' +
                ", xmid='" + xmid + '\'' +
                ", qlcfbs='" + qlcfbs + '\'' +
                '}';
    }
}
