package cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.BdcZdTdytDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/6.
 * @description
 */
public class FwqlCxResponseTdxx {
    private String tdzh;
    private String qdfs;
    private String tdsykssj;
    private String tdsyjssj;

    @Zd(tableClass = BdcZdTdytDO.class)
    @ApiModelProperty(value = "宗地宗海用途")
    private String zdzhyt;

    @ApiModelProperty(value = "宗地宗海面积")
    private String zdzhmj;

    public String getZdzhyt() {
        return zdzhyt;
    }

    public void setZdzhyt(String zdzhyt) {
        this.zdzhyt = zdzhyt;
    }

    public String getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(String zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public String getTdzh() {
        return tdzh;
    }

    public void setTdzh(String tdzh) {
        this.tdzh = tdzh;
    }

    public String getQdfs() {
        return qdfs;
    }

    public void setQdfs(String qdfs) {
        this.qdfs = qdfs;
    }

    public String getTdsykssj() {
        return tdsykssj;
    }

    public void setTdsykssj(String tdsykssj) {
        this.tdsykssj = tdsykssj;
    }

    public String getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(String tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
    }
}
