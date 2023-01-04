package cn.gtmap.realestate.common.core.qo.config;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Id;

/**
 * @author <a href ="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.3, 2021/08/20
 * @description 不动产省市级共享接口查询DO
 */
public class BdcZdSsjGxQO {
    @ApiModelProperty(value = "父目录代码")
    private String fmldm;
    @ApiModelProperty(value = "子目录代码")
    private String zmldm;
    @ApiModelProperty(value = "父目录名称")
    private String fmlmc;
    @ApiModelProperty(value = "子目录名称")
    private String zmlmc;
    @ApiModelProperty(value = "页面地址")
    private String ymdz;
    @ApiModelProperty(value = "接口地址")
    private String jkdz;
    @ApiModelProperty(value = "接口名称")
    private String jkmc;

    public String getFmldm() {
        return fmldm;
    }

    public void setFmldm(String fmldm) {
        this.fmldm = fmldm;
    }

    public String getZmldm() {
        return zmldm;
    }

    public void setZmldm(String zmldm) {
        this.zmldm = zmldm;
    }

    public String getFmlmc() {
        return fmlmc;
    }

    public void setFmlmc(String fmlmc) {
        this.fmlmc = fmlmc;
    }

    public String getZmlmc() {
        return zmlmc;
    }

    public void setZmlmc(String zmlmc) {
        this.zmlmc = zmlmc;
    }


    public String getYmdz() {
        return ymdz;
    }

    public void setYmdz(String ymdz) {
        this.ymdz = ymdz;
    }

    public String getJkdz() {
        return jkdz;
    }

    public void setJkdz(String jkdz) {
        this.jkdz = jkdz;
    }

    public String getJkmc() {
        return jkmc;
    }

    public void setJkmc(String jkmc) {
        this.jkmc = jkmc;
    }

    @Override
    public String toString() {
        return "BdcZdSsjGxDO{" +
                ", fmldm='" + fmldm + '\'' +
                ", zmldm='" + zmldm + '\'' +
                ", fmlmc='" + fmlmc + '\'' +
                ", zmlmc='" + zmlmc + '\'' +
                ", ymdz='" + ymdz + '\'' +
                ", jkdz='" + jkdz + '\'' +
                ", jkmc='" + jkmc + '\'' +
                '}';
    }

    public boolean isEmpty() {
        if (StringUtils.isBlank(this.getFmldm())
                && StringUtils.isBlank(this.getFmlmc())
                && StringUtils.isBlank(this.getJkdz())
                && StringUtils.isBlank(this.getJkmc())
                && StringUtils.isBlank(this.getZmldm())
                && StringUtils.isBlank(this.getYmdz())
                && StringUtils.isBlank(this.getZmlmc())) {
            return true;
        }else {
            return false;
        }
    }
}
