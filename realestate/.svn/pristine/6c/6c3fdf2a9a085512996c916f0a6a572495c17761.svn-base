package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcDjyySjclPzService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjyySjclPzDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcDjyySjclPzQO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcDjyySjclPzRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: realestate
 * @description: 登记原因收件材料配置rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-12-11 14:17
 **/
@RestController
@Api(tags = "不动产受理登记原因收件材料配置服务")
public class BdcDjyySjclPzRestController extends BaseController implements BdcDjyySjclPzRestService {
    @Autowired
    BdcDjyySjclPzService bdcDjyySjclPzService;

    /**
     * @param bdcDjyySjclPzQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2019/12/11 14:26
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不动产受理登记原因收件材料配置", notes = "不动产受理登记原因收件材料配置服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDjyySjclPzQO", value = "登记原因收件材料查询条件", required = true, dataType = "BdcDjyySjclPzQO")})
    public List<BdcDjyySjclPzDO> querySjclPz(@RequestBody BdcDjyySjclPzQO bdcDjyySjclPzQO) {
        return bdcDjyySjclPzService.querySjclPz(bdcDjyySjclPzQO);
    }

    /**
     * @param bdcDjyySjclPzJson 登记原因收件材料配置
     * @return 登记原因收件材料配置分页
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 登记原因收件材料配置分页
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登记原因收件材料配置分页", notes = "登记原因收件材料配置分页服务")
    public Page<BdcDjyySjclPzDO> listBdcDjyySjclPzByPage(Pageable pageable, @RequestParam(name = "bdcDjyySjclPzJson", required = false) String bdcDjyySjclPzJson) {
        return bdcDjyySjclPzService.listBdcDjyySjclPzByPage(pageable, bdcDjyySjclPzJson);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存不动产登记原因收件材料配置信息", notes = "保存不动产登记原因收件材料配置信息服务")
    @ApiImplicitParam(name = "bdcDjyySjclPzDO", value = "不动产登记原因收件材料配置信息", required = true, dataType = "BdcDjyySjclPzDO", paramType = "query")
    public int saveBdcDjyySjclPz(@RequestBody BdcDjyySjclPzDO bdcDjyySjclPzDO) {
        return bdcDjyySjclPzService.saveBdcDjyySjclPz(bdcDjyySjclPzDO);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "删除不动产登记原因收件材料配置信息", notes = "删除不动产登记原因收件材料配置信息服务")
    @ApiImplicitParam(name = "bdcDjyySjclPzDOList", value = "不动产受理收件材料配置集合", required = true, dataType = "BdcDjyySjclPzDO", paramType = "query")
    public int deleteBdcDjyySjclPzList(@RequestBody List<BdcDjyySjclPzDO> bdcDjyySjclPzDOList){
        return bdcDjyySjclPzService.deleteBdcDjyySjclPzList(bdcDjyySjclPzDOList);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取收件材料登记原因配置最大序号", notes = "获取收件材料登记原因配置最大序号")
    public int queryDjyySjclPzMaxSxh(@RequestBody BdcDjyySjclPzDO bdcDjyySjclPzDO){
        return bdcDjyySjclPzService.queryDjyySjclPzMaxSxh(bdcDjyySjclPzDO);

    }
}
