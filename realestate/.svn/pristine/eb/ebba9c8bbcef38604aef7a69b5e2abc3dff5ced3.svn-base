package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcTsywPzDO;
import cn.gtmap.realestate.common.core.domain.BdcTsywZdyzdxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcTsywPzDTO;
import cn.gtmap.realestate.common.core.qo.config.BdcTsywPzQO;
import cn.gtmap.realestate.common.core.service.rest.config.BdcTsywPzRestService;
import cn.gtmap.realestate.common.util.BdcTsywPzUtils;
import cn.gtmap.realestate.config.service.BdcTsywPzService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/3/5
 * @description 不动产YML配置服务REST接口
 */
@RestController
@Api(tags = "不动产YML配置服务REST接口")
public class BdcTsywPzRestController implements BdcTsywPzRestService {
    @Autowired
    private BdcTsywPzService bdcTsywPzService;
    @Autowired
    BdcTsywPzUtils bdcTsywPzUtils;


    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键获取YML配置", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")) )
    @ApiImplicitParam(name = "id", value = "YML配置主键", required = true, paramType = "String")
    public BdcTsywPzDO queryBdcTsywPzById(@RequestParam(name = "pzid") String pzid){
        return bdcTsywPzService.queryBdcTsywPzById(pzid);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询YML配置", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParam(name = "bdcTsywPzQO", value = "不动产YML配置查询QO", required = true, paramType = "BdcTsywPzQO")
    public List<BdcTsywPzDO> listBdcTsywPz(@RequestBody BdcTsywPzQO bdcTsywPzQO){
        return bdcTsywPzService.listBdcTsywPz(bdcTsywPzQO);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "分页查询YML配置", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParam(name = "bdcTsywPzQOStr", value = "不动产YML配置查询QO", required = true, paramType = "String")
    public Page<BdcTsywPzDO> listBdcTsywPzByPage(Pageable pageable, @RequestParam(name = "bdcTsywPzQOStr", required = false) String bdcTsywPzQOStr){
        BdcTsywPzQO bdcTsywPzQO =new BdcTsywPzQO();
        if(StringUtils.isNotBlank(bdcTsywPzQOStr)){
            bdcTsywPzQO =JSONObject.parseObject(bdcTsywPzQOStr,BdcTsywPzQO.class);
        }

        return bdcTsywPzService.listBdcTsywPzByPage(pageable, bdcTsywPzQO);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不分页查询YML配置", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParam(name = "bdcTsywPzQOStr", value = "不动产YML配置查询QO", required = true, paramType = "String")
    public List<BdcTsywPzDO> bdcTsywPzList(@RequestParam(name = "bdcTsywPzQOStr", required = false) String bdcTsywPzQOStr){
        BdcTsywPzQO bdcTsywPzQO =new BdcTsywPzQO();
        if(StringUtils.isNotBlank(bdcTsywPzQOStr)){
            bdcTsywPzQO =JSONObject.parseObject(bdcTsywPzQOStr,BdcTsywPzQO.class);
        }

        return bdcTsywPzService.bdcTsywPzList(bdcTsywPzQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据主键更新YM配置信息", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParam(name = "bdcTsywPzDO", value = "更新YML配置信息", required = true, paramType = "body")
    public int updateBdcTsywPz(@RequestBody BdcTsywPzDO bdcTsywPzDO){
        return bdcTsywPzService.updateBdcTsywPz(bdcTsywPzDO);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "新增YML配置信息")
    @ApiImplicitParam(name = "bdcTsywPzDO", value = "YML配置信息", required = true, paramType = "body")
    public BdcTsywPzDO insertBdcTsywPz(@RequestBody BdcTsywPzDO bdcTsywPzDO){
        return bdcTsywPzService.insertBdcTsywPz(bdcTsywPzDO);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "新增特殊业务配置信息")
    @ApiImplicitParam(name = "bdcTsywPzDOList", value = "特殊业务配置信息", required = true, paramType = "body")
    public void insertBdcTsywPzPl(@RequestBody List<BdcTsywPzDO> bdcTsywPzDOList){
        bdcTsywPzService.insertBdcTsywPzPl(bdcTsywPzDOList);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询配置自定义字典项", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParam(name = "bdcTsywZdyzdxDO", value = "查询对象", required = true, paramType = "body")
    public List<BdcTsywZdyzdxDO> listZdyzdx(@RequestBody BdcTsywZdyzdxDO bdcTsywZdyzdxDO){
        return bdcTsywPzService.listZdyzdx(bdcTsywZdyzdxDO);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "初始化各模块配置内容", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    public void initYmlData() {
        bdcTsywPzService.initYmlData();
    }

    @Override
    @ApiOperation(value = "通过pzid获取特殊业务配置DTO", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "pzid", value = "配置ID", required = true, dataType = "String", paramType = "path")})
    @ResponseStatus(HttpStatus.OK)
    public BdcTsywPzDTO queryBdcTsywPzDTO(@PathVariable("pzid") String pzid){
        return bdcTsywPzService.queryBdcTsywPzDTO(pzid);

    }

    @Override
    @ApiOperation(value = "保存特殊业务配置DTO")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcTsywPzDTO", value = "特殊业务配置DTO", required = true, dataType = "body", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public String saveBdcTsywPzDTO(@RequestBody BdcTsywPzDTO bdcTsywPzDTO){
        return bdcTsywPzService.saveBdcTsywPzDTO(bdcTsywPzDTO);

    }

    @Override
    @ApiOperation(value = "根据字典项标识获取当前配置项以外的配置项", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcTsywPzDO", value = "特殊业务配置", required = true, dataType = "body", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    public List<BdcTsywPzDO> listOtherTsywPzByZdxbs(@RequestBody BdcTsywPzDO bdcTsywPzDO){
        return bdcTsywPzService.listOtherTsywPzByZdxbs(bdcTsywPzDO);

    }

    @Override
    @ApiOperation(value = "批量更新特殊业务配置功能模块")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcTsywPzDO", value = "特殊业务配置", required = true, dataType = "body", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    public void batchModifyTsywpzGnmk(@RequestBody List<BdcTsywPzDO> bdcTsywPzDOList) {
        this.bdcTsywPzService.batchModifyTsywpzGnmk(bdcTsywPzDOList);
    }

    @Override
    @ApiOperation(value = "批量删除特殊业务配置信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "string", value = "特殊业务配置id", required = true, dataType = "body", paramType = "body")})
    @ResponseStatus(HttpStatus.OK)
    public void batchDeleteBdcTsywPz(@RequestBody List<String> ids) {
        this.bdcTsywPzService.batchDeleteTsywpz(ids);
    }

    @Override
    @ApiOperation(value = "同步共享接口目录字典项")
    @ResponseStatus(HttpStatus.OK)
    public void syncGxjkmlZdyzdx(){
        bdcTsywPzService.syncGxjkmlZdyzdx();

    }

}
