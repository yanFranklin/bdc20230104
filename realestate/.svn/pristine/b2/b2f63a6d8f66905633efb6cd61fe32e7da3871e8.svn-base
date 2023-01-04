package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlJrlwService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJrLwDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlJrlwRestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: bdcdj3.0
 * @description: 接入例外表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-10-05 16:01
 **/
@RestController
@Api(tags = "不动产审核信息服务接口")
public class BdcSlJrlwRestController extends BaseController implements BdcSlJrlwRestService {
    @Autowired
    BdcSlJrlwService bdcSlJrlwService;

    /**
     * @param bdcSlJrLwDOList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增或者保存例外信息
     * @date : 2022/10/5 16:05
     */
    @Override
    public int saveOrUpdateJrlw(@RequestBody List<BdcSlJrLwDO> bdcSlJrLwDOList) {
        return bdcSlJrlwService.saveOrUpdateJrlw(bdcSlJrLwDOList);
    }
}
