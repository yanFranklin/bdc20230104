package cn.gtmap.realestate.exchange.core.dto.nantong.sq.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @date 2022/4/27
 * @description
 */
public abstract class AbstactFileInfo {

    @ApiModelProperty(value = "资料名称+格式")
    public String fileName;
    @ApiModelProperty(value = "资料")
    public String fileData;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    @Override
    public String toString() {
        return "AbstactFileInfo{" +
                "fileName='" + fileName + '\'' +
                ", fileData='" + fileData + '\'' +
                '}';
    }
}
