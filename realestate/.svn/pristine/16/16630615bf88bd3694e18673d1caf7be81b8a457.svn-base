package cn.gtmap.realestate.init.service.tshz.bengbu;

import cn.gtmap.realestate.common.core.domain.BdcDyaqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 蚌埠权利相关默认值处理
 *
 * @author <a href="mailto:zhuyong@gtmap.com">zhuyong</a>
 * @version 2021/06/02
 */
@Service("bdcQlMrzServiceImpl")
public class InitBdcQlMrzServiceImpl implements InitBdcTsHzService {
    private static final Logger logger = LoggerFactory.getLogger(InitBdcQlMrzServiceImpl.class);

    /**
     * 需要设置默认值的流程定义ID
     */
    @Value("#{'${qlxx.mrz.gzldyids:}'.split(',')}")
    private List<String> gzldyids;

    /**
     * 担保范围默认值
     */
    @Value("${qlxx.mrz.dbfw:}")
    private String dbfw;

    /**
     * 禁止转让默认值
     */
    @Value("${qlxx.mrz.jzzr:}")
    private Integer jzzr;

    /**
     * 抵押范围默认值
     */
    @Value("${qlxx.mrz.dyfw:}")
    private String dyfw;

    /*
     * 预告登记种类
     * */
    @Value("#{'${qlxx.mrz.ygdjzl:3}'.split(',')}")
    private List<Integer> ygdjzl;


    /**
     * 抵押流程和预抵押流程：是否存在禁止或限制转让抵押不动产的约定、担保范围 字段默认值处理
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        logger.info("抵押流程和预抵押流程默认值处理");

        if (null != initServiceDTO && null != initServiceDTO.getBdcQl() && containsProcess(initServiceDTO)) {
            BdcQl bdcQl = initServiceDTO.getBdcQl();
            if(bdcQl instanceof BdcDyaqDO) {
                BdcDyaqDO dyaqDO = (BdcDyaqDO) initServiceDTO.getBdcQl();
                if(StringUtils.isBlank(dyaqDO.getDbfw())) {
                    dyaqDO.setDbfw(dbfw);
                }
                if(null == dyaqDO.getJzzr()){
                    dyaqDO.setJzzr(jzzr);
                }
                if(StringUtils.isNotBlank(dyfw) && StringUtils.isBlank(dyaqDO.getDyfw())){
                    dyaqDO.setDyfw(dyfw);
                }
            }

            if(bdcQl instanceof BdcYgDO) {
                BdcYgDO bdcYgDO = (BdcYgDO) initServiceDTO.getBdcQl();
                if (null != bdcYgDO.getYgdjzl() && ygdjzl.contains(bdcYgDO.getYgdjzl())) {
                    if(StringUtils.isBlank(bdcYgDO.getDbfw())) {
                        bdcYgDO.setDbfw(dbfw);
                    }
                    if(null == bdcYgDO.getJzzr()){
                        bdcYgDO.setJzzr(jzzr);
                    }
                }
            }
        }
        return initServiceDTO;
    }

    private boolean containsProcess(InitServiceDTO initServiceDTO) {
        return CollectionUtils.isNotEmpty(gzldyids)
                && null != initServiceDTO.getBdcXm()
                && gzldyids.contains(initServiceDTO.getBdcXm().getGzldyid());
    }
}
