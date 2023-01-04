package cn.gtmap.realestate.common.core.domain.check;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lst
 * @version 1.0, 2020-01-03
 * @description 规则类型字典表实体类
 */
@Table(name = "CHECK_ZD_GZLX")
@ApiModel(value = "CheckZdGzlxDO",description = "规则类型字典表")
public class CheckZdGzlxDO {
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
        return "CheckZdGzlxDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
