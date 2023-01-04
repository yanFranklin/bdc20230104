package cn.gtmap.realestate.accept.service;


import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/11/8
 * @description 纳税联系单接口服务
 */
public interface BdcNslxdService {

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: processInsId 工作流实例id
     * @return: List<Map < String, String>> 纳税联系单数据
     * @description 通过工作流实例id获取纳税联系单表单数据，返回值为List类型
     */
    List<Map<String,String>> getNslxdData(String processInsId);

}
