package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.xfire.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn>lisongtao</a>"
 * @version 1.0, 2019/08/13
 * @description webservice调用工具类
 */
@Component
public class WebServiceUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceUtils.class);

    /**
     *  调用webservice
     * @param url
     * @param uri
     * @param methodName
     * @param paramNameArray
     * @param paramValArray
     * @return
     */
    public static String callWebService(String url, String uri, String methodName, String[] paramNameArray, Object[] paramValArray) {
        String result = "";
        if (StringUtils.isNoneBlank(url)) {
            try {
                Service service = new Service();
                Call call = (Call) service.createCall();
                call.setTargetEndpointAddress(url);
                call.setOperationName(new javax.xml.namespace.QName(uri, methodName));
                call.setUseSOAPAction(true);
                call.setSOAPActionURI(uri + methodName);
                call.setProperty(Call.CONNECTION_TIMEOUT_PROPERTY, Integer.valueOf(20000));
                call.setTimeout(Integer.valueOf(20000));
                for (int i=0;i<paramNameArray.length;i++) {
                    QName qName=XMLType.XSD_STRING;
                    if(i<paramValArray.length){
                        if(paramValArray[i] instanceof Integer){
                            qName=XMLType.XSD_INTEGER;
                        }
                    }
                    call.addParameter(new QName(uri, paramNameArray[i]), qName, ParameterMode.IN);
                }
                // 设置返回类型
                call.setReturnType(XMLType.XSD_STRING);
                result = (String) call.invoke(paramValArray);
            } catch (RemoteException e) {
                LOGGER.error("RemoteException",e);
            } catch (ServiceException e) {
                LOGGER.error("ServiceException",e);
            }
        }
        return result;
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [url, methodName, paramXmlString]
     * @return java.lang.String
     * @description 调用webservice接口（直接传入参数XML）
     */
     public static String callWebService(String url, String methodName, String paramXmlString) {
         String result = "";
         if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(methodName)) {
             try {
                 Service service = new Service();
                 Call call = (Call) service.createCall();
                 call.setTargetEndpointAddress(url);
                 call.setOperationName(methodName);
                 call.addParameter("string",
                         org.apache.axis.encoding.XMLType.XSD_STRING,
                         javax.xml.rpc.ParameterMode.IN);
                 call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
                 result = (String) call.invoke(new Object[]{paramXmlString});
             } catch (ServiceException|RemoteException e) {
                 LOGGER.error("czYjfwWebService接口请求异常：{}", e.getMessage());
                 throw new AppException("czYjfwWebService接口请求异常!");
             }
         }
         LOGGER.info("czYjfwWebService接口响应结果：{}", result);
         return result;
     }

    /**
     * xfire调用webservice
     *
     * @param url
     * @param method
     * @param param
     */
    public static String callWebService(String url, String  method,Object[] param) {
        String result="";
        Object[] results = null;
        Client client = null;
        try {
            client = new Client(new URL(url));
            results = client.invoke(method, param);
        } catch (Exception e) {
            LOGGER.error("调用报错：[{}]",method,e);
        }finally {
            if(client!=null){
                client.close();
            }
        }
        if(results!=null){
            result=results[0].toString();
        }
        return result;
    }

    /**
     * HttpURLConnection形式请求webservice接口
     * @author <a href="mailto:zhongjinpeng@gtmap.cn>zhongjinpeng</a>"
     * @param requestUrl webservice接口请求地址
     * @param soapXml 组织SOAP协议数据
     * @return
     */
    public static String HttpURLConnection(String requestUrl, String soapXml){
        try{
            //1：创建服务地址
            URL url = new URL(requestUrl);
            //2：打开到服务地址的一个连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //3：设置连接参数
            //3.1设置发送方式：POST必须大写
            connection.setRequestMethod("POST");
            //3.2设置数据格式：Content-type
            connection.setRequestProperty("content-type", "text/xml;charset=utf-8");
            //3.3设置输入输出，新创建的connection默认是没有读写权限的，
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            //4：将组织的SOAP协议数据发送至服务端
            os.write(soapXml.getBytes());
            //5：接收服务端的响应
            int responseCode = connection.getResponseCode();
            //表示服务端响应成功
            if(200 == responseCode){
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                StringBuilder sb = new StringBuilder();
                String temp = null;

                while(null != (temp = br.readLine())){
                    sb.append(temp);
                }
                is.close();
                isr.close();
                br.close();
                return sb.toString();
            }
            InputStream is = connection.getErrorStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            String temp = null;

            while(null != (temp = br.readLine())){
                sb.append(temp);
            }
            is.close();
            isr.close();
            br.close();
            os.close();
            throw new AppException(sb.toString());
        }catch (Exception e){
            throw new AppException(e.getMessage());
        }
    }

    /**
     * 功能描述 蚌埠webservice接口调用
     *
     * @param wsdlUrl   接口地址
     * @param parameter 加密后的参数
     * @return java.lang.String
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @date 2020-12-24
     */
    public static String BengbuJy(String wsdlUrl, String parameter) {
        String result = "";
        StringBuffer sb = new StringBuffer("");
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sjy=\"SJYZX\">");
        sb.append("<soapenv:Header/>");
        sb.append("<soapenv:Body>");
        sb.append("<sjy:DoAsk>");
        sb.append("<sjy:XmlParam>");
        sb.append(parameter);
        sb.append("</sjy:XmlParam>");
        sb.append("</sjy:DoAsk>");
        sb.append("</soapenv:Body>");
        sb.append("</soapenv:Envelope>");
        try {
            int timeout = 10000;
            // HttpClient发送SOAP请求
            LOGGER.info("HttpClient 发送SOAP请求");
            HttpClient client = new HttpClient();
            PostMethod postMethod = new PostMethod(wsdlUrl);
            // 设置连接超时
            client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
            // 设置读取时间超时
            client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
            // 然后把Soap请求数据添加到PostMethod中
            RequestEntity requestEntity = new StringRequestEntity(sb.toString(), "text/xml", "UTF-8");
            //设置请求头部，否则可能会报 “no SOAPAction header” 的错误
            postMethod.setRequestHeader("SOAPAction", "SJYZX/NJYZX/DoAsk");
            // 设置请求体
            postMethod.setRequestEntity(requestEntity);
            int status = client.executeMethod(postMethod);
            // 打印请求状态码
            LOGGER.info("status:" + status);
            // 获取响应体输入流
            InputStream is = postMethod.getResponseBodyAsStream();
            // 获取请求结果字符串
            result = IOUtils.toString(is);
            LOGGER.info("result: " + result);
        } catch (Exception e) {
            LOGGER.error("---webservice请求异常,异常信息:{},异常堆栈:{},请求地址:{},参数:{}",e.getMessage(),e,wsdlUrl,parameter);
            throw new AppException(e.getMessage());
        }
        return result;
    }

}
