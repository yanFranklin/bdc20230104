package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcXtMryjRestService;
import cn.gtmap.realestate.register.service.BdcXtMryjService;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/11/29
 * @description 默认意见接口
 */
@RestController
@Api(tags = "不动产默认意见服务接口")
public class BdcXtMryjRestController extends BaseController implements BdcXtMryjRestService {

    @Autowired
    private BdcXtMryjService bdcXtMryjService;

    /**
     * @param cjrid
     * @return List<BdcXtMryjDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取可选意见")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "cjrid", value = "创建人id", paramType = "path", dataType = "string"),
                    @ApiImplicitParam(name = "gzldyKey", value = "工作流定义key", paramType = "path", dataType = "string"),
                    @ApiImplicitParam(name = "jdmc", value = "节点名称", paramType = "path", dataType = "string")
            }
    )
    public List<BdcXtMryjDO> listBdcXtKxyj(@RequestParam(value = "cjrid", required = false) String cjrid, @PathVariable(value = "gzldyKey") String gzldyKey, @RequestParam(value = "jdmc") String jdmc) {
        return bdcXtMryjService.listBdcXtKxyj(cjrid, gzldyKey, jdmc);
    }


    /**
     * @param cjrid 创建人id
     * @param gzldyKey 工作流定义key
     * @param jdmc 节点名称
     * @return BdcXtMryjDO 默认意见列表
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取当前用户当前流程当前节点对应的默认意见
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取默认意见", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "cjrid", value = "创建人id", paramType = "path", dataType = "string"),
                    @ApiImplicitParam(name = "gzldyKey", value = "工作流定义key", paramType = "path", dataType = "string"),
                    @ApiImplicitParam(name = "jdmc", value = "节点名称", paramType = "path", dataType = "string")
            }
    )
    public BdcXtMryjDO queryMryj(@RequestParam(value = "cjrid", required = false) String cjrid, @PathVariable(value = "gzldyKey") String gzldyKey, @PathVariable(value = "jdmc") String jdmc) {
        return bdcXtMryjService.queryBdcXtMryj(cjrid, gzldyKey, jdmc);
    }
}
