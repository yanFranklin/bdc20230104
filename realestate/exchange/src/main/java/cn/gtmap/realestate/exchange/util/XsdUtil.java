package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.domain.exchange.BdcJrXsdjyDO;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.util.XMLErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @program: bdcdj3.0
 * @description: xsd校验工具
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-11-23 13:59
 **/
@Component
public class XsdUtil {

    @Autowired
    BdcdjMapper bdcdjMapper;
    @Autowired
    RedisUtils redisUtils;
    private static final Logger LOGGER = LoggerFactory.getLogger(XsdUtil.class);

    /*
     * 因为没有登记数据，所以xsd校验排除这些信息
     * */
    private static final List<String> excludeElement = Arrays.asList("DJF_DJ_FZ", "DJF_DJ_GD", "DJF_DJ_SF", "DJF_DJ_SH", "DJF_DJ_SJ", "DJF_DJ_SQ",
            "DJT_DJ_SL", "FJ_F_100", "KTF_QT_DZDZW", "KTF_QT_MZDZW", "KTF_QT_XZDZW", "KTF_ZDBHQK", "KTF_ZHBHQK", "KTF_ZH_YHYDZB", "KTF_ZH_YHZK", "KTT_GZW",
            "KTT_ZDJBXX", "KTT_ZHJBXX", "QLF_FW_FDCQ_DZ_XM", "QLF_FW_FDCQ_QFSYQ", "QlfQlCfdjDO", "QLF_QL_DYAQ", "QLF_QL_DYIQ", "QLF_QL_HYSYQ", "QLF_QL_JSYDSYQ",
            "QLF_QL_NYDSYQ", "KTF_ZH_YHZK", "ZTT_GY_QLR", "ZTT_GY_JTCY", "QLF_QL_GZDJ", "DJF_DJ_DB", "QLF_QL_QTXGQL", "QLF_QL_TDSYQ",
            "QLF_QL_YGDJ", "QLF_QL_YYDJ", "QLF_QL_ZXDJ", "QLT_FW_FDCQ_DZ", "QLT_FW_FDCQ_YZ", "QLT_QL_GJZWSYQ", "QLT_QL_LQ", "QLF_QL_DYAQ_DYWQD");
    private Map xsdjyzdMap = new HashMap(20);
    public static final String ELEMENT_ALL_LIMITED = "cos-all-limited.2";
    public static final String ELEMENT_LOSE = "cvc-complex-type.2.4.a";
    public static final String ELEMENT_PATTERN_VALID = "cvc-pattern-valid";
    public static final String ELEMENT_REQUIRED_TEXT_LENGTH = "cvc-length-valid";
    public static final String ELEMENT_REQUIRED_TEXT_MIN_LENGTH = "cvc-minLength-valid";
    public static final String ELEMENT_REQUIRED_TEXT_MAX_LENGTH = "cvc-maxLength-valid";
    public static final String ELEMENT_TEXT_VALID = "cvc-type.3.1.3";
    public static final String ELEMENT_ENUMERATION = "cvc-enumeration-valid";
    public static final String ELEMENT_CHILDREN_LOSE = "cvc-complex-type.2.4.b";
    public static final String ELEMENT_REQUIERD_ATTRIBUTE_VALUE_LOSE = "cvc-pattern-valid";
    public static final String ELEMENT_OPTIONAL_ATTRIBUTE_VALUE_LOSE = "cvc-attribute.3";
    public static final String SINGLE_QUOTE = "'";
    public static final String ELEMENT_OPPTIONAL_TEXT_DATATYPE = "cvc-datatype-valid.1.2.1";
    public static final String ELEMENT_WITH_ATTRIBUTE_LENGTH = "cvc-complex-type.2.2";
    public static final String ELEMENT_ATTRIBUTE_UNIT = "cvc-complex-type.3.1";
    public static final String ELEMENT_REQUIRED_ATTRIBUTE_LOSE = "cvc-complex-type.4";


