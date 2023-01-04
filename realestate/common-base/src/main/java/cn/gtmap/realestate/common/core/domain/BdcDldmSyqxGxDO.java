package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/7
 * @description 地类代码使用期限关系
 */
@Table(name = "BDC_DLDM_SYQX_GX")
public class BdcDldmSyqxGxDO {
    @Id
    @ApiModelProperty("主键ID")
    String id;

    @ApiModelProperty("地类代码（土地用途）")
    String dldm;

    @ApiModelProperty("土地使用期限")
    Integer syqx;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDldm() {
        return dldm;
    }

    public void setDldm(String dldm) {
        this.dldm = dldm;
    }

    public Integer getSyqx() {
        return syqx;
    }

    public void setSyqx(Integer syqx) {
        this.syqx = syqx;
    }

    @Override
    public String toString() {
        return "BdcDldmSyqxGxDO{" +
                "id='" + id + '\'' +
                ", dldm='" + dldm + '\'' +
                ", syqx=" + syqx +
                '}';
    }
}
