package cn.gtmap.realestate.common.core.qo.rules;
/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/11/29 13:43
 * @description
 */

import cn.gtmap.realestate.common.core.dto.rules.BdcGzYzsjDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/11/29 13:43
 * @description
 */
@ApiModel(value = "BdcGzZhQO", description = "不动产规则组合信息")
public class BdcGzZhQO implements Serializable {
    private static final long serialVersionUID = 1131979640123546345L;
    @ApiModelProperty(value = "登记大类")
    private String djdl;

    @ApiModelProperty(value = "规则验证数据")
    private List<BdcGzYzsjDTO> bdcgzyzsjdtolist;

    @ApiModelProperty(value = "验证模式")
    private String yzms;

    @ApiModelProperty(value = "工作流定义id")
    private String gzldyid;

    public String getDjdl() {
        return djdl;
    }

    public void setDjdl(String djdl) {
        this.djdl = djdl;
    }

    public List<BdcGzYzsjDTO> getBdcgzyzsjdtolist() {
        return bdcgzyzsjdtolist;
    }

    public void setBdcgzyzsjdtolist(List<BdcGzYzsjDTO> bdcgzyzsjdtolist) {
        this.bdcgzyzsjdtolist = bdcgzyzsjdtolist;
    }

    public String getYzms() {
        return yzms;
    }

    public void setYzms(String yzms) {
        this.yzms = yzms;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    @Override
    public String toString() {
        return "BdcGzZhQO{" +
                "djdl='" + djdl + '\'' +
                ", bdcgzyzsjdtolist=" + bdcgzyzsjdtolist +
                ", yzms='" + yzms + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                '}';
    }
}
