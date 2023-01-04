package cn.gtmap.realestate.certificate.core.service.impl;
/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description 
 */

import cn.gtmap.realestate.certificate.core.mapper.BdcDzzzConfigMapper;
import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzConfigDo;
import cn.gtmap.realestate.certificate.core.model.dzzzgl.BdcDzzzZzxx;
import cn.gtmap.realestate.certificate.core.service.BdcDzzzConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BdcDzzzConfigServiceImpl implements BdcDzzzConfigService {
    @Autowired
    private BdcDzzzConfigMapper bdcDzzzConfigMapper;

    @Override
    public BdcDzzzConfigDo queryBdcDzzzConfigDoByDwdm(String dwdm) {
        return bdcDzzzConfigMapper.queryBdcDzzzConfigDoByDwdm(dwdm);
    }

    @Override
    public List<BdcDzzzConfigDo> listBdcDzzzConfig() {
        return bdcDzzzConfigMapper.listBdcDzzzConfig();
    }

    @Override
    public String queryYymcByDwdm(BdcDzzzZzxx bdcDzzzZzxx) {
        String yymc = "";
        if (null != bdcDzzzZzxx) {
            BdcDzzzConfigDo BdcDzzzConfigDo = queryBdcDzzzConfigDoByDwdm(bdcDzzzZzxx.getDwdm());
            if (null != BdcDzzzConfigDo) {
                yymc = BdcDzzzConfigDo.getYymc();
            }
        }
        return yymc;
    }

}
