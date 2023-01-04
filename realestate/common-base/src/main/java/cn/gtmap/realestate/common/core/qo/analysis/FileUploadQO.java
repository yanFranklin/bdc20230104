package cn.gtmap.realestate.common.core.qo.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/14
 * @description
 */
@ApiModel(value = "FileUploadQO", description = "文件上传")
public class FileUploadQO {

    @ApiModelProperty("上传文件")
    private MultipartFile file;

    @ApiModelProperty("台账类型")
    private String type;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
