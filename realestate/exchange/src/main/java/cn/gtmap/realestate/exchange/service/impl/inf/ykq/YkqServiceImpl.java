package cn.gtmap.realestate.exchange.service.impl.inf.ykq;


import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.accept.BdcDdxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSqrSwDTO;
import cn.gtmap.realestate.common.core.dto.accept.TsswDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.YrbFhmResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfrwjs.request.YrbClfFcjyxxRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfrwjs.request.YrbClfFyjbxxRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.request.YrbClfskxxhqRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.request.YrbClfskxxhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.response.ClfskxxhqResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.response.YrbClfskxxFhmResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.response.YrbClfskxxhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.request.YrbEwmjkxxRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.request.YrbEwmjkxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.response.YrbEwmjkxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.response.YrbEwmjkxxResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.request.FcjydjkxxhqRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.request.FcjydjkxxhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.response.FcjydjkxxhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.response.FcjydjkxxhqResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.request.YrbFcjyEwmRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.request.YrbFcjyEwmTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.response.YrbFcjyEwmDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.response.YrbFcjyEwmResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.request.YrbFcjysbxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.request.YrbFcjysbxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.response.YrbFcjysbxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxx.response.YrbFcjysbxxFjxxResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxxqr.request.YrbFcjysbxxQrRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxxqr.request.YrbFcjysbxxQrTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjysbxxqr.response.YrbFcjysbqrResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.request.YrbFcjyyjkRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.request.YrbFcjyyjkTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyyjk.response.YrbFcjyyjkDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.request.YrbQslxdhqRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.request.YrbQslxdhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qslxdhq.response.YrbQslxdhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.request.YrbQswspzhqRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.request.YrbQswspzhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.response.YrbQswspzhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswspzhq.response.YrbYrbQswspzhqResponse;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswsxxhq.request.YrbQswsxxhqRequest;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswsxxhq.request.YrbQswsxxhqTzxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswsxxhq.response.YrbQswsxxhqDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.rwzt.request.YrbRwztRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.rwzt.request.YrbRwztTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.rwzt.response.YrbRwztDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.request.YrbSbztcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.request.YrbSbztcxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.response.YrbSbztcxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.request.YrbYxzlTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.response.YrbYxzlResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfrwjs.request.*;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.request.YrbZlfskxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.request.YrbZlfskxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.response.YrbZlfjsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfskxxhq.response.YrbZlfskxxResponse;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.YkqRequet;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.YkqSwRequestHead;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.clfrwjs.request.YkqClfRwjshouseRequest;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.clfrwjs.request.YkqClfTsxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.clfskxxhq.request.YkqClfskxxhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.ddztv2.request.YkqDdztDataDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.ddztv2.request.YkqDdztRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.fcjydjkxxhq.request.YkqFcjydjkxxhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.fcjyewmjkxx.request.YkqFcjyEwmTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.fcjysbxx.request.YkqFcjysbxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.fcjysbxxqr.request.YkqFcjysbxxQrTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.fcjyyjk.request.YkqFcjyyjkTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.fcjyyjk.response.YkqFcjyyjkDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.fcjyyjk.response.YkqFcjyyjsksj;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.qslxdhq.request.YkqQslxdhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.qswspzhq.request.YkqQswspzhqTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.qswsxxhq.request.YkqQswsxxhqTzxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.rwzt.request.YkqRwztTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.yxzlxxjs.request.YkqYxzlTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.yxzlxxjs.request.YkqYxzlxxjsList;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.zlfskxxhq.request.YkqZlfskxxTaxbizml;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.zlfwjs.request.YkqZlfRwjshouseRequest;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.zlfwjs.request.YkqZlfTsxxTaxbizml;
import cn.gtmap.realestate.common.core.enums.BdcSfztEnum;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlHsxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfssDdxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxmFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.util.ClassHandleUtil;
import cn.gtmap.realestate.exchange.web.rest.ExchangeInterfaceRestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * ????????????????????????
 */
