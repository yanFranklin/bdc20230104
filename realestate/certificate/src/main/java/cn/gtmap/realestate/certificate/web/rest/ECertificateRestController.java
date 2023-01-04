package cn.gtmap.realestate.certificate.web.rest;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.certificate.service.ECertificate2Service;
import cn.gtmap.realestate.certificate.service.ECertificateClService;
import cn.gtmap.realestate.certificate.service.ECertificateService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.web.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcEcertificateConfigDTO;
import cn.gtmap.realestate.common.core.dto.certificate.DzzzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.CertOwnerDzzzDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.DzqzCsDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.EZsDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData;
import cn.gtmap.realestate.common.core.dto.exchange.dzzz.DzzzcxxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.ResponseHead;
import cn.gtmap.realestate.common.core.service.rest.certificate.ECertificateRestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/17
 * @description 电子证照RestController
 */
@RestController
@Api(tags = "电子证照RestController")
public class ECertificateRestController extends BaseController implements ECertificateRestService {
    @Autowired
    ECertificateService eCertificateService;
    @Autowired
    ECertificate2Service eCertificate2Service;
    @Autowired
    ECertificateClService eCertificateClService;

    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取电子证照的访问token值
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("创建电子证照")
    public String getECertificateToken() {
        return eCertificateService.getAccessToken();
    }

