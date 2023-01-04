package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.vo.accept.ui.BdcBgxxDbVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/10/21.
 * @description 变更信息对比Rest服务
 */
public interface BdcBgxxDbRestService {

    /**
     * 根据工作流实例ID,获取业务信息并存至ES中
     * @param gzlslid  工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-accept/rest/v1.0/bgxxdb/es")
    void addYwxxToEs(@RequestParam(name="gzlslid")String gzlslid);

    /**
     * 根据xmid获取不动产业务信息，包含项目、权利、权利人信息
     * @param xmid  项目ID
     * @param gzlslid  工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 变更对比信息VO集合
     */
    @GetMapping("/realestate-accept/rest/v1.0/bgxxdb/bdcywxx")
    List<BdcBgxxDbVO> getBdcYwxx(@RequestParam(name="xmid", required = false)String xmid,
                                 @RequestParam(name="gzlslid")String gzlslid);


    @GetMapping("/realestate-accept/rest/v1.0/bgxxdb/ywxxdb")
    Object compareYwxx(@RequestParam(name="gzlslid")String gzlslid);

}
