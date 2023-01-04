package cn.gtmap.realestate.common.core.service.rest.realestate_e_certificate;

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: realestate
 * @description: 常州电子签章restService
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-10 09:50
 **/
public interface BdcDzqzRestService {


    /**
     * @param jsonString
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州电子签章接口
     * @date : 2022/3/10 9:42
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v2.0/zzgl/dzqz/cz")
    DzzzResponseModel czdzqz(@RequestBody String jsonString);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州公告推送电子签章
     * @date : 2022/3/14 15:13
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v2.0/zzgl/dzqz/gg/cz")
    DzzzResponseModel czggdzqz(@RequestBody String jsonString);

    /**
     * @param jsonString 推送的信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送pdf 数据到签章信息
     * @date : 2022/8/24 9:40
     */
    @PostMapping(path = "/realestate-e-certificate/rest/v2.0/zzgl/dzqz/fdjyw")
    DzzzResponseModel pushDzqz(@RequestBody String jsonString);

    /**
     * @param qzbs
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据签章标识获取签章的文件下载地址
     * @date : 2022/3/15 14:29
     */
    @PostMapping("/realestate-e-certificate/rest/v1.0/zzgl/gg/qzxz")
    Object queryQzfj(@RequestParam(value = "qzbs") String qzbs);

    /**
     * @param zzid
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据签章标识获取签章的文件下载地址
     * @date : 2022/7/8 14:29
     */
    @PostMapping("/realestate-e-certificate/rest/v1.0/zzgl/dzqz/getqzxx")
    String getQzxx(@RequestParam(value = "zzid") String zzid);
}
