package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang.com">zedq</a>
 * @version 1.0
 * @date 2021/04/26 14:24
 */
public interface BdcShijiRestService {

    /**
     * 查询统计市级接口台账
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @GetMapping(value = "/realestate-exchange/rest/v1.0/yancheng/shiji/list/query/log")
    public Object listQueryLog(@LayuiPageable Pageable pageable,@RequestParam(value = "kssj",required = false) String kssj,@RequestParam(value = "jssj",required = false) String jssj,@RequestParam(value = "bmmc",required = false) String bmmc,@RequestParam(value = "bjry",required = false) String bjry,@RequestParam(value = "jkmc",required = false) String jkmc) throws Exception;

    /**
     * 保存市级接口日志信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @GetMapping(value = "/realestate-exchange/rest/v1.0/yancheng/shiji/save/log")
    void insertShijiInterfaceLog(@RequestParam(value = "interfaceName",required = false) String interfaceName, @RequestParam(value = "creater",required = false) String creater, @RequestParam(value = "department",required = false) String department, @RequestParam(value = "request",required = false) String request, @RequestParam(value = "response",required = false) String response, @RequestParam(value = "url",required = false) String url);

}
