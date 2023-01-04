package cn.gtmap.realestate.init.service.tshz.nantong;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.MapUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 南通组合贷款处理贷款方式字段
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
@Service("bdcDkfsServiceImpl_nantong")
@ConfigurationProperties(prefix = "nttscl")
public class InitBdcDkfsServiceImpl implements InitBdcTsHzService {

    /**
     * 获取贷款方式的配置
     */
    private Map<String, String> dkfs;

    /**
     * 特殊后置处理
     *
     * @param initServiceQO
     * @param initServiceDTO
     * @return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        if (initServiceDTO != null && initServiceDTO.getBdcQl() instanceof BdcDyaqDO) {
            if (MapUtils.getString(dkfs, initServiceDTO.getBdcXm().getDjxl()) != null) {
                ((BdcDyaqDO) initServiceDTO.getBdcQl()).setDkfs(MapUtils.getString(dkfs, initServiceDTO.getBdcXm().getDjxl()));
            }
        }
        return initServiceDTO;
    }

    public Map<String, String> getDkfs() {
        return dkfs;
    }

    public void setDkfs(Map<String, String> dkfs) {
        this.dkfs = dkfs;
    }
}
