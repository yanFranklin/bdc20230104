package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.service.rest.job.BdcJobGroupRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.config.service.BdcJobGroupService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0, 2022/01/01 15:12
 * @description jobGroup服务接口
 */

@RestController
@Api(tags = "jobGroup服务接口")
public class BdcJobGroupRestController implements BdcJobGroupRestService {

    @Autowired
    private BdcJobGroupService bdcJobGroupService;

    @Autowired
    EntityMapper  entityMapper;
    /**
     * 分页获取执行器数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param jobParamJson jobParamJson
     * @return JobGroup
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询子规则列表服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "zgzParamJson", value = "子规则查询参数Json", dataType = "BdcGzZgzDO", required = false, paramType = "query")})
    public Page<BdcJobGroupDO> listBdcJobGroupPage(Pageable pageable,
                                                   @RequestParam(name = "jobParamJson",required = false) String jobParamJson) {
        return bdcJobGroupService.listBdcJobGroupPage(pageable, JSON.parseObject(jobParamJson, BdcJobGroupDO.class));
    }

    /**
     * 保存执行器信息 没有记录则新增
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO
     * @return jobGroup
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "保存执行器信息")
    public BdcJobGroupDO saveJobGroup(@RequestBody BdcJobGroupDO bdcJobGroupDO) {
        return bdcJobGroupService.saveJobGroup(bdcJobGroupDO);
    }

}
