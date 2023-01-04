package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjxxDO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTOForZhlc;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.GltdzxxDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 受理信息
 */
@ApiModel(value = "BdcSlxxDTO", description = "受理信息模型")
public class BdcSlxxDTO implements Serializable {
    private static final long serialVersionUID = 9143208260816257109L;
    @ApiModelProperty(value = "是否创建前验证，true:创建前验证，false:创建时验证")
    private boolean beforeCjGzyz;
    @ApiModelProperty(value = "不动产受理基本信息")
    private BdcSlJbxxDO bdcSlJbxx;
    @ApiModelProperty(value = "项目类模型集合")
    private List<BdcSlXmDTO> bdcSlXmList;
    @ApiModelProperty(value = "不动产受理邮寄信息")
    private BdcSlYjxxDO bdcSlYjxxDO;
    @ApiModelProperty(value = "不动产水电气信息")
    private BdcSdqghDTO bdcSdqghDTO;

    @ApiModelProperty(value = "验证关联信息")
    private List<GltdzxxDTO> yzFctdList;

    @ApiModelProperty(value = "组合流程合并附件材料")
    private List<FjclDTOForZhlc> hbfjxx;

    @ApiModelProperty(value = "组合标识")
    private String zhbs;

    @ApiModelProperty(value = "是否信息补录")
    private boolean xxbl = false;

    public String getZhbs() {
        return zhbs;
    }

    public void setZhbs(String zhbs) {
        this.zhbs = zhbs;
    }

    public List<FjclDTOForZhlc> getHbfjxx() {
        return hbfjxx;
    }

    public void setHbfjxx(List<FjclDTOForZhlc> hbfjxx) {
        this.hbfjxx = hbfjxx;
    }

    public List<GltdzxxDTO> getYzFctdList() {
        return yzFctdList;
    }

    public void setYzFctdList(List<GltdzxxDTO> yzFctdList) {
        this.yzFctdList = yzFctdList;
    }

    public BdcSdqghDTO getBdcSdqghDTO() {
        return bdcSdqghDTO;
    }

    public void setBdcSdqghDTO(BdcSdqghDTO bdcSdqghDTO) {
        this.bdcSdqghDTO = bdcSdqghDTO;
    }

    public BdcSlJbxxDO getBdcSlJbxx() {
        return bdcSlJbxx;
    }

    public void setBdcSlJbxx(BdcSlJbxxDO bdcSlJbxx) {
        this.bdcSlJbxx = bdcSlJbxx;
    }

    public List<BdcSlXmDTO> getBdcSlXmList() {
        return bdcSlXmList;
    }

    public void setBdcSlXmList(List<BdcSlXmDTO> bdcSlXmList) {
        this.bdcSlXmList = bdcSlXmList;
    }

    public BdcSlYjxxDO getBdcSlYjxxDO() {
        return bdcSlYjxxDO;
    }

    public void setBdcSlYjxxDO(BdcSlYjxxDO bdcSlYjxxDO) {
        this.bdcSlYjxxDO = bdcSlYjxxDO;
    }


    public boolean isBeforeCjGzyz() {
        return beforeCjGzyz;
    }

    public void setBeforeCjGzyz(boolean beforeCjGzyz) {
        this.beforeCjGzyz = beforeCjGzyz;
    }

    public boolean getXxbl() {
        return xxbl;
    }

    public void setXxbl(boolean xxbl) {
        this.xxbl = xxbl;
    }

    @Override
    public String toString() {
        return "BdcSlxxDTO{" +
                "beforeCjGzyz=" + beforeCjGzyz +
                ", bdcSlJbxx=" + bdcSlJbxx +
                ", bdcSlXmList=" + bdcSlXmList +
                ", bdcSlYjxxDO=" + bdcSlYjxxDO +
                ", bdcSdqghDTO=" + bdcSdqghDTO +
                ", yzFctdList=" + yzFctdList +
                ", hbfjxx=" + hbfjxx +
                ", zhbs='" + zhbs + '\'' +
                ", xxbl=" + xxbl +
                '}';
    }
}
