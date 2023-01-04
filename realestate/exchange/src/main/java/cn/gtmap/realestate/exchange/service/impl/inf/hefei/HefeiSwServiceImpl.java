package cn.gtmap.realestate.exchange.service.impl.inf.hefei;

import cn.gtmap.gtc.storage.domain.dto.BaseResultDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dto.accept.TsswDataDTO;
import cn.gtmap.realestate.common.core.dto.accept.TsswDataFjclDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.PlQuerySwxxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.swxx.QuerySwxxResponseDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tsfjcl.response.TsfjclResponseHead;
import cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tssw.request.Fctpfj;
import cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tssw.request.Fctpfjlb;
import cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tssw.request.Fctpfjsy;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-09
 * @description 税务相关特殊处理
 */
@Service
public class HefeiSwServiceImpl {

    protected static Logger LOGGER = LoggerFactory.getLogger(HefeiSwServiceImpl.class);

    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    BdcZdFeignService zdFeignService;
    private static String BASE64_QZ_PDF = "data:application/pdf;base64,";
    @Value("${dksw.sf_tslxdh: true}")
    private Boolean tslxdh;

    /**
     * @param bdcSlSqrDOS
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 推送税务 处理 是否直系亲属 字段
     */
    public String dealSfzxqs(List<BdcSlSqrDO> bdcSlSqrDOS) {
        if (CollectionUtils.isNotEmpty(bdcSlSqrDOS)) {
            Set<Integer> sfzxqsSet = Sets.newHashSetWithExpectedSize(bdcSlSqrDOS.size());
            for (int i = 0; i < bdcSlSqrDOS.size(); i++) {
                BdcSlSqrDO temp = bdcSlSqrDOS.get(i);
                if (StringUtils.equals(CommonConstantUtils.QLRLB_QLR, temp.getSqrlb())) {
                    sfzxqsSet.add(temp.getSfzxqs());
                }
            }
            if (sfzxqsSet.size() == 1) {
                Integer sfzxqs = sfzxqsSet.iterator().next();
                if (sfzxqs != null) {
                    return sfzxqs + "";
                }
            }
        }
        return null;
    }

    /**
     * @param fjclList
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 推送税务 处理 是否直系亲属 字段
     */
    public Integer countFjxx(List<TsswDataFjclDTO> fjclList) {
        return CollectionUtils.isEmpty(fjclList) ? 0 : fjclList.size();
    }

    /**
     * @param param
     * @return Map
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 合并存量房税务信息
     */
    public Map combineClfSwxx(JSONObject param) {
        LOGGER.info("合肥合并存量房税务信息入参：{}", param.toString());
        Map map = new HashMap();
        Map secondHouseInfoCollectByIntegrated = new HashMap();
        Map request = new HashMap();
        request.put("head", param.get("head"));
        if (param.get("body") != null) {
            if (param.get("qlrs") != null) {
                String zxqsjybj = "";
                List<JSONObject> crfList = new ArrayList<>();
                List<JSONObject> srfList = new ArrayList();
                List<JSONObject> jsonObjectList = (List<JSONObject>) param.get("qlrs");
                if (CollectionUtils.isNotEmpty(jsonObjectList)) {
                    for (JSONObject jsonObject : jsonObjectList) {
                        if (StringUtils.equals(MapUtils.getString(jsonObject, "sqrlb"), CommonConstantUtils.QLRLB_QLR)) {
                            srfList.add(jsonObject);
                            if (!StringUtils.equals(zxqsjybj, "1") && StringUtils.isNotBlank(MapUtils.getString(jsonObject, "sfzxqs"))) {
                                zxqsjybj = MapUtils.getString(jsonObject, "sfzxqs");
                            }
                        } else {
                            crfList.add(jsonObject);
                        }
                    }
                }
                if (StringUtils.isNotBlank(zxqsjybj)) {
                    MapUtils.getMap(param, "body").put("zxqsjybj", zxqsjybj);
                }
                //56607 【合肥】推送税务不要推送权利人电话需求20220701 做成开关配置项,默认为true推送
                if (!tslxdh) {
                    if (CollectionUtils.isNotEmpty(crfList)) {
                        for (JSONObject crf : crfList) {
                            crf.put("lxdh", "");
                        }
                    }
                    if (CollectionUtils.isNotEmpty(srfList)) {
                        for (JSONObject srf : srfList) {
                            srf.put("lxdh", "");
                        }
                    }

                }
                MapUtils.getMap(param, "body").put("crfxxlb", crfList);
                MapUtils.getMap(param, "body").put("srfxxlb", srfList);
            }
            if (param.get("sdsjcxm") != null) {
                JSONObject sdsjcxm = (JSONObject) param.get("sdsjcxm");
                if (CommonConstantUtils.SF_S_DM.equals(MapUtils.getInteger(sdsjcxm, "sdsjcxm"))) {
                    MapUtils.getMap(param, "body").put("sdsjcxm", sdsjcxm);
                }
            }
            if (param.get("tdcrj") != null) {
                JSONObject tdcrj = (JSONObject) param.get("tdcrj");
                MapUtils.getMap(param, "body").put("tdcrj", tdcrj);
            }
            MapUtils.getMap(param, "body").put("sstpfjzs", param.get("sstpfjzs"));
            request.put("body", param.get("body"));
        }
        secondHouseInfoCollectByIntegrated.put("request", request);
        map.put("secondHouseInfoCollectByIntegrated", secondHouseInfoCollectByIntegrated);
        return map;
    }

