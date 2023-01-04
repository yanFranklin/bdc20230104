package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcYxbdcdyKgPzService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcYxbdcdyKgPzDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcYxbdcdyKgPzRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/2/28
 * @description 不动产已选不动产单元开关配置 Rest服务
 */
@RestController
@Api(tags = "不动产已选不动产单元开关配置rest服务")
public class BdcYxbdcdyKgPzRestController extends BaseController implements BdcYxbdcdyKgPzRestService {
    @Autowired
    private BdcYxbdcdyKgPzService bdcYxbdcdyKgPzService;

    /**
     * @param gzldyid 工作流定义id
     * @return 不动产已选不动产单元开关配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义id获取已选不动产单元开关配置
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流定义ID获取已选不动产单元开关配置", notes = "根据工作流定义id获取已选不动产单元开关配置服务")
    @ApiImplicitParam(name = "gzldyid", value = "工作流定义ID", dataType = "string", paramType = "query")
    public BdcYxbdcdyKgPzDO queryBdcYxbdcdyKgPzDOByGzldyid(@RequestParam(value = "gzldyid") String gzldyid) {
        if (StringUtils.isBlank(gzldyid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcYxbdcdyKgPzService.queryBdcYxbdcdyKgPzDOByGzldyid(gzldyid);
    }

    /**
     * @param gzldyid 工作流定义id
     * @return 不动产已选不动产单元开关配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义id获取已选不动产单元开关配置
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流定义ID获取已选不动产单元开关配置", notes = "根据工作流定义id获取已选不动产单元开关配置服务")
    @ApiImplicitParam(name = "gzldyid", value = "工作流定义ID", dataType = "string", paramType = "query")
    public List<BdcYxbdcdyKgPzDO> queryBdcYxbdcdyKgPzDOListByGzldyid(@RequestParam(value = "gzldyid") String gzldyid) {
        return bdcYxbdcdyKgPzService.queryBdcYxbdcdyKgPzDOListByGzldyid(gzldyid);
    }

    /**
     * @param bdcYxbdcdyKgPzDO 已选不动产单元开关配置
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存已选不动产单元开关配置
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存已选不动产单元开关配置", notes = "保存已选不动产单元开关配置服务")
    @ApiImplicitParam(name = "bdcYxbdcdyKgPzDO", value = "已选不动产单元开关配置", dataType = "BdcYxbdcdyKgPzDO", paramType = "query")
    public int saveBdcYxbdcdyKgPzDO(@RequestBody BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO){
        return bdcYxbdcdyKgPzService.saveBdcYxbdcdyKgPzDO(bdcYxbdcdyKgPzDO);
    }

    /**
     * @param gzldyid 工作流定义ID
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义ID删除不动产单元开关配置
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据工作流定义ID删除不动产单元开关配置", notes = "根据工作流定义ID删除不动产单元开关配置服务")
    @ApiImplicitParam(name = "gzldyid", value = "工作流定义ID", dataType = "string", paramType = "query")
    public int deleteBdcYxbdcdyKgPzDOByGzldyid(@RequestParam(value = "gzldyid") String gzldyid){
        return bdcYxbdcdyKgPzService.deleteBdcYxbdcdyKgPzDOByGzldyid(gzldyid);

    }
}
