package cn.gtmap.realestate.etl.core.mapper.fcjy;

import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @description: 房产交易合同信息查询数据库
 * @author: <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @create: 2022-10-27 15:24
 **/
@Component
public interface FcjyHtxxMapper {

    /**
     * @description 根据条件查询房产交易备案合同信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/27 19:49
     * @param paramMap
     * @return List<HtbaSpfDO>
     */
    List<HtbaSpfDO> listFcjySpfHtbaxx(Map paramMap);

    /**
     * @description 根据条件查询房产交易备案权利人信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/27 19:49
     * @param paramMap
     * @return List<HtbaQlrDO>
     */
    List<HtbaQlrDO> listFcjySpfHtbaQlrxx(Map paramMap);
}
