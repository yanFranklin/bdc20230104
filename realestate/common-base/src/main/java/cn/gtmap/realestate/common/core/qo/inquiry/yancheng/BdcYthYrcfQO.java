package cn.gtmap.realestate.common.core.qo.inquiry.yancheng;

import io.swagger.annotations.ApiModel;

import java.util.Arrays;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/12/8 16:15
 * @description  盐城 以人查房接口查询参数QO定义 用于一体化平台
 */
@ApiModel(value = "BdcYthYrcfQO", description = "以人查房接口查询参数封装对象")
public class BdcYthYrcfQO {
    private String zjh;

    private String qlrmc;

    private String bdcqzh;

    private String bdcdyh;

    private String zl;

    private int pageSize;

    private int pageNumber;

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "BdcYthYrcfQO{" +
                "zjh='" + zjh + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", zl='" + zl + '\'' +
                ", pageNumber='" + pageNumber + '\'' +
                ", pageSize='" + pageSize + '\'' +
                '}';
    }
}
