package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSjclDzzzService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSjclDzzzDzDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSjclDzzzRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: realestate
 * @description: 收件材料电子证照对照关系rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-02 14:47
 **/

@RestController
@Api(tags = "不动产登记小类配置rest服务")
public class BdcSjclDzzzRestController extends BaseController implements BdcSjclDzzzRestService {

    @Autowired
    BdcSjclDzzzService bdcSjclDzzzService;

    /**
     * @param bdcSjclDzzzDzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据收件材料名称获取配置表中的数据
     * @date : 2020/1/2 14:51
     */
    @Override
    public List<BdcSjclDzzzDzDO> querySjclDzzzDz(@RequestBody BdcSjclDzzzDzDO bdcSjclDzzzDzDO) {
        return bdcSjclDzzzService.querySjclDzzzDzByClmc(bdcSjclDzzzDzDO);
    }


    /**
     * @param bdcSjclDzzzDzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存对照关系
     * @date : 2020/1/2 15:23
     */
    @Override
    public int saveBdcSjclDzzz(@RequestBody BdcSjclDzzzDzDO bdcSjclDzzzDzDO) {
        return bdcSjclDzzzService.insertBdcSjclDzzzDzDO(bdcSjclDzzzDzDO);
    }

    /**
     * @param bdcSjclDzzzDzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新对照关系
     * @date : 2020/1/2 15:39
     */
    @Override
    public int updateBdcSjclDzzz(@RequestBody BdcSjclDzzzDzDO bdcSjclDzzzDzDO) {
        return bdcSjclDzzzService.updateBdcSjclDzzzDzDO(bdcSjclDzzzDzDO);
    }
}
