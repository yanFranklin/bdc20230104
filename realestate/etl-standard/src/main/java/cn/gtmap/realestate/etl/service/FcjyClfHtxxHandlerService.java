package cn.gtmap.realestate.etl.service;


import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlClfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaListResponseDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.dto.etl.EtlZydjCxMfjyxxResponseDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.TransactionInquiryQO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
 * @version 2020/05/11,1.0
 * @description 蚌埠交易库数据读取
 */
public interface FcjyClfHtxxHandlerService {


    EtlClfHtbaResponseDTo clfHtxx(String contractNo, String yjybh, String fwbm);

    /**
     * @param contractNo 工作流实例id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据合同编号或者fwbm查询商品房合同信息
     */
    EtlSpfHtbaResponseDTo spfHtxx(String contractNo, String fwbm);

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/10
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     */
    EtlSpfHtbaResponseDTo spfHtxxForCf(@RequestBody TransactionInquiryQO transactionInquiryQO);

    /**
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/10
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     */
    EtlClfHtbaResponseDTo clfHtxxForCf(@RequestBody TransactionInquiryQO transactionInquiryQO);

    /**
     * @author <a href="mailto:zqdeqiang@gtmap.cn">zedq</a>
     * @Date 2020/11/03
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     */
    EtlSpfHtbaListResponseDTO listSpfHtxxForCf(Pageable pageable, TransactionInquiryQO transactionInquiryQO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcdyh
     * @return
     * @description 增量房转移登记根据不动产单元号查询买方交易信息
     */
    FcjyBaxxDTO zlfCxmfjyxx(@PathVariable(name = "bdcdyh") String bdcdyh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param cqzh
     * @return
     * @description 存量房转移登记根据不动产单元号查询买方交易信息
     */
    FcjyBaxxDTO clfCxmfjyxx(@PathVariable(name = "cqzh") String cqzh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param htbh
     * @return
     * @description 通过htbh去交易SqlServer库htba表中查获取买方交易信息
     */
    FcjyBaxxDTO cxmfjyxxByHtbh(@PathVariable(name = "htbh") String htbh,@PathVariable(name = "type") String type);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcdyh
     * @return
     * @description 增量房“删除”或者“撤销时告知房产交易处该合同解除限制
     */
    FcjyBaxxDTO zlfca(@PathVariable(name = "bdcdyh") String bdcdyh);





}
