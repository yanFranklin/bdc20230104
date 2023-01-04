package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcTddysfDyhDO;
import cn.gtmap.realestate.common.core.dto.register.BdcTddysfDyhDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcTddysfDyhShDTO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcTddysfDyhRestService;
import cn.gtmap.realestate.register.core.service.BdcTddysfDyhService;
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
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/10/12
 * @description 不动产项目土地抵押释放关系服务
 */
@RestController
@Api(tags = "不动产项目土地抵押释放关系服务接口")
public class BdcTddysfDyhRestController implements BdcTddysfDyhRestService {

    @Autowired
    private BdcTddysfDyhService bdcTddysfDyhService;

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取土地抵押释放单元号集合")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public List<BdcTddysfDyhDO> listTddysfBdcdyhByGzlslid(@PathVariable(value = "gzlslid") String gzlslid){
        return bdcTddysfDyhService.listTddysfBdcdyhByGzlslid(gzlslid);

    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "生成土地抵押释放单元号信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcTddysfDyhDTO", value = "土地抵押释放单元号信息", required = true, dataType = "body", paramType = "query")})
    @Override
    public void initBdcTddysfDyh(@RequestBody BdcTddysfDyhDTO bdcTddysfDyhDTO){
        bdcTddysfDyhService.initBdcTddysfDyh(bdcTddysfDyhDTO);

    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除土地抵押释放单元号信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcTddysfDyhDTO", value = "土地抵押释放单元号信息", required = true, dataType = "body", paramType = "query")})
    @Override
    public void deleteBdcTddysfDyh(@RequestBody BdcTddysfDyhDTO bdcTddysfDyhDTO){
        bdcTddysfDyhService.deleteBdcTddysfDyh(bdcTddysfDyhDTO);

    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新土地抵押释放审核信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcTddysfDyhShDTO", value = "审核信息", required = true, dataType = "body", paramType = "query")})
    @Override
    public void updateBdcTddysfDyhShxx(@RequestBody BdcTddysfDyhShDTO bdcTddysfDyhShDTO){
        bdcTddysfDyhService.updateBdcTddysfDyhShxx(bdcTddysfDyhShDTO);
    }
}
