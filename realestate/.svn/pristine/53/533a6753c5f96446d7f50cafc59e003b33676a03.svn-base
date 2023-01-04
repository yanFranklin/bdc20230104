package cn.gtmap.realestate.natural.core.service.impl;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmLsgxDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyInitQO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyInitResultDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyInitServiceDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzySlymYwxxDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repository;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.natural.core.service.ZrzyCommonService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/27
 * @description
 */
@Service
public class ZrzyXmLsgxServiceImpl implements ZrzyCommonService {
    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private Repository repository;

    @Override
    public ZrzySlymYwxxDTO queryZrzySlymYwxxDTO(String xmid, ZrzySlymYwxxDTO zrzySlymYwxxDTO) {
        return zrzySlymYwxxDTO;
    }

    @Override
    public ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO, ZrzyInitServiceDTO zrzyInitServiceDTO) {

        if (zrzyInitQO != null && StringUtils.isNotBlank(zrzyInitQO.getSjly()) && StringUtils.equals(CommonConstantUtils.QLSJLY_YXM, zrzyInitQO.getSjly())) {
            List<ZrzyXmLsgxDO> zrzyXmLsgxDOList = new ArrayList<>();
            ZrzyXmLsgxDO zrzyXmLsgxDO = new ZrzyXmLsgxDO();
            zrzyXmLsgxDO.setXmid(zrzyInitQO.getXmid());
            zrzyXmLsgxDO.setYxmid(zrzyInitQO.getYxmid());
            zrzyXmLsgxDO.setGxid(UUIDGenerator.generate16());
            zrzyXmLsgxDOList.add(zrzyXmLsgxDO);
            zrzyInitServiceDTO.setZrzyXmLsgxDOList(zrzyXmLsgxDOList);
        }
        return zrzyInitServiceDTO;

    }

    @Override
    public void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO) {
        if (CollectionUtils.isNotEmpty(zrzyInitResultDTO.getZrzyXmLsgxDOList())) {
            zrzyInitResultDTO.getZrzyXmLsgxDOList().forEach(zrzyXmLsgxDO -> entityMapper.insertSelective(zrzyXmLsgxDO));
        }
    }

    @Override
    public void deleteYwxx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzyXmLsgxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            entityMapper.deleteByExample(example);
        }
    }
}
