package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcRedisRestService;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.inquiry.web.main.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-11
 * @description redis服务
 */
@RestController
@Api(tags = "redis服务")
public class BdcRedisRestController extends BaseController implements BdcRedisRestService {
    @Autowired
    RedisUtils redisUtils;
    /**
     * @param key     value
     * @param value
     * @param timeout
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增字符串值
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("新增字符串值")
    @ApiImplicitParams({@ApiImplicitParam(name = "key", value = "key", paramType = "string"),
            @ApiImplicitParam(name = "value", value = "value", paramType = "string"),
            @ApiImplicitParam(name = "timeout", value = "timeout", paramType = "string")})
    public String addStringValue(@RequestParam(name = "key") String key, @RequestParam(name = "value") String value, @RequestParam(name = "timeout") String timeout) {
        return redisUtils.addStringValue(key,value,Long.valueOf(timeout));
    }

    /**
     * @param key@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取字符串值
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取字符串值")
    @ApiImplicitParams({@ApiImplicitParam(name = "key", value = "key", paramType = "string")})
    public String getStringValue(@RequestParam(name = "key") String key) {
        return redisUtils.getStringValue(key);
    }

    /**
     * @param key   value
     * @param value
     * @return
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 新增字符串值
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("新增字符串值")
    @ApiImplicitParams({@ApiImplicitParam(name = "key", value = "key", paramType = "string"),
            @ApiImplicitParam(name = "value", value = "value", paramType = "string")})
    @Override
    public String addStringValue(@RequestParam(name = "key") String key, @RequestParam(name = "value") String value) {
        return redisUtils.addStringValue(key,value);
    }

    /**
     * 新增Hash类型数据
     * @param key 键
     * @param hashKey Hash键
     * @param value 值
     */
    @Override
    public String addHashValue(@RequestParam(name = "key") String key,
                               @RequestParam(name = "hashKey") String hashKey,
                               @RequestParam(name = "value") String value,
                               @RequestParam(name = "timeout") Long timeout) {
        return redisUtils.addHashValue(key, hashKey, value, timeout);
    }
}
