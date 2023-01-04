package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcZdydbService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcZdydbymPzDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcZdydbymPzRestService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcFpyzHyxxDbxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcFpyzQyxxDbxxVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/9/24
 * @description 自定义对比页面配置rest服务
 */
@RestController
@Api(tags = "自定义对比页面配置rest服务")
public class BdcZdydbymPzRestController extends BaseController implements BdcZdydbymPzRestService {

    @Autowired
    BdcZdydbService bdcZdydbService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据对比代号查询自定义对比配置", notes = "根据对比代号查询自定义对比配置服务")
    @ApiImplicitParam(name = "dbdh", value = "对比代号", dataType = "string", paramType = "query")
    public BdcZdydbymPzDO queryBdcZdydbPzByDbdh(@PathVariable(value = "dbdh") String dbdh){
        return bdcZdydbService.queryBdcZdydbPzByDbdh(dbdh);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "加载婚姻对比信息", notes = "加载婚姻对比信息服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "string", paramType = "query")
    public List<BdcFpyzHyxxDbxxVO> generateHyxx(@PathVariable(value = "xmid") String xmid){
        return bdcZdydbService.generateHyxx(xmid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "加载企业对比信息", notes = "加载企业对比信息服务")
    @ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "string", paramType = "query")
    public List<BdcFpyzQyxxDbxxVO> generateQyxx(@PathVariable(value = "xmid") String xmid){
        return bdcZdydbService.generateQyxx(xmid);

    }
}
