package cn.gtmap.realestate.common.core.qo.inquiry.count;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: <a href="@mailto:wutao@163.com">wutao</a>
 * @version: V1.0, 2022.11.21
 * @description: 林权查询统计
 */
public class BdcLqtjQO {

    @ApiModelProperty("查询起始日期")
    private String kssj;

    @ApiModelProperty("查询截止日期")
    private String jzsj;

    @ApiModelProperty("区县代码")
    private List<String> qxdmList;

    @ApiModelProperty("登记小类集合")
    private List<String> djxlList;

    @ApiModelProperty("登记小类")
    private String djxl;

    @ApiModelProperty("林地种类")
    private String ldzl;

    @ApiModelProperty("登记类型")
    private String djlx;

    public String getLdzl() {return ldzl;}

    public void setLdzl(String ldzl) {this.ldzl = ldzl;}

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJzsj() {
        return jzsj;
    }

    public void setJzsj(String jzsj) {
        this.jzsj = jzsj;
    }

    public List<String> getDjxlList() {return djxlList;}

    public void setDjxlList(List<String> djxlList) {this.djxlList = djxlList;}

    public List<String> getQxdmList() {return qxdmList;}

    public void setQxdmList(List<String> qxdmList) {this.qxdmList = qxdmList;}

    public String getDjxl() {return djxl;}

    public void setDjxl(String djxl) {this.djxl = djxl;}

    public String getDjlx() {return djlx;}

    public void setDjlx(String djlx) {this.djlx = djlx;}

    @Override
    public String toString() {
        return "BdcLqtjQO{" +
                "kssj='" + kssj + '\'' +
                ", jzsj='" + jzsj + '\'' +
                ", qxdmList='" + qxdmList + '\'' +
                ", djxlList='" + djxlList + '\'' +
                ", ldzl='" + ldzl + '\'' +
                ", djlx='" + djlx + '\'' +
                '}';
    }
}