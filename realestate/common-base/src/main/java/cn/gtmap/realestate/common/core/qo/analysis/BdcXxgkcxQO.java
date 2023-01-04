package cn.gtmap.realestate.common.core.qo.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/14
 * @description
 */
@ApiModel(value = "BdcXxgkxcQO", description = "信息公开查询QO")
public class BdcXxgkcxQO {

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("权利人证件号")
    private String zjh;

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty("不动产权证号")
    private String bdcqzh;

    @ApiModelProperty("信息公开查询清单样式明细 0：概要明细  1：详细明细")
    private String qdys;

    @ApiModelProperty("信息公开查询清单样式是否有房 0：有房  1：全部")
    private String sfyf;

    @ApiModelProperty("是否导出清单  0：不导出  1：导出")
    private String sfdc;

    @ApiModelProperty(value = "导入查询文件名称")
    private String fileName;

    @ApiModelProperty(value = "查询方式 sureZjh likeZjh")
    private String cxfs;

    @ApiModelProperty("是否导入查询")
    private boolean isImportQuery;

    @ApiModelProperty("是否查询历史")
    private boolean isQueryLs;

    @ApiModelProperty("是否精确查询")
    private boolean isQuerySure;

    @ApiModelProperty("是否查询预告信息")
    private boolean isQueryYg;

    @ApiModelProperty("是否查询预查封信息")
    private boolean isQueryYcf;

    @ApiModelProperty("是否查询限制状态信息")
    private boolean queryXzzzt;

    public boolean isQueryXzzzt() {
        return queryXzzzt;
    }

    public void setQueryXzzzt(boolean queryXzzzt) {
        this.queryXzzzt = queryXzzzt;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
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

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public boolean isImportQuery() {
        return isImportQuery;
    }

    public void setImportQuery(boolean importQuery) {
        isImportQuery = importQuery;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isQueryLs() {
        return isQueryLs;
    }

    public void setQueryLs(boolean queryLs) {
        isQueryLs = queryLs;
    }

    public boolean isQuerySure() {
        return isQuerySure;
    }

    public void setQuerySure(boolean querySure) {
        isQuerySure = querySure;
    }

    public boolean isQueryYg() {
        return isQueryYg;
    }

    public void setQueryYg(boolean queryYg) {
        isQueryYg = queryYg;
    }

    public boolean isQueryYcf() {
        return isQueryYcf;
    }

    public void setQueryYcf(boolean queryYcf) {
        isQueryYcf = queryYcf;
    }

    public String getQdys() {
        return qdys;
    }

    public void setQdys(String qdys) {
        this.qdys = qdys;
    }

    public String getSfyf() {
        return sfyf;
    }

    public void setSfyf(String sfyf) {
        this.sfyf = sfyf;
    }

    public String getSfdc() {
        return sfdc;
    }

    public void setSfdc(String sfdc) {
        this.sfdc = sfdc;
    }

    public String getCxfs() {
        return cxfs;
    }

    public void setCxfs(String cxfs) {
        this.cxfs = cxfs;
    }

    public BdcXxgkcxQO() {

    }

    public BdcXxgkcxQO(String qdys, String sfyf, String fileName, String cxfs, boolean isImportQuery) {
        this.qdys = qdys;
        this.sfyf = sfyf;
        this.fileName = fileName;
        this.cxfs = cxfs;
        this.isImportQuery = isImportQuery;
    }

}
