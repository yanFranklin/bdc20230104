package cn.gtmap.realestate.common.util.groovy

import cn.gtmap.realestate.common.core.ex.AppException
import cn.gtmap.realestate.common.core.ex.MissingArgumentException
import cn.gtmap.realestate.common.util.StringToolUtils
import com.alibaba.fastjson.JSON
import com.google.common.collect.Lists
import com.google.common.collect.Maps
import groovy.xml.XmlUtil
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.collections.MapUtils
import org.apache.commons.lang3.StringUtils
import org.dom4j.Attribute
import org.dom4j.Document
import org.dom4j.DocumentHelper
import org.dom4j.Element
import org.dom4j.io.SAXReader
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.xml.sax.InputSource

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.text.SimpleDateFormat

/**
 * @author <ahref ="mailto:songhaowen@gtmap.cn" > songhaowen</a>
 * @version 1.3 , 2018/11/14
 * @description 打印 xml工具类
 */
class XmlUtils {
    static final Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);
    /**
     * @param xml dataMap
     * @return <datas> </datas>
     * @author <ahref ="mailto:songhaowen@gtmap.cn" > songhaowen</a>
     * @description 替换 datas 数据
     */
    static String parsePrintDatasXml(def page, def dataMap) {
        StringBuilder builder = new StringBuilder()
        boolean emptyMap = MapUtils.isEmpty(dataMap)
        if (emptyMap) {
            dataMap= Maps.newHashMap()
        }
        def configKeys = Lists.newArrayList()
        page.datas.each { data ->
            builder.append(nodeToXmlString(data))
            configKeys.addAll(StringUtils.split(data.text(), '$'))
        }
        /**空字符串替换null,添加未查询出的字段*/
        configKeys.each { key ->
            dataMap.putIfAbsent(key,'')
        }
        def xml=builder.toString()
        dataMap.each{entry ->
            xml= StringToolUtils.replaceXml(String.valueOf(entry.key),entry.value,xml)
        }
        return xml
    }
    /**
     * 获取 <data> xml
     * @param page
     * @param bulider
     * @return
     */
    static String queryDataXml(def page,def bulider){
        if(page==null){
            return
        }
        page.datas.each { data ->
            bulider.append(nodeToXmlString(data))
        }
        return bulider.toString()
    }
    /**
     * 获取 xml中的字段名称
     * @param page
     * @param configKeys
     * @return
     */
    static List<String> queryDataConfigKeys(def page,def configKeys){
        if(page==null){
            return
        }
        page.datas.each { data ->
            configKeys.addAll(StringUtils.split(data.text(), '$'))
        }
        return configKeys
    }

    /**
     * @param xml dataMap
     * @return <detail></detail>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 替换detail 数据
     */
    static String parsePrintDetailXml(def page, def dataMap) {
//        def engine = new groovy.text.SimpleTemplateEngine()
        StringBuilder pageXml = new StringBuilder()
        if (dataMap.isEmpty()) {
            return pageXml
        }
        /**循环处理xml中的detail*/
        page.detail.each { data ->
            /**detail xml*/
            String detailXml = nodeToXmlString(data)
            def detailId = data.attribute("ID")
//            /**按照配置sql查询出的每个detail数据MultilMap<String,List>*/
//            def list = dataMap.get(detailId)
            /**每个detail替换数据后的xml*/
//            StringBuilder dataBulider = new StringBuilder()

            /**每个row节点*/
            StringBuilder rowBulider = new StringBuilder()
//            List<String> configKeys=Lists.newArrayList()
            data.row.each {
                rowBulider.append(nodeToXmlString(it))
//                configKeys.addAll(StringUtils.split(it.text(),'$'))
            }
//            /**替换数据*/
//            list.each { rowData ->
//                rowData.each { rowMap ->
//                    /**空字符串替换null,添加未查询出的字段*/
//                    configKeys.each {key->
//                        rowMap.putIfAbsent(key,'')
//                    }
//                    def rowTemplate = engine.createTemplate(rowBulider.toString()).make(rowMap)
//                    dataBulider.append(rowTemplate.toString())
//                }
//            }
            String pageDetail = StringUtils.replace(StringUtils.normalizeSpace(detailXml), StringUtils.normalizeSpace(rowBulider.toString()), StringUtils.normalizeSpace(dataMap.get(detailId)))
            pageXml.append(pageDetail)
        }
        return pageXml.toString()
    }
    /**
     * @param xml dataMap
     * @return <detail></detail>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取detail row xml格式数据
     */
    static Map<String,String> converDetailRowXmlString(def xml) {
        Map<String,String> detailMap=new HashMap<String,String>()
        xml.page.each { page ->
            /**循环处理xml中的detail*/
            page.detail.each { data ->
                /**detail xml*/
                def detailId = data.attribute("ID")
                /**每个row节点*/
                StringBuilder rowBulider = new StringBuilder()
                data.row.each {
                    rowBulider.append(nodeToXmlString(it))
                }
                detailMap.put(detailId,rowBulider.toString())
            }
        }

        return detailMap
    }
    /**
     * @param xml dataMap
     * @return <detail></detail>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取detail xml格式数据
     */
    static Map<String,String> converDetailXmlString(def xml) {
        Map<String,String> detailMap=new HashMap<String,String>()
        xml.page.each { page ->
            /**循环处理xml中的detail*/
            page.detail.each { data ->
                /**detail xml*/
                String detailXml = nodeToXmlString(data)
                def detailId = data.attribute("ID")
                detailMap.put(detailId,detailXml)
            }
        }
        return detailMap
    }
    /**
     * @param xml dataMap
     * @return <detail></detail>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取detail xml 中的所有字段
     */
    static Map<String,List<String>> getDetailXmlKeys(def xml) {
        Map<String,List<String>> detailMap=new HashMap<String,List<String>>()
        xml.page.each { page->
            /**循环处理xml中的detail*/
            page.detail.each { data ->
                def detailId = data.attribute("ID")
                List<String> configKeys=Lists.newArrayList()
                data.row.each {
                    String keys=it.text()
                    String id=it.attribute("ID")
                    configKeys.addAll(StringUtils.split(keys,'$'))
                    if(StringUtils.isNotBlank(id)){
                        id=StringUtils.replacePattern(id,"\\\$","")
                        id=StringUtils.replacePattern(id,"\\{","")
                        id=StringUtils.replacePattern(id,"\\}","")
                        configKeys.add(id);
                    }

                }
                detailMap.put(detailId,configKeys)
            }
        }
        return detailMap
    }

    /**
     * @param xml dataMap detailDataMap
     * @return <page></page>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    static String processPageXml(def page, def dataMap, def detailDataMap) {
        def datas = parsePrintDatasXml(page, dataMap)
        def detail = parsePrintDetailXml(page, detailDataMap)
        StringBuilder pageStr = new StringBuilder()
        pageStr.append(datas)
        pageStr.append(detail)
        return pageStr.toString()
    }
    /**
     * @param xml dataMap detailDataMap
     * @return <page></page>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    static String processOtherDataSourcePageXml(def page, def dataMap, def detailDataMap) {
        def datas = parsePrintDatasXml(page, dataMap)
        def detail = parseOtherDataSourcePrintDetailXml(page, detailDataMap)
        StringBuilder pageStr = new StringBuilder()
        if(StringUtils.isBlank(datas)){
            datas="<datas></datas>"
        }
        pageStr.append(datas)
        pageStr.append(detail)
        return pageStr.toString()
    }

    /**
     * @param xml dataMap
     * @return <detail></detail>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 替换其他数据源 detail 数据
     */
    static String parseOtherDataSourcePrintDetailXml(def page, def dataMap) {
//        def engine = new groovy.text.SimpleTemplateEngine()
        StringBuilder pageXml = new StringBuilder()
        if (dataMap.isEmpty()) {
            return pageXml
        }
        /**循环处理xml中的detail*/
        page.detail.each { data ->
            /**detail xml*/
            String detailXml = nodeToXmlString(data)
            def detailId = data.attribute("ID")
            /**按照配置sql查询出的每个detail数据MultilMap<String,List>*/
            def list = dataMap.get(detailId)
            /**每个detail替换数据后的xml*/
            StringBuilder dataBulider = new StringBuilder()

            /**每个row节点*/
            StringBuilder rowBulider = new StringBuilder()
            List<String> configKeys=Lists.newArrayList()
            data.row.each {
                rowBulider.append(nodeToXmlString(it))
                configKeys.addAll(StringUtils.split(it.text(),'$'))
                String id=it.attribute("ID")
                if(StringUtils.isNotBlank(id)){
                    id=StringUtils.replacePattern(id,"\\\$","")
                    id=StringUtils.replacePattern(id,"\\{","")
                    id=StringUtils.replacePattern(id,"\\}","")
                    configKeys.add(id)
                }
            }
            /**替换数据*/
            list.each { rowData ->
                rowData.each { rowMap ->
                    /**空字符串替换null,添加未查询出的字段*/
                    configKeys.each {key->
                        rowMap.putIfAbsent(key,'')
                    }
                    def xml=rowBulider.toString()
                    rowMap.each{entry ->
                        xml= StringToolUtils.replaceXml(String.valueOf(entry.key),entry.value,xml)
                    }
//                    def rowTemplate = engine.createTemplate(rowBulider.toString()).make(rowMap)
//                    dataBulider.append(rowTemplate.toString())
                    dataBulider.append(xml)
                }
            }
            String pageDetail = StringUtils.replace(StringUtils.normalizeSpace(detailXml), StringUtils.normalizeSpace(rowBulider.toString()), StringUtils.normalizeSpace(dataBulider.toString()))
            pageXml.append(pageDetail)
        }
        return pageXml.toString()
    }

    /**
     * @param xml dataMap detailDataMap
     * @return <page></page>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    static String processPrintXml(def xml, def dataMap, def detailDataMap) {
        StringBuilder pages = new StringBuilder()
        xml.page.each { page ->
            pages.append("<page>")
            String pageStr = processPageXml(page, dataMap, detailDataMap)
            pages.append(pageStr)
            pages.append("</page>")
        }
        return pages
    }

    static String processPrintXmlNoPage(def xml, def dataMap, def detailDataMap) {
        StringBuilder pages = new StringBuilder()
        xml.page.each { page ->
            String pageStr = processPageXml(page, dataMap, detailDataMap)
            pages.append(pageStr)
        }
        return pages
    }

    /**
     * @param xml dataMap detailDataMap
     * @return <page></page>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 处理其他数据源 xml
     */
    static String processOtherDataSourcePrintXml(def xml, def dataMap, def detailDataMap) {
        StringBuilder pages = new StringBuilder()
        xml.page.each { page ->
            pages.append("<page>")
            String pageStr = processOtherDataSourcePageXml(page, dataMap, detailDataMap)
            pages.append(pageStr)
            pages.append("</page>")
        }
        return pages
    }

    /**
     * @param xml dataMap detailDataMap
     * @return <page></page>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 处理其他数据源 xml
     */
    static String processOtherDataSourcePrintXmlNoPage(def xml, def dataMap, def detailDataMap) {
        StringBuilder pages = new StringBuilder()
        xml.page.each { page ->
            String pageStr = processOtherDataSourcePageXml(page, dataMap, detailDataMap)
            pages.append(pageStr)
        }
        return pages
    }

    /**
     * @param xml
     * @return xml字符串
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description node转换为xml字符串
     */
    static String nodeToXmlString(Node xml) {
        StringWriter stringWriter = new StringWriter()
        XmlNodePrinter nodePrinter = new XmlNodePrinter(new PrintWriter(stringWriter))
        nodePrinter.setPreserveWhitespace(true)
        nodePrinter.print(xml)
        return stringWriter.toString()
    }
    /**
     * @param name xml文件名称
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据名称获取xml
     */
    static Node getXmlNodeByName(def name) {
        if (StringUtils.isEmpty(name)) {
            throw new MissingArgumentException("")
        }
        def xmlPath = null
        try {
            xmlPath = ClassLoader.getResource('/print/' + StringUtils.normalizeSpace(name) + '.xml').getPath()
        } catch (Exception e) {
            throw new AppException("未找到xml文件")
        }
        def xml = new XmlParser().parse(xmlPath)
        return xml
    }

    static String getModelByDylx(def dylx) {
        if (StringUtils.isEmpty(dylx)) {
            throw new MissingArgumentException("")
        }
        def xmlPath = null
        try {
            xmlPath = ClassLoader.getResource('/printModel/' + StringUtils.normalizeSpace(dylx) + '.fr3').getPath()
        } catch (Exception e) {
            throw new AppException("未找到打印模板文件")
        }
        if(StringUtils.isNotBlank(xmlPath) && xmlPath.startsWith("/")) {
            xmlPath =  xmlPath.substring(1);
        }
        return xmlPath
    }
    /**
     * @param xml ( InputStream )
     * @return xml Node
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据流转换xml Node
     */
    static Node getXmlNodeByInputStream(def xml) {
        return new XmlParser().parse(xml)
    }
    /**
     * @param xml(String)
     * @return xml Node
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 根据字符串转换xml Node
     */
    static Node getXmlNodeByString(def xml) {
        return new XmlParser().parseText(xml)
    }
    /**
     * @param xml page
     * @return xml 字符串
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description
     */
    static String replacePageInXml(def xml, def page) {
        if (xml != null && StringUtils.isNotEmpty(page)) {
            xml.setValue(page)
        }
        return XmlUtil.serialize(xml)
    }

    /**
     * @param text
     * @return xml 字符串
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 处理特殊字符
     */
    static String processCharacterSet(String text) {
        if (StringUtils.isNotBlank(text)) {
            text = text.replace("&lt;", "<").replace("&gt;", ">")
                    .replace("&apos;", "\'").replace("&amp;", "&")
                    .replace("&quot;", '"')
        }
        return text
    }
    /**
     * @param gdXmlStr dataMap :数据集
     * @return String
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 归档xml
     */
    static String replaceGdXmlValue(def gdXmlStr, def dataMap) {
        if (gdXmlStr == null || MapUtils.isEmpty(dataMap)) {
            throw new AppException("xml和数据集不能为空")
        }
        StringBuilder files = new StringBuilder();
        StringBuilder files1 = new StringBuilder();
        Object children = dataMap.get("CHILDRENLIST")
        if(children instanceof List && CollectionUtils.isNotEmpty(children)){
            for(Map map:children){
                files.append(String.format("<file url=\"%s\" />", map.get("sjcldz")))
            }
            for(Map map:children){
                files1.append(String.format("<file url=\"%s\" ><filed name=\"relationid\"></filed></file>", map.get("sjcldz")))
            }
            dataMap.put("FILES", files.toString());
            dataMap.put("FILES1", files1.toString());
        }else{
            dataMap.put("FILES","");
            dataMap.put("FILES1", files1.toString());
        }
        def engine = new groovy.text.SimpleTemplateEngine()
        def template = engine.createTemplate(gdXmlStr).make(dataMap)
        return StringUtils.replace(template.toString(),"sjcldz", dataMap.get("SJCLDZ"))
    }

    /**
     * @param gdXml
     * @return String
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description archive 节点转换为string
     */
    static String parseGdArchive(def gdXml) {
        if (gdXml == null) {
            throw new AppException("xml不能为空")
        }
        StringBuilder archiveBulider = new StringBuilder()
        gdXml.archive.each { archive ->
            def doc = archive.doc
            archive.remove(doc)
            archiveBulider.append(nodeToXmlString(archive))
        }
        return StringUtils.normalizeSpace(archiveBulider.toString())
    }

    /**
     * @param gdXml
     * @return String
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description doc节点转换为string
     */
    static String parseGdDoc(def gdXml) {
        if (gdXml == null) {
            throw new AppException("xml不能为空")
        }
        StringBuilder docBulider = new StringBuilder()
        gdXml.archive.each { archive ->
            archive.doc.each { doc ->
                docBulider.append(nodeToXmlString(doc))
            }
        }
        return StringUtils.normalizeSpace(docBulider.toString())
    }
    /**
     * @param gdlxValue :gdlx xml
     * @return String
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 archivemodel 名称
     */
    static String getArchiveModelName(String gdlxValue, def xml) {
        if (xml == null) {
            throw new AppException("xml不能为空")
        }
        def name = xml.children().find { node ->
            gdlxValue.equals(node.attribute("name"))
        }
        return name.text()
    }
    /**
     * @param archiveModelXml 配置xml
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取归档类型
     */
    static String getGdlxpz(def archiveModelXml) {
        if (archiveModelXml == null) {
            throw new AppException("归档类型配置为空！")
        }
        return archiveModelXml.attribute("gdlx")
    }

    static String getType(def archiveModelXml) {
        if (archiveModelXml == null) {
            throw new AppException("归档类型配置为空！")
        }
        return archiveModelXml.attribute("type")
    }

    /**
     * @param xml node 对象 value 属性值
     * @return xml
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 设置节点属性
     */
    static Node setXmlArchiveName(def xml, def value) {
        if (xml == null) {
            throw new AppException("xml不能为空")
        }
        xml.archive.each { archive ->
            archive.@type = value
        }
        return xml
    }
    /**
     * @param gdXml dataMap
     * @return dataMap
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 添加没有查询出来的字段，避免空指针
     */
    static Map replaceNull(def gdXml,def dataMap){
        if (gdXml == null) {
            throw new AppException("xml不能为空")
        }
        List<String> configKeys=Lists.newArrayList()
        gdXml.archive.each { archive ->
            def doc = archive.doc
            if(CollectionUtils.isNotEmpty(doc)){
                archive.remove(doc)
            }
            configKeys.addAll(StringUtils.split(archive.text(),'$'))
        }
        configKeys.each {key->
            dataMap.putIfAbsent(key,null)
        }
        return dataMap

    }
    /**
     * @param gdXml gdMap docMap
     * @return dataMap
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 赋值归档xml
     */
    static String processGdXml(def gdXml,def gdMap,def docList){
        LOGGER.info("归档xml{}，归档数据{}，文件数据{}", JSON.toJSONString(gdXml), JSON.toJSONString(gdMap), JSON.toJSONString(docList))
        if (gdXml == null) {
            throw new AppException("xml不能为空")
        }
        StringBuilder xml = new StringBuilder()
        gdXml.archive.each { archive ->
            StringBuilder archiveBulider = new StringBuilder()
            archiveBulider.append('<archive type="'+archive.@type+'">')
            StringBuilder docBulider = new StringBuilder()
            StringBuilder fieldBulider = new StringBuilder()
            def docs = archive.doc
            if(CollectionUtils.isNotEmpty(docs)){
                docs.each {doc->
                    String docStr=nodeToXmlString(doc)
                    docList.each{ map->
                        docBulider.append(replaceGdXmlValue(docStr,map))
                    }
                }
            }
            gdMap=replaceNull(gdXml,gdMap)
            archive.field.each{ field->
                fieldBulider.append(nodeToXmlString(field))
            }
            archiveBulider.append(replaceGdXmlValue(fieldBulider.toString(),gdMap))
            archiveBulider.append(docBulider)
            archiveBulider.append("</archive>")
            xml.append(archiveBulider)
        }
        return StringUtils.normalizeSpace(xml.toString())
    }

    /**
     *
     * @param xml
     * @return
     */
    static String getBdcSfxxMain(def xml){
        if(xml==null){
            throw new AppException("收费项目xml不能为空！")
        }
        return nodeToXmlString(xml.DATA.RECORD.MAIN[0])
    }
    /**
     *
     * @param xml
     * @return
     */
    static String getBdcSfxxDetails(def xml){
        if(xml==null){
            throw new AppException("收费项目xml不能为空！")
        }
        return nodeToXmlString(xml.DATA.RECORD.DETAILS[0].DETAIL[0])
    }

    static Node getNodeByType(def xml,def type){
        if(xml == null){
            throw new AppException("xml不能为空！")
        }

        def archives = xml.children();
        def archive = archives.find { it.@type == type }
        if(null == archive){
            throw new AppException("xml中未设置type为" + type + "的模板!");
        }
        String nodeStr  = nodeToXmlString(archive);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('<?xml version="1.0" encoding="UTF-8"?> <list>');
        stringBuffer.append(nodeStr);
        stringBuffer.append('</list>');
        return new XmlParser().parseText(stringBuffer.toString());
    }

    /**
     * @param xmlStr map
     * @return String
     * @author <a href ="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据xml元素名称和map种的key匹配，给xml赋值对应的value
     */
    static String setXmlData(String xmlStr,Map<String,String> map){
        if(StringUtils.isBlank(xmlStr) || map == null || map.isEmpty()){
            return xmlStr;
        }
        Document document = DocumentHelper.parseText(xmlStr);
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        while (iterator.hasNext()){
            Element stu = (Element) iterator.next();
            LOGGER.info("======遍历子节点======");
            Iterator iterator1 = stu.elementIterator();
            while (iterator1.hasNext()){
                Element stuChild = (Element) iterator1.next();
                String value = (String) map.get(stuChild.getName());
                if(org.apache.commons.lang.StringUtils.isNotBlank(value)){
                    stuChild.setText(value);
                }
                LOGGER.info("节点名："+stuChild.getName()+"---节点值："+stuChild.getStringValue());
            }
        }
        return document.asXML().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n","");
    }

    /**
     * @param xmlStr map
     * @return String
     * @author <a href ="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据xml元素名称和map种的key匹配，给xml赋值对应的value
     */
    static String getXmlElementValue(String xmlStr,String elementName){
        if(StringUtils.isBlank(xmlStr) || StringUtils.isBlank(elementName)){
            return elementName;
        }
        Document document = DocumentHelper.parseText(xmlStr);
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        while (iterator.hasNext()){
            Element stu = (Element) iterator.next();
            LOGGER.info("======遍历子节点======");
            Iterator iterator1 = stu.elementIterator();
            while (iterator1.hasNext()){
                Element stuChild = (Element) iterator1.next();
                if(stuChild.getName().equals(elementName)){
                    return stuChild.getStringValue();
                }
                LOGGER.info("节点名："+stuChild.getName()+"---节点值："+stuChild.getStringValue());
            }
        }
        return document.asXML().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n","");
    }

}
