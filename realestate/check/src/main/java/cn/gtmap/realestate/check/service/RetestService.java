package cn.gtmap.realestate.check.service;


import cn.gtmap.realestate.check.core.vo.SelectCgsjVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author lst
 * @version 1.0, 2018-3-13
 * @description 重检服务
 */
public interface RetestService {

    /**
     * 根据错误日志id数组查询数据进行重新检测
     * 支持BdcGzjcLog
     * @author lst
     * @param xmids  项目id数组
     * @return 响应信息
     */
    String retestErrorData(String[] xmids);



    /**
     * 根据时间段来重新检测数据
     * @author lst
     * @param beginTime 起始时间
     * @param finishTime 结束时间
     * @return  响应信息
     */
    String retestTimesData(String beginTime, String finishTime);

    /**
     * 根据规则检测数据，可以选择时间段，不选择时间段时检查全部项目
     * @author ccx
     * @param beginTime
     * @param finishTime
     * @param gzid
     * @return
     */
    String retestRuleData(String beginTime, String finishTime, String gzid,String qxdm);
    /**
     * 获取分组规则信息
     * @param map
     * @return
     */
    List<Map<String,Object>> queryCgsjList(SelectCgsjVo selectCgsjVo);

    /**
     * 导出查询结果
     * @param list
     */
    void export(List<Map<String, Object>> list, HttpServletResponse response, String jcxx);

}
