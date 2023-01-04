package cn.gtmap.realestate.etl.service;

import cn.gtmap.realestate.common.core.vo.etl.FgHtfyVO;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 视图fg_fyxx
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-12-17 17:46
 **/
public interface FgFyService {

    /**
     * @param map
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询Fg_fy数据
     * @date : 2020/12/17 17:47
     */
    List<FgHtfyVO> selectFgHtfyxx(Map map);
}
