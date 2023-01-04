package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/06/07
 * @description  不动产注销清单
 */
@ApiModel(description = "不动产注销清单实体")
public class BdcZxDTO {

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "不动产坐落")
    private String zl;

    @ApiModelProperty(value = "土地面积")
    private Double tdmj;

    @ApiModelProperty(value = "房屋面积")
    private Double fwmj;

    @ApiModelProperty(value = "定着物用途")
    private Integer dzwyt;

    @ApiModelProperty(value = "定着物用途名称")
    private String dzwytMc;

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getTdmj() {
        return tdmj;
    }

    public void setTdmj(Double tdmj) {
        this.tdmj = tdmj;
    }

    public Double getFwmj() {
        return fwmj;
    }

    public void setFwmj(Double fwmj) {
        this.fwmj = fwmj;
    }

    public Integer getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(Integer dzwyt) {
        this.dzwyt = dzwyt;
    }

    public String getDzwytMc() {
        return dzwytMc;
    }

    public void setDzwytMc(String dzwytMc) {
        this.dzwytMc = dzwytMc;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof BdcZxDTO)){
            return false;
        }
        BdcZxDTO bdcZxDTO = (BdcZxDTO) o;
        return Objects.equals(getSlbh(), bdcZxDTO.getSlbh()) &&
                Objects.equals(getBdcdyh(), bdcZxDTO.getBdcdyh()) &&
                Objects.equals(getZl(), bdcZxDTO.getZl()) &&
                Objects.equals(getTdmj(), bdcZxDTO.getTdmj()) &&
                Objects.equals(getFwmj(), bdcZxDTO.getFwmj()) &&
                Objects.equals(getDzwyt(), bdcZxDTO.getDzwyt()) &&
                Objects.equals(getDzwytMc(), bdcZxDTO.getDzwytMc()) &&
                Objects.equals(getBdcqzh(), bdcZxDTO.getBdcqzh()) &&
                Objects.equals(getQlr(), bdcZxDTO.getQlr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSlbh(), getBdcdyh(), getZl(), getTdmj(), getFwmj(), getDzwyt(), getDzwytMc(), getBdcqzh(), getQlr());
    }
}
