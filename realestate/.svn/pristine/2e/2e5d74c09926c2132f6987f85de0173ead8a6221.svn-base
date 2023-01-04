package cn.gtmap.realestate.certificate.service;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcEcertificateConfigDTO;
import cn.gtmap.realestate.common.core.dto.certificate.DzzzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.CertOwnerDzzzDTO;
import cn.gtmap.realestate.common.core.dto.exchange.dzzz.DzzzcxxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/17
 * @description 电子证照服务
 */
public interface ECertificateService {
    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取电子证照的访问token值
     */
    String getAccessToken();

    /**
     * @param gzlslid    工作流实例ID
     * @param currentUserName
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 创建电子证照
     */
    void createECertificate(String gzlslid, String currentUserName);

    /**
     * @param bdcZsDOList     证书信息
     * @param bdcXmDO         项目信息
     * @param currentUserName 当前用户
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 电子证照生成请求服务
     */
    List<DzzzResponseModel<Map>> requestCreateECertificate(List<BdcZsDO> bdcZsDOList, BdcXmDO bdcXmDO, String currentUserName);

    /**
     * @param gzlslid         工作流实例ID
     * @param currentUserName 当前用户账号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 注销原项目电子证照
     */
    void cancelYxmECertificate(String gzlslid, String currentUserName);

    /**
     * @param bdcXmDO         项目信息
     * @param currentUserName 当前用户账号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 注销原项目电子证照
     */
    void cancelYxmECertificate(BdcXmDO bdcXmDO, String currentUserName);

    /**
     * @param gzlslid         工作流实例ID
     * @param zzbs            证照标识
     * @param fileName        上传大云的电子证照文件名
     * @param currentUserName 当前用户信息
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 将电子证照内容上传大云附件
     */
    StorageDto uploadECertificate(String gzlslid, String zzbs, String fileName, String currentUserName) throws IOException;

    /**
     * 获取电子证照附件
     * @param gzlslid 工作流实例Id
     * @param bdcqzh 产权证号
     * @return StorageDto 大云存储对象
     */
    StorageDto getECertificateFile(String gzlslid, String bdcqzh);

    /**
     * @param zzbs 证照标识
     * @return Base64字符串值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 下载电子证照，返回Base64字符串值
     */
    String getECertificateContent(String zzbs);

    /**
     * 获取电子证照相关配置
     *
     * @return BdcEcertificateConfigDTO 电子证照配置实体
     * @Date 2020/2/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    BdcEcertificateConfigDTO queryEcertificateConfig();

    /**
     * @param bdcXmDO         项目信息
     * @param currentUserName 当前请求的用户名
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成当前项目的电子证照信息
     */
    List<DzzzResponseModel<Map>> createXmECertificate(BdcXmDO bdcXmDO, String currentUserName);

    /**
     * 下载电子证照并上传到登记 (处理生成证照时下载失败的数据)
     *
     * @param bdcXmDO 项目ID
     * @return
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    void reUploadECertificate(BdcXmDO bdcXmDO, String currentUserName);

    /**
     * @param bdcXmDO         当前项目信息
     * @param zzbgyy          证照变更原因{@code 1：注销； @code 2：作废}
     * @param currentUserName 当前请求的用户信息
     * @return List<DzzzResponseModel < Map>> 电子证照请求结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 注销当前项目的电子证照信息
     */
    List<DzzzResponseModel> cancelXmECertificate(BdcXmDO bdcXmDO, String zzbgyy, String currentUserName);

    /**
     * @param zsidList 证书ID信息
     * @return List<DzzzResponseModel   <   Map>> 查询结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书的电子证照状态
     */
    List<DzzzResponseModel<List<Map>>> checkZzzt(List<String> zsidList);


    /**
     * 调取大数据局的生成电子证照接口，生成电子证照
     * @param gzlslid
     * @param zsid
     * @param currentUserName
     * @return 证照信息
     */
    Object createHefeiDzzz(String gzlslid, String zsid, String currentUserName,String taskName);

    /**
     * 压缩并上传电子证照
     * @param gzlslid
     * @Date 2022/11/2
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    void xzysdzzz(String gzlslid);

    /**
     * 调取大数据局的注销电子证照接口，注销电子证照
     * @param gzlslid
     * @return 注销结果
     */
    void zxHefeiDzzz(String gzlslid,String zsid);

    /**
     * 查询电子证照
     * @param slbh
     * @param spxtywh
     * @return
     */
    List queryDzzzBySlbh(String slbh,String spxtywh, Boolean parseBase64);

    /**
     * 验证是否可办结
     * @param processInsId
     * @return
     */
    boolean yzDzzzSfkbj(String processInsId);

    /**
     * 查询电子证照
     * @param zzbs
     * @return
     */
    Object queryDzzzByZzbs(String zzbs);

    /**
     * 同步电子证照信息，市级接口同步证照信息时调用，
     * 将证照信息上传至大云
     *
     * @param bdcqzh    不动产产权证号
     * @param base64str base64加密字符串
     * @return
     */
    Object syncDzzzxx(DzzzxxDTO dzzzxxDTO);

    /**
     * 上传电子证照内容至文件中心
     *
     * @param gzlslid
     * @param zzbs
     * @param fileName
     * @param currentUserName
     * @param base64str
     * @return cn.gtmap.gtc.storage.domain.dto.StorageDto
     * @author <a href="mailto:lixin1@lixin.com">lixin</a>
     */
    StorageDto uploadECertificateToWjzx(String gzlslid, String zzbs, String fileName, String currentUserName, String base64str);

    /**
     * 批量电子证照注销
     *
     * @param count
     */
    void batchCancelXmECertificate(String count);

    /**
     * 查询个人电子证照信息
     * @param certownerno
     * @return
     */
    CertOwnerDzzzDTO.Custom getQlrDzzz(String certownerno);

    /**
     * 查询电子证照信息
     * @param dzzzcxxxRequestDTO
     * @return
     */
    DzzzResponseModel getDzzzxx(DzzzcxxxRequestDTO dzzzcxxxRequestDTO);
}
