package cn.gtmap.realestate.exchange.core.dto;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2021/03/23
 * @description  Excel（含多个sheet）导出信息实体定义
 */
public class ExcelExportSheetsDTO {
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 多个工作表sheet对应信息定义
     */
    private List<ExcelExportDTO> sheets;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<ExcelExportDTO> getSheets() {
        return sheets;
    }

    public void setSheets(List<ExcelExportDTO> sheets) {
        this.sheets = sheets;
    }

    @Override
    public String toString() {
        return "ExcelExportSheetsDTO{" +
                "fileName='" + fileName + '\'' +
                ", sheets=" + sheets +
                '}';
    }
}
