package cn.gtmap.realestate.exchange.core.dto.wwsq.sczmd.response;


import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-27
 * @description
 */
public class SczmdResponseDTO {

    private String page;

    private String itemSize;

    private String total;

    private String pageSize;

    private String records;

    private List<SczmdResponseData> data;

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

    public List<SczmdResponseData> getData() {
        return data;
    }

    public void setData(List<SczmdResponseData> data) {
        this.data = data;
    }
}
