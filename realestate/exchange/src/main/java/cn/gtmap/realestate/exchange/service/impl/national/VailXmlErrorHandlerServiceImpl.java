package cn.gtmap.realestate.exchange.service.impl.national;

import cn.gtmap.realestate.exchange.service.national.VailXmlErrorHandlerService;
import cn.gtmap.realestate.exchange.util.XsdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:zhaodongdong@gtmap.cn">zdd</a>
 * @description 根据国家汇交验证的xsd标准，验证xml文件，并将验证信息输出（哈尔滨数据汇交）
 */
@Service
public class VailXmlErrorHandlerServiceImpl implements ErrorHandler, VailXmlErrorHandlerService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 错误信息
    private String ERRORMSG = "";

    @Autowired
    XsdUtil xsdUtil;

    private List<SAXParseException> errorList = new ArrayList<>(1);

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        if (ERRORMSG.length() < 1000000) {
            ERRORMSG += "警告：" + exception + "\n";
        } else {
            ERRORMSG += "提示：错误信息过长，此处忽略......" + "\n";
        }

    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        if (ERRORMSG.length() < 1000000) {
            ERRORMSG += "错误：" + exception + "\n";
        } else {
            ERRORMSG += "提示：错误信息过长，此处忽略......" + "\n";
        }
        errorList.add(exception);
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        if (ERRORMSG.length() < 1000000) {
            ERRORMSG += "严重：" + exception + "\n";
        } else {
            ERRORMSG += "提示：错误信息过长，此处忽略......" + "\n";
        }
    }

    /**
     * @param xmlFilePath   汇交xml文件
     * @param xsdFolderPath 验证汇交文件标准的xsd文件
     * @param xsdFolderPath 验证汇交文件标准的xsd文件
     * @return msgFolderName 存放验证信息的文件夹名称
     * @author <a href="mailto:zhaodongdong@gtmap.cn">zdd</a>
     * @description
     */
    @Override
    public void vailXmlErrorHandlerService(String xmlFilePath, String xsdFolderPath, String msgFileName, String
            msgFolderName) {
        ERRORMSG = "";
        File xmlFile = new File(xmlFilePath);
        String schemaLanguage = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
        Schema schema;
        try {
            // 解析XML的RecType 获取XSD文件
            File xsdFile = new File(xsdFolderPath);
            if (xmlFile.exists() && xsdFile.exists()) {
                InputSource inputSource = new InputSource(new FileInputStream(xmlFile));
                Source source = new SAXSource(inputSource);
                schema = schemaFactory.newSchema(xsdFile);
                Validator validator = schema.newValidator();
                validator.setErrorHandler(this);
                // 验证
                validator.validate(source);
                // 将验证信息写入TXT
                writeTxt(msgFileName, xmlFile.getParentFile().getPath() + "\\" + msgFolderName);
            }
        } catch (SAXException e) {
            logger.error("error:", e);
        } catch (Exception e) {
            logger.error("error:", e);
        }
    }

    @Override
    public String vailXmlErrorHandlerService(String xml, String xsdFolderPath) {
        ERRORMSG = "";
        String schemaLanguage = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory schemaFactory = SchemaFactory.newInstance(schemaLanguage);
        Schema schema;
        try {
            // 解析XML的RecType 获取XSD文件
            File xsdFile = new File(xsdFolderPath);
            if (xsdFile.exists()) {
                InputStream inputSource = new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8")));
                Source source = new StreamSource(inputSource);
                schema = schemaFactory.newSchema(xsdFile);
                Validator validator = schema.newValidator();
                validator.setErrorHandler(this);
                // 验证
                validator.validate(source);
            } else {
                ERRORMSG = "无验证文件";
            }
        } catch (SAXException e) {
            logger.error("error:", e);
        } catch (Exception e) {
            logger.error("error:", e);
        }
        return ERRORMSG;
    }

    /**
     * @param xml
     * @param xsdPath
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 对xml进行xsd验证，并把返回值以及对应的字段对照并返回
     * @date : 2022/11/23 8:40
     */
    @Override
    public String xmlXsdjyHandler(String xml, String xsdPath) {
        ERRORMSG = "";
        try {
            String resmsg = xsdUtil.validateXmlByXsd(xml, xsdPath);
            logger.warn("校验结果{}", resmsg);
            return resmsg;
        } catch (SAXException e) {
            logger.error("error:", e);
        } catch (Exception e) {
            logger.error("error:", e);
        }
        return ERRORMSG;
    }

    // 将ERRORMSG写入新建的文件
    private void writeTxt(String msgFileName, String msgFolder) {
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            File file = new File(createMsgFile(msgFileName, msgFolder));
            fileOutputStream = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.append(ERRORMSG);
            bufferedWriter.close();
        } catch (Exception e) {
            logger.error("error:", e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    logger.error("errorMsg:", e);
                }
            }
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    logger.error("errorMsg:", e);
                }
            }
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    logger.error("errorMsg:", e);
                }
            }
        }

    }

    // 新建msgFile 并且返回新建文件的路径
    private String createMsgFile(String msgFileName, String msgFolder) throws IOException {

        // 拼接msgFolder路径
        File xsdFolder = new File(msgFolder);
        if (!xsdFolder.isDirectory()) {
            // 如果文件夹不存在   创建
            xsdFolder.mkdir();
        }
        // 拼接msgFile路径
        File msgFile = new File(msgFolder + "/" + msgFileName);
        if (!msgFile.exists()) {
            // 新建msgFile
            msgFile.createNewFile();
        }
        return msgFile.getPath();
    }


}
