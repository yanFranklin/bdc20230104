package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcDjyySjclPzDO;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/2/14
 * @description 登记原因收件材料配置
 */
@Repository
public interface BdcDjyySjclPzMapper {

    /**
     * @param bdcDjyySjclPzDO 登记原因收件材料配置
     * @author <a href ="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取收件材料登记原因配置最大序号
     */
    int queryDjyySjclPzMaxSxh(BdcDjyySjclPzDO bdcDjyySjclPzDO);
}
