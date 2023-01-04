package cn.gtmap.realestate.portal.ui.service;


import java.util.List;
import java.util.Map;

/**
 * 已办列表移交单
 *
 * @author <a href="mailto:lixin1@lixin.com">lixin</a>
 * @version 5:06 下午 2020/10/11
 */
public interface BdcYbYjdService {

    /**
     * 生成移交单编号<br> 蚌埠
     *
     * @param taskids taskids
     * @return {String} 移交单单号
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    String generateYbYjdBh(List<String> taskids);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [yjdTaskList] 已办任务列表
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @description 已办任务列表添加移交单打印状态
     */
    List<Map<String, Object>> addYjdDyzt(List<Map<String, Object>> ybTaskList);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [ybTaskList]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @description 已办任务列表添加移交单号
     */
    List<Map<String, Object>> addYjdh(List<Map<String, Object>> ybTaskList);

}
