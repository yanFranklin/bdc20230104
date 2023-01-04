package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.QltFwFdcqYzDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QltFwFdcqYzOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 房地产权_独幢、层、套、间房屋业务信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface QltFwFdcqYzMapper {
    /**
     * 获取房地产权_独幢、层、套、间房屋业务信息
     *
     * @param map
     * @return
     */
    List<QltFwFdcqYzDO> queryQltFwFdcqYzList(Map map);

    /**
     * 获取房地产权_独幢、层、套、间房屋业务信息
     *
     * @param map
     * @return
     */
    List<QltFwFdcqYzOldDO> queryQltFwFdcqYzListOld(Map map);
}
