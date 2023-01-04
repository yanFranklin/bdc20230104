package cn.gtmap.realestate.init.service.qlxx.qlfl;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.service.BdcCfService;
import cn.gtmap.realestate.init.service.qlxx.InitBdcQlDataAbstractService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 初始化查封信息
 *
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/10/31.
 * @description
 */
public abstract class InitBdcCfAbstractService extends InitBdcQlDataAbstractService {
    @Autowired
    public BdcCfService bdcCfService;


    /**
     * 处理查封结束时间
     * @param bdcCfDO
     * @return
     */
    public BdcCfDO dealCfjssj(BdcCfDO bdcCfDO) {
        //查封登记根据起始时间做+3年
        //续封登记查封结束时间不做+3年
        if(bdcCfDO!=null && !CommonConstantUtils.CFLX_XF.equals(bdcCfDO.getCflx()) && bdcCfDO.getCfqssj()!=null && bdcCfDO.getCfjssj()==null){
            bdcCfDO.setCfjssj(DateUtils.addDays(DateUtils.addYears(bdcCfDO.getCfqssj(),3),-1));
        }
        return bdcCfDO;
    }
}
