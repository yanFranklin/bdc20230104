package cn.gtmap.realestate.engine.core.service.impl;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBdsTsxxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.engine.core.mapper.BdcGzSjlMapper;
import cn.gtmap.realestate.engine.core.service.BdcGzZgzDaoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/8
 * @description
 */
@Service
public class BdcGzZgzDaoServiceImpl implements BdcGzZgzDaoService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcGzSjlMapper bdcGzSjlMapper;

    /**
     * @param gzid
     * @return bdcGzZgzDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 根据规则id获取子规则
     */
    @Override
    public BdcGzZgzDO queryBdcGzZgzDO(String gzid) {
        if(StringUtils.isBlank(gzid)){
            throw new AppException("查询参数不能为空!");
        }
        return entityMapper.selectByPrimaryKey(BdcGzZgzDO.class,gzid);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 子规则集合
     * @description 获取所有的子规则
     */
    @Override
    public List<BdcGzZgzDO> listBdcGzZgzDO(){
        Example example = new Example(BdcGzZgzDO.class);
        return entityMapper.selectByExample(example);
    }

    /**
     * @param bdcGzZgzDO
     * @return bdcGzZgzDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 新增子规则记录
     */
    @Override
    public BdcGzZgzDO insertBdcGzZgz(BdcGzZgzDO bdcGzZgzDO) {
        if(bdcGzZgzDO != null){
            if(StringUtils.isBlank(bdcGzZgzDO.getGzid())){
                bdcGzZgzDO.setGzid(UUIDGenerator.generate());
            }
            entityMapper.insertSelective(bdcGzZgzDO);
        }
        return bdcGzZgzDO;
    }

    /**
     * @param bdcGzSjlDO
     * @return bdcGzSjlDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 新增子规则数据流记录
     */
    @Override
    public BdcGzSjlDO insertBdcGzSjl(BdcGzSjlDO bdcGzSjlDO) {
        if(bdcGzSjlDO != null){
            if(StringUtils.isBlank(bdcGzSjlDO.getSjlid())){
                bdcGzSjlDO.setSjlid(UUIDGenerator.generate());
            }
            entityMapper.insertSelective(bdcGzSjlDO);
        }
        return bdcGzSjlDO;
    }

    /**
     * @param bdcGzSjlCsDO
     * @return bdcGzSjlCsDO
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 新增子规则数据流参数记录
     */
    @Override
    public BdcGzSjlCsDO insertBdcGzSjlCs(BdcGzSjlCsDO bdcGzSjlCsDO) {
        if(bdcGzSjlCsDO != null){
            if(StringUtils.isBlank(bdcGzSjlCsDO.getSjlcsid())){
                bdcGzSjlCsDO.setSjlcsid(UUIDGenerator.generate());
            }
            entityMapper.insertSelective(bdcGzSjlCsDO);
        }
        return bdcGzSjlCsDO;
    }

    /**
     * @param bdcGzZgzDO
     * @return num
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 删除不动产规则子规则
     */
    @Override
    public int delBdcGzZgz(BdcGzZgzDO bdcGzZgzDO) {
        if(bdcGzZgzDO==null){
            throw new AppException("删除对象不能为空");
        }
        return entityMapper.deleteByPrimaryKey(bdcGzZgzDO.getClass(),bdcGzZgzDO.getGzid());
    }

    /**
     * @param gzid
     * @return bdcGzSjlDOList
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过gzid获取子规则数据流
     */
    @Override
    public List<BdcGzSjlDO> listBdcGzSjlDOByGzid(String gzid) {
        if(StringUtils.isBlank(gzid)){
            throw new AppException("查询参数不可为空");
        }

        return bdcGzSjlMapper.queryBdcGzSjl(gzid);
    }

    /**
     * @param sjlid
     * @return bdcGzSjlCsDOList
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 通过sjid获取数据流参数
     */
    @Override
    public List<BdcGzSjlCsDO> queryBdcGzSjlCsDOListBySjlid(String sjlid) {
        if(StringUtils.isBlank(sjlid)){
            throw new AppException("查询参数不能为空");
        }
        Example example = new Example(BdcGzSjlCsDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sjlid",sjlid);
        return entityMapper.selectByExample(example);
    }

    /**
     * @param sjlid
     * @return num
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 删除sjlid对应的数据流参数
     */
    @Override
    public int delBdcGzSjlCsBySjlid(String sjlid) {
        if(StringUtils.isBlank(sjlid)){
            throw new AppException("查询参数不能为空");
        }
        Example example = new Example(BdcGzSjlCsDO.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sjlid",sjlid);
        return entityMapper.deleteByExample(example);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzid 子规则ID
     * @description 删除子规则关联的表达式、提示信息
     */
    @Override
    public int deleteGzBdsTsxxByGzid(String gzid) {
        if(StringUtils.isBlank(gzid)){
            return 0;
        }

        Example example = new Example(BdcGzBdsTsxxDO.class);
        example.createCriteria().andEqualTo("gzid", gzid);
        return entityMapper.deleteByExample(example);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsTsxxDO 规则表达式
     * @return {int} 数量
     * @description 新增规则表达式、提示信息
     */
    @Override
    public int insertBdcGzBdsTsxx(BdcGzBdsTsxxDO bdcGzBdsTsxxDO) {
        if(null == bdcGzBdsTsxxDO){
            return 0;
        }

        return entityMapper.insert(bdcGzBdsTsxxDO);
    }

    /**
     * @param bdcGzSjlDO
     * @return num
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 删除不动产规则数据流
     */
    @Override
    public int delBdcGzSjl(BdcGzSjlDO bdcGzSjlDO) {
        if(bdcGzSjlDO == null){
            throw new AppException("删除对象不能为空");
        }
        return entityMapper.delete(bdcGzSjlDO);
    }
}
