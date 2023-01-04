package cn.gtmap.realestate.exchange.service.inf;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDaCxLog;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0
 * @date 2021/4/26 15:23
 */
public interface BdcDaCxService {

    /**
     * @author <a href="mailto:zedeqinag@gtmap.cn">zedq</a>
     */
    int saveBdcDaCxLog( BdcDaCxLog bdcDaCxLog);

    /**
     * @author <a href="mailto:zedeqinag@gtmap.cn">zedq</a>
     */
    int updateBdcDaCxLog( BdcDaCxLog bdcDaCxLog);

    /**
     * @author <a href="mailto:zedeqinag@gtmap.cn">zedq</a>
     */
    CommonResponse queryBdcDaCxLogInfo(String queryId, HttpServletResponse response);

    /**
     * @author <a href="mailto:zedeqinag@gtmap.cn">zedq</a>
     */
    void downloadBdcDaCxLogPdf(String fileId, HttpServletResponse response);
}
