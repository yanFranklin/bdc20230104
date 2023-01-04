package cn.gtmap.realestate.exchange.core.dto.wwsq.tdsyqcx.response;

import cn.gtmap.realestate.exchange.core.dto.wwsq.gzwsyqcx.response.GzwsqyDTO;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/6/29
 * @description 土地所有权查询DTO
 */
public class TdsqyResponseDTO {

    private String page;

    private String itemSize;

    private String total;

    private String pageSize;

    private String records;

    private List<TdsyqDTO> tdsyqxx;

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

    public List<TdsyqDTO> getTdsyqxx() {
        return tdsyqxx;
    }

    public void setTdsyqxx(List<TdsyqDTO> tdsyqxx) {
        this.tdsyqxx = tdsyqxx;
    }

    @Override
    public String toString() {
        return "TdsqyResponseDTO{" +
                "page='" + page + '\'' +
                ", itemSize='" + itemSize + '\'' +
                ", total='" + total + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", records='" + records + '\'' +
                ", tdsyqxx=" + tdsyqxx +
                '}';
    }
}
