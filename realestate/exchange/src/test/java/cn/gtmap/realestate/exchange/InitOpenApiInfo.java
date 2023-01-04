package cn.gtmap.realestate.exchange;

import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.enums.OpenApiReleaseStatus;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcDwJkMapper;
import cn.gtmap.realestate.exchange.util.constants.ConfigLocations;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InitOpenApiInfo {

    private static final Logger logger = LoggerFactory.getLogger(InitOpenApiInfo.class);

    private final String BASE_PACKAGE = "cn.gtmap.realestate.exchange.web.rest";

    private final String RESOURCE_PATTERN = "/**/*.class";

    private static final String XML_BEAN_INTERFACE_URL_PRE_FIX = "/realestate-exchange/rest/v1.0/interface/";

    @Autowired
    private BdcDwJkMapper bdcDwJkMapper;

    @Test
    public void test() {
        try {
            logger.info("start init api");
            List<BdcDwJkDO> bdcDwJkDOS = new ArrayList<>();
            bdcDwJkDOS.addAll(getApiFromXml());
            bdcDwJkDOS.addAll(getApiFromSwagger());
            logger.info("api count:{}", bdcDwJkDOS.size());
//            List<BdcDwJkDO> param = new ArrayList<>();
            bdcDwJkMapper.batchInsertApiInfoMerge(bdcDwJkDOS);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return;
        }
    }

    public List<BdcDwJkDO> getApiFromXml() {
        List<BdcDwJkDO> bdcDwJkDOS = new ArrayList<>();
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(ConfigLocations.PATHS);
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            ExchangeBean bean = (ExchangeBean) applicationContext.getBean(beanName);
            if (bean != null) {
                BdcDwJkDO bdcDwJkDO = BdcDwJkDO.BdcDwJkDOBuilder.aBdcDwJkDO().withYyid("exchange-app")
                        .withJkid(UUIDGenerator.generate())
                        .withJklx(2)
                        .withCjr("system")
                        .withSfjlrz(0)
                        .withFbzt(OpenApiReleaseStatus.PUBLISHED.getReleaseStatus())
//                        .withJkmc((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("logEventName", "接口名称初始化异常请手动补录"))
//                        .withJkms((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("logEventName", "接口名称初始化异常请手动补录"))
                        .withJkdz(XML_BEAN_INTERFACE_URL_PRE_FIX + bean.getId())
                        .withFbzt(OpenApiReleaseStatus.PUBLISHED.getReleaseStatus())
                        .withJkqqfs(RequestMethod.POST.name())
                        .build();
                if (bean.getServiceInfoBO() != null && MapUtils.isNotEmpty(bean.getServiceInfoBO().getRequestInfo()) && bean.getServiceInfoBO().getRequestInfo().get("logEventName") != null) {
                    bdcDwJkDO.setJkmc((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("logEventName", "接口名称初始化异常请手动补录"));
                    bdcDwJkDO.setJkms((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("logEventName", "接口名称初始化异常请手动补录"));
                    bdcDwJkDO.setJkxff((String) bean.getServiceInfoBO().getRequestInfo().getOrDefault("requester", "接口调用方初始化异常请手动补录"));
                } else {
                    bdcDwJkDO.setJkmc(bean.getLogBO() != null && StringUtils.isNoneBlank(bean.getLogBO().getLogEventName()) ? bean.getLogBO().getLogEventName() : "接口名称初始化异常请手动补录");
                    bdcDwJkDO.setJkms(bean.getLogBO() != null && StringUtils.isNoneBlank(bean.getLogBO().getLogEventName()) ? bean.getLogBO().getLogEventName() : "接口名称初始化异常请手动补录");
                    bdcDwJkDO.setJkxff(bean.getLogBO() != null && StringUtils.isNoneBlank(bean.getLogBO().getRequester()) ? bean.getLogBO().getRequester() : "接口调用方初始化异常请手动补录");
                }
                bdcDwJkDOS.add(bdcDwJkDO);
            }

        }
        return bdcDwJkDOS;
    }

    public List<BdcDwJkDO> getApiFromSwagger() {
        List<BdcDwJkDO> bdcDwJkDOS = new ArrayList<>();
        //spring工具类，可以获取指定路径下的全部类
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(BASE_PACKAGE) + RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            //MetadataReader 的工厂类
            MetadataReaderFactory readerfactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                //用于读取类信息
                MetadataReader reader = readerfactory.getMetadataReader(resource);
                //扫描到的class
                String classname = reader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(classname);
                //判断是否有指定主解
                OpenController annotation = clazz.getAnnotation(OpenController.class);
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                if (annotation != null) {
                    //将注解中的类型值作为key，对应的类作为 value
                    String preFixUrl = "";
                    if (requestMapping != null) {
                        String[] urlArray = requestMapping.value();
                        if (urlArray != null && urlArray.length > 0) {
                            preFixUrl = urlArray[0];
                        }
                    }
                    Method[] methods = clazz.getMethods();
//                    Method[] interfaceMethods = null;
//                    if (clazz.getInterfaces() != null && clazz.getInterfaces().length > 0) {
//                        interfaceMethods = clazz.getInterfaces()[0].getMethods();
//                    }
                    for (Method method : methods) {
                        DsfLog dsfLog = method.getAnnotation(DsfLog.class);
                        if (dsfLog != null) {
                            PostMapping postMapping = method.getAnnotation(PostMapping.class);
                            GetMapping getMapping = method.getAnnotation(GetMapping.class);
                            if (clazz.getInterfaces() != null && clazz.getInterfaces().length > 0) {
                                Method tempMethod = clazz.getInterfaces()[0].getMethod(method.getName(), method.getParameterTypes());
                                postMapping = tempMethod.getAnnotation(PostMapping.class);
                                getMapping = tempMethod.getAnnotation(GetMapping.class);
                            }
                            BdcDwJkDO bdcDwJkDO = BdcDwJkDO.BdcDwJkDOBuilder.aBdcDwJkDO().withJkid(UUIDGenerator.generate())
                                    .withCjr("system")
                                    .withFbzt(OpenApiReleaseStatus.PUBLISHED.getReleaseStatus())
                                    .withJklx(2)
                                    .withSfjlrz(0)
                                    .withJkxff(dsfLog.requester())
                                    .withYyid("exchange-app")
                                    .withJkmc(dsfLog.logEventName())
                                    .withJkms(dsfLog.logEventName())
                                    .withFbzt(1)
                                    .withJkqqfs(postMapping != null ? RequestMethod.POST.name() : RequestMethod.GET.name())
                                    .build();
                            if (postMapping != null) {
                                bdcDwJkDO.setJkdz(preFixUrl + (postMapping.value().length > 0 ? postMapping.value()[0] : ""));
                            } else if (getMapping != null) {
                                bdcDwJkDO.setJkdz(preFixUrl + (getMapping.value().length > 0 ? getMapping.value()[0] : ""));
                            } else {
                                bdcDwJkDO.setJkdz("接口地址获取异常请手动补录");
                            }
                            bdcDwJkDOS.add(bdcDwJkDO);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return bdcDwJkDOS;
    }

}
