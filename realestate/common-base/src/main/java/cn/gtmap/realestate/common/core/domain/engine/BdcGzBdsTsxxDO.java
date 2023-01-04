package cn.gtmap.realestate.common.core.domain.engine;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/21
 * @description  不动产规则子系统 子规则对应的表达式、提示信息实体定义
 */
@Table(name = "BDC_GZ_BDS_TSXX")
@ApiModel(value = "BdcGzBdsTsxxDO", description = "规则表达式、提示信息")
public class BdcGzBdsTsxxDO {
    @Id
    @ApiModelProperty(value = "表达式ID")
    private String bdsid;

    @ApiModelProperty(value = "关联的子规则ID")
    private String gzid;

    @ApiModelProperty(value = "规则表达式")
    private String gzbds;

    @ApiModelProperty(value = "提示信息")
    private String tsxx;


    public String getBdsid() {
        return bdsid;
    }

    public void setBdsid(String bdsid) {
        this.bdsid = bdsid;
    }

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    public String getGzbds() {
        return gzbds;
    }

    public void setGzbds(String gzbds) {
        this.gzbds = gzbds;
    }

    public String getTsxx() {
        return tsxx;
    }

    public void setTsxx(String tsxx) {
        this.tsxx = tsxx;
    }

    @Override
    public String toString() {
        return "BdcGzBdsTsxxDO{" +
                "bdsid='" + bdsid + '\'' +
                ", gzid='" + gzid + '\'' +
                ", gzbds='" + gzbds + '\'' +
                ", tsxx='" + tsxx + '\'' +
                '}';
    }
}
