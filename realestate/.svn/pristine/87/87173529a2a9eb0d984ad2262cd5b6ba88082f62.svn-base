package cn.gtmap.realestate.common.core.service.rest.inquiry.changzhou;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 电子签章
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午3:55 2021/1/12
 */
public interface BdcZzqzCzRestService {
    /**
     * 创建电子证照
     *
     * @param xmids
     * @return 创建个数
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zzqzcz/createDzzz")
    Integer createDzzz(@RequestBody List<String> xmids);

    /**
     * 注销电子证照
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zzqzcz/cancelDzzz")
    Integer cancelDzzz(@RequestBody List<String> xmids);

    /**
     * 作废电子证照
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zzqzcz/zfDzzz")
    Integer zfDzzz(@RequestBody List<String> xmids);

    /**
     * 判断电子证照是否是历史状态
     *
     * @param zsids 证书ids
     * @return flag
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/zzqzcz/queryDzzzZt")
    Integer sfYzx(@RequestBody List<String> zsids);
}
