package cn.gtmap.realestate.common.core.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/4/15
 * @description 用于上传构建PDF文件实体
 */
public class BdcPdfDTO {

    @ApiModelProperty("是否组合收件")
    private Boolean zhsjcl;

    @ApiModelProperty("工作流实例ID")
    private String gzlslid;

    @ApiModelProperty("受理编号")
    private String slbh;

    @ApiModelProperty("组合收件时对应目录的xmid,必传")
    private String xmid;

    @ApiModelProperty("组合收件时对应目录的djxl")
    private String djxl;

    @ApiModelProperty("打印类型")
    private String dylx;

    @ApiModelProperty("生成的PDF文件路径")
    private String pdfFilePath;

    @ApiModelProperty("PDF文件下载url")
    private String pdfUrl;

    @ApiModelProperty("PDF附件名称")
    private String pdffjmc;

    @ApiModelProperty("文件目录名称")
    private String foldName;

    @ApiModelProperty("文件后缀名")
    private String fileSuffix;

    @ApiModelProperty(value = "文件base64字符串")
    private String base64str;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdffjmc() {
        return pdffjmc;
    }

    public void setPdffjmc(String pdffjmc) {
        this.pdffjmc = pdffjmc;
    }

    public String getFoldName() {
        return foldName;
    }

    public void setFoldName(String foldName) {
        this.foldName = foldName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getBase64str() {
        return base64str;
    }

    public void setBase64str(String base64str) {
        this.base64str = base64str;
    }

    public Boolean getZhsjcl() {
        return zhsjcl;
    }

    public void setZhsjcl(Boolean zhsjcl) {
        this.zhsjcl = zhsjcl;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public BdcPdfDTO() {
    }

    @Override
    public String toString() {
        return "BdcPdfDTO{" +
                "zhsjcl=" + zhsjcl +
                ", gzlslid='" + gzlslid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", djxl='" + djxl + '\'' +
                ", dylx='" + dylx + '\'' +
                ", pdfFilePath='" + pdfFilePath + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", pdffjmc='" + pdffjmc + '\'' +
                ", foldName='" + foldName + '\'' +
                ", fileSuffix='" + fileSuffix + '\'' +
                ", base64str='" + base64str + '\'' +
                '}';
    }

    public BdcPdfDTO(String gzlslid, String dylx, String pdffjmc, String foldName, String fileSuffix) {
        this.gzlslid = gzlslid;
        this.dylx = dylx;
        this.pdffjmc = pdffjmc;
        this.foldName = foldName;
        this.fileSuffix = fileSuffix;
    }
}
