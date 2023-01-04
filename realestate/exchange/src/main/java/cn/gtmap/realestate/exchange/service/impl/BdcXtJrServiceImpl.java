package cn.gtmap.realestate.exchange.service.impl;

import cn.gtmap.realestate.common.core.domain.exchange.BdcXtJrDO;
import cn.gtmap.realestate.common.core.qo.exchange.openapi.BdcXtJrQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcXtJrMapper;
import cn.gtmap.realestate.exchange.service.BdcXtJrService;
import cn.gtmap.realestate.exchange.service.impl.national.NationalAccessXmlContext;
import cn.gtmap.realestate.exchange.service.national.NationalAccessXmlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 系统接入配置实现服务
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-07 09:32
 **/
@Service
public class BdcXtJrServiceImpl implements BdcXtJrService {


    @Autowired
    BdcXtJrMapper bdcXtJrMapper;
    @Autowired
    EntityMapper entityMapper;

    @Autowired
    NationalAccessXmlContext nationalAccessXmlContext;

    /**
     * @param bdcXtJrQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入配置
     * @date : 2022/7/7 9:31
     */
    @Override
    public List<BdcXtJrDO> listBdcXtJr(BdcXtJrQO bdcXtJrQO) {
        if (StringToolUtils.isAllNullOrEmpty(bdcXtJrQO.getDjxl(), bdcXtJrQO.getQllx()) && Objects.isNull(bdcXtJrQO.getBdcdyfwlxList()) && Objects.isNull(bdcXtJrQO.getBdclx())) {
            return null;
        }
        return bdcXtJrMapper.listBdcXtJrPz(bdcXtJrQO);
    }

    /**
     * @param id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据id删除接入信息
     * @date : 2022/7/7 19:39
     */
    @Override
    public int deleteBdcXtJr(String id) {
        if (StringUtils.isNotBlank(id)) {
            return entityMapper.deleteByPrimaryKey(BdcXtJrDO.class, id);
        }
        return 0;
    }

    /**
     * @param ywfwdm
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询接入业务代码
     * @date : 2022/7/8 11:25
     */
    @Override
    public String queryYwdm(String ywfwdm) {
        if (StringUtils.isNotBlank(ywfwdm)) {
            NationalAccessXmlService nationalAccessXmlService = nationalAccessXmlContext.getNationNalServiceInstance(ywfwdm);
            if (Objects.nonNull(nationalAccessXmlService)) {
                return nationalAccessXmlService.getRecType();
            } else {
                return "";
            }
        }
        return "";
    }
}
