package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtLscsDO;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyXtLscsRestService;
import cn.gtmap.realestate.natural.service.ZrzyXtLscsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2021/01/14
 * @description 临时参数表操作服务
 */
@RestController
@Api(tags = "不动产证书证明查询服务接口")
public class ZrzyXtLscsRestController implements ZrzyXtLscsRestService {
    @Autowired
    private ZrzyXtLscsService zrzyXtLscsService;


    /**
     * 批量添加临时参数数据
     *
     * @param lscsDOList 参数数据
     * @return {int} 新增记录数量
     * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量添加临时参数数据")
    @ApiImplicitParam(name = "lscsDOList", value = "参数数据", required = true, paramType = "body")
    public int addValues(@RequestBody List<ZrzyXtLscsDO> lscsDOList) {
        return zrzyXtLscsService.addValues(lscsDOList);
    }

    /**
     * 批量删除临时参数数据
     *
     * @param lscsDOList 参数数据
     * @return {int} 删除记录数量
     * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("批量删除临时参数数据")
    @ApiImplicitParam(name = "lscsDOList", value = "参数数据", required = true, paramType = "body")
    public int deleteRecords(@RequestBody List<ZrzyXtLscsDO> lscsDOList) {
        return zrzyXtLscsService.deleteRecords(lscsDOList);
    }
}
