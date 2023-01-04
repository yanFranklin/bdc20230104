package cn.gtmap.realestate.inquiry.ui.web.rest.hefei;

import cn.gtmap.gtc.workflow.clients.manage.ProcessDefinitionClient;
import cn.gtmap.gtc.workflow.domain.manage.ProcessDefData;
import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.qo.exchange.openapi.BdcOpenApiQO;
import cn.gtmap.realestate.common.core.qo.init.BdcBjbhQO;
import cn.gtmap.realestate.common.core.service.feign.init.hefei.BdcBjbhFeignService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static cn.gtmap.realestate.common.util.PageUtils.addLayUiCode;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/23
 * @description 合肥办件编号
 */
@RestController
@RequestMapping("/rest/v1.0/bjbh")
@Api(tags = "合肥办件编号")
public class BdcBjbhController {

    @Autowired
    private BdcBjbhFeignService bdcBjbhFeignService;

    @Autowired
    private ProcessDefinitionClient processDefinitionClient;

    /**
     * 办件编号台账
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param pageable
     * @param bdcBjbhQO
     * @return
     */
    @ApiOperation(value = "办件编号台账")
    @GetMapping("/page/search")
    @ResponseStatus(code = HttpStatus.OK)
    public Object listBjbhByPage(@LayuiPageable Pageable pageable, BdcBjbhQO bdcBjbhQO) {
        String bdcBjbhQOStr = JSON.toJSONString(bdcBjbhQO);
        return addLayUiCode(bdcBjbhFeignService.listBjbhByPage(pageable,bdcBjbhQOStr));
    }

    /**
     * 取号
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param gzlslid
     * @return
     */
    @ApiOperation(value = "调用政务申报登记反馈接口取号")
    @GetMapping("/takeNumber/{gzlslid}")
    @ResponseStatus(code = HttpStatus.OK)
    public Object takeNumber(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcBjbhFeignService.takeNumber(gzlslid);
    }

    /**
     * 获取所有已经发布的最新版本的流程定义
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @return
     */
    @ApiOperation(value = "获取所有已经发布的最新版本的流程定义")
    @GetMapping("/process/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProcessDefData> getAllProcessDefData() {
        return processDefinitionClient.getAllProcessDefData();
    }
}
