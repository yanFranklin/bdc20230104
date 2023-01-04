package cn.gtmap.realestate.common.core.dto.certificate.yjd;

import io.swagger.annotations.ApiModelProperty;

public class BdcYjdXmFilesDTO {

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "相关文件信息")
    private  String fileListJsonString;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getFileListJsonString() {
        return fileListJsonString;
    }

    public void setFileListJsonString(String fileListJsonString) {
        this.fileListJsonString = fileListJsonString;
    }
}
