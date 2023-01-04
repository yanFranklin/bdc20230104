package cn.gtmap.realestate.etl.core.mapper.exchange;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program:
 * @description: 合同备案接口与数据库交互
 * @author: <a href="mailto:duwei@gtmap.cn">duwei</a>
 * @create: 2022-11-22 9:43
 **/
@Component
public interface BdcHtbaMapper {

    /**
     * @param
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 查询合同备案数据
     * @date : 2022-11-22 9:43
     */
    List<Map> listHtbaByPage(Map paramMap);

}
