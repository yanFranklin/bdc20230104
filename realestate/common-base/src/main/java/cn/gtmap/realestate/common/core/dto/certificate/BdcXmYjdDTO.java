package cn.gtmap.realestate.common.core.dto.certificate;

import cn.gtmap.realestate.common.core.domain.BdcYjdDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/4
 * @description 移交单和项目是多对多关系，此实体，项目为主信息，移交单为附属信息
 */
@ApiModel(value = "BdcXmYjdDTO", description = "移交单和项目是多对多关系，此实体，项目为主信息，移交单为附属信息")
public class BdcXmYjdDTO {
    @ApiModelProperty(value = "项目ID")
    String xmid;
    @ApiModelProperty(value = "受理编号")
    String slbh;
    @ApiModelProperty(value = "移交单信息List")
    List<BdcYjdDO> bdcYjdDOList;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<BdcYjdDO> getBdcYjdDOList() {
        return bdcYjdDOList;
    }

    public void setBdcYjdDOList(List<BdcYjdDO> bdcYjdDOList) {
        this.bdcYjdDOList = bdcYjdDOList;
    }

    @Override
    public String toString() {
        return "BdcXmYjdDTO{" +
                "xmid='" + xmid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", bdcYjdDOList=" + bdcYjdDOList +
                '}';
    }

}
