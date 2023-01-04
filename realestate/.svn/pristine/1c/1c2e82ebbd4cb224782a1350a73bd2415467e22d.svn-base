package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcBhService;
import cn.gtmap.realestate.accept.service.BdcLshService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcBhRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/4/1
 * @description 不动产编号rest服务
 */
@RestController
@Api(tags = "不动产编号rest服务")
public class BdcBhRestController implements BdcBhRestService {
    @Autowired
    BdcBhService bdcBhService;
    @Autowired
    BdcLshService bdcLshService;

    /**
     * @param ywlx 业务类型
     * @return 自增时间范围 包括YEAR  MONTH  DAY
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据自增时间范围、自增业务类型生成编号
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据自增时间范围、自增业务类型生成编号", notes = "根据自增时间范围、自增业务类型生成编号服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ywlx", value = "业务类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zzsjfw", value = "自增时间范围", required = true, dataType = "String", paramType = "query")
    })
    public String queryBh(@RequestParam(value = "ywlx") String ywlx, @RequestParam(value = "zzsjfw") String zzsjfw) {
        return bdcBhService.queryBh(ywlx, zzsjfw);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param ywlx 业务类型
     * @return zzsjfw 自增时间范围 包括YEAR  MONTH  DAY
     * @return {Integer} 流水号
     * @description 获取指定业务类型的流水号（例如 1、2、3）
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取指定业务类型的流水号", notes = "获取指定业务类型的流水号")
    @ApiImplicitParams({@ApiImplicitParam(name = "ywlx", value = "业务类型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zzsjfw", value = "自增时间范围", required = true, dataType = "String", paramType = "query")})
    public Integer queryLsh(@RequestParam(value = "ywlx") String ywlx, @RequestParam(value = "zzsjfw") String zzsjfw) {
        return bdcLshService.queryLsh(ywlx, zzsjfw);
    }

    /**
     * @param ywlx 业务类型
     * @param zzsjfw 自增时间范围
     * @param zzxlh 自增序列号
     * @param prebh 编号前缀
     * @return 编号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据自增时间范围、自增业务类型生成编号通用方法
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据自增时间范围、自增业务类型生成编号通用方法", notes = "根据自增时间范围、自增业务类型生成编号通用方法服务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ywlx", value = "业务类型", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zzsjfw", value = "自增时间范围", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zzxlh", value = "自增序列号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "prebh", value = "编号前缀", required = true, dataType = "String", paramType = "query")
    })
    public String queryCommonBh(@PathVariable(name = "ywlx")String ywlx, @PathVariable(name = "zzsjfw") String zzsjfw, @PathVariable(name = "zzxlh")Integer zzxlh, @RequestParam(value = "prebh") String prebh){
        return bdcBhService.queryCommonBh(ywlx, zzsjfw,zzxlh,prebh);
    }
}
