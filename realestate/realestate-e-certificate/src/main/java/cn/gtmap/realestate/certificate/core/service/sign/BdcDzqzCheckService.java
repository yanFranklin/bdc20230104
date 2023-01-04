package cn.gtmap.realestate.certificate.core.service.sign;

import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;

import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 电子签章验证服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-03-10 10:20
 **/
public interface BdcDzqzCheckService {
    /**
     * @param bdcDzzzZzxx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2022/3/10 10:20
     */
    Map<String, Object> check(BdcDzzzZzxx bdcDzzzZzxx, String checkCode, List<String> resultList);
}
