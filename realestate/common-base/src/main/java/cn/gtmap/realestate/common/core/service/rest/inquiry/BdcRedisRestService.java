package cn.gtmap.realestate.common.core.service.rest.inquiry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-11
 * @description
 */
public interface BdcRedisRestService {
    /**
     * @param key value
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增字符串值
     */
    @PutMapping("/realestate-inquiry/rest/v1.0/redis/string/timeout")
    String addStringValue(@RequestParam(name = "key") String key,@RequestParam(name = "value") String value, @RequestParam(name = "timeout") String timeout);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取字符串值
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/redis/string")
    String getStringValue(@RequestParam(name = "key") String key);

    /**
     * @param key value
     * @return
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 新增字符串值
     */
    @PutMapping("/realestate-inquiry/rest/v1.0/redis/string")
    String addStringValue(@RequestParam(name = "key") String key,@RequestParam(name = "value") String value);

    /**
     * 新增Hash类型值
     * @param key 键值
     * @return
     */
    @PutMapping("/realestate-inquiry/rest/v1.0/redis/hash")
    String addHashValue(@RequestParam(name = "key") String key,
                        @RequestParam(name = "hashKey") String hashKey,
                        @RequestParam(name = "value") String value,
                        @RequestParam(name = "timeout") Long timeout);
}
