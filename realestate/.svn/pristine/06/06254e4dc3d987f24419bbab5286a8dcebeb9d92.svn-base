package cn.gtmap.realestate.common.core.service.rest.accept;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/3/27
 * @description 不动产权利类型rest服务
 */
public interface BdcQllxRestService {

    /**
     * @param bdcdyh 不动产单元号
     * @param dyhcxlx 单元号查询类型,1-土地及其定着物,2-海域及其定着物,3-构筑物
     * @return 权利类型
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据不动产单元获取权利类型
     */
    @GetMapping("/realestate-accept/rest/v1.0/qllx")
    Integer getQllxByBdcdyh(@RequestParam(value = "bdcdyh") String bdcdyh,@RequestParam(value = "dyhcxlx") String dyhcxlx);
}
