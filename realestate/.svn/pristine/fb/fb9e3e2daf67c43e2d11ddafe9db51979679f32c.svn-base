package cn.gtmap.realestate.common.core.service.rest.inquiry.changzhou;

import cn.gtmap.realestate.common.core.qo.inquiry.BdcCxzmdPdfQO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcCxzmdPdfVO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcStfzmCxPdfVO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcdjbcxPdfVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @date 2020/8/25 14:29
 * @description 常州查询证明接口处理
 */
public interface BdcXxcxRestService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 查询权利人房产证明等内容并生成对应PDF文件
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/changzhou/cxzm/pdf")
    BdcCxzmdPdfVO generateCxzmdPdf(@RequestBody BdcCxzmdPdfQO bdcCxzmdPdfQO);


    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 汇总表
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/changzhou/cxzm/hz/pdf")
    BdcCxzmdPdfVO generateCxzmdHzPdf(@RequestBody BdcCxzmdPdfQO bdcCxzmdPdfQO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 明细表
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/changzhou/cxzm/mx/pdf")
    BdcCxzmdPdfVO generateCxzmdMxPdf(@RequestBody BdcCxzmdPdfQO bdcCxzmdPdfQO);

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 房屋套次查询pdf接口
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/changzhou/cxzm/stfzm/pdf")
    BdcStfzmCxPdfVO generateStfzmPdf(@RequestBody BdcCxzmdPdfQO bdcCxzmdPdfQO);

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回不动产登记簿查询PDF数据
     * @description 不动产登记簿查询pdf接口
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/changzhou/cxzm/bdcdjbcx/pdf")
    BdcdjbcxPdfVO generateBdcdjbcxPdf(@RequestBody BdcCxzmdPdfQO bdcCxzmdPdfQO);

}
