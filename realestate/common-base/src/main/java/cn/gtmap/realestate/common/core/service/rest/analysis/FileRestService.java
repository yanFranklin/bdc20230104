package cn.gtmap.realestate.common.core.service.rest.analysis;

import cn.gtmap.realestate.common.core.dto.analysis.FileUploadDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/1/16
 * @description 文件上传
 */
public interface FileRestService {

    /**
     * version 1.0
     * @param file 文件
     * @param file 上传文件
     * @param viewType 台账类型
     * @param fileName 上传文件名 保存日志时使用
     * @return
     * @description 上传文件
     * @date 2019/1/17
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/bdc/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileUploadDTO uploadFile(@RequestPart("file") MultipartFile file,
                             @RequestParam(value = "viewType", required = false) String viewType,
                             @RequestParam(value = "fileName", required = false) String fileName);


    /**
     * version 1.0
     * @description 下载文件
     * @param fileName 文件名
     * @param viewType 台账类型 根据台账配置信息获取文件路径
     * @return
     * @date 2019/3/25
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping(value = "/realestate-analysis/rest/v1.0/bdc/file/download")
    ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName,
                                        @RequestParam(value = "viewType", required = false) String viewType);



    /**
     * version 1.0
     * @description 下载文件
     * @param fileName 文件名
     * @param filePath 文件路径
     * @return
     * @date 2019/3/25
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping(value = "/realestate-analysis/rest/v1.0/bdc/file/path/download")
    ResponseEntity<byte[]> downloadFileByPath(@RequestParam("filePath") String filePath, @RequestParam("fileName") String fileName);

}