    @PostConstruct
    public void listXsddz() {
        redisUtils.deleteKey("xsdjyzddz");
        List<BdcJrXsdjyDO> bdcJrXsdjyDOList = bdcdjMapper.listXsdJyDz();
        if (CollectionUtils.isNotEmpty(bdcJrXsdjyDOList)) {
            for (BdcJrXsdjyDO bdcJrXsdjyDO : bdcJrXsdjyDOList) {
                redisUtils.addHashValue("xsdjyzddz", bdcJrXsdjyDO.getXsdzd(), bdcJrXsdjyDO.getSjkzd());
            }
            xsdjyzdMap = redisUtils.getHash("xsdjyzddz");
        }
    }


    public String validateXmlByXsd(String xml, String xsdPath) throws ParserConfigurationException, SAXException {
        XMLErrorHandler errorHandler = new XMLErrorHandler();
        File xsdFile = new File(xsdPath);
        String schemaLanguage = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
        Schema schema;
        if (xsdFile.exists()) {
            try {
                InputStream inputSource = new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8")));
                Source source = new StreamSource(inputSource);
                schema = schemaFactory.newSchema(xsdFile);
                Validator validator = schema.newValidator();
                validator.setErrorHandler(errorHandler);
                validator.setProperty("http://apache.org/xml/properties/locale", new Locale(""));
//                validator.setErrorHandler(xmlErrorHandler);
                // 验证
                validator.validate(source);
                return parseSchemaErrors(errorHandler);
            } catch (SAXException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return "未找到xsd校验文件";
        }
    }

    public String parseSchemaErrors(XMLErrorHandler errorHandler) {
        StringBuffer errors = new StringBuffer();
        boolean trag = false;
        Document errorDoc = null;
        try {
            errorDoc = DocumentHelper.parseText(errorHandler
                    .getErrors().asXML());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(errorDoc)) {
            return "";
        }
        Element root = errorDoc.getRootElement();
        if (Objects.isNull(root)) {
            return "";
        }
        for (Iterator<Element> iter = root.elementIterator("error"); iter.hasNext(); ) {
            if (!trag) {
                trag = true;
            }
            Element childElement = (Element) iter.next();
            String childMessage = childElement.getText();
            LOGGER.warn("元素异常信息{}", childMessage);
            if (childMessage.contains("cos-all-limited.2")) {
                if (StringUtils.isNotBlank(validateAll(childMessage))) {
                    errors.append(validateAll(childMessage));
                }
            } else if (childMessage.contains("cvc-pattern-valid")) {
                String nextErrorMsg = ((Element) iter.next()).getText();
                if (StringUtils.isNotBlank(validatePatternMsg(childMessage, nextErrorMsg))) {
                    errors.append(validatePatternMsg(childMessage, nextErrorMsg));
                }
            } else if (childMessage.contains("cvc-length-valid")) {
                String nextErrorMsg = ((Element) iter.next()).getText();
                if (StringUtils.isNotBlank(validateLengthValid(childMessage, nextErrorMsg))) {
                    errors.append(validateLengthValid(childMessage, nextErrorMsg));
                }
            } else if (childMessage.contains("cvc-minLength-valid")) {
                String nextErrorMsg = ((Element) iter.next()).getText();
                if (StringUtils.isNotBlank(validateMinLengthValid(childMessage, nextErrorMsg))) {
                    errors.append(validateMinLengthValid(childMessage, nextErrorMsg));
                }
            } else if (childMessage.contains("cvc-maxLength-valid")) {
                String nextErrorMsg = ((Element) iter.next()).getText();
                if (StringUtils.isNotBlank(validateMaxLengthValid(childMessage, nextErrorMsg))) {
                    errors.append(validateMaxLengthValid(childMessage, nextErrorMsg));
                }
            } else if (childMessage.contains("cvc-enumeration-valid")) {
                String nextErrorMsg = ((Element) iter.next()).getText();
                if (StringUtils.isNotBlank(validateEnumeration(childMessage, nextErrorMsg))) {
                    errors.append(validateEnumeration(childMessage, nextErrorMsg));
                }
            } else if (childMessage.contains("cvc-datatype-valid.1.2.1")) {
                String nextErrorMsg = ((Element) iter.next()).getText();
                if (StringUtils.isNotBlank(validateDataType(childMessage, nextErrorMsg))) {
                    errors.append(validateDataType(childMessage, nextErrorMsg));
                }
            } else if (childMessage.contains("cvc-complex-type.2.4.b")) {
                if (StringUtils.isNotBlank(validateChildrenLose(childMessage))) {
                    errors.append(validateChildrenLose(childMessage));
                }
            } else if (childMessage.contains("cvc-complex-type.4")) {
                if (StringUtils.isNotBlank(validateRequiredAttributeLose(childMessage))) {
                    errors.append(validateRequiredAttributeLose(childMessage));
                }
            } else if (childMessage.contains("cvc-complex-type.2.4.a")) {
                if (StringUtils.isNotBlank(validateElementLose(childMessage))) {
                    errors.append(validateElementLose(childMessage));
                }
            } else {
                if (StringUtils.isNotBlank(childMessage)) {
                    errors.append(childMessage);
                }
            }
        }
        return errors.toString();
    }


    public String validateAll(String errorMsg) {
        try {
            int i = errorMsg.lastIndexOf("element");
            int j = errorMsg.lastIndexOf("is invalid");
            String realMessage = errorMsg.substring(i + 9, j - 2);
            String msg = "Schema中元素" + realMessage + "选择器 all 最大/小次数设置错误";
            return setDjsjDz(msg);
        } catch (Exception e) {
        }
        return errorMsg;
    }


    public String validatePatternMsg(String errorMsg, String nextErrorMsg) {
        try {
            String msg = getPrefixMsg(nextErrorMsg)
                    + " 赋值非法,原因：正则校验不通过.正则表达式为："
                    + errorMsg.substring(errorMsg.lastIndexOf("pattern") + 9,
                    errorMsg.lastIndexOf("' for type"))
                    + " ,当前值:"
                    + errorMsg.substring(errorMsg.lastIndexOf("Value '") + 7,
                    errorMsg.lastIndexOf("' is not facet-valid"));
            return setDjsjDz(msg);
        } catch (Exception e) {
        }
        return errorMsg;
    }


    public String validateLengthValid(String errorMsg,
                                      String nextErrorMsg) {
        try {
            String msg = getPrefixMsg(nextErrorMsg) + " 长度不正确,原因：要求固定长度为："
                    + errorMsg.substring(
                    errorMsg.lastIndexOf("respect to length") + 19,
                    errorMsg.lastIndexOf("' for type"))
                    + "位"
                    + " ,当前值:"
                    + errorMsg.substring(errorMsg.lastIndexOf("Value '") + 7,
                    errorMsg.lastIndexOf("' with length"));
            return setDjsjDz(msg);

        } catch (Exception e) {
        }
        return errorMsg;
    }


    public String validateMinLengthValid(String errorMsg,
                                         String nextErrorMsg) {
        try {
            String msg = getPrefixMsg(nextErrorMsg)
                    + " 长度不正确,原因：要求最小长度为："
                    + errorMsg
                    .substring(
                            errorMsg.lastIndexOf("respect to minLength '") + 22,
                            errorMsg.lastIndexOf("' for type"))
                    + "位"
                    + " ,当前值:"
                    + errorMsg.substring(errorMsg.lastIndexOf("Value '") + 7,
                    errorMsg.lastIndexOf("' with length"));
            return setDjsjDz(msg);
        } catch (Exception e) {
        }
        return errorMsg;
    }


    public String validateMaxLengthValid(String errorMsg,
                                         String nextErrorMsg) {
        try {
            String msg = getPrefixMsg(nextErrorMsg)
                    + " 长度不正确,原因：要求最大长度为："
                    + errorMsg
                    .substring(
                            errorMsg.lastIndexOf("respect to maxLength '") + 22,
                            errorMsg.lastIndexOf("' for type"))
                    + "位"
                    + " ,当前值:"
                    + errorMsg.substring(errorMsg.lastIndexOf("Value '") + 7,
                    errorMsg.lastIndexOf("' with length"));
            return setDjsjDz(msg);
        } catch (Exception e) {
        }
        return errorMsg;
    }


    public String validateEnumeration(String errorMsg,
                                      String nextErrorMsg) {
        try {
            String msg = getPrefixMsg(nextErrorMsg) + "赋值非法,原因：不在枚举范围内";
            return setDjsjDz(msg);
        } catch (Exception e) {
        }
        return errorMsg;
    }


    public String validateDataType(String errorMsg, String nextErrorMsg) {
        try {
            int m = errorMsg.lastIndexOf("for");
            int n = errorMsg.lastIndexOf("'");
            String typeName = errorMsg.substring(m + 5, n);
            String msg = getPrefixMsg(nextErrorMsg) + "赋值无效,原因：要求类型为：" + typeName;
            return setDjsjDz(msg);
        } catch (Exception e) {
        }
        return errorMsg;
    }


    public String validateChildrenLose(String errorMsg) {
        try {
            int m = errorMsg.lastIndexOf("element");
            int n = errorMsg.lastIndexOf("' is not complete");
            String element = errorMsg.substring(m + 9, n);
            int i = errorMsg.lastIndexOf("{");
            int j = errorMsg.lastIndexOf("'");
            String realMessage = errorMsg.substring(i + 1, j - 1);
            String msg = "元素" + element + "不完整,缺少子元素" + realMessage;
            return setDjsjDz(msg);
        } catch (Exception e) {
        }
        return errorMsg;
    }


    public String validateRequiredAttributeLose(String errorMsg) {
        try {
            int i = errorMsg.indexOf("'");
            int j = errorMsg.indexOf("must");
            String attributeMessage = errorMsg.substring(i + 1, j - 2);
            int m = errorMsg.indexOf("element");
            int n = errorMsg.lastIndexOf("'");
            String elementMessage = errorMsg.substring(m + 9, n);
            String msg = "元素" + elementMessage + "属性" + attributeMessage + "缺失";

            return setDjsjDz(msg);
        } catch (Exception e) {
        }
        return errorMsg;
    }


    public String validateElementLose(String errorMsg) {
        try {
            int i = errorMsg.lastIndexOf("element");
            int j = errorMsg.lastIndexOf("One of ");
            String msg = "";
            if (errorMsg.contains("}'")) {
                int k = errorMsg.lastIndexOf("'{");
                int l = errorMsg.lastIndexOf("}'");
                String needElm = errorMsg.substring(k + 2, l);
                if (StringUtils.isNotBlank(needElm)) {
                    msg = "元素" + needElm + "缺失相关数据";
                } else {
                    msg = "元素" + errorMsg.substring(i + 9, j - 3) + "缺失,冗余或重复";
                }
            } else {
                msg = "元素" + errorMsg.substring(i + 9, j - 3) + "缺失,冗余或重复";
            }
            return setDjsjDz(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorMsg;
    }


    public String getPrefixMsg(String nextErrorMsg) throws Exception {
        String msg = "元素 ";
        if (nextErrorMsg.contains("cvc-type.3.1.3")) {
            msg = msg + validateElementTextLength(nextErrorMsg);
        } else if (nextErrorMsg.contains("cvc-attribute.3")) {
            String[] attrMgs = validateElementAttributeUnit(nextErrorMsg);
            msg = msg + attrMgs[1] + "属性 " + attrMgs[0];
        }
        return msg;
    }


    public String validateElementTextLength(String message)
            throws Exception {
        int m = message.lastIndexOf("element");
        int j = message.lastIndexOf("'");
        String realMessage = message.substring(m + 9, j);
        return realMessage;
    }


    public static String[] validateElementAttributeUnit(String message)
            throws Exception {
        int i = message.lastIndexOf("attribute");
        int j = message.indexOf("element");
        String attributeName = message.substring(i + 11, j - 5);


        int n = message.indexOf("is");
        String elementName = message.substring(j + 9, n - 2);


        int k = message.lastIndexOf("of");
        int l = message.lastIndexOf("'");
        String unit = message.substring(k + 4, l);


        String[] realMessage = {attributeName, elementName, unit};
        return realMessage;
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 设置登记数据字段对照
     * @date : 2022/11/23 16:55
     */
    public String setDjsjDz(String msg) {
        for (Object key : xsdjyzdMap.keySet()) {
            if (StringUtils.isNotBlank(msg) && msg.contains(String.valueOf(key))) {
                int i = msg.lastIndexOf(String.valueOf(key));
                String prefix = msg.substring(0, i + String.valueOf(key).length());
                String suffix = msg.substring(i + String.valueOf(key).length());
                msg = prefix + "对应到" + xsdjyzdMap.get(key) + suffix;
            }
        }
        for (String elem : excludeElement) {
            if (msg.contains(elem)) {
                return "";
            }
        }
        return msg;
    }
}
