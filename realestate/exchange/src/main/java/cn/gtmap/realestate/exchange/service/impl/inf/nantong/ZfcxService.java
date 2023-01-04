package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlJsydsyqDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcFwqlDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcZfxxCxlyEnum;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcFwqlQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.service.feign.building.DjxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.EntityZdConvertUtils;
import cn.gtmap.realestate.exchange.core.bo.anno.AnalysisAuditLog;
import cn.gtmap.realestate.exchange.core.bo.anno.PublicQueryLog;
import cn.gtmap.realestate.exchange.core.dto.common.*;
import cn.gtmap.realestate.exchange.core.dto.ycsl.queryxzqlbybdcdyh.response.YcslQueryXzqlByBdcdyhSdxx;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.BaseQlrRequest;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestQlr;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.*;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.request.ZfcxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.request.ZfcxRequestQlr;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.FwlbResponse;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.ZfcxResponseFw;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.ZfcxResponseQlrxx;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlJsydsyqMapper;
import cn.gtmap.realestate.exchange.core.qo.BdcFwxxQO;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.DateUtil;
import cn.gtmap.realestate.exchange.util.constants.LogCzxxConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-24
 * @description 有房无房 住房查询
 */
@Service
public class ZfcxService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZfcxService.class);

    @Resource(name = "exchangeDozerMapper")
    private DozerBeanMapper dozerBeanMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private BdcdjMapper bdcdjMapper;
    @Autowired
    private BdcZfxxCxFeignService bdcZfxxCxFeignService;
    @Autowired
    DjxxFeignService djxxFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    private QlfQlJsydsyqMapper qlfQlJsydsyqMapper;
    @Autowired
    private EntityZdConvertUtils zdConvertUtils;
    @Autowired
    private EntityMapper entityMapper;


    /**
     * 产权限制状态展示，默认只展示查封/抵押两种，预告/预抵押/异议/锁定需要配置
     */
    @Value("${zzcxj.xzzt.xzlx:}")
    private String xzlx;

    /**
     * 是否需要将权利人名称作为查询条件
     */
    @Value("${zzcxj.qlrmc:false}")
    private Boolean needQlrmc;

    /**
     * 房产是否需要区分区县代码（数据权限控制）
     */
    @Value("${zzcxj.fcqfqxdm:false}")
    private boolean fcqfqxdm;

    /**
     * 房产查询全市数据区划标识
     */
    @Value("${zzcxj.fcqssjqxdm:}")
    private String fcqssjqxdm;

    /**
     * 权属是否需要区分区县代码（数据权限控制）
     */
    @Value("${zzcxj.qsqfqxdm:false}")
    private boolean qsqfqxdm;

    /**
     * 权属查询全市数据区划标识
     */
    @Value("${zzcxj.qsqssjqxdm:}")
    private String qsqssjqxdm;


    /**
     * 住房信息查询是否要查询合同信息,默认不查询
     */
    @Value("${zzcxj.fwxx.cxhtfwxx:false}")
    private boolean cxhtfwxx;


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param zfcxRequestDTO
     * @param cdsj
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.ZfcxResponseFw>
     * @description 查询 房屋列表
      */
    public List<ZfcxResponseFw> queryFwListByRequest(ZfcxRequestDTO zfcxRequestDTO,String cdsj, String areacode){
        List<ZfcxRequestQlr> qlrList = zfcxRequestDTO.getQlrlist();
        // 调用 查询子系统 有房无房查询接口
        BdcZfxxQO zfxxQO = new BdcZfxxQO();
        zfxxQO.setQlrxx(new ArrayList<>());
        for(ZfcxRequestQlr reqQlr : qlrList){
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            // 此处修改为只用证件号查询 考虑有做过人名变更的情况
            bdcQlrQO.setZjh(reqQlr.getSfzh());
            if(needQlrmc) {
                bdcQlrQO.setQlrmc(reqQlr.getQlr());
            }
            zfxxQO.getQlrxx().add(bdcQlrQO);
        }

        // 1 设置过滤区划
        // 1.1 传值可查询全市数据标识，不过滤区划
        // 1.2 传值其他区划标识，过滤区划
        // 2 设置不过滤区划，查询全市
        if(fcqfqxdm) {
            zfxxQO.setQxdm(StringUtils.equals(fcqssjqxdm, areacode) ? null : areacode);
        } else {
            zfxxQO.setQxdm(null);
        }

        zfxxQO.setCxly("1");
        List<BdcZfxxDTO> zfxxList = bdcZfxxCxFeignService.listBdcNtZfxxDTO(zfxxQO);
        return dealFwxx(zfxxList,cdsj);
    }
    /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param zfcxRequestDTO
     * @param cdsj
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.ZfcxResponseFw>
     * @description 查询 房屋列表  蚌埠
     */
    public List<ZfcxResponseFw> queryFwListByRequestBb(ZfcxRequestDTO zfcxRequestDTO,String cdsj){
        List<ZfcxRequestQlr> qlrList = zfcxRequestDTO.getQlrlist();
        // 调用 查询子系统 有房无房查询接口
        BdcZfxxQO zfxxQO = new BdcZfxxQO();
        zfxxQO.setQlrxx(new ArrayList<>());
        for(ZfcxRequestQlr reqQlr : qlrList){
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            // 此处修改为只用证件号查询 考虑有做过人名变更的情况
            bdcQlrQO.setZjh(reqQlr.getSfzh());
            zfxxQO.getQlrxx().add(bdcQlrQO);
        }
        // 蚌埠自助查询机需要规划用途为空的数据也支持查询出来，同时登记和查询机设置的规划用提一致，所以直接设置查询来源为“1”
        zfxxQO.setCxly(BdcZfxxCxlyEnum.DJXT.getCode());
        //先调用南通的服务
        List<BdcZfxxDTO> zfxxList = bdcZfxxCxFeignService.listBdcNtZfxxDTO(zfxxQO);

        return dealFwxx(zfxxList,cdsj);
    }
    /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param
     * @param cdsj
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.FwqlCxResponseQl>
     * @description 查询 房屋权利信息
     */
    public List<FwqlCxResponseQl> queryFwQlListByRequest(FwqlCxRequestDTO fwqlCxRequestDTO, String cdsj, String areacode){
        List<FwqlCxRequestQlr> qlrList = fwqlCxRequestDTO.getQlrlist();
        // 调用 查询子系统 房屋权利信息查询接口
        BdcFwqlQO bdcFwqlQO = new BdcFwqlQO();
        bdcFwqlQO.setQlrxx(new ArrayList<>());
        for(FwqlCxRequestQlr reqQlr : qlrList){
            BdcFwqlQlrQO fwqlQlrQO = new BdcFwqlQlrQO();
            // 此处修改为只用证件号查询 考虑有做过人名变更的情况
            fwqlQlrQO.setZjh(reqQlr.getSfzh());
            fwqlQlrQO.setBdcdyh(reqQlr.getBdcdyh());
            fwqlQlrQO.setBdcqzh(reqQlr.getCqzh());
            fwqlQlrQO.setZl(reqQlr.getZl());
            bdcFwqlQO.getQlrxx().add(fwqlQlrQO);
        }
        // 1 设置过滤区划
        // 1.1 传值可查询全市数据标识，不过滤区划
        // 1.2 传值其他区划标识，过滤区划
        // 2 设置不过滤区划，查询全市
        if(qsqfqxdm) {
            bdcFwqlQO.setQxdm(StringUtils.equals(qsqssjqxdm, areacode) ? null : areacode);
        } else {
            bdcFwqlQO.setQxdm(null);
        }
        // 2是自助查询机
        bdcFwqlQO.setCxly("2");
        //查询权利信息
        //是否要查询多幢信息
        bdcFwqlQO.setCxdz(fwqlCxRequestDTO.getCxdz());
        bdcFwqlQO.setCxyg(fwqlCxRequestDTO.getCxyg());
        bdcFwqlQO.setCxht(fwqlCxRequestDTO.getCxht());
        List<BdcFwqlDTO> bdcFwqlList = bdcZfxxCxFeignService.listBdcFwqlDTO(bdcFwqlQO);
        return dealFwqlxx(bdcFwqlList,cdsj,bdcFwqlQO);
    }

    @AnalysisAuditLog(name = LogCzxxConstants.CXLB_CXJCX_YFCX, description = "")
    public Map<String, Object> yfcxWebServiceQuery(String json, String ps) {
        Map<String, Object> map = new HashMap();
        map.put("json", json);
        return map;
    }

    @AnalysisAuditLog(name = LogCzxxConstants.CXLB_CXJCX_WFCX, description = "")
    public Map<String, Object> wfcxWebServiceQuery(String json, String ps) {
        Map<String, Object> map = new HashMap();
        map.put("json", json);
        return map;
    }
    @PublicQueryLog(name = LogCzxxConstants.CXLB_CXJCX_YFCX, description = "")
    public Map<String, Object> yfcxWebServicePublicQueryLog(String json, String ps) {
        Map<String, Object> map = new HashMap();
        map.put("json", json);
        return map;
    }

    @PublicQueryLog(name = LogCzxxConstants.CXLB_CXJCX_WFCX, description = "")
    public Map<String, Object> wfcxWebServicePublicQueryLog(String json, String ps) {
        Map<String, Object> map = new HashMap();
        map.put("json", json);
        return map;
    }
    @PublicQueryLog(name = LogCzxxConstants.CXLB_CXJCX_FWQS, description = "")
    public Map<String, Object> fwqsWebServiceQuery(String json, String ps) {
        Map<String, Object> map = new HashMap();
        map.put("json", json);
        return map;
    }
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param qlrList
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQlrDO>
     * @description  判断权利人证件号是否存在异常情况
     */
    public boolean checkBdcQlrHasZjhEx(List qlrList){
        boolean hasEx = false;
        for(Object qlr:qlrList){
            if(qlr != null && StringUtils.isNotBlank(((BaseQlrRequest)qlr).getQlr())){
                List<BdcQlrDO> tempList = listBdcFdcqQlrByQlrmc(((BaseQlrRequest)qlr).getQlr());
                if(CollectionUtils.isNotEmpty(tempList)){
                    for(BdcQlrDO tempQlr:tempList){
                        if(StringUtils.isBlank(tempQlr.getZjh())
                                || (tempQlr.getZjh().length() != 15
                                && tempQlr.getZjh().length() != 18)){
                            hasEx = true;
                            LOGGER.error("自助查询机查询人证件号存在异常：qlr:{} ,qlrzjh:{}", tempQlr.getQlrmc(), tempQlr.getZjh());
                            break;
                        }
                    }
                }
            }
        }
        return hasEx;
    }

    /**
     * @param qlrList
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQlrDO>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 判断权利人证件号是否存在异常情况, 增加不动产权证的查询，
     * 如果产权的权利人证件号正常，则不验证其他产权的权利人数据情况
     * 59336 【南通大市】关于修改产权查询接口调取逻辑需求
     */
    public boolean checkBdcQlrHasZjhExBycqzh(List<FwqlCxRequestQlr> qlrList) {
        boolean hasEx = false;
        for (FwqlCxRequestQlr qlr : qlrList) {
            if (qlr != null && StringUtils.isNotBlank(qlr.getQlr())) {
                List<BdcQlrDO> tempList = listBdcFdcqQlr(qlr.getQlr(), qlr.getCqzh());
                if (CollectionUtils.isNotEmpty(tempList)) {
                    for (BdcQlrDO tempQlr : tempList) {
                        if (StringUtils.isBlank(tempQlr.getZjh())
                                || (tempQlr.getZjh().length() != 15
                                && tempQlr.getZjh().length() != 18)) {
                            hasEx = true;
                            LOGGER.error("自助查询机查询人证件号存在异常：qlr:{} ,qlrzjh:{}", tempQlr.getQlrmc(), tempQlr.getZjh());
                            break;
                        }
                    }
                }
            }
        }


        return hasEx;
    }

    /**
     * @param qlrmc
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQlrDO>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 根据 姓名,产权证号 查询 产权的权利人
     */
    private List<BdcQlrDO> listBdcFdcqQlr(String qlrmc, String bdcqzh) {
        if (StringUtils.isNotBlank(qlrmc)) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("qlrmc", qlrmc);
            paramMap.put("bdcqzh", bdcqzh);
            paramMap.put("qlrlb", CommonConstantUtils.QLRLB_QLR);
            return bdcdjMapper.queryFdcqBdcQlr(paramMap);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @param qlrmc
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcQlrDO>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据 姓名 查询 产权的权利人
     */
    private List<BdcQlrDO> listBdcFdcqQlrByQlrmc(String qlrmc) {
        if (StringUtils.isNotBlank(qlrmc)) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("qlrmc",qlrmc);
            paramMap.put("qlrlb",CommonConstantUtils.QLRLB_QLR);
            return bdcdjMapper.queryFdcqBdcQlr(paramMap);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 处理房屋信息
     * @param zfxxList
     * @param cdsj
     * @return
     */
    private List<ZfcxResponseFw> dealFwxx(List<BdcZfxxDTO> zfxxList,String cdsj){
        List<ZfcxResponseFw> fwList = new ArrayList<>();
        QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
        if(CollectionUtils.isNotEmpty(zfxxList)){
            for(int i = 0 ; i < zfxxList.size() ; i++){
                ZfcxResponseFw zfcxResponseFw = new ZfcxResponseFw();
                zfcxResponseFw.setQlrxxlist(new ArrayList<>());
                BdcZfxxDTO zfxxDTO = zfxxList.get(i);
                dozerBeanMapper.map(zfxxDTO,zfcxResponseFw,"zfcxDTOToFw");

              if(Objects.nonNull(zfxxDTO.getSfhtba())
                      && CommonConstantUtils.SF_S_DM.equals(zfxxDTO.getSfhtba())){
                    //尝试查询合同的权利人
                    Example example = new Example(HtbaQlrDO.class);
                    example.createCriteria().andEqualTo("baid",zfxxDTO.getXmid());
                    List<HtbaQlrDO> htbaQlrs = entityMapper.selectByExample(example);
                    if(CollectionUtils.isNotEmpty(htbaQlrs)){
                        zfcxResponseFw.setQlr(htbaQlrs.stream().map(HtbaQlrDO::getQlrmc).collect(Collectors.joining(",")));
                        for (HtbaQlrDO htbaQlrDO : htbaQlrs) {
                            ZfcxResponseQlrxx qlrxx = new ZfcxResponseQlrxx();
                            qlrxx.setQlr(htbaQlrDO.getQlrmc());
                            qlrxx.setQlrzjh(htbaQlrDO.getZjh());
                            if (zfxxDTO.getDjsj() != null) {
                                qlrxx.setDbsj(DateUtil.formateTime(zfxxDTO.getDjsj()));
                            }
                            zfcxResponseFw.getQlrxxlist().add(qlrxx);
                        }
                    }
                } else {
                    //正常的房屋
                    // 处理限制状态
                    queryQlRequestDTO.setBdcdyh(zfxxDTO.getBdcdyh());
                    queryQlRequestDTO.setWithXm(true);
                    Map<String, Object> xzztMap = getXzzt(queryQlRequestDTO, true);
                    if (xzztMap != null) {
                        zfcxResponseFw.setXzzt(MapUtils.getString(xzztMap, "xzzt"));
                    }
                    // 处理权利人
                    List<BdcQlrDO> bdcQlrList = commonService.listBdcQlrByXmid(zfxxDTO.getXmid(), CommonConstantUtils.QLRLB_QLR);
                    if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                        for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                            ZfcxResponseQlrxx qlrxx = new ZfcxResponseQlrxx();
                            dozerBeanMapper.map(bdcQlrDO, qlrxx, "bdcQlrToQlr");
                            if (zfxxDTO.getDjsj() != null) {
                                qlrxx.setDbsj(DateUtil.formateTime(zfxxDTO.getDjsj()));
                            }
                            zfcxResponseFw.getQlrxxlist().add(qlrxx);
                        }
                    }
                }

                // 处理序号
                zfcxResponseFw.setXh((i+1)+"");

                // 处理查档时间
                zfcxResponseFw.setCdsj(cdsj);

                // 处理层数 总层数/所在层
                zfcxResponseFw.setCs(CommonUtil.ternaryOperator(zfxxDTO.getZcs())
                        + "/" + CommonUtil.ternaryOperator(zfxxDTO.getSzmyc()));

                // 处理 土地使用权期限
                zfcxResponseFw.setTdsyqx(getTdsyqx(zfxxDTO.getTdsyqssj(),zfxxDTO.getTdsyjssj()));

                // 处理面积  宗地面积/房屋面积
                zfcxResponseFw.setMj(CommonUtil.ternaryOperator(zfxxDTO.getZdmj())
                        + "/" + CommonUtil.ternaryOperator(zfxxDTO.getJzmj()));
                fwList.add(zfcxResponseFw);
            }
        }
        return fwList;
    }
    /**
     * 处理房屋权利信息
     * @param fwqlList
     * @param cdsj
     * @return
     */
    private List<FwqlCxResponseQl> dealFwqlxx(List<BdcFwqlDTO> fwqlList,String cdsj,BdcFwqlQO bdcFwqlQO){
        List<FwqlCxResponseQl> fwqlCxResponseQlList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(fwqlList)){
            for(int i=0; i<fwqlList.size();i++){
                FwqlCxResponseQl fwqlCxResponseQl = new FwqlCxResponseQl();
                fwqlCxResponseQl.setQlrxxlist(new ArrayList<>());
                BdcFwqlDTO bdcFwqlDTO = fwqlList.get(i);
                dozerBeanMapper.map(bdcFwqlDTO,fwqlCxResponseQl,"fwqlToResponseQl");
                if (Objects.nonNull(bdcFwqlDTO.getSfhtba()) && bdcFwqlDTO.getSfhtba()){
                    //尝试查询合同的权利人
                    Example example = new Example(HtbaQlrDO.class);
                    example.createCriteria().andEqualTo("baid",bdcFwqlDTO.getXmid());
                    List<HtbaQlrDO> htbaQlrs = entityMapper.selectByExample(example);
                    if(CollectionUtils.isNotEmpty(htbaQlrs)){
                        for (HtbaQlrDO htbaQlrDO : htbaQlrs) {
                            FwqlCxResponseQlrxx qlrxx = new FwqlCxResponseQlrxx();
                            qlrxx.setQlr(htbaQlrDO.getQlrmc());
                            qlrxx.setQlrzjh(htbaQlrDO.getZjh());
                            if (bdcFwqlDTO.getDjsj() != null) {
                                qlrxx.setDbsj(DateUtil.formateTime(bdcFwqlDTO.getDjsj()));
                            }
                            fwqlCxResponseQl.getQlrxxlist().add(qlrxx);
                        }
                    }
                } else {
                    // 处理限制状态
                    QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
                    queryQlRequestDTO.setBdcdyh(bdcFwqlDTO.getBdcdyh());
                    queryQlRequestDTO.setWithXm(true);
                    Map<String, Object> xzztMap = getXzzt(queryQlRequestDTO, true);
                    if (xzztMap != null) {
                        fwqlCxResponseQl.setXzzt(MapUtils.getString(xzztMap, "xzzt"));
                        fwqlCxResponseQl.setCfxxlist((List<FwqlCxResponseCfxx>) MapUtils.getObject(xzztMap, "cfxxList"));
                        fwqlCxResponseQl.setDyaqxxlist((List<FwqlCxResponseDyaqxx>) MapUtils.getObject(xzztMap, "dyaqxxList"));
                    }
                    // 异议信息
                    fwqlCxResponseQl.setYyxxlist(getYyxx(queryQlRequestDTO));
                    // 预告信息
                    fwqlCxResponseQl.setYgxxlist(getYgxx(queryQlRequestDTO));
                    if(Objects.nonNull(bdcFwqlQO.getCxyg()) && bdcFwqlQO.getCxyg()
                            && CollectionUtils.isNotEmpty(fwqlCxResponseQl.getYgxxlist())){
                        for (FwqlCxResponseYgxx fwqlCxResponseYgxx : fwqlCxResponseQl.getYgxxlist()) {
                            if(StringUtils.isBlank(fwqlCxResponseYgxx.getXmid())){
                                continue;
                            }
                            // 处理权利人
                            List<BdcQlrDO> bdcQlrList = commonService.listBdcQlrByXmid(fwqlCxResponseYgxx.getXmid(), CommonConstantUtils.QLRLB_QLR);
                            if (CollectionUtils.isNotEmpty(bdcQlrList)){
                                String qlrmc = bdcQlrList.stream().map(BdcQlrDO::getQlrmc).collect(Collectors.joining("/"));
                                String qlrzjh = bdcQlrList.stream().map(BdcQlrDO::getZjh).collect(Collectors.joining("/"));
                                fwqlCxResponseYgxx.setQlrmc(qlrmc);
                                fwqlCxResponseYgxx.setYgqlr(qlrmc);
                                fwqlCxResponseYgxx.setQlrzjh(qlrzjh);
                            }
                            List<BdcQlrDO> bdcYwrList = commonService.listBdcQlrByXmid(fwqlCxResponseYgxx.getXmid(), CommonConstantUtils.QLRLB_YWR);
                            if (CollectionUtils.isNotEmpty(bdcYwrList)){
                                if (CollectionUtils.isNotEmpty(bdcYwrList)){
                                    String ywrmc = bdcYwrList.stream().map(BdcQlrDO::getQlrmc).collect(Collectors.joining("/"));
                                    fwqlCxResponseYgxx.setYgywr(ywrmc);
                                }
                            }
                        }
                    }
                    // 锁定信息
                    fwqlCxResponseQl.setSdxxlist(getSdxx(bdcFwqlDTO.getBdcdyh(), bdcFwqlDTO.getXmid()));
                    // 土地信息
                    fwqlCxResponseQl.setTdxxlist(getTdxx(bdcFwqlDTO.getBdcdyh(), fwqlCxResponseQl.getQlxz()));
                    // 处理权利人
                    List<BdcQlrDO> bdcQlrList = commonService.listBdcQlrByXmid(bdcFwqlDTO.getXmid(), CommonConstantUtils.QLRLB_QLR);
                    StringBuffer qlrs = new StringBuffer();
                    StringBuffer qlrzjhs = new StringBuffer();
                    if (CollectionUtils.isNotEmpty(bdcQlrList)) {
                        for (BdcQlrDO bdcQlrDO : bdcQlrList) {
                            qlrs.append(bdcQlrDO.getQlrmc() + ",");
                            qlrzjhs.append(bdcQlrDO.getZjh() + ",");
                            FwqlCxResponseQlrxx qlrxx = new FwqlCxResponseQlrxx();
                            dozerBeanMapper.map(bdcQlrDO, qlrxx, "FwqlCxbdcQlrToQlr");
                            if (bdcFwqlDTO.getDjsj() != null) {
                                qlrxx.setDbsj(DateUtil.formateTime(bdcFwqlDTO.getDjsj()));
                            }
                            if (bdcFwqlDTO.getFj() != null) {
                                qlrxx.setFj(bdcFwqlDTO.getFj());
                            }
                            fwqlCxResponseQl.getQlrxxlist().add(qlrxx);
                        }
                    }
                    // 合并的权利人信息
                    if (qlrs.length() > 0) {
                        fwqlCxResponseQl.setQlr(qlrs.substring(0, qlrs.length() - 1));
                    }
                    if (qlrzjhs.length() > 0) {
                        fwqlCxResponseQl.setQlrzjh(qlrzjhs.substring(0, qlrzjhs.length() - 1));
                    }
                    // 处理序号
                    fwqlCxResponseQl.setXh((i + 1) + "");
                    // 处理查档时间
                    fwqlCxResponseQl.setCdsj(cdsj);
                    // 处理层数 总层数/所在层
                    fwqlCxResponseQl.setCs(CommonUtil.ternaryOperator(bdcFwqlDTO.getZcs()) + "/" + CommonUtil.ternaryOperator(bdcFwqlDTO.getSzc()));
                    // 处理 土地使用权期限
                    String tdsyqx = getTdsyqx(bdcFwqlDTO.getTdsyqssj(), bdcFwqlDTO.getTdsyjssj());
                    fwqlCxResponseQl.setTdsyqx(tdsyqx);

                    // 处理面积  宗地面积/房屋面积
                    fwqlCxResponseQl.setMj(CommonUtil.ternaryOperator(bdcFwqlDTO.getZdmj())
                            + "/" + CommonUtil.ternaryOperator(bdcFwqlDTO.getJzmj()));

                    // 房屋信息
                    List<FwqlCxResponseFwxx> fwxxList = new ArrayList<>();
                    if (bdcFwqlQO.getCxdz() && StringUtils.isNotBlank(bdcFwqlDTO.getQlid())) {
                        //查询项目内多幢
                        Example example = new Example(BdcFdcqFdcqxmDO.class);
                        example.createCriteria().andEqualTo("qlid", bdcFwqlDTO.getQlid());
                        List<BdcFdcqFdcqxmDO> bdcFdcqFdcqxms = entityMapper.selectByExample(example);
                        if (CollectionUtils.isNotEmpty(bdcFdcqFdcqxms)) {
                            BdcFwqlDTO dto = new BdcFwqlDTO();
                            for (BdcFdcqFdcqxmDO bdcFdcqFdcqxmDO : bdcFdcqFdcqxms) {
                                FwqlCxResponseFwxx fwxx = new FwqlCxResponseFwxx();
                                BeanUtils.copyProperties(bdcFwqlDTO, dto);
                                BeanUtils.copyProperties(bdcFdcqFdcqxmDO, dto);
                                dozerBeanMapper.map(dto, fwxx, "fwxxToResponseFw");
                                fwxx.setTdsyqx(tdsyqx);
                                fwxxList.add(fwxx);
                            }
                        }
                    } else {
                        FwqlCxResponseFwxx fwxx = new FwqlCxResponseFwxx();
                        dozerBeanMapper.map(bdcFwqlDTO, fwxx, "fwxxToResponseFw");
                        fwxx.setTdsyqx(tdsyqx);
                        fwxxList.add(fwxx);
                    }
                    fwqlCxResponseQl.setFwxxlist(fwxxList);

                    //附属设施
                    fwqlCxResponseQl.setFsssxxlist(getFsss(bdcFwqlDTO, tdsyqx));
                }
                // 将处理的信息放到返回结果中
                fwqlCxResponseQlList.add(fwqlCxResponseQl);
            }
        }
        return fwqlCxResponseQlList;
    }

    /**
     * 获取限制状态信息
     * @param queryQlRequestDTO
     * @param xzxx 是否返回限制信息具体数据
     * @return
     */
    private Map<String,Object> getXzzt(QueryQlRequestDTO queryQlRequestDTO,boolean xzxx){
        Map<String,Object> resultMap = new HashMap<>();
        String xzzt = "";
        List<CfQlWithXmQlrDTO> cfList = commonService.listCfByBdcdyh(queryQlRequestDTO);
        if(CollectionUtils.isNotEmpty(cfList)){
            xzzt += "查封";
            if(xzxx){
                List<FwqlCxResponseCfxx> cfxxList = new ArrayList<>();
                for(CfQlWithXmQlrDTO dto : cfList){
                    FwqlCxResponseCfxx cf = new FwqlCxResponseCfxx();
                    dozerBeanMapper.map(dto.getBdcql(),cf,"bdcCfToResponseCf");
                    if(dto.getBdcXmDO() != null) {
                        dozerBeanMapper.map(dto.getBdcXmDO(), cf, "bdcXmToResponseCf");
                    }
                    String cfqx = "";
                    if(dto.getBdcql().getCfqssj()  != null){
                        cfqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getCfqssj()) + "起";
                    }
                    if(dto.getBdcql().getCfjssj() != null){
                        cfqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getCfjssj()) + "止";
                    }
                    cf.setCfqx(cfqx);
                    cfxxList.add(cf);
                }
                resultMap.put("cfxxList",cfxxList);
            }

        }
        List<DyQlWithXmQlrDTO> dyaqList = commonService.listDyaqByBdcdyh(queryQlRequestDTO);
        if(CollectionUtils.isNotEmpty(dyaqList)){
            if(xzzt.length() == 0){
                xzzt += "抵押";
            }else{
                xzzt += "/抵押";
            }
            List<FwqlCxResponseDyaqxx> dyaqxxList = new ArrayList<>();
            for(DyQlWithXmQlrDTO dto : dyaqList){
                FwqlCxResponseDyaqxx dyaqxx = new FwqlCxResponseDyaqxx();
                dozerBeanMapper.map(dto.getBdcql(),dyaqxx,"bdcDyaqToResponseDyaq");
                if(dto.getBdcXmDO() != null) {
                    dozerBeanMapper.map(dto.getBdcXmDO(), dyaqxx, "bdcXmToResponseDyaq");
                }
                List<BdcQlrDO> bdcQlrDOList = commonService.listBdcQlrByXmid(dto.getBdcql().getXmid(),CommonConstantUtils.QLRLB_QLR);
                if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
                    dyaqxx.setDyaqlr(bdcQlrDOList.get(0).getQlrmc());
                    dyaqxx.setDjzmh(dto.getBdcXmDO().getBdcqzh());
                }
                String zwlxqx = "";
                if(dto.getBdcql().getZwlxqssj() != null){
                    zwlxqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getZwlxqssj()) + "起";
                }
                if(dto.getBdcql().getZwlxjssj() != null){
                    zwlxqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getZwlxjssj()) + "止";
                }
                dyaqxx.setZwlxqx(zwlxqx);
                if(dyaqxx != null){
                    dyaqxxList.add(dyaqxx);
                }
            }
            resultMap.put("dyaqxxList",dyaqxxList);
        }

        if(StringUtils.isNotBlank(xzlx)) {
            // 展示预告、预抵押、异议、锁定等其它限制状态，根据盐城需求先不返回详细信息，后期其它需求需要自行添加
            String[] xzlxArray = xzlx.split(",");
            if(null != xzlxArray && xzlxArray.length > 0) {
                List<String> xzlxList = Arrays.asList(xzlxArray);
                // 预告 yg
                if(xzlxList.contains("yg")) {
                    List<YgQlWithXmQlrDTO> ygList = commonService.listYgByBdcdyh(queryQlRequestDTO);
                    xzzt = this.getXzxxStr(ygList, xzzt, "预告");
                }

                // 预抵押 ydya
                if(xzlxList.contains("ydya")) {
                    List<YgQlWithXmQlrDTO> ygDyaqList = commonService.listYgDyaqByBdcdyh(queryQlRequestDTO);
                    xzzt = this.getXzxxStr(ygDyaqList, xzzt, "预抵押");
                }

                // 异议 yy
                if(xzlxList.contains("yy")) {
                    List<YyQlWithXmQlrDTO> yyList = commonService.listYyByBdcdyh(queryQlRequestDTO);
                    xzzt = this.getXzxxStr(yyList, xzzt, "异议");
                }

                // 锁定 sd
                if(xzlxList.contains("sd")) {
                    List<YcslQueryXzqlByBdcdyhSdxx> sdList = commonService.listSdxxByBdcdyh(queryQlRequestDTO.getBdcdyh());
                    xzzt = this.getXzxxStr(sdList, xzzt, "锁定");
                }
            }
        }

        resultMap.put("xzzt",xzzt);
        return resultMap;
    }

    /**
     * 拼接限制状态展示
     * @param xzxxList 限制信息数据
     * @param xzzt 已有限制状态字段
     * @param name 当前限制状态名称
     * @return 新的限制状态名称
     */
    private <T> String getXzxxStr(List<T> xzxxList, String xzzt, String name) {
        if(CollectionUtils.isNotEmpty(xzxxList)) {
            if (xzzt.length() == 0) {
                xzzt += name;
            } else {
                xzzt += "/" + name;
            }
        }
        return xzzt;
    }

    /**
     * 查询锁定信息  包括不动产单元锁定和证书锁定
     *
     * @param bdcdyh
     * @param xmid
     * @return
     */
    public List<FwqlCxResponseSdxx> getSdxx(String bdcdyh, String xmid) {
        List<FwqlCxResponseSdxx> responseSdxxList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            // 单元锁定
            List<BdcDysdDO> dysdDOList = commonService.listDysdByBdcdyh(bdcdyh, CommonConstantUtils.SDZT_SD);
            if (CollectionUtils.isNotEmpty(dysdDOList)) {
                for (BdcDysdDO dysdDO : dysdDOList) {
                    FwqlCxResponseSdxx sdxx = new FwqlCxResponseSdxx();
                    dozerBeanMapper.map(dysdDO, sdxx, "bdcDysdToResponseSdxx");
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
     * 查询异议信息
     * @param queryQlRequestDTO
     * @return
     */
    private List<FwqlCxResponseYyxx> getYyxx(QueryQlRequestDTO queryQlRequestDTO){
        List<FwqlCxResponseYyxx> yyxxList = new ArrayList<>();
        List<YyQlWithXmQlrDTO> yyList = commonService.listYyByBdcdyh(queryQlRequestDTO);
        if(CollectionUtils.isNotEmpty(yyList)){
            for(YyQlWithXmQlrDTO dto : yyList){
                FwqlCxResponseYyxx yy = new FwqlCxResponseYyxx();
                dozerBeanMapper.map(dto.getBdcql(),yy,"bdcYyToResponseYy");
                if(dto.getBdcXmDO() != null) {
                    dozerBeanMapper.map(dto.getBdcXmDO(), yy, "bdcXmToResponseYy");
                }
                String qlqx = "";
                if(dto.getBdcql().getYydjksrq()  != null){
                    qlqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getYydjksrq()) + "起";
                }
                if(dto.getBdcql().getYydjjsrq() != null){
                    qlqx += DateUtil.formateTimeYmdWithSplit(dto.getBdcql().getYydjjsrq()) + "止";
                }
                yy.setQlqx(qlqx);
                List<BdcQlrDO> yyQlrs = commonService.listBdcQlrByXmid(dto.getBdcql().getXmid(),CommonConstantUtils.QLRLB_QLR);
                if(CollectionUtils.isNotEmpty(yyQlrs)){
                    StringBuffer sqr = new StringBuffer();
                    StringBuffer yydjzmh = new StringBuffer();
                    for(BdcQlrDO bdcQlr:yyQlrs){
                        sqr.append(bdcQlr.getQlrmc()+",");
                        yydjzmh.append(bdcQlr.getBdcqzh()+",");
                    }
                    yy.setSqr(sqr.substring(0,sqr.length()-1));
                    yy.setYydjzmh(yydjzmh.substring(0,yydjzmh.length()-1));
                }
                yyxxList.add(yy);
            }
        }
        return yyxxList;
    }

    /**
     * 查询预告信息
     * @param queryQlRequestDTO
     * @return
     */
    private List<FwqlCxResponseYgxx>  getYgxx(QueryQlRequestDTO queryQlRequestDTO){
        List<FwqlCxResponseYgxx> ygxxList = new ArrayList<>();
        List<YgQlWithXmQlrDTO> ygList = commonService.listYgByBdcdyh(queryQlRequestDTO);
        if(CollectionUtils.isNotEmpty(ygList)){
            for(YgQlWithXmQlrDTO dto : ygList){
                FwqlCxResponseYgxx yg = new FwqlCxResponseYgxx();
                dozerBeanMapper.map(dto.getBdcql(),yg,"bdcYgToResponseYg");
                if(dto.getBdcXmDO() != null) {
                    dozerBeanMapper.map(dto.getBdcXmDO(), yg, "bdcXmToResponseYg");
                }
                ygxxList.add(yg);
            }
        }
        return ygxxList;
    }

    /**
     * 查询土地信息
     * @param bdcdyh
     * @param qlxz
     * @return
     */
    private List<FwqlCxResponseTdxx>  getTdxx(String bdcdyh,String qlxz){
        List<FwqlCxResponseTdxx> responseTdList = new ArrayList<>();
        HashMap<String, String> map = new HashMap();
        map.put("bdcdyh",bdcdyh.substring(0,19)+"W00000000");
        List<QlfQlJsydsyqDO> qlfQlJsydsyqList = qlfQlJsydsyqMapper.queryQlfQlJsydsyqList(map);
        if(CollectionUtils.isNotEmpty(qlfQlJsydsyqList)){
            for(QlfQlJsydsyqDO qlJsydsyqDO:qlfQlJsydsyqList){
                FwqlCxResponseTdxx responseTd = new FwqlCxResponseTdxx();
                dozerBeanMapper.map(qlJsydsyqDO,responseTd,"bdcJsydsyqToResponseTd");
                responseTd.setQdfs(qlxz);

                // 35739 权属证明接口增加宗地用途和面积
                if(StringUtils.isNotBlank(qlJsydsyqDO.getYwh())) {
                    BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(qlJsydsyqDO.getYwh());
                    if(null != bdcXmDO) {
                        responseTd.setZdzhmj(null == bdcXmDO.getZdzhmj() ? "":  String.valueOf(bdcXmDO.getZdzhmj()));

                        if(StringUtils.isNotBlank(bdcXmDO.getZdzhyt())) {
                            responseTd.setZdzhyt(bdcXmDO.getZdzhyt());
                            zdConvertUtils.convertEntityToMc(responseTd);
                        } else {
                            responseTd.setZdzhyt("");
                        }
                    }
                }

                responseTdList.add(responseTd);
            }
        }
        return responseTdList;
    }

    /**
     * 查询附属设施
     * @param bdcFwqlDTO
     * @param tdsyqx
     * @return
     */
    private List<FwqlCxResponseFsssxx> getFsss(BdcFwqlDTO bdcFwqlDTO,String tdsyqx){
        List<BdcFwfsssDO> bdcFwfsssDOList = bdcQllxFeignService.listFwfsss(bdcFwqlDTO.getXmid());
        List<FwqlCxResponseFsssxx> responseFsssList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(bdcFwfsssDOList)){
            for(BdcFwfsssDO fwfsssDO : bdcFwfsssDOList){
                FwqlCxResponseFsssxx fsss = new FwqlCxResponseFsssxx();
                dozerBeanMapper.map(fwfsssDO,fsss,"bdcFsssToResponseFsss");
                dozerBeanMapper.map(bdcFwqlDTO,fsss,"bdcFwToResponseFsss");
                fsss.setTdsyqx(tdsyqx);
                responseFsssList.add(fsss);

            }
        }
        return responseFsssList;
    }

    /**
     * 组织土地使用期限
     * @param tdsyqssj
     * @param tdsyjssj
     * @return
     */
    private String getTdsyqx(Date tdsyqssj,Date tdsyjssj){
        String tdsyqx = "国有土地使用权";
        if(tdsyqssj != null){
            tdsyqx += DateUtil.formateTimeYmdWithSplit(tdsyqssj) + "起";
        }
        if(tdsyjssj != null){
            tdsyqx += DateUtil.formateTimeYmdWithSplit(tdsyjssj) + "止";
        }
        return tdsyqx;
    }

    /**
     * @description 查询房屋信息
     * @param bdcFwxxQOList
     * @return: java.util.List<java.util.Map>
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/11/15 15:44
     */
    public List<FwlbResponse> getFwxxcx(List<BdcFwxxQO> bdcFwxxQOList) {
        //房屋信息查询
        List<BdcQlrQO> qlrxx = new ArrayList<>(bdcFwxxQOList.size());
        QueryQlRequestDTO queryQlRequestDTO = new QueryQlRequestDTO();
        //封装查询参数
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        for (BdcFwxxQO bdcFwxxQO : bdcFwxxQOList) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrmc(bdcFwxxQO.getQlrmc());
            bdcQlrQO.setZjh(bdcFwxxQO.getQlrzjhm());
            qlrxx.add(bdcQlrQO);
        }
        //添加权利人信息
        bdcZfxxQO.setQlrxx(qlrxx);
        //查询房屋信息
        List<BdcZfxxDTO> listFwxx = bdcZfxxCxFeignService.listBdcNtZfxxDTO(bdcZfxxQO);
        List<FwlbResponse> fwlbResponseList = null;
        //查询权利人
        if (CollectionUtils.isNotEmpty(listFwxx)) {
            //根据qlid对返回结果去重
            listFwxx = listFwxx.stream().collect(Collectors.collectingAndThen(
                    Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BdcZfxxDTO::getQlid))),
                    ArrayList::new));
            //处理数据
            fwlbResponseList = new ArrayList<>(listFwxx.size());
            for (BdcZfxxDTO bdcZfxxDTO : listFwxx) {
                FwlbResponse fwlbResponse = new FwlbResponse();
                dozerBeanMapper.map(bdcZfxxDTO,fwlbResponse,"zfcxDTOToFwlb");
                // 处理限制状态
                queryQlRequestDTO.setBdcdyh(bdcZfxxDTO.getBdcdyh());
                queryQlRequestDTO.setWithXm(true);
                Map<String, Object> xzztMap = getXzzt(queryQlRequestDTO, false);
                if (xzztMap != null) {
                    fwlbResponse.setQlzt(StringUtils.isNotBlank(MapUtils.getString(xzztMap, "xzzt")) ? MapUtils.getString(xzztMap, "xzzt") : "正常");
                }
                //处理权利人
                BdcXmDO bdcXmDO = commonService.getBdcXmByXmid(bdcZfxxDTO.getXmid());
                if (Objects.nonNull(bdcXmDO)) {
                    String qlr = bdcXmDO.getQlr();
                    fwlbResponse.setQlr(qlr);
                    fwlbResponseList.add(fwlbResponse);
                }
            }
        }
        return fwlbResponseList;
    }


}
