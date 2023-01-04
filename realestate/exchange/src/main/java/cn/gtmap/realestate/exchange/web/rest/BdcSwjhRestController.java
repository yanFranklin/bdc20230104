package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.BdcCommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.ResponseHead;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.tsztjs.YrbSwTsztjs;
import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.tsztjs.response.YrbTsswztResponse;
import cn.gtmap.realestate.common.core.dto.exchange.ykq.jsswzt.request.YkqSwztRequestDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlHsxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlHsxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/11/23
 * @description 不动产与税务交互相关接口-存在不同税务单位，注释需要写清楚
 */

@OpenController(consumer = "不动产与税务交互相关接口")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/swjh")
@Api(tags = "不动产与税务交互相关接口")
public class BdcSwjhRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSwjhRestController.class);

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private BdcZdFeignService zdFeignService;

    @Autowired
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    BdcSlHsxxFeignService bdcSlHsxxFeignService;


    /**
     * 德宽 -通知德宽税务更新制证接口
     *
     * @param gzlslid 工作流实例ID
     * @return
     * @Date 2022/11/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @ApiOperation(value = "更新制证状态")
    @ResponseStatus(code = HttpStatus.OK)
    @DsfLog(logEventName = "更新制证状态", dsfFlag = "DKSW", requester = "BDC", responser = "DKSW")
    @PostMapping("/gxzzzt")
    @ResponseBody
    public BdcCommonResponse querySjsjlcxCount(@RequestParam(value = "processInsId") String processInsId) {
        if (StringUtils.isBlank(processInsId)) {
            throw new MissingArgumentException("缺少工作流实例id！请检查！");
        }
        //查询受理项目表，获取fwuuid  多个fwuuid循环调用推送德宽接口
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(processInsId);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            for (BdcSlXmDO slXmDO : bdcSlXmDOList) {
                JSONObject param = new JSONObject();

                if (StringUtils.isNotBlank(slXmDO.getFwuuid())) {
                    param.put("fwuuid", slXmDO.getFwuuid());
                }
                //xmid查询项目表，获取bdclx,组织tdbz
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setGzlslid(processInsId);
                bdcXmQO.setXmid(slXmDO.getXmid());
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList) && CommonConstantUtils.DYBDCLX_CTD.equals(bdcXmDOList.get(0).getBdclx())) {
                    param.put("tdbz", CommonConstantUtils.SF_S_DM.toString());
                } else {
                    param.put("tdbz", CommonConstantUtils.SF_F_DM.toString());
                }
                //开始推送德宽税务，更新制证状态
                Object data = exchangeBeanRequestService.request("gxzzzt_dk", param);
                LOGGER.info("德宽税务更新制证状态接口返回：{}", JSONObject.toJSONString(data));
                return BdcCommonResponse.ok(data);
            }
        } else {
            LOGGER.warn("查不到相关受理项目信息，请检查数据！工作流实例id：{}", processInsId);
            return BdcCommonResponse.fail("查不到相关受理项目信息，请检查数据！工作流实例id" + processInsId);
        }
        return BdcCommonResponse.ok("成功！");
    }


    /**
     * 一卡清 -接受一卡清推送税务状态接口
     *
     * @param
     * @return
     * @Date 2022/11/23
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @ApiOperation(value = "接收一卡清推送税务状态接口")
    @ResponseStatus(code = HttpStatus.OK)
    @DsfLog(logEventName = "接收一卡清推送税务状态接口", dsfFlag = "YKQ", requester = "BDC", responser = "YKQ")
    @PostMapping("/ykq/swztjs")
    @ResponseBody
    public Map ykqSwzt(@RequestBody YkqSwztRequestDTO ykqSwztRequestDTO) {
        ResponseHead head = new ResponseHead();
        YrbTsswztResponse response = new YrbTsswztResponse();
        if (Objects.isNull(ykqSwztRequestDTO) && Objects.isNull(ykqSwztRequestDTO.getData().getSbzttsxxlist())) {
            head.setStatusCode(Constants.CODE_SEARCH_ERROR);
            head.setMsg("推送税务入参为空！");
        }

        List<YrbSwTsztjs> ykqSwTsztjsList = ykqSwztRequestDTO.getData().getSbzttsxxlist();
        try {
            if (CollectionUtils.isNotEmpty(ykqSwTsztjsList)) {
                for (YrbSwTsztjs swTsztjs : ykqSwTsztjsList) {
                    BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                    bdcZdDsfzdgxDO.setZdbs("SW_SBZT");
                    bdcZdDsfzdgxDO.setDsfzdz(swTsztjs.getShzt());
                    bdcZdDsfzdgxDO.setDsfxtbs("SW");
                    BdcZdDsfzdgxDO zdDsfzdgxDO = zdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                    // 一卡清推送作废状态时，更新hsxx 退回原因
                    String zfyy = "";
                    if (StringUtils.equals("30", swTsztjs.getShzt())) {
                        try {
                            zfyy = URLDecoder.decode(swTsztjs.getBz(), "GBK");
                        } catch (UnsupportedEncodingException e) {
                            LOGGER.error("一卡清税务作废原因转码失败，转码内容为：{}", swTsztjs.getBz());
                        }
                    }
                    if (null != zdDsfzdgxDO) {
                        if (StringUtils.isNotBlank(swTsztjs.getSjbh())) {
                            //slbh查bdc_sl_jbxx 表，获取gzlslid,再去更新wszt
                            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(swTsztjs.getSjbh(), "");
                            if (null != bdcSlJbxxDO && StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())) {
                                try {
                                    List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByJbxxid(bdcSlJbxxDO.getJbxxid());
                                    BdcSlHsxxQO bdcSlHsxxQO = new BdcSlHsxxQO();
                                    bdcSlHsxxQO.setXmid(bdcSlXmDOList.get(0).getXmid());
                                    List<BdcSlHsxxDO> bdcSlHsxxDOList = this.bdcSlHsxxFeignService.listBdcSlHsxx(bdcSlHsxxQO);
                                    if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                                        for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                                            bdcSlHsxxDO.setWszt(Integer.valueOf(zdDsfzdgxDO.getBdczdz()));
                                            bdcSlHsxxDO.setThyy(zfyy);
                                            this.bdcSlHsxxFeignService.updateBdcSlHsxx(bdcSlHsxxDO);
                                        }
                                    } else {
                                        BdcSlHsxxDO bdcSlHsxxDO = new BdcSlHsxxDO();
                                        bdcSlHsxxDO.setXmid(bdcSlXmDOList.get(0).getXmid());
                                        bdcSlHsxxDO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                                        bdcSlHsxxDO.setWszt(Integer.valueOf(zdDsfzdgxDO.getBdczdz()));
                                        bdcSlHsxxDO.setThyy(zfyy);
                                        this.bdcSlHsxxFeignService.insertBdcSlHsxxDO(bdcSlHsxxDO);
                                    }
                                    head.setStatusCode(Constants.CODE_SUCCESS);
                                    head.setMsg("更新完税状态成功！");
                                    response.setFhm(Constants.SUCCESS_CODE_0);
                                    response.setFhxx("更新完税状态成功！");
                                } catch (Exception e) {
                                    head.setStatusCode(Constants.CODE_SEARCH_ERROR);
                                    head.setMsg("更新完税状态失败！原因：" + e.getMessage());
                                    response.setFhm(Constants.FAIL_CODE_1);
                                    response.setFhxx("更新完税状态失败！原因：" + e.getMessage());
                                }
                            } else {
                                head.setStatusCode(Constants.CODE_SEARCH_ERROR);
                                head.setMsg("查不到该流程信息！请检查数据！");
                                response.setFhm(Constants.FAIL_CODE_1);
                                response.setFhxx("查不到该流程信息！请检查数据！");
                            }
                        } else {
                            head.setStatusCode(Constants.CODE_SEARCH_ERROR);
                            head.setMsg("收件编号为空！");
                            response.setFhm(Constants.FAIL_CODE_1);
                            response.setFhxx("收件编号为空！");
                        }

                    } else {
                        head.setStatusCode(Constants.CODE_SEARCH_ERROR);
                        head.setMsg("审核状态未配置对照！请检查第三方字典标识SW_SBZT，系统标识SW相关对照配置！");
                        response.setFhm(Constants.FAIL_CODE_1);
                        response.setFhxx("审核状态未配置对照！请检查第三方字典标识SW_SBZT，系统标识SW相关对照配置！");
                    }
                }
            }
        } catch (Exception e) {
            head.setStatusCode(Constants.CODE_SEARCH_ERROR);
            head.setMsg("处理异常" + e.getMessage());
            response.setFhm(Constants.FAIL_CODE_1);
            response.setFhxx("处理异常" + e.getMessage());
        }

        HashMap data = new HashMap();
        data.put("rwjshouselist", response);
        HashMap resp = new HashMap();
        resp.put("head", head);
        resp.put("data", data);
        return resp;

    }
}
