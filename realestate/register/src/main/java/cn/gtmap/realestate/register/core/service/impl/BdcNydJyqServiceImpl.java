package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcNydjyqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbQlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.dto.BdcQlNumDTO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.mapper.BdcNydJyqMapper;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import cn.gtmap.realestate.register.core.service.BdcXzQlService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>liaoxiang</a>"
 * @version 1.0, 2020/8/13
 * @description 不动产经营权查询实现服务
 */
@Service("bdcNydJyqServiceImpl")
public class BdcNydJyqServiceImpl implements BdcXzQlService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    Repo repo;
    @Autowired
    BdcNydJyqMapper bdcNydJyqMapper;


    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        set.add(BdcNydjyqDO.class.getSimpleName());
        set.add(CommonConstantUtils.QLLX_NYDJYQ.toString());
        return set;
    }

    /**
     * @param bdcdyh 不动产单元号
     * @param qsztList
     * @return List<BdcNydjyqDO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前单元的经营权登记，并按照djsj升序排序
     */
    @Override
    public List<BdcQl> queryListBdcQl(String bdcdyh, List qsztList) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Example example = new Example(BdcNydjyqDO.class);
            example.setOrderByClause("djsj ASC,slbh ASC");

            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("bdcdyh", bdcdyh);
            if (CollectionUtils.isNotEmpty(qsztList)) {
                criteria.andIn("qszt", qsztList);
            }
            return entityMapper.selectByExampleNotNull(example);
        }
        return new ArrayList<>();
    }

    /**
     * @param bdcdyh 不动产单元号
     * @return list 注销权利信息
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 查询不动产单元的指定类型的注销权利信息
     */
    @Override
    public List<BdcQl> queryListBdcZxQl(String bdcdyh) {
        List<BdcQl> bdcZxQlList = new ArrayList<>();
        if (StringUtils.isNotBlank(bdcdyh)) {
            bdcZxQlList = repo.selectList("listZxTdjyq",bdcdyh);
        }
        return bdcZxQlList;
    }

    /**
     *
     * @param bdcQlNumDTO
     * @param bdcdyh
     * @param qsztList
     * @return Integer 查询结果数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前单元号的经营权总量
     */
    @Override
    public BdcQlNumDTO countBdcQl(BdcQlNumDTO bdcQlNumDTO, String bdcdyh, List qsztList) {

        if (StringUtils.isNotBlank(bdcdyh)) {
            Example example = new Example(BdcNydjyqDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("bdcdyh", bdcdyh);
            if (CollectionUtils.isNotEmpty(qsztList)) {
                criteria.andIn("qszt", qsztList);
            }
            bdcQlNumDTO.setJyqNum(entityMapper.countByExample(BdcNydjyqDO.class, example));
        }
        return bdcQlNumDTO;
    }


    /**
     * @param bdcQl
     * @param bdcZxQO   注销登簿人
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新经营权注销信息
     */
    @Override
    public int updateZxDbxx(BdcQl bdcQl, BdcZxQO bdcZxQO) {
        if (bdcQl instanceof BdcNydjyqDO) {
            BdcNydjyqDO bdcNydjyqDO = (BdcNydjyqDO) bdcQl;
            bdcNydjyqDO.setQszt(bdcZxQO.getQszt());
            bdcNydjyqDO.setZxdbr(bdcZxQO.getZxdbr());
            bdcNydjyqDO.setZxdjsj(bdcZxQO.getZxdjsj());
            bdcNydjyqDO.setZxyy(bdcZxQO.getZxyy());
            return entityMapper.updateByPrimaryKeyNull(bdcNydjyqDO);
        }
        return -1;
    }

    /**
     * @param bdcDjbQlDTO 登记簿权利DTO
     * @param bdcdyh      不动产单元号
     * @param qsztList
     * @return BdcDjbQlDTO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 判断bdcdyh的限制权利信息
     */
    @Override
    public BdcDjbQlDTO judgeDjbXzQl(BdcDjbQlDTO bdcDjbQlDTO, String bdcdyh, List<Integer> qsztList) {
        // 经营权
        BdcQlNumDTO bdcQlNumDTO = new BdcQlNumDTO();
        bdcQlNumDTO = countBdcQl(bdcQlNumDTO, bdcdyh, qsztList);
        if (bdcQlNumDTO.getJyqNum() > 0) {
            bdcDjbQlDTO.setJyq("jyq");
        }
        return bdcDjbQlDTO;
    }

    /**
     * @param map      查询参数
     * @param pageable
     * @return Page<BdcQl>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 分页查询各权利
     */
    @Override
    public Page<BdcQl> bdcQlByPageJson(Map<String, Object> map, Pageable pageable) {
        return repo.selectPaging("listTdjyqByPage", map, pageable);
    }

    /**
     * @param bdcGyqkDTO 不动产共有情况DTO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新同个权利的共有情况信息
     */
    @Override
    public int updateQlGyqkPl(BdcGyqkDTO bdcGyqkDTO) {
        if (StringUtils.isBlank(bdcGyqkDTO.getGzlslid()) && CollectionUtils.isEmpty(bdcGyqkDTO.getXmidList())) {
            throw new MissingArgumentException("缺失工作流实例ID和xmidList");
        }
        return bdcNydJyqMapper.updateQlGyqkPl(bdcGyqkDTO);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量流程更新项目生成的权利的登簿信息和权属状态
     */
    @Override
    public int udpateBdcQlDbxxAndQsztPl(DbxxQO dbxxQO) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        return bdcNydJyqMapper.udpateBdcQlDbxxAndQsztPl(dbxxQO);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量流程，更新上一手权利的权属状态（注销类限制权利还要更新注销登簿信息）
     */
    @Override
    public int udpateYqlZxDbxxAndQsztPl(DbxxQO dbxxQO) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        return bdcNydJyqMapper.udpateBdcQlZxDbxxAndQsztPl(dbxxQO);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量更新权利的权属状态
     */
    @Override
    public int updateBdcQlQsztPl(DbxxQO dbxxQO) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        return bdcNydJyqMapper.udpateBdcNydjyqQsztPl(dbxxQO);
    }

    @Override
    public void updateRyzdQlr(BdcRyzdDTO bdcRyzdDTO){


    }

    @Override
    public BdcSyqxDTO generateQlSyqx(BdcQl bdcQl){
        return null;
    }

    @Override
    public BdcSyqxDTO generateQlSyqxPl(JSONObject jsonObject){
        return null;
    }

    @Override
    public int updateBdcQlDbrPl(DbxxQO dbxxQO){
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        return bdcNydJyqMapper.updateBdcQlDbrPl(dbxxQO);

    }

    @Override
    public int updateZxQlDbrPl(DbxxQO dbxxQO){
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        return bdcNydJyqMapper.updateZxQlDbrPl(dbxxQO);

    }

}
