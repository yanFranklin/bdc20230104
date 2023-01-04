package cn.gtmap.realestate.common.core.service.rest.init.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcYwblBahdDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @program: realestate
 * @description: 业务办理备案核对restService
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-07-21 09:58
 **/
public interface BdcYwblBahdRestService {

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询业务办理备案核定数据
     * @date : 2021/7/21 10:11
     */
    @GetMapping("/init/rest/v1.0/ywblbahd/{gzlslid}")
    List<BdcYwblBahdDO> listYwblBahd(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param bdcYwblBahdDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增业务办理备案核定数据
     * @date : 2021/7/21 10:18
     */
    @PostMapping("/init/rest/v1.0/ywblbahd")
    BdcYwblBahdDO insertYwblBahd(@RequestBody BdcYwblBahdDO bdcYwblBahdDO);
}
