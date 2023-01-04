package cn.gtmap.realestate.common.core.dto.exchange.wwsq.getWwsqBdcdyxx.response;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/7/13
 * @description 不动产单元信息DTO
 */
public class WwsqBdcdyxxResponseDTO {
    private String page;

    private String itemSize;

    private String total;

    private String pageSize;

    private String records;

    private List<WwsqBdcdyxxDTO> bdcdyxx;

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

    public List<WwsqBdcdyxxDTO> getBdcdyxx() {
        return bdcdyxx;
    }

    public void setBdcdyxx(List<WwsqBdcdyxxDTO> bdcdyxx) {
        this.bdcdyxx = bdcdyxx;
    }

    @Override
    public String toString() {
        return "WwsqBdcdyxxResponseDTO{" +
                "page='" + page + '\'' +
                ", itemSize='" + itemSize + '\'' +
                ", total='" + total + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", records='" + records + '\'' +
                ", bdcdyxx=" + bdcdyxx +
                '}';
    }
}
