package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/11.
 * @description
 */
@ApiModel(value = "BdcXmFbQO",description = "不动产项目附表查询参数封装对象")
public class BdcXmFbQO {
    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "项目id")
    private List<String> xmids;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "宗地所有类型")
    private String zdsylx;

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

    public String getZdsylx() {
        return zdsylx;
    }

    public void setZdsylx(String zdsylx) {
        this.zdsylx = zdsylx;
    }

    public List<String> getXmids() {
        return xmids;
    }

    public void setXmids(List<String> xmids) {
        this.xmids = xmids;
    }

    @Override
    public String toString() {
        return "BdcXmFbQO{" +
                "xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", zdsylx='" + zdsylx + '\'' +
                '}';
    }
}
