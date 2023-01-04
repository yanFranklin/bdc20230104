package cn.gtmap.realestate.exchange.core.dto.hefei.yhyjxx;

import cn.gtmap.realestate.common.core.dto.building.ZdxxByJqxxDTO;

import java.util.List;

public class QueryYjjfxxListResponseData {

    private String page;

    private String itemSize;

    private String total;

    private String pageSize;

    private String records;

    private List<YjjfxxDTO> yjjfxxList;

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

    public List<YjjfxxDTO> getYjjfxxList() {
        return yjjfxxList;
    }

    public void setYjjfxxList(List<YjjfxxDTO> yjjfxxList) {
        this.yjjfxxList = yjjfxxList;
    }

    @Override
    public String toString() {
        return "QueryYjjfxxListResponseData{" +
                "page='" + page + '\'' +
                ", itemSize='" + itemSize + '\'' +
                ", total='" + total + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", records='" + records + '\'' +
                ", yjjfxxList=" + yjjfxxList +
                '}';
    }
}
