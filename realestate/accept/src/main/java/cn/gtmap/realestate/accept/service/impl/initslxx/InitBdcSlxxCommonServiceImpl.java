package cn.gtmap.realestate.accept.service.impl.initslxx;

import cn.gtmap.realestate.accept.core.service.BdcSlJyxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlSqrService;
import cn.gtmap.realestate.accept.service.BdcAddGwcSjclCommonService;
import cn.gtmap.realestate.accept.service.InitBdcSlxxService;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlxxInitDTO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcCfxxPageResponseDTO;
import cn.gtmap.realestate.common.core.qo.accept.InitSlxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcCfQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/5/30
 * @description 通用流程初始化受理信息
 */
@Service
public class InitBdcSlxxCommonServiceImpl implements InitBdcSlxxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitBdcSlxxCommonServiceImpl.class);

    @Autowired
    BdcSlJyxxService bdcSlJyxxService;
    @Autowired
    BdcSlSqrService bdcSlSqrService;
    @Autowired
    BdcAddGwcSjclCommonService bdcAddGwcSjclCommonService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcDjbxxFeignService bdcDjbxxFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    /**
     * 抵押查封不换证
     */
    @Value("#{'${accept.lcbs.withdycfbhz:}'.split(',')}")
    private List<String> withdycfbhz;

    @Value("#{'${sfcj.gzldyid:}'.split(',')}")
    private List<String> sfcjGzldyidList;

    @Value("#{'${sfcjzy.gzldyid:}'.split(',')}")
    private List<String> sfcjZyGzldyidList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  解封产权合并流程
     */
    @Value("#{'${lcbs.jfcqhb:}'.split(',')}")
    private List<String> jfcqhbGzldyidList;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 解押产权合并流程
     */
    @Value("#{'${lcbs.dyzxcqhb:}'.split(',')}")
    private List<String> jycqhbGzldyidList;

    @Value("#{'${spf.gzldyid:}'.split(',')}")
    private List<String> spflcGzldyidList;

    @Override
    public BdcSlxxInitDTO initBdcSlxx(InitSlxxQO initSlxxQO) {
        BdcSlxxInitDTO bdcSlxxInitDTO = new BdcSlxxInitDTO();
        List<BdcSlXmDO> bdcSlXmDOList = new ArrayList<>();
        List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = new ArrayList<>();
        //解封产权合并中的解封项目
        boolean isJfCqhb = CollectionUtils.isNotEmpty(jfcqhbGzldyidList) && jfcqhbGzldyidList.contains(initSlxxQO.getGzldyid()) && CommonConstantUtils.QLLX_CF.equals(initSlxxQO.getBdcSlYwxxDTO().getQllx()) && initSlxxQO.getSxh() == 1;
        //解押产权合并中的解押项目
        boolean isJyCqhb = CollectionUtils.isNotEmpty(jycqhbGzldyidList) && jycqhbGzldyidList.contains(initSlxxQO.getGzldyid()) && CommonConstantUtils.QLLX_DYAQ_DM.equals(initSlxxQO.getBdcSlYwxxDTO().getQllx()) && initSlxxQO.getSxh() == 1;
        //自动生成解封项目或解押项目
        if(isJfCqhb ||isJyCqhb) {
            initJfJyxm(initSlxxQO, bdcSlXmDOList, bdcSlXmLsgxDOList);
            LOGGER.info("解封产权合并或解押产权合并流程,组织受理项目数据:{}",bdcSlXmDOList);
        }else {
            BdcSlXmDO bdcSlXmDO = bdcAddGwcSjclCommonService.changeYwxxDtoIntoBdcSlXm(initSlxxQO.getBdcSlYwxxDTO(), initSlxxQO.getCzrid(), initSlxxQO.getJbxxid());
            bdcSlXmDOList.add(bdcSlXmDO);
            if (StringUtils.isNotBlank(initSlxxQO.getYxmid())) {
                BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), initSlxxQO.getYxmid(), "", "", "");
                bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
                //生成对应项目与原抵押证明号关系
                bdcAddGwcSjclCommonService.generateXmidDyzmhMap(bdcSlXmDO.getXmid(), initSlxxQO.getYxmid(), initSlxxQO.getXmidDyzmhMap());
            }

            //生成外联历史关系
            List<BdcSlXmLsgxDO> wlXmlsgxList = bdcAddGwcSjclCommonService.initWlXmlsgx(initSlxxQO, bdcSlXmDO);
            if (CollectionUtils.isNotEmpty(wlXmlsgxList)) {
                bdcSlXmLsgxDOList.addAll(wlXmlsgxList);
            }

            //抵押证明,查封不换证,建立查封抵押与新产权的关联关系
            if (CollectionUtils.isNotEmpty(withdycfbhz) && withdycfbhz.contains(initSlxxQO.getGzldyid()) && !ArrayUtils.contains(CommonConstantUtils.BDC_QLLX_XZQL, bdcSlXmDO.getQllx())) {
                if (StringUtils.isNotBlank(initSlxxQO.getBdcSlYwxxDTO().getBdcdyh())) {
                    List<String> qllxs = new ArrayList<>();
                    qllxs.add(CommonConstantUtils.QLLX_DYAQ_DM.toString());
                    qllxs.add(CommonConstantUtils.QLLX_CF.toString());
                    BdcXmQO bdcXmQO = new BdcXmQO();
                    bdcXmQO.setQllxs(qllxs);
                    bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
                    bdcXmQO.setBdcdyh(initSlxxQO.getBdcSlYwxxDTO().getBdcdyh());
                    List<BdcXmDO> dycfXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
                    if (CollectionUtils.isNotEmpty(dycfXmList)) {
                        for (BdcXmDO dycfxm : dycfXmList) {
                            BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(dycfxm.getXmid(), bdcSlXmDO.getXmid(), "", "", "");
                            bdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_F_DM);
                            bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
                        }
                    }
                }
            }

            //司法裁决流程把除了当前选择的查封之外的信息作为外联
            if (CollectionUtils.isNotEmpty(sfcjGzldyidList) && sfcjGzldyidList.contains(initSlxxQO.getGzldyid()) || CollectionUtils.isNotEmpty(sfcjZyGzldyidList) && sfcjZyGzldyidList.contains(initSlxxQO.getGzldyid())) {
                if (StringUtils.isNotBlank(initSlxxQO.getBdcSlYwxxDTO().getBdcdyh())) {
                    //查询单元号在查封表的 除查封外的查封信息
                    BdcCfQO bdcCfQO = new BdcCfQO();
                    bdcCfQO.setBdcdyh(Collections.singletonList(initSlxxQO.getBdcSlYwxxDTO().getBdcdyh()));
                    bdcCfQO.setCflxList(Arrays.asList(1, 2, 3, 4, 5));
                    List<BdcCfxxPageResponseDTO> bdcCfxxPageResponseDTOList = bdcXmFeignService.bdcCfxxList(bdcCfQO);
                    if (CollectionUtils.isNotEmpty(bdcCfxxPageResponseDTOList)) {
                        for (BdcCfxxPageResponseDTO bdcCfxxPageResponseDTO : bdcCfxxPageResponseDTOList) {
                            //司法裁决排除当前选择的数据
                            if (sfcjGzldyidList.contains(initSlxxQO.getGzldyid()) && StringUtils.isNotBlank(initSlxxQO.getYxmid()) && StringUtils.equals(initSlxxQO.getYxmid(), bdcCfxxPageResponseDTO.getXmid())) {
                                //当前选择数据的xmid 和 查封的项目id 相同执行下一个循环
                                continue;
                            }
                            BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), bdcCfxxPageResponseDTO.getXmid(), "", "", "");
                            if (sfcjZyGzldyidList.contains(initSlxxQO.getGzldyid())) {
                                //司法裁决转移注销上一手
                                bdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
                            } else {
                                bdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_F_DM);
                            }
                            bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                            bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
                        }
                    }
                    if (sfcjZyGzldyidList.contains(initSlxxQO.getGzldyid())) {
                        //司法裁决转移
                        wlDyaq(initSlxxQO.getBdcSlYwxxDTO().getBdcdyh(), bdcSlXmDO.getXmid(), bdcSlXmLsgxDOList);
                    }
                }
            }
            //判断存在房产交易信息
            try {
                if (CollectionUtils.isNotEmpty(spflcGzldyidList) && spflcGzldyidList.contains(initSlxxQO.getGzldyid()) && initSlxxQO.getBdcSlYwxxDTO().getDrjyxx() && StringUtils.isNotBlank(bdcSlXmDO.getBdcdyh())) {
                    //先根据单元号获取合同编号
                    Map paramMap = new HashMap<>(1);
                    paramMap.put("bdcdyh", bdcSlXmDO.getBdcdyh());
                    paramMap.put("currentPage", 1);
                    paramMap.put("pageSize", 10);
                    LOGGER.warn("商品房流程查询交易信息接口入参{}", paramMap);
                    Object response = exchangeInterfaceFeignService.requestInterface("xcFcjySpfQlr", paramMap);
                    LOGGER.warn("当前单元号{}查询交易信息接口返回值{}", bdcSlXmDO.getBdcdyh(), JSON.toJSONString(response));
                    if (Objects.nonNull(response)) {
                        Map resultMap = (Map) response;
                        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(resultMap.get("data")));
                        if (CollectionUtils.isNotEmpty(jsonArray)) {
                            JSONObject jsonObject = (jsonArray.getJSONObject(0));
                            String htbh = jsonObject.getString("htbh");
                            if (StringUtils.isNotBlank(htbh)) {
                                Map jyxxParamMap = new HashMap(1);
                                jyxxParamMap.put("htbh", htbh);
                                Object jyxxResponse = exchangeInterfaceFeignService.requestInterface("xcFcjySpfBaxx", jyxxParamMap);
                                LOGGER.warn("当前合同编号{}获取到交易信息结果{}", htbh, JSON.toJSONString(jyxxResponse));
                                if (Objects.nonNull(jyxxResponse)) {
                                    FcjyBaxxDTO fcjyBaxxDTO = JSON.parseObject(JSON.toJSONString(jyxxResponse), FcjyBaxxDTO.class);
                                    initSlxxQO.getBdcSlYwxxDTO().setFcjyBaxxDTO(fcjyBaxxDTO);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("获取交易信息存在异常", e);
            }

            if (initSlxxQO.getBdcSlYwxxDTO().getFcjyBaxxDTO() != null) {
                bdcSlJyxxService.dealFcjyBaxxDTO(initSlxxQO.getBdcSlYwxxDTO(), bdcSlXmDO.getXmid(), bdcSlxxInitDTO, initSlxxQO.getGzldyid());
                if (CommonConstantUtils.QLLX_CF.equals(initSlxxQO.getBdcSlYwxxDTO().getQllx()) && StringUtils.isBlank(initSlxxQO.getBdcSlYwxxDTO().getXmid()) && CollectionUtils.isNotEmpty(bdcSlxxInitDTO.getBdcSlSqrDOList())) {
                    //预查封流程，将权利人转为义务人
                    bdcSlxxInitDTO.setBdcSlSqrDOList(bdcSlSqrService.changeQlrToYwr(bdcSlxxInitDTO.getBdcSlSqrDOList()));
                }

                // 如果 当前qllx不是抵押  且 没有抵押的数据 则清除jyxx数据
                if (!CommonConstantUtils.QLLX_DYAQ_DM.equals(initSlxxQO.getBdcSlYwxxDTO().getQllx())
                        && (CollectionUtils.isEmpty(initSlxxQO.getBdcSlYwxxDTO().getFcjyBaxxDTO().getListBdcSlDyaqDTOSy()) && CollectionUtils.isEmpty(initSlxxQO.getBdcSlYwxxDTO().getFcjyBaxxDTO().getListBdcSlDyaqDTOGjj()))) {
                    //合并流程交易信息只带入一个项目,后面清空交易数据
                    initSlxxQO.getBdcSlYwxxDTO().setFcjyBaxxDTO(null);
                }
            }
        }


        bdcSlxxInitDTO.setBdcSlXmDOList(bdcSlXmDOList);
        bdcSlxxInitDTO.setBdcSlXmLsgxDOList(bdcSlXmLsgxDOList);
        return bdcSlxxInitDTO;

    }


    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description
      */
    private void wlDyaq(String bdcdyh,String xmid,List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList){
        if(StringUtils.isNotBlank(bdcdyh)) {
            //外联抵押
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setQszt(CommonConstantUtils.QSZT_VALID);
            bdcXmQO.setBdcdyh(bdcdyh);
            bdcXmQO.setQllxs(Arrays.asList(CommonConstantUtils.QLLX_DYAQ_DM));
            List<BdcXmDO> dyaqXmList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(dyaqXmList)) {
                for (BdcXmDO dyaqXm : dyaqXmList) {
                    BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(xmid, dyaqXm.getXmid(), "", "", "");
                    bdcSlXmLsgxDO.setZxyql(CommonConstantUtils.SF_S_DM);
                    bdcSlXmLsgxDO.setSfwlzs(CommonConstantUtils.SF_S_DM);
                    bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
                }
            }
        }

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自动生成解封解押项目
     */
    private void initJfJyxm(InitSlxxQO initSlxxQO,List<BdcSlXmDO> bdcSlXmDOList,List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList){

        List<Integer> qsztList = new ArrayList(1);
        qsztList.add(CommonConstantUtils.QSZT_VALID);
        List<BdcQl> bdcQlList = bdcDjbxxFeignService.listBdcQlxx(initSlxxQO.getBdcSlYwxxDTO().getBdcdyh(), initSlxxQO.getBdcSlYwxxDTO().getQllx().toString(), qsztList);
        if (CollectionUtils.isNotEmpty(bdcQlList)) {
            for (BdcQl bdcQl : bdcQlList) {
                BdcSlXmDO bdcSlXmDO = bdcAddGwcSjclCommonService.changeYwxxDtoIntoBdcSlXm(initSlxxQO.getBdcSlYwxxDTO(), initSlxxQO.getCzrid(), initSlxxQO.getJbxxid());
                bdcSlXmDOList.add(bdcSlXmDO);
                if (StringUtils.isNotBlank(bdcQl.getXmid())) {
                    BdcXmQO bdcXmQO =new BdcXmQO();
                    bdcXmQO.setXmid(bdcQl.getXmid());
                    List<BdcXmDO> bdcXmDOList =bdcXmFeignService.listBdcXm(bdcXmQO);
                    if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                        bdcSlXmDO.setQlr(bdcXmDOList.get(0).getQlr());
                    }
                    BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO(bdcSlXmDO.getXmid(), bdcQl.getXmid(), "", "", "");
                    bdcSlXmLsgxDOList.add(bdcSlXmLsgxDO);
                    //生成对应项目与原抵押证明号关系
                    bdcAddGwcSjclCommonService.generateXmidDyzmhMap(bdcSlXmDO.getXmid(),initSlxxQO.getYxmid(),initSlxxQO.getXmidDyzmhMap());
                }

            }

        }

    }


}
