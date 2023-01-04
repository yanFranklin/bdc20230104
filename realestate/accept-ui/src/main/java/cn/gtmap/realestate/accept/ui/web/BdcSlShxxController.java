package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlShxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlShxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcXtMryjFeignService;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/14
 * @description 审核信息页面服务
 */
@RestController
@RequestMapping("/slshxx")
public class BdcSlShxxController extends BaseController {
    @Autowired
    BdcXtMryjFeignService bdcXtMryjFeignService;

    @Autowired
    BdcSlShxxFeignService bdcSlShxxFeignService;

    @Autowired
    WorkFlowUtils workFlowUtils;

    @Autowired
    BdcXtMryjFeignService bdcXtMryjRestService;

    /**
     * 签名图片地址
     */
    @Value("${url.sign.image:}")
    protected String signImageUrl;

    /**
     * @param bdcSlShxxDO
     * @return signid
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取sign id
     */
    @PutMapping(value = "/sign")
    public BdcShxxVO sign(@RequestBody BdcSlShxxDO bdcSlShxxDO) {
        BdcShxxVO bdcShxxVO = bdcSlShxxFeignService.getShxxSign(bdcSlShxxDO);
        bdcShxxVO.setQmtpdz(signImageUrl + bdcShxxVO.getQmid());
        return bdcShxxVO;
    }

    /**
     * @param taskId
     * @return
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取用户的可选意见
     */
    @GetMapping(value = "/kxyj")
    public List<BdcXtMryjDO> queryKxyj(@RequestParam(value = "taskId") String taskId) {
        if (StringUtils.isEmpty(taskId)) {
            throw new MissingArgumentException("taskId");
        }
        TaskData taskData = workFlowUtils.getTaskById(taskId);
        String jdmc = taskData.getTaskName();
        String gzldyKey = taskData.getProcessKey();
        return bdcXtMryjFeignService.listBdcXtKxyj(null, gzldyKey, jdmc);
    }

    /**
     * @param bdcSlShxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 新增或者插入受理审核信息
     */
    @PatchMapping(value = "/shxx")
    public void saveOrUpdateBdcShxx(@RequestBody BdcSlShxxDO bdcSlShxxDO) {
        if(null != bdcSlShxxDO){
            bdcSlShxxFeignService.saveOrUpdateBdcShxx(bdcSlShxxDO);
        }
    }

    /**
     * @param shxxid
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除受理审核信息
     */
    @DeleteMapping(value = "/shxx/{shxxid}")
    public void deleteSlShxx(@PathVariable(name = "shxxid") String shxxid) {
        bdcSlShxxFeignService.deleteShxx(shxxid);
    }


}
