package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.gtc.workflow.domain.manage.ProcessInstanceData;
import cn.gtmap.gtc.workflow.domain.manage.UserTaskDto;
import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcDjxlPzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcDjxlPzRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.matcher.ProcessInstanceClientMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.register.service.BdcDypzService;
import cn.gtmap.realestate.register.service.BdcPrintService;
import cn.gtmap.realestate.register.service.BdcShbdPrintService;
import cn.gtmap.realestate.register.service.BdcXmxxService;
import cn.gtmap.realestate.register.util.Constants;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/21
 * @description ?????????????????????
 */
@Service
public class BdcShbdPrintServiceImpl implements BdcShbdPrintService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcPrintService bdcPrintService;
    @Autowired
    BdcSlSjclFeignService bdcSlSjclFeignService;
    @Autowired
    BdcSlPrintFeignService bdcSlPrintFeignService;
    @Autowired
    BdcXmxxService bdcXmxxService;
    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;
    @Autowired
    ProcessInstanceClientMatcher processInstanceClient;
    @Autowired
    BdcDjxlPzRestService bdcDjxlPzRestService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcZdFeignService bdcZdFeignService;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    BdcDypzService bdcDypzService;
    @Autowired
    BdcDysjPzService bdcDysjPzService;

    /**
     * ????????????
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcShbdPrintServiceImpl.class.getName());
    /**
     * ????????????????????????????????????????????????????????????????????????3???
     */
    @Value("${shxx.jd-number:3}")
    String jdNum;
    /**
     * ?????????????????? ?????????????????????????????????????????????
     */
    @Value("${shxx.fwyt-fzf-tszf: ??????,??????,??????}")
    String fzfytStr;
    @Value("#{'${shxx.yszlc.gzldyid:}'.split(',')}")
    List<String> yszGzldyidList;

    @Value("#{'${print.spb.dylx:}'.split(',')}")
    private List<String> spbDylxList;

    @Value("#{'${print.onespb.dylx:}'.split(',')}")
    private List<String> oneSpbDylxList;

    /**
     * @param bdcPrintDTO ????????????
     * @return String ??????xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ???????????????xml
     */
    @Override
    public String bdPrintXml(BdcPrintDTO bdcPrintDTO) {
        if ((StringUtils.isBlank(bdcPrintDTO.getGzlslid()) && StringUtils.isBlank(bdcPrintDTO.getGzlslids())) || StringUtils.isBlank(bdcPrintDTO.getDylx())) {
            throw new MissingArgumentException("?????????????????????gzlslid???dylx???");
        }
        List<Map> maps = new ArrayList<>(1);
        List<Map> resultMaps = new ArrayList<>(1);
        if (StringUtils.isNotBlank(bdcPrintDTO.getGzlslids())) {
            String[] gzlslidArray = bdcPrintDTO.getGzlslids().split(CommonConstantUtils.ZF_YW_DH);
            for (String gzlslid : gzlslidArray) {
                BdcPrintDTO printDTO = new BdcPrintDTO();
                printDTO.setGzlslid(gzlslid);
                printDTO.setDylx(bdcPrintDTO.getDylx());
                printDTO.setBdcUrlDTO(bdcPrintDTO.getBdcUrlDTO());
                printDTO.setPrintMode(bdcPrintDTO.getPrintMode());
                List jdmcList = new ArrayList();
                List<UserTaskDto> userTaskDataList = workFlowUtils.listShjdxx(gzlslid);
                userTaskDataList.forEach(taskData -> {
                    jdmcList.add(taskData.getName());
                });
                printDTO.setJdmcList(jdmcList);
                maps = getPrintMaps(printDTO);
                resultMaps.addAll(maps);
            }
        } else {
            maps = getPrintMaps(bdcPrintDTO);
            resultMaps.addAll(maps);
        }
        Map<String, List<Map>> paramMap = new HashMap(1);
        paramMap.put(bdcPrintDTO.getDylx(), resultMaps);
        return bdcPrintService.print(paramMap);
    }

    /**
     * @param bdcPrintDTO ????????????
     * @param clmc        ????????????
     * @return Map<String, String>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description ??????????????????
     */
    private Map<String, Object> initParam(BdcPrintDTO bdcPrintDTO, String clmc) {
        Map<String, Object> map = new HashMap(5);
        map.put("gzlslid", bdcPrintDTO.getGzlslid());
        map.put("xmid", bdcPrintDTO.getXmid());
        map.put("clmc", clmc);
        map.put("signImageUrl", bdcPrintDTO.getBdcUrlDTO().getSignImageUrl());
        map.put("ewm", bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + bdcPrintDTO.getSlbh());
        map.put("djxl", bdcPrintDTO.getDjxlListStr());
        initMapJdmc(map, bdcPrintDTO.getJdmcList(), bdcPrintDTO.getGzlslid());
        // ???????????????????????????
        map.put("yxmid", "");
        if (null != bdcPrintDTO.getZxlc() && bdcPrintDTO.getZxlc()) {
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcPrintDTO.getXmid(), null, 0);
            if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                map.put("yxmid", bdcSlXmLsgxDOList.get(0).getYxmid());
            }
        }
        return map;
    }

    /**
     * @param xmid
     * @param gzlslid    ???????????????ID
     * @param sjclIdsStr
     * @return clmc
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ?????????????????????????????????
     */
    private String getClmc(String xmid, String gzlslid, String sjclIdsStr) {
        // ????????????????????????
        StringBuilder sjclMcSb = new StringBuilder();
        List<String> xmClmcList = bdcSlSjclFeignService.listSjclMc(gzlslid, sjclIdsStr);
        if (CollectionUtils.isNotEmpty(xmClmcList)) {
            for (int i = 1; i <= xmClmcList.size(); i++) {
                sjclMcSb.append(i).append(".").append(xmClmcList.get(i - 1)).append(" ");
            }
        }

        return sjclMcSb.toString();
    }

    /**
     * @param gzlslid ???????????????ID
     * @return List<String>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????????????????????????????????????????
     */
    @Override
    public Map<String, List<String>> getShxxDylx(String gzlslid) {
        Map<String, List<String>> dylxListMaps = new HashMap();
        if (StringUtils.isBlank(gzlslid)) {

            return dylxListMaps;
        }
        Map<String, Set<String>> dylxMaps = new HashMap();
        String[] djxlArr = bdcXmxxService.getListDjxlByGzlslid(gzlslid);
        ProcessInstanceData processInstanceData = processInstanceClient.getProcessInstance(gzlslid);
        List<BdcDjxlPzDO> bdcDjxlPzDOList = bdcDjxlPzRestService.queryBdcDjxlPzByGzldyid(processInstanceData.getProcessDefinitionKey());
//        Set<String> dylxSet = new HashSet();
        if (CollectionUtils.isNotEmpty(bdcDjxlPzDOList)) {
            for (BdcDjxlPzDO bdcDjxlPzDO : bdcDjxlPzDOList) {
                String djxl = bdcDjxlPzDO.getDjxl();
                if (StringUtils.isNotBlank(bdcDjxlPzDO.getSpbdylx()) && ArrayUtils.contains(djxlArr, djxl)) {
                    String[] spbdylxArr = StringUtils.split(bdcDjxlPzDO.getSpbdylx(), CommonConstantUtils.ZF_YW_DH);
                    for (String spbDylx : spbdylxArr) {
                        Set<String> djxlSet;
                        if (dylxMaps.containsKey(spbDylx)) {
                            djxlSet = dylxMaps.get(spbDylx);

                        } else {
                            djxlSet = new HashSet();
                        }
                        djxlSet.add(djxl);
                        dylxMaps.put(spbDylx, djxlSet);
                    }
                }
            }
        }
        // ???set?????????List
        for (Map.Entry<String, Set<String>> dylxSetMap : dylxMaps.entrySet()) {
            dylxListMaps.put(dylxSetMap.getKey(), new ArrayList(dylxSetMap.getValue()));
        }
        return dylxListMaps;
    }

    /**
     * @param bdcPrintDTO ????????????
     * @return String ??????xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ???????????????xml??????????????????
     */
    @Override
    public String bdPrintXmlNantong(BdcPrintDTO bdcPrintDTO) {
        if (StringUtils.isBlank(bdcPrintDTO.getGzlslid()) || StringUtils.isBlank(bdcPrintDTO.getDylx())) {
            throw new MissingArgumentException("?????????????????????gzlslid???dylx???");
        }
        String dylx = bdcPrintDTO.getDylx();
        List<Map> maps = new ArrayList();
        if (StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_SC, dylx)) {
            // ??????
            maps = initScSpbParam(bdcPrintDTO);
        } else if (StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_GR, dylx) || StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_DW, dylx) || StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_HY, dylx)) {
            // ?????? ??? ???????????????????????????????????????????????????????????????
            maps = initSpbParam(bdcPrintDTO);
        } else if (StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_GR_ZX, dylx) || StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_DW_ZX, dylx) || StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_HYZX, dylx)) {
            // ?????????????????????
            maps = initGrSpbZxParam(bdcPrintDTO);
        } else if (StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_DYA, dylx) || StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_PLHZ, dylx)) {
            // ???????????????(?????????????????????????????????????????????????????????????????????????????????)
            maps = initSpbParam(bdcPrintDTO);
        } else if (StringUtils.equals(Constants.DYLX_BDC_SQ_SPB_DYA_ZX, dylx)) {
            // ????????????
            maps = initDyaSpbZxParam(bdcPrintDTO);
        }

        // ??????????????????
        Map<String, List<Map>> paramMap = new HashMap(1);
        paramMap.put(dylx, maps);
        LOGGER.info("???????????????{}??????????????????ID???{}?????????????????????{}???", bdcPrintDTO.getDylx(), bdcPrintDTO.getGzlslid(), maps.toString());
        return bdcPrintService.print(paramMap);
    }

    @Override
    public BdcDysjDTO getPrintDataMap(BdcPrintDTO bdcPrintDTO) {
        if (StringUtils.isBlank(bdcPrintDTO.getSlbh())) {
            BdcXmDO bdcXmDO = bdcXmxxService.getBdcXmByXmid(bdcPrintDTO.getXmid());
            bdcPrintDTO.setSlbh(bdcXmDO.getSlbh());
            bdcPrintDTO.setDjxlListStr(bdcXmDO.getDjxl());
        }
        String clmc = this.getClmc(bdcPrintDTO.getXmid(), bdcPrintDTO.getGzlslid(), bdcPrintDTO.getSjclIdsStr());
        Map configParam = this.initParam(bdcPrintDTO, clmc);
        String dylx = bdcPrintDTO.getDylx();
        if (StringUtils.isEmpty(dylx)) {
            throw new MissingArgumentException("????????????");
        }
        /**
         * ??????????????????
         */
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOS = bdcDypzService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOS)) {
            throw new AppException("??????????????????????????????");
        }
        /**
         * ??????????????????
         */
        BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
        bdcDysjZbPzDO.setDylx(dylx);
        List<BdcDysjZbPzDO> bdcDysjZbPzDOS = bdcDypzService.listBdcDysjZbPz(bdcDysjZbPzDO);
        /**
         * ??????????????????????????????
         */
        Map dataMap = bdcDysjPzService.queryPrintDatasList(configParam, "bdcRegisterConfigMapper", bdcDysjPzDOS);
        /**
         * ??????????????????????????????
         */
        Multimap<String, List<Map>> detailMap = bdcDysjPzService.queryPrintDetailList(configParam, "bdcRegisterConfigMapper", bdcDysjZbPzDOS);
        BdcDysjDTO bdcDysjDTO = new BdcDysjDTO();
        bdcDysjDTO.setDysj(JSON.toJSONString(dataMap));
        bdcDysjDTO.setDyzd(JSON.toJSONString(detailMap));
        return bdcDysjDTO;
    }

    /**
     * @param bdcPrintDTO ??????????????????
     * @return ???????????????????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????????????????????????????
     */
    private List<Map> initSpbParam(BdcPrintDTO bdcPrintDTO) {
        String gzlslid = bdcPrintDTO.getGzlslid();
        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("???????????????ID???" + gzlslid + "???????????????????????????");
        }
        // ????????????????????????
        bdcPrintDTO.setSlbh(bdcXmDOList.get(0).getSlbh());

        List<BdcCshFwkgSlDO> bdcCshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcCshFwkgSlDOList)) {
            throw new MissingArgumentException("???????????????ID???" + gzlslid + "?????????????????????????????????????????????");
        }

        // ??????djxl??????
        Map<String, List<BdcCshFwkgSlDO>> djxlGroupMap = bdcCshFwkgSlDOList.stream().collect(groupingBy(BdcCshFwkgSlDO::getDjxl));
        if (CollectionUtils.size(djxlGroupMap) == 1) {
            return this.initSpbParamCommon(bdcPrintDTO, djxlGroupMap);
        }

        // ??????????????????1?????????????????????????????????????????????????????????????????????
        String[] djxlArr = StringUtils.split(bdcPrintDTO.getDjxlListStr(), CommonConstantUtils.ZF_YW_DH);
        Map<String, List<BdcCshFwkgSlDO>> djxlGroupMapTemp = new HashMap();
        for (String djxl : djxlArr) {
            for (Map.Entry<String, List<BdcCshFwkgSlDO>> djxlGroup : djxlGroupMap.entrySet()) {
                if (StringUtils.equals(djxl, djxlGroup.getKey())) {
                    djxlGroupMapTemp.put(djxlGroup.getKey(), djxlGroup.getValue());
                }
            }
        }
        return this.initSpbParamCommon(bdcPrintDTO, djxlGroupMapTemp);
    }

    /**
     * @param bdcPrintDTO  ??????????????????
     * @param djxlGroupMap ?????????????????????djxl?????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @return ??????????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????????????????????????????????????????????????????
     */
    private List<Map> initSpbParamCommon(BdcPrintDTO bdcPrintDTO, Map<String, List<BdcCshFwkgSlDO>> djxlGroupMap) {
        List<Map> maps = new ArrayList();
        // ??????djxl??????????????????
        for (Map.Entry<String, List<BdcCshFwkgSlDO>> djxlGroup : djxlGroupMap.entrySet()) {
            List<BdcCshFwkgSlDO> bdcCshFwkgSlByDjxl = djxlGroup.getValue();

            // ????????????????????????????????????????????????????????????
            List<BdcCshFwkgSlDO> zsxhIsNullList = bdcCshFwkgSlByDjxl.stream().filter(bdcCshFwkgSlDO -> null == bdcCshFwkgSlDO.getZsxh()).collect(Collectors.toList());
            for (BdcCshFwkgSlDO bdcCshFwkgSlDO : zsxhIsNullList) {
                Map<String, Object> map = new HashMap();
                initGrSpbPublicParam(map, bdcPrintDTO, bdcCshFwkgSlDO);
                String xmid = bdcCshFwkgSlDO.getId();
                BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
                map.put("zl", bdcXmDO.getZl());
                // ?????????????????????????????????????????????id??????????????????????????????zsxh????????????????????????xmid
                map.put("zsxh", xmid);
                maps.add(map);
            }

            // ?????????????????????????????????????????????????????????
            List<BdcCshFwkgSlDO> zsxhNotNullList = bdcCshFwkgSlByDjxl.stream().filter(bdcCshFwkgSlDO -> null != bdcCshFwkgSlDO.getZsxh()).collect(Collectors.toList());
            Map<Integer, List<BdcCshFwkgSlDO>> zsxhGroupMap = zsxhNotNullList.stream().collect(groupingBy(BdcCshFwkgSlDO::getZsxh));
            for (Map.Entry<Integer, List<BdcCshFwkgSlDO>> zsxhGroup : zsxhGroupMap.entrySet()) {
                List<BdcCshFwkgSlDO> bdcCshFwkgSlByZsxh = zsxhGroup.getValue();
                if (CollectionUtils.size(bdcCshFwkgSlByZsxh) > 1) {
                    // ???????????????
                    boolean yzf = false;
                    for (BdcCshFwkgSlDO bdcCshFwkgSlDO : bdcCshFwkgSlByZsxh) {
                        // ???????????????????????????
                        if (CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfzf())) {
                            yzf = true;
                            Map<String, Object> map = new HashMap();
                            initGrSpbPublicParam(map, bdcPrintDTO, bdcCshFwkgSlDO);
                            // ??????????????????"???"
                            map.put(Constants.ZL_SFX, "???");
                            // ???????????????????????????
                            map.put("zl", generateMulZl(bdcCshFwkgSlByZsxh, bdcCshFwkgSlDO, yzf));
                            maps.add(map);
                            break;
                        }
                    }
                    if (!yzf) {
                        // ??????????????????????????????????????????
                        Map<String, Object> map = new HashMap();
                        initGrSpbPublicParam(map, bdcPrintDTO, bdcCshFwkgSlByZsxh.get(0));
                        // ??????????????????"???"
                        map.put(Constants.ZL_SFX, "???");
                        // ???????????????????????????
                        map.put("zl", generateMulZl(bdcCshFwkgSlByZsxh, bdcCshFwkgSlByZsxh.get(0), yzf));
                        maps.add(map);
                    }
                } else {
                    Map<String, Object> map = new HashMap();
                    initGrSpbPublicParam(map, bdcPrintDTO, bdcCshFwkgSlByZsxh.get(0));
                    // ???????????????????????????
                    map.put("zl", generateMulZl(bdcCshFwkgSlByZsxh, bdcCshFwkgSlByZsxh.get(0), false));
                    maps.add(map);
                }
            }
        }
        return maps;
    }

    /**
     * @param bdcCshFwkgSlByZsxh ???????????????????????????
     * @param bdcCshFwkgSlDO     ??????????????????xmid ?????????????????????
     * @param yzf                ?????????????????????????????????
     * @return String ?????????????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????????????????????????????????????????????????????3?????????????????????????????????????????????+??????
     * ????????????3????????????????????????????????????????????????????????????????????????????????????
     */
    private String generateMulZl(List<BdcCshFwkgSlDO> bdcCshFwkgSlByZsxh, BdcCshFwkgSlDO bdcCshFwkgSlDO, boolean yzf) {
        int size = CollectionUtils.size(bdcCshFwkgSlByZsxh);
        String xmid = bdcCshFwkgSlDO.getId();
        BdcXmDO bdcXmDO = entityMapper.selectByPrimaryKey(BdcXmDO.class, xmid);
        String zl = bdcXmDO.getZl();
        if (size > 3) {
            return zl + "???";
        } else if (size <= 3 && size > 1) {
            // ????????????
            String glZl = "";
            // ????????????
            String ckzl = "";
            // ????????????
            String cwzl = "";
            StringBuilder zlBuilder = new StringBuilder(zl).append(CommonConstantUtils.ZF_YW_DH);
            if (yzf) {
                // ?????????
                List<Map> fwytZd = bdcZdFeignService.queryBdcZd("fwyt");
                for (BdcCshFwkgSlDO bdcCshFwkgSlDOTemp : bdcCshFwkgSlByZsxh) {
                    if (!StringUtils.equals(xmid, bdcCshFwkgSlDOTemp.getId())) {
                        BdcXmDO bdcXmDOTemp = entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcCshFwkgSlDOTemp.getId());
                        String fwyt = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getDzwyt(), fwytZd);
                        if (StringUtils.indexOf(fwyt, Constants.FWYT_GL) > -1) {
                            glZl = bdcXmDOTemp.getZl();
                            continue;
                        }
                        if (StringUtils.indexOf(fwyt, Constants.FWYT_CK) > -1) {
                            ckzl = bdcXmDOTemp.getZl();
                            continue;
                        }
                        if (StringUtils.indexOf(fwyt, Constants.FWYT_CW) > -1) {
                            cwzl = bdcXmDOTemp.getZl();
                            continue;
                        }
                        zlBuilder.append(bdcXmDOTemp.getZl()).append(CommonConstantUtils.ZF_YW_DH);
                    }
                }
                zlBuilder.append(StringUtils.isBlank(glZl) ? "" : glZl + CommonConstantUtils.ZF_YW_DH);
                zlBuilder.append(StringUtils.isBlank(ckzl) ? "" : ckzl + CommonConstantUtils.ZF_YW_DH);
                zlBuilder.append(StringUtils.isBlank(cwzl) ? "" : cwzl + CommonConstantUtils.ZF_YW_DH);

            } else {
                for (BdcCshFwkgSlDO bdcCshFwkgSlDOTemp : bdcCshFwkgSlByZsxh) {
                    if (!StringUtils.equals(xmid, bdcCshFwkgSlDOTemp.getId())) {
                        zlBuilder.append(entityMapper.selectByPrimaryKey(BdcXmDO.class, bdcCshFwkgSlDOTemp.getId()).getZl()).append(CommonConstantUtils.ZF_YW_DH);
                    }
                }
            }
            zl = zlBuilder.toString();
            if (StringUtils.isNotBlank(zl)) {
                //???????????????????????????????????????????????????????????????
                zl = zl.substring(0, zl.length() - CommonConstantUtils.ZF_YW_DH.length());
            }
        }
        return zl;
    }

    /**
     * @param bdcPrintDTO ??????????????????
     * @return ???????????????????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * ??????????????????????????????????????????????????????????????????
     */
    private List<Map> initDyaSpbZxParam(BdcPrintDTO bdcPrintDTO) {
        return this.initGrSpbZxParam(bdcPrintDTO);
    }

    /**
     * @param bdcPrintDTO ??????????????????
     * @return ???????????????????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ?????????????????????????????????????????????????????????????????????????????????
     */
    private List<Map> initGrSpbZxParam(BdcPrintDTO bdcPrintDTO) {
        List<Map> maps = new ArrayList();
        String gzlslid = bdcPrintDTO.getGzlslid();

        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("???????????????ID???" + gzlslid + "???????????????????????????");
        }
        bdcPrintDTO.setSlbh(bdcXmDOList.get(0).getSlbh());

        Map<String, Object> map = new HashMap();
        initPublicParam(map, bdcPrintDTO);
        map.put("xmid", bdcXmDOList.get(0).getXmid());
        if (CollectionUtils.size(bdcXmDOList) > 1) {
            map.put(Constants.ZL_SFX, "???");
        }
        maps.add(map);
        return maps;
    }

    /**
     * @param map            ????????????map
     * @param bdcPrintDTO    ????????????
     * @param bdcCshFwkgSlDO ???????????????????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ???????????????????????????????????????
     */
    private void initGrSpbPublicParam(Map<String, Object> map, BdcPrintDTO bdcPrintDTO, BdcCshFwkgSlDO bdcCshFwkgSlDO) {
        initPublicParam(map, bdcPrintDTO);
        map.put("xmid", bdcCshFwkgSlDO.getId());
        map.put("djxl", bdcCshFwkgSlDO.getDjxl());
        map.put("zsxh", bdcCshFwkgSlDO.getZsxh());
    }


    /**
     * @param bdcPrintDTO ????????????????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ????????????????????????????????????
     */
    private List<Map> initScSpbParam(BdcPrintDTO bdcPrintDTO) {
        List<Map> maps = new ArrayList();
        String gzlslid = bdcPrintDTO.getGzlslid();

        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("???????????????ID???" + gzlslid + "???????????????????????????");
        }

        Map<String, Object> map = new HashMap(5);
        // ????????????
        initMapJdmc(map, bdcPrintDTO.getJdmcList(), bdcPrintDTO.getGzlslid());

        map.put("gzlslid", bdcPrintDTO.getGzlslid());
        map.put("signImageUrl", bdcPrintDTO.getBdcUrlDTO().getSignImageUrl());
        map.put("ewm", bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + bdcXmDOList.get(0).getSlbh());
        if (CollectionUtils.size(bdcXmDOList) == 1) {
            map.put("xmid", bdcXmDOList.get(0).getXmid());
            map.put(Constants.ZL_SFX, CommonConstantUtils.ZF_KG_CHAR);
        } else {
            map.put(Constants.ZL_SFX, "???");
            map.put("xmid", getScSpbXmid(bdcXmDOList));
        }
        maps.add(map);
        return maps;
    }

    /**
     * @param bdcXmDOList ????????????????????????
     * @return ???????????????????????????xmid
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ???????????????????????????xmid????????????????????????????????????????????????
     */
    private String getScSpbXmid(List<BdcXmDO> bdcXmDOList) {
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            return null;
        }
        List<Map> fwytZd = bdcZdFeignService.queryBdcZd("fwyt");
        // ?????????????????????
        String[] fzfytArr = StringUtils.split(fzfytStr, CommonConstantUtils.ZF_YW_DH);
        for (BdcXmDO bdcXmDO : bdcXmDOList) {
            String fwyt = StringToolUtils.convertBeanPropertyValueOfZd(bdcXmDO.getDzwyt(), fwytZd);
            if (StringUtils.isBlank(fwyt)) {
                return bdcXmDO.getXmid();
            } else {
                boolean iszf = true;
                for (String fzfyt : fzfytArr) {
                    if (StringUtils.indexOf(fwyt, fzfyt) > -1) {
                        iszf = false;
                        break;
                    }
                }
                if (iszf) {
                    return bdcXmDO.getXmid();
                }
            }
        }
        return bdcXmDOList.get(0).getXmid();
    }

    /**
     * @param map         map??????
     * @param bdcPrintDTO ????????????
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ?????????????????????
     */
    private void initPublicParam(Map<String, Object> map, BdcPrintDTO bdcPrintDTO) {
        // ????????????
        initMapJdmc(map, bdcPrintDTO.getJdmcList(), bdcPrintDTO.getGzlslid());
        map.put("gzlslid", bdcPrintDTO.getGzlslid());
        map.put("signImageUrl", bdcPrintDTO.getBdcUrlDTO().getSignImageUrl());
        map.put("ewm", bdcPrintDTO.getBdcUrlDTO().getRegisterUiUrl() + "/rest/v1.0/print/ewm/" + bdcPrintDTO.getSlbh());
        map.put(Constants.ZL_SFX, CommonConstantUtils.ZF_KG_CHAR);
    }

    /**
     * @param map      map??????
     * @param jdmcList ??????List
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description ???map???????????????????????????
     */
    private void initMapJdmc(Map<String, Object> map, List<String> jdmcList, String gzlslid) {
        if (CollectionUtils.isNotEmpty(jdmcList)) {
            int i = 1;
            for (String jdmc : jdmcList) {
                map.put("jdmc" + i, jdmc);
                i++;
            }
            // ???????????????????????????
            while (i <= Integer.parseInt(jdNum)) {
                map.put("jdmc" + i, " ");
                i++;
            }
            if (CollectionUtils.isNotEmpty(yszGzldyidList) && StringUtils.isNotBlank(gzlslid)) {
                List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(bdcXmDOList) && yszGzldyidList.contains(bdcXmDOList.get(0).getGzldyid())) {
                    //?????????????????????????????????????????????????????????????????????
                    // ???????????????????????????
                    int j = 1;
                    while (j <= jdmcList.size()) {
                        map.put("jdmc" + j, " ");
                        j++;
                    }
                    map.put("yszjdmc", jdmcList.get(0));
                } else {
                    map.put("yszjdmc", "");
                }
            } else {
                map.put("yszjdmc", "");
            }
        }
    }

    private List<Map> getPrintMaps(BdcPrintDTO bdcPrintDTO) {
        String gzlslid = bdcPrintDTO.getGzlslid();
        int xmlx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
        String dylx = bdcPrintDTO.getDylx();
        boolean exitXmid = false;
        if (StringUtils.isNotBlank(bdcPrintDTO.getXmid())) {
            exitXmid = true;
        }
        List<BdcXmDO> bdcXmDOList = bdcXmxxService.getListBdcXmByGzlslid(gzlslid);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new MissingArgumentException("???????????????ID???" + gzlslid + "???????????????????????????");
        }
        bdcPrintDTO.setSlbh(bdcXmDOList.get(0).getSlbh());

        List<Map> maps = new ArrayList();
        if (!Objects.equals(3, bdcPrintDTO.getPrintMode())) {
            if (ArrayUtils.contains(Constants.DYLX_SPB_ARR, dylx)) {
                // ???????????????
                String clmc = "";
                // ???????????? ?????? ????????????
                if (CollectionUtils.size(bdcXmDOList) > 2) {
                    clmc = getClmc("", gzlslid, bdcPrintDTO.getSjclIdsStr());
                }
                for (BdcXmDO bdcXmDO : bdcXmDOList) {
                    String xmid = bdcXmDO.getXmid();
                    if (StringUtils.isBlank(clmc)) {
                        clmc = getClmc(xmid, gzlslid, bdcPrintDTO.getSjclIdsStr());
                    }
                    //DTO????????????xmid
                    if (!exitXmid) {
                        bdcPrintDTO.setXmid(xmid);
                    }
                    maps.add(initParam(bdcPrintDTO, clmc));
                    if (bdcPrintDTO.getDysl() != null && bdcPrintDTO.getDysl().equals(1) || (CollectionUtils.isNotEmpty(oneSpbDylxList) && oneSpbDylxList.contains(dylx))) {
                        break;
                    }
                }
            } else if (ArrayUtils.contains(Constants.DYLX_SPB_PLHB_ARR, dylx) || spbDylxList.contains(dylx)) {
                Set<BdcXmDO> newBdcXm = null;
                List<BdcCshFwkgSlDO> cshFwkgSlDOList = bdcXmFeignService.queryFwkgslByGzlslid(gzlslid);
                if (CollectionUtils.isNotEmpty(cshFwkgSlDOList)) {
                    newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl));
                    //??????????????????
                    Map<Object, List<BdcCshFwkgSlDO>> groupFwkgSlMap = new HashMap<>();
                    //???????????????????????????????????????????????????????????????
                    List<BdcCshFwkgSlDO> nullZsxhBdcCshFwkgSlDOS = cshFwkgSlDOList
                            .stream().filter(xm -> xm.getZsxh() == null ||
                                    !Objects.equals(CommonConstantUtils.QLLX_DYAQ_DM, xm.getQllx()))
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(nullZsxhBdcCshFwkgSlDOS)) {
                        if (xmlx == 3) {
                            //??????????????????????????????
                            Map<String, List<BdcCshFwkgSlDO>> djxlXmMap = nullZsxhBdcCshFwkgSlDOS
                                    .stream()
                                    .collect(Collectors.groupingBy(BdcCshFwkgSlDO::getDjxl));
                            if (MapUtils.isNotEmpty(djxlXmMap)) {
                                groupFwkgSlMap.putAll(djxlXmMap);
                            }
                            cshFwkgSlDOList.removeAll(nullZsxhBdcCshFwkgSlDOS);
                        } else {
                            Map<String, List<BdcCshFwkgSlDO>> xmMap = nullZsxhBdcCshFwkgSlDOS
                                    .stream()
                                    .collect(Collectors.groupingBy(BdcCshFwkgSlDO::getId));
                            if (MapUtils.isNotEmpty(xmMap)) {
                                groupFwkgSlMap.putAll(xmMap);
                            }
                            cshFwkgSlDOList.removeAll(nullZsxhBdcCshFwkgSlDOS);
                        }
                    }
                    //?????????????????????????????????????????????
                    if (CollectionUtils.isNotEmpty(cshFwkgSlDOList)) {
                        Map<Integer, List<BdcCshFwkgSlDO>> zsxhXmMap = cshFwkgSlDOList
                                .stream()
                                .collect(Collectors.groupingBy(BdcCshFwkgSlDO::getZsxh));
                        if (MapUtils.isNotEmpty(zsxhXmMap)) {
                            groupFwkgSlMap.putAll(zsxhXmMap);
                        }
                    }
                    //???????????????????????????
                    if (MapUtils.isNotEmpty(groupFwkgSlMap)) {
                        List<String> xmids = new ArrayList<>();
                        for (Map.Entry<Object, List<BdcCshFwkgSlDO>> objectListEntry : groupFwkgSlMap.entrySet()) {
                            xmids.add(objectListEntry.getValue().get(0).getId());
                        }
                        List<BdcXmDO> printXmList = bdcXmDOList.stream().filter(bdcxm -> xmids.contains(bdcxm.getXmid())).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(printXmList)) {
                            newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getXmid));
                            newBdcXm.addAll(printXmList);
                        } else {
                            newBdcXm.addAll(bdcXmDOList);
                        }
                    } else {
                        newBdcXm.addAll(bdcXmDOList);
                    }
                } else {
                    newBdcXm = new TreeSet<>(Comparator.comparing(BdcXmDO::getDjxl));
                    newBdcXm.addAll(bdcXmDOList);
                }

                for (BdcXmDO bdcXm : newBdcXm) {
                    // ??????????????????????????????????????????????????????????????????
                    String xmid = bdcXm.getXmid();
                    String clmc = getClmc(xmid, gzlslid, bdcPrintDTO.getSjclIdsStr());
                    //DTO????????????xmid
                    if (!exitXmid) {
                        bdcPrintDTO.setXmid(xmid);
                    }
                    maps.add(initParam(bdcPrintDTO, clmc));
                    if (bdcPrintDTO.getDysl() != null && bdcPrintDTO.getDysl().equals(1) || (CollectionUtils.isNotEmpty(oneSpbDylxList) && oneSpbDylxList.contains(dylx))) {
                        break;
                    }
                }
