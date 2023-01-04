package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzQqjlDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/5/10
 */
@Mapper
public interface BdcDzzzQqjlMapper {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description
     */
    void insertBdcDzzzQqjl(BdcDzzzQqjlDo bdcDzzzQqjlDo);

    /**
     *
     * @param map
     * @return
     * @description 月请求次数
     */
    List<Map> countRecordMonth(Map map);

    /**
     *
     * @param map
     * @return
     * @description 年请求次数
     */
    List<Map> countRecordYear(Map map);

    /**
     *
     * @param map
     * @return
     * @description 周请求次数
     */
    List<Map> countRecordWeek(Map map);

    /**
     *
     * @param map
     * @return
     * @description 部门请求次数
     */
    List<Map> countDepart(Map map);

    /**
     *
     * @param map
     * @return
     * @description 接口请求占比
     */
    List<Map> countPortPercent(Map map);

    /**
     *
     * @param map
     * @return
     * @description 各行政区接口请求情况
     */
    List<Map> countRegionUse(Map map);
}
