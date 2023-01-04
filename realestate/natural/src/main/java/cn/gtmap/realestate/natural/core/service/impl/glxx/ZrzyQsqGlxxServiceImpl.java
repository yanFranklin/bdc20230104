package cn.gtmap.realestate.natural.core.service.impl.glxx;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyKyqglxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyQsqglxxDO;
import cn.gtmap.realestate.common.core.domain.naturalresource.QsqglxxDO;
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
 * @description 自然资源取水权关联信息服务
 */
@Service
public class ZrzyQsqGlxxServiceImpl implements ZrzyCommonService {

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
            List<ZrzyQsqglxxDO> zrzyQsqglxxDOList = listZrzyQsqglxxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(zrzyQsqglxxDOList)) {
                zrzyQsqglxxDOList.sort(Comparator.comparing(ZrzyQsqglxxDO::getQsxkzh, Comparator.nullsFirst(Comparator.naturalOrder())));
                zrzySlymYwxxDTO.setZrzyQsqglxxList(zrzyQsqglxxDOList);
            }
        }
        return zrzySlymYwxxDTO;

    }

    @Override
    public ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO, ZrzyInitServiceDTO zrzyInitServiceDTO) {
        if (zrzyInitQO != null && StringUtils.isNotBlank(zrzyInitQO.getSjly())) {
            List<ZrzyQsqglxxDO> zrzyQsqglxxDOList = new ArrayList<>();
            if (StringUtils.equals(CommonConstantUtils.QLSJLY_LPB, zrzyInitQO.getSjly())) {
                readDjData(zrzyInitQO, zrzyQsqglxxDOList);
            } else {
                readYxmData(zrzyInitQO, zrzyQsqglxxDOList);
            }
            zrzyInitServiceDTO.setZrzyQsqglxxDOList(zrzyQsqglxxDOList);
        }

        return zrzyInitServiceDTO;

    }

    /**
     * @param xmid 项目ID
     * @return 自然资源取水权关联信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取自然资源取水权关联信息
     */
    private List<ZrzyQsqglxxDO> listZrzyQsqglxxByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzyQsqglxxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            List<ZrzyQsqglxxDO> zrzyQsqglxxDOList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(zrzyQsqglxxDOList)) {
                return zrzyQsqglxxDOList;
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
    private List<ZrzyQsqglxxDO> readDjData(ZrzyInitQO zrzyInitQO, List<ZrzyQsqglxxDO> zrzyQsqglxxDOList) {
        if (zrzyInitQO.getJbzkDTO() != null && CollectionUtils.isNotEmpty(zrzyInitQO.getJbzkDTO().getQsqglxxDOList())) {
            for (QsqglxxDO qsqglxxDO : zrzyInitQO.getJbzkDTO().getQsqglxxDOList()) {
                ZrzyQsqglxxDO zrzyQsqglxxDO = new ZrzyQsqglxxDO();
                initDozerMapper.map(qsqglxxDO, zrzyQsqglxxDO);
                zrzyQsqglxxDO.setXmid(zrzyInitQO.getXmid());
                zrzyQsqglxxDO.setGlxxid(UUIDGenerator.generate16());
                zrzyQsqglxxDOList.add(zrzyQsqglxxDO);

            }

        }
        return zrzyQsqglxxDOList;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 从原项目中读取数据
     */
    private List<ZrzyQsqglxxDO> readYxmData(ZrzyInitQO zrzyInitQO, List<ZrzyQsqglxxDO> zrzyQsqglxxDOList) {
        if (StringUtils.isNotBlank(zrzyInitQO.getYxmid())) {
            List<ZrzyQsqglxxDO> yzrzyQsqglxxDOList = listZrzyQsqglxxByXmid(zrzyInitQO.getYxmid());
            if (CollectionUtils.isNotEmpty(yzrzyQsqglxxDOList)) {
                for (ZrzyQsqglxxDO yzrzyQsqglxxDO : yzrzyQsqglxxDOList) {
                    ZrzyQsqglxxDO zrzyQsqglxxDO = new ZrzyQsqglxxDO();
                    initDozerMapper.map(yzrzyQsqglxxDO, zrzyQsqglxxDO);
                    zrzyQsqglxxDO.setXmid(zrzyInitQO.getXmid());
                    zrzyQsqglxxDO.setGlxxid(UUIDGenerator.generate16());
                    zrzyQsqglxxDOList.add(zrzyQsqglxxDO);
                }

            }
        }
        return zrzyQsqglxxDOList;
    }

    @Override
    public void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO) {
        if (CollectionUtils.isNotEmpty(zrzyInitResultDTO.getZrzyQsqglxxDOList())) {
            zrzyInitResultDTO.getZrzyQsqglxxDOList().forEach(zrzyQsqglxxDO -> entityMapper.insertSelective(zrzyQsqglxxDO));
        }
    }

    @Override
    public void deleteYwxx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzyQsqglxxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            entityMapper.deleteByExample(example);
        }
    }
}
