package cn.gtmap.realestate.common.core.service.rest.accept;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/01/15
 * @description 不动产受理房改房rest服务
 */
public interface BdcSlFgfRestService {
    /**
     * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param: [gzlslid] 工作流程实例id
     * @description 房改房推送
     */
    @GetMapping("/realestate-accept/rest/v1.0/fgf/{processInsId}")
    void tsszfgb(@PathVariable(value = "processInsId") String gzlslid);

    /**
     * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param: 获取szfgf djyy 配置
     * @description 获取szfgf djyy 配置
     */
    @GetMapping("/realestate-accept/rest/v1.0/fgf/getDjyy")
    String getDjyy();

}
