package cn.gtmap.realestate.exchange.service.impl.inf;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeRz;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.sjpt.GxRzService;
import cn.gtmap.realestate.exchange.util.constants.SjptConstants;
import cn.gtmap.realestate.exchange.util.enums.SjptRzEnum;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-30
 * @description  ExchangeBean 请求方式
 */
@Service
public class ExchangeBeanRequestServiceImpl implements ExchangeBeanRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeBeanRequestServiceImpl.class);

    @Autowired
    private GxRzService gxRzService;

    /**
     * 调用哪家税务接口的配置， 默认：nk（直接调用南开）;ykq(调用一卡清进行中转)
     */
    @Value("${sw.interface.type:nk}")
    private String swinterfaceType;

    /**
     * @param beanName
     * @param requestObject
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 请求 exchangebean
     */
    @Override
    public Object request(String beanName, Object requestObject) {
        // 强制省级平台的接口必须使用sjptRequestInterface方法
        if(StringUtils.isNotBlank(SjptRzEnum.getCzlxByBeanName(beanName))){
            return sjptRequest(beanName,requestObject);
        }
        return normalRequest(beanName,requestObject);
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param beanName
     * @param requestObject
     * @return java.lang.Object
     * @description 单纯的请求
     */
    private Object normalRequest(String beanName, Object requestObject){
//        LOGGER.info("-=-=-=-=-=-=beanName，requestObject-=-=-=-==-：{},{}", beanName, requestObject);

        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        if(exchangeBean != null){
            InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,requestObject);
            return requestBuilder.invoke();
        }else{
            throw new AppException("无法获取名称为：" + beanName + "的配置信息");
        }
    }

    /**
     * @param beanName
     * @param requestObject
     * @param entityClass
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 请求 exchangebean
     */
    @Override
    public <T> T request(String beanName, Object requestObject, Class<T> entityClass) {
        Object result = request(beanName,requestObject);
        if(result != null){
            try{
                return JSONObject.parseObject(JSONObject.toJSONString(result),entityClass);
            }catch (Exception e){
                LOGGER.error("请求exchangebean结果返回异常，beanName:{},response:{}",beanName,JSONObject.toJSONString(result),e);
            }
        }
        return null;
    }

    /**
     * @param beanName
     * @param requestObject
     * @return java.lang.Object
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 与省级平台相关的接口 记录日志
     */
    @Override
    public Object sjptRequest(String beanName, Object requestObject) {
        GxPeRz gxPeRz = gxRzService.initRz();
        SjptRzEnum rzEnum = SjptRzEnum.getEnumByBeanName(beanName);
        if(rzEnum == null){
            throw new AppException("无法获取名称为：" + beanName + "的省级平台日志配置");
        }
        gxPeRz.setCzlx(rzEnum.getCzlx());
        //获取操作人
        UserManagerUtils userManagerUtils = Container.getBean(UserManagerUtils.class);
        String userName = userManagerUtils.getCurrentUserName();
        UserDto userDto = userManagerUtils.getUserByName(userName);
        if(userDto != null){
            gxPeRz.setCzr(userDto.getAlias());
        }
        Object responseObject = null;
        try{
            responseObject = normalRequest(beanName,requestObject);
        } catch (Exception e){
            LOGGER.error("省级平台相关的接口处理异常",e);
            gxPeRz.setZt(SjptConstants.RZ_ZT_FAIL);
            gxPeRz.setErrorMsg(e.getMessage());
        } finally {
            try{
                gxRzService.anaysisRzByResponse(gxPeRz,responseObject,rzEnum,requestObject);
            }catch (Exception e){
                LOGGER.error("省级平台相关的接口日志处理异常 rzid:{}",gxPeRz.getRzid(),e);
            }
            gxRzService.saveRz(gxPeRz);
        }
        return responseObject;
    }

    @Override
    public Object requestByUrl(String beanName, Object requestObject, String url) {

        ExchangeBean exchangeBean = ExchangeBean.getExchangeBean(beanName);
        if(exchangeBean != null){
            Object u = exchangeBean.getServiceInfoBO().getRequestInfo().get("url");
            exchangeBean.getServiceInfoBO().getRequestInfo().put("url",url);
            InterfaceRequestBuilder requestBuilder = new InterfaceRequestBuilder(exchangeBean,requestObject);
            return requestBuilder.invoke();
        }else{
            throw new AppException("无法获取名称为：" + beanName + "的配置信息");
        }

    }

    @Override
    public Object swRequest(String beanName, Object requestObject) {
        // 通过一卡清调用税务接口时，beanName前拼凑 ykq_ 来调用一卡清的接口
        if(StringUtils.isNotBlank(swinterfaceType) && StringUtils.equals("ykq", swinterfaceType)){
            beanName = "ykq_" + beanName;
        }
        return normalRequest(beanName,requestObject);
    }
}
