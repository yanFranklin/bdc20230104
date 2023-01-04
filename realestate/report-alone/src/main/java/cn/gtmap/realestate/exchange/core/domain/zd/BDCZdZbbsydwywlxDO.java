package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/5 16:43
 * @description  字典：中编办事业单位业务类型
 */
@Table(name = "BDC_ZD_ZBBSYDWYWLX")
public class BDCZdZbbsydwywlxDO {

    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "编码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "国籍")
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
        return "BdcZdGjDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }

}