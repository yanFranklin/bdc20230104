package cn.gtmap.realestate.building.util;


import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/12/27
 * @description dozer配置加载
 */
@Component
public class DozerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DozerUtils.class);

    @Resource(name = "bdcDozerMapper")
    private DozerBeanMapper bdcDozerMapper;

    @Value("${sysversion:standard}")
    private String version;

    @PostConstruct
    private void checkDozerXml() throws Exception {
        //对照配置文件路径
        List<String> existMappingFiles = new ArrayList<>();
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            org.springframework.core.io.Resource[] resources = resolver.getResources("classpath*:conf/bdc-dozer/"+version+"/*.xml");
            for (org.springframework.core.io.Resource r : resources) {
                existMappingFiles.add(r.getURI().toString());
            }
        } catch (Exception e) {
           LOGGER.error("启动加载同步权籍数据对照逻辑配置失败",e);
           throw e;
        }
        bdcDozerMapper.setMappingFiles(existMappingFiles);
        bdcDozerMapper.map(new Object(), new Object());
    }
}
