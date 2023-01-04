package cn.gtmap.realestate.natural.service;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyShxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtMryjDO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyShxxQO;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyShxxVO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/10/30
 * @description 审核信息业务服务类接口
 */
public interface ZrzyShxxService {
    /**
     * description 新增审核信息，初始化审核信息
     *
     * @param zrzyShxxDO
     * @return BdcShxxDO 返回保存的对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    ZrzyShxxDO insertBdcShxx(ZrzyShxxDO zrzyShxxDO);

    /**
     * 更新指定节点的审核信息
     *
     * @param bdcShxx BdcShxxDO
     * @return int 返回操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    int updateBdcShxx(ZrzyShxxDO zrzyShxxDO);

    /**
     * 根据条件查询审核信息，（暂时只实现根据shxxid和gzlslid精确查询）
     *
     * @param bdcShxxQO BdcShxxDO
     * @return List<BdcShxxDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     */
    List<ZrzyShxxDO> queryZrzyShxx(ZrzyShxxQO zrzyShxxQO);

    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 依据主键更新多条审核信息数据
     */
    int updateShxxList(List<ZrzyShxxDO> paramList);

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
    ZrzyShxxDO queryBdcShxxById(String shxxid);

    /**
     * @param bdcShxxDO 审核信息对象
     * @return int 操作数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    int saveOrUpdateBdcShxx(ZrzyShxxDO zrzyShxxDO);

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
    String generateMryjBySql(String gzlslid, ZrzyXtMryjDO bdcXtMryjDO);

    /**
     * @param bdcShxxQO 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    List<ZrzyShxxVO> queryShJdxx(ZrzyShxxQO bdcShxxQO);

    /**
     * @param shxxid 任务Id
     * @return BdcShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程节点，最新的审核信息以及默认意见
     */
    ZrzyShxxVO queryMryj(String shxxid);

    /**
     * @param bdcShxxDO 审核信息DO
     * @return 签名信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前人的签名信息
     */
    ZrzyShxxVO getShxxSign(ZrzyShxxDO zrzyShxxDO);

    /**
     * @param gzlslid 工作流实例ID
     * @return {List} 生成的审核信息
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成流程项目所有节点审核信息，意见内容采用默认意见
     */
    List<ZrzyShxxDO> generateShxxOfProInsId(String gzlslid);


    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    void deleteShxxPdf(String processInsId);


}
