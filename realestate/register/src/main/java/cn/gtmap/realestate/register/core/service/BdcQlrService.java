package cn.gtmap.realestate.register.core.service;

import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/4/25
 * @description 权利人更新服务接口
 */
public interface BdcQlrService {
    /**
     * @param bdcGyqkDTO 不动产共有情况DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将共有情况更新到权利人表中
     */
    int updateQlrGyqk(BdcGyqkDTO bdcGyqkDTO);



    /**
     * @param bdcRyzdDTO 冗余字段
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 更新权利人表冗余字段
     */
    int updateQlrRyzd(BdcRyzdDTO bdcRyzdDTO);

    /**
     * @param bdcGyqkDTO 共有情况DTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新权利人的共有情况（权利人名称一致）
     */
    int updateQlrGyqkPl(BdcGyqkDTO bdcGyqkDTO);
}
