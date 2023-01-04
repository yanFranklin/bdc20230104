package cn.gtmap.realestate.engine.service;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzCodeDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/1
 * @description 规则动态代码处理逻辑
 */
public interface BdcGzDmService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDO  子规则
     * @param zgzSjlResult 数据流执行结果
     * @return {List} 规则提示信息
     * @description 执行配置的动态规则验证代码获取提示信息结果
     */
    <T> List<String> executeJavaCode(BdcGzZgzDO bdcGzZgzDO, Map<String, T> zgzSjlResult);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzCodeDTO  校验代码信息
     * @description  校验子规则动态代码
     */
    String checkCode(BdcGzCodeDTO bdcGzCodeDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO  子规则信息
     * @description  更新校验代码缓存
     */
    void updateJavaCodeCompileCache(BdcGzZgzDTO bdcGzZgzDTO);
}
