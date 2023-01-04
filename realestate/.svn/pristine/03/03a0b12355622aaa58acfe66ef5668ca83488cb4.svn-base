package cn.gtmap.realestate.inquiry.ui.web.engine;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzLwDO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzXzYzLwFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/2
 * @description 限制验证例外控制层
 */
@RestController
@RequestMapping("/rest/v1.0")
public class BdcGzXzYzLwController extends BaseController {

    @Autowired
    BdcGzXzYzLwFeignService bdcGzXzYzLwFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 分页查询不动产规则限制验证例外信息
     */
    @GetMapping(value = "/bdcgzlw")
    public Object listBdcGzYwgzByPageJson(Pageable pageable, String czry, String lwwh) {
        pageable = delPageRequest(pageable);
        return addLayUiCode(bdcGzXzYzLwFeignService.listBdcXzyzLwPage(czry,"",lwwh,pageable));
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcGzLwDOList 例外规则集合
     * @return {int} 操作数据记录数
     * @description  新增的验证例外规则
     */
    @PostMapping(value = "/bdcgzlw")
    public int addBdcGzLw(@RequestBody List<BdcGzLwDO> bdcGzLwDOList){
        return bdcGzXzYzLwFeignService.addBdcGzLw(bdcGzLwDOList);
    }

    /**
     * @author  <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param  bdcGzLwDOList 待删除例外规则集合
     * @return {int} 操作数据记录数
     * @description  删除已设置的验证例外规则
     */
    @DeleteMapping(value = "/bdcgzlw")
    public int deleteBdcGzLw(@RequestBody List<BdcGzLwDO> bdcGzLwDOList){
        return bdcGzXzYzLwFeignService.deleteBdcGzLwList(bdcGzLwDOList);
    }
}
