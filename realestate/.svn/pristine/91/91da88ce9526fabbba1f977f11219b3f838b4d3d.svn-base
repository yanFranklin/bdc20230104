package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlYcslDjywDzbService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYcslDjywDzbDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcYcslDjywDzbRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/5
 * @description
 */
@RestController
@Api(tags = "一窗受理登记业务对照关系服务")
public class BdcYcslDjywDzbRestController extends BaseController implements BdcYcslDjywDzbRestService {
    @Autowired
    BdcSlYcslDjywDzbService bdcSlYcslDjywDzbService;

    /**
     * @param bdcSlYcslDjywDzbDO 查询对象
     * @return 对照关系
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询一窗受理与登记业务对照关系
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询一窗受理与登记业务对照关系")
    public BdcSlYcslDjywDzbDO queryYcslDjywDzb(@RequestBody BdcSlYcslDjywDzbDO bdcSlYcslDjywDzbDO){
        return bdcSlYcslDjywDzbService.queryYcslDjywDzb(bdcSlYcslDjywDzbDO);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "判断是否为一窗受理流程")
    public Boolean checkIsYcslLc(@PathVariable(value = "gzldyid") String gzldyid){
        return bdcSlYcslDjywDzbService.checkIsYcslLc(gzldyid);
    }
}
