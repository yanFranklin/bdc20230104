package cn.gtmap.realestate.natural.core.service.impl.zrzkxx;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyCyDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzySzyDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyTmclkczyDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZrzk;
import cn.gtmap.realestate.common.core.domain.naturalresource.TmclkczyzkxxDO;
import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.common.core.dto.natural.*;
import cn.gtmap.realestate.common.core.enums.natural.ZrzyZrzklxEnum;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyZrzkVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
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
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源草原服务
 */
@Service
public class ZrzyTmclkczyServiceImpl implements ZrzyCommonService {
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
            List<ZrzyZrzk> zrzyZrzkList = zrzyZrzkService.listZrzyZrzkList(xmid, ZrzyTmclkczyDO.class);
            if (CollectionUtils.isNotEmpty(zrzyZrzkList)) {
                Map<String, List<Map>> zdMap = zrzyZdFeignService.listZrzyzd();
                if (CollectionUtils.isEmpty(zrzySlymYwxxDTO.getZrzyZrzkList())) {
                    zrzySlymYwxxDTO.setZrzyZrzkList(new ArrayList<>());
                }
                for (ZrzyZrzk zrzyZrzk : zrzyZrzkList) {
                    ZrzyZrzkVO zrzyZrzkVO = new ZrzyZrzkVO();
                    BeanUtils.copyProperties(zrzyZrzk, zrzyZrzkVO);
                    zrzyZrzkVO.setZrzklx(ZrzyZrzklxEnum.TMCLKCZY.getCode());
                    zrzyZrzkVO.setZrzklxmc(ZrzyZrzklxEnum.TMCLKCZY.getName());
                    ZrzyTmclkczyDO zrzyTmclkczyDO = (ZrzyTmclkczyDO) zrzyZrzk;
                    zrzyZrzkVO.setZrzklxzlmc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(zrzyTmclkczyDO.getZrlx()),
                            zdMap.get("zylx")));
                    zrzySlymYwxxDTO.getZrzyZrzkList().add(zrzyZrzkVO);
                }
            }
        }
        return zrzySlymYwxxDTO;

    }

    @Override
    public ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO, ZrzyInitServiceDTO zrzyInitServiceDTO) {

        if (zrzyInitQO != null && StringUtils.isNotBlank(zrzyInitQO.getSjly())) {
            List<ZrzyTmclkczyDO> zrzyTmclkczyDOList = new ArrayList<>();
            if (StringUtils.equals(CommonConstantUtils.QLSJLY_LPB, zrzyInitQO.getSjly())) {
                readDjData(zrzyInitQO, zrzyTmclkczyDOList);
            } else {
                readYxmData(zrzyInitQO, zrzyTmclkczyDOList);
            }
            zrzyInitServiceDTO.setZrzyTmclkczyDOList(zrzyTmclkczyDOList);
        }
        return zrzyInitServiceDTO;

    }

    @Override
    public ZrzyZrzkYwxxDTO queryZrzyZrzkYwxxDTO(String xmid, ZrzyZrzkYwxxDTO zrzyZrzkYwxxDTO) {
        if (zrzyZrzkYwxxDTO == null) {
            zrzyZrzkYwxxDTO = new ZrzyZrzkYwxxDTO();
        }
        if (StringUtils.isNotBlank(xmid)) {
            List<ZrzyTmclkczyDO> zrzyZrzkList = zrzyZrzkService.listZrzyZrzkList(xmid, ZrzyTmclkczyDO.class);
            if (CollectionUtils.isNotEmpty(zrzyZrzkList)) {
                zrzyZrzkList.sort(Comparator.comparing(ZrzyTmclkczyDO::getKqyqtzmj, Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
                zrzyZrzkYwxxDTO.setZrzyTmclkczyDOList(zrzyZrzkList);
            } else {
                zrzyZrzkYwxxDTO.setZrzyTmclkczyDOList(new ArrayList<>());
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
    private List<ZrzyTmclkczyDO> readDjData(ZrzyInitQO zrzyInitQO, List<ZrzyTmclkczyDO> zrzyTmclkczyDOList) {
        if (zrzyInitQO.getJbzkDTO() != null && CollectionUtils.isNotEmpty(zrzyInitQO.getJbzkDTO().getTmclkczyzkxxDOList())) {
            for (TmclkczyzkxxDO tmclkczyzkxxDO : zrzyInitQO.getJbzkDTO().getTmclkczyzkxxDOList()) {
                ZrzyTmclkczyDO zrzyTmclkczyDO = new ZrzyTmclkczyDO();
                initDozerMapper.map(tmclkczyzkxxDO, zrzyTmclkczyDO);
                zrzyTmclkczyDO.setXmid(zrzyInitQO.getXmid());
                zrzyTmclkczyDO.setZkxxid(UUIDGenerator.generate16());
                zrzyTmclkczyDOList.add(zrzyTmclkczyDO);

            }

        }
        return zrzyTmclkczyDOList;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 从原项目中读取数据
     */
    private List<ZrzyTmclkczyDO> readYxmData(ZrzyInitQO zrzyInitQO, List<ZrzyTmclkczyDO> zrzyTmclkczyDOList) {
        if (StringUtils.isNotBlank(zrzyInitQO.getYxmid())) {
            List<ZrzyZrzk> zrzyZrzkList = zrzyZrzkService.listZrzyZrzkList(zrzyInitQO.getYxmid(), ZrzyTmclkczyDO.class);
            if (CollectionUtils.isNotEmpty(zrzyZrzkList)) {
                for (ZrzyZrzk zrzyZrzk : zrzyZrzkList) {
                    ZrzyTmclkczyDO zrzyTmclkczyDO = new ZrzyTmclkczyDO();
                    initDozerMapper.map(zrzyZrzk, zrzyTmclkczyDO);
                    zrzyTmclkczyDO.setXmid(zrzyInitQO.getXmid());
                    zrzyTmclkczyDO.setZkxxid(UUIDGenerator.generate16());
                    zrzyTmclkczyDOList.add(zrzyTmclkczyDO);
                }

            }
        }
        return zrzyTmclkczyDOList;
    }

    @Override
    public void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO) {
        if (CollectionUtils.isNotEmpty(zrzyInitResultDTO.getZrzyTmclkczyDOList())) {
            zrzyInitResultDTO.getZrzyTmclkczyDOList().forEach(zrzyTmclkczyDO -> entityMapper.insertSelective(zrzyTmclkczyDO));
        }
    }

    @Override
    public void deleteYwxx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzyTmclkczyDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            entityMapper.deleteByExample(example);
        }
    }
}
