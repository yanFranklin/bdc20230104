package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlFwxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxxUpdateQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: realestate
 * @description: 受理房屋信息rest服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-25 16:43
 **/
public interface BdcSlFwxxRestService {

    /**
     * @param xmid 项目id
     * @return 不动产受理房屋信息集合
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据项目id获取不动产受理房屋信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/fwxx/list/{xmid}")
    List<BdcSlFwxxDO> listBdcSlFwxxByXmid(@PathVariable(value = "xmid") String xmid);

    /**
     * @param xmidList 项目ID集合
     * @return 不动产受理项目
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description 根据项目ID集合获取不动产受理房屋信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/xm/list/fwxx")
    List<BdcSlFwxxDO> listBdcSlFwxxByXmids(@RequestBody List<String> xmidList);

    /**
     * @param bdcDjxxUpdateQO 更新对象
     * @return 更新数量
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @description  批量更新受理房屋信息
     */
    @PostMapping(path = "/realestate-accept/rest/v1.0/xm/fwxx/jsonStr")
    Integer updateBatchBdcSlFwxx(@RequestBody BdcDjxxUpdateQO bdcDjxxUpdateQO);

    /**
     * @param fwxxid 房屋信息id
     * @return 不动产受理房屋信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据房屋信息id获取不动产受理房屋信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/fwxx/{fwxxid}")
    BdcSlFwxxDO queryBdcSlFwxxByFwxxid(@PathVariable(value = "fwxxid") String fwxxid);

    /**
     * @param bdcSlFwxxDO 不动产受理房屋信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增不动产受理房屋信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/fwxx/")
    BdcSlFwxxDO insertBdcSlFwxx(@RequestBody BdcSlFwxxDO bdcSlFwxxDO);

    /**
     * @param fwxxid 交易信息id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理房屋信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/fwxx/{fwxxid}")
    Integer deleteBdcSlFwxxByFwxxid(@PathVariable(value = "fwxxid") String fwxxid);

    /**
     * @param xmid 项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除不动产受理房屋信息
     */
    @DeleteMapping("/realestate-accept/rest/v1.0/fwxx/xm/{xmid}")
    Integer deleteBdcSlFwxxByXmid(@PathVariable(value = "xmid") String xmid);

      /**
     * @param bdcSlFwxxDO 不动产受理房屋信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新不动产受理房屋信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/fwxx/")
    Integer updateBdcSlFwxx(@RequestBody BdcSlFwxxDO bdcSlFwxxDO);

    /**
     * @param bdcSlFwxxDO 不动产受理房屋信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据项目ID更新不动产受理房屋信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/fwxx/xm")
    Integer updateBdcSlFwxxByXmid(@RequestBody BdcSlFwxxDO bdcSlFwxxDO);

}
