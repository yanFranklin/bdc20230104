package cn.gtmap.realestate.inquiry.ui.web.config;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.BdcFphDO;
import cn.gtmap.realestate.common.core.domain.BdcFphSymxDO;
import cn.gtmap.realestate.common.core.qo.config.BdcFphQO;
import cn.gtmap.realestate.common.core.service.feign.config.BdcXtFphFeignService;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcFphVO;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-09
 * @description
 */
@RestController
@RequestMapping("/rest/v1.0/fph")
public class BdcXtFphController extends BaseController {
    /**
     * 发票号模板服务
     */
    @Autowired
    private BdcXtFphFeignService bdcXtFphFeignService;

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    @GetMapping("/page")
    public Object listBdcFph(@LayuiPageable Pageable pageable, BdcFphQO bdcFphQO) {
        Page<BdcFphVO> bdcFphVOS = bdcXtFphFeignService.listBdcFph(pageable, JSON.toJSONString(bdcFphQO));
        return super.addLayUiCode(bdcFphVOS);
    }

    /**
     * @param bdcFphQO
     * @return {int}
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 生成发票号
     */
    @PostMapping
    public int generateBdcFph(@RequestBody BdcFphQO bdcFphQO) {
        return bdcXtFphFeignService.generateBdcFph(bdcFphQO);
    }

    /**
     * @param bdcFphDO
     * @return {int}
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存发票号配置信息
     */
    @PutMapping
    public int saveBdcFph(@RequestBody BdcFphDO bdcFphDO) {
        return bdcXtFphFeignService.saveBdcFph(bdcFphDO);
    }

    /**
     * @param bdcYzhDOList
     * @return {int}
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除发票号配置信息
     */
    @DeleteMapping
    public int deleteBdcFph(@RequestBody List<BdcFphDO> bdcYzhDOList) {
        return bdcXtFphFeignService.deleteBdcFph(bdcYzhDOList);
    }

    /**
     * @param bdcFphSymxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 作废发票号
     */
    @DeleteMapping("/zf")
    public void deleteBdcFph(@RequestBody BdcFphSymxDO bdcFphSymxDO) {
        bdcXtFphFeignService.deleteBdcFph(bdcFphSymxDO);
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取发票号领取的配置方式
     */
    @GetMapping("/lqfs")
    public Map<String, String> getAllFphLqfs() {
        return bdcXtFphFeignService.getAllFphLqfs();
    }

    /**
     * @param
     * @return
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 修改发票号使用状况
     */
    @PostMapping("/syqkedit")
    public Integer syqkEdit(@RequestBody List<BdcFphDO> bdcFphDOList) {
        return bdcXtFphFeignService.syqkEdit(bdcFphDOList);
    }

    /**
     * @param fphid 发票号id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取发票号使用明细情况
     * @date : 2020/11/26 18:14
     */
    @GetMapping("/{fphid}/symx")
    public Object queryFphSymx(@PathVariable(name = "fphid") String fphid) {
        return bdcXtFphFeignService.listFphSymx(fphid);
    }

}
