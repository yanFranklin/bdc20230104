package cn.gtmap.realestate.exchange.core.qo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * 外网共享申请信息查询对象
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/3/22 15:03
 */
@Api(value = "GxWwSqxxQO", description = "外网共享申请信息查询")
public class GxWwSqxxQO {

    @ApiModelProperty("申请类型")
    private String sqlx;

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("外网受理编号")
    private String sqslbh;

    @ApiModelProperty("不动产登记受理编号")
    private String bdcdjslbh;

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("坐落")
    private String zl;

    @Override
    public String toString() {
        return "GxWwSqxxQO{" +
                "sqlx='" + sqlx + '\'' +
                ", qlr='" + qlr + '\'' +
                ", sqslbh='" + sqslbh + '\'' +
                ", bdcdjslbh='" + bdcdjslbh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                '}';
    }

    public String getSqlx() {
        return sqlx;
    }

    public void setSqlx(String sqlx) {
        this.sqlx = sqlx;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getSqslbh() {
        return sqslbh;
    }

    public void setSqslbh(String sqslbh) {
        this.sqslbh = sqslbh;
    }

    public String getBdcdjslbh() {
        return bdcdjslbh;
    }

    public void setBdcdjslbh(String bdcdjslbh) {
        this.bdcdjslbh = bdcdjslbh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }
}
