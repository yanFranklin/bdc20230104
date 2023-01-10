package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcYczfVO;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 2021/11/02
 * @description 不动产一次支付接口服务
 */
public interface BdcYczfService {

    /**
     * @description 一次支付后台分账，生成支付订单接口（调用3.1 生成支付订单接口）
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/11 10:58
     * @param gzlslid 工作流实例ID
     * @param lx 类型 1:扫码下单,2:pos下单
     * @return Object {"payOrderNo":"政融支付订单号","payUrl":"支付URL"}
     */
    Object sczfdd(String gzlslid, String lx, String qlrlb);

    /**
     * 查询缴费结果（调用3.2 查询缴费结果接口）
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return
     */
    CommonResponse cxjfjg(String gzlslid, String qlrlb);

    /**
     * 获取不动产一次支付收费收税信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb  权利人类别
     * @return 权利人义务人收费收税信息
     */
    BdcYczfVO queryBdcYczfSfssxx(String gzlslid, String qlrlb);

    /**
     * POS缴费成功通知政融支付
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb  权利人类别
     * @return 通知结果
     */
    CommonResponse posZfcgtz(String gzlslid, String qlrlb);

    /**
     * 线上退款申请
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb  权利人类别
     * @return 申请情况（退款状态，退款金额，退款时间）
     */
    CommonResponse xstksq(String gzlslid, String qlrlb);

    /**
     * 线上退款结果查询
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb  权利人类别
     * @return 退款结果（退款金额，退款时间）
     */
    CommonResponse xstkjgcx(String gzlslid, String qlrlb);

    /**
     * @description POS银行卡支付
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 9:06
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    Object posYhkzf(String gzlslid, String qlrlb);

    /**
     * @description POS付款码支付
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 9:06
     * @param gzlslid
     * @param qlrlb
     * @param fkm
     * @return Object
     */
    Object posFkmzf(String gzlslid, String qlrlb, String fkm);

    /**
     * @description POS银行卡撤销
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 9:06
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    Object posYhkcx(String gzlslid, String qlrlb);

    /**
     * @description POS银行卡退款
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 9:06
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    Object posYhktk(String gzlslid, String qlrlb);

    /**
     * @description POS付款码退款
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 9:06
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    Object posFkmtk(String gzlslid, String qlrlb);

    /**
     * @description POS重打小票
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 9:06
     * @param gzlslid
     * @param qlrlb
     * @return Object
     */
    Object posCdxp(String gzlslid, String qlrlb);

    /**
     * @description POS保存交易信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2023/1/9 9:06
     * @param gzlslid
     * @param qlrlb
     * @param output
     * @return void
     */
    void posSaveJyxx(String gzlslid, String qlrlb, String output);
}
