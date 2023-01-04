package cn.gtmap.realestate.register.core.mapper;

import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.qo.etl.HtbaxxQO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: realestate
 * @description: 数据库查询合同备案商品房信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-18 09:43
 **/
@Repository
public interface HtbaSpfMapper {
    /**
     * @param htbaxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询备案信息
     * @date : 2020/12/18 9:43
     */

    List<HtbaSpfDO> listHtbaSpf(HtbaxxQO htbaxxQO);
}
