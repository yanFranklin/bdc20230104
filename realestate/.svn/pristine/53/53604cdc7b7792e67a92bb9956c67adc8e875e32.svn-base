package cn.gtmap.realestate.common.core.vo.accept.ui;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-11
 * @description 受理收费信息VO
 */
@ApiModel(value = "BdcSlSfxxVO", description = "不动产受理收费信息VO")
public class BdcSlSfxxVO extends BdcSlSfxxDO {

    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;
    @ApiModelProperty(value = "收费项目")
    private List<BdcSlSfxmVO> bdcSlSfxmVOList;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @ApiModelProperty(value = "用途")
    private Integer yt;
    @ApiModelProperty(value = "不动产证明号")
    private String bdcqzh;

    public String getSfxmmc() {
        return sfxmmc;
    }

    public void setSfxmmc(String sfxmmc) {
        this.sfxmmc = sfxmmc;
    }

    public List<BdcSlSfxmVO> getBdcSlSfxmVOList() {
        return bdcSlSfxmVOList;
    }

    public void setBdcSlSfxmVOList(List<BdcSlSfxmVO> bdcSlSfxmVOList) {
        this.bdcSlSfxmVOList = bdcSlSfxmVOList;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Integer getYt() {
        return yt;
    }

    public void setYt(Integer yt) {
        this.yt = yt;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    @Override
    public String toString() {
        return "BdcSlSfxxVO{" +
                "sfxmmc='" + sfxmmc + '\'' +
                ", bdcSlSfxmVOList=" + bdcSlSfxmVOList +
                ", zl='" + zl + '\'' +
                ", slbh='" + slbh + '\'' +
                ", djyy='" + djyy + '\'' +
                ", yt=" + yt +
                ", bdcqzh='" + bdcqzh + '\'' +
                '}';
    }
}
