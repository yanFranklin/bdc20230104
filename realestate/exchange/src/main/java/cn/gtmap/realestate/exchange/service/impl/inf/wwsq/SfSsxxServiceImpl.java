package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfSsxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.IllegalArgumentException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfSsxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlHsxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfssDdxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.wwsq.ResponseHead;
import cn.gtmap.realestate.exchange.core.dto.wwsq.jssfsszt.request.BdcJsSfssztRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.sfssxx.request.BdcSfSsxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.sfssxx.response.BdcSfSsxxResponseBdcSfxxDTOS;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.wwsq.GxWwSqxxService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-02
 * @description 收费收税信息 接口相关
 */
@Service
public class SfSsxxServiceImpl {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SfSsxxServiceImpl.class);


    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;

    @Autowired
    private BdcSlSfssDdxxFeignService bdcSlSfssDdxxFeignService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private GxWwSqxxService gxWwSqxxService;

    @Autowired
    BdcSlHsxxFeignService bdcSlHsxxFeignService;
    @Autowired
    private BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    /**
     * @param requestObject
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.accept.BdcSfSsxxDTO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 查询收费收税信息
     */
    public List<BdcSfSsxxDTO> listBdcSfSsxx(JSONObject requestObject) {
        List<BdcSfSsxxDTO> resultList = new ArrayList<>();
        BdcSfSsxxRequestDTO requestDTO = JSONObject.toJavaObject(requestObject, BdcSfSsxxRequestDTO.class);
        if(StringUtils.equals(CommonConstantUtils.SF_S_DM.toString(), requestDTO.getSfyj())){
            //是否月结为是,根据月结单号查询
            BdcSfSsxxQO bdcSfSsxxQO =new BdcSfSsxxQO();
            bdcSfSsxxQO.setYjdh(requestDTO.getSlbh());
            bdcSfSsxxQO.setSqrlb(requestDTO.getQlrlb());
            resultList =bdcSlSfxxFeignService.listYjBdcSfSsxxDTO(bdcSfSsxxQO);
            if(CollectionUtils.isEmpty(resultList)){
                resultList =Collections.emptyList();
            }
            return resultList;
        }

        List<Map> paramMapList = new ArrayList<>();
        if (StringUtils.isNotBlank(requestDTO.getXmid())) {
            setParamListWithXmid(requestDTO.getXmid(), requestDTO.getQlrlb(), paramMapList);
        } else if (StringUtils.isNotBlank(requestDTO.getSlbh())) {
            // 如果受理编号不为空 则需要查询所有xmid
            List<BdcXmDO> bdcXmDOList = this.queryBdcXmxxBySlbh(requestDTO);
            // 判断流程类型，批量流程和单个流程使用工作流实例ID, 查询收费信息即可； 批量组合和组合流程根据项目ID，进行查询
            if (CollectionUtils.isEmpty(bdcXmDOList)) {
                throw new AppException("该受理编号无项目数据，请核查！");
            }
            int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmDOList);
            if (CommonConstantUtils.LCLX_PLZH.equals(lclx) || CommonConstantUtils.LCLX_ZH.equals(lclx)) {
                Map<String, BdcXmDO> slbhAndXmidListMap = bdcXmDOList.parallelStream().collect(Collectors.groupingBy(BdcXmDO::getDjxl,
                        Collectors.collectingAndThen(Collectors.reducing((t1, t2) -> t1.getXmid().compareTo(t2.getXmid()) < 0 ? t1 : t2), Optional::get)));
                for (String key : slbhAndXmidListMap.keySet()) {
                    setParamListWithXmid(slbhAndXmidListMap.get(key).getXmid(), requestDTO.getQlrlb(), paramMapList);
                }
            } else {
                // 批量、单个流程采用工作流实例ID进行查询
                setParamListWithXmid(bdcXmDOList.get(0).getXmid(), requestDTO.getQlrlb(), paramMapList);
            }
        }
        if (CollectionUtils.isEmpty(paramMapList)) {
            throw new IllegalArgumentException("查询参数为空");
        }
        // 循环调用受理子系统的接口 查询
        for (Map paramMap : paramMapList) {
            BdcSfSsxxQO bdcSfSsxxQO =new BdcSfSsxxQO();
            bdcSfSsxxQO.setXmid(MapUtils.getString(paramMap, "xmid"));
            bdcSfSsxxQO.setCxbz(requestDTO.getCxbz());
            bdcSfSsxxQO.setSqrlb(MapUtils.getString(paramMap, "qlrlb"));
            BdcSfSsxxDTO bdcSfSsxxDTO = bdcSlSfxxFeignService.queryBdcSfSsxxDTO(bdcSfSsxxQO);
            if(CheckParameter.checkAnyParameter(bdcSfSsxxDTO)){
                if(CollectionUtils.isNotEmpty(bdcSfSsxxDTO.getBdcSwxxDTOS())
                        || CollectionUtils.isNotEmpty(bdcSfSsxxDTO.getBdcSfxxDTOS())){
                    resultList.add(bdcSfSsxxDTO);
                }
            }
        }
        return resultList;
    }

    private void setParamListWithXmid(String xmid, String qlrlb, List<Map> paramMapList) {
        // 如果权利人类别不为空 则查对应权利人的 收费收税信息
        if (StringUtils.isNotBlank(qlrlb)) {
            Map<String, String> map = new HashMap<>();
            map.put("xmid", xmid);
            map.put("qlrlb", qlrlb);
            paramMapList.add(map);
        } else {
            // 如果权利人类别为空，则需要查询权利人和义务人的收费收税信息
            Map<String, String> map = new HashMap<>();
            map.put("xmid", xmid);
            map.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
            paramMapList.add(map);
            Map<String, String> map2 = new HashMap<>();
            map2.put("xmid", xmid);
            map2.put("qlrlb", CommonConstantUtils.QLRLB_YWR);
            paramMapList.add(map2);
        }
    }

    /**
     * 缴费方式为合一支付时，在生成订单时nwslbh使用（受理编号+权利人类别）, 所以此处获取缴费明细信息时，互联网传的可能（受理编号+权利人类别）得受理编号进行查询
     * 先根据slbh 查询 bdc_xm 表，没有数据时，在使用slbh作为 sfglid 查询订单信息表
     */
    private List<BdcXmDO> queryBdcXmxxBySlbh(BdcSfSsxxRequestDTO requestDTO){
        String slbh = requestDTO.getSlbh();
        // 1、先根据slbh 查询 bdc_xm 表
        List<BdcXmDO> bdcXmDOList = commonService.listBdcXmBySlbh(slbh);
        if(CollectionUtils.isNotEmpty(bdcXmDOList)){
            return bdcXmDOList;
        }
        // 2、没有数据时，使用slbh作为sjglid 查询订单信息表
        BdcSlSfssDdxxQO queryParam = new BdcSlSfssDdxxQO();
        queryParam.setSfglid(slbh);
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = this.bdcSlSfssDdxxFeignService.listBdcSlSfssDdxx(queryParam);
        if(CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)){
            String gzlslid = bdcSlSfssDdxxDOList.get(0).getGzlslid();
            String qlrlb =  bdcSlSfssDdxxDOList.get(0).getQlrlb();
            if(StringUtils.isNotBlank(qlrlb)){
                requestDTO.setQlrlb(qlrlb);
            }
            List<BdcXmDO> bdcXmDOList1 = commonService.listBdcXmByGzlslid(gzlslid);
            if(CollectionUtils.isNotEmpty(bdcXmDOList1)){
                return bdcXmDOList1;
            }
        }
        return null;
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param jsonObject
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description 接收缴费状态
     */
    public Map<String,Object> jsJfzt(JSONObject jsonObject){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("msg","更新成功");
        resultMap.put("success",true);
        if(jsonObject != null){
            BdcJsSfssztRequestDTO requestDTO = JSONObject.toJavaObject(jsonObject,BdcJsSfssztRequestDTO.class);
            if(CollectionUtils.isNotEmpty(requestDTO.getBdcSfxxDTOS())
                    || CollectionUtils.isNotEmpty(requestDTO.getBdcSwxxDTOS())){
                try {
                    if(CollectionUtils.isNotEmpty(requestDTO.getBdcSfxxDTOS())){
                        // 互联网 缴款方式 赋值 2 线上缴费
                        for(BdcSfSsxxResponseBdcSfxxDTOS sfxxTemp: requestDTO.getBdcSfxxDTOS()){
                            sfxxTemp.getBdcSlSfxxDO().setJkfs(CommonConstantUtils.JKFS_XSJF);
                        }
                    }
                    BdcSfSsxxDTO dto = new BdcSfSsxxDTO();
                    BeanUtils.copyProperties(requestDTO,dto);
                    bdcSlSfxxFeignService.updateSfSszt(dto);
                }catch (Exception e){
                    LOGGER.error("保存收费状态异常",e);
                    resultMap.put("msg","保存收费状态异常");
                    resultMap.put("success",false);
                }

                // 一卡清推送过来jfType为“DK”时，不调用缴库接口
                if(MapUtils.getBooleanValue(resultMap,"success") && !StringUtils.equals("DK", requestDTO.getJfType())){
                    // 接受成功后 触发调用互联网+缴库接口
                    String slbh = requestDTO.getSlbh();
                    String sfyj =CommonConstantUtils.SF_F_DM.toString();
                    if(StringUtils.isNotBlank(requestDTO.getSfyj())){
                        sfyj =requestDTO.getSfyj();
                    }

                    if(StringUtils.isNotBlank(slbh)){
                        try {
                            // 处理三要素信息  后  调用外网 推送缴库入库接口
                            getSysAndTsjkrk(slbh,sfyj);
                        }catch (Exception e){
                            LOGGER.error("推送缴库入库处理异常",e);
                            resultMap.put("msg","推送缴库入库处理异常");
                            resultMap.put("success",false);
                        }
                    }
                }
            }
        }else{
            resultMap.put("msg","参数错误");
            resultMap.put("success",false);
        }
        return resultMap;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param slbh
     * @return void
     * @description 处理三要素信息 并 操作推送缴库入库
     */
    public ResponseHead getSysAndTsjkrk(String slbh,String sfyj) {
        if (StringUtils.isBlank(slbh)) {
            throw new MissingArgumentException("slbh");
        }
        Map<String, Object> slbhMap = new HashMap<>();
        slbhMap.put("slbh", slbh);
        slbhMap.put("sfyj", sfyj);
        slbhMap.put("fwuuid", this.getFwuuidBySlbh(slbh));
        //外网 获取三要素接口
        // 逻辑为：先查询受理子系统三要素
        // 如果受理子系统存在 则直接返回，
        // 如果受理子系统不存在 则调用税务接口 保存到受理子系统之后 返回税务三要素
        JSONObject syxxResult = gxWwSqxxService.wwsqCxSwsys(JSONObject.parseObject(JSONObject.toJSONString(slbhMap)));
        boolean tsjkrk = true;
        /**
         * 由于拆迁安置抵扣契税买方无需缴税，所以去除税费三要素验证
         */
//        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList) && ((bdcSlHsxxDOList.get(0).getYnsehj() == null ? NumberUtils.toDouble("0") : bdcSlHsxxDOList.get(0).getYnsehj()) <= 0 ||
//                (StringUtils.isBlank(bdcSlHsxxDOList.get(0).getSwjgdm()) || StringUtils.isBlank(bdcSlHsxxDOList.get(0).getNsrsbh())
//                        || StringUtils.isBlank(bdcSlHsxxDOList.get(0).getSphm())))) {
//            tsjkrk = false;
//        }
        // bdc_sl_hsxx.ynsehj 为0的数据 或者验证三要素是否完整中三要素不完整的数据，不允许调取缴库接口
        BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(slbh, "1");
        if (bdcSlJbxxDO != null && StringUtils.isNotBlank(bdcSlJbxxDO.getGzlslid())) {
            List<BdcSlSfxxDO> bdcSlSfxxDOList = bdcSlSfxxFeignService.listBdcSlSfxxByGzlslid(bdcSlJbxxDO.getGzlslid());
            if (CollectionUtils.isNotEmpty(bdcSlSfxxDOList)) {
                if ((bdcSlSfxxDOList.get(0).getHj() == null ? NumberUtils.toDouble("0") : bdcSlSfxxDOList.get(0).getHj()) <= 0) {
                    tsjkrk = false;
                }
            }
        }
        if (tsjkrk) {
            // 将sysResult 保存到 受理库
            JSONObject sysxxJsonObject = new JSONObject();
            sysxxJsonObject.put("slbh", slbh);
            sysxxJsonObject.put("sfyj", StringUtils.isBlank(sfyj)? CommonConstantUtils.SF_F_DM.toString():sfyj);
            if (syxxResult != null && syxxResult.get("result") != null) {
                sysxxJsonObject.put("sysxx", syxxResult.get("result"));
            }
            LOGGER.info("动推送给外网的三要素为：{}", sysxxJsonObject.toString());
            // 主动推送给外网
            return exchangeBeanRequestService.request("wwsq_tsswsys", sysxxJsonObject, ResponseHead.class);
        }
        ResponseHead responseHead = new ResponseHead();
        responseHead.setMsg("无可以缴库的数据");
        responseHead.setStatusCode(Constants.CODE_SEARCH_ERROR);
        return responseHead;
    }

    /**
     * 根据受理编号获取不动产受理基本信息中的 fwuuid （税务唯一标识）
     */
    private String getFwuuidBySlbh(String slbh){
        if(StringUtils.isNotBlank(slbh)){
            BdcSlJbxxDO bdcSlJbxxDO = this.bdcSlJbxxFeignService.queryBdcSlJbxxBySlbh(slbh, "");
            if(Objects.nonNull(bdcSlJbxxDO) && StringUtils.isNotBlank(bdcSlJbxxDO.getFwuuid())){
                return bdcSlJbxxDO.getFwuuid();
            }else{
                LOGGER.error("获取税务fwuuid异常，未获取到受理基本信息，受理编号：{}", slbh);
            }
        }else{
            LOGGER.error("获取税务fwuuid异常，未获取到受理编号。");
        }
        return "";
    }
}
