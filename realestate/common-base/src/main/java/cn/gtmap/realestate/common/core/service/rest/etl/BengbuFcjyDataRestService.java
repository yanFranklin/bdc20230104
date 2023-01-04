package cn.gtmap.realestate.common.core.service.rest.etl;

import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlClfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaListResponseDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.dto.etl.EtlZydjCxMfjyxxResponseDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.TransactionInquiryQO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-04-26
 * @description 一窗受理共享业务
 */
public interface BengbuFcjyDataRestService {

    /**
     * @param contractNo 工作流实例id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据合同编号查询存量房合同信息
     */
    @PostMapping("/realestate-etl/rest/v1.0/bengbu/fcjy/clfhtxx")
    EtlClfHtbaResponseDTo clfHtxx(@RequestParam(value = "contractNo", required = false) String contractNo, @RequestParam(value = "yjybh", required = false) String yjybh, @RequestParam(value = "fwbm", required = false) String fwbm);

    /**
     * @param contractNo 工作流实例id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据合同编号查询商品房合同信息
     */
    @PostMapping("/realestate-etl/rest/v1.0/bengbu/fcjy/spfhtxx")
    EtlSpfHtbaResponseDTo spfHtxx(@RequestParam(value = "contractNo", required = false) String contractNo, @RequestParam(value = "fwbm", required = false) String fwbm);

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/10
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     */
    @PostMapping("/realestate-etl/rest/v1.0/bengbu/fcjy/spfhtxxForCf")
    EtlSpfHtbaResponseDTo spfHtxxForCfck(@RequestBody TransactionInquiryQO transactionInquiryQO);

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/10
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     */
    @PostMapping("/realestate-etl/rest/v1.0/bengbu/fcjy/clfhtxxForCf")
    EtlClfHtbaResponseDTo clfHtxxForCfck(@RequestBody TransactionInquiryQO transactionInquiryQO);

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/10
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     */
    @PostMapping("/realestate-etl/rest/v1.0/bengbu/fcjy/list/spfhtxxForCf")
    EtlSpfHtbaListResponseDTO listSpfHtxxForCfck(Pageable pageable, @RequestParam(name = "transactionInquiryQOJSON", required = false) String transactionInquiryQOJSON);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcdyh
     * @return
     * @description 增量房转移登记根据不动产单元号查询买方交易信息
     */
    @GetMapping("/realestate-etl/rest/v1.0/bengbu/fcjy/zlfCxmfjyxx/{bdcdyh}")
    FcjyBaxxDTO zlfCxmfjyxx(@PathVariable(name = "bdcdyh") String bdcdyh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param cqzh
     * @return
     * @description 存量房转移登记根据不动产单元号查询买方交易信息
     */
    @GetMapping("/realestate-etl/rest/v1.0/bengbu/fcjy/clfCxmfjyxx/{cqzh}")
    FcjyBaxxDTO clfCxmfjyxx(@PathVariable(name = "cqzh") String cqzh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param htbh
     * @param type 1：一手房 2：二手房
     * @return
     * @description 通过htbh去交易SqlServer库htba表中查获取买方交易信息
     */
    @GetMapping("/realestate-etl/rest/v1.0/bengbu/fcjy/cxmfjyxxByHtbh/{htbh}/{type}")
    FcjyBaxxDTO cxmfjyxxByHtbh(@PathVariable(name = "htbh") String htbh,@PathVariable(name = "type") String type);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcdyh
     * @return
     * @description 增量房“删除”或者“撤销时告知房产交易处该合同解除限制
     */
    @GetMapping("/realestate-etl/rest/v1.0/bengbu/fcjy/zlfca/{bdcdyh}")
    FcjyBaxxDTO zlfca(@PathVariable(name = "bdcdyh") String bdcdyh);
}
