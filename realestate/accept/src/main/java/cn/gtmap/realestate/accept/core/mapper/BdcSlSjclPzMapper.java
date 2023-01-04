package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclPzDO;
import org.springframework.stereotype.Repository;

/**
 * @program: realestate
 * @description: 收件材料配置
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-19 15:54
 **/
@Repository
public interface BdcSlSjclPzMapper {
    /**
     * @param bdcSlSjclPzDO 收件材料配置
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取收件材料最大序号
     */
    Integer querySjclPzMaxSxh(BdcSlSjclPzDO bdcSlSjclPzDO);
}
