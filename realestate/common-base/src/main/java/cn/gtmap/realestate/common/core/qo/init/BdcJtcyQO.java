package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/8/4
 * @description
 */
@ApiModel(value = "BdcJtcyQO",description = "不动产家庭成员查询参数封装对象")
public class BdcJtcyQO {

    @ApiModelProperty(value = "家庭成员ID")
    private String jtcyid;

    @ApiModelProperty(value = "户口簿编码")
    private String hkbbm;

    public String getJtcyid() {
        return jtcyid;
    }

    public void setJtcyid(String jtcyid) {
        this.jtcyid = jtcyid;
    }

    public String getHkbbm() {
        return hkbbm;
    }

    public void setHkbbm(String hkbbm) {
        this.hkbbm = hkbbm;
    }
}
