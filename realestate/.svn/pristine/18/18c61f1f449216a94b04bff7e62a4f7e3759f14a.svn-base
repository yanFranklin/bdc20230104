package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmPzDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxmPlxgDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxmQO;

import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理收费项目数据服务
 */
public interface BdcSlSfxmService {
    /**
     * @param sfxmid 收费项目ID
     * @return 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费项目ID获取不动产受理收费项目
     */
    BdcSlSfxmDO queryBdcSlSfxmBySfxmid(String sfxmid);

    /**
     * @param sfxxid 收费信息ID
     * @return 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID获取不动产受理收费项目
     */
    List<BdcSlSfxmDO> listBdcSlSfxmBySfxxid(String sfxxid);

    /**
     * @param bdcSlSfxmPzDOList 不动产受理收费项目配置
     * @return 不动产受理收费项目
     * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
     * @description 初始化不动产受理收费项目
     */
    List<BdcSlSfxmDO> listCshBdcSlSfxm(List<BdcSlSfxmPzDO> bdcSlSfxmPzDOList,String sfxxid);

    /**
     * @param bdcSlSfxmDO 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理收费项目
     */
    BdcSlSfxmDO insertBdcSlSfxm(BdcSlSfxmDO bdcSlSfxmDO);

    /**
     * @param bdcSlSfxmDO 不动产受理收费项目
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理收费项目
     */
    Integer updateBdcSlSfxm(BdcSlSfxmDO bdcSlSfxmDO);

    /**
     * @param bdcSlSfxmDO 不动产受理收费项目
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存不动产受理收费项目
     */
    Integer saveOrUpdateBdcSlSfxm(BdcSlSfxmDO bdcSlSfxmDO);

    /**
     * @param bdcSlSfxmDOList 不动产受理收费项目集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 批量保存不动产受理收费项目
     */
    void batchUpdateBdcSlSfxm(List<BdcSlSfxmDO> bdcSlSfxmDOList);

    /**
     * @param sfxmid 收费项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费项目ID删除不动产受理收费项目
     */
    Integer deleteBdcSlSfxmBySfxmid(String sfxmid);

    /**
     * @param sfxxid 收费信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID删除不动产受理收费项目
     */
    Integer deleteBdcSlSfxmBySfxxid(String sfxxid);

    /**
     * @param bdcSlSfxmQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询获取收费项目
     * @date : 2020/11/30 11:33
     */
    List<BdcSlSfxmDO> listSfxmBySfxmdm(BdcSlSfxmQO bdcSlSfxmQO);

    /**
     * 批量修改收费项目内容
     * <p>批量修改多个流程的收费项目信息</p>
     * @param bdcSlSfxmPlxgDTO 不动产收费项目批量修改信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void plxgSfxm(BdcSlSfxmPlxgDTO bdcSlSfxmPlxgDTO);

    /**
     * 根据登记原因、sfxxid获取不动产受理收费项目
     * @param sfxxid
     * @param djyy
     * @return 不动产受理收费项目list
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     */
    List<BdcSlSfxmDO> listBdcSlSfxmBySfxxidAndDjyy(String sfxxid, String djyy);

    /**
     * 批量修改收费项目内容
     * <p>批量修改多个流程的收费项目信息收费状态</p>
     * @param sfxxidList 收费信息ID
     *
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void plxgSfxmSfzt(List<String> sfxxidList, Integer sfzt);

    /**
     * 批量修改收费项目登记费的实收金额
     * <p>
     *     慎重使用：
     *     该接口用于批量修改减免原因为小微企业时，更改收费项目的登记费实收金额为：0
     * </p>
     * @param sfxxidList 收费信息ID
     * @param ssje 实收金额
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void plxgDjfSfxmSsje(List<String> sfxxidList, Double ssje);

    /**
     * 根据sfxmbz分组查询月结的收费项目信息
     * @param xmid 项目ID
     * @param sqrlb 申请人类别
     * @param sfxxidList 收费信息ID集合
     * @param sfyj 是否月结
     * @param hjfk 合计非空
     * @return 收费项目信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcSlSfxmDO> queryYjSfxmxxGroupBySfxmbz(String xmid, String sqrlb, List<String> sfxxidList, Integer sfyj, boolean hjfk);
}
