package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.vo.accept.ui.BdcdjjfglVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/11/7
 * @description 收费推送信息
 */
public class BdcLcTsjjsDTO {

    @ApiModelProperty(value = "推送缴费集合")
    private List<BdcdjjfglVO> content;

    @ApiModelProperty(value = "缴费金额合计")
    private Double hj;

    @ApiModelProperty(value = "流程合计")
    private Integer lcsl;

    @ApiModelProperty(value = "流程受理编号")
    private String lcslbh;

    @ApiModelProperty(value = "提示信息")
    private String msg;

    @ApiModelProperty(value = "分页响应码：0成功")
    private Integer code;

    @ApiModelProperty(value = "总数")
    private Integer totalElements;

    public BdcLcTsjjsDTO init(){
        BdcLcTsjjsDTO bdcLcTsjjsDTO =new BdcLcTsjjsDTO();
        bdcLcTsjjsDTO.setCode(1);
        bdcLcTsjjsDTO.setTotalElements(0);
        bdcLcTsjjsDTO.setContent(new ArrayList<>());
        return bdcLcTsjjsDTO;
    }

    public List<BdcdjjfglVO> getContent() {
        return content;
    }

    public void setContent(List<BdcdjjfglVO> content) {
        this.content = content;
    }

    public Double getHj() {
        return hj;
    }

    public void setHj(Double hj) {
        this.hj = hj;
    }

    public Integer getLcsl() {
        return lcsl;
    }

    public void setLcsl(Integer lcsl) {
        this.lcsl = lcsl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public String getLcslbh() {
        return lcslbh;
    }

    public void setLcslbh(String lcslbh) {
        this.lcslbh = lcslbh;
    }
}
