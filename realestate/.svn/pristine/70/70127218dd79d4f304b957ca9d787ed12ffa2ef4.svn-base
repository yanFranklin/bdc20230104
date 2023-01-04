package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import org.apache.commons.collections.CollectionUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lst on 2015/11/19.
 */

@Service
public class DozerUtil {

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper exchangeDozerMapper;
    @Resource(name = "dozerSameNullFMapper")
    private DozerBeanMapper dozerMapperF;
    @Resource(name = "dozerSameNullTMapper")
    private DozerBeanMapper dozerMapperT;

    @PostConstruct
    private void checkDozerXml() {
        exchangeDozerMapper.map(new Object(), new Object());
        dozerMapperF.map(new Object(), new Object());
        dozerMapperT.map(new Object(), new Object());
    }


    /**
     * 转换对象共用方法
     * @param source
     * @param destination
     */
    public void exchangeBeanDateConvert(Object source, Object destination) {
        exchangeDozerMapper.map(source, destination);
    }

    /**
     * 转换对象共用方法
     * @param source
     * @param destination
     */
    public void exchangeBeanDateConvert(Object source, Object destination,String mapId) {
        exchangeDozerMapper.map(source, destination,mapId);
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


}
