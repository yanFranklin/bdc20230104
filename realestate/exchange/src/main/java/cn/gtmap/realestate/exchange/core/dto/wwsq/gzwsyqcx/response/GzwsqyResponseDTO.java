package cn.gtmap.realestate.exchange.core.dto.wwsq.gzwsyqcx.response;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/6/29
 * @description 构（建）筑物所有权查询DTO
 */
public class GzwsqyResponseDTO {

    private String page;

    private String itemSize;

    private String total;

    private String pageSize;

    private String records;

    private List<GzwsqyDTO> gzwsyqxx;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public List<GzwsqyDTO> getGzwsyqxx() {
        return gzwsyqxx;
    }

    public void setGzwsyqxx(List<GzwsqyDTO> gzwsyqxx) {
        this.gzwsyqxx = gzwsyqxx;
    }

    @Override
    public String toString() {
        return "GzwsqyResponseDTO{" +
                "page='" + page + '\'' +
                ", itemSize='" + itemSize + '\'' +
                ", total='" + total + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", records='" + records + '\'' +
                ", gzwsyqxx=" + gzwsyqxx +
                '}';
    }
}
