package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlQl;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/1
 * @description 受理信息结果集
 */
@ApiModel(value = "BdcSlxxInitDTO",description = "受理信息结果集")
public class BdcSlxxInitDTO {

    @ApiModelProperty(value = "存储受理基本信息相关数据")
    private List<BdcSlJbxxDO> bdcSlJbxxDOList;

    @ApiModelProperty(value = "存储受理项目相关数据")
    private List<BdcSlXmDO> bdcSlXmDOList;

    @ApiModelProperty(value = "存储受理项目历史关系相关数据")
    private List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList;

    @ApiModelProperty(value = "存储受理交易信息相关数据")
    private List<BdcSlJyxxDO> bdcSlJyxxDOList;

    @ApiModelProperty(value = "存储受理申请人相关数据")
    private List<BdcSlSqrDO> bdcSlSqrDOList;

    @ApiModelProperty(value = "存储受理房屋信息相关数据")
    private List<BdcSlFwxxDO> bdcSlFwxxDOList;

    @ApiModelProperty(value = "存储受理权利信息相关数据")
    private List<BdcSlQl> bdcSlQlList;

    @ApiModelProperty(value = "存储需删除的数据")
    private List<Object> deleteList;

    public List<BdcSlJbxxDO> getBdcSlJbxxDOList() {
        return bdcSlJbxxDOList;
    }

    public void setBdcSlJbxxDOList(List<BdcSlJbxxDO> bdcSlJbxxDOList) {
        this.bdcSlJbxxDOList = bdcSlJbxxDOList;
    }

    public List<BdcSlXmDO> getBdcSlXmDOList() {
        return bdcSlXmDOList;
    }

    public void setBdcSlXmDOList(List<BdcSlXmDO> bdcSlXmDOList) {
        this.bdcSlXmDOList = bdcSlXmDOList;
    }

    public List<BdcSlXmLsgxDO> getBdcSlXmLsgxDOList() {
        return bdcSlXmLsgxDOList;
    }

    public void setBdcSlXmLsgxDOList(List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList) {
        this.bdcSlXmLsgxDOList = bdcSlXmLsgxDOList;
    }

    public List<BdcSlJyxxDO> getBdcSlJyxxDOList() {
        return bdcSlJyxxDOList;
    }

    public void setBdcSlJyxxDOList(List<BdcSlJyxxDO> bdcSlJyxxDOList) {
        this.bdcSlJyxxDOList = bdcSlJyxxDOList;
    }

    public List<BdcSlSqrDO> getBdcSlSqrDOList() {
        return bdcSlSqrDOList;
    }

    public void setBdcSlSqrDOList(List<BdcSlSqrDO> bdcSlSqrDOList) {
        this.bdcSlSqrDOList = bdcSlSqrDOList;
    }

    public List<BdcSlFwxxDO> getBdcSlFwxxDOList() {
        return bdcSlFwxxDOList;
    }

    public void setBdcSlFwxxDOList(List<BdcSlFwxxDO> bdcSlFwxxDOList) {
        this.bdcSlFwxxDOList = bdcSlFwxxDOList;
    }

    public List<BdcSlQl> getBdcSlQlList() {
        return bdcSlQlList;
    }

    public void setBdcSlQlList(List<BdcSlQl> bdcSlQlList) {
        this.bdcSlQlList = bdcSlQlList;
    }

    public List<Object> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<Object> deleteList) {
        this.deleteList = deleteList;
    }

    @Override
    public String toString() {
        return "BdcSlxxInitDTO{" +
                "bdcSlJbxxDOList=" + bdcSlJbxxDOList +
                ", bdcSlXmDOList=" + bdcSlXmDOList +
                ", bdcSlXmLsgxDOList=" + bdcSlXmLsgxDOList +
                ", bdcSlJyxxDOList=" + bdcSlJyxxDOList +
                ", bdcSlSqrDOList=" + bdcSlSqrDOList +
                ", bdcSlFwxxDOList=" + bdcSlFwxxDOList +
                ", bdcSlQlList=" + bdcSlQlList +
                ", deleteList=" + deleteList +
                '}';
    }
}
