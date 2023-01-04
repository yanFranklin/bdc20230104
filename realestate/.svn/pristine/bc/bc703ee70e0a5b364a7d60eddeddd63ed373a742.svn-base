package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.gtc.sso.domain.dto.OrganizationDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.realestate.certificate.core.dto.BdcCancelECertificateDTO;
import cn.gtmap.realestate.certificate.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.certificate.core.service.BdcQlrService;
import cn.gtmap.realestate.certificate.core.service.BdcXmService;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.core.service.ECertificateModelServiceThread;
import cn.gtmap.realestate.certificate.core.thread.AutoCancelECertificateHandlerThread;
import cn.gtmap.realestate.certificate.service.ECertificateService;
import cn.gtmap.realestate.certificate.service.SyncDzzzService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.ECertificateParamUtils;
import cn.gtmap.realestate.certificate.util.ECertificateUtils;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.electronic.BdcDzzzZzxxDO;
import cn.gtmap.realestate.common.core.dto.building.ZdtResponseDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcEcertificateConfigDTO;
import cn.gtmap.realestate.common.core.dto.certificate.DzzzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.*;
import cn.gtmap.realestate.common.core.dto.exchange.dzzz.DzzzcxxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.dzzz.request.HefeiDzzzRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.dzzz.response.HefeiDzzzResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.ResponseHead;
import cn.gtmap.realestate.common.core.dto.register.BdcXmZsDTO;
import cn.gtmap.realestate.common.core.enums.ECertificateErrorMsgEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.ZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzGxFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcZzxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.thread.ThreadEngine;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/12/17
 * @description 电子证照服务实现类
 */
