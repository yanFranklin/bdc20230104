package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.lzzt.CourtLzztRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.lzzt.CourtLzztResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.*;
import cn.gtmap.realestate.common.core.qo.certificate.BdcZsQO;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcPpFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcDjbxxRestService;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfDaCheckLog;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.inf.yancheng.BdcCourtService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "bdcCourtServiceImpl")
public class BdcCourtServiceImpl implements BdcCourtService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcCourtServiceImpl.class);

    private static final String DATA_PATTERN = "yyyy/MM/dd";


    @Autowired
    private BdcSdFeignService bdcSdFeignService;

    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private BdcXmFeignService bdcXmFeignService;

    @Autowired
    private BdcDjbxxRestService bdcDjbxxRestService;

    @Autowired
    private BdcPpFeignService bdcPpFeignService;

    @Autowired
    private BdcZsFeignService zsFeignService;

    @Autowired
    private BdcDbxxFeignService bdcDbxxFeignService;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    @Value("${court.filter:}")
    private String courtFilter;

    @Value("${data.version:standard}")
    private String dataVersion;

    /**
     * @param bdcDysdDOList
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 锁定不动产单元
     */
    @Override
    public ExchangeDsfCommonResponse sdBdcdy(List<BdcDysdDO> bdcDysdDOList) {
        if (CollectionUtils.isNotEmpty(bdcDysdDOList) && StringUtils.isNotBlank(bdcDysdDOList.get(0).getBdcdyh())
                && StringUtils.isNotBlank(bdcDysdDOList.get(0).getSdsqwh())) {
            try {
                BdcDysdDO param = new BdcDysdDO();
                param.setBdcdyh(CollectionUtils.isNotEmpty(bdcDysdDOList) ? bdcDysdDOList.get(0).getBdcdyh() : "");
                param.setSdzt(1);
                param.setSdsqwh(CollectionUtils.isNotEmpty(bdcDysdDOList) ? bdcDysdDOList.get(0).getSdsqwh() : "");
                List<BdcDysdDO> queryBdczsSd = bdcSdFeignService.queryBdcdySd(param);
                if (CollectionUtils.isNotEmpty(queryBdczsSd)) {
                    return ExchangeDsfCommonResponse.fail("该不动产单元号在此申请文号下已存在锁定信息!");
                }

                bdcSdFeignService.sdBdcdy(bdcDysdDOList);
                return ExchangeDsfCommonResponse.ok();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return ExchangeDsfCommonResponse.fail(e.getMessage());
            }
        } else {
            return ExchangeDsfCommonResponse.fail("请检查参数！");
        }
    }

    /**
     * @param bdcZssdDOList
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 锁定不动产证书
     */
    @Override
    public ExchangeDsfCommonResponse sdBdczs(List<BdcZssdDO> bdcZssdDOList) {
        if (CollectionUtils.isNotEmpty(bdcZssdDOList) && StringUtils.isNotBlank(bdcZssdDOList.get(0).getCqzh())
                && StringUtils.isNotBlank(bdcZssdDOList.get(0).getSdsqwh())) {
            try {
                BdcZssdDO param = new BdcZssdDO();
                String sdyy = CollectionUtils.isNotEmpty(bdcZssdDOList) ? bdcZssdDOList.get(0).getSdyy() : "";
                param.setCqzh(CollectionUtils.isNotEmpty(bdcZssdDOList) ? bdcZssdDOList.get(0).getCqzh() : "");
                param.setSdzt(1);
                param.setSdsqwh(CollectionUtils.isNotEmpty(bdcZssdDOList) ? bdcZssdDOList.get(0).getSdsqwh() : "");
                List<BdcZssdDO> queryBdczsSd = bdcSdFeignService.queryBdczsSd(param);
                if (CollectionUtils.isNotEmpty(queryBdczsSd)) {
                    return ExchangeDsfCommonResponse.fail("该证书在此申请文号下已存在锁定信息!");
                }
                BdcZssdDO zssdDO = new BdcZssdDO();
                BdcZsQO zsQO = new BdcZsQO();
                zsQO.setBdcqzh(param.getCqzh());
                List<BdcZsDO> zsDOList = zsFeignService.listBdcZs(zsQO);
                if (CollectionUtils.isNotEmpty(zsDOList)) {
                    BdcZsDO bdcZsDO = zsDOList.get(0);
                    bdcZssdDOList.forEach(bdcZssdDO -> {
                        bdcZssdDO.setZsid(bdcZsDO.getZsid());
                    });
                    bdcSdFeignService.sdBdczs(bdcZssdDOList, sdyy);
                    return ExchangeDsfCommonResponse.ok();
                } else {
                    return ExchangeDsfCommonResponse.fail("未查到该证书信息，请核查!");
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return ExchangeDsfCommonResponse.fail(e.getMessage());
            }
        } else {
            return ExchangeDsfCommonResponse.fail("请检查参数！");
        }
    }

    /**
     * @param bdcDysdDO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 解锁不动产单元
     */
    @Override
    public ExchangeDsfCommonResponse jsBdcdy(BdcDysdDO bdcDysdDO) {
        if (null != bdcDysdDO && StringUtils.isNotBlank(bdcDysdDO.getBdcdyh())
                && StringUtils.isNotBlank(bdcDysdDO.getSdsqwh())) {
            try {
                BdcDysdDO param = new BdcDysdDO();
                param.setBdcdyh(bdcDysdDO.getBdcdyh());
                param.setSdsqwh(bdcDysdDO.getSdsqwh());
                param.setSdzt(1);
                //同一bdcdyh和sdsqwh，只存在一条现势锁定数据
                List<BdcDysdDO> queryBdcdySd = bdcSdFeignService.queryBdcdySd(param);
                if (CollectionUtils.isEmpty(queryBdcdySd)) {
                    return ExchangeDsfCommonResponse.fail("根据bdcdyh未查询到不动产单元锁定信息!");
                }
                bdcDysdDO.setSdzt(0);
                bdcDysdDO.setDysdid(queryBdcdySd.get(0).getDysdid());
                List<BdcDysdDO> bdcDysdDOSd = new ArrayList<>();
                bdcDysdDOSd.add(bdcDysdDO);
//                bdcSdFeignService.jsBdcdyhByBdcdyh(bdcDysdDO);
                bdcSdFeignService.jsBdcdy(bdcDysdDOSd,bdcDysdDO.getJsyy());
                bdcDbxxFeignService.synQjBdcdyztSd(Arrays.asList(bdcDysdDO.getBdcdyh()), CommonConstantUtils.SDZT_JS);
                return ExchangeDsfCommonResponse.ok();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return ExchangeDsfCommonResponse.fail(e.getMessage());
            }
        } else {
            return ExchangeDsfCommonResponse.fail("请检查参数！");
        }
    }

    /**
     * @param bdcZssdDO
     * @return
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * 解锁不动产证书
     */
    @Override
    public ExchangeDsfCommonResponse jsBdczs(BdcZssdDO bdcZssdDO) {
        if (null != bdcZssdDO && StringUtils.isNotBlank(bdcZssdDO.getCqzh()) && StringUtils.isNotBlank(bdcZssdDO.getSdsqwh())) {
            try {
                BdcZssdDO param = new BdcZssdDO();
                param.setCqzh(bdcZssdDO.getCqzh());
                param.setSdzt(1);
                param.setSdsqwh(bdcZssdDO.getSdsqwh());
                //同一产权证号和锁定申请文号，只存在一条现势锁定数据
                List<BdcZssdDO> queryBdczsSd = bdcSdFeignService.queryBdczsSd(param);
                if (CollectionUtils.isEmpty(queryBdczsSd)) {
                    return ExchangeDsfCommonResponse.fail("根据产权证号未查询到该证书锁定信息!");
                }
                BdcZssdDO zssdDO = queryBdczsSd.get(0);
                zssdDO.setJsr(bdcZssdDO.getJsr());
                zssdDO.setJsyy(bdcZssdDO.getJsyy());
                bdcZssdDO.setZsid(zssdDO.getZsid());
                bdcZssdDO.setSdzt(0);
                bdcZssdDO.setZssdid(zssdDO.getZssdid());
                List<BdcZssdDO> bdcZssdDOSd = new ArrayList<>();
                bdcZssdDOSd.add(zssdDO);
                BdcZsDO bdcZsDO = zsFeignService.queryBdcZs(bdcZssdDO.getZsid());
                if (bdcZsDO != null) {
                    bdcDbxxFeignService.synQjBdcdyztSd(Arrays.asList(bdcZsDO.getBdcdyh()), CommonConstantUtils.SDZT_JS);
                } else {
                    return ExchangeDsfCommonResponse.fail("根据zsid查询证书数据失败!");
                }
//                bdcSdFeignService.jsBdczsByCqzh(bdcZssdDO);
                bdcSdFeignService.jsBdczs(bdcZssdDOSd,bdcZssdDO.getJsyy());
                return ExchangeDsfCommonResponse.ok();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                return ExchangeDsfCommonResponse.fail(e.getMessage());
            }
        } else {
            return ExchangeDsfCommonResponse.fail("请检查参数！");
        }
    }

    @Override
    public ExchangeDsfCommonResponse<CourtLzztResponseDTO> cxlzztByBdcdyh(CourtLzztRequestDTO requestDTO) {
        CourtLzztResponseDTO courtLzztResponseDTO = new CourtLzztResponseDTO();
        try {
            if (StringUtils.isEmpty(requestDTO.getBdcdyh())) {
                return ExchangeDsfCommonResponse.fail("bdcdyh参数为空");
            }
            /**
             * 判断是否是虚拟不动产单元号
             */
            if (BdcdyhToolUtils.checkXnbdcdyh(requestDTO.getBdcdyh())) {
                BdcXnbdcdyhGxDO bdcXnbdcdyhGxDO = new BdcXnbdcdyhGxDO();
                bdcXnbdcdyhGxDO.setXnbdcdyh(requestDTO.getBdcdyh());
                List<BdcXnbdcdyhGxDO> bdcXnbdcdyhGxDOList = bdcPpFeignService.queryBdcXnbdcdyhByCondition(bdcXnbdcdyhGxDO);
                if (CollectionUtils.isEmpty(bdcXnbdcdyhGxDOList)) {
                    courtLzztResponseDTO.setLzzt("0");
//                    responseDTO.setLzzt("0");
                } else {
//                    responseDTO.setLzzt("1");
                    courtLzztResponseDTO.setLzzt("1");
                }
                return ExchangeDsfCommonResponse.ok(courtLzztResponseDTO);
            } else {
                courtLzztResponseDTO.setLzzt("1");
//                responseDTO.setLzzt("1");
                return ExchangeDsfCommonResponse.ok(courtLzztResponseDTO);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExchangeDsfCommonResponse.fail(e.getMessage());
        }
    }

    @DsfDaCheckLog(logEventName = "法院查询产权信息接口",dsfFlag = "COURT",requester = "COURT",responser = "BDC",interfaceFlagCode = "01",checkFlagIndex = 0,checkFlagClassName = "cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.CourtYwxxcxRequestDTO",checkFlagLever = "0",interfaceUrl = "/realestate-exchange/rest/v1.0/interface/fy_qlrbdcqlxx")
    @Override
    public Object ywxxcx(CourtYwxxcxRequestDTO courtYwxxcxRequestDTO) {
        CourtYwxxcxResponseDTO responseDTO = new CourtYwxxcxResponseDTO();
        try {
            if (StringUtils.isBlank(courtYwxxcxRequestDTO.getZjzl())) {
                return ExchangeDsfCommonResponse.fail("zjzl不能为空!");
            }
            if (StringUtils.isBlank(courtYwxxcxRequestDTO.getQlrmc())) {
                return ExchangeDsfCommonResponse.fail("qlrmc不能为空!");
            }
            if (StringUtils.isBlank(courtYwxxcxRequestDTO.getZjh())) {
                return ExchangeDsfCommonResponse.fail("zjh不能为空!");
            }
            if (courtYwxxcxRequestDTO.getZjzl().equals(CommonConstantUtils.ZJZL_SFZ.toString()) && courtYwxxcxRequestDTO.getZjh().length() == 18) {
                courtYwxxcxRequestDTO.setFifteenNumZjh(CardNumberTransformation.idCard18to15(courtYwxxcxRequestDTO.getZjh()));
            }
            /**
             * 1.查询现势产权
             */
            List<BdcXmDO> bdcXmDOS = null;
            if("6".equals(courtYwxxcxRequestDTO.getZjzl())){
                bdcXmDOS = bdcXmMapper.listCourtXscqWithZjhOrQlrmc(courtYwxxcxRequestDTO);
            }else {
                bdcXmDOS = bdcXmMapper.listCourtXscqWithZjhAndQlrmc(courtYwxxcxRequestDTO);
            }
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                LOGGER.info("查询当前项目信息为空!");
                return ExchangeDsfCommonResponse.fail(ExchangeDsfCommonResponse.SUCCESS_CODE,"查询当前项目信息为空!");
            }

            // 过滤商品房首登和商品房首登批量业务生成的产权证
            List<String> filter = Arrays.asList(StringUtils.split(courtFilter, ","));
            bdcXmDOS = bdcXmDOS.stream().filter(x -> !filter.contains(x.getGzldyid())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                LOGGER.info("过滤商品房首登和商品房首登批量业务生成的产权证后的项目信息为空");
                return ExchangeDsfCommonResponse.fail(ExchangeDsfCommonResponse.SUCCESS_CODE,"过滤商品房首登和商品房首登批量业务生成的产权证后的项目信息为空!");
            }
            //盐城要求去除xm表中限制权利类型相关的xm信息
//            List<String> bdcdyhList = bdcXmDOS.stream().filter(
//                    bdcXmDO -> !CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx()) &&
//                            !CommonConstantUtils.QLLX_CF.equals(bdcXmDO.getQllx()) &&
//                            !CommonConstantUtils.QLLX_YG_DM.equals(bdcXmDO.getQllx()) &&
//                            !CommonConstantUtils.QLLX_YY.equals(bdcXmDO.getQllx())).map(BdcXmDO::getBdcdyh).distinct().collect(Collectors.toList());
            List<String> bdcdyhList = bdcXmDOS.stream().map(BdcXmDO::getBdcdyh).distinct().collect(Collectors.toList());
            if (CollectionUtils.isEmpty(bdcdyhList)) {
                return ExchangeDsfCommonResponse.fail("查询到的项目信息无bdcdyh信息");
            }

            LOGGER.info("---过滤之后的现势产权对应不动产单元号:{}，过滤参数:{}", bdcdyhList, filter);

            /**
             * 2
             */
            List<BdcQl> bdcQls = new ArrayList<>();
            if (bdcdyhList.size() >= 1000){
                List<List<String>> lists = groupListByQuantity(bdcdyhList, 700);
                for (int i = 0; i < lists.size(); i++) {
                    LOGGER.info("---分组后不动产单元号:{}",lists.get(i));
                    bdcQls.addAll(bdcQllxFeignService.listXzxxByBdcdyhs(lists.get(i)));
                }
            }else {
                bdcQls.addAll(bdcQllxFeignService.listXzxxByBdcdyhs(bdcdyhList));
            }
            List<String> xmidList = bdcXmDOS.stream().map(BdcXmDO::getXmid).distinct().collect(Collectors.toList());
            if (Constants.DATA_VERSION_YANCHENG.equals(dataVersion)) {
                if (xmidList.size() >= 1000){
                    List<List<String>> lists = groupListByQuantity(xmidList, 700);
                    for (int i = 0; i < lists.size(); i++) {
                        LOGGER.info("---分组后xmid:{}",lists.get(i));
                        bdcQls.addAll(bdcQllxFeignService.listXzxxByXmids(lists.get(i)));
                    }
                }else {
                    bdcQls.addAll(bdcQllxFeignService.listXzxxByXmids(xmidList));
                }
            }
            if (CollectionUtils.isEmpty(bdcQls)) {
                return ExchangeDsfCommonResponse.ok(responseDTO);
            }
            LOGGER.info("---过滤之后的现势产权对应不动产单元号:{}", JSON.toJSONString(bdcQls));

            List<CourtYwxxcxResponseTdsyqDTO> tdsyqList = new ArrayList<>();
            List<CourtYwxxcxResponseJsydsyqDTO> jsydsyqList = new ArrayList<>();
            List<CourtYwxxcxResponseFdcqDTO> fdcqList = new ArrayList<>();
            List<CourtYwxxcxResponseHysyqDTO> hysyqList = new ArrayList<>();
            List<CourtYwxxcxResponseGjzwsyqDTO> gjzwsyqList = new ArrayList<>();
            List<CourtYwxxcxResponseLqDTO> lqList = new ArrayList<>();
            List<CourtYwxxcxResponseDyaqDTO> dyaqList = new ArrayList<>();
            List<CourtYwxxcxResponseYgDTO> ygdjList = new ArrayList<>();
            List<CourtYwxxcxResponseCfDTO> cfdjList = new ArrayList<>();

            Map<String, BdcXmDO> bdcXmDOMap = bdcXmDOS.stream().collect(Collectors.toMap(BdcXmDO::getXmid, v -> v, (v1, v2) -> v1));
            for (BdcQl bdcQl : bdcQls) {
                BdcXmDO bdcXmDO = new BdcXmDO();
                if (bdcQl instanceof BdcTdsyqDO) {

                    BdcTdsyqDO bdcTdsyqDO = (BdcTdsyqDO) bdcQl;
                    String xmid = bdcTdsyqDO.getXmid();
                    if (!xmidList.contains(xmid)) {
                        continue;
                    }
                    if (Objects.nonNull(bdcXmDOMap) && Objects.nonNull(bdcXmDOMap.get(xmid))) {
                        bdcXmDO = bdcXmDOMap.get(xmid);
                    }
                    CourtYwxxcxResponseTdsyqDTO tdsyqDTO = new CourtYwxxcxResponseTdsyqDTO();
                    tdsyqDTO.setBdcdyh(bdcTdsyqDO.getBdcdyh());
                    tdsyqDTO.setZl(bdcTdsyqDO.getZl());
                    tdsyqDTO.setZdmj(doubleToString(bdcXmDO.getZdzhmj()));
                    tdsyqDTO.setMjdw(intToString(bdcXmDO.getMjdw()));
                    tdsyqDTO.setYt(bdcXmDO.getZdzhyt());
                    tdsyqDTO.setQlxz(bdcXmDO.getQlxz());
                    tdsyqDTO.setBdcqzh(bdcXmDO.getBdcqzh());
                    tdsyqDTO.setDjjg(bdcTdsyqDO.getDjjg());
                    tdsyqDTO.setDqdm(bdcXmDO.getQxdm());
                    tdsyqDTO.setYwh(bdcXmDO.getXmid());

                    tdsyqList.add(tdsyqDTO);

                } else if (bdcQl instanceof BdcJsydsyqDO) {
                    BdcJsydsyqDO bdcJsydsyqDO = (BdcJsydsyqDO) bdcQl;
                    String xmid = bdcJsydsyqDO.getXmid();
                    if (!xmidList.contains(xmid)) {
                        continue;
                    }
                    if (Objects.nonNull(bdcXmDOMap) && Objects.nonNull(bdcXmDOMap.get(xmid))) {
                        bdcXmDO = bdcXmDOMap.get(xmid);
                    }
                    CourtYwxxcxResponseJsydsyqDTO jsydsyqDTO = new CourtYwxxcxResponseJsydsyqDTO();
                    jsydsyqDTO.setBdcdyh(bdcJsydsyqDO.getBdcdyh());
                    jsydsyqDTO.setZl(bdcJsydsyqDO.getZl());
                    jsydsyqDTO.setSyqmj(doubleToString(bdcJsydsyqDO.getSyqmj()));
                    jsydsyqDTO.setSyqqssj(dateToString(bdcJsydsyqDO.getSyqqssj(), DATA_PATTERN));
                    jsydsyqDTO.setSyqjssj(dateToString(bdcJsydsyqDO.getSyqjssj(), DATA_PATTERN));
                    jsydsyqDTO.setYt(bdcXmDO.getZdzhyt());
                    jsydsyqDTO.setQlxz(bdcXmDO.getQlxz());
                    jsydsyqDTO.setBdcqzh(bdcXmDO.getBdcqzh());
                    jsydsyqDTO.setDjjg(bdcJsydsyqDO.getDjjg());
                    jsydsyqDTO.setDqdm(bdcXmDO.getQxdm());
                    jsydsyqDTO.setYwh(bdcXmDO.getXmid());

                    jsydsyqList.add(jsydsyqDTO);

                } else if (bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    String xmid = bdcFdcqDO.getXmid();
                    if (!xmidList.contains(xmid)) {
                        continue;
                    }
                    if (Objects.nonNull(bdcXmDOMap) && Objects.nonNull(bdcXmDOMap.get(xmid))) {
                        bdcXmDO = bdcXmDOMap.get(xmid);
                    }
                    CourtYwxxcxResponseFdcqDTO fdcqDTO = new CourtYwxxcxResponseFdcqDTO();
                    fdcqDTO.setBdcdyh(bdcFdcqDO.getBdcdyh());
                    fdcqDTO.setFdzl(bdcFdcqDO.getZl());
                    fdcqDTO.setJzmj(doubleToString(bdcXmDO.getDzwmj()));
                    fdcqDTO.setZyjzmj(doubleToString(bdcFdcqDO.getZyjzmj()));
                    fdcqDTO.setFtjzmj(doubleToString(bdcFdcqDO.getFtjzmj()));
                    fdcqDTO.setGhyt(intToString(bdcFdcqDO.getGhyt()));
                    fdcqDTO.setFwxz(intToString(bdcFdcqDO.getFwxz()));
                    fdcqDTO.setJgsj(bdcFdcqDO.getJgsj());
                    fdcqDTO.setTdsyqssj(dateToString(bdcFdcqDO.getTdsyqssj(), DATA_PATTERN));
                    fdcqDTO.setTdsyjssj(dateToString(bdcFdcqDO.getTdsyjssj(), DATA_PATTERN));
                    fdcqDTO.setBdcqzh(bdcXmDO.getBdcqzh());
                    fdcqDTO.setDjjg(bdcFdcqDO.getDjjg());
                    fdcqDTO.setDqdm(bdcXmDO.getQxdm());
                    fdcqDTO.setYwh(bdcXmDO.getXmid());

                    fdcqList.add(fdcqDTO);

                } else if (bdcQl instanceof BdcHysyqDO) {
                    BdcHysyqDO bdcHysyqDO = (BdcHysyqDO) bdcQl;
                    String xmid = bdcHysyqDO.getXmid();
                    if (!xmidList.contains(xmid)) {
                        continue;
                    }
                    if (Objects.nonNull(bdcXmDOMap) && Objects.nonNull(bdcXmDOMap.get(xmid))) {
                        bdcXmDO = bdcXmDOMap.get(xmid);
                    }
                    CourtYwxxcxResponseHysyqDTO hysyqDTO = new CourtYwxxcxResponseHysyqDTO();
                    BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO = bdcDjbxxRestService.queryBdcBdcdjbZhjbxx(bdcHysyqDO.getBdcdyh());
                    hysyqDTO.setBdcdyh(bdcHysyqDO.getBdcdyh());
                    hysyqDTO.setXmmc(bdcHysyqDO.getXmmc());
                    hysyqDTO.setYhwzsm(bdcHysyqDO.getZl());
                    hysyqDTO.setYhlxa(intToString(bdcXmDO.getYhlxa()));
                    hysyqDTO.setYhlxb(intToString(bdcXmDO.getYhlxb()));
                    hysyqDTO.setHdmc(bdcBdcdjbZhjbxxDO.getHdmc());
                    hysyqDTO.setHdyt(intToString(bdcBdcdjbZhjbxxDO.getHdyt()));
                    hysyqDTO.setSyqmj(doubleToString(bdcHysyqDO.getSyqmj()));
                    hysyqDTO.setSyqjssj(dateToString(bdcHysyqDO.getSyqjssj(), DATA_PATTERN));
                    hysyqDTO.setSyqqssj(dateToString(bdcHysyqDO.getSyqqssj(), DATA_PATTERN));
                    hysyqDTO.setBdcqzh(bdcXmDO.getBdcqzh());
                    hysyqDTO.setDjjg(bdcHysyqDO.getDjjg());
                    hysyqDTO.setDqdm(bdcXmDO.getQxdm());
                    hysyqDTO.setYwh(bdcXmDO.getXmid());

                    hysyqList.add(hysyqDTO);

                } else if (bdcQl instanceof BdcGjzwsyqDO) {
                    BdcGjzwsyqDO bdcGjzwsyqDO = (BdcGjzwsyqDO) bdcQl;
                    String xmid = bdcGjzwsyqDO.getXmid();
                    if (!xmidList.contains(xmid)) {
                        continue;
                    }
                    if (Objects.nonNull(bdcXmDOMap) && Objects.nonNull(bdcXmDOMap.get(xmid))) {
                        bdcXmDO = bdcXmDOMap.get(xmid);
                    }
                    CourtYwxxcxResponseGjzwsyqDTO gjzwsyqDTO = new CourtYwxxcxResponseGjzwsyqDTO();
                    gjzwsyqDTO.setBdcdyh(bdcGjzwsyqDO.getBdcdyh());
                    gjzwsyqDTO.setZl(bdcGjzwsyqDO.getZl());
                    gjzwsyqDTO.setGjzwghyt(bdcGjzwsyqDO.getGjzwghyt());
                    gjzwsyqDTO.setGjzwmj(doubleToString(bdcGjzwsyqDO.getGjzwmj()));
                    gjzwsyqDTO.setTdhysyjssj(dateToString(bdcGjzwsyqDO.getTdhysyjssj(), DATA_PATTERN));
                    gjzwsyqDTO.setTdhysyqssj(dateToString(bdcGjzwsyqDO.getTdhysyqssj(), DATA_PATTERN));
                    gjzwsyqDTO.setBdcqzh(bdcXmDO.getBdcqzh());
                    gjzwsyqDTO.setDjjg(bdcGjzwsyqDO.getDjjg());
                    gjzwsyqDTO.setDqdm(bdcXmDO.getQxdm());
                    gjzwsyqDTO.setYwh(bdcXmDO.getXmid());

                    gjzwsyqList.add(gjzwsyqDTO);

                } else if (bdcQl instanceof BdcLqDO) {
                    BdcLqDO bdcLqDO = (BdcLqDO) bdcQl;
                    String xmid = bdcLqDO.getXmid();
                    if (!xmidList.contains(xmid)) {
                        continue;
                    }
                    if (Objects.nonNull(bdcXmDOMap) && Objects.nonNull(bdcXmDOMap.get(xmid))) {
                        bdcXmDO = bdcXmDOMap.get(xmid);
                    }
                    CourtYwxxcxResponseLqDTO lqDTO = new CourtYwxxcxResponseLqDTO();
                    lqDTO.setBdcdyh(bdcLqDO.getBdcdyh());
                    lqDTO.setZl(bdcLqDO.getZl());
                    lqDTO.setSyqmj(doubleToString(bdcLqDO.getSyqmj()));
                    lqDTO.setLdsyjssj(dateToString(bdcLqDO.getLdsyjssj(), DATA_PATTERN));
                    lqDTO.setLdsyqssj(dateToString(bdcLqDO.getLdsyqssj(), DATA_PATTERN));
                    lqDTO.setLdsyqxz(intToString(bdcLqDO.getLdsyqxz()));
                    lqDTO.setBdcqzh(bdcXmDO.getBdcqzh());
                    lqDTO.setDjjg(lqDTO.getDjjg());
                    lqDTO.setDqdm(bdcXmDO.getQxdm());
                    lqDTO.setYwh(bdcXmDO.getXmid());

                    lqList.add(lqDTO);

                } else if (bdcQl instanceof BdcDyaqDO) {
                    BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                    String xmid = bdcDyaqDO.getXmid();
                    BdcXmDO bdcXmDO1 = bdcXmMapper.queryBdcXm(xmid);
                    if (bdcXmDO1 == null) {
                        continue;
                    }
                    CourtYwxxcxResponseDyaqDTO dyaqDTO = new CourtYwxxcxResponseDyaqDTO();
                    dyaqDTO.setBdcdyh(bdcDyaqDO.getBdcdyh());
                    dyaqDTO.setDybdclx(intToString(bdcDyaqDO.getDybdclx()));
                    dyaqDTO.setZl(bdcDyaqDO.getZl());
                    dyaqDTO.setDyr(bdcDyaqDO.getZwr());
                    dyaqDTO.setDyfs(intToString(bdcDyaqDO.getDyfs()));
                    dyaqDTO.setBdbzzqse(null == bdcDyaqDO.getBdbzzqse() ? "" : doubleToString(bdcDyaqDO.getBdbzzqse()));
                    dyaqDTO.setZwlxjssj(dateToString(bdcDyaqDO.getZwlxjssj(), DATA_PATTERN));
                    dyaqDTO.setZwlxqssj(dateToString(bdcDyaqDO.getZwlxqssj(), DATA_PATTERN));
                    dyaqDTO.setBdcdjzmh(bdcXmDO1.getBdcqzh());
                    dyaqDTO.setDyqr(bdcXmDO1.getQlr());
                    dyaqDTO.setDjjg(bdcDyaqDO.getDjjg());
                    dyaqDTO.setDqdm(bdcXmDO1.getQxdm());
                    dyaqDTO.setYwh(bdcXmDO1.getXmid());

                    dyaqList.add(dyaqDTO);

                } else if (bdcQl instanceof BdcYgDO) {
                    BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                    String xmid = bdcYgDO.getXmid();
                    BdcXmDO bdcXmDO1 = bdcXmMapper.queryBdcXm(xmid);
                    if (bdcXmDO1 == null) {
                        continue;
                    }
                    if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR,bdcYgDO.getYgdjzl())){
                        continue;
                    }
                    CourtYwxxcxResponseYgDTO ygDTO = new CourtYwxxcxResponseYgDTO();
                    ygDTO.setBdcdyh(bdcYgDO.getBdcdyh());
                    ygDTO.setYgdjzl(intToString(bdcYgDO.getYgdjzl()));
                    ygDTO.setZl(bdcYgDO.getZl());
                    ygDTO.setGhyt(intToString(bdcYgDO.getGhyt()));
                    ygDTO.setJzmj(doubleToString(bdcYgDO.getJzmj()));
                    ygDTO.setBdcdjzmh(bdcXmDO1.getBdcqzh());
                    ygDTO.setDjjg(bdcYgDO.getDjjg());
                    ygDTO.setDqdm(bdcXmDO1.getQxdm());
                    ygDTO.setYwh(bdcXmDO1.getXmid());

                    ygdjList.add(ygDTO);

                } else if (bdcQl instanceof BdcCfDO) {
                    BdcCfDO bdcCfDO = (BdcCfDO) bdcQl;
                    String xmid = bdcCfDO.getXmid();
                    BdcXmDO bdcXmDO1 = bdcXmMapper.queryBdcXm(xmid);
                    if (bdcXmDO1 == null) {
                        continue;
                    }
                    CourtYwxxcxResponseCfDTO cfDTO = new CourtYwxxcxResponseCfDTO();
                    cfDTO.setBdcdyh(bdcCfDO.getBdcdyh());
                    cfDTO.setZl(bdcCfDO.getZl());
                    cfDTO.setCfjg(bdcCfDO.getCfjg());
                    cfDTO.setCflx(intToString(bdcCfDO.getCflx()));
                    cfDTO.setCfwh(bdcCfDO.getCfwh());
                    cfDTO.setCfjssj(dateToString(bdcCfDO.getCfjssj(), DATA_PATTERN));
                    cfDTO.setCfqssj(dateToString(bdcCfDO.getCfqssj(), DATA_PATTERN));
                    cfDTO.setCfywh(bdcXmDO1.getXmid());
                    cfDTO.setDjjg(bdcCfDO.getDjjg());
                    cfDTO.setDqdm(bdcXmDO1.getQxdm());
                    cfDTO.setYwh(bdcXmDO1.getXmid());

                    cfdjList.add(cfDTO);

                }
            }
            responseDTO.setTdsyqList(CollectionUtils.isEmpty(tdsyqList) ? null : tdsyqList);
            responseDTO.setJsydsyqList(CollectionUtils.isEmpty(jsydsyqList) ? null : jsydsyqList);
            responseDTO.setFdcqList(CollectionUtils.isEmpty(fdcqList) ? null : fdcqList);
            responseDTO.setHysyqList(CollectionUtils.isEmpty(hysyqList) ? null : hysyqList);
            responseDTO.setGjzwsyqList(CollectionUtils.isEmpty(gjzwsyqList) ? null : gjzwsyqList);
            responseDTO.setLqList(CollectionUtils.isEmpty(lqList) ? null : lqList);
            responseDTO.setDyaqList(CollectionUtils.isEmpty(dyaqList) ? null : dyaqList);
            responseDTO.setYgdjList(CollectionUtils.isEmpty(ygdjList) ? null : ygdjList);
            responseDTO.setCfdjList(CollectionUtils.isEmpty(cfdjList) ? null : cfdjList);
            LOGGER.info("---返回结果:{}", JSON.toJSONString(responseDTO));
            return ExchangeDsfCommonResponse.ok(responseDTO);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExchangeDsfCommonResponse.fail(e.getMessage());
        }
    }

    private static String doubleToString(Double d) {
        if (Objects.isNull(d)) {
            return "";
        }
        DecimalFormat df1 = new DecimalFormat("0.00");
        return df1.format(d);
    }

    private static String intToString(Integer i) {
        if (Objects.isNull(i)) {
            return "";
        }
        return String.valueOf(i);
    }

    private static String dateToString(Date date, String pattern) {
        if (Objects.isNull(date) || StringUtils.isEmpty(pattern)) {
            return null;
        }
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * 按数量分组list
     * @param list
     * @param quantity
     * @return
     */
    public static List<List<String>> groupListByQuantity(List<String> list, int quantity) {
        if (list == null || list.size() == 0) {
            return null;
        }

        if (quantity <= 0) {
            new IllegalArgumentException("Wrong quantity.");
        }

        List<List<String>> wrapList = new ArrayList();
        int count = 0;
        while (count < list.size()) {
            wrapList.add(new ArrayList(list.subList(count, Math.min((count + quantity), list.size()))));
            count += quantity;
        }

        return wrapList;
    }
//
//    public static void main(String[] args) {
//        String ss = "";
//        JSONArray jsonObject = JSON.parseArray(ss);
//        String sss = "";
//        JSONArray jsonArray = JSON.parseArray(sss);
//        jsonObject.removeAll(jsonArray);
//    }

}
