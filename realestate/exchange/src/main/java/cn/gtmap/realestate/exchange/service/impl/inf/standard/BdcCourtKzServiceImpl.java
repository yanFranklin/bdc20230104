package cn.gtmap.realestate.exchange.service.impl.inf.standard;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlXmDTO;
import cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmRequestDTO;
import cn.gtmap.realestate.common.core.dto.accept.WwsqCjBdcXmResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.court.*;
import cn.gtmap.realestate.common.core.dto.exchange.court.kz.*;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTO;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTOForZhlc;
import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclmxDTO;
import cn.gtmap.realestate.common.core.enums.BdcCzrzLxEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcCzrzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDbxxFeignService;
import cn.gtmap.realestate.common.util.Base64Utils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.cfjfdj.CfCfxxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.cfjfdj.CfRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.cfjfdj.CreateData;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.cfjfdj.InitCfjfdjRquestDataQlrDTO;
import cn.gtmap.realestate.exchange.core.enums.BdcCourtKzEnum;
import cn.gtmap.realestate.exchange.core.enums.BdcCourtKzcsEnum;
import cn.gtmap.realestate.exchange.service.impl.inf.wwsq.GxWwSqxxServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.FjclService;
import cn.gtmap.realestate.exchange.service.inf.standard.BdcCourtKzService;
import cn.gtmap.realestate.exchange.service.inf.standard.court.BdcCourtKzJhService;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSON;
import org.apache.avalon.framework.parameters.ParameterException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static cn.gtmap.realestate.common.util.InterfaceCodeBeanFactory.getBean;

