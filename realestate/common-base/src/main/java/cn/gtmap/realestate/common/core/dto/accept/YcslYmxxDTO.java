package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 一窗受理页面信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-24 14:37
 **/
@ApiModel(value = "YcslYmxxDTO", description = "一窗受理页面信息")
public class YcslYmxxDTO implements Serializable{
    private static final long serialVersionUID = 6450738097716355130L;

    @ApiModelProperty(value = "不动产受理交易信息")
    private BdcSlJyxxDO bdcSlJyxxDO;
    @ApiModelProperty(value = "不动产受理房屋信息")
    private BdcSlFwxxDO bdcSlFwxxDO;
    @ApiModelProperty(value = "不动产受理转入方税务信息")
    private List<BdcSwxxDTO> bdcZrfSwxxList;
    @ApiModelProperty(value = "不动产受理转出方税务信息")
    private List<BdcSwxxDTO> bdcZcfSwxxList;
    @ApiModelProperty(value = "不动产受理转入方信息")
    private List<BdcSlSqrDTO> bdcSlZrfDTOList;
    @ApiModelProperty(value = "不动产受理转出方信息")
    private List<BdcSlSqrDTO> bdcSlZcfDTOList;
    @ApiModelProperty(value = "权利人家庭分组申请人信息列表")
    private List<List<BdcSlJtcyGroupDTO>> bdcQlrGroupDTOLists;
    @ApiModelProperty(value = "义务人家庭分组申请人信息列表")
    private List<List<BdcSlJtcyGroupDTO>> bdcYwrGroupDTOLists;
    @ApiModelProperty(value = "不动产受理交易差额征收信息")
    private BdcSlJyxxCezsDO bdcSlJyxxCezsDO;
    @ApiModelProperty(value = "不动产受理项目")
    private BdcSlXmDO bdcSlXmDO;
    @ApiModelProperty(value = "土地出让金信息")
    private List<BdcSlTdcrjDO> bdcSlTdcrjDOList;


    public BdcSlJyxxDO getBdcSlJyxxDO() {
        return bdcSlJyxxDO;
    }

    public void setBdcSlJyxxDO(BdcSlJyxxDO bdcSlJyxxDO) {
        this.bdcSlJyxxDO = bdcSlJyxxDO;
    }

    public List<BdcSwxxDTO> getBdcZrfSwxxList() {
        return bdcZrfSwxxList;
    }

    public void setBdcZrfSwxxList(List<BdcSwxxDTO> bdcZrfSwxxList) {
        this.bdcZrfSwxxList = bdcZrfSwxxList;
    }

    public List<BdcSwxxDTO> getBdcZcfSwxxList() {
        return bdcZcfSwxxList;
    }

    public void setBdcZcfSwxxList(List<BdcSwxxDTO> bdcZcfSwxxList) {
        this.bdcZcfSwxxList = bdcZcfSwxxList;
    }

    public List<BdcSlSqrDTO> getBdcSlZrfDTOList() {
        return bdcSlZrfDTOList;
    }

    public void setBdcSlZrfDTOList(List<BdcSlSqrDTO> bdcSlZrfDTOList) {
        this.bdcSlZrfDTOList = bdcSlZrfDTOList;
    }

    public List<BdcSlSqrDTO> getBdcSlZcfDTOList() {
        return bdcSlZcfDTOList;
    }

    public void setBdcSlZcfDTOList(List<BdcSlSqrDTO> bdcSlZcfDTOList) {
        this.bdcSlZcfDTOList = bdcSlZcfDTOList;
    }


    public BdcSlFwxxDO getBdcSlFwxxDO() {
        return bdcSlFwxxDO;
    }

    public void setBdcSlFwxxDO(BdcSlFwxxDO bdcSlFwxxDO) {
        this.bdcSlFwxxDO = bdcSlFwxxDO;
    }

    public List<List<BdcSlJtcyGroupDTO>> getBdcQlrGroupDTOLists() {
        return bdcQlrGroupDTOLists;
    }

    public void setBdcQlrGroupDTOLists(List<List<BdcSlJtcyGroupDTO>> bdcQlrGroupDTOLists) {
        this.bdcQlrGroupDTOLists = bdcQlrGroupDTOLists;
    }

    public List<List<BdcSlJtcyGroupDTO>> getBdcYwrGroupDTOLists() {
        return bdcYwrGroupDTOLists;
    }

    public void setBdcYwrGroupDTOLists(List<List<BdcSlJtcyGroupDTO>> bdcYwrGroupDTOLists) {
        this.bdcYwrGroupDTOLists = bdcYwrGroupDTOLists;
    }

    public BdcSlJyxxCezsDO getBdcSlJyxxCezsDO() {
        return bdcSlJyxxCezsDO;
    }

    public void setBdcSlJyxxCezsDO(BdcSlJyxxCezsDO bdcSlJyxxCezsDO) {
        this.bdcSlJyxxCezsDO = bdcSlJyxxCezsDO;
    }

    public BdcSlXmDO getBdcSlXmDO() {
        return bdcSlXmDO;
    }

    public void setBdcSlXmDO(BdcSlXmDO bdcSlXmDO) {
        this.bdcSlXmDO = bdcSlXmDO;
    }

    public List<BdcSlTdcrjDO> getBdcSlTdcrjDOList() {
        return bdcSlTdcrjDOList;
    }

    public void setBdcSlTdcrjDOList(List<BdcSlTdcrjDO> bdcSlTdcrjDOList) {
        this.bdcSlTdcrjDOList = bdcSlTdcrjDOList;
    }

    @Override
    public String toString() {
        return "YcslYmxxDTO{" +
                "bdcSlJyxxDO=" + bdcSlJyxxDO +
                ", bdcSlFwxxDO=" + bdcSlFwxxDO +
                ", bdcZrfSwxxList=" + bdcZrfSwxxList +
                ", bdcZcfSwxxList=" + bdcZcfSwxxList +
                ", bdcSlZrfDTOList=" + bdcSlZrfDTOList +
                ", bdcSlZcfDTOList=" + bdcSlZcfDTOList +
                ", bdcQlrGroupDTOLists=" + bdcQlrGroupDTOLists +
                ", bdcYwrGroupDTOLists=" + bdcYwrGroupDTOLists +
                ", bdcSlJyxxCezsDO=" + bdcSlJyxxCezsDO +
                ", bdcSlXmDO=" + bdcSlXmDO +
                ", bdcSlTdcrjDOList=" + bdcSlTdcrjDOList +
                '}';
    }
}
