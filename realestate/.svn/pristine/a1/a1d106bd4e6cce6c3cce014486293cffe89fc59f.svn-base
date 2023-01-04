package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.core.service.BdcdyhService;
import cn.gtmap.realestate.building.web.main.BaseController;
import cn.gtmap.realestate.common.core.service.rest.building.BdcdyhRestService;
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

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/20
 * @description
 */
@RestController
@Api(tags = "不动产单元号生成接口")
public class BdcdyhRestController extends BaseController implements BdcdyhRestService {
    @Autowired
    private BdcdyhService bdcdyhService;

    /**
     * @param fwDcbIndex
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据逻辑幢主键生成不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据逻辑幢主键生成不动产单元号")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwDcbIndex", value = "逻辑幢主键", required = true, dataType = "String", paramType = "path")})
    public String creatFwBdcdyhByFwDcbIndex(@PathVariable("fwDcbIndex") String fwDcbIndex) {
        return bdcdyhService.creatFwBdcdyhByFwDcbIndex(fwDcbIndex);
    }

    /**
     * @param djh
     * @param zrzh
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据DJH和ZRZH生成不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据DJH和ZRZH生成不动产单元号")
    @ApiImplicitParams({@ApiImplicitParam(name = "djh", value = "地籍号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zrzh", value = "自然幢号", required = true, dataType = "String", paramType = "path")})
    public String creatFwBdcdyhByDjhAndZrzh(@PathVariable("djh") String djh, @PathVariable("zrzh") String zrzh) {
        return bdcdyhService.creatFwBdcdyhByZdzhdmAndZrzh(djh, zrzh);
    }

    /**
     * @param fwXmxxIndex
     * @return java.lang.String
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据项目信息主键生成不动产单元号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据项目信息主键生成不动产单元号")
    @ApiImplicitParams({@ApiImplicitParam(name = "fwXmxxIndex", value = "项目信息主键", required = true, dataType = "String", paramType = "path")})
    public String creatXmxxBdcdyhByFwXmxxIndex(@PathVariable("fwXmxxIndex") String fwXmxxIndex) {
        return bdcdyhService.creatXmxxBdcdyhByFwXmxxIndex(fwXmxxIndex);
    }

    /**
     * @param bdcdyh
     * @return java.lang.String
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 验证选择的不动产单元是否存在且有实测面积
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "验证选择的不动产单元是否存在且有实测面积")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", required = false, dataType = "string", paramType = "query")})
    public String checkBdcdyhSfczscmj(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyhService.checkBdcdyhSfczscmj(bdcdyh);
    }

    /**
     * @param bdcdyh,qjgldm
     * @return java.lang.String
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @description 验证所在宗地不动产单元锁定
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "验证所在宗地不动产单元锁定")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qjgldm", value = "权籍代码", required = false, dataType = "string", paramType = "query")})
    public String checkBdcdyhSfsd(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return bdcdyhService.checkBdcdyhSfsd(bdcdyh);
    }

}