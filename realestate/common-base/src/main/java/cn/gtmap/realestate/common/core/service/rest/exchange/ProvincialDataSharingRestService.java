package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.provincialdatasharing.zw.samr.enterprisecheck.ZwSamrEnterpriseCheckRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.provincialdatasharing.zw.samr.enterprisecheck.ZwSamrEnterpriseCheckResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/4 11:13
 * @description 省级数据共享交换平台接口
 */
public interface ProvincialDataSharingRestService {

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param info
     * @return ZwSamrEnterpriseCheckResponseDTO
     * @description 3.9市场监管总局-企业基本信息验证接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/provincialDataSharing/zw/samr/enterpriseCheck")
    ZwSamrEnterpriseCheckResponseDTO enterpriseCheck(@RequestBody ZwSamrEnterpriseCheckRequestDTO info);
}
