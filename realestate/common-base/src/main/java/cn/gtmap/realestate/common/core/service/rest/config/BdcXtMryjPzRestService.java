package cn.gtmap.realestate.common.core.service.rest.config;

import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.domain.BdcXtZsmbPzDO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcXtMryjPzVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/29
 * @description 不动产默认意见配置服务
 */
public interface BdcXtMryjPzRestService {

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取默认意见
     */
    @GetMapping("/realestate-config/rest/v1.0/mryj/page")
    Page<BdcXtMryjPzVO> listBdcXtMryj(Pageable pageable, @RequestParam(name = "mryjParamJson",required = false) String mryjParamJson);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存默认意见
     */
    @PutMapping("/realestate-config/rest/v1.0/mryj")
    int saveBdcXtMryj(@RequestBody BdcXtMryjDO bdcXtMryjDO);
    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改默认意见
     */
    @PostMapping("/realestate-config/rest/v1.0/mryj")
    int updateBdcXtMryj(@RequestBody BdcXtMryjDO bdcXtMryjDO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除默认意见
     */
    @DeleteMapping("/realestate-config/rest/v1.0/mryj/")
    int deleteBdcXtMryj(@RequestBody List<BdcXtMryjDO> bdcXtMryjDO);

    /**
     * @param bdcXtMryjDO
     * @return List<BdcXtMryjDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 默认意见配置
     */
    @PostMapping("/realestate-config/rest/v1.0/mryj/list")
    List<BdcXtMryjDO> listMryjpz(@RequestBody BdcXtMryjDO bdcXtMryjDO);
}
