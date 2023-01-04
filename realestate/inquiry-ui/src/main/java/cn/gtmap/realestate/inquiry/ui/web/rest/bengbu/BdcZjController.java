package cn.gtmap.realestate.inquiry.ui.web.rest.bengbu;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZjFeignService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/4/10
 * @description 质检查询相关方法
 */
@RestController
@RequestMapping(value = "/rest/v1.0/zjcx")
public class BdcZjController {
    @Autowired
    private BdcZjFeignService bdcZjFeignService;

    /**
     * 查询质检状态 判断是否存在质检信息
     * @param kssj
     * @param jssj
     * @return
     */
    @GetMapping("/queryZjzt")
    public Object queryZjzt(String kssj, String jssj) {
        if (StringUtils.isAnyBlank(kssj, jssj)){
            throw new AppException("请输入开始时间和结束时间！");
        }
        return bdcZjFeignService.queryZjzt(kssj, jssj);
    }

    @GetMapping("/cjzjxx")
    public Integer cjzjxx(String kssj, String jssj) {
        if (StringUtils.isAnyBlank(kssj, jssj)){
            throw new AppException("请输入开始时间和结束时间！");
        }
        return bdcZjFeignService.cjzjxx(kssj, jssj);
    }

    @PostMapping("/zjtg")
    public void zjtg(@RequestBody List<BdcZjDO> bdcZjDOList) {
        if (CollectionUtils.isEmpty(bdcZjDOList)){
            throw new AppException("请选择质检信息！");
        }
        for (BdcZjDO bdcZjDO : bdcZjDOList){
            bdcZjDO.setZjzt(1);
        }
        bdcZjFeignService.zjztgx(bdcZjDOList);
    }

    @PostMapping("/zjwtg")
    public void zjxtg(@RequestBody List<BdcZjDO> bdcZjDOList,@RequestParam("spyj") String spyj) {
        if (CollectionUtils.isEmpty(bdcZjDOList)){
            throw new AppException("请选择质检信息！");
        }
        for (BdcZjDO bdcZjDO : bdcZjDOList){
            bdcZjDO.setSpyj(spyj);
            bdcZjDO.setZjzt(2);
        }
        bdcZjFeignService.zjztgx(bdcZjDOList);
    }
}
