package cn.gtmap.realestate.supervise.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政区划表
 */
@Table(name = "LF_XZQH")
public class LfXzqhDO {
    @Id
    @ApiModelProperty(value = "区划代码")
    private String qhdm;

    @ApiModelProperty(value = "区划名称")
    private String qhmc;


    public String getQhdm() {
        return qhdm;
    }

    public void setQhdm(String qhdm) {
        this.qhdm = qhdm;
    }

    public String getQhmc() {
        return qhmc;
    }

    public void setQhmc(String qhmc) {
        this.qhmc = qhmc;
    }

    @Override
    public String toString() {
        return "LfXzqhDO{" +
                "qhdm='" + qhdm + '\'' +
                ", qhmc='" + qhmc + '\'' +
                '}';
    }
}
