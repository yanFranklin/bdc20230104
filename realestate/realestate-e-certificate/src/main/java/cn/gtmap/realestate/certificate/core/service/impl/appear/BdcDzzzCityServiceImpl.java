package cn.gtmap.realestate.certificate.core.service.impl.appear;


import cn.gtmap.realestate.certificate.core.mapper.*;
import cn.gtmap.realestate.certificate.core.model.domain.*;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxxYsj;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.SyncDzzzBo;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.down.DzzzDownRequestModel;
import cn.gtmap.realestate.certificate.core.model.dzzzgx.query.DzzzQueryRequestModel;
import cn.gtmap.realestate.certificate.core.model.em.ResponseEnum;
import cn.gtmap.realestate.certificate.core.service.*;
import cn.gtmap.realestate.certificate.core.service.appear.BdcDzzzCityService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileCenterService;
import cn.gtmap.realestate.certificate.core.service.file.BdcDzzzFileConfigService;
import cn.gtmap.realestate.certificate.core.service.redisson.BdcDzzzRedissionService;
import cn.gtmap.realestate.certificate.util.*;
import cn.gtmap.realestate.common.core.domain.BdcZsDO;
import cn.gtmap.realestate.common.core.dto.accept.DzzzClxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.DzzzxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.eCertificate.SyncDzzzClxxDTO;
import cn.gtmap.realestate.common.core.dto.realestate_e_certificate.DzzzResponseModel;
import cn.gtmap.realestate.common.core.enums.BdcZslxEnum;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0???2019-9-2
 */
