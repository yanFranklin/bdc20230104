package cn.gtmap.realestate.inquiry.service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/10
 * @description 综合大屏-所有中心办理业务的集合展示
 */
public interface BdcZhdpCxService {
    /**
     * 查询当前排号信息
     *
     * @return 当前呼叫信息
     */
    List<Map> listBdcZhdp();


}
