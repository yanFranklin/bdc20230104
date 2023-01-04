package cn.gtmap.realestate.common.core.dto.accept;


import cn.gtmap.realestate.common.core.domain.BdcDsQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcGzdjDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTOForZhlc;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.GltdzxxDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 项目类
 */
@ApiModel(value = "BdcSlXmDTO", description = "项目类模型")
public class BdcSlXmDTO implements Serializable {
    private static final long serialVersionUID = -1629778108636683417L;
    @ApiModelProperty(value = "不动产受理项目")
    private BdcSlXmDO bdcSlXmDO;

    @ApiModelProperty(value = "第三方受理项目属性实体")
    private DsfSlxxDTO dsfSlxxDTO;

    @ApiModelProperty(value = "不动产受理权利信息")
    private BdcSlQl bdcSlQl;

    @ApiModelProperty(value = "不动产受理交易信息项目")
    private BdcSlJyxxDO bdcSlJyxxDO;

    @ApiModelProperty(value = "不动产受理项目历史关系集合")
    private List<BdcSlXmLsgxDO> bdcSlXmLsgxList;

    @ApiModelProperty(value = "不动产受理申请人集合")
    private List<BdcSlSqrDO> bdcSlSqrDOList;

    @ApiModelProperty(value = "一窗受理申请人家庭成员实体集合")
    private List<BdcSlSqrDTO> bdcSlSqrDTOList;

    @ApiModelProperty(value = "税务信息列表")
    private List<BdcSwxxDTO> bdcSwxxDTOList;

    @ApiModelProperty(value = "领证人信息列表")
    private List<BdcSlLzrDO> bdcSlLzrDOList;

    /**
     * 【35802】 债务人
     */
    @ApiModelProperty(value = "第三权利人信息")
    private List<BdcDsQlrDO> bdcDsQlrDOList;

    /**
     * 36673 【盐城】抵押首次创建接口支持房产证和土地证一并创建
     */
    @ApiModelProperty(value = "关联土地证信息")
    private List<GltdzxxDTO> gltdzxxDTOList;

    @ApiModelProperty(value = "项目附件信息")
    private List<FjclDTOForZhlc> fjxx;

    @ApiModelProperty(value = "收费信息")
    private List<BdcSfxxDTO> bdcSfxxDTOList;

    @ApiModelProperty(value = "更正登记信息")
    private BdcGzdjDO bdcGzdjDO;

    @ApiModelProperty(value = "不动产受理邮寄信息")
    private BdcSlYjxxDO bdcSlYjxxDO;

    /**
     * 关联预告预抵押信息
     */
    @ApiModelProperty(value = "关联预告预抵押信息")
    private List<BdcSlYgDTO> glygydy;

