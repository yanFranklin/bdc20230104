package cn.gtmap.realestate.common.core.domain.engine;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/4/3
 * @description 不动产规则 —— 流程和组合规则关系DO对象
 */
@Table(name = "BDC_GZ_LC_ZHGZ_GX")
public class BdcGzLcZhgzGxDO {
    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "流程名称")
    private String lcmc;

    @ApiModelProperty(value = "流程标识")
    private String lcbs;

    @ApiModelProperty(value = "组合规则名称")
    private String zhgzmc;

    @ApiModelProperty(value = "组合规则标识")
    private String zhgzbs;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getLcbs() {
        return lcbs;
    }

    public void setLcbs(String lcbs) {
        this.lcbs = lcbs;
    }

    public String getZhgzmc() {
        return zhgzmc;
    }

    public void setZhgzmc(String zhgzmc) {
        this.zhgzmc = zhgzmc;
    }

    public String getZhgzbs() {
        return zhgzbs;
    }

    public void setZhgzbs(String zhgzbs) {
        this.zhgzbs = zhgzbs;
    }

    @Override
    public String toString() {
        return "BdcGzLcZhgzGxDO{" +
                "id='" + id + '\'' +
                ", lcmc='" + lcmc + '\'' +
                ", lcbs='" + lcbs + '\'' +
                ", zhgzmc='" + zhgzmc + '\'' +
                ", zhgzbs='" + zhgzbs + '\'' +
                '}';
    }
}
