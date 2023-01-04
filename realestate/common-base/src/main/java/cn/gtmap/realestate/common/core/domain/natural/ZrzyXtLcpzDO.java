package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/26
 * @description 自然资源流程配置
 */
@Table(name = "ZRZY_XT_LCPZ")
@ApiModel(value = "ZrzyXtLcpzDO",description = "自然资源流程配置")
public class ZrzyXtLcpzDO {

    @Id
    @ApiModelProperty(value="工作流定义ID")
    private String gzldyid;

    @ApiModelProperty(value="选择台账类型")
    private String xztzlx;

    @ApiModelProperty(value="选择登记单元类型")
    private String xzdjdylx;

    @ApiModelProperty(value="登记类型")
    private Integer djlx;

    @ApiModelProperty(value="注销原权利")
    private Integer zxyql;

    @ApiModelProperty(value="是否生成权利")
    private Integer sfscql;

    @ApiModelProperty(value="是否生成证书")
    private Integer sfsczs;

    @ApiModelProperty(value="数据来源")
    private String sjly;

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getXztzlx() {
        return xztzlx;
    }

    public void setXztzlx(String xztzlx) {
        this.xztzlx = xztzlx;
    }

    public String getXzdjdylx() {
        return xzdjdylx;
    }

    public void setXzdjdylx(String xzdjdylx) {
        this.xzdjdylx = xzdjdylx;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public Integer getZxyql() {
        return zxyql;
    }

    public void setZxyql(Integer zxyql) {
        this.zxyql = zxyql;
    }

    public Integer getSfscql() {
        return sfscql;
    }

    public void setSfscql(Integer sfscql) {
        this.sfscql = sfscql;
    }

    public Integer getSfsczs() {
        return sfsczs;
    }

    public void setSfsczs(Integer sfsczs) {
        this.sfsczs = sfsczs;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }
}
