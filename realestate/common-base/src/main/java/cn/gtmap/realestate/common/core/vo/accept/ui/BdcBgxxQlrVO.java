package cn.gtmap.realestate.common.core.vo.accept.ui;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/10/30.
 * @description 变更的权利人信息VO
 */
@ApiModel(value = "BdcBgxxDbVO", description = "变更信息对比信息实体")
public class BdcBgxxQlrVO {

    /**
     * 人员变更类型：0：修改
     */
    public static final String RYBGLX_UPDATE = "0";
    /**
     * 人员变更类型：1：修改
     */
    public static final String RYBGLX_ADD = "1";
    /**
     * 人员变更类型：-1：修改
     */
    public static final String RYBGLX_DELETE = "-1";

    @ApiModelProperty(value = "人员变更类型：0：修改， 1：新增， -1：删除")
    private String type;

    @ApiModelProperty(value = "不动产权利人信息")
    private BdcQlrDO bdcQlrDO;

    @ApiModelProperty(value = "原有不动产权利人信息")
    private BdcQlrDO ybdcQlrDO;

    public BdcQlrDO getBdcQlrDO() {
        return bdcQlrDO;
    }

    public void setBdcQlrDO(BdcQlrDO bdcQlrDO) {
        this.bdcQlrDO = bdcQlrDO;
    }

    public BdcQlrDO getYbdcQlrDO() {
        return ybdcQlrDO;
    }

    public void setYbdcQlrDO(BdcQlrDO ybdcQlrDO) {
        this.ybdcQlrDO = ybdcQlrDO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BdcBgxxQlrVO(String type) {
        this.type = type;
    }

    public BdcBgxxQlrVO(){}

    private BdcBgxxQlrVO(Builder builder){
        this.setType(builder.type);
        this.setYbdcQlrDO(builder.ybdcQlrDO);
        this.setBdcQlrDO(builder.bdcQlrDO);
    }

    public static class Builder{
        private String type;
        private BdcQlrDO bdcQlrDO;
        private BdcQlrDO ybdcQlrDO;

        public Builder type(String type){
            this.type = type;
            return this;
        }

        public Builder bdcQlrDO(BdcQlrDO bdcQlrDO){
            this.bdcQlrDO = bdcQlrDO;
            return this;
        }

        public Builder ybdcQlrDO(BdcQlrDO ybdcQlrDO){
            this.ybdcQlrDO = ybdcQlrDO;
            return this;
        }

        public BdcBgxxQlrVO build(){
            return new BdcBgxxQlrVO(this);
        }
    }

}
