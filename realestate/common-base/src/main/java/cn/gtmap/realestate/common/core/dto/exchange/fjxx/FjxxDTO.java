package cn.gtmap.realestate.common.core.dto.exchange.fjxx;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/4/7 13:45
 * @description
 */
public class FjxxDTO {
    // 文件名称
    private String fileName;

    // 文件查看路径
    private String fileReadPath;

    // 文件下载路径
    private String fileDownloadPath;

    // 文件上传时间
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
}
