package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFcdaDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFwqlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcFwJbxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFcdaQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcFwQsxxQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcZfxxCxRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.inquiry.service.BdcZfxxCxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/8
 * @description 不动产住房信息查询服务接口
 */
@RestController
@Api(tags = "不动产住房信息查询服务接口")
public class BdcZfxxCxRestController implements BdcZfxxCxRestService{
    /**
     * 住房信息查询
     */
    @Autowired
    private BdcZfxxCxService bdcZfxxCxService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @description 根据权利人名称、证件号查询房产信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据权利人名称、证件号查询房产信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcZfxxQO", value = "住房查询参数",
            required = true, paramType = "body")})
    public List<BdcZfxxDTO> listBdcZfxxDTO(@RequestBody BdcZfxxQO bdcZfxxQO){
        return bdcZfxxCxService.listBdcZfxxDTO(bdcZfxxQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcZfxxQO 查询参数
     * @return {List} 住房信息
     * @description 根据权利人名称、证件号查询房产信息（有房、无房查询）
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据权利人名称、证件号查询房产信息")
    @ApiImplicitParam(name = "bdcZfxxQO", value = "住房查询参数", required = true, paramType = "body")
    public List<BdcZfxxDTO> listBdcNtZfxxDTO(@RequestBody BdcZfxxQO bdcZfxxQO) {
        return bdcZfxxCxService.listBdcNtZfxxDTO(bdcZfxxQO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcdyh 不动产单元号
     * @return {List} 房产档案信息
     * @description 根据不动产单元号查询房产档案信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据不动产单元号查询房产档案信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, paramType = "path")})
    public BdcFcdaDTO getBdcFcdaDTO(@PathVariable("bdcdyh") String bdcdyh,@RequestParam(value = "qjgldm",required = false) String qjgldm) {
        return bdcZfxxCxService.getBdcFcdaDTO(bdcdyh,qjgldm);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据不动产单元号查询房产档案信息(exchange转发用)")
    @ApiImplicitParam(name = "bdcFcdaQO", value = "住房查询参数", required = true, paramType = "body")
    public BdcFcdaDTO getBdcFcdaInfo(@RequestBody BdcFcdaQO bdcFcdaQO) {
        return bdcZfxxCxService.getBdcFcdaDTO(bdcFcdaQO.getBdcdyh(),bdcFcdaQO.getQjgldm());
    }

    /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param bdcFwqlQO 查询参数
     * @return {List} 房屋权利
     * @description 自助查询机查询房屋权利
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据权利人名称、证件号等查询房屋权利信息")
    @ApiImplicitParam(name = "bdcFwqlQO", value = "住房查询参数", required = true, paramType = "body")
    public List<BdcFwqlDTO> listBdcFwqlDTO(@RequestBody BdcFwqlQO bdcFwqlQO) {
        return bdcZfxxCxService.listBdcFwqlDTO(bdcFwqlQO);
    }

    /**
     * （盐城）查询房屋权属信息（以物为主）
     * @param bdcFwQsxxQO 查询参数
     * @return {List} 房屋权属信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询房屋权属信息")
    @ApiImplicitParam(name = "bdcFwQsxxQO", value = "查询参数", required = true, paramType = "body")
    public List<BdcFwJbxxDTO> listBdcFwQsxxDTO(@RequestBody BdcFwQsxxQO bdcFwQsxxQO) {
        return bdcZfxxCxService.listBdcFwQsxxDTO(bdcFwQsxxQO);
    }

    /**
     * 自助查询机查询合同信息
     * @param bdcZfxxQO
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("自助查询机查询合同信息")
    @ApiImplicitParam(name = "bdcFwQsxxQO", value = "查询参数", required = true, paramType = "body")
    public List<BdcZfxxDTO> listBdcHtxx(@RequestBody BdcZfxxQO bdcZfxxQO) {
        return bdcZfxxCxService.listBdcHtxx(bdcZfxxQO);
    }
}
