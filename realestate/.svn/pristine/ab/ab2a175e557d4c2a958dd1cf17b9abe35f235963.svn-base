package cn.gtmap.realestate.supervise.service;

import cn.gtmap.realestate.common.core.domain.BdcXtCxLogDO;
import cn.gtmap.realestate.supervise.core.dto.LfCxycDTO;
import cn.gtmap.realestate.supervise.core.qo.LfCxycQO;
import cn.gtmap.realestate.supervise.core.qo.LfLogQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/31
 * @description 查询异常监管逻辑定义
 */
public interface LfCxycjgService {
    /**
     * 统计岗位查询异常（分页台账）
     * @param pageable 分页参数
     * @param lfCxycQO 业务查询参数
     * @return 异常查询统计信息
     */
    Page<LfCxycDTO> listGwcxycTableData(Pageable pageable, LfCxycQO lfCxycQO);

    /**
     * 统计岗位查询异常（柱状图，封装展示数据）
     * @param lfCxycQO 业务查询参数
     * @return 异常查询统计信息
     */
    List<Map> listGwcxycChartData(LfCxycQO lfCxycQO);

    /**
     * 统计岗位查询异常数据
     * @param lfCxycQO 业务查询参数
     * @return 异常查询统计信息
     */
    List<LfCxycDTO> listGwcxycData(LfCxycQO lfCxycQO);

    /**
     * 查询指定用户查询操作日志记录
     * @param pageable 分页参数
     * @param lfLogQO  查询参数
     * @return 用户查询日志
     */
    Page<BdcXtCxLogDO> listUserQueryLog(Pageable pageable, LfLogQO lfLogQO);

    /**
     * 相同字段重复查询异常（分页台账）
     * @param pageable 分页参数
     * @param lfCxycQO 业务查询参数
     * @return 异常查询信息
     */
    Page<LfCxycDTO> listXtzdcfcxycTableData(Pageable pageable, LfCxycQO lfCxycQO);

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
}
