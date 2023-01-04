package cn.gtmap.realestate.common.core.qo.init;

import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/6/10
 * @description 不动产原项目查询参数封装对象
 */
@ApiModel(value = "BdcYxmQO",description = "不动产原项目查询参数封装对象")
public class BdcYxmQO {

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    @ApiModelProperty(value = "项目ID")
    private List<String> xmidList;

    @ApiModelProperty(value = "是否外联项目  0:否  1：是")
    private Integer wlxm;

    @ApiModelProperty(value = "是否注销原权利  0:否  1：是")
    private Integer zxyql;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
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

    public Integer getWlxm() {
        return wlxm;
    }

    public void setWlxm(Integer wlxm) {
        this.wlxm = wlxm;
    }

    public Integer getZxyql() {
        return zxyql;
    }

    public void setZxyql(Integer zxyql) {
        this.zxyql = zxyql;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcYxmQO{");
        sb.append("gzlslid='").append(gzlslid).append('\'');
        sb.append(", djxl='").append(djxl).append('\'');
        sb.append(", xmidList=").append(xmidList);
        sb.append(", wlxm=").append(wlxm);
        sb.append(", zxyql=").append(zxyql);
        sb.append('}');
        return sb.toString();
    }
}
