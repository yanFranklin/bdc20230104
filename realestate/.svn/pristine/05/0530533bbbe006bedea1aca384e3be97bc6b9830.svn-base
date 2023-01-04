package cn.gtmap.realestate.natural.core.service.impl.glxx;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyPwqglxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyQsqglxxDO;
import cn.gtmap.realestate.common.core.domain.naturalresource.PwqglxxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyInitQO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyInitResultDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyInitServiceDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzySlymYwxxDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.natural.core.service.ZrzyCommonService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源排污权关联信息服务
 */
@Service
public class ZrzyPwqGlxxServiceImpl implements ZrzyCommonService {

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
            List<ZrzyPwqglxxDO> zrzyPwqglxxDOList = listZrzyPwqglxxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(zrzyPwqglxxDOList)) {
                zrzyPwqglxxDOList.sort(Comparator.comparing(ZrzyPwqglxxDO::getPwxkzh, Comparator.nullsFirst(Comparator.naturalOrder())));
                zrzySlymYwxxDTO.setZrzyPwqglxxList(zrzyPwqglxxDOList);
            }
        }
        return zrzySlymYwxxDTO;

    }

    @Override
    public ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO, ZrzyInitServiceDTO zrzyInitServiceDTO) {
        if (zrzyInitQO != null && StringUtils.isNotBlank(zrzyInitQO.getSjly())) {
            List<ZrzyPwqglxxDO> zrzyPwqglxxDOList = new ArrayList<>();
            if (StringUtils.equals(CommonConstantUtils.QLSJLY_LPB, zrzyInitQO.getSjly())) {
                readDjData(zrzyInitQO, zrzyPwqglxxDOList);
            } else {
                readYxmData(zrzyInitQO, zrzyPwqglxxDOList);
            }
            zrzyInitServiceDTO.setZrzyPwqglxxDOList(zrzyPwqglxxDOList);
        }

        return zrzyInitServiceDTO;

    }

    /**
     * @param xmid 项目ID
     * @return 自然资源排污权关联信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取自然资源排污权关联信息
     */
    private List<ZrzyPwqglxxDO> listZrzyPwqglxxByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzyPwqglxxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            List<ZrzyPwqglxxDO> zrzyPwqglxxDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(zrzyPwqglxxDOList)) {
                return zrzyPwqglxxDOList;
            }
        }
        return Collections.emptyList();

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 从地籍信息中读取数据
     */
    private List<ZrzyPwqglxxDO> readDjData(ZrzyInitQO zrzyInitQO, List<ZrzyPwqglxxDO> zrzyPwqglxxDOList) {
        if (zrzyInitQO.getJbzkDTO() != null && CollectionUtils.isNotEmpty(zrzyInitQO.getJbzkDTO().getPwqglxxDOList())) {
            for (PwqglxxDO pwqglxxDO : zrzyInitQO.getJbzkDTO().getPwqglxxDOList()) {
                ZrzyPwqglxxDO zrzyPwqglxxDO = new ZrzyPwqglxxDO();
                initDozerMapper.map(pwqglxxDO, zrzyPwqglxxDO);
                zrzyPwqglxxDO.setXmid(zrzyInitQO.getXmid());
                zrzyPwqglxxDO.setGlxxid(UUIDGenerator.generate16());
                zrzyPwqglxxDOList.add(zrzyPwqglxxDO);

            }

        }
        return zrzyPwqglxxDOList;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 从原项目中读取数据
     */
    private List<ZrzyPwqglxxDO> readYxmData(ZrzyInitQO zrzyInitQO, List<ZrzyPwqglxxDO> zrzyPwqglxxDOList) {
        if (StringUtils.isNotBlank(zrzyInitQO.getYxmid())) {
            List<ZrzyPwqglxxDO> yzrzyBdcqlglxxDOList = listZrzyPwqglxxByXmid(zrzyInitQO.getYxmid());
            if (CollectionUtils.isNotEmpty(yzrzyBdcqlglxxDOList)) {
                for (ZrzyPwqglxxDO yzrzyKyqglxxDO : yzrzyBdcqlglxxDOList) {
                    ZrzyPwqglxxDO zrzyPwqglxxDO = new ZrzyPwqglxxDO();
                    initDozerMapper.map(yzrzyKyqglxxDO, zrzyPwqglxxDO);
                    zrzyPwqglxxDO.setXmid(zrzyInitQO.getXmid());
                    zrzyPwqglxxDO.setGlxxid(UUIDGenerator.generate16());
                    zrzyPwqglxxDOList.add(zrzyPwqglxxDO);
                }

            }
        }
        return zrzyPwqglxxDOList;
    }

    @Override
    public void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO) {
        if (CollectionUtils.isNotEmpty(zrzyInitResultDTO.getZrzyPwqglxxDOList())) {
            zrzyInitResultDTO.getZrzyPwqglxxDOList().forEach(zrzyPwqglxxDO -> entityMapper.insertSelective(zrzyPwqglxxDO));
        }
    }

    @Override
    public void deleteYwxx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzyPwqglxxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            entityMapper.deleteByExample(example);
        }
    }
}
