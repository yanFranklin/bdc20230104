package cn.gtmap.realestate.certificate.service;

import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/17
 * @description 证书打印服务接口
 */
public interface BdcZsPrintService {
    /**
     * @param bdcPrintDTO 证书打印QO
     * @return String 打印的xml字符串
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取项目所有证书的打印xml
     */
    String zsPrintXml(BdcPrintDTO bdcPrintDTO);

    /**
     * @param bdcPrintDTO 证书打印QO
     * @return 单个证书的打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取单个证书的打印xml
     */
    String singleZsPrintXml(BdcPrintDTO bdcPrintDTO);

    /**
     * @param bdcPrintDTO 证书打印QO
     * @return String 打印的xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证书批量打印的xml
     */
    String batchZsPrintXml(BdcPrintDTO bdcPrintDTO);

    /**
     * @param bdcPrintDTO 打印参数
     * @return 打印xml结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取和证书相关的附属清单的打印xml
     */
    String zsFsqdPrintXml(BdcPrintDTO bdcPrintDTO);
}
