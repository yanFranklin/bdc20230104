package cn.gtmap.realestate.exchange.service.inf.sjpt;

import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeRz;
import cn.gtmap.realestate.exchange.util.enums.SjptRzEnum;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-23
 * @description 省级平台记录日志
 */
public interface GxRzService {


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @return cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeRz
     * @description 初始化一个日志实体
     */
    GxPeRz initRz();


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gxPeRz
     * @param response
     * @param rzEnum
     * @return void
     * @description 根据相应分析日志
     */
    void anaysisRzByResponse(GxPeRz gxPeRz,Object response, SjptRzEnum rzEnum,Object requestObject);


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gxPeRz
     * @return void
     * @description 保存日志
     */
    void saveRz(GxPeRz gxPeRz);

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param gxPeRz
     * @return void
     * @description 保存失败日志
     */
    void saveFailedRz(GxPeRz gxPeRz, Throwable e);
}
