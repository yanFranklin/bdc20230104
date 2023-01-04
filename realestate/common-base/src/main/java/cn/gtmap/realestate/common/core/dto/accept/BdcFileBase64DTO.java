package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/4/19
 * @description 文件Base64信息
 */
public class BdcFileBase64DTO {

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    @ApiModelProperty(value = "文件base64字符串")
    private String base64;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
