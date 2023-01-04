package cn.gtmap.realestate.exchange.core.dto.yancheng.yth;

import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlYgdjOldDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/2/23
 * @description 预告节点信息(与上报字段有所差异)
 */
public class YthYgdjDTO extends QlfQlYgdjOldDO {

    @ApiModelProperty(value = "抵押方式:抵押方式字典表")
    private String dyfs;

    @ApiModelProperty(value = "债务履行起始时间")
    private Date zwlxqssj;

    @ApiModelProperty(value = "债务履行结束时间")
    private Date zwlxjssj;

    @ApiModelProperty(value = "担保范围")
    private String dbfw;

    @ApiModelProperty(value = "抵押期间抵押物转让")
    private String dyqjdywzr;

    public String getDyqjdywzr() {
        return dyqjdywzr;
    }

    public void setDyqjdywzr(String dyqjdywzr) {
        this.dyqjdywzr = dyqjdywzr;
    }

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

    public String getDyfs() {
        return dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public Date getZwlxqssj() {
        return zwlxqssj;
    }

    public void setZwlxqssj(Date zwlxqssj) {
        this.zwlxqssj = zwlxqssj;
    }

    public Date getZwlxjssj() {
        return zwlxjssj;
    }

    public void setZwlxjssj(Date zwlxjssj) {
        this.zwlxjssj = zwlxjssj;
    }
}
