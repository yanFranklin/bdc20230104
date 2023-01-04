package cn.gtmap.realestate.engine.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.lang3.StringUtils;
import org.kie.api.io.ResourceType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/1
 * @description Drools引擎操作工具类
 */
public class KnowledgeBaseUtil {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseUtil.class);

    /**
     * 当前限定类名
     */
    private static final String CLASS_NAME = KnowledgeBaseUtil.class.getName();


    private KnowledgeBaseUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param fileName 文件名称
     * @description 编译并校验错误
     */
    public static void createKnowledgeBase(String fileName){
        if(StringUtils.isBlank(fileName)){
            throw new NullPointerException("规则子系统——处理规则文件产生异常：未指明要处理文件！");
        }

        /**
         * KnowledgeBuilder用来在业务代码中收集已经编写好的规则
         * 然后对这些规则进行编译，产生一批编译好的规则包：KnowledgePackage
         */
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource(fileName), ResourceType.DRL);

        /**
         * 编译的过程中可以通过hasErrors()方法得到编译规则过程中发现规则是否有错误
         * 如果有错误，可以通过getErrors()得到错误信息
         */
        if(builder.hasErrors()){
            LOGGER.error("{}：规则子系统——处理规则文件产生异常，异常文件名：{}", CLASS_NAME, fileName);
            throw new AppException("规则子系统——处理规则文件产生异常");
        }
    }
}
