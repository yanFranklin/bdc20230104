package cn.gtmap.realestate.common.core.service.feign.natural;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyDrExcelCxRestService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/7/2 15:58
 * @description 导入excel查询
 */
@FeignClient(name = "${app.services.natural-app:natural-app}")
public interface ZrzyDrExcelCxFeignService extends ZrzyDrExcelCxRestService {

}
