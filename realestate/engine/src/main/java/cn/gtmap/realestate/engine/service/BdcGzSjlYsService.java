package cn.gtmap.realestate.engine.service;

import cn.gtmap.realestate.engine.core.bo.BdcGzBdsYsBO;

import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/15
 * @description 不动产规则数据流中元素处理逻辑接口定义
 */
public interface BdcGzSjlYsService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsYsBO 前一个元素
     * @description  解析前一个元素组成内容
     */
    void setPreElementItems(BdcGzBdsYsBO bdcGzBdsYsBO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsYsBO 前一个元素
     * @description  设置前一个元素结构类型
     */
    void setPreElementType(BdcGzBdsYsBO bdcGzBdsYsBO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsYsBO 前一个元素
     * @param zgzSjlResult 数据流结果集
     * @return  {Object}  元素对象
     * @description  从数据流结果集中获取前一个元素对应的实际值
     */
    <T> void setPreElementValObj(BdcGzBdsYsBO bdcGzBdsYsBO, Map<String, T> zgzSjlResult);
}
