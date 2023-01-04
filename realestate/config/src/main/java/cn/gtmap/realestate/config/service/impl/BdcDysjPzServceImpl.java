package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDysjPzDO;
import cn.gtmap.realestate.common.core.domain.BdcDysjZbPzDO;
import cn.gtmap.realestate.common.core.dto.config.BdcDysjPzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.config.BdcDysjPzDateQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDypzFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.config.service.BdcDysjPzServce;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/13
 * @description
 */
@Service
public class BdcDysjPzServceImpl implements BdcDysjPzServce {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDysjPzServceImpl.class);
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    private Repo repo;
    @Autowired
    UserManagerUtils userManagerUtils;
    @Autowired
    BdcDypzFeignService bdcDypzFeignService;
    @Autowired
    RedisUtils redisUtils;

    /**
     * @param pageable
     * @param bdcDysjPzDateQO
     * @return 打印数据配置分页
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印数据配置分页数据
     */
    @Override
    public Page<BdcDysjPzDO> listBdcDysjPzByPage(Pageable pageable, BdcDysjPzDateQO bdcDysjPzDateQO) {
        return repo.selectPaging("listBdcDysjPzByPage", bdcDysjPzDateQO, pageable);
    }

    /**
     * @param id@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印主表配置
     */
    @Override
    public BdcDysjPzDO queryBdcDysjPz(String id) {
        if (StringUtils.isBlank(id)) {
            throw new AppException("获取打印主表配置主键不能为空！");
        }
        return entityMapper.selectByPrimaryKey(BdcDysjPzDO.class, id);
    }

    /**
     * @param id@return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取子表配置
     */
    @Override
    public BdcDysjZbPzDO queryBdcDysjZbPz(String id) {
        if (StringUtils.isBlank(id)) {
            throw new AppException("获取打印子表表配置主键不能为空！");
        }
        return entityMapper.selectByPrimaryKey(BdcDysjZbPzDO.class, id);
    }

    /**
     * @param bdcDysjPzDO
     * @return 保存打印数据主表配置
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存打印数据主表配置
     */
    @Override
    public int saveBdcDysjPz(BdcDysjPzDO bdcDysjPzDO) {
        if (bdcDysjPzDO == null) {
            throw new AppException("保存打印数据主表配置！");
        }
        bdcDysjPzDO.setId(UUIDGenerator.generate());
        return entityMapper.insertSelective(bdcDysjPzDO);
    }

    /**
     * @param bdcDysjPzDO
     * @return 修改的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改打印数据主表配置
     */
    @Override
    public int updateBdcDysjPz(BdcDysjPzDO bdcDysjPzDO) {
        if (bdcDysjPzDO == null) {
            throw new AppException("修改打印数据主表配置！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcDysjPzDO);
    }

    /**
     * @param bdcDysjPzDOList
     * @return 删除的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除打印数据主表配置
     */
    @Override
    public int deleteBdcDysjPz(List<BdcDysjPzDO> bdcDysjPzDOList) {
        if (CollectionUtils.isEmpty(bdcDysjPzDOList)) {
            throw new AppException("需要删除的默认意见不能为空！");
        }
        return bdcDysjPzDOList.stream().filter(bdcDysjPzDO -> StringUtils.isNotBlank(bdcDysjPzDO.getId())).mapToInt(bdcDysjPzDO -> entityMapper.deleteByPrimaryKey(BdcDysjPzDO.class, bdcDysjPzDO.getId())).sum();
    }

    /**
     * @param pageable
     * @param bdcDysjZbPzDO
     * @return 打印数据子表配置分页
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印数据子表配置分页数据
     */
    @Override
    public Page<BdcDysjZbPzDO> listBdcDysjZbPzByPage(Pageable pageable, BdcDysjZbPzDO bdcDysjZbPzDO) {
        return repo.selectPaging("listBdcDysjZbPzByPage", bdcDysjZbPzDO, pageable);
    }

    /**
     * @param bdcDysjZbPzDO
     * @return 保存的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存打印数据子表配置
     */
    @Override
    public int saveBdcDysjZbPz(BdcDysjZbPzDO bdcDysjZbPzDO) {
        if (bdcDysjZbPzDO == null) {
            throw new AppException("保存的打印数据子表配置不能为空！");
        }
        bdcDysjZbPzDO.setId(UUIDGenerator.generate());
        return entityMapper.insertSelective(bdcDysjZbPzDO);
    }

    /**
     * @param bdcDysjZbPzDO
     * @return 修改的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改打印数据子表配置
     */
    @Override
    public int updateBdcDysjZbPz(BdcDysjZbPzDO bdcDysjZbPzDO) {
        if (bdcDysjZbPzDO == null) {
            throw new AppException("修改打印数据主表配置！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcDysjZbPzDO);
    }

    /**
     * @param bdcDysjZbPzDOList
     * @return 删除的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除不动产打印数据配置配置
     */
    @Override
    public int deleteBdcDysjZbPz(List<BdcDysjZbPzDO> bdcDysjZbPzDOList) {
        if (CollectionUtils.isEmpty(bdcDysjZbPzDOList)) {
            throw new AppException("需要删除的打印数据子表配置不能为空！");
        }
        return bdcDysjZbPzDOList.stream().filter(bdcDysjZbPz -> StringUtils.isNotBlank(bdcDysjZbPz.getId())).mapToInt(bdcDysjZbPz -> entityMapper.deleteByPrimaryKey(BdcDysjZbPzDO.class, bdcDysjZbPz.getId())).sum();
    }

    /**
     * @param bdcDysjZbPzDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取打印子表配置数量
     */
    @Override
    public int countBdcDysjZbPz(BdcDysjZbPzDO bdcDysjZbPzDO) {
        if (bdcDysjZbPzDO == null || (StringUtils.isAnyBlank(bdcDysjZbPzDO.getDylx(), bdcDysjZbPzDO.getDyzbid()
        ))) {
            throw new AppException("获取子表配置数量的参数不能为空！");
        }
        Example example = new Example(BdcDysjZbPzDO.class);
        example.createCriteria().andEqualTo("dylx", bdcDysjZbPzDO.getDylx()).andEqualTo("dyzbid", bdcDysjZbPzDO.getDyzbid());
        return entityMapper.countByExample(example);
    }

    /**
     * @param dylx 打印类型
     * @return BdcDysjPzDTO 打印配置信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据打印类型查询主表和子表的配置信息，如果打印类型在库里重复，则会给出报错信息
     */
    @Override
    public BdcDysjPzDTO getPzxx(String dylx) {
        if (StringUtils.isBlank(dylx)) {
            throw new MissingArgumentException("缺失打印参数：打印类型dylx");
        }
        BdcDysjPzDTO bdcDysjPzDTO = new BdcDysjPzDTO();

        // 查询主表数据
        BdcDysjPzDO bdcDysjPzDO = this.queryBdcDysjPzByDylx(dylx);
        if (null == bdcDysjPzDO) {
            return bdcDysjPzDTO;
        }

        // 查询字表数据
        BdcDysjZbPzDO bdcDysjZbPzDO = new BdcDysjZbPzDO();
        bdcDysjZbPzDO.setDylx(dylx);
        List<BdcDysjZbPzDO> bdcDysjZbPzDOList = bdcDypzFeignService.listBdcDysjZbPz(bdcDysjZbPzDO);

        BeanUtils.copyProperties(bdcDysjPzDO, bdcDysjPzDTO);
        bdcDysjPzDTO.setBdcDysjZbPzDOList(bdcDysjZbPzDOList);
        return bdcDysjPzDTO;
    }

    /**
     * @param bdcDysjPzDTO 打印配置数据DTO
     * @return int 更新/保存的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 保存或更新打印配置信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BdcDysjPzDTO saveOrUpdatePzxx(BdcDysjPzDTO bdcDysjPzDTO) {
        if (null == bdcDysjPzDTO || StringUtils.isBlank(bdcDysjPzDTO.getDylx())) {
            throw new MissingArgumentException("缺失打印类型参数！");
        }
        String dylx = bdcDysjPzDTO.getDylx();
        // 主表主键(主键有值，表示更新，没有值时给予新值)
        String id;
        if (bdcDysjPzDTO.isFuZhi()) {
            id = UUIDGenerator.generate16();
        } else {
            id = StringUtils.isBlank(bdcDysjPzDTO.getId()) ? UUIDGenerator.generate16() : bdcDysjPzDTO.getId();
        }
        bdcDysjPzDTO.setId(id);

        // 保存主表信息
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        BeanUtils.copyProperties(bdcDysjPzDTO, bdcDysjPzDO);
        bdcDysjPzDO.setId(id);
        //获取当前Date类型的时间，并把时间赋值给DO的pzrq属性；（BDC_DYSJ_PZ的PZRQ）
        Date pzrq = new Date();
        bdcDysjPzDO.setPzrq(pzrq);



        int result = entityMapper.saveOrUpdate(bdcDysjPzDO, id);
        LOGGER.warn("更新主表数据{}条，更新信息{}", result, bdcDysjPzDO);

        if (CollectionUtils.isEmpty(bdcDysjPzDTO.getBdcDysjZbPzDOList())) {
            LOGGER.warn("打印类型{}没有子表信息", dylx);
            return bdcDysjPzDTO;
        }
        // 保存子表信息，子表信息先更加打印类型删除，再保存数据
        List<BdcDysjZbPzDO> bdcDysjZbPzDOList = bdcDysjPzDTO.getBdcDysjZbPzDOList();
        for (BdcDysjZbPzDO bdcDysjZbPzDO : bdcDysjZbPzDOList) {
            bdcDysjZbPzDO.setDylx(dylx);
            bdcDysjZbPzDO.setId(UUIDGenerator.generate16());
        }
        // 先删除
        Example example = new Example(BdcDysjZbPzDO.class);
        example.createCriteria().andEqualTo("dylx", dylx);
        result = entityMapper.deleteByExampleNotNull(example);
        LOGGER.warn("删除子表数据{}条", result);

        // 再保存（insertBatchSelective 批量保存值太大时clob有异常，所以采用单个循环保存）
        result = 0;
        for (BdcDysjZbPzDO bdcDysjZbPzDO : bdcDysjZbPzDOList) {
            result += entityMapper.insertSelective(bdcDysjZbPzDO);
        }
        LOGGER.warn("更新子表数据{}条", result);

        return bdcDysjPzDTO;
    }

    @Override
    public String sendXmlToRedis(String xml) {
        return redisUtils.addStringValue(UUIDGenerator.generate16(), xml, 60);
    }

    /**
     * @param redisKey redis健
     * @return String xml信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 从redis中获取保存的xml信息
     */
    @Override
    public String getXmlFromRedis(String redisKey) {
        return redisUtils.getStringValue(redisKey);
    }

    /**
     * @param dylx 打印类型
     * @return BdcDysjPzDO 打印数据信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据打印类型获取打印主表配置信息
     */
    @Override
    public BdcDysjPzDO queryBdcDysjPzByDylx(String dylx) {
        BdcDysjPzDO bdcDysjPzDO = new BdcDysjPzDO();
        bdcDysjPzDO.setDylx(dylx);
        List<BdcDysjPzDO> bdcDysjPzDOList = bdcDypzFeignService.listBdcDysjPz(bdcDysjPzDO);
        if (CollectionUtils.isEmpty(bdcDysjPzDOList)) {
            return null;
        }
        if (CollectionUtils.size(bdcDysjPzDOList) > 1) {
            throw new IllegalArgumentException("dylx：" + dylx + "——查询到多条打印主表数据，请先处理数据！");
        }
        return bdcDysjPzDOList.get(0);
    }

    /**
     * @param dylxList 打印类型list
     * @return Map 各个打印类型的配置信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量获取各打印类型的配置信息
     */
    @Override
    public Map queryBdcDysjPzByDylx(List<String> dylxList) {
        Map map = new HashMap();
        if (CollectionUtils.isNotEmpty(dylxList)) {
            for (String dylx : dylxList) {
                BdcDysjPzDO bdcDysjPzDO = this.queryBdcDysjPzByDylx(dylx);
                if (null != bdcDysjPzDO) {
                    map.put(dylx, bdcDysjPzDO);
                }
            }
        }
        return map;
    }

    @Override
    public int checkDylx(String checkDylx) {
        Example example = new Example(BdcDysjPzDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dylx", checkDylx);
        int result = entityMapper.countByExample(BdcDysjPzDO.class, example);
        return result;
    }
}
