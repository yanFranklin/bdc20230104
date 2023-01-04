package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.ZttGyQlrDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.ZttGyQlrOldDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 权利人业务信息接口
 *
 * @author lst
 * @version V1.0
 */
@Component
public interface ZttGyQlrMapper {
    /**
     * 获取权利人业务信息
     *
     * @param map
     * @return
     */
    List<ZttGyQlrDO> queryZttGyQlrList(Map map);

    /**
     * 获取权利人业务信息
     *
     * @param map
     * @return
     */
    List<ZttGyQlrOldDO> queryZttGyQlrListOld(Map map);

    /**
     * @param map
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 获取qlrid
     */
    String getQlrid(Map map);

    /**
     * @param qlrid 权利人id
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 根据权利人id找对应产权证信息
     */
    List<Map> queryBdcZs(String qlrid);
}
