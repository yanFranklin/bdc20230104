package cn.gtmap.realestate.inquiry.service;


import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/4
 * @description 综合小屏-中心不同业务的排号情况
 */
public interface BdcZhxpCxService {

    /**
     * 通过区县代码查询当前呼叫信息
     *
     * @param zxmc 中心名称
     * @return 当前呼叫信息
     */
    List<Map> listBdcZhxpDqhj(String zxmc);

    /**
     * 通过区县代码和业务类型查询等待呼叫信息
     *
     * @param zxmc 中心名称
     * @return 等待呼叫信息
     */
    List<Map> listBdcZhxpDdhj(String zxmc);

}
