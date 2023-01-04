package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.scopsr.organquery;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/3 11:23
 */
public class ScopsrOrganQueryParamDTO {

    @ApiModelProperty(value = "统一社会信用代码")
    private String tyshxydm;

    @ApiModelProperty(value = "第一名称")
    private String name;

    public String getTyshxydm() {
        return tyshxydm;
    }

    public void setTyshxydm(String tyshxydm) {
        this.tyshxydm = tyshxydm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ScopsrOrganQueryParamDTO{" +
                "tyshxydm='" + tyshxydm + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
