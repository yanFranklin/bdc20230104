package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSfssDdxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcYczfFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcYczfVO;
import cn.gtmap.realestate.common.util.IPPortUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0  2022/10/11
 * @description 一次支付后台分账
 */
@RestController
@RequestMapping("/rest/v1.0/yczf")
public class BdcYczfController extends BaseController {

    /**
     * 缴费书二维码url 是否转译
     */
    @Value("${sfxx.jfsurl.sfzy:false}")
    private boolean jfsUrlSfzy;

    @Autowired
    BdcYczfFeignService bdcYczfFeignService;

    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    @Autowired
    BdcSlSfssDdxxFeignService bdcSlSfssDdxxFeignService;

    /**
     * @description 一次支付后台分账，生成支付订单接口
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/11 11:02
     * @param gzlslid 工作流实例ID
     * @param lx 类型 1:扫码下单,2:pos下单
     * @param qlrlb 权利人类别
     * @return Object {"payOrderNo":"政融支付订单号","payUrl":"支付URL"}
     */
    @PostMapping("/sczfdd")
    public Object sczfdd(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "lx") String lx,
                                @RequestParam(value = "qlrlb") String qlrlb) {
        return bdcYczfFeignService.sczfdd(gzlslid, lx, qlrlb);
    }

    /**
     * @description 一次支付后台分账，查询缴费结果接口
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/11 11:02
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return Object 缴费结果： 2（成功） 1（失败）
     */
    @PostMapping("/cxjfjg")
    public CommonResponse cxjfjg(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        return bdcYczfFeignService.cxjfjg(gzlslid,qlrlb);
    }

    /**
     * 获取税费统缴信息
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return 税费统缴信息
     */
    @PostMapping("/sftjdxx")
    public BdcYczfVO querySftjdxx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb", required = false) String qlrlb) {
        return this.bdcYczfFeignService.queryBdcYczfSfssxx(gzlslid, qlrlb);
    }

    /**
     * PO支付成功通知政融平台
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return 通知结果
     */
    @PostMapping("/pos/tzzr")
    public CommonResponse posZfcgtz(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        return this.bdcYczfFeignService.posZfcgtz(gzlslid, qlrlb);
    }

    /**
     * 推送税费的支付二维码内容到摩科评价器
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param httpServletRequest
     * @return 推送结果
     */
    @GetMapping("/tsjkm")
    public Object tsjkm(String gzlslid, String qlrlb, HttpServletRequest httpServletRequest) {
        if(StringUtils.isAnyBlank(gzlslid, qlrlb)){
            throw new AppException(ErrorCode.MISSING_ARG, "缺失参数：工作流实例ID");
        }
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        bdcSlSfssDdxxQO.setQlrlb(qlrlb);
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = bdcSlSfssDdxxFeignService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if(CollectionUtils.isEmpty(bdcSlSfssDdxxDOList)){
            throw new AppException(ErrorCode.MISSING_ARG, "未获取到税费订单信息");
        }
        HashMap<String, String> paramMap = new HashMap<>(2);
        paramMap.put("sysIp", IPPortUtils.getClientIp(httpServletRequest));
        if (jfsUrlSfzy) {
            try {
                paramMap.put("qrCode", URLEncoder.encode(bdcSlSfssDdxxDOList.get(0).getJfurl(), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("url编码错误{}",bdcSlSfssDdxxDOList.get(0).getJfurl(), e);
            }
        } else {
            paramMap.put("qrCode", bdcSlSfssDdxxDOList.get(0).getJfurl());
        }
        Object response = exchangeInterfaceFeignService.requestInterface("tsjfdz_mk", paramMap);
        LOGGER.info("推送缴款码返回结果{}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 线上退款申请
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return 申请情况（退款状态，退款金额，退款时间）
     */
    @PostMapping("/xstksq")
    public CommonResponse xstksq(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        return this.bdcYczfFeignService.xstksq(gzlslid, qlrlb);
    }

    /**
     * 线上退款结果查询
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return 退款结果（退款金额，退款时间）
     */
    @PostMapping("/xstkjgcx")
    public CommonResponse xstkjgcx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        return this.bdcYczfFeignService.xstkjgcx(gzlslid, qlrlb);
    }

    /**
     * @description POS银行卡支付
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    @PostMapping("/pos/yhkzf")
    public Object posYhkzf(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        if (StringUtils.isAnyBlank(gzlslid, qlrlb)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或权利人类别");
        }
        return bdcYczfFeignService.posYhkzf(gzlslid, qlrlb);
    }

    /**
     * @description POS付款码支付
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @param fkm
     * @return Object
     */
    @PostMapping("/pos/fkmzf")
    public Object posFkmzf(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb, @RequestParam(value = "fkm") String fkm) {
        if (StringUtils.isAnyBlank(gzlslid, qlrlb, fkm)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或权利人类别或付款码");
        }
        return bdcYczfFeignService.posFkmzf(gzlslid, qlrlb, fkm);
    }

    /**
     * @description POS银行卡撤销
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    @PostMapping("/pos/yhkcx")
    Object posYhkcx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        if (StringUtils.isAnyBlank(gzlslid, qlrlb)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或权利人类别");
        }
        return bdcYczfFeignService.posYhkcx(gzlslid, qlrlb);
    }

    /**
     * @description POS银行卡退款
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    @ResponseBody
    @PostMapping("/pos/yhktk")
    Object posYhktk(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        if (StringUtils.isAnyBlank(gzlslid, qlrlb)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或权利人类别");
        }
        return bdcYczfFeignService.posYhktk(gzlslid, qlrlb);
    }

    /**
     * @description POS付款码退款
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    @ResponseBody
    @PostMapping("/pos/fkmtk")
    Object posFkmtk(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        if (StringUtils.isAnyBlank(gzlslid, qlrlb)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或权利人类别");
        }
        return bdcYczfFeignService.posFkmtk(gzlslid, qlrlb);
    }

    /**
     * @description POS重打小票
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    @ResponseBody
    @PostMapping("/pos/cdxp")
    Object posCdxp(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb) {
        if (StringUtils.isAnyBlank(gzlslid, qlrlb)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或权利人类别");
        }
        return bdcYczfFeignService.posCdxp(gzlslid, qlrlb);
    }

    /**
     * @description POS保存交易信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @param output
     * @return CommonResponse
     */
    @ResponseBody
    @PostMapping("/pos/savejyxx")
    void posSaveJyxx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb, @RequestParam(value = "output") String output) {
        if (StringUtils.isAnyBlank(gzlslid, qlrlb, output)) {
            throw new AppException(ErrorCode.MISSING_ARG, "缺少参数：工作流实例ID或权利人类别或POS交易返回参数");
        }
        bdcYczfFeignService.posSaveJyxx(gzlslid, qlrlb, output);
    }
}
