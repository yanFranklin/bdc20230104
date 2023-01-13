package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.service.rest.job.BdcJobGroupRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.config.core.mapper.BdcJobGroupMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobInfoMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobRegistryMapper;
import cn.gtmap.realestate.config.service.BdcJobGroupService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    public BdcJobInfoMapper bdcJobInfoMapper;
    @Resource
    public BdcJobGroupMapper bdcJobGroupMapper;
    @Resource
    private BdcJobRegistryMapper bdcJobRegistryMapper;

    /**
     * 分页获取执行器数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param jobParamJson jobParamJson
     * @return JobGroup
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "jobGroupParamJson", value = "查询参数Json", dataType = "String", required = false, paramType = "query")})
    public Page<BdcJobGroupDO> listBdcJobGroupPage(Pageable pageable,
                                                    @RequestParam(name = "jobGroupParamJson",required = false) String jobGroupParamJson) {
        return bdcJobGroupService.listBdcJobGroupPage(pageable, JSON.parseObject(jobGroupParamJson, BdcJobGroupDO.class));
    }

    /**
     * 查询所有执行器列表
     *
     * @return Object Object
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public List<BdcJobGroupDTO> listBdcJobGroupAll() {
        return bdcJobGroupService.listBdcJobGroupAll();
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
    public ReturnT<String> saveJobGroup(@RequestBody BdcJobGroupDO bdcJobGroupDO) {
        return bdcJobGroupService.saveJobGroup(bdcJobGroupDO);
    }

    /**
     * 更新执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO
     * @return ReturnT
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "更新执行器信息")
    public ReturnT<String> updateJobGroup(@RequestBody BdcJobGroupDO bdcJobGroupDO) {
        return bdcJobGroupService.updateJobGroup(bdcJobGroupDO);
    }

    /**
     * 删除执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除执行器信息")
    public ReturnT<String> removeJobGroup(@RequestParam(name = "id", required = false) Integer id) {
        return bdcJobGroupService.removeJobGroup(id);
    }


    /**
     * 根据执行器id查询执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "删除执行器信息")
    public ReturnT<BdcJobGroupDTO> loadJobGroupById(@RequestParam(name = "id", required = false) Integer id) {
        return bdcJobGroupService.loadJobGroupById(id);
    }


    /**
     * @param request
     * @param start
     * @param length
     * @param appname
     * @param title
     * @return 单表查询 注册的执行器信息
     */
    @RequestMapping("/pageList")
    @ResponseBody
    public Map<String, Object> pageList(HttpServletRequest request,
                                        @RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        String appname, String title) {

        // page query
        List<BdcJobGroupDTO> list = bdcJobGroupMapper.pageList(start, length, appname, title);
        int list_count = bdcJobGroupMapper.pageListCount(start, length, appname, title);

        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", list_count);		// 总记录数
        maps.put("recordsFiltered", list_count);	// 过滤后的总记录数
        maps.put("data", list);  					// 分页列表
        return maps;
    }

}
