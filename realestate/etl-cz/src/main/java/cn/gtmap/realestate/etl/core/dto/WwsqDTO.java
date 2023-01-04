package cn.gtmap.realestate.etl.core.dto;

import cn.gtmap.realestate.etl.core.domian.wwsq.GxWwSqxmDo;
import cn.gtmap.realestate.etl.core.domian.wwsq.GxWwSqxxClxxDo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/15
 * @description 外网申请DTO
 */
public class WwsqDTO {

    @ApiModelProperty("外网申请项目")
    private GxWwSqxmDo gxWwSqxmDo;

    @ApiModelProperty("共享外网申请材料信息")
    private List<GxWwSqxxClxxDo> gxWwSqxxClxxDoList;

    @ApiModelProperty("外网申请项目")
    private List<GxWwsqxxDTO> gxWwsqxxDTOList;

    @ApiModelProperty("退回意见")
    private String thyj;

    public GxWwSqxmDo getGxWwSqxmDo() {
        return gxWwSqxmDo;
    }

    public void setGxWwSqxmDo(GxWwSqxmDo gxWwSqxmDo) {
        this.gxWwSqxmDo = gxWwSqxmDo;
    }

    public List<GxWwsqxxDTO> getGxWwsqxxDTOList() {
        return gxWwsqxxDTOList;
    }

    public void setGxWwsqxxDTOList(List<GxWwsqxxDTO> gxWwsqxxDTOList) {
        this.gxWwsqxxDTOList = gxWwsqxxDTOList;
    }

    public String getThyj() {
        return thyj;
    }

    public void setThyj(String thyj) {
        this.thyj = thyj;
    }

    public List<GxWwSqxxClxxDo> getGxWwSqxxClxxDoList() {
        return gxWwSqxxClxxDoList;
    }

    public void setGxWwSqxxClxxDoList(List<GxWwSqxxClxxDo> gxWwSqxxClxxDoList) {
        this.gxWwSqxxClxxDoList = gxWwSqxxClxxDoList;
    }
}
