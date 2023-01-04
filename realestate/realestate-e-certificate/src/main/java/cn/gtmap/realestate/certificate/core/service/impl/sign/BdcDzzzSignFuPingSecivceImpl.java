package cn.gtmap.realestate.certificate.core.service.impl.sign;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, 2019/1/4
 * @description    18880 富平县_电子证照系统中电子签章防伪系统定制需求
 */

import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzConfigDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.template.DzzzPdf;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzConfigService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzSignConfigService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzSignService;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzZzxxPdfService;
import cn.gtmap.realestate.certificate.util.Base64Util;
import cn.gtmap.realestate.certificate.util.BdcDzzzPdfUtil;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.Md5Util;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.namespace.QName;

@Service
public class BdcDzzzSignFuPingSecivceImpl implements BdcDzzzSignService {
    private static final Logger logger = LoggerFactory.getLogger(BdcDzzzSignFuPingSecivceImpl.class);
    @Autowired
    private BdcDzzzSignConfigService bdcDzzzSignConfigService;
    @Autowired
    private BdcDzzzConfigService bdcDzzzConfigService;
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;

    @Override
    public byte[] signature(Object o, byte[] out) {
        BdcDzzzZzxx bdcDzzzZzxx = (BdcDzzzZzxx)o;
        byte[] result = null;
        BdcDzzzConfigDo BdcDzzzConfigDo = bdcDzzzConfigService.queryBdcDzzzConfigDoByDwdm(bdcDzzzZzxx.getDwdm());
        // 查找模板
        DzzzPdf dzzzPdf = bdcDzzzZzxxPdfService.getDzzzPdf(bdcDzzzZzxx.getZzlxdm());
        if (null != BdcDzzzConfigDo && null != dzzzPdf) {
            // Pdf文件MD5摘要
            String sPdfMd5 = Md5Util.MD5(out);
            DataHandler sPdfBase64Bin = new DataHandler(new ByteArrayDataSource(out,"application/octet-stream"));

            String retXml = tZSign5(BdcDzzzPdfUtil.FUPING_SIGN_URL, sPdfBase64Bin, sPdfMd5
                    , BdcDzzzConfigDo.getContainerName(), BdcDzzzConfigDo.getAccessCode(),
                    dzzzPdf.getSign().getPage(), dzzzPdf.getSign().getX(), dzzzPdf.getSign().getY()
                    , dzzzPdf.getSign().getxE(), dzzzPdf.getSign().getyE(), 0);

            String success = tZGetResultInfo(retXml, 0);
            if (StringUtils.equals(Constants.TRUE, success)) {
                String pdfBase64 = tZGetResultInfo(retXml, 2);
                String pdfMd5 = tZGetResultInfo(retXml, 3);
                result = Base64Util.decodeBase64StrToByte(pdfBase64);
                // 计算签章PDF文件MD5摘要
                String dPdfMd5 = Md5Util.MD5(result);
                if (StringUtils.isNotBlank(pdfMd5) && StringUtils.isNotBlank(dPdfMd5)
                        && pdfMd5.compareToIgnoreCase(dPdfMd5) == 0) {
                    logger.info("富平签章:返回PDF文件MD5校验通过");
                } else {
                    result = null;
                    logger.info("富平签章:返回PDF文件MD5校验失败");
                }
                bdcDzzzSignConfigService.updateSignInfo(result, bdcDzzzZzxx);
            } else {
                String ErrMsg = tZGetResultInfo(retXml, 1);
                logger.info("富平签章" + ErrMsg);
            }
        }

        return result;
    }

    @Override
    public DzzzResponseModel verifyFile(String content, String bfjgxzqdm) {
        return null;
    }

