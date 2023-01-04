package cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.request;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传
 * @author wangyinghao
 */
public class GmxaDzzzUploadReq {

    /**
     * 业务系统id
     */
    @ApiModelProperty("业务系统id")
    String siteId;

    /**
     * 用户唯一标识
     */
    @ApiModelProperty("用户唯一标识")
    String userId;

    /**
     * 文件类型（可传文件pdf、png）1-pdf  2-ofd  3-png 4-jpg
     */
    @ApiModelProperty("文件类型（可传文件pdf、png）1-pdf  2-ofd  3-png 4-jpg")
    String fileType;

    /**
     *
     */
    @ApiModelProperty("待签章文件。将文件读取为byte数组后使用BASE64编码为String")
    List<Map<String,String>> fileDatas;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<Map<String, String>> getFileDatas() {
        return fileDatas;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public void setFileDatas(Map<String, String> fileDatas) {
        if(CollectionUtils.isEmpty(this.fileDatas)) {
            this.fileDatas = new ArrayList<>();
        }

        //每一个文件作为一个Map
        for (Map.Entry<String, String> stringStringEntry : fileDatas.entrySet()) {
            HashMap<String,String> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put(stringStringEntry.getKey(),stringStringEntry.getValue());
            this.fileDatas.add(objectObjectHashMap);
        }
    }
}
