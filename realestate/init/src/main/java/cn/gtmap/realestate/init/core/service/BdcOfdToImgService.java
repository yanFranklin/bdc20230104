package cn.gtmap.realestate.init.core.service;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @description ofd转图片服务
 * @date : 2022/8/24 17:54
 */
public interface BdcOfdToImgService {

    /**
     *@author <a href="mailto:wutao@gtmap.cn">wutao</a>
     *@param storageid
     *@return String
     *@description ofd转图片
     */
    String ofdtoimg(String storageid);
}
