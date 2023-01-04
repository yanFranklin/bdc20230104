package cn.gtmap.realestate.supervise.core.mapper;

import cn.gtmap.realestate.supervise.core.dto.LfCxycDTO;
import cn.gtmap.realestate.supervise.core.qo.LfCxycQO;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/31
 * @description 廉防3：查询异常监管Mapper
 */
public interface LfCxycjgMapper {
    /**
     * 统计指定角色下用户平均查询次数
     * @param lfCxycQO 查询参数
     * @return 平均查询次数
     */
    Double countRoleAverageQueryNumber(LfCxycQO lfCxycQO);

    /**
     * 统计每个用户查询次数
     * @param lfCxycQO 查询参数
     * @return 平均查询次数
     */
    List<LfCxycDTO> countUserQueryNumber(LfCxycQO lfCxycQO);

    /**
     * 统计指定字段重复查询次数
     * @param lfCxycQO 业务查询参数
     * @return {List} 统计信息
     */
    List<LfCxycDTO> listXtzdcfcxycChartData(LfCxycQO lfCxycQO);

    /**
     * 统计指定字段重复查询次数,被查询人统计图表
     * @param lfCxycQO 业务查询参数
     * @return {List} 统计信息
     */
    List<LfCxycDTO> listXtzdcfcxycBcxrChartData(LfCxycQO lfCxycQO);

    /**
     * 相同字段重复查询异常
     * @param lfCxycQO 业务查询参数
     * @return {List} 统计信息
     */
    List<LfCxycDTO> listXtzdcfcxycTableData(LfCxycQO lfCxycQO);
}
