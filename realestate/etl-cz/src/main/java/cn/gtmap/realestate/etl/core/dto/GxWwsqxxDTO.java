package cn.gtmap.realestate.etl.core.dto;

import cn.gtmap.realestate.etl.core.domian.wwsq.GxWwSqxxClxxDo;
import cn.gtmap.realestate.etl.core.domian.wwsq.GxWwSqxxDo;
import cn.gtmap.realestate.etl.core.domian.wwsq.GxWwSqxxQlrDo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/15
 * @description 共享外网申请信息DTO
 */
public class GxWwsqxxDTO {

    @ApiModelProperty("共享外网申请信息")
    private GxWwSqxxDo gxWwSqxxDo;

    @ApiModelProperty("共享外网申请不动产字典值信息")
    private GxWwsqBdcZdzDTO gxWwsqBdcZdzDTO;

    @ApiModelProperty("共享外网申请权利人信息")
    private List<GxWwSqxxQlrDo> gxWwSqxxQlrDoList;

    public GxWwSqxxDo getGxWwSqxxDo() {
        return gxWwSqxxDo;
    }

    public void setGxWwSqxxDo(GxWwSqxxDo gxWwSqxxDo) {
        this.gxWwSqxxDo = gxWwSqxxDo;
    }

    public GxWwsqBdcZdzDTO getGxWwsqBdcZdzDTO() {
        return gxWwsqBdcZdzDTO;
    }

    public void setGxWwsqBdcZdzDTO(GxWwsqBdcZdzDTO gxWwsqBdcZdzDTO) {
        this.gxWwsqBdcZdzDTO = gxWwsqBdcZdzDTO;
    }

    public List<GxWwSqxxQlrDo> getGxWwSqxxQlrDoList() {
        return gxWwSqxxQlrDoList;
    }

    public void setGxWwSqxxQlrDoList(List<GxWwSqxxQlrDo> gxWwSqxxQlrDoList) {
        this.gxWwSqxxQlrDoList = gxWwSqxxQlrDoList;
    }

}
