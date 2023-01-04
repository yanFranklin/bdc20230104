package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.BdcJsQxdmGxDO;
import cn.gtmap.realestate.common.core.domain.BdcZdQjgldmDO;
import cn.gtmap.realestate.common.core.dto.BdcJsPzDTO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcJsPzRestService;
import cn.gtmap.realestate.init.core.service.BdcQjgldmService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/12
 * @description 角色配置REST服务
 */
@RestController
@Api(tags = "角色配置接口")
public class BdcJsPzRestController implements BdcJsPzRestService {

    @Autowired
    private BdcQjgldmService bdcQjgldmService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据角色编码获取权籍管理代码列表", notes = "根据角色编码获取权籍管理代码列表服务", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParam(name = "roleCode", value = "角色编码", required = true, dataType = "String", paramType = "query")
    public List<String> listQjgldmByRoleCode(@PathVariable("roleCode") String roleCode){
        return bdcQjgldmService.listQjgldmByRoleCode(roleCode);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据用户ID获取权籍管理列表", notes = "根据用户ID获取权籍管理列表服务", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    public List<BdcZdQjgldmDO> listDistinctQjgldmByUserId(@PathVariable("userId") String userId){
        return bdcQjgldmService.listDistinctQjgldmByUserId(userId);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据用户ID获取权籍管理列表", notes = "根据用户ID获取权籍管理列表服务", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    public BdcJsPzDTO queryJsPzDTOByUserId(@RequestBody UserDto userDto){
        return bdcQjgldmService.queryJsPzDTOByUserId(userDto);

    }

    /**
     * 获取用户角色关联最多的那个权籍管理代码
     * @param userId 用户ID
     * @return {String} 权籍管理代码
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取用户角色关联最多的那个权籍管理代码", notes = "获取用户角色关联最多的那个权籍管理代码", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    public String listMostQjgldmByUserId(@PathVariable("userId")String userId) {
        return bdcQjgldmService.listMostQjgldmByUserId(userId);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据角色获取区县代码集合", notes = "根据角色获取区县代码集合", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParam(name = "roleCodeList", value = "角色编码集合", required = true, dataType = "String", paramType = "query")
    public List<String> listQxdmByRoleCodeList(@RequestBody List<String> roleCodeList){
        return bdcQjgldmService.listQxdmByRoleCodeList(roleCodeList);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询角色区县代码配置")
    @ApiImplicitParam(name = "json", value = "不动产角色区县代码查询对象", required = false, paramType = "String")
    public Page<BdcJsQxdmGxDO> listBdcJsQxdmGxByPage(Pageable pageable, @RequestParam(name = "json", required = false) String json){
        BdcJsQxdmGxDO bdcJsQxdmGxDO = new BdcJsQxdmGxDO();
        if(StringUtils.isNotBlank(json)){
            bdcJsQxdmGxDO = JSON.parseObject(json, BdcJsQxdmGxDO.class);
        }
        return bdcQjgldmService.listBdcJsQxdmGxByPage(pageable, bdcJsQxdmGxDO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据角色编码删除配置项信息")
    @ApiImplicitParam(name = "json", value = "不动产角色区县代码查询对象", paramType = "String")
    public void deleteJsQxdmPzByRoleCode(@RequestParam(name = "rolecode") String rolecode){
        bdcQjgldmService.deleteJsQxdmPzByRoleCode(rolecode);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存配置项信息")
    @ApiImplicitParam(name = "bdcJsQxdmGxDO", value = "不动产角色区县代码查询对象",paramType = "BdcJsQxdmGxDO")
    public void saveJsQxdmPz(@RequestBody BdcJsQxdmGxDO bdcJsQxdmGxDO){
        bdcQjgldmService.saveJsQxdmPz(bdcJsQxdmGxDO);

    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("刷新角色区县代码配置内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rolecode", value = "角色编码",paramType = "query"),
            @ApiImplicitParam(name = "qxdm", value = "区县代码",paramType = "query")
    })
    public void refreshBdcJsQxdmPz(@RequestParam(name = "rolecode") String rolecode){
        bdcQjgldmService.refreshBdcJsQxdmPz(rolecode);
    }


}
