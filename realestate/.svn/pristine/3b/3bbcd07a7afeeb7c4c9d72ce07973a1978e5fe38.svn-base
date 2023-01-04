package cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.reponse;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wangyinghao
 */
public class GmxaDzzzPositionSignReponse extends GmxaDzzzReponse
{
    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    List<FileItem> data;

    public static class FileItem {
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
