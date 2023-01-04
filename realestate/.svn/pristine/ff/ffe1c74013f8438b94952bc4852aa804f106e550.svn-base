package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.BdcCdBlxxDO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2021/08/12
 * @Description: 补录信息Rest服务
 */
public interface BdcSlCdBlxxRestService {
    /**
     * @param bdcCdBlxxDO
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 查询补录信息
     * @date : 2021/08/12
     */
    @PostMapping("/realestate-accept/rest/v1.0/blxx/query")
    BdcCdBlxxDO queryBdcBlxx(@RequestBody BdcCdBlxxDO bdcCdBlxxDO);

    /**
     * @param bdcCdBlxxDO
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 保存补录信息
     * @date : 2021/08/12
     */
    @PostMapping("/realestate-accept/rest/v1.0/blxx/save")
    BdcCdBlxxDO saveBdcBlxx(@RequestBody BdcCdBlxxDO bdcCdBlxxDO);

    /**
     * @param blxxid,xmid
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 删除补录信息
     * @date : 2021/08/12
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/blxx/delete")
    int deleteBdcBlxx(@RequestParam(value = "blxxid",required = false) String blxxid, @RequestParam(value = "xmid",required = false) String xmid);

}
