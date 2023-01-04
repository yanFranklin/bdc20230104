package cn.gtmap.realestate.etl.core.mapper.exchange;

import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.qo.etl.HtbaxxQO;
import cn.gtmap.realestate.common.core.dto.etl.HtbaSpfWqxxDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询合同备案和房屋信息
     * @date : 2020/12/16 9:50
     */
    List<HtbaSpfDO> listHtbaSpfxx(HtbaxxQO htbaxxQO);


    /**
     * 根据工作流实例ID,获取商品房网签信息
     *
     * @param gzlslid 工作流实例ID
     * @return 商品房网签信息
     */
    List<HtbaSpfWqxxDTO> queryHtbaSpfWqxx(String gzlslid);

    /**
     * 根据htbh获取商品房网签信息
     * @param htbh
     * @return 商品房网签信息
     * */
    List<HtbaSpfWqxxDTO> listHtbaSpfWqxx(String htbh);

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
