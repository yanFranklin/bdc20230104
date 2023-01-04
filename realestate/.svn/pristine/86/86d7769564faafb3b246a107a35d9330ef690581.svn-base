package cn.gtmap.realestate.common.core.service.rest.accept;


import cn.gtmap.realestate.common.core.domain.accept.BdcSlCxcsDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/12/03
 * @description  不动产查询参数 Rest 接口服务
 */
public interface BdcSlCxcsRestService {

    /**
     * 通过工作流实例ID获取不动产受理查询参数
     * @param gzlslid 工作流实例ID
     * @return List<BdcSlCxcsDO></BdcSlCxcsDO> 不动产查询参数集合
     */
    @GetMapping("/realestate-accept/rest/v1.0/cxcs/{gzlslid}")
    List<BdcSlCxcsDO> getBdcSlCxcs(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * 保存不动产查询参数（根据工作流实例ID,判断是否存在记录，存在则修改，不存在则新增）
     * @param bdcSlCxcsDO 不动产受理查询参数记录
     */
    @PostMapping("/realestate-accept/rest/v1.0/cxcs/gzlslid")
    void saveBdcSlCxcsByGzlslid(@RequestBody BdcSlCxcsDO bdcSlCxcsDO);
}
