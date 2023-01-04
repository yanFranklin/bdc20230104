package cn.gtmap.realestate.exchange.core.dto.wwsq.gzwxxcx.response;

import java.util.List;

public class Gzwxx {
    private String page;

    private String itemSize;

    private String total;

    private String pageSize;

    private String records;

    private List<GzwxxResponseDTO> bdcdyxx;

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

    public List<GzwxxResponseDTO> getBdcdyxx() {
        return bdcdyxx;
    }

    public void setBdcdyxx(List<GzwxxResponseDTO> bdcdyxx) {
        this.bdcdyxx = bdcdyxx;
    }
}
