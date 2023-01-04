package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxDTO;

import java.util.List;

/**
 * 虚拟不动产单元号服务接口
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/10/09 18:17
 */
public interface BdcXnBdcdyhService {

    /**
     * 生成虚拟不动产单元号，修改项目表中对应的 bdcdyh
     * 虚拟号设置规则： bdcdyhPrefix + 0000000X（X 表示顺序号）
     *
     * @param bdcdyhPrefix 不动产单元号前缀
     * @param bdcSlxxDTO   不动产受理信息DTO
     * @return List<BdcXmDO> 初始化后的项目信息
     */
    List<BdcXmDO> updateXnbdcdyh(String bdcdyhPrefix, BdcSlxxDTO bdcSlxxDTO) throws Exception;
}
