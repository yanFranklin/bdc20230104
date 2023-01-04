package cn.gtmap.realestate.building.util;

import cn.gtmap.realestate.common.core.support.spring.Container;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-10
 * @description
 */
public class BdcdyxxUtils {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcdyxxUtils.class);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param targetDO
     * @param bdcdyh
     * @return boolean
     * @description dozer 的 验证方法
     */
    public static boolean checkNeedDozer(Class targetDO,String bdcdyh) throws InvocationTargetException, IllegalAccessException {
        if(targetDO != null){
            BdcdyxxEnum bdcdyxxEnum = null;
            try{
                bdcdyxxEnum = BdcdyxxEnum.valueOf(StringUtils.upperCase(targetDO.getSimpleName()));
            }catch (IllegalArgumentException e){
                LOGGER.warn("枚举值不存在",e.getMessage());
            }
            if(bdcdyxxEnum != null){
                Method checkMethod = bdcdyxxEnum.getCheckMethod();
                if(checkMethod != null){
                    Object result = checkMethod.invoke(Container.getBean(checkMethod.getDeclaringClass()),bdcdyh);
                    return result != null ? BooleanUtils.toBoolean(result.toString()) : false;
                }else{
                    // 如果 checkMethod 为null 则 说明 不需要验证 直接 处理
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param targetDO
     * @param bdcdyh
     * @return java.lang.Object
     * @description 根据BDCDYH查询不动产单元实体信息
     */
    public static Object queryBdcdyxxByBdcdyh(Class targetDO,String bdcdyh) throws InvocationTargetException, IllegalAccessException {
        if(targetDO != null){
            BdcdyxxEnum bdcdyxxEnum = BdcdyxxEnum.valueOf(StringUtils.upperCase(targetDO.getSimpleName()));
            if(bdcdyxxEnum != null){
                Method queryMethod = bdcdyxxEnum.getQueryMethod();
                if(queryMethod != null){
                    return queryMethod.invoke(Container.getBean(queryMethod.getDeclaringClass()),bdcdyh);
                }
            }
        }
        return null;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcdyh
     * @param obj
     * @return void
     * @description 保存外键
     */
    public static void setObjectFk(String bdcdyh,Object obj) throws InvocationTargetException, IllegalAccessException {
        if(obj != null){
            BdcdyxxEnum bdcdyxxEnum = BdcdyxxEnum.valueOf(StringUtils.upperCase(obj.getClass().getSimpleName()));
            if(bdcdyxxEnum != null){
                Method setFkMethod = bdcdyxxEnum.getSetFkMethod();
                if(setFkMethod != null){
                    setFkMethod.invoke(Container.getBean(setFkMethod.getDeclaringClass()),bdcdyh,obj);
                }
            }
        }
    }
}
