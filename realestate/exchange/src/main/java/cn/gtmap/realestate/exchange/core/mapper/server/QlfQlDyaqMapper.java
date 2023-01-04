package cn.gtmap.realestate.exchange.core.mapper.server;

import cn.gtmap.realestate.common.core.domain.exchange.QlfQlDyaqDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlDyaqOldDO;
import cn.gtmap.realestate.exchange.core.national.BdcZjjzwxx;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 抵押权
 * Created by lst on 2015/11/25
 */
@Component
public interface QlfQlDyaqMapper {

    /**
     * 获取抵押权信息
     *
     * @param map
     * @return
     */
    List<QlfQlDyaqDO> queryQlfQlDyaqList(Map map);

    /**
     * 获取抵押权信息
     *
     * @param map
     * @return
     */
    List<QlfQlDyaqOldDO> queryQlfQlDyaqListOld(Map map);

    /**
     * @param
     * @return
     * @author <a herf="mailto:liwenshuo@gtmap.cn">liwenshuo</a>
     * @description 查询抵押物清单信息
     */
    List<BdcZjjzwxx> queryBdcZjjzwxxList(Map map);
}
