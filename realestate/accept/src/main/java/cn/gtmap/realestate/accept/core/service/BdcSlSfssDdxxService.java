package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcDsfSfssDdxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfssDdxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/5/28
 * @description 不动产收费收税订单信息服务
 */
public interface BdcSlSfssDdxxService {

    /**
     * @param bdcSlSfssDdxxQO 不动产收费收税订单信息查询QO对象
     * @return 不动产收费收税订单信息集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据查询参数查询不动产收费收税订单信息
     */
    List<BdcSlSfssDdxxDO> listBdcSlSfssDdxx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * @param yjdh 月结单号
     * @return 不动产收费收税订单信息集合
     * @author <a href="mailto:liaoxiang@gtmap.cn">yaoyi</a>
     * @description 根据月结单号查询月结订单信息
     */
    List<BdcSlSfssDdxxDO> queryBdcSlSfssDdxxByYjdh(String yjdh);

    /**
     * @param bdcSlSfssDdxxQO 不动产收费收税订单信息查询QO对象
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除不动产收费收税订单信息
     */
    void deleteBdcSlSfssDdxx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * @param bdcSlSfssDdxxDOList 不动产收费收税订单信息新增集合
     * @return 新增数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量新增不动产收费收税订单信息
     */
    int insertBatchBdcSlSfssDdxx(List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList);

    /**
     * @param bdcSlSfssDdxxDO 不动产收费收税订单信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新订单信息
     * @date : 2020/5/28 17:42
     */
    int updateBdcSlSfssDdxx(BdcSlSfssDdxxDO bdcSlSfssDdxxDO);

    /**
     * @param bdcSlSfssDdxxDTOList 不动产收费收税订单信息DTO集合
     * @author <a href="mailto:yoayi@gtmap.cn">yaoyi</a>
     * @description 保存并创建不动产收费收税订单
     */
    List<BdcSlSfssDdxxDO> saveAndCreateDdxx(String jkfs,List<BdcSlSfssDdxxDTO> bdcSlSfssDdxxDTOList);

    /**
     * @param xmid    项目ID
     * @param gzlslid 工作流实例ID
     * @return Map 权利人总金额与义务人总金额
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 计算权利人与义务人税费总金额
     */
    Object computeQlrAndYwrSfje(String xmid, String gzlslid);

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO对象
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 据受理编号查询收费状态并且更新订单信息和收费收税信息
     * @date : 2020/5/29 8:47
     */
    void queryAndUpdateSfzt(BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * @param bdcDsfSfssDdxxDTO 第三方订单信息
     * @return 保存数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 接收第三方订单信息
     */
    void saveDsfDdxx(BdcDsfSfssDdxxDTO bdcDsfSfssDdxxDTO);

    /**
     * @param bdcSlSfssDdxxDOList 订单集合
     * @param gzlslid 工作流实例ID
     * @param yjdh 月结单号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据订单编号保存订单信息,订单号不存在新增订单信息,存在状态不一致更新,一致不处理
     */
    void saveAndUpdateDdxxByDdbh(List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList,String gzlslid, String yjdh);

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO对象
     * @retur 返回结果
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 收费信息退款申请
     */
    List<BdcSlSfssDdxxDO> ddxxTksq(BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * @param bdcSlSfssDdxxQO 收费收税订单信息查询QO对象
     * @return 收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 查询退款状态
     */
    BdcSlSfxxDO queryTkqk(BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 合并缴费
     */
    List<BdcSlSfssDdxxDO> hbjf(String jkfs,String gzlslid);

    /**
     * @param bdcSlSfssDdxxQO 不动产收费收税订单信息查询QO对象
     * @return true:已失效 false:未失效
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 验证订单信息是否已失效
     */
    boolean checkDdxxIsSx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * 根据月结单号创建订单
     * @param yjdh 月结单号
     * @param hyzfjftj 合一支付缴费途径（1表示商业银行 2 表示合一支付）
     * @param jkqd 缴款渠道（0 表示线上支付 1 表示线下支付）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 订单信息
     */
    List<BdcSlSfssDdxxDO> createYjSfOrder(String yjdh, String hyzfjftj, String jkqd);

    /**
      * @param bdcSlSfssDdxxQO 不动产收费收税订单信息查询QO对象
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 作废订单信息
      */
    void zfDdxx(BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * 更新月结订单缴费状态
     * <p>根据月结单号，更新当前月结订单下所有收费信息的收费状态</p>
     * @param bdcSlSfssDdxxQO 不动产收费收税订单信息查询QO对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void udpateYjDdxxSfzt(@RequestBody BdcSlSfssDdxxQO bdcSlSfssDdxxQO);

    /**
     * 获取税费总额
     * <p>权利人类别为空时，计算权利人和义务人的税费总额。
     * 税费总额包含：收费金额、核税金额、维修基金金额</p>
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return 税费总额(保留两位小数)
     */
    Double computeSfze(String gzlslid, String qlrlb);
}
