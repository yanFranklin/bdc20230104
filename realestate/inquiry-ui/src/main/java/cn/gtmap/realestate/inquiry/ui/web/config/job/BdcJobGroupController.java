package cn.gtmap.realestate.inquiry.ui.web.config.job;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import cn.gtmap.realestate.common.core.service.feign.config.BdcJobGroupFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0, 2022/01/01 15:12
 * @description jobGroup台账
 */
@RestController
@RequestMapping("/job/group")
public class BdcJobGroupController extends BaseController {

    @Autowired
    BdcJobGroupFeignService bdcJobGroupFeignService;

    /**
     * 查询执行器列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param bdcJobGroupDO jobGroup
     * @return Object Object
     */
    @GetMapping("/page")
    public Object listBdcJobGroupPage(@LayuiPageable Pageable pageable,  BdcJobGroupDO bdcJobGroupDO){
        String jobParamJson = JSON.toJSONString(bdcJobGroupDO);
        Page<BdcJobGroupDO> bdcJobGroupPage = bdcJobGroupFeignService.listBdcJobGroupPage(pageable,jobParamJson);
        return addLayUiCode(bdcJobGroupPage);
    }

    /**
     * 保存执行器信息 没有记录则新增
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO jobGroup
     * @return jobGroup jobGroup
     */
    @PostMapping("/save")
    public ReturnT<String> saveJobGroup(@RequestBody BdcJobGroupDO bdcJobGroupDO) {
        return bdcJobGroupFeignService.saveJobGroup(bdcJobGroupDO);
    }

    /**
     * 更新执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO jobGroup
     * @return jobGroup jobGroup
     */
    @PostMapping("/update")
    public ReturnT<String> updateJobGroup(@RequestBody BdcJobGroupDO bdcJobGroupDO) {
        return bdcJobGroupFeignService.updateJobGroup(bdcJobGroupDO);
    }

    /**
     * 删除 执行器
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/remove")
    @ResponseBody
    public ReturnT<String> removeJobGroup(@RequestParam(value = "id",required = false) Integer id){
        return bdcJobGroupFeignService.removeJobGroup(id);
    }


    /**
     * 根据执行器id查询执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @RequestMapping("/loadById")
    @ResponseBody
    public ReturnT<BdcJobGroupDTO> loadJobGroupById(@RequestParam(value = "id", required = false) Integer id){
        return bdcJobGroupFeignService.loadJobGroupById(id);
    }



}
