package cn.gtmap.realestate.common.core.qo.inquiry.yancheng;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/11/24
 * @description  盐城 房屋权属信息查询参数QO定义
 */
@ApiModel(value = "BdcFwQsxxQO", description = "不动产住房查询参数封装对象")
public class BdcFwQsxxQO {
    @ApiModelProperty(value = "权利人信息")
    private List<BdcFwQsxxQlrQO> qlrxx;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "房屋坐落")
    private String fwzl;

    @ApiModelProperty(value = "房屋编码")
    private String fwbm;

    /**
     * 1 不动产登记系统  2  自助查询机  3 互联网+
     */
    @ApiModelProperty(value = "查询来源")
    private String cxly;

    @ApiModelProperty(value = "规划用途")
    private String ghyt;

    /**
     * 指定类型规划用途不展示
     */
    @ApiModelProperty(value = "排除规划用途")
    private String pcghyt;

    @ApiModelProperty(value = "是否需要过滤规划用途  Y 是 N 否")
    private String sfghyt;


    public String getSfghyt() {
        return sfghyt;
    }

    public void setSfghyt(String sfghyt) {
        this.sfghyt = sfghyt;
    }

    public String getPcghyt() {
        return pcghyt;
    }

    public void setPcghyt(String pcghyt) {
        this.pcghyt = pcghyt;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getCxly() {
        return cxly;
    }

    public void setCxly(String cxly) {
        this.cxly = cxly;
    }

    public List<BdcFwQsxxQlrQO> getQlrxx() {
        return qlrxx;
    }

    public void setQlrxx(List<BdcFwQsxxQlrQO> qlrxx) {
        this.qlrxx = qlrxx;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }
}
