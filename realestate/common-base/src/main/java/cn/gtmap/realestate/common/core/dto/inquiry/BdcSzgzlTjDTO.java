package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/03/10/21:12
 * @Description: 不动产缮证工作量统计DTO
 */
@ApiModel(value = "BdcSzgzlTjDTO", description = "不动产缮证工作量统计DTO")
public class BdcSzgzlTjDTO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "业务办理总量")
    private int ywblzl;

    @ApiModelProperty(value = "证书打印业务量")
    private int zsdyywl;

    @ApiModelProperty(value = "证书印制号领用量")
    private int zsyzhlyl;

    @ApiModelProperty(value = "证书印制号使用量")
    private int zsyzhsyl;
    @ApiModelProperty(value = "证书印制号作废量")
    private int zsyzhzfl;

    @ApiModelProperty(value = "证明打印业务量")
    private int zmdyywl;

    @ApiModelProperty(value = "证明印制号领用量")
    private int zmyzhlyl;

    @ApiModelProperty(value = "证明印制号使用量")
    private int zmyzhsyl;

    @ApiModelProperty(value = "证明印制号作废量")
    private int zmyzhzfl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYwblzl() {
        return ywblzl;
    }

    public void setYwblzl(int ywblzl) {
        this.ywblzl = ywblzl;
    }

    public int getZsdyywl() {
        return zsdyywl;
    }

    public void setZsdyywl(int zsdyywl) {
        this.zsdyywl = zsdyywl;
    }

    public int getZsyzhlyl() {
        return zsyzhlyl;
    }

    public void setZsyzhlyl(int zsyzhlyl) {
        this.zsyzhlyl = zsyzhlyl;
    }

    public int getZsyzhsyl() {
        return zsyzhsyl;
    }

    public void setZsyzhsyl(int zsyzhsyl) {
        this.zsyzhsyl = zsyzhsyl;
    }

    public int getZsyzhzfl() {
        return zsyzhzfl;
    }

    public void setZsyzhzfl(int zsyzhzfl) {
        this.zsyzhzfl = zsyzhzfl;
    }

    public int getZmdyywl() {
        return zmdyywl;
    }

    public void setZmdyywl(int zmdyywl) {
        this.zmdyywl = zmdyywl;
    }

    public int getZmyzhlyl() {
        return zmyzhlyl;
    }

    public void setZmyzhlyl(int zmyzhlyl) {
        this.zmyzhlyl = zmyzhlyl;
    }

    public int getZmyzhsyl() {
        return zmyzhsyl;
    }

    public void setZmyzhsyl(int zmyzhsyl) {
        this.zmyzhsyl = zmyzhsyl;
    }

    public int getZmyzhzfl() {
        return zmyzhzfl;
    }

    public void setZmyzhzfl(int zmyzhzfl) {
        this.zmyzhzfl = zmyzhzfl;
    }

    @Override
    public String toString() {
        return "BdcSzgzlTjDTO{" +
                "name='" + name + '\'' +
                ", ywblzl=" + ywblzl +
                ", zsdyywl=" + zsdyywl +
                ", zsyzhlyl=" + zsyzhlyl +
                ", zsyzhsyl=" + zsyzhsyl +
                ", zsyzhzfl=" + zsyzhzfl +
                ", zmdyywl=" + zmdyywl +
                ", zmyzhlyl=" + zmyzhlyl +
                ", zmyzhsyl=" + zmyzhsyl +
                ", zmyzhzfl=" + zmyzhzfl +
                '}';
    }
}
