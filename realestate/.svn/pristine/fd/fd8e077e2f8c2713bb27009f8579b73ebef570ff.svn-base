package cn.gtmap.realestate.building.ui.util;

import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-25
 * @description 文档中心服务工具类
 */
public class StorageImgUtils {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param userName
     * @param file
     * @return cn.gtmap.gtc.storage.domain.dto.MultipartDto
     * @description 构造上传参数
     */
    public static MultipartDto getUploadParamDto(String userName,MultipartFile file) throws IOException {
        MultipartDto multipartDto = new MultipartDto();
        if(file != null){
            multipartDto.setData(file.getBytes());
            multipartDto.setOwner(userName);
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            multipartDto.setOriginalFilename(file.getOriginalFilename());
            multipartDto.setName(file.getName());
        }
        return multipartDto;
    }
}
