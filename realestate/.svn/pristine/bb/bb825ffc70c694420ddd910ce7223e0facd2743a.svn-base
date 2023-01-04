package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmSfbzDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/22
 * @description
 */
@ApiModel(value = "BdcSlSfxmDTO", description = "不动产受理收费项目类")
public class BdcSlSfxmDTO implements Serializable {
    private static final long serialVersionUID = 1684077382764979497L;
    @ApiModelProperty(value = "收费项目代码")
    private String sfxmdm;
    @ApiModelProperty(value = "收费项目名称")
    private String sfxmmc;
    @ApiModelProperty(value = "不动产受理收费项目收费标准")
    private List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzList;
    @ApiModelProperty(value = "数量")
    private Integer mrsl;
    @ApiModelProperty(value = "金额单位")
    private String mrjedw;
    @ApiModelProperty(value = "计算系数")
    private Double jsxs;
    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;

    public BdcSlSfxmDTO(String sfxmdm, String sfxmmc, Integer mrsl, String mrjedw, Double jsxs, String qlrlb) {
        this.sfxmdm = sfxmdm;
        this.sfxmmc = sfxmmc;
        this.mrsl = mrsl;
        this.mrjedw = mrjedw;
        this.jsxs = jsxs;
        this.qlrlb = qlrlb;
    }

    public BdcSlSfxmDTO() {
        super();
    }

    public String getSfxmdm() {
        return sfxmdm;
    }

    public void setSfxmdm(String sfxmdm) {
        this.sfxmdm = sfxmdm;
    }

    public String getSfxmmc() {
        return sfxmmc;
    }

    public void setSfxmmc(String sfxmmc) {
        this.sfxmmc = sfxmmc;
    }

    public List<BdcSlSfxmSfbzDO> getBdcSlSfxmSfbzList() {
        return bdcSlSfxmSfbzList;
    }

    public void setBdcSlSfxmSfbzList(List<BdcSlSfxmSfbzDO> bdcSlSfxmSfbzList) {
        this.bdcSlSfxmSfbzList = bdcSlSfxmSfbzList;
    }

    public Integer getMrsl() {
        return mrsl;
    }

    public void setMrsl(Integer mrsl) {
        this.mrsl = mrsl;
    }

    public String getMrjedw() {
        return mrjedw;
    }

    public void setMrjedw(String mrjedw) {
        this.mrjedw = mrjedw;
    }

    public Double getJsxs() {
        return jsxs;
    }

    public void setJsxs(Double jsxs) {
        this.jsxs = jsxs;
    }

    @Override
    public String toString() {
        return "BdcSlSfxmDTO{" +
                "sfxmdm='" + sfxmdm + '\'' +
                ", sfxmmc='" + sfxmmc + '\'' +
                ", bdcSlSfxmSfbzList=" + bdcSlSfxmSfbzList +
                ", mrsl=" + mrsl +
                ", mrjedw='" + mrjedw + '\'' +
                ", jsxs=" + jsxs +
                ", qlrlb='" + qlrlb + '\'' +
                '}';
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        BdcSlSfxmDTO that = (BdcSlSfxmDTO) o;
        return sfxmdm.equals(that.sfxmdm) && sfxmmc.equals(that.sfxmmc) && bdcSlSfxmSfbzList.equals(that.bdcSlSfxmSfbzList) && mrsl.equals(that.mrsl) && mrjedw.equals(that.mrjedw) && jsxs.equals(that.jsxs) && qlrlb.equals(that.qlrlb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sfxmdm, sfxmmc, bdcSlSfxmSfbzList, mrsl, mrjedw, jsxs, qlrlb);
    }
}
