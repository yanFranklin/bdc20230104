package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.certificate.core.model.dzzzgx.down.DzzzDownRequestModel;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/27
 */
public interface BdcDzzzDownloadService {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param dzzzDownRequestModel
     * @return
     * @description 电子证照系统提供获取文件地址功能
     */
    DzzzResponseModel dzzzDownloadUrl(DzzzDownRequestModel dzzzDownRequestModel, HttpServletRequest request);

    /**
     * @param dzzzDownRequestModel
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 电子证照系统提供获取文件信息功能
     */
    DzzzResponseModel dzzzDownloadFile(DzzzDownRequestModel dzzzDownRequestModel, HttpServletRequest request);

    /**
     * 电子证照系统提供存量证照获取文件信息功能
     *
     * @param dzzzDownRequestModel
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     */
    DzzzResponseModel dzzzDownloadClFile(DzzzDownRequestModel dzzzDownRequestModel, HttpServletRequest request);


    /**
     * @param zzwjlj
     * @return
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @description 通过下载地址获取证照文件功能
     */
    DzzzResponseModel dzzzDownloadFileByUrl(String zzwjlj);

    DzzzResponseModel dzzzDownloadCheck(String jsonString);
}
