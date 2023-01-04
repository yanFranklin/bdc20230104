package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.EntityService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcEntityRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/29
 * @description  实体公共操作处理服务
 */
@RestController
@Api(tags = "实体公共操作处理服务接口")
public class BdcEntityRestController implements BdcEntityRestService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcEntityRestController.class.getName());

    /**
     * 实体操作
     */
    @Autowired
    private EntityService entityService;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param json  实体数据JSON
     * @param className 对应实体类限定名
     * @return {int} 更新记录数量
     * @description
     *  <p>
     *      更新实体部分属性数据值 <br>
     *      1、这里需要注意json数据采用@RequestBody方式提交，不用@RequestParam，因为URL后缀参数长度有限制 <br>
     *      2、因为FeignClient不能有多个@RequestBody，所以class改为传名称，再逻辑处理一下获取类限定名 <br>
     *  </p>
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新实体部分属性数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "json", value = "实体JSON数据", required = true, dataType = "String", paramType = "body"),
                        @ApiImplicitParam(name = "className", value = "实体类限定名", required = true, dataType = "String", paramType = "query")})
    @Override
    public int updateByJsonEntity(@RequestBody String json, @RequestParam("className")String className){
        if(StringUtils.isBlank(json) || StringUtils.isBlank(className)){
            throw new NullPointerException("空参数异常！");
        }

        try {
            return entityService.updateByJsonEntity(json, Class.forName(className));
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("更新实体数据异常！");
        }
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("新增实体属性数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "json", value = "实体JSON数据", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "className", value = "实体类限定名", required = true, dataType = "String", paramType = "query")})
    @Override
    public int insertByJsonEntity(@RequestBody String json, @RequestParam("className")String className){
        if(StringUtils.isBlank(json) || StringUtils.isBlank(className)){
            throw new NullPointerException("空参数异常！");
        }

        try {
            return entityService.insertJsonEntity(json, Class.forName(className));
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new AppException("更新实体数据异常！");
        }
    }
}
