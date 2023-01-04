package cn.gtmap.realestate.common.core.qo.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/21
 * @description 不动产受理收费页面类
 */
public class BdcSlSfxxWithDepartmentQO implements Serializable {

    private static final long serialVersionUID = -7108652328327567119L;
    @ApiModelProperty(value = "不动产受理收费页面类")
    @JsonProperty(value = "Data")
    private BdcSlSfxxQO data;
    @ApiModelProperty(value = "登记部门代码列表")
    @JsonProperty(value = "DepartmentList")
    private List<String> djbmdmList;

    @ApiModelProperty(value = "page")
    @JsonProperty(value = "page")
    private Integer page;
    @ApiModelProperty(value = "size")
    @JsonProperty(value = "size")
    private Integer size;

    public List<String> getDjbmdmList() {
        return djbmdmList;
    }

    public void setDjbmdmList(List<String> djbmdmList) {
        this.djbmdmList = djbmdmList;
    }

    public BdcSlSfxxQO getData() {
        return data;
    }

    public void setData(BdcSlSfxxQO data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
