package cn.gtmap.realestate.exchange.service.inf.yancheng;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.exchange.ntfssr.request.JfsxxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.yancheng.fs.FsczfpxxRquestDTO;

import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 2021-05-11 11:05:54
 * @description (盐城) 非税相关服务处理
 */
public interface BdcFsService {

    /**
     * @param requestJson
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * 开具电子缴款书
     */
    CommonResponse createTaxNotice(FsczfpxxRquestDTO requestJson);

    /**
     * @param requestJson
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * 获取缴费状态
     */
    CommonResponse confirmPayStatus(Map requestJson);

    /**
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * 获取电子票号
     */
    CommonResponse getEticketNum(Map map);

    /**
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * 作废电子缴费书
     */
    CommonResponse invalidate(Map requestJson);

//    /**
//     * @param requestJson
//     * @return
//     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
//     * 根据缴款码获取对应电子票信息
//     */
//    CommonResponse getInvoiceByPaycode(Map requestJson);

    /**
     * @param requestJson
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * 电子票据下载（根据缴款码获取对应电子票）
     */
    CommonResponse downloadInvoice(Map requestJson);

    /**
     * @param requestDTO
     * @return
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * 接收已单位签名的开票数据
     */
    CommonResponse issueInvoice(FsczfpxxRquestDTO requestDTO);
}
