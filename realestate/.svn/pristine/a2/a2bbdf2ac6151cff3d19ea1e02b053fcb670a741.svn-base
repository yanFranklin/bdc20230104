package cn.gtmap.realestate.natural.core.service.impl.zrzkxx;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyHdDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzySdDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzySzyDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZrzk;
import cn.gtmap.realestate.common.core.domain.naturalresource.SdzkxxDO;
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
 * @description 自然资源湿地服务
 */
@Service
public class ZrzySdServiceImpl implements ZrzyCommonService {
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
            List<ZrzyZrzk> zrzyZrzkList = zrzyZrzkService.listZrzyZrzkList(xmid, ZrzySdDO.class);
            if (CollectionUtils.isNotEmpty(zrzyZrzkList)) {
                Map<String, List<Map>> zdMap = zrzyZdFeignService.listZrzyzd();
                if (CollectionUtils.isEmpty(zrzySlymYwxxDTO.getZrzyZrzkList())) {
                    zrzySlymYwxxDTO.setZrzyZrzkList(new ArrayList<>());
                }
                for (ZrzyZrzk zrzyZrzk : zrzyZrzkList) {
                    ZrzyZrzkVO zrzyZrzkVO = new ZrzyZrzkVO();
                    BeanUtils.copyProperties(zrzyZrzk, zrzyZrzkVO);
                    zrzyZrzkVO.setZrzklx(ZrzyZrzklxEnum.SD.getCode());
                    zrzyZrzkVO.setZrzklxmc(ZrzyZrzklxEnum.SD.getName());
                    ZrzySdDO zrzySdDO = (ZrzySdDO) zrzyZrzk;
                    zrzyZrzkVO.setZrzklxzlmc(StringToolUtils.convertBeanPropertyValueOfZd(Integer.valueOf(zrzySdDO.getSdlx()),
                            zdMap.get("sdlx")));
                    zrzySlymYwxxDTO.getZrzyZrzkList().add(zrzyZrzkVO);
                }
            }
        }
        return zrzySlymYwxxDTO;

    }

    @Override
    public ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO, ZrzyInitServiceDTO zrzyInitServiceDTO) {
        if (zrzyInitQO != null && StringUtils.isNotBlank(zrzyInitQO.getSjly())) {
            List<ZrzySdDO> zrzySdDOList = new ArrayList<>();
            if (StringUtils.equals(CommonConstantUtils.QLSJLY_LPB, zrzyInitQO.getSjly())) {
                readDjData(zrzyInitQO, zrzySdDOList);
            } else {
                readYxmData(zrzyInitQO, zrzySdDOList);
            }
            zrzyInitServiceDTO.setZrzySdDOList(zrzySdDOList);
        }
        return zrzyInitServiceDTO;

    }

    @Override
    public ZrzyZrzkYwxxDTO queryZrzyZrzkYwxxDTO(String xmid, ZrzyZrzkYwxxDTO zrzyZrzkYwxxDTO) {
        if (zrzyZrzkYwxxDTO == null) {
            zrzyZrzkYwxxDTO = new ZrzyZrzkYwxxDTO();
        }
        if (StringUtils.isNotBlank(xmid)) {
            List<ZrzySdDO> zrzyZrzkList = zrzyZrzkService.listZrzyZrzkList(xmid, ZrzySdDO.class);
            if (CollectionUtils.isNotEmpty(zrzyZrzkList)) {
                zrzyZrzkList.sort(Comparator.comparing(ZrzySdDO::getZmj, Comparator.nullsFirst(Comparator.naturalOrder())).reversed());
                zrzyZrzkYwxxDTO.setZrzySdDOList(zrzyZrzkList);
            } else {
                zrzyZrzkYwxxDTO.setZrzySdDOList(new ArrayList<>());
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
    private List<ZrzySdDO> readDjData(ZrzyInitQO zrzyInitQO, List<ZrzySdDO> zrzySdDOList) {
        if (zrzyInitQO.getJbzkDTO() != null && CollectionUtils.isNotEmpty(zrzyInitQO.getJbzkDTO().getSdzkxxDOList())) {
            for (SdzkxxDO sdzkxxDO : zrzyInitQO.getJbzkDTO().getSdzkxxDOList()) {
                ZrzySdDO zrzySdDO = new ZrzySdDO();
                initDozerMapper.map(sdzkxxDO, zrzySdDO);
                zrzySdDO.setXmid(zrzyInitQO.getXmid());
                zrzySdDO.setZkxxid(UUIDGenerator.generate16());
                zrzySdDOList.add(zrzySdDO);

            }

        }
        return zrzySdDOList;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 从原项目中读取数据
     */
    private List<ZrzySdDO> readYxmData(ZrzyInitQO zrzyInitQO, List<ZrzySdDO> zrzySdDOList) {
        if (StringUtils.isNotBlank(zrzyInitQO.getYxmid())) {
            List<ZrzyZrzk> zrzyZrzkList = zrzyZrzkService.listZrzyZrzkList(zrzyInitQO.getYxmid(), ZrzySdDO.class);
            if (CollectionUtils.isNotEmpty(zrzyZrzkList)) {
                for (ZrzyZrzk zrzyZrzk : zrzyZrzkList) {
                    ZrzySdDO zrzySdDO = new ZrzySdDO();
                    initDozerMapper.map(zrzyZrzk, zrzySdDO);
                    zrzySdDO.setXmid(zrzyInitQO.getXmid());
                    zrzySdDO.setZkxxid(UUIDGenerator.generate16());
                    zrzySdDOList.add(zrzySdDO);
                }

            }
        }
        return zrzySdDOList;
    }

    @Override
    public void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO) {
        if (CollectionUtils.isNotEmpty(zrzyInitResultDTO.getZrzySdDOList())) {
            zrzyInitResultDTO.getZrzySdDOList().forEach(zrzySdDO -> entityMapper.insertSelective(zrzySdDO));
        }
    }

    @Override
    public void deleteYwxx(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(ZrzySdDO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            entityMapper.deleteByExample(example);
        }
    }
}
