package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
 * @version 1.0, 2022/4/6
 * @description 卖方房屋套次字典表
 */
@Table(name = "BDC_SL_ZD_MFFWTC")
public class BdcSlZdMfFwtcDO {

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
        return "BdcSlZdMFFwtcDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
