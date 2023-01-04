package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.dto.register.BdcShxxPdfDTO;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;


import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/10/30
 * @description 审核信息业务服务类接口
 */
public interface BdcShxxService {
    /**
     * description 新增审核信息，初始化审核信息
     * @param bdcShxx BdcShxxDO
     * @return BdcShxxDO 返回保存的对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     *
     */
    BdcShxxDO insertBdcShxx(BdcShxxDO bdcShxx);

    /**
     * 更新指定节点的审核信息
     * @param bdcShxx BdcShxxDO
     * @return int 返回操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     *
     */
    int updateBdcShxx(BdcShxxDO bdcShxx);

    /**
     * 根据条件查询审核信息，（暂时只实现根据shxxid和gzlslid精确查询）
     * @param bdcShxxQO BdcShxxDO
     * @return List<BdcShxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    List<BdcShxxDO> queryBdcShxx(BdcShxxQO bdcShxxQO);

    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 依据主键更新多条审核信息数据
     */
    int updateShxxList(List<BdcShxxDO> paramList);

    /**
     * @param shxxid
     * @return int
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 删除审核意见和签名信息
     */
    int deleteShxxSign(String shxxid);

    /**
     * @param shxxidList
     * @return int
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量删除审核意见和签名信息
     */
    int deleteShxxSign(List<String> shxxidList);

    /**
     * @param shxxid 审核信息ID
     * @return BdcShxxDO 审核信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询审核信息
     */
    BdcShxxDO queryBdcShxxById(String shxxid);

    /**
     * @param bdcShxxDO 审核信息对象
     * @return int 操作数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    int saveOrUpdateBdcShxx(BdcShxxDO bdcShxxDO);

    /**
     * @param shxxid 审核信息id
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程退回删除审核意见和签名信息，并保存审核结束时间
     */
    int deleteSignAndSaveShjssj(String shxxid);

    /**
     * @param shxxid 审核信息id
     * @return int 操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新审核结束时间（taskId和shxxid一致）
     */
    int updateShjssj(String shxxid);

    /**
     * @param gzlslid     工作流实例ID
     * @param bdcXtMryjDO 系统默认意见
     * @return String 最终获得的默认意见
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据配置的sql生成默认意见
     */
    String generateMryjBySql(String gzlslid, BdcXtMryjDO bdcXtMryjDO);

    /**
     * @param bdcShxxQO 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    List<BdcShxxVO> queryShJdxx(BdcShxxQO bdcShxxQO);

    /**
     * @param shxxid 任务Id
     * @return BdcShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程节点，最新的审核信息以及默认意见
     */
    BdcShxxVO queryMryj(String shxxid);

    /**
     * @param bdcShxxDO 审核信息DO
     * @return 签名信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前人的签名信息
     */
    BdcShxxVO getShxxSign(BdcShxxDO bdcShxxDO);

    /**
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzlslid 工作流实例ID
     * @return {List} 生成的审核信息
     * @description  生成流程项目所有节点审核信息，意见内容采用默认意见
     */
    List<BdcShxxDO> generateShxxOfProInsId(String gzlslid);

    /**
     * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param gzlslids 工作流实例ID集合
     * @param userName 当前用户
     * @return boolean
     * @description  初审和二审是否是一样的审核人
     */
    boolean hasSameShr(List<String> gzlslids,String userName);


    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    void deleteShxxPdf(String processInsId);

    /**
     * @param processInsId 工作流实例ID
     * @return 审核信息列表
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 转发自动签名
     */
    List<BdcShxxDO> zdqm(String processInsId,String currentUserName);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcShxxQOList 审核信息参数
     * @return List<BdcShxxPdfDTO> 审批表信息
     * @description 获取打印审批表PDF
     */
    List<BdcShxxPdfDTO> getBdcSpbPdf(List<BdcShxxQO> bdcShxxQOList);
}
