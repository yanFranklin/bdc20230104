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
 * @description (盐城) 房屋权属信息处理类
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
     * 查询房屋权属信息
     *
     * @param fwqlCxRequestDTO 自助查询机查询参数
     * @param cdsj             查档时间
     * @return List 房屋权属信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    @Override
    public List<FwqsCxResponseDTO> queryFwQsxx(FwqlCxRequestDTO fwqlCxRequestDTO, String cdsj) {
        // 转换权利人查询参数
        List<BdcFwQsxxQlrQO> qlrList = new ArrayList<>();
        for (FwqlCxRequestQlr reqQlr : fwqlCxRequestDTO.getQlrlist()) {
            BdcFwQsxxQlrQO bdcFwQsxxQlrQO = new BdcFwQsxxQlrQO();
            bdcFwQsxxQlrQO.setSfzh(reqQlr.getSfzh());
            bdcFwQsxxQlrQO.setQlr(reqQlr.getQlr());
            qlrList.add(bdcFwQsxxQlrQO);
        }

        // 查询参数处理
        BdcFwQsxxQO bdcFwQsxxQO = new BdcFwQsxxQO();
        bdcFwQsxxQO.setQlrxx(qlrList);
        bdcFwQsxxQO.setBdcdyh(fwqlCxRequestDTO.getBdcdyh());
        bdcFwQsxxQO.setFwbm(fwqlCxRequestDTO.getFwbm());
        bdcFwQsxxQO.setFwzl(fwqlCxRequestDTO.getFwzl());
        // 2是自助查询机
        bdcFwQsxxQO.setCxly("2");

        // 调用查询子系统房屋权利信息查询接口
        List<BdcFwJbxxDTO> bdcFwJbxxDTOList = bdcZfxxCxFeignService.listBdcFwQsxxDTO(bdcFwQsxxQO);

        // 处理返回结果，增加限制信息查询
        return this.resolveFwjbxxAndXzxx(bdcFwJbxxDTOList, cdsj);
    }

    /**
     * @param zzcxjSfxxDTO@return BdcSlSfxxZzcxjDTO 收费信息
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
     * 处理返回的房屋基本信息，追加查询限制信息
     *
     * @param bdcFwJbxxDTOList 房屋基本信息
     * @param cdsj             查档时间
     * @return List 房屋权属详细信息
     */
    private List<FwqsCxResponseDTO> resolveFwjbxxAndXzxx(List<BdcFwJbxxDTO> bdcFwJbxxDTOList, String cdsj) {
        if (CollectionUtils.isEmpty(bdcFwJbxxDTOList)) {
            return Collections.emptyList();
        }

        List<FwqsCxResponseDTO> result = new ArrayList<>();
        for (BdcFwJbxxDTO fwJbxxDTO : bdcFwJbxxDTOList) {
            FwqsCxResponseDTO resultItem = new FwqsCxResponseDTO();
            // 复制属性值
            BeansResolveUtils.clonePropsValueWithParentProps(fwJbxxDTO, resultItem);
            //38941【盐城】自助机权属查询接口土地使用期限取值  ,要求xmly为2的存量数据，tdsyqx取土地的值，直接用bdcdyh去查询BDC_JSYDSYQ
            if (CommonConstantUtils.XMLY_CLGD_DM.equals(resultItem.getXmly())) {
                GrdacxRequestBody grdacxRequestBody = new GrdacxRequestBody();
                grdacxRequestBody.setLikeBdcdyh(resultItem.getBdcdyh());
                List<BdcJsydsyqDO> jsydsyqDOList = bdcdjMapper.queryBdcJsydsyq(grdacxRequestBody);
                if (CollectionUtils.isNotEmpty(jsydsyqDOList)) {
                    BdcJsydsyqDO jsydsyqDO = jsydsyqDOList.get(0);
                    String qssj = null != jsydsyqDO.getSyqqssj() ? DateFormatUtils.format(jsydsyqDO.getSyqqssj(), "yyyy-MM-dd") + "起" : "起";
                    String jssj = null != jsydsyqDO.getSyqjssj() ? DateFormatUtils.format(jsydsyqDO.getSyqjssj(), "yyyy-MM-dd") + "止" : "止";
                    resultItem.setTdsyqx(qssj + jssj);
                }
            }
            // 处理查档时间
            resultItem.setCdsj(cdsj);

            // 查封多幢、分幢信息
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

            // 限制状态查询参数
            QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
            queryQlRequestDTO.setBdcdyh(fwJbxxDTO.getBdcdyh());
            queryQlRequestDTO.setWithXm(true);

            Map<String, Object> xzztMap = this.getXzzt(queryQlRequestDTO, true);
            if (xzztMap != null) {
                resultItem.setXzzt(MapUtils.getString(xzztMap, "xzzt"));
                // 查封
                resultItem.setCfxxlist((List<FwqlCxResponseCfxx>) MapUtils.getObject(xzztMap, "cfxxList"));
                if (CollectionUtils.isEmpty(resultItem.getCfxxlist())) {
                    resultItem.setCfzt("0");
                } else {
                    resultItem.setCfzt("1");
                }

                // 抵押
                resultItem.setDyaqxxlist((List<FwqlCxResponseDyaqxx>) MapUtils.getObject(xzztMap, "dyaqxxList"));
                if (CollectionUtils.isEmpty(resultItem.getDyaqxxlist())) {
                    resultItem.setDyzt("0");
                } else {
                    resultItem.setDyzt("1");
                }
            }

            // 异议信息
            resultItem.setYyxxlist(this.getYyxx(queryQlRequestDTO));

            // 预告信息
            resultItem.setYgxxlist(this.getYgxx(queryQlRequestDTO));

            // 锁定信息
            resultItem.setSdxxlist(this.getSdxx(fwJbxxDTO.getBdcdyh(),fwJbxxDTO.getXmid()));

            // 处理权利人
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
            // 合并的权利人信息
            if (qlrs.length() > 0) {
                resultItem.setQlr(qlrs.substring(0, qlrs.length() - 1));
            }
            if (qlrzjhs.length() > 0) {
                resultItem.setSfzh(qlrzjhs.substring(0, qlrzjhs.length() - 1));
            }

            // 将处理的信息放到返回结果中
            result.add(resultItem);
        }
        return result;
    }

    /**
     * 查询锁定信息  包括不动产单元锁定和证书锁定
     * @param bdcdyh
     * @param xmid
     * @return FwqlCxResponseSdxx
     */
    private List<FwqlCxResponseSdxx> getSdxx(String bdcdyh,String xmid){
        List<FwqlCxResponseSdxx> responseSdxxList = new ArrayList<>();
        if(StringUtils.isNotBlank(bdcdyh)){
            // 单元锁定
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
            // 证书锁定
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
         * 获取限制状态信息
         *
         * @param queryQlRequestDTO
         * @param xzxx              是否返回限制信息具体数据
         * @return
         */
    private Map<String, Object> getXzzt(QueryQlRequestDTO queryQlRequestDTO, boolean xzxx) {
        Map<String, Object> resultMap = new HashMap<>();
        String xzzt = "";
        List<CfQlWithXmQlrDTO> cfList = commonService.listCfByBdcdyh(queryQlRequestDTO);
        if (CollectionUtils.isNotEmpty(cfList)) {
            xzzt += "查封";
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
                        cfqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getCfqssj()) + "起";
                    }
                    if (dto.getBdcql().getCfjssj() != null) {
                        cfqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getCfjssj()) + "止";
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
                xzzt += "抵押";
            } else {
                xzzt += "/抵押";
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
                    zwlxqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getZwlxqssj()) + "起";
                }
                if (dto.getBdcql().getZwlxjssj() != null) {
                    zwlxqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getZwlxjssj()) + "止";
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
     * 查询异议信息
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
                    qlqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getYydjksrq()) + "起";
                }
                if (dto.getBdcql().getYydjjsrq() != null) {
                    qlqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getYydjjsrq()) + "止";
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
     * 查询预告信息
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
                //设置权利人和bdcqzh
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("xmid",dto.getBdcXmDO().getXmid());
                List<BdcZsDO> bdcZsDOS = bdcdjMapper.getZsbsxxByXmid(paramMap);
                if(CollectionUtils.isNotEmpty(bdcZsDOS)){
                    yg.setYgzmh(bdcZsDOS.get(0).getBdcqzh());
                }
                //权利人
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