@Service
public class BdcDzzzCityServiceImpl implements BdcDzzzCityService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BdcDzzzZzxxPdfService bdcDzzzZzxxPdfService;
    @Autowired
    private BdcDzzzService bdcDzzzService;
    @Autowired
    private BdcDzzzZzzxConfigService bdcDzzzZzzxConfigService;
    @Autowired
    private BdcDzzzZzxxService bdcDzzzZzxxService;
    @Autowired
    private BdcDzzzFileConfigService bdcDzzzFileConfigService;
    @Autowired
    private BdcDzzzMlxxMapper bdcDzzzMlxxMapper;
    @Autowired
    private BdcDzzzHttpService bdcDzzzHttpService;
    @Autowired
    private BdcDzzzFileCenterService bdcDzzzFileCenterService;
    @Autowired
    private BdcDzzzJzjxxMapper bdcDzzzJzjxxMapper;
    @Autowired
    private BdcDzzzUseConditionMapper bdcDzzzUseConditionMapper;
    @Autowired
    private BdcDzzzDownloadService bdcDzzzDownloadService;
    @Autowired
    private BdcDzzzQueryService bdcDzzzQueryService;
    @Autowired
    private BdcDzzzRestTemplateService bdcDzzzRestTemplateService;
    @Autowired
    private BdcDzzzZzxxMapper bdcDzzzZzxxMapper;
    @Autowired
    private BdcDzzzYwxxMapper bdcDzzzYwxxMapper;
    @Autowired
    private BdcDzzzZzxxCzztMapper bdcDzzzZzxxCzztMapper;
    @Autowired
    private BdcDzzzRedissionService bdcDzzzRedissionService;
    @Autowired
    private BdcDzzzLogService bdcDzzzLogService;
    @Autowired
    private BdcDzzzAsyncService bdcDzzzAsyncService;
    @Autowired
    private ECertificateFeignService eCertificateFeignService;


    /**
     * ????????????????????????????????????
     */
    @Value("${city.zzpdf.url:/rest/v1.1/zzgl/zzpdf}")
    private String cityZzpdf;

    @Override
    public DzzzResponseModel zzpdf(String jsonString, HttpServletRequest request) {
        DzzzResponseModel resultModel = exchangePostProvinceToken(request, cityZzpdf, jsonString);
        logger.info("??????4???resultModel??????{}", JSON.toJSONString(resultModel));
        if (null == resultModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
        }
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), resultModel.getHead().getStatus())) {
            return resultModel;
        }

        Map<String, Object> resultData = (Map<String, Object>) resultModel.getData();
        if (null == resultData) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
        }

        String zzbs = (String) resultData.get("zzbs");
        if (StringUtils.isBlank(zzbs)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
        }
        logger.info("??????5");
        bdcDzzzAsyncService.syncDzzz(zzbs);

        //?????????id????????????????????????
        logger.info("?????????id????????????????????????,jsonString?????????{}", jsonString);
        LinkedHashMap tmpMap = JSONObject.parseObject(jsonString, LinkedHashMap.class);
        if(tmpMap != null && tmpMap.get("zsid") != null){
            String zsid = (String) tmpMap.get("zsid");
            if(StringUtils.isNotBlank(zsid)){
                logger.info("?????????id????????????????????????,zsid???{}",zsid);
                BdcDzzzZzxxDO bdcDzzzZzxxDO = new BdcDzzzZzxxDO();
                bdcDzzzZzxxDO.setZzbs(zzbs);
                bdcDzzzZzxxDO.setZsid(zsid);
                bdcDzzzAsyncService.updateDzzzByZzbs(bdcDzzzZzxxDO);
            }
        }
        return resultModel;
    }

    @Override
    public DzzzResponseModel clzzpdf(String jsonString, HttpServletRequest request) {
        DzzzResponseModel resultModel = exchangePostProvinceToken(request, "/rest/v1.1/zzgl/clzzpdf", jsonString);

        if (null == resultModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
        }
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), resultModel.getHead().getStatus())) {
            return resultModel;
        }

        Map<String, Object> resultData = (Map<String, Object>) resultModel.getData();
        if (null == resultData) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
        }

        String zzbs = (String) resultData.get("zzbs");
        if (StringUtils.isBlank(zzbs)) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
        }

        bdcDzzzAsyncService.syncDzzz(zzbs);

        return resultModel;
    }

    @Override
    public DzzzResponseModel zzzx(String jsonString, HttpServletRequest request) {
        DzzzResponseModel resultModel = exchangePostProvinceToken(request, "/rest/v1.0/zzgl/zzzt", jsonString);

        //?????????????????????false??????????????????????????????
        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), resultModel.getHead().getStatus())
                && !StringUtils.equals(ResponseEnum.RESPONSE_CERTIFICATE_CANCELLED_ERROR.getCode(), resultModel.getHead().getMessage())) {
            return resultModel;
        }

        BdcDzzzZzxx bdcDzzzZzxx = JSON.parseObject(jsonString, BdcDzzzZzxx.class);
        if (null == bdcDzzzZzxx) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
        }

        BdcDzzzZzxxDO bdcDzzzZzxxDO = bdcDzzzZzxxService.queryBdcDzzzZzxxByZzbs(bdcDzzzZzxx.getZzbs());
        if (null != bdcDzzzZzxxDO && Constants.BDC_DZZZ_ZZZT_N.equals(bdcDzzzZzxxDO.getZzzt())) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_CERTIFICATE_CANCELLED_ERROR.getCode(), null);
        }

        bdcDzzzAsyncService.syncDzzz(bdcDzzzZzxx.getZzbs());
        return bdcDzzzService.dzzzResponseSuccess(null);
    }

    @Override
    public DzzzResponseModel dzzzDownloadFile(String jsonString, HttpServletRequest request) {

        DzzzDownRequestModel dzzzDownRequestModel = JSON.parseObject(jsonString, DzzzDownRequestModel.class);
        if (null == dzzzDownRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }

        String dzzzsymd = null != dzzzDownRequestModel.getData() ? dzzzDownRequestModel.getData().getDzzzsymd() : null;
        if (StringUtils.isBlank(dzzzsymd)) {
            DzzzResponseModel dzzzResponseModel = bdcDzzzDownloadService.dzzzDownloadFile(dzzzDownRequestModel, request);
            if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), dzzzResponseModel.getHead().getStatus())) {
                return dzzzResponseModel;
            }
        }

        DzzzResponseModel resultModel = exchangePostProvinceToken(request, "/rest/v1.0/zzgx/zzxxxz", jsonString);
        if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), resultModel.getHead().getStatus())) {
            mapResultSync(resultModel);
        }

        return resultModel;
    }

    @Override
    public DzzzResponseModel dzzzDownloadUrl(String jsonString, HttpServletRequest request) {
        DzzzDownRequestModel dzzzDownRequestModel = JSON.parseObject(jsonString, DzzzDownRequestModel.class);
        if (null == dzzzDownRequestModel) {
            return bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_PARAMETER_ERROR.getCode(), null);
        }

        String dzzzsymd = dzzzDownRequestModel.getData().getDzzzsymd();
        if (StringUtils.isBlank(dzzzsymd)) {
            DzzzResponseModel dzzzResponseModel = bdcDzzzDownloadService.dzzzDownloadUrl(dzzzDownRequestModel, request);
            if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), dzzzResponseModel.getHead().getStatus())) {
                return dzzzResponseModel;
            }
        }

        DzzzResponseModel resultModel = exchangePostProvinceToken(request, "/rest/v1.0/zzgx/zzdzxz", jsonString);
        if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), resultModel.getHead().getStatus())) {
            mapResultSync(resultModel);
        }

        return resultModel;
    }

    @Override
    public DzzzResponseModel zzcx(DzzzQueryRequestModel dzzzQueryRequestModel, HttpServletRequest request) {
        dzzzQueryRequestModel.getData().setDwdm(BdcDzzzPdfUtil.DZZZ_CITY_DWDM);

        DzzzResponseModel resultModel = exchangePostProvinceToken(request, "/rest/v1.0/zzgx/zzcx", JSON.toJSONString(dzzzQueryRequestModel));
        logger.info("?????????????????????" + JSON.toJSONString(resultModel));
        if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), resultModel.getHead().getStatus())) {
            listResultSync(resultModel);
        }
        logger.info("??????????????????222222???" + JSON.toJSONString(resultModel));
        return resultModel;
    }

    @Override
    public DzzzResponseModel zzjs(DzzzQueryRequestModel dzzzQueryRequestModel, HttpServletRequest request) {
        dzzzQueryRequestModel.getData().setDwdm(BdcDzzzPdfUtil.DZZZ_CITY_DWDM);
        DzzzResponseModel resultModel = exchangePostProvinceToken(request, "/rest/v1.0/zzgx/zzjs", JSON.toJSONString(dzzzQueryRequestModel));
        if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), resultModel.getHead().getStatus())) {
            listResultSync(resultModel);
        }

        return resultModel;
    }

    @Override
    public DzzzResponseModel zzysj(String jsonString, HttpServletRequest request) {
        DzzzResponseModel dzzzResponseModel = bdcDzzzQueryService.zzysj(jsonString);
        if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), dzzzResponseModel.getHead().getStatus())) {
            return dzzzResponseModel;
        }

        DzzzResponseModel resultModel = exchangePostProvinceToken(request, "/rest/v1.0/zzgx/zzysj", jsonString);
        if (StringUtils.equals(ResponseEnum.SUCCESS.getCode(), resultModel.getHead().getStatus())) {
            ysjResultSync(resultModel);
        }

        return resultModel;
    }

    @Override
    public DzzzResponseModel syncDzzzPdf(String zzids) {
        JSONArray message = new JSONArray();

        DzzzResponseModel resultModel;
        try {
            resultModel = bdcDzzzRestTemplateService.exchangePostDzzzResponseModel(BdcDzzzPdfUtil.DZZZ_PROVINCE_URL
                    + "/rest/v2.0/zzgl/syncDzzzPdf", zzids);
        } catch (Exception e) {
            logger.info("????????????????????????????????????????????????{}????????????{}"  , DateUtil.now());
            resultModel = bdcDzzzService.dzzzResponseFalse("????????????????????????", null);
        }

        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), resultModel.getHead().getStatus())) {
            return resultModel;
        }

        String resultData = (String) resultModel.getData();
        if (StringUtils.isNotBlank(resultData)) {
            JSONArray result = JSON.parseArray(resultData);
            if (CollectionUtils.isNotEmpty(result)) {
                for (int i = 0; i < result.size(); i++) {
                    JSONObject job = result.getJSONObject(i);
                    if (null != job) {
                        String zzid = job.getString("zzid");
                        String bdcqzh = job.getString("bdcqzh");
                        String zzqzlj = job.getString("zzqzlj");
                        if (StringUtils.isBlank(zzqzlj)) {
                            message.add(bdcqzh + ":???????????????PDF");
                            continue;
                        }
                        String zzqzljNew = syncDzzzPdf(zzid, zzqzlj, bdcqzh);
                        if (StringUtils.isNotBlank(zzqzljNew)) {
                            bdcDzzzZzxxService.updateBdcDzzzZzqzlj(zzqzljNew, zzid);
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(message)) {
            return bdcDzzzService.dzzzResponseFalse(JSON.toJSONString(message), null);
        }
        return bdcDzzzService.dzzzResponseSuccess(ResponseEnum.SUCCESS.getMsg(), null);
    }

    private DzzzResponseModel exchangePostProvinceToken(HttpServletRequest request, String url, String jsonString) {
        logger.info("??????9?????????????????????2.0????????????:{}", jsonString);
        DzzzResponseModel resultModel = null;
        String token = getProvincetoken((String) request.getAttribute(Constants.YYMC));
        //String logFlag = StringUtil.getUUID32();
        String controller = BdcDzzzPdfUtil.DZZZ_PROVINCE_URL + url;
        //String ip = PublicUtil.getIpByUrl(controller);
        BdcDzzzLogDO bdcDzzzLogDO = bdcDzzzLogService.getRequestData(jsonString, controller, (String) request.getAttribute(Constants.YYMC));
        //bdcDzzzLogService.insertBdcDzzzLog(bdcDzzzLogDO);

        /*DzzzResponseModel resultModel = bdcDzzzRestTemplateService.exchangePostDzzzResponseModel(controller + "?token="
                + token, jsonString);*/
        try {
            logger.info("?????????????????????{}", controller + "?token=" + token);
            byte[] result = bdcDzzzHttpService.doPostWithJson(controller + "?token=" + token, jsonString);
            if (null != result) {
                resultModel =  JSON.parseObject(StringUtil.byteToStrUtf8(result),DzzzResponseModel.class);
                logger.info("??????10???resultModel:{}", JSON.toJSONString(resultModel));
            } else {
                logger.info("??????10???resultModel:???");
            }
        } catch (IOException e) {
            logger.info("?????????????????????{}????????????{}" , url , DateUtil.now());
            resultModel = bdcDzzzService.dzzzResponseFalse(ResponseEnum.RESPONSE_SERVICE_TIMEOUT_ERROR.getCode(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //bdcDzzzLogDO.setMessage(resultModel.getHead().getMessage());
        bdcDzzzLogService.getResponseData(bdcDzzzLogDO, JSON.toJSONString(resultModel));
        bdcDzzzLogService.insertBdcDzzzLog(bdcDzzzLogDO);
        logger.info("??????11");
        return resultModel;
    }

    private String getProvincetoken(String yymc) {
        String token = bdcDzzzRedissionService.getMapCacheValue(Constants.REQUEST_TOKEN_RMAPCACHE, yymc, String.class);
        if (StringUtils.isNotBlank(token)) {
            return token;
        }

        JSONObject dataJob = new JSONObject();
        JSONObject yymcJob = new JSONObject();
        yymcJob.put(Constants.YYMC, yymc);
        dataJob.put("data", yymcJob);

        byte[] result = null;
        try {
            result = bdcDzzzHttpService.doPostWithJson(BdcDzzzPdfUtil.DZZZ_PROVINCE_URL + "/rest/v1.0/zzgx/token",dataJob.toJSONString());
            logger.info("??????token?????????{}", new String(result));
        } catch (Exception e) {
            logger.error("????????????token?????????{}" , DateUtil.now(), e);
        }

        if (null != result) {
            JSONObject tokenJob = JSON.parseObject(StringUtil.byteToStrUtf8(result));
            if (null != tokenJob) {
                JSONObject tokenDataJob = tokenJob.getJSONObject("data");
                if (null != tokenDataJob) {
                    token = tokenDataJob.getString(Constants.REQUEST_PARAM_TOKEN);
                    if (StringUtils.isNotBlank(token)) {
                        bdcDzzzRedissionService.setMapCacheValue(Constants.REQUEST_TOKEN_RMAPCACHE, yymc, token);
                    }
                }
            }
        }

        return token;
    }

    public void mapResultSync(DzzzResponseModel resultModel) {
        Map<String, Object> downResponseData = (Map<String, Object>) resultModel.getData();
        if (MapUtils.isNotEmpty(downResponseData)) {
            bdcDzzzAsyncService.syncDzzzDownloadInfo((String) downResponseData.get("zzbs"));
            bdcDzzzAsyncService.syncDzzz((String) downResponseData.get("zzbs"));
        }
    }

    public void ysjResultSync(DzzzResponseModel resultModel) {
        Map<String, Object> downResponseData = (Map<String, Object>) resultModel.getData();
        if (MapUtils.isNotEmpty(downResponseData)) {
            BdcDzzzZzxxYsj bdcDzzzZzxxYsj = (BdcDzzzZzxxYsj) downResponseData.get("zzzmxx");
            bdcDzzzAsyncService.syncDzzz(bdcDzzzZzxxYsj.getZZBS());
        }
    }

    public void listResultSync(DzzzResponseModel resultModel) {
        List<Map<String, Object>> dzzzQueryResponseDataList = (List<Map<String, Object>>) resultModel.getData();
        if (CollectionUtils.isNotEmpty(dzzzQueryResponseDataList)) {
            for (Map<String, Object> map : dzzzQueryResponseDataList) {
                bdcDzzzAsyncService.syncDzzz((String) map.get("zzbs"));
            }
        }
    }

    @Override
    public void syncDzzzDownloadInfo(String zzbs) {
        DzzzResponseModel dzzzResponseModel = bdcDzzzRestTemplateService.exchangePostDzzzResponseModel(BdcDzzzPdfUtil.DZZZ_PROVINCE_URL + "/rest/v2.0/zzgl/syncDzzzDownloadInfo"
                , zzbs);
        if (null == dzzzResponseModel) {
            logger.info("???????????????????????????????????????????????? {}??????????????????????????????", zzbs);
        }
        if (null != dzzzResponseModel && StringUtils.equals(ResponseEnum.FALSE.getCode(), dzzzResponseModel.getHead().getStatus())) {
            logger.info("???????????????????????????????????????????????? {}????????????????????? {}", zzbs, JSON.toJSONString(dzzzResponseModel));
        }

        String result = null != dzzzResponseModel ? (String) dzzzResponseModel.getData() : null;
        if (StringUtils.isNotBlank(result)) {
            JSONObject jon = JSON.parseObject(result);
            if (null != jon) {
                String bdcqzh = jon.getString("bdcqzh");
                JSONArray jzjArr = JSON.parseArray(jon.getString("syncDzzzJzjxx"));
                if (CollectionUtils.isNotEmpty(jzjArr)) {
                    for (int i = 0; i < jzjArr.size(); i++) {
                        BdcDzzzJzjxxDO jzjxxDO = jzjArr.getObject(i, BdcDzzzJzjxxDO.class);
                        BdcDzzzJzjxxDO bdcDzzzJzjxxDO = bdcDzzzJzjxxMapper.queryJzjxxByJzjId(jzjxxDO.getJzjid());
                        if (null == bdcDzzzJzjxxDO) {
                            String zzqzlj = syncDzzzPdf(jzjxxDO.getJzjid(), jzjxxDO.getZzwjlj(), bdcqzh);
                            if (StringUtils.isNotBlank(zzqzlj)) {
                                jzjxxDO.setZzwjlj(zzqzlj);
                            }
                            bdcDzzzJzjxxMapper.insertBdcDzzzJzjxx(jzjxxDO);
                        }
                    }
                }
                JSONArray conditionArr = JSON.parseArray(jon.getString("syncDzzzUseCondition"));
                if (CollectionUtils.isNotEmpty(conditionArr)) {
                    for (int i = 0; i < conditionArr.size(); i++) {
                        BdcDzzzUseConditionDo useConditionDo = conditionArr.getObject(i, BdcDzzzUseConditionDo.class);
                        BdcDzzzUseConditionDo bdcDzzzUseConditionDo = bdcDzzzUseConditionMapper.getUseConditionById(useConditionDo.getId());
                        if (null == bdcDzzzUseConditionDo) {
                            bdcDzzzUseConditionMapper.insertDzzzUseCondition(useConditionDo);
                        }
                    }
                }
            }

        }

    }

    @Override
    public DzzzResponseModel zzdzxzReplaceRestToFeign(DzzzResponseModel responseModel) {
        if (null != responseModel && StringUtils.equals(ResponseEnum.SUCCESS.getCode(), responseModel.getHead().getStatus())) {
            Map data = (Map)responseModel.getData();
            if (null != data) {
                String content = (String)data.get("content");
                if (StringUtils.isNotBlank(content) && StringUtils.indexOf(content, "rest") > -1) {
                    content = content.replace("rest", "feign");
                    data.put("content", content);
                }
            }
        }

        return responseModel;
    }

    @Override
    public void syncDzzz(String zzbs) {
        logger.info("??????????????????????????????????????? {}" + zzbs);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("zzbs", zzbs);
        DzzzResponseModel dzzzResponseModel = bdcDzzzRestTemplateService.exchangePostDzzzResponseModel(BdcDzzzPdfUtil.DZZZ_PROVINCE_URL + "/rest/v2.0/zzgl/syncDzzz"
                , JSON.toJSONString(jsonObject));
        logger.info("??????6???{}", JSON.toJSONString(dzzzResponseModel));
        if (null == dzzzResponseModel) {
            logger.info("??????????????????????????????????????? {}??????????????????????????????" + zzbs);
            return ;
        }

        if (StringUtils.equals(ResponseEnum.FALSE.getCode(), dzzzResponseModel.getHead().getStatus())) {
            logger.info("??????????????????????????????????????? {}?????????????????????" + JSON.toJSONString(dzzzResponseModel));
            return ;
        }

        String resultData = (String) dzzzResponseModel.getData();
        if (StringUtils.isBlank(resultData)) {
            logger.info("??????????????????????????????????????? {}?????????????????????:????????????" + zzbs);
            return ;
        }

        SyncDzzzBo syncDzzzBo = JSON.parseObject(resultData, SyncDzzzBo.class);
        if (null == syncDzzzBo) {
            logger.info("??????????????????????????????????????? {}?????????????????????:???????????????????????????" + zzbs);
            return ;
        }

        BdcDzzzZzxxDO bdcDzzzZzxxDO = syncDzzzBo.getBdcDzzzZzxxDO();
        BdcDzzzYwxxDo bdcDzzzYwxxDo = syncDzzzBo.getBdcDzzzYwxxDo();
        List<BdcDzzzZzxxCzztDo> bdcDzzzZzxxCzztDoList = syncDzzzBo.getBdcDzzzZzxxCzztDoList();
        BdcDzzzMlxxDO bdcDzzzMlxxDO = syncDzzzBo.getBdcDzzzMlxxDO();

        if (null != bdcDzzzZzxxDO && null != bdcDzzzYwxxDo) {
            BdcDzzzZzxxDO zzxxDO = bdcDzzzZzxxMapper.queryBdcDzzzZzxxByZzid(bdcDzzzZzxxDO.getZzid());
            if (null == zzxxDO) {
                bdcDzzzZzxxMapper.insertBdcDzzzZzxx(bdcDzzzZzxxDO);
            }
            //???????????????????????????1??????????????????2???zzqzlj??????
            if (null == zzxxDO || StringUtils.isBlank(zzxxDO.getZzqzlj())) {
                String zzqzlj = syncDzzzPdf(bdcDzzzZzxxDO.getZzid(), bdcDzzzZzxxDO.getZzqzlj(), bdcDzzzZzxxDO.getBdcqzh());
                //logger.info("????????????????????????????????????????????? {}" , zzqzlj);
                if (StringUtils.isNotBlank(zzqzlj)) {
                    bdcDzzzZzxxService.updateBdcDzzzZzqzlj(zzqzlj, bdcDzzzZzxxDO.getZzid());
                    syncDzzzTbzt(bdcDzzzZzxxDO.getZzid());
                    logger.info("????????????????????????PDF?????????bdcqzh {}???zzqzlj {}", bdcDzzzZzxxDO.getBdcqzh(), zzqzlj);
                }
            }

            //3?????????
            if (Constants.BDC_DZZZ_ZZZT_N.equals(bdcDzzzZzxxDO.getZzzt())) {
                syncDzzzPdf(bdcDzzzZzxxDO.getZzid(), bdcDzzzZzxxDO.getZzqzlj(), bdcDzzzZzxxDO.getBdcqzh());
                bdcDzzzZzzxConfigService.updateDzzzZxxxInfo(bdcDzzzZzxxDO.getZzid(), bdcDzzzZzxxDO.getZzbgyy(), bdcDzzzZzxxDO.getZzbgsj());
            }

            BdcDzzzYwxxDo ywxxDO = bdcDzzzYwxxMapper.queryYwxxByYwid(bdcDzzzYwxxDo.getYwid());
            if (null == ywxxDO) {
                bdcDzzzYwxxMapper.insertYwxx(bdcDzzzYwxxDo);
            }

            if (CollectionUtils.isNotEmpty(bdcDzzzZzxxCzztDoList)) {
                List<BdcDzzzZzxxCzztDo> czztDoList = bdcDzzzZzxxCzztMapper.queryBdcDzzzZzxxCzztDoByZzid(bdcDzzzZzxxDO.getZzid());
                if (CollectionUtils.isEmpty(czztDoList)) {
                    for (BdcDzzzZzxxCzztDo bdcDzzzZzxxCzztDo : bdcDzzzZzxxCzztDoList) {
                        bdcDzzzZzxxCzztMapper.insertBdcDzzzZzxxCzztDo(bdcDzzzZzxxCzztDo);
                    }
                }
            }

            if (null != bdcDzzzMlxxDO) {
                BdcDzzzMlxxDO mlxxDO = bdcDzzzMlxxMapper.queryBdcDzzzMlxxByMlid(bdcDzzzMlxxDO.getMlid());
                if (null == mlxxDO) {
                    bdcDzzzMlxxMapper.insertBdcDzzzMlxx(bdcDzzzMlxxDO);
                }
            }
        }

    }

    @Override
    public void syncDzzzClxx(List<SyncDzzzClxxDTO> syncDzzzClxxDTOList){
        if(CollectionUtils.isNotEmpty(syncDzzzClxxDTOList)) {
            logger.info("??????????????????????????????????????????????????? {}", JSON.toJSONString(syncDzzzClxxDTOList));
            for (SyncDzzzClxxDTO syncDzzzClxxDTO : syncDzzzClxxDTOList) {
                DzzzClxxDTO dzzzClxxDTO =syncDzzzClxxDTO.getDzzzClxxDTO();
                BdcZsDO bdcZsDO =syncDzzzClxxDTO.getBdcZsDO();
                if(dzzzClxxDTO ==null ||bdcZsDO ==null){
                    logger.error("????????????????????????????????????????????????????????? {}{}{}", dzzzClxxDTO,bdcZsDO);
                    continue;
                }
                if (StringUtils.isNotBlank(dzzzClxxDTO.getId())) {
                    //????????????
                    BdcDzzzZzxxDO zzxxDO = bdcDzzzZzxxMapper.queryBdcDzzzZzxxByZzid(dzzzClxxDTO.getId());
                    //????????????dozer??????
                    if (null == zzxxDO) {
                        zzxxDO = new BdcDzzzZzxx();
                        zzxxDO.setZzid(dzzzClxxDTO.getId());
                        zzxxDO.setZsid(bdcZsDO.getZsid());
                        zzxxDO.setZzmc(bdcZsDO.getBdcqzh());
                        zzxxDO.setZzlxdm(dzzzClxxDTO.getStyleCode());
                        zzxxDO.setZzbh(dzzzClxxDTO.getInfoCode());
                        zzxxDO.setZzbs(bdcZsDO.getZzbs());
                        zzxxDO.setZzbfjg(dzzzClxxDTO.getIssueOrgName());
                        zzxxDO.setZzbfjgdm(dzzzClxxDTO.getIssueOrgCode());
                        try {
                            zzxxDO.setZzbfrq(DateUtils.formatDate(dzzzClxxDTO.getMakeTime(), DateUtils.sdf));
                            zzxxDO.setZzyxqqsrq(DateUtils.formatDate(dzzzClxxDTO.getInfoValidityBegin(), DateUtils.sdf));
                            zzxxDO.setZzyxqjzrq(DateUtils.formatDate(dzzzClxxDTO.getInfoValidityEnd(), DateUtils.sdf));
                        }catch (Exception e){
                            logger.info("?????????????????????????????????????????????:{}{}{}",dzzzClxxDTO.getMakeTime(),dzzzClxxDTO.getInfoValidityBegin(),dzzzClxxDTO.getInfoValidityEnd());
                        }
                        zzxxDO.setCzzt(dzzzClxxDTO.getOwnerName());
                        zzxxDO.setCzztdm(dzzzClxxDTO.getOwnerId());

                        zzxxDO.setBdcqzh(bdcZsDO.getBdcqzh());
                        zzxxDO.setZzlsh(bdcZsDO.getZhlsh());
                        bdcDzzzZzxxMapper.insertBdcDzzzZzxx(zzxxDO);
                        //????????????
                        List<BdcDzzzYwxxDo> ywxxDoList = bdcDzzzYwxxMapper.queryYwxxByZzid(dzzzClxxDTO.getId());
                        BdcDzzzYwxxDo bdcDzzzYwxxDo =null;
                        if (CollectionUtils.isEmpty(ywxxDoList)) {
                            bdcDzzzYwxxDo = new BdcDzzzYwxxDo();
                            BeanUtils.copyProperties(bdcZsDO, bdcDzzzYwxxDo);
                            bdcDzzzYwxxDo.setYwid(UUIDGenerator.generate16());
                            bdcDzzzYwxxDo.setZzid(dzzzClxxDTO.getId());
                            bdcDzzzYwxxDo.setYwh(syncDzzzClxxDTO.getSlbh());
                            bdcDzzzYwxxDo.setZstype(BdcZslxEnum.getZhlx(bdcZsDO.getZslx(), ""));
                            bdcDzzzYwxxMapper.insertYwxx(bdcDzzzYwxxDo);
                        }
                        //????????????
                        List<BdcDzzzZzxxCzztDo> czztDoList = bdcDzzzZzxxCzztMapper.queryBdcDzzzZzxxCzztDoByZzid(dzzzClxxDTO.getId());
                        if (CollectionUtils.isEmpty(czztDoList)) {
                            BdcDzzzZzxxCzztDo bdcDzzzZzxxCzztDo =new BdcDzzzZzxxCzztDo();
                            bdcDzzzZzxxCzztDo.setZzid(dzzzClxxDTO.getId());
                            bdcDzzzZzxxCzztDo.setCzztid(UUIDGenerator.generate16());
                            bdcDzzzZzxxCzztDo.setCzzt(dzzzClxxDTO.getOwnerName());
                            bdcDzzzZzxxCzztDo.setCzztdm(dzzzClxxDTO.getOwnerId());
                            bdcDzzzZzxxCzztMapper.insertBdcDzzzZzxxCzztDo(bdcDzzzZzxxCzztDo);
                        }
                        BdcDzzzMlxxDO mlxxDO = bdcDzzzMlxxMapper.queryBdcDzzzMlxxByZzid(dzzzClxxDTO.getId());
                        if (mlxxDO == null) {
                            mlxxDO =new BdcDzzzMlxxDO();
                            BeanUtils.copyProperties(zzxxDO, mlxxDO);
                            if(bdcDzzzYwxxDo != null) {
                                BeanUtils.copyProperties(bdcDzzzYwxxDo, mlxxDO);
                            }
                            mlxxDO.setMlid(UUIDGenerator.generate16());
                            bdcDzzzMlxxMapper.insertBdcDzzzMlxx(mlxxDO);
                        }
                    }
                }
            }
        }
    }

    public String syncDzzzPdf(String id, String zzqzlj, String bdcqzh) {
        logger.info("????????????????????????PDF???bdcqzh {}, ???????????????????????????{}", bdcqzh, zzqzlj);
        String result = null;
        if (StringUtils.isNotBlank(zzqzlj)) {
            String nodeId = bdcDzzzFileCenterService.getNodeIdByZzwjlj(zzqzlj);
            if (StringUtils.isNotBlank(nodeId)) {
                DzzzResponseModel dzzzResponseModel = bdcDzzzRestTemplateService.exchangePostDzzzResponseModel(BdcDzzzPdfUtil.DZZZ_PROVINCE_URL + "/rest/v2.0/zzgl/zzxzfile"
                        , Base64Util.encodeStrToBase64Str(nodeId));
                if (null == dzzzResponseModel) {
                    logger.info("????????????????????????PDF?????????nodeId {}??????????????????????????????", nodeId);
                    return result;
                }
                if (StringUtils.equals(ResponseEnum.FALSE.getCode(), dzzzResponseModel.getHead().getStatus())) {
                    logger.info("????????????????????????PDF?????????nodeId {}?????????????????????????????? {}", nodeId, JSON.toJSONString(dzzzResponseModel));
                    return result;
                }
                Map<String, Object> resultData = (Map<String, Object>) dzzzResponseModel.getData();
                if (null == resultData) {
                    logger.info("????????????????????????PDF?????????nodeId {}?????????????????????" + nodeId);
                    return result;
                }

                String content = (String) resultData.get("content");
                logger.info("????????????????????????PDF????????????????????? {}", bdcqzh);
                if (StringUtils.isNotBlank(content)) {
                    byte[] signArr = Base64Util.decodeBase64StrToByte(content);
                    if (null != signArr) {
                        result = bdcDzzzFileConfigService.sendFileCenter(signArr, id, bdcqzh, Constants.BDC_DZZZ);
                    }
                    logger.info("??????12345");
                    this.syncDzzzToDj(bdcqzh, content);
                }
            }
        }
        return result;
    }

    private void syncDzzzTbzt(String zzid) {
        if (StringUtils.isNotBlank(zzid)) {
            DzzzResponseModel dzzzResponseModel = bdcDzzzRestTemplateService.exchangePostDzzzResponseModel(BdcDzzzPdfUtil.DZZZ_PROVINCE_URL + "/rest/v2.0/zzgl/syncDzzzTbzt"
                    , zzid);
            if (null == dzzzResponseModel) {
                logger.info("???????????????-???????????????????????????????????????????????????zzid???" + zzid);
            }
        }
    }

    /**
     * ???????????????????????????3.0??????
     * @param bdcqzh
     * @param content
     */
    private void syncDzzzToDj(String bdcqzh, String content){
        try{
            if(BdcDzzzPdfUtil.DZZZ_SYNC_DJ){
                eCertificateFeignService.syncDzzzxx(new DzzzxxDTO(bdcqzh, content));
            }
        }catch(Exception e){
            logger.error("???????????????????????????3.0??????", e);
        }
    }

}
