package cn.gtmap.realestate.exchange.config;

import cn.gtmap.realestate.exchange.config.dozer.ExchangeProperterDescriptorCreation;
import cn.gtmap.realestate.exchange.core.dozer.DozerBeanMapper;
import org.dozer.propertydescriptor.PropertyDescriptorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-06
 * @description 不动产 同步 权籍信息 对照
 */
@Configuration
public class ExchangeDozerConfig implements ApplicationRunner {

    private static List<String> ACCESS_DONAMES;


    static {
        ACCESS_DONAMES = new ArrayList<>();
        ACCESS_DONAMES.add("DjfDjFzDO");
        ACCESS_DONAMES.add("DjfDjGdDO");
        ACCESS_DONAMES.add("DjfDjGgDO");
        ACCESS_DONAMES.add("DjfDjSfDO");
        ACCESS_DONAMES.add("DjfDjShDO");
        ACCESS_DONAMES.add("DjfDjSjDO");
        ACCESS_DONAMES.add("DjfDjSqrDO");
        ACCESS_DONAMES.add("DjfDjSzDO");
        ACCESS_DONAMES.add("DjtDjSlsqDO");
        ACCESS_DONAMES.add("FjFDO");
        ACCESS_DONAMES.add("KtfQtDzdzwDO");
        ACCESS_DONAMES.add("KtfQtMzdzwDO");
        ACCESS_DONAMES.add("KtfQtXzdzwDO");
        ACCESS_DONAMES.add("KtfZdbhqkDO");
        ACCESS_DONAMES.add("KtfZhbhqkDO");
        ACCESS_DONAMES.add("KtfZhYhydzbDO");
        ACCESS_DONAMES.add("KttFwCDO");
        ACCESS_DONAMES.add("KttFwHDO");
        ACCESS_DONAMES.add("KttFwLjzDO");
        ACCESS_DONAMES.add("KttFwZrzDO");
        ACCESS_DONAMES.add("KttGyJzdDO");
        ACCESS_DONAMES.add("KttGyJzxDO");
        ACCESS_DONAMES.add("KttZdjbxxDO");
        ACCESS_DONAMES.add("KttZhjbxxDO");
        ACCESS_DONAMES.add("QlfFwFdcqDzXmDO");
        ACCESS_DONAMES.add("QlfFwFdcqQfsyqDO");
        ACCESS_DONAMES.add("QlfQlCfdjDO");
        ACCESS_DONAMES.add("QlfQlDyaqDO");
        ACCESS_DONAMES.add("QlfQlDyaqDywqdDO");
        ACCESS_DONAMES.add("QlfQlDyiqDO");
        ACCESS_DONAMES.add("QlfQlHysyqDO");
        ACCESS_DONAMES.add("QlfQlJsydsyqDO");
        ACCESS_DONAMES.add("QlfQlNydsyqDO");
        ACCESS_DONAMES.add("QlfQlQtxgqlDO");
        ACCESS_DONAMES.add("QlfQlTdsyqDO");
        ACCESS_DONAMES.add("QlfQlYgdjDO");
        ACCESS_DONAMES.add("QlfQlYgdjDO");
        ACCESS_DONAMES.add("QlfQlYydjDO");
        ACCESS_DONAMES.add("QlfQlZxdjDO");
        ACCESS_DONAMES.add("QltFwFdcqDzDO");
        ACCESS_DONAMES.add("QltFwFdcqYzDO");
        ACCESS_DONAMES.add("QltQlGjzwsyqDO");
        ACCESS_DONAMES.add("QltQlLqDO");
        ACCESS_DONAMES.add("ZdKDO");
        ACCESS_DONAMES.add("ZhKDO");
        ACCESS_DONAMES.add("ZtfGyQlQlrGxDO");
        ACCESS_DONAMES.add("ZttGyQlrDO");
        ACCESS_DONAMES.add("ZttGyQlrOldDO");
        ACCESS_DONAMES.add("DjfDjFzOldDO");
        ACCESS_DONAMES.add("DjfDjSzOldDO");
        ACCESS_DONAMES.add("KttZdjbxxOldDO");
    }

    @Value("${access.version:hefei}")
    private String accessVersion;

    @Bean(name = "accessDefaultValueDozerMapper")
    public DozerBeanMapper accessDefaultValueDozerMapper(){
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        List<String> fileList = new ArrayList<>();
        for(String temp:ACCESS_DONAMES){
            String path = "conf/dozer/access/"+accessVersion+"/" + temp +".xml";
            ClassPathResource classPathResource = new ClassPathResource(path);
            if(classPathResource.exists()){
                fileList.add(path);
            }
        }
        dozerBeanMapper.setMappingFiles(fileList);
        return dozerBeanMapper;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        PropertyDescriptorFactory.addPluggedPropertyDescriptorCreationStrategy(new ExchangeProperterDescriptorCreation());
    }
}