    public List<FjclDTOForZhlc> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<FjclDTOForZhlc> fjxx) {
        this.fjxx = fjxx;
    }

    public List<GltdzxxDTO> getGltdzxxDTOList() {
        return gltdzxxDTOList;
    }

    public void setGltdzxxDTOList(List<GltdzxxDTO> gltdzxxDTOList) {
        this.gltdzxxDTOList = gltdzxxDTOList;
    }

    public BdcSlXmDO getBdcSlXm() {
        return bdcSlXmDO;
    }

    public void setBdcSlXm(BdcSlXmDO bdcSlXm) {
        bdcSlXmDO = bdcSlXm;
    }

    public List<BdcSlXmLsgxDO> getBdcSlXmLsgxList() {
        return bdcSlXmLsgxList;
    }

    public void setBdcSlXmLsgxList(List<BdcSlXmLsgxDO> bdcSlXmLsgxList) {
        this.bdcSlXmLsgxList = bdcSlXmLsgxList;
    }

    public List<BdcSlSqrDO> getBdcSlSqrDOList() {
        return bdcSlSqrDOList;
    }

    public void setBdcSlSqrDOList(List<BdcSlSqrDO> bdcSlSqrDOList) {
        this.bdcSlSqrDOList = bdcSlSqrDOList;
    }

    public BdcSlJyxxDO getBdcSlJyxxDO() {
        return bdcSlJyxxDO;
    }

    public void setBdcSlJyxxDO(BdcSlJyxxDO bdcSlJyxxDO) {
        this.bdcSlJyxxDO = bdcSlJyxxDO;
    }

    public DsfSlxxDTO getDsfSlxxDTO() {
        return dsfSlxxDTO;
    }

    public void setDsfSlxxDTO(DsfSlxxDTO dsfSlxxDTO) {
        this.dsfSlxxDTO = dsfSlxxDTO;
    }

    public List<BdcSlSqrDTO> getBdcSlSqrDTOList() {
        return bdcSlSqrDTOList;
    }

    public void setBdcSlSqrDTOList(List<BdcSlSqrDTO> bdcSlSqrDTOList) {
        this.bdcSlSqrDTOList = bdcSlSqrDTOList;
    }


    public List<BdcSwxxDTO> getBdcSwxxDTOList() {
        return bdcSwxxDTOList;
    }

    public void setBdcSwxxDTOList(List<BdcSwxxDTO> bdcSwxxDTOList) {
        this.bdcSwxxDTOList = bdcSwxxDTOList;
    }

    public BdcSlQl getBdcSlQl() {
        return bdcSlQl;
    }

    public void setBdcSlQl(BdcSlQl bdcSlQl) {
        this.bdcSlQl = bdcSlQl;
    }

    public List<BdcSlLzrDO> getBdcSlLzrDOList() {
        return bdcSlLzrDOList;
    }

    public void setBdcSlLzrDOList(List<BdcSlLzrDO> bdcSlLzrDOList) {
        this.bdcSlLzrDOList = bdcSlLzrDOList;
    }

    public BdcSlXmDO getBdcSlXmDO() {
        return bdcSlXmDO;
    }

    public void setBdcSlXmDO(BdcSlXmDO bdcSlXmDO) {
        this.bdcSlXmDO = bdcSlXmDO;
    }

    public List<BdcDsQlrDO> getBdcDsQlrDOList() {
        return bdcDsQlrDOList;
    }

    public void setBdcDsQlrDOList(List<BdcDsQlrDO> bdcDsQlrDOList) {
        this.bdcDsQlrDOList = bdcDsQlrDOList;
    }

    public List<BdcSfxxDTO> getBdcSfxxDTOList() {
        return bdcSfxxDTOList;
    }

    public void setBdcSfxxDTOList(List<BdcSfxxDTO> bdcSfxxDTOList) {
        this.bdcSfxxDTOList = bdcSfxxDTOList;
    }

    public BdcGzdjDO getBdcGzdjDO() {
        return bdcGzdjDO;
    }

    public void setBdcGzdjDO(BdcGzdjDO bdcGzdjDO) {
        this.bdcGzdjDO = bdcGzdjDO;
    }

    public BdcSlYjxxDO getBdcSlYjxxDO() {
        return bdcSlYjxxDO;
    }

    public void setBdcSlYjxxDO(BdcSlYjxxDO bdcSlYjxxDO) {
        this.bdcSlYjxxDO = bdcSlYjxxDO;
    }

    public List<BdcSlYgDTO> getGlygydy() {
        return glygydy;
    }

    public void setGlygydy(List<BdcSlYgDTO> glygydy) {
        this.glygydy = glygydy;
    }

    @Override
    public String toString() {
        return "BdcSlXmDTO{" +
                "bdcSlXmDO=" + bdcSlXmDO +
                ", dsfSlxxDTO=" + dsfSlxxDTO +
                ", bdcSlQl=" + bdcSlQl +
                ", bdcSlJyxxDO=" + bdcSlJyxxDO +
                ", bdcSlXmLsgxList=" + bdcSlXmLsgxList +
                ", bdcSlSqrDOList=" + bdcSlSqrDOList +
                ", bdcSlSqrDTOList=" + bdcSlSqrDTOList +
                ", bdcSwxxDTOList=" + bdcSwxxDTOList +
                ", bdcSlLzrDOList=" + bdcSlLzrDOList +
                ", bdcDsQlrDOList=" + bdcDsQlrDOList +
                ", gltdzxxDTOList=" + gltdzxxDTOList +
                ", fjxx=" + fjxx +
                ", bdcSfxxDTOList=" + bdcSfxxDTOList +
                ", bdcGzdjDO=" + bdcGzdjDO +
                ", bdcSlYjxxDO=" + bdcSlYjxxDO +
                ", glygydy=" + glygydy +
                '}';
    }
}
