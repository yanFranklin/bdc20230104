package cn.gtmap.realestate.init.service.tshz.changzhou;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 不动产权利默认值设置
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-18 11:09
 **/
@Service("bdcQlxxMrzServiceImpl_changzhou")
public class InitBdcQlMrzServiceImpl implements InitBdcTsHzService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitBdcQlMrzServiceImpl.class);

    @Value("#{'${spf.mrz.djxls:}'.split(',')}")
    private List<String> qlMrzList;

    @Value("#{'${spf.mrz.gzldyid:}'.split(',')}")
    private List<String> gzldyidList;

    @Value("${spf.cqlymrz:28}")
    private String cqlyMrz;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    /**
     * 特殊后置处理
     *
     * @param initServiceQO
     * @param initServiceDTO *@return InitServiceDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        //根据配置的登记小类设置取得方式和房屋类型的默认值
        if (initServiceDTO != null && CollectionUtils.isNotEmpty(qlMrzList) && CollectionUtils.isNotEmpty(gzldyidList) && qlMrzList.contains(initServiceDTO.getBdcXm().getDjxl()) && gzldyidList.contains(initServiceDTO.getBdcXm().getGzldyid())) {
            if (StringUtils.isBlank(initServiceDTO.getBdcXmFb().getCqly())) {
                initServiceDTO.getBdcXmFb().setCqly(cqlyMrz);
            }
            //房屋类型赋值
            if (Objects.nonNull(initServiceDTO.getBdcXm().getDzwyt())) {
                BdcQl bdcQl = initServiceDTO.getBdcQl();
                if (bdcQl instanceof BdcFdcqDO) {
                    BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                    //获取规划用途
                    List<Map> fwytZdList = bdcZdFeignService.queryBdcZd("fwyt");
                    String ghytmc = StringToolUtils.convertBeanPropertyValueOfZd(bdcFdcqDO.getGhyt(), fwytZdList);
                    //根据规划用途的名称判断房屋类型
                    if (StringUtils.isNotBlank(ghytmc)) {
                        LOGGER.warn("当前项目id{}规划用途名称{}", initServiceDTO.getBdcXm().getXmid(), ghytmc);
                        if (ghytmc.contains("住")) {
                            bdcFdcqDO.setFwlx(1);
                        } else if (ghytmc.contains("车")) {
                            bdcFdcqDO.setFwlx(6);
                        } else if (ghytmc.contains("商")) {
                            bdcFdcqDO.setFwlx(2);
                        } else if (ghytmc.contains("办")) {
                            bdcFdcqDO.setFwlx(3);
                        } else {
                            bdcFdcqDO.setFwlx(99);
                        }
                    }
                }
                initServiceDTO.setBdcQl(bdcQl);
            }
        }
        return initServiceDTO;
    }
}
