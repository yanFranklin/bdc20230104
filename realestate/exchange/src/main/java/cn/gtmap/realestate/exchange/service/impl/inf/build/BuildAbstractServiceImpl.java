package cn.gtmap.realestate.exchange.service.impl.inf.build;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.exchange.core.bo.xsd.ElementBO;
import cn.gtmap.realestate.exchange.service.inf.build.BuildRequestService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.XmlEntityConvertUtil;
import jodd.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-26
 * @description
 */
public abstract class BuildAbstractServiceImpl implements BuildRequestService {

    protected static Logger LOGGER = LoggerFactory.getLogger(BuildAbstractServiceImpl.class);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param elementBO
     * @return java.lang.Object
     * @description 对空值做处理
     */
    public Object dealNull(ElementBO elementBO){
        if(StringUtil.isNotEmpty(elementBO.getSaveNull())){
            try {
                Class tempClass = Class.forName(elementBO.getSaveNull());
                return tempClass.newInstance();
            } catch (ClassNotFoundException e) {
                LOGGER.error("空值处理异常",e);
            } catch (IllegalAccessException e) {
                LOGGER.error("空值处理异常",e);
            } catch (InstantiationException e) {
                LOGGER.error("空值处理异常",e);
            }
        }
        return null;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param entityObject
     * @param methodName
     * @return java.lang.Object
     * @description 执行加密或解密
     */
    public Object executeStaticMethodWithEntity(Object entityObject,String methodName){
        if(entityObject != null){
            if(StringUtils.isNotBlank(methodName)){
                Object result = CommonUtil.executeStaticMethod(methodName,entityObject);
                if(result != null){
                    return result;
                }else{
                    throw new AppException("执行加密方法异常");
                }
            }
        }
        return entityObject;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param entityObject
     * @param className
     * @return java.lang.Object
     * @description 把字符串 xml 转换为实体
     */
    public Object xmlToObject(Object entityObject,String className){
        try {
            Class clasz = Class.forName(className);
            return XmlEntityConvertUtil.toBean((String)entityObject,clasz);
        } catch (ClassNotFoundException e) {
            LOGGER.error("空值处理异常",e);
        }
        return null;
    }

    /**
     * @author wangyinghao
     * @param entityObject
     * @return String
     * @description base64解密
     */
    public Object base64Decode(Object entityObject,String charset){
        try {
            // base64解密
            return new String(Base64.decodeBase64((String) entityObject), charset);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @author wangyinghao
     * @param entityObject
     * @return String
     * @description 格式转换
     */
    public Object charsetChange(Object entityObject,String charset){
        try {
            // 字符串重新编码为当前格式
            if(StringUtils.isNotBlank(charset)) {
                return new String(((String) entityObject).getBytes(charset), StandardCharsets.UTF_8);
            }else{
                return entityObject;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
