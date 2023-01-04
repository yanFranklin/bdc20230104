package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.building.ZdQlrDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/04
 * @Description: 土地楼盘表相关服务
 */
public interface TdlpbRestService {

    /**
     * @param djh 地籍号
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description
     */
    @GetMapping("/building/rest/v1.0/tdlpb/listTdlpbxx")
    List<Map<String,Object>> listTdlpbxx(@RequestParam(value = "djh", required = false) String djh);

}
