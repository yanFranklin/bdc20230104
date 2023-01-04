package cn.gtmap.realestate.common.core.qo.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Auther: xiejianan
 * @Date: 2018/12/18 18:33
 * @Description:
 */
@ApiModel(value = "BdcZfcxQO", description = "住房开查询QO")
public class BdcZfcxQO extends BdcKkcxListQO {

    @ApiModelProperty("权利人姓名,家庭查询时多个以 ',' 拼接")
    private List<String> qlrList;

    @ApiModelProperty("权利人证件号,家庭查询时多个以 ',' 拼接且个数与姓名相等")
    private List<String> zjhList;

    @ApiModelProperty("家庭查询关系")
    private List<String> qlrjtcygxList;

    @ApiModelProperty("查询方式")
    private String cxfs;

    @ApiModelProperty("房屋用途")
    private String fwyt;

    @ApiModelProperty("查询地区")
    private String area;

    @ApiModelProperty("查询类型")
    private String queryType;

    @ApiModelProperty("导入查询文件名")
    private String fileName;

    @ApiModelProperty("项目id")
    private List<String> xmidList;

    @ApiModelProperty("是否查询限制状态")
    private boolean queryXzzt;

    @ApiModelProperty("登记起始时间")
    private String djqssj;

    @ApiModelProperty("登记结束时间")
    private String djjssj;

    public List<String> getQlrList() {
        return qlrList;
    }

    public void setQlrList(List<String> qlrList) {
        this.qlrList = qlrList;
    }

    public List<String> getZjhList() {
        return zjhList;
    }

    public void setZjhList(List<String> zjhList) {
        this.zjhList = zjhList;
    }

    public String getCxfs() {
        return cxfs;
    }

    public void setCxfs(String cxfs) {
        this.cxfs = cxfs;
    }

    public String getFwyt() {
        return fwyt;
    }

    public void setFwyt(String fwyt) {
        this.fwyt = fwyt;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public List<String> getQlrjtcygxList() {
        return qlrjtcygxList;
    }

    public void setQlrjtcygxList(List<String> qlrjtcygxList) {
        this.qlrjtcygxList = qlrjtcygxList;
    }

    public boolean isQueryXzzt() {
        return queryXzzt;
    }

    public void setQueryXzzt(boolean queryXzzt) {
        this.queryXzzt = queryXzzt;
    }

    public String getDjqssj() {
        return djqssj;
    }

    public void setDjqssj(String djqssj) {
        this.djqssj = djqssj;
    }

    public String getDjjssj() {
        return djjssj;
    }

    public void setDjjssj(String djjssj) {
        this.djjssj = djjssj;
    }
}
