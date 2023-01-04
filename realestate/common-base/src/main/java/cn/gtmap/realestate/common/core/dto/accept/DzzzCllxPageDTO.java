package cn.gtmap.realestate.common.core.dto.accept;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 电子证照简要材料信息DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-12-30 15:23
 **/
public class DzzzCllxPageDTO implements Serializable {
    private static final long serialVersionUID = 5713862139433002800L;

    private String currentPageNo;

    private String pageSize;

    private String totalCount;

    private List<DzzzCllxDTO> result;

    public String getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(String currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<DzzzCllxDTO> getResult() {
        return result;
    }

    public void setResult(List<DzzzCllxDTO> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DzzzJyclxxPageDTO{" +
                "currentPageNo='" + currentPageNo + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", result=" + result +
                '}';
    }
}
