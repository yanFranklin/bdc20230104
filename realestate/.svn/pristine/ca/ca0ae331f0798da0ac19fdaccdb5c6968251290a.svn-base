package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzZzxxDO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/2/19
 */
public interface BdcDzzzAsyncService {

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param bdcDzzzZzxxDO
     * @description 每天定时检查电子证照未生成PDF数据
     */
    void zzxxIncrement(BdcDzzzZzxxDO bdcDzzzZzxxDO);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param zzbs
     * @description 市级同步电子证照
     */
    void syncDzzz(String zzbs);

    /**
     * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
     * @param bdcDzzzZzxxDO
     * @description 根据zzbs更新证照信息表
     */
    void updateDzzzByZzbs(BdcDzzzZzxxDO bdcDzzzZzxxDO);

    /**
    * 功能描述: <br>
    * @Author: @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
    * @Param: [list]
    * @return: void
    * @Date: 2020/9/7
    * @Description:  市级同步电子证照
    */
    void syncDzzz( List<Map<String,Object>> list);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @param zzbs
     * @description 市级同步电子证照加注件信息
     */
    void syncDzzzDownloadInfo(String zzbs);
}
