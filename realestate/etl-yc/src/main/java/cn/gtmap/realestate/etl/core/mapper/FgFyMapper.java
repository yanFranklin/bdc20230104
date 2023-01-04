package cn.gtmap.realestate.etl.core.mapper;

import cn.gtmap.realestate.common.core.qo.etl.HtbaxxQO;
import cn.gtmap.realestate.common.core.vo.etl.FgHtfyVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 数据库查询Fg_fy
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-17 17:49
 **/
@Repository
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

    /**
     * @param map
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取已撤销需要同步的的Fg_fy数据
     * @date : 2022/11/21 17:47
     */
    List<FgHtfyVO> selectYcxFghtfy(Map map);
}
