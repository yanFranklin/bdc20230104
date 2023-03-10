
package cn.gtmap.realestate.exchange.util.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "JrrzService", targetNamespace = "http://loushang.ws")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface JrrzService {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "uploadData", targetNamespace = "http://loushang.ws", className = "cn.gtmap.realestate.exchange.util.webservice.UploadData")
    @ResponseWrapper(localName = "uploadDataResponse", targetNamespace = "http://loushang.ws", className = "cn.gtmap.realestate.exchange.util.webservice.UploadDataResponse")
    public String uploadData(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0,
            @WebParam(name = "arg1", targetNamespace = "")
                    boolean arg1);

    /**
     * 
     * @param args0
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "urn:exchangeInfo")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "exchangeInfo", targetNamespace = "http://loushang.ws", className = "cn.gtmap.realestate.exchange.util.webservice.ExchangeInfo")
    @ResponseWrapper(localName = "exchangeInfoResponse", targetNamespace = "http://loushang.ws", className = "cn.gtmap.realestate.exchange.util.webservice.ExchangeInfoResponse")
    public String exchangeInfo(
            @WebParam(name = "args0", targetNamespace = "http://loushang.ws")
                    String args0);

}
