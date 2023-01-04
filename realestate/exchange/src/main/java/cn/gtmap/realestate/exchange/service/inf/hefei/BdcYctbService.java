package cn.gtmap.realestate.exchange.service.inf.hefei;

import cn.gtmap.realestate.common.core.dto.exchange.hefei.yctb.YctbCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.yctb.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 2021-06-23 11:05:54
 * @description (合肥) 一窗通办相关服务处理
 */
public interface BdcYctbService {

    /**
     * 添加税务明细和缴费明细
     * @return
     */
    public YctbCommonResponse addTaxAndPayInfo(String gzlslId);

    /**
     * 获取纳税缴费支付地址
     * @return
     */
    public YctbCommonResponse getPayUrl(YctbGetPayUrlRequest yctbGetPayUrlRequest);

    /**
     * 获取缴费状态
     * @return
     */
    public YctbCommonResponse getTaxPaymentState(YctbGetTaxPayRequest yctbGetTaxPayRequest);

    /**
     * 合肥_登记派件分发
     * @param yctbWwcjRequest
     * @return
     */
    public YctbCommonResponse saveApplicationInfo(YctbWwcjRequest yctbWwcjRequest);

    /**
     * 合肥_在线查档
     * @param yctbZxcdRequest
     * @return
     */
    public YctbCommonResponse yctbZxcd(@RequestBody YctbZxcdRequest yctbZxcdRequest);

    /**
     * 合肥_办件效能监管
     * @param gzlslid
     * @param nextNodeCode
     * @return
     */
    public YctbCommonResponse yctbBjxnjg(@PathVariable(value = "gzlslid") String gzlslid, @PathVariable(value = "nextNodeCode") String nextNodeCode);

    /**
     * 合肥_接收附件材料
     * @param httpServletRequest
     * @return
     */
    public YctbCommonResponse uploadFjxx(MultipartHttpServletRequest httpServletRequest);

    /**
     * 合肥_获取不动产单元信息
     * @param yctbGetHouseInfoRequest
     * @return
     */
    public YctbCommonResponse yctbGetHouseInfo(YctbGetHouseInfoRequest yctbGetHouseInfoRequest);

    /**
     * 合肥_获取不动产上手业务信息
     * @param yctbOldBusinessRequest
     * @return
     */
    public YctbCommonResponse oldBusiness(YctbOldBusinessRequest yctbOldBusinessRequest);

}
