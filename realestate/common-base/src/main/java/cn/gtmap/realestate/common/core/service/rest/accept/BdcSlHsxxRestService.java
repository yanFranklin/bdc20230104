package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSwxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.swxx.SwHsztDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/27
 * @description 不动产受理核税信息rest服务
 */
public interface BdcSlHsxxRestService {

    /**
     * @param bdcSlHsxxDO 不动产受理核税信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新不动产受理核税信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/hsxx/")
    int updateBdcSlHsxx(@RequestBody BdcSlHsxxDO bdcSlHsxxDO);

    /**
     * @param BdcSlHsxxQO 不动产受理核税信息
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 获取不动产受理核税信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/hsxx/")
    List<BdcSlHsxxDO> listBdcSlHsxx(@RequestBody BdcSlHsxxQO BdcSlHsxxQO);


    /**
     * 根据项目ID与纳税人识别号信息更新受理核税信息
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: [bdcSlHsxxDO] 不动产受理核税信息
     * @return: Integer 更新返回状态 1：更新成功  0：更新失败
     */
    @PutMapping("/realestate-accept/rest/v1.0/hsxx/xmid")
    Integer updateBdcSlHsxxByXmidAndNsrsbh(@RequestBody BdcSlHsxxDO bdcSlHsxxDO);

    /**
     * @param xmid 项目ID
     * @param sqrlb 申请人类别
     * @return 税务信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据xmid和申请人类别查询税务信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/sw/queryBdcSwxxDTO/{xmid}")
    List<BdcSwxxDTO> queryBdcSwxxDTO(@PathVariable(value = "xmid") String xmid, @RequestParam(value = "sqrlb", required = false) String sqrlb);

    /**
     * @param gzlslid 工作流实例ID
     * @param wszt    完税状态
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID更新完税状态
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/zt/{wszt}/{gzlslid}")
    void updateWsztByGzlslid(@PathVariable(value = "wszt") Integer wszt, @PathVariable(value = "gzlslid") String gzlslid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据权利人类别不同分别更新完税状态
     * @date : 2022/9/21 16:31
     */
    @PostMapping("/realestate-accept/rest/v1.0/sw/zt/{wszt}/{gzlslid}/qlrlb")
    void updateWsztByQlrlb(@PathVariable(value = "wszt") Integer wszt, @PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb);

    /**
     * @param bdcSlHsxxDO 不动产受理核税信息 (可更新的参数有：jypzh、ytsswzt、yhjkrkzt)
     * @param gzlslid     工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 根据工作流实例ID更新核税信息
     */
    @PutMapping("/realestate-accept/rest/v1.0/hsxx/gzlslid")
    void updateHsxxByGzlslid(@RequestBody BdcSlHsxxDO bdcSlHsxxDO, @RequestParam(value = "gzlslid") String gzlslid);

    /**
     * @param slbh 受理编号
     * @return 核税信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据受理编号获取核税信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/hsxx/list/{slbh}")
    List<BdcSlHsxxDO> listBdcSlHsxxBySlbh(@PathVariable(value = "slbh") String slbh);


    /**
     * @param bdcSlHsxxDO 不动产受理核税信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增受理核税信息数据
     * @date : 2020/5/19 14:15
     */
    @PostMapping("/realestate-accept/rest/v1.0/hsxx/insert")
    Integer insertBdcSlHsxxDO(@RequestBody BdcSlHsxxDO bdcSlHsxxDO);


    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/10/28 14:57
     */
    @PostMapping("/realestate-accept/rest/v1.0/hsxx/hszt")
    Integer updateHszt(@RequestBody SwHsztDTO swHsztDTO);


    /**
     * 根据工作流实例ID和申请人类别查询不动产核税信息
     * @param gzlslid 工作流实例ID
     * @param sqrlb 申请人类别
     * @return 不动产核税信息DO
     */
    @PostMapping("/realestate-accept/rest/v1.0/hsxx/list/sqrlb")
    List<BdcSlHsxxDO> listBdcSlHsxxByGzlslidAndSqrlb(@RequestParam(value="gzlslid") String gzlslid, @RequestParam(value="sqrlb", required = false)String sqrlb);


}
