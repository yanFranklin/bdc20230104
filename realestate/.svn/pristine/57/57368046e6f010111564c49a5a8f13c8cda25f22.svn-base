package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJcggDO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理继承公告restservice
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-12-01 16:07
 **/
public interface BdcSlJcggRestService {

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询继承公告信息
     * @date : 2021/12/1 16:08
     */
    @GetMapping("/realestate-accept/rest/v1.0/jcgg/{gzlslid}")
    List<BdcSlJcggDO> listBdcSlJcgg(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除继承公告信息
     * @date : 2021/12/1 16:08
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/jcgg/{gzlslid}")
    void deleteBdcSlJcgg(@PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param bdcSlJcggDO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存公告信息
     * @date : 2021/12/1 16:08
     */
    @PatchMapping("/realestate-accept/rest/v1.0/jcgg")
    BdcSlJcggDO saveBdcSlJcgg(@RequestBody BdcSlJcggDO bdcSlJcggDO);
}
