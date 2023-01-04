package cn.gtmap.realestate.exchange.service.impl.inf.request;

import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.bo.reqProp.NtCssnjWebservicePropBO;
import cn.gtmap.realestate.exchange.service.inf.build.InterfaceRequestBuilder;
import cn.gtmap.realestate.exchange.service.inf.request.InterfaceRequestService;
import cn.com.infosec.netsign.agent.NetSignAgent;
import cn.com.infosec.netsign.agent.NetSignResult;
import cn.com.infosec.netsign.agent.exception.NetSignAgentException;
import cn.com.infosec.netsign.agent.exception.ServerProcessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">zedq</a>
 * @version 1.0  2020-09-24
 * @description 盐城 非税 签章
 */
@Service(value = "ycFsSign")
public class YcFsSignRequestServiceImpl extends InterfaceRequestService<NtCssnjWebservicePropBO> {
    /**
     * @param builder
     * @return java.lang.Object
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 发送请求
     */
    @Override
    public void sendRequest(InterfaceRequestBuilder builder) {
        try {
            //签名原文
            byte[] laintext = "11111".getBytes() ;
            //签名证书DN，null表示用服务器默认证书进行签名（填证书序列号）
            String subject = null ;
            //摘要算法，null表示用服务器默认的摘要算法（默认就是SM2）
            String digestAlg = null ;
            //是否做TSA签名
            boolean useTSA = false ;
            //签名
            NetSignResult result = NetSignAgent.detachedSignature(laintext , subject , digestAlg , useTSA ) ;
            //获取byte形式的签名结果
            byte[] signedText = result.getByteArrayResult( NetSignResult.SIGN_TEXT ) ;
            //获取base64编码后的签名结果
            String signedTextB64 = result.getStringResult( NetSignResult.SIGN_TEXT ) ;
        } catch ( NetSignAgentException e ) {
            e.printStackTrace();
        } catch ( ServerProcessException e ) {
            e.printStackTrace();
        }
    }

    /**
     * 处理外部接口返回体转化为exchange内部的返回体,由子类实现
     *
     * @param response
     * @return
     */
    @Override
    public String dealWithResponse(String response) {return response;}


    public static String createSendSOAPPackageForMap(HashMap<String, Object> reqMap, String channelId) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version='1.0' encoding='UTF-8'?>");
        sb.append("<reqws>");
        sb.append("<tran_id>CSSNJ.WS.FCJY.QUERYDSF</tran_id>");
        sb.append("<channel_id>").append(channelId).append("</channel_id>");
        sb.append("<tran_seq>").append(UUIDGenerator.generate()).append("</tran_seq>");
        sb.append("<body>");
        sb.append("<![CDATA[");
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<taxxml>");
        sb.append("<inobj>");
        for (Iterator<String> iter = reqMap.keySet().iterator(); iter.hasNext(); ) {
            String key = iter.next();
            String value = (String) reqMap.get(key);
            sb.append("<" + key + ">" + value + "</" + key + ">");
        }
        sb.append("</inobj>");
        sb.append("</taxxml>");
        sb.append("]]>");
        sb.append("</body>");
        sb.append("</reqws>");
        return sb.toString();
    }

}
