package cn.gtmap.realestate.common.core.dto;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/10/23
 * @description  Pdf导出信息实体定义
 */
public class OfficeExportDTO {
    /**
     * PDF模板名称（模板和FR3打印模板放置一个位置）
     */
    private String modelName;

    /**
     * 对应的FR3打印数据（PDF打印不单独做数据源获取配置，直接和FR3打印共用，只是最终用于不同的用途）
     */
    private String xmlData;

    /**
     * 导出的文件名
     */
    private String fileName;

    /**
     * 本地缓存文件名称
     */
    private String localFile;


    public String getLocalFile() {
        return localFile;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getXmlData() {
        return xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "PdfExportDTO{" +
                "modelName='" + modelName + '\'' +
                ", xmlData='" + xmlData + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