    /*
     * WEB SERVICE 接口tZSign5调用
	 * 功能: 根据单位编号和印章编号获取服务端配置的PFX证书和印章图片，在指定的页面和位置对PDF进行签章，返回签章后PDF文件
	 * 参数：
	 *      String      sUrl         	  WEB SERVICE　WSDL网址
	 *		DataHandler sPdfBase64Bin，        PDF文件字节数组，传递时使用DataHandler
	 *		String      sPdfMd5，             　　 PDF文件MD5
	 *		String      unitID,           单位编号
	 *		String      sealID，                           印章编号
	 *		int         page，    		               签章页
	 *		int         x0，     		               图片左下角X坐标
	 *		int         y0，      		               图片左下角Y坐标
	 *		int         x1，     		               图片右上角X坐标
	 *		int         y1，      		               图片右上角Y坐标
	 *返回值：
	 *		返回值为XML字符串，格式为：
	 *		<xml>
	 *		<success>true/false</success>
	 *		<errmsg>签章失败原因</errmsg>
	 *		<pdfbytes>盖好章的PDF文件内容Base64串</pdfbytes>
	 *		<md5>盖好章的PDF文件Md5摘要</md5>
	 *		</xml>
	 *		如果success 为true 时执行签章成功， pdfbytes返回盖章后的PDF文件内容Base64串，md5为盖章后的PDF文件文件Md5摘要；
	 *		如果success 为false时执行签章失败，errmsg返回出错信息
	 *
	 */
    private static String tZSign5(String sUrl, DataHandler sPdfBase64Bin,
                                  String sPdfMd5, String unitID, String sealID, int page, int x0,
                                  int y0, int x1, int y1, int mode) {

        Object[] results = getWebService(sUrl, "TZSign5", new Object[]{
                sPdfBase64Bin, sPdfMd5, unitID, sealID, page, x0, y0, x1, y1, mode});
        String ret = "";
        if (results != null && results[0] != null) {
            ret = (String) results[0];
        }
        return ret;
    }

    private static Object[] getWebService(String surl, String saction, Object[] objarr) {
        RPCServiceClient client = null;
        try {
            Object[] param = objarr;
            client = new RPCServiceClient();
            Options option = client.getOptions();


            option.setTimeOutInMilliSeconds(100000);
            option.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, true);
            option.setManageSession(true);


            EndpointReference erf = new EndpointReference(surl);
            option.setTo(erf);
            option.setProperty("mtom-enabled", "true");   //设置此参数才支持字节参数
            // 方法
            QName name = new QName("http://service.com", saction);
            Class[] returnTypes = new Class[]{String.class};
            Object[] response = client.invokeBlocking(name, param, returnTypes);

            return response;
        } catch (AxisFault e) {
            logger.error("签章 WebService的服务中也会出现异常", e);
            return null;
        } finally {
            try {
                if (null != client) {
                    client.cleanupTransport();
                }
            } catch (AxisFault e) {
                logger.error("签章 WebService的服务中也会出现异常", e);
            }

        }
    }

    /*
     *功能：　PDF签章 WEB SERVICE接口返回值为XML字符串，格式为：
	 * 		 <xml>
	 * 		 <success>true/false</success>
	 * 		 <errmsg>签章失败原因</errmsg>
	 * 		 <pdfbytes>盖好章的PDF文件内容Base64串</pdfbytes>
	 * 		 <md5>盖好章的PDF文件Md5摘要</md5>
	 * 	     </xml>
	 *       对签章返回的XML 提取每个值节点辅助函数，
	 *
	 *参数：
	 * 		 String      xml         	     PDF签章 WEB SERVICE接口返回值为XML字符串
	 *       int         type，                                      提取的节点类型
	 *
	 *返回值：
	 *		type==0时，返回success 节点的值；
	 *		type==1时，返回errmsg  节点的值；
	 *		type==2时，返回pdfbytes节点的值；
	 *　　　type==3时，返回md5               节点的值；
	 */
    private static String tZGetResultInfo(String xml, int type) {
        String str = "";

        if (type == 0) {
            int beginIndex = xml.indexOf("<success>") + "<success>".length();
            int endIndex = xml.indexOf("</success>");
            if (beginIndex != -1 && endIndex != -1) {
                str = xml.substring(beginIndex, endIndex);
            }
        } else if (type == 1) {
            int beginIndex = xml.indexOf("<errmsg>") + "<errmsg>".length();
            int endIndex = xml.indexOf("</errmsg>");
            if (beginIndex != -1 && endIndex != -1) {
                str = xml.substring(beginIndex, endIndex);
            }
        } else if (type == 2) {
            int beginIndex = xml.indexOf("<pdfbytes>") + "<pdfbytes>".length();
            int endIndex = xml.indexOf("</pdfbytes>");
            if (beginIndex != -1 && endIndex != -1) {
                str = xml.substring(beginIndex, endIndex);
            }
        } else if (type == 3) {
            int beginIndex = xml.indexOf("<md5>") + "<md5>".length();
            int endIndex = xml.indexOf("</md5>");
            if (beginIndex != -1 && endIndex != -1) {
                str = xml.substring(beginIndex, endIndex);
            }
        }
        return str;
    }
}
