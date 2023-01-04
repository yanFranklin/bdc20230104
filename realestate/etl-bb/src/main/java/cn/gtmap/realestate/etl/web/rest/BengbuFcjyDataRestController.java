package cn.gtmap.realestate.etl.web.rest;

import cn.gtmap.realestate.common.core.dto.accept.FcjyBaxxDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlClfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaListResponseDTO;
import cn.gtmap.realestate.common.core.dto.etl.EtlSpfHtbaResponseDTo;
import cn.gtmap.realestate.common.core.qo.inquiry.TransactionInquiryQO;
import cn.gtmap.realestate.common.core.service.rest.etl.BengbuFcjyDataRestService;
import cn.gtmap.realestate.etl.service.FcjyClfHtxxHandlerService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
 * @version 2020/0426,1.0
 * @description
 */
@RestController
@Api(tags = "蚌埠交易库数据读取")
public class BengbuFcjyDataRestController implements BengbuFcjyDataRestService {

    @Autowired
    FcjyClfHtxxHandlerService fcjyClfHtxxHandlerService;

    private static Logger LOGGER = LoggerFactory.getLogger(BengbuFcjyDataRestController.class);

    /**
     * @param contractNo 工作流实例id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据合同编号查询存量房合同信息
     */
    @Override
    public EtlClfHtbaResponseDTo clfHtxx(@RequestParam(value = "contractNo",required = false) String contractNo,@RequestParam(value = "yjybh",required = false) String yjybh,@RequestParam(value = "fwbm",required = false) String fwbm) {
        return fcjyClfHtxxHandlerService.clfHtxx(contractNo,yjybh,fwbm);
    }

    /**
     * @param contractNo 工作流实例id
     * @return void
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 根据合同编号查询商品房合同信息
     */
    @Override
    public EtlSpfHtbaResponseDTo spfHtxx(@RequestParam(value = "contractNo",required = false) String contractNo,@RequestParam(value = "fwbm",required = false) String fwbm) {
        return fcjyClfHtxxHandlerService.spfHtxx(contractNo,fwbm);
    }

    /**
     * @param transactionInquiryQO
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/10
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     */
    @Override
    public EtlSpfHtbaResponseDTo spfHtxxForCfck(@RequestBody TransactionInquiryQO transactionInquiryQO) {
        return fcjyClfHtxxHandlerService.spfHtxxForCf(transactionInquiryQO);
    }

    /**
     * @param transactionInquiryQO
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/10
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     */
    @Override
    public EtlClfHtbaResponseDTo clfHtxxForCfck(@RequestBody TransactionInquiryQO transactionInquiryQO) {
        return fcjyClfHtxxHandlerService.clfHtxxForCf(transactionInquiryQO);
    }

    /**
     * @param pageable
     * @param transactionInquiryQOJSON
     * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
     * @Date 2020/10/10
     * @description 为查封窗口提供合同查询服务，查询参数为：合同编号，姓名，证件号，坐落
     */
    @Override
    public EtlSpfHtbaListResponseDTO listSpfHtxxForCfck(Pageable pageable, @RequestParam(name = "transactionInquiryQOJSON", required = false) String transactionInquiryQOJSON) {
        if (StringUtils.isNoneBlank(transactionInquiryQOJSON)) {
            return fcjyClfHtxxHandlerService.listSpfHtxxForCf(pageable, JSON.parseObject(transactionInquiryQOJSON, TransactionInquiryQO.class));
        }
        return null;
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcdyh
     * @return
     * @description 增量房转移登记根据不动产单元号查询买方交易信息
     */
    @Override
    public FcjyBaxxDTO zlfCxmfjyxx(@PathVariable(name = "bdcdyh") String bdcdyh) {
        return fcjyClfHtxxHandlerService.zlfCxmfjyxx(bdcdyh);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param cqzh
     * @return
     * @description 存量房转移登记根据不动产单元号查询买方交易信息
     */
    @Override
    public FcjyBaxxDTO clfCxmfjyxx(@PathVariable(name = "cqzh") String cqzh) {
        return fcjyClfHtxxHandlerService.clfCxmfjyxx(cqzh);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param htbh
     * @return
     * @description 通过htbh去交易SqlServer库htba1表中查获取买方交易信息
     */
    @Override
    public FcjyBaxxDTO cxmfjyxxByHtbh(@PathVariable(name = "htbh") String htbh,@PathVariable(name = "type") String type) {
        return fcjyClfHtxxHandlerService.cxmfjyxxByHtbh(htbh,type);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcdyh
     * @return
     * @description 增量房“删除”或者“撤销时告知房产交易处该合同解除限制
     */
    @Override
    public FcjyBaxxDTO zlfca(@PathVariable(name = "bdcdyh") String bdcdyh) {
        return fcjyClfHtxxHandlerService.zlfca(bdcdyh);
    }


}