    /**
     * @param processInsId    工作流实例ID
     * @param currentUserName
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 创建电子证照
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("创建电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "string", paramType = "query")
            , @ApiImplicitParam(name = "currentUserName", value = "用户名/账户名", dataType = "string", paramType = "query")})
    public void createECertificate(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) {
        eCertificateService.createECertificate(processInsId, currentUserName);
    }

    /**
     * @param bdcXmDO         项目ID
     * @param currentUserName
     * @return List<DzzzResponseModel       <       Map>>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成项目的电子证照，返回电子证照请求结果
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("创建项目的电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目ID", dataType = "string", paramType = "query")
            , @ApiImplicitParam(name = "currentUserName", value = "用户名/账户名", dataType = "string", paramType = "query")})
    public List<DzzzResponseModel<Map>> createXmECertificate(@RequestBody BdcXmDO bdcXmDO, @RequestParam(value = "currentUserName") String currentUserName) {
        return eCertificateService.createXmECertificate(bdcXmDO, currentUserName);
    }

    /**
     * 下载电子证照并上传到登记 (处理生成证照时下载失败的数据)
     *
     * @param bdcXmDO         项目ID
     * @param currentUserName 用户名
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void reUploadECertificate(@RequestBody BdcXmDO bdcXmDO, @RequestParam(value = "currentUserName") String currentUserName) {
        eCertificateService.reUploadECertificate(bdcXmDO, currentUserName);
    }

    /**
     * @param processInsId    工作流实例ID
     * @param currentUserName
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 注销原项目电子证照
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("注销原项目电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", dataType = "string", paramType = "query")
            , @ApiImplicitParam(name = "currentUserName", value = "用户名/账户名", dataType = "string", paramType = "query")})
    public void cancelYxmECertificate(@RequestParam(value = "processInsId") String processInsId, @RequestParam(value = "currentUserName") String currentUserName) {
        eCertificateService.cancelYxmECertificate(processInsId, currentUserName);
    }

    /**
     * @param bdcXmDO         当前项目信息
     * @param currentUserName 当前请求的用户信息
     * @return List<DzzzResponseModel < Map>> 电子证照请求结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 注销当前项目的电子证照信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("注销当前项目电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXmDO", value = "当前项目信息", dataType = "BdcXmDO", paramType = "body")
            , @ApiImplicitParam(name = "currentUserName", value = "用户名/账户名", dataType = "string", paramType = "query")})
    public List<DzzzResponseModel> cancelXmECertificate(@RequestBody BdcXmDO bdcXmDO, @RequestParam(value = "currentUserName") String currentUserName) {
        return eCertificateService.cancelXmECertificate(bdcXmDO, Constants.ZZBGYY_BDC_ZX, currentUserName);
    }

    /**
     * @param bdcXmDO         当前项目信息
     * @param currentUserName 当前请求的用户信息
     * @return List<DzzzResponseModel   <   Map>> 电子证照请求结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 作废当前项目的电子证照信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("作废当前项目的电子证照信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXmDO", value = "当前项目信息", dataType = "BdcXmDO", paramType = "body")
            , @ApiImplicitParam(name = "currentUserName", value = "用户名/账户名", dataType = "string", paramType = "query")})
    public List<DzzzResponseModel> zfXmECertificate(@RequestBody BdcXmDO bdcXmDO, @RequestParam(value = "currentUserName") String currentUserName) {
        return eCertificateService.cancelXmECertificate(bdcXmDO, Constants.ZZBGYY_BDC_ZF, currentUserName);
    }

    /**
     * @param bdcXmDO         当前流程的项目信息
     * @param currentUserName 当前请求的用户信息
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据当前项目注销原项目的电子证照信息
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据当前项目信息，注销原项目电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcXmDO", value = "当前项目信息", dataType = "BdcXmDO", paramType = "body")
            , @ApiImplicitParam(name = "currentUserName", value = "用户名/账户名", dataType = "string", paramType = "query")})
    public void cancelYxmECertificate(@RequestBody BdcXmDO bdcXmDO, @RequestParam(value = "currentUserName") String currentUserName) {
        eCertificateService.cancelYxmECertificate(bdcXmDO, currentUserName);
    }

    /**
     * @param zzbs 证照标识
     * @return Base64字符串值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 下载电子证照，返回Base64字符串值
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("下载电子证照，返回Base64字符串值")
    @ApiImplicitParams({@ApiImplicitParam(name = "zzbs", value = "证照标识", dataType = "string", paramType = "query")})
    public String getECertificateContent(@RequestParam(value = "zzbs", required = false) String zzbs) {
        return eCertificateService.getECertificateContent(zzbs);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param zzbs    证照标识
     * @param fileName 上传大云的电子证照文件名
     * @return Base64字符串值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取电子证照，并上传文件中心
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("下载电子证照，返回Base64字符串值")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
            , @ApiImplicitParam(name = "zzbs", value = "证照标识", dataType = "string", paramType = "query")})
    public void uploadECertificateContent(@PathVariable(value = "gzlslid") String gzlslid, @RequestParam(value = "zzbs") String zzbs, @RequestParam(value = "fileName") String fileName) throws IOException {
        eCertificateService.uploadECertificate(gzlslid, zzbs, fileName, userManagerUtils.getCurrentUserName());
    }

    /**
     * @param gzlslid 工作流实例ID
     * @param bdcqzh    不动产权证号
     * @return 字符窜
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 获取电子证照上传文件中心的附件的下载路径
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取电子证照上传文件中心的附件的下载路径")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path")
            , @ApiImplicitParam(name = "bdcqzh", value = "证号", dataType = "string", paramType = "query")})
    public String getECertificateFilePath(@PathVariable("gzlslid") String gzlslid, @RequestParam(value = "bdcqzh") String bdcqzh) {
        String path = StringUtils.EMPTY;
        StorageDto storageDto = eCertificateService.getECertificateFile(gzlslid, bdcqzh);
        if (storageDto != null && StringUtils.isNotBlank(storageDto.getDownUrl())) {
            path = storageDto.getDownUrl();
        }
        return path;
    }

    /**
     * 获取电子证照相关配置
     *
     * @return BdcEcertificateConfigDTO 电子证照配置实体
     * @Date 2020/2/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取电子证照相关配置")
    public BdcEcertificateConfigDTO queryConfig() {
        return eCertificateService.queryEcertificateConfig();
    }

    /**
     * @param zsidList 证书ID信息
     * @return List<DzzzResponseModel   <   Map>> 查询结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书的电子证照状态
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("查询证书的电子证照状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsidList", value = "证书ID信息", dataType = "List", paramType = "body")})
    public List<DzzzResponseModel<List<Map>>> checkZzzt(@RequestBody List<String> zsidList) {
        return eCertificateService.checkZzzt(zsidList);
    }

    /**
     * 调取大数据局的制证接口 生成电子证照 合肥
     * @param processInsId
     * @param zsid
     * @param currentUserName
     * @return 证照信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("调取大数据局接口生成电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zsid", value = "证书id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "currentUserName", value = "当前用户", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "currentNodeName", value = "节点名称", dataType = "String", paramType = "query")})
    public Object createHefeiDzzz(@RequestParam(value = "processInsId",required = false) String processInsId,
                                  @RequestParam(value = "zsid",required = false) String zsid,
                                  @RequestParam(value = "currentUserName",required = false) String currentUserName,
                                  @RequestParam(value = "currentNodeName",required = false) String currentNodeName) {
        return eCertificateService.createHefeiDzzz(processInsId, zsid,currentUserName,currentNodeName);
    }

    /**
     * 压缩并上传电子证照
     * @param processInsId
     * @Date 2022/11/2
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("下载电子证照并压缩上传")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", dataType = "String", paramType = "path")})
    public void xzysdzzz(@PathVariable(value = "processInsId") String processInsId) {
        eCertificateService.xzysdzzz(processInsId);
    }


    /**
     * 调取大数据局的制证接口 注销电子证照 合肥
     * @param processInsId
     * @return 注销结果
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("调取大数据局接口注销电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zsid", value = "证书id", dataType = "String", paramType = "query")})
    public void zxHefeiDzzz(@RequestParam(value = "processInsId",required = false) String processInsId,
                            @RequestParam(value = "zsid",required = false) String zsid) {
        eCertificateService.zxHefeiDzzz(processInsId,zsid);
    }


    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据受理编号查询流程下的所有电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "slbh", value = "受理编号", dataType = "String", paramType = "query")
            ,@ApiImplicitParam(name = "spxtywh", value = "审批系统业务号", dataType = "String", paramType = "query")})
    public List queryDzzzBySlbh(@RequestParam(value = "slbh",required = false) String slbh,
                                @RequestParam(value = "spxtywh",required = false) String spxtywh,
                                @RequestParam(value = "parseBase64",required = false) Boolean parseBase64) {
        return eCertificateService.queryDzzzBySlbh(slbh,spxtywh,parseBase64);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("验证当前的电子证照的节点是否满足办结条件")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", dataType = "String", paramType = "query")})
    public boolean yzDzzzSfkbj(@RequestParam(value = "processInsId",required = true) String processInsId) {
        return eCertificateService.yzDzzzSfkbj(processInsId);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据证照标识查询流程下的所有电子证照")
    @ApiImplicitParams({@ApiImplicitParam(name = "zzbs", value = "证照标识", dataType = "String", paramType = "query")})
    public Object queryDzzzByZzbs(@RequestParam(value = "zzbs",required = true) String zzbs) {
        return eCertificateService.queryDzzzByZzbs(zzbs);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("同步电子证照信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "电子证照信息", value = "电子证照信息", dataType = "DzzzxxDTO", paramType = "body")
    })
    public Object syncDzzzxx(@RequestBody DzzzxxDTO dzzzxxDTO) {
        return eCertificateService.syncDzzzxx(dzzzxxDTO);
    }

    /**
     * 常州电子签章
     *
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("常州电子签章")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "processInsId", dataType = "String", paramType = "query")})
    public void changzhouZzqz(@RequestParam(value = "processInsId") String processInsId) {
        eCertificate2Service.changzhouZzqz(processInsId);
    }

    /**
     * 常州 推送单个证书电子签章
     * @param zsid 证书id
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("常州 推送单个证书电子签章")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "zsid", dataType = "String", paramType = "query")})
    public void tsBdcZsDzqz(@RequestParam(value = "zsid") String zsid) {
        eCertificate2Service.tsBdcZsDzqz(zsid);
    }

    /**
     * 生成证照
     * <p>
     * 电子证照 2.0 版本
     * </p>
     *
     * @param xmid xmid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public List<DzzzResponseData> createZzqz(String xmid) {
        return eCertificate2Service.mbpzPdf(xmid);
    }

    /**
     * 注销/作废 电子证照
     * <p>
     * 电子证照 2.0 版本
     * </p>
     *
     * @param xmid xmid
     * @param bgyy
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("电子证照文件下载")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bgyy", value = "bgyy", dataType = "String", paramType = "query")})
    public List<DzzzResponseData> cancelZzqz(String xmid, Integer bgyy) {
        return eCertificate2Service.cancelZzqz(xmid, bgyy);
    }

    /**
     * 注销单个证书的电子签章
     * @param zsid 证书id
     * @param bgyy 变更原因
     * @return 注销结果信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("注销单个证书的电子签章")
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "zsid", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bgyy", value = "bgyy", dataType = "Integer", paramType = "query")})
    public DzzzResponseData cancelBdcZsZzqz(@RequestParam(value = "zsid") String zsid, @RequestParam(value = "bgyy") Integer bgyy) {
        return eCertificate2Service.zzzx(zsid, bgyy);
    }

    /**
     * 常州电子签章 文件下载
     * <p>
     * 电子证照 2.0 版本
     * </p>
     *
     * @param gzlslid
     * @param zsid
     * @param symd
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("电子证照文件下载")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "xmid", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "symd", value = "symd", dataType = "Integer", paramType = "query")})
    public void zzxxxz(String gzlslid, String zsid, Integer symd) {
        eCertificate2Service.zzxxxz(gzlslid, zsid, symd);
    }

    /**
     * 存量电子证照签发
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("存量电子证照签发")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcqzhs", value = "产权证号", dataType = "List", paramType = "body")})
    public Integer clzzQf(@RequestBody List<String> bdcqzhs) {
        return eCertificateClService.qfClDzzz(bdcqzhs);
    }

    /**
     * 存量电子证照 pdf 下载
     *
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("存量电子证照 pdf 下载")
    @ApiImplicitParams({@ApiImplicitParam(name = "zzbsList", value = "证照标识", dataType = "List", paramType = "body")})
    public Integer clzzPdf(@RequestBody List<String> zzbsList) {
        return eCertificateClService.xzClDzzz(zzbsList);
    }

    /**
     * 批量电子证照注销
     *
     * @param count
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("作废当前项目的电子证照信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "count", value = "需要注销数量", dataType = "String", paramType = "String")})
    @GetMapping(value = "/realestate-certificate/rest/v1.0/eCertificate/batch/cancel/ls")
    public void batchCancelXmECertificate(@RequestParam(value = "count") String count) {
        eCertificateService.batchCancelXmECertificate(count);
    }

    /**
     * @param ggid 公告信息ID
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 配置生成
     * @date : 2022/3/14 9:24
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("公告推送常州电子签章")
    @ApiImplicitParams({@ApiImplicitParam(name = "公告信息ID", value = "ggid", dataType = "String", paramType = "query")})
    public void tsggqz(@RequestParam(name = "ggid") String ggid) {
        eCertificate2Service.changZhouGgtsqz(ggid);
    }

    /**
     * @param dzqzCsDTO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 组织生成电子签章的一些必要信息
     * @date : 2022/8/24 13:47
     */
    @Override
    public EZsDTO generateDzqzxx(@RequestBody DzqzCsDTO dzqzCsDTO) {
        return eCertificate2Service.getDzqzCs(dzqzCsDTO);
    }

