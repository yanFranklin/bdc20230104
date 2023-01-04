package cn.gtmap.realestate.register.service;

import cn.gtmap.realestate.common.core.dto.BdcPrintDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/21
 * @description 审核表单打印服务
 */
public interface BdcShbdPrintService {
    /**
     * @param bdcPrintDTO 打印参数
     * @return String 打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取打印的xml
     */
    String bdPrintXml(BdcPrintDTO bdcPrintDTO);

    /**
     * @param gzlslid 工作流实例ID
     * @return Map
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取流程配置的审批表打印类型
     */
    Map<String, List<String>> getShxxDylx(String gzlslid);

    /**
     * @param bdcPrintDTO 打印参数
     * @return String 打印xml
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取打印的xml（南通特供）
     */
    String bdPrintXmlNantong(BdcPrintDTO bdcPrintDTO);

    /**
     * 获取打印数据
     * <p>返回执行好sql的主表与子表数据，其中主表数据中添加了收件材料名称</p>
     * @param bdcPrintDTO 打印所需的参数的实体类
     * @return BdcDysjDTO 打印数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    BdcDysjDTO getPrintDataMap(BdcPrintDTO bdcPrintDTO);
}
