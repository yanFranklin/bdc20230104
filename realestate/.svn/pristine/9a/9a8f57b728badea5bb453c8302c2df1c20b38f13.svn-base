package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.YhxxTsspxxResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangyinghao
 * @version 1.0  2020-12-14
 * @description 信息推送服务
 */
public interface YhxxTsRestService {

    /**
     * 抵押物二押实时推送抵押信息
     *
     * @param processInsId
     * @param currentUserName
     */
    @PostMapping("/realestate-exchange/rest/v1.0/xxts/dyw/dydj/dyaqxx")
    void tzyyh(@RequestParam(name = "processInsId") String processInsId,
               @RequestParam(value = "currentUserName", required = false) String currentUserName);

    /**
     * 抵押物被查封时推送查封信息
     *
     * @param processInsId
     * @param currentUserName
     */
    @PostMapping("/realestate-exchange/rest/v1.0/xxts/dyw/cfdj/cfxx")
    void cftzyyh(@RequestParam(name = "processInsId") String processInsId,
               @RequestParam(value = "currentUserName", required = false) String currentUserName);

    /**
     * 一手房信息推送
     *
     * @param processInsId
     * @param currentUserName
     */
    @PostMapping("/realestate-exchange/rest/v1.0/xxts/ysfdy/ysfxx")
    void ysftzyh(@RequestParam(name = "processInsId") String processInsId,
               @RequestParam(value = "currentUserName", required = false) String currentUserName);

    /**
     * 审批状态通知银行 转发事件
     *
     *@param gzlslid gzlslid
     * @return
     */
    @GetMapping("/realestate-exchange/rest/v1.0/xxts/spzt/zfsj")
    YhxxTsspxxResponseDTO spztZfsj(@RequestParam(name = "gzlslid") String gzlslid);

    /**
     * 审批状态通知银行 删除事件
     *
     *@param gzlslid gzlslid
     *@param reason reason
     * @return
     */
    @GetMapping("/realestate-exchange/rest/v1.0/xxts/spzt/scsj")
    YhxxTsspxxResponseDTO spztScsj(@RequestParam(name = "gzlslid") String gzlslid,
                                   @RequestParam(value = "reason", required = false) String reason);

}
