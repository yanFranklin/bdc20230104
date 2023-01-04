package cn.gtmap.realestate.engine.service;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBdsTsxxDO;
import org.kie.api.KieBase;

import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/8
 * @description 不动产规则表达式逻辑接口定义
 */
public interface BdcGzBdsService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsTsxxDO  表达式、提示信息
     * @param zgzSjlResult 数据流执行结果
     * @return {String} 规则校验内容
     * @description  生成进行规则引擎校验的DRL内容
     */
    <T> String getDroolsDRL(BdcGzBdsTsxxDO bdcGzBdsTsxxDO, Map<String, T> zgzSjlResult);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzbds  规则表达式
     * @param zgzSjlResult 数据流结果集
     * @return {String} Drools表达式
     * @description 将数据流结果判断表达式解析成对应的Drools引擎表达式
     */
    <T> String resolveDroolsExpression(String gzbds, Map<String, T> zgzSjlResult);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param drool 验证表达式转换为drools的表达式
     * @param bdsid 验证表达式ID
     * @return {InternalKnowledgeBase} Drools验证
     * @description 获取每个规则验证表达式对应的kbase对象
     */
    KieBase getDroolsKbase(String drool, String bdsid);
}
