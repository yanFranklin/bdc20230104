package cn.gtmap.realestate.common.core.dto.etl;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 外网预约导出excelDTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-09-01 17:33
 **/
public class WwyyExcelDTO {

    @ApiModelProperty("文件名")
    private String fileName;

    @ApiModelProperty("列名")
    private String column;

    @ApiModelProperty("数据")
    private String data;

    @ApiModelProperty("查询条件")
    private String cxtj;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCxtj() {
        return cxtj;
    }

    public void setCxtj(String cxtj) {
        this.cxtj = cxtj;
    }

    @Override
    public String toString() {
        return "WwyyExcelDTO{" +
                "fileName='" + fileName + '\'' +
                ", column='" + column + '\'' +
                ", data='" + data + '\'' +
                ", cxtj='" + cxtj + '\'' +
                '}';
    }
}
