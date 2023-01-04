package cn.gtmap.realestate.building.util;

import cn.gtmap.realestate.building.service.bdcdyhzt.BdcdyhZtAbstractService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-03-26
 * @description BDCDYH状态相关 工具类
 */
public class BdcdyhZtUtils {

    /**
     * 处理状态的实现类集合
     */
    public volatile static Map<String,BdcdyhZtAbstractService> IMPL_BEAN = new HashMap<>();

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param className
     * @param impl
     * @return void
     * @description 添加实现
     */
    public static void setImplBean(String className,BdcdyhZtAbstractService impl){
        if(IMPL_BEAN.get(className) == null){
            synchronized (IMPL_BEAN){
                IMPL_BEAN.put(className,impl);
            }
        }
    }
}
