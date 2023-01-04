package cn.gtmap.realestate.etl.service;


import cn.gtmap.realestate.common.core.domain.CommonResponse;

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
     *
     * @param param
     * @return
     */
    @WebMethod
    @WebResult()
    public String checkResultInfo(@WebParam(name = "param") String param) throws Exception;


    /**
     * 保存人脸识别接口上传的图片，更新文件id到项目表
     *
     * @param
     * @return
     * @Date 2021/11/5
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @WebMethod
    @WebResult()
    CommonResponse saveCheckFacePicture(@WebParam(name = "param") String param) throws Exception;


    /**
     * 查询人脸识别接口上传的图片，更新文件id到项目表
     *
     * @param
     * @return
     * @Date 2021/11/5
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @WebMethod
    @WebResult()
    String queryCheckFacePicture(String ywnum);
}
