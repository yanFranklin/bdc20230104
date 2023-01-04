package cn.gtmap.realestate.exchange.util;


import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.Date;


/**
 * @author
 * @Description: WebService客户端通用类
 */
public class WebServiceClientHelper {

    /**
     * @Title: callService
     * @Description: 调用远程的webservice并返回数据
     * @param wsUrl ws地址
     * @param method 调用的ws方法名
     * @param arg 参数
     * @return
     * @return：String
     * @throws
     */
    private static final int CXF_CLIENT_TIME_OUT = 60 * 60 * 1000;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceClientHelper.class);

    public static StringBuilder callService(String wsUrl, String method, Object... arg) {
        LOGGER.info(DateUtil.formateTime(new Date()) + "开始调用" + wsUrl);
        // TODO 删掉调用该方法的所有方法
//        // 创建动态客户端
//        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        StringBuilder resultMsg = new StringBuilder();
//        // 创建客户端连接
//        try {
//            Client client = dcf.createClient(wsUrl);
//            //设置超时单位为毫秒
//            HTTPConduit conduit = (HTTPConduit) client.getConduit();
//            HTTPClientPolicy policy = new HTTPClientPolicy();
//            policy.setConnectionTimeout(CXF_CLIENT_TIME_OUT);
//            policy.setReceiveTimeout(CXF_CLIENT_TIME_OUT);
//            conduit.setClient(policy);
//
//            Object[] res = client.invoke(method, arg);
//            if (res != null && res.length > 0) {
//                resultMsg.append(res[0].toString());
//            }
//        } catch (Exception e) {
//            LOGGER.error(DateUtil.formateTime(new Date()) + "调用" + wsUrl + "失败！", e);
//        }
        return resultMsg;
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 设置cxf连接超时时间和响应超时时间
     */
    private static void setCxfClientTimeOut(Object client) {
//        Client proxy = ClientProxy.getClient(client);
//        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
//        HTTPClientPolicy policy = new HTTPClientPolicy();
//        policy.setConnectionTimeout(10000); //连接超时时间 10s
//        policy.setReceiveTimeout(300000);//请求超时时间 5min
//        conduit.setClient(policy);
    }

    /**
     * @param weburl 请求服务地址
     * @param param  参数
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @rerutn
     * @description 调用webservice
     */
    public static StringBuilder httpConnection(String weburl, String param) {
        StringBuilder stringBuilder = null;
        if (StringUtils.isNotBlank(weburl) && StringUtils.isNotBlank(param)) {
            stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            OutputStream outputStream = null;
            try {
                URL url = new URL(weburl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Accept-Charset", CharEncoding.UTF_8);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                outputStream = connection.getOutputStream();
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                printWriter.write(param);
                printWriter.close();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                LOGGER.error(DateUtil.formateTime(new Date()) + "调用" + weburl + "失败！", e);
            } catch (IOException e) {
                LOGGER.error(DateUtil.formateTime(new Date()) + "调用" + weburl + "失败！", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        LOGGER.error("errorMsg:", e);
                    }
                }
            }
        }
        return stringBuilder;
    }


    public static StringBuilder httpClient(String weburl, String param) {

        StringBuilder sb = null;
        if (StringUtils.isNotBlank(weburl) && StringUtils.isNotBlank(param)) {
            CloseableHttpResponse response = null;
            CloseableHttpClient client = null;
            try {
                client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(weburl);
                StringEntity entity = new StringEntity(param, CharEncoding.UTF_8);//解决中文乱码问题
                entity.setContentEncoding(CharEncoding.UTF_8);
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
                response = client.execute(httpPost);
                if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    sb = new StringBuilder();
                    sb.append(EntityUtils.toString(response.getEntity(), CharEncoding.UTF_8));
                }

            } catch (IOException e) {
                LOGGER.error(DateUtil.formateTime(new Date()) + "调用" + weburl + "失败！", e);
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        LOGGER.error("errorMsg:", e);
                    }
                }
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        LOGGER.error("errorMsg:", e);
                    }
                }
            }
        }
        return sb;
    }


    /**
     * @param url 请求服务地址
     * @param xml 请求的参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description webservice动态调用
     */
    public static String axis2Rpc(String url, String xml) {
        //使用RPC方式调用WebService
        RPCServiceClient serviceClient = null;
        String res = null;
        try {
            serviceClient = new RPCServiceClient();
            Options options = serviceClient.getOptions();
            EndpointReference targetEPR = new EndpointReference(url);
            options.setTo(targetEPR);
            OMFactory fac = OMAbstractFactory.getOMFactory();
            // 这个和qname差不多，设置命名空间
            OMNamespace omNs = fac.createOMNamespace("http://loushang.ws", "exchangeInfo");
            OMElement data = fac.createOMElement("exchangeInfo", omNs);
            // 对应参数的节点，xmlStr为参数名称
            String strName = "args0";
            // 参数值
            OMElement inner = fac.createOMElement(strName, omNs);
            inner.setText(xml);
            data.addChild(inner);
            OMElement object = serviceClient.sendReceive(data);
            res = object.getFirstElement().getText();
            serviceClient.cleanupTransport();
        } catch (Exception e) {
            LOGGER.error("errorMsg:", e);
        }
        return res;
    }

    /**
     * @param url       请求服务地址
     * @param namespace 请求服务的命名空间
     * @param method    调用服务的方法名
     * @param param     调用服务的方法参数名
     * @param arg       请求服务的参数
     * @return
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description webservice动态调用，可以成功请求.asmx后缀的soap地址
     */
    public static String axisCallService(String url, String namespace, String namespaceURL, String method, String param, Object... arg) {
        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
        Call call;
        String result = null;
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(namespace);
            // WSDL里面描述的接口名称(要调用的方法)
            call.setOperationName(new QName(namespaceURL, method));
            // 接口方法的参数名, 参数类型,参数模式  IN(输入), OUT(输出) or INOUT(输入输出)
            call.addParameter(new QName(namespaceURL, param), XMLType.XSD_STRING, ParameterMode.IN);
            // 设置被调用方法的返回值类型
            call.setReturnType(XMLType.XSD_STRING);
            // 给方法传递参数，并且调用方法
            result = (String) call.invoke(arg);
        } catch (ServiceException e) {
            LOGGER.error("errorMsg:", e);
        } catch (RemoteException e) {
            LOGGER.error("errorMsg:", e);
        }
        return result;
    }

}