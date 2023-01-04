package cn.gtmap.realestate.portal.ui.service;

import cn.gtmap.realestate.portal.ui.core.dto.BdcBtxpzDTO;
import cn.gtmap.realestate.portal.ui.core.vo.BdcBtxyzVO;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/4/11
 * @description 必填项验证业务类
 */
public interface BdcBtxYzService {
    /**
     * @param formViewKey gzlslid
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 组织配置sql 和 参数
     */
    BdcBtxpzDTO queryFormConfig(String formViewKey, String gzlslid,String taskId);

    /**
     * @param bdcBtxpzDTO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询比较必填项字段
     */
    List<BdcBtxyzVO> queryBdcBtx(BdcBtxpzDTO bdcBtxpzDTO);

    /**
     * @param
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证必填项
     */
    List<BdcBtxyzVO> checkBtx(String formViewKey, String gzlslid, String taskId);
}
