package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.rest.register.BdcSjyPzRestService;
import cn.gtmap.realestate.register.service.BdcRegisterConfigService;
import cn.gtmap.realestate.register.web.main.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/4/9
 * @description
 */
@RestController
@Api(tags = "不动产默认意见服务接口")
public class BdcSjyPzRestController extends BaseController implements BdcSjyPzRestService {
    @Autowired
    BdcRegisterConfigService bdcRegisterConfigService;

    /**
     * @param formMap
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "运行配置的sql", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "", value = "", dataType = "String", paramType = "query")})
    public Map yxPzSjyPl(@RequestBody Map<String, List<Map>> formMap) {
        if (MapUtils.isEmpty(formMap)) {
            return Maps.newHashMap();
        }
        Map result=Maps.newHashMap();
        int[] i = {0};
        formMap.forEach((key, value) -> {
            List<Map> formList = Lists.newArrayList();
            value.forEach(param -> {
                formList.addAll(bdcRegisterConfigService.executeConfigSql(param));
            });
            result.put(key,formList);
            i[0] += formList.size();
            if (i[0] > 100000){
                throw new AppException("yxPzSjy方法查询结果集过多，可能导致内存溢出！");
            }
        });
        return result;
    }

    /**
     * @param param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "运行配置的sql", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "", value = "", dataType = "String", paramType = "query")})
    public List<Map> yxPzSjy(@RequestBody Map<String, Object> param) {
        if (MapUtils.isEmpty(param)) {
            return Lists.newArrayList();
        }
        return bdcRegisterConfigService.executeConfigSql(param);
    }
}
