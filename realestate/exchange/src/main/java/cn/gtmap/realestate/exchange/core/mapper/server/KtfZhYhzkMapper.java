package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.KtfZhYhzkDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KtfZhYhzkOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liuyu@gtmap.cn">刘宇</a>
 * @version 1.0, 2017/02/13
 * @description 用海状况信息Mapper
 */
@Component
public interface KtfZhYhzkMapper {
    /**
     * 用海状况信息
     *
     * @param map
     * @return
     */
    List<KtfZhYhzkDO> queryKtfZhYhzkList(Map map);

    /**
     * 用海状况信息
     *
     * @param map
     * @return
     */
    List<KtfZhYhzkOldDO> queryKtfZhYhzkListOld(Map map);

}