    /**
     * 【连云港市】根据身份证号获取电子证照信息接口
     *
     * @param certownerno
     * @return
     */
    @Override
    public CertOwnerDzzzDTO queryDzzzBySfzh(@RequestParam(value = "certownerno",required = true) String certownerno) {
        CertOwnerDzzzDTO certOwnerDzzzDTO = new CertOwnerDzzzDTO();
        CertOwnerDzzzDTO.Status status = new CertOwnerDzzzDTO.Status();
        certOwnerDzzzDTO.setStatus(status);
        if (StringUtils.isBlank(certownerno)){
            status.setText("必填参数缺失");
            status.setCode(-1);
            return certOwnerDzzzDTO;
        }
        //查询个人所有的证书
        CertOwnerDzzzDTO.Custom qlrDzzz = eCertificateService.getQlrDzzz(certownerno);
        if (Objects.nonNull(qlrDzzz)){
            certOwnerDzzzDTO.setCustom(qlrDzzz);
        }
        return certOwnerDzzzDTO;
    }

    /**
     * 查询电子证照信息
     *
     * @param
     * @return
     */
    @Override
    public DzzzResponseModel queryDzzzxx(@RequestBody DzzzcxxxRequestDTO dzzzcxxxRequestDTO) {
        DzzzResponseModel result = new DzzzResponseModel();
        ResponseHead head = new ResponseHead();
        if (dzzzcxxxRequestDTO == null || StringUtils.isBlank(dzzzcxxxRequestDTO.getQlrzjh())){
            head.setStatus("1");
            head.setMessage("必填参数缺失");
            result.setHead(head);
            return result;
        }
        return eCertificateService.getDzzzxx(dzzzcxxxRequestDTO);
    }
}
