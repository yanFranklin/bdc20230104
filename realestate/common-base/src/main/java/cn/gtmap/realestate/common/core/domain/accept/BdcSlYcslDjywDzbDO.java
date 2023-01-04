package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/5
 * @description 不动产受理一窗受理与登记业务流程对照表
 */
@Table(name = "BDC_SL_YCSL_DJYW_DZB")
public class BdcSlYcslDjywDzbDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "一窗受理流程定义ID")
    private String ycslgzldyid;

    @ApiModelProperty(value = "登记流程定义ID")
    private String djgzldyid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYcslgzldyid() {
        return ycslgzldyid;
    }

    public void setYcslgzldyid(String ycslgzldyid) {
        this.ycslgzldyid = ycslgzldyid;
    }

    public String getDjgzldyid() {
        return djgzldyid;
    }

    public void setDjgzldyid(String djgzldyid) {
        this.djgzldyid = djgzldyid;
    }
}
