package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.service.InterfaceCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
/**
 * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
 * @version 1.0, 2018/11/16
 * @description InterfaceCode接口的工厂处理类
 */
public class InterfaceCodeBeanFactory {

    /**
     * @author <a href="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @param listBeans InterfaceCode接口实现类的集合
     * @param code   查找的唯一标识码
     * @return 具体实现类
     * @description
     */
    public static <T> T getBean(Set<T> listBeans, String code){
        if (CollectionUtils.isEmpty(listBeans) || StringUtils.isBlank(code)){
            return null;
        }

        InterfaceCode interfaceCode;
        for (Object interfaceCodeTemp:listBeans){
            if(interfaceCodeTemp instanceof InterfaceCode){
                interfaceCode = (InterfaceCode)interfaceCodeTemp;

                /**
                 * 接口唯一标识码类型改为Set以支持多个标识码对应同一个实现类
                 * modified by <a href="mailto:zhuyong@gtmap.cn"> 2018/11/20
                 */
                Set<String> codes = interfaceCode.getInterfaceCode();
                if(CollectionUtils.isEmpty(codes)){
                    continue;
                }

                for (String beanCode : codes){
                    if(StringUtils.equals(beanCode, code)){
                        // 直接return返回，避免break只跳出当前循环还需要标识判断
                        return (T)interfaceCode;
                    }
                }
            }
        }

        return null;
    }
}
