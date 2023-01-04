package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlXztzPzService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXztzPzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXztzPzDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlXztzPzRestService;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
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

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2019/1/30
 * @description
 */
@RestController
@Api(tags = "不动产受理选择台账配置服务")
public class BdcSlXztzPzRestController extends BaseController implements BdcSlXztzPzRestService {
    @Autowired
    private BdcSlXztzPzService bdcSlXztzPzService;


    /**
     * @param gzldyid 工作流定义ID
     * @return 不动产受理选择台账配置
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流定义ID获取不动产受理选择台账配置
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流定义ID获取不动产受理选择台账配置", notes = "根据工作流定义ID获取不动产受理选择台账配置服务")
    @ApiImplicitParam(name = "gzldyid", value = "工作流定义ID", required = true, dataType = "String", paramType = "query")
    public BdcSlXztzPzDO queryBdcSlXztzPzDOByGzldyid(@RequestParam(value = "gzldyid") String gzldyid) {
        if (StringUtils.isBlank(gzldyid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcSlXztzPzService.queryBdcSlXztzPzDOByGzldyid(gzldyid);
    }

    /**
     * @param bdcSlXztzPzDO 不动产受理选择台账配置
     * @return 保存数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理选择台账配置
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存不动产受理选择台账配置", notes = "保存不动产受理选择台账配置服务")
    @ApiImplicitParam(name = "bdcSlXztzPzDO", value = "不动产受理选择台账配置", required = true, dataType = "BdcSlXztzPzDO", paramType = "query")
    public int saveBdcSlXztzPzDO(@RequestBody BdcSlXztzPzDO bdcSlXztzPzDO){
        return bdcSlXztzPzService.saveBdcSlXztzPzDO(bdcSlXztzPzDO);
    }

    /**
     * @param gzldyid 工作流定义ID
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义ID删除受理选择台账配置
     */
    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据工作流定义ID删除受理选择台账配置", notes = "根据工作流定义ID删除受理选择台账配置服务")
    @ApiImplicitParam(name = "gzldyid", value = "工作流定义ID", required = true, dataType = "String", paramType = "query")
    public int deleteBdcSlXztzPzDOByGzldyid(@RequestParam(value = "gzldyid") String gzldyid){
        return bdcSlXztzPzService.deleteBdcSlXztzPzDOByGzldyid(gzldyid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流定义ID获取不动产受理选择台账配置", notes = "根据工作流定义ID获取不动产受理选择台账配置服务")
    @ApiImplicitParam(name = "gzldyid", value = "工作流定义ID", required = true, dataType = "String", paramType = "query")
    public BdcSlXztzPzDTO queryBdcSlXztzPzDTOByGzldyid(@RequestParam(value = "gzldyid") String gzldyid){
        if (StringUtils.isBlank(gzldyid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcSlXztzPzService.queryBdcSlXztzPzDTOByGzldyid(gzldyid);

    }
}
