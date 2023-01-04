package cn.gtmap.realestate.common.core.service.rest.init;

import cn.gtmap.realestate.common.core.domain.BdcDlrDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDlrQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 代理人rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-21 11:18
 **/
public interface BdcDlrRestService {

    /**
     * @param bdcDlrQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询代理人服务
     * @date : 2022/3/21 11:20
     */
    @PostMapping("/init/rest/v1.0/dlr/list")
    List<BdcDlrDO> listBdcDlr(@RequestBody BdcDlrQO bdcDlrQO);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增保存代理人服务
     * @date : 2022/3/21 11:22
     */
    @PatchMapping("/init/rest/v1.0/dlr/pl")
    List<BdcDlrDO> insertBatchDlr(@RequestBody String json, @RequestParam("gzlslid") String gzlslid,
                                  @RequestParam("djxl") String djxl);

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新代理人
     * @date : 2022/3/21 16:20
     */
    @PutMapping("/init/rest/v1.0/dlr/pl")
    List<BdcDlrDO> updateBatchDlr(@RequestBody String json, @RequestParam("gzlslid") String gzlslid,
                                  @RequestParam("djxl") String djxl);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据代理人信息删除代理人
     * @date : 2022/3/21 11:24
     */
    @DeleteMapping("/init/rest/v1.0/dlr/dlrxx")
    int deleteDlr(@RequestBody String json);


    /**
     * @param dlrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据代理人id删除代理人
     * @date : 2022/3/22 15:58
     */
    @DeleteMapping("/init/rest/v1.0/dlr/dlrxx/{dlrid}")
    int deleteDlrById(@PathVariable(value = "dlrid") String dlrid);
}
