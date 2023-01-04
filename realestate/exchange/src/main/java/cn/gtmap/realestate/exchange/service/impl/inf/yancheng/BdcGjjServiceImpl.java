package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.enums.GjjActionTypeEnum;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.rest.init.BdcCzrzRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.dto.yancheng.gjj.BdcDySuccessRequest;
import cn.gtmap.realestate.exchange.core.dto.yancheng.gjj.BdcYdySuccessRequest;
import cn.gtmap.realestate.exchange.core.dto.yancheng.gjj.BdcDyrxxDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcGjjService;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcShijiService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 盐城公积金接口相关处理服务
 */
@Service
public class BdcGjjServiceImpl implements BdcGjjService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcShijiService.class);

    @Autowired
    private ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    private BdcCzrzRestService bdcCzrzRestService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    private static final String SYGDY_INTERFACE = "sygdyGjjCallback";

    private static final String SCDY_INTERFACE = "scdyGjjCallback";

    private static final String ZXDY_INTERFACE = "zcdyGjjCallback";

    @Value("${yancheng.gjj.interface.head.syg.tradeCode:}")
    private String sygtradeCode;
    @Value("${yancheng.gjj.interface.head.sc.tradeCode:}")
    private String sctradeCode;
    @Value("${yancheng.gjj.interface.head.zx.tradeCode:}")
    private String zxtradeCode;

    /**
     * 盐城_公积金双预告抵押回调接口
     *
     * @param processInsId
     * @return
     */
    @Override
    public CommonResponse sygdyGjjCallback(String processInsId,String actionType,String reason) {
        LOGGER.info("盐城_公积金双预告抵押回调接口入参:{},actionType:{},reson:{}", processInsId,actionType,reason);
        if (GjjActionTypeEnum.SUCCESS.getCode().equals(actionType)){
            Example example = new Example(BdcXmDO.class);
            example.createCriteria().andEqualTo("gzlslid", processInsId).andEqualTo("qllx",CommonConstantUtils.QLLX_YG_DM);
            List<BdcXmDO> bdcXmList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                BdcXmDO bdcXmDO = bdcXmList.get(0);
                Example dyxxExample = new Example(BdcYgDO.class);
                dyxxExample.createCriteria().andEqualTo("xmid", bdcXmDO.getXmid()).andEqualTo("ygdjzl",CommonConstantUtils.YGDJZL_YSSPF);
                List<BdcYgDO> bdcYgDOS = entityMapper.selectByExample(dyxxExample);
                if (CollectionUtils.isEmpty(bdcYgDOS)){
                    dyxxExample.clear();
                    dyxxExample.createCriteria().andEqualTo("xmid", bdcXmList.get(1).getXmid()).andEqualTo("ygdjzl",CommonConstantUtils.YGDJZL_YSSPF);
                    bdcYgDOS = entityMapper.selectByExample(dyxxExample);
                    bdcXmDO = bdcXmList.get(1);
                }
                BdcYdySuccessRequest bdcYdySuccessRequest = BdcYdySuccessRequest.BdcYdySuccessRequestBuilder.aBdcYdySuccessRequest()
                        .withBdcdyh(bdcXmDO.getBdcdyh())
//                    .withBdcdyygdjzh()
                        .withDjzt("1")
                        .withDyqdjrq(new SimpleDateFormat("yyyy-MM-dd").format(bdcXmDO.getDjsj()))
//                    .withDyqdqrq()
                        .withDywzl(bdcXmDO.getZl())
                        .withYwslbh(bdcXmDO.getSlbh())
                        .withMsg("success")
                        .build();
                //查询产权证号
                Map<String, Object> queryBdcXxMap = new HashMap<>();
                queryBdcXxMap.put("xmid", bdcXmDO.getXmid());
                List<BdcZsDO> bdcqzhMap = bdcdjMapper.getZsbsxxByXmid(queryBdcXxMap);
                StringBuilder bdcqzh = new StringBuilder();
                if (CollectionUtils.isNotEmpty(bdcqzhMap)) {
                    for (int i = 0; i < bdcqzhMap.size(); i++) {
                        if (i == 0) {
                            bdcqzh.append(bdcqzhMap.get(i).getBdcqzh());
                        } else {
                            bdcqzh.append(",").append(bdcqzhMap.get(i).getBdcqzh());
                        }
                    }
                }
                bdcYdySuccessRequest.setBdcdyygdjzh(bdcqzh.toString());
                //查询dyrxx
                Example dyrExample = new Example(BdcQlrDO.class);
                dyrExample.createCriteria().andEqualTo("xmid", bdcXmDO.getXmid()).andEqualTo("qlrlb", CommonConstantUtils.QLRLB_YWR_DM);
                List<BdcQlrDO> dyrList = entityMapper.selectByExample(dyrExample);
                List<BdcDyrxxDTO> dyrxxs = new ArrayList<>(4);
                if (CollectionUtils.isNotEmpty(dyrList)) {
                    for (BdcQlrDO bdcQlrDO : dyrList) {
                        BdcDyrxxDTO dyrxxDTO = BdcDyrxxDTO.BdcDyrxxDTOBuilder.aBdcDyrxxDTO()
                                .withDyrxm(bdcQlrDO.getQlrmc())
                                .withDyrzjhm(bdcQlrDO.getZjh())
                                .withDyrlxdh(bdcQlrDO.getDh())
                                .build();
                        dyrxxs.add(dyrxxDTO);
                    }
                }
                bdcYdySuccessRequest.setDyrxx(dyrxxs);
                //查询dy到期时间
                if (CollectionUtils.isNotEmpty(bdcYgDOS)){
                    bdcYdySuccessRequest.setDyqdqrq(new SimpleDateFormat("yyyy-MM-dd").format(bdcYgDOS.get(0).getZwlxjssj()));
                }
                Map<String, Object> requestParam = new HashMap<>(2);
                requestParam.put("head", initHeadParam(bdcXmDO.getXmid(),sygtradeCode));
                requestParam.put("body", bdcYdySuccessRequest);
                Object response = exchangeInterfaceFeignService.requestInterface(SYGDY_INTERFACE, requestParam);
                if (response != null) {
                    return dealWithResponse(response);
                } else {
                    throw new RuntimeException("公积金接口调用无返回,gzlslid:" + processInsId);
                }
            }
            throw new RuntimeException("未查询到相关的项目信息,gzlslid:" + processInsId);
        }else if (GjjActionTypeEnum.DELETE.getCode().equals(actionType)){
            return deleteOrReturnGjjCallback(processInsId, reason,SYGDY_INTERFACE,sygtradeCode);
        }
        throw new RuntimeException("当前操作类型错误,actionType:" + actionType);
    }

    @NotNull
    private CommonResponse<Object> deleteOrReturnGjjCallback(String processInsId, String reason,String interfaceBeanId,String tradeCode) {
        LOGGER.info("当前抵押删除或退回调用公积金接口入参:{}", processInsId);
        if (StringUtils.isNotBlank(reason)){
            Example example = new Example(BdcXmDO.class);
            example.createCriteria().andEqualTo("gzlslid", processInsId).andEqualTo("qllx",CommonConstantUtils.QLLX_DYAQ_DM);
            List<BdcXmDO> bdcXmList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                Map<String, Object> requestParam = new HashMap<>(2);
                requestParam.put("head", initHeadParam(bdcXmList.get(0).getXmid(),tradeCode));
                Map<String, Object> bodyParam = new HashMap<>(4);
                /**
                 * "body" : {
                 * 	"ywslbh":"123123123",
                 * 	"djzt":"1",
                 * 	"msg":"success",
                 * 	"dyqzxrq":"2021-07-29",
                 * }
                 */
                bodyParam.put("ywslbh", bdcXmList.get(0).getSlbh());
                bodyParam.put("djzt", "0");
                bodyParam.put("msg", reason);
                requestParam.put("body", bodyParam);
                Object response = exchangeInterfaceFeignService.requestInterface(interfaceBeanId, requestParam);
                if (response != null) {
                    return dealWithResponse(response);
                } else {
                    throw new RuntimeException("公积金接口调用无返回,gzlslid:" + processInsId);
                }
            }
            throw new RuntimeException("未查询到相关的项目信息,gzlslid:" + processInsId);
        }else {
            BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
            bdcCzrzDO.setGzlslid(processInsId);
            bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_SC.key());
            List<BdcCzrzDO> czrz = bdcCzrzRestService.listBdcCzrz(bdcCzrzDO);
            String deleteReason;
            String xmid;
            String slbh;
            if (CollectionUtils.isNotEmpty(czrz)) {
                // 获取时间最大的一条数据
                Optional<BdcCzrzDO> bdcCzrz = czrz.stream().max(Comparator.comparing(BdcCzrzDO::getCzsj));
                if (!bdcCzrz.isPresent()) {
                    LOGGER.info("当前抵押删除或退回调用公积金接口报错:未查询到相关删除的操作日志");
                    return CommonResponse.fail("当前抵押删除或退回调用公积金接口报错:未查询到相关删除的操作日志");
//                    throw new RuntimeException("未查询到相关删除的操作日志");
                }
                xmid = bdcCzrz.get().getXmid();
                slbh = bdcCzrz.get().getSlbh();
                deleteReason = bdcCzrz.get().getCzyy().substring(bdcCzrz.get().getCzyy().lastIndexOf(":") + 1);
            }else {
//                throw new RuntimeException("未查询到相关的操作日志记录,gzlslid:" + processInsId);
                LOGGER.info("当前抵押删除或退回调用公积金接口报错:未查询到相关删除的操作日志");
                return CommonResponse.fail("当前抵押删除或退回调用公积金接口报错:未查询到相关删除的操作日志");
            }
            Map<String, Object> requestParam = new HashMap<>(2);
            requestParam.put("head", initHeadParam(xmid,tradeCode));
            Map<String, Object> bodyParam = new HashMap<>(4);
            /**
             * "body" : {
             * 	"ywslbh":"123123123",
             * 	"djzt":"1",
             * 	"msg":"success",
             * 	"dyqzxrq":"2021-07-29",
             * }
             */
            bodyParam.put("ywslbh",slbh);
            bodyParam.put("djzt", "0");
            bodyParam.put("msg", deleteReason);
            requestParam.put("body", bodyParam);
            Object response = exchangeInterfaceFeignService.requestInterface(interfaceBeanId, requestParam);
            if (response != null) {
                return dealWithResponse(response);
            } else {
                throw new RuntimeException("公积金接口调用无返回,gzlslid:" + processInsId);
            }
        }
    }

    /**
     * 盐城_公积金首次抵押回调接口
     *
     * @param processInsId
     * @return
     */
    @Override
    public CommonResponse scdyGjjCallback(String processInsId, String actionType, String reason) {
        LOGGER.info("当前盐城_公积金首次抵押回调接口入参:{},actionType:{},reason:{}", processInsId,actionType,reason);
        if (GjjActionTypeEnum.SUCCESS.getCode().equals(actionType)){
            Example example = new Example(BdcXmDO.class);
            example.createCriteria().andEqualTo("gzlslid", processInsId).andEqualTo("qllx",CommonConstantUtils.QLLX_DYAQ_DM);
            List<BdcXmDO> bdcXmList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                BdcDySuccessRequest bdcYdySuccessRequest = BdcDySuccessRequest.BdcDySuccessRequestBuilder.aBdcDySuccessRequest()
                        .withBdcdyh(bdcXmList.get(0).getBdcdyh())
//                    .withBdcdyygdjzh()
                        .withBdcqzh(bdcXmList.get(0).getYcqzh())
                        .withDjzt("1")
                        .withDyqdjrq(new SimpleDateFormat("yyyy-MM-dd").format(bdcXmList.get(0).getDjsj()))
//                    .withDyqdqrq()
                        .withDywzl(bdcXmList.get(0).getZl())
                        .withYwslbh(bdcXmList.get(0).getSlbh())
                        .withMsg("success")
                        .build();
                //查询产权证号
                Map<String, Object> queryBdcXxMap = new HashMap<>();
                queryBdcXxMap.put("xmid", bdcXmList.get(0).getXmid());
                List<BdcZsDO> bdcqzhMap = bdcdjMapper.getZsbsxxByXmid(queryBdcXxMap);
                StringBuilder bdcqzh = new StringBuilder();
                if (CollectionUtils.isNotEmpty(bdcqzhMap)) {
                    for (int i = 0; i < bdcqzhMap.size(); i++) {
                        if (i == 0) {
                            bdcqzh.append(bdcqzhMap.get(i).getBdcqzh());
                        } else {
                            bdcqzh.append(",").append(bdcqzhMap.get(i).getBdcqzh());
                        }
                    }
                }
                bdcYdySuccessRequest.setBdcdydjzmh(bdcqzh.toString());
                //查询dyrxx
                Example dyrExample = new Example(BdcQlrDO.class);
                dyrExample.createCriteria().andEqualTo("xmid", bdcXmList.get(0).getXmid()).andEqualTo("qlrlb", CommonConstantUtils.QLRLB_YWR_DM);
                List<BdcQlrDO> dyrList = entityMapper.selectByExample(dyrExample);
                List<BdcDyrxxDTO> dyrxxs = new ArrayList<>(4);
                if (CollectionUtils.isNotEmpty(dyrList)) {
                    for (BdcQlrDO bdcQlrDO : dyrList) {
                        BdcDyrxxDTO dyrxxDTO = BdcDyrxxDTO.BdcDyrxxDTOBuilder.aBdcDyrxxDTO()
                                .withDyrxm(bdcQlrDO.getQlrmc())
                                .withDyrzjhm(bdcQlrDO.getZjh())
                                .withDyrlxdh(bdcQlrDO.getDh())
                                .build();
                        dyrxxs.add(dyrxxDTO);
                    }
                }
                bdcYdySuccessRequest.setDyrxx(dyrxxs);
                //查询dy到期时间
                Example dyxxExample = new Example(BdcDyaqDO.class);
                dyxxExample.createCriteria().andEqualTo("xmid", bdcXmList.get(0).getXmid());
                List<BdcDyaqDO> bdcDyaqDOS = entityMapper.selectByExample(dyxxExample);
                if (CollectionUtils.isNotEmpty(bdcDyaqDOS)){
                    bdcYdySuccessRequest.setDyqdqrq(new SimpleDateFormat("yyyy-MM-dd").format(bdcDyaqDOS.get(0).getZwlxjssj()));
                }else {
                    throw new RuntimeException("未查询到关联的dyq信息,gzlslid:" + processInsId);
                }
                Map<String, Object> requestParam = new HashMap<>(2);
                requestParam.put("head", initHeadParam(bdcXmList.get(0).getXmid(),sctradeCode));
                requestParam.put("body", bdcYdySuccessRequest);
                Object response = exchangeInterfaceFeignService.requestInterface(SCDY_INTERFACE, requestParam);
                if (response != null) {
                    return dealWithResponse(response);
                } else {
                    throw new RuntimeException("公积金接口调用无返回,gzlslid:" + processInsId);
                }
            }
            throw new RuntimeException("未查询到相关的项目信息,gzlslid:" + processInsId);
        }else if (GjjActionTypeEnum.DELETE.getCode().equals(actionType)){
            return deleteOrReturnGjjCallback(processInsId, reason,SCDY_INTERFACE,sctradeCode);
        }
        throw new RuntimeException("当前操作类型错误,actionType:" + actionType);
    }

    /**
     * 盐城_公积金注销押回调接口
     *
     * @param processInsId
     * @return
     */
    @Override
    public CommonResponse zxdyGjjCallback(String processInsId,String actionType, String reason) {
        LOGGER.info("当前盐城_公积金注销押回调接口入参:{},actionType:{},reson:{}", processInsId,actionType, reason);
        if (GjjActionTypeEnum.SUCCESS.getCode().equals(actionType)){
            Example example = new Example(BdcXmDO.class);
            example.createCriteria().andEqualTo("gzlslid", processInsId).andEqualTo("qllx",CommonConstantUtils.QLLX_DYAQ_DM);
            List<BdcXmDO> bdcXmList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                Map<String, Object> requestParam = new HashMap<>(2);
                requestParam.put("head", initHeadParam(bdcXmList.get(0).getXmid(),zxtradeCode));
                Map<String, Object> bodyParam = new HashMap<>(4);
                bodyParam.put("ywslbh", bdcXmList.get(0).getSlbh());
                bodyParam.put("djzt", "1");
                bodyParam.put("dyqzxrq", new SimpleDateFormat("yyyy-MM-dd").format(bdcXmList.get(0).getDjsj()));
                bodyParam.put("msg", "success");
                requestParam.put("body", bodyParam);
                Object response = exchangeInterfaceFeignService.requestInterface(ZXDY_INTERFACE, requestParam);
                if (response != null) {
                    return dealWithResponse(response);
                } else {
                    throw new RuntimeException("公积金接口调用无返回,gzlslid:" + processInsId);
                }
            }
        }else if (GjjActionTypeEnum.DELETE.getCode().equals(actionType)){
            return deleteOrReturnGjjCallback(processInsId, reason,ZXDY_INTERFACE,zxtradeCode);
        }
        throw new RuntimeException("当前操作类型错误,actionType:" + actionType);
    }

    @NotNull
    private CommonResponse<Object> dealWithResponse(Object response) {
        JSONObject responseJson = JSON.parseObject(JSON.toJSONString(response));
        if (responseJson.containsKey("head")
                && StringUtils.isNotBlank(responseJson.getJSONObject("head").getString("responseCode"))
                && "0000".equals(responseJson.getJSONObject("head").getString("responseCode"))
                && responseJson.containsKey("body")
                && StringUtils.isNotBlank(responseJson.getJSONObject("body").getString("jszt"))
                && "1".equals(responseJson.getJSONObject("body").getString("jszt"))) {
            return CommonResponse.ok();
        } else {
            throw new RuntimeException("公积金接口调用报错:" + JSON.toJSONString(response));
        }
    }

    @Value("${yancheng.gjj.interface.head.secretKey:}")
    private String secretKey;
    @Value("${yancheng.gjj.interface.head.sendNodeNo:}")
    private String sendNodeNo;

    /**
     * {
     * "secretKey" : " c1f9a14d2d9c84be2d47",
     * "sendDateTime" : "2018-12-14 11:53:35",
     * "sendNodeNo" : " 109fe24a92bcb403eb5d",
     * "tradeCode" : "BDCXB300",
     * "sendSequenceNo" : "92388322-5b0d-4995-817f-cf309c45b8d0"
     * }
     *
     * @return
     */
    private Map<String, Object> initHeadParam(String xmid,String tradeCode) {
        Map<String, Object> headParamMap = new HashMap<>();
        headParamMap.put("secretKey", secretKey);
        headParamMap.put("sendDateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        headParamMap.put("sendNodeNo", sendNodeNo);
        headParamMap.put("tradeCode", tradeCode);
        headParamMap.put("sendSequenceNo", UUIDGenerator.generate());
        return headParamMap;
    }

}
