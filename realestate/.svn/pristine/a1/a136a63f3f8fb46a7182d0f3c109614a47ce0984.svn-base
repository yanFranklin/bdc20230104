package cn.gtmap.realestate.exchange.service.impl.inf.yzw;

import cn.gtmap.realestate.common.core.domain.exchange.yzw.InfApplyDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.mapper.sjpt.InfApplyMapper;
import cn.gtmap.realestate.exchange.service.inf.yzw.YzwShareService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/4
 * @description
 */
@Service("yzwShareServiceImpl_standard")
public class YzwShareServiceImpl implements YzwShareService {


    @Autowired
    private InfApplyMapper infApplyMapper;


    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        set.add(CommonConstantUtils.SYSTEM_VERSION_STD);
        return set;
    }

    @Override
    public String makeCaseNo(InfApplyDO infApplyDO) {
        StringBuffer sb = new StringBuffer();
        sb.append(infApplyDO.getDeptYwRegNo());
        sb.append(DateUtil.formateTimeYmd(new Date()));
        String xh = infApplyMapper.getYzwBhSeq().toString();
        /**
         * @description 大于4位截取，小于4位补齐
         */
        if (xh.length()>4){
            xh = xh.substring(xh.length() - 4, xh.length());
        }else{
            xh = String.format("%04d", Integer.valueOf(xh));
        }
        sb.append(xh);
        return sb.toString();
    }
}
