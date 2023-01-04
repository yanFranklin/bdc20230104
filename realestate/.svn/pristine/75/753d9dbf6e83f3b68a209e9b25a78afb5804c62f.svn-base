package cn.gtmap.realestate.common.core.service.rest.init.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcFwjsztckDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @program: realestate
 * @description: 房屋建设状态查看restService
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-20 13:52
 **/
public interface BdcFwJsztCkRestService {

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流实例id查询房屋建设状态
     * @date : 2021/7/20 13:55
     */
    @GetMapping("/init/rest/v1.0/jsztck/{gzlslid}")
    List<BdcFwjsztckDO> queryFwjszt(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param bdcFwjsztckDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增房屋建设状态查看信息
     * @date : 2021/7/20 14:07
     */
    @PostMapping("/init/rest/v1.0/jsztck")
    BdcFwjsztckDO insertFwjsztCk(@RequestBody BdcFwjsztckDO bdcFwjsztckDO);
}
