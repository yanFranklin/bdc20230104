package cn.gtmap.realestate.certificate.core.service;

import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhSyqkQO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/10
 * @description 印制号使用明细管理服务
 */
public interface BdcYzhSymxService {
    /**
     * @param bdcYzhSyqkQO 印制号使用情况信息
     * @return BdcYzhsymxDO 印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存印制号使用明细(一般配合印制号更新使用)
     */
    BdcYzhsymxDO insertBdcYzhSymx(BdcYzhSyqkQO bdcYzhSyqkQO);

    /**
     * @param yzhid 印制号ID
     * @return BdcYzhsymxDO 印制号使用明细
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询印制号的使用明细
     */
    List<BdcYzhsymxDO> queryBdcYzhsymx(String yzhid);
}
