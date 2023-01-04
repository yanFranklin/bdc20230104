package cn.gtmap.realestate.exchange.service.impl.inf.standard;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.exchange.court.*;
import cn.gtmap.realestate.common.core.dto.exchange.court.cx.*;
import cn.gtmap.realestate.common.core.dto.exchange.court.kz.CourtKzHeader;
import cn.gtmap.realestate.common.core.dto.exchange.court.ywxxcx.*;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTOForZhlc;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclmxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.ywxxcx.CourtYwxxcxRequestDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcDjbxxRestService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CardNumberTransformation;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.enums.BdcCourtKzEnum;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.wwsq.GxWwSqxxServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.FjclService;
import cn.gtmap.realestate.exchange.service.inf.standard.BdcCourtCxService;
import cn.gtmap.realestate.exchange.service.inf.standard.court.BdcCourtKzJhService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.avalon.framework.parameters.ParameterException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static cn.gtmap.realestate.common.util.InterfaceCodeBeanFactory.getBean;

/**
 * 法院查询信息
 */
@Service
public class BdcCourtCxServiceImpl implements BdcCourtCxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcCourtCxServiceImpl.class);


    @Autowired
    private BdcQllxFeignService bdcQllxFeignService;

    @Autowired
    private BdcDjbxxRestService bdcDjbxxRestService;

    @Autowired
    private BdcXmMapper bdcXmMapper;

    @Autowired
    Set<BdcCourtKzJhService> bdcCourtKzJhServices;

    @Autowired
    GxWwSqxxServiceImpl gxWwSqxxService;

    @Autowired
    BdcSlFeignService bdcSlFeignService;

    @Autowired
    CommonService commonService;

    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Value("${CourtKz.enable:false}")
    private Boolean enable;

    /**
     * 交互方式,默认使用接口
     */
    @Value("${CourtKz.version:soap}")
    private String CourtKzJhVersion;

    @Value("${CourtKz.password:jk}")
    private String password;

    @Value("${CourtKz.username:web}")
    private String username;

    @Value("${CourtKz.digitalsign:web}")
    private String digitalsign;

    @Value("${CourtKz.sqdjlx.cf:}")
    private String sqdjlxCf;

    @Value("${CourtKz.sqdjlx.jf:}")
    private String sqdjlxJf;

    @Value("${CourtKz.sqdjlx.xf:}")
    private String sqdjlxXf;

    @Autowired
    FjclService fjclService;

    @Autowired
    BdcYwsjHxFeignService bdcYwsjHxFeignService;


    /**
     * 轮询查看当前法院的请求
     */
    @Override
    @Scheduled(cron = "${CourtKz.kz.cron:0 0/20 8-17 ? * MON-FRI}")
    public void CourtCxBdcQL() {
        if (!enable) {
            return;
        }
        LOGGER.info("定时接收法院的件,当前时间:{},定时执行法院查控系统请求", new Date());
        BdcCourtKzJhService bdcCourtKzJhService = getjhService(BdcCourtKzEnum.SOAP.getCode());
        CourtKzUserDTO usermarker = buildCourtKzUserRequest();
        CourtCxDTO courtKzDTO = bdcCourtKzJhService.cxBdcQL(usermarker);
        //处理收到的请求
        LOGGER.info("定时接收法院的件,当前时间:{},收到的请求为:{}", new Date(), JSON.toJSONString(courtKzDTO));
        if (StringUtils.isNotBlank(courtKzDTO.getErrorMSG())) {
            LOGGER.info("定时接收法院的件,当前时间:{},请求错误,收到的返回为:{}", new Date(), courtKzDTO.getErrorMSG());
            return;
        }
        if (Objects.nonNull(courtKzDTO.getCourtCxData()) && CollectionUtils.isNotEmpty(courtKzDTO.getCourtCxData().getBdcQQItemList())) {
            //处理返回值
            List<CourtCxBdcQQItem> bdcQQItemList = courtKzDTO.getCourtCxData().getBdcQQItemList();
            for (CourtCxBdcQQItem courtKzBdcQQItem : bdcQQItemList) {
                LOGGER.info("定时接收法院的件,当前时间:{},处理收到的请求:{}", new Date(), JSON.toJSONString(courtKzBdcQQItem));
                try {
                    feedCourtCxBdcQL(courtKzBdcQQItem);
                    //构建返回
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 调用该接口将司法控制结果信息反馈请求单位
     *
     * @param courtKzBdcQQItem
     * @return
     */
    @Override
    @Async
    public void feedCourtCxBdcQL(CourtCxBdcQQItem courtKzBdcQQItem) {
        LOGGER.info("定时接收法院的件,当前时间:{},处理收到的控制请求:{}", new Date(), JSON.toJSONString(courtKzBdcQQItem));
        BdcCourtKzJhService bdcCourtKzJhService = getjhService(BdcCourtKzEnum.SOAP.getCode());
        if (Objects.nonNull(courtKzBdcQQItem.getCXQQDH())) {
            CourtKzUserDTO usermarker = buildCourtKzUserRequest();
            CourtKzHeader courtKzHeader = buildCourtKzHeadRequest();
            try {
                CourtFeedCxDTO ywxxcx = ywxxcx(courtKzBdcQQItem);
                LOGGER.info("定时接收法院的件{},处理收到的控制请求:{}", courtKzBdcQQItem.getCXQQDH(),
                        JSON.toJSONString(ywxxcx));
                CourtFeedCxBdcQLDTO courtFeedCxBdcQLDTO = new CourtFeedCxBdcQLDTO();
                CourtFeedCxDATA courtFeedCxDATA = new CourtFeedCxDATA();
                courtFeedCxDATA.setCourtFeedKzZhzHzs(Arrays.asList(ywxxcx));
                courtFeedCxBdcQLDTO.setCourtFeedCxDATA(courtFeedCxDATA);
                courtFeedCxBdcQLDTO.setCourtKzHeader(courtKzHeader);
                CourtCxjgDTO courtCxjgDTO = bdcCourtKzJhService.feedCxBdcQL(usermarker, courtFeedCxBdcQLDTO);
                LOGGER.info("定时接收法院的件{},处理收到的控制请求结果:{}", courtKzBdcQQItem.getCXQQDH(),
                        JSON.toJSONString(courtCxjgDTO));
            } catch (Exception e) {
                CourtFeedCxBdcQLDTO courtFeedCxBdcQLDTO = new CourtFeedCxBdcQLDTO();
                courtFeedCxBdcQLDTO.setCourtKzHeader(courtKzHeader);
                CourtFeedCxDATA courtFeedCxDATA = new CourtFeedCxDATA();
                CourtFeedCxDTO courtFeedCxDTO = new CourtFeedCxDTO();
                courtFeedCxDTO.setRESULT("2040");
                courtFeedCxDTO.setCXQQDH(courtKzBdcQQItem.getCXQQDH());
                courtFeedCxDTO.setMESSAGE(e.getMessage());
                courtFeedCxDATA.setCourtFeedKzZhzHzs(Arrays.asList(courtFeedCxDTO));
                courtFeedCxBdcQLDTO.setCourtFeedCxDATA(courtFeedCxDATA);
                CourtCxjgDTO courtCxjgDTO = bdcCourtKzJhService.feedCxBdcQL(usermarker, courtFeedCxBdcQLDTO);
                LOGGER.info("定时接收法院的件{},处理收到的控制请求结果:{}", courtKzBdcQQItem.getCXQQDH(),
                        JSON.toJSONString(courtCxjgDTO));
            }
        }
    }


    /**
     * 查询
     *
     * @param courtKzBdcQQItem
     * @return
     */
    public CourtFeedCxDTO ywxxcx(CourtCxBdcQQItem courtKzBdcQQItem) throws ParameterException {
        CourtFeedCxDTO responseDTO = new CourtFeedCxDTO();
        responseDTO.setRESULT("0000");
        responseDTO.setCXQQDH(courtKzBdcQQItem.getCXQQDH());
        try {
            if (org.apache.commons.lang.StringUtils.isBlank(courtKzBdcQQItem.getZJZL())) {
                throw new ParameterException("zjzl不能为空!");
            }
            if (org.apache.commons.lang.StringUtils.isBlank(courtKzBdcQQItem.getXM())) {
                throw new ParameterException("qlrmc不能为空!");
            }
            if (org.apache.commons.lang.StringUtils.isBlank(courtKzBdcQQItem.getZJH())) {
                throw new ParameterException("zjh不能为空!");
            }
            if (courtKzBdcQQItem.getZJZL().equals(CommonConstantUtils.ZJZL_SFZ.toString()) &&
                    courtKzBdcQQItem.getZJH().length() == 18) {
                courtKzBdcQQItem.setFIFTEENZJH(CardNumberTransformation.idCard18to15(courtKzBdcQQItem.getZJH()));
            }
            /**
             * 1.查询现势产权
             */
            List<BdcXmDO> bdcXmDOS = null;
            CourtYwxxcxRequestDTO courtYwxxcxRequestDTO = new CourtYwxxcxRequestDTO();
            courtYwxxcxRequestDTO.setZjh(courtKzBdcQQItem.getZJH());
            courtYwxxcxRequestDTO.setQlrmc(courtKzBdcQQItem.getXM());
            courtYwxxcxRequestDTO.setZjzl(courtKzBdcQQItem.getZJZL());

//            courtYwxxcxRequestDTO.setQlrmc("中**大银行股份有限公司合肥分行");
//            courtYwxxcxRequestDTO.setZjzl("1");
            courtYwxxcxRequestDTO.setQsztList(Arrays.asList(1));
            if ("6".equals(courtKzBdcQQItem.getZJZL())) {
                bdcXmDOS = bdcXmMapper.listCourtXscqWithZjhOrQlrmc(courtYwxxcxRequestDTO);
            } else {
                courtYwxxcxRequestDTO.setFifteenNumZjh(courtKzBdcQQItem.getFIFTEENZJH());
                bdcXmDOS = bdcXmMapper.listCourtXscqWithZjhAndQlrmc(courtYwxxcxRequestDTO);
            }
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                LOGGER.info("查询当前项目信息为空!");
                return responseDTO;
            }

            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                LOGGER.info("过滤商品房首登和商品房首登批量业务生成的产权证后的项目信息为空");
                return responseDTO;
            }

            List<String> bdcdyhList = bdcXmDOS
                    .stream()
                    .map(BdcXmDO::getBdcdyh)
                    .distinct()
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(bdcdyhList)) {
                LOGGER.info("查询到的项目信息无bdcdyh信息");
                return responseDTO;
            }


            //查询不动产权利
            List<BdcQl> bdcQls = new ArrayList<>();
            List<BdcQlrDO> bdcQlrDOs = new ArrayList<>();
            List<List<String>> lists = Lists.partition(bdcdyhList, 500);
            for (List<String> bdcdyhs : lists) {
                bdcQls.addAll(bdcQllxFeignService.listXzxxByBdcdyhs(bdcdyhs));
            }

            List<String> xmidList = bdcXmDOS
                    .stream()
                    .map(BdcXmDO::getXmid)
                    .distinct()
                    .collect(Collectors.toList());
            List<List<String>> xmidPartition = Lists.partition(xmidList, 500);
            for (List<String> strings : xmidPartition) {
                bdcQls.addAll(bdcQllxFeignService.listXzxxByXmids(strings));
            }

            if (CollectionUtils.isEmpty(bdcQls)) {
                LOGGER.info("未查询到信息");
                return responseDTO;
            }

            //查询权利对应的权利人
            List<String> allQlXmids = bdcQls.stream().map(BdcQl::getXmid).collect(Collectors.toList());
            List<List<String>> partition = Lists.partition(allQlXmids, 500);
            for (List<String> xmids : partition) {
                bdcQlrDOs.addAll(bdcQlrFeignService.listBdcQlrByXmidList(xmids, ""));
            }

            LOGGER.info("---过滤之后的现势产权对应不动产单元号:{}", JSON.toJSONString(bdcQls));

            List<CourtCxTdsyqDTO> tdsyqList = new ArrayList<>();
            List<CourtCxJsydsyqDTO> jsydsyqList = new ArrayList<>();
            List<CourtCxFdcqDTO> fdcqList = new ArrayList<>();
            List<CourtCxHysyqDTO> hysyqList = new ArrayList<>();
            List<CourtCxGjzwsyqDTO> gjzwsyqList = new ArrayList<>();
            List<CourtCxLqDTO> lqList = new ArrayList<>();
            List<CourtCxDyaqDTO> dyaqList = new ArrayList<>();
            List<CourtCxYgDTO> ygdjList = new ArrayList<>();
            List<CourtCxCfDTO> cfdjList = new ArrayList<>();

            Map<String, BdcXmDO> bdcXmDOMap = bdcXmDOS
                    .stream()
                    .collect(Collectors.toMap(BdcXmDO::getXmid, v -> v, (v1, v2) -> v1));

            Map<String, List<BdcQlrDO>> qlrMap = bdcQlrDOs
                    .stream()
                    .collect(Collectors.groupingBy(BdcQlrDO::getXmid));

            List<String> qlidList = new ArrayList<>();
            for (BdcQl bdcQl : bdcQls) {
                if (qlidList.contains(bdcQl.getQlid())) {
                    continue;
                }
                qlidList.add(bdcQl.getQlid());
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

                    CourtCxTdsyqDTO tdsyq = new CourtCxTdsyqDTO();
                    tdsyq.setBdcdyh(bdcTdsyqDO.getBdcdyh());
                    tdsyq.setZl(bdcTdsyqDO.getZl());
                    tdsyq.setZdmj(doubleToString(bdcXmDO.getZdzhmj()));
                    tdsyq.setMjdw(intToString(bdcXmDO.getMjdw()).toString());
                    tdsyq.setYt(bdcXmDO.getZdzhyt());
                    tdsyq.setQlxz(bdcXmDO.getQlxz());
                    tdsyq.setBdcqzh(bdcXmDO.getBdcqzh());
                    tdsyq.setDjjg(bdcTdsyqDO.getDjjg());
                    tdsyq.setDqdm(bdcXmDO.getQxdm());
                    tdsyq.setYwh(bdcXmDO.getXmid());
                    tdsyq.setBz(bdcXmDO.getBz());
                    if (qlrMap.containsKey(xmid)) {
                        List<BdcQlrDO> bdcQlrDOS = qlrMap.get(xmid);
                        List<CourtCxQlrDTO> courtCxQlrDTOS = new ArrayList<>();
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                            CourtCxQlrDTO courtCxQlrDTO = new CourtCxQlrDTO();
                            BeanUtils.copyProperties(bdcQlrDO, courtCxQlrDTO);
                            courtCxQlrDTO.setBdcdyh(bdcTdsyqDO.getBdcdyh());
                            courtCxQlrDTO.setSxh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setQzysxlh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setSfczr(intToString(bdcQlrDO.getSfczr()));
                            courtCxQlrDTO.setZjzl(intToString(bdcQlrDO.getZjzl()));
                            courtCxQlrDTO.setFzjg(bdcTdsyqDO.getDjjg());
                            courtCxQlrDTO.setXb(intToString(bdcQlrDO.getXb()));
                            courtCxQlrDTO.setDz(bdcQlrDO.getTxdz());
                            courtCxQlrDTO.setQlrlx(intToString(bdcQlrDO.getQlrlx()));
                            courtCxQlrDTO.setGyfs(intToString(bdcQlrDO.getGyfs()));
                            courtCxQlrDTO.setYwh(bdcXmDO.getXmid());
                            courtCxQlrDTO.setQszt(intToString(bdcXmDO.getQszt()));
                            courtCxQlrDTO.setBdclx(intToString(bdcXmDO.getBdclx()));
                            courtCxQlrDTOS.add(courtCxQlrDTO);
                        }
                        tdsyq.setQlrxxList(courtCxQlrDTOS);
                    }
                    tdsyqList.add(tdsyq);

                } else if (bdcQl instanceof BdcJsydsyqDO) {
                    BdcJsydsyqDO bdcJsydsyqDO = (BdcJsydsyqDO) bdcQl;
                    String xmid = bdcJsydsyqDO.getXmid();
                    if (!xmidList.contains(xmid)) {
                        continue;
                    }
                    if (Objects.nonNull(bdcXmDOMap) && Objects.nonNull(bdcXmDOMap.get(xmid))) {
                        bdcXmDO = bdcXmDOMap.get(xmid);
                    }
                    CourtCxJsydsyqDTO jsydsyq = new CourtCxJsydsyqDTO();
                    jsydsyq.setBdcdyh(bdcJsydsyqDO.getBdcdyh());
                    jsydsyq.setZl(bdcJsydsyqDO.getZl());
                    jsydsyq.setSyqmj(doubleToString(bdcJsydsyqDO.getSyqmj()));
                    jsydsyq.setSyqqssj(DateUtils.formateDateToString(bdcJsydsyqDO.getSyqqssj(), DateUtils.DATE_FORMAT_5));
                    jsydsyq.setSyqjssj(DateUtils.formateDateToString(bdcJsydsyqDO.getSyqjssj(), DateUtils.DATE_FORMAT_5));
                    jsydsyq.setYt(bdcXmDO.getZdzhyt());
                    jsydsyq.setQlxz(bdcXmDO.getQlxz());
                    jsydsyq.setBdcqzh(bdcXmDO.getBdcqzh());
                    jsydsyq.setDjjg(bdcJsydsyqDO.getDjjg());
                    jsydsyq.setDqdm(bdcXmDO.getQxdm());
                    jsydsyq.setYwh(bdcXmDO.getXmid());
                    jsydsyq.setBz(bdcXmDO.getBz());
                    if (qlrMap.containsKey(xmid)) {
                        List<BdcQlrDO> bdcQlrDOS = qlrMap.get(xmid);
                        List<CourtCxQlrDTO> courtCxQlrDTOS = new ArrayList<>();
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                            CourtCxQlrDTO courtCxQlrDTO = new CourtCxQlrDTO();
                            BeanUtils.copyProperties(bdcQlrDO, courtCxQlrDTO);
                            courtCxQlrDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                            courtCxQlrDTO.setSxh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setQzysxlh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setSfczr(intToString(bdcQlrDO.getSfczr()));
                            courtCxQlrDTO.setZjzl(intToString(bdcQlrDO.getZjzl()));
                            courtCxQlrDTO.setFzjg(bdcXmDO.getDjjg());
                            courtCxQlrDTO.setXb(intToString(bdcQlrDO.getXb()));
                            courtCxQlrDTO.setDz(bdcQlrDO.getTxdz());
                            courtCxQlrDTO.setQlrlx(intToString(bdcQlrDO.getQlrlx()));
                            courtCxQlrDTO.setGyfs(intToString(bdcQlrDO.getGyfs()));
                            courtCxQlrDTO.setYwh(bdcXmDO.getXmid());
                            courtCxQlrDTO.setQszt(intToString(bdcXmDO.getQszt()));
                            courtCxQlrDTO.setBdclx(intToString(bdcXmDO.getBdclx()));
                            courtCxQlrDTOS.add(courtCxQlrDTO);
                        }
                        jsydsyq.setQlrxxList(courtCxQlrDTOS);
                    }
                    jsydsyqList.add(jsydsyq);

                } else if (bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    String xmid = bdcFdcqDO.getXmid();
                    if (!xmidList.contains(xmid)) {
                        continue;
                    }
                    if (Objects.nonNull(bdcXmDOMap) && Objects.nonNull(bdcXmDOMap.get(xmid))) {
                        bdcXmDO = bdcXmDOMap.get(xmid);
                    }
                    CourtCxFdcqDTO fdcq = new CourtCxFdcqDTO();
                    fdcq.setBdcdyh(bdcFdcqDO.getBdcdyh());
                    fdcq.setFdzl(bdcFdcqDO.getZl());
                    fdcq.setJzmj(doubleToString(bdcXmDO.getDzwmj()));
                    fdcq.setZyjzmj(doubleToString(bdcFdcqDO.getZyjzmj()));
                    fdcq.setFtjzmj(doubleToString(bdcFdcqDO.getFtjzmj()));
                    fdcq.setGhyt(intToString(bdcFdcqDO.getGhyt()));
                    fdcq.setFwxz(intToString(bdcFdcqDO.getFwxz()));
                    fdcq.setJgsj(bdcFdcqDO.getJgsj());
                    fdcq.setTdsyqssj(DateUtils.formateDateToString(bdcFdcqDO.getTdsyqssj(), DateUtils.DATE_FORMAT_5));
                    fdcq.setTdsyjssj(DateUtils.formateDateToString(bdcFdcqDO.getTdsyjssj(), DateUtils.DATE_FORMAT_5));
                    fdcq.setBdcqzh(bdcXmDO.getBdcqzh());
                    fdcq.setDjjg(bdcFdcqDO.getDjjg());
                    fdcq.setDqdm(bdcXmDO.getQxdm());
                    fdcq.setYwh(bdcXmDO.getXmid());
                    fdcq.setBz(bdcXmDO.getBz());
                    if (qlrMap.containsKey(xmid)) {
                        List<BdcQlrDO> bdcQlrDOS = qlrMap.get(xmid);
                        List<CourtCxQlrDTO> courtCxQlrDTOS = new ArrayList<>();
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                            CourtCxQlrDTO courtCxQlrDTO = new CourtCxQlrDTO();
                            BeanUtils.copyProperties(bdcQlrDO, courtCxQlrDTO);
                            courtCxQlrDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                            courtCxQlrDTO.setSxh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setQzysxlh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setSfczr(intToString(bdcQlrDO.getSfczr()));
                            courtCxQlrDTO.setZjzl(intToString(bdcQlrDO.getZjzl()));
                            courtCxQlrDTO.setFzjg(bdcXmDO.getDjjg());
                            courtCxQlrDTO.setXb(intToString(bdcQlrDO.getXb()));
                            courtCxQlrDTO.setDz(bdcQlrDO.getTxdz());
                            courtCxQlrDTO.setQlrlx(intToString(bdcQlrDO.getQlrlx()));
                            courtCxQlrDTO.setGyfs(intToString(bdcQlrDO.getGyfs()));
                            courtCxQlrDTO.setYwh(bdcXmDO.getXmid());
                            courtCxQlrDTO.setQszt(intToString(bdcXmDO.getQszt()));
                            courtCxQlrDTO.setBdclx(intToString(bdcXmDO.getBdclx()));
                            courtCxQlrDTOS.add(courtCxQlrDTO);
                        }
                        fdcq.setQlrxxList(courtCxQlrDTOS);
                    }
                    fdcqList.add(fdcq);

                } else if (bdcQl instanceof BdcHysyqDO) {
                    BdcHysyqDO bdcHysyqDO = (BdcHysyqDO) bdcQl;
                    String xmid = bdcHysyqDO.getXmid();
                    if (!xmidList.contains(xmid)) {
                        continue;
                    }
                    if (Objects.nonNull(bdcXmDOMap) && Objects.nonNull(bdcXmDOMap.get(xmid))) {
                        bdcXmDO = bdcXmDOMap.get(xmid);
                    }
                    CourtCxHysyqDTO hysyq = new CourtCxHysyqDTO();
                    BdcBdcdjbZhjbxxDO bdcBdcdjbZhjbxxDO = bdcDjbxxRestService.queryBdcBdcdjbZhjbxx(bdcHysyqDO.getBdcdyh());
                    hysyq.setBdcdyh(bdcHysyqDO.getBdcdyh());
                    hysyq.setXmmc(bdcHysyqDO.getXmmc());
                    hysyq.setYhwzsm(bdcHysyqDO.getZl());
                    hysyq.setYhlxa(intToString(bdcXmDO.getYhlxa()));
                    hysyq.setYhlxb(intToString(bdcXmDO.getYhlxb()));
                    hysyq.setHdmc(bdcBdcdjbZhjbxxDO.getHdmc());
                    if (Objects.nonNull(bdcBdcdjbZhjbxxDO.getHdyt())) {
                        if (bdcBdcdjbZhjbxxDO.getHdyt() >= 3) {
                            hysyq.setHdyt(intToString(bdcBdcdjbZhjbxxDO.getHdyt() + 1));
                        } else {
                            hysyq.setHdyt(intToString(bdcBdcdjbZhjbxxDO.getHdyt()));
                        }
                    } else {
                        hysyq.setHdyt("");
                    }
                    hysyq.setSyqmj(doubleToString(bdcHysyqDO.getSyqmj()));
                    hysyq.setSyqjssj(DateUtils.formateDateToString(bdcHysyqDO.getSyqjssj(), DateUtils.DATE_FORMAT_5));
                    hysyq.setSyqqssj(DateUtils.formateDateToString(bdcHysyqDO.getSyqqssj(), DateUtils.DATE_FORMAT_5));
                    hysyq.setBdcqzh(bdcXmDO.getBdcqzh());
                    hysyq.setDjjg(bdcHysyqDO.getDjjg());
                    hysyq.setDqdm(bdcXmDO.getQxdm());
                    hysyq.setYwh(bdcXmDO.getXmid());
                    hysyq.setBz(bdcXmDO.getBz());
                    if (qlrMap.containsKey(xmid)) {
                        List<BdcQlrDO> bdcQlrDOS = qlrMap.get(xmid);
                        List<CourtCxQlrDTO> courtCxQlrDTOS = new ArrayList<>();
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                            CourtCxQlrDTO courtCxQlrDTO = new CourtCxQlrDTO();
                            BeanUtils.copyProperties(bdcQlrDO, courtCxQlrDTO);
                            courtCxQlrDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                            courtCxQlrDTO.setSxh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setQzysxlh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setSfczr(intToString(bdcQlrDO.getSfczr()));
                            courtCxQlrDTO.setZjzl(intToString(bdcQlrDO.getZjzl()));
                            courtCxQlrDTO.setFzjg(bdcXmDO.getDjjg());
                            courtCxQlrDTO.setXb(intToString(bdcQlrDO.getXb()));
                            courtCxQlrDTO.setDz(bdcQlrDO.getTxdz());
                            courtCxQlrDTO.setQlrlx(intToString(bdcQlrDO.getQlrlx()));
                            courtCxQlrDTO.setGyfs(intToString(bdcQlrDO.getGyfs()));
                            courtCxQlrDTO.setYwh(bdcXmDO.getXmid());
                            courtCxQlrDTO.setQszt(intToString(bdcXmDO.getQszt()));
                            courtCxQlrDTO.setBdclx(intToString(bdcXmDO.getBdclx()));
                            courtCxQlrDTOS.add(courtCxQlrDTO);
                        }
                        hysyq.setQlrxxList(courtCxQlrDTOS);
                    }
                    hysyqList.add(hysyq);

                } else if (bdcQl instanceof BdcGjzwsyqDO) {
                    BdcGjzwsyqDO bdcGjzwsyqDO = (BdcGjzwsyqDO) bdcQl;
                    String xmid = bdcGjzwsyqDO.getXmid();
                    if (!xmidList.contains(xmid)) {
                        continue;
                    }
                    if (Objects.nonNull(bdcXmDOMap) && Objects.nonNull(bdcXmDOMap.get(xmid))) {
                        bdcXmDO = bdcXmDOMap.get(xmid);
                    }
                    CourtCxGjzwsyqDTO gjzwsyq = new CourtCxGjzwsyqDTO();
                    gjzwsyq.setBdcdyh(bdcGjzwsyqDO.getBdcdyh());
                    gjzwsyq.setZl(bdcGjzwsyqDO.getZl());
                    gjzwsyq.setGjzwghyt(bdcGjzwsyqDO.getGjzwghyt());
                    gjzwsyq.setGjzwmj(doubleToString(bdcGjzwsyqDO.getGjzwmj()));
                    gjzwsyq.setTdhysyjssj(DateUtils.formateDateToString(bdcGjzwsyqDO.getTdhysyjssj(), DateUtils.DATE_FORMAT_5));
                    gjzwsyq.setTdhysyqssj(DateUtils.formateDateToString(bdcGjzwsyqDO.getTdhysyqssj(), DateUtils.DATE_FORMAT_5));
                    gjzwsyq.setBdcqzh(bdcXmDO.getBdcqzh());
                    gjzwsyq.setDjjg(bdcGjzwsyqDO.getDjjg());
                    gjzwsyq.setDqdm(bdcXmDO.getQxdm());
                    gjzwsyq.setYwh(bdcXmDO.getXmid());
                    gjzwsyq.setBz(bdcXmDO.getBz());
                    if (qlrMap.containsKey(xmid)) {
                        List<BdcQlrDO> bdcQlrDOS = qlrMap.get(xmid);
                        List<CourtCxQlrDTO> courtCxQlrDTOS = new ArrayList<>();
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                            CourtCxQlrDTO courtCxQlrDTO = new CourtCxQlrDTO();
                            BeanUtils.copyProperties(bdcQlrDO, courtCxQlrDTO);
                            courtCxQlrDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                            courtCxQlrDTO.setSxh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setQzysxlh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setSfczr(intToString(bdcQlrDO.getSfczr()));
                            courtCxQlrDTO.setZjzl(intToString(bdcQlrDO.getZjzl()));
                            courtCxQlrDTO.setFzjg(bdcXmDO.getDjjg());
                            courtCxQlrDTO.setXb(intToString(bdcQlrDO.getXb()));
                            courtCxQlrDTO.setDz(bdcQlrDO.getTxdz());
                            courtCxQlrDTO.setQlrlx(intToString(bdcQlrDO.getQlrlx()));
                            courtCxQlrDTO.setGyfs(intToString(bdcQlrDO.getGyfs()));
                            courtCxQlrDTO.setYwh(bdcXmDO.getXmid());
                            courtCxQlrDTO.setQszt(intToString(bdcXmDO.getQszt()));
                            courtCxQlrDTO.setBdclx(intToString(bdcXmDO.getBdclx()));
                            courtCxQlrDTOS.add(courtCxQlrDTO);
                        }
                        gjzwsyq.setQlrxxList(courtCxQlrDTOS);
                    }
                    gjzwsyqList.add(gjzwsyq);

                } else if (bdcQl instanceof BdcLqDO) {
                    BdcLqDO bdcLqDO = (BdcLqDO) bdcQl;
                    String xmid = bdcLqDO.getXmid();
                    if (!xmidList.contains(xmid)) {
                        continue;
                    }
                    if (Objects.nonNull(bdcXmDOMap) && Objects.nonNull(bdcXmDOMap.get(xmid))) {
                        bdcXmDO = bdcXmDOMap.get(xmid);
                    }
                    CourtCxLqDTO lq = new CourtCxLqDTO();
                    lq.setBdcdyh(bdcLqDO.getBdcdyh());
                    lq.setZl(bdcLqDO.getZl());
                    lq.setSyqmj(doubleToString(bdcLqDO.getSyqmj()));
                    lq.setLdsyjssj(DateUtils.formateDateToString(bdcLqDO.getLdsyjssj(), DateUtils.DATE_FORMAT_5));
                    lq.setLdsyqssj(DateUtils.formateDateToString(bdcLqDO.getLdsyqssj(), DateUtils.DATE_FORMAT_5));
                    lq.setLdsyqxz(intToString(bdcLqDO.getLdsyqxz()));
                    lq.setBdcqzh(bdcXmDO.getBdcqzh());
                    lq.setDjjg(bdcXmDO.getDjjg());
                    lq.setDqdm(bdcXmDO.getQxdm());
                    lq.setYwh(bdcXmDO.getXmid());
                    lq.setBz(bdcXmDO.getBz());
                    if (qlrMap.containsKey(xmid)) {
                        List<BdcQlrDO> bdcQlrDOS = qlrMap.get(xmid);
                        List<CourtCxQlrDTO> courtCxQlrDTOS = new ArrayList<>();
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                            CourtCxQlrDTO courtCxQlrDTO = new CourtCxQlrDTO();
                            BeanUtils.copyProperties(bdcQlrDO, courtCxQlrDTO);
                            courtCxQlrDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                            courtCxQlrDTO.setSxh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setQzysxlh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setSfczr(intToString(bdcQlrDO.getSfczr()));
                            courtCxQlrDTO.setZjzl(intToString(bdcQlrDO.getZjzl()));
                            courtCxQlrDTO.setFzjg(bdcXmDO.getDjjg());
                            courtCxQlrDTO.setXb(intToString(bdcQlrDO.getXb()));
                            courtCxQlrDTO.setDz(bdcQlrDO.getTxdz());
                            courtCxQlrDTO.setQlrlx(intToString(bdcQlrDO.getQlrlx()));
                            courtCxQlrDTO.setGyfs(intToString(bdcQlrDO.getGyfs()));
                            courtCxQlrDTO.setYwh(bdcXmDO.getXmid());
                            courtCxQlrDTO.setQszt(intToString(bdcXmDO.getQszt()));
                            courtCxQlrDTO.setBdclx(intToString(bdcXmDO.getBdclx()));
                            courtCxQlrDTOS.add(courtCxQlrDTO);
                        }
                        lq.setQlrxxList(courtCxQlrDTOS);
                    }
                    lqList.add(lq);

                } else if (bdcQl instanceof BdcDyaqDO) {
                    BdcDyaqDO bdcDyaqDO = (BdcDyaqDO) bdcQl;
                    String xmid = bdcDyaqDO.getXmid();
                    BdcXmDO bdcXmDO1 = bdcXmMapper.queryBdcXm(xmid);
                    if (bdcXmDO1 == null) {
                        continue;
                    }
                    CourtCxDyaqDTO dyaq = new CourtCxDyaqDTO();
                    dyaq.setBdcdyh(bdcDyaqDO.getBdcdyh());
                    dyaq.setDybdclx(intToString(bdcDyaqDO.getDybdclx()));
                    dyaq.setZl(bdcDyaqDO.getZl());
                    dyaq.setDyr(bdcDyaqDO.getZwr());
                    dyaq.setDyfs(intToString(bdcDyaqDO.getDyfs()).toString());
                    dyaq.setBdbzzqse(null == bdcDyaqDO.getBdbzzqse() ? "" : doubleToString(bdcDyaqDO.getBdbzzqse()));
                    dyaq.setZwlxjssj(DateUtils.formateDateToString(bdcDyaqDO.getZwlxjssj(), DateUtils.DATE_FORMAT_5));
                    dyaq.setZwlxqssj(DateUtils.formateDateToString(bdcDyaqDO.getZwlxqssj(), DateUtils.DATE_FORMAT_5));
                    dyaq.setBdcdjzmh(bdcXmDO1.getBdcqzh());
                    dyaq.setDyqr(bdcXmDO1.getQlr());
                    dyaq.setDjjg(bdcDyaqDO.getDjjg());
                    dyaq.setDqdm(bdcXmDO1.getQxdm());
                    dyaq.setYwh(bdcXmDO1.getXmid());
                    dyaq.setBz(bdcXmDO.getBz());
                    if (qlrMap.containsKey(xmid)) {
                        List<BdcQlrDO> bdcQlrDOS = qlrMap.get(xmid);
                        List<CourtCxQlrDTO> courtCxQlrDTOS = new ArrayList<>();
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                            CourtCxQlrDTO courtCxQlrDTO = new CourtCxQlrDTO();
                            BeanUtils.copyProperties(bdcQlrDO, courtCxQlrDTO);
                            courtCxQlrDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                            courtCxQlrDTO.setSxh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setQzysxlh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setSfczr(intToString(bdcQlrDO.getSfczr()));
                            courtCxQlrDTO.setZjzl(intToString(bdcQlrDO.getZjzl()));
                            courtCxQlrDTO.setFzjg(bdcXmDO.getDjjg());
                            courtCxQlrDTO.setXb(intToString(bdcQlrDO.getXb()));
                            courtCxQlrDTO.setDz(bdcQlrDO.getTxdz());
                            courtCxQlrDTO.setQlrlx(intToString(bdcQlrDO.getQlrlx()));
                            courtCxQlrDTO.setGyfs(intToString(bdcQlrDO.getGyfs()));
                            courtCxQlrDTO.setYwh(bdcXmDO.getXmid());
                            courtCxQlrDTO.setQszt(intToString(bdcXmDO.getQszt()));
                            courtCxQlrDTO.setBdclx(intToString(bdcXmDO.getBdclx()));
                            courtCxQlrDTOS.add(courtCxQlrDTO);
                        }
                        dyaq.setQlrxxList(courtCxQlrDTOS);
                    }
                    dyaqList.add(dyaq);

                } else if (bdcQl instanceof BdcYgDO) {
                    BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
                    String xmid = bdcYgDO.getXmid();
                    BdcXmDO bdcXmDO1 = bdcXmMapper.queryBdcXm(xmid);
                    if (bdcXmDO1 == null) {
                        continue;
                    }
                    if (ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl())) {
                        continue;
                    }
                    CourtCxYgDTO yg = new CourtCxYgDTO();
                    yg.setBdcdyh(bdcYgDO.getBdcdyh());
                    yg.setYgdjzl(intToString(bdcYgDO.getYgdjzl()).toString());
                    yg.setZl(bdcYgDO.getZl());
                    yg.setGhyt(intToString(bdcYgDO.getGhyt()).toString());
                    yg.setJzmj(doubleToString(bdcYgDO.getJzmj()));
                    yg.setBdcdjzmh(bdcXmDO1.getBdcqzh());
                    yg.setDjjg(bdcYgDO.getDjjg());
                    yg.setDqdm(bdcXmDO1.getQxdm());
                    yg.setYwh(bdcXmDO1.getXmid());
                    yg.setBz(bdcXmDO.getBz());
                    if (qlrMap.containsKey(xmid)) {
                        List<BdcQlrDO> bdcQlrDOS = qlrMap.get(xmid);
                        List<CourtCxQlrDTO> courtCxQlrDTOS = new ArrayList<>();
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                            CourtCxQlrDTO courtCxQlrDTO = new CourtCxQlrDTO();
                            BeanUtils.copyProperties(bdcQlrDO, courtCxQlrDTO);
                            courtCxQlrDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                            courtCxQlrDTO.setSxh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setQzysxlh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setSfczr(intToString(bdcQlrDO.getSfczr()));
                            courtCxQlrDTO.setZjzl(intToString(bdcQlrDO.getZjzl()));
                            courtCxQlrDTO.setFzjg(bdcXmDO.getDjjg());
                            courtCxQlrDTO.setXb(intToString(bdcQlrDO.getXb()));
                            courtCxQlrDTO.setDz(bdcQlrDO.getTxdz());
                            courtCxQlrDTO.setQlrlx(intToString(bdcQlrDO.getQlrlx()));
                            courtCxQlrDTO.setGyfs(intToString(bdcQlrDO.getGyfs()));
                            courtCxQlrDTO.setYwh(bdcXmDO.getXmid());
                            courtCxQlrDTO.setQszt(intToString(bdcXmDO.getQszt()));
                            courtCxQlrDTO.setBdclx(intToString(bdcXmDO.getBdclx()));
                            courtCxQlrDTOS.add(courtCxQlrDTO);
                        }
                        yg.setQlrxxList(courtCxQlrDTOS);
                    }
                    ygdjList.add(yg);

                } else if (bdcQl instanceof BdcCfDO) {
                    BdcCfDO bdcCfDO = (BdcCfDO) bdcQl;
                    String xmid = bdcCfDO.getXmid();
                    BdcXmDO bdcXmDO1 = bdcXmMapper.queryBdcXm(xmid);
                    if (bdcXmDO1 == null) {
                        continue;
                    }
                    CourtCxCfDTO cf = new CourtCxCfDTO();
                    cf.setBdcdyh(bdcCfDO.getBdcdyh());
                    cf.setZl(bdcCfDO.getZl());
                    cf.setCfjg(bdcCfDO.getCfjg());
                    cf.setCflx(intToString(bdcCfDO.getCflx()).toString());
                    cf.setCfwh(bdcCfDO.getCfwh());
                    cf.setCfjssj(DateUtils.formateDateToString(bdcCfDO.getCfjssj(), DateUtils.DATE_FORMAT_5));
                    cf.setCfqssj(DateUtils.formateDateToString(bdcCfDO.getCfqssj(), DateUtils.DATE_FORMAT_5));
                    cf.setCfywh(bdcXmDO1.getXmid());
                    cf.setDjjg(bdcCfDO.getDjjg());
                    cf.setDqdm(bdcXmDO1.getQxdm());
                    cf.setYwh(bdcXmDO1.getXmid());
                    cf.setBz(bdcXmDO.getBz());
                    if (qlrMap.containsKey(xmid)) {
                        List<BdcQlrDO> bdcQlrDOS = qlrMap.get(xmid);
                        List<CourtCxQlrDTO> courtCxQlrDTOS = new ArrayList<>();
                        for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                            CourtCxQlrDTO courtCxQlrDTO = new CourtCxQlrDTO();
                            BeanUtils.copyProperties(bdcQlrDO, courtCxQlrDTO);
                            courtCxQlrDTO.setBdcdyh(bdcXmDO.getBdcdyh());
                            courtCxQlrDTO.setSxh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setQzysxlh(intToString(bdcQlrDO.getSxh()));
                            courtCxQlrDTO.setSfczr(intToString(bdcQlrDO.getSfczr()));
                            courtCxQlrDTO.setZjzl(intToString(bdcQlrDO.getZjzl()));
                            courtCxQlrDTO.setFzjg(bdcXmDO.getDjjg());
                            courtCxQlrDTO.setXb(intToString(bdcQlrDO.getXb()));
                            courtCxQlrDTO.setDz(bdcQlrDO.getTxdz());
                            courtCxQlrDTO.setQlrlx(intToString(bdcQlrDO.getQlrlx()));
                            courtCxQlrDTO.setGyfs(intToString(bdcQlrDO.getGyfs()));
                            courtCxQlrDTO.setYwh(bdcXmDO.getXmid());
                            courtCxQlrDTO.setQszt(intToString(bdcXmDO.getQszt()));
                            courtCxQlrDTO.setBdclx(intToString(bdcXmDO.getBdclx()));
                            courtCxQlrDTOS.add(courtCxQlrDTO);
                        }
                        cf.setQlrxxList(courtCxQlrDTOS);
                    }
                    cfdjList.add(cf);

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
            return responseDTO;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ParameterException(e.getMessage());
        }
    }


    /**
     * 调用该接口获取请求单位各控制申请涉及的相关文书信息
     *
     * @return
     */
    @Override
    public FjclDTOForZhlc courtCxwsInfo(String kzqqdh) {
        FjclDTOForZhlc fjxx = new FjclDTOForZhlc();
        LOGGER.info("查询{}对应文书信息,当前时间:{}", kzqqdh, new Date());
        BdcCourtKzJhService bdcCourtKzJhService = getjhService(BdcCourtKzEnum.SOAP.getCode());
        CourtKzUserDTO usermarker = buildCourtKzUserRequest();
        CourtKzdhDTO courtKzdhDTO = buildCourtKzdhRequest(kzqqdh);
        CourtKzBdcWsInfoDTO courtKzBdcWsInfoDTO = bdcCourtKzJhService.kzwsInfo(usermarker, courtKzdhDTO);
        LOGGER.info("查询{}对应文书信息,当前时间:{},返回{}", kzqqdh, new Date(), JSON.toJSONString(courtKzBdcWsInfoDTO));
        if (CollectionUtils.isNotEmpty(courtKzBdcWsInfoDTO.getCourtKzBdcWsInfos())) {
            for (CourtKzBdcWsInfo courtKzBdcWsInfo : courtKzBdcWsInfoDTO.getCourtKzBdcWsInfos()) {
                if (Objects.isNull(fjxx.getClmc())) {
                    fjxx.setClmc("文书信息");
                    fjxx.setFjlx("1");
                    fjxx.setYs(1);
                    fjxx.setFs(courtKzBdcWsInfoDTO.getCourtKzBdcWsInfos().size());
                    List<FjclmxDTO> clnr = new ArrayList<>();
                    fjxx.setClnr(clnr);
                }
                FjclmxDTO clnr = new FjclmxDTO();
                clnr.setFjnr(courtKzBdcWsInfo.getWSNR());
                clnr.setFjmc(courtKzBdcWsInfo.getWJMC() + "." + courtKzBdcWsInfo.getWJLX());
                fjxx.getClnr().add(clnr);
            }
        }
        return fjxx;
    }

    /**
     * 调用该接口获取请求单位各控制申请涉及的法官证件信息
     *
     * @return
     */
    @Override
    public FjclDTOForZhlc courtCxZjInfo(String kzqqdh) {
        FjclDTOForZhlc fjxx = new FjclDTOForZhlc();
        BdcCourtKzJhService bdcCourtKzJhService = getjhService(BdcCourtKzEnum.SOAP.getCode());
        CourtKzUserDTO usermarker = buildCourtKzUserRequest();
        CourtKzdhDTO courtKzdhDTO = buildCourtKzdhRequest(kzqqdh);
        CourtKzBdcZjInfoDTO courtKzBdcZjInfoDTO = bdcCourtKzJhService.zjInfo(usermarker, courtKzdhDTO);
        LOGGER.info("查询{}对应文书信息,当前时间:{},返回{}", kzqqdh, new Date(), JSON.toJSONString(courtKzBdcZjInfoDTO));
        if (CollectionUtils.isNotEmpty(courtKzBdcZjInfoDTO.getCourtKzBdcZjInfos())) {
            for (CourtKzBdcZjInfo courtKzBdcZjInfo : courtKzBdcZjInfoDTO.getCourtKzBdcZjInfos()) {
                if (Objects.isNull(fjxx.getClmc())) {
                    fjxx.setClmc("公务证");
                    fjxx.setFjlx("1");
                    fjxx.setYs(1);
                    fjxx.setFs(courtKzBdcZjInfoDTO.getCourtKzBdcZjInfos().size());
                    List<FjclmxDTO> clnr = new ArrayList<>();
                    fjxx.setClnr(clnr);
                }
                FjclmxDTO gwz = new FjclmxDTO();
                gwz.setFjnr(courtKzBdcZjInfo.getGWZ());
                gwz.setFjmc(courtKzBdcZjInfo.getGWZBM() + "." + courtKzBdcZjInfo.getGWZWJGS());
                fjxx.getClnr().add(gwz);


                FjclmxDTO gzz = new FjclmxDTO();
                gwz.setFjnr(courtKzBdcZjInfo.getGZZ());
                gwz.setFjmc(courtKzBdcZjInfo.getGZZBM() + "." + courtKzBdcZjInfo.getGZZWJGS());
                fjxx.getClnr().add(gwz);
            }
        }
        return fjxx;
    }

    // TODO: 2022/11/1 暂时只有一个soap
    private BdcCourtKzJhService getjhService(String type) {
        return getBean(bdcCourtKzJhServices, type);
    }

    /**
     * 生成用户请求
     *
     * @return
     */
    public CourtKzUserDTO buildCourtKzUserRequest() {
        CourtKzUserDTO USERMARKER = new CourtKzUserDTO();
        CourtKzUserDTO.CONDITION condition = new CourtKzUserDTO.CONDITION();
        condition.setUSERNAME(Base64Utils.encodeByteToBase64Str(username.getBytes(StandardCharsets.UTF_8)));
        condition.setPASSWORD(Base64Utils.encodeByteToBase64Str(password.getBytes(StandardCharsets.UTF_8)));
        USERMARKER.setCONDITION(condition);
        //转换为xml
        return USERMARKER;
    }

    public CourtKzHeader buildCourtKzHeadRequest() {
        CourtKzHeader courtKzHeader = new CourtKzHeader();
        courtKzHeader.setCREATETIME(DateUtils.formateTime(new Date(), DateUtils.DATE_FORMAT_5));
        courtKzHeader.setRESPONSECODE("0000");
        courtKzHeader.setRESPONSEINFO("");
        courtKzHeader.setDIGITALSIGN(digitalsign);
        //转换为xml
        return courtKzHeader;
    }

    /**
     * 生成控制单号请求
     *
     * @return
     */
    public CourtKzdhDTO buildCourtKzdhRequest(String cxkzdh) {
        CourtKzdhDTO courtKzdhDTO = new CourtKzdhDTO();
        CourtKzdhDTO.CONDITION condition = new CourtKzdhDTO.CONDITION();
        condition.setCXQQDH(Base64Utils.encodeByteToBase64Str(cxkzdh.getBytes(StandardCharsets.UTF_8)));
        courtKzdhDTO.setCONDITION(condition);
        //转换为xml
        return courtKzdhDTO;
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
}
