package cn.gtmap.realestate.exchange.core.dto.wwsq.getWwsqcfCqzxxFy.response;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2021-12-06
 * @description 查封产权返回
 */
public class GetWwsqCqzxxFyResponseData {

    private String page;

    private String itemSize;

    private String total;

    private String pageSize;

    private String records;

    private List<GetWwsqCfCqzxxResponseCqxx> cqxx;

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

    public List<GetWwsqCfCqzxxResponseCqxx> getCqxx() {
        return cqxx;
    }

    public void setCqxx(List<GetWwsqCfCqzxxResponseCqxx> cqxx) {
        this.cqxx = cqxx;
    }
}
