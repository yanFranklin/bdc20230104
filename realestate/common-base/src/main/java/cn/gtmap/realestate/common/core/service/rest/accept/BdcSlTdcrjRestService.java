package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlTdcrjDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/8
 * @description 土地出让金rest服务
 */
public interface BdcSlTdcrjRestService {

    /**
     * @param xmid 项目ID
     * @return 出让金信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取出让金信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/tdcrj/{xmid}")
    List<BdcSlTdcrjDO> listBdcSlTdcrjByXmid(@PathVariable(value = "xmid")String xmid);

    /**
      * @param bdcSlTdcrjDO 土地出让金信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 新增土地出让金信息
      */
    @PostMapping("/realestate-accept/rest/v1.0/tdcrj")
    void insertBdcSlTdcrj(@RequestBody  BdcSlTdcrjDO bdcSlTdcrjDO);

    /**
     * @param tdcrjid 土地出让金ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除土地出让金信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/tdcrj")
    void deleteBdcSlTdcrj(@RequestParam(name = "tdcrjid") String tdcrjid);
}
