package cn.gtmap.realestate.common.core.dto.inquiry.shucheng;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/07/05
 * @description 舒城-共享业务系统获取附件接口-附件信息
 */
@Api(value = "BdcGxywFjDTO", description = "舒城-共享业务系统获取附件接口-附件信息")
public class BdcGxywFjDTO {

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件查看路径")
    private String fileReadPath;

    @ApiModelProperty(value = "文件下载路径")
    private String fileDownloadPath;

    @ApiModelProperty(value = "文件上传时间")
    private String fileUploadTime;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileReadPath() {
        return fileReadPath;
    }

    public void setFileReadPath(String fileReadPath) {
        this.fileReadPath = fileReadPath;
    }

    public String getFileDownloadPath() {
        return fileDownloadPath;
    }

    public void setFileDownloadPath(String fileDownloadPath) {
        this.fileDownloadPath = fileDownloadPath;
    }

    public String getFileUploadTime() {
        return fileUploadTime;
    }

    public void setFileUploadTime(String fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }

    @Override
    public String toString() {
        return "BdcGxywFjDTO{" +
                "fileName='" + fileName + '\'' +
                ", fileReadPath='" + fileReadPath + '\'' +
                ", fileDownloadPath='" + fileDownloadPath + '\'' +
                ", fileUploadTime='" + fileUploadTime + '\'' +
                '}';
    }
}
