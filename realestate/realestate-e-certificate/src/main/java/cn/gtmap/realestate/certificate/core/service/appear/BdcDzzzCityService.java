package cn.gtmap.realestate.certificate.core.service.appear;


import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.SyncDzzzClxxDTO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.query.DzzzQueryRequestModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BdcDzzzCityService {

    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 中转接口 证照签发 生成pdf接口 市级
     */
    DzzzResponseModel zzpdf(String jsonString, HttpServletRequest request);

    /**
     * @param
     * @author @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @rerutn
     * @description 中转接口 证照签发(存量) 生成pdf接口
     */
    DzzzResponseModel clzzpdf(String jsonString, HttpServletRequest request);


    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 中转接口 证照注销接口 市级
     */
    DzzzResponseModel zzzx(String jsonString, HttpServletRequest request);

    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 中转接口 证照下载文件 市级
     */
    DzzzResponseModel dzzzDownloadFile(String jsonString, HttpServletRequest request);

    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 中转接口 证照下载地址 市级
     */
    DzzzResponseModel dzzzDownloadUrl(String jsonString, HttpServletRequest request);

    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 中转接口 证照查询 市级
     */
    DzzzResponseModel zzcx(DzzzQueryRequestModel dzzzQueryRequestModel, HttpServletRequest request);

    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 中转接口 证照检索 市级
     */
    DzzzResponseModel zzjs(DzzzQueryRequestModel dzzzQueryRequestModel, HttpServletRequest request);

    /**
     * @param
     * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
     * @rerutn
     * @description 中转接口 证照元数据 市级
     */
    DzzzResponseModel zzysj(String jsonString, HttpServletRequest request);

    /**
     *
     * @param zzids
     * @return
     * @description 同步证照PDF 市级
     */
    DzzzResponseModel syncDzzzPdf(String zzids);

    /**
     *
     * @param zzbs
     * @description 同步证照数据 市级
     */
    void syncDzzz(String zzbs);

    void syncDzzzDownloadInfo(String zzbs);

    /**
     *
     * @param responseModel
     * @description
     */
    DzzzResponseModel zzdzxzReplaceRestToFeign(DzzzResponseModel responseModel);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 同步电子证照材料信息(安徽地方)
     */
    void syncDzzzClxx(List<SyncDzzzClxxDTO> syncDzzzClxxDTOList);
}
