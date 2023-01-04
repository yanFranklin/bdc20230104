package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqFdcqxmDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.dto.init.BdcQjtbxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcSynchFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/28
 * @description 收件材料
 */
@RestController
@RequestMapping("/synlpb")
public class BdcSynlpbController extends BaseController {
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private BdcSynchFeignService bdcSynchFeignService;

    /**
     * @param xmid 项目id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 项目同步权籍数据对照信息
     */
    @GetMapping("dzxx")
    public List<BdcQjtbxxDTO> querySynlpbDzxx(@NotBlank(message = "项目ID参数不能为空") String xmid) throws Exception {
        return bdcSynchFeignService.queryLpbDataDzxx(xmid);
    }

    /**
     * @param xmid 项目id
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 项目同步权籍数据对照信息
     */
    @PostMapping("tbdzxx/{xmid}")
    public void synchLpbDataToXm(@RequestBody List<BdcQjtbxxDTO> list,@PathVariable(value = "xmid") String xmid) throws Exception {
        BdcQl bdcQl=bdcQllxFeignService.queryQlxx(xmid);
        if(bdcQl!=null && CommonConstantUtils.QSZT_VALID.equals(bdcQl.getQszt())){
            throw new AppException("项目:"+xmid+" 已登簿，不允许更新权籍信息");
        }
        bdcSynchFeignService.synchLpbDataToXm(list,xmid);
    }


}
