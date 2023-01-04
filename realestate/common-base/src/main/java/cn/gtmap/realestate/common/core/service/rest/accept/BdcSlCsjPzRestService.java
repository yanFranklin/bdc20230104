package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlCsjpzDO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @program: realestate
 * @description: 长三角业务数据配置restservice
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-10 13:58
 **/
public interface BdcSlCsjPzRestService {

    /**
     * @param bdcSlCsjpzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询长三角配置服务
     * @date : 2022/5/10 13:59
     */
    @PostMapping("/realestate-accept/rest/v1.0/csjpz")
    List<BdcSlCsjpzDO> listCsjpz(@RequestBody BdcSlCsjpzDO bdcSlCsjpzDO);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除配置
     * @date : 2022/5/10 14:03
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/csjpz/{pzid}")
    int deleteCsjpz(@PathVariable(value = "pzid") String pzid);

    /**
     * @param bdcSlCsjpzDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存配置
     * @date : 2022/5/10 14:03
     */
    @PostMapping("/realestate-accept/rest/v1.0/csjpz/save")
    BdcSlCsjpzDO saveCsjpz(@RequestBody BdcSlCsjpzDO bdcSlCsjpzDO);
}
