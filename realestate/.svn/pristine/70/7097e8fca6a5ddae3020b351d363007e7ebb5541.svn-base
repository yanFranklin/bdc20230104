package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDjxlPzQO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/25
 * @description 登记小类配置
 */
@Repository
public interface BdcDjxlPzMapper {

    /**
     * @param bdcDjxlPzDO 登记小类配置
     * @return 最大顺序号
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取登记小类配置最大顺序号
     */
    int queryDjxlPzMaxSxh(BdcDjxlPzDO bdcDjxlPzDO);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询是否上报流程定义id
     * @date : 2022/11/29 17:10
     */
    List<String> listSfsbLcdyids(BdcDjxlPzQO bdcDjxlPzQO);
}
