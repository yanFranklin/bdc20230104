package cn.gtmap.realestate.common.core.domain.exchange.yzw;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/10/16
 * @description 申请材料附件信息表
 */

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Date;

@Table(name = "inf_apply_material_files")
public class InfApplyMaterialFilesDO {
    @Id
    @ApiModelProperty(value = "唯一标识")
    private String no;
    @ApiModelProperty(value = "全省唯一办件编号")
    private String internalNo;
    @ApiModelProperty(value = "材料唯一标识")
    private String matecode;
    @ApiModelProperty(value = "部门编码")
    private String orgId;
    @ApiModelProperty(value = "附件名称")
    private String fileName;
    @ApiModelProperty(value = "文件内容")
    private byte[] fileContent;
    @ApiModelProperty(value = "存储文件路径")
    private String filePath;
    @ApiModelProperty(value = "信息同步时间")
    private Date syncDate;
    @ApiModelProperty(value = "信息同步标志")
    private String syncSign;
    @ApiModelProperty(value = "信息同步错误原因")
    private String syncErrorDesc;
    @ApiModelProperty(value = "数据来源")
    private String dataSources;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getInternalNo() {
        return internalNo;
    }

    public void setInternalNo(String internalNo) {
        this.internalNo = internalNo;
    }

    public String getMatecode() {
        return matecode;
    }

    public void setMatecode(String matecode) {
        this.matecode = matecode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public String getSyncSign() {
        return syncSign;
    }

    public void setSyncSign(String syncSign) {
        this.syncSign = syncSign;
    }

    public String getSyncErrorDesc() {
        return syncErrorDesc;
    }

    public void setSyncErrorDesc(String syncErrorDesc) {
        this.syncErrorDesc = syncErrorDesc;
    }

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }

    @Override
    public String toString() {
        return "InfApplyMaterialFilesDO{" +
                "no='" + no + '\'' +
                ", internalNo='" + internalNo + '\'' +
                ", matecode='" + matecode + '\'' +
                ", orgId='" + orgId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileContent=" + Arrays.toString(fileContent) +
                ", filePath='" + filePath + '\'' +
                ", syncDate=" + syncDate +
                ", syncSign='" + syncSign + '\'' +
                ", syncErrorDesc='" + syncErrorDesc + '\'' +
                ", dataSources='" + dataSources + '\'' +
                '}';
    }
}
