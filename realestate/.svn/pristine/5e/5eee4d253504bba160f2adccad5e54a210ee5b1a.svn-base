package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.other.InitServiceQoService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 土地评估价格读取
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/8/29.
 * @description
 */
@Service("bdcTdpgjgServiceImpl_nantong")
@ConfigurationProperties(prefix = "djxl")
public class InitBdcTdpgjgServiceImpl implements InitBdcTsHzService {
    private List<String> pgjgjc;
    @Autowired
    private InitServiceQoService initServiceQoService;
    /**
     * 土地评估价格读取
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //属于登记小类的做处理
        if(!initServiceQO.isSfzqlpbsj() && initServiceDTO!=null && initServiceDTO.getBdcQl() instanceof BdcDyaqDO
                && ((BdcDyaqDO) initServiceDTO.getBdcQl()).getTdpgjg()== null && StringUtils.isNotBlank(initServiceQO.getYxmid()) && initServiceDTO.getBdcXm()!=null){
            if(CollectionUtils.isNotEmpty(pgjgjc) && pgjgjc.contains(initServiceDTO.getBdcXm().getDjxl())){
                BdcQl yql=initServiceQoService.queryYql(initServiceQO.getYxmid(),initServiceQO);
                if(yql instanceof BdcFdcqDO){
                    //获取
                    ((BdcDyaqDO) initServiceDTO.getBdcQl()).setTdpgjg(((BdcFdcqDO) yql).getJyjg());
                }
            }
        }
        return initServiceDTO;
    }

    public List<String> getPgjgjc() {
        return pgjgjc;
    }

    public void setPgjgjc(List<String> pgjgjc) {
        this.pgjgjc = pgjgjc;
    }
}
