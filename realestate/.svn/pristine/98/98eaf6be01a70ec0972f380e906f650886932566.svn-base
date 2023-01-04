package cn.gtmap.realestate.etl.core.mapper.exchange;

import cn.gtmap.realestate.common.core.qo.etl.HtbaxxQO;
import cn.gtmap.realestate.common.core.vo.etl.FgHtfyVO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 数据库查询Fg_fy
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-17 17:49
 **/
@Component
public interface FgFyMapper {

    /**
     * @param map
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/12/17 17:50
     */
    List<FgHtfyVO> selectFgHtfyxx(Map map);

    /**
     * @param htbaxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取需要同步的数据
     * @date : 2021/7/7 10:22
     */
    List<FgHtfyVO> selectFgHtfyxxByPage(HtbaxxQO htbaxxQO);
}
