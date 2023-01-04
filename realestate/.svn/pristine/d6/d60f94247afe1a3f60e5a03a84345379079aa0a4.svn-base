package cn.gtmap.realestate.exchange.service.nantong;

import cn.gtmap.realestate.common.core.domain.CommonResponse;

/**
 * @author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @date 2022/4/26
 * @description
 */
public interface NantongSqService {
    void qgh(String processInsId);

    void sgh(String processInsId);

    CommonResponse<Integer> sghjc(String processInsId, String consno, String dwdm);

    CommonResponse<Integer> qghjc(String processInsId, String consno);

    /**
     * 推送不动产登记数据至自来水公司
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    CommonResponse bdcSdqZlsTs(String processInsId);
}
