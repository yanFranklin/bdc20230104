package cn.gtmap.realestate.common.core.service.rest.natural;

import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/7/22
 * @description 规则验证
 */
public interface ZrzyGzyzRestService {

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @return 验证结果（包含入参）
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 流程规则验证
     */
    @PostMapping(value = "/realestate-natural/rest/v1.0/gzyz")
    List<Map<String, Object>> yzBdcgz(@RequestBody(required = false) BdcGzYzQO bdcGzYzQO);

    /**
     * @param bdcGzYzQO 规则验证查询实体(支持任意参数)
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 除流程验证外其他验证, 如匹配验证, 页面按钮操作验证等
     * @date : 2020/7/10 14:13
     */
    @PostMapping(value = "/realestate-natural/rest/v1.0/qtyz")
    List<Map<String, Object>> qtyz(@RequestBody(required = false) BdcGzYzQO bdcGzYzQO);

}
