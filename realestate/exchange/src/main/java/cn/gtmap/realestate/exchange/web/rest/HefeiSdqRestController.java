package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.request.HefeiDianSqghRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.HefeiDianSqghResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.yhcx.request.DianYhcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.yhcx.response.HefeiDianYhcxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.ghcx.request.HefeiRanqiGhcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.ghcx.response.HefeiRanqiGhcxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.ghcx.response.HefeiWnRanqiGhcxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.HefeiWnRanqiGhJgRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.request.RanqiSqghCommonRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.HefeiWnRanqiGhJgResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.RanqiSqghCommonResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.request.HefeiShuifeicxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.response.HefeiShuifeicxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.request.HefeiShuiSqghRequestTransferPerson;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.HefeiShuiSqghResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.NewHefeiShuiSqghResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztResponseDTO;
import cn.gtmap.realestate.common.core.enums.*;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.rest.exchange.HefeiSdqRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.service.impl.inf.hefei.SdqServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.shucheng.ShuChengSqdService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-20
 * @description ?????? ?????? ?????? ??? ????????????
 */
@OpenController(consumer = "?????? ?????? ?????? ??? ????????????")
@RestController
@Api(tags = "?????????????????????????????????")
public class HefeiSdqRestController implements HefeiSdqRestService {

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private SdqServiceImpl sdqService;

