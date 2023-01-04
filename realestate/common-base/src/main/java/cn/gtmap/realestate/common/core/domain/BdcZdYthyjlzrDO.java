package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 盐城一体化邮寄信息领证人字典表
 * @author: <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
 * @create: 2022-10-17 09:24
 **/
@Table(name = "BDC_ZD_YTHYJLZR")
public class BdcZdYthyjlzrDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "领证人代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "领证人名称")
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
        return "BdcZdYthyjlzrDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
