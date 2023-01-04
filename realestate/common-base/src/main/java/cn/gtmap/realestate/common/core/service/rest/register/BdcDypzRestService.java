package cn.gtmap.realestate.common.core.service.rest.register;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/17
 * @description 不动产打印配置表服务
 */
public interface BdcDypzRestService {

    /**
     * @param bdcDysjPzDO
     * @return List<BdcDysjPzDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取不动产打印配置主表数据
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dysjpz", method = RequestMethod.POST)
    List<BdcDysjPzDO> listBdcDysjPz(@RequestBody BdcDysjPzDO bdcDysjPzDO);

    /**
     * @param  bdcDysjZbPzDO
     * @return List<BdcDysjZbPzDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印配置子表数据
     */
    @RequestMapping(value = "/realestate-register/rest/v1.0/dyzbsjpz", method = RequestMethod.POST)
    List<BdcDysjZbPzDO> listBdcDysjZbPz(@RequestBody BdcDysjZbPzDO bdcDysjZbPzDO);
}
