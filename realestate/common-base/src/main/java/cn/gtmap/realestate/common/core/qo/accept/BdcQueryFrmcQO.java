package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/9/14
 * @description 验证企业信息参数
 */
@ApiModel(value = "BdcQlrInterfaceQO", description = "权利人调用第三方接口查询参数")
public class BdcQueryFrmcQO {

    @ApiModelProperty(value = "接口名称")
    private String beanId;

    @ApiModelProperty(value = "机构类别")
    private Integer jglb;

    @ApiModelProperty(value = "机构名称")
    private String jgmc;

    @ApiModelProperty(value = "机构证件种类")
    private Integer jgzjzl;

    @ApiModelProperty(value = "机构证件编号")
    private String jgzjbh;

    @ApiModelProperty(value = "法人名称")
    private String frmc;

    @ApiModelProperty(value = "权利人类型")
    private Integer qlrlx;

    @ApiModelProperty(value = "权利人类别")
    private Integer qlrlb;


    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public Integer getJglb() {
        return jglb;
    }

    public void setJglb(Integer jglb) {
        this.jglb = jglb;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public Integer getJgzjzl() {
        return jgzjzl;
    }

    public void setJgzjzl(Integer jgzjzl) {
        this.jgzjzl = jgzjzl;
    }

    public String getJgzjbh() {
        return jgzjbh;
    }

    public void setJgzjbh(String jgzjbh) {
        this.jgzjbh = jgzjbh;
    }

    public String getFrmc() {
        return frmc;
    }

    public void setFrmc(String frmc) {
        this.frmc = frmc;
    }

    public Integer getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(Integer qlrlx) {
        this.qlrlx = qlrlx;
    }

    public Integer getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(Integer qlrlb) {
        this.qlrlb = qlrlb;
    }

    @Override
    public String toString() {
        return "BdcQueryFrmcQO{" +
                "beanId='" + beanId + '\'' +
                ", jglb=" + jglb +
                ", jgmc='" + jgmc + '\'' +
                ", jgzjzl=" + jgzjzl +
                ", jgzjbh='" + jgzjbh + '\'' +
                ", frmc='" + frmc + '\'' +
                ", qlrlx=" + qlrlx +
                ", qlrlb=" + qlrlb +
                '}';
    }
}
