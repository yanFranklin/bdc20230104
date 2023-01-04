package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 续封数据处理
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/7/30.
 * @description
 */
@Service("bdcXfsjclServiceImpl_nantong")
@ConfigurationProperties(prefix = "djxl")
public class InitBdcXfsjclServiceImpl implements InitBdcTsHzService {
    private List<String> sfcd;
    @Autowired
    private InitServiceQoService initServiceQoService;

    /**
     * 特殊后置处理
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if(!initServiceQO.isSfzqlpbsj() &&  initServiceDTO!=null && initServiceDTO.getBdcQl() instanceof BdcCfDO){
            BdcCfDO bdcCfDO= (BdcCfDO) initServiceDTO.getBdcQl();
            if(CollectionUtils.isNotEmpty(sfcd) && sfcd.contains(initServiceQO.getBdcXm().getDjxl())){
                bdcCfDO.setCfqssj(new Date());
                bdcCfDO.setCfwh(null);
                bdcCfDO.setCflx(CommonConstantUtils.CFLX_CF);
            }else if (CommonConstantUtils.CFLX_LHCF.equals(bdcCfDO.getCflx()) || CommonConstantUtils.CFLX_LHYCF.equals(bdcCfDO.getCflx())) {
                // 南通 轮候查封去除默认填写查封开始时间和查封结束时间的逻辑
                bdcCfDO.setCfqssj(null);
                bdcCfDO.setCfjssj(null);
            }else if(CommonConstantUtils.CFLX_XF.equals(bdcCfDO.getCflx())){
                //改变查封类型
                bdcCfDO.setCflx(CommonConstantUtils.CFLX_CF);
                //续封的项目备注处理
                if(StringUtils.isNotBlank(initServiceQO.getYxmid())){
                    BdcQl bdcQl=initServiceQoService.queryYql(initServiceQO.getYxmid(),initServiceQO);
                    if(bdcQl instanceof BdcCfDO){
                        bdcCfDO.setCfqssj(((BdcCfDO) bdcQl).getCfqssj());
                        StringBuilder stringBuilder=new StringBuilder();
                        BdcXmDO bdcXmDO=initServiceDTO.getBdcXm();
                        if(bdcXmDO!=null){
                            if(StringUtils.isNotBlank(bdcXmDO.getBz())){
                                stringBuilder.append(bdcXmDO.getBz()).append("\n");
                            }
                            stringBuilder.append("针对").append(((BdcCfDO) bdcQl).getCfwh()).append("文号续封");
                            bdcXmDO.setBz(stringBuilder.toString());
                        }
                    }
                }
                //续封从当前时间+3年
                bdcCfDO.setCfjssj(DateUtils.addDays(DateUtils.addYears(new Date(),3),-1));
                return initServiceDTO;
            }
            //其他的查封登记根据起始时间做+3年
            if(bdcCfDO.getCfqssj()!=null){
                bdcCfDO.setCfjssj(DateUtils.addDays(DateUtils.addYears(bdcCfDO.getCfqssj(),3),-1));
            }
        }
        return initServiceDTO;
    }

    public List<String> getSfcd() {
        return sfcd;
    }

    public void setSfcd(List<String> sfcd) {
        this.sfcd = sfcd;
    }
}
