package cn.gtmap.realestate.natural.core.service.impl.zrzkxx;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyHyDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZrzk;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.natural.*;
import cn.gtmap.realestate.common.core.enums.natural.ZrzyZrzklxEnum;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyZrzkVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.natural.core.service.ZrzyCommonService;
import cn.gtmap.realestate.natural.core.service.zrzkxx.ZrzyZrzkService;
import cn.gtmap.realestate.natural.service.ZrzyZdService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源草原服务
 */
@Service
public class ZrzyHyServiceImpl implements ZrzyCommonService {
    @Autowired
    EntityMapper entityMapper;

    @Autowired
    private ZrzyZrzkService zrzyZrzkService;

    @Resource(name = "initDozerMapper")
    private DozerBeanMapper initDozerMapper;

    @Autowired
    @Lazy
    ZrzyZdService zrzyZdFeignService;

    @Override
    public ZrzySlymYwxxDTO queryZrzySlymYwxxDTO(String xmid, ZrzySlymYwxxDTO zrzySlymYwxxDTO) {
        if (zrzySlymYwxxDTO == null) {
            zrzySlymYwxxDTO = new ZrzySlymYwxxDTO();
        }
        if (StringUtils.isNotBlank(xmid)) {
            List<ZrzyZrzk> zrzyZrzkList = zrzyZrzkService.listZrzyZrzkList(xmid, ZrzyHyDO.class);
            if (CollectionUtils.isNotEmpty(zrzyZrzkList)) {
                if (CollectionUtils.isEmpty(zrzySlymYwxxDTO.getZrzyZrzkList())) {
                    zrzySlymYwxxDTO.setZrzyZrzkList(new ArrayList<>());
                }
                for (ZrzyZrzk zrzyZrzk : zrzyZrzkList) {
                    ZrzyZrzkVO zrzyZrzkVO = new ZrzyZrzkVO();
                    BeanUtils.copyProperties(zrzyZrzk, zrzyZrzkVO);
                    zrzyZrzkVO.setZrzklx(ZrzyZrzklxEnum.HY.getCode());
                    zrzyZrzkVO.setZrzklxmc(ZrzyZrzklxEnum.HY.getName());
                    zrzyZrzkVO.setZrzklxzlmc(ZrzyZrzklxEnum.HY.getName());
                    zrzySlymYwxxDTO.getZrzyZrzkList().add(zrzyZrzkVO);
                }

            }
        }
        return zrzySlymYwxxDTO;

    }

    @Override
    public ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO, ZrzyInitServiceDTO zrzyInitServiceDTO) {
        if (zrzyInitQO != null && StringUtils.isNotBlank(zrzyInitQO.getSjly())) {
            List<ZrzyHyDO> zrzyHyDOList = new ArrayList<>();
            if (StringUtils.equals(CommonConstantUtils.QLSJLY_LPB, zrzyInitQO.getSjly())) {
                readDjData(zrzyInitQO, zrzyHyDOList);
            } else {
                readYxmData(zrzyInitQO, zrzyHyDOList);
            }
            zrzyInitServiceDTO.setZrzyHyDOList(zrzyHyDOList);
        }
        return zrzyInitServiceDTO;

    }

    @Override
    public ZrzyZrzkYwxxDTO queryZrzyZrzkYwxxDTO(String xmid, ZrzyZrzkYwxxDTO zrzyZrzkYwxxDTO) {
        if (zrzyZrzkYwxxDTO == null) {
            zrzyZrzkYwxxDTO = new ZrzyZrzkYwxxDTO();
        }
        if (StringUtils.isNotBlank(xmid)) {
            List<ZrzyHyDO> zrzyZrzkList = zrzyZrzkService.listZrzyZrzkList(xmid, ZrzyHyDO.class);
            if (CollectionUtils.isNotEmpty(zrzyZrzkList)) {
                zrzyZrzkList.sort(Comparator.comparing(ZrzyHyDO::getHymj, Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
                zrzyZrzkYwxxDTO.setZrzyHyDOList(zrzyZrzkList);
            } else {
                zrzyZrzkYwxxDTO.setZrzyHyDOList(new ArrayList<>());
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
    private List<ZrzyHyDO> readDjData(ZrzyInitQO zrzyInitQO, List<ZrzyHyDO> zrzyHyDOList) {
        if (zrzyInitQO.getJbzkDTO() != null && CollectionUtils.isNotEmpty(zrzyInitQO.getJbzkDTO().getHdzkxxDOList())) {
            ZrzyHyDO zrzyHyDO = new ZrzyHyDO();
            zrzyHyDO.setXmid(zrzyInitQO.getXmid());
            zrzyHyDO.setZkxxid(UUIDGenerator.generate16());
            zrzyHyDOList.add(zrzyHyDO);
        }
        return zrzyHyDOList;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 从原项目中读取数据
     */
    private List<ZrzyHyDO> readYxmData(ZrzyInitQO zrzyInitQO, List<ZrzyHyDO> zrzyHyDOList) {
        if (StringUtils.isNotBlank(zrzyInitQO.getYxmid())) {
            List<ZrzyZrzk> zrzyZrzkList = zrzyZrzkService.listZrzyZrzkList(zrzyInitQO.getYxmid(), ZrzyHyDO.class);
            if (CollectionUtils.isNotEmpty(zrzyZrzkList)) {
                for (ZrzyZrzk zrzyZrzk : zrzyZrzkList) {
                    ZrzyHyDO zrzyHyDO = new ZrzyHyDO();
                    initDozerMapper.map(zrzyZrzk, zrzyHyDO);
                    zrzyHyDO.setXmid(zrzyInitQO.getXmid());
                    zrzyHyDO.setZkxxid(UUIDGenerator.generate16());
                    zrzyHyDOList.add(zrzyHyDO);
                }

            }
        }
        return zrzyHyDOList;
    }

    @Override
    public void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO) {
        if (CollectionUtils.isNotEmpty(zrzyInitResultDTO.getZrzyHyDOList())) {
            zrzyInitResultDTO.getZrzyHyDOList().forEach(zrzyHyDO -> entityMapper.insertSelective(zrzyHyDO));
        }
    }


    @Override
    public void deleteYwxx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzyHyDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            entityMapper.deleteByExample(example);
        }
    }
}
