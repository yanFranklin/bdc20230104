package cn.gtmap.realestate.inquiry.service.changzhou;

import cn.gtmap.realestate.common.core.qo.inquiry.BdcCxzmdPdfQO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcCxzmdPdfVO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcStfzmCxPdfVO;
import cn.gtmap.realestate.common.core.vo.inquiry.BdcdjbcxPdfVO;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @date 2020/8/25 15:10
 * @description 常州查询证明接口处理
 */
public interface BdcXxcxService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 查询权利人房产证明等内容并生成对应PDF文件
     */
    BdcCxzmdPdfVO generateCxzmdPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 查询权利人房产证明等内容并生成对应汇总PDF文件
     */
    BdcCxzmdPdfVO generateCxzmdHzPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 查询权利人房产证明等内容并生成对应明细PDF文件
     */
    BdcCxzmdPdfVO generateCxzmdMxPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO);

    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回不动产登记簿查询PDF数据
     * @description 不动产登记簿查询pdf接口
     */
    BdcdjbcxPdfVO generateBdcdjbcxPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO);
    /**
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @param bdcCxzmdPdfQO 查询参数
     * @return {BdcCxzmdPdfVO} 返回PDF数据
     * @description 房屋套次查询pdf接口
     */
    BdcStfzmCxPdfVO generateStfzmPdf(BdcCxzmdPdfQO bdcCxzmdPdfQO);
}
