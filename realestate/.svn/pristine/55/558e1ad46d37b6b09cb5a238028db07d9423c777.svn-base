package cn.gtmap.realestate.exchange.service.impl.inf.nantong;

import cn.gtmap.realestate.common.core.domain.BdcCshFwkgSlDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hyxx.response.XgbmHyxxResponseCxjg;
import cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hyxx.response.XgbmHyxxResponseDTO;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-08-05
 * @description 南通地区 与招商银行对接功能
 */
@Service
public class NtZsyhService {

    @Autowired
    private CommonService commonService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    /**
     * @param processInsId
     * @return
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     */
    public Object tsxxByGzlslidAndValid(String processInsId, String msg) {
        JSONObject param = new JSONObject();
        if (StringUtils.isNotBlank(processInsId)) {
            List<BdcXmDO> bdcXmDOList = commonService.listBdcXmByGzlslid(processInsId);
            if (CollectionUtils.isNotEmpty(bdcXmDOList) && isZsyhLc(bdcXmDOList.get(0))) {
                param.put("centerNumber", bdcXmDOList.get(0).getSlbh());
                param.put("actionTime", FastDateFormat.getInstance("yyyyMMdd HH:mm:ss").format(new Date()));
                param.put("message", msg);
                param.put("result", "2");
                BdcCshFwkgSlDO bdcCshFwkgSlDO = bdcXmFeignService.queryFwkgsl(bdcXmDOList.get(0).getXmid());
                if (bdcCshFwkgSlDO != null && CommonConstantUtils.SF_S_DM.equals(bdcCshFwkgSlDO.getSfscql())) {
                    return exchangeBeanRequestService.request("nt_zsyhdyzxxx_sc", param);
                } else {
                    return exchangeBeanRequestService.request("nt_zsyhdyxx_sc", param);
                }
            }
        }
        return new Object();
    }

    /**
     * 根据受理编号判断是否是招商银行
     *
     * @param slbh
     * @return
     */
    private boolean isZsyhLc(BdcXmDO bdcXmDO) {
        if (bdcXmDO == null || StringUtils.isBlank(bdcXmDO.getSlbh())) {
            return false;
        }
        return (bdcXmDO.getSlbh().indexOf(CommonConstantUtils.ZSYH) > -1) && CommonConstantUtils.QLLX_DYAQ_DM.equals(bdcXmDO.getQllx());
    }


}
