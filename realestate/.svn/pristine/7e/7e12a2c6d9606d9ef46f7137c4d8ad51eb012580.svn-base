package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/12
 * @description 不动产权利人ID关联表同步QO
 */
@ApiModel(value = "BdcQlridSyncQO",description = "不动产权利人ID关联表同步QO")
public class BdcQlridSyncQO {

    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;

    @ApiModelProperty(value = "同步领证人")
    private Boolean synclzr;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public boolean isSynclzr() {
        if (synclzr ==null){
            return true;
        }else {
            return synclzr;
        }
    }

    public void setSynclzr(boolean synclzr) {
        this.synclzr = synclzr;
    }
}
