package cn.gtmap.realestate.common.core.service;

import java.util.List;
import java.util.Set;

/**
 * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
 * @version 1.0, 2018/11/16
 * @description 定义统一接口标识码获取方法
 */
public interface InterfaceCode {

    /**
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @description 获取实现类的标识码
     */
    Set<String> getInterfaceCode();



}
