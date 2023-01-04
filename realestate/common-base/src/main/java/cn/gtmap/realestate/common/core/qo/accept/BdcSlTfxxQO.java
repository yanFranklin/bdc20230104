package cn.gtmap.realestate.common.core.qo.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlTfxxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/11/19.
 * @description 不动产受理退费信息QO
 */
@ApiModel(value = "BdcSlTfxxQO", description = "不动产受理退费信息QO")
public class BdcSlTfxxQO  extends BdcSlTfxxDO {

    @ApiModelProperty(value = "收费开始时间")
    private String sfkssj;
    @ApiModelProperty(value = "收费结束时间")
    private String sfjssj;
    @ApiModelProperty(value = "申请退费开始时间")
    private String sqtfkssj;
    @ApiModelProperty(value = "申请退费结束时间")
    private String sqtfjssj;

    public String getSfkssj() {
        return sfkssj;
    }

    public void setSfkssj(String sfkssj) {
        this.sfkssj = sfkssj;
    }

    public String getSfjssj() {
        return sfjssj;
    }

    public void setSfjssj(String sfjssj) {
        this.sfjssj = sfjssj;
    }

    public String getSqtfkssj() {
        return sqtfkssj;
    }

    public void setSqtfkssj(String sqtfkssj) {
        this.sqtfkssj = sqtfkssj;
    }

    public String getSqtfjssj() {
        return sqtfjssj;
    }

    public void setSqtfjssj(String sqtfjssj) {
        this.sqtfjssj = sqtfjssj;
    }
}
