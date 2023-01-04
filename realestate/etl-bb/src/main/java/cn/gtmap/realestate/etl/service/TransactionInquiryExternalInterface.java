package cn.gtmap.realestate.etl.service;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.WebServiceProvider;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020/06/23,1.0
 * @description 蚌埠交易查询webservice
 */
@WebService(name = "TransactionInquiryExternalInterface", targetNamespace = "http://service.etl.realestate.gtmap.cn")
public interface TransactionInquiryExternalInterface {

    /**
     * 交易查询
     * @param param
     * @return
     */
    @WebMethod
    @WebResult()
    public String DoAsk(@WebParam(name = "param") String param) throws Exception;

}
