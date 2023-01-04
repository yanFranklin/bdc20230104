package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/1/11
 * @description 工程进度字典表
 */
@Api(value = "BdcGcjdDO", description = "工程进度")
@Table(name = "BDC_ZD_GCJD")
public class BdcZdGcjdDO {
    @Id
    @ApiModelProperty(value = "代码")
    private Integer dm;

    @ApiModelProperty(value = "名称")
    private String mc;


    public Integer getDm() {
        return dm;
    }

    public void setDm(Integer dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    @Override
    public String toString() {
        return "BdcZdGcjdDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
