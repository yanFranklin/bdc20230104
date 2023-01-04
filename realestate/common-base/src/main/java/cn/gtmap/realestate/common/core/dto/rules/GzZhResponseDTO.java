package cn.gtmap.realestate.common.core.dto.rules;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0  2018/12/14
 * @description 规则组合DTO
 */
@ApiModel(value = "GzZhResponseDTO", description = "规则组合DTO")
public class GzZhResponseDTO implements Serializable {
    private static final long serialVersionUID = 8945801696047039047L;
    /**
     * 组合ID
     */
    @ApiModelProperty(value = "组合ID")
    private String zhid;
    /**
     * 规则组合名称
     */
    @ApiModelProperty(value = "规则组合名称")
    private String gzzhmc;

    /**
     * 规则组合说明
     */
    @ApiModelProperty(value = "规则组合说明")
    private String gzzhsm;
    /**
     * 登记小类
     */
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    /**
     * 规则条件
     */
    @ApiModelProperty(value = "规则条件")
    private String gztj;

    public String getZhid() {
        return zhid;
    }

    public void setZhid(String zhid) {
        this.zhid = zhid;
    }

    public String getGzzhmc() {
        return gzzhmc;
    }

    public void setGzzhmc(String gzzhmc) {
        this.gzzhmc = gzzhmc;
    }

    public String getGzzhsm() {
        return gzzhsm;
    }

    public void setGzzhsm(String gzzhsm) {
        this.gzzhsm = gzzhsm;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getGztj() {
        return gztj;
    }

    public void setGztj(String gztj) {
        this.gztj = gztj;
    }

    @Override
    public String toString() {
        return "GzZhResponseDTO{" +
                "zhid='" + zhid + '\'' +
                ", gzzhmc='" + gzzhmc + '\'' +
                ", gzzhsm='" + gzzhsm + '\'' +
                ", djxl='" + djxl + '\'' +
                ", gztj='" + gztj + '\'' +
                '}';
    }
}