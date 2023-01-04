package cn.gtmap.realestate.common.core.vo.accept.ui;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: realestate
 * @description: 批量创建流程页面返回值，重新赋值受理项目对应的jbxxid和受理编号
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-01-06 09:07
 **/
@ApiModel(value = "BdcSlJbxxXmVO", description = "不动产受理基本信息项目信息VO")
public class BdcSlJbxxXmVO {
    @ApiModelProperty("基本信息id")
    private String jbxxid;

    @ApiModelProperty("受理编号")
    private String slbh;

    @ApiModelProperty("受理项目数据")
    private List<BdcSlXmDO> bdcSlXmDOList;

    public String getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(String jbxxid) {
        this.jbxxid = jbxxid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<BdcSlXmDO> getBdcSlXmDOList() {
        return bdcSlXmDOList;
    }

    public void setBdcSlXmDOList(List<BdcSlXmDO> bdcSlXmDOList) {
        this.bdcSlXmDOList = bdcSlXmDOList;
    }
}
