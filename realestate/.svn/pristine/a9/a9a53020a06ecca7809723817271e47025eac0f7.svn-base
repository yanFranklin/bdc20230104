package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcFgfDO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcFgfRestService;
import cn.gtmap.realestate.register.service.BdcFgfService;
import cn.gtmap.realestate.register.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/17
 * @description 登记房改房服务
 */
@RestController
@Api(tags = "登记房改房服务")
public class BdcFgfRestCotroller extends BaseController implements BdcFgfRestService {
    @Autowired
    BdcFgfService bdcFgfService;


    /**
     * @param bdcFgfDOList 批量的房改房信息
     * @return int 执行的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存房改房项目信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存房改房项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFgfDOList", value = "房改房实体List", required = true, dataType = "list", paramType = "body")})
    @Override
    public int saveFgfxm(@RequestBody List<BdcFgfDO> bdcFgfDOList) {
        return bdcFgfService.saveFgfxm(bdcFgfDOList);
    }

    /**
     * @param bdcFgfDOList 批量的房改房信息
     * @return int 执行的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新房改房项目信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新房改房项目信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcFgfDOList", value = "房改房实体List", required = true, dataType = "List", paramType = "body")})
    @Override
    public int updateFgfxm(@RequestBody List<BdcFgfDO> bdcFgfDOList) {
        return bdcFgfService.updateFgfxm(bdcFgfDOList);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return List<BdcFgfDO>  查询到的房改房信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程的房改房信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取当前流程的房改房信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public List<BdcFgfDO> getLcFgfxm(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcFgfService.getLcFgfxm(gzlslid);
    }


    /**
     * @param processInsId
     * @return void
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 现省直房改房上市交易成功后，把不动产登记的数据再推送给房改办进行登记
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("现省直房改房上市交易成功后，把不动产登记的数据再推送给房改办进行登记")
    public void djSzfgb(String processInsId) {
        bdcFgfService.djSzfgb(processInsId);
    }
}