    @Autowired
    private ShuChengSqdService shuChengSqdService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    protected static Logger LOGGER = LoggerFactory.getLogger(HefeiSdqRestController.class);

    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.ghcx.response.HefeiRanqiGhcxResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ?????? ????????????
     * @modify ????????????????????????
     */
    @OpenApi(apiDescription = "??????????????????",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "requestDTO", value = "???????????????", required = true, dataType = "HefeiRanqiGhcxRequestDTO", paramType = "body")})
    public RanqiSqghCommonResponseDTO ranqiGhcx(@RequestBody HefeiRanqiGhcxRequestDTO requestDTO) {
        RanqiSqghCommonResponseDTO ranqiSqghCommonResponseDTO = new RanqiSqghCommonResponseDTO();
        BeanUtils.copyProperties(requestDTO,ranqiSqghCommonResponseDTO);
        LOGGER.info("????????????????????????????????????{}", JSONObject.toJSONString(requestDTO));
        String beanName;
        if(requestDTO.getRqfwbsm().equals(BdcSdqRqfwbsmEnum.HFRQ.key())){
            beanName = "ranqi_hf_ghcx";
            HefeiRanqiGhcxResponseDTO responseDTO = exchangeBeanRequestService.request(beanName,requestDTO,HefeiRanqiGhcxResponseDTO.class);
            ranqiSqghCommonResponseDTO.setResponseContent(JSON.toJSONString(responseDTO));
        }else if(requestDTO.getRqfwbsm().equals(BdcSdqRqfwbsmEnum.WNRQ.key())){
            beanName = "ranqi_wn_ghcx";
            HefeiWnRanqiGhcxResponseDTO responseDTO = exchangeBeanRequestService.request(beanName,requestDTO,HefeiWnRanqiGhcxResponseDTO.class);
            ranqiSqghCommonResponseDTO.setResponseContent(JSON.toJSONString(responseDTO));
        }
        LOGGER.info("????????????????????????????????????{}", JSONObject.toJSONString(ranqiSqghCommonResponseDTO));
        return ranqiSqghCommonResponseDTO;
    }

    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.HefeiRanqiSqghResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??????????????????
     */
    @OpenApi(apiDescription = "??????????????????",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "requestDTO", value = "???????????????", required = true, dataType = "HefeiRanqiSqghRequestDTO", paramType = "body")})
    public RanqiSqghCommonResponseDTO ranqiSqgh(@RequestBody RanqiSqghCommonRequestDTO requestDTO) {
        LOGGER.info("????????????????????????????????????{}", JSONObject.toJSONString(requestDTO));
        RanqiSqghCommonResponseDTO responseDTO = sdqService.ranqiSqgh(requestDTO);
        LOGGER.info("????????????????????????????????????{}", JSONObject.toJSONString(responseDTO));
        return responseDTO;
    }

    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.ranqi.sqgh.response.HefeiWnRanqiGhJgResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????????????????????????????
     */
    @OpenApi(apiDescription = "????????????????????????????????????",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "requestDTO", value = "???????????????", required = true,
            dataType = "HefeiWnRanqiGhJgRequestDTO", paramType = "body")})
    public HefeiWnRanqiGhJgResponseDTO wnRanqiGhJg(HefeiWnRanqiGhJgRequestDTO requestDTO) {
        LOGGER.info("????????????????????????????????????????????????????????????{}", JSONObject.toJSONString(requestDTO));
        HefeiWnRanqiGhJgResponseDTO hefeiWnRanqiGhJgResponseDTO = new HefeiWnRanqiGhJgResponseDTO();
        hefeiWnRanqiGhJgResponseDTO.setYhh(requestDTO.getYhh());
        hefeiWnRanqiGhJgResponseDTO.setYwh(requestDTO.getYwh());
        //????????????
        String beanName = "ranqi_lj_wn_ghjg";
        BdcSdqBlztRequestDTO bdcSdqBlztRequestDTO = new BdcSdqBlztRequestDTO();
        bdcSdqBlztRequestDTO.setConsno(requestDTO.getYhh());
        bdcSdqBlztRequestDTO.setYwlx(BdcSdqEnum.Q.key());
        if(requestDTO.getShjg().equals(BdcSdqWnJgShyjEnum.ADOPT.code())){
            bdcSdqBlztRequestDTO.setBlzt(CommonConstantUtils.BLCG);
        }else if(requestDTO.getShjg().equals(BdcSdqWnJgShyjEnum.REJECT.code())){
            bdcSdqBlztRequestDTO.setBlzt(CommonConstantUtils.BLSB);
        }
        BdcSdqBlztResponseDTO responseDTO = null;
        try {
            responseDTO = exchangeBeanRequestService
                    .request(beanName,requestDTO, BdcSdqBlztResponseDTO.class);
            hefeiWnRanqiGhJgResponseDTO.setEchoCode(BdcSdqWnRqStatusEnum.SUCCESS.code());
        } catch (Exception e) {
            hefeiWnRanqiGhJgResponseDTO.setEchoCode(BdcSdqWnRqStatusEnum.ERROR.code());
            hefeiWnRanqiGhJgResponseDTO.setEchoDes(BdcSdqWnRqStatusEnum.ERROR.name());
        }

        LOGGER.info("????????????????????????????????????{}", JSONObject.toJSONString(responseDTO));
        return hefeiWnRanqiGhJgResponseDTO;
    }

    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.yhcx.response.HefeiDianYhcxResponseData
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??? ????????????
     */
    @OpenApi(apiDescription = "???????????????",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public HefeiDianYhcxResponseDTO dianGhcx(@RequestBody DianYhcxRequestDTO requestDTO) {
        LOGGER.info("?????????????????????????????????{}", JSONObject.toJSONString(requestDTO));
        HefeiDianYhcxResponseDTO responseDTO = exchangeBeanRequestService.request("dian_ghcx",requestDTO,HefeiDianYhcxResponseDTO.class);
        LOGGER.info("?????????????????????????????????{}", JSONObject.toJSONString(responseDTO));
        return responseDTO;
    }

    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.response.HefeiDianSqghResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??? ????????????
     */
    @OpenApi(apiDescription = "???????????????",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public HefeiDianSqghResponseDTO dianSqgh(@RequestBody HefeiDianSqghRequestDTO requestDTO) {
        LOGGER.info("?????????????????????????????????{}",requestDTO.getSqghData());
        HefeiDianSqghResponseDTO responseDTO = sdqService.dianSqgh(requestDTO);
        LOGGER.info("?????????????????????????????????{}", JSONObject.toJSONString(responseDTO));
        return responseDTO;
    }

    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.HefeiShuiSqghResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ??? ????????????
     */
    @OpenApi(apiDescription = "???????????????",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public HefeiShuiSqghResponseDTO shuiSqgh(@RequestBody HefeiShuiSqghRequestTransferPerson requestDTO) {
        LOGGER.info("?????????????????????????????????{}", JSONObject.toJSONString(requestDTO));
        HefeiShuiSqghResponseDTO responseDTO = sdqService.shuiSqgh(requestDTO);
        LOGGER.info("?????????????????????????????????{}", JSONObject.toJSONString(responseDTO));
        return responseDTO;
    }

    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.sqgh.response.HefeiShuiSqghResponseDTO
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description ??? ??? ????????????
     */
    @OpenApi(apiDescription = "??????????????????",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    public HefeiShuiSqghResponseDTO newShuiSqgh(@RequestBody HefeiShuiSqghRequestTransferPerson requestDTO) {
        LOGGER.info("????????????????????????????????????{}", JSONObject.toJSONString(requestDTO));
        NewHefeiShuiSqghResponseDTO responseDTO = sdqService.newShuiSqgh(requestDTO);
        //??????????????????????????????????????????????????????
        HefeiShuiSqghResponseDTO hefeiShuiSqghResponseDTO = new HefeiShuiSqghResponseDTO();
        hefeiShuiSqghResponseDTO.setStatus(responseDTO.getCode());
        hefeiShuiSqghResponseDTO.setMsg(responseDTO.getMsg());
        hefeiShuiSqghResponseDTO.setData(hefeiShuiSqghResponseDTO.getData());
        LOGGER.info("????????????????????????????????????{}", JSONObject.toJSONString(responseDTO));
        return hefeiShuiSqghResponseDTO;
    }

    /**
     * @param requestDTO
     * @return cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.response.HefeiShuifeicxResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description ????????????
     */
    @OpenApi(apiDescription = "????????????",apiName = "",openLog = false)
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @DsfLog(logEventName = "????????????",dsfFlag = "SHUI",requester = "BDC",responser = "SHUI")
    public HefeiShuifeicxResponseDTO shuifeiCx(@RequestBody HefeiShuifeicxRequestDTO requestDTO) {
        LOGGER.info("??????????????????????????????{}", JSONObject.toJSONString(requestDTO));
        HefeiShuifeicxResponseDTO responseDTO = new HefeiShuifeicxResponseDTO();
        if(StringUtils.isNotEmpty(requestDTO.getDsffwbs())
                && BdcSdqRqfwbsmEnum.KXGS.key().equals(requestDTO.getDsffwbs())){
            List<BdcXmDTO> listXm = bdcXmFeignService.listBdcXmBfxxByGzlslid(requestDTO.getGzlslid());
            if(CollectionUtils.isNotEmpty(listXm)) {
                BdcXmDTO bdcXmDTO = listXm.get(0);
                CommonResponse<Boolean> sghjc = shuChengSqdService.sghjc(bdcXmDTO.getGzlslid(),
                        requestDTO.getYhdm(),
                        bdcXmDTO.getQjgldm());
                if(sghjc.isSuccess()){
                    responseDTO.setXym("004");
                }else {
                    responseDTO.setXym("000");
                }
            }
        }else {
            responseDTO = sdqService.shuifeiCx(requestDTO);
        }
        LOGGER.info("??????????????????????????????{}", JSONObject.toJSONString(responseDTO));
        return responseDTO;
    }
}
