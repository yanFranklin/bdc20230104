package cn.gtmap.realestate.building.ui.core.vo;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author
 * @version 1.0  2018-12-22
 * @description 不动产单元类型变更vo
 */
public class BdcdyFwlxBgVO {
    private String fwDcbIndex;
    private String fwlx;
    private String yfwlx;
    private Boolean check;

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public String getYfwlx() {
        return yfwlx;
    }

    public void setYfwlx(String yfwlx) {
        this.yfwlx = yfwlx;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
