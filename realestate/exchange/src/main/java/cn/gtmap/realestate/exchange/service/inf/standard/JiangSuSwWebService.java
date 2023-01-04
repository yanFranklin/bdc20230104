package cn.gtmap.realestate.exchange.service.inf.standard;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/10/8
 * @description 江苏税务webservice相关接口处理实现类
 */

@WebService(name = "JiangSuSwWebService", targetNamespace = "http://service.exchange.realestate.gtmap.cn")
public interface JiangSuSwWebService {

    /**
     * 接受税务返回的任务状态
     *
     * @param xmlStr 任务状态
     * @return
     * @Date 2022/10/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @WebMethod
    @WebResult()
    String RWZTTS(@WebParam(name = "xmlStr") String xmlStr);
}
