package cn.gtmap.realestate.init.service.tshz.bengbu;

import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.mapper.BdcXmLsgxMapper;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/7.
 * @description
 */
@Service("bdcYsggXmBzServiceImpl_bengbu")
@ConfigurationProperties(prefix = "djxl")
public class InitBdcYsggXmBzServiceImpl implements InitBdcTsHzService {
    private List<String> ysgg;
    @Autowired
    private BdcXmLsgxMapper bdcXmLsgxMapper;
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        // 判断当前不动产单元是否办理遗失公告
        if(CollectionUtils.isNotEmpty(ysgg) && initServiceQO.getBdcXm() != null && StringUtils.isNotBlank(initServiceQO.getYxmid())){
            // 用登记小类和原产权证号为条件查询
            // 当前业务选择的产权证号办理过遗失登记  则增加备注
            Map map=new HashMap<>();
            map.put("djxls",ysgg);
            map.put("yxmid",initServiceQO.getYxmid());
            List<BdcXmLsgxDO> bdcXmLsgxDOList = bdcXmLsgxMapper.queryBdcXmLsgxList(map);
            if(CollectionUtils.isNotEmpty(bdcXmLsgxDOList)){
                if(initServiceDTO.getBdcXm() != null){
                    if(StringUtils.isNotBlank(initServiceDTO.getBdcXm().getBz())){
                        initServiceDTO.getBdcXm().setBz(initServiceDTO.getBdcXm().getBz() +"\n 该产权证已办理遗失公告登记");
                    }else{
                        initServiceDTO.getBdcXm().setBz("该产权证已办理遗失公告登记");
                    }
                }
            }
        }
        return initServiceDTO;
    }

    public List<String> getYsgg() {
        return ysgg;
    }

    public void setYsgg(List<String> ysgg) {
        this.ysgg = ysgg;
    }
}
