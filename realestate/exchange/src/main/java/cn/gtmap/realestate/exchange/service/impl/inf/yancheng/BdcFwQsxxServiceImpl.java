package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;


import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxxZzcxjResponseDTO;
import cn.gtmap.realestate.common.core.dto.accept.ZzcxjSfxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcFwJbxxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcFwQsxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.yancheng.BdcFwQsxxQlrQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.common.*;
import cn.gtmap.realestate.exchange.core.dto.wwsq.grdacx.request.GrdacxRequestBody;
import cn.gtmap.realestate.exchange.core.dto.yancheng.zzcxj.response.FwqsCxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.zzcxj.sfxx.request.SfxxResponeHead;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestQlr;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.*;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2020-11-23
 * @description (??????) ???????????????????????????
 */
@Service("bdcFwQsxxServiceImpl")
public class BdcFwQsxxServiceImpl implements BdcFwQsxxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcFwQsxxServiceImpl.class);

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private CommonService commonService;
    @Autowired
    private BdcZfxxCxFeignService bdcZfxxCxFeignService;
    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private BdcSlSfxxFeignService slSfxxFeignService;


    @Autowired
    private BdcPpFeignService bdcPpFeignService;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    /**
     * ????????????????????????
     *
     * @param fwqlCxRequestDTO ???????????????????????????
     * @param cdsj             ????????????
     * @return List ????????????????????????
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<FwqsCxResponseDTO> queryFwQsxx(FwqlCxRequestDTO fwqlCxRequestDTO, String cdsj) {
        // ???????????????????????????
        List<BdcFwQsxxQlrQO> qlrList = new ArrayList<>();
        for (FwqlCxRequestQlr reqQlr : fwqlCxRequestDTO.getQlrlist()) {
            BdcFwQsxxQlrQO bdcFwQsxxQlrQO = new BdcFwQsxxQlrQO();
            bdcFwQsxxQlrQO.setSfzh(reqQlr.getSfzh());
            bdcFwQsxxQlrQO.setQlr(reqQlr.getQlr());
            qlrList.add(bdcFwQsxxQlrQO);
        }

        // ??????????????????
        BdcFwQsxxQO bdcFwQsxxQO = new BdcFwQsxxQO();
        bdcFwQsxxQO.setQlrxx(qlrList);
        bdcFwQsxxQO.setBdcdyh(fwqlCxRequestDTO.getBdcdyh());
        bdcFwQsxxQO.setFwbm(fwqlCxRequestDTO.getFwbm());
        bdcFwQsxxQO.setFwzl(fwqlCxRequestDTO.getFwzl());
        // 2??????????????????
        bdcFwQsxxQO.setCxly("2");

        // ???????????????????????????????????????????????????
        List<BdcFwJbxxDTO> bdcFwJbxxDTOList = bdcZfxxCxFeignService.listBdcFwQsxxDTO(bdcFwQsxxQO);

        // ?????????????????????????????????????????????
        return this.resolveFwjbxxAndXzxx(bdcFwJbxxDTOList, cdsj);
    }

    /**
     * @param zzcxjSfxxDTO@return BdcSlSfxxZzcxjDTO ????????????
     * @Date 2020/11/27
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public Object queryZsSfxx(ZzcxjSfxxDTO zzcxjSfxxDTO) {
        HashMap map = new HashMap();

        LOGGER.info("dsa{},", zzcxjSfxxDTO);
//        String data= param.getString("data");
        if (null != zzcxjSfxxDTO) {
            List<BdcSlSfxxZzcxjResponseDTO> list = slSfxxFeignService.zzcxjQuerySfxx(zzcxjSfxxDTO);
            SfxxResponeHead head = new SfxxResponeHead();
            head.setRecords(list.size());
            head.setPage(list.size());
            head.setTotal(list.size());
            head.setPageSize(1000);
            head.setReturncode("0000");
            map.put("head", head);
            map.put("data", list);

        }

        return map;
    }

    /**
     * ????????????????????????????????????????????????????????????
     *
     * @param bdcFwJbxxDTOList ??????????????????
     * @param cdsj             ????????????
     * @return List ????????????????????????
     */
    private List<FwqsCxResponseDTO> resolveFwjbxxAndXzxx(List<BdcFwJbxxDTO> bdcFwJbxxDTOList, String cdsj) {
        if (CollectionUtils.isEmpty(bdcFwJbxxDTOList)) {
            return Collections.emptyList();
        }

        List<FwqsCxResponseDTO> result = new ArrayList<>();
        for (BdcFwJbxxDTO fwJbxxDTO : bdcFwJbxxDTOList) {
            FwqsCxResponseDTO resultItem = new FwqsCxResponseDTO();
            // ???????????????
            BeansResolveUtils.clonePropsValueWithParentProps(fwJbxxDTO, resultItem);
            //38941???????????????????????????????????????????????????????????????  ,??????xmly???2??????????????????tdsyqx???????????????????????????bdcdyh?????????BDC_JSYDSYQ
            if (CommonConstantUtils.XMLY_CLGD_DM.equals(resultItem.getXmly())) {
                GrdacxRequestBody grdacxRequestBody = new GrdacxRequestBody();
                grdacxRequestBody.setLikeBdcdyh(resultItem.getBdcdyh());
                List<BdcJsydsyqDO> jsydsyqDOList = bdcdjMapper.queryBdcJsydsyq(grdacxRequestBody);
                if (CollectionUtils.isNotEmpty(jsydsyqDOList)) {
                    BdcJsydsyqDO jsydsyqDO = jsydsyqDOList.get(0);
                    String qssj = null != jsydsyqDO.getSyqqssj() ? DateFormatUtils.format(jsydsyqDO.getSyqqssj(), "yyyy-MM-dd") + "???" : "???";
                    String jssj = null != jsydsyqDO.getSyqjssj() ? DateFormatUtils.format(jsydsyqDO.getSyqjssj(), "yyyy-MM-dd") + "???" : "???";
                    resultItem.setTdsyqx(qssj + jssj);
                }
            }
            // ??????????????????
            resultItem.setCdsj(cdsj);

            // ???????????????????????????
            if (StringUtils.isNotBlank(fwJbxxDTO.getQlid())) {
                List<BdcFdcqFdcqxmDO> fdcqxmDOList = bdcQllxFeignService.listFdcqXm(fwJbxxDTO.getQlid());
                resultItem.setFwfzxxlist(new ArrayList<>());

                if (CollectionUtils.isNotEmpty(fdcqxmDOList)) {
                    for (BdcFdcqFdcqxmDO fdcqxmDO : fdcqxmDOList) {
                        FwqlCxResponseFzxx fzxx = new FwqlCxResponseFzxx();
                        dozerBeanMapper.map(fdcqxmDO, fzxx, "FwqlCxbdcFdcqxmToFzxx");
                        resultItem.getFwfzxxlist().add(fzxx);
                    }
                }
            }

            // ????????????????????????
            QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
            queryQlRequestDTO.setBdcdyh(fwJbxxDTO.getBdcdyh());
            queryQlRequestDTO.setWithXm(true);

            Map<String, Object> xzztMap = this.getXzzt(queryQlRequestDTO, true);
            if (xzztMap != null) {
                resultItem.setXzzt(MapUtils.getString(xzztMap, "xzzt"));
                // ??????
                resultItem.setCfxxlist((List<FwqlCxResponseCfxx>) MapUtils.getObject(xzztMap, "cfxxList"));
                if (CollectionUtils.isEmpty(resultItem.getCfxxlist())) {
                    resultItem.setCfzt("0");
                } else {
                    resultItem.setCfzt("1");
                }

                // ??????
                resultItem.setDyaqxxlist((List<FwqlCxResponseDyaqxx>) MapUtils.getObject(xzztMap, "dyaqxxList"));
                if (CollectionUtils.isEmpty(resultItem.getDyaqxxlist())) {
                    resultItem.setDyzt("0");
                } else {
                    resultItem.setDyzt("1");
                }
            }

            // ????????????
            resultItem.setYyxxlist(this.getYyxx(queryQlRequestDTO));

            // ????????????
            resultItem.setYgxxlist(this.getYgxx(queryQlRequestDTO));

            // ????????????
            resultItem.setSdxxlist(this.getSdxx(fwJbxxDTO.getBdcdyh(),fwJbxxDTO.getXmid()));

            // ???????????????
            resultItem.setQlrxxlist(new ArrayList<>());
            List<BdcQlrDO> bdcQlrList = commonService.listBdcQlrByXmid(fwJbxxDTO.getXmid(), CommonConstantUtils.QLRLB_QLR);
            StringBuffer qlrs = new StringBuffer();
            StringBuffer qlrzjhs = new StringBuffer();
            if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                    qlrs.append(bdcQlrDO.getQlrmc() + ",");
                    qlrzjhs.append(bdcQlrDO.getZjh() + ",");

                    FwqlCxResponseQlrxx qlrxx = new FwqlCxResponseQlrxx();
                    dozerBeanMapper.map(bdcQlrDO, qlrxx, "FwqlCxbdcQlrToQlr");

                    if (fwJbxxDTO.getDjsj() != null) {
                        qlrxx.setDbsj(DateUtil.formateTime(fwJbxxDTO.getDjsj()));
                    }
                    if (fwJbxxDTO.getFj() != null) {
                        qlrxx.setFj(fwJbxxDTO.getFj());
                    }
                    if (fwJbxxDTO.getFzrq() != null) {
                        qlrxx.setFzrq(DateFormatUtils.format(fwJbxxDTO.getFzrq(), "yyyy-MM-dd HH:mm:ss"));
                    }

                    resultItem.getQlrxxlist().add(qlrxx);
                }
            }
            // ????????????????????????
            if (qlrs.length() > 0) {
                resultItem.setQlr(qlrs.substring(0, qlrs.length() - 1));
            }
            if (qlrzjhs.length() > 0) {
                resultItem.setSfzh(qlrzjhs.substring(0, qlrzjhs.length() - 1));
            }

            // ???????????????????????????????????????
            result.add(resultItem);
        }
        return result;
    }

    /**
     * ??????????????????  ??????????????????????????????????????????
     * @param bdcdyh
     * @param xmid
     * @return FwqlCxResponseSdxx
     */
    private List<FwqlCxResponseSdxx> getSdxx(String bdcdyh,String xmid){
        List<FwqlCxResponseSdxx> responseSdxxList = new ArrayList<>();
        if(StringUtils.isNotBlank(bdcdyh)){
            // ????????????
            List<BdcDysdDO> dysdDOList = commonService.listDysdByBdcdyh(bdcdyh,CommonConstantUtils.SDZT_SD);
            if(CollectionUtils.isNotEmpty(dysdDOList) ){
                for(BdcDysdDO dysdDO : dysdDOList){
                    FwqlCxResponseSdxx sdxx = new FwqlCxResponseSdxx();
                    dozerBeanMapper.map(dysdDO,sdxx,"bdcDysdToResponseSdxx");
                    responseSdxxList.add(sdxx);
                }
            }
        }
        if(StringUtils.isNotBlank(xmid)){
            // ????????????
            List<BdcZssdDO> zssdDOList = commonService.listZssdByXmid(xmid,CommonConstantUtils.SDZT_SD);
            if(CollectionUtils.isNotEmpty(zssdDOList) ){
                for(BdcZssdDO zssdDO : zssdDOList){
                    FwqlCxResponseSdxx sdxx = new FwqlCxResponseSdxx();
                    dozerBeanMapper.map(zssdDO,sdxx,"bdcZssdToResponseSdxx");
                    responseSdxxList.add(sdxx);
                }
            }
        }
        return responseSdxxList;
    }

        /**
         * ????????????????????????
         *
         * @param queryQlRequestDTO
         * @param xzxx              ????????????????????????????????????
         * @return
         */
    private Map<String, Object> getXzzt(QueryQlRequestDTO queryQlRequestDTO, boolean xzxx) {
        Map<String, Object> resultMap = new HashMap<>();
        String xzzt = "";
        List<CfQlWithXmQlrDTO> cfList = commonService.listCfByBdcdyh(queryQlRequestDTO);
        if (CollectionUtils.isNotEmpty(cfList)) {
            xzzt += "??????";
            if (xzxx) {
                List<FwqlCxResponseCfxx> cfxxList = new ArrayList<>();
                for (CfQlWithXmQlrDTO dto : cfList) {
                    FwqlCxResponseCfxx cf = new FwqlCxResponseCfxx();
                    dozerBeanMapper.map(dto.getBdcql(), cf, "bdcCfToResponseCf");
                    if (dto.getBdcql().getDjsj() != null) {
                        cf.setDjsj(DateFormatUtils.format(dto.getBdcql().getDjsj(), "yyyy-MM-dd HH:mm:ss"));
                    }
                    if (dto.getBdcXmDO() != null) {
                        dozerBeanMapper.map(dto.getBdcXmDO(), cf, "bdcXmToResponseCf");
                    }
                    String cfqx = "";
                    if (dto.getBdcql().getCfqssj() != null) {
                        cfqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getCfqssj()) + "???";
                    }
                    if (dto.getBdcql().getCfjssj() != null) {
                        cfqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getCfjssj()) + "???";
                    }
                    cf.setCfqx(cfqx);
                    cfxxList.add(cf);
                }
                resultMap.put("cfxxList", cfxxList);
            }

        }
        List<DyQlWithXmQlrDTO> dyaqList = commonService.listDyaqByBdcdyh(queryQlRequestDTO);
        if (CollectionUtils.isNotEmpty(dyaqList)) {
            if (xzzt.length() == 0) {
                xzzt += "??????";
            } else {
                xzzt += "/??????";
            }
            List<FwqlCxResponseDyaqxx> dyaqxxList = new ArrayList<>();
            for (DyQlWithXmQlrDTO dto : dyaqList) {
                FwqlCxResponseDyaqxx dyaqxx = new FwqlCxResponseDyaqxx();
                dozerBeanMapper.map(dto.getBdcql(), dyaqxx, "bdcDyaqToResponseDyaq");
                dyaqxx.setDysw(String.valueOf(dto.getBdcql().getDysw()));
                if (dto.getBdcXmDO() != null) {
                    dozerBeanMapper.map(dto.getBdcXmDO(), dyaqxx, "bdcXmToResponseDyaq");
                }
                List<BdcQlrDO> bdcQlrDOList = commonService.listBdcQlrByXmid(dto.getBdcql().getXmid(), CommonConstantUtils.QLRLB_QLR);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    dyaqxx.setDyaqlr(bdcQlrDOList.get(0).getQlrmc());
                    dyaqxx.setDjzmh(bdcQlrDOList.get(0).getBdcqzh());
                    if (bdcQlrDOList.size() > 1){
                        String bdcqzh = bdcQlrDOList.get(0).getBdcqzh();
                        boolean tempFlag = true;
                        StringBuilder dyaqrStringBuilder = new StringBuilder(bdcQlrDOList.get(0).getQlrmc());
                        StringBuilder djzmhStringBuilder = new StringBuilder(bdcQlrDOList.get(0).getBdcqzh());
                        for (int i = 1; i < bdcQlrDOList.size(); i++) {
                            dyaqrStringBuilder.append(",").append(bdcQlrDOList.get(i).getQlrmc());
                            if (bdcqzh.equals(bdcQlrDOList.get(i).getBdcqzh())){
                                tempFlag = false;
                            }else {
                                djzmhStringBuilder.append(",").append(bdcQlrDOList.get(i).getBdcqzh());
                            }
                        }
                        if (tempFlag){
                            dyaqxx.setDjzmh(djzmhStringBuilder.toString());
                        }
                        dyaqxx.setDyaqlr(dyaqrStringBuilder.toString());
                    }
                }
                String zwlxqx = "";
                if (dto.getBdcql().getZwlxqssj() != null) {
                    zwlxqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getZwlxqssj()) + "???";
                }
                if (dto.getBdcql().getZwlxjssj() != null) {
                    zwlxqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getZwlxjssj()) + "???";
                }
                if (dto.getBdcql().getBdbzzqse() != null) {
                    dyaqxx.setBdbzqse(doubleToString(dto.getBdcql().getBdbzzqse()));
                }
                dyaqxx.setZwlxqx(zwlxqx);
                if (dyaqxx != null) {
                    dyaqxxList.add(dyaqxx);
                }
            }
            resultMap.put("dyaqxxList", dyaqxxList);
        }
        resultMap.put("xzzt", xzzt);
        return resultMap;
    }

    /**
     * ??????????????????
     *
     * @param queryQlRequestDTO
     * @return
     */
    private List<FwqlCxResponseYyxx> getYyxx(QueryQlRequestDTO queryQlRequestDTO) {
        List<FwqlCxResponseYyxx> yyxxList = new ArrayList<>();
        List<YyQlWithXmQlrDTO> yyList = commonService.listYyByBdcdyh(queryQlRequestDTO);
        if (CollectionUtils.isNotEmpty(yyList)) {
            for (YyQlWithXmQlrDTO dto : yyList) {
                FwqlCxResponseYyxx yy = new FwqlCxResponseYyxx();
                dozerBeanMapper.map(dto.getBdcql(), yy, "bdcYyToResponseYy");
                if (dto.getBdcXmDO() != null) {
                    dozerBeanMapper.map(dto.getBdcXmDO(), yy, "bdcXmToResponseYy");
                }
                String qlqx = "";
                if (dto.getBdcql().getYydjksrq() != null) {
                    qlqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getYydjksrq()) + "???";
                }
                if (dto.getBdcql().getYydjjsrq() != null) {
                    qlqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getYydjjsrq()) + "???";
                }
                yy.setQlqx(qlqx);
                List<BdcQlrDO> yyQlrs = commonService.listBdcQlrByXmid(dto.getBdcql().getXmid(), CommonConstantUtils.QLRLB_QLR);
                if (CollectionUtils.isNotEmpty(yyQlrs)) {
                    StringBuffer sqr = new StringBuffer();
                    StringBuffer yydjzmh = new StringBuffer();
                    for (BdcQlrDO bdcQlr : yyQlrs) {
                        sqr.append(bdcQlr.getQlrmc() + ",");
                        yydjzmh.append(bdcQlr.getBdcqzh() + ",");
                    }
                    yy.setSqr(sqr.substring(0, sqr.length() - 1));
                    yy.setYydjzmh(yydjzmh.substring(0, yydjzmh.length() - 1));
                }
                yyxxList.add(yy);
            }
        }
        return yyxxList;
    }

    /**
     * ??????????????????
     *
     * @param queryQlRequestDTO
     * @return
     */
    private List<FwqlCxResponseYgxx> getYgxx(QueryQlRequestDTO queryQlRequestDTO) {
        List<FwqlCxResponseYgxx> ygxxList = new ArrayList<>();
        List<YgQlWithXmQlrDTO> ygList = commonService.listYgByBdcdyh(queryQlRequestDTO);
        if (CollectionUtils.isNotEmpty(ygList)) {
            for (YgQlWithXmQlrDTO dto : ygList) {
                FwqlCxResponseYgxx yg = new FwqlCxResponseYgxx();
                dozerBeanMapper.map(dto.getBdcql(), yg, "bdcYgToResponseYg");
                if (dto.getBdcXmDO() != null) {
                    dozerBeanMapper.map(dto.getBdcXmDO(), yg, "bdcXmToResponseYg");
                }
                //??????????????????bdcqzh
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("xmid",dto.getBdcXmDO().getXmid());
                List<BdcZsDO> bdcZsDOS = bdcdjMapper.getZsbsxxByXmid(paramMap);
                if(CollectionUtils.isNotEmpty(bdcZsDOS)){
                    yg.setYgzmh(bdcZsDOS.get(0).getBdcqzh());
                }
                //?????????
                List<BdcQlrDO> qlrDOList = new ArrayList<>();
                if(Objects.nonNull(queryQlRequestDTO.getWithQlr()) && queryQlRequestDTO.getWithQlr()){
                    qlrDOList = dto.getBdcQlrList();
                } else {
                    paramMap.put("qlrlb", "1");
                    qlrDOList = bdcdjMapper.queryQlrList(paramMap);
                }
                if(CollectionUtils.isNotEmpty(qlrDOList)) {
                    qlrDOList = qlrDOList
                            .stream()
                            .filter(bdcQlrDO -> bdcQlrDO.getQlrlb().equals(CommonConstantUtils.QLRLB_QLR))
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(qlrDOList)) {
                        yg.setYgqlr(qlrDOList.stream().map(BdcQlrDO::getQlrmc).collect(Collectors.joining(",")));
                    }
                }
                ygxxList.add(yg);
            }
        }
        return ygxxList;
    }

    private static String doubleToString(Double d) {
        if (Objects.isNull(d)) {
            return "";
        }
        DecimalFormat df1 = new DecimalFormat("0.00");
        return df1.format(d);
    }
}
