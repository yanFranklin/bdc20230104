package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 遗失公告页面组织参数
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-04-22 17:47
 **/
@ApiModel(value = "BdcYsggDTO", description = "遗失公告页面组织参数")
public class BdcYsggDTO implements Serializable {
    private static final long serialVersionUID = -3572050138151686209L;
    @ApiModelProperty(value = "提示信息")
    private String zslx;

    @ApiModelProperty(value = "项目信息")
    private List<BdcXmDO> bdcXmDOList;

    public String getZslx() {
        return zslx;
    }

    public void setZslx(String zslx) {
        this.zslx = zslx;
    }

    public List<BdcXmDO> getBdcXmDOList() {
        return bdcXmDOList;
    }

    public void setBdcXmDOList(List<BdcXmDO> bdcXmDOList) {
        this.bdcXmDOList = bdcXmDOList;
    }

    @Override
    public String toString() {
        return "BdcYsggDTO{" +
                "zslx='" + zslx + '\'' +
                ", bdcXmDOList=" + bdcXmDOList +
                '}';
    }
}
