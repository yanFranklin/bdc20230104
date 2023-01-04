package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.gtc.workflow.clients.manage.ProcessInsCustomExtendClient;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.init.BdcYwsjHxRestService;
import cn.gtmap.realestate.init.service.other.BdcYwsjHxService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/3/1
 * @description 不动产业务数据回写接口
 */
@RestController
@Api(tags = "不动产业务数据回写接口")
public class BdcYwsjHxRestController extends BaseController implements BdcYwsjHxRestService {
    @Autowired
    private ProcessInsCustomExtendClient processInsCustomExtendClient;
    @Autowired
    private BdcYwsjHxService bdcYwsjHxService;

    /**
     * @param gzlslid
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询业务数据
     */
    @ApiOperation(value = "查询业务数据")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "string", paramType = "path")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<Map<String, Object>> queryBdcYwsj(@PathVariable("gzlslid") String gzlslid) throws Exception {
        return processInsCustomExtendClient.getProcessInsCustomExtend(gzlslid);
    }

    /**
     * 更新流程扩展属性
     * @param gzlslid
     * @param params  key: 表列名称  object：属性
     */
    @ApiOperation(value = "更新流程扩展属性")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "params", value = "更新数据", dataType = "map")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void updateBdcYwsj(@RequestParam("gzlslid") String gzlslid, @RequestBody Map<String, Object> params) {
        if(StringUtils.isBlank(gzlslid) || MapUtils.isEmpty(params)){
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        processInsCustomExtendClient.updateProcessInsCustomExtend(gzlslid,params);
    }

    /**
     * @param gzlslid
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存/更新组织的业务数据
     */
    @ApiOperation(value = "保存/更新组织的业务数据")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "string", paramType = "path")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void saveBdcYwsj(@PathVariable("gzlslid") String gzlslid) throws Exception {
        bdcYwsjHxService.saveBdcYwsj(gzlslid);
    }


    /**
     * @param gzlslid
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除业务数据
     */
    @ApiOperation(value = "删除业务数据")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", dataType = "string", paramType = "path")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteBdcYwsj(@PathVariable("gzlslid") String gzlslid) throws Exception {
        processInsCustomExtendClient.delProcessInsCustomExtend(gzlslid);
    }


}
