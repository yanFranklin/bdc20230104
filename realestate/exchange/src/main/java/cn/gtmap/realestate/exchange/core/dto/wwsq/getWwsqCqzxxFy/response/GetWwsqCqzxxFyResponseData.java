package cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqCqzxxFy.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-07
 * @description
 */
public class GetWwsqCqzxxFyResponseData {

    private String page;

    private String itemSize;

    private String total;

    private String pageSize;

    private String records;

    private List<GetWwsqCqzxxResponseCqxx> cqxx;

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

    public List<GetWwsqCqzxxResponseCqxx> getCqxx() {
        return cqxx;
    }

    public void setCqxx(List<GetWwsqCqzxxResponseCqxx> cqxx) {
        this.cqxx = cqxx;
    }
}
