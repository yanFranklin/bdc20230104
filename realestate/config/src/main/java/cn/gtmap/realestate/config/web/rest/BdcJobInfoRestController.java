package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobUserDO;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.common.core.service.rest.job.BdcJobInfoRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobGroupMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobInfoMapper;
import cn.gtmap.realestate.config.core.mapper.BdcJobRegistryMapper;
import cn.gtmap.realestate.config.core.service.LoginService;
import cn.gtmap.realestate.config.job.util.I18nUtil;
import cn.gtmap.realestate.config.service.BdcJobGroupService;
import cn.gtmap.realestate.config.service.BdcJobInfoService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0, 2022/01/01 15:12
 * @description jobInfo服务接口
 */

@RestController
@Api(tags = "jobInfo服务接口")
public class BdcJobInfoRestController implements BdcJobInfoRestService {

    @Autowired
    private BdcJobInfoService bdcJobInfoService;

    @Autowired
    EntityMapper  entityMapper;

    @Resource
    public BdcJobInfoMapper bdcJobInfoMapper;
    @Resource
    public BdcJobGroupMapper bdcJobGroupMapper;
    @Resource
    private BdcJobRegistryMapper bdcJobRegistryMapper;


    /**
     * 分页获取任务数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param jobInfoParamJson jobInfoParamJson
     * @return JobInfo
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "jobInfoParamJson", value = "查询参数Json", dataType = "String", required = false, paramType = "query")})
    public Page<BdcJobInfoDO> listBdcJobInfoPage(Pageable pageable,
                                       @RequestParam(name = "jobInfoParamJson",required = false) String jobInfoParamJson) {
        return bdcJobInfoService.listBdcJobInfoPage(pageable, JSON.parseObject(jobInfoParamJson, BdcJobInfoDO.class));
    }

    /**
     * 添加任务
     *
     * @param bdcJobInfoDO
     * @return Object Object
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> addJobnfo(@RequestBody BdcJobInfoDO bdcJobInfoDO) {
        return bdcJobInfoService.addJobnfo(bdcJobInfoDO);
    }

    /**
     * 保存任务信息
     *
     * @param bdcJobInfoDO
     * @return bdcJobInfoDO
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> saveJobnfo(@RequestBody BdcJobInfoDO bdcJobInfoDO) {
        return bdcJobInfoService.saveJobnfo(bdcJobInfoDO);
    }

    /**
     * 更新任务信息
     *
     * @param bdcJobInfoDO jobGroup
     * @return jobGroup jobGroup
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> updateJobnfo(@RequestBody BdcJobInfoDO bdcJobInfoDO) {
        return bdcJobInfoService.updateJobnfo(bdcJobInfoDO);
    }

    /**
     * 删除 任务
     *
     * @param id
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> removeJobnfo(@RequestParam(value = "id",required = false) Integer id) {
        return bdcJobInfoService.removeJobnfo(id);
    }

    /**
     * 终止 任务
     *
     * @param id
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> pauseJobnfo(@RequestParam(value = "id",required = false) Integer id) {
        return bdcJobInfoService.pauseJobnfo(id);
    }

    /**
     * 启动 任务
     *
     * @param id
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> startJobInfo(@RequestParam(value = "id",required = false) Integer id) {
        return bdcJobInfoService.startJobInfo(id);
    }

    /**
     * 执行一次 任务
     *
     * @param id
     * @param executorParam
     * @param addresslist
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<String> triggerJobInfo(@RequestParam(value = "id",required = false) Integer id, @RequestParam(value = "executorParam",required = false) String executorParam, @RequestParam(value = "addresslist",required = false) String addresslist) {
        return bdcJobInfoService.triggerJobInfo(id, executorParam, addresslist);
    }

    /**
     * 任务下次执行时间
     *
     * @param scheduleType
     * @param scheduleConf
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<List<String>> nextTriggerTime(@RequestParam(value = "scheduleType",required = false) String scheduleType, @RequestParam(value = "scheduleConf",required = false) String scheduleConf) {
        return bdcJobInfoService.nextTriggerTime(scheduleType, scheduleConf);
    }

    /**
     * 根据任务id查询任务信息
     *
     * @param id
     * @return ReturnT
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     */
    @Override
    public ReturnT<BdcJobInfoDO> loadJobInfoById(@RequestParam(value = "id", required = false) Integer id) {
        return bdcJobInfoService.loadJobInfoById(id);
    }

    /**
     * 筛选出所有的该用户拥有权限的定时任务List
     * @param request
     * @param bdcJobGroupDTOList_all
     * @return
     */
    public static List<BdcJobGroupDTO> filterJobGroupByRole(HttpServletRequest request, List<BdcJobGroupDTO> bdcJobGroupDTOList_all){
        List<BdcJobGroupDTO> bdcJobGroupDTOList = new ArrayList<>();
        if (bdcJobGroupDTOList_all !=null && bdcJobGroupDTOList_all.size()>0) {
            BdcJobUserDO loginUser = (BdcJobUserDO) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
            if (loginUser.getRole() == 1) {
                bdcJobGroupDTOList = bdcJobGroupDTOList_all;
            } else {
                List<String> groupIdStrs = new ArrayList<>();
                //执行器按照","分割， 获取执行器list
                if (loginUser.getPermission()!=null && loginUser.getPermission().trim().length()>0) {
                    groupIdStrs = Arrays.asList(loginUser.getPermission().trim().split(","));
                }
                for (BdcJobGroupDTO groupItem: bdcJobGroupDTOList_all) {
                    if (groupIdStrs.contains(String.valueOf(groupItem.getId()))) {
                        bdcJobGroupDTOList.add(groupItem);
                    }
                }
            }
        }
        return bdcJobGroupDTOList;
    }

    /**
     *验证用户是否对该jobGroup有权限
     * @param request
     * @param jobGroup
     */
    public static void validPermission(HttpServletRequest request, int jobGroup) {
        BdcJobUserDO loginUser = (BdcJobUserDO) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
        if (!loginUser.validPermission(jobGroup)) {
            throw new RuntimeException(I18nUtil.getString("system_permission_limit") + "[username="+ loginUser.getUsername() +"]");
        }
    }
}
