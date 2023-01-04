package cn.gtmap.realestate.common.core.dto.inquiry;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import cn.gtmap.realestate.common.core.dto.inquiry.DtcxRequestDTO;
import org.springframework.data.domain.Sort;


/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2021/1/25
 * @description 动态查询--自定义查询
 */
@ApiModel(value = "DtcxRequestDTO", description = "动态查询的request")
public class DtcxRequestDTO {

    @ApiModelProperty("加载全部")
    private Boolean loadTotal;

    @ApiModelProperty("当前页码")
    private Integer page;

    @ApiModelProperty("一页展示记录数")
    private Integer size;

    @ApiModelProperty("验证结果")
    private String data;

    @ApiModelProperty("排序")
    private Sort sort;

    public Boolean getLoadTotal() {
        return loadTotal;
    }

    public void setLoadTotal(Boolean loadTotal) {
        this.loadTotal = loadTotal;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "DtcxRequestDTO{" +
                "loadTotal=" + loadTotal +
                ", page=" + page +
                ", size=" + size +
                ", data='" + data + '\'' +
                ", sort=" + sort +
                '}';
    }
}
