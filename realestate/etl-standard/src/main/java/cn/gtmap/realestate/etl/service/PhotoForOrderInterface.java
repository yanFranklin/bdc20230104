package cn.gtmap.realestate.etl.service;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2020/06/23,1.0
 * @description 蚌埠交易查询webservice
 */
public interface PhotoForOrderInterface {

    /**
     * 交易查询受理编号关联的文件信息
     *
     * @param param
     * @return
     */
    @WebMethod
    @WebResult()
    public String queryAllFileInfo(@WebParam(name = "param") String param) throws Exception;

    /**
     * 交易查询单个文件信息
     *
     * @param param
     * @return
     */
    @WebMethod
    @WebResult()
    public String queryFileInfo(@WebParam(name = "param") String param) throws Exception;

    /**
     * 文件下载
     *
     * @param filename
     * @return
     */
    @WebMethod
    @WebResult()
    public String fileDownload(String filename);

}
