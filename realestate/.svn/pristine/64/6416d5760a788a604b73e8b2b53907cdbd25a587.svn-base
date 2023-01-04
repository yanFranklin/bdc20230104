package cn.gtmap.realestate.certificate.service.impl;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.certificate.config.DzzzClywConfig;
import cn.gtmap.realestate.certificate.core.enums.DzzzClZtEnum;
import cn.gtmap.realestate.certificate.core.service.BdcZsService;
import cn.gtmap.realestate.certificate.core.thread.DzzzClThread;
import cn.gtmap.realestate.certificate.core.thread.HefeiDzzzClThread;
import cn.gtmap.realestate.certificate.service.ECertificateClService;
import cn.gtmap.realestate.certificate.service.ECertificateService;
import cn.gtmap.realestate.certificate.util.Constants;
import cn.gtmap.realestate.certificate.util.ECertificateParamUtils;
import cn.gtmap.realestate.certificate.util.ECertificateUtils;
import cn.gtmap.realestate.certificate.util.ECertificateValidationUtil;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.domain.electronic.BdcDzzzClzzDO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.DzzzClDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcDzzzCxDTO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.enums.ECertificateQfztEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcDzzzQO;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzClFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 存量电子证照补偿服务
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 下午4:56 2021/2/3
 */