    /**
     * @param param
     * @return Map
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 合并商品房税务信息
     */
    public Map combineSpfSwxx(JSONObject param) {
        Map map = new HashMap();
        Map firstHouse = new HashMap();
        Map request = new HashMap();
        request.put("head", param.get("head"));
        if (param.get("body") != null) {
            if (param.get("qlrs") != null) {
                List<JSONObject> srfList = new ArrayList();
                List<JSONObject> jsonObjectList = (List<JSONObject>) param.get("qlrs");
                if (CollectionUtils.isNotEmpty(jsonObjectList)) {
                    for (JSONObject jsonObject : jsonObjectList) {
                        if (StringUtils.equals(MapUtils.getString(jsonObject, "sqrlb"), CommonConstantUtils.QLRLB_QLR)) {
                            srfList.add(jsonObject);
                        } else {
                            MapUtils.getMap(param, "body").put("kfsmc", MapUtils.getString(jsonObject, "nsrmc"));
                            MapUtils.getMap(param, "body").put("kfsnsrsbh", MapUtils.getString(jsonObject, "sfzjhm"));
                        }
                    }
                }
                //56607 【合肥】推送税务不要推送权利人电话需求20220701 做成开关配置项,默认为true推送
                if (!tslxdh) {
                    if (CollectionUtils.isNotEmpty(srfList)) {
                        for (JSONObject srf : srfList) {
                            srf.put("lxdh", "");
                        }
                    }
                }
                MapUtils.getMap(param, "body").put("srfxxlb", srfList);
            }
            MapUtils.getMap(param, "body").put("sstpfjzs", param.get("sstpfjzs"));
            request.put("body", param.get("body"));
        }
        firstHouse.put("request", request);
        map.put("firstHouseInfoCollectByIntegrated", firstHouse);
        return map;
    }

    /**
     * @param param
     * @return Map
     * @author <a href="mailto:shaoliyao@gtmap.cn">huangjian</a>
     * @description 合并土地契税信息
     */
    public Map combineTdqsxx(JSONObject param) {
        LOGGER.info("土地契税数据：{}", param.toJSONString());
        Map map = new HashMap();
        Map firstHouse = new HashMap();
        Map request = new HashMap();
        request.put("head", param.get("head"));
        if (param.get("tdcrj") != null) {
            MapUtils.getMap(param, "body").put("tdcrj", param.get("tdcrj"));
        }
        request.put("body", param.get("body"));

        firstHouse.put("request", request);
        map.put("landTransferInfoCollectByIntegrated", firstHouse);
        return map;
    }

