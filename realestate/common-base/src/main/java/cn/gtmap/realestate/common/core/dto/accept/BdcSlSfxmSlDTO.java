package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/10/22
 * @description 不动产受理收费项目数量模型
 */
@ApiModel(value = "BdcSlSfxmSlDTO", description = "不动产受理收费项目数量模型")
public class BdcSlSfxmSlDTO {

    @ApiModelProperty(value = "住宅登记费数量")
    private Integer zzdjfsl = 0;

    @ApiModelProperty(value = "非住宅登记费数量")
    private Integer fzzdjfsl = 0;

    @ApiModelProperty(value = "其他登记费数量")
    private Integer qtdjfsl;

    @ApiModelProperty(value = "工本费数量-第一本减免")
    private Integer gbfsl = 0;

    @ApiModelProperty(value = "工本费数量-最终出证数量")
    private Integer gbfslAll = 0;

    @ApiModelProperty(value = "土地收益金数量-土地面积总和")
    private Double tdsyjsl = 0d;

    @ApiModelProperty(value = "土地收益金数量-住宅面积总和")
    private Double zzmjsl = 0d;

    @ApiModelProperty(value = "土地收益金数量-非住宅面积总和")
    private Double fzzmjsl = 0d;

    @ApiModelProperty(value = "是否异议登记")
    private Boolean sfyydj = false;

    @ApiModelProperty(value = "是否特殊收费项目")
    private Boolean sftssfxm = false;

    public Integer getZzdjfsl() {
        return zzdjfsl;
    }

    public Double getTdsyjsl() {
        return tdsyjsl;
    }

    public void setTdsyjsl(Double tdsyjsl) {
        this.tdsyjsl = tdsyjsl;
    }

    public void setZzdjfsl(Integer zzdjfsl) {
        this.zzdjfsl = zzdjfsl;
    }

    public Integer getFzzdjfsl() {
        return fzzdjfsl;
    }

    public void setFzzdjfsl(Integer fzzdjfsl) {
        this.fzzdjfsl = fzzdjfsl;
    }

    public Integer getGbfsl() {
        return gbfsl;
    }

    public void setGbfsl(Integer gbfsl) {
        this.gbfsl = gbfsl;
    }

    public Double getZzmjsl() {
        return zzmjsl;
    }

    public void setZzmjsl(Double zzmjsl) {
        this.zzmjsl = zzmjsl;
    }

    public Double getFzzmjsl() {
        return fzzmjsl;
    }

    public void setFzzmjsl(Double fzzmjsl) {
        this.fzzmjsl = fzzmjsl;
    }

    public Integer getGbfslAll() {
        return gbfslAll;
    }

    public void setGbfslAll(Integer gbfslAll) {
        this.gbfslAll = gbfslAll;
    }

    public Integer getQtdjfsl() {
        return qtdjfsl;
    }

    public void setQtdjfsl(Integer qtdjfsl) {
        this.qtdjfsl = qtdjfsl;
    }

    public Boolean getSfyydj() {
        return sfyydj;
    }

    public void setSfyydj(Boolean sfyydj) {
        this.sfyydj = sfyydj;
    }

    public Boolean getSftssfxm() {
        return sftssfxm;
    }

    public void setSftssfxm(Boolean sftssfxm) {
        this.sftssfxm = sftssfxm;
    }

    @Override
    public String toString() {
        return "BdcSlSfxmSlDTO{" +
                "zzdjfsl=" + zzdjfsl +
                ", fzzdjfsl=" + fzzdjfsl +
                ", qtdjfsl=" + qtdjfsl +
                ", gbfsl=" + gbfsl +
                ", gbfslAll=" + gbfslAll +
                ", tdsyjsl=" + tdsyjsl +
                ", zzmjsl=" + zzmjsl +
                ", fzzmjsl=" + fzzmjsl +
                ", sfyydj=" + sfyydj +
                ", sftssfxm=" + sftssfxm +
                '}';
    }
}
