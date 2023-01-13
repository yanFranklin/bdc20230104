package cn.gtmap.realestate.common.core.service.rest.job;

import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDO;
import cn.gtmap.realestate.common.core.domain.job.BdcJobGroupDTO;
import cn.gtmap.realestate.common.core.dto.ReturnT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BdcJobGroupRestService {
    /**
     * 分页获取执行器数据列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param pageable pageable
     * @param jobParamJson jobParamJson
     * @return JobGroup
     */
    @GetMapping("/realestate-config/rest/v1.0/job/group/page")
    Page<BdcJobGroupDO> listBdcJobGroupPage(Pageable pageable,
                                            @RequestParam(name = "jobParamJson",required = false) String jobParamJson);


    /**
     * 查询所有执行器列表
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @return Object Object
     */
    @GetMapping("/realestate-config/rest/v1.0/job/group/all")
    List<BdcJobGroupDTO> listBdcJobGroupAll();

    /**
     * 保存执行器信息 没有记录则新增
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO
     * @return jobGroup
     */
    @PostMapping(value = "/realestate-config/rest/v1.0/job/group/save")
    ReturnT<String> saveJobGroup(@RequestBody BdcJobGroupDO bdcJobGroupDO);

    /**
     * 更新执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcJobGroupDO
     * @return jobGroup
     */
    @PostMapping(value = "/realestate-config/rest/v1.0/job/group/update")
    ReturnT<String> updateJobGroup(@RequestBody BdcJobGroupDO bdcJobGroupDO);


    /**
     * 删除 执行器
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @PostMapping(value = "/realestate-config/rest/v1.0/job/group/remove")
    ReturnT<String> removeJobGroup(@RequestParam(value = "id",required = false) Integer id);


    /**
     * 根据执行器id查询执行器信息
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param id
     * @return ReturnT
     */
    @PostMapping(value = "/realestate-config/rest/v1.0/job/group/loadById")
    ReturnT<BdcJobGroupDTO> loadJobGroupById(@RequestParam(value = "id",required = false) Integer id);

}
