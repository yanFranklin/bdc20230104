package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlCdxxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcCdxxQO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: realestate
 * @description: 查档信息rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-23 17:54
 **/
public interface BdcSlCdxxRestService {


    /**
     * @param bdcCdxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询查档信息
     * @date : 2020/9/23 17:54
     */
    @PostMapping("/realestate-accept/rest/v1.0/cdxx/query")
    BdcSlCdxxDO queryBdcCdxx(@RequestBody BdcCdxxQO bdcCdxxQO);

    /**
     * @param bdcSlCdxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存查档信息
     * @date : 2020/9/23 17:54
     */
    @PostMapping("/realestate-accept/rest/v1.0/cdxx/save")
    BdcSlCdxxDO saveBdcCdxx(@RequestBody BdcSlCdxxDO bdcSlCdxxDO);

    /**
     * @param cdxxid,xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除查档信息
     * @date : 2020/9/23 17:54
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/cdxx/delete")
    int deleteBdcCdxx(@RequestParam(value = "cdxxid", required = false) String cdxxid, @RequestParam(value = "xmid", required = false) String xmid);


}
