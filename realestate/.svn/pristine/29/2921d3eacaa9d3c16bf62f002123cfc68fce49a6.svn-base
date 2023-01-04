package cn.gtmap.realestate.natural.core.service.impl.glxx;

import cn.gtmap.realestate.common.core.domain.natural.*;
import cn.gtmap.realestate.common.core.domain.naturalresource.GggzglxxDO;
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
 * @description 自然资源公共管制关联信息服务
 */
@Service
public class ZrzyGggzGlxxServiceImpl implements ZrzyCommonService {

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
            List<ZrzyGggzglxxDO> zrzyGggzglxxList=listZrzyGggzglxxByXmid(xmid);
            if(CollectionUtils.isNotEmpty(zrzyGggzglxxList)){
                zrzyGggzglxxList.sort(Comparator.comparing(ZrzyGggzglxxDO::getQkbh, Comparator.nullsFirst(Comparator.naturalOrder())));
                zrzySlymYwxxDTO.setZrzyGggzglxxList(zrzyGggzglxxList);
            }
        }
        return zrzySlymYwxxDTO;

    }

    @Override
    public ZrzyInitServiceDTO initYwxx(ZrzyInitQO zrzyInitQO, ZrzyInitServiceDTO zrzyInitServiceDTO){
        if(zrzyInitQO != null &&StringUtils.isNotBlank(zrzyInitQO.getSjly())){
            List<ZrzyGggzglxxDO> zrzyGggzglxxDOList =new ArrayList<>();
            if(StringUtils.equals(CommonConstantUtils.QLSJLY_LPB,zrzyInitQO.getSjly())){
                readDjData(zrzyInitQO,zrzyGggzglxxDOList);
            }else{
                readYxmData(zrzyInitQO,zrzyGggzglxxDOList);
            }
            zrzyInitServiceDTO.setZrzyGggzglxxDOList(zrzyGggzglxxDOList);
        }
        return zrzyInitServiceDTO;

    }


    /**
      * @param xmid 项目ID
      * @return 自然资源公共管制关联信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据项目ID获取自然资源公共管制关联信息
      */
    private List<ZrzyGggzglxxDO> listZrzyGggzglxxByXmid(String xmid){
        if(StringUtils.isNotBlank(xmid)){
            Example example =new Example(ZrzyGggzglxxDO.class);
            example.createCriteria().andEqualTo("xmid",xmid);
            List<ZrzyGggzglxxDO> zrzyGggzglxxList=entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(zrzyGggzglxxList)){
                return zrzyGggzglxxList;
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
    private List<ZrzyGggzglxxDO> readDjData(ZrzyInitQO zrzyInitQO,List<ZrzyGggzglxxDO> zrzyGggzglxxDOList){
        if(zrzyInitQO.getJbzkDTO() != null &&CollectionUtils.isNotEmpty(zrzyInitQO.getJbzkDTO().getGggzglxxDOList())){
            for(GggzglxxDO gggzglxxDO:zrzyInitQO.getJbzkDTO().getGggzglxxDOList()){
                ZrzyGggzglxxDO zrzyGggzglxxDO =new ZrzyGggzglxxDO();
                initDozerMapper.map(gggzglxxDO,zrzyGggzglxxDO);
                zrzyGggzglxxDO.setXmid(zrzyInitQO.getXmid());
                zrzyGggzglxxDO.setGlxxid(UUIDGenerator.generate16());
                zrzyGggzglxxDO.setGdsdsj(gggzglxxDO.getHdsdsj());
                zrzyGggzglxxDOList.add(zrzyGggzglxxDO);

            }

        }
        return zrzyGggzglxxDOList;

    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 从原项目中读取数据
     */
    private List<ZrzyGggzglxxDO> readYxmData(ZrzyInitQO zrzyInitQO,List<ZrzyGggzglxxDO> zrzyGggzglxxDOList){
        if(StringUtils.isNotBlank(zrzyInitQO.getYxmid())){
            List<ZrzyGggzglxxDO> yzrzyGggzglxxDOList =listZrzyGggzglxxByXmid(zrzyInitQO.getYxmid());
            if(CollectionUtils.isNotEmpty(yzrzyGggzglxxDOList)){
                for(ZrzyGggzglxxDO yzrzyGggzglxxDO:yzrzyGggzglxxDOList){
                    ZrzyGggzglxxDO zrzyGggzglxxDO =new ZrzyGggzglxxDO();
                    initDozerMapper.map(yzrzyGggzglxxDO,zrzyGggzglxxDO);
                    zrzyGggzglxxDO.setXmid(zrzyInitQO.getXmid());
                    zrzyGggzglxxDO.setGlxxid(UUIDGenerator.generate16());
                    zrzyGggzglxxDOList.add(zrzyGggzglxxDO);
                }

            }
        }
        return zrzyGggzglxxDOList;
    }

    @Override
    public void saveYwxx(ZrzyInitResultDTO zrzyInitResultDTO) {
        if (CollectionUtils.isNotEmpty(zrzyInitResultDTO.getZrzyGggzglxxDOList())) {
            zrzyInitResultDTO.getZrzyGggzglxxDOList().forEach(zrzyGggzglxxDO -> entityMapper.insertSelective(zrzyGggzglxxDO));
        }
    }

    @Override
    public void deleteYwxx(String xmid) {
        if(StringUtils.isNotBlank(xmid)){
            Example example =new Example(ZrzyGggzglxxDO.class);
            example.createCriteria().andEqualTo("xmid",xmid);
            entityMapper.deleteByExample(example);
        }
    }
}