@Service
public class YkqServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(YkqServiceImpl.class);

    @Autowired
    ExchangeInterfaceRestController exchangeInterfaceFeignService;
    @Autowired
    BdcZdFeignService zdFeignService;
    @Resource(name = "exchangeDozerMapper")
    DozerBeanMapper dozerBeanMapper;

    @Autowired
    BdcSlSfssDdxxFeignService bdcSlSfssDdxxFeignService;
    @Autowired
    BdcSlSfxmFeignService bdcSlSfxmFeignService;
    @Autowired
    BdcSlHsxxFeignService bdcSlHsxxFeignService;
    @Autowired
    BdcSlSfxxFeignService bdcSlSfxxFeignService;


    @Value("${ykq.sw.csdm:}")
    private String csdm;

    /**
     * ????????????????????????A001?????????????????????
     *
     * @param tsswDataDTO ???????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFhmResponse> zlfrwTs(TsswDataDTO tsswDataDTO) {
        if (Objects.isNull(tsswDataDTO)) {
            throw new MissingArgumentException("?????????????????????????????????????????????");
        }
        //????????????????????????
        YrbZlfHouseRequest houseRequest = new YrbZlfHouseRequest();
        dozerBeanMapper.map(tsswDataDTO, houseRequest, "zlf_house");
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(houseRequest);

        YrbZlfQtRequest qtRequest = new YrbZlfQtRequest();
        qtRequest.setSffgyrdp("Y");
        YrbZlfFcjyxxRequest fcjyxxRequest = new YrbZlfFcjyxxRequest();
        dozerBeanMapper.map(tsswDataDTO, fcjyxxRequest, "zlf_jyxx");
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(fcjyxxRequest);
        YrbZlfFyjbxxRequest zlfFyjbxxRequest = new YrbZlfFyjbxxRequest();
        dozerBeanMapper.map(tsswDataDTO, zlfFyjbxxRequest, "zlf_jbxx");
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(zlfFyjbxxRequest);

        List<YrbZlfJyfxxRequest> jyfxxList = new ArrayList<>();
        List<YrbZlfHyxxRequest> hyxxList = new ArrayList<>();
        List<YrbZlfWcnznRequest> wxnxxList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tsswDataDTO.getSqrSwList())) {
            for (BdcSlSqrSwDTO sqrSwDTO : tsswDataDTO.getSqrSwList()) {
                YrbZlfJyfxxRequest jyfxxRequest = new YrbZlfJyfxxRequest();
                dozerBeanMapper.map(sqrSwDTO, jyfxxRequest, "zlf_jyfxx");
                if(StringUtils.isBlank(jyfxxRequest.getDz())){
                    String dzDzz = getDsfZdDzxx("BDC_ZLF_DZ", "SW", "DEFAULT");
                    if(StringUtils.isNotBlank(dzDzz)){
                        jyfxxRequest.setDz(dzDzz);
                    }
                }
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(jyfxxRequest);

                YrbZlfHyxxRequest hyxxRequest = new YrbZlfHyxxRequest();
                dozerBeanMapper.map(sqrSwDTO, hyxxRequest, "zlf_hyxx");
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(hyxxRequest);

                YrbZlfWcnznRequest wcnznRequest = new YrbZlfWcnznRequest();
                dozerBeanMapper.map(sqrSwDTO, wcnznRequest, "zlf_wcnjzxx");
                if (CollectionUtils.isNotEmpty(sqrSwDTO.getBdcSlJtcyDOList())) {
                    for (BdcSlJtcyDO jtcyDO : sqrSwDTO.getBdcSlJtcyDOList()) {
                        dozerBeanMapper.map(jtcyDO, wcnznRequest, "zlf_wcnxx");
                        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(wcnznRequest);

                        wxnxxList.add(wcnznRequest);
                    }
                } else {
                    wcnznRequest.setCzwcnbj("2");
                }
                wxnxxList.add(wcnznRequest);
                jyfxxList.add(jyfxxRequest);
                hyxxList.add(hyxxRequest);
            }
        } else {
            return BdcCommonResponse.fail("???????????????????????????????????????!" + tsswDataDTO.getXmid());
        }

        YkqZlfRwjshouseRequest rwjshouseRequest = new YkqZlfRwjshouseRequest();
        rwjshouseRequest.setHousevo(Arrays.asList(houseRequest));
        rwjshouseRequest.setJyfxx(jyfxxList);
        rwjshouseRequest.setHyxx(hyxxList);
        rwjshouseRequest.setWcnznxx(wxnxxList);
        rwjshouseRequest.setFyjbxx(Arrays.asList(zlfFyjbxxRequest));
        rwjshouseRequest.setFcjycjxx(Arrays.asList(fcjyxxRequest));
        rwjshouseRequest.setQt(Arrays.asList(qtRequest));

        YkqZlfTsxxTaxbizml ykqZlfTsxxTaxbizml = new YkqZlfTsxxTaxbizml();
        List<YkqZlfRwjshouseRequest> rwjshouselist = new ArrayList<>(1);
        rwjshouselist.add(rwjshouseRequest);
        ykqZlfTsxxTaxbizml.setRwjshouselist(rwjshouselist);

        LOGGER.info("???????????????????????????????????????????????????{}", JSON.toJSONString(ykqZlfTsxxTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_zlfrwts_http", this.setRequestParam(ykqZlfTsxxTaxbizml));
        LOGGER.info("???????????????????????????????????????{}", response.toString());
        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                List<YrbFhmResponse> yrbFhmResponseList = JSONObject.parseArray(data.getString("rwjshouselist"), YrbFhmResponse.class);
                if (CollectionUtils.isNotEmpty(yrbFhmResponseList)) {
                    YrbFhmResponse yrbFhmResponse = yrbFhmResponseList.get(0);
                    if (Objects.equals("0", yrbFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbFhmResponse);
                    } else {
                        return BdcCommonResponse.fail(yrbFhmResponse.getFhxx(), yrbFhmResponse);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("??????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * ????????????????????????A002?????????????????????
     *
     * @param tsswDataDTO ???????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFhmResponse> clfrwTs(TsswDataDTO tsswDataDTO) {
        if (Objects.isNull(tsswDataDTO)) {
            throw new MissingArgumentException("?????????????????????????????????????????????");
        }
        //????????????????????????
        YrbZlfHouseRequest houseRequest = new YrbZlfHouseRequest();
        dozerBeanMapper.map(tsswDataDTO, houseRequest, "zlf_house");
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(houseRequest);

        YrbZlfQtRequest qtRequest = new YrbZlfQtRequest();
        qtRequest.setSffgyrdp("Y");
        YrbClfFcjyxxRequest fcjyxxRequest = new YrbClfFcjyxxRequest();
        dozerBeanMapper.map(tsswDataDTO, fcjyxxRequest, "clf_jyxx");
        if (Objects.nonNull(tsswDataDTO.getBdcxmfb())) {
            dozerBeanMapper.map(tsswDataDTO.getBdcxmfb(), fcjyxxRequest, "clf_xm_jyxx");
        }
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(fcjyxxRequest);
        YrbClfFyjbxxRequest zlfFyjbxxRequest = new YrbClfFyjbxxRequest();
        dozerBeanMapper.map(tsswDataDTO, zlfFyjbxxRequest, "clf_jbxx");
        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(zlfFyjbxxRequest);

        List<YrbZlfJyfxxRequest> jyfxxList = new ArrayList<>();
        List<YrbZlfHyxxRequest> hyxxList = new ArrayList<>();
        List<YrbZlfWcnznRequest> wxnxxList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tsswDataDTO.getSqrSwList())) {
            for (BdcSlSqrSwDTO sqrSwDTO : tsswDataDTO.getSqrSwList()) {
                YrbZlfJyfxxRequest jyfxxRequest = new YrbZlfJyfxxRequest();
                dozerBeanMapper.map(sqrSwDTO, jyfxxRequest, "zlf_jyfxx");
                if(StringUtils.isBlank(jyfxxRequest.getDz())){
                    String dzDzz = getDsfZdDzxx("BDC_ZLF_DZ", "SW", "DEFAULT");
                    if(StringUtils.isNotBlank(dzDzz)){
                        jyfxxRequest.setDz(dzDzz);
                    }
                }
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(jyfxxRequest);

                YrbZlfHyxxRequest hyxxRequest = new YrbZlfHyxxRequest();
                dozerBeanMapper.map(sqrSwDTO, hyxxRequest, "zlf_hyxx");
                ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(hyxxRequest);

                YrbZlfWcnznRequest wcnznRequest = new YrbZlfWcnznRequest();
                dozerBeanMapper.map(sqrSwDTO, wcnznRequest, "zlf_wcnjzxx");
                if (CollectionUtils.isNotEmpty(sqrSwDTO.getBdcSlJtcyDOList())) {
                    for (BdcSlJtcyDO jtcyDO : sqrSwDTO.getBdcSlJtcyDOList()) {
                        dozerBeanMapper.map(jtcyDO, wcnznRequest, "zlf_wcnxx");
                        ClassHandleUtil.headModelSetDefaultValueToNullFieldXmlElemen(wcnznRequest);

                        wxnxxList.add(wcnznRequest);
                    }
                } else {
                    wcnznRequest.setCzwcnbj("2");
                }
                if (CommonConstantUtils.QLRLB_YWR.equals(sqrSwDTO.getSqrlb())) {
                    BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                    bdcZdDsfzdgxDO.setZdbs("SW_TSSWXX_GYFS");
                    bdcZdDsfzdgxDO.setBdczdz(String.valueOf(sqrSwDTO.getGyfs()));
                    bdcZdDsfzdgxDO.setDsfxtbs("SW");
                    BdcZdDsfzdgxDO result = zdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                    if (result != null && StringUtils.isNotBlank(result.getBdczdz())) {
                        fcjyxxRequest.setZrfgyfs_dm(result.getDsfzdz());
                    }
                }
                wxnxxList.add(wcnznRequest);
                jyfxxList.add(jyfxxRequest);
                hyxxList.add(hyxxRequest);
            }
        } else {
            return BdcCommonResponse.fail("???????????????????????????????????????!" + tsswDataDTO.getXmid());
        }

        YkqClfRwjshouseRequest rwjshouseRequest = new YkqClfRwjshouseRequest();
        rwjshouseRequest.setHousevo(Arrays.asList(houseRequest));
        rwjshouseRequest.setJyfxx(jyfxxList);
        rwjshouseRequest.setHyxx(hyxxList);
        rwjshouseRequest.setWcnznxx(wxnxxList);
        rwjshouseRequest.setFyjbxx(Arrays.asList(zlfFyjbxxRequest));
        rwjshouseRequest.setFcjycjxx(Arrays.asList(fcjyxxRequest));
        rwjshouseRequest.setQt(Arrays.asList(qtRequest));

        YkqClfTsxxTaxbizml ykqClfTsxxTaxbizml = new YkqClfTsxxTaxbizml();
        List<YkqClfRwjshouseRequest> rwjshouselist = new ArrayList<>(1);
        rwjshouselist.add(rwjshouseRequest);
        ykqClfTsxxTaxbizml.setRwjshouselist(rwjshouselist);

        LOGGER.info("???????????????????????????????????????????????????{}", JSON.toJSONString(ykqClfTsxxTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_clfrwts_http", this.setRequestParam(ykqClfTsxxTaxbizml));
        LOGGER.info("???????????????????????????????????????{}", response.toString());
        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                List<YrbFhmResponse> yrbFhmResponseList = JSONObject.parseArray(data.getString("rwjshouselist"), YrbFhmResponse.class);
                if (CollectionUtils.isNotEmpty(yrbFhmResponseList)) {
                    YrbFhmResponse yrbFhmResponse = yrbFhmResponseList.get(0);
                    if (Objects.equals("0", yrbFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbFhmResponse);
                    } else {
                        return BdcCommonResponse.fail(yrbFhmResponse.getFhxx(), yrbFhmResponse);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("??????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 1.3	?????????????????????A003???
     *
     * @param yrbRwztTaxbizml ??????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbRwztDTO> tsRwzt(YrbRwztTaxbizml yrbRwztTaxbizml) {
        if (Objects.isNull(yrbRwztTaxbizml)) {
            throw new MissingArgumentException("???????????????????????????????????????");
        }
        String rwzt = "";
        if (StringUtils.isNotBlank(yrbRwztTaxbizml.getRwjshouselist().getRWZT())) {
            rwzt = yrbRwztTaxbizml.getRwjshouselist().getRWZT();
        }
        String rwzt_dsf = getDsfZdDzxx("DSF_ZD_RWZT", "SW", rwzt);
        yrbRwztTaxbizml.getRwjshouselist().setRWZT(rwzt_dsf);
        // ????????????????????????
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbRwztTaxbizml.getRwjshouselist().getSJGSDQ())) {
            sjgsdq = yrbRwztTaxbizml.getRwjshouselist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbRwztTaxbizml.getRwjshouselist().setSJGSDQ(sjgsdq_dsf);

        // ????????????????????????????????????
        YkqRwztTaxbizml ykqRwztTaxbizml = new YkqRwztTaxbizml();
        List<YrbRwztRequestDTO> yrbRwztRequestDTOList = new ArrayList<>(1);
        if (Objects.nonNull(yrbRwztTaxbizml.getRwjshouselist())) {
            yrbRwztRequestDTOList.add(yrbRwztTaxbizml.getRwjshouselist());
        }
        ykqRwztTaxbizml.setRwjshouselist(yrbRwztRequestDTOList);

        LOGGER.info("??????????????????????????????????????????????????????{}", JSON.toJSONString(ykqRwztTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_rwzt_http", this.setRequestParam(ykqRwztTaxbizml));
        LOGGER.info("????????????????????????????????????{}", response.toString());
        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                List<YrbFhmResponse> yrbFhmResponseList = JSONObject.parseArray(data.getString("rwjshouselist"), YrbFhmResponse.class);
                if (CollectionUtils.isNotEmpty(yrbFhmResponseList)) {
                    YrbFhmResponse yrbFhmResponse = yrbFhmResponseList.get(0);
                    YrbRwztDTO yrbRwztDTO = new YrbRwztDTO();
                    yrbRwztDTO.setRwjshouselist(yrbFhmResponse);
                    if (Objects.equals("0", yrbFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbRwztDTO);
                    } else {
                        return BdcCommonResponse.fail(yrbFhmResponse.getFhxx(), yrbRwztDTO);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("???????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 1.4	??????????????????????????????A004???
     *
     * @param yrbZlfskxxTaxbizml ???????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbZlfjsxxDTO> zlfSkxxhq(YrbZlfskxxTaxbizml yrbZlfskxxTaxbizml) {
        if (Objects.isNull(yrbZlfskxxTaxbizml)) {
            throw new MissingArgumentException("???????????????????????????????????????");
        }
        // ????????????????????????
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbZlfskxxTaxbizml.getZlfskxxhqlist().getSJGSDQ())) {
            sjgsdq = yrbZlfskxxTaxbizml.getZlfskxxhqlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbZlfskxxTaxbizml.getZlfskxxhqlist().setSJGSDQ(sjgsdq_dsf);

        // ????????????????????????????????????
        YkqZlfskxxTaxbizml ykqZlfskxxTaxbizml = new YkqZlfskxxTaxbizml();
        List<YrbZlfskxxRequestDTO> yrbZlfskxxRequestDTOList = new ArrayList<>(1);
        if (Objects.nonNull(yrbZlfskxxTaxbizml.getZlfskxxhqlist())) {
            yrbZlfskxxRequestDTOList.add(yrbZlfskxxTaxbizml.getZlfskxxhqlist());
        }
        ykqZlfskxxTaxbizml.setZlfskxxhqlist(yrbZlfskxxRequestDTOList);
        LOGGER.info("?????????????????????????????????????????????????????????{}", JSON.toJSONString(ykqZlfskxxTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_zlfskxxhq_http", this.setRequestParam(ykqZlfskxxTaxbizml));
        LOGGER.info("?????????????????????????????????????????????{}", response.toString());

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                List<YrbFhmResponse> yrbFhmResponseList = JSONObject.parseArray(data.getString("rwjshouselist"), YrbFhmResponse.class);
                List<YrbZlfskxxResponse> yrbZlfskxxResponseList = JSONObject.parseArray(data.getString("zlfskxxhqlist"), YrbZlfskxxResponse.class);
                if (CollectionUtils.isNotEmpty(yrbFhmResponseList)) {
                    YrbFhmResponse yrbFhmResponse = yrbFhmResponseList.get(0);
                    YrbZlfjsxxDTO yrbZlfjsxxDTO = new YrbZlfjsxxDTO();
                    yrbZlfjsxxDTO.setZlfjsjglist(yrbFhmResponse);
                    yrbZlfjsxxDTO.setZlfskxxhqlist(yrbZlfskxxResponseList);
                    if (Objects.equals("0", yrbFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbZlfjsxxDTO);
                    } else {
                        return BdcCommonResponse.fail(yrbFhmResponse.getFhxx(), yrbZlfjsxxDTO);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("????????????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 1.5	??????????????????????????????A005???
     *
     * @param yrbClfskxxhqTaxbizml ???????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbClfskxxhqDTO> clfSkxxhq(YrbClfskxxhqTaxbizml yrbClfskxxhqTaxbizml) {
        if (Objects.isNull(yrbClfskxxhqTaxbizml)) {
            throw new MissingArgumentException("?????????????????????????????????????????????");
        }
        // ????????????????????????
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbClfskxxhqTaxbizml.getClfskxxhqlist().getSJGSDQ())) {
            sjgsdq = yrbClfskxxhqTaxbizml.getClfskxxhqlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbClfskxxhqTaxbizml.getClfskxxhqlist().setSJGSDQ(sjgsdq_dsf);
        // ????????????????????????????????????
        YkqClfskxxhqTaxbizml ykqClfskxxhqTaxbizml = new YkqClfskxxhqTaxbizml();
        List<YrbClfskxxhqRequestDTO> yrbClfskxxhqRequestDTOList = new ArrayList<>(1);
        if (Objects.nonNull(yrbClfskxxhqTaxbizml.getClfskxxhqlist())) {
            yrbClfskxxhqRequestDTOList.add(yrbClfskxxhqTaxbizml.getClfskxxhqlist());
        }
        ykqClfskxxhqTaxbizml.setClfskxxhqlist(yrbClfskxxhqRequestDTOList);

        LOGGER.info("?????????????????????????????????????????????????????????{}", JSON.toJSONString(ykqClfskxxhqTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_clfskxxhq_http", this.setRequestParam(ykqClfskxxhqTaxbizml));
        LOGGER.info("?????????????????????????????????????????????{}", response.toString());

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                List<YrbClfskxxFhmResponse> yrbFhmResponseList = JSONObject.parseArray(data.getString("clffjsjglist"), YrbClfskxxFhmResponse.class);
                List<ClfskxxhqResponse> yrbZlfskxxResponseList = JSONObject.parseArray(data.getString("clfskxxhqlist"), ClfskxxhqResponse.class);
                if (CollectionUtils.isNotEmpty(yrbFhmResponseList)) {
                    YrbClfskxxFhmResponse yrbClfskxxFhmResponse = yrbFhmResponseList.get(0);
                    YrbClfskxxhqDTO yrbClfskxxhqDTO = new YrbClfskxxhqDTO();
                    yrbClfskxxhqDTO.setClfjsjglist(yrbClfskxxFhmResponse);
                    yrbClfskxxhqDTO.setClfskxxhqlist(yrbZlfskxxResponseList);
                    if (Objects.equals("0", yrbClfskxxFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbClfskxxhqDTO);
                    } else {
                        return BdcCommonResponse.fail(yrbClfskxxFhmResponse.getFhxx(), yrbClfskxxhqDTO);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("????????????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 1.6.	???????????????????????????A006???
     *
     * @param yrbYxzlTaxbizml ??????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbYxzlResponseDTO> yxzlxxjs(YrbYxzlTaxbizml yrbYxzlTaxbizml) {
        if (Objects.isNull(yrbYxzlTaxbizml)) {
            throw new MissingArgumentException("????????????????????????????????????");
        }
        // ????????????????????????
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbYxzlTaxbizml.getYrbYxzlxxjsList().getSJGSDQ())) {
            sjgsdq = yrbYxzlTaxbizml.getYrbYxzlxxjsList().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbYxzlTaxbizml.getYrbYxzlxxjsList().setSJGSDQ(sjgsdq_dsf);
        // ????????????????????????????????????
        YkqYxzlTaxbizml ykqYxzlTaxbizml = new YkqYxzlTaxbizml();
        List<YkqYxzlxxjsList> ykqYxzlxxjsLists = new ArrayList<>(1);
        if (Objects.nonNull(yrbYxzlTaxbizml.getYrbYxzlxxjsList())) {
            YkqYxzlxxjsList ykqYxzlxxjsList = new YkqYxzlxxjsList();
            BeanUtils.copyProperties(yrbYxzlTaxbizml.getYrbYxzlxxjsList(), ykqYxzlxxjsList);
            ykqYxzlxxjsList.setYxwjxx(yrbYxzlTaxbizml.getYrbYxzlxxjsList().getYwwjxxList());
            ykqYxzlxxjsLists.add(ykqYxzlxxjsList);
        }
        ykqYxzlTaxbizml.setYxzlxxjsList(ykqYxzlxxjsLists);

        LOGGER.info("??????????????????????????????????????????????????????{}", JSON.toJSONString(ykqYxzlTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_yxzlxxjs_http", this.setRequestParam(ykqYxzlTaxbizml));
        LOGGER.info("?????????????????????????????????????????????{}", response.toString());

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                List<YrbFhmResponse> yrbFhmResponseList = JSONObject.parseArray(data.getString("rwjshouselist"), YrbFhmResponse.class);
                if (CollectionUtils.isNotEmpty(yrbFhmResponseList)) {
                    YrbFhmResponse yrbFhmResponse = yrbFhmResponseList.get(0);
                    YrbYxzlResponseDTO yrbYxzlResponseDTO = new YrbYxzlResponseDTO();
                    yrbYxzlResponseDTO.setYxzlxxjsList(yrbFhmResponse);
                    if (Objects.equals("0", yrbFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbYxzlResponseDTO);
                    } else {
                        return BdcCommonResponse.fail(yrbFhmResponse.getFhxx(), yrbYxzlResponseDTO);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("?????????????????????????????????????????????????????????????????????????????????");
    }


    /**
     * 1.9.	???????????????????????????A009???
     *
     * @param yrbQswsxxhqTzxbizml ????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbQswsxxhqDTO> qsWsxxhq(YrbQswsxxhqTzxbizml yrbQswsxxhqTzxbizml) {
        if (Objects.isNull(yrbQswsxxhqTzxbizml)) {
            throw new MissingArgumentException("??????????????????????????????????????????");
        }

        // ????????????????????????
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbQswsxxhqTzxbizml.getFcjyskxxlist().getSJGSDQ())) {
            sjgsdq = yrbQswsxxhqTzxbizml.getFcjyskxxlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbQswsxxhqTzxbizml.getFcjyskxxlist().setSJGSDQ(sjgsdq_dsf);
        // ????????????????????????????????????
        YkqQswsxxhqTzxbizml ykqQswsxxhqTzxbizml = new YkqQswsxxhqTzxbizml();
        List<YrbQswsxxhqRequest> yrbQswsxxhqRequests = new ArrayList<>(1);
        if (Objects.nonNull(yrbQswsxxhqTzxbizml.getFcjyskxxlist())) {
            yrbQswsxxhqRequests.add(yrbQswsxxhqTzxbizml.getFcjyskxxlist());
        }
        ykqQswsxxhqTzxbizml.setFcjyskxxlist(yrbQswsxxhqRequests);

        LOGGER.info("??????????????????????????????????????????????????????{}", JSON.toJSONString(ykqQswsxxhqTzxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_qswsxxhq_http", this.setRequestParam(ykqQswsxxhqTzxbizml));
        LOGGER.info("??????????????????????????????????????????{}", response.toString());

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                List<YrbFhmResponse> yrbFhmResponseList = JSONObject.parseArray(data.getString("fcjyskxxlist"), YrbFhmResponse.class);
                List<YrbFcjysbxxFjxxResponse> yrbFcjysbxxFjxxResponseList = JSONObject.parseArray(data.getString("qswsfjxxlist"), YrbFcjysbxxFjxxResponse.class);
                if (CollectionUtils.isNotEmpty(yrbFhmResponseList)) {
                    YrbFhmResponse yrbFhmResponse = yrbFhmResponseList.get(0);
                    YrbQswsxxhqDTO yrbQswsxxhqDTO = new YrbQswsxxhqDTO();
                    yrbQswsxxhqDTO.setFcjyskxxlist(yrbFhmResponse);
                    yrbQswsxxhqDTO.setQswsfjxxlist(yrbFcjysbxxFjxxResponseList);
                    if (Objects.equals("0", yrbFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbQswsxxhqDTO);
                    } else {
                        return BdcCommonResponse.fail(yrbFhmResponse.getFhxx(), yrbQswsxxhqDTO);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("?????????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 2.10.	?????????????????????A010???
     *
     * @param yrbSbztcxTaxbizml ??????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbSbztcxDTO> sbztCx(YrbSbztcxTaxbizml yrbSbztcxTaxbizml) {
        if (Objects.isNull(yrbSbztcxTaxbizml)) {
            throw new MissingArgumentException("??????????????????????????????????????????");
        }

        // ????????????????????????
        if (CollectionUtils.isNotEmpty(yrbSbztcxTaxbizml.getSbztxxlist())) {
            for (YrbSbztcxRequestDTO yrbSbztcxRequestDTO : yrbSbztcxTaxbizml.getSbztxxlist()) {
                String sjgsdq = "";
                if (StringUtils.isNotBlank(yrbSbztcxRequestDTO.getSJGSDQ())) {
                    sjgsdq = yrbSbztcxRequestDTO.getSJGSDQ();
                }
                String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
                yrbSbztcxRequestDTO.setSJGSDQ(sjgsdq_dsf);
            }
        }
        LOGGER.info("???????????????????????????{}", JSON.toJSONString(yrbSbztcxTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_sbztcx_http", JSONObject.toJSON(this.setRequestParam(yrbSbztcxTaxbizml)));
        LOGGER.info("??????????????????????????????????????????{}", response.toString());
        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            JSONObject head = (JSONObject) res.get("head");
            if (!data.isEmpty()) {
                YrbSbztcxDTO yrbSbztcxDTO = JSONObject.parseObject(JSONObject.toJSONString(data), YrbSbztcxDTO.class);
                if (Objects.nonNull(yrbSbztcxDTO)) {
                    return BdcCommonResponse.ok(yrbSbztcxDTO);
                } else {
                    return BdcCommonResponse.fail(String.valueOf(res.get("msg")));
                }
            }
        }


        return BdcCommonResponse.fail("???????????????,?????????????????????????????????");
    }

    /**
     * 1.14.	?????????????????????????????????A014???
     *
     * @param yrbFcjysbxxTaxbizml ??????????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFcjysbxxDTO> fcjySbxx(YrbFcjysbxxTaxbizml yrbFcjysbxxTaxbizml) {
        if (Objects.isNull(yrbFcjysbxxTaxbizml)) {
            throw new MissingArgumentException("??????????????????????????????????????????");
        }

        // ????????????????????????
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbFcjysbxxTaxbizml.getFcjysbxxlist().getSJGSDQ())) {
            sjgsdq = yrbFcjysbxxTaxbizml.getFcjysbxxlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbFcjysbxxTaxbizml.getFcjysbxxlist().setSJGSDQ(sjgsdq_dsf);
        // ????????????????????????????????????
        YkqFcjysbxxTaxbizml ykqFcjysbxxTaxbizml = new YkqFcjysbxxTaxbizml();
        List<YrbFcjysbxxRequestDTO> yrbFcjysbxxRequestDTOList = new ArrayList<>(1);
        if (Objects.nonNull(yrbFcjysbxxTaxbizml.getFcjysbxxlist())) {
            yrbFcjysbxxRequestDTOList.add(yrbFcjysbxxTaxbizml.getFcjysbxxlist());
        }
        ykqFcjysbxxTaxbizml.setFcjysbxxlist(yrbFcjysbxxRequestDTOList);

        LOGGER.info("????????????????????????????????????????????????????????????{}", JSON.toJSONString(ykqFcjysbxxTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_fcjysbxx_http", this.setRequestParam(ykqFcjysbxxTaxbizml));
        LOGGER.info("????????????????????????????????????????????????{}", response.toString());

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                List<YrbFhmResponse> yrbFhmResponseList = JSONObject.parseArray(data.getString("fcjysbxxlist"), YrbFhmResponse.class);
                List<YrbFcjysbxxFjxxResponse> yrbFcjysbxxFjxxResponseList = JSONObject.parseArray(data.getString("fcjysbfjxxlist"), YrbFcjysbxxFjxxResponse.class);
                if (CollectionUtils.isNotEmpty(yrbFhmResponseList)) {
                    YrbFhmResponse yrbFhmResponse = yrbFhmResponseList.get(0);
                    YrbFcjysbxxDTO yrbFcjysbxxDTO = new YrbFcjysbxxDTO();
                    yrbFcjysbxxDTO.setFcjysbxxlist(yrbFhmResponse);
                    yrbFcjysbxxDTO.setFcjysbfjxxlist(yrbFcjysbxxFjxxResponseList);
                    if (Objects.equals("0", yrbFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbFcjysbxxDTO);
                    } else {
                        return BdcCommonResponse.fail(yrbFhmResponse.getFhxx(), yrbFcjysbxxDTO);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("???????????????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 1.15.	?????????????????????????????????A015???
     *
     * @param yrbFcjysbxxQrTaxbizml ??????????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFcjysbqrResponse> fcjySbxxqr(YrbFcjysbxxQrTaxbizml yrbFcjysbxxQrTaxbizml) {
        if (Objects.isNull(yrbFcjysbxxQrTaxbizml)) {
            throw new MissingArgumentException("??????????????????????????????????????????");
        }
        // ????????????????????????
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbFcjysbxxQrTaxbizml.getFcjysbqrxxlist().getSJGSDQ())) {
            sjgsdq = yrbFcjysbxxQrTaxbizml.getFcjysbqrxxlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbFcjysbxxQrTaxbizml.getFcjysbqrxxlist().setSJGSDQ(sjgsdq_dsf);
        // ????????????????????????????????????
        YkqFcjysbxxQrTaxbizml ykqFcjysbxxQrTaxbizml = new YkqFcjysbxxQrTaxbizml();
        List<YrbFcjysbxxQrRequestDTO> yrbFcjysbxxQrRequestDTOList = new ArrayList<>(1);
        if (Objects.nonNull(yrbFcjysbxxQrTaxbizml.getFcjysbqrxxlist())) {
            yrbFcjysbxxQrRequestDTOList.add(yrbFcjysbxxQrTaxbizml.getFcjysbqrxxlist());
        }
        ykqFcjysbxxQrTaxbizml.setFcjysbqrxxlist(yrbFcjysbxxQrRequestDTOList);
        if (CollectionUtils.isNotEmpty(yrbFcjysbxxQrTaxbizml.getFcjysbqrfjxxlist())) {
            ykqFcjysbxxQrTaxbizml.setFcjysbqrfjxxlist(yrbFcjysbxxQrTaxbizml.getFcjysbqrfjxxlist());
        }

        LOGGER.info("????????????????????????????????????????????????????????????{}", JSON.toJSONString(ykqFcjysbxxQrTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_fcjysbxxqr_http", this.setRequestParam(ykqFcjysbxxQrTaxbizml));
        LOGGER.info("????????????????????????????????????????????????{}", response.toString());

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                List<YrbFhmResponse> yrbFhmResponseList = JSONObject.parseArray(data.getString("fcjysbqrxxlist"), YrbFhmResponse.class);
                if (CollectionUtils.isNotEmpty(yrbFhmResponseList)) {
                    YrbFhmResponse yrbFhmResponse = yrbFhmResponseList.get(0);
                    YrbFcjysbqrResponse yrbFcjysbqrResponse = new YrbFcjysbqrResponse();
                    yrbFcjysbqrResponse.setFcjysbqrxxlist(yrbFhmResponse);
                    if (Objects.equals("0", yrbFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbFcjysbqrResponse);
                    } else {
                        return BdcCommonResponse.fail(yrbFhmResponse.getFhxx(), yrbFcjysbqrResponse);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("???????????????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 1.11.	??????????????????????????????A017???
     *
     * @param yrbFcjyyjkTaxbizml ???????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFcjyyjkDTO> queryFcjyyjkList(YrbFcjyyjkTaxbizml yrbFcjyyjkTaxbizml) {
        if (Objects.isNull(yrbFcjyyjkTaxbizml)) {
            throw new MissingArgumentException("?????????????????????????????????????????????");
        }
        // ????????????????????????
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbFcjyyjkTaxbizml.getFcjyyjskList().getSJGSDQ())) {
            sjgsdq = yrbFcjyyjkTaxbizml.getFcjyyjskList().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbFcjyyjkTaxbizml.getFcjyyjskList().setSJGSDQ(sjgsdq_dsf);
        // ??????????????????????????????????????????+?????????????????????
        YrbFcjyyjkRequestDTO yrbFcjyyjkRequestDTO = yrbFcjyyjkTaxbizml.getFcjyyjskList();
        yrbFcjyyjkTaxbizml.getFcjyyjskList().setSJBH(yrbFcjyyjkRequestDTO.getSJGSDQ() + yrbFcjyyjkRequestDTO.getSJBH());

        // ????????????????????????????????????
        YkqFcjyyjkTaxbizml ykqFcjyyjkTaxbizml = new YkqFcjyyjkTaxbizml();
        List<YrbFcjyyjkRequestDTO> yrbFcjyyjkRequestDTOList = new ArrayList<>(1);
        if (Objects.nonNull(yrbFcjyyjkTaxbizml.getFcjyyjskList())) {
            yrbFcjyyjkRequestDTOList.add(yrbFcjyyjkTaxbizml.getFcjyyjskList());
        }
        ykqFcjyyjkTaxbizml.setFcjyyjsklist(yrbFcjyyjkRequestDTOList);

        LOGGER.info("?????????????????????????????????????????????????????????{}", JSON.toJSONString(ykqFcjyyjkTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_queryFcjyyjkList_http", this.setRequestParam(ykqFcjyyjkTaxbizml));
        LOGGER.info("?????????????????????????????????????????????{}", response.toString());

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                YrbFhmResponse yrbFhmResponse = JSON.parseObject(data.getString("fcjyyjkjg"), YrbFhmResponse.class);
                if (Objects.nonNull(yrbFhmResponse)) {
                    YkqFcjyyjkDTO ykqFcjyyjkDTO = new YkqFcjyyjkDTO();
                    ykqFcjyyjkDTO.setYrbFhmResponse(yrbFhmResponse);
                    YkqFcjyyjsksj ykqFcjyyjsksj = JSON.parseObject(data.getString("fcjyyjsksj"), YkqFcjyyjsksj.class);
                    ykqFcjyyjkDTO.setYkqFcjyyjsksj(ykqFcjyyjsksj);

                    YrbFcjyyjkDTO yrbFcjyyjkResponse = new YrbFcjyyjkDTO();
                    yrbFcjyyjkResponse.setFcjyyjsklist(ykqFcjyyjsksj.getFcjyyjsklist());
                    if (Objects.equals("0", yrbFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbFcjyyjkResponse);
                    } else {
                        return BdcCommonResponse.fail(yrbFhmResponse.getFhxx(), yrbFcjyyjkResponse);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("????????????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 1.12.	????????????????????????????????????A018???
     *
     * @param yrbFcjyEwmTaxbizml ?????????????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbFcjyEwmDTO> getFcjyewmJkxx(YrbFcjyEwmTaxbizml yrbFcjyEwmTaxbizml) {
        if (Objects.isNull(yrbFcjyEwmTaxbizml)) {
            throw new MissingArgumentException("???????????????????????????????????????????????????");
        }
        // ????????????????????????
        if (CollectionUtils.isNotEmpty(yrbFcjyEwmTaxbizml.getFcjyewmlist())) {
            for (YrbFcjyEwmRequest yrbFcjyEwmRequest : yrbFcjyEwmTaxbizml.getFcjyewmlist()) {
                String sjgsdq = "";
                if (StringUtils.isNotBlank(yrbFcjyEwmRequest.getSJGSDQ())) {
                    sjgsdq = yrbFcjyEwmRequest.getSJGSDQ();
                }
                String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
                yrbFcjyEwmRequest.setSJGSDQ(sjgsdq_dsf);
            }
        }
        // ????????????????????????????????????
        YkqFcjyEwmTaxbizml ykqFcjyEwmTaxbizml = new YkqFcjyEwmTaxbizml();
        if (CollectionUtils.isNotEmpty(yrbFcjyEwmTaxbizml.getFcjyewmlist())) {
            ykqFcjyEwmTaxbizml.setFcjyewmlist(yrbFcjyEwmTaxbizml.getFcjyewmlist().get(0));
        }

        LOGGER.info("???????????????????????????????????????????????????????????????{}", JSON.toJSONString(ykqFcjyEwmTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_getFcjyewmJkxx_http", this.setRequestParam(ykqFcjyEwmTaxbizml));
        LOGGER.info("???????????????????????????????????????????????????{}", response.toString());

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                YrbFhmResponse yrbFhmResponse = JSON.parseObject(data.getString("fcjyjkermjg"), YrbFhmResponse.class);
                if (Objects.nonNull(yrbFhmResponse)) {
                    YrbFcjyEwmDTO yrbFcjyEwmDTO = new YrbFcjyEwmDTO();
                    yrbFcjyEwmDTO.setFcjyjkermjg(yrbFhmResponse);

                    YrbFcjyEwmResponse kkewm = JSON.parseObject(data.getString("kkewm"), YrbFcjyEwmResponse.class);
                    yrbFcjyEwmDTO.setKkewm(kkewm);

                    if (Objects.equals("0", yrbFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbFcjyEwmDTO);
                    } else {
                        return BdcCommonResponse.fail(yrbFhmResponse.getFhxx(), yrbFcjyEwmDTO);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("??????????????????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 1.19.	?????????????????????????????????A019???
     *
     * @param yrbEwmjkxxTaxbizml ??????????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbEwmjkxxDTO> queryEwmjkxx(YrbEwmjkxxTaxbizml yrbEwmjkxxTaxbizml) {
        if (Objects.isNull(yrbEwmjkxxTaxbizml)) {
            throw new MissingArgumentException("????????????????????????????????????????????????");
        }
        // ????????????????????????
        if (CollectionUtils.isNotEmpty(yrbEwmjkxxTaxbizml.getFcjyzfquerylist())) {
            for (YrbEwmjkxxRequest yrbEwmjkxxRequest : yrbEwmjkxxTaxbizml.getFcjyzfquerylist()) {
                String sjgsdq = "";
                if (StringUtils.isNotBlank(yrbEwmjkxxRequest.getSJGSDQ())) {
                    sjgsdq = yrbEwmjkxxRequest.getSJGSDQ();
                }
                String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
                yrbEwmjkxxRequest.setSJGSDQ(sjgsdq_dsf);
            }
        }

        LOGGER.info("????????????????????????????????????????????????????????????{}", JSON.toJSONString(yrbEwmjkxxTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_queryewmjkxx_http", this.setRequestParam(yrbEwmjkxxTaxbizml));
        LOGGER.info("????????????????????????????????????????????????{}", response.toString());

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                YrbFhmResponse yrbFhmResponse = JSON.parseObject(data.getString("fcjyjkcxjg"), YrbFhmResponse.class);
                if (Objects.nonNull(yrbFhmResponse)) {
                    YrbEwmjkxxDTO yrbEwmjkxxDTO = new YrbEwmjkxxDTO();
                    yrbEwmjkxxDTO.setFcjyjkcxjg(yrbFhmResponse);
                    YrbEwmjkxxResponse kkjg = JSON.parseObject(data.getString("kkjg"), YrbEwmjkxxResponse.class);
                    yrbEwmjkxxDTO.setKkjg(kkjg);
                    if (Objects.equals("0", yrbFhmResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbEwmjkxxDTO);
                    } else {
                        return BdcCommonResponse.fail(yrbFhmResponse.getFhxx(), yrbEwmjkxxDTO);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("???????????????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 2.20	 ???????????????????????????A020???
     *
     * @param yrbQswspzhqTaxbizml ????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbQswspzhqDTO> qswspzHq(YrbQswspzhqTaxbizml yrbQswspzhqTaxbizml) {
        if (Objects.isNull(yrbQswspzhqTaxbizml)) {
            throw new MissingArgumentException("??????????????????????????????????????????");
        }
        // ????????????????????????
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbQswspzhqTaxbizml.getFcjyqswspzlist().getSJGSDQ())) {
            sjgsdq = yrbQswspzhqTaxbizml.getFcjyqswspzlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbQswspzhqTaxbizml.getFcjyqswspzlist().setSJGSDQ(sjgsdq_dsf);
        // ????????????????????????????????????
        YkqQswspzhqTaxbizml ykqQswspzhqTaxbizml = new YkqQswspzhqTaxbizml();
        List<YrbQswspzhqRequest> yrbQswspzhqRequestList = new ArrayList<>(1);
        if (Objects.nonNull(yrbQswspzhqTaxbizml.getFcjyqswspzlist())) {
            yrbQswspzhqRequestList.add(yrbQswspzhqTaxbizml.getFcjyqswspzlist());
        }
        ykqQswspzhqTaxbizml.setFcjyqswspzlist(yrbQswspzhqRequestList);

        LOGGER.info("??????????????????????????????????????????????????????{}", JSON.toJSONString(ykqQswspzhqTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_qswspzhq_http", this.setRequestParam(ykqQswspzhqTaxbizml));
        LOGGER.info("??????????????????????????????????????????{}", response.toString());

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                List<YrbYrbQswspzhqResponse> yrbYrbQswspzhqResponseList = JSONObject.parseArray(data.getString("fcjyqswspzlist"), YrbYrbQswspzhqResponse.class);
                if (CollectionUtils.isNotEmpty(yrbYrbQswspzhqResponseList)) {
                    YrbYrbQswspzhqResponse yrbYrbQswspzhqResponse = yrbYrbQswspzhqResponseList.get(0);

                    YrbQswspzhqDTO yrbQswspzhqDTO = new YrbQswspzhqDTO();
                    yrbQswspzhqDTO.setFcjyqswspzlist(yrbYrbQswspzhqResponse);
                    yrbQswspzhqDTO.setQswsfjxxlist(yrbYrbQswspzhqResponse.getQswsfjxxlist());
                    if (Objects.equals("0", yrbYrbQswspzhqResponse.getFhm())) {
                        return BdcCommonResponse.ok(yrbQswspzhqDTO);
                    } else {
                        return BdcCommonResponse.fail(yrbYrbQswspzhqResponse.getFhxx(), yrbQswspzhqDTO);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("?????????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 1.21. ????????????????????? ???A021???
     *
     * @param yrbQslxdhqTaxbizml ?????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public BdcCommonResponse<YrbQslxdhqDTO> qslxdHq(YrbQslxdhqTaxbizml yrbQslxdhqTaxbizml) {
        if (Objects.isNull(yrbQslxdhqTaxbizml)) {
            throw new MissingArgumentException("??????????????????????????????????????????");
        }
        String sjgsdq = "";
        if (StringUtils.isNotBlank(yrbQslxdhqTaxbizml.getFcjyqslxdlist().getSJGSDQ())) {
            sjgsdq = yrbQslxdhqTaxbizml.getFcjyqslxdlist().getSJGSDQ();
        }
        String sjgsdq_dsf = getDsfZdDzxx("DSF_ZD_SJGSDQ", "SW", sjgsdq);
        yrbQslxdhqTaxbizml.getFcjyqslxdlist().setSJGSDQ(sjgsdq_dsf);

        // ????????????????????????????????????
        YkqQslxdhqTaxbizml ykqQslxdhqTaxbizml = new YkqQslxdhqTaxbizml();
        List<YrbQslxdhqRequest> yrbQslxdhqRequestList = new ArrayList<>(1);
        if (Objects.nonNull(yrbQslxdhqTaxbizml.getFcjyqslxdlist())) {
            yrbQslxdhqRequestList.add(yrbQslxdhqTaxbizml.getFcjyqslxdlist());
        }
        ykqQslxdhqTaxbizml.setFcjyqslxdlist(yrbQslxdhqRequestList);

        LOGGER.info("???????????????????????????????????????????????????{}", JSON.toJSONString(ykqQslxdhqTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_qslxdhq_http", this.setRequestParam(ykqQslxdhqTaxbizml));
        LOGGER.info("???????????????????????????????????????{}", response.toString());

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                List<YrbQslxdhqDTO> yrbQslxdhqDTOList = JSONObject.parseArray(data.getString("fcjyqslxdlist"), YrbQslxdhqDTO.class);
                if (CollectionUtils.isNotEmpty(yrbQslxdhqDTOList)) {
                    YrbQslxdhqDTO yrbQslxdhqDTO = yrbQslxdhqDTOList.get(0);
                    if (Objects.equals("0", yrbQslxdhqDTO.getFcjyqslxdlist().getFhm())) {
                        return BdcCommonResponse.ok(yrbQslxdhqDTO);
                    } else {
                        return BdcCommonResponse.fail(yrbQslxdhqDTO.getFcjyqslxdlist().getFhxx(),
                                yrbQslxdhqDTO);
                    }
                }
            }
        }
        return BdcCommonResponse.fail("??????????????????????????????????????????????????????????????????????????????");
    }

    /**
     * 1.22. ?????????????????????????????????????????? ???A022???
     *
     * @param fcjydjkxxhqTaxbizml ??????????????????????????????????????????
     * @return ???????????????
     * @Date 2022/8/8
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    public BdcCommonResponse<FcjydjkxxhqDTO> fcjydjkxxHq(FcjydjkxxhqTaxbizml fcjydjkxxhqTaxbizml) {
        if (Objects.isNull(fcjydjkxxhqTaxbizml)) {
            throw new MissingArgumentException("????????????????????????????????????????????????????????????");
        }
        // ????????????????????????????????????
        YkqFcjydjkxxhqTaxbizml ykqFcjydjkxxhqTaxbizml = new YkqFcjydjkxxhqTaxbizml();
        List<FcjydjkxxhqRequest> fcjydjkxxhqRequestList = new ArrayList<>(1);
        if (Objects.nonNull(fcjydjkxxhqTaxbizml.getFcjydjkxxhqRequest())) {
            fcjydjkxxhqRequestList.add(fcjydjkxxhqTaxbizml.getFcjydjkxxhqRequest());
        }
        ykqFcjydjkxxhqTaxbizml.setFcjydjkxxhqlist(fcjydjkxxhqRequestList);

        LOGGER.info("????????????????????????????????????????????????????????????????????????{}", JSON.toJSONString(ykqFcjydjkxxhqTaxbizml));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_sw_fcjydjkxxHq_http", this.setRequestParam(ykqFcjydjkxxhqTaxbizml));
        LOGGER.info("????????????????????????????????????????????????????????????{}", JSON.toJSONString(response));

        if (Objects.nonNull(response)) {
            JSONObject res = JSON.parseObject(JSON.toJSONString(response));
            JSONObject data = (JSONObject) res.get("data");
            if (!data.isEmpty()) {
                FcjydjkxxhqResponse fcjydjkxxhqResponse = JSON.parseObject(data.getString("jkxxgrid"), FcjydjkxxhqResponse.class);
                FcjydjkxxhqDTO fcjydjkxxhqDTO = new FcjydjkxxhqDTO();
                fcjydjkxxhqDTO.setJkxxgrid(fcjydjkxxhqResponse);
                if (Objects.equals("0", fcjydjkxxhqResponse.getFhm())) {
                    return BdcCommonResponse.ok(fcjydjkxxhqDTO);
                } else {
                    return BdcCommonResponse.fail(fcjydjkxxhqResponse.getFhxx(), fcjydjkxxhqDTO);
                }
            }
        }
        return BdcCommonResponse.fail("???????????????????????????????????????????????????????????????????????????????????????????????????");
    }

    private <T> YkqRequet setRequestParam(T data) {
        YkqRequet ykqRequet = new YkqRequet();
        YkqSwRequestHead head = new YkqSwRequestHead();
        head.setOrgid("");
        head.setRegionCode("");
        head.setCsdm(csdm);
        ykqRequet.setHead(head);
        ykqRequet.setData(data);
        return ykqRequet;
    }

    // ???????????????????????????
    public String getDsfZdDzxx(String zdbs, String dsfzdbs, String bdczdz) {
        //??????????????????????????????
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs(zdbs);
        bdcZdDsfzdgxDO.setBdczdz(bdczdz);
        bdcZdDsfzdgxDO.setDsfxtbs(dsfzdbs);
        BdcZdDsfzdgxDO result = zdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        LOGGER.info("---????????????????????????:{},????????????:{}", result, bdcZdDsfzdgxDO);
        if (null != result && StringUtils.isNotBlank(result.getDsfzdz())) {
            return result.getDsfzdz();
        }
        return bdczdz;
    }

    /**
     * ?????????????????????V3
     *
     * @param bdcDdxxDTO ????????????DTO
     * @return Object
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    public Object ykqScdd(BdcDdxxDTO bdcDdxxDTO) {
        LOGGER.info("???????????????????????????V3????????????????????????{}", JSON.toJSONString(bdcDdxxDTO));
        Object response = exchangeInterfaceFeignService.requestInterface("ykq_scdd_http", this.setRequestParam(bdcDdxxDTO));
        LOGGER.info("?????????????????????V3??????????????????{}", response.toString());
        return response;
    }

    /**
     * ???????????????????????????
     *
     * @param ykqDdztRequestDTO ????????????DTO
     * @return Object
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    public Object ykqDdzt(YkqDdztRequestDTO ykqDdztRequestDTO) {
        LOGGER.info("???????????????????????????V2????????????????????????{}", JSON.toJSONString(ykqDdztRequestDTO));
        if (null == ykqDdztRequestDTO || CollectionUtils.isEmpty(ykqDdztRequestDTO.getData())) {
            throw new MissingArgumentException("???????????????????????????????????????");
        }
        List<YkqDdztDataDTO> ykqDdztDataDTOList = ykqDdztRequestDTO.getData();
        for (YkqDdztDataDTO ykqDdztDataDTO : ykqDdztDataDTOList) {
            BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
            bdcSlSfssDdxxQO.setDdbh(ykqDdztDataDTO.getDdbh());
            bdcSlSfssDdxxQO.setQlrlb(ykqDdztDataDTO.getQlrlb());
            List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = bdcSlSfssDdxxFeignService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
            if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
                String ddzt = ykqDdztDataDTO.getDdzt();
                String sfzt = ykqDdztDataDTO.getJfzt();
                for (BdcSlSfssDdxxDO bdcSlSfssDdxxDO : bdcSlSfssDdxxDOList) {
                    // ???????????????????????????
                    if (StringUtils.isNotBlank(sfzt)) {
                        this.modifyHsxxSfxx(bdcSlSfssDdxxDO.getGzlslid(), bdcSlSfssDdxxDO.getQlrlb(), Integer.parseInt(sfzt), bdcSlSfssDdxxDO.getDdlx());
                    }

                    // ??????????????????????????????
                    if (Objects.equals(BdcSfztEnum.YJF.key(), Integer.parseInt(sfzt))) {
                        bdcSlSfssDdxxDO.setJfzt(BdcSfztEnum.YJF.key());
                    } else {
                        bdcSlSfssDdxxDO.setJfzt(BdcSfztEnum.WJF.key());
                    }
                    if (StringUtils.isNotBlank(ddzt)) {
                        bdcSlSfssDdxxDO.setDdzt(Integer.parseInt(ddzt));
                    }
                    this.bdcSlSfssDdxxFeignService.updateBdcSlSfssDdxxDO(bdcSlSfssDdxxDO);
                }
            }
        }
        return "";
    }

    private void modifyHsxxSfxx(String gzlslid, String qlrlb, Integer zfzt, Integer ddlx) {
        LOGGER.info("?????????????????????{}, gzlslid:{}, qlrlb:{}, ddlx:{}", zfzt, gzlslid, qlrlb, ddlx);

        // ??????????????????????????????
        {
            if (Objects.equals(ddlx, CommonConstantUtils.DDXX_DDLX_SFTJ) || Objects.equals(ddlx, CommonConstantUtils.DDXX_DDLX_JNS)) {
                List<BdcSlHsxxDO> bdcSlHsxxDOList = this.queryBdcSlHsxx(gzlslid, null, qlrlb);
                if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                    for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                        if (Objects.nonNull(zfzt)) {
                            bdcSlHsxxDO.setJfzt(zfzt);
                            if (Objects.equals(BdcSfztEnum.YJF.key(), zfzt)) {
                                bdcSlHsxxDO.setWszt(CommonConstantUtils.SF_S_DM);
                            } else {
                                bdcSlHsxxDO.setWszt(CommonConstantUtils.SF_F_DM);
                            }
                            this.bdcSlHsxxFeignService.updateBdcSlHsxx(bdcSlHsxxDO);
                        }
                    }
                }
            }
        }
        { // ????????????????????? ????????????
            if (Objects.equals(ddlx, CommonConstantUtils.DDXX_DDLX_SFTJ) || Objects.equals(ddlx, CommonConstantUtils.DDXX_DDLX_JNF)) {
                BdcSlSfxxQO bdcSlSfxxQO = new BdcSlSfxxQO();
                bdcSlSfxxQO.setGzlslid(gzlslid);
                bdcSlSfxxQO.setQlrlb(qlrlb);
                List<BdcSlSfxxDO> bdcSlSfxxDOList = this.bdcSlSfxxFeignService.queryBdcSlSfxx(bdcSlSfxxQO);
                if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList) && Objects.nonNull(zfzt)) {
                    Integer sfzt = BdcSfztEnum.WJF.key();
                    if (Objects.equals(BdcSfztEnum.YJF.key(), zfzt)) {
                        sfzt = BdcSfztEnum.YJF.key();
                    }
                    for (BdcSlSfxxDO bdcSlSfxxDO : bdcSlSfxxDOList) {
                        bdcSlSfxxDO.setSfzt(sfzt);
                        Date jfsj = new Date();
                        bdcSlSfxxDO.setSfztczsj(jfsj);
                        bdcSlSfxxDO.setSfsj(jfsj);
                        bdcSlSfxxDO.setSfxsjf(CommonConstantUtils.SF_S_DM);
                        this.bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);

                        List<BdcSlSfxmDO> bdcSlSfxmDOList = this.bdcSlSfxmFeignService.listBdcSlSfxmBySfxxid(bdcSlSfxxDO.getSfxxid());
                        if (CollectionUtils.isNotEmpty(bdcSlSfxmDOList)) {
                            for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                                bdcSlSfxmDO.setSfzt(sfzt);
                            }
                            this.bdcSlSfxmFeignService.batchUpdateBdcSlSfxm(bdcSlSfxmDOList);
                            // ???????????????????????????????????????????????????????????????????????????????????? sfzt
                            boolean allYjf = true;
                            for (BdcSlSfxmDO bdcSlSfxmDO : bdcSlSfxmDOList) {
                                if (!Objects.equals(bdcSlSfxmDO.getSfzt(), BdcSfztEnum.YJF.key())) {
                                    allYjf = false;
                                    break;
                                }
                            }
                            if (allYjf) {
                                bdcSlSfxxDO.setSfzt(BdcSfztEnum.YJF.key());
                                this.bdcSlSfxxFeignService.updateBdcSlSfxx(bdcSlSfxxDO);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * ?????????????????????ID?????????ID?????????????????? ??????????????????
     */
    private List<BdcSlHsxxDO> queryBdcSlHsxx(String gzlslid, String xmid, String qlrlb) {
        List<BdcSlHsxxDO> bdcSlHsxxDOList = new ArrayList<>(10);
        if (StringUtils.isBlank(xmid)) {
            bdcSlHsxxDOList = this.bdcSlHsxxFeignService.listBdcSlHsxxByGzlslidAndSqrlb(gzlslid, qlrlb);
        } else {
            BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
            bdcSlHsxxQO.setXmid(xmid);
            bdcSlHsxxQO.setSqrlb(qlrlb);
            bdcSlHsxxDOList = this.bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
        }
        // ????????????????????????????????????????????????????????????????????????0?????????
        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
            bdcSlHsxxDOList = bdcSlHsxxDOList.stream()
                    .filter(t -> StringUtils.isBlank(t.getHsxxlx()) || StringUtils.equals("0", t.getHsxxlx()))
                    .collect(Collectors.toList());
        }
        return bdcSlHsxxDOList;
    }

}
