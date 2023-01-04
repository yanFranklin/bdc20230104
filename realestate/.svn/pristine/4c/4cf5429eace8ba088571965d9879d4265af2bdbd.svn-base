package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.QszdQlrService;
import cn.gtmap.realestate.building.core.service.QszdService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.building.QszdDjdcbDO;
import cn.gtmap.realestate.common.core.domain.building.QszdQlrDO;
import cn.gtmap.realestate.common.core.service.rest.building.QszdRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/13
 * @description 权属宗地相关服务
 */
@RestController
@Api(tags = "权属宗地服务接口")
public class QszdRestController extends BaseController implements QszdRestService {
    @Autowired
    private QszdService qszdService;

    @Autowired
    private QszdQlrService qszdQlrService;

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.domain.building.BdcDjsjDO
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据BDCDYH查询权属宗地基本信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询权属宗地基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public QszdDjdcbDO queryQszdByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return qszdService.queryQszdDjdcbByBdcdyh(bdcdyh);
    }


    /**
     * @param bdcdyh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.building.QszdQlrDO>
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据不动产单元号查询权属宗地权利人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询权属宗地权利人基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public List<QszdQlrDO> listQszdQlrByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return qszdQlrService.listQszdQlrByBdcdyh(bdcdyh);
    }
}