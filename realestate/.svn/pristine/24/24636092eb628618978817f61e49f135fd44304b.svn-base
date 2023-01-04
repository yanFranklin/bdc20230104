package cn.gtmap.realestate.engine.web.rest;

import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.annotations.SendMsg;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzQO;
import cn.gtmap.realestate.common.core.dto.engine.*;
import cn.gtmap.realestate.common.core.service.rest.engine.BdcGzZgzRestService;
import cn.gtmap.realestate.engine.service.BdcGzDmService;
import cn.gtmap.realestate.engine.service.BdcGzZgzCheckService;
import cn.gtmap.realestate.engine.core.service.BdcGzZgzService;
import cn.gtmap.realestate.engine.util.Constants;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/3/5 14:52
 * @description 规则系统子规则服务接口
 */

@RestController
@Api(tags = "规则系统子规则服务接口")
public class BdcGzZgzRestController implements BdcGzZgzRestService {
    @Autowired
    BdcGzZgzService bdcGzZgzService;
    @Autowired
    BdcGzZgzCheckService bdcGzZgzCheckService;
    @Autowired
    BdcGzDmService bdcGzDmService;


    /**
     * 获取子规则数据列表
     * @param pageable     pageable
     * @param zgzParamJson zgzParamJson
     * @return BdcGzZgzDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询子规则列表服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "zgzParamJson", value = "子规则查询参数Json", dataType = "BdcGzZgzDO", required = false, paramType = "query")})
    public Page<BdcGzZgzDO> listBdcGzZgzPage(Pageable pageable, String zgzParamJson) {
        return bdcGzZgzService.listBdcGzZgzPage(pageable, JSON.parseObject(zgzParamJson, BdcGzZgzQO.class));
    }

    /**
     * 删除子规则
     * @param bdcGzZgzDOList bdcGzZgzDOList
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("删除子规则服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzZgzDOList", value = "规则集合", dataType = "BdcGzZgzDO", required = true, paramType = "body")})
    @LogNote(value = "删除子规则信息", action = Constants.LOG_ACTION_GZCZ)
    @SendMsg(description = "删除子规则信息", action = Constants.ZGZ_ACTION_DELETE, gzlx = Constants.GZLX_ZGZ,excutePoint = Constants.EXCUTEPOINT_DOAFTER)
    public void deleteBdcGzZgz(@RequestBody List<BdcGzZgzDO> bdcGzZgzDOList) {
        bdcGzZgzService.deleteBdcGzZgz(bdcGzZgzDOList);
    }

    /**
     * @param zhid
     * @return bdcGzZgzDTOList
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过zhid获取子规则DTO列表
     */
    @Override
    @ApiOperation(value = "查询子规则DTO列表")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "zhid", value = "组合id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcGzZgzDTO> queryBdcGzZgzDTOList(@PathVariable("zhid") String zhid) {
        return bdcGzZgzService.queryBdcGzZgzDTOListByZhid(zhid);
    }

    /**
     * @param gzid
     * @return bdcGzZgzDTO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过gzid获取子规则DTO
     */
    @Override
    @ApiOperation(value = "查询子规则DTO")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "gzid", value = "规则id", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    public BdcGzZgzDTO queryBdcGzZgzDTO(@PathVariable("gzid") String gzid) {
        return bdcGzZgzService.queryBdcGzZgzDTOByGzid(gzid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO 子规则DTO
     * @return {String} 子规则主键ID
     * @description 保存子规则及关联的数据流、表达式等信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("保存子规则信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzZgzDTO", value = "子规则信息实体", required = true, paramType = "body")})
    @LogNote(value = "保存子规则信息", action = Constants.LOG_ACTION_GZCZ)
    @SendMsg(description = "保存子规则信息", action = Constants.ZGZ_ACTION_SAVE, gzlx = Constants.GZLX_ZGZ,excutePoint = Constants.EXCUTEPOINT_DOAFTER)
    public String saveBdcGzZgz(@RequestBody BdcGzZgzDTO bdcGzZgzDTO) {
        return bdcGzZgzService.saveBdcGzZgz(bdcGzZgzDTO);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzsDTO 子规则DTO
     * @return {List} 规则提示信息
     * @description 获取多个子规则校验结果信息（如果校验通过无需提示则返回空）
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取多个子规则校验结果信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcGzZgzsDTO", value = "子规则集合", required = true, paramType = "body")})
    public List<BdcGzZgzTsxxDTO> getBdcZgzCheckResult(@RequestBody BdcGzZgzsDTO bdcGzZgzsDTO) {
        if(null == bdcGzZgzsDTO || CollectionUtils.isEmpty(bdcGzZgzsDTO.getBdcGzZgzDOList())){
            throw new NullPointerException("子规则系统V2.0：获取多个子规则校验结果信息，参数为空！");
        }

        return bdcGzZgzCheckService.getZgzCheckResult(bdcGzZgzsDTO.getBdcGzZgzDOList());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzYzDTO 验证参数信息
     * @description 获取子规则在不同参数条件下执行得到的数据流结果
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取子规则在不同参数条件下执行得到的数据流结果")
    @ApiImplicitParam(name = "bdcGzYzDTO", value = "验证参数信息", required = true, paramType = "body")
    public List<BdcGzZgzTsxxDTO> getZgzSjlResult(@RequestBody BdcGzYzDTO bdcGzYzDTO) {
        return bdcGzZgzCheckService.getZgzSjlResult(bdcGzYzDTO.getBdcGzZgzDTOList(), bdcGzYzDTO.getBdcGzYzQO());
    }

    /**
     * @param gzid
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: 子规则ID
     * @return: ｛String｝拷贝后的子规则ID
     * @description 拷贝当前子规则ID的信息，并创建一个新的子规则ID
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("复制子规则信息")
    @ApiImplicitParam(name = "gzid", value = "子规则ID", required = true, paramType = "path")
    @LogNote(value = "复制子规则信息", action = Constants.LOG_ACTION_GZCZ)
    public String copyBdcGzZgz(@PathVariable("gzid") String gzid) {
        if(StringUtils.isBlank(gzid)){
            throw new NullPointerException("子规则系统V2.0：子规则信息为空！");
        }
        return this.bdcGzZgzService.copyBdcGzZgz(gzid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzCodeDTO  校验代码信息
     * @description  校验子规则动态代码
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("校验子规则动态代码")
    @ApiImplicitParam(name = "bdcGzCodeDTO", value = "校验代码信息", required = true, paramType = "body")
    public String codeCheck(@RequestBody BdcGzCodeDTO bdcGzCodeDTO) {
        return bdcGzDmService.checkCode(bdcGzCodeDTO);
    }
}
