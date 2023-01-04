package cn.gtmap.realestate.common.core.dto.analysis;

import org.springframework.http.ResponseEntity;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/1/16
 * @description 文件下载
 */
public class FileDownloadDTO {

    private String fileName;

    private ResponseEntity entity;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ResponseEntity getEntity() {
        return entity;
    }

    public void setEntity(ResponseEntity entity) {
        this.entity = entity;
    }
}