@Service
public class ECertificateServiceImpl implements ECertificateService {
    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ECertificateServiceImpl.class);
    /**
     * 电子证照的请求地址
     */
    @Value("${eCertificate.urlPath:}")
    private String eCerticifatePath;
    /**
     * 电子证照版本
     */
    @Value("${eCertificate.version:}")
    private String version;

    /**
     * 获取token的应用名称
     */
    @Value("${eCertificate.tokenYymc:}")
    private String tokenYymc;

    /**
     * 公钥
     */
    @Value("${eCertificate.publicKey:}")
    private String publicKey;

    /**
     * 私钥
     */
    @Value("${eCertificate.privateKey:}")
    private String privateKey;

    /**
     * 港澳台证件种类代码
     */
    @Value("${eCertificate.H-M-T-zjzldm:}")
    private Integer zjzldm_HMT;

    /**
     * 上传storage的根方式.默认为eCertificateId
     */
    @Value("${eCertificate.uploadStorageRoot:}")
    private String uploadStorageRoot;

    @Value("#{${eCertificate.contentCode.zs}}")
    private Map<String, String> eCertificate_zs;

    /**
     * 证明印章code
     */
    @Value("#{${eCertificate.contentCode.zm}}")
    private Map<String, String> eCertificate_zm;

    /**
     * 电子证照格式
     */
    @Value("${eCertificate.format:png}")
    private String eCertificate_format;

    /**
     * 审核登簿UI服务地址
     */
    @Value("${url.register-ui:}")
    protected String registerUiUrl;

    /**
     * 电子证照地址，有些地区不能直接访问返回的地址
     */
    @Value("${url.zzdz:}")
    protected String zzdz;
    /**
     * 证书归档服务地址
     */
    @Value("${url.certificate:}")
    protected String certificateUrl;


    /**
     * 同步电子证照材料信息
     */
    @Value("${sync.dzzzclxx:false}")
    private Boolean syncDzzzClxx;

    /**
     * 安徽地区请求生成电子证照是否需要传宗地图信息，默认需要
     */
    @Value("${eCertificate.sfszzdt:true}")
    private boolean sfszzdt;

    /**
     * 登簿制证验证验证lzfs，是否自动生成电子证照
     */
    @Value("${eCertificate.yzlzfs:true}")
    private boolean yzlzfs;

    @Autowired
    private BdcZsService bdcZsService;
    @Autowired
    private BdcXmService bdcXmService;
    @Autowired
    private BdcQlrService bdcQlrService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcZdFeignService bdcZdFeignService;
    @Autowired
    private StorageClientMatcher storageClient;
    @Autowired
    private BdcDzzzFeignService bdcDzzzFeignService;
    @Autowired
    private BdcDzzzGxFeignService bdcDzzzGxFeignService;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    ZdFeignService zdFeignService;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    ProcessTaskClient processTaskClient;
    @Autowired
    private ECertificateUtils eCertificateUtils;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private ECertificateModelServiceThread eCertificateModelServiceThread;
    @Autowired
    private ThreadEngine threadEngine;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private ECertificateParamUtils eCertificateParamUtils;
    @Autowired
    private SyncDzzzService syncDzzzService;
    @Autowired
    BdcZzxxFeignService bdcZzxxFeignService;
    @Autowired
    ZipUtils zipUtils;

    /**
     * 电子证照压缩包临时路径
     */
    @Value("${zip.path:}")
    private String zipPath;


    /**
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取电子证照的访问token值
     */
    @Override
    public String getAccessToken() {
        if (StringUtils.isBlank(tokenYymc)) {
            throw new MissingArgumentException("缺失获取token的应用名称配置！缺失配置字段：" + tokenYymc);
        }
        Map yymcMap = new HashMap(1);
        yymcMap.put("yymc", tokenYymc);

        Map<String, Object> dataMap = new HashMap(1);
        dataMap.put("data", yymcMap);

        DzzzResponseModel<Map> dzzzResponseModel = bdcDzzzGxFeignService.creatToken(JSONObject.toJSONString(dataMap));
        if (null != dzzzResponseModel) {
            dataMap = dzzzResponseModel.getData();
            return MapUtils.getString(dataMap, "token");
        }
        return "";
    }

    /**
     * @param gzlslid         工作流实例ID
     * @param currentUserName 当前操作用户
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 创建电子证照
     */
    @Override
    public void createECertificate(String gzlslid, String currentUserName) {
        LOGGER.warn("生成电子证照，操作用户{}", currentUserName);
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("缺失查询参数processInsId");
        }
        List<BdcXmDO> bdcXmDOList = bdcXmService.listBdcXmByProInsID(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                this.createXmECertificate(bdcXmDO, currentUserName);
            }
        }
    }

    /**
     * @param bdcXmDO         项目信息
     * @param currentUserName 当前操作用户
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成当前项目的电子证照信息
     */
    @Override
    public List<DzzzResponseModel<Map>> createXmECertificate(BdcXmDO bdcXmDO, String currentUserName) {
        if (null != bdcXmDO && CommonConstantUtils.QSZT_VALID.equals(bdcXmDO.getQszt())) {
            try {
                // 新生成的证书信息
                List<BdcZsDO> bdcZsDOList = bdcZsService.queryBdcZsByXmid(bdcXmDO.getXmid());
                return this.requestCreateECertificate(bdcZsDOList, bdcXmDO, currentUserName);
            } catch (Exception e) {
                LOGGER.error("项目{}电子证照生成失败！", bdcXmDO.getXmid(), e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * 下载电子证照并上传到登记 <br/>
     * 处理生成证照时下载失败的数据，未生成电子证照的数据不执行
     *
     * @param bdcXmDO         项目ID
     * @param currentUserName 用户名
     * @return 证照信息
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @Override
    public void reUploadECertificate(BdcXmDO bdcXmDO, String currentUserName) {
        if (null == bdcXmDO) {
            throw new NullPointerException("下载电子证照传入参数为空");
        }
        // 新生成的证书信息
        List<BdcZsDO> bdcZsDOList = bdcZsService.queryBdcZsByXmid(bdcXmDO.getXmid());
        for (BdcZsDO bdcZsDO : bdcZsDOList) {
            // 如果数据库中不存在电子证照标识，则不执行上传操作
            if (StringUtils.isBlank(bdcZsDO.getZzbs())) {
                continue;
            }
            StorageDto storageDto = uploadECertificate(bdcXmDO.getGzlslid(), bdcZsDO.getZzbs(),
                    bdcZsDO.getBdcqzh() + CommonConstantUtils.WJHZ_PDF, currentUserName);
            if (storageDto == null) {
                throw new AppException("下载电子证照信息失败 gzlslid:" + bdcXmDO.getGzlslid());
            }
            LOGGER.warn("证书信息{}补偿生成证照时storageId{}", bdcZsDO.getZsid(), storageDto.getId());
            bdcZsDO.setStorageid(storageDto.getId());
            bdcZsService.updateBdcZs(bdcZsDO);
        }
    }

    /**
     * @param bdcZsDOList     当前项目的证书信息
     * @param bdcXmDO         项目信息
     * @param currentUserName 当前用户
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 电子证照生成请求服务
     */
    @Override
    public List<DzzzResponseModel<Map>> requestCreateECertificate(List<BdcZsDO> bdcZsDOList, BdcXmDO bdcXmDO, String currentUserName) {
        List<DzzzResponseModel<Map>> dzzzResponseModelList = new ArrayList();

        for (BdcZsDO bdcZsDO : bdcZsDOList) {
            // 如果数据库中已存在电子证照标识，则不生成
            if (StringUtils.isNotBlank(bdcZsDO.getZzbs())) {
                // 证照已经生成但没有下载成功，重新下载
                //判断是否作废 如果是现势状态且storageid 是空的就重新下载上传，否则就重新生成电子证照
                String zzzt = "";
                String zzbgyy = "";
                List<DzzzResponseModel<List<Map>>> dzzzZtResponseModels = checkZzzt(Collections.singletonList(bdcZsDO.getZsid()));
                if (CollectionUtils.isNotEmpty(dzzzZtResponseModels)) {
                    DzzzResponseModel<List<Map>> dzzzZtResponse = dzzzZtResponseModels.get(0);
                    LOGGER.warn("当前证书id{},请求查询电子证照状态{}", bdcZsDO.getZsid(), JSON.toJSONString(dzzzZtResponse));
                    if (StringUtils.equals(Constants.STATUS_SUCCESS, dzzzZtResponse.getHead().getStatus())) {
                        //请求响应成功
                        if (CollectionUtils.isNotEmpty(dzzzZtResponse.getData())) {
                            Map dataMap = dzzzZtResponse.getData().get(0);
                            if (MapUtils.isNotEmpty(dataMap) && dataMap.containsKey("zzzt")) {
                                zzzt = MapUtils.getString(dataMap, "zzzt");
                            }
                            if ("2".equals(zzzt) && dataMap.containsKey("zzbgyy")) {
                                zzbgyy = MapUtils.getString(dataMap, "zzbgyy", "");
                            }
                        }
                    } else {
                        //请求响应失败跳过循环
                        continue;
                    }
                } else {
                    //未查询到状态跳出循环
                    continue;
                }
                if ("1".equals(zzzt)) {
                    //证照状态正常，文件中心无值，重新下载；   证照状态正常，文件中心有值，说明一切正常，啥都不要操作！！
                    if (StringUtils.isBlank(bdcZsDO.getStorageid())) {
                        reUploadECertificate(bdcXmDO, currentUserName, bdcZsDO);
                        continue;
                    } else {
                        continue;
                    }
                }
               /* if (StringUtils.isBlank(bdcZsDO.getStorageid()) && "1".equals(zzzt)) {
                    reUploadECertificate(bdcXmDO, currentUserName, bdcZsDO);
                    continue;
                }*/
                //如果是正常注销(zzzt=2 zzbgyy=1)，跳过此次循环,如果是注销，zzbgyy=2 是作废的，就继续执行，重新生成上传
                if ("2".equals(zzzt) && "1".equals(zzbgyy)) {
                    continue;
                }
            }

            if (StringUtils.isNotBlank(bdcZsDO.getStorageid())) {
                LOGGER.info("删除证书表里storageid对应的PDF,证书id：" + bdcZsDO.getZsid());
                LOGGER.info("删除的storageid为：" + bdcZsDO.getStorageid());
                storageClient.deleteStorages(Collections.singletonList(bdcZsDO.getStorageid()));
            }
            EZsDTO eZsDTO = eCertificateUtils.generateEZsDTO(bdcZsDO, bdcXmDO, Constants.DZZZ);
            LinkedHashMap paramData = eCertificateUtils.getParamData(eZsDTO, bdcXmDO.getQxdm());

            // 新版-电子证照 增加批量抵押的抵押物信息
            if (eCertificateParamUtils.isXbPldy(bdcZsDO, bdcXmDO, Constants.DZZZ)) {
                List<EZzfsxxDTO> zzfsxx = eCertificateParamUtils.getZzfsxx(bdcXmDO, bdcZsDO, Constants.DZZZ);
                if(CollectionUtils.isNotEmpty(zzfsxx) && CollectionUtils.size(zzfsxx) > 1) {
                    // 根据实际查询出得单元判断是否需要传附属信息，避免部分外联项目在isXbPldy判断是批量抵押但是实际查询出来抵押单元只有一个后也添加了附属信息导致生成证照失败
                    paramData.put(Constants.KEY_ZZFSXX, zzfsxx);
                }
            }
            paramData.put(Constants.ZSID, bdcZsDO.getZsid());
            String token = eCertificateParamUtils.getTokenYymc(bdcXmDO.getQxdm(), Constants.DZZZ);
            LOGGER.info("调用电子证照参数：{}, token名称：{}", JSON.toJSONString(paramData), token);
            DzzzResponseModel<Map> dzzzResponseModel = bdcDzzzFeignService.zzpdf2(token, JSONObject.toJSONString(paramData));
            if (null != dzzzResponseModel) {
                dzzzResponseModelList.add(dzzzResponseModel);

                ResponseHead head = dzzzResponseModel.getHead();

                if (StringUtils.equals(Constants.STATUS_SUCCESS, head.getStatus()) || StringUtils.equals(ECertificateErrorMsgEnum.MSG6.getCode(), head.getMessage())
                        || StringUtils.equals(ECertificateErrorMsgEnum.MSG7.getCode(), head.getMessage())) {
                    // 已生成过电子证照，重新保存，在登记更新生成结果，默认为生成成功
                    dzzzResponseModel.getHead().setStatus(Constants.STATUS_SUCCESS);

                    if (dzzzResponseModel.getData() instanceof java.util.Map) {
                        Map data = dzzzResponseModel.getData();

                        // 保存证照标识到证书信息中
                        String zzbs = MapUtils.getString(data, Constants.KEY_ZZBS);
                        bdcZsDO.setZzbs(zzbs);
                        // 下载电子证照更新证书表
                        reUploadECertificate(bdcXmDO, currentUserName, bdcZsDO);
                    } else {
                        LOGGER.error("获取电子证照返回信息错误，返回值类型应为：java.utils.Map，返回值为:{}", dzzzResponseModel.toString());
                    }
                } else {
                    LOGGER.warn("此次请求证书【{}】未生成新的电子证照！,请求结果{}", bdcZsDO.getZsid(), dzzzResponseModel);
                }
            }
        }
        return dzzzResponseModelList;
    }

    /**
     * 重新下载证书并上传到文件中心，更新证书中 storageId
     *
     * @param bdcXmDO         项目信息
     * @param currentUserName 用户名
     * @param bdcZsDO         证书
     */
    private void reUploadECertificate(BdcXmDO bdcXmDO, String currentUserName, BdcZsDO bdcZsDO) {
        // 下载电子证照更新证书表
        StorageDto storageDto = uploadECertificate(bdcXmDO.getGzlslid(), bdcZsDO.getZzbs(),
                bdcZsDO.getBdcqzh() + CommonConstantUtils.WJHZ_PDF, currentUserName);
        if (null != storageDto) {
            bdcZsDO.setStorageid(storageDto.getId());
        }
        bdcZsService.updateBdcZs(bdcZsDO);
        LOGGER.warn("证书{}生成电子证照，storageid{}", bdcZsDO.getZsid(), Objects.nonNull(storageDto) ? storageDto.getId() : "storage信息为空");
    }

    /**
     * @param gzlslid         工作流实例ID
     * @param currentUserName 当前用户账号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 注销原项目电子证照
     */
    @Override
    public void cancelYxmECertificate(String gzlslid, String currentUserName) {
        LOGGER.warn("注销电子证照，操作用户{}", currentUserName);
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid");
        }

        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setGzlslid(gzlslid);
        bdcZsQO.setZxyql(CommonConstantUtils.SF_S_DM);
        List<BdcZsDO> bdcZsDOList = bdcZsService.listYxmZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            LOGGER.warn("当前流程gzlslid:{}未查询到已注销的原项目信息！", gzlslid);
            return;
        }

        this.requestCancelECertificate(bdcZsDOList, Constants.ZZBGYY_BDC_ZX, null, currentUserName);
    }

    private DzzzResponseModel cancelEcertificateByZzbsWithOutUpdateStorage(BdcCancelECertificateDTO bdcCancelECertificateDTO, String zzbgyy) {
        Map paramMap = new HashMap();
        paramMap.put(Constants.KEY_ZZBS, bdcCancelECertificateDTO.getZzbs());
        paramMap.put(Constants.KEY_ZZBGYY, zzbgyy);
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcCancelECertificateDTO.getXmid());
        String token = eCertificateParamUtils.getTokenYymc(bdcXmDO.getQxdm(), Constants.DZZZ);
        LOGGER.warn("证书{}注销电子证照获取参数{}，token名称：{}！", bdcCancelECertificateDTO.getZsid(), JSON.toJSONString(paramMap), token);
        DzzzResponseModel dzzzResponseModel = bdcDzzzFeignService.zzzt2(token, JSONObject.toJSONString(paramMap));
        if (null != dzzzResponseModel) {
            ResponseHead head = dzzzResponseModel.getHead();
            if (StringUtils.equals(Constants.STATUS_SUCCESS, head.getStatus())) {
                LOGGER.warn("证书{}的电子证照{}注销成功！", bdcCancelECertificateDTO.getZsid(), bdcCancelECertificateDTO.getZzbs());
                BdcZsDO bdcZsDO = new BdcZsDO();
                bdcZsDO.setZzbs("");
                bdcZsDO.setZsid(bdcCancelECertificateDTO.getZsid());
                bdcZsService.updateBdcZs(bdcZsDO);
                LOGGER.warn("证书：{} 更新注销电子证照附件 id：{}", bdcZsDO.getZsid(), bdcZsDO.getStorageid());
            } else {
                LOGGER.error("证书【{}】电子证照标识【{}】注销失败！请求结果{}", bdcCancelECertificateDTO.getZsid(), bdcCancelECertificateDTO.getZzbs(), dzzzResponseModel);
            }
        }
        return dzzzResponseModel;
    }

    /**
     * @param bdcZsDOList 要注销的证书信息
     * @param zzbgyy
     * @param gzlslid     当前证书所对应的gzlslid，值为空时根据zsid进行查询
     *                    gzlslid 不为空的情况肯定是当前的bdcZsDOList 在同一个流程中，否则gzlslid请传null，重现依据zsid查询
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 执行注销电子证照
     */
    private List<DzzzResponseModel> requestCancelECertificate(List<BdcZsDO> bdcZsDOList, String zzbgyy, String gzlslid, String currentUserName) {
        List<DzzzResponseModel> dzzzResponseModelList = new ArrayList();

        for (BdcZsDO bdcZsDO : bdcZsDOList) {
            String zzbs = bdcZsDO.getZzbs();
            if (StringUtils.isBlank(zzbs)) {
                LOGGER.warn("证书{}没有证照标识数据！", bdcZsDO.getZsid());
                continue;
            }
            Map paramMap = new HashMap();
            paramMap.put(Constants.KEY_ZZBS, zzbs);
            paramMap.put(Constants.KEY_ZZBGYY, zzbgyy);
            String token = eCertificateParamUtils.getTokenYymc(bdcZsDO.getQxdm(), Constants.DZZZ);
            LOGGER.warn("证书{}注销电子证照获取参数{}，token名称：{}！", bdcZsDO.getZsid(), JSON.toJSONString(paramMap), token);
            DzzzResponseModel dzzzResponseModel = bdcDzzzFeignService.zzzt2(token, JSONObject.toJSONString(paramMap));
            if (null != dzzzResponseModel) {
                dzzzResponseModelList.add(dzzzResponseModel);

                ResponseHead head = dzzzResponseModel.getHead();
                if (StringUtils.equals(Constants.STATUS_SUCCESS, head.getStatus())) {
                    LOGGER.warn("证书{}的电子证照{}注销成功！", bdcZsDO.getZsid(), zzbs);
                    if (StringUtils.isBlank(gzlslid)) {
                        // 注销证照，需要查询证书对应项目的gzlslid
                        List<BdcXmDO> bdcXmDOList = bdcZsService.queryZsXmByZsid(bdcZsDO.getZsid());
                        if (CollectionUtils.isEmpty(bdcXmDOList) || StringUtils.isBlank(bdcXmDOList.get(0).getGzlslid())) {
                            LOGGER.error("需要注销的证书未查询到对应的项目信息！");
                        } else {
                            gzlslid = bdcXmDOList.get(0).getGzlslid();
                        }
                    }
                    StorageDto storageDto = this.uploadECertificate(gzlslid, zzbs,
                            bdcZsDO.getBdcqzh() + CommonConstantUtils.WJHZ_PDF, currentUserName);
                    if (null != storageDto) {
                        bdcZsDO.setStorageid(storageDto.getId());
                        bdcZsDO.setZzbs("");
                    }
                    bdcZsService.updateBdcZs(bdcZsDO);
                    LOGGER.warn("证书：{} 更新注销电子证照附件 id：{}", bdcZsDO.getZsid(), bdcZsDO.getStorageid());
                } else {
                    LOGGER.error("证书【{}】电子证照标识【{}】注销失败！请求结果{}", bdcZsDO.getZsid(), zzbs, dzzzResponseModel);
                }
            }
        }
        return dzzzResponseModelList;
    }

    /**
     * @param bdcXmDO         项目信息
     * @param currentUserName 当前用户账号
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 注销原项目电子证照
     */
    @Override
    public void cancelYxmECertificate(BdcXmDO bdcXmDO, String currentUserName) {
        if (null == bdcXmDO || StringUtils.isBlank(bdcXmDO.getXmid())) {
            return;
        }

        try {
            // 需要注销的证书信息
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setXmid(bdcXmDO.getXmid());
            bdcZsQO.setZxyql(CommonConstantUtils.SF_S_DM);
            List<BdcZsDO> yxmZsDOList = bdcZsService.listYxmZs(bdcZsQO);

            this.requestCancelECertificate(yxmZsDOList, Constants.ZZBGYY_BDC_ZX, null, currentUserName);
        } catch (Exception e) {
            LOGGER.error("项目{}注销原项目电子证照失败！", bdcXmDO.getXmid(), e);
        }
    }

    /**
     * @param bdcXmDO         当前项目信息
     * @param zzbgyy
     * @param currentUserName 当前请求的用户信息
     * @return List<DzzzResponseModel < Map>> 电子证照请求结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 注销当前项目的电子证照信息
     */
    @Override
    public List<DzzzResponseModel> cancelXmECertificate(BdcXmDO bdcXmDO, String zzbgyy, String currentUserName) {
        try {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setXmid(bdcXmDO.getXmid());
            List<BdcZsDO> bdcZsDOList = bdcZsService.listBdcZs(bdcZsQO);
            return this.requestCancelECertificate(bdcZsDOList, zzbgyy, bdcXmDO.getGzlslid(), currentUserName);
        } catch (Exception e) {
            LOGGER.error("项目{}注销电子证照失败！", bdcXmDO.getXmid(), e);
        }
        return Collections.emptyList();
    }

    /**
     * @param zsidList 证书ID信息
     * @return List<DzzzResponseModel < Map>> 查询结果信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询证书的电子证照状态
     */
    @Override
    public List<DzzzResponseModel<List<Map>>> checkZzzt(List<String> zsidList) {
        List<DzzzResponseModel<List<Map>>> dzzzResponseModelList = new ArrayList();
        if (CollectionUtils.isEmpty(zsidList)) {
            throw new MissingArgumentException("缺失参数zsidList信息！");
        }
        for (String zsid : zsidList) {
            // 判断电子证照的状态
            BdcZsDO bdcZsDO = bdcZsService.queryBdcZs(zsid);
            if (null != bdcZsDO) {
                Map paramMap = new HashMap();
                if (CommonConstantUtils.ZSLX_ZS.equals(bdcZsDO.getZslx())) {
                    paramMap.put("zzlxdm", Constants.ZZLXDM_ZS);
                }
                if (CommonConstantUtils.ZSLX_ZM.equals(bdcZsDO.getZslx())) {
                    paramMap.put("zzlxdm", Constants.ZZLXDM_ZM);
                }
                paramMap.put("bdcqzh", bdcZsDO.getBdcqzh());

                Map dataMap = new HashMap();
                dataMap.put(Constants.KEY_DATA, paramMap);
                String paramJson = JSONObject.toJSONString(dataMap);
                String token = eCertificateParamUtils.getTokenYymc(bdcZsDO.getQxdm(), Constants.DZZZ);
                LOGGER.warn("查询证书{}电子证照状态参数{}，token名称：{}！", bdcZsDO.getZsid(), paramJson, token);
                //查询电子证照状态
                DzzzResponseModel<List<Map>> dzzzResponseModel = bdcDzzzFeignService.zzcx2(token, paramJson);
                if (null != dzzzResponseModel) {
                    // 未查询到数据会返回 {"head":{"message":"3","status":"1"},"data":""}，如果Data直接空字符串会在接口返回时序列化错误
                    if (StringUtils.isBlank(String.valueOf(dzzzResponseModel.getData()))) {
                        dzzzResponseModel.setData(null);
                    }

                    dzzzResponseModelList.add(dzzzResponseModel);
                }
                LOGGER.warn("证书信息id{},查询到的电子证照状态{}", bdcZsDO.getZsid(), JSON.toJSONString(dzzzResponseModel));
            }

        }
        return dzzzResponseModelList;
    }

    /**
     * @param gzlslid         证书ID
     * @param zzbs            证照标识
     * @param fileName
     * @param currentUserName
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 上传电子证照到登记
     */
    @Override
    public StorageDto uploadECertificate(String gzlslid, String zzbs, String fileName, String currentUserName) {
        if (StringUtils.isAnyBlank(gzlslid, zzbs)) {
            throw new MissingArgumentException("gzlslid和zzbs不能为空！");
        }
        String eContent = this.getECertificateContent(zzbs);
        if (StringUtils.isBlank(eContent)) {
            return null;
        }
        return this.uploadECertificateToWjzx(gzlslid, zzbs, fileName, currentUserName, eContent);
    }

    /**
     * 获取电子证照附件
     *
     * @param gzlslid
     * @param bdcqzh
     * @return
     */
    @Override
    public StorageDto getECertificateFile(String gzlslid, String bdcqzh) {
        if (StringUtils.isAnyBlank(gzlslid, bdcqzh)) {
            throw new MissingArgumentException("gzlslid和bdcqzh不能为空！");
        }
        if (!StringUtils.equals(Constants.WJZX_CLIENTID_ECERTIFICATE, uploadStorageRoot) && !StringUtils.equals(Constants.WJZX_CLIENTID, uploadStorageRoot)) {
            throw new MissingArgumentException("配置错误！期望uploadStorageRoot为" + Constants.WJZX_CLIENTID_ECERTIFICATE + "或" + Constants.WJZX_CLIENTID + "，实际值为" + uploadStorageRoot);
        }

        //获取
        List<StorageDto> storageDtoList = storageClient.listStoragesByName(uploadStorageRoot, gzlslid, null, bdcqzh, null, 3);
        if (CollectionUtils.isNotEmpty(storageDtoList)) {
            return storageDtoList.get(0);
        }
        return null;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 测试方法，生成本地文件
     */
    private void decodeBase64StrToFile(String str, String zzbs) {
        String xmlStr = "C:\\temp\\" + zzbs + CommonConstantUtils.WJHZ_PDF;
        boolean result = Base64Utils.decodeBase64StrToFile(str, xmlStr);
        LOGGER.warn("文件保存结果：{}。测试pdf保存路径：{}", result, xmlStr);
    }

    /**
     * @param currentUserName 当前用户名
     * @param fileByte        文件字节
     * @param fileName        文件名称
     * @return MultipartDto 大云上传参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 组织大云上传参数
     */
    private MultipartDto getUploadParamDto(String currentUserName, StorageDto storageDto, byte[] fileByte, String fileName) {
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(Constants.WJZX_CLIENTID_ECERTIFICATE);
        if (fileByte != null) {
            multipartDto.setData(fileByte);
            multipartDto.setOwner(currentUserName);
            multipartDto.setContentType("application/pdf");
            multipartDto.setSize(fileByte.length);
            multipartDto.setOriginalFilename(fileName);
            multipartDto.setName(storageDto.getName());
        }
        return multipartDto;
    }

    /**
     * @param zzbs 证照标识
     * @return Base64字符串值
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 下载电子证照，返回Base64字符串值
     */
    @Override
    public String getECertificateContent(String zzbs) {
        if (StringUtils.isBlank(zzbs)) {
            throw new MissingArgumentException("缺失查询参数zzbs!");
        }
        // 获取电子证照token
        // String token = this.getAccessToken();

        Map paramMap = new HashMap();
        paramMap.put(Constants.KEY_ZZBS, zzbs);

        Map dataMap = new HashMap();
        dataMap.put(Constants.KEY_DATA, paramMap);

        Example example = new Example(BdcZsDO.class);
        example.createCriteria().andEqualTo("zzbs", zzbs);
        List<BdcZsDO> zsDOList = entityMapper.selectByExampleNotNull(example);
        String qxdm = "";
        if (CollectionUtils.isNotEmpty(zsDOList) && null != zsDOList.get(0)) {
            qxdm = zsDOList.get(0).getQxdm();
        }
        String token = eCertificateParamUtils.getTokenYymc(qxdm, Constants.DZZZ);
        LOGGER.warn("下载证书zzbs对应电子证照:{}，token名称：{}！", zzbs, token);

        DzzzResponseModel dzzzResponseModel = bdcDzzzFeignService.zzxxxz2(token, JSONObject.toJSONString(dataMap));
        if (null != dzzzResponseModel) {
            ResponseHead head = dzzzResponseModel.getHead();
            Object data = dzzzResponseModel.getData();
            if (StringUtils.equals(Constants.STATUS_SUCCESS, head.getStatus())) {
                if (data instanceof Map) {
                    return MapUtils.getString((Map) data, Constants.KEY_CONTENT);
                }
                if (data instanceof String) {
                    JSONObject jsonObject = JSON.parseObject((String) data);
                    return jsonObject.get(Constants.KEY_CONTENT).toString();
                }
            }
        }
        LOGGER.error("电子证照{}下载失败！", zzbs);
        return null;
    }

    /**
     * 获取电子证照相关配置
     *
     * @return BdcEcertificateConfigDTO 电子证照配置实体
     * @Date 2020/2/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public BdcEcertificateConfigDTO queryEcertificateConfig() {
        BdcEcertificateConfigDTO bdcEcertificateConfigDTO = new BdcEcertificateConfigDTO();
        bdcEcertificateConfigDTO.seteCerticifatePath(this.eCerticifatePath);
        bdcEcertificateConfigDTO.setPrivateKey(this.privateKey);
        bdcEcertificateConfigDTO.setPublicKey(this.publicKey);
        bdcEcertificateConfigDTO.setTokenYymc(this.tokenYymc);
        bdcEcertificateConfigDTO.setVersion(this.version);
        bdcEcertificateConfigDTO.setZjzldm_HMT(this.zjzldm_HMT);
        bdcEcertificateConfigDTO.setUploadStorageRoot(this.uploadStorageRoot);
        return bdcEcertificateConfigDTO;
    }

    /**
     * 创建电子证照 合肥
     *
     * @param gzlslid
     * @param zsid
     * @param currentUserName
     * @return 证号信息
     */
    @Override
    public Object createHefeiDzzz(String gzlslid, String zsid, String currentUserName, String taskName) {
        LOGGER.info("安徽电子证照入参，zsid:{},gzlslid:{},username:{},taskName:{}", zsid, gzlslid, currentUserName, taskName);
        /**
         * 当zsid有值时，不管gzlslid是否有值，只处理当前证书的电子证照
         * 当zsid没有值，处理当前流程下的所有证书，生成电子证照
         * 当且只当zsid有值，调用来源是补偿机制
         */
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(zsid)) {
            throw new MissingArgumentException("缺失参数");
        }
        if (StringUtils.isNotBlank(currentUserName) && StringUtils.isNotBlank(gzlslid)
                && StringUtils.isBlank(zsid)) {
            /**
             * 工作流调用有两种方式
             * 1. ems 窗口 登簿转发证
             * 2. 自主打证机 办结
             * lzfs 和 调用来源要对应
             */
            //登簿制证转发证时，只执行lzfs为窗口和ems的 ，其他跳过，lzfs为电子证照的，要求点击按钮制！！
            //蚌埠用这个配置
            if (!CommonConstantUtils.SYSTEM_VERSION_BB.equals(version)) {
                Integer lzfs = null;
                List<BdcXmFbDO> bdcXmFbDOList = bdcXmService.listBdcXmfbByProInsID(gzlslid);
                if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                    lzfs = bdcXmFbDOList.get(0).getLzfs();
                    LOGGER.info("当前领证方式为：{}", lzfs);
                } else {
                    throw new AppException("未查询到项目附表信息");
                }
                if (null == lzfs) {
                    throw new AppException("未查询到领证方式");
                }
                if (!CommonConstantUtils.JDZDMAP.get("fz").equals(taskName)) {
                    if (!CommonConstantUtils.LZFS_CK.equals(lzfs) && !CommonConstantUtils.LZFS_EMS.equals(lzfs) && yzlzfs) {
                        LOGGER.info("该工作流事件领证方式错误，无法制证,当前lzfs:{}", lzfs);
                        return null;
                    }
                } else {
                    if (!CommonConstantUtils.LZFS_ZZDZ.equals(lzfs)) {
                        LOGGER.info("自主打证的领证方式,发证节点才能制证,当前lzfs:{}", lzfs);
                        return null;
                    }
                }
            }
        }

        BdcZsQO bdcZsQO = new BdcZsQO();
        if (StringUtils.isNotBlank(zsid)) {
            bdcZsQO.setZsid(zsid);
        } else {
            bdcZsQO.setGzlslid(gzlslid);
        }

        List<BdcZsDO> bdcZsDOList = bdcZsService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            throw new AppException("当前流程没有证书信息！");
        }
        String slbh = "";
        List<HefeiDzzzResponseDTO> list = new ArrayList<>();
        for (int i = 0; i < bdcZsDOList.size(); i++) {
            BdcZsDO bdcZsDO = bdcZsDOList.get(i);
            List<BdcXmDO> listXm = bdcZsService.queryZsXmByZsid(bdcZsDO.getZsid());
            if (CollectionUtils.isNotEmpty(listXm)) {
                slbh = listXm.get(0).getSlbh();
            }
            //zzbs不为空，先注销，再生成
            if (StringUtils.isNotBlank(bdcZsDO.getZzbs())) {
                List<BdcZsDO> zsDOS = new ArrayList<>();
                zsDOS.add(bdcZsDO);
                this.zxDzzzByZsids(zsDOS, slbh);
                LOGGER.info("重新生成电子证照，此次注销结束");
            }
            if (CollectionUtils.isNotEmpty(listXm)) {
                for (BdcXmDO zsBdcXmDO : listXm) {
                    LOGGER.info("需要制证的项目为：{}", JSONObject.toJSONString(zsBdcXmDO));
                    if (zsBdcXmDO.getQszt().equals(CommonConstantUtils.QSZT_VALID)) {
                        HefeiDzzzResponseDTO befeiDzzzResponseDTO = this.createHefeiDzzzDTO(bdcZsDO, zsBdcXmDO, currentUserName);
                        list.add(befeiDzzzResponseDTO);
                        // 并案流程中 一个现势产权 一个历史产权，这种情况只需要制一次证书
                        // 一般流程 也制一次证书
                        break;
                    }
                }

            }
        }
        if (Boolean.TRUE.equals(syncDzzzClxx)) {
            //同步电子证照库
            syncDzzzService.syncDzzzClxx(bdcZsDOList, slbh, currentUserName);
        }
        return list;
    }

    /**
     * 压缩上传电子证照
     *
     * @param gzlslid
     * @Date 2022/11/2
     * @author <a href="mailto:wutao2@gtmap.cn">wutao2</a>
     */
    @Override
    public void xzysdzzz(String gzlslid) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("gzlslid不能为空");
        }
        LOGGER.info("下载压缩电子证照pdf开始，gzlslid:{}",gzlslid);
        if(StringUtils.isNotBlank(zipPath)){
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setGzlslid(gzlslid);
            List<BdcZsDO> listBdcZsDO = bdcZsService.listBdcZs(bdcZsQO);
            List<String> zipfile=new ArrayList<>();
            String parentId = "";
            if(CollectionUtils.isNotEmpty(listBdcZsDO)){
                for(BdcZsDO bdcZsDO: listBdcZsDO){
                    if(StringUtils.isNotBlank(bdcZsDO.getStorageid())){
                        zipfile.add(bdcZsDO.getStorageid());
                        StorageDto storageDto = storageClient.findById(bdcZsDO.getStorageid());
                        StorageDto parent = storageDto.getParent();
                        if(null != parent && StringUtils.isNotBlank(parent.getId())){
                            parentId = parent.getId();
                        }
                    }
                }
            }
            //生成一个随机文件夹
            String randomMl = "电子证照压缩包" + gzlslid;
            String randomPath = zipPath + randomMl + "/";
            File fileMl = new File(randomPath);
            boolean mkdir = fileMl.mkdir();
            String zipFilePath = zipPath + randomMl + ".zip";
            String clmc = randomMl + ".zip";
            if (!mkdir) {
                throw new MissingArgumentException("压缩失败！");
            }
            try {
                //获取要压缩的文件，并存到
                zipUtils.createFiles(randomPath, zipfile, true);
                //压缩目标文件夹
                ZipUtils.zip(randomPath, zipFilePath);
                //获取文件并且上传到大云
                StorageDto storageDto = zipUtils.uploadFile(parentId, zipFilePath, gzlslid);
                //删除本地目录和文件
                File fileZip = new File(zipFilePath);
                fileZip.delete();
                ZipUtils.deleteDir(fileMl);
                updateSlclxx(gzlslid,clmc,storageDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateSlclxx(String gzlslid,String clmc,StorageDto storageDto){
        //判断是否有受理收件材料文件夹，不能再新增
        List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, clmc);
        if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
            LOGGER.info("未生成受理文件夹,开始新增,流程实例id{}", gzlslid);
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                bdcSlSjclDO.setDjxl(bdcXmDTOList.get(0).getDjxl());
                bdcSlSjclDO.setGzlslid(gzlslid);
                bdcSlSjclDO.setClmc(clmc);
                bdcSlSjclDO.setWjzxid(storageDto.getId());
                bdcSlSjclDO.setFs(1);
                bdcSlSjclDO.setSjlx(CommonConstantUtils.SJLX_QT);
                bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
            }
        }
    }



    /**
     * 创建电子证照实体dto
     *
     * @param bdcZsDO
     * @param bdcXmDO
     * @param currentUserName
     * @return
     */
    public HefeiDzzzResponseDTO createHefeiDzzzDTO(BdcZsDO bdcZsDO, BdcXmDO bdcXmDO, String currentUserName) {
        OrganizationDto organizationDto = null;
        if (StringUtils.isBlank(currentUserName)) {
            currentUserName = userManagerUtils.getCurrentUserName();
        }
        // 当传空和获取当前用户为空的时候，则不再通过当前用户的方式获取qxdm
        String qxdm = "";
        if (StringUtils.isNotBlank(currentUserName)) {
            UserDto userDto = userManagerUtils.getUserByName(currentUserName);
            if (userDto != null && CollectionUtils.isNotEmpty(userDto.getOrgRecordList())) {
                organizationDto = userDto.getOrgRecordList().get(0);
            }
            if (null != organizationDto) {
                qxdm = organizationDto.getRegionCode();
            } else {
                throw new AppException("未获取到用户组织,用户名:" + currentUserName);
            }
        } else {
            qxdm = bdcZsDO.getQxdm();
        }
        HefeiDzzzRequestDTO hefeiDzzzDTO = new HefeiDzzzRequestDTO();
        Map map = new HashMap();
        // 证书和证明区分逻辑
        if (CommonConstantUtils.ZSLX_ZS.equals(bdcZsDO.getZslx())) {
            HefeiDzzzZsDataDTO zsData = this.generateHefeiZsDzzzDataDTO(bdcZsDO, bdcXmDO);
            hefeiDzzzDTO.setContentCode(eCertificate_zs.get(qxdm));
            String jsonStr = JSONObject.toJSONString(zsData, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
            map = JSONObject.parseObject(jsonStr, HashMap.class);
        } else if (CommonConstantUtils.ZSLX_ZM.equals(bdcZsDO.getZslx())) {
            HefeiDzzzZmDataDTO zmData = this.generateHefeiZmDzzzDataDTO(bdcZsDO);
            hefeiDzzzDTO.setContentCode(eCertificate_zm.get(qxdm));
            String jsonStr = JSONObject.toJSONString(zmData, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
            map = JSONObject.parseObject(jsonStr, HashMap.class);
        }
        if (map.containsKey("czrZjhAndNames")) {
            List list = (List) map.get("czrZjhAndNames");
            if (CollectionUtils.isNotEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    Map<String, String> mapCzs = (Map<String, String>) list.get(i);
                    for (Map.Entry<String, String> entry : mapCzs.entrySet()) {
                        map.put("czr" + (i + 1), entry.getValue());
                        map.put("czrsfzh" + (i + 1), entry.getKey());
                    }
                }
            }
            map.remove("czrZjhAndNames");
        }
        hefeiDzzzDTO.setData(JSON.toJSONString(map));
        hefeiDzzzDTO.setSlbh(bdcXmDO.getSlbh());
        //LOGGER.info("制证接口请求值：{}", hefeiDzzzDTO.toString());

        hefeiDzzzDTO.setFormat(eCertificate_format);
        // 当前时间为有效期开始时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String kssj = sdf.format(date);
        hefeiDzzzDTO.setInfoValidityBegin(kssj);

        String qlrzjh = bdcXmDO.getQlrzjh();
        if (StringUtils.isNotBlank(qlrzjh)) {
            hefeiDzzzDTO.setOwnerId(qlrzjh.split(",")[0]);
        }
        String qlr = bdcXmDO.getQlr();
        if (StringUtils.isNotBlank(qlr)) {
            hefeiDzzzDTO.setOwnerName(qlr.split(",")[0]);
        }
        HefeiDzzzResponseDTO hfeiDzzzResponseDTO = new HefeiDzzzResponseDTO();
        //LOGGER.info("制证接口入参：{}",hefeiDzzzDTO.toString());

        /**************测试代码*************/
//        hfeiDzzzResponseDTO.setFlag("200");
//        hfeiDzzzResponseDTO.setResult("true");
//        HefeiDzzzDataResponseDTO hefeiDzzzDataResponseDTO = new HefeiDzzzDataResponseDTO();
//        hefeiDzzzDataResponseDTO.setCode("1.2.156.3005.2.11100000MB03271699022.340100.000000016.001.9");
//        hefeiDzzzDataResponseDTO.setId("1111");
//        hefeiDzzzDataResponseDTO.setContentCode("2222");
//        hefeiDzzzDataResponseDTO.setUrl("https://avatar.csdnimg.cn/F/F/B/1_dahaii0.jpg");
//        hfeiDzzzResponseDTO.setData(hefeiDzzzDataResponseDTO);
        /**************测试代码*************/

//        LOGGER.info("制证请求参数为：{}",hefeiDzzzDTO.toString());
        Object obj = exchangeInterfaceFeignService.requestInterface("dzzz_zzqz", hefeiDzzzDTO);
        if (null != obj) {
            LOGGER.info("制证接口返回值：{}", JSON.toJSONString(obj));
            hfeiDzzzResponseDTO = JSONObject.parseObject(JSONObject.toJSONString(obj), HefeiDzzzResponseDTO.class);
        }
        if (null == hfeiDzzzResponseDTO.getData()) {
            throw new AppException("制证异常,制证返回值data为空,请联系大数据局！");
        }
        if (!CommonConstantUtils.RESPONSE_SUCCESS.equals(hfeiDzzzResponseDTO.getFlag())) {
            throw new AppException("制证异常,制证返回状态为" + hfeiDzzzResponseDTO.getFlag() + ",请联系大数据局！");
        }

        // 回写电子证照相关信息
        BdcZsDO bdcZsDOTemp = new BdcZsDO();
        bdcZsDOTemp.setZsid(bdcZsDO.getZsid());
        bdcZsDOTemp.setZzbs(hfeiDzzzResponseDTO.getData().getCode());
        bdcZsDOTemp.setZzid(hfeiDzzzResponseDTO.getData().getId());
        // 更新电子证照状态为已经制证
        bdcZsDOTemp.setDzzzzt(CommonConstantUtils.DZZZZT_YZZ);
        //50727 如果配置了地址则直接访问，防止有些地区因为网络问题无法访问返回的地址
        if (StringUtils.isBlank(zzdz)) {
            bdcZsDOTemp.setZzdz(hfeiDzzzResponseDTO.getData().getUrl());
        } else {
            //替换返回结果中的IP地址
            String urlStr = getIps(hfeiDzzzResponseDTO.getData().getUrl());
            LOGGER.info("电子证照地址替换，原地址{},ip{}", hfeiDzzzResponseDTO.getData().getUrl(), urlStr);
            if (StringUtils.isNotBlank(urlStr)) {
                String replaceUrl = hfeiDzzzResponseDTO.getData().getUrl().replace(urlStr, zzdz);
                LOGGER.info("电子证照地址替换，原地址{},ip{},替换后的地址{}",
                        hfeiDzzzResponseDTO.getData().getUrl(),
                        urlStr,
                        replaceUrl);
                bdcZsDOTemp.setZzdz(replaceUrl);
            } else {
                bdcZsDOTemp.setZzdz(hfeiDzzzResponseDTO.getData().getUrl());
            }
        }
        bdcZsDOTemp.setZzmlbm(hfeiDzzzResponseDTO.getData().getContentCode());
        bdcZsDOTemp.setZzqfsj(new Date());
        // 更新完成后，需要上传电子证照到文件中心
        try {
            StorageDto storageDto = this.uploadDzzzToWjzx(bdcXmDO.getGzlslid(), currentUserName, bdcZsDOTemp, bdcXmDO.getDjxl());
            // 保存 storageid
            if (null != storageDto) {
                bdcZsDOTemp.setStorageid(storageDto.getId());
            }
        } catch (Exception e) {
            LOGGER.error("制证后上传文件中心异常", e);
        }
        LOGGER.info("回写电子证照信息：{}", bdcZsDOTemp.toString());
        bdcZsService.updateBdcZs(bdcZsDOTemp);
        return hfeiDzzzResponseDTO;
    }


    /**
     * 组装证明电子证照主体内容实体类
     *
     * @param bdcZsDO
     * @return
     */
    private HefeiDzzzZmDataDTO generateHefeiZmDzzzDataDTO(BdcZsDO bdcZsDO) {
        HefeiDzzzZmDataDTO data = new HefeiDzzzZmDataDTO();

        data.setBdcdyh(bdcZsDO.getBdcdyh());
        if (null == bdcZsDO.getQzysxlh()) {
            data.setBh("");
        } else {
            data.setBh(bdcZsDO.getQzysxlh());
        }

        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setZsid(bdcZsDO.getZsid());
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> listQlr = bdcQlrFeignService.listBdcQlr(bdcQlrQO);

        List<Map<String, String>> listCzrs = new ArrayList<>();
        StringBuffer qlrzjhSb = new StringBuffer();
        StringBuffer qlrSb = new StringBuffer();
        if (CollectionUtils.isNotEmpty(listQlr)) {
            for (BdcQlrDO bdcQlrDO : listQlr) {
                if (!StringUtils.contains(qlrzjhSb.toString(), bdcQlrDO.getZjh())) {
                    Map<String, String> czrZjhAndNamemap = new HashMap<>();
                    czrZjhAndNamemap.put(bdcQlrDO.getZjh(), bdcQlrDO.getQlrmc());
                    if (StringUtils.isNotEmpty(qlrzjhSb.toString())) {
                        qlrzjhSb.append(",");
                    }
                    if (StringUtils.isNotEmpty(qlrSb.toString())) {
                        qlrSb.append(",");
                    }
                    qlrzjhSb.append(bdcQlrDO.getZjh());
                    qlrSb.append(bdcQlrDO.getQlrmc());
                    listCzrs.add(czrZjhAndNamemap);
                }
                data.setCzrZjhAndNames(listCzrs);
                data.setQlr(qlrSb.toString());
            }
        }

        data.setDjh(bdcZsDO.getZhlsh());
        String fj2 = "";
        String qt2 = "";
        if (StringUtils.isNotBlank(bdcZsDO.getFj())) {
            String fj = bdcZsDO.getFj();
            String fj1 = fj.replace("\\n", "\\\n");
            fj2 = fj1.replace("\\r", "\\\r");
        }

        data.setFj(fj2);
        data.setQlhsx(bdcZsDO.getZmqlsx());
        data.setYwr(bdcZsDO.getYwr());

        if (StringUtils.isNotBlank(bdcZsDO.getQlqtzk())) {
            String qt = bdcZsDO.getQlqtzk();
            String qt1 = qt.replace("\\n", "\\\n");
            qt2 = qt1.replace("\\r", "\\\r");
        }

        data.setQt(qt2);
        data.setZl(bdcZsDO.getZl());
        data.setZmh1(bdcZsDO.getSqsjc());
        data.setZmh2(bdcZsDO.getNf());
        data.setZmh3(bdcZsDO.getSzsxqc());
        data.setCxewm("");
        data.setInfoCode(bdcZsDO.getBdcqzh());

        // 年月日
        Calendar calendar = Calendar.getInstance();
        Date szsj = bdcZsDO.getSzsj();
        if (null != szsj) {
            calendar.setTime(szsj);
        } else {
            szsj = new Date();
            calendar.setTime(szsj);
            BdcZsDO bdczsDOTemp = new BdcZsDO();
            bdczsDOTemp.setZsid(bdcZsDO.getZsid());
            bdczsDOTemp.setSzsj(szsj);
            bdcZsService.updateBdcZs(bdczsDOTemp);
        }
        data.setN(calendar.get(Calendar.YEAR) + "");
        data.setY(calendar.get(Calendar.MONTH) + 1 + "");
        data.setR(calendar.get(Calendar.DATE) + "");

        // 二维码转base64
        data.setEwm(convertEwmToBase64(bdcZsDO));
        data.setWhewm("");
        return data;
    }

    /**
     * 组装证书实体类
     *
     * @param bdcZsDO
     * @param bdcXmDO
     * @return
     */
    private HefeiDzzzZsDataDTO generateHefeiZsDzzzDataDTO(BdcZsDO bdcZsDO, BdcXmDO bdcXmDO) {
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        //String xmid = bdcXmDO.getXmid();
        String gzlslid = bdcXmDO.getGzlslid();
        Map map = new HashMap();
        map.put("gzlslid", gzlslid);

        HefeiDzzzZsDataDTO data = new HefeiDzzzZsDataDTO();
        data.setBdcdyh(bdcZsDO.getBdcdyh());
        if (null == bdcZsDO.getQzysxlh()) {
            data.setNo1("");
        } else {
            data.setNo1(bdcZsDO.getQzysxlh());
        }

        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setZsid(bdcZsDO.getZsid());
        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
        List<BdcQlrDO> listQlr = bdcQlrFeignService.listBdcQlr(bdcQlrQO);

        List<Map<String, String>> listCzrs = new ArrayList<>();
        StringBuffer qlrzjhSb = new StringBuffer();
        StringBuffer qlrSb = new StringBuffer();
        if (CollectionUtils.isNotEmpty(listQlr)) {
            for (BdcQlrDO bdcQlrDO : listQlr) {
                if (!StringUtils.contains(qlrzjhSb.toString(), bdcQlrDO.getZjh())) {
                    Map<String, String> czrZjhAndNamemap = new HashMap<>();
                    czrZjhAndNamemap.put(bdcQlrDO.getZjh(), bdcQlrDO.getQlrmc());
                    if (StringUtils.isNotEmpty(qlrzjhSb.toString())) {
                        qlrzjhSb.append(",");
                    }
                    if (StringUtils.isNotEmpty(qlrSb.toString())) {
                        qlrSb.append(",");
                    }
                    qlrzjhSb.append(bdcQlrDO.getZjh());
                    qlrSb.append(bdcQlrDO.getQlrmc());
                    listCzrs.add(czrZjhAndNamemap);
                }
                data.setCzrZjhAndNames(listCzrs);
                data.setQlr(qlrSb.toString());
            }
        }
        if (StringUtils.isNotBlank(bdcZsDO.getFj())) {
            String fj = bdcZsDO.getFj();
            String fj1 = fj.replace("\\n", "\\\n");
            String fj2 = fj1.replace("\\r", "\\\r");
            data.setFj1(fj2);
        }
        data.setInfoCode(bdcZsDO.getBdcqzh());
        data.setCxewm("");
        data.setWhewm("");
        if (StringUtils.isNotBlank(bdcZsDO.getQlqtzk())) {
            String qlqtzk = bdcZsDO.getQlqtzk();
            String qlqtzk1 = qlqtzk.replace("\\n", "\\\n");
            String qlqtzk2 = qlqtzk1.replace("\\r", "\\\r");
            data.setQlqtzk(qlqtzk2);
        }

        data.setZl(bdcZsDO.getZl());
        data.setBh1(bdcZsDO.getSqsjc());
        data.setBh2(bdcZsDO.getNf());
        data.setBh3(bdcZsDO.getSzsxqc());
        data.setBh4(bdcZsDO.getZhlsh());
        data.setGyqk(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getGyfs(), zdMap.get("gyfs")));

        // 宗地图的base64
        if (StringUtils.isNotBlank(bdcZsDO.getBdcdyh()) && sfszzdt) {
            ZdtResponseDTO zdtResponseDTO = zdFeignService.queryZdtByBdcdyh(bdcZsDO.getBdcdyh(), "");
            if (null != zdtResponseDTO) {
                data.setPhoto(zdtResponseDTO.getBase64());
            }
        }

        String qllx = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getQllx(), zdMap.get("qllx"));
        data.setQllx(qllx);
        data.setQlxz(bdcZsDO.getQlxz());
        data.setYt(bdcZsDO.getYt());
        data.setMj(bdcZsDO.getMj());
        data.setSyqx(bdcZsDO.getSyqx());

        // 年月日
        Calendar calendar = Calendar.getInstance();
        Date szsj = bdcZsDO.getSzsj();
        if (null != szsj) {
            calendar.setTime(szsj);
        } else {
            szsj = new Date();
            calendar.setTime(szsj);
            BdcZsDO bdczsDOTemp = new BdcZsDO();
            bdczsDOTemp.setZsid(bdcZsDO.getZsid());
            bdczsDOTemp.setSzsj(szsj);
            bdcZsService.updateBdcZs(bdczsDOTemp);
        }

        data.setN1(calendar.get(Calendar.YEAR) + "");
        data.setY1(calendar.get(Calendar.MONTH) + 1 + "");
        data.setR1(calendar.get(Calendar.DATE) + "");

        // 二维码转base64
        data.setEwm(convertEwmToBase64(bdcZsDO));
        return data;
    }

    /**
     * 二维码转base64
     *
     * @param bdcZsDO
     * @return
     */
    private static String convertEwmToBase64(BdcZsDO bdcZsDO) {
        String ewm = bdcZsDO.getEwmnr();
        if (StringUtils.isNotBlank(ewm)) {
            return QRcodeUtils.getBase64byEwmnr(ewm);
        } else {
            String zsid = bdcZsDO.getZsid();
            return QRcodeUtils.getBase64byEwmnr(zsid);
        }
    }

    /**
     * 上传电子证照到文件中心
     *
     * @return
     */
    private StorageDto uploadDzzzToWjzx(String gzlslid, String currentUserName, BdcZsDO bdcZsDOT, String djxl) throws Exception {
        String folderName = CommonConstantUtils.SJCL_DZZZ_NAME;
        StorageDto storageDto = null;
        List<BdcSlSjclDO> listSjcl = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, folderName);
        // 当收件材料目录为空的时候，需要新建一个目录
        StorageDto createStorageDto = null;

        MultipartDto multipartDto = new MultipartDto();
        if (CollectionUtils.isEmpty(listSjcl)) {
            // 保存电子证照的文件夹名称
            createStorageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, gzlslid, folderName, null);
            if (null != createStorageDto) {
                multipartDto.setNodeId(createStorageDto.getId());
                BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                bdcSlSjclDO.setWjzxid(createStorageDto.getId());
                bdcSlSjclDO.setClmc(folderName);
                bdcSlSjclDO.setGzlslid(gzlslid);
                bdcSlSjclDO.setDjxl(djxl);
                bdcSlSjclDO.setFs(1);
                bdcSlSjclDO.setYs(1);
                bdcSlSjclDO.setSjlx(CommonConstantUtils.SJLX_QT);
                bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
            }
        } else {
            BdcSlSjclDO bdcSlSjclDO = listSjcl.get(0);
            multipartDto.setNodeId(bdcSlSjclDO.getWjzxid());
        }

        multipartDto.setClientId(Constants.WJZX_CLIENTID);
        URL url = new URL(bdcZsDOT.getZzdz());
        MultipartFile file = null;
        if ("png".equals(eCertificate_format)) {
            String base64 = Base64Utils.encodeDzzzImageToBase64(url);
            if (StringUtils.isNotBlank(base64)) {
                base64 = "data:image/png;base64," + base64;
                file = Base64Utils.base64ToMultipart(base64);
            }
        } else {
            file = Base64Utils.createFileItem(url, eCertificate_format);
        }

        if (null != file) {
            multipartDto.setData(file.getBytes());
            multipartDto.setOwner(currentUserName);
            multipartDto.setContentType(file.getContentType());
            multipartDto.setSize(file.getSize());
            //multipartDto.setOriginalFilename(file.getOriginalFilename());
            multipartDto.setOriginalFilename(bdcZsDOT.getZsid() + "." + eCertificate_format);
            multipartDto.setName(file.getName());
        }
        //上传文件
        storageDto = storageClient.multipartUpload(multipartDto);
        return storageDto;
    }

    /**
     * 注销合肥电子证照
     *
     * @param gzlslid
     * @param zsid
     * @return
     */
    @Override
    public void zxHefeiDzzz(String gzlslid, String zsid) {
        if (StringUtils.isBlank(gzlslid) && StringUtils.isBlank(zsid)) {
            throw new AppException("缺失参数");
        }
        List<BdcZsDO> resultList = new ArrayList<>();
        String slbh = "";

        if (StringUtils.isBlank(zsid)) {
            LOGGER.info("开始调用电子证照注销接口--------------gzlslid:{}", gzlslid);
            List<BdcXmDTO> list = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(list)) {
                // 查询原项目
                slbh = list.get(0).getSlbh();
                List<BdcXmDO> listYxm = bdcXmMapper.listBdcZxYxm(gzlslid);
                LOGGER.info("注销电子证照查询原项目信息：{}", listYxm);
                if (CollectionUtils.isNotEmpty(listYxm)) {
                    // 有分别持证的情况 这里不能以项目为循环体，要以证书集合为循环体
                    for (BdcXmDO bdcxmDO : listYxm) {
                        String xmid = bdcxmDO.getXmid();
                        // 查询当前项目所有的证书
                        List<BdcZsDO> listZs = bdcZsService.queryBdcZsByXmid(xmid);
                        if (CollectionUtils.isNotEmpty(listZs)) {
                            // 去除zzbs为空的(查询证书方法已经去重，不需要再去重了)
                            List<BdcZsDO> zxZsList = listZs.stream().filter(bdczsDO -> StringUtils.isNotBlank(bdczsDO.getZzbs())).collect(Collectors.toList());
                            resultList.addAll(zxZsList);
                        }
                    }
                }
            }
        } else {
            // 当传入zsid时候 则做单个精确证书注销
            BdcZsDO bdczsDo = bdcZsService.queryBdcZs(zsid);
            List<BdcXmZsGxDO> xmZsGxDOS = bdcXmService.queryBdcXmZsgxByZsid(zsid);
            if (CollectionUtils.isNotEmpty(xmZsGxDOS)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmid(xmZsGxDOS.get(0).getXmid());
                List<BdcXmDO> bdcXmDOS = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOS)) {
                    slbh = bdcXmDOS.get(0).getSlbh();
                }
            }
            if (null != bdczsDo && StringUtils.isNotBlank(bdczsDo.getZzbs())) {
                resultList.add(bdczsDo);
            }
        }
        LOGGER.info("注销电子证照查询原证书信息：{}", resultList);
        this.zxDzzzByZsids(resultList, slbh);
    }

    /**
     * 注销电子证照
     *
     * @param listzs
     */
    private void zxDzzzByZsids(List<BdcZsDO> listzs, String slbh) {
        if (CollectionUtils.isEmpty(listzs)) {
            return;
        }
        Map map = new HashMap();
        map.put("type", "6");
        map.put("reason", "注销");
        String zsids = "";
        for (BdcZsDO bdczsDo : listzs) {
            if (zsids.indexOf(bdczsDo.getZsid()) == -1) {
                // 当证照标识有值的时候才取调用注销接口
                map.put("code", bdczsDo.getZzbs());
                map.put("slbh", slbh);
//                map.put("slbh","2020101300001");
                LOGGER.info("注销电子证照入参:{}", map);
                Object obj = null;
                try {
                    obj = exchangeInterfaceFeignService.requestInterface("dzzz_zzwh", map);
                    if (null != obj) {
                        zsids += bdczsDo.getZsid() + " ";
                        LOGGER.info("注销电子证照返回值:{}", obj.toString());
                        // 回写电子证照相关信息
                        BdcZsDO bdcZsDOTemp = new BdcZsDO();
                        bdcZsDOTemp.setZsid(bdczsDo.getZsid());
                        bdcZsDOTemp.setDzzzzt(CommonConstantUtils.DZZZZT_YZX);
                        bdcZsService.updateBdcZs(bdcZsDOTemp);
                    }
                } catch (Exception e) {
                    LOGGER.error("注销电子证照异常，调用方法：dzzz_zzwh");
                }

            }
        }
        LOGGER.info("已完成调用电子证照注销接口，注销的zsid有：{}", zsids);

    }

    /**
     * 查询电子证照
     *
     * @param slbh
     * @param spxtywh
     * @param parseBase64
     * @return
     */
    @Override
    public List queryDzzzBySlbh(String slbh, String spxtywh, Boolean parseBase64) {
        if (StringUtils.isBlank(slbh) && StringUtils.isBlank(spxtywh)) {
            throw new AppException("缺失参数");
        }
        LOGGER.info("查询电子证照入参,slbh:{},spxtywh:{}", slbh, spxtywh);
        List<DzzzExtendDTO> listDto = new ArrayList<>();
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setSlbh(slbh);
        bdcXmQO.setSpxtywh(spxtywh);
        bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
        List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
        String zsid = "";
        if (CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                String xmid = list.get(i).getXmid();
                List<BdcZsDO> bdcZsDOs = bdcZsService.queryBdcZsByXmid(xmid);
                if (CollectionUtils.isNotEmpty(bdcZsDOs)) {
                    for (BdcZsDO bdcZsDO : bdcZsDOs) {
                        // 多个项目一本证时  不能重复
                        if (zsid.indexOf(bdcZsDO.getZsid()) == -1) {
//                            HefeiDzzzDTO hefeiDzzzDTO = new HefeiDzzzDTO();
                            DzzzExtendDTO dzzzExtendDTO = new DzzzExtendDTO();
                            if (StringUtils.isNotBlank(bdcZsDO.getZzbs())) {
                                dzzzExtendDTO.setZzbs(bdcZsDO.getZzbs());
                                dzzzExtendDTO.setZzdz(bdcZsDO.getZzdz());
                                dzzzExtendDTO.setZzid(bdcZsDO.getZzid());
                                dzzzExtendDTO.setZzmlbm(bdcZsDO.getZzmlbm());
                                dzzzExtendDTO.setZzqfsj(bdcZsDO.getZzqfsj());
                                dzzzExtendDTO.setBdcdyh(bdcZsDO.getBdcdyh());
                                dzzzExtendDTO.setBdcqzh(bdcZsDO.getBdcqzh());
                                dzzzExtendDTO.setZmqlsx(bdcZsDO.getZmqlsx());
                                dzzzExtendDTO.setQlr(bdcZsDO.getQlr());
                                dzzzExtendDTO.setYwr(bdcZsDO.getYwr());
                                dzzzExtendDTO.setZl(bdcZsDO.getZl());
                                dzzzExtendDTO.setQlqtzk(bdcZsDO.getQlqtzk());
                                dzzzExtendDTO.setFj(bdcZsDO.getFj());
                                dzzzExtendDTO.setSzsj(bdcZsDO.getSzsj());
                                dzzzExtendDTO.setZsid(bdcZsDO.getZsid());

                                //这个参数不再起作用，下面提供通过zzbs查询证照的接口，这个参数没有意义
//                                if(parseBase64.booleanValue()){
//                                    try {
//                                        hefeiDzzzDTO.setData(Base64Utils.encodeDzzzImageToBase64(new URL(bdcZsDO.getZzdz())));
//                                    } catch (MalformedURLException e) {
//                                        LOGGER.error("电子证照地址转base64异常",e);
//                                    }
//                                }
                                listDto.add(dzzzExtendDTO);
                                zsid += bdcZsDO.getZsid();
                            }
                        }
                    }
                }

            }
        }
        return listDto;
    }


    /**
     * 查询电子证照
     *
     * @param zzbs
     * @return
     */
    @Override
    public Object queryDzzzByZzbs(String zzbs) {
        if (StringUtils.isBlank(zzbs)) {
            throw new AppException("缺失参数");
        }
        LOGGER.info("查询电子证照入参,zzbs:{}", zzbs);
        BdcZsQO bdcZsQO = new BdcZsQO();
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        bdcZsQO.setQsztList(qsztList);
        bdcZsQO.setZzbs(zzbs);
        Map map = new HashMap();
        List<BdcZsDO> bdcZsDOs = bdcZsService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isNotEmpty(bdcZsDOs)) {
            BdcZsDO bdcZsDO = bdcZsDOs.get(0);
            String zsid = bdcZsDO.getZsid();
            String bdcqzh = bdcZsDO.getBdcqzh();
            List<BdcXmDO> listxm = bdcZsService.queryZsXmByZsid(zsid);
            if (CollectionUtils.isNotEmpty(listxm)) {
                String gzlslid = listxm.get(0).getGzlslid();
                LOGGER.info("查询文件中心gzlslid：{}", gzlslid);
                // 查询电子证照文件夹
                List<StorageDto> listFjcl = storageClient.listStoragesByName("clientId", gzlslid, null, CommonConstantUtils.SJCL_DZZZ_NAME, 1, 0);
                if (CollectionUtils.isNotEmpty(listFjcl)) {
                    LOGGER.info("查询文件中心电子证照文件夹数量：{}", listFjcl.size());
                    for (StorageDto storageDto : listFjcl) {
                        // 查询电子证照文件下的所有电子证照
                        List<StorageDto> listFile = storageClient.listAllSubsetStorages(storageDto.getId(), StringUtils.EMPTY, 1, null, 0, null);
                        if (CollectionUtils.isNotEmpty(listFile)) {
                            LOGGER.info("查询文件中心电子证照数量：{}", listFile.size());
                            for (StorageDto storageDto1 : listFile) {
                                // 上传的时候设置的origionname为zsid,这里找出当前证书的电子证照
                                // 取大云的下载地址给互联网
                                String fileName = storageDto1.getName();
                                if (StringUtils.isNotBlank(fileName) && (fileName.indexOf(bdcqzh) != -1 || fileName.indexOf(zsid) != -1)) {
                                    String url = storageDto1.getDownUrl();
                                    if (StringUtils.isNotBlank(url)) {
                                        byte[] data = Base64Utils.getFile(url);
                                        String base64 = Base64Util.encode(data);
                                        // 先以配置为准，因存在时间延迟，再以url是否包含格式字符
                                        map.put("format", eCertificate_format);
                                        if (url.indexOf("png") != -1) {
                                            map.put("format", "png");
                                        } else if (url.indexOf("ofd") != -1) {
                                            map.put("format", "ofd");
                                        }
                                        map.put("byteArray", data);
                                        map.put("fileName",fileName);
                                        map.put("base64",base64);
                                        LOGGER.info("解析文件流结果为：{}", map);
                                        return map;
                                    } else {
                                        throw new AppException("当前证书没有证照地址，zzid:" + bdcZsDO.getZsid());
                                    }
                                }else {
                                    LOGGER.info("当前电子证照名称：{},不动产权证号：{},zsid：{}",fileName,bdcqzh,zsid);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            throw new AppException("证照标识未查询到证书，zzbs:" + zzbs);
        }
        return map;
    }

    @Override
    public Object syncDzzzxx(DzzzxxDTO dzzzxxDTO) {
        if (dzzzxxDTO == null) {
            throw new MissingArgumentException("缺失参数：电子证照信息");
        }
        String bdcqzh = dzzzxxDTO.getBdcqzh();
        String base64str = dzzzxxDTO.getBase64str();
        // 获取文件夹，上传文件
        if (StringUtils.isAnyBlank(bdcqzh, base64str)) {
            throw new MissingArgumentException("缺失参数：不动产权证号、base64加密文件内容。");
        }
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setBdcqzh(bdcqzh);
        List<BdcZsDO> bdcZsDOList = this.bdcZsService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            throw new MissingArgumentException("没有查询到证书信息");
        }
        BdcZsDO bdcZsDO = bdcZsDOList.get(0);
        List<BdcXmDO> bdcXmDOList = this.bdcXmService.listBdcXmByZsid(bdcZsDO.getZsid());
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("未获取到相关联的项目信息");
        }
        String fileName = bdcZsDO.getBdcqzh() + CommonConstantUtils.WJHZ_PDF;
        String currentUserName = this.userManagerUtils.getCurrentUserName();
        StorageDto storageDto = this.uploadECertificateToWjzx(bdcXmDOList.get(0).getGzlslid(), bdcZsDO.getZzbs(), fileName, currentUserName, base64str);
        if (null != storageDto) {
            bdcZsDO.setStorageid(storageDto.getId());
            this.bdcZsService.updateBdcZs(bdcZsDO);
        }
        return storageDto;
    }

    /**
     * 上传电子证照内容至文件中心
     */
    @Override
    public StorageDto uploadECertificateToWjzx(String gzlslid, String zzbs, String fileName, String currentUserName, String base64str) {
        if (!StringUtils.equals(Constants.WJZX_CLIENTID_ECERTIFICATE, uploadStorageRoot)
                && !StringUtils.equals(Constants.WJZX_CLIENTID, uploadStorageRoot)) {
            throw new MissingArgumentException("配置错误！期望uploadStorageRoot为" + Constants.WJZX_CLIENTID_ECERTIFICATE
                    + "或" + Constants.WJZX_CLIENTID + "，实际值为" + uploadStorageRoot);
        }
        // 避免附件重复，上传前先判断，如果已存在，则删除已有的信息
        LOGGER.warn("流程{}上传电子证照，判断文件中心是否有数据，查询参数uploadStorageRoot{}fileName{}", gzlslid, uploadStorageRoot, fileName);
        List<StorageDto> storageDtoList = storageClient.listStoragesByName(uploadStorageRoot, gzlslid, null, fileName, null, 3);
        if (CollectionUtils.isNotEmpty(storageDtoList)) {
            List<String> listId = new ArrayList();
            for (StorageDto storageDto : storageDtoList) {
                listId.add(storageDto.getId());
            }
            if (CollectionUtils.isNotEmpty(listId)) {
                LOGGER.warn("文件中心已有storage数据{}", listId);
                boolean result = storageClient.deleteStorages(listId);
                LOGGER.warn("文件删除结果：{}", result);
            }
        }
        //新建文件夹
        // 保存电子证照的文件夹名称
        String folderName = CommonConstantUtils.SJCL_DZZZ_NAME;
        StorageDto storageDto = storageClient.createRootFolder(uploadStorageRoot, gzlslid, folderName, null);
        if (storageDto != null) {
            //上传文件
            byte[] fileByte = Base64Utils.decodeBase64StrToByte(base64str);
            MultipartDto multipartDto = this.getUploadParamDto(currentUserName, storageDto, fileByte, fileName);
            //判断是否有受理收件材料文件夹，不能再新增
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, folderName);
            if (CollectionUtils.isEmpty(bdcSlSjclDOList)) {
                LOGGER.info("未生成受理文件夹,开始新增,流程实例id{}", gzlslid);
                List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
                    BdcSlSjclDO bdcSlSjclDO = new BdcSlSjclDO();
                    bdcSlSjclDO.setDjxl(bdcXmDTOList.get(0).getDjxl());
                    bdcSlSjclDO.setGzlslid(gzlslid);
                    bdcSlSjclDO.setClmc(folderName);
                    bdcSlSjclDO.setWjzxid(storageDto.getId());
                    bdcSlSjclDO.setFs(1);
                    bdcSlSjclDO.setYs(1);
                    bdcSlSjclDO.setSjlx(CommonConstantUtils.SJLX_QT);
                    bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
                }
            } else {
                multipartDto.setNodeId(bdcSlSjclDOList.get(0).getWjzxid());
            }
            storageDto = storageClient.multipartUpload(multipartDto);
        }
        return storageDto;
    }

    /**
     * 批量电子证照注销
     *
     * @param count
     */
    @Override
    public void batchCancelXmECertificate(String count) {
        //查询需要注销的证书信息
        LOGGER.info("批量注销证书入参:{}", count);
        if (StringUtils.isNotBlank(count)) {
            List<BdcCancelECertificateDTO> zslist = bdcXmMapper.listLsZzxxForYcCancel(Integer.parseInt(count));
            if (CollectionUtils.isNotEmpty(zslist)) {
                LOGGER.info("批量注销证书数量:{}", zslist.size());
                if (zslist.size() > 1000) {
                    LOGGER.info("多线程处理开始");
                    List<AutoCancelECertificateHandlerThread> listThread = new ArrayList<>();
                    //多线程处理操作
                    List<List<BdcCancelECertificateDTO>> list = groupListByQuantity(zslist, 800);
                    for (List<BdcCancelECertificateDTO> tempZs : list) {
                        AutoCancelECertificateHandlerThread accessHandlerThread = new AutoCancelECertificateHandlerThread(eCertificateModelServiceThread, tempZs);
                        listThread.add(accessHandlerThread);
                    }
                    threadEngine.excuteThread(listThread, true);
                } else {
                    LOGGER.info("单线程处理开始");
                    for (BdcCancelECertificateDTO bdcCancelECertificateDTO : zslist) {
                        try {
                            cancelEcertificateByZzbsWithOutUpdateStorage(bdcCancelECertificateDTO, Constants.ZZBGYY_BDC_ZX);
                        } catch (Exception e) {
                            LOGGER.error("当前zsid:{},在注销证照时出现异常:", bdcCancelECertificateDTO.getZsid(), e);
                        }
                    }
                }

            }
        }
    }

    /**
     * 查询个人电子证照信息
     *
     * @param certownerno
     * @return
     */
    @Override
    public CertOwnerDzzzDTO.Custom getQlrDzzz(String certownerno) {
        CertOwnerDzzzDTO.Custom custom = new CertOwnerDzzzDTO.Custom();
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setQlrzjh(certownerno);
        bdcZsQO.setQsztList(Arrays.asList(CommonConstantUtils.QSZT_VALID));
        List<BdcZsDO> bdcZsDOS = bdcZsService.listBdcZs(bdcZsQO);
        if (CollectionUtils.sizeIsEmpty(bdcZsDOS)){
            custom.setText("未获取到证照信息！");
            custom.setCode("1");
            return custom;
        }
        //根据证书查找
        List<CertOwnerDzzzDTO.Info> dzzzInfo = new ArrayList<>();
        for (BdcZsDO bdcZsDO : bdcZsDOS) {
            try {
                if (StringUtils.isNotBlank(bdcZsDO.getZzbs())) {
                    BdcXmZsDTO bdcXmZsDTO = new BdcXmZsDTO();
                    bdcXmZsDTO.setZzbs(bdcZsDO.getZzbs());
                    BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcZzxxFeignService.getZzxxByzzbs(bdcXmZsDTO);
                    if (Objects.nonNull(bdcDzzzZzxxDO) && StringUtils.isNotBlank(bdcDzzzZzxxDO.getZzid())) {
                        BdcDzzzYwxxDTO bdcDzzzYwxxDTO = bdcZzxxFeignService.getZzywxxByzzid(bdcDzzzZzxxDO.getZzid());
                        CertOwnerDzzzDTO.Info info = new CertOwnerDzzzDTO.Info();
                        CertOwnerDzzzDTO.Certinfo certinfo = new CertOwnerDzzzDTO.Certinfo();
                        certinfo.setCertname(bdcDzzzZzxxDO.getZzmc());
                        certinfo.setCertawarddept(bdcDzzzZzxxDO.getZzbfjg());
                        certinfo.setCertownername(bdcDzzzZzxxDO.getCzzt());
                        certinfo.setCertownerno(bdcDzzzZzxxDO.getCzztdm());
                        if(Objects.nonNull(bdcDzzzZzxxDO.getZzyxqqsrq())) {
                            certinfo.setExpiredatefrom(DateUtils.formateTime(bdcDzzzZzxxDO.getZzyxqqsrq(),DateUtils.DATE_FORMAT_2));
                        }
                        if(Objects.nonNull(bdcDzzzZzxxDO.getZzyxqjzrq())) {
                            certinfo.setExpiredateto(DateUtils.formateTime(bdcDzzzZzxxDO.getZzyxqjzrq(),DateUtils.DATE_FORMAT_2));
                        }
                        if(Objects.nonNull(bdcDzzzZzxxDO.getZzbfrq())) {
                            certinfo.setAwarddate(DateUtils.formateTime(bdcDzzzZzxxDO.getZzbfrq(),DateUtils.DATE_FORMAT_2));
                        }
                        CertOwnerDzzzDTO.Certinfoextension certinfoextension = new CertOwnerDzzzDTO.Certinfoextension();
                        BeanUtils.copyProperties(bdcZsDO, certinfoextension);
                        certinfoextension.setZh(bdcZsDO.getBdcqzh());
                        certinfoextension.setNyr(bdcDzzzZzxxDO.getZzbfrq());
                        certinfoextension.setQlqtqk(bdcZsDO.getQlqtzk());
                        certinfoextension.setBh(bdcZsDO.getZhlsh());
                        if (Objects.nonNull(bdcDzzzYwxxDTO)) {
                            certinfoextension.setQllx(bdcDzzzYwxxDTO.getQllx());
                            certinfoextension.setGyqk(bdcDzzzYwxxDTO.getGyqk());
                        }
                        //获取电子证照的内容
                        try {
                            certinfoextension.setFile(getECertificateContent(bdcZsDO.getZzbs()));
                        } catch (Exception e){
                            e.printStackTrace();
                            LOGGER.info("连云港获取个人证照信息内容失败:{},{}",certownerno,e.getMessage());
                            custom.setText("获取证照信息失败");
                            custom.setCode("1");
                            return custom;
                        }
                        info.setCertinfo(certinfo);
                        info.setCertinfoextension(certinfoextension);
                        dzzzInfo.add(info);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                LOGGER.info("连云港获取个人证照信息失败:{},{}",certownerno,e.getMessage());
                custom.setText("获取证照信息失败");
                custom.setCode("1");
                return custom;
            }
        }
        custom.setText("获取证照信息成功！");
        custom.setCode("1");
        custom.setInfo(dzzzInfo);
        return custom;
    }

    /**
     * 查询电子证照信息
     *
     * @param dzzzcxxxRequestDTO
     * @return
     */
    @Override
    public DzzzResponseModel getDzzzxx(DzzzcxxxRequestDTO dzzzcxxxRequestDTO) {
        DzzzResponseModel result = new DzzzResponseModel();
        ResponseHead head = new ResponseHead();
        LOGGER.info("获取证照信息请求参数:{}",dzzzcxxxRequestDTO);
        if (dzzzcxxxRequestDTO == null || StringUtils.isBlank(dzzzcxxxRequestDTO.getQlrzjh())){
            head.setStatus("1");
            head.setMessage("必填参数缺失");
            result.setHead(head);
            return result;
        }
        Map<String, List<Map>> zdMap = bdcZdFeignService.listBdcZd();
        BdcZsQO bdcZsQO = new BdcZsQO();
        bdcZsQO.setQlrzjh(dzzzcxxxRequestDTO.getQlrzjh());
        if(StringUtils.isNotBlank(dzzzcxxxRequestDTO.getBdcqzh())){
            bdcZsQO.setBdcqzh(dzzzcxxxRequestDTO.getBdcqzh());
        }
        if(StringUtils.isNotBlank(dzzzcxxxRequestDTO.getQlr())){
            bdcZsQO.setQlr(dzzzcxxxRequestDTO.getQlr());
        }
        bdcZsQO.setQsztList(Arrays.asList(CommonConstantUtils.QSZT_VALID));
        List<BdcZsDO> bdcZsDOS = bdcZsService.listBdcZs(bdcZsQO);
        if (CollectionUtils.sizeIsEmpty(bdcZsDOS)){
            head.setStatus("1");
            head.setMessage("未获取到证照信息");
            result.setHead(head);
            return result;
        }
        //根据证书查找
        List<DzzxxcxDTO> dzzxxcxList = new ArrayList<>();
        for (BdcZsDO bdcZsDO : bdcZsDOS) {
            try {
                DzzxxcxDTO dzzxxcxDTO = new DzzxxcxDTO();
                dzzxxcxDTO.setBdcdyh(bdcZsDO.getBdcdyh());
                dzzxxcxDTO.setZl(bdcZsDO.getZl());
                dzzxxcxDTO.setGyqk(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getGyfs(), zdMap.get("gyfs")));
                dzzxxcxDTO.setQllx(StringToolUtils.convertBeanPropertyValueOfZd(bdcZsDO.getQllx(), zdMap.get("qllx")));
                dzzxxcxDTO.setQlxz(bdcZsDO.getQlxz());
                dzzxxcxDTO.setYt(bdcZsDO.getYt());
                dzzxxcxDTO.setSyqx(bdcZsDO.getSyqx());
                dzzxxcxDTO.setFzrq(bdcZsDO.getFzsj());
                dzzxxcxDTO.setBdcqzh(bdcZsDO.getBdcqzh());
                dzzxxcxDTO.setQlqtzk(bdcZsDO.getQlqtzk());
                List<BdcXmDO> xmDOList = bdcXmService.listBdcXmByZsid(bdcZsDO.getZsid());
                if(CollectionUtils.isNotEmpty(xmDOList) || null != xmDOList.get(0)) {
                    dzzxxcxDTO.setDjjg(xmDOList.get(0).getDjjg());
                    List<BdcQlrDO>  bdcQlrList =bdcQlrService.queryListBdcQlr(xmDOList.get(0).getXmid(),1);
                    if(CollectionUtils.isNotEmpty(bdcQlrList) || null != bdcQlrList.get(0)) {
                        dzzxxcxDTO.setQlrmc(bdcQlrList.get(0).getQlrmc());
                        dzzxxcxDTO.setQlr(bdcQlrList.get(0).getQlrmc());
                        dzzxxcxDTO.setQlrzjh(bdcQlrList.get(0).getZjh());
                        dzzxxcxDTO.setQlrzjlx(StringToolUtils.convertBeanPropertyValueOfZd(bdcQlrList.get(0).getZjzl(), zdMap.get("zjzl")));
                    }
                }
                if (StringUtils.isNotBlank(bdcZsDO.getZzbs()) && StringUtils.isNotBlank(bdcZsDO.getStorageid())) {
                    MultipartDto storageDto = storageClient.download(bdcZsDO.getStorageid());
                    if(null != storageDto){
                        dzzxxcxDTO.setZzfj(storageDto.getData());
                        dzzxxcxDTO.setFjlx("pdf");
                    }
                }
                dzzxxcxList.add(dzzxxcxDTO);
            }catch (Exception e){
                e.printStackTrace();
                LOGGER.info("获取个人证照信息失败:{},{}",dzzzcxxxRequestDTO,e.getMessage());
                head.setStatus("1");
                head.setMessage("未获取到证照信息");
                result.setHead(head);
                return result;
            }
        }
        head.setStatus("0");
        head.setMessage("获取证照信息成功");
        result.setHead(head);
        result.setData(dzzxxcxList);
        return result;
    }

    /**
     * 按数量分组list
     *
     * @param list
     * @param quantity
     * @return
     */
    public static List<List<BdcCancelECertificateDTO>> groupListByQuantity(List<BdcCancelECertificateDTO> list, int quantity) {
        if (list == null || list.size() == 0) {
            return null;
        }

        if (quantity <= 0) {
            new IllegalArgumentException("Wrong quantity.");
        }

        List<List<BdcCancelECertificateDTO>> wrapList = new ArrayList();
        int count = 0;
        while (count < list.size()) {
            wrapList.add(new ArrayList(list.subList(count, Math.min((count + quantity), list.size()))));
            count += quantity;
        }

        return wrapList;
    }

    /**
     * 验证当前节点（电子证照）是否可办结
     *
     * @param processInsId
     * @return
     */
    @Override
    public boolean yzDzzzSfkbj(String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new AppException("缺失参数");
        }

        List<BdcXmDTO> list = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isNotEmpty(list)) {
            List<BdcXmFbDO> bdcXmFbDOList = bdcXmService.listBdcXmfbByProInsID(processInsId);
            if (CollectionUtils.isNotEmpty(bdcXmFbDOList)) {
                Integer lzfs = bdcXmFbDOList.get(0).getLzfs();
                if (CommonConstantUtils.LZFS_DZZZ.equals(lzfs)) {
                    // 是电子证照后需要查询当前流程下的所有证书是否都制证
                    for (int i = 0; i < list.size(); i++) {
                        String xmidT = list.get(i).getXmid();
                        List<BdcZsDO> listzs = bdcZsService.queryBdcZsByXmid(xmidT);
                        if (CollectionUtils.isNotEmpty(listzs)) {
                            for (BdcZsDO bdcZsDO : listzs) {
                                if (StringUtils.isBlank(bdcZsDO.getZzbs()) || StringUtils.isBlank(bdcZsDO.getZzdz())) {
                                    throw new AppException("当前流程的证书或证明有未制电子证照的");
                                }
                            }
                        }
                    }
                }
            } else {
                throw new AppException("未查询到领证人信息");
            }
        } else {
            throw new AppException("未查询到项目信息");
        }
        return true;
    }

    /**
     * 查询电子证照备份版
     *
     * @param zzbs
     * @return
     */
    public Object queryDzzzByZzbsBak(String zzbs) {
        if (StringUtils.isBlank(zzbs)) {
            throw new AppException("缺失参数");
        }
        LOGGER.info("查询电子证照入参,zzbs:{}", zzbs);
        BdcZsQO bdcZsQO = new BdcZsQO();
        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        bdcZsQO.setQsztList(qsztList);
        bdcZsQO.setZzbs(zzbs);
        Map map = new HashMap();
        List<BdcZsDO> bdcZsDOs = bdcZsService.listBdcZs(bdcZsQO);
        if (CollectionUtils.isNotEmpty(bdcZsDOs)) {
            BdcZsDO bdcZsDO = bdcZsDOs.get(0);
            if ("png".equals(eCertificate_format)) {
                try {
                    String base64 = Base64Utils.encodeDzzzImageToBase64(new URL(bdcZsDO.getZzdz()));
                    if (StringUtils.isNotBlank(base64)) {
                        map.put("base64", base64);
                        return map;
                    }
                } catch (MalformedURLException e) {
                    LOGGER.error("电子证照地址转base64异常", e);
                }
            } else {
                String zsid = bdcZsDO.getZsid();
                List<BdcXmDO> listxm = bdcZsService.queryZsXmByZsid(zsid);
                if (CollectionUtils.isNotEmpty(listxm)) {
                    String gzlslid = listxm.get(0).getGzlslid();
                    String clmc = "电子证照";
                    // 查询电子证照文件夹
                    List<StorageDto> listFjcl = storageClient.listStoragesByName("clientId", gzlslid, null, clmc, 1, 0);
                    if (CollectionUtils.isNotEmpty(listFjcl)) {
                        for (StorageDto storageDto : listFjcl) {
                            // 查询电子证照文件下的所有电子证照
                            List<StorageDto> listFile = storageClient.listAllSubsetStorages(storageDto.getId(), StringUtils.EMPTY, 1, null, 0, null);
                            if (CollectionUtils.isNotEmpty(listFile)) {
                                for (StorageDto storageDto1 : listFile) {
                                    // 上传的时候设置的origionname为zsid,这里找出当前证书的电子证照
                                    // 取大云的下载地址给互联网
                                    String fileName = storageDto1.getName();
                                    if (StringUtils.isNotBlank(fileName) && fileName.indexOf(zsid) != -1) {
                                        map.put("base64", storageDto1.getDownUrl());
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return map;
    }

    private String getIps(String ipString) {
        String url = "";
        String regEx = "(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(ipString);
        while (m.find()) {
            url = url + m.group(1) + ":" + m.group(2);
        }
        return url;
    }
}
