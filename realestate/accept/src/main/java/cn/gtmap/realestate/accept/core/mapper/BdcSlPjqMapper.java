package cn.gtmap.realestate.accept.core.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/11/28
 * @description 受理评价器
 */
@Repository
public interface BdcSlPjqMapper {

    /**
     * @description 分页查询
     */
    List<Map<String,Object>> listBdcSlPjTjByPage(Map map);

    /**
     * @description 分组查询
     */
    List<Map<String,Object>> listGroupBdcSlPjTj(Map map);

}
