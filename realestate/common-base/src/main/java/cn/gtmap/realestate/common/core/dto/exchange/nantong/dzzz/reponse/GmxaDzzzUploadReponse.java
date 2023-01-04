package cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 文件上传返回
 * @author wangyinghao
 */
public class GmxaDzzzUploadReponse extends GmxaDzzzReponse{
    /**
     * 数据体
     */
    @ApiModelProperty
    List<FileItem> data;

    /**
     *数据体项
     */
    public static class FileItem{
        /**
         * 文件路径
         */
        @ApiModelProperty("文件路径")
        String layoutPath;

        public String getLayoutPath() {
            return layoutPath;
        }

        public void setLayoutPath(String layoutPath) {
            this.layoutPath = layoutPath;
        }
    }

    public List<FileItem> getData() {
        return data;
    }

    public void setData(List<FileItem> data) {
        this.data = data;
    }
}
