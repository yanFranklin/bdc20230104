package cn.gtmap.realestate.natural.core.service.impl.glxx;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyBdcqlglxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyKyqglxxDO;
import cn.gtmap.realestate.common.core.domain.naturalresource.KyqglxxDO;
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
 * @description 自然资源矿业权关联信息服务
 */
@Service
public class ZrzyKyqGlxxServiceImpl implements ZrzyCommonService {

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
            List<ZrzyKyqglxxDO> zrzyKyqglxxList = listZrzyKyqglxxByXmid(xmid);
            if (CollectionUtils.isNotEmpty(zrzyKyqglxxList)) {
                zrzyKyqglxxList.sort(Comparator.comparing(ZrzyKyqglxxDO::getKcckxkzh, Comparator.nullsFirst(Comparator.naturalOrder())));
                zrzySlymYwxxDTO.setZrzyKyqglxxList(zrzyKyqglxxList);
            }
        }
        return zrzySlymYwxxDTO;

    }

    @Override
    public ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO, ZrzyInitServiceDTO zrzyInitServiceDTO) {
        if (zrzyInitQO != null && StringUtils.isNotBlank(zrzyInitQO.getSjly())) {
            List<ZrzyKyqglxxDO> zrzyKyqglxxDOList = new ArrayList<>();
            if (StringUtils.equals(CommonConstantUtils.QLSJLY_LPB, zrzyInitQO.getSjly())) {
                readDjData(zrzyInitQO, zrzyKyqglxxDOList);
            } else {
                readYxmData(zrzyInitQO, zrzyKyqglxxDOList);
            }
            zrzyInitServiceDTO.setZrzyKyqglxxDOList(zrzyKyqglxxDOList);
        }

        return zrzyInitServiceDTO;

    }

    /**
     * @param xmid 项目ID
     * @return 自然资源不动产权利关联信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取自然资源不动产权利关联信息
     */
    private List<ZrzyKyqglxxDO> listZrzyKyqglxxByXmid(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzyKyqglxxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            List<ZrzyKyqglxxDO> zrzyKyqglxxList = entityMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(zrzyKyqglxxList)) {
                return zrzyKyqglxxList;
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
    private List<ZrzyKyqglxxDO> readDjData(ZrzyInitQO zrzyInitQO, List<ZrzyKyqglxxDO> zrzyKyqglxxDOList) {
        if (zrzyInitQO.getJbzkDTO() != null && CollectionUtils.isNotEmpty(zrzyInitQO.getJbzkDTO().getKyqglxxDOList())) {
            for (KyqglxxDO kyqglxxDO : zrzyInitQO.getJbzkDTO().getKyqglxxDOList()) {
                ZrzyKyqglxxDO zrzyKyqglxxDO = new ZrzyKyqglxxDO();
                initDozerMapper.map(kyqglxxDO, zrzyKyqglxxDO);
                zrzyKyqglxxDO.setXmid(zrzyInitQO.getXmid());
                zrzyKyqglxxDO.setGlxxid(UUIDGenerator.generate16());
                zrzyKyqglxxDOList.add(zrzyKyqglxxDO);

            }

        }
        return zrzyKyqglxxDOList;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 从原项目中读取数据
     */
    private List<ZrzyKyqglxxDO> readYxmData(ZrzyInitQO zrzyInitQO, List<ZrzyKyqglxxDO> zrzyKyqglxxDOList) {
        if (StringUtils.isNotBlank(zrzyInitQO.getYxmid())) {
            List<ZrzyKyqglxxDO> yzrzyBdcqlglxxDOList = listZrzyKyqglxxByXmid(zrzyInitQO.getYxmid());
            if (CollectionUtils.isNotEmpty(yzrzyBdcqlglxxDOList)) {
                for (ZrzyKyqglxxDO yzrzyKyqglxxDO : yzrzyBdcqlglxxDOList) {
                    ZrzyKyqglxxDO zrzyKyqglxxDO = new ZrzyKyqglxxDO();
                    initDozerMapper.map(yzrzyKyqglxxDO, zrzyKyqglxxDO);
                    zrzyKyqglxxDO.setXmid(zrzyInitQO.getXmid());
                    zrzyKyqglxxDO.setGlxxid(UUIDGenerator.generate16());
                    zrzyKyqglxxDOList.add(zrzyKyqglxxDO);
                }

            }
        }
        return zrzyKyqglxxDOList;
    }

    @Override
    public void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO) {
        if (CollectionUtils.isNotEmpty(zrzyInitResultDTO.getZrzyKyqglxxDOList())) {
            zrzyInitResultDTO.getZrzyKyqglxxDOList().forEach(zrzyKyqglxxDO -> entityMapper.insertSelective(zrzyKyqglxxDO));
        }
    }


    @Override
    public void deleteYwxx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzyKyqglxxDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            entityMapper.deleteByExample(example);
        }
    }
}
