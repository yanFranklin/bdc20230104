package cn.gtmap.realestate.common.core.domain.rules;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0  2018-11-10
 * @description 不动产规则组合
 */
@Table(name = "bdc_gz_zh")
@ApiModel(value = "BdcGzZhDO", description = "不动产规则组合")
public class BdcGzZhDO implements Serializable {

    private static final long serialVersionUID = 8278736536226352213L;
    @Id
    @ApiModelProperty(value = "组合ID")
    private String zhid;

    @ApiModelProperty(value = "规则组合名称")
    private String gzzhmc;

    @ApiModelProperty(value = "规则组合说明")
    private String gzzhsm;

    @ApiModelProperty(value = "流程类型")
    private String lclx;

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

    public String getGztj() {
        return gztj;
    }

    public void setGztj(String gztj) {
        this.gztj = gztj;
    }

    public String getLclx() {
        return lclx;
    }

    public void setLclx(String lclx) {
        this.lclx = lclx;
    }

    @Override
    public String toString() {
        return "BdcGzZhDO{" +
                "zhid='" + zhid + '\'' +
                ", gzzhmc='" + gzzhmc + '\'' +
                ", gzzhsm='" + gzzhsm + '\'' +
                ", lclx='" + lclx + '\'' +
                ", gztj='" + gztj + '\'' +
                '}';
    }
}
