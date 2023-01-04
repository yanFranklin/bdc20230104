package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.DjxxService;
import cn.gtmap.realestate.building.service.NydDjxxService;
import cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.building.DjxxZdResponseDTO;
import cn.gtmap.realestate.common.core.service.rest.building.DjxxRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-16
 * @description 为业务初始化系统提供地籍信息服务
 */
@Api(tags = "地籍信息服务接口")
@RestController
public class DjxxRestController implements DjxxRestService{

    @Autowired
    private DjxxService djxxService;

    @Autowired
    private NydDjxxService nydDjxxService;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @description 根据BDCDYH查询地籍信息 包含DJDCB、DCB、权利人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询地籍信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public DjxxResponseDTO queryDjxxByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm){
        return djxxService.getDjxxDTOForInitByBdcdyh(bdcdyh,"");
    }

    /**
     * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 根据zl查询地籍信息（多个）
     * @Date 2022/5/9
     **/
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据zl查询地籍信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zl", value = "坐落", required = true, dataType = "String", paramType = "query")})
    public DjxxZdResponseDTO queryBdcjsydsyqByzl(@RequestParam(name = "zl", required = false) String zl){
        return djxxService.queryBdcjsydsyqByzl(zl);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询地籍信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public DjxxResponseDTO queryDjxx(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "djqlrgxid", required = false) String djqlrgxid,@RequestParam(name = "qjgldm", required = false) String qjgldm){
        return djxxService.getDjxxDTOForInitByBdcdyh(bdcdyh,djqlrgxid);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据承包宗地与承包方关系主键查询承包宗地籍信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public DjxxResponseDTO queryCbzdDjxxByCbzdDcbcbfrelIndex(@PathVariable("bdcdyh") String bdcdyh,@PathVariable("cbzdDcbcbfrelIndex") String cbzdDcbcbfrelIndex,@RequestParam(name = "qjgldm", required = false) String qjgldm){
        return nydDjxxService.getCbzdDjxxForInit(bdcdyh, cbzdDcbcbfrelIndex);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjxxResponseDTO
     * @description 根据BDCDYH查询地籍信息 包含DJDCB、DCB、权利人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询最后一次备份的地籍信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public DjxxResponseDTO queryHDjxxByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm){
        return djxxService.getHDjxxDTOForInitByBdcdyh(bdcdyh);
    }

    /**
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据bdcdyh查询土地地籍调查表
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据bdcdyh查询地籍信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public DjDcbResponseDTO queryDjDcbByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return djxxService.getDjdcbDTOByBdcdyh(bdcdyh);
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @param bdcdyh
     * @return cn.gtmap.realestate.common.core.dto.building.DjDcbAndQlrResponseDTO
     * @description 根据BDCDYH查询地籍信息 包含DJDCB、权利人
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据BDCDYH查询地籍信息和权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "String", paramType = "path")})
    public DjDcbAndQlrResponseDTO getDjdcbAndQlrByBdcdyh(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(name = "qjgldm", required = false) String qjgldm) {
        return djxxService.getDjdcbAndQlrByBdcdyh(bdcdyh);
    }

}
