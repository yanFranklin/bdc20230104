package cn.gtmap.realestate.common.core.qo.inquiry.count;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author: <a href="@mailto:chenyucheng@163.com">chenyucheng</a>
 * @version: V1.0, 2020.07.28
 * @description: 办件时长统计qo对象
 */
public class BjsctjQO {

    @ApiModelProperty("登记机构")
    private String djjg;

    @ApiModelProperty("登记机构集合")
    private List djjglist;

    @ApiModelProperty("流程名称")
    private String processname;

    @ApiModelProperty("流程名称集合")
    private List processnamelist;

    @ApiModelProperty("登记原因")
    private String djyy;

    @ApiModelProperty("登记原因集合")
    private List djyylist;

    @ApiModelProperty("统计维度")
    private String dimension;

    @ApiModelProperty("查询起始日期")
    private String kssj;

    @ApiModelProperty("查询截止日期")
    private String jzsj;


    @ApiModelProperty("查询类型")
    private String type;

    @ApiModelProperty("审批来源")
    private String sply;

    @ApiModelProperty("审批来源集合")
    private List splylist;


    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public List getDjjglist() {
        return djjglist;
    }

    public void setDjjglist(List djjglist) {
        this.djjglist = djjglist;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    public List getProcessnamelist() {
        return processnamelist;
    }

    public void setProcessnamelist(List processnamelist) {
        this.processnamelist = processnamelist;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public List getDjyylist() {
        return djyylist;
    }

    public void setDjyylist(List djyylist) {
        this.djyylist = djyylist;
    }

    public String getSply() {
        return sply;
    }

    public void setSply(String sply) {
        this.sply = sply;
    }

    public List getSplylist() {
        return splylist;
    }

    public void setSplylist(List splylist) {
        this.splylist = splylist;
    }

    @Override
    public String toString() {
        return "BjsctjQO{" +
                "djjg='" + djjg + '\'' +
                ", djjglist=" + djjglist +
                ", processname='" + processname + '\'' +
                ", processnamelist=" + processnamelist +
                ", djyy='" + djyy + '\'' +
                ", djyylist=" + djyylist +
                ", dimension='" + dimension + '\'' +
                ", kssj='" + kssj + '\'' +
                ", jzsj='" + jzsj + '\'' +
                ", type='" + type + '\'' +
                ", sply='" + sply + '\'' +
                ", splylist=" + splylist +
                '}';
    }
}