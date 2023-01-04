package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/9/14
 * @description 验证企业信息参数
 */
@ApiModel(value = "BdcQlrInterfaceQO", description = "权利人调用第三方接口查询参数")
public class BdcYzQyxxInterfaceQO {

    @ApiModelProperty(value = "企业名称")
    private String entname;

    @ApiModelProperty(value = "法定代表人")
    private String name;

    @ApiModelProperty(value = "统一社会信用代码")
    private String uniscid;

    public String getEntname() {
        return entname;
    }

    public void setEntname(String entname) {
        this.entname = entname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniscid() {
        return uniscid;
    }

    public void setUniscid(String uniscid) {
        this.uniscid = uniscid;
    }

    @Override
    public String toString() {
        return "BdcYzQyxxInterfaceQO{" +
                "entname='" + entname + '\'' +
                ", name='" + name + '\'' +
                ", uniscid='" + uniscid + '\'' +
                '}';
    }
}
