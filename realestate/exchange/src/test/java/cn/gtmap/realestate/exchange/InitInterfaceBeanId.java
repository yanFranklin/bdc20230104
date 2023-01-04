package cn.gtmap.realestate.exchange;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

public class InitInterfaceBeanId {

    private static final Logger logger = LoggerFactory.getLogger(InitInterfaceBeanId.class);

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    private static final String IMPORT = "import";

    public static final String ID_ATTRIBUTE = "id";

    public static final String RESOURCE_ATTRIBUTE = "resource";

    private static final String SOURCE_XML = "/Users/wahaha-zhanshi/Desktop/config-service.xml";

    private static final String PRE_FIX_PATH = "/Users/wahaha-zhanshi/Desktop/guotuCode/realestate/exchange/src/main/resources/conf/spring/";

    private static final String PRE_FIX_TARGET_PATH = "/Users/wahaha-zhanshi/Desktop/guotuCode/realestate/exchange/src/main/resources/conf/spring/exchange/standard/";

    private static final String PRE_FIX_DATA = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<beans xmlns=\"http://www.springframework.org/schema/beans\"\n" +
            "       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "       xmlns:ex=\"http://www.gtmap.cn/schema/exchange-bean\"\n" +
            "       xsi:schemaLocation=\"http://www.springframework.org/schema/beans\n" +
            "       http://www.springframework.org/schema/beans/spring-beans.xsd\n" +
            "    http://www.gtmap.cn/schema/exchange-bean http://www.gtmap.cn/schema/exchange-bean.xsd\">";

    private static final String SUF_FIX_DATA = "\n" +
            "</beans>";

    private static Set<BeanInfo> beanInfoSet = new HashSet<>();

    private static Map<String,String> xmlMap = new HashMap<>();

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        try {
            logger.info("start interfacBeanId");
            String filePath = SOURCE_XML;
            parseXmlFile(filePath);
            if (!CollectionUtils.isEmpty(beanInfoSet)) {
                beanInfoSet.forEach(beanInfo -> {
//                    String xmlFileName = beanInfo.getFilePath().substring(beanInfo.getFilePath().lastIndexOf("/")+1);
                    String xmlFileName = beanInfo.getFilePath();
                    if (xmlMap.containsKey(xmlFileName)){
                        xmlMap.put(xmlFileName,xmlMap.get(xmlFileName)+"\n"+beanInfo.getXmlStr());
                    }else {
                        xmlMap.put(xmlFileName,beanInfo.getXmlStr());
                    }
                });
                logger.info(JSON.toJSONString(beanInfoSet));
                logger.info("*************************************");
                logger.info(String.valueOf(xmlMap.size()));
                logger.info("*************************************");
                logger.info("*************************************");
                logger.info("*************************************");
                logger.info(JSON.toJSONString(xmlMap));
                xmlMap.keySet().forEach(key->{
                    try {
                        String[] split = key.split("/");
                        String floder = PRE_FIX_TARGET_PATH + split[split.length-2];
                        String fileName = floder + key.substring(key.lastIndexOf("/"));
                        File floder1 = new File(floder);
                        if(!floder1.exists()){//如果文件夹不存在
                            floder1.mkdir();//创建文件夹
                        }
                        File file = new File(fileName);
                        boolean b = file.createNewFile();
                        if(b) {
                            Writer out = new FileWriter(file);
                            out.write(PRE_FIX_DATA+xmlMap.get(key)+SUF_FIX_DATA);
                            out.close();
                        }
                    }catch (Exception e){

                    }
                });

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static void parseXmlFile(String filePath) throws ParserConfigurationException, SAXException, IOException {
        File file = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        logger.info(filePath);
        Document doc = builder.parse(file);
        Element root = doc.getDocumentElement();
        if (isDefaultNamespace(root)) {
            NodeList nl = root.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                Node node = nl.item(i);
                if (node instanceof Element) {
                    Element ele = (Element) node;
                    initCustomElement(ele, filePath);
                }
            }
        }
    }

    private static boolean isDefaultNamespace(Node node) {
        String namespaceUri = node.getNamespaceURI();
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    private static void initCustomElement(Element ele, String filePath) {
        if (IMPORT.equals(ele.getNodeName()) || IMPORT.equals(ele.getLocalName())) {
            filePath = PRE_FIX_PATH + ele.getAttribute(RESOURCE_ATTRIBUTE).substring(0, ele.getAttribute(RESOURCE_ATTRIBUTE).indexOf("*"));
            List<String> fileNames = getAllFileUnderOneFloderPath(filePath);
            fileNames.forEach(fileName -> {
                try {
                    parseXmlFile(fileName);
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            String id = ele.getAttribute(ID_ATTRIBUTE);
            String xmlStr = doc2String(ele);
            beanInfoSet.add(BeanInfo.BeanInfoBuilder.aBeanInfo().withBeanId(id).withFilePath(filePath).withXmlStr(xmlStr).build());
        }
    }

    public static String doc2String(Element element) {
        String s = "";
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            Source source = new DOMSource(element);
            StringWriter out = new StringWriter();
            Result output = new StreamResult(out);
            transformer.transform(source, output);
            out.flush();
            s = out.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s.substring(38);
    }


    private static List<String> getAllFileUnderOneFloderPath(String floderPath) {
        List<String> fileNames = new ArrayList<>();
        File file = new File(floderPath);
        File[] tempLists = file.listFiles();
        for (int i = 0; i < tempLists.length; i++) {
            if (tempLists[i].isFile() && tempLists[i].toString().endsWith("xml")) {
                fileNames.add(tempLists[i].toString());
            }
        }
        return fileNames;
    }

}
