package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcGxjkPdfDTO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/2/4
 * @description 共享接口服务
 */
public interface BdcGxJkService {

    /**
     * @param dataList 共享接口数据
     * @return redis-key
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  保存共享接口数据到REDIS
     */
    String saveGxjkDataToRedis(List<Map> dataList);

    /**
     * @param bdcGxjkPdfDTO 共享接口PDF实体
     * @return PDF打印数据
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description  获取PDF打印数据
     */
    String getPrintXmlOfGxjk(BdcGxjkPdfDTO bdcGxjkPdfDTO);

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @param bdcGxjkPdfDTO PDF及流程信息
     * @return {String} 文件存储ID
     * @description  保存验证结果文件到大云并关联到当前流程作为附件
     */
    String saveGxjkPdfFile(BdcGxjkPdfDTO bdcGxjkPdfDTO);
}
