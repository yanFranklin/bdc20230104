package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXtQlqtzkFjPzDO;
import cn.gtmap.realestate.common.core.qo.config.BdcXtQlqtzkFjPzQO;
import cn.gtmap.realestate.common.core.service.rest.config.BdcXtQlqtzkFjPzRestService;
import cn.gtmap.realestate.config.service.BdcXtQlqtzkFjPzService;
import com.alibaba.fastjson.JSON;
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
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/2/18
 * @description 不动产系统权利其他状况、附记配置服务
 */
@RestController
@Api(tags = "不动产系统权利其他状况、附记配置服务接口")
public class BdcXtQlqtzkFjPzController implements BdcXtQlqtzkFjPzRestService {
    @Autowired
    BdcXtQlqtzkFjPzService bdcXtQlqtzkFjPzService;

    /**
     * @param pageable
     * @param qlqtzkFjParamJson
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取权利其他状况附记配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询权利其他状况附记配置列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "qlqtzkFjParamJson", value = "权利其他状况附记查询参数JSON", required = false, paramType = "query")})
    public Page<BdcXtQlqtzkFjPzDO> listBdcXtQlqtzkFjPz(Pageable pageable, @RequestParam String qlqtzkFjParamJson) {
        return bdcXtQlqtzkFjPzService.listBdcXtQlqtzkFjPzByPage(pageable, JSON.parseObject(qlqtzkFjParamJson, BdcXtQlqtzkFjPzQO.class));
    }

    /**
     * @param bdcXtQlqtzkFjPzDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存权利其他状况附记配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存系统权利其他状况附记配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXtQlqtzkFjPzDO", value = "不动产系统权利其他状况附记配置", required = true, paramType = "body")})
    public int saveBdcXtQlqtzkFjPz(@RequestBody BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO) {
        return bdcXtQlqtzkFjPzService.saveBdcXtQlqtzkFjPz(bdcXtQlqtzkFjPzDO);
    }

    /**
     * @param bdcXtQlqtzkFjPzDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改权利其他状况附记配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("修改权利其他状况附记配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXtQlqtzkFjPzDO", value = "不动产系统权利其他状况附记配置", required = true, paramType = "body")})
    public int updateBdcXtQlqtzkFjPz(@RequestBody BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO) {
        return bdcXtQlqtzkFjPzService.updateBdcXtQlqtzkFjPz(bdcXtQlqtzkFjPzDO);
    }

    /**
     * @param bdcXtQlqtzkFjPzDOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除权利其他状况附记配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("删除权利其他状况附记配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXtQlqtzkFjPzDOList", value = "权利其他状况附记配置集合", required = true, paramType = "body")})
    public int deleteBdcXtQlqtzkFjPz(@RequestBody List<BdcXtQlqtzkFjPzDO> bdcXtQlqtzkFjPzDOList) {
        return bdcXtQlqtzkFjPzService.deleteBdcXtQlqtzkFjPz(bdcXtQlqtzkFjPzDOList);
    }

    /**
     * @param sqlList
     * @param params
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证sql
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("验证权利其他状况附记配置sql")
    @ApiImplicitParams({@ApiImplicitParam(name = "sqls", value = "权利其他状况附记配置sql集合", required = true, paramType = "body")})
    public boolean checkBdcXtQlqtzkFjPz(@RequestParam List<String> sqlList, @RequestBody Map params) {
        return bdcXtQlqtzkFjPzService.checkBdcXtQlqtzkFjPz(sqlList,params);
    }

    /**
     * @param bdcXtQlqtzkFjPzDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取权利其他状况、附记 配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取权利其他状况、附记配置")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXtQlqtzkFjPzDO", value = "不动产系统权利其他状况附记配置", required = true, paramType = "body")})
    public List<BdcXtQlqtzkFjPzDO> listQlqtzkFjpz(@RequestBody BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO) {
        return bdcXtQlqtzkFjPzService.listQlqtzkFjpz(bdcXtQlqtzkFjPzDO);
    }
}
