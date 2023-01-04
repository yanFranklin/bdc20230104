package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.exchange.BdcXtJrDO;
import cn.gtmap.realestate.common.core.qo.exchange.openapi.BdcXtJrQO;
import cn.gtmap.realestate.common.core.service.rest.exchange.BdcXtJrRestService;
import cn.gtmap.realestate.exchange.service.BdcXtJrService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 系统接入配置restcontroller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-06 16:22
 **/
@RestController
@Api(tags = "系统接入配置接口")
public class BdcXtJrRestController implements BdcXtJrRestService {

    @Autowired
    BdcXtJrService bdcXtJrService;

    @Override
    public List<BdcXtJrDO> listBdcXtJr(@RequestBody BdcXtJrQO bdcXtJrQO) {
        return bdcXtJrService.listBdcXtJr(bdcXtJrQO);
    }

    @Override
    public int deleteBdcXtJr(@PathVariable(value = "id") String id) {
        return bdcXtJrService.deleteBdcXtJr(id);
    }

    /**
     * @param ywfwdm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入服务的业务代码
     * @date : 2022/7/8 11:24
     */
    @Override
    public Map queryJrYwdm(@RequestParam(value = "ywfwdm", required = true) String ywfwdm) {
        Map resultMap = new HashMap(1);
        resultMap.put("ywdm", bdcXtJrService.queryYwdm(ywfwdm));
        return resultMap;
    }


}
