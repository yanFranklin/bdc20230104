package cn.gtmap.realestate.init.core.qo;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/9/2.
 * @description
 */
public class InitQlqtzkFjDataQO {
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "证书ID")
    private String zsid;
    @ApiModelProperty(value = "权利人集合")
    private List<BdcQlrDO> bdcQlrDOList;
    @ApiModelProperty(value = "分别持证是否显示持证人")
    private boolean fbczsfxsczr;
    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public List<BdcQlrDO> getBdcQlrDOList() {
        return bdcQlrDOList;
    }

    public void setBdcQlrDOList(List<BdcQlrDO> bdcQlrDOList) {
        this.bdcQlrDOList = bdcQlrDOList;
    }

    public boolean getFbczsfxsczr() {
        return fbczsfxsczr;
    }

    public void setFbczsfxsczr(boolean fbczsfxsczr) {
        this.fbczsfxsczr = fbczsfxsczr;
    }
}
