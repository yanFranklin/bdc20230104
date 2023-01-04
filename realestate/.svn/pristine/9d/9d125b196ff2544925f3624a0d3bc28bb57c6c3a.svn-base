package cn.gtmap.realestate.exchange.service.impl.inf.changzhou;

import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseData;
import cn.gtmap.realestate.common.core.dto.exchange.changzhou.dzqz.DzzzResponseHead;
import cn.gtmap.realestate.common.core.service.feign.certificate.ECertificateFeignService;
import cn.gtmap.realestate.common.core.service.feign.realestate_e_certificate.BdcDzzzGxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.exchange.core.convert.ZzdzjConvert;
import cn.gtmap.realestate.exchange.core.dto.changzhou.dv.DvZzdy;
import cn.gtmap.realestate.exchange.core.dto.changzhou.dv.DvZzdyPl;
import cn.gtmap.realestate.exchange.core.dto.changzhou.zzdzj.ChangzhouDzzzResponseData;
import cn.gtmap.realestate.exchange.core.dto.changzhou.zzdzj.ChangzhouDzzzResponseModel;
import cn.gtmap.realestate.exchange.core.dto.changzhou.zzdzj.ChangzhouDzzzplxzResponseData;
import cn.gtmap.realestate.exchange.core.vo.ChangZhouDzzzplxzRequestVO;
import cn.gtmap.realestate.exchange.core.vo.ChangZhouZzdzjRequestVO;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.changzhou.ChangZhouZzdzjService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChangZhouZzdzjServiceImpl implements ChangZhouZzdzjService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangZhouZzdzjService.class);

    private static final String DZZZSYMD = "用于自助机@dzzzsymd自助打证";

    private static final String DZZZSYMD_REPLACE_FLAG = "@dzzzsymd";

    private static final String REDIS_DZZZ_EXCHANGE_TOKEN = "redisDzzzExchangeToken";

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private ECertificateFeignService eCertificateFeignService;

    @Autowired
    private RedisUtils<String> redisStringUtils;

    @Autowired
    private BdcDzzzGxFeignService bdcDzzzGxFeignService;

    @Autowired
    private ZzdzjConvert zzdzjConvert;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;


    @Override
    public ChangzhouDzzzResponseModel<ChangzhouDzzzResponseData> getDzzzxxByzzxxxz(ChangZhouZzdzjRequestVO changZhouZzdzjRequestVO) {
        if (changZhouZzdzjRequestVO != null && StringUtils.isNotBlank(changZhouZzdzjRequestVO.getLzrzjh()) && StringUtils.isNotBlank(changZhouZzdzjRequestVO.getYwlsh()) && StringUtils.isNotBlank(changZhouZzdzjRequestVO.getZzjId())) {
            //获取证照标识
            Example example = new Example(DvZzdy.class);
            example.createCriteria().andEqualTo("lzrzjh", changZhouZzdzjRequestVO.getLzrzjh()).andEqualTo("ywlsh", changZhouZzdzjRequestVO.getYwlsh());
            List<DvZzdy> dvZzdyDOS = entityMapper.selectByExample(example);
//            List<DvZzdyDO> dvZzdyDOS = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(dvZzdyDOS)) {
                String zzbs = dvZzdyDOS.get(0).getZzbs();
                if (StringUtils.isNotBlank(zzbs)) {
                    // 组织下载证照请求参数
                    JSONObject data = new JSONObject();
                    data.put("zzbs", zzbs);
//                    data.put("dzzzsymd", DZZZSYMD.replace(DZZZSYMD_REPLACE_FLAG, changZhouZzdzjRequestVO.getZzjId()));
                    data.put("dzzzsymd", "");
                    JSONObject request = new JSONObject();
                    request.put("data", data);

                    // 获取 Token
//                    String accessToken = eCertificateFeignService.getECertificateToken();
//                    LOGGER.info("电子证照存量数据下载请求参数：{}， Token：{}", request, accessToken);

                    // 下载证照
                    JSONObject jsonObject = (JSONObject) exchangeBeanRequestService.request("zzxxxz", request);
//                    DzzzResponseModel dzzzResponseModel = bdcDzzzGxFeignService.zzxxxz(accessToken, JSON.toJSONString(request));
//                    DzzzResponseModel dzzzResponseModel = JSON.parseObject(jsonObject.toJSONString(), DzzzResponseModel.class);
                    if (jsonObject != null) {
                        LOGGER.info("查询到电子证照信息");
                        DzzzResponseDTO dzzzResponseDTO = JSON.parseObject(JSON.toJSONString(jsonObject), DzzzResponseDTO.class);
                        DzzzResponseHead dzzzResponseHead = dzzzResponseDTO.getHead();
                        DzzzResponseData dzzzResponseData = dzzzResponseDTO.getData();
                        if (null == dzzzResponseData || null == dzzzResponseHead) {
                            return ChangzhouDzzzResponseModel.fail(Constants.RESPONSE_FAIL_CODE, "未查询到数据", null);
                        }
                        if (!StringUtils.equals("0", dzzzResponseHead.getStatus())) {
                            return ChangzhouDzzzResponseModel.fail(dzzzResponseHead.getStatus(), dzzzResponseHead.getMessage(), null);
                        }
                        ChangzhouDzzzResponseData resultData = zzdzjConvert.getChangzhouDzzzResponseDataByDzzzResponseData(dzzzResponseData);
                        resultData.setZzjId(changZhouZzdzjRequestVO.getZzjId());
                        //获取dv_zzdy 中的状态值
                        resultData.setTsstatus(dvZzdyDOS.get(0).getTsstatus());
                        return ChangzhouDzzzResponseModel.ok(dzzzResponseHead.getStatus(), dzzzResponseHead.getMessage(), resultData);
                    } else {
                        return ChangzhouDzzzResponseModel.fail(Constants.RESPONSE_FAIL_CODE, "调用电子证照系统返回为空", null);
                    }
                } else {
                    return ChangzhouDzzzResponseModel.fail(Constants.RESPONSE_FAIL_CODE, "查询到的DvZzdyDO数据中的zzbs为空", null);
                }
            } else {
                return ChangzhouDzzzResponseModel.fail(Constants.RESPONSE_FAIL_CODE, "未查询到DvZzdyDO数据", null);
            }
        } else {
            return ChangzhouDzzzResponseModel.fail(Constants.RESPONSE_FAIL_CODE, "入参不全", null);
        }
    }

    @Override
    public ChangzhouDzzzResponseModel<List<ChangzhouDzzzplxzResponseData>> dzzzplxz(ChangZhouDzzzplxzRequestVO changZhouZzdzjRequestVO) {
        LOGGER.info("批量下载:{}", JSON.toJSONString(changZhouZzdzjRequestVO));
        if (changZhouZzdzjRequestVO != null && StringUtils.isNotBlank(changZhouZzdzjRequestVO.getBoptid()) && StringUtils.isNotBlank(changZhouZzdzjRequestVO.getSoptid())) {
            //获取证照标识
            Example example = new Example(DvZzdyPl.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("boptid", changZhouZzdzjRequestVO.getBoptid()).andEqualTo("soptid", changZhouZzdzjRequestVO.getSoptid());
            if (StringUtils.isNotBlank(changZhouZzdzjRequestVO.getSqr())) {
                criteria.andEqualTo("sqr", changZhouZzdzjRequestVO.getSqr());
            }
            if (StringUtils.isNotBlank(changZhouZzdzjRequestVO.getYwlsh())) {
                criteria.andEqualTo("ywlsh", changZhouZzdzjRequestVO.getYwlsh());
            }
            if (StringUtils.isNotBlank(changZhouZzdzjRequestVO.getZl())) {
                criteria.andEqualTo("zl", changZhouZzdzjRequestVO.getZl());
            }
            List<DvZzdyPl> dvZzdyDOS = entityMapper.selectByExample(example);
//            List<DvZzdyDO> dvZzdyDOS = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(dvZzdyDOS)) {
                List<ChangzhouDzzzplxzResponseData> responseDataList = new ArrayList<>(dvZzdyDOS.size());
                for (DvZzdyPl dvZzdyDO : dvZzdyDOS) {
                    String zzbs = dvZzdyDO.getZzbs();
                    LOGGER.info("当前zzbs:{}", zzbs);
                    if (StringUtils.isNotBlank(zzbs)) {
                        // 组织下载证照请求参数
                        JSONObject data = new JSONObject();
                        data.put("zzbs", zzbs);
//                    data.put("dzzzsymd", DZZZSYMD.replace(DZZZSYMD_REPLACE_FLAG, changZhouZzdzjRequestVO.getZzjId()));
                        data.put("dzzzsymd", "");
                        JSONObject request = new JSONObject();
                        request.put("data", data);

                        // 获取 Token
//                    String accessToken = eCertificateFeignService.getECertificateToken();
//                    LOGGER.info("电子证照存量数据下载请求参数：{}， Token：{}", request, accessToken);
                        // 下载证照
                        try {
                            JSONObject jsonObject = (JSONObject) exchangeBeanRequestService.request("zzxxxz", request);
//                    DzzzResponseModel dzzzResponseModel = bdcDzzzGxFeignService.zzxxxz(accessToken, JSON.toJSONString(request));
//                    DzzzResponseModel dzzzResponseModel = JSON.parseObject(jsonObject.toJSONString(), DzzzResponseModel.class);
                            if (jsonObject != null) {
                                LOGGER.info("查询到电子证照信息");
                                DzzzResponseDTO dzzzResponseDTO = JSON.parseObject(JSON.toJSONString(jsonObject), DzzzResponseDTO.class);
                                DzzzResponseHead dzzzResponseHead = dzzzResponseDTO.getHead();
                                DzzzResponseData dzzzResponseData = dzzzResponseDTO.getData();
                                if (null == dzzzResponseData || null == dzzzResponseHead) {
                                    ChangzhouDzzzplxzResponseData responseData = new ChangzhouDzzzplxzResponseData();
                                    initChangzhouPldzzzResponse(responseDataList, dvZzdyDO, responseData, "调用电子证照系统返回为空");
                                    continue;
//                                    return ChangzhouDzzzResponseModel.fail(Constants.RESPONSE_FAIL_CODE,"未查询到数据",null);
                                }
                                if (!StringUtils.equals("0", dzzzResponseHead.getStatus())) {
                                    ChangzhouDzzzplxzResponseData responseData = new ChangzhouDzzzplxzResponseData();
                                    initChangzhouPldzzzResponse(responseDataList, dvZzdyDO, responseData, dzzzResponseHead.getMessage());
                                    continue;
//                                    return ChangzhouDzzzResponseModel.fail(dzzzResponseHead.getStatus(),dzzzResponseHead.getMessage(),null);
                                }
                                ChangzhouDzzzplxzResponseData responseData = zzdzjConvert.getChangzhouDzzzplResponseDataByDzzzResponseData(dzzzResponseData);
                                //获取dv_zzdy 中的状态值
                                responseData.setTsstatus(dvZzdyDO.getTsstatus());
                                responseData.setBoname(dvZzdyDO.getBoname());
                                responseData.setSoname(dvZzdyDO.getSoname());
                                responseData.setSqr(dvZzdyDO.getSqr());
                                responseData.setYwlsh(dvZzdyDO.getYwlsh());
                                responseData.setZl(dvZzdyDO.getZl());
                                responseData.setZh(dvZzdyDO.getZh());
                                responseDataList.add(responseData);
                            } else {
                                ChangzhouDzzzplxzResponseData responseData = new ChangzhouDzzzplxzResponseData();
                                initChangzhouPldzzzResponse(responseDataList, dvZzdyDO, responseData, "调用电子证照系统返回为空");
//                                return ChangzhouDzzzResponseModel.fail(Constants.RESPONSE_FAIL_CODE,"调用电子证照系统返回为空",null);
                            }
                        } catch (Exception e) {
                            ChangzhouDzzzplxzResponseData responseData = new ChangzhouDzzzplxzResponseData();
                            initChangzhouPldzzzResponse(responseDataList, dvZzdyDO, responseData, "调用电子证照系统报错:" + e.getMessage());
                        }

                    } else {
                        return ChangzhouDzzzResponseModel.fail(Constants.RESPONSE_FAIL_CODE, "查询到的DvZzdyDO数据中的zzbs为空", null);
                    }
                }
                if (CollectionUtils.isNotEmpty(responseDataList)) {
                    return ChangzhouDzzzResponseModel.ok("0", "success", responseDataList);
                } else {
                    return ChangzhouDzzzResponseModel.fail(Constants.RESPONSE_FAIL_CODE, "未查询到相关的电子证照信息", null);
                }
            } else {
                return ChangzhouDzzzResponseModel.fail(Constants.RESPONSE_FAIL_CODE, "未查询到DvZzdyDO数据", null);
            }
        } else {
            return ChangzhouDzzzResponseModel.fail(Constants.RESPONSE_FAIL_CODE, "入参不全", null);
        }
    }

    private void initChangzhouPldzzzResponse(List<ChangzhouDzzzplxzResponseData> responseDataList, DvZzdyPl dvZzdyDO, ChangzhouDzzzplxzResponseData responseData, String msg) {
        responseData.setTsstatus(dvZzdyDO.getTsstatus());
        responseData.setBoname(dvZzdyDO.getBoname());
        responseData.setSoname(dvZzdyDO.getSoname());
        responseData.setSqr(dvZzdyDO.getSqr());
        responseData.setYwlsh(dvZzdyDO.getYwlsh());
        responseData.setZl(dvZzdyDO.getZl());
        responseData.setZh(dvZzdyDO.getZh());
        responseData.setMsg(msg);
        responseDataList.add(responseData);
    }

    /**
     * 获取 token
     */
    private String getToken() {
        String accessToken = redisStringUtils.getStringValue(REDIS_DZZZ_EXCHANGE_TOKEN);
        if (StringUtils.isBlank(accessToken)) {
            accessToken = eCertificateFeignService.getECertificateToken();
            // 安全令牌有效时限默认为2小时
            redisStringUtils.addStringValue(REDIS_DZZZ_EXCHANGE_TOKEN, accessToken, 7200L);
        }
        return accessToken;
    }

}
