package cn.gtmap.realestate.exchange.core.mapper.server.bdcdsflog;


import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface BdcDsfLogMapper {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description 第三方日志各系统请求统计
     */
    List<Map> countBdcDsfLogByGxbmbz(Map paramMap);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description 第三方日志总计
     */
    Map countBdcDsfLogZj(Map paramMap);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description 第三方日志各系统调用确定区间段数据
     */
    List<Map> countBdcDsfLogWeek(Map paramMap);

    /**
     *
     * @return
     * @description 初始化统计所需日期
     */
    Map<String,String> countWeek();

    /**
     *
     * @return
     * @description 第三方日志各系统二级各系统调用明细
     */
    List<Map> countGxtMx(Map paramMap);

    /**
     *
     * @param paramMap
     * @return
     * @description 第三方日志 导出excel 获取各分中心数据
     */
    List<Map> countBdcDsfLogByFzx(Map paramMap);

    /**
     *
     * @param paramMap
     * @return
     * @description 第三方日志 导出excel 获取各分中心对应明细接口数据
     */
    List<Map> countGxtMxFzx(Map paramMap);
}
