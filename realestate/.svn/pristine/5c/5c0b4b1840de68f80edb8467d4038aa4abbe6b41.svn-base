package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.sjpt.checkopenid.response.CheckOpenidResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.sjpt.sscx.request.SjptSscxRequestHead;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.service.inf.sjpt.OpenIdService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-23
 * @description 省级平台 验证 openid
 */
@Service
public class OpenIdServiceImpl implements OpenIdService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    private static final String CHECK_OPENID_BEANNAME = "check_openid";
    private static final String YC_CHECK_OPENID_BEANNAME = "yc_check_openid";

    // 有效
    private static final String OPENID_VALID_TRUE = "1";

    // 无效
    private static final String OPENID_VALID_FALSE = "0";

    /**
     * 盐城市本级实时查询（精准查询）标识
     */
    @Value("${ycsscx.jkbs:}")
    private String ycsscxJkbns;

    /**
     * @param head head
     * @return
     * @author <a href="mailto:yinyao@gtmap.cn">liyinqiao</a>
     * @description
     */
    @Override
    public Boolean valOpenID(SjptSscxRequestHead head) {
        Boolean boo = false;
        Map<String,String> openIdMap = new HashMap<>();
        openIdMap.put("openid",head.getOpenid());

        CheckOpenidResponseDTO response = null;
        // 盐城增加市本级精准查询，认证需要和省厅分开 by zhuyong 20220418 （需求52436）
        if(isSbjdw(head.getCxjgbs())) {
            response = exchangeBeanRequestService.request(YC_CHECK_OPENID_BEANNAME,openIdMap,CheckOpenidResponseDTO.class);
        } else {
            response = exchangeBeanRequestService.request(CHECK_OPENID_BEANNAME,openIdMap,CheckOpenidResponseDTO.class);
        }

        if(response != null && response.getData() != null
                && OPENID_VALID_TRUE.equals(response.getData().getValid())){
            return true;
        }
        return boo;
    }

    /**
     * 判断当前实时查询是否是市本级单位查询
     * @param cxjgbs 查询机构标识
     * @return {boolean} true: 是  false: 否
     */
    private boolean isSbjdw(String cxjgbs) {
        if(StringUtils.isBlank(cxjgbs) || StringUtils.isBlank(ycsscxJkbns)) {
            return false;
        }

        String[] ycsscxJkbnsArray = ycsscxJkbns.split(CommonConstantUtils.ZF_YW_DH);
        for(String item : ycsscxJkbnsArray) {
            if(StringUtils.equals(item, cxjgbs)) {
                return true;
            }
        }
        return false;
    }
}
