package cn.gtmap.realestate.exchange.core.dto.wwsq.yyzmcx.response;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/6/28
 * @description 地役证明返回实体
 */
public class YyzmResponseDTO {

    private String page;

    private String itemSize;

    private String total;

    private String pageSize;

    private String records;

    private List<YyzmDTO> yyxx;

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

    public List<YyzmDTO> getYyxx() {
        return yyxx;
    }

    public void setYyxx(List<YyzmDTO> yyxx) {
        this.yyxx = yyxx;
    }

    @Override
    public String toString() {
        return "YyzmResponseDTO{" +
                "page='" + page + '\'' +
                ", itemSize='" + itemSize + '\'' +
                ", total='" + total + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", records='" + records + '\'' +
                ", yyxx=" + yyxx +
                '}';
    }
}