@Service
public class ECertificateClServiceImpl implements ECertificateClService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ECertificateClServiceImpl.class);

    //---------------------- 配置项 --------------------------------
    @Autowired
    private DzzzClywConfig dzzzClywConfig;

    //----------------------  服务  --------------------------------
    @Autowired
    private ECertificateService eCertificateService;

    @Autowired
    private BdcZsService bdcZsService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    Repo repo;

    @Autowired
    private BdcDzzzFeignService bdcDzzzFeignService;

    @Autowired
    private BdcDzzzClFeignService bdcDzzzClFeignService;

    //----------------------  工具  --------------------------------
    @Autowired
    private ECertificateParamUtils eCertificateParamUtils;

    @Autowired
    private ECertificateUtils eCertificateUtils;

    @Resource(name = "zzbcPool")
    private ThreadPoolExecutor zzbcThreadPool;

    @Resource(name = "hefeiZzbcPool")
    private ThreadPoolExecutor hefeiZzbcThreadPool;


    @Override
    public Integer dzzzClywbc(DzzzClZtEnum clZtEnum) {
        Integer performSize = dzzzClywConfig.getPerformSize();
        if (performSize == null || performSize <= 0) {
            return 0;
        }
        // 获取证书中存在未拥有证书信息的数据，设置 STORAGEID 为空, ZZBS不为空 的查询条件
        BdcDzzzQO bdcDzzzQO = new BdcDzzzQO();
        bdcDzzzQO.setSfscpdf(clZtEnum.getSfscpdf());
        bdcDzzzQO.setSfsczzbs(clZtEnum.getSfsczzbs());
        bdcDzzzQO.setFzjsrq(dzzzClywConfig.getEndtime());
        bdcDzzzQO.setZscxlx("1");
        bdcDzzzQO.setQszt(CommonConstantUtils.QSZT_VALID);
        bdcDzzzQO.setZslx(dzzzClywConfig.getZslx());
        bdcDzzzQO.setLwgzldyid(dzzzClywConfig.getLwgzldyid());
        bdcDzzzQO.setFzsjOrder("DESC");

        // 统计目前系统中存量数据，按照配置每天处理的数据流进行分页查询
        Page<BdcDzzzCxDTO> bdcDzzzCxDTOS = repo.selectPaging("listDzzzxxByPageOrder", bdcDzzzQO, new PageRequest(0, performSize));

        Integer result = 0;
        if (bdcDzzzCxDTOS.hasContent()) {
            result = dealDzzzClData(bdcDzzzCxDTOS.getContent(), clZtEnum);
        }
        LOGGER.info("{} 共：{} 条，处理：{} 条，成功：{} 条", clZtEnum.getValue(), bdcDzzzCxDTOS.getTotalElements(), performSize, result);
        return result;
    }

    /**
     * 存量数据下处理
     *
     * @param bdcDzzzCxDTOS
     * @return java.lang.Integer
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    private Integer dealDzzzClData(List<BdcDzzzCxDTO> bdcDzzzCxDTOS, DzzzClZtEnum clZtEnum) {
        List<Future<Boolean>> results = Lists.newArrayListWithCapacity(bdcDzzzCxDTOS.size());
        for (BdcDzzzCxDTO bdcDzzzCxDTO : bdcDzzzCxDTOS) {
            if (StringUtils.isAnyBlank(bdcDzzzCxDTO.getXmid(), bdcDzzzCxDTO.getZsid())) {
                LOGGER.error("存量数据缺少 zsid，xmid：{}", JSON.toJSONString(bdcDzzzCxDTO));
                continue;
            }
            DzzzClThread dzzzClThread = new DzzzClThread(this, bdcDzzzCxDTO, clZtEnum);
            results.add(zzbcThreadPool.submit(dzzzClThread));
        }
        Integer size = 0;
        for (Future<Boolean> result : results) {
            try {
                if (Boolean.TRUE.equals(result.get())) {
                    size++;
                }
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("{} 处理错误: {}", clZtEnum.getValue(), e.getMessage());
            }
        }
        return size;
    }

    @Override
    public Boolean xzClDzzz(BdcDzzzCxDTO bdcDzzzCxDTO) {
        return this.xzClDzzz(bdcDzzzCxDTO.getZzbs(), bdcDzzzCxDTO.getZsid(), bdcDzzzCxDTO.getGzlslid(), bdcDzzzCxDTO.getBdcqzh());
    }

    @Override
    public Boolean qfClDzzz(BdcDzzzCxDTO bdcDzzzCxDTO) {
        if (StringUtils.isBlank(bdcDzzzCxDTO.getZsid())) {
            LOGGER.info("签发存量电子证照 zsid 为空");
            return false;
        }
        if (StringUtils.isBlank(bdcDzzzCxDTO.getXmid())) {
            LOGGER.info("签发存量电子证照 xmid 为空");
            return false;
        }
        String zsid = bdcDzzzCxDTO.getZsid();
        // 组织下载证照请求参数
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcDzzzCxDTO.getXmid());
        BdcZsDO bdcZs = entityMapper.selectByPrimaryKey(BdcZsDO.class, zsid);
        return this.qfClDzzz(bdcXmDO, bdcZs);
    }

    @Override
    public Integer xzClDzzz(List<String> zzbsList) {
        if (CollectionUtils.isEmpty(zzbsList)) {
            LOGGER.info("下载存量电子证照 pdf 传入参数为空");
            return 0;
        }
        List<BdcZsDO> bdcZsDOList = Lists.newArrayList();
        for (String zzbs : zzbsList) {
            if (StringUtils.isBlank(zzbs)) {
                LOGGER.info("下载存量电子证照 pdf 传入参数为空");
                continue;
            }
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setZzbs(zzbs);
            List<BdcZsDO> bdcZsDOS = bdcZsService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isNotEmpty(bdcZsDOS)) {
                bdcZsDOList.addAll(bdcZsDOS);
            } else {
                LOGGER.info("zzbs: {} 未查询到对应证书信息", zzbs);
            }
        }
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            LOGGER.info("下载存量电子证照 pdf 未查询到对应证书信息");
            return 0;
        }

        Integer success = 0;
        for (BdcZsDO bdcZsDO : bdcZsDOList) {
            List<BdcXmDO> bdcXmDOS = bdcZsService.queryZsXmByZsid(bdcZsDO.getZsid());
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                LOGGER.info("zsid: {} 未查询到对应项目信息", bdcZsDO.getZsid());
                continue;
            }
            Boolean status = xzClDzzz(bdcZsDO.getZzbs(), bdcZsDO.getZsid(), bdcXmDOS.get(0).getGzlslid(), bdcZsDO.getBdcqzh());
            if (status) {
                success++;
            } else {
                LOGGER.info("bdcqzh：{} 下载电子证照 pdf 失败", bdcZsDO.getBdcqzh());
            }
        }

        return success;
    }

    @Override
    public Integer qfClDzzz(List<String> bdcqzhs) {
        if (CollectionUtils.isEmpty(bdcqzhs)) {
            LOGGER.info("签发存量电子证照传入参数为空");
            return 0;
        }
        List<BdcZsDO> bdcZsDOList = Lists.newArrayList();
        for (String bdcqzh : bdcqzhs) {
            BdcZsQO bdcZsQO = new BdcZsQO();
            bdcZsQO.setBdcqzh(bdcqzh);
            List<BdcZsDO> bdcZsDOS = bdcZsService.listBdcZs(bdcZsQO);
            if (CollectionUtils.isNotEmpty(bdcZsDOS)) {
                bdcZsDOList.addAll(bdcZsDOS);
            } else {
                LOGGER.info("bdcqzh: {} 未查询到对应证书信息", bdcqzh);
            }
        }
        if (CollectionUtils.isEmpty(bdcZsDOList)) {
            LOGGER.info("签发存量电子证照未查询到对应证书信息");
            return 0;
        }

        Integer success = 0;
        for (BdcZsDO bdcZsDO : bdcZsDOList) {
            List<BdcXmDO> bdcXmDOS = bdcZsService.queryZsXmByZsid(bdcZsDO.getZsid());
            if (CollectionUtils.isEmpty(bdcXmDOS) || bdcXmDOS.get(0) == null || StringUtils.isBlank(bdcXmDOS.get(0).getXmid())) {
                LOGGER.info("zsid: {} 未查询到对应项目信息", bdcZsDO.getZsid());
                continue;
            }
            String xmid = bdcXmDOS.get(0).getXmid();
            BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
            if (bdcXmDO == null) {
                LOGGER.info("zsid: {} 未查询到对应项目信息", bdcZsDO.getZsid());
                continue;
            }
            Boolean status = this.qfClDzzz(bdcXmDO, bdcZsDO);
            if (status) {
                success++;
            } else {
                LOGGER.info("bdcqzh：{} 签发电子证照失败", bdcZsDO.getBdcqzh());
            }
        }
        return success;
    }

    /**
     * @description 批量创建合肥存量电子证照
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/3 9:26
     * @param zsids
     * @return Integer
     */
    @Override
    public Integer plCreateHefeiClDzzz(List<String> zsids) {
        Integer result = 0;
        if (CollectionUtils.isEmpty(zsids)) {
            return result;
        }
        result = dealHefeiDzzzClData(zsids);
        return result;
    }

    /**
     * @description 创建合肥存量电子证照
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/3 9:35
     * @param zsid
     * @return Boolean
     */
    @Override
    public Boolean createHefeiClDzzz(String zsid) {
        if (StringUtils.isBlank(zsid)) {
            LOGGER.error("制作存量电子证照，传入参数 zsid 为空");
            return false;
        }
        LOGGER.info("开始制作存量电子证照，当前zsid：{}",zsid);
        try{
            eCertificateService.createHefeiDzzz("", zsid, "", "");
            LOGGER.info("制作存量电子证照完成，当前zsid：{}完成", zsid);
            return Boolean.TRUE;
        }catch (Exception e){
            // 记录异常的zsid，设置电子证照状态为3，用于台账查询制证失败
            BdcZsDO bdcZsDO = bdcZsService.queryBdcZs(zsid);
            bdcZsDO.setDzzzzt(CommonConstantUtils.DZZZZT_ZZSB);
            LOGGER.info("回写制证失败的存量电子证照信息：{}", bdcZsDO.toString());
            bdcZsService.updateBdcZs(bdcZsDO);

            LOGGER.info("制作存量电子证照异常，当前zsid：{}", zsid);
            LOGGER.error("异常信息:", e);
        }
        return Boolean.FALSE;
    }

    /**
     * @description 处理合肥存量电子证照数据
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/3 9:30
     * @param zsids
     * @return Integer
     */
    private Integer dealHefeiDzzzClData(List<String> zsids) {
        List<Future<Boolean>> results = Lists.newArrayListWithCapacity(zsids.size());
        for (String zsid : zsids) {
            if (StringUtils.isBlank(zsid)) {
                LOGGER.error("制作存量电子证照 zsid 为空");
                continue;
            }
            HefeiDzzzClThread hefeiDzzzClThread = new HefeiDzzzClThread(this, zsid);
            results.add(hefeiZzbcThreadPool.submit(hefeiDzzzClThread));
        }
        Integer size = 0;
        for (Future<Boolean> result : results) {
            try {
                if (Boolean.TRUE.equals(result.get())) {
                    size++;
                }
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("制作存量电子证照处理错误: {}", e.getMessage());
            }
        }
        return size;
    }

    /**
     * 下载存量电子证照方法
     *
     * @return 是否下载成功
     */
    private Boolean xzClDzzz(String zzbs, String zsid, String gzlslid, String bdcqzh) {
        if (StringUtils.isBlank(zzbs)) {
            LOGGER.error("下载存量电子证照，传入参数 zzbs 为空");
            return false;
        }
        if (StringUtils.isBlank(zsid)) {
            LOGGER.error("下载存量电子证照，传入参数 zsid 为空");
            return false;
        }
        if (StringUtils.isBlank(bdcqzh)) {
            LOGGER.error("下载存量电子证照，传入参数 bdcqzh 为空");
            return false;
        }
        if (StringUtils.isBlank(gzlslid)) {
            LOGGER.error("下载存量电子证照，传入参数 gzlslid 为空");
            return false;
        }
        DzzzResponseModel dzzzResponseModel = null;
        try {
            // 组织下载证照请求参数
            JSONObject data = new JSONObject();
            data.put("zzbs", zzbs);
            data.put("dzzzsymd", dzzzClywConfig.getDzzzsymd());
            JSONObject request = new JSONObject();
            request.put("data", data);

            BdcZsDO zsxx = entityMapper.selectByPrimaryKey(BdcZsDO.class, zsid);
            String token = eCertificateParamUtils.getTokenYymc(zsxx.getQxdm(), Constants.DZZZ);
            LOGGER.info("电子证照存量数据:{}，自动补偿服务，下载请求参数：{}， Token：{}", bdcqzh, request, token);

            // 下载证照
            dzzzResponseModel = bdcDzzzFeignService.zzxxxz2(token, JSON.toJSONString(request));

            DzzzResponseData responseData = eCertificateUtils.dealDzzzResponsePrint(dzzzResponseModel, "定时任务下载存量电子证照", bdcqzh);
            if (Boolean.FALSE.equals(responseData.isStatus())) {
                throw new AppException("返回状态错误");
            }
            if (StringUtils.isBlank(responseData.getContent())) {
                throw new AppException("返回内容为空");
            }

            // 上传 base64 文件到大云
            StorageDto storageDto = eCertificateService.uploadECertificateToWjzx(gzlslid, zzbs,
                    bdcqzh + CommonConstantUtils.WJHZ_PDF, "证照补偿事件", responseData.getContent());

            // 证书信息上传大云后，更新证书中的 storageid
            if (null == storageDto || StringUtils.isBlank(storageDto.getDownUrl())) {
                throw new AppException("未返回 path 或下载地址！");
            }
            BdcZsDO bdcZsDO = new BdcZsDO();
            bdcZsDO.setZsid(zsid);
            bdcZsDO.setStorageid(storageDto.getId());
            bdcZsService.updateBdcZs(bdcZsDO);

            LOGGER.info("下载存量电子证书成功: {}，id：{}", bdcqzh, bdcZsDO.getZsid());

            saveOrUpdateClDzzz(bdcqzh, dzzzResponseModel, ECertificateQfztEnum.QFZT_QFWC.getCode());

            return Boolean.TRUE;
        } catch (Exception e) {
            if (dzzzResponseModel != null) {
                saveOrUpdateClDzzz(bdcqzh, dzzzResponseModel, ECertificateQfztEnum.QFZT_XZSB.getCode());
            } else {
                saveOrUpdateClDzzz(bdcqzh, "-1", "请求异常，详见日志：bdcqzh" + bdcqzh, ECertificateQfztEnum.QFZT_XZSB.getCode());
            }
            LOGGER.error("下载存量电子证照数据失败，证书ID:{},不动产权证号{}", zsid, bdcqzh, e);
        }
        return Boolean.FALSE;
    }

    /**
     * 签发存量电子证照方法
     *
     * @return 是否签发成功
     */
    private Boolean qfClDzzz(BdcXmDO bdcXmDO, BdcZsDO bdcZs) {
        if (bdcXmDO == null || bdcZs == null) {
            LOGGER.error("签发存量电子证照，传入参数为空");
            return Boolean.FALSE;
        }
        if (StringUtils.isBlank(bdcZs.getBdcqzh())) {
            LOGGER.error("签发存量电子证照，zsid: {}, 传入参数为产权证号为空", bdcZs.getZsid());
            return Boolean.FALSE;
        }

        DzzzResponseModel dzzzResponseModel = null;
        DzzzClDTO dzzzClDTO;
        try {
            dzzzClDTO = eCertificateParamUtils.generateClDzzzDTO(bdcXmDO, bdcZs);
            LOGGER.info("证书{}存量电子证照组织参数：{}", bdcZs.getZsid(), JSON.toJSONString(dzzzClDTO));
            String paramter = ECertificateValidationUtil.clDzzzValidation(dzzzClDTO, bdcZs, bdcXmDO);
            if (StringUtils.isNotBlank(paramter)) {
                throw new IllegalArgumentException(paramter);
            }
        } catch (Exception e) {
            // 处理为必填项问题
            saveOrUpdateClDzzz(bdcZs.getBdcqzh(), "1", "5", ECertificateQfztEnum.QFZT_QFSB.getCode());
            LOGGER.error("签发电子证照-组织数据失败，证书ID:{}, 失败原因：{}, 异常位置：{}", bdcZs.getZsid(), e.getMessage(), e.getCause());
            e.printStackTrace();
            return Boolean.FALSE;
        }

        if (dzzzClDTO == null) {
            LOGGER.error("签发电子证照-组织数据失败，证书ID:{}, 失败原因：{}", bdcZs.getZsid(), "数据为空");
            return Boolean.FALSE;
        }

        try {
            LinkedHashMap paramData = eCertificateUtils.getParamData(dzzzClDTO,bdcXmDO.getQxdm());
            String token = eCertificateParamUtils.getTokenYymc(bdcXmDO.getQxdm(), Constants.DZZZ);
            LOGGER.info("电子证照存量数据 zsid:{}，自动补偿服务，签发请求参数：{}， yymc：{}", bdcZs.getZsid(), paramData, token);
            dzzzResponseModel = bdcDzzzFeignService.clzzpdf(token, JSON.toJSONString(paramData));

            DzzzResponseData responseData = eCertificateUtils.dealDzzzResponsePrint(dzzzResponseModel, "定时任务签发存量电子证照", bdcZs.getZsid());
            if (Boolean.FALSE.equals(responseData.isStatus())) {
                throw new AppException("返回状态错误");
            }
            if (StringUtils.isBlank(responseData.getZzbs())) {
                throw new AppException("返回证照标示 zzbs 为空");
            }
//             签发成功更新 zzbs 字段
            BdcZsDO bdcZsDO = new BdcZsDO();
            bdcZsDO.setZsid(bdcZs.getZsid());
            bdcZsDO.setZzbs(responseData.getZzbs());
            bdcZsService.updateBdcZs(bdcZsDO);
            LOGGER.info("更新存量电子证照标示成功: {}，zsid：{}", bdcZs.getBdcqzh(), bdcZsDO.getZsid());

            saveOrUpdateClDzzz(bdcZs.getBdcqzh(), dzzzResponseModel, ECertificateQfztEnum.QFZT_QFCG.getCode());

            return Boolean.TRUE;
        } catch (Exception e) {
            if (dzzzResponseModel != null) {
                saveOrUpdateClDzzz(bdcZs.getBdcqzh(), dzzzResponseModel, ECertificateQfztEnum.QFZT_QFSB.getCode());
            } else {
                saveOrUpdateClDzzz(bdcZs.getBdcqzh(), "-1", "请求异常，详见日志：bdcqzh" + bdcZs.getBdcqzh(), ECertificateQfztEnum.QFZT_QFSB.getCode());
            }
            LOGGER.error("签发电子证照数据失败，证书ID:{}, 失败原因：{}", bdcZs.getZsid(), e.getMessage());
        }
        return Boolean.FALSE;
    }

    private void saveOrUpdateClDzzz(String bdcqzh, DzzzResponseModel dzzzResponseModel, Integer status) {
        BdcDzzzClzzDO bdcDzzzClzzDO = new BdcDzzzClzzDO();
        bdcDzzzClzzDO.setBdcqzh(bdcqzh);
        bdcDzzzClzzDO.setStatus(status);
        bdcDzzzClzzDO.setCzrq(new Date());
        bdcDzzzClzzDO.setCode(dzzzResponseModel.getHead().getStatus());
        bdcDzzzClzzDO.setMsg(dzzzResponseModel.getHead().getMessage());
        bdcDzzzClFeignService.saveOrUpdateClzz(bdcDzzzClzzDO);
        LOGGER.info("更新存量电子证书状态: {} ", JSON.toJSONString(bdcDzzzClzzDO));
    }

    private void saveOrUpdateClDzzz(String bdcqzh, String code, String msg, Integer status) {
        BdcDzzzClzzDO bdcDzzzClzzDO = new BdcDzzzClzzDO();
        bdcDzzzClzzDO.setBdcqzh(bdcqzh);
        bdcDzzzClzzDO.setStatus(status);
        bdcDzzzClzzDO.setCzrq(new Date());
        bdcDzzzClzzDO.setCode(code);
        bdcDzzzClzzDO.setMsg(msg);
        bdcDzzzClFeignService.saveOrUpdateClzz(bdcDzzzClzzDO);
        LOGGER.info("更新存量电子证书状态: {} ", JSON.toJSONString(bdcDzzzClzzDO));
    }
}
