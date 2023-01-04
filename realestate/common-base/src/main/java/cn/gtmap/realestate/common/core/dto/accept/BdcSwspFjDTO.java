package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/1/19
 * @description
 */
@ApiModel(value = "BdcSwspFjDTO", description = "税务税票附件")
public class BdcSwspFjDTO {

    @ApiModelProperty(value = "票证号码")
    private String pzhm;

    @ApiModelProperty(value = "文件base64")
    private String file;

    @ApiModelProperty(value = "文件base64文件头")
    private String base64qz;

    public String getPzhm() {
        return pzhm;
    }

    public void setPzhm(String pzhm) {
        this.pzhm = pzhm;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getBase64qz() {
        return base64qz;
    }

    public void setBase64qz(String base64qz) {
        this.base64qz = base64qz;
    }
}
