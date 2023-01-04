package cn.gtmap.realestate.register.core.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/11/17
 * @description 外联注销产权对应的限制权利DTO
 */
public class BdcWlzxcqXzqlDTO {

    @ApiModelProperty("限制权利项目ID")
    private String xzqlxmid;

    @ApiModelProperty("单元锁定ID")
    private String dysdid;

    @ApiModelProperty("限制权利的单元号")
    private String xzqldyh;

    @ApiModelProperty("当前项目ID")
    private String xmid;

    @ApiModelProperty("当前项目单元号")
    private String bdcdyh;

    public String getXzqlxmid() {
        return xzqlxmid;
    }

    public void setXzqlxmid(String xzqlxmid) {
        this.xzqlxmid = xzqlxmid;
    }

    public String getDysdid() {
        return dysdid;
    }

    public void setDysdid(String dysdid) {
        this.dysdid = dysdid;
    }

    public String getXzqldyh() {
        return xzqldyh;
    }

    public void setXzqldyh(String xzqldyh) {
        this.xzqldyh = xzqldyh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }
}
