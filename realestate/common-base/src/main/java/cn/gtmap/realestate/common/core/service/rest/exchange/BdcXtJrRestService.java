package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.exchange.BdcXtJrDO;
import cn.gtmap.realestate.common.core.qo.exchange.openapi.BdcXtJrQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 系统接入rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-06 16:27
 **/
public interface BdcXtJrRestService {


    /**
     * @param bdcXtJrQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询系统接入信息
     * @date : 2022/7/6 19:00
     */
    @PostMapping("/realestate-exchange/rest/v1.0/xtjr")
    List<BdcXtJrDO> listBdcXtJr(@RequestBody BdcXtJrQO bdcXtJrQO);


    @DeleteMapping("/realestate-exchange/rest/v1.0/xtjr/{id}")
    int deleteBdcXtJr(@PathVariable(value = "id") String id);

    /**
     * @param ywfwdm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入服务的业务代码
     * @date : 2022/7/8 11:24
     */
    @GetMapping("/realestate-exchange/rest/v1.0/xtjr/ywdm")
    Map queryJrYwdm(@RequestParam(value = "ywfwdm", required = true) String ywfwdm);
}
