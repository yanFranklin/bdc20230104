package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZqZxsqDO;

import java.util.List;
import java.util.Set;

/**
 * *
 *
 * @author <a href="mailto:zhongjinpeng@gtmap.cn>zhongjinpeng</a>"
 * @version 1.0, 2020/12/16
 * @description 盐城征迁
 */
public interface BdcZqZxsqMapper {

    /**
     * 更新注销申请
     * 支持更新 : 审核状态、注销原因、注销申请人、注销申请人ID、注销申请时间、删除原因、删除人、删除人ID、删除时间、备注
     * @author <a href="mailto:zhongjinpeng@gtmap.cn>zhongjinpeng</a>"
     * @param bdcZqZxsqDO
     */
    void updateZxsq(BdcZqZxsqDO bdcZqZxsqDO);

    /**
     * 查询证号对应的证书记录
     * @param list 证号集合
     * @return
     */
    List<BdcZsDO> queryBdcZsByZhList(List<String> list);
}
