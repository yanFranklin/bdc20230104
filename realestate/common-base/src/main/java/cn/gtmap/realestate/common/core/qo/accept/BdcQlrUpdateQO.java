package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/6/11
 * @description
 */
public class BdcQlrUpdateQO {

    @ApiModelProperty(value = "权利人更新json")
    private String json;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    @ApiModelProperty(value = "项目ID集合")
    private List<String> xmidList;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcQlrUpdateQO{");
        sb.append("json='").append(json).append('\'');
        sb.append(", gzlslid='").append(gzlslid).append('\'');
        sb.append(", xmid='").append(xmid).append('\'');
        sb.append(", djxl='").append(djxl).append('\'');
        sb.append(", xmidList=").append(xmidList);
        sb.append('}');
        return sb.toString();
    }
}
