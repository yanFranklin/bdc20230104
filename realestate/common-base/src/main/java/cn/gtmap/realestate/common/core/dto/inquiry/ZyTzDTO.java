package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/2/27
 * @description 台账通用信息
 */
@Api(value = "ZyTzDTO", description = "台账通用信息")
public class ZyTzDTO {

    @ApiModelProperty("方法名")
    private String tzType;

    @ApiModelProperty("所属资源")
    private String tzName;

    @ApiModelProperty("台账地址")
    private String tzAddress;

    @ApiModelProperty("上传文件解析后存储目录")
    private String importPath;

    @ApiModelProperty("待下载文件目录")
    private String exportPath;

    public String getTzType() {
        return tzType;
    }

    public void setTzType(String tzType) {
        this.tzType = tzType;
    }

    public String getTzName() {
        return tzName;
    }

    public void setTzName(String tzName) {
        this.tzName = tzName;
    }

    public String getTzAddress() {
        return tzAddress;
    }

    public void setTzAddress(String tzAddress) {
        this.tzAddress = tzAddress;
    }

    public String getImportPath() {
        return importPath;
    }

    public void setImportPath(String importPath) {
        this.importPath = importPath;
    }

    public String getExportPath() {
        return exportPath;
    }

    public void setExportPath(String exportPath) {
        this.exportPath = exportPath;
    }
}
