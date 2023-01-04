package cn.gtmap.realestate.common.core.service.rest.certificate;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcEcertificateConfigDTO;
import cn.gtmap.realestate.common.core.dto.certificate.DzzzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.CertOwnerDzzzDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.DzqzCsDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData;
import cn.gtmap.realestate.common.core.dto.exchange.dzzz.DzzzcxxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/17
 * @description 电子证照rest服务
 */
public interface ECertificateRestService {
    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取电子证照的访问token值
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/token")
    String getECertificateToken();

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 创建电子证照
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate")
    void createECertificate(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @param bdcXmDO 项目ID
     * @return List<DzzzResponseModel < Map>>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成项目的电子证照，返回电子证照请求结果
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/xm")
    List<DzzzResponseModel<Map>> createXmECertificate(@RequestBody BdcXmDO bdcXmDO, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * 下载电子证照并上传到登记 (处理生成证照时下载失败的数据)
     *
     * @param bdcXmDO 项目ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/xm/file")
    void reUploadECertificate(@RequestBody BdcXmDO bdcXmDO, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 注销原项目电子证照
     */
    @PutMapping(value = "/realestate-certificate/rest/v1.0/eCertificate")
    void cancelYxmECertificate(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @param bdcXmDO         当前项目信息
     * @param currentUserName 当前请求的用户信息
     * @return List<DzzzResponseModel < Map>> 电子证照请求结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 注销当前项目的电子证照信息
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/xm/zx")
    List<DzzzResponseModel> cancelXmECertificate(@RequestBody BdcXmDO bdcXmDO, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @param bdcXmDO         当前项目信息
     * @param currentUserName 当前请求的用户信息
     * @return List<DzzzResponseModel < Map>> 电子证照请求结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 作废当前项目的电子证照信息
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/xm/zf")
    List<DzzzResponseModel> zfXmECertificate(@RequestBody BdcXmDO bdcXmDO, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @param bdcXmDO         当前流程的项目信息
     * @param currentUserName 当前请求的用户信息
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据当前项目注销原项目的电子证照信息
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/yxm")
    void cancelYxmECertificate(@RequestBody BdcXmDO bdcXmDO, @RequestParam(value = "currentUserName") String currentUserName);

    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @param zzbs 证照标识
     * @return Base64字符串值
     * @description 下载电子证照，返回Base64字符串值
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/download")
    String getECertificateContent(@RequestParam(value = "zzbs", required = false) String zzbs);

    /**
     * @param gzlslid 工作流实例ID
     * @param zzbs 证照标识
     * @param fileName 上传大云的电子证照文件名
     * @return Base64字符串值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取电子证照，并上传文件中心
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/upload/{gzlslid}")
    void uploadECertificateContent(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "zzbs") String zzbs, @RequestParam(value = "fileName") String fileName) throws IOException;


    /**
     * @param gzlslid 工作流实例ID
     * @param bdcqzh  不动产权证号
     * @return 字符窜
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取电子证照上传文件中心的附件的下载路径
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/file/{gzlslid}")
    String getECertificateFilePath(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "bdcqzh") String bdcqzh);

    /**
     * 获取电子证照相关配置
     *
     * @return BdcEcertificateConfigDTO 电子证照配置实体
     * @Date 2020/2/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/queryconfig")
    BdcEcertificateConfigDTO queryConfig();

    /**
     * @param zsidList 证书ID信息
     * @return List<DzzzResponseModel   <   Map>> 查询结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书的电子证照状态
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/zzzt")
    List<DzzzResponseModel<List<Map>>> checkZzzt(@RequestBody List<String> zsidList);


    /**
     * 调取大数据局的制证接口 生成电子证照 合肥
     * @param processInsId
     * @param zsid
     * @param currentUserName
     * @return 证照信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/createHefeiDzzz")
    Object createHefeiDzzz(@RequestParam(value = "processInsId",required = false) String processInsId,
                           @RequestParam(value = "zsid",required = false) String zsid,
                           @RequestParam(value = "currentUserName",required = false) String currentUserName,
                           @RequestParam(value = "currentNodeName",required = false) String currentNodeName
    );

    /**
     * 压缩并上传电子证照
     * @param processInsId
     * @Date 2022/11/2
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/xzysdzzz/{processInsId}")
    void xzysdzzz(@PathVariable(value = "processInsId") String processInsId);

    /**
     * 调取大数据局的制证接口 注销电子证照 合肥
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/zxHefeiDzzz")
    void zxHefeiDzzz(@RequestParam(value = "processInsId",required = false) String processInsId,
                     @RequestParam(value = "zsid",required = false) String zsid);

    /**
     * 查询电子证照
     * @param slbh
     * @param spxtywh
     * @param parseBase64
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/queryDzzzBySlbh")
    Object queryDzzzBySlbh(@RequestParam(value = "slbh",required = false) String slbh,
                           @RequestParam(value = "spxtywh",required = false) String spxtywh,
                           @RequestParam(value = "parseBase64",required = false) Boolean parseBase64);

    /**
     * 验证当前的电子证照的节点是否满足办结条件
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/yzDzzzSfkbj")
    boolean yzDzzzSfkbj(@RequestParam(value = "processInsId",required = true) String processInsId);


    /**
     * 查询电子证照
     * @param zzbs
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/queryDzzzByZzbs")
    Object queryDzzzByZzbs(@RequestParam(value = "zzbs",required = true) String zzbs);


    /**
     * 同步电子证照信息，市级接口同步证照信息时调用，
     * 将证照信息上传至大云
     *
     * @param bdcqzh    不动产产权证号
     * @param base64str base64加密字符串
     * @return
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/syncDzzzxx")
    Object syncDzzzxx(@RequestBody DzzzxxDTO dzzzxxDTO);

    /**
     * 常州电子签章
     *
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/changzhou/zzqz")
    void changzhouZzqz(@RequestParam(value = "processInsId") String processInsId);

    /**
     * 常州 推送单个证书电子签章
     * @param zsid 证书id
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/changzhou/bdczs/zzqz")
    void tsBdcZsDzqz(@RequestParam(value = "zsid") String zsid);

    /**
     * 生成证照
     * <p>
     * 电子证照 2.0 版本
     * </p>
     *
     * @param xmid xmid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/v2.0/mbpzPdf")
    List<DzzzResponseData> createZzqz(@RequestParam(value = "xmid") String xmid);

    /**
     * 注销/作废 电子证照
     * <p>
     * 电子证照 2.0 版本
     * </p>
     *
     * @param xmid xmid
     * @param bgyy 变更原因 1：注销 2:作废
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/v2.0/zzzx")
    List<DzzzResponseData> cancelZzqz(@RequestParam(value = "xmid") String xmid, @RequestParam(value = "bgyy") Integer bgyy);

    /**
     * 注销单个证书的电子签章
     * @param zsid 证书id
     * @param bgyy 变更原因
     * @return 注销结果信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/v2.0/dzqz/zzzx")
    DzzzResponseData cancelBdcZsZzqz(@RequestParam(value = "zsid") String zsid, @RequestParam(value = "bgyy") Integer bgyy);

    /**
     * 常州电子签章 文件下载
     * <p>
     * 电子证照 2.0 版本
     * </p>
     *
     * @param gzlslid 工作流实例ID
     * @param zsid    证书 id
     * @param symd    symd
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/v2.0/zzxxxz")
    void zzxxxz(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "zsid") String zsid,
                @RequestParam(value = "symd") Integer symd);

    /**
     * 存量电子证照签发
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/clzz")
    Integer clzzQf(@RequestBody List<String> bdcqzhs);

    /**
     * 存量电子证照 pdf 下载
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/clzz/pdf")
    Integer clzzPdf(@RequestBody List<String> zzbsList);

    /**
     * 批量电子证照注销
     *
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     */
    @GetMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/batch/cancel/ls")
    void batchCancelXmECertificate(@RequestParam(value = "count") String count);

    /**
     * @param ggid 公告信息ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 配置生成
     * @date : 2022/3/14 9:24
     */
    @PostMapping("/realestate-certificate/rest/v1.0/eCertificate/tsgg/cz")
    void tsggqz(@RequestParam(value = "ggid") String ggid);

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织生成电子签章的一些必要信息
     * @date : 2022/8/24 13:47
     */
    @PostMapping("/realestate-certificate/rest/v1.0/eCertificate/dzqzcs")
    EZsDTO generateDzqzxx(@RequestBody DzqzCsDTO dzqzCsDTO);


    /**
     * 【连云港市】根据身份证号获取电子证照信息接口
     *
     * @param certownerno
     * @return
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/queryDzzzBySfzh")
    CertOwnerDzzzDTO queryDzzzBySfzh(@RequestParam(value = "certownerno",required = true) String certownerno);

    /**
     * 获取电子证照信息接口
     *
     * @param dzzzcxxxRequestDTO
     * @return
     */
    @PostMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/queryDzzzxx")
    DzzzResponseModel queryDzzzxx(@RequestBody DzzzcxxxRequestDTO dzzzcxxxRequestDTO);
}
