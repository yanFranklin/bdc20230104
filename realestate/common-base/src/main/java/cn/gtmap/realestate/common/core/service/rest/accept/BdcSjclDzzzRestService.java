package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSjclDzzzDzDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 收件材料电子证照对照服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-02 14:50
 **/
public interface BdcSjclDzzzRestService {

    /**
     * @param bdcSjclDzzzDzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据收件材料名称获取配置表中的数据
     * @date : 2020/1/2 14:51
     */
    @PostMapping("/realestate-accept/rest/v1.0/dzzz")
    List<BdcSjclDzzzDzDO> querySjclDzzzDz(@RequestBody BdcSjclDzzzDzDO bdcSjclDzzzDzDO);


    /**
     * @param bdcSjclDzzzDzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存对照关系
     * @date : 2020/1/2 15:23
     */
    @PutMapping("/realestate-accept/rest/v1.0/dzzz/save")
    int saveBdcSjclDzzz(@RequestBody BdcSjclDzzzDzDO bdcSjclDzzzDzDO);


    /**
     * @param bdcSjclDzzzDzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新对照关系
     * @date : 2020/1/2 15:39
     */
    @PatchMapping("/realestate-accept/rest/v1.0/dzzz/update")
    int updateBdcSjclDzzz(@RequestBody BdcSjclDzzzDzDO bdcSjclDzzzDzDO);
}
