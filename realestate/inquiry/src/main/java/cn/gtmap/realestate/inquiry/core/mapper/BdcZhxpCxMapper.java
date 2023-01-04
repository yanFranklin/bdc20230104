package cn.gtmap.realestate.inquiry.core.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/4
 * @description 综合小屏-中心不同业务的排号情况
 */
public interface BdcZhxpCxMapper {
    /**
     * 通过中心名称查询综合小屏当前呼叫信息
     *
     * @param zxmc
     * @return 当前呼叫信息
     */
    List<Map> listBdcZhxpDqhj(String zxmc);

    /**
     * 通过传入参数查询综合小屏等待呼叫信息
     *
     * @param map
     * @return 等待呼叫信息
     */
    List<Map> listBdcZhxpDdhjHjh(Map map);

    /**
     * 清空窗口信息
     *
     */
    void updateCkxx();

    /**
     * 清空呼叫信息
     */
    void updateHjxx();
}
