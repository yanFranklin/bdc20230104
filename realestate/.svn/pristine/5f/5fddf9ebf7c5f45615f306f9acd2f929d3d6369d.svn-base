package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.enums.BdcSdqRqfwbsmEnum;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @version 1.0   sdq工具类
 * @description
 * @modify 可以从配置中判定
 */
@Component
public class SdqUtils {
    //指定当前的燃气类型
    @Value("#{${sdq.qi.rqlxzd.wn: null}}")
    private Map<String, String> rqlxzdwn;

    @Value("#{${sdq.qi.rqlxzd.hf: null}}")
    private Map<String, String> rqlxzdhf;


    /**
     * 获取燃气服务标识码
     *
     * @param qjgldm
     * @param rqlx
     * @return
     */
    public String getRqfwBean(String qjgldm, String rqlx) {
        if (rqlx.equals(BdcSdqRqfwbsmEnum.HFRQ.key()) && MapUtils.isNotEmpty(rqlxzdhf) && rqlxzdhf.containsKey(qjgldm)) {
            return rqlxzdhf.get(qjgldm);
        }

        if (rqlx.equals(BdcSdqRqfwbsmEnum.WNRQ.key()) && MapUtils.isNotEmpty(rqlxzdwn) && rqlxzdwn.containsKey(qjgldm)) {
            return rqlxzdhf.get(qjgldm);
        }

        return null;
    }
}