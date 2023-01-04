package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 工作流事件类型实体
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-24 14:52
 **/
@Table(name = "BDC_ZD_GZLSJLX")
public class BdcZdGzlsjlxDO {

    @Id
    private Integer dm;

    private String mc;

    @ApiModelProperty("事件标识")
    private String sjbs;

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

    public String getSjbs() {
        return sjbs;
    }

    public void setSjbs(String sjbs) {
        this.sjbs = sjbs;
    }
}
