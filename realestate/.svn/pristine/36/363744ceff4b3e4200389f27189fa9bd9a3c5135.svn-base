package cn.gtmap.realestate.init.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcDjxlDjyyGxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.core.qo.init.BdcDjxlDjyyQO;
import cn.gtmap.realestate.init.core.service.BdcDjxlDjyyGxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/5/31
 * @description 登记类型、登记原因关系业务类
 */
@Service
public class BdcDjxlDjyyGxServiceImpl implements BdcDjxlDjyyGxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcDjxlDjyyGxServiceImpl.class);
    @Autowired
    EntityMapper entityMapper;

    /**
     * @param bdcDjxlDjyyGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询
     */
    @Override
    public List<BdcDjxlDjyyGxDO> listBdcDjxlDjyyGx(BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO) {
        if (bdcDjxlDjyyGxDO == null) {
            LOGGER.debug("初始化子系统——获取 登记类型、登记原因关系配置的参数为空！");
            throw new AppException("获取登记类型、登记原因配置的参数不能为空！");
        }
        return entityMapper.selectByObj(bdcDjxlDjyyGxDO);
    }

    @Override
    public int queryDjyyMaxCount(String djxl) {
        if(StringUtils.isNotBlank(djxl)){
            Example example = new Example(BdcDjxlDjyyGxDO.class);
            example.createCriteria().andEqualTo("djxl", djxl);
            return entityMapper.countByExample(example);
        }
        return 0;
    }

    /**
     * @param bdcDjxlDjyyGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 新增
     */
    @Override
    public int insertBdcDjxlDjyyGx(BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO) {
        if (bdcDjxlDjyyGxDO == null) {
            LOGGER.debug("初始化子系统——新增登记类型、登记原因关系配置的参数为空！");
            throw new AppException("新增登记类型、登记原因 关系配置不能为空！");
        }
        if(StringUtils.isBlank(bdcDjxlDjyyGxDO.getId())){
            bdcDjxlDjyyGxDO.setId(UUIDGenerator.generate16());
        }
        return entityMapper.saveOrUpdate(bdcDjxlDjyyGxDO,bdcDjxlDjyyGxDO.getId());
    }

    /**
     * @param bdcDjxlDjyyGxDO
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改
     */
    @Override
    public int updateBdcDjxlDjyyGx(BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO) {
        if (bdcDjxlDjyyGxDO == null) {
            LOGGER.debug("初始化子系统——修改登记类型、登记原因关系配置的参数为空！");
            throw new AppException("修改登记类型、登记原因关系配置的参数不能为空！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcDjxlDjyyGxDO);
    }

    @Override
    public void batchUpdateBdcDjxlDjyyGx(List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList) {
        if(CollectionUtils.isNotEmpty(bdcDjxlDjyyGxDOList)){
           entityMapper.batchSaveSelective(bdcDjxlDjyyGxDOList);
        }
    }

    /**
     * @param bdcDjxlDjyyGxDOList
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除
     */
    @Override
    public int deleteBdcDjxlDjyyGx(List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOList) {
        if (CollectionUtils.isEmpty(bdcDjxlDjyyGxDOList)) {
            LOGGER.debug("初始化子系统——删除登记类型、登记原因关系配置的参数为空！");
            throw new AppException("删除登记类型、登记原因关系配置的参数不能为空！");
        }
        return bdcDjxlDjyyGxDOList.stream().filter(bdcDjxlDjyyGxDO -> StringUtils.isNotBlank(bdcDjxlDjyyGxDO.getId())).mapToInt(bdcDjxlDjyyGxDO -> entityMapper.deleteByPrimaryKey(BdcDjxlDjyyGxDO.class, bdcDjxlDjyyGxDO.getId())).sum();

    }

    /**
     * 根据登记小类查询所有的登记原因
     * @param djxls
     * @return List<String> 登记原因集合
     * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description 查询
     */
    @Override
    public List<String> listDjyys(List<String> djxls) {
        //返回结果
        List<String> result=new ArrayList<>();
        //查询数据库结果
        List<BdcDjxlDjyyGxDO> list=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(djxls)){
            for(String djxl:djxls){
                if(StringUtils.isNotBlank(djxl)){
                    BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO=new BdcDjxlDjyyGxDO();
                    bdcDjxlDjyyGxDO.setDjxl(djxl);
                    List<BdcDjxlDjyyGxDO> listDjyy=listBdcDjxlDjyyGx(bdcDjxlDjyyGxDO);
                    if(CollectionUtils.isNotEmpty(listDjyy)){
                        list.addAll(listDjyy);
                    }
                }
            }
        }else{
            List<BdcDjxlDjyyGxDO> listDjyy=entityMapper.select(new BdcDjxlDjyyGxDO());
            if(CollectionUtils.isNotEmpty(listDjyy)){
                list.addAll(listDjyy);
            }
        }
        //去重
        if(CollectionUtils.isNotEmpty(list)){
             for(BdcDjxlDjyyGxDO bdcDjxlDjyyGxDO:list){
                 if(StringUtils.isNotBlank(bdcDjxlDjyyGxDO.getDjyy()) && !result.contains(bdcDjxlDjyyGxDO.getDjyy())){
                     result.add(bdcDjxlDjyyGxDO.getDjyy());
                 }
             }
        }
        return result;
    }

    @Override
    public List<BdcDjxlDjyyGxDO> listBdcDjxlDjyyGxWithParam(BdcDjxlDjyyQO bdcDjxlDjyyQO){
        List<BdcDjxlDjyyGxDO> bdcDjxlDjyyGxDOS =new ArrayList<>();
        if(StringUtils.isNotBlank(bdcDjxlDjyyQO.getDjxl())) {
            Example example = new Example(BdcDjxlDjyyGxDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("djxl",bdcDjxlDjyyQO.getDjxl());
            if(StringUtils.isNotBlank(bdcDjxlDjyyQO.getGzldyid())){
                criteria.andEqualNvlTo("gzldyid",bdcDjxlDjyyQO.getGzldyid(),bdcDjxlDjyyQO.getGzldyid());
            }
            example.setOrderByClause("sxh ASC");
            bdcDjxlDjyyGxDOS =entityMapper.selectByExample(example);
        }
        return bdcDjxlDjyyGxDOS;
    }
}
