package cn.gtmap.realestate.init.util;


import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.Id;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/5
 * @description 数据类型转换
 */
public class DozerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DozerUtils.class);

    @Resource(name = "initDozerMapper")
    private DozerBeanMapper initDozerMapper;
    @Resource(name = "acceptDozerMapper")
    private DozerBeanMapper acceptDozerMapper;
    @Resource(name = "dozerSameNullFMapper")
    private DozerBeanMapper dozerMapperF;
    @Resource(name = "dozerSameNullTMapper")
    private DozerBeanMapper dozerMapperT;

    @Value("${init.dozerVersion:standard}")
    private String version;

    @PostConstruct
    private void checkDozerXml() throws Exception {
        Set<String> fileName=new HashSet<>();
        //对照配置文件路径
        List<String> existMappingFiles = new ArrayList<>();
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            org.springframework.core.io.Resource[] resources = resolver.getResources("classpath*:conf/init-dozer/"+version+"/*.xml");
            for (org.springframework.core.io.Resource r : resources) {
                if(!fileName.contains(r.getFilename())){
                    fileName.add(r.getFilename());
                    existMappingFiles.add(r.getURI().toString());
                }
            }
            resources = resolver.getResources("classpath*:conf/init-dozer/*.xml");
            for (org.springframework.core.io.Resource r : resources) {
                if(!fileName.contains(r.getFilename())){
                    existMappingFiles.add(r.getURI().toString());
                }
            }
        } catch (Exception e) {
           LOGGER.error("启动加载初始化数据对照逻辑配置失败",e);
           throw e;
        }
        initDozerMapper.setMappingFiles(existMappingFiles);
        initDozerMapper.map(new Object(), new Object());
        acceptDozerMapper.map(new Object(),new Object());
        dozerMapperF.map(new Object(), new Object());
        dozerMapperT.map(new Object(), new Object());
    }


    /**
     * 初始化逻辑的转换对象共用方法
     * @param source
     * @param destination
     */
    public void initBeanDateConvert(Object source, Object destination) {
        initDozerMapper.map(source, destination);
    }

    /**
     * 初始化逻辑的转换对象共用方法
     * @param source
     * @param destination
     */
    public void initBeanDateConvert(Object source, Object destination,String mapId) {
        initDozerMapper.map(source, destination,mapId);
    }


    /**
     * xhc
     * 实体类之间数据转换公共方法  没有mapId,有配置文件,参数为true时，null覆盖；false时，null不覆盖
     *
     * @param source
     * @param destination
     * @param args
     */
    public void sameBeanDateConvert(Object source, Object destination, boolean args) {
        if (args) {
            dozerMapperT.map(source, destination);
        }else {
            dozerMapperF.map(source, destination);
        }
    }

    /**
     * lst 单独加载dozer xml配置
     * @param mapFiles
     * @return
     */
    public DozerBeanMapper initDozer(List<String> mapFiles){
        DozerBeanMapper dozerMapper=new DozerBeanMapper();
        if(CollectionUtils.isNotEmpty(mapFiles)){
            dozerMapper.setMappingFiles(mapFiles);
        }
        return dozerMapper;
    }

    /**
     *获取dozer的xml中的class-a的集合
     * @param filePath
     * @return  xml中A的class
     * @throws JDOMException
     * @throws IOException
     */
    public List<Class> getDozerXmlAClass(String filePath) throws JDOMException, IOException, ClassNotFoundException {
        List<Class> list=new ArrayList();
        SAXBuilder builder = new SAXBuilder();
        Document document =builder.build(filePath);//获得文档对象
        //取的根元素
        Element root = document.getRootElement();
        Namespace namespace=root.getNamespace();
        List<Element> listElement=root.getChildren("mapping",namespace);
        if(listElement!=null){
            for(int i=0;i<listElement.size();i++){
                Element element=listElement.get(i);
                String dest=element.getChild("class-a",namespace).getText();
                Class destForName = Class.forName(dest);
                list.add(destForName);
            }
        }
        return  list;
    }

    /**
     * 对数据源的数据进行处理，根据源数据的数据
     * 目前只有前缀和后缀两种
     * append-（户） 添加户的后缀
     *（户）-prepend 添加户的前缀
     * @param source
     * @param destination
     */
    public void sourceBeanDateSpecialConvert(Object originObject, Object targetObject) {
        // 获取源对象所有属性
        Field[] originFields = originObject.getClass().getDeclaredFields();
        // 目标对象
        Field[] targetFields = targetObject.getClass().getDeclaredFields();
        List<String> targetFieldsNameList = BeansResolveUtils.getClassFieldsName(targetFields);
        try {
            for(Field originField : originFields){
                if(targetFieldsNameList.contains(originField.getName())){
                    // 获取目标对象属性值
                    Field targetField = targetObject.getClass().getDeclaredField(originField.getName());
                    targetField.setAccessible(true);
                    originField.setAccessible(true);
                    Object orgValue = originField.get(originObject);
                    Object targetValue = targetField.get(targetObject);

                    //判断源值是否要进行特殊处理
                    if ((orgValue instanceof String) && StringUtils.isNotBlank(String.valueOf(orgValue))) {
                        String orgValueString = String.valueOf(orgValue);
                        String targetValueString = String.valueOf(targetValue);
                        if (orgValueString.startsWith("append-") || orgValueString.endsWith("-prepend")){
                            //在特殊处理的情况下，如果目标无值，则清空源值，防止被复制
                            if(Objects.isNull(targetValue)){
                                originField.set(originObject, null);
                                continue;
                            }

                            if(orgValueString.startsWith("append-")) {
                                orgValueString = orgValueString.replace("append-", targetValueString);
                            }

                            if(orgValueString.startsWith("-prepend")) {
                                orgValueString = orgValueString.replace("-prepend", targetValueString);
                            }

                            //设置值
                            originField.set(originObject,orgValueString);
                        }
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e){
            LOGGER.error(e.getMessage(), e);
        }
    }
}
