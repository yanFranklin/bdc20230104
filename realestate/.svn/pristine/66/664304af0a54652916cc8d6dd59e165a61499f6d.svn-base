package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcDjxlPzService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDjxlPzQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcDjxlPzRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/12
 * @description 不动产登记小类配置rest服务
 */
@RestController
@Api(tags = "不动产登记小类配置rest服务")
public class BdcDjxlPzRestController extends BaseController implements BdcDjxlPzRestService {
    @Autowired
    private BdcDjxlPzService bdcDjxlPzService;

    /**
     * @param gzldyid 工作流定义ID
     * @return 不动产登记小类配置
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流定义ID获取不动产登记小类配置 gzldyid可能为中文，修改参数格式
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流定义ID获取不动产登记小类配置", notes = "根据工作流定义ID获取不动产登记小类配置服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzldyid", value = "工作流实例ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "qllx", value = "权利类型", required = false, dataType = "integer", paramType = "query")})
    public List<BdcDjxlPzDO> listBdcDjxlPzByGzldyid(@RequestParam(value = "gzldyid") String gzldyid, @RequestParam(value = "qllx", required = false) Integer qllx) {
        return bdcDjxlPzService.listBdcDjxlPzByGzldyid(gzldyid, qllx,null);
    }

    /**
     * @param pzid 配置ID
     * @return 不动产登记小类配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据配置ID获取不动产登记小类配置
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = " 根据配置ID获取不动产登记小类配置", notes = " 根据配置ID获取不动产登记小类配置服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "pzid", value = "配置ID", required = true, dataType = "string", paramType = "path")})
    public BdcDjxlPzDO queryBdcDjxlPzDOByPzid(@PathVariable(value = "pzid") String pzid) {
        return bdcDjxlPzService.queryBdcDjxlPzDOByPzid(pzid);
    }

    /**
     * @param gzldyid 工作流定义ID
     * @return 不动产登记小类配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义ID获取不动产登记小类配置
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流定义ID获取不动产登记小类配置", notes = "根据工作流定义ID获取不动产登记小类配置服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzldyid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    public List<BdcDjxlPzDO> queryBdcDjxlPzByGzldyid(@PathVariable(value = "gzldyid") String gzldyid) {
        return bdcDjxlPzService.queryBdcDjxlPzByGzldyid(gzldyid);
    }

    /**
     * @param bdcDjxlPzDO 不动产登记小类配置
     * @return 保存数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产登记小类配置
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存不动产登记小类配置", notes = "保存不动产登记小类配置服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlPzDO", value = "不动产登记小类配置", required = true, dataType = "BdcDjxlPzDO", paramType = "query")})
    public int saveBdcDjxlPzDO(@RequestBody BdcDjxlPzDO bdcDjxlPzDO) {
        return bdcDjxlPzService.saveBdcDjxlPzDO(bdcDjxlPzDO);

    }

    /**
     * @param bdcDjxlPzDOList 登记小类配置
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除登记小类配置
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除登记小类配置", notes = "删除登记小类配置服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlPzDOList", value = "登记小类配置", required = true, dataType = "BdcDjxlPzDO", paramType = "query")})
    public int deleteBdcDjxlPzDO(@RequestBody List<BdcDjxlPzDO> bdcDjxlPzDOList) {
        return bdcDjxlPzService.deleteBdcDjxlPz(bdcDjxlPzDOList);
    }

    /**
     * @param bdcDjxlPzJson 登记小类配置
     * @return 登记小类配置分页
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登记小类配置分页
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登记小类配置分页", notes = "登记小类配置分页服务")
    public Page<BdcDjxlPzDO> listBdcDjxlPzByPage(Pageable pageable, @RequestParam(name = "bdcDjxlPzJson", required = false) String bdcDjxlPzJson) {
        return bdcDjxlPzService.listBdcDjxlPzByPage(pageable, bdcDjxlPzJson);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取登记小类配置最大顺序号", notes = "获取登记小类配置最大顺序号服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjxlPzDO", value = "登记小类配置", required = true, dataType = "BdcDjxlPzDO", paramType = "query")})
    public int queryDjxlPzMaxSxh(@RequestBody BdcDjxlPzDO bdcDjxlPzDO){
        return bdcDjxlPzService.queryDjxlPzMaxSxh(bdcDjxlPzDO);
    }

    /**
     * @param gzldyid 工作流定义ID
     * @param djxl 登记小类
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取登记小类配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取登记小类配置", notes = "获取登记小类配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzldyid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path"), @ApiImplicitParam(name = "djxl", value = "登记小类", required = true, dataType = "string", paramType = "path")})
    public BdcDjxlPzDO queryBdcDjxlPzByGzldyidAndDjxl(@PathVariable(value = "gzldyid") String gzldyid, @PathVariable(value = "djxl") String djxl) {
        return bdcDjxlPzService.queryBdcDjxlPzByGzldyidAndDjxl(gzldyid, djxl);
    }

    /**
     * @param pzid@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 上报配置校验
     * @date : 2022/7/8 10:16
     */
    @Override
    public int sbpzjy(@RequestParam(value = "pzid", required = false) String pzid) {
        return bdcDjxlPzService.sbpzjy(pzid);
    }

    /**
     * @param bdcDjxlPzQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询流程定义id
     * @date : 2022/11/29 17:04
     */
    @Override
    public List<String> listGzldyid(@RequestBody BdcDjxlPzQO bdcDjxlPzQO) {
        return bdcDjxlPzService.listLcdyids(bdcDjxlPzQO);
    }
}
