package cn.gtmap.realestate.exchange.service.inf.log;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-16
 * @description 第三方子系统记录日志服务
 */
public interface BdcDsfLogService {

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param pageable, gxbmbz, jkmc, slbh, xmid, bdcdz, dsfdz, kssj, jssj
     * @return java.lang.Object
     * @description
     */
    Object listBdcDsfRzByPage(Pageable pageable, Map<String,Object> paramMap);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description 第三方日志各系统请求统计
     */
    JSONObject countBdcDsfLogByGxbmbz(Map paramMap);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description 第三方日志各系统调用趋势统计
     */
    JSONObject countBdcDsfLogXtdyqs(Map paramMap);

    /**
     * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
     * @return
     * @description 第三方日志各系统二级各系统调用量柱状图
     */
    JSONObject countGxtDymx(Map paramMap);

    Object countZj(Map paramMap);

    Object countMx(Map paramMap);
}
