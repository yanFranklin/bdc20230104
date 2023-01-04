package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.*;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.jsfxx.request.JfsxxHlwRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.jsfxx.response.JfsxxHlwResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2019-09-17
 * @description 南通市财政局非税收入系统支付服务
 */
public interface NantongFsxtzfRestService {

    /**
      * 互联网创建缴费书
      * @param requestDTO
      * @return
      * @Date 2021/8/5
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
         */
    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/hlwCreatebill")
    JfsxxHlwResponse createBillForHlw(@RequestBody JfsxxHlwRequestDTO requestDTO);


    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @param info
     * @return java.lang.Object
     * @description 单位新增缴费书接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/createbill")
    String createBill(@RequestBody JfsxxRequestDTO info);

    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @param msg
     * @return java.lang.Object
     * @description 单位新增缴费书接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/payResult")
    Object payResult(@RequestParam(value = "MSG")String msg);
    /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param invoiceRequestDTO
     * @return java.lang.Object
     * @description 3.1 获取电子票据信息接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/invoiceBeforIssue")
    Object invoiceBeforIssue(@RequestBody InvoiceRequestDTO invoiceRequestDTO);
   /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param invoiceRequestDTO
     * @return java.lang.Object
     * @description 3.2 提交电子票据开具接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/invoiceHandleIssue")
    Object invoiceHandleIssue(@RequestBody InvoiceRequestDTO invoiceRequestDTO);
    /**
     * @author <a href="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @param invoiceRequestDTO
     * @return java.lang.Object
     * @description 3.3下载电子票据信息接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/invoiceDownload")
    Object invoiceDownload(@RequestBody InvoiceRequestDTO invoiceRequestDTO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param voidPaymentFormRequestDTO
     * @return java.lang.Object
     * @description 2.8作废缴费书
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/voidPaymentForm")
    Object voidPaymentForm(@RequestBody VoidPaymentFormRequestDTO voidPaymentFormRequestDTO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param obtainRedemptionTicketInfoRequestDTO
     * @return java.lang.Object
     * @description 3.3获取冲红票据信息
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/obtainRedemptionTicketInfo")
    Object obtainRedemptionTicketInfo(@RequestBody ObtainRedemptionTicketInfoRequestDTO obtainRedemptionTicketInfoRequestDTO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param handleRedemptionTicketInfoRequestDTO
     * @return java.lang.Object
     * @description 3.4提交冲红票据开具
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/handleRedemptionTicketInfo")
    Object handleRedemptionTicketInfo(@RequestBody HandleRedemptionTicketInfoRequestDTO handleRedemptionTicketInfoRequestDTO);

    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/updateJfztForHlw")
    void updateJfztForHlw(@RequestParam(value = "sfxxid") String sfxxid) ;

    /**
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @param info
     * @return java.lang.Object
     * @description 税费同缴新增缴费书接口
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/createSftjBill")
    String createSftjBill(@RequestBody JfsxxRequestDTO info);

    /**
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @param msg
     * @return java.lang.Object
     * @description 税费同缴支付结果通知接口，此接口只用于更新税费状态，登记费状态，在etl中更新
     */
    @PostMapping("/realestate-exchange/rest/v1.0/ntfssr/sftjPayResult")
    Object sftjPayResult(@RequestParam(value = "MSG")String msg);
}
