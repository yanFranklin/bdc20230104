package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmFbDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQjgldmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmFbQO;
import cn.gtmap.realestate.common.core.service.rest.init.BdcXmFbRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.init.core.service.BdcXmFbService;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/6/11.
 * @description
 */
@RestController
@Api(tags = "业务项目附表信息服务接口")
public class BdcXmFbRestController extends BaseController implements BdcXmFbRestService {
    @Autowired
    BdcXmFbService bdcXmFbService;
    @Autowired
    EntityMapper entityMapper;
    /**
     * 批量更新不动产项目附表
     * @param bdcDjxxUpdateQO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "批量更新项目附表")
    @ApiImplicitParam(name = "bdcDjxxUpdateQO", value = "更新数量", dataType = "BdcDjxxUpdateQO", paramType = "query")
    @ResponseStatus(code = HttpStatus.OK)
    @Override
    public int updateBatchBdcxmFb(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO) throws Exception {
        return bdcXmFbService.updateBatchBdcXmFb(bdcDjxxUpdateQO);
    }
    /**
     * 通过传入参数返回不动产项目信息
     *
     * @param bdcXmFbQO
     * @return
     */
    @ApiOperation(value = "查询项目附表信息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcXmFbDO> listBdcXmFb(@RequestBody BdcXmFbQO bdcXmFbQO) {
       return bdcXmFbService.listBdcXmFb(bdcXmFbQO);
    }


    /**
     * 通过不动产单元查询现势的sfszfgf状态
     * @param bdcdyh
     * @return
     */
    @ApiOperation(value = "通过不动产单元查询现势的sfszfgf状态")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcXmDO> listBdcFgfXmByBdcdyh(@PathVariable(name = "bdcdyh") String bdcdyh) {
        return bdcXmFbService.listBdcFgfXmByBdcdyh(bdcdyh);
    }

    /**
     * 根据业务信息查询关联权籍管理代码
     * @param bdcQjgldmQO 登记业务数据（例如单元号、证号等）
     * @return 权籍管理代码
     */
    @Override
    @ApiOperation(value = "根据业务信息查询关联权籍管理代码")
    @ApiImplicitParam(name = "bdcQjgldmQO", value = "登记业务数据", required = true, paramType = "body")
    public String queryQjgldm(@RequestBody BdcQjgldmQO bdcQjgldmQO) {
        return bdcXmFbService.queryQjgldm(bdcQjgldmQO);
    }

    @Override
    @ApiOperation(value = "更新证书认领状态")
    public int updateZsrlzt(@RequestBody List<String> gzlslids,@PathVariable(name = "zsrlzt") Integer zsrlzt){
        return bdcXmFbService.updateZsrlzt(gzlslids, zsrlzt);

    }

    @Override
    @ApiOperation(value = "更新选中数据档案归属地")
    public int updateBatchBdcxmFbByXmid(@RequestBody List<BdcXmFbDO> bdcXmFbDOList) {
        for (BdcXmFbDO bdcXmFbDO : bdcXmFbDOList){
            entityMapper.updateByPrimaryKeySelective(bdcXmFbDO);
        }
        return 0;
    }

    @Override
    @ApiOperation(value = "根据xmid获取项目附表中dagsd")
    public String queryDagsd(@RequestBody BdcXmFbQO bdcXmFbQO) {
        List<BdcXmFbDO> bdcXmFbDOList = bdcXmFbService.listBdcXmFb(bdcXmFbQO);
        if (CollectionUtils.isNotEmpty(bdcXmFbDOList)){
            return bdcXmFbDOList.get(0).getDagsd();
        }
        return null;
    }

}
