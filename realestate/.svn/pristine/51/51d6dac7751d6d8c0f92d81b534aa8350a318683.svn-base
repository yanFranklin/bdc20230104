package cn.gtmap.realestate.inquiry.service.yancheng;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqDjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqZxsqDO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcZqJsDTO;

import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2021/01/08
 * @description 盐城征迁单元证书锁定、解锁逻辑
 */
public interface BdcZqSdService {
    /**
     * 注销时候锁定不动产单元和证书
     * @param bdcZqZxsqDO 注销申请信息
     */
    void sdBdcdyZs4Zx(BdcZqZxsqDO bdcZqZxsqDO);

    /**
     * 冻结时候同步锁定单元、证书
     * @param bdcZqDjDO
     */
    void sdBdcdyZs4Dj(BdcZqDjDO bdcZqDjDO);

    /**
     * 冻结时候批量同步锁定单元、证书
     * @param zqDjDOList 多个单元冻结信息
     */
    void sdBdcdyZs4Dj(List<BdcZqDjDO> zqDjDOList);

    /**
     * 解锁单元、证书
     * @param bdcZqJsDTO 解锁参数信息
     */
    void jsBdcdyZs(BdcZqJsDTO bdcZqJsDTO);
}