    /**
     * @param fjList
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tssw.request.Fctpfj>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 处理附件材料  需要循环 上传
     */
    public Fctpfjsy dealFjcl(List<Fctpfj> fjList, String slbh, String xmid) {
        List<Fctpfj> revertFjList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fjList)) {
            for (Fctpfj fctpfj : fjList) {
                String wjzxid = fctpfj.getFctpid();
                if (StringUtils.isNotBlank(wjzxid)) {
                    fctpfj.setFctp(getBase64Code(wjzxid));
                    JSONObject requestJSON = new JSONObject();
                    requestJSON.put("fctpfj", fctpfj);
                    requestJSON.put("slbh", slbh);
                    requestJSON.put("xmid", xmid);
                    TsfjclResponseHead head = exchangeBeanRequestService.request("swTsFjcl", requestJSON, TsfjclResponseHead.class);
                    if (head != null) {
                        if (StringUtils.equals(head.getRtn_code(), "200")) {
                            fctpfj.setFctp(null);
                            revertFjList.add(fctpfj);
                            continue;
                        } else {
                            throw new AppException("税务推送附件失败：{}" + head.getRtn_msg());
                        }
                    } else {
                        throw new AppException("税务推送附件接口返回值为空");
                    }
                }
            }
        }

        Fctpfjsy fctpfjsy = new Fctpfjsy();
        fctpfjsy.setFctpfjlb(new Fctpfjlb());
        fctpfjsy.getFctpfjlb().setFctpfj(revertFjList);
        fctpfjsy.setFctpzs(revertFjList.size() + "");
        return fctpfjsy;
    }

    /**
     * @param fjList
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tssw.request.Fctpfj>
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 德宽处理附件材料  需要循环 上传
     */
    public void dealFjcl_dk(List<Fctpfj> fjList, String slbh, String fwuuid, String tdbz,JSONObject head) {
        LOGGER.info("德宽处理附件材料开始推送附件信息，slbh={},fwuuid={}", slbh, fwuuid);
        List<Fctpfj> revertFjList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fjList)) {
            Map resmap = new HashMap();
            Map map = new HashMap();
            Map pictureMaterialsCollect = new HashMap();
            Map request = new HashMap();
            resmap.put("params", map);
            resmap.put("slbh", slbh);
            map.put("pictureMaterialsCollect", pictureMaterialsCollect);
            pictureMaterialsCollect.put("request", request);
            request.put("head", head);
            for (Fctpfj fctpfj : fjList) {
                String wjzxid = fctpfj.getFctpid();
                if (StringUtils.isNotBlank(wjzxid)) {
                    Map body = new HashMap();
                    body.put("tplx", fctpfj.getFctplx());
                    body.put("fwuuid", fwuuid);
                    body.put("tdbz",tdbz);
                    body.put("fctp", getBase64CodeHasQz(wjzxid));
                    request.put("body", body);
//                    LOGGER.info("德宽处理附件材料,{}",body.toString());

                    if (StringUtils.isNotBlank(MapUtils.getString(body, "fctp"))) {
                        Object resHead = exchangeBeanRequestService.request("swTsFjcl_dk", resmap);
                        LOGGER.error("德宽处理附件材料，返回值：{}", JSONObject.toJSONString(resHead));
                        if (resHead != null) {
                            JSONObject responseHead = JSONObject.parseObject(JSON.toJSONString(resHead));
                            if (StringUtils.equals(MapUtils.getString(responseHead, "rtn_code"), "200")) {
                                continue;
                            } else {
                                throw new AppException("税务推送附件失败：" + responseHead.getString("rtn_msg"));
                            }
                        } else {
                            throw new AppException("税务推送附件接口返回值为空");
                        }
                    }
                }
            }
        }

    }

    /**
     * 单个推送税务商品房信息 德宽
     *
     * @param tsswDataDTO
     * @return QuerySwxxResponseDTO
     */
    public Object postSpfSwxxAndFjxx(TsswDataDTO tsswDataDTO) {
        LOGGER.info("开始推送税务商品房信息");
        QuerySwxxResponseDTO obj = exchangeBeanRequestService.request("tsSwxxSpf_http_dk", JSON.parseObject(JSON.toJSONString(tsswDataDTO)), QuerySwxxResponseDTO.class);
        LOGGER.info("结束推送税务商品房信息，返回信息：{}", JSON.toJSONString(obj));
        if (obj != null && StringUtils.equals("200", obj.getResponseCode())) {
            LOGGER.info("开始推送商品房附件信息:fwuuid:{}", obj.getFwuuid());
            tsswDataDTO.setFwuuid(obj.getFwuuid());
            obj.setXmid(tsswDataDTO.getXmid());
            exchangeBeanRequestService.request("swTsFjclPl_dk", JSON.parseObject(JSON.toJSONString(tsswDataDTO)));
        }
        return obj;
    }

    /**
     * 单个推送税务存量房信息 德宽
     *
     * @param tsswDataDTO
     * @return QuerySwxxResponseDTO
     */
    public Object postClfSwxxAndFjxx(TsswDataDTO tsswDataDTO) {
        LOGGER.info("开始推送税务存量房信息");
        QuerySwxxResponseDTO obj = exchangeBeanRequestService.request("tsSwxxClf_http_dk", JSON.parseObject(JSON.toJSONString(tsswDataDTO)), QuerySwxxResponseDTO.class);
        LOGGER.info("结束推送税务存量房信息，返回信息：{}", JSON.toJSONString(obj));
        if (obj != null && StringUtils.equals("200", obj.getResponseCode())) {
            LOGGER.info("开始推送存量房附件信息:fwuuid:{}", obj.getFwuuid());
            tsswDataDTO.setFwuuid(obj.getFwuuid());
            obj.setXmid(tsswDataDTO.getXmid());
            exchangeBeanRequestService.request("swTsFjclPl_dk", JSON.parseObject(JSON.toJSONString(tsswDataDTO)));
        }
        return obj;
    }

    /**
     * 批量推送税务商品房信息 德宽
     *
     * @param tsswDataDTOList
     * @return PlQuerySwxxResponseDTO
     */
    public Object postPlSpfSwxxAndFjxx(List<JSONObject> tsswDataDTOList) {
        PlQuerySwxxResponseDTO plQuerySwxxResponseDTO = new PlQuerySwxxResponseDTO();
        List<QuerySwxxResponseDTO> swxxResponseList = new ArrayList<>();
        LOGGER.info("开始批量推送税务商品房信息，参数为：{}",JSONObject.toJSONString(tsswDataDTOList));
        if (CollectionUtils.isNotEmpty(tsswDataDTOList)) {
            for (JSONObject jsonObject : tsswDataDTOList) {
                TsswDataDTO tsswDataDTO = JSONObject.toJavaObject(jsonObject, TsswDataDTO.class);
                QuerySwxxResponseDTO obj = exchangeBeanRequestService.request("tsSwxxSpf_http_dk", jsonObject, QuerySwxxResponseDTO.class);
                LOGGER.info("结束推送税务商品房信息，返回信息：{}", JSON.toJSONString(obj));
                String mergeid = tsswDataDTO.getSwMergeDTO().getMergeid();
                if (obj != null && StringUtils.equals("200", obj.getResponseCode())) {
                    // 批量业务推送附件，只推一次，推编号“-1”的
                    if (mergeid.contains("-1")) {
                        LOGGER.info("开始推送商品房附件信息:fwuuid:{}", obj.getFwuuid());
                        tsswDataDTO.setFwuuid(obj.getFwuuid());
                        exchangeBeanRequestService.request("swTsFjclPl_dk", JSON.parseObject(JSON.toJSONString(tsswDataDTO)));
                    }
                    obj.setXmid(tsswDataDTO.getXmid());
                    swxxResponseList.add(obj);
                }
            }
            plQuerySwxxResponseDTO.setSwxxResponseDTOS(swxxResponseList);
        }
        return plQuerySwxxResponseDTO;
    }


    /**
     * 批量推送税务存量房信息 德宽
     *
     * @param tsswDataDTOList
     * @return PlQuerySwxxResponseDTO
     */
    public Object postPlClfSwxxAndFjxx(List<JSONObject> tsswDataDTOList) {
        PlQuerySwxxResponseDTO plQuerySwxxResponseDTO = new PlQuerySwxxResponseDTO();
        List<QuerySwxxResponseDTO> swxxResponseList = new ArrayList<>();
        LOGGER.info("开始批量推送税务存量房信息，参数为：{}",JSONObject.toJSONString(tsswDataDTOList));
        if (CollectionUtils.isNotEmpty(tsswDataDTOList)) {
            for (JSONObject jsonObject : tsswDataDTOList) {
                TsswDataDTO tsswDataDTO = JSONObject.toJavaObject(jsonObject, TsswDataDTO.class);
                QuerySwxxResponseDTO obj = exchangeBeanRequestService.request("tsSwxxClf_http_dk", jsonObject, QuerySwxxResponseDTO.class);
                LOGGER.info("结束推送税务存量房信息，返回信息：{}", JSON.toJSONString(obj));
                String mergeid = tsswDataDTO.getSwMergeDTO().getMergeid();
                if (obj != null) {
                    // 批量业务推送附件，只推一次，推编号-1的
                    if (StringUtils.equals("200", obj.getResponseCode()) && mergeid.contains("-1")) {
                        LOGGER.info("开始推送存量房附件信息:fwuuid:{}", obj.getFwuuid());
                        tsswDataDTO.setFwuuid(obj.getFwuuid());
                        exchangeBeanRequestService.request("swTsFjclPl_dk", JSON.parseObject(JSON.toJSONString(tsswDataDTO)));
                    }
                    obj.setXmid(tsswDataDTO.getXmid());
                    swxxResponseList.add(obj);
                }
            }
            plQuerySwxxResponseDTO.setSwxxResponseDTOS(swxxResponseList);
        }
        return plQuerySwxxResponseDTO;
    }


    /**
     * @param wjzxFileId
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据文件中心ID 获取 base64位码
     */
    private String getBase64Code(String wjzxFileId) {
        if (StringUtils.isNotBlank(wjzxFileId)) {
            BaseResultDto resultDto = storageClient.downloadBase64(wjzxFileId);
            if (resultDto != null && resultDto.getCode() == 0 && StringUtils.isNotBlank(resultDto.getMsg())) {
                String baseCode = resultDto.getMsg();
                if (baseCode.startsWith("data:")) {
                    baseCode = baseCode.substring(baseCode.indexOf(",") + 1, baseCode.length());
                }
                return baseCode;
            } else {
                throw new AppException("根据文档中心ID获取文件Base64位码失败：" + resultDto.getMsg());
            }
        }
        return "";
    }

    /**
     * @param wjzxFileId
     * @return java.lang.String
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据文件中心ID 获取带文件格式前缀的 base64位码
     */
    private String getBase64CodeHasQz(String wjzxFileId) {
        if (StringUtils.isNotBlank(wjzxFileId)) {
            String dataQz = CommonConstantUtils.BASE64_QZ_IMAGE;
            StorageDto storageDto = storageClient.findById(wjzxFileId);
            /*if (storageDto != null && 2 != storageDto.getType() && StringUtils.indexOf(storageDto.getName(), "jpg") < 0) {
                dataQz = BASE64_QZ_PDF;
            }*/
            //合肥要求，后缀名不为pdf的，全部为jpg文件头
            /*if (storageDto != null && StringUtils.indexOf(storageDto.getName(), "pdf") > 0) {
                dataQz = BASE64_QZ_PDF;
            }*/
            //改为利用对照表对照的方式
            String hzname = "";

            if (storageDto != null && StringUtils.indexOf(storageDto.getName(), "pdf") > 0) {
                hzname = "pdf";
            }
            if (storageDto != null && StringUtils.indexOf(storageDto.getName(), "jpg") > 0) {
                hzname = "jpg";
            }
            if (storageDto != null && StringUtils.indexOf(storageDto.getName(), "jpeg") > 0) {
                hzname = "jpeg";
            }
            if (storageDto != null && StringUtils.indexOf(storageDto.getName(), "png") > 0) {
                hzname = "png";
            }
            if (storageDto != null && StringUtils.indexOf(storageDto.getName(), "bmp") > 0) {
                hzname = "bmp";
            }
            if (storageDto != null && StringUtils.indexOf(storageDto.getName(), "doc") > 0) {
                hzname = "doc";
            }
            if (storageDto != null && StringUtils.indexOf(storageDto.getName(), "docx") > 0) {
                hzname = "docx";
            }
            if (storageDto != null && StringUtils.indexOf(storageDto.getName(), "xls") > 0) {
                hzname = "xls";
            }
            if (storageDto != null && StringUtils.indexOf(storageDto.getName(), "xlsx") > 0) {
                hzname = "xlsx";
            }
            if (StringUtils.isNotBlank(hzname)) {
                BdcZdDsfzdgxDO zdDsfzdgxDO = new BdcZdDsfzdgxDO();
                zdDsfzdgxDO.setDsfxtbs(CommonConstantUtils.SW_DK);
                zdDsfzdgxDO.setBdczdz(hzname);
                zdDsfzdgxDO.setZdbs(CommonConstantUtils.BDC_ZD_FILE);
                BdcZdDsfzdgxDO zdDsfzdgxDO1 = zdFeignService.dsfZdgx(zdDsfzdgxDO);
                if (null != zdDsfzdgxDO1) {
                    dataQz = zdDsfzdgxDO1.getDsfzdz();
                }
            }

            LOGGER.info("文件头签名，{}", dataQz);
            String base64 = getBase64Code(wjzxFileId);
            if (StringUtils.isNotBlank(base64)) {
                return dataQz + base64;
            }
        }
        return "";
    }
}
