package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlShxxDO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlShxxQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>"
 * @version 1.0, 2021/2/23
 * @description 受理审核信息业务服务类接口
 */
public interface BdcSlShxxService {

    /**
     * 更新指定节点的审核信息
     * @param bdcShxx BdcSlShxxDO
     * @return int 返回操作的数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *
     */
    int updateBdcShxx(BdcSlShxxDO bdcShxx);

    /**
     * 根据条件查询审核信息，（暂时只实现根据shxxid和gzlslid精确查询）
     * @param bdcSlShxxQO BdcSlShxxDO
     * @return List<BdcSlShxxDO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    List<BdcSlShxxDO> queryBdcShxx(BdcSlShxxQO bdcSlShxxQO);

    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 依据主键更新多条审核信息数据
     */
    int updateShxxList(List<BdcSlShxxDO> paramList);

    /**
     * @param shxxid
     * @return int
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    int deleteShxx(String shxxid);

    /**
     * @param shxxidList
     * @return int
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 批量删除审核意见和签名信息
     */
    int deleteShxxSign(List<String> shxxidList);

    /**
     * @param shxxid 审核信息ID
     * @return BdcSlShxxDO 审核信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据主键查询审核信息
     */
    BdcSlShxxDO queryBdcShxxById(String shxxid);

    /**
     * @param BdcSlShxxDO 审核信息对象
     * @return int 操作数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    int saveOrUpdateBdcShxx(BdcSlShxxDO BdcSlShxxDO);

    /**
     * @param shxxid 审核信息id
     * @return 更新数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 流程退回删除审核意见和签名信息，并保存审核结束时间
     */
    int deleteSignAndSaveShjssj(String shxxid);

    /**
     * @param shxxid 审核信息id
     * @return int 操作的数据量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新审核结束时间（taskId和shxxid一致）
     */
    int updateShjssj(String shxxid);


    /**
     * @param bdcSlShxxQO 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    List<BdcShxxVO> queryShJdxx(BdcSlShxxQO bdcSlShxxQO);

    /**
     * @param BdcSlShxxDO 审核信息DO
     * @return 签名信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 获取当前人的签名信息
     */
    BdcShxxVO getShxxSign(BdcSlShxxDO BdcSlShxxDO);

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除审核信息
     * @date : 2021/9/22 21:25
     */
    int deleteShxxByGzlslid(String gzlslid);


}