//                // ??????????????????
//                Set<String> djxlSet = new HashSet();
//                for (BdcXmDO bdcXmDO : bdcXmDOList) {
//                    if (!djxlSet.contains(bdcXmDO.getDjxl())) {
//                        djxlSet.add(bdcXmDO.getDjxl());
//
//                        // ??????????????????????????????????????????????????????????????????
//                        String xmid = bdcXmDO.getXmid();
//                        String clmc = getClmc(xmid, gzlslid, bdcPrintDTO.getSjclIdsStr());
//                        //DTO????????????xmid
//                        if (!exitXmid) {
//                            bdcPrintDTO.setXmid(xmid);
//                        }
//                        maps.add(initParam(bdcPrintDTO, clmc));
//                        if (bdcPrintDTO.getDysl() != null && bdcPrintDTO.getDysl().equals(1) || (CollectionUtils.isNotEmpty(oneSpbDylxList) && oneSpbDylxList.contains(dylx))) {
//                            break;
//                        }
//                    }
//                }
            }
        } else if (Objects.equals(3, bdcPrintDTO.getPrintMode()) && CollectionUtils.isNotEmpty(spbDylxList) && spbDylxList.contains(dylx) || (CollectionUtils.isNotEmpty(oneSpbDylxList) && oneSpbDylxList.contains(dylx))) {
            //??????mode=3 ??????????????????????????????????????????????????????????????????
            BdcXmDO bdcXmDO = bdcXmDOList.get(0);
            String xmid = bdcXmDO.getXmid();
            String clmc = getClmc(xmid, gzlslid, bdcPrintDTO.getSjclIdsStr());
            //DTO????????????xmid
            if (!exitXmid) {
                bdcPrintDTO.setXmid(xmid);
            }
            maps.add(initParam(bdcPrintDTO, clmc));
        }
        return maps;
    }
}