@Service
public class BdcCourtKzServiceImpl implements BdcCourtKzService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcCourtKzServiceImpl.class);


    @Autowired
    private BdcSdFeignService bdcSdFeignService;

    @Autowired
    Set<BdcCourtKzJhService> bdcCourtKzJhServices;

    @Autowired
    GxWwSqxxServiceImpl gxWwSqxxService;

    @Autowired
    BdcSlFeignService bdcSlFeignService;

    @Autowired
    CommonService commonService;

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcCzrzFeignService bdcCzrzFeignService;

    @Value("${CourtKz.enable:false}")
    private Boolean enable;

    /**
     * 交互方式,默认使用接口
     */
    @Value("${CourtKz.version:soap}")
    private String CourtKzJhVersion;

    @Value("${CourtKz.password:password}")
    private String password;

    @Value("${CourtKz.username:username}")
    private String username;

    @Value("${CourtKz.digitalsign:digitalsign}")
    private String digitalsign;

    @Value("${CourtKz.sqdjlx.cf:zAks8CDshxeYNpdF}")
    private String sqdjlxCf;

    @Value("${CourtKz.sqdjlx.jf:MFwG65TJxPXd6iaB}")
    private String sqdjlxJf;

    @Value("${CourtKz.sqdjlx.xf:NKYJn22pkbPKzdMQ}")
    private String sqdjlxXf;

    @Autowired
    FjclService fjclService;

    @Autowired
    BdcYwsjHxFeignService bdcYwsjHxFeignService;

    @Autowired
    BdcDbxxFeignService bdcDbxxFeignService;


    /**
     * 轮询查看当前法院的请求
     */
    @Override
    @Scheduled(cron = "${CourtKz.kz.cron:0 0/20 8-17 ? * MON-FRI}")
    public void CourtKzBdcQL() {
        if (!enable) {
            return;
        }
        BdcCourtKzJhService bdcCourtKzJhService = getjhService(BdcCourtKzEnum.SOAP.getCode());
        CourtKzUserDTO usermarker = buildCourtKzUserRequest();
        CourtKzDTO courtKzDTO = bdcCourtKzJhService.kzBdcQL(usermarker);
        //处理收到的请求
        LOGGER.info("定时接收法院的件,当前时间:{},收到的请求为:{}", new Date(), JSON.toJSONString(courtKzDTO));
        if (StringUtils.isNotBlank(courtKzDTO.getErrorMSG())) {
            LOGGER.info("定时接收法院的件,当前时间:{},请求错误:{}", new Date(), courtKzDTO.getErrorMSG());
            //  return;
        }
        if (Objects.nonNull(courtKzDTO.getCourtKzData()) && CollectionUtils.isNotEmpty(courtKzDTO.getCourtKzData().getBdcQQItemList())) {
            //处理返回值
            List<CourtKzBdcQQItem> bdcQQItemList = courtKzDTO.getCourtKzData().getBdcQQItemList();
            for (CourtKzBdcQQItem courtKzBdcQQItem : bdcQQItemList) {
                LOGGER.info("定时接收法院的件,当前时间:{},处理收到的请求:{}", new Date(), JSON.toJSONString(courtKzBdcQQItem));
                feedCourtKzCfBdcQL(courtKzBdcQQItem);
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
    public void feedCourtKzCfBdcQL(CourtKzBdcQQItem courtKzBdcQQItem) {
        LOGGER.info("定时接收法院的件,当前时间:{},处理收到的控制请求:{}", new Date(), JSON.toJSONString(courtKzBdcQQItem));
        BdcCourtKzJhService bdcCourtKzJhService = getjhService(BdcCourtKzEnum.SOAP.getCode());
        if (CollectionUtils.isNotEmpty(courtKzBdcQQItem.getBdcQlList())) {
            try {
                List<BdcDysdDO> bdcDysdDOList = new ArrayList<>();
                for (CourtKzBdcQlItem courtKzBdcQlItem : courtKzBdcQQItem.getBdcQlList()) {
                    BdcDysdDO bdcDysdDO = new BdcDysdDO();
                    bdcDysdDO.setBdcdyh(courtKzBdcQlItem.getBDCDYH());
//                    bdcDysdDO.setBdcdyh("34****306008GB00014F00310202");
                    bdcDysdDO.setSdsqwh(courtKzBdcQQItem.getCXQQDH());
                    bdcDysdDOList.add(bdcDysdDO);
                }
                if (CollectionUtils.isNotEmpty(bdcDysdDOList)) {
                    String kzcs = courtKzBdcQQItem.getBdcQlList().get(0).getKZCS();
                    //生成锁定信息
                    if (BdcCourtKzcsEnum.CF.getCode().equals(kzcs) || BdcCourtKzcsEnum.XF.getCode().equals(kzcs) ) {
                        sdBdcdy(bdcDysdDOList);
                    }
                    //初始化查封业务数据
                    CfRequestDTO cfRequestDTO = buildCfRequest(courtKzBdcQQItem);
                    CreateData createData = new CreateData();
                    createData.setData(cfRequestDTO);
                    cfRequestDTO.setBjbh(courtKzBdcQQItem.getCXQQDH());
                    WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = gxWwSqxxService.initCfParamDTO(createData);
                    //生成业务数据
                    WwsqCjBdcXmResponseDTO wwsqCjBdcXmResponseDTO = bdcSlFeignService.wwsqCjBdcXm(wwsqCjBdcXmRequestDTO);
                    LOGGER.info("定时接收法院的件{},流程创建结果{}", courtKzBdcQQItem.getCXQQDH(), JSON.toJSONString(wwsqCjBdcXmResponseDTO));
                    //附件信息
                    List<FjclDTOForZhlc> hbfjxx = new ArrayList<>();
                    hbfjxx.add(courtKzwsInfo(courtKzBdcQQItem.getCXQQDH()));
                    hbfjxx.add(courtKzZjInfo(courtKzBdcQQItem.getCXQQDH()));
                    revertCjResponse(wwsqCjBdcXmResponseDTO, hbfjxx, wwsqCjBdcXmRequestDTO);
                    LOGGER.info("定时接收法院的件{},流程创建结果{}", courtKzBdcQQItem.getCXQQDH(), JSON.toJSONString(wwsqCjBdcXmResponseDTO));
                    //返回数据
                    CourtKzUserDTO usermarker = buildCourtKzUserRequest();
                    CourtKzHeader courtKzHeader = buildCourtKzHeadRequest();
                    CourtFeedKzBdcQLDTO feedbackinfo = new CourtFeedKzBdcQLDTO();
                    feedbackinfo.setCourtKzHeader(courtKzHeader);

                    CourtKzKzxxItem courtKzKzxxItem = new CourtKzKzxxItem();
                    courtKzKzxxItem.setCXQQDH(courtKzBdcQQItem.getCXQQDH());
                    courtKzKzxxItem.setRESULT("0000");
                    List<CourtKzKzxxItem.CourtKzKzxx> KZXXLIST = new ArrayList<>();
                    for (CourtKzBdcQlItem courtKzBdcQlItem : courtKzBdcQQItem.getBdcQlList()) {
                        CourtKzKzxxItem.CourtKzKzxx courtKzKzxx = new CourtKzKzxxItem.CourtKzKzxx();
                        courtKzKzxx.setBDCQLLX("3");
                        courtKzKzxx.setBDCDYH(courtKzBdcQlItem.getBDCDYH());
                        courtKzKzxx.setBDCQZH(courtKzBdcQlItem.getBDCQZH());
                        courtKzKzxx.setKZZT("1");
                        courtKzKzxx.setCSJSRQ(courtKzBdcQlItem.getJSRQ());
                        courtKzKzxx.setCSKSRQ(courtKzBdcQlItem.getKSRQ());
                        courtKzKzxx.setYWH(wwsqCjBdcXmResponseDTO.getBdcXmDOList().get(0).getXmid());
                        courtKzKzxx.setCFYWH(wwsqCjBdcXmResponseDTO.getBdcXmDOList().get(0).getXmid());
                        KZXXLIST.add(courtKzKzxx);
                    }
                    courtKzKzxxItem.setKZXXLIST(KZXXLIST);
                    CourtKzKzxxIData courtKzKzxxIData = new CourtKzKzxxIData();
                    courtKzKzxxIData.setBdcQQItemList(Arrays.asList(courtKzKzxxItem));
                    feedbackinfo.setCourtKzKzxxIData(courtKzKzxxIData);
                    bdcCourtKzJhService.feedkzBdcQL(usermarker, feedbackinfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info("定时接收法院的件{},流程创建错误{}", courtKzBdcQQItem.getCXQQDH(), e.getMessage());
            }
        }
    }

    /**
     * 锁定单元号
     *
     * @param bdcDysdDOList
     * @throws Exception
     */
    public void sdBdcdy(List<BdcDysdDO> bdcDysdDOList) throws Exception {
        if (CollectionUtils.isNotEmpty(bdcDysdDOList) && StringUtils.isNotBlank(bdcDysdDOList.get(0).getBdcdyh())
                && StringUtils.isNotBlank(bdcDysdDOList.get(0).getSdsqwh())) {
            try {
                BdcDysdDO param = new BdcDysdDO();
                param.setBdcdyh(CollectionUtils.isNotEmpty(bdcDysdDOList) ? bdcDysdDOList.get(0).getBdcdyh() : "");
                param.setSdzt(1);
                param.setSdsqwh(CollectionUtils.isNotEmpty(bdcDysdDOList) ? bdcDysdDOList.get(0).getSdsqwh() : "");
                List<BdcDysdDO> queryBdczsSd = bdcSdFeignService.queryBdcdySd(param);
                if (CollectionUtils.isNotEmpty(queryBdczsSd)) {
                    throw new ParameterException("该不动产单元号在此申请文号下已存在锁定信息!");
                }
                bdcSdFeignService.sdBdcdy(bdcDysdDOList);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new ParameterException(e.getMessage());
            }
        } else {
            throw new ParameterException("请检查参数！");
        }
    }


    /**
     * 解锁单元
     *
     * @param bdcDysdDOList
     * @throws Exception
     */
    public void jsBdcdy(List<BdcDysdDO> bdcDysdDOList) throws Exception {
        if (CollectionUtils.isNotEmpty(bdcDysdDOList) && StringUtils.isNotBlank(bdcDysdDOList.get(0).getBdcdyh())
                && StringUtils.isNotBlank(bdcDysdDOList.get(0).getSdsqwh())) {
            try {
                for (BdcDysdDO bdcDysdDO : bdcDysdDOList) {
                    BdcDysdDO param = new BdcDysdDO();
                    param.setBdcdyh(bdcDysdDO.getBdcdyh());
                    param.setSdsqwh(bdcDysdDO.getSdsqwh());
                    param.setSdzt(1);
                    //同一bdcdyh和sdsqwh，只存在一条现势锁定数据
                    List<BdcDysdDO> queryBdcdySd = bdcSdFeignService.queryBdcdySd(param);
                    if (CollectionUtils.isEmpty(queryBdcdySd)) {
                        throw new ParameterException("根据bdcdyh未查询到不动产单元锁定信息!");
                    }
                    bdcDysdDO.setSdzt(0);
                    bdcDysdDO.setDysdid(queryBdcdySd.get(0).getDysdid());
                    List<BdcDysdDO> bdcDysdDOSd = new ArrayList<>();
                    bdcDysdDOSd.add(bdcDysdDO);
                    bdcSdFeignService.jsBdcdy(bdcDysdDOSd, bdcDysdDO.getJsyy());
                    bdcDbxxFeignService.synQjBdcdyztSd(Arrays.asList(bdcDysdDO.getBdcdyh()), CommonConstantUtils.SDZT_JS);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new ParameterException(e.getMessage());
            }
        } else {
            throw new ParameterException("请检查参数！");
        }
    }


    /**
     * 更新业务状态为未创建
     *
     * @param cxkzdh
     * @throws Exception
     */
    public void updateCjywzt(String processId, String cxkzdh) {
        BdcCourtKzJhService bdcCourtKzJhService = getjhService(BdcCourtKzEnum.SOAP.getCode());
        LOGGER.info("更新业务{},{}状态为未创建", processId, cxkzdh);
        if (StringUtils.isBlank(cxkzdh)) {
            return;
        }
        CourtKzUserDTO usermarker = buildCourtKzUserRequest();
        CourtKzHeader courtKzHeader = buildCourtKzHeadRequest();
        CourtFeedKzBdcQLDTO feedbackinfo = new CourtFeedKzBdcQLDTO();
        feedbackinfo.setCourtKzHeader(courtKzHeader);
        CourtKzKzxxIData courtKzKzxxIData = new CourtKzKzxxIData();
        CourtKzKzxxItem courtKzKzxxItem = new CourtKzKzxxItem();
        courtKzKzxxItem.setCXQQDH(cxkzdh);
        courtKzKzxxItem.setRESULT("0000");
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(processId);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
            //获取退回意见
            BdcCzrzDO bdcCzrzDO = new BdcCzrzDO();
            bdcCzrzDO.setGzlslid(processId);
            bdcCzrzDO.setCzlx(BdcCzrzLxEnum.CZRZ_CZLX_WWTJ.key());
            List<BdcCzrzDO> bdcCzrzDOS = bdcCzrzFeignService.listBdcCzrz(bdcCzrzDO);
            String opinion = "";
            if (CollectionUtils.isNotEmpty(bdcCzrzDOS) && org.apache.commons.lang3.StringUtils.isNotBlank(bdcCzrzDOS.get(0).getCzyy())) {
                opinion = bdcCzrzDOS.get(0).getCzyy();
            }
            List<CourtKzKzxxItem.CourtKzKzxx> courtKzKzxxList = new ArrayList<>();
            for (BdcXmDO bdcXmDO : bdcXmDOList) {
                CourtKzKzxxItem.CourtKzKzxx courtKzKzxx = new CourtKzKzxxItem.CourtKzKzxx();
                courtKzKzxx.setBDCQLLX("3");
                courtKzKzxx.setBDCDYH(bdcXmDO.getBdcdyh());
                courtKzKzxx.setBDCQZH(bdcXmDO.getBdcqzh());
                courtKzKzxx.setKZZT("2");
                courtKzKzxx.setWNKZYY(opinion);
                courtKzKzxx.setYWH(bdcXmDO.getXmid());
                courtKzKzxx.setCFYWH(bdcXmDO.getXmid());
                courtKzKzxxList.add(courtKzKzxx);
            }
            courtKzKzxxItem.setKZXXLIST(courtKzKzxxList);
            courtKzKzxxIData.setBdcQQItemList(Arrays.asList(courtKzKzxxItem));
            feedbackinfo.setCourtKzKzxxIData(courtKzKzxxIData);
            bdcCourtKzJhService.feedkzBdcQL(usermarker, feedbackinfo);
        } else {
            LOGGER.info("更新业务{},{}状态为未创建,未找到数据", processId, cxkzdh);
        }
    }


    /**
     * 构建查询参数
     *
     * @return
     */
    private CfRequestDTO buildCfRequest(CourtKzBdcQQItem courtKzBdcQQItem) {
        CfRequestDTO cfRequestDTO = new CfRequestDTO();
        cfRequestDTO.setSlly(CommonConstantUtils.SPLY_FY.toString());
        if (BdcCourtKzcsEnum.CF.getCode().equals(courtKzBdcQQItem.getBdcQlList().get(0).getKZCS())) {
            cfRequestDTO.setSqdjlx(sqdjlxCf);
        } else if (BdcCourtKzcsEnum.XF.getCode().equals(courtKzBdcQQItem.getBdcQlList().get(0).getKZCS())) {
            cfRequestDTO.setSqdjlx(sqdjlxXf);
        } else if (BdcCourtKzcsEnum.JF.getCode().equals(courtKzBdcQQItem.getBdcQlList().get(0).getKZCS())) {
            cfRequestDTO.setSqdjlx(sqdjlxJf);
        } else {
            cfRequestDTO.setSqdjlx(sqdjlxCf);
        }
        List<CfCfxxRequestDTO> cfxx = new ArrayList<>();
        for (CourtKzBdcQlItem courtKzBdcQlItem : courtKzBdcQQItem.getBdcQlList()) {
            CfCfxxRequestDTO cfCfxxRequestDTO = new CfCfxxRequestDTO();
            cfCfxxRequestDTO.setCfkssj(courtKzBdcQlItem.getKSRQ());
            cfCfxxRequestDTO.setCfjssj(courtKzBdcQlItem.getJSRQ());
            cfCfxxRequestDTO.setBdcdyh(courtKzBdcQlItem.getBDCDYH());
//            cfCfxxRequestDTO.setBdcdyh("34****306008GB00014F00310202");
            cfCfxxRequestDTO.setZl(courtKzBdcQlItem.getZL());
            cfCfxxRequestDTO.setQllx(CommonConstantUtils.QLLX_CF.toString());
            cfCfxxRequestDTO.setCfsj(DateUtils.formateYmdhms(new Date()));
            //如果是解封和续封
            if (BdcCourtKzcsEnum.CF.getCode().equals(courtKzBdcQlItem.getKZCS())
                    || BdcCourtKzcsEnum.XF.getCode().equals(courtKzBdcQlItem.getKZCS())) {
                cfCfxxRequestDTO.setCfwh(courtKzBdcQQItem.getAH());
                cfCfxxRequestDTO.setCflx(CommonConstantUtils.CFLX_CF.toString());
                cfCfxxRequestDTO.setCfwj(courtKzBdcQlItem.getCKWH());
                cfCfxxRequestDTO.setCfjg(courtKzBdcQQItem.getFYMC());
                cfCfxxRequestDTO.setBdcqzh(courtKzBdcQlItem.getBDCQZH());
            } else if (BdcCourtKzcsEnum.JF.getCode().equals(courtKzBdcQlItem.getKZCS())) {
                cfCfxxRequestDTO.setJfwh(courtKzBdcQQItem.getAH());
                cfCfxxRequestDTO.setJfwj(courtKzBdcQlItem.getCKWH());
                cfCfxxRequestDTO.setJfjg(courtKzBdcQQItem.getFYMC());
                cfCfxxRequestDTO.setCflx(CommonConstantUtils.CFLX_XF.toString());
            }
            if (BdcCourtKzcsEnum.JF.getCode().equals(courtKzBdcQlItem.getKZCS())
                    || BdcCourtKzcsEnum.XF.getCode().equals(courtKzBdcQlItem.getKZCS())) {
                //输入原来的查封文号
                List<BdcQl> listCf = commonService.listXsQlByBdcdyh(cfCfxxRequestDTO.getBdcdyh(),
                        CommonConstantUtils.QLLX_CF.toString());
                if (CollectionUtils.isNotEmpty(listCf)) {
                    for (BdcQl bdcQl : listCf) {
                        if (bdcQl.getXmid().equals(courtKzBdcQlItem.getYCFYWH())) {
                            cfCfxxRequestDTO.setBdcqzh(((BdcCfDO) bdcQl).getCfwh());
                            cfCfxxRequestDTO.setZsxmid(((BdcCfDO) bdcQl).getXmid());
                        }
                    }
                }
            }
            //权利人信息
            if (CollectionUtils.isNotEmpty(courtKzBdcQlItem.getGhdxList())) {
                List<InitCfjfdjRquestDataQlrDTO> qlr = new ArrayList<>();
                for (CourtKzBdcQlGhdxItem courtKzBdcQlGhdxItem : courtKzBdcQlItem.getGhdxList()) {
                    InitCfjfdjRquestDataQlrDTO initCfjfdjRquestDataQlrDTO = new InitCfjfdjRquestDataQlrDTO();
                    initCfjfdjRquestDataQlrDTO.setQlrmc(courtKzBdcQlGhdxItem.getDXXM());
                    initCfjfdjRquestDataQlrDTO.setZjh(courtKzBdcQlGhdxItem.getDXSFZHM());
                    initCfjfdjRquestDataQlrDTO.setZjzl(courtKzBdcQlGhdxItem.getDXZJLX());
                    initCfjfdjRquestDataQlrDTO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    qlr.add(initCfjfdjRquestDataQlrDTO);
                }
                cfCfxxRequestDTO.setQlr(qlr);
            }
            cfxx.add(cfCfxxRequestDTO);
        }
        cfRequestDTO.setCfxx(cfxx);
        return cfRequestDTO;
    }

    public void revertCjResponse(WwsqCjBdcXmResponseDTO responseDTO,
                                 List<FjclDTOForZhlc> fjclList,
                                 WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO) {
        // 验证不通过的处理
        if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getGzyzList())) {
            StringBuilder msgSb = new StringBuilder();
            List<Map<String, Object>> yzResult = responseDTO.getGzyzList();
            for (Map<String, Object> map : yzResult) {
                String bdcdyh = org.apache.commons.lang3.StringUtils.defaultString(MapUtils.getString(map, "bdcdyh"));
                String yzmsg = org.apache.commons.lang3.StringUtils.defaultString(MapUtils.getString(map, "msg"));
                msgSb.append(bdcdyh).append(yzmsg).append(",");
            }
            String msg = msgSb.toString();
            LOGGER.info("生成登记项目验证出错{}", msg);
            throw new AppException("生成登记项目验证出错");
        }
        // 异步上传附件
        if (CollectionUtils.isNotEmpty(fjclList)) {
            List<FjclDTO> fjclDTOList = new ArrayList<>();
            for (FjclDTOForZhlc fjclDTOForZhlc : fjclList) {
                FjclDTO fjclDTO = new FjclDTO();
                BeanUtils.copyProperties(fjclDTOForZhlc, fjclDTO);
                fjclDTO.setFjclmxDTOList(fjclDTOForZhlc.getClnr());
                fjclDTOList.add(fjclDTO);
            }
            fjclService.asynUploadAndSaveFjcl(responseDTO, fjclDTOList);
        }

        if (Objects.isNull(responseDTO) || CollectionUtils.isEmpty(responseDTO.getBdcXmDOList())) {
            throw new AppException("没有生成登记项目");
        }
    }

    /**
     * 调用该接口获取请求单位各控制申请涉及的相关文书信息
     *
     * @return
     */
    @Override
    public FjclDTOForZhlc courtKzwsInfo(String kzqqdh) {
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
    public FjclDTOForZhlc courtKzZjInfo(String kzqqdh) {
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
                gzz.setFjnr(courtKzBdcZjInfo.getGZZ());
                gzz.setFjmc(courtKzBdcZjInfo.getGZZBM() + "." + courtKzBdcZjInfo.getGZZWJGS());
                fjxx.getClnr().add(gzz);
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
        courtKzHeader.setCREATETIME(DateUtils.formateTime(new Date(), DateUtils.DATE_FORMAT_4));
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
}
