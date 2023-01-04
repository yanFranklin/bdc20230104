package cn.gtmap.realestate.etl.core.mapper.bdcdj;

import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 合同备案商品房查询数据库
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-14 15:24
 **/
@Component
public interface HtbaSpfMapper {

    /**
     * @description 查询htba_spf baid最大值
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/28 9:54
     * @return int
     */
    String queryMaxBaid();

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询未注销合同备案和房屋信息
     * @date : 2020/12/16 9:50
     */
    List<HtbaSpfDO> listWzxHtbaSpfxx(Map paramMap);

}
