package cn.gtmap.realestate.etl.service;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 2020/06/23,1.0
 * @description 蚌埠交易查询webservice
 */
@WebService(name = "FaceCheckInterface", targetNamespace = "http://service.etl.realestate.gtmap.cn")
public interface FaceCheckInterface {

    /**
     * 人脸识别结果返回
     * @param param
     * @return
     */
    @WebMethod
    @WebResult()
    public String checkResultInfo(@WebParam(name = "param") String param) throws Exception;

}
