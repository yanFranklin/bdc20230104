package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.exchange.core.dto.wwsq.jtcy.response.WwsqQueryJtcyResponseData;
import cn.gtmap.realestate.exchange.service.inf.wwsq.JtcyService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.enums.YcslJtcygxEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-20
 * @description 外网申请 家庭成员相关服务
 */
@Service
public class JtcyServiceImpl implements JtcyService {

    /**
     * @param jtcyList
     * @return java.util.List<cn.gtmap.realestate.exchange.core.dto.wwsq.jtcy.response.WwsqQueryJtcyResponseData>
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 为互联网+做家庭成员过滤
     */
    @Override
    public List<WwsqQueryJtcyResponseData> filterMainCy(List<WwsqQueryJtcyResponseData> jtcyList,String sqrzjh) {
        List<WwsqQueryJtcyResponseData> filteredJtcyList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(jtcyList) && StringUtils.isNotBlank(sqrzjh)){
            boolean adult = CommonUtil.checkSfcn(sqrzjh);
            for(WwsqQueryJtcyResponseData data:jtcyList){
                if (adult) {
                    if (StringUtils.equals("子女", data.getYhzgx())) {
                        data.setYhzgx(YcslJtcygxEnum.YCSL_JTCY_WCNZNV.getDm());
                        filteredJtcyList.add(data);
                    }
                } else {
                    if (StringUtils.equals("父亲", data.getYhzgx())
                            || StringUtils.equals("母亲", data.getYhzgx())) {
                        data.setYhzgx(YcslJtcygxEnum.YCSL_JTCY_FM.getDm());
                        filteredJtcyList.add(data);
                    } else if (StringUtils.equals("兄弟姐妹", data.getYhzgx())) {
                        data.setYhzgx(YcslJtcygxEnum.YCSL_JTCY_WCNXM.getDm());
                        filteredJtcyList.add(data);
                    }
                }

            }
        }
        return filteredJtcyList;
    }

}
