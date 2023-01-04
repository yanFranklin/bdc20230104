package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXzxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXzxxQO;

import java.util.List;

/**
 * @program: realestate
 * @description: 流程修正相关服务
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2021-05-25 17:20
 **/
public interface BdcSlXzxxService {

    /**
     * @param bdcSlXzxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 查询流程修正
     * @date : 2021/9/23 17:21
     */
    BdcSlXzxxDO queryBdcSlXzxx(BdcSlXzxxDO bdcSlXzxxDO);

    /**
     * @param bdcSlXzxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存需求流转数据
     * @date : 2021/9/23 17:29
     */
    BdcSlXzxxDO saveBdcSlXzxx(BdcSlXzxxDO bdcSlXzxxDO);

    /**
     * @param xzxxid,xmid
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除流程修正信息
     * @date : 2021/9/23 17:37
     */
    int deleteBdcSlXzxx(String xzxxid, String xmid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @description 删除流程修正信息
     */
    void deleteBdcSlXzxxByGzlslid(String gzlslid);

    /**
     * @param processInsId 工作流实例ID
     * @param bdcSlCshDTO
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 用于创建修正流程时，插入数据到BDC_SL_XZXX
     */
    void cshBdcXzxx(String processInsId, BdcSlCshDTO bdcSlCshDTO);

    /**
     * @param bdcSlXzxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询修正信息
     * @date : 2021/9/27 19:23
     */
    List<BdcSlXzxxDO> listBdcSlXzxx(BdcSlXzxxQO bdcSlXzxxQO);
}
