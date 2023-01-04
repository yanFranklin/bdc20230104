package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3GyxxDO;
import cn.gtmap.realestate.common.core.service.rest.register.BdcFdcq3GyxxRestService;
import cn.gtmap.realestate.register.core.service.BdcFdcq3GyxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/7/16 09:30
 * @description 不动产登记簿建筑物共有信息服务接口
 */
@RestController
@Api(tags = "不动产登记簿建筑物共有信息服务接口")
public class BdcFdcq3GyxxRestController implements BdcFdcq3GyxxRestService {

    @Autowired
    BdcFdcq3GyxxService bdcFdcq3GyxxService;

    /**
     * @param xmid
     * @return List<BdcFdcq3GyxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建筑物区分所有权业主共有部分登记信息_共有部分
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "查询建筑物共有部分信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目id", required = true, dataType = "string", paramType = "query")})
    @Override
    public List<BdcFdcq3GyxxDO> queryListBdcQlByXmid(@RequestParam(name = "xmid") String xmid) {
        return bdcFdcq3GyxxService.queryListBdcQlByXmid(xmid);
    }

    /**
     * @param bdcdyh 不动产单元号（或地籍号）
     * @return List<BdcFdcq3GyxxDO> 查询现势的共有信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据单元号或者地籍号，查询现势的共有信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据单元号或者地籍号，查询现势的共有信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcdyh", value = "不动产单元号", required = true, dataType = "string", paramType = "query")})
    @Override
    public List<BdcFdcq3GyxxDO> queryListFdcq3Gyxx(String bdcdyh) {
        return bdcFdcq3GyxxService.queryListFdcq3Gyxx(bdcdyh);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return xmid 项目ID
     * @description 获取建筑物区分所有权业主共有部分登记权利人信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取建筑物区分所有权业主共有部分登记权利人信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", required = true, dataType = "string", paramType = "query")})
    @Override
    public String getFdcq3Qlr(@RequestParam("xmid") String xmid) {
        return bdcFdcq3GyxxService.getFdcq3Qlr(xmid);
    }
}
