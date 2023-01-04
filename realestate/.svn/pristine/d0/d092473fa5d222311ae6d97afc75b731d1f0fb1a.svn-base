package cn.gtmap.realestate.exchange.service.impl.inf.yctb;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZdDsfzdgxDO;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.dto.building.BdcdyhZtResponseDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.exchange.hefei.yctb.YctbCommonResponse;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQszmDyDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcQszmQO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSfSsxxQO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.building.FwYcHsFeignService;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLsgxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcBzbZmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcdyZtFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.config.CaqzConfig;
import cn.gtmap.realestate.exchange.core.dto.yctb.*;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXmMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.request.HfYctbInterfaceServiceImpl;
import cn.gtmap.realestate.exchange.service.inf.hefei.BdcYctbService;
import cn.gtmap.realestate.exchange.util.AhCaPdfQzUtils;
import cn.gtmap.realestate.exchange.util.PdfSearchPositionUtil;
import cn.gtmap.realestate.exchange.util.SM2Util;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.web.rest.ExchangeInterfaceRestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 3.0  2022/2/25
 * @description (合肥) 一窗通办相关服务处理
 */
@Service
public class BdcYctbServiceImpl implements BdcYctbService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYctbServiceImpl.class);

    @Value("${hefei.yctb.pay.url:https://www.hfzrzy.com/estateplat-olcommon/api/v2/ykqjfModel/getqrcodepayForDj?username=dj3}")
    private String payUrl;

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcXmMapper bdcXmMapper;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private BdcSlSfxxFeignService bdcSlSfxxFeignService;
    @Autowired
    private ExchangeInterfaceRestController exchangeInterfaceRestController;
    @Autowired
    private BdcSlZdFeignService bdcSlZdFeignService;
    @Autowired
    private BdcdjMapper bdcdjMapper;

    @Autowired
    private PdfController pdfController;

    @Autowired
    private DozerBeanMapper exchangeDozerMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private BdcSlFeignService bdcSlFeignService;

    @Autowired
    private BdcZdFeignService bdcZdFeignService;

    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;

    @Autowired
    private BdcBzbZmFeignService bdcBzbZmFeignService;

    @Autowired
    private BdcPrintFeignService bdcPrintFeignService;

    @Autowired
    private BdcYctbService bdcYctbService;

    @Autowired
    private BdcLsgxFeignService bdcLsgxFeignService;

    @Autowired
    private BdcdyZtFeignService bdcdyZtFeignService;

    @Autowired
    private FwHsFeignService fwHsFeignService;

    @Autowired
    private FwYcHsFeignService fwYcHsFeignService;

    @Autowired
    private BdcGzZhGzFeignService bdcGzZhGzFeignService;

    @Autowired
    private BdcShxxFeignService bdcShxxFeignService;

    @Autowired
    UserManagerUtils userManagerUtils;
    /**
     * 登记文件操作缓存目录（可以配置和打印一个路径）
     */
    @Value("${hefei.yctb.dengji.filepath:/Users/wahaha-zhanshi/Desktop/tempzip}")
    private String localFilePath;

    //对应省平台要求推送的，相当于对照
    @Value("#{${hefei.yctb.jdmc.mapping:{'01': '初审'}}}")
    private Map<String, String> nodeMap;
    //对应我们，自己的审核信息节点，用于查询开始时间和结束时间
    @Value("#{${hefei.yctb.shxxjdmc.mapping:{'01': '初审'}}}")
    private Map<String, String> bdcNodeMap;
    /**
     * 文件编码方式
     */
    @Value("${hefei.yctb.encoding:GBK}")
    private String encoding;

    /**
     * 权属证明打印类型（权利人）
     */
    @Value("${hefei.yctb.zxcd.dylxQlr:fwqszm}")
    private String qlrDylx;

    /**
     * 权属证明打印类型（其他人）
     */
    @Value("${hefei.yctb.zxcd.dylxQtr:fwqszm2}")
    private String qtrDylx;

    /**
     * 登记文件操作缓存目录（可以配置和打印一个路径）
     */
    @Value("${hefei.yctb.gzyz.zhbs:}")
    private String gzyzzhbs;

    /**
     * 在线查档返回pdf模版位置
     */
    @Value("${hefei.yctb.zxcd.mbwzQlr:/Users/wahaha-zhanshi/Desktop/temp/sczmd.docx}")
    private String printPath;
    @Value("${hefei.yctb.zxcd.mbwzQtr:/Users/wahaha-zhanshi/Desktop/temp/sczmdqt.docx}")
    private String printPathCxlx2;

    @Value("${data.version}")
    private String dataVersion;
    @Value("#{'${hefei.yctb.sply:}'.split(',')}")
    private List<Integer> splyList;

    @Value("#{${hefei.yctb.zxcd.qxdmmap:{'340124':'340124'}}}")
    private Map<String, String> qxdmMap;

    /**
     * 省平台调我们接口，请求体是否解密,true是不加解密，false是加解密
     */
    @Value("${yctb.interface.test.flag:true}")
    private String jmkg;
    @Value("#{${yctb.qxdm.appid:{'340101': 'hefei'}}}")
    private Map<String, String> appidMap;

    @Resource(name = "hfyctbPostService")
    private HfYctbInterfaceServiceImpl hfYctbInterfaceService;


    @Autowired
    private StorageClientMatcher storageClient;

    @Autowired
    CaqzConfig caqzConfig;

    private static List<Object> YCTB_SPLY_LIST = new ArrayList<>(2);

    static {
        YCTB_SPLY_LIST.add(CommonConstantUtils.SPLY_YCTB);
        YCTB_SPLY_LIST.add(CommonConstantUtils.SPLY_YCTB_SS);
    }

    // ca签章，查找pdf关键字的位置，进行盖章
    private static final String qzKeyWord = "不动产登记中心";

    /**
     * 添加税务明细和缴费明细
     *
     * @param gzlslid
     * @return
     */
    @Override
    public YctbCommonResponse addTaxAndPayInfo(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcXmDO> bdcXmList = bdcXmMapper.queryBdcXmList(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcXmList)) {
                int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcXmList);
                if (CommonConstantUtils.LCLX_PLZH.equals(lclx) || CommonConstantUtils.LCLX_ZH.equals(lclx)) {
                    return YctbCommonResponse.fail("组合流程不支持推送");
                }
                BdcSfSsxxQO bdcSfSsxxQO = new BdcSfSsxxQO();
                bdcSfSsxxQO.setXmid(bdcXmList.get(0).getXmid());
                bdcSfSsxxQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                BdcSfSsxxDTO qlrSfxx = bdcSlSfxxFeignService.queryBdcSfSsxxDTO(bdcSfSsxxQO);
                LOGGER.info("qlrSfxx:{}", JSON.toJSONString(qlrSfxx));
                String dh = "";
                for (BdcSlSqrDO bdcSlSqrDO : qlrSfxx.getBdcSlSqrDOS()) {
                    if (StringUtils.isNotBlank(bdcSlSqrDO.getDh())) {
                        dh = bdcSlSqrDO.getDh();
                        break;
                    }
                }
                bdcSfSsxxQO.setSqrlb(CommonConstantUtils.QLRLB_YWR);
                BdcSfSsxxDTO ywrSfxx = bdcSlSfxxFeignService.queryBdcSfSsxxDTO(bdcSfSsxxQO);
                LOGGER.info("ywrSfxx:{}", JSON.toJSONString(ywrSfxx));
                List<YctbJyskb> jyskbList = new ArrayList<>(bdcXmList.size());
                YctbAddTaxInfoRequest yctbAddTaxInfoRequest = new YctbAddTaxInfoRequest();
                YctbTaxDetail taxDetail = YctbTaxDetail.YctbTaxDetailBuilder.anYctbTaxDetail().withYwh(bdcXmList.get(0).getSpxtywh()).withJyuuid(bdcXmList.get(0).getSlbh()).build();
                //组织税务信息
                if (CollectionUtils.isNotEmpty(qlrSfxx.getBdcSwxxDTOS())) {
                    initTaxInfo(bdcXmList, qlrSfxx, jyskbList, "1");
                }
                if (CollectionUtils.isNotEmpty(ywrSfxx.getBdcSwxxDTOS())) {
                    initTaxInfo(bdcXmList, ywrSfxx, jyskbList, "0");
                }
                taxDetail.setJyskbList(jyskbList);
                yctbAddTaxInfoRequest.setTaxDetail(taxDetail);
                //组织收费信息
                try {
                    if (CollectionUtils.isNotEmpty(qlrSfxx.getBdcSfxxDTOS())) {
                        //没有组合流程，只有一条收费信息
                        BdcSfxxDTO sfxx = qlrSfxx.getBdcSfxxDTOS().get(0);
                        if (sfxx.getBdcSlSfxxDO() == null) {
                            return YctbCommonResponse.fail("无收费信息请核查");
                        }
                        YctbPaymentInfo paymentInfo = YctbPaymentInfo.YctbPaymentInfoBuilder.anYctbPaymentInfo().withPaymentstatus(sfxx.getBdcSlSfxxDO().getSfzt() != null ? Integer.toString(sfxx.getBdcSlSfxxDO().getSfzt()) : null)
                                .withSqr(sfxx.getBdcSlSfxxDO().getJfrxm())
                                .withSqrlxdh(dh)
                                .withSszje(sfxx.getBdcSlSfxxDO().getHj() != null ? Double.toString(sfxx.getBdcSlSfxxDO().getHj()) : null)
                                .withYwh(bdcXmList.get(0).getSpxtywh())
                                .withZdzl(bdcXmList.get(0).getZl()).build();
                        List<YctbJfxx> jfList = new ArrayList<>(bdcXmList.size());
                        for (BdcSlSfxmDO bdcSlSfxmDO : sfxx.getBdcSlSfxmDOS()) {
                            Matcher m = null;
                            if (StringUtils.isNotBlank(bdcSlSfxmDO.getSfxmbz())) {
                                String regEx = "[^0-9]";
                                Pattern p = Pattern.compile(regEx);
                                m = p.matcher(bdcSlSfxmDO.getSfxmbz());
                            }
                            jfList.add(YctbJfxx.YctbJfxxBuilder.anYctbJfxx()
                                    .withJfjs(m != null ? m.replaceAll("").trim() : null)
                                    .withJfxmdm_mc(bdcSlSfxmDO.getSfxmbz())
//                                    .withJfxmmc(bdcSlSfxmDO.getSfxmdm())
                                    .withOid(bdcSlSfxmDO.getXh() != null ? Integer.toString(bdcSlSfxmDO.getXh()) : null)
//                                    .withSflx(bdcSlSfxmDO.getJffs())
                                    .withShul(bdcSlSfxmDO.getSl() != null ? Double.toString(bdcSlSfxmDO.getSl()) : null)
                                    .withSsdxje(ChangeMoneyToCnUtils.convert(ChangeMoneyToCnUtils.moneyFormat(bdcSlSfxmDO.getSsje() != null ? bdcSlSfxmDO.getSsje().toString() : "0")))
                                    .withSsje(bdcSlSfxmDO.getSsje() != null ? Double.toString(bdcSlSfxmDO.getSsje()) : null)
                                    .withYsje(bdcSlSfxmDO.getYsje() != null ? Double.toString(bdcSlSfxmDO.getYsje()) : null)
                                    .withYwh(bdcXmList.get(0).getSpxtywh()).build());
                        }
                        paymentInfo.setJfList(jfList);
                        yctbAddTaxInfoRequest.setPayMent(paymentInfo);
                    }
                } catch (Exception e) {
                    LOGGER.info("组织缴费信息异常:", e);
                    return YctbCommonResponse.fail("组织缴费信息异常:" + e.getMessage());
                }
                yctbAddTaxInfoRequest.setQxdm(bdcXmList.get(0).getQxdm());
                LOGGER.info("yctbAddTaxInfoRequest:{}", JSON.toJSONString(yctbAddTaxInfoRequest));
                Object response = exchangeInterfaceRestController.requestInterface("yctb_addTaxInfo", yctbAddTaxInfoRequest);
                LOGGER.info("合肥一窗通办-添加税务明细和缴费明细接口返回结果：{}", JSON.toJSONString(response));
                if (response != null) {
                    YctbCommonResponse<Object> yctbResponseDTO = JSON.parseObject(JSON.toJSONString(response), new TypeReference<YctbCommonResponse<Object>>() {
                    });
                    if (yctbResponseDTO.getCode().equals(200)) {
                        return YctbCommonResponse.ok();
                    } else {
                        throw new MissingArgumentException("调用合肥一窗通办-添加税务明细和缴费明细接口接口失败:" + JSON.toJSONString(yctbResponseDTO));
                    }
                } else {
                    return YctbCommonResponse.fail();
                }
            } else {
                return YctbCommonResponse.fail("未查询到相关项目信息");
            }
        } else {
            return YctbCommonResponse.fail("入参gzlslid为空");
        }
    }

    private void initTaxInfo(List<BdcXmDO> bdcXmList, BdcSfSsxxDTO qlrSfxx, List<YctbJyskb> jyskbList, String zrfcsfbz) {
//        List<Map> zdxxList = bdcSlZdFeignService.queryBdcSlzd("bdc_sl_zd_swzl");
        qlrSfxx.getBdcSwxxDTOS().forEach(bdcSwxxDTO -> {
            if (bdcSwxxDTO.getBdcSlHsxxDO() == null) {
                throw new RuntimeException("无核税信息请核查");
            }
            if (CollectionUtils.isNotEmpty(bdcSwxxDTO.getBdcSlHsxxMxDOList())) {
                bdcSwxxDTO.getBdcSlHsxxMxDOList().forEach(bdcSlHsxxMxDO -> {
//                    String mxlxmc = "";
//                    if (CollectionUtils.isNotEmpty(zdxxList)) {
//                        for (Map zdxx : zdxxList) {
//                            if (zdxx != null && zdxx.get("DM") != null && zdxx.get("MC") != null && zdxx.get("DM").equals(bdcSlHsxxMxDO.getMxzl())) {
//                                mxlxmc = (String) zdxx.get("MC");
//                            }
//                        }
//                    }
                    YctbJyskb taxInfo = YctbJyskb.YctbJyskbBuilder.anYctbJyskb().withFwuuid(bdcXmList.get(0).getSlbh())
                            .withJsje(bdcSwxxDTO.getBdcSlHsxxDO().getHdjsjg() != null ? Double.toString(bdcSwxxDTO.getBdcSlHsxxDO().getHdjsjg()) : null)
//                            .withJyfe("")//暂无 缴费份额
                            .withNsr_id(bdcSlHsxxMxDO.getHsxxid())//纳税人id 合肥现场说直接传表id
                            .withNsrsbh(bdcSwxxDTO.getBdcSlHsxxDO().getNsrsbh())
                            .withSl(bdcSlHsxxMxDO.getSl())
                            .withYnse(bdcSlHsxxMxDO.getYnse() != null ? Double.toString(bdcSlHsxxMxDO.getYnse()) : null)
                            .withZrfcsfbz(zrfcsfbz)
                            .withZsxm_dm(bdcSlHsxxMxDO.getMxzl())
                            .withZsxmmc(bdcSlHsxxMxDO.getZsxm()).build();
                    jyskbList.add(taxInfo);
                });
            }
        });
    }

    /**
     * 获取纳税缴费支付地址
     *
     * @param yctbGetPayUrlRequest
     * @return YctbCommonResponse
     */
    @Override
    public YctbCommonResponse getPayUrl(YctbGetPayUrlRequest yctbGetPayUrlRequest) {
        if (StringUtils.isNotBlank(yctbGetPayUrlRequest.getType()) && StringUtils.isNotBlank(yctbGetPayUrlRequest.getYwh())) {
            //查询项目信息
            Example xmExample = new Example(BdcXmDO.class);
            xmExample.createCriteria().andEqualTo("spxtywh", yctbGetPayUrlRequest.getYwh()).andIn("sply", YCTB_SPLY_LIST);
            List<BdcXmDO> bdcXmDOS = entityMapper.selectByExample(xmExample);
            if (CollectionUtils.isEmpty(bdcXmDOS) || StringUtils.isBlank(bdcXmDOS.get(0).getGzlslid())) {
                return YctbCommonResponse.fail("未查询到相关的项目信息");
            }
            List<YctbTaxInfo> list = new ArrayList<>(1);
            list.add(YctbTaxInfo.YctbTaxInfoBuilder.anYctbTaxInfo().withFwuuid(bdcXmDOS.get(0).getSlbh()).withYwh(yctbGetPayUrlRequest.getYwh()).build());
            YctbGetPayUrlResponse yctbGetPayUrlResponse = YctbGetPayUrlResponse.YctbGetPayUrlResponseBuilder.anYctbGetPayUrlResponse().withTaxpaymenturl(payUrl + "&slbh=" + bdcXmDOS.get(0).getSlbh() + "&sfyj=0").build();
            yctbGetPayUrlResponse.setTaxList(list);
            return YctbCommonResponse.ok(yctbGetPayUrlResponse);
        }
        return YctbCommonResponse.fail("必填参数为空");
    }

    /**
     * 获取缴费状态
     *
     * @param yctbGetTaxPayRequest
     * @return
     */
    @Override
    public YctbCommonResponse getTaxPaymentState(YctbGetTaxPayRequest yctbGetTaxPayRequest) {
        if (StringUtils.isNotBlank(yctbGetTaxPayRequest.getYwh())&& StringUtils.isNotBlank(yctbGetTaxPayRequest.getType())) {
            //查询项目信息
            Example xmExample = new Example(BdcXmDO.class);
            xmExample.createCriteria().andEqualTo("spxtywh", yctbGetTaxPayRequest.getYwh()).andIn("sply", YCTB_SPLY_LIST);
            List<BdcXmDO> bdcXmList = entityMapper.selectByExample(xmExample);
            if (CollectionUtils.isEmpty(bdcXmList) || StringUtils.isBlank(bdcXmList.get(0).getGzlslid())) {
                return YctbCommonResponse.fail("未查询到相关的项目信息");
            }
            JSONObject response = new JSONObject();
            BdcSfSsxxQO bdcSfSsxx = new BdcSfSsxxQO();
            bdcSfSsxx.setXmid(bdcXmList.get(0).getXmid());
            bdcSfSsxx.setSqrlb(CommonConstantUtils.QLRLB_QLR);
            BdcSfSsxxDTO qlrSfxx = bdcSlSfxxFeignService.queryBdcSfSsxxDTO(bdcSfSsxx);
            bdcSfSsxx.setSqrlb(CommonConstantUtils.QLRLB_YWR);
            BdcSfSsxxDTO ywrSfxx = bdcSlSfxxFeignService.queryBdcSfSsxxDTO(bdcSfSsxx);
            if ("1".equals(yctbGetTaxPayRequest.getType())) {
                AtomicBoolean wsbs = new AtomicBoolean(true);
                if (CollectionUtils.isNotEmpty(qlrSfxx.getBdcSwxxDTOS())) {
                    qlrSfxx.getBdcSwxxDTOS().forEach(bdcSwxxDTO -> {
                        if (bdcSwxxDTO.getBdcSlHsxxDO().getWszt() != null && bdcSwxxDTO.getBdcSlHsxxDO().getWszt() == 1) {
                            wsbs.set(false);
                        }
                    });
                }
                if (CollectionUtils.isNotEmpty(ywrSfxx.getBdcSwxxDTOS())) {
                    ywrSfxx.getBdcSwxxDTOS().forEach(bdcSwxxDTO -> {
                        if (bdcSwxxDTO.getBdcSlHsxxDO().getWszt() != null && bdcSwxxDTO.getBdcSlHsxxDO().getWszt() == 1) {
                            wsbs.set(false);
                        }
                    });
                }
                if (wsbs.get()) {
                    response.put("taxstate", "01");
                } else {
                    response.put("taxstate", "02");
                }
            } else if ("2".equals(yctbGetTaxPayRequest.getType())) {
                if (StringUtils.isNotBlank(qlrSfxx.getJfzt()) && "1".equals(qlrSfxx.getJfzt())) {
                    response.put("paystate", "01");
                } else {
                    response.put("paystate", "02");
                }
            }
            return YctbCommonResponse.ok(response);
        }
        return YctbCommonResponse.fail("必填参数为空");
    }

    /**
     * 合肥_登记派件分发
     *
     * @param yctbWwcjRequest
     * @return
     */
    @Override
    public YctbCommonResponse saveApplicationInfo(YctbWwcjRequest yctbWwcjRequest) {
        //WwsqCjBdcXmRequestDTO
        WwsqCjBdcXmRequestDTO wwsqCjBdcXmRequestDTO = new WwsqCjBdcXmRequestDTO();
        exchangeDozerMapper.map(yctbWwcjRequest, wwsqCjBdcXmRequestDTO, "initWwsqCjBdcXmRequestDTO");
        //InitRequestData   BdcSlJbxxDO
        BdcSlxxDTO bdcSlxxDTO = new BdcSlxxDTO();
        //初始化项目基本信息
        BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();
        exchangeDozerMapper.map(yctbWwcjRequest, bdcSlJbxxDO, "initBdcSlJbxxDO");
        LOGGER.info("==================>{}", bdcSlJbxxDO.getGzldyid());
        YctbWwcjSlInfo acceptance = yctbWwcjRequest.getAcceptanceList();
        if (StringUtils.isNotBlank(bdcSlJbxxDO.getGzldyid()) && bdcSlJbxxDO.getGzldyid().contains("{")) {
            JSONObject calyConfig = JSON.parseObject(StringEscapeUtils.unescapeJava(bdcSlJbxxDO.getGzldyid()));
            if (StringUtils.isNotBlank(calyConfig.getString(acceptance.getCqly()))) {
                bdcSlJbxxDO.setGzldyid(calyConfig.getString(acceptance.getCqly()));
            } else {
                LOGGER.info("获取工作流实例id异常:{}", bdcSlJbxxDO.getGzldyid());
                return YctbCommonResponse.fail("获取工作流实例id异常:" + yctbWwcjRequest.getDjlx());
            }
        }
        //InitRequestData   BdcSlXmDTO
        if (acceptance == null) {
            return YctbCommonResponse.fail("缺少受理参数");
        }
        bdcSlxxDTO.setBdcSlJbxx(bdcSlJbxxDO);
        //初始化项目信息
        List<BdcSlXmDTO> bdcSlXmList = new ArrayList<>();
        for (YctbWwcjDyInfo yctbWwcjDyInfo : yctbWwcjRequest.getHouseRightList()) {
            BdcSlXmDTO bdcSlXmDTO = new BdcSlXmDTO();
            //上一手项目处理
            List<BdcSlXmLsgxDO> bdcSlXmLsgxDOS = new ArrayList<>();
            if (StringUtil.isBlank(yctbWwcjDyInfo.getBdcqzh()) || StringUtil.isBlank(yctbWwcjDyInfo.getBdcdyh())) {
                return YctbCommonResponse.fail("bdcqzh和bdcdyh入参不全");
            }
            BdcQszmQO bdcQszmQO = new BdcQszmQO();
            bdcQszmQO.setBdcdyh(yctbWwcjDyInfo.getBdcqzh());
            bdcQszmQO.setBdcdyh(yctbWwcjDyInfo.getBdcdyh());
            List<BdcXmDO> bdcXmDOS = bdcdjMapper.queryXmxxByBdcqzhAndBdcDyh(bdcQszmQO);
            if (CollectionUtils.isEmpty(bdcXmDOS)) {
                return YctbCommonResponse.fail("bdcqzh和bdcdyh未查询到相关的项目数据");
            }
            Date djsj = null;
            for (BdcXmDO bdcXmDO : bdcXmDOS) {
                BdcSlXmLsgxDO bdcSlXmLsgxDO = new BdcSlXmLsgxDO();
                bdcSlXmLsgxDO.setYxmid(bdcXmDO.getXmid());
                djsj = bdcXmDO.getDjsj();
                bdcSlXmLsgxDOS.add(bdcSlXmLsgxDO);
            }
            LOGGER.info("wahaha============>{}", JSON.toJSONString(bdcSlXmLsgxDOS));
            bdcSlXmDTO.setBdcSlXmLsgxList(bdcSlXmLsgxDOS);
            BdcSlJyxxDO bdcSlJyxxDO = new BdcSlJyxxDO();
            if (StringUtils.isNotBlank(acceptance.getQdjg())) {
                bdcSlJyxxDO.setJyje(parseDouble(acceptance.getQdjg()) / 10000);
            }
            if (StringUtils.isNotBlank(acceptance.getJyrq())) {
                bdcSlJyxxDO.setHtdjsj(DateUtils.formatDate(acceptance.getJyrq()));
            }
            bdcSlJyxxDO.setFczfzsj(djsj);
            bdcSlXmDTO.setBdcSlJyxxDO(bdcSlJyxxDO);

            BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();
            exchangeDozerMapper.map(acceptance, bdcSlXmDO, "initBdcSlxxDTO");
            if (acceptance.getSqfbcz() != null && acceptance.getSqfbcz() == 0) {
                bdcSlXmDO.setSqfbcz(1);
            } else if (acceptance.getSqfbcz() != null && acceptance.getSqfbcz() == 1) {
                bdcSlXmDO.setSqfbcz(0);
            }
            bdcSlXmDO.setBdcdyh(yctbWwcjDyInfo.getBdcdyh());
            bdcSlXmDTO.setBdcSlXm(bdcSlXmDO);
            //初始化申请人信息
            boolean familyFlag = false;
            DsfSlxxDTO dsfSlxxDTO = new DsfSlxxDTO();
            dsfSlxxDTO.setSply(CommonConstantUtils.SPLY_YCTB.toString());
            dsfSlxxDTO.setSpxtywh(yctbWwcjRequest.getYwh());
            if (StringUtil.isNotBlank(acceptance.getQdjg())) {
                dsfSlxxDTO.setJyje(Double.parseDouble(acceptance.getQdjg()) / 10000);
            }
            bdcSlXmDTO.setDsfSlxxDTO(dsfSlxxDTO);
            if (CollectionUtils.isNotEmpty(yctbWwcjRequest.getJmssbdlbList())) {
                wwsqCjBdcXmRequestDTO.setSfss(true);
                dsfSlxxDTO.setSfss("1");
                Map<String, List<YctbWwcjJmsbjtcyInfo>> jtcy = new HashMap<>();
                Map<String, List<YctbWwcjJmsbjtcyzvInfo>> zvcy = new HashMap<>();
                if (CollectionUtils.isNotEmpty(yctbWwcjRequest.getJmssbdlbList())) {
                    for (YctbWwcjJmsbjtcyInfo yctbWwcjJmsbjtcyInfo : yctbWwcjRequest.getJmssbdlbList()) {
                        if ("0".equals(yctbWwcjJmsbjtcyInfo.getIschild()) || "0".equals(yctbWwcjJmsbjtcyInfo.getIspo())) {
                            familyFlag = true;
                        }
                        if (jtcy.containsKey(yctbWwcjJmsbjtcyInfo.getIdcard())) {
                            jtcy.get(yctbWwcjJmsbjtcyInfo.getIdcard()).add(yctbWwcjJmsbjtcyInfo);
                        } else {
                            List<YctbWwcjJmsbjtcyInfo> list = new ArrayList<>();
                            list.add(yctbWwcjJmsbjtcyInfo);
                            jtcy.put(yctbWwcjJmsbjtcyInfo.getIdcard(), list);
                        }
                    }
                    for (YctbWwcjJmsbjtcyzvInfo yctbWwcjJmsbjtcyzvInfo : yctbWwcjRequest.getJmssbdchildlbList()) {
                        if (zvcy.containsKey(yctbWwcjJmsbjtcyzvInfo.getIdcard())) {
                            zvcy.get(yctbWwcjJmsbjtcyzvInfo.getIdcard()).add(yctbWwcjJmsbjtcyzvInfo);
                        } else {
                            List<YctbWwcjJmsbjtcyzvInfo> list = new ArrayList<>();
                            list.add(yctbWwcjJmsbjtcyzvInfo);
                            zvcy.put(yctbWwcjJmsbjtcyzvInfo.getIdcard(), list);
                        }
                    }
                }
                if (familyFlag) {
                    List<BdcSlSqrDTO> bdcSlSqrDTOList = new ArrayList<>(yctbWwcjRequest.getApplicantList().size());
                    List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>(yctbWwcjRequest.getApplicantList().size());
                    yctbWwcjRequest.getApplicantList().forEach(yctbWwcjQlrInfo -> {
                        BdcSlSqrDTO bdcSlSqrDTO = new BdcSlSqrDTO();
                        BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                        exchangeDozerMapper.map(yctbWwcjQlrInfo, bdcSlSqrDO, "initBdcSlSqrDO");
                        AtomicInteger jtmwwyzz = new AtomicInteger();
                        AtomicBoolean jtmwwyzzFlag = new AtomicBoolean(true);
                        jtcy.get(yctbWwcjQlrInfo.getQlrzjbh()).forEach(yctbWwcjJmsbjtcyInfo -> {
                            if (StringUtil.isNotBlank(yctbWwcjJmsbjtcyInfo.getIsmwwy()) && "0".equals(yctbWwcjJmsbjtcyInfo.getIsmwwy())) {
                                jtmwwyzz.set(1);
                                jtmwwyzzFlag.set(true);
                            } else if (StringUtil.isNotBlank(yctbWwcjJmsbjtcyInfo.getIsmwwy()) && "1".equals(yctbWwcjJmsbjtcyInfo.getIsmwwy())) {
                                jtmwwyzz.set(0);
                                jtmwwyzzFlag.set(true);
                            } else {
                                jtmwwyzzFlag.set(false);
                            }
                            if (StringUtil.isNotBlank(yctbWwcjJmsbjtcyInfo.getZftslx())) {
                                bdcSlSqrDO.setSbfwtc(yctbWwcjJmsbjtcyInfo.getZftslx());
                            }
                        });
                        if (jtmwwyzzFlag.get()) {
                            bdcSlSqrDO.setJtmwwyzz(jtmwwyzz.intValue());
                        }
                        if (CollectionUtils.isNotEmpty(yctbWwcjQlrInfo.getGxrlist())) {
                            yctbWwcjQlrInfo.getGxrlist().forEach(yctbWwcjGxrInfo -> {
                                if ("0".equals(yctbWwcjGxrInfo.getLxdh())) {
                                    exchangeDozerMapper.map(yctbWwcjGxrInfo, bdcSlSqrDO, "initFrxx");
                                }
                                if ("1".equals(yctbWwcjGxrInfo.getLxdh())) {
                                    exchangeDozerMapper.map(yctbWwcjGxrInfo, bdcSlSqrDO, "initDlrxx");
                                }
                            });
                        }
//                        LOGGER.info("===========wahaha:{}",JSON.toJSONString(bdcSlSqrDO));
                        bdcSlSqrDOList.add(bdcSlSqrDO);
                        bdcSlSqrDTO.setBdcSlSqrDO(bdcSlSqrDO);
                        List<BdcSlJtcyDO> bdcSlJtcyDOList = new ArrayList<>();
                        if (MapUtils.isNotEmpty(jtcy) && jtcy.containsKey(yctbWwcjQlrInfo.getQlrzjbh())) {
                            List<YctbWwcjJmsbjtcyInfo> list = jtcy.get(yctbWwcjQlrInfo.getQlrzjbh());
                            list.forEach(yctbWwcjJmsbjtcyInfo -> {
                                BdcSlJtcyDO bdcSlJtcyDO = new BdcSlJtcyDO();
                                if ("0".equals(yctbWwcjJmsbjtcyInfo.getIspo())) {
                                    bdcSlJtcyDO.setJtcymc(yctbWwcjJmsbjtcyInfo.getPoname());
                                    bdcSlJtcyDO.setZjh(yctbWwcjJmsbjtcyInfo.getPoidcard());
                                    bdcSlJtcyDO.setYsqrgx("配偶");
                                    bdcSlJtcyDOList.add(bdcSlJtcyDO);
                                }
                            });
                        }
                        if (MapUtils.isNotEmpty(zvcy) && zvcy.containsKey(yctbWwcjQlrInfo.getQlrzjbh())) {
                            List<YctbWwcjJmsbjtcyzvInfo> list = zvcy.get(yctbWwcjQlrInfo.getQlrzjbh());
                            list.forEach(yctbWwcjJmsbjtcyzvInfo -> {
                                BdcSlJtcyDO bdcSlJtcyDO = new BdcSlJtcyDO();
                                bdcSlJtcyDO.setJtcymc(yctbWwcjJmsbjtcyzvInfo.getChildname());
                                bdcSlJtcyDO.setZjh(yctbWwcjJmsbjtcyzvInfo.getChildidcard());
                                bdcSlJtcyDO.setYsqrgx("未成年子女");
                                bdcSlJtcyDOList.add(bdcSlJtcyDO);
                            });
                        }
                        if (CollectionUtils.isNotEmpty(bdcSlJtcyDOList)) {
                            bdcSlSqrDTO.setBdcSlJtcyDOList(bdcSlJtcyDOList);
                        }
                        bdcSlSqrDTOList.add(bdcSlSqrDTO);
                        bdcSlXmDTO.setBdcSlSqrDTOList(bdcSlSqrDTOList);
                        bdcSlXmDTO.setBdcSlSqrDOList(bdcSlSqrDOList);
                    });
                } else {
                    List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>(yctbWwcjRequest.getApplicantList().size());
                    yctbWwcjRequest.getApplicantList().forEach(yctbWwcjQlrInfo -> {
                        BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                        exchangeDozerMapper.map(yctbWwcjQlrInfo, bdcSlSqrDO, "initBdcSlSqrDO");
                        AtomicInteger jtmwwyzz = new AtomicInteger();
                        AtomicBoolean jtmwwyzzFlag = new AtomicBoolean(true);
                        jtcy.get(yctbWwcjQlrInfo.getQlrzjbh()).forEach(yctbWwcjJmsbjtcyInfo -> {
                            if (StringUtil.isNotBlank(yctbWwcjJmsbjtcyInfo.getIsmwwy()) && "0".equals(yctbWwcjJmsbjtcyInfo.getIsmwwy())) {
                                jtmwwyzz.set(1);
                                jtmwwyzzFlag.set(true);
                            } else if (StringUtil.isNotBlank(yctbWwcjJmsbjtcyInfo.getIsmwwy()) && "1".equals(yctbWwcjJmsbjtcyInfo.getIsmwwy())) {
                                jtmwwyzz.set(0);
                                jtmwwyzzFlag.set(true);
                            } else {
                                jtmwwyzzFlag.set(false);
                            }
                            if (StringUtil.isNotBlank(yctbWwcjJmsbjtcyInfo.getZftslx())) {
                                bdcSlSqrDO.setSbfwtc(yctbWwcjJmsbjtcyInfo.getZftslx());
                            }
                        });
                        if (jtmwwyzzFlag.get()) {
                            bdcSlSqrDO.setJtmwwyzz(jtmwwyzz.intValue());
                        }
                        if (CollectionUtils.isNotEmpty(yctbWwcjQlrInfo.getGxrlist())) {
                            yctbWwcjQlrInfo.getGxrlist().forEach(yctbWwcjGxrInfo -> {
                                if ("0".equals(yctbWwcjGxrInfo.getLxdh())) {
                                    exchangeDozerMapper.map(yctbWwcjGxrInfo, bdcSlSqrDO, "initFrxx");
                                }
                                if ("1".equals(yctbWwcjGxrInfo.getLxdh())) {
                                    exchangeDozerMapper.map(yctbWwcjGxrInfo, bdcSlSqrDO, "initDlrxx");
                                }
                            });
                        }
                        bdcSlSqrDOList.add(bdcSlSqrDO);
                        bdcSlXmDTO.setBdcSlSqrDOList(bdcSlSqrDOList);
                    });
                }
            } else {
                List<BdcSlSqrDO> bdcSlSqrDOList = new ArrayList<>(yctbWwcjRequest.getApplicantList().size());
                yctbWwcjRequest.getApplicantList().forEach(yctbWwcjQlrInfo -> {
                    BdcSlSqrDO bdcSlSqrDO = new BdcSlSqrDO();
                    exchangeDozerMapper.map(yctbWwcjQlrInfo, bdcSlSqrDO, "initBdcSlSqrDO");
                    if (CollectionUtils.isNotEmpty(yctbWwcjQlrInfo.getGxrlist())) {
                        yctbWwcjQlrInfo.getGxrlist().forEach(yctbWwcjGxrInfo -> {
                            if ("0".equals(yctbWwcjGxrInfo.getLxdh())) {
                                exchangeDozerMapper.map(yctbWwcjGxrInfo, bdcSlSqrDO, "initFrxx");
                            }
                            if ("1".equals(yctbWwcjGxrInfo.getLxdh())) {
                                exchangeDozerMapper.map(yctbWwcjGxrInfo, bdcSlSqrDO, "initDlrxx");
                            }
                        });
                    }
                    bdcSlSqrDOList.add(bdcSlSqrDO);
                    bdcSlXmDTO.setBdcSlSqrDOList(bdcSlSqrDOList);
                });
            }
            bdcSlXmList.add(bdcSlXmDTO);
        }
        //FjclDTO
        //初始化附件信息
        List<YctbWwcjSjdInfo> receiveList = yctbWwcjRequest.getReceiveList();
        if (CollectionUtils.isNotEmpty(receiveList)) {
            redisUtils.addStringValue(CommonConstantUtils.REDIS_HEFEI_YCTB_WWSQ_FJXX_PREFIX + yctbWwcjRequest.getYwh(), JSON.toJSONString(receiveList));
        }
        bdcSlxxDTO.setBdcSlXmList(bdcSlXmList);
        wwsqCjBdcXmRequestDTO.setBdcSlxxDTO(bdcSlxxDTO);

        //wwsqCjBdcXm  WwsqCjBdcXmRequestDTO
        try {
            WwsqCjBdcXmResponseDTO responseDTO = bdcSlFeignService.wwsqCjBdcXm(wwsqCjBdcXmRequestDTO);
            if (responseDTO != null && CollectionUtils.isNotEmpty(responseDTO.getGzyzList())) {
                StringBuilder msgSb = new StringBuilder();
                List<Map<String, Object>> yzResult = responseDTO.getGzyzList();
                for (Map<String, Object> map : yzResult) {
                    String bdcdyh = StringUtils.defaultString(MapUtils.getString(map, "bdcdyh"));
                    String yzmsg = StringUtils.defaultString(MapUtils.getString(map, "msg"));
                    msgSb.append(bdcdyh).append(yzmsg).append(",");
                }
                String msg = msgSb.toString();
                if (msg.endsWith(",")) {
                    msg = msg.substring(0, msg.length() - 1);
                }
                return YctbCommonResponse.fail(msg);
            } else if (responseDTO == null) {
                return YctbCommonResponse.fail("创建失败");
            }
        } catch (Exception e) {
            return YctbCommonResponse.fail(e.getMessage());
        }
        return YctbCommonResponse.ok();
    }

    /**
     * 合肥_在线查档
     *
     * @param yctbZxcdRequest
     * @return
     */
    @Override
    public YctbCommonResponse yctbZxcd(YctbZxcdRequest yctbZxcdRequest) {
        try {
            // 封装参数
            Map<String, List<Map>> paramMap = new HashMap<>(1);
            List<Map> bdcdyhListMap = new ArrayList<>(1);
            OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
            String fileName = UUIDGenerator.generate16();
            //获取项目信息 [{"bdcdyh":"1","qszt":"1","gzlslid":"1"},{"bdcdyh":"1","qszt":"1","gzlslid":"1"}]
            String qxdmConfig = qxdmMap.get(yctbZxcdRequest.getQxdm());
            yctbZxcdRequest.setQxdmConfig(qxdmConfig);
            List<BdcQszmQO> list = bdcdjMapper.queryXmxxListByQlrAndZlAndBdcdyh(yctbZxcdRequest);
            if (CollectionUtils.isEmpty(list)) {
                LOGGER.info("未查到相关数据");
                Map<String, Object> mapTemp = new HashMap<>(1);
                mapTemp.put("bdcdyh", "");
                mapTemp.put("qszt", "");
                mapTemp.put("gzlslid", "");
                mapTemp.put("bdcqzh", "");
                mapTemp.put("cxh", "");
                mapTemp.put("xmid", "");
                mapTemp.put("bdclx", "");
                mapTemp.put("jbr", "");
                mapTemp.put("qlr", yctbZxcdRequest.getName());
                mapTemp.put("qxdm", yctbZxcdRequest.getQxdm());
                bdcdyhListMap.add(mapTemp);

            } else {
                BdcQszmDyDTO bdcQszmDyDTO = bdcBzbZmFeignService.saveBdcQszmParamToRedis(list);
                if (StringUtils.isBlank(bdcQszmDyDTO.getRedisKey())) {
                    throw new NullPointerException("查询子系统：打印权属证明没有参数信息");
                }

                String json = redisUtils.getStringValue(bdcQszmDyDTO.getRedisKey());
                List<BdcQszmQO> qszmQOList = JSONArray.parseArray(json, BdcQszmQO.class);


                if (CollectionUtils.isEmpty(qszmQOList)) {
                    throw new NullPointerException("查询子系统：打印权属证明没有参数信息");
                }

                for (BdcQszmQO bdcQszmQO : qszmQOList) {
                    Map<String, Object> mapTemp = new HashMap<>(1);
                    mapTemp.put("bdcdyh", bdcQszmQO.getBdcdyh());
                    mapTemp.put("qszt", bdcQszmQO.getQszt());
                    mapTemp.put("gzlslid", bdcQszmQO.getGzlslid());
                    mapTemp.put("bdcqzh", bdcQszmQO.getBdcqzh());
                    mapTemp.put("cxh", bdcQszmQO.getCxh());
                    mapTemp.put("xmid", bdcQszmQO.getXmid());
                    mapTemp.put("bdclx", bdcQszmQO.getBdclx());
                    mapTemp.put("jbr", bdcQszmQO.getUsername());
                    mapTemp.put("qlr", yctbZxcdRequest.getName());
                    mapTemp.put("qxdm",yctbZxcdRequest.getQxdm());

                    bdcdyhListMap.add(mapTemp);
                }
            }
            if ("1".equals(yctbZxcdRequest.getCxlx())) {
                paramMap.put(qlrDylx, bdcdyhListMap);
                pdfExportDTO.setModelName(printPath);
            } else {
                paramMap.put(qtrDylx, bdcdyhListMap);
                pdfExportDTO.setModelName(printPathCxlx2);
            }
            String xmlData = bdcPrintFeignService.print(paramMap);
            LOGGER.info("------xmlData:{}", xmlData);
            pdfExportDTO.setFileName(fileName);
            pdfExportDTO.setXmlData(xmlData);
            OutputStream outputStream = null;
            LOGGER.info("生成pdf文件");
            String pdfFile = pdfController.generatePdfFile(pdfExportDTO);
            LOGGER.info("生成pdf文件的路径，pdfFile：{}",pdfFile);
            String pdfBinary = "";
            try {
                // 电子签章  合肥使用
                if (CommonConstantUtils.SYSTEM_VERSION_HF.equals(dataVersion)) {
                    LOGGER.info("在线查档，开始电子签章,pdfFile:{},qxdm:{}", pdfFile, yctbZxcdRequest.getQxdm());
                    pdfBinary = fwdPdfQzInterface(pdfFile, yctbZxcdRequest.getQxdm());

                } else {
                    LOGGER.info("在线查档，不用电子签章");
                    File file = new File(pdfFile);
                    pdfBinary = Base64Utils.getPDFBinary(file);
                }
                Map<String, String> result = new HashMap<>(2);
                result.put("result", pdfBinary);
                // 返回体需要加密
                if ("false".equals(jmkg)) {
                    // 获取公钥
                    YctbSginpriKeyInfo gyInfo =  hfYctbInterfaceService.getSginpriKeyInfo(yctbZxcdRequest.getQxdm(),2);
                    String gyStr = gyInfo.getPubkey();
                    JSONObject json = JSON.parseObject(JSON.toJSONString(result));
                    String data = json.toJSONString();
                    String jmStr = SM2Util.encryptedMealReturn(data, gyStr);
                    return YctbCommonResponse.ok(jmStr);
                } else {
                    return YctbCommonResponse.ok(result);
                }
            } catch (Exception e) {
                LOGGER.error("系统导出PDF报错:", e);
                return YctbCommonResponse.fail(e.getMessage());
            } finally {
                IOUtils.closeQuietly(outputStream);
                if (null != pdfFile) {
                    File file = new File(pdfFile);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }


        } catch (Exception e) {
            LOGGER.error("在线查档异常:", e);
            return YctbCommonResponse.fail(e.getMessage());
        }
    }

    /**
     * pdf文件进行ca签章
     * 
     * @param path 文件路径
     * @return pdf的base64字符串
     * @throws IOException
     */
    private String fwdPdfQzInterface(String path, String qxdm) throws IOException {
        //1.给定文件
        File pdfFile = new File(path);
        //3.IO流读取文件内容到byte数组
        byte[] pdfData = Base64Utils.getPDFByteArr(pdfFile);
        //4.指定关键字
        String keyword = qzKeyWord;
        //5.调用方法，给定关键字和文件
        List<float[]> positions = PdfSearchPositionUtil.findKeywordPostions(pdfData, keyword);
        //6.返回值类型是 List<float[]> 每个list元素代表一个匹配的位置，分别为 float[0]所在页码 float[1]所在x轴 float[2]所在y轴
        //7.调用服务端pdf签章接口
        if (CollectionUtils.isEmpty(positions)){
            throw  new AppException("ca签章未获取到位置");
        }
        String secretKey = caqzConfig.getSecretkey().get(qxdm);
        String uniqueId = caqzConfig.getUniqueid().get(qxdm);
        String fwdUrl = caqzConfig.getFwdpdfqzurl().get(qxdm);
        LOGGER.info("ca签章配置信息,secretKey:{},uniqueId:{},fwdUrl:{},qxdm:{}", secretKey, uniqueId, fwdUrl, qxdm);
        byte[]  result = null;
        for (float[] position : positions) {
            LOGGER.info("ca签章位置信息,页码：{}，x轴位置：{}，y轴位置:{}", position[0], position[1], position[2]);
            result = AhCaPdfQzUtils.pdfAddHz(secretKey, uniqueId, fwdUrl, "", pdfData, String.valueOf((int) position[0]),
                    String.valueOf(position[1]), String.valueOf(position[2]), "", "", "", "");
        }
        String pdfBinary = "";
        if (result != null) {
            pdfBinary = Base64Utils.encodeByteToBase64Str(result);
        }
        return pdfBinary;
    }

    /**
     * 合肥_办件效能监管
     *
     * @param gzlslid
     * @param nextNodeCode
     * @return
     */
    @Override
    public YctbCommonResponse yctbBjxnjg(String gzlslid, String nextNodeCode) {
        LOGGER.info("配置的nodemap值为：{}", nodeMap.toString());
        LOGGER.info("配置的nextNodeCode值为：{}", nextNodeCode);
        if (!nodeMap.containsKey(nextNodeCode)) {
            throw new RuntimeException("配置的节点名称对照有误");
        }
        Example xmExample = new Example(BdcXmDO.class);
        xmExample.createCriteria().andEqualTo("gzlslid", gzlslid);
        List<BdcXmDO> bdcXmDOS = entityMapper.selectByExample(xmExample);
        if (CollectionUtils.isEmpty(bdcXmDOS) || StringUtils.isBlank(bdcXmDOS.get(0).getGzlslid())) {
            return YctbCommonResponse.fail("未查询到相关的项目信息");
        }
        //50083 【合肥】省平台效能统计接口调整 --舒城上线，许丽霞说舒城也要
        // 1、业务号：省平台业务（审批来源为10，11）、地方登记系统互联网业务（审批来源3,5）推送spxtywh，其他审批来源不推送，spxtywh为空也不推送。
        if ((CommonConstantUtils.SYSTEM_VERSION_HF.equals(dataVersion) || CommonConstantUtils.SYSTEM_VERSION_SC.equals(dataVersion)) && StringUtils.isBlank(bdcXmDOS.get(0).getSpxtywh())) {
            if (StringUtils.isBlank(bdcXmDOS.get(0).getSpxtywh()) || splyList.contains(bdcXmDOS.get(0).getSply())) {
                LOGGER.info("不推送，sply为：{}", bdcXmDOS.get(0).getSply());
                return YctbCommonResponse.ok("非一窗通办项目直接返回");
            }

        }
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs("YCTB_DJLX_MAPPING");
        bdcZdDsfzdgxDO.setBdczdz(bdcXmDOS.get(0).getGzldyid());
        bdcZdDsfzdgxDO.setDsfxtbs("yctb");
        BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        LOGGER.info("---第三方字典返回值:{},查询参数:{}", result, bdcZdDsfzdgxDO);
        LOGGER.info("配置的nodemap值为：{}", nodeMap.toString());
        LOGGER.info("配置的bdcnNodemap值为：{}", bdcNodeMap.toString());
        //查询审核信息开始时间和结束时间
        BdcShxxQO bdcShxxQO = new BdcShxxQO();
        bdcShxxQO.setGzlslid(gzlslid);
        bdcShxxQO.setJdmc(bdcNodeMap.get(nextNodeCode));
        List<BdcShxxDO> bdcShxxDOList = bdcShxxFeignService.queryBdcShxx(bdcShxxQO);
        if (CollectionUtils.isNotEmpty(bdcShxxDOList)) {
            YctbBjxnjgRequest request = YctbBjxnjgRequest.YctbBjxnjgRequestBuilder.anYctbBjxnjgRequest().withQxdm(bdcXmDOS.get(0).getQxdm())
                    .withBegindate(dateformat.format(bdcShxxDOList.get(0).getShkssj()))
                    .withDjlx(null != result ? result.getDsfzdz() : "")
                    .withDqhj(nodeMap.get(nextNodeCode))
                    .withEnddate(dateformat.format(bdcShxxDOList.get(0).getShjssj()))
                    .withLcmc(bdcXmDOS.get(0).getGzldymc())
                    .withYwh(bdcXmDOS.get(0).getSlbh())
                    .build();
            if (CommonConstantUtils.SYSTEM_VERSION_HF.equals(dataVersion)) {
                request.setYwh(bdcXmDOS.get(0).getSpxtywh());
            }
            Object response = exchangeInterfaceRestController.requestInterface("yctb_bjxnjg", request);
            LOGGER.info("合肥一窗通办-办件效能监管接口返回结果：{}", JSON.toJSONString(response));
            if (response != null) {
                YctbCommonResponse<Object> yctbResponseDTO = JSON.parseObject(JSON.toJSONString(response), new TypeReference<YctbCommonResponse<Object>>() {
                });
                if (yctbResponseDTO.getCode().equals(200)) {
                    return YctbCommonResponse.ok();
                } else {
                    throw new MissingArgumentException("调用合肥一窗通办-办件效能监管接口失败:" + yctbResponseDTO.toString());
                }
            } else {
                return YctbCommonResponse.fail();
            }
        } else {
            LOGGER.info("请求参数节点：{}，配置对应审核信息节点：{}", nextNodeCode, bdcNodeMap.get(nextNodeCode));
            throw new AppException("该节点审核信息为空！请检查！");
        }
    }

    /**
     * 合肥_接收附件材料
     *
     * @param httpServletRequest
     * @return
     */
    @Override
    public YctbCommonResponse uploadFjxx(MultipartHttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return null;
        }
        InputStream zipfile = null;
        Iterator<String> iterator = httpServletRequest.getFileNames();
        String deleteFile = "";
        try {
            LOGGER.info("上传是否有文件:{}", iterator.hasNext());
            // 遍历所有上传文件
            String fileName = iterator.next();
            LOGGER.info("上传的文件:{}", fileName);
            MultipartFile multipartFile = httpServletRequest.getFile(fileName);

            zipfile = multipartFile.getInputStream();
            String spxtywh = multipartFile.getName();
            if (spxtywh.contains(".")) {
                spxtywh = spxtywh.split("\\.")[0];
            }
            //获取xm信息
            Example xmExample = new Example(BdcXmDO.class);
            xmExample.createCriteria().andEqualTo("spxtywh", spxtywh);
            List<BdcXmDO> bdcXmDOS = entityMapper.selectByExample(xmExample);
            if (CollectionUtils.isEmpty(bdcXmDOS) || StringUtils.isBlank(bdcXmDOS.get(0).getGzlslid())) {
                return YctbCommonResponse.fail("未查询到相关的项目信息");
            }
            //上传大云
            LOGGER.info("开始处理申请创件信息");

            String localDir = localFilePath + File.separator + multipartFile.getName().split("\\.")[0];

            // 1、缓存本地
            this.savePic(zipfile, localDir, multipartFile.getName());

            // 2、解压
            this.unzipFtpFile(localDir + File.separator + fileName, localDir);
            deleteFile = localDir;
            String stringValue = redisUtils.getStringValue(CommonConstantUtils.REDIS_HEFEI_YCTB_WWSQ_FJXX_PREFIX + spxtywh);
            List<YctbWwcjSjdInfo> yctbWwcjSjdInfos = JSON.parseArray(stringValue, YctbWwcjSjdInfo.class);
            String gzlslid = bdcXmDOS.get(0).getGzlslid();
            Map<String, List<YctbWwcjSjdInfo>> collect = new HashMap<>();
            for (YctbWwcjSjdInfo yctbWwcjSjdInfo : yctbWwcjSjdInfos) {
                if (collect.containsKey(yctbWwcjSjdInfo.getClmc())) {
                    collect.get(yctbWwcjSjdInfo.getClmc()).add(yctbWwcjSjdInfo);
                } else {
                    List<YctbWwcjSjdInfo> list = new ArrayList<>();
                    list.add(yctbWwcjSjdInfo);
                    collect.put(yctbWwcjSjdInfo.getClmc(), list);
                }
            }
            /**
             * {
             *             "ax_owner":"17bb2728c3e54beeb3510db209bb8692",
             *             "clmc":"申请人身份证明",
             *             "fjxx":[
             *                 {
             *                     "ax_expd":"jpg",
             *                     "ax_ident":"16ec31a0d14f417188a23ec900dece39 ",
             *                     "ax_path":"16ec31a0d14f417188a23ec900dece39.jpg",
             *                     "fjmc":"img-1618976282176a2eb3aea345e8219f8091060eee2c62d.jpg"
             *                 }
             *             ],
             *             "sjsl":"",
             *             "slid":"92ed9d4b29fd4228bf403114e9dbe534"
             *         }
             */
//            List<Map> djxlZdMap = bdcZdFeignService.queryBdcZd("djxl");
//            String djxlmc = StringToolUtils.convertBeanPropertyValueOfZdByString(bdcXmDOS.get(0).getDjxl(), djxlZdMap);
            for (String key : collect.keySet()) {
                // 判断文件夹存不存在 不存在新建
                StorageDto storageDto = storageClient.createRootFolder(Constants.WJZX_CLIENTID, gzlslid, key, "");
                List<YctbWwcjSjdInfo> sjdInfos = collect.get(key);
                for (YctbWwcjSjdInfo sjdInfo : sjdInfos) {
                    List<YctbWwcjFjInfo> fjxx = sjdInfo.getFjxx();
                    for (YctbWwcjFjInfo fj : fjxx) {
                        File file = new File(localDir + File.separator + fj.getAx_path());
                        MultipartDto multipartDto = getUploadParamDto(storageDto, file);
                        StorageDto dto = storageClient.multipartUpload(multipartDto);
                        LOGGER.info("附件上传成功 fjmc:{},storageid:{},spaceId:{}", fj.getFjmc(), dto.getId(), dto.getSpaceId());
                    }
                    // 保存受理库收件材料,有的更新，没有的插入
                    BdcSlSjclDO bdcSlSjclDO;
                    List<BdcSlSjclDO> bdcSlSjclDOS = bdcSlSjclFeignService.listBdcSlSjclByClmc(gzlslid, sjdInfo.getClmc());
                    if (CollectionUtils.isNotEmpty(bdcSlSjclDOS)) {
                        bdcSlSjclDO = bdcSlSjclDOS.get(0);
                    } else {
                        bdcSlSjclDO = new BdcSlSjclDO();
                    }
                    bdcSlSjclDO.setClmc(sjdInfo.getClmc());
                    bdcSlSjclDO.setFs(1);
                    bdcSlSjclDO.setYs(fjxx != null ? fjxx.size() : 0);
                    // 处理附件类型
//                    String fjlx = fjcl.getFjlx();
//                    if (StringUtils.isNotBlank(fjlx)) {
//                        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
//                        bdcZdDsfzdgxDO.setZdbs("BDC_SL_ZD_SJLX");
//                        bdcZdDsfzdgxDO.setDsfzdz(fjlx);
//                        bdcZdDsfzdgxDO.setDsfxtbs("wwsq");
//                        BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
//                        if (result != null && StringUtils.isNotBlank(result.getBdczdz())) {
//                            bdcSlSjclDO.setSjlx(Integer.parseInt(result.getBdczdz()));
//                        }
//                    }
                    bdcSlSjclDO.setDjxl(bdcXmDOS.get(0).getDjxl());
                    bdcSlSjclDO.setGzlslid(gzlslid);
                    bdcSlSjclDO.setWjzxid(storageDto.getId());
                    if (StringUtils.isBlank(bdcSlSjclDO.getSjclid())) {
                        bdcSlSjclFeignService.insertBdcSlSjcl(bdcSlSjclDO);
                    } else {
                        bdcSlSjclFeignService.updateBdcSlSjcl(bdcSlSjclDO);
                    }
                }
            }
            redisUtils.deleteKey(CommonConstantUtils.REDIS_HEFEI_YCTB_WWSQ_FJXX_PREFIX + spxtywh);
        } catch (Exception e) {
            LOGGER.error("上传文件失败,", e);
            return YctbCommonResponse.fail(e.getMessage());
        } finally {
            File delete = new File(deleteFile);
            if (delete.exists() && delete.isDirectory()) {
                for (File file : delete.listFiles()) {
                    try {
                        FileDeleteStrategy.FORCE.delete(file);
                    } catch (IOException e) {
                        LOGGER.error("删除临时文件失败,", e);
                    }
                }
                delete.delete();
            }
        }
        return YctbCommonResponse.ok();
    }

    /**
     * 合肥_获取不动产单元信息
     *
     * @param yctbGetHouseInfoRequest
     * @return
     */
    @Override
    public YctbCommonResponse yctbGetHouseInfo(YctbGetHouseInfoRequest yctbGetHouseInfoRequest) {
        UserDto yzr = userManagerUtils.getCurrentUser();//验证人；
        String yzrid = null == yzr ? null : yzr.getId();
        String yzrzh = null == yzr ? null : yzr.getUsername();

        //规则验证
        //查询信息
        BdcQszmQO bdcQszmQO = new BdcQszmQO();
        if (StringUtils.isNotBlank(yctbGetHouseInfoRequest.getBdcdyh())) {
            bdcQszmQO.setBdcdyh(yctbGetHouseInfoRequest.getBdcdyh());
        }
        if (StringUtils.isNotBlank(yctbGetHouseInfoRequest.getBdcqzh())) {
            bdcQszmQO.setBdcqzh(yctbGetHouseInfoRequest.getBdcqzh());
        }
        if (StringUtils.isNotBlank(yctbGetHouseInfoRequest.getBdcdjzmh())) {
            bdcQszmQO.setBdcqzh(yctbGetHouseInfoRequest.getBdcdjzmh());
        }
        List<YctbGetHouseInfoResponse> yctbGetHouseInfoResponses = bdcdjMapper.queryBdcdyhxxForYctb(bdcQszmQO);
        if (CollectionUtils.isNotEmpty(yctbGetHouseInfoResponses)) {
            List<Map> fwxzMapList = bdcZdFeignService.queryBdcZd("fwxz");
            List<Map> fwlxMapList = bdcZdFeignService.queryBdcZd("fwlx");
            for (YctbGetHouseInfoResponse yctbGetHouseInfoResponse : yctbGetHouseInfoResponses) {
                if (StringUtils.isNotBlank(gzyzzhbs)) {
                    BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
                    bdcZdDsfzdgxDO.setZdbs("YCTB_DJLX_MAPPING");
                    bdcZdDsfzdgxDO.setDsfzdz(yctbGetHouseInfoRequest.getDjlx());
                    bdcZdDsfzdgxDO.setDsfxtbs("yctb");
                    BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
                    String gzldyid = result.getBdczdz();
                    if (StringUtils.isNotBlank(result.getBdczdz()) && result.getBdczdz().contains("{")) {
                        JSONObject calyConfig = JSON.parseObject(StringEscapeUtils.unescapeJava(result.getBdczdz()));
                        LOGGER.info("第三方配置字段值为“{}", calyConfig.toJSONString());

                        if (StringUtils.isNotBlank(calyConfig.getString(yctbGetHouseInfoRequest.getLcmc().contains("商品") ? "41" : "42"))) {
                            LOGGER.info("参数“{}", calyConfig.getString(yctbGetHouseInfoRequest.getLcmc().contains("商品") ? "41" : "42"));
                            gzldyid = calyConfig.getString(yctbGetHouseInfoRequest.getLcmc().contains("商品") ? "41" : "42");
                        } else {
                            LOGGER.info("获取工作流实例id异常:{}", gzldyid);
                            return YctbCommonResponse.fail("获取工作流实例id异常:" + yctbGetHouseInfoRequest.getDjlx());
                        }
                    }
                    BdcGzYzQO bdcGzYzQO = new BdcGzYzQO();
                    bdcGzYzQO.setZhbs(gzldyid + gzyzzhbs);
                    // 获取规则验证的参数列表
                    List<Map<String, Object>> gzyzParamList = new ArrayList<>();
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("bdcdyh", yctbGetHouseInfoResponse.getBdcdyh());
                    gzyzParamList.add(paramMap);
                    bdcGzYzQO.setParamList(gzyzParamList);
                    bdcGzYzQO.setYzrid(yzrid);
                    bdcGzYzQO.setYzrzh(yzrzh);
                    // 验证组合规则
                    List<BdcGzYzTsxxDTO> listBdcGzYzTsxx = bdcGzZhGzFeignService.listBdcGzYzTsxxOfAnyParam(bdcGzYzQO);
                    if (CollectionUtils.isNotEmpty(listBdcGzYzTsxx)) {
                        String yzResultMsg;
                        StringBuilder tsxxSb = new StringBuilder();
                        BdcGzYzTsxxDTO dto = listBdcGzYzTsxx.get(0);
                        if (CollectionUtils.isNotEmpty(dto.getZgzTsxxDTOList())) {
                            // 循环 拼接提示信息
                            for (BdcGzZgzTsxxDTO zgzTsxx : dto.getZgzTsxxDTOList()) {
                                tsxxSb.append(zgzTsxx.getTsxx()).append(",");
                            }
                            yzResultMsg = tsxxSb.toString();
                            if (StringUtils.isNotBlank(yzResultMsg) && yzResultMsg.endsWith(",")) {
                                List<YctbGetHouseInfoResponse> list = new ArrayList<>();
                                YctbGetHouseInfoResponse error = new YctbGetHouseInfoResponse();
                                JSONObject errorRows = new JSONObject();
                                error.setSfbl("1");
                                error.setBkblyy(yzResultMsg.substring(0, yzResultMsg.length() - 1));
                                list.add(error);
                                errorRows.put("rows", list);

                                return YctbCommonResponse.ok(errorRows);
                            }
                        }
                    }
                }
                yctbGetHouseInfoResponse.setSfbl("0");
                if (StringUtils.isNotBlank(yctbGetHouseInfoResponse.getFwxz())) {
                    if (CollectionUtils.isNotEmpty(fwxzMapList)) {
                        for (Map zdMap : fwxzMapList) {
                            if (StringUtils.equals(yctbGetHouseInfoResponse.getFwxz(), org.apache.commons.collections4.MapUtils.getString(zdMap, "DM"))) {
                                if (Objects.nonNull(zdMap.get("MC"))) {
                                    yctbGetHouseInfoResponse.setFwxzmc(zdMap.get("MC").toString());
                                }
                            }
                        }
                    }
                }
                if (StringUtils.isNotBlank(yctbGetHouseInfoResponse.getFwlx())) {
                    if (CollectionUtils.isNotEmpty(fwlxMapList)) {
                        for (Map zdMap : fwlxMapList) {
                            if (StringUtils.equals(yctbGetHouseInfoResponse.getFwlx(), org.apache.commons.collections4.MapUtils.getString(zdMap, "DM"))) {
                                if (Objects.nonNull(zdMap.get("MC"))) {
                                    yctbGetHouseInfoResponse.setFwlxmc(zdMap.get("MC").toString());
                                }
                            }
                        }
                    }
                }
                String qjgldm = bdcdjMapper.queryQjgldm(yctbGetHouseInfoResponse.getXmid());
                BdcdyhZtResponseDTO bdcdyhZtResponseDTO = bdcdyZtFeignService.commonQueryBdcdyhZtBybdcdyh(yctbGetHouseInfoResponse.getBdcdyh(), qjgldm);
                if (bdcdyhZtResponseDTO != null) {
                    yctbGetHouseInfoResponse.setCfzt(bdcdyhZtResponseDTO.getCf() ? "1" : "0");
                    yctbGetHouseInfoResponse.setDyzt(bdcdyhZtResponseDTO.getDya() ? "1" : "0");
                    yctbGetHouseInfoResponse.setYgzt(bdcdyhZtResponseDTO.getYg() ? "1" : "0");
                    yctbGetHouseInfoResponse.setYyzt(bdcdyhZtResponseDTO.getYy() ? "1" : "0");
                    yctbGetHouseInfoResponse.setYgdyzt(bdcdyhZtResponseDTO.getYdya() ? "1" : "0");
                } else {
                    yctbGetHouseInfoResponse.setCfzt("1");
                    yctbGetHouseInfoResponse.setDyzt("1");
                    yctbGetHouseInfoResponse.setYgzt("1");
                    yctbGetHouseInfoResponse.setYyzt("1");
                    yctbGetHouseInfoResponse.setYgdyzt("1");
                }
                yctbGetHouseInfoResponse.setCqzt("1");
                if (StringUtils.isNotBlank(yctbGetHouseInfoResponse.getKssynx())) {
                    yctbGetHouseInfoResponse.setKssynx(DateUtils.convertTimeStr(yctbGetHouseInfoResponse.getKssynx(), DateUtils.DATE_FORMAT));
                }
                if (StringUtils.isNotBlank(yctbGetHouseInfoResponse.getJssynx())) {
                    yctbGetHouseInfoResponse.setJssynx(DateUtils.convertTimeStr(yctbGetHouseInfoResponse.getJssynx(), DateUtils.DATE_FORMAT));
                }
                /**
                 * scjzmj	string 	实测建筑面积	fw_hs.scjzmj
                 * sctnjzmj	string 	实测套内建筑面积	fw_hs.sctnjzmj
                 * scftjzmj	string 	实测分摊建筑面积	fw_hs.scftjzmj
                 *
                 * scdxbfjzmj	string 	实测地下部分建筑面积	fw_hs.scdxbfjzmj
                 * scqtjzmj	string 	实测其他建筑面积	fw_hs.scqtjzmj
                 * ycjzmj	string 	预测建筑面积	fw_ychs.ycjzmj
                 * yctnjzmj	string 	预测套内建筑面积	fw_ychs.yctnjzmj
                 * ycftjzmj	string 	预测分摊建筑面积	fw_ychs.ycftjzmj
                 * ycdxbfjzmj	string 	预测地下部分建筑面积	fw_ychs.ycdxbfjzmj
                 * ycqtjzmj	string 	预测其它建筑面积	fw_ychs.ycqtjzmj
                 */
                FwHsDO fwHsDO = fwHsFeignService.queryFwhsByBdcdyh(yctbGetHouseInfoResponse.getBdcdyh(), qjgldm);
                if (fwHsDO != null) {
                    yctbGetHouseInfoResponse.setScjzmj(fwHsDO.getScjzmj() == null ? null : Double.toString(fwHsDO.getScjzmj()));
                    yctbGetHouseInfoResponse.setSctnjzmj(fwHsDO.getSctnjzmj() == null ? null : Double.toString(fwHsDO.getSctnjzmj()));
                    yctbGetHouseInfoResponse.setScftjzmj(fwHsDO.getScftjzmj() == null ? null : Double.toString(fwHsDO.getScftjzmj()));
                    yctbGetHouseInfoResponse.setScdxbfjzmj(fwHsDO.getScdxbfjzmj() == null ? null : Double.toString(fwHsDO.getScdxbfjzmj()));
                    yctbGetHouseInfoResponse.setScqtjzmj(fwHsDO.getScqtjzmj() != null ? Double.toString(fwHsDO.getScqtjzmj()) : null);
                }
                FwYchsDO fwYchsDO = fwYcHsFeignService.queryFwYcHsByBdcdyh(yctbGetHouseInfoResponse.getBdcdyh(), qjgldm);
                if (fwYchsDO != null) {
                    yctbGetHouseInfoResponse.setYcjzmj(fwYchsDO.getYcjzmj() == null ? null : Double.toString(fwYchsDO.getYcjzmj()));
                    yctbGetHouseInfoResponse.setYctnjzmj(fwYchsDO.getYctnjzmj() == null ? null : Double.toString(fwYchsDO.getYctnjzmj()));
                    yctbGetHouseInfoResponse.setYcftjzmj(fwYchsDO.getYcftjzmj() == null ? null : Double.toString(fwYchsDO.getYcftjzmj()));
                    yctbGetHouseInfoResponse.setYcdxbfjzmj(fwYchsDO.getYcdxbfjzmj() == null ? null : Double.toString(fwYchsDO.getYcdxbfjzmj()));
                    yctbGetHouseInfoResponse.setYcqtjzmj(fwYchsDO.getYcqtjzmj() == null ? null : Double.toString(fwYchsDO.getYcqtjzmj()));
                }
            }
            JSONObject result = new JSONObject(2);

            result.put("rows", yctbGetHouseInfoResponses);
            LOGGER.info("第三方配置字段值为rows“{}", result.toString());

            return YctbCommonResponse.ok(result);
        } else {
            return YctbCommonResponse.fail("根据cqzh或者bdcdyh未查询到项目信息");
        }
    }

    /**
     * 合肥_获取不动产上手业务信息
     *
     * @param yctbOldBusinessRequest
     * @return
     */
    @Override
    public YctbCommonResponse oldBusiness(YctbOldBusinessRequest yctbOldBusinessRequest) {
        LOGGER.info("合肥_获取不动产上手业务信息入参:{}", JSON.toJSONString(yctbOldBusinessRequest));
        //获取上一手gzlslid
        BdcQszmQO bdcQszmQO = new BdcQszmQO();
        HashMap reqMap = new HashMap<>(12);
        if (StringUtils.isNotBlank(yctbOldBusinessRequest.getBdcdyh())) {
            bdcQszmQO.setBdcdyh(yctbOldBusinessRequest.getBdcdyh());
            reqMap.put("bdcdyh", yctbOldBusinessRequest.getBdcdyh());
        }
        if (StringUtils.isNotBlank(yctbOldBusinessRequest.getBdcqzh())) {
            bdcQszmQO.setBdcqzh(yctbOldBusinessRequest.getBdcqzh());
            reqMap.put("bdcqzh", yctbOldBusinessRequest.getBdcqzh());

        }
        if (StringUtils.isNotBlank(yctbOldBusinessRequest.getBdcdjzmh())) {
            bdcQszmQO.setBdcqzh(yctbOldBusinessRequest.getBdcdjzmh());
            reqMap.put("bdcqzh", yctbOldBusinessRequest.getBdcdjzmh());

        }
        //获取对照后的qllx
        BdcZdDsfzdgxDO bdcZdDsfzdgxDO = new BdcZdDsfzdgxDO();
        bdcZdDsfzdgxDO.setZdbs("YCTB_DJXL_QLLX");
        bdcZdDsfzdgxDO.setDsfzdz(yctbOldBusinessRequest.getDjlx());
        bdcZdDsfzdgxDO.setDsfxtbs("yctb");
        BdcZdDsfzdgxDO result = bdcZdFeignService.dsfZdgx(bdcZdDsfzdgxDO);
        LOGGER.debug("---第三方字典返回值:{},查询参数:{}", result, bdcZdDsfzdgxDO);
        reqMap.put("qllx", result.getBdczdz());

        List<BdcXmDO> bdcXmDOS = bdcdjMapper.queryXmxxByBdcqzhAndBdcDyh_map(reqMap);
        if (CollectionUtils.isNotEmpty(bdcXmDOS)) {

            List<YctbOldBusinessReqmap> yctbOldBusinessReqmaps = bdcdjMapper.queryReqmapForYctb_bf(bdcXmDOS.get(0).getXmid());
            //获取qdjg
            if (CollectionUtils.isNotEmpty(yctbOldBusinessReqmaps)) {
//                    LOGGER.info("yctbOldBusinessReqmaps:{}", JSON.toJSONString(yctbOldBusinessReqmaps));
                YctbOldBusinessResponse response = new YctbOldBusinessResponse();
                List<Map> maps = bdcdjMapper.queryQdjgForYctb(bdcXmDOS.get(0).getXmid());
//                    LOGGER.info("maps:{}", JSON.toJSONString(maps));
                if (CollectionUtils.isNotEmpty(maps) && maps != null && maps.get(0) != null) {
                    if (maps.get(0).get("YGQDJG") != null) {
                        yctbOldBusinessReqmaps.get(0).setQdjg((String) convert(maps.get(0).get("YGQDJG")));
                    } else if (maps.get(0).get("FDCQQDJG") != null) {
                        yctbOldBusinessReqmaps.get(0).setQdjg((String) convert(maps.get(0).get("FDCQQDJG")));
                    } else if (maps.get(0).get("JSYDSYQQDJG") != null) {
                        yctbOldBusinessReqmaps.get(0).setQdjg((String) convert(maps.get(0).get("JSYDSYQQDJG")));
                    }
                }

                response.setReqmap(yctbOldBusinessReqmaps.get(0));
                Example qlrExample = new Example(BdcQlrDO.class);
                qlrExample.createCriteria().andEqualTo("xmid", bdcXmDOS.get(0).getXmid());
                List<BdcQlrDO> qlrxxList = entityMapper.selectByExample(qlrExample);
                List<YctbOldBusinessQlrInfo> oldBusinessQlrInfoList;
                if (CollectionUtils.isNotEmpty(qlrxxList)) {
//                        LOGGER.info("qlrxxList:{}", JSON.toJSONString(qlrxxList));
                    oldBusinessQlrInfoList = new ArrayList<>(qlrxxList.size());
                    for (BdcQlrDO bdcQlrDO : qlrxxList) {
                        YctbOldBusinessQlrInfo qlrInfo = new YctbOldBusinessQlrInfo();
                        exchangeDozerMapper.map(bdcQlrDO, qlrInfo, "initOldBusinessQlrxx");
                        if ("1".equals(qlrInfo.getQlrlb())) {
                            qlrInfo.setQlrlb("0");
                        } else if ("2".equals(qlrInfo.getQlrlb())) {
                            qlrInfo.setQlrlb("1");
                        }
                        List<YctbWwcjGxrInfo> gxrInfoList = new ArrayList<>(2);
                        if (StringUtils.isNotBlank(bdcQlrDO.getFrmc())) {
                            YctbWwcjGxrInfo frInfo = new YctbWwcjGxrInfo();
                            exchangeDozerMapper.map(bdcQlrDO, frInfo, "initOldBusinessFrxx");
                            gxrInfoList.add(frInfo);
                        }
                        if (StringUtils.isNotBlank(bdcQlrDO.getDlrmc())) {
                            YctbWwcjGxrInfo dlrInfo = new YctbWwcjGxrInfo();
                            exchangeDozerMapper.map(bdcQlrDO, dlrInfo, "initOldBusinessDlrxx");
                            gxrInfoList.add(dlrInfo);
                        }
                        qlrInfo.setGxrlist(gxrInfoList);
                        oldBusinessQlrInfoList.add(qlrInfo);
                    }
                } else {
                    return YctbCommonResponse.fail("xmid：" + bdcXmDOS.get(0).getXmid() + "未查询到相关的qlr信息");
                }
                response.setApplicantList(oldBusinessQlrInfoList);
                return YctbCommonResponse.ok(response);


            } else {
                return YctbCommonResponse.fail("根据cqzh或者bdcdyh未查询到证书信息");
            }


        } else {
            return YctbCommonResponse.fail("根据cqzh或者bdcdyh未查询到项目信息");
        }
    }

    public Object convert(Object sourceFieldValue) {
        if (sourceFieldValue != null) {
            String param = "#.########";
            try {
                Double doubleVal = NumberUtils.createDouble(sourceFieldValue.toString());
                if (doubleVal != null) {

                    DecimalFormat df = new DecimalFormat(param);
                    return df.format(doubleVal);
                }
            } catch (Exception e) {
                LOGGER.error("double转string失败,{}", sourceFieldValue, e);
            }
        }
        return sourceFieldValue;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description
     */
    private static MultipartDto getUploadParamDto(StorageDto storageDto, File file) throws Exception {
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(file));
        MultipartDto multipartDto = new MultipartDto();
        multipartDto.setNodeId(storageDto.getId());
        multipartDto.setClientId(Constants.WJZX_CLIENTID);
        //盐城附件上传接口不覆盖重名文件
        multipartDto.setData(multipartFile.getBytes());
        multipartDto.setContentType(multipartFile.getContentType());
        multipartDto.setName(multipartFile.getName());
        multipartDto.setOriginalFilename(multipartFile.getOriginalFilename());
        multipartDto.setSize(multipartFile.getSize());
        multipartDto.setRename(0);
        return multipartDto;
    }

    private void savePic(InputStream inputStream, String targetFile, String fileName) {
        OutputStream os = null;
        try {
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(targetFile);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(targetFile + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            os.flush();

        } catch (IOException e) {
            LOGGER.error("解压文件失败, {}", e);
        } catch (Exception e) {
            LOGGER.error("解压文件失败, {}", e);
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                LOGGER.error("解压文件失败, {}", e);
            }
        }
    }

    private void unzipFtpFile(String localFtpFile, String localFtpDir) throws Exception {
        try {
            LOGGER.info(exec("unzip " + localFtpFile + " -d " + localFtpDir));
            LOGGER.info("解压文件成功,路径：{}", localFtpDir);
        } catch (Exception exception) {
            LOGGER.error("解压文件失败", exception);
            throw exception;
        }
    }

    public static String exec(String command) throws InterruptedException {
        String returnString = "";
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            LOGGER.error("Create runtime false!");
        }
        try {
            pro = runTime.exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                returnString = returnString + line + "\n";
            }
            input.close();
            output.close();
            pro.destroy();
        } catch (IOException ex) {
            LOGGER.info("解压失败", ex);
        }
        return returnString;
    }

    /**
     * 判断入参是否是json
     *
     * @param content
     * @return
     */
    public static boolean isJson(String content) {
        if (StringUtils.isEmpty(content)) {
            return false;
        }
        boolean isJsonObject = true;
        boolean isJsonArray = true;
        try {
            JSONObject.parseObject(content);
        } catch (Exception e) {
            isJsonObject = false;
        }
        try {
            JSONObject.parseArray(content);
        } catch (Exception e) {
            isJsonArray = false;
        }
        if (!isJsonObject && !isJsonArray) { //不是json格式
            return false;
        }
        return true;
    }

}
