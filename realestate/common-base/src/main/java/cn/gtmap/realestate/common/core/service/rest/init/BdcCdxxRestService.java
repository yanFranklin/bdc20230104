package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcCdxxDO;
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
public interface BdcCdxxRestService {


    /**
     * @param bdcCdxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询查档信息
     * @date : 2020/9/23 17:54
     */
    @PostMapping("/init/rest/v1.0/cdxx/query")
    BdcCdxxDO queryBdcCdxx(@RequestBody BdcCdxxQO bdcCdxxQO);

    /**
     * @param bdcCdxxDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存查档信息
     * @date : 2020/9/23 17:54
     */
    @PostMapping("/init/rest/v1.0/cdxx/save")
    BdcCdxxDO saveBdcCdxx(@RequestBody BdcCdxxDO bdcCdxxDO);

    /**
     * @param cdxxid,xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除查档信息
     * @date : 2020/9/23 17:54
     */
    @DeleteMapping("/init/rest/v1.0/cdxx/delete")
    int deleteBdcCdxx(@RequestParam(value = "cdxxid",required = false) String cdxxid, @RequestParam(value = "xmid",required = false) String xmid);


}
