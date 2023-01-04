package cn.gtmap.realestate.common.core.qo.register;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Sort;

import java.util.List;


/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/28
 * @description 用于登记簿权利查询的QO
 */
public class BdcDjbqlQO {

    @ApiModelProperty(value = "权利ID", dataType = "String")
    String qlid;
    @ApiModelProperty(value = "权利类型", dataType = "String")
    String qllx;
    @ApiModelProperty(value = "不动产单元号", dataType = "String")
    String bdcdyh;
    @ApiModelProperty(value = "宗地宗海号", dataType = "String")
    String zdzhh;
    @ApiModelProperty(value = "项目ID", dataType = "String")
    String xmid;
    @ApiModelProperty(value = "工作流实例ID", dataType = "String")
    String gzlslid;
    @ApiModelProperty(value = "坐落", dataType = "String")
    String zl;
    @ApiModelProperty(value = "是否原权利", dataType = "Boolean")
    Boolean sfyql;
    @ApiModelProperty(value = "权属状态", dataType = "List")
    List<Integer> qsztList;
    @ApiModelProperty(value = "页码", dataType = "int")
    int page;
    @ApiModelProperty(value = "每页数量", dataType = "int")
    int size;
    @ApiModelProperty(value = "排序", dataType = "Sort")
    Sort sort;
    @ApiModelProperty(value = "是否查询注销项目", dataType = "Boolean")
    Boolean sfcxzx;

    public List<Integer> getQsztList() {
        return qsztList;
    }

    public void setQsztList(List<Integer> qsztList) {
        this.qsztList = qsztList;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public String getZdzhh() {
        return zdzhh;
    }

    public void setZdzhh(String zdzhh) {
        this.zdzhh = zdzhh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Boolean getSfyql() {
        return sfyql;
    }

    public void setSfyql(Boolean sfyql) {
        this.sfyql = sfyql;
    }

    public Boolean getSfcxzx() {
        return sfcxzx;
    }

    public void setSfcxzx(Boolean sfcxzx) {
        this.sfcxzx = sfcxzx;
    }

    @Override
    public String toString() {
        return "BdcDjbqlQO{" +
                "qlid='" + qlid + '\'' +
                ", qllx='" + qllx + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zdzhh='" + zdzhh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", zl='" + zl + '\'' +
                ", sfyql=" + sfyql +
                ", qsztList=" + qsztList +
                ", page=" + page +
                ", size=" + size +
                ", sort=" + sort +
                ", sfcxzx=" + sfcxzx +
                '}';
    }

}
