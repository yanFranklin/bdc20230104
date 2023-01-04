package cn.gtmap.realestate.engine.service.impl.code;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzCodeDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.engine.core.service.BdcGzZgzDaoService;
import cn.gtmap.realestate.engine.service.BdcGzDmService;
import cn.gtmap.realestate.engine.util.JavaStringCompiler;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/1
 * @description  规则动态代码处理逻辑
 */
@Service
public class BdcGzDmServiceImpl implements BdcGzDmService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcGzDmServiceImpl.class);

    /**
     * 子规则DAO
     */
    @Autowired
    private BdcGzZgzDaoService bdcGzZgzDaoService;

    /**
     * 缓存子规则与其对应动态代码Class
     */
    private ConcurrentHashMap<String, Class<?>> bdcGzJydmClassMap = new ConcurrentHashMap<>(10);


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 初始化规则校验代码生成Class对象并缓存
     */
    @PostConstruct
    public void setBdcGzJydmClass(){
        List<BdcGzZgzDO> bdcGzZgzDOList = bdcGzZgzDaoService.listBdcGzZgzDO();
        if(CollectionUtils.isEmpty(bdcGzZgzDOList)){
            return;
        }

        if(bdcGzJydmClassMap.isEmpty()){
            bdcGzJydmClassMap = new ConcurrentHashMap<>(10);
        }
        try {
            for(BdcGzZgzDO bdcGzZgzDO : bdcGzZgzDOList){
                if(StringUtils.isNotBlank(bdcGzZgzDO.getJydm())){
                    JavaStringCompiler compiler = new JavaStringCompiler();
                    LOGGER.debug("校验子规则ID:{}", bdcGzZgzDO.getGzid());
                    Map<String, byte[]> results = compiler.compile("RuleCheck.java", bdcGzZgzDO.getJydm());
                    Class<?> clazz = compiler.loadClass("cn.gtmap.realestate.engine.service.impl.RuleCheck", results);
                    bdcGzJydmClassMap.put(bdcGzZgzDO.getGzid(), clazz);
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.error("初始化规则校验代码失败，{}，{}", e.getMessage(), e);
        }
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDO  子规则
     * @param zgzSjlResult 数据流执行结果
     * @return {List} 规则提示信息
     * @description 执行配置的动态规则验证代码获取提示信息结果
     */
    @Override
    public <T> List<String> executeJavaCode(BdcGzZgzDO bdcGzZgzDO, Map<String, T> zgzSjlResult) {
        try {
            Class<?> clazz;
            if(!bdcGzJydmClassMap.isEmpty() && null != bdcGzJydmClassMap.get(bdcGzZgzDO.getGzid())){
                clazz = bdcGzJydmClassMap.get(bdcGzZgzDO.getGzid());
            } else {
                JavaStringCompiler compiler = new JavaStringCompiler();
                Map<String, byte[]> results = compiler.compile("RuleCheck.java", bdcGzZgzDO.getJydm());
                clazz = compiler.loadClass("cn.gtmap.realestate.engine.service.impl.RuleCheck", results);

                if(bdcGzJydmClassMap.isEmpty()){
                    bdcGzJydmClassMap = new ConcurrentHashMap<>(10);
                }
                bdcGzJydmClassMap.put(bdcGzZgzDO.getGzid(), clazz);
            }
            Method method = clazz.getMethod("getTsxxList", Map.class);

            Object obj = clazz.newInstance();
            method.setAccessible(true);
            return (List<String>) method.invoke(obj, zgzSjlResult);
        } catch (Exception e) {
            LOGGER.error("子规则：{}，动态代码验证异常，{}", bdcGzZgzDO.getGzid(), e.getCause());
            throw new AppException("子规则动态代码验证异常");
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzCodeDTO  校验代码信息
     * @description  校验子规则动态代码
     */
    @Override
    public String checkCode(BdcGzCodeDTO bdcGzCodeDTO) {
        if(null == bdcGzCodeDTO || StringUtils.isBlank(bdcGzCodeDTO.getCode()) || StringUtils.isBlank(bdcGzCodeDTO.getParam())){
            return "验证失败，原因：未定义验证代码内容或测试参数！";
        }

        Map param;
        try {
            param = JSON.parseObject(bdcGzCodeDTO.getParam(), Map.class);
        } catch (Exception e) {
            return "验证失败，原因：测试参数解析失败，请检查参数格式是否正确！";
        }

        try {
            JavaStringCompiler compiler = new JavaStringCompiler();
            Map<String, byte[]> results = compiler.compile("RuleCheck.java", bdcGzCodeDTO.getCode());
            Class<?> clazz = compiler.loadClass("cn.gtmap.realestate.engine.service.impl.RuleCheck", results);
            Method method = clazz.getMethod("getTsxxList", Map.class);

            Object obj = clazz.newInstance();
            method.setAccessible(true);

            List<String> list = (List<String>) method.invoke(obj, param);
            if(CollectionUtils.isEmpty(list)){
                return "验证通过，结果：\n\n【 代码执行返回结果 提示信息为空 】";
            } else {
                String res = JSON.toJSONString(method.invoke(obj, param));
                return "验证通过，结果：\n\n "  + res;
            }
        } catch (Exception e) {
            LOGGER.error("子规则代码测试失败，原因：", e);
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer, true));
            String exception = writer.toString();
            if(StringUtils.contains(exception, "Compilation failed")){
                return "验证失败，原因：语法格式存在错误\n\n Caused by: " + writer.toString();
            } else {
                return "验证失败，原因：\n\n Caused by: " + writer.toString();
            }
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzZgzDTO  子规则信息
     * @description  更新校验代码缓存
     */
    @Override
    public void updateJavaCodeCompileCache(BdcGzZgzDTO bdcGzZgzDTO) {
        if(null == bdcGzZgzDTO || StringUtils.isAnyBlank(bdcGzZgzDTO.getGzid(), bdcGzZgzDTO.getJydm())){
            return;
        }

        if(bdcGzJydmClassMap.isEmpty()){
            bdcGzJydmClassMap = new ConcurrentHashMap<>(10);
        }

        try{
            JavaStringCompiler compiler = new JavaStringCompiler();
            LOGGER.debug("处理子规则:{} 动态代码缓存", bdcGzZgzDTO.getGzid());
            Map<String, byte[]> results = compiler.compile("RuleCheck.java", bdcGzZgzDTO.getJydm());
            Class<?> clazz = compiler.loadClass("cn.gtmap.realestate.engine.service.impl.RuleCheck", results);
            bdcGzJydmClassMap.put(bdcGzZgzDTO.getGzid(), clazz);
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.error("处理子规则:{} 动态代码缓存处理失败，{}", bdcGzZgzDTO.getGzid(), e);
            throw new AppException("子规则" + bdcGzZgzDTO.getGzid() + "动态代码缓存处理失败");
        }
    }
}
