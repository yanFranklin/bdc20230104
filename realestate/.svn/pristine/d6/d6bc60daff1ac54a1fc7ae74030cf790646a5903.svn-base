package cn.gtmap.realestate.exchange.service.national;

/**
 * @author <a href="mailto:zhaodongdong@gtmap.cn">zdd</a>
 * @version 1.1.0, 2016/6/18.
 * @description 根据国家汇交验证的xsd标准，验证xml文件，并将验证信息输出（哈尔滨数据汇交）
 */
public interface VailXmlErrorHandlerService {

    /**
     * @param xmlFilePath   汇交xml文件
     * @param xsdFolderPath 验证汇交文件标准的xsd文件
     * @return msgFolderName 存放验证信息的文件夹名称
     * @author <a href="mailto:zhaodongdong@gtmap.cn">zdd</a>
     * @description
     */
    void vailXmlErrorHandlerService(String xmlFilePath, String xsdFolderPath, String msgFileName, String msgFolderName);

    /**
     * @param xml           xml字符串
     * @param xsdFolderPath xsd文件地址
     * @return 返回验证信息。验证通过为空值
     * @author zyy
     */
    String vailXmlErrorHandlerService(String xml, String xsdFolderPath);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 对xml进行xsd验证，并把返回值以及对应的字段对照并返回
     * @date : 2022/11/23 8:40
     */
    String xmlXsdjyHandler(String xml, String xsdPath);
}
