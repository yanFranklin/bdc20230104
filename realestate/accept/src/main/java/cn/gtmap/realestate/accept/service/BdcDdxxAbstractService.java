package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.domain.CommonResponse;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/11/3
 * @description 不动产一次支付订单服务抽象类
 */
public interface BdcDdxxAbstractService {

    /**
     * @description 一次支付后台分账，生成支付订单接口
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @date 2022/11/3
     * @param gzlslid 工作流实例ID
     * @param lx 类型 1:扫码下单, 2:pos下单
     * @return Object {"payOrderNo":"政融支付订单号","payUrl":"支付URL"}
     */
    Object sczfdd(String gzlslid, String lx, String qlrlb);

    /**
     * 查询缴费结果
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param gzlslid 工作流实例ID
     * @param qlrlb 权利人类别
     * @return 缴费情况
     */
    CommonResponse cxjfjg(String gzlslid, String qlrlb);
}
