package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcCdBlxxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcBlxxQO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: realestate
 * @description: 补录信息Rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-24 09:12
 **/
public interface BdcCdBlxxRestService{

    /**
     * @param bdcBlxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询补录信息
     * @date : 2020/9/23 17:54
     */
    @PostMapping("/init/rest/v1.0/blxx/query")
    BdcCdBlxxDO queryBdcBlxx(@RequestBody BdcBlxxQO bdcBlxxQO);

    /**
     * @param bdcCdBlxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存补录信息
     * @date : 2020/9/23 17:54
     */
    @PostMapping("/init/rest/v1.0/blxx/save")
    BdcCdBlxxDO saveBdcBlxx(@RequestBody BdcCdBlxxDO bdcCdBlxxDO);

    /**
     * @param blxxid,xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除补录信息
     * @date : 2020/9/23 17:54
     */
    @DeleteMapping("/init/rest/v1.0/blxx/delete")
    int deleteBdcBlxx(@RequestParam(value = "blxxid",required = false) String blxxid, @RequestParam(value = "xmid",required = false) String xmid);
}
