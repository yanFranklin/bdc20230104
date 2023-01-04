package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: 3.0
 * @description: 限定人员查询QO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-08-03 10:02
 **/
public class BdcXdryQO {
    @ApiModelProperty(value = "ID主键")
    private String id;
    @ApiModelProperty(value = "姓名")
    private String xm;
    @ApiModelProperty(value = "证件号")
    private String zjh;
    @ApiModelProperty(value = "通讯地址")
    private String txdz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getTxdz() {
        return txdz;
    }

    public void setTxdz(String txdz) {
        this.txdz = txdz;
    }

    @Override
    public String toString() {
        return "BdcXdryQO{" +
                "id='" + id + '\'' +
                ", xm='" + xm + '\'' +
                ", zjh='" + zjh + '\'' +
                ", txdz='" + txdz + '\'' +
                '}';
    }
}
