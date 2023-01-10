package cn.gtmap.realestate.common.core.service.rest.accept;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcYczfVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0  2022/10/11
 * @description 一次支付后台分账
 */
public interface BdcYczfRestService {

    /**
     * @description 一次支付后台分账，生成支付订单接口
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/11 10:58
     * @param gzlslid 工作流实例ID
     * @param lx 类型 1:扫码下单,2:pos下单
     * @param qlrlb 权利人类别
     * @return Object {"payOrderNo":"政融支付订单号","payUrl":"支付URL"}
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/sczfdd")
    Object sczfdd(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "lx") String lx,
                  @RequestParam(value = "qlrlb") String qlrlb);

    /**
     * @description 一次支付后台分账，查询缴费结果接口
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/11 11:02
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return Object 缴费结果： 2（成功） 1（失败）
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/cxjfjg")
    CommonResponse cxjfjg(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb);

    /**
     * 查询不动产一次支付收费收税信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return 收费收税信息
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/sfssxx")
    BdcYczfVO queryBdcYczfSfssxx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb", required = false) String qlrlb);

    /**
     * POS缴费成功通知政融支付
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return 通知结果
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/pos/zfcgtz")
    CommonResponse posZfcgtz(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb);

    /**
     * 线上退款申请
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return 申请情况（退款状态，退款金额，退款时间）
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/xstksq")
    CommonResponse xstksq(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb);

    /**
     * 线上退款结果查询
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return 退款结果（退款金额，退款时间）
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/xstkjgcx")
    CommonResponse xstkjgcx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb);

    /**
     * @description POS银行卡支付
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/pos/yhkzf")
    Object posYhkzf(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb);

    /**
     * @description POS付款码支付
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @param fkm
     * @return Object
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/pos/fkmzf")
    Object posFkmzf(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb, @RequestParam(value = "fkm") String fkm);

    /**
     * @description POS银行卡撤销
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/pos/yhkcx")
    Object posYhkcx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb);

    /**
     * @description POS银行卡退款
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/pos/yhktk")
    Object posYhktk(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb);

    /**
     * @description POS付款码退款
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/pos/fkmtk")
    Object posFkmtk(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb);

    /**
     * @description POS重打小票
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/pos/cdxp")
    Object posCdxp(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb);

    /**
     * @description POS保存交易信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 8:44
     * @param gzlslid
     * @param qlrlb
     * @param output
     * @return void
     */
    @PostMapping("/realestate-accept/rest/v1.0/yczf/pos/savejyxx")
    void posSaveJyxx(@RequestParam(value = "gzlslid") String gzlslid, @RequestParam(value = "qlrlb") String qlrlb, @RequestParam(value = "output") String output);
}
