package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/29
 * @description 宗地（宗海）特征码字典表
 */
@Table(name = "BDC_ZD_ZDZHTZM")
public class BdcZdZdzhtzmDO {

    @Id
    @ApiModelProperty(value = "主键id")
    private String id;

    /**
     * 代码
     */
    @ApiModelProperty(value = "宗地宗海特征码代码")
    private String dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "宗地宗海特征码名称")
    private String mc;

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BdcZdZdzhtzmDO{" +
                "id='" + id + '\'' +
                "dm='" + dm + '\'' +
                ", mc='" + mc + '\'' +
                '}';
    }
}
