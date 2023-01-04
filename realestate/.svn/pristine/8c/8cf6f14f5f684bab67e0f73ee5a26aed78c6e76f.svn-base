package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcWtsjDO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcWtsjRestService;
import cn.gtmap.realestate.inquiry.service.BdcWtsjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/11
 * @description 问题数据服务
 */
@RestController
@Api(value = "问题数据服务")
public class BdcWtsjRestController implements BdcWtsjRestService {

    @Autowired
    private BdcWtsjService bdcWtsjService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询问题数据")
    @ApiImplicitParam(name = "bdcWtsjDO", value = "不动产问题数据", required = true, paramType = "body")
    public List<BdcWtsjDO> queryBdcWtsj(@RequestBody BdcWtsjDO bdcWtsjDO){
        return bdcWtsjService.queryBdcWtsj(bdcWtsjDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "添加问题数据")
    @ApiImplicitParam(name = "bdcWtsjDOList", value = "不动产问题数据", required = true, paramType = "body")
    public int addWtBdcdy(@RequestBody List<BdcWtsjDO> bdcWtsjDOList){
        return bdcWtsjService.addWtBdcdy(bdcWtsjDOList);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "问题数据编辑解决")
    @ApiImplicitParam(name = "bdcWtsjDOList", value = "不动产问题数据", required = true, paramType = "body")
    public int jjBdcWtsj(@RequestBody List<BdcWtsjDO> bdcWtsjDOList,
                  @RequestParam("jjfa") String jjfa){
        return bdcWtsjService.jjBdcWtsj(bdcWtsjDOList,jjfa);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新问题数据")
    @ApiImplicitParam(name = "bdcWtsjDO", value = "不动产问题数据", required = true, paramType = "body")
    public int updateWtsj(@RequestBody BdcWtsjDO bdcWtsjDO){
        return bdcWtsjService.updateWtsj(bdcWtsjDO);

    }
}
