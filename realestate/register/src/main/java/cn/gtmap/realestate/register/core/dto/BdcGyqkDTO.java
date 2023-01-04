package cn.gtmap.realestate.register.core.dto;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/22
 * @description 共有情况DTO
 */
public class BdcGyqkDTO {
    /**
     * 权利人共有情况
     */
    List<Map<String, Object>> qlrGyqk;
    /**
     * 项目ID
     */
    private String xmid;
    private List<String> xmidList;
    /**
     * 工作流实例ID
     */
    private String gzlslid;
    /**
     * 权利共有情况
     */
    private String qlGyqk;

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public String getQlGyqk() {
        return qlGyqk;
    }

    public void setQlGyqk(String qlGyqk) {
        this.qlGyqk = qlGyqk;
    }

    public List<Map<String, Object>> getQlrGyqk() {
        return qlrGyqk;
    }

    public void setQlrGyqk(List<Map<String, Object>> qlrGyqk) {
        this.qlrGyqk = qlrGyqk;
    }

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

    @Override
    public String toString() {
        return "BdcGyqkDTO{" +
                "xmid='" + xmid + '\'' +
                ", xmidList=" + xmidList +
                ", gzlslid='" + gzlslid + '\'' +
                ", qlGyqk='" + qlGyqk + '\'' +
                ", qlrGyqk=" + qlrGyqk +
                '}';
    }
}
