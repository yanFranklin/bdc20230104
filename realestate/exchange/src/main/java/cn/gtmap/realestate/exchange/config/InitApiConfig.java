//package cn.gtmap.realestate.exchange.config;
//
//import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
//import cn.gtmap.realestate.common.core.enums.OpenApiReleaseStatus;
//import cn.gtmap.realestate.common.util.UUIDGenerator;
//import cn.gtmap.realestate.exchange.core.annotation.OpenController;
//import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
//import cn.gtmap.realestate.exchange.core.mapper.server.BdcDwJkMapper;
//import cn.gtmap.realestate.exchange.util.constants.ConfigLocations;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.collections.MapUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
//import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//import org.thymeleaf.util.StringUtils;
//
//import javax.annotation.PostConstruct;
//import java.io.File;
//import java.io.FilenameFilter;
//import java.lang.annotation.Annotation;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static java.util.stream.Collectors.collectingAndThen;
//import static java.util.stream.Collectors.toCollection;
//
//@Component
//public class InitApiConfig implements ApplicationContextAware {
//
//    private static final Logger logger = LoggerFactory.getLogger(InitApiConfig.class);
//
//    private static final String XML_BEAN_INTERFACE_URL_PRE_FIX = "/realestate-exchange/rest/v1.0/interface/";
//
//    @Autowired
//    private BdcDwJkMapper bdcDwJkMapper;
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    public List<BdcDwJkDO> getApiFromSwagger(){
//        List<BdcDwJkDO> bdcDwJkDOS = new ArrayList<>();
//
//        Map<String, Object> openApiController = applicationContext.getBeansWithAnnotation(OpenController.class);
//
//        List<Map<String, String>> resultList = new ArrayList<>();
//
//        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
//        // 获取url与类和方法的对应信息
//        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
//
//        for (Map.Entry<RequestMappingInfo, HandlerMethod> mappingInfoHandlerMethodEntry : map.entrySet()) {
//            RequestMappingInfo requestMappingInfo = mappingInfoHandlerMethodEntry.getKey();
//            HandlerMethod handlerMethod = mappingInfoHandlerMethodEntry.getValue();
//            String className = handlerMethod.getMethod().getDeclaringClass().getName();
//            if(!StringUtils.startsWith(className,"cn.gtmap.realestate.exchange.web")){
//                continue;
//            }
//
//            Map<String, String> resultMap = new LinkedHashMap<>();
//            BdcDwJkDO bdcDwJkDO = new BdcDwJkDO();
//            bdcDwJkDO.setJkid(UUIDGenerator.generate());
//            bdcDwJkDO.setCjr("system");
//            bdcDwJkDO.setFbzt(OpenApiReleaseStatus.PUBLISHED.getReleaseStatus());
//            bdcDwJkDO.setJklx(2);
//            bdcDwJkDO.setSfjlrz(0);
//            bdcDwJkDO.setYyid("exchange-app");
//            // 类名
//            resultMap.put("className",className);
//            Annotation[] parentAnnotations = handlerMethod.getBeanType().getAnnotations();
//            for (Annotation annotation : parentAnnotations) {
//                if (annotation instanceof Api) {
//                    Api api = (Api) annotation;
//                    resultMap.put("classDesc",api.value());
//                } else if (annotation instanceof RequestMapping) {
//                    RequestMapping requestMapping = (RequestMapping) annotation;
//                    if (null != requestMapping.value() && requestMapping.value().length > 0) {
//                        //类URL
//                        resultMap.put("classURL",requestMapping.value()[0]);
//                    }
//                }
//            }
//
//            resultMap.put("methodName", handlerMethod.getMethod().getName()); // 方法名
//
//            bdcDwJkDO.setJkmc(className + "." + handlerMethod.getMethod().getName());
//
//            Annotation[] annotations = handlerMethod.getMethod().getDeclaredAnnotations();
//            if (annotations != null) {
//                // 处理具体的方法信息
//                for (Annotation annotation : annotations) {
//                    if (annotation instanceof ApiOperation) {
//                        ApiOperation methodDesc = (ApiOperation) annotation;
//                        String desc = methodDesc.value();
//                        //接口描述
//                        resultMap.put("methodDesc",desc);
//                        bdcDwJkDO.setJkms(desc);
//                    }
//                }
//            }
//            PatternsRequestCondition p = requestMappingInfo.getPatternsCondition();
//            for (String url : p.getPatterns()) {
//                //请求URL
//                resultMap.put("methodURL",url);
//                bdcDwJkDO.setJkdz(url);
//            }
//            RequestMethodsRequestCondition methodsCondition = requestMappingInfo.getMethodsCondition();
//            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
//                //请求方式：POST/PUT/GET/DELETE
//                resultMap.put("requestType",requestMethod.toString());
//                bdcDwJkDO.setJkqqfs(requestMethod.toString());
//            }
//            resultList.add(resultMap);
//            bdcDwJkDOS.add(bdcDwJkDO);
//        }
//        bdcDwJkDOS = bdcDwJkDOS.stream().collect(
//                collectingAndThen(
//                        toCollection(() -> new TreeSet<>(Comparator.comparing(BdcDwJkDO::getJkmc))), ArrayList::new));
//
//        return bdcDwJkDOS;
//    }
//
//    public List<BdcDwJkDO> getApiFromXml(){
//        List<BdcDwJkDO> bdcDwJkDOS = new ArrayList<>();
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(ConfigLocations.PATHS);
//        String[] beanNames = applicationContext.getBeanDefinitionNames();
//        for(String beanName : beanNames){
//            ExchangeBean bean = (ExchangeBean)applicationContext.getBean(beanName);
//            if (bean!=null && bean.getServiceInfoBO()!=null && MapUtils.isNotEmpty(bean.getServiceInfoBO().getRequestInfo())){
//                BdcDwJkDO bdcDwJkDO = BdcDwJkDO.BdcDwJkDOBuilder.aBdcDwJkDO().withYyid("exchange-app")
//                        .withJkid(UUIDGenerator.generate())
//                        .withJklx(2)
//                        .withCjr("system")
//                        .withSfjlrz(0)
//                        .withFbzt(OpenApiReleaseStatus.PUBLISHED.getReleaseStatus())
//                        .withJkmc((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("logEventName", "接口名称初始化异常请手动补录"))
//                        .withJkms((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("logEventName", "接口名称初始化异常请手动补录"))
//                        .withJkdz(XML_BEAN_INTERFACE_URL_PRE_FIX + bean.getId()).build();
//                bdcDwJkDOS.add(bdcDwJkDO);
//            }
//
//        }
//        return bdcDwJkDOS;
//    }
//
//    @PostConstruct
//    public void initInterfaceInfo() {
//        try{
//            logger.info("start init api");
//            List<BdcDwJkDO> bdcDwJkDOS = new ArrayList<>();
////            bdcDwJkDOS.addAll(getApiFromXml());
//            bdcDwJkDOS.addAll(getApiFromSwagger());
//            logger.info("api count:{}",bdcDwJkDOS.size());
////            List<BdcDwJkDO> param = new ArrayList<>();
//            bdcDwJkMapper.batchInsertApiInfoMerge(bdcDwJkDOS);
//        }catch (Exception e){
//            logger.error(e.getMessage(),e);
//            return;
//        }
//    }
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//}
