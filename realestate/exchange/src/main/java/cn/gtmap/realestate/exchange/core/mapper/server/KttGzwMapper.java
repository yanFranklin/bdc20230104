package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.KttGzwDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.KttGzwOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2017/7/20
 * @description 构筑物信息
 */
@Component
public interface KttGzwMapper {
    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取构筑物信息
     */
    List<KttGzwDO> queryKttGzwList(Map map);

    /**
     * @param map 参数
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取构筑物信息
     */
    List<KttGzwOldDO> queryKttGzwListOld(Map map);
}
