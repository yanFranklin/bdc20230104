package cn.gtmap.realestate.natural.core.mapper;

import cn.gtmap.realestate.common.core.domain.natural.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.domain.natural.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyDtcxDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version: V1.0, 2019/07/16
 * @description: 动态查询相关sql映射
 */
@Repository
public interface ZrzyDtcxMapper {
    /**
     * 传入sql直接执行
     * 可用于判断sql正确性
     *
     * @param sql 输入sql，此sql不再处理直接跑，所以必须是可执行的sql
     * @return java.util.List<java.util.Map>
     * @date 2019/07/16
     * @author hanyi
     */
    List<Map> testSql(@Param("sql") String sql);

    /**
     * 通过查询代号获取查询信息
     *
     * @param cxdh 查询代号
     * @return cn.gtmap.realestate.common.core.dto.inquiry.BdcDtcxDTO
     * @date 2019/07/16
     * @author hanyi
     */
    ZrzyDtcxDTO getDtcxByCxdh(String cxdh);

    /**
     * 通过查询代号获取对应的查询条件配置
     *
     * @param cxdh 查询代号
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxtjDO>
     * @date 2019/07/16
     * @author hanyi
     */
    List<DtcxCxtjDO> getCxtjConfig(String cxdh);

    /**
     * 通过查询代号获取对应的查询结果列配置
     *
     * @param cxdh
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO>
     * @date 2019/07/16
     * @author hanyi
     */
    List<DtcxCxjgDO> getCxjgConfig(String cxdh);

    /**
     * 传入sql直接执行
     * 可用于判断sql正确性
     *
     * @param dataMap 查询条件集和查询sql
     * @return java.util.List<java.util.Map>
     * @date 2019/07/16
     * @author hanyi
     */
    List<Map> getViewList(Map dataMap);

    /**
     * 根据cxid查询sql
     *
     * @param cxid
     * @return
     */
    String getSqlBycxid(String cxid);

    List<Map> getResultList(String sql);

}