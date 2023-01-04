package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/18
 * @description 一窗推送登记请求对象
 */
@ApiModel(value = "BdcTsDjxxRequestDTO", description = "一窗推送登记请求对象")
public class BdcTsDjxxRequestDTO {

    @ApiModelProperty(value = "基本信息ID")
    private String jbxxid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "受理人登录名")
    private String slrdlm;

    @ApiModelProperty(value = "一体化推送登记配置")
    private BdcYcslPzDTO bdcYcslPzDTO;

    @ApiModelProperty(value = "一体化受理信息实体")
    private BdcSlxxDTO bdcSlxxDTO;

    public String getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(String jbxxid) {
        this.jbxxid = jbxxid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSlrdlm() {
        return slrdlm;
    }

    public void setSlrdlm(String slrdlm) {
        this.slrdlm = slrdlm;
    }

    public BdcYcslPzDTO getBdcYcslPzDTO() {
        return bdcYcslPzDTO;
    }

    public void setBdcYcslPzDTO(BdcYcslPzDTO bdcYcslPzDTO) {
        this.bdcYcslPzDTO = bdcYcslPzDTO;
    }

    public BdcSlxxDTO getBdcSlxxDTO() {
        return bdcSlxxDTO;
    }

    public void setBdcSlxxDTO(BdcSlxxDTO bdcSlxxDTO) {
        this.bdcSlxxDTO = bdcSlxxDTO;
    }
}
