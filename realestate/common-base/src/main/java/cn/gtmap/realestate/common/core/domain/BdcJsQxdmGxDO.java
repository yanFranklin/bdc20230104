package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/12
 * @description
 */
@Table(name = "BDC_JS_QXDM_GX")
public class BdcJsQxdmGxDO {

    @Id
    @ApiModelProperty(value = "关系ID")
    private String gxid;

    @ApiModelProperty(value = "角色编码")
    private String rolecode;

    @ApiModelProperty(value = "区县代码,多个用英文逗号隔开")
    private String qxdm;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }
}
