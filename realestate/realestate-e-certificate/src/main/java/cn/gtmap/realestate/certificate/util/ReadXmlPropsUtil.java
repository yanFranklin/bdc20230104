package cn.gtmap.realestate.certificate.util;

import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/7
 */
public class ReadXmlPropsUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReadXmlPropsUtil.class);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description 电子证照读取信息验证配置项
     */
    public static List<String> getDzzzCheckinfo(String bdcqzh) {
        List<String> listAllNodeid = new ArrayList<>(10);
        SAXReader sr = new SAXReader();
        Document document = null;
        try {
            document = sr.read( "/conf/realestate-e-certificate/dzzz_checkinfo.xml");
        } catch (DocumentException e) {
            logger.error("getDzzzCheckinfo", e);
        }
        if (document != null) {
            Element root = document.getRootElement();
            Element element = null;
            if ( bdcqzh.indexOf(Constants.BDC_ZSLX_MC_ZS) !=-1) {
                element = root.element("zs_fields");
            } else if (bdcqzh.indexOf(Constants.BDC_ZSLX_MC_ZMS) !=-1) {
                element = root.element("zm_fields");
            } else if (bdcqzh.indexOf(Constants.BDC_ZSLX_MC_SCDJZ) !=-1) {
                element = root.element("scdjz_fields");
            }
            if (null != element) {
                for (Iterator it = element.elementIterator(); it.hasNext(); ) {
                    Element element1 = (Element) it.next();
                    listAllNodeid.add(element1.getText());
                }
            }
        }
        return listAllNodeid;
    }

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param xmlStr
     * @return
     * @throws DocumentException
     * @description 常州电子证照签章，解析webservice结果xml
     */
    public static String analysisSoapXml(String xmlStr) throws DocumentException {
        String msg = "";
        Document document = DocumentHelper.parseText(xmlStr);
        Element root = document.getRootElement();
        Iterator body = root.elementIterator("Body");
        while (body.hasNext()) {
            Element recordEle = (Element) body.next();
            Iterator execByPathResponse = recordEle.elementIterator("execResponse");
            while (execByPathResponse.hasNext()) {
                Element execByPathResponseEle = (Element) execByPathResponse.next();
                Iterator result = execByPathResponseEle.elementIterator("signedPdf");
                while (result.hasNext()) {
                    Element resultEle = (Element) result.next();
                    msg = resultEle.getText();
                }
            }
        }
        return msg;
    }

    public static String analysisSealXml(String bfjgdm, String qzrqmc){
        String result = null;
        SAXReader sr = new SAXReader();
        Document document = null;
        try {
            document = sr.read( "/conf/realestate-e-certificate/dzzz_seal.xml");
        } catch (DocumentException e) {
            logger.error("analysisSealXml", e);
        }
        if (document != null) {
            Element root = document.getRootElement();
            if (null != root) {
                for (Iterator it = root.elementIterator(); it.hasNext(); ) {
                    Element element = (Element) it.next();
                    if (null != element) {
                        Attribute attribute = element.attribute("zzbfjgdm");
                        if (null != attribute && StringUtils.equals(attribute.getValue(), bfjgdm)) {
                            for (Iterator seal = element.elementIterator(); seal.hasNext(); ) {
                                Element sealEl = (Element) seal.next();
                                if (null != sealEl && StringUtils.equals(qzrqmc,sealEl.attribute("sealName").getValue())) {
                                    return sealEl.attribute("sealPwd").getValue();
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private ReadXmlPropsUtil() {
    }
}
