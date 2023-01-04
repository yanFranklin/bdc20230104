package cn.gtmap.realestate.certificate.core.service;

import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYzhQO;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/10/9
 * @description 生成证书所需印制号的服务接口
 */
public interface BdcYzhScService {
    /**
     * @param num 需要获取的印制号的数量
     * @return List<BdcYzhDTO> 印制号的信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量获取印制号
     */
    List<BdcYzhDTO> queryBatchYzh(int num, BdcYzhQO bdcYzhQO);
}
