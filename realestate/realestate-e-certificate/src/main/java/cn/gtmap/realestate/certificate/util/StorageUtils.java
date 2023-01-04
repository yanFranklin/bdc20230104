package cn.gtmap.realestate.certificate.util;

import cn.gtmap.realestate.certificate.core.model.storage.MultipartDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @description 文档中心工具类
 */
public class StorageUtils {

    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @param userName
     * @param file
     * @return cn.gtmap.gtc.storage.domain.dto.MultipartDto
     * @description 构造上传参数
     */
    public static MultipartDto getUploadParamDto(String userName, MultipartFile file) throws IOException {
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
