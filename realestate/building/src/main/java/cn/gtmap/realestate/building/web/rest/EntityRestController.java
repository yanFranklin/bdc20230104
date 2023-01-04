package cn.gtmap.realestate.building.web.rest;

import cn.gtmap.realestate.building.service.BuildEntityService;
import cn.gtmap.realestate.common.core.service.rest.building.EntityRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-18
 * @description 实体公共操作处理服务接口
 */
@RestController
@Api(tags = "实体公共操作处理服务接口")
public class EntityRestController implements EntityRestService{


    @Autowired
    private BuildEntityService buildEntityService;

    /**
     * @param json
     * @param className
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据JSON更新实体
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据JSON更新实体")
    @ApiImplicitParams({@ApiImplicitParam(name = "json", value = "实体JSON数据", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "className", value = "实体类限定名", required = true, dataType = "String", paramType = "query")})
    public void updateEntityByJson(String json, String className) {
        buildEntityService.updateEntityByJson(json,className);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jsonArray
     * @param className
     * @description 根据JSON更新实体
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据JSON批量更新实体")
    @ApiImplicitParams({@ApiImplicitParam(name = "jsonArray", value = "实体列表JSON数据", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "className", value = "实体类限定名", required = true, dataType = "String", paramType = "query")})
    public void batchUpdateEntityByJson(String jsonArray, String className){
        buildEntityService.batchUpdateEntityByJson(jsonArray,className);
    }
}
