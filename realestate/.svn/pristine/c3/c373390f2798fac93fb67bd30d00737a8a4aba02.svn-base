package cn.gtmap.realestate.common.core.domain.building;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/5.
 * @description 承包地块与承包方关系表
 */
@Table(name = "cbzd_dcbcbf_rel")
public class CbzdDcbcbfRelDO {
    @Id
    private String cbzdDcbcbfrelIndex;
    /**
     * 地籍号
     */
    private String djh;
    /**
     * 不动产单元号
     */
    private String bdcdyh;
    /**
     * 承包方外键
     */
    private String cbzdCbfIndex;

    /**
     * 实测面积亩
     */
    private String scmjm;

    /**
     * 确权合同面积亩
     */
    private String htmjm;

    public String getCbzdDcbcbfrelIndex() {
        return cbzdDcbcbfrelIndex;
    }

    public void setCbzdDcbcbfrelIndex(String cbzdDcbcbfrelIndex) {
        this.cbzdDcbcbfrelIndex = cbzdDcbcbfrelIndex;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getCbzdCbfIndex() {
        return cbzdCbfIndex;
    }

    public void setCbzdCbfIndex(String cbzdCbfIndex) {
        this.cbzdCbfIndex = cbzdCbfIndex;
    }

    public String getScmjm() {
        return scmjm;
    }

    public void setScmjm(String scmjm) {
        this.scmjm = scmjm;
    }

    public String getHtmjm() {
        return htmjm;
    }

    public void setHtmjm(String htmjm) {
        this.htmjm = htmjm;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CbzdDcbcbfRelDO{");
        sb.append("cbzdDcbcbfrelIndex='").append(cbzdDcbcbfrelIndex).append('\'');
        sb.append(", djh='").append(djh).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", cbzdCbfIndex='").append(cbzdCbfIndex).append('\'');
        sb.append(", scmjm='").append(scmjm).append('\'');
        sb.append(", htmjm='").append(htmjm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
