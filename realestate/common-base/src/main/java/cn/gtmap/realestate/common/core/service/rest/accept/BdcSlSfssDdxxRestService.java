package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcDsfSfssDdxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfssDdxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/5/28
 * @description 不动产收费收税订单信息rest服务
 */
public interface BdcSlSfssDdxxRestService {

    /**
     * @param bdcSlSfssDdxxQO 不动产收费收税订单信息查询QO对象
     * @return 不动产收费收税订单信息集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询参数查询不动产收费收税订单信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/list")
    List<BdcSlSfssDdxxDO> listBdcSlSfssDdxx(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产收费收税订单信息集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID查询不动产收费收税订单信息
     */
    @GetMapping("/realestate-accept/rest/v1.0/ddxx/list/{gzlslid}")
    List<BdcSlSfssDdxxDO> listBdcSlSfssDdxxByGzlslid(@PathVariable("gzlslid") String gzlslid);


    /**
     * @param bdcSlSfssDdxxDO 收税订单信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新收税订单信息
     * @date : 2020/5/28 17:41
     */
    @PatchMapping("/realestate-accept/rest/v1.0/ddxx/update")
    Integer updateBdcSlSfssDdxxDO(@RequestBody BdcSlSfssDdxxDO bdcSlSfssDdxxDO);

    /**
     * @param bdcSlSfssDdxxDTOList 不动产收费收税订单信息DTO对象集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 保存并创建收费收税订单信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/scddxx")
    List<BdcSlSfssDdxxDO> saveAndCreateDdxx(@RequestParam("jkfs") String jkfs, @RequestBody List<BdcSlSfssDdxxDTO> bdcSlSfssDdxxDTOList);

    /**
     * @param xmid     项目ID
     * @param gzlslid  工作流实例ID
     * @return Map 权利人总金额与义务人总金额
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 计算权利人与义务人税费总金额
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/sfzje")
    Object computeQlrAndYwrSfje(@RequestParam(name="xmid")String xmid, @RequestParam(name = "gzlslid") String gzlslid);

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据受理编号查询收费状态并且更新订单信息和收费收税信息
     * @date : 2020/5/29 8:44
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/sfzt")
    void querySfzt(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * @param bdcDsfSfssDdxxDTO 第三方订单信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 接收第三方订单信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/dsfddxx")
    void saveDsfDdxx(@RequestBody BdcDsfSfssDdxxDTO bdcDsfSfssDdxxDTO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据订单编号保存订单信息,订单号不存在新增订单信息,存在状态不一致更新,一致不处理
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/savebyddbh")
    void saveAndUpdateDdxxByDdbh(@RequestBody List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList,
                                 @RequestParam(name = "gzlslid",required = false) String gzlslid, @RequestParam(name="yjdh",required = false) String yjdh);

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO对象
     * @retur 返回结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费信息退款申请
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/sfxxTksq")
    List<BdcSlSfssDdxxDO> ddxxTksq(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询退款状态
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/querySfxxTkqk")
    BdcSlSfxxDO querySfxxTkqk(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * @param gzlslid 工作流实例ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 合并缴费
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/hbjf")
    List<BdcSlSfssDdxxDO> hbjf(@RequestParam(name = "jkfs") String jkfs, @RequestParam(name = "gzlslid") String gzlslid);

    /**
     * @param bdcSlSfssDdxxQO 不动产收费收税订单信息查询QO对象
     * @return true:已失效 false:未失效
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证订单信息是否已失效
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/issx")
    boolean checkDdxxIsSx(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * 根据月结单号创建订单
     * @param yjdh 月结单号
     * @param hyzfjftj 合一支付缴费途径（1表示商业银行 2 表示合一支付）
     * @param jkqd 缴款渠道（0 表示线上支付 1 表示线下支付）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/yjOrder")
    List<BdcSlSfssDdxxDO> createYjSfOrder(@RequestParam(value="yjdh") String yjdh, @RequestParam(value="hyzfjftj", required = false) String hyzfjftj,
                         @RequestParam(value="jkqd", required = false) String jkqd);

    /**
     * @param bdcSlSfssDdxxQO 不动产收费收税订单信息查询QO对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 作废订单信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/ddxx/zf")
    void zfDdxx(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * 更新月结订单缴费状态
     * <p>根据月结单号，更新当前月结订单下所有收费信息的收费状态</p>
     * @param bdcSlSfssDdxxQO 不动产收费收税订单信息查询QO对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-accept/rest/v1.0/ddxx/gxsfzt/hlw")
    void udpateYjDdxxSfztForHlw(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

}
