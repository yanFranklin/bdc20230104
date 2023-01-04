package cn.gtmap.realestate.natural.core.service.impl.glxx;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyBdcqlglxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyGggzglxxDO;
import cn.gtmap.realestate.common.core.domain.naturalresource.BdcqlglxxDO;
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
 * @description 自然资源不动产权利关联信息服务
 */
@Service
public class ZrzyBdcqlGlxxServiceImpl implements ZrzyCommonService {

    @Autowired
    private EntityMapper entityMapper;

    @Resource(name = "initDozerMapper")
    private DozerBeanMapper initDozerMapper;

    @Override
    public ZrzySlymYwxxDTO queryZrzySlymYwxxDTO(String xmid, ZrzySlymYwxxDTO zrzySlymYwxxDTO){
        if(zrzySlymYwxxDTO == null){
            zrzySlymYwxxDTO = new ZrzySlymYwxxDTO();
        }
        if(StringUtils.isNotBlank(xmid)){
            List<ZrzyBdcqlglxxDO> zrzyBdcqlglxxList=listZrzyBdcqlglxxByXmid(xmid);
            if(CollectionUtils.isNotEmpty(zrzyBdcqlglxxList)){
                zrzyBdcqlglxxList.sort(Comparator.comparing(ZrzyBdcqlglxxDO::getBdcdyh, Comparator.nullsFirst(Comparator.naturalOrder())));
                zrzySlymYwxxDTO.setZrzyBdcqlglxxList(zrzyBdcqlglxxList);
            }
        }
        return zrzySlymYwxxDTO;

    }

    @Override
    public ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO,ZrzyInitServiceDTO zrzyInitServiceDTO){
        if(zrzyInitQO != null &&StringUtils.isNotBlank(zrzyInitQO.getSjly())){
            List<ZrzyBdcqlglxxDO> zrzyBdcqlglxxDOList =new ArrayList<>();
            if(StringUtils.equals(CommonConstantUtils.QLSJLY_LPB,zrzyInitQO.getSjly())){
                readDjData(zrzyInitQO,zrzyBdcqlglxxDOList);
            }else{
                readYxmData(zrzyInitQO,zrzyBdcqlglxxDOList);
            }
            zrzyInitServiceDTO.setZrzyBdcqlglxxDOList(zrzyBdcqlglxxDOList);
        }

        return zrzyInitServiceDTO;

    }

    /**
     * @param xmid 项目ID
     * @return 自然资源不动产权利关联信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取自然资源不动产权利关联信息
     */
    private List<ZrzyBdcqlglxxDO> listZrzyBdcqlglxxByXmid(String xmid){
        if(StringUtils.isNotBlank(xmid)){
            Example example =new Example(ZrzyBdcqlglxxDO.class);
            example.createCriteria().andEqualTo("xmid",xmid);
            List<ZrzyBdcqlglxxDO> zrzyBdcqlglxxList=entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(zrzyBdcqlglxxList)){
                return zrzyBdcqlglxxList;
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
    private List<ZrzyBdcqlglxxDO> readDjData(ZrzyInitQO zrzyInitQO,List<ZrzyBdcqlglxxDO> zrzyBdcqlglxxDOList){
        if(zrzyInitQO.getJbzkDTO() != null &&CollectionUtils.isNotEmpty(zrzyInitQO.getJbzkDTO().getBdcqlglxxDOList())){
            for(BdcqlglxxDO bdcqlglxxDO:zrzyInitQO.getJbzkDTO().getBdcqlglxxDOList()){
                ZrzyBdcqlglxxDO zrzyBdcqlglxxDO =new ZrzyBdcqlglxxDO();
                initDozerMapper.map(bdcqlglxxDO,zrzyBdcqlglxxDO);
                zrzyBdcqlglxxDO.setXmid(zrzyInitQO.getXmid());
                zrzyBdcqlglxxDO.setGlxxid(UUIDGenerator.generate16());
                zrzyBdcqlglxxDOList.add(zrzyBdcqlglxxDO);

            }

        }
        return zrzyBdcqlglxxDOList;

    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 从原项目中读取数据
      */
    private List<ZrzyBdcqlglxxDO> readYxmData(ZrzyInitQO zrzyInitQO,List<ZrzyBdcqlglxxDO> zrzyBdcqlglxxDOList){
        if(StringUtils.isNotBlank(zrzyInitQO.getYxmid())){
            List<ZrzyBdcqlglxxDO> yzrzyBdcqlglxxDOList =listZrzyBdcqlglxxByXmid(zrzyInitQO.getYxmid());
            if(CollectionUtils.isNotEmpty(yzrzyBdcqlglxxDOList)){
                for(ZrzyBdcqlglxxDO yzrzyBdcqlglxxDO:yzrzyBdcqlglxxDOList){
                    ZrzyBdcqlglxxDO zrzyBdcqlglxxDO =new ZrzyBdcqlglxxDO();
                    initDozerMapper.map(yzrzyBdcqlglxxDO,zrzyBdcqlglxxDO);
                    zrzyBdcqlglxxDO.setXmid(zrzyInitQO.getXmid());
                    zrzyBdcqlglxxDO.setGlxxid(UUIDGenerator.generate16());
                    zrzyBdcqlglxxDOList.add(zrzyBdcqlglxxDO);
                }

            }
        }
        return zrzyBdcqlglxxDOList;
    }

    @Override
    public void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO) {
        if (CollectionUtils.isNotEmpty(zrzyInitResultDTO.getZrzyBdcqlglxxDOList())) {
            zrzyInitResultDTO.getZrzyBdcqlglxxDOList().forEach(zrzyBdcqlglxxDO -> entityMapper.insertSelective(zrzyBdcqlglxxDO));
        }
    }

    @Override
    public void deleteYwxx(String xmid) {
        if(StringUtils.isNotBlank(xmid)){
            Example example =new Example(ZrzyBdcqlglxxDO.class);
            example.createCriteria().andEqualTo("xmid",xmid);
            entityMapper.deleteByExample(example);
        }
    }
}
