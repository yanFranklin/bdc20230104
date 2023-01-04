package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.core.service.BdcSlSjclPzService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclPzDO;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlSjclPzRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/28
 * @description 不动产受理收件材料配置rest服务
 */
@RestController
@Api(tags = "不动产受理收件材料配置服务")
public class BdcSlSjclPzRestController extends BaseController implements BdcSlSjclPzRestService {
    /**
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 不动产受理收件材料配置数据服务
     */
    @Autowired
    private BdcSlSjclPzService bdcSlSjclPzService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据登记小类获取不动产受理收件材料配置信息", notes = "根据登记小类获取不动产受理收件材料配置信息")
    @ApiImplicitParam(name = "djxl", value = "登记小类", required = true, dataType = "String", paramType = "path")
    public List<BdcSlSjclPzDO> listBdcSlSjclPzByDjxl(@PathVariable(value = "djxl") String djxl) {
        return bdcSlSjclPzService.listBdcSlSjclPzByDjxl(djxl);
    }

    /**
     * @param bdcSlSjclPzDO 不动产受理收件材料配置信息
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理收件材料配置信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "保存不动产受理收件材料配置信息", notes = "保存不动产受理收件材料配置信息服务")
    @ApiImplicitParam(name = "bdcSlSjclPzDO", value = "不动产受理收件材料配置信息", required = true, dataType = "BdcSlSjclPzDO", paramType = "query")
    public int saveBdcSlSjclPzDO(@RequestBody BdcSlSjclPzDO bdcSlSjclPzDO) {
        return bdcSlSjclPzService.saveBdcSlSjclPzDO(bdcSlSjclPzDO);

    }

    /**
     * @param bdcSlSjclPzDOList 不动产受理收件材料配置集合
     * @return 删除数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产受理收件材料配置信息
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据配置ID删除不动产受理收件材料配置信息", notes = "根据配置ID删除不动产受理收件材料配置信息服务")
    @ApiImplicitParam(name = "bdcSlSjclPzDOList", value = "不动产受理收件材料配置集合", required = true, dataType = "BdcSlSjclPzDO", paramType = "query")
    public int deleteBdcSlSjclPzDO(@RequestBody List<BdcSlSjclPzDO> bdcSlSjclPzDOList) {
        return bdcSlSjclPzService.deleteBdcSlSjclPzDO(bdcSlSjclPzDOList);
    }

    /**
     * @param pageable
     * @param bdcSlSjclPzJson     收件材料配置
     * @return 收件材料配置分页
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询收件材料配置
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "收件材料配置分页", notes = "收件材料配置分页服务")
    public Page<BdcSlSjclPzDO> listBdcSlSjclPzByPage(Pageable pageable, @RequestParam(name = "bdcSlSjclPzJson", required = false) String bdcSlSjclPzJson) {
        return bdcSlSjclPzService.listBdcSlSjclPzByPage(pageable, bdcSlSjclPzJson);
    }

    /**
     * @param bdcSlSjclPzDO@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取收件材料配置最大序号
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取收件材料配置最大序号", notes = "获取收件材料配置最大序号")
    public Integer querySjclPzMaxSxh(@RequestBody BdcSlSjclPzDO bdcSlSjclPzDO) {
        return bdcSlSjclPzService.querySjclPzMaxSxh(bdcSlSjclPzDO);
    }
}

