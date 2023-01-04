package cn.gtmap.realestate.exchange.core.bo.xsd;

import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-14
 * @description 实体转换 元素
 */
public class ElementBO extends TagWithRefereceBO{

    private String name;

    // dozer文件地址
    private String dozerXml;

    // dozer xml存放相对路径
    private String dozerRelXml;

    // 集合强转成实体
    private boolean listToObject;

    // 实体强转为List
    private boolean ObjectToList;

    // 不为空 直接返回，不进行下面element处理
    private boolean notNullReturn;

    private ExchangeBean sourceService;

    private DozerBeanMapper dozerBeanMapper;

    // 是否实体转成Json格式String
    private boolean objectToJsonString;

    //  是否转存进参数实体
    private boolean appendRequestBody;

    //  是否需要不为空
    private boolean notEmpty;

    //  为空时提示信息
    private String emptyExMsg;

    // 是否捕获，不向外抛出异常
    private boolean ignoreException;

    // null时处理返回
    private String saveNull;

    // 加密 方法 类名.方法名
    private String encodeMethod;

    //  解密 方法 类名.方法名
    private String decodeMethod;

    // 实体转XML 的方法
    private String objectToXmlStringMethod;

    // xml转实体 默认使用xstream方式 参数为 转换的实体类全名称
    private String xmlToObject;

    // base64解密为string
    private String base64Decode;

    // 编码格式转换
    private String charsetChange;

    // 是否先执行dozer转换
    private String dozerFirst;

    // 是否先执行 source
    private String sourceFirst;

    // 是否不转存
    private boolean noSetRequestBody;

    public boolean isNoSetRequestBody() {
        return noSetRequestBody;
    }

    public void setNoSetRequestBody(boolean noSetRequestBody) {
        this.noSetRequestBody = noSetRequestBody;
    }
    public String getDozerXml() {
        return dozerXml;
    }

    public void setDozerXml(String dozerXml) {
        this.dozerXml = dozerXml;
    }

    public ExchangeBean getSourceService() {
        return sourceService;
    }

    public void setSourceService(ExchangeBean sourceService) {
        this.sourceService = sourceService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasDozer(){
        if(StringUtils.isNotBlank(this.dozerRelXml) || StringUtils.isNotBlank(this.dozerXml)){
            return true;
        }
        return false;
    }

    public DozerBeanMapper getDozerBeanMapper() {
        List<String> mappingFiles = new ArrayList<>();
        if(StringUtils.isNotBlank(this.dozerRelXml)){
            mappingFiles.add(this.dozerRelXml);
        }else{
            mappingFiles.add(this.dozerXml);
        }
        this.dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(mappingFiles);
        return this.dozerBeanMapper;
    }

    public void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    public Boolean getListToObject() {
        return listToObject;
    }

    public void setListToObject(Boolean listToObject) {
        this.listToObject = listToObject;
    }

    public Boolean getNotNullReturn() {
        return notNullReturn;
    }

    public void setNotNullReturn(Boolean notNullReturn) {
        this.notNullReturn = notNullReturn;
    }

    public boolean isObjectToJsonString() {
        return objectToJsonString;
    }

    public void setObjectToJsonString(boolean objectToJsonString) {
        this.objectToJsonString = objectToJsonString;
    }

    public boolean isAppendRequestBody() {
        return appendRequestBody;
    }

    public void setAppendRequestBody(boolean appendRequestBody) {
        this.appendRequestBody = appendRequestBody;
    }

    public boolean isNotEmpty() {
        return notEmpty;
    }

    public void setNotEmpty(boolean notEmpty) {
        this.notEmpty = notEmpty;
    }

    public boolean isObjectToList() {
        return ObjectToList;
    }

    public void setObjectToList(boolean objectToList) {
        ObjectToList = objectToList;
    }

    public boolean isIgnoreException() {
        return ignoreException;
    }

    public void setIgnoreException(boolean ignoreException) {
        this.ignoreException = ignoreException;
    }

    public String getSaveNull() {
        return saveNull;
    }

    public void setSaveNull(String saveNull) {
        this.saveNull = saveNull;
    }

    public String getEmptyExMsg() {
        return emptyExMsg;
    }

    public void setEmptyExMsg(String emptyExMsg) {
        this.emptyExMsg = emptyExMsg;
    }


    public String getDecodeMethod() {
        return decodeMethod;
    }

    public void setDecodeMethod(String decodeMethod) {
        this.decodeMethod = decodeMethod;
    }

    public String getEncodeMethod() {
        return encodeMethod;
    }

    public void setEncodeMethod(String encodeMethod) {
        this.encodeMethod = encodeMethod;
    }

    public String getObjectToXmlStringMethod() {
        return objectToXmlStringMethod;
    }

    public void setObjectToXmlStringMethod(String objectToXmlStringMethod) {
        this.objectToXmlStringMethod = objectToXmlStringMethod;
    }

    public String getXmlToObject() {
        return xmlToObject;
    }

    public void setXmlToObject(String xmlToObject) {
        this.xmlToObject = xmlToObject;
    }

    public String getBase64Decode() {
        return base64Decode;
    }

    public void setBase64Decode(String base64Decode) {
        this.base64Decode = base64Decode;
    }

    public String getCharsetChange() {
        return charsetChange;
    }

    public void setCharsetChange(String charsetChange) {
        this.charsetChange = charsetChange;
    }

    public String getDozerFirst() {
        return dozerFirst;
    }

    public void setDozerFirst(String dozerFirst) {
        this.dozerFirst = dozerFirst;
    }

    public String getDozerRelXml() {
        return dozerRelXml;
    }

    public void setDozerRelXml(String dozerRelXml) {
        this.dozerRelXml = dozerRelXml;
    }

    public String getSourceFirst() {
        return sourceFirst;
    }

    public void setSourceFirst(String sourceFirst) {
        this.sourceFirst = sourceFirst;
    }
}
