package cn.gtmap.realestate.common.core.qo.config;

import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/4/3
 * @description 用于封装pdf打印参数
 */
public class BdcPdfDyQO extends BdcPrintDTO {
    @ApiModelProperty(value = "pdf模板请求路径")
    private String pdfpath;
    @ApiModelProperty(value = "生成的pdf文件名")
    private String fileName;
    @ApiModelProperty(value = "xml生成地址")
    private String dataUrl;
    @ApiModelProperty(value = "微服务应用名称/ IP：端口类型")
    private String appName;

    public String getPdfpath() {
        return pdfpath;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "BdcPdfDyQO{" +
                "pdfpath='" + pdfpath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", dataUrl='" + dataUrl + '\'' +
                ", appName='" + appName + '\'' +
                '}';
    }
}
