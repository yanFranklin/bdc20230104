package cn.gtmap.realestate.natural.core.service.impl;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyDjdyDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.natural.*;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.natural.core.service.ZrzyCommonService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源登记单元服务
 */
@Service
public class ZrzyDjdyServiceImpl implements ZrzyCommonService {

    @Autowired
    private EntityMapper entityMapper;

    @Resource(name = "initDozerMapper")
    private DozerBeanMapper initDozerMapper;

    @Override
    public ZrzySlymYwxxDTO queryZrzySlymYwxxDTO(String xmid, ZrzySlymYwxxDTO zrzySlymYwxxDTO) {
        if (zrzySlymYwxxDTO == null) {
            zrzySlymYwxxDTO = new ZrzySlymYwxxDTO();
        }
        if (StringUtils.isNotBlank(xmid)) {
            ZrzyDjdyDO zrzyDjdyDO = entityMapper.selectByPrimaryKey(ZrzyDjdyDO.class, xmid);
            if (zrzyDjdyDO != null) {
                zrzySlymYwxxDTO.setZrzyDjdy(zrzyDjdyDO);
            }
        }
        return zrzySlymYwxxDTO;

    }

    @Override
    public ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO, ZrzyInitServiceDTO zrzyInitServiceDTO) {
        if (zrzyInitQO != null && org.apache.commons.lang3.StringUtils.isNotBlank(zrzyInitQO.getSjly())) {
            ZrzyDjdyDO zrzyDjdyDO = new ZrzyDjdyDO();
            if (org.apache.commons.lang3.StringUtils.equals(CommonConstantUtils.QLSJLY_LPB, zrzyInitQO.getSjly())) {
                readDjData(zrzyInitQO, zrzyDjdyDO);
            } else {
                readYxmData(zrzyInitQO, zrzyDjdyDO);
            }
            zrzyDjdyDO.setDjdymc(zrzyInitQO.getJbzkDTO().getMc());
            zrzyDjdyDO.setXmid(zrzyInitQO.getXmid());
            zrzyInitServiceDTO.setZrzyDjdyDO(zrzyDjdyDO);
        }
        return zrzyInitServiceDTO;

    }

    @Override
    public ZrzyZrzkYwxxDTO queryZrzyZrzkYwxxDTO(String xmid, ZrzyZrzkYwxxDTO zrzyZrzkYwxxDTO) {
        if (zrzyZrzkYwxxDTO == null) {
            zrzyZrzkYwxxDTO = new ZrzyZrzkYwxxDTO();
        }
        if (StringUtils.isNotBlank(xmid)) {
            ZrzyDjdyDO zrzyDjdyDO = entityMapper.selectByPrimaryKey(ZrzyDjdyDO.class, xmid);
            if (zrzyDjdyDO != null) {
                zrzyZrzkYwxxDTO.setZrzyDjdy(zrzyDjdyDO);
            }
        }
        return zrzyZrzkYwxxDTO;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 从地籍信息中读取数据
     */
    private ZrzyDjdyDO readDjData(ZrzyInitQO zrzyInitQO, ZrzyDjdyDO zrzyDjdyDO) {
        if (zrzyInitQO.getJbzkDTO() != null) {
            initDozerMapper.map(zrzyInitQO.getJbzkDTO(), zrzyDjdyDO);
        }
        return zrzyDjdyDO;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 从原项目中读取数据
     */
    private ZrzyDjdyDO readYxmData(ZrzyInitQO zrzyInitQO, ZrzyDjdyDO zrzyDjdyDO) {
        if (StringUtils.isNotBlank(zrzyInitQO.getYxmid())) {
            ZrzyDjdyDO yzrzyDjdyDO = entityMapper.selectByPrimaryKey(ZrzyDjdyDO.class, zrzyInitQO.getYxmid());
            if (yzrzyDjdyDO != null) {
                initDozerMapper.map(yzrzyDjdyDO, zrzyDjdyDO);
            }
        }
        return zrzyDjdyDO;
    }

    @Override
    public void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO) {
        if (CollectionUtils.isNotEmpty(zrzyInitResultDTO.getZrzyDjdyDOList())) {
            zrzyInitResultDTO.getZrzyDjdyDOList().forEach(zrzyDjdyDO -> entityMapper.insertSelective(zrzyDjdyDO));
        }
    }

    @Override
    public void deleteYwxx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzyDjdyDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            entityMapper.deleteByExample(example);
        }
    }
}
