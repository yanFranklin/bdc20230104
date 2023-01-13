package cn.gtmap.realestate.inquiry.ui.web.config.job;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobInfoDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobUserDO;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.common.core.service.feign.config.BdcJobGroupFeignService;
import cn.gtmap.realestate.common.core.service.feign.config.BdcJobInfoFeignService;
import cn.gtmap.realestate.common.job.util.DateUtil;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0, 2022/01/01 15:12
 * @description jobInfo 任务台账
 */
@RestController
@RequestMapping("/job/info")
public class BdcJobInfoController extends BaseController {

    @Autowired
    BdcJobInfoFeignService bdcJobInfoFeignService;

    /**
     * 分页查询任务列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param bdcJobInfoDO
     * @return Object Object
     */
    @GetMapping("/page")
    public Object listBdcJobInfoPage(@LayuiPageable Pageable pageable, BdcJobInfoDO bdcJobInfoDO){
        String jobParamJson = JSON.toJSONString(bdcJobInfoDO);
        Page<BdcJobInfoDO> bdcJobInfoDOPage = bdcJobInfoFeignService.listBdcJobInfoPage(pageable,jobParamJson);
        return addLayUiCode(bdcJobInfoDOPage);
    }

    /**
     * 添加任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobInfoDO
     * @return Object Object
     */
    @RequestMapping("/add")
    @ResponseBody
    public ReturnT<String> addJobnfo(@RequestBody BdcJobInfoDO bdcJobInfoDO) {
        return bdcJobInfoFeignService.addJobnfo(bdcJobInfoDO);
    }

    /**
     * 保存任务信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobInfoDO
     * @return bdcJobInfoDO
     */
    @PostMapping("/save")
    public ReturnT<String> saveJobnfo(@RequestBody BdcJobInfoDO bdcJobInfoDO) {
        return bdcJobInfoFeignService.saveJobnfo(bdcJobInfoDO);
    }

    /**
     * 更新任务信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobInfoDO jobGroup
     * @return jobGroup jobGroup
     */
    @PostMapping("/update")
    public ReturnT<String> updateJobnfo(@RequestBody BdcJobInfoDO bdcJobInfoDO) {
        return bdcJobInfoFeignService.updateJobnfo(bdcJobInfoDO);

    }

    /**
     * 删除 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/remove")
    @ResponseBody
    public ReturnT<String> removeJobnfo(@RequestParam(value = "id",required = false) Integer id){
        return bdcJobInfoFeignService.removeJobnfo(id);
    }

    /**
     * 终止 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/pause")
    @ResponseBody
    public ReturnT<String> pauseJobnfo(@RequestParam(value = "id",required = false) Integer id) {
        return bdcJobInfoFeignService.pauseJobnfo(id);
    }


    /**
     * 启动 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/start")
    @ResponseBody
    public ReturnT<String> startJobInfo(@RequestParam(value = "id",required = false) Integer id) {
        return bdcJobInfoFeignService.startJobInfo(id);
    }


    /**
     * 执行一次 任务
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/trigger")
    @ResponseBody
    //@PermissionLimit(limit = false)
    public ReturnT<String> triggerJobInfo(@RequestParam(value = "id",required = false) Integer id, @RequestParam(value = "executorParam",required = false) String executorParam, @RequestParam(value = "addresslist",required = false) String addresslist) {
        return bdcJobInfoFeignService.triggerJobInfo(id, executorParam, addresslist);
    }

    /**
     * 任务下次执行时间
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param scheduleType
     * @param scheduleConf
     * @return ReturnT
     */
    @RequestMapping("/nextTriggerTime")
    @ResponseBody
    public ReturnT<List<String>> nextTriggerTime(@RequestParam(value = "scheduleType",required = false) String scheduleType, @RequestParam(value = "scheduleConf",required = false) String scheduleConf) {
        return bdcJobInfoFeignService.nextTriggerTime(scheduleType, scheduleConf);

    }

    /**
     * 根据任务id查询任务信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/loadById")
    @ResponseBody
    public ReturnT<BdcJobInfoDO> loadJobGroupById(@RequestParam(value = "id", required = false) Integer id){
        return bdcJobInfoFeignService.loadJobInfoById(id);
    }

//    /**
//     * 筛选出所有的该用户拥有权限的定时任务List
//     * @param request
//     * @param bdcJobGroupDTOList_all
//     * @return
//     */
//    public static List<BdcJobGroupDTO> filterJobGroupByRole(HttpServletRequest request, List<BdcJobGroupDTO> bdcJobGroupDTOList_all){
//        List<BdcJobGroupDTO> bdcJobGroupDTOList = new ArrayList<>();
//        if (bdcJobGroupDTOList_all !=null && bdcJobGroupDTOList_all.size()>0) {
//            BdcJobUserDO loginUser = (BdcJobUserDO) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
//            if (loginUser.getRole() == 1) {
//                bdcJobGroupDTOList = bdcJobGroupDTOList_all;
//            } else {
//                List<String> groupIdStrs = new ArrayList<>();
//                //执行器按照","分割， 获取执行器list
//                if (loginUser.getPermission()!=null && loginUser.getPermission().trim().length()>0) {
//                    groupIdStrs = Arrays.asList(loginUser.getPermission().trim().split(","));
//                }
//                for (BdcJobGroupDTO groupItem: bdcJobGroupDTOList_all) {
//                    if (groupIdStrs.contains(String.valueOf(groupItem.getId()))) {
//                        bdcJobGroupDTOList.add(groupItem);
//                    }
//                }
//            }
//        }
//        return bdcJobGroupDTOList;
//    }
//
//    /**
//     *验证用户是否对该jobGroup有权限
//     * @param request
//     * @param jobGroup
//     */
//    public static void validPermission(HttpServletRequest request, int jobGroup) {
//        BdcJobUserDO loginUser = (BdcJobUserDO) request.getAttribute(LoginService.LOGIN_IDENTITY_KEY);
//        if (!loginUser.validPermission(jobGroup)) {
//            throw new RuntimeException(I18nUtil.getString("system_permission_limit") + "[username="+ loginUser.getUsername() +"]");
//        }
//    }


}
