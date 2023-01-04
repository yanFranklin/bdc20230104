package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-29
 * @description 房屋类型的不动产单元响应实体
 */
@ApiModel(value = "FwBdcdyDTO", description = "房屋类型的不动产单元响应实体")
public class FwBdcdyDTO {

    /**
     * 数据来源表名
     */
    private String tablename;

    /**
     * 数据主键
     */
    private String fwIndex;

    /**
     * 不动产单元号
     */
    private String bdcdyh;

    /**
     * 房屋编码
     */
    private String fwbm;

    public String getFwIndex() {
        return fwIndex;
    }

    public void setFwIndex(String fwIndex) {
        this.fwIndex = fwIndex;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FwBdcdyDTO{");
        sb.append("tablename='").append(tablename).append('\'');
        sb.append(", fwIndex='").append(fwIndex).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", fwbm='").append(fwbm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
