package cn.gtmap.realestate.etl.core.mapper.exchange;

import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.vo.etl.HtbaMsrVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 查询合同备案买受人数据库接口
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-21 17:53
 **/
@Component
public interface HtbaQlrMapper {

    /**
     * @param null
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/12/21 17:54
     */
    List<HtbaQlrDO> listHtbaQlr(@Param("fwhsindex") String fwhsindex);

    /**
     * @param map
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description
     * @date : 2021/01/18
     */
    List<HtbaQlrDO> listHtbaQlrByParamMap(Map map);

    /**
     * @param map
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询合同备案买受人信息
     * @date : 2021/3/3 17:37
     */
    List<HtbaMsrVO> listMsr(Map map);
}
