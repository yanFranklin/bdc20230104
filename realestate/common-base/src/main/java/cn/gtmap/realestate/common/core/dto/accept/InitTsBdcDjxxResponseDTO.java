package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/12
 * @description 一窗创建登记流程返回结果
 */
public class InitTsBdcDjxxResponseDTO {

    @ApiModelProperty(value = "提示信息-失败信息")
    private String msg;

    @ApiModelProperty(value = "登记受理编号")
    private String slbh;

    @ApiModelProperty(value = "登记工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "流程状态")
    private String lczt;

    @ApiModelProperty(value = "节点ID")
    private String taskId;

    @ApiModelProperty(value = "登记项目集合")
    private List<BdcXmDO> bdcXmDOList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getLczt() {
        return lczt;
    }

    public void setLczt(String lczt) {
        this.lczt = lczt;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<BdcXmDO> getBdcXmDOList() {
        return bdcXmDOList;
    }

    public void setBdcXmDOList(List<BdcXmDO> bdcXmDOList) {
        this.bdcXmDOList = bdcXmDOList;
    }
}
