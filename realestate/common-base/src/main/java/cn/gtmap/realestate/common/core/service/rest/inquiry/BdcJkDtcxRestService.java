package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.DtcxConfigCheckDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


/**
 * @author: <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version: V1.0, 2019/07/16
 * @description:
 */
public interface BdcJkDtcxRestService {
    /**
     * 检查查询条件配置正确与否
     *
     * @param sql  sql字符串
     * @param cxtj 查询条件json字符串（所有条件配置项）
     * @return cn.gtmap.realestate.common.core.dto.inquiry.DtcxConfigCheckDTO
     * @date 2019/07/16
     * @author hanyaning
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/jk/dtcx/check/cxtj")
    DtcxConfigCheckDTO checkCxtj(@RequestParam("cxjson") String json, @RequestParam("cxtj") String cxtj);


    /**
     * 查询结果获取
     *
     * @param dataString json字符串，包括查询代号、sql、查询条件信息
     * @param page
     * @param size
     * @param sort
     * @return org.springframework.data.domain.Page
     * @date 2019/07/16
     * @author hanyi
     */
    @PostMapping("/realestate-inquiry/rest/v1.0/jk/dtcx/list/result")
    Object listResult(@RequestParam("dataString") String dataString,  @RequestParam("jk") String jk,
                      @RequestParam("jkid") String jkid, @RequestParam("fhzkey") String fhzkey, @RequestParam("page") int page
            , @RequestParam("size") int size);
}