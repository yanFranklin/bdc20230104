package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.accept.config.FdjywConfig;
import cn.gtmap.realestate.accept.core.service.BdcSlFdjywService;
import cn.gtmap.realestate.accept.core.service.BdcSlShxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlSjxgService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjxgDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlCshDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @description 数据修改服务实现层
 * @date : 2022/11/30
 */
@Service
public class BdcSlSjxgServiceImpl implements BdcSlFdjywService, BdcSlSjxgService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSlSjxgServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;

    @Autowired
    BdcSlShxxService bdcSlShxxService;

    @Autowired
    protected UserManagerUtils userManagerUtils;

    @Autowired
    FdjywConfig fdjywConfig;


    @Override
    public BdcSlSjxgDO querySlSjxgDO(String gzlslid) {
        BdcSlSjxgDO bdcSlSjxgDO = new BdcSlSjxgDO();
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlSjxgDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            List<BdcSlSjxgDO> bdcSlSjxgDOS = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(bdcSlSjxgDOS)) {
                return bdcSlSjxgDOS.get(0);
            }
        }
        return bdcSlSjxgDO;
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 根据工作流实例id删除数据修改数据
     * @date : 2022/11/30
     */
    @Override
    public int deleteSlSjxg(String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            Example example = new Example(BdcSlSjxgDO.class);
            example.createCriteria().andEqualTo("gzlslid", gzlslid);
            return entityMapper.deleteByExample(example);
        }
        return 0;
    }

    /**
     * @param bdcSlSjxgDO
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description 新增数据修改数据
     * @date : 2022/11/30
     */
    @Override
    public BdcSlSjxgDO saveSlSjxg(BdcSlSjxgDO bdcSlSjxgDO) {
        if (StringUtils.isBlank(bdcSlSjxgDO.getSjxgid())) {
            bdcSlSjxgDO.setSjxgid(UUIDGenerator.generate16());
            entityMapper.insertSelective(bdcSlSjxgDO);
        } else {
            entityMapper.saveOrUpdate(bdcSlSjxgDO, bdcSlSjxgDO.getSjxgid());
        }
        return bdcSlSjxgDO;
    }

    @Override
    public void initFdjywxx(BdcSlCshDTO bdcSlCshDTO) {
        LOGGER.info("数据修改初始化开始：{}", JSON.toJSONString(bdcSlCshDTO));
        if (StringUtils.isNotBlank(bdcSlCshDTO.getGzlslid())) {
            BdcSlSjxgDO bdcSlSjxgDO = new BdcSlSjxgDO();
            bdcSlSjxgDO.setSjxgid(UUIDGenerator.generate16());
            bdcSlSjxgDO.setGzlslid(bdcSlCshDTO.getGzlslid());
            UserDto userDto = userManagerUtils.getCurrentUser();
            if (userDto != null) {
                bdcSlSjxgDO.setSqr(userDto.getAlias());
            }
            bdcSlSjxgDO.setSqsj(new Date());
            entityMapper.insertSelective(bdcSlSjxgDO);
        }
    }

    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        List<String> sjxgGzldyidList = fdjywConfig.getFdjywlcDyidList("sjxg");
        if (CollectionUtils.isNotEmpty(sjxgGzldyidList)) {
            set.addAll(sjxgGzldyidList);
        } else {
            set.add("sjxg");
        }
        return set;
    }
}
