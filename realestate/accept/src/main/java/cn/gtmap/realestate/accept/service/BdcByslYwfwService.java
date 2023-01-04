package cn.gtmap.realestate.accept.service;

import cn.gtmap.realestate.common.core.dto.BdcPdfDTO;

/**
 * @program: realestate
 * @description: 不予受理服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-08-24 09:23
 **/
public interface BdcByslYwfwService {

    /**
     * @param gzlslid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 推送不予受理信息到签章台账
     * @date : 2022/8/24 9:24
     */
    void pushBysldjToDzqz(String gzlslid);


    /**
     * @param bdcPdfDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 更新不予受理流程的附件信息
     * @date : 2022/8/25 11:18
     */
    void updateByslFjxx(BdcPdfDTO bdcPdfDTO);
}
