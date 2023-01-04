package cn.gtmap.realestate.exchange.core.dto.wwsq.ljzfy.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-03
 * @description 逻辑幢分页查询 响应结构
 */
public class LjzFyResponseDTO {

    private Integer page;

    private Integer size;

    private Integer totalPages;

    private Integer totalElements;

    private List<LjzFyResponseContent> content;

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

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public List<LjzFyResponseContent> getContent() {
        return content;
    }

    public void setContent(List<LjzFyResponseContent> content) {
        this.content = content;
    }
}
