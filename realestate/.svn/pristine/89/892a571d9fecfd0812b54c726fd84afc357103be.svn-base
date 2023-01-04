package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcCxqlrDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcCxsqsDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcCxsqsDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcCxsqsQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/8/22
 * @description 查询申请书Service
 */
public interface BdcCxsqsService {
    /**
     * @param bdcCxsqsDTO 查询申请书对象
     * @return Integer 操纵结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存或更新查询申请书信息
     */
    BdcCxsqsDTO saveOrUpdateBdcCxsqs(BdcCxsqsDTO bdcCxsqsDTO);

    /**
     * @param bdcCxsqsQO 查询申请书QO
     * @return 申请书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询申请书信息
     */
    List<BdcCxsqsDTO> queryBdcCxSqs(BdcCxsqsQO bdcCxsqsQO);

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询
     */
    Page<BdcCxsqsDO> listBdcCxsqsPage(Pageable pageable, BdcCxsqsQO bdcCxsqsQO);

    /**
     * @param bdcCxqlrDOList 查询权利人信息
     * @param sqsid          查询申请书ID
     * @return List<BdcCxqlrDO> 保存的权利人信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量保存查询申请权利人新信息
     */
    List<BdcCxqlrDO> batchSaveCxQlr(String sqsid, List<BdcCxqlrDO> bdcCxqlrDOList);
}
