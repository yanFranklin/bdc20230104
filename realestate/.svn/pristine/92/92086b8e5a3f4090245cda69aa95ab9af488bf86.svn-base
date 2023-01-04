package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.service.rest.etl.HlwCreateRestService;
import cn.gtmap.realestate.etl.service.HlwYwxxService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
 * @version 2020/0426,1.0
 * @description
 */
@RestController
@Api(tags = "互联网业务数据读取逻辑")
public class HlwCreateRestController implements HlwCreateRestService {

    @Autowired
    HlwYwxxService hlwYwxxService;
    private static Logger LOGGER = LoggerFactory.getLogger(HlwCreateRestController.class);

    /**
     * @param pageable
     * @param paramJson
     * @return java.lang.Object
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 互联网业务数据分页查询方法
     */
    @Override
    public Page<Map> listHlwYwxxByPageJson(Pageable pageable,
                                           @RequestParam(name = "paramJson", required = false) String paramJson) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(paramJson)) {
            map = JSONObject.parseObject(paramJson, Map.class);
        }
        return hlwYwxxService.listHlwYwxxByPage(pageable, map);
    }

    @Override
    public JSONObject listHlwYwxxByHlwXmid(@RequestParam(name = "hlwxmid") String hlwxmid,@RequestParam(name = "slr", required = false) String slr) {
        return hlwYwxxService.queryHlwJsonByHlwXmid(hlwxmid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "更新互联网审核状态事件", notes = "更新互联网审核状态事件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    })
    public void modifyHlwSlzt(@PathVariable(name="processInsId") String processInsId) {

    }

    @Override
    public Map<String,Object> cjBdcXm(@PathVariable(name= "hlwxmid") String hlwxmid,@RequestParam(name = "slr", required = false) String slr){
        return null;
    }

}
