package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;

/**
 * @program: bdcdj3.0
 * @description: 测量结果材料类型字典表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-12-19 10:26
 **/
@Table(name = "BDC_SL_ZD_CLJGCLLX")
public class BdcSlZdCljgCllxDO {
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
}
