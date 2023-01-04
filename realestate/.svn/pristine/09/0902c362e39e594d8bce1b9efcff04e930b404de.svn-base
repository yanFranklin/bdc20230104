package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcYgDO;
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
import cn.gtmap.realestate.register.core.mapper.BdcYgMapper;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import cn.gtmap.realestate.register.core.service.BdcXzQlService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/10
 * @description 不动产预告查询服务类实现
 */
@Service
public class BdcYgServiceImpl implements BdcXzQlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcYgServiceImpl.class);

    @Autowired
    EntityMapper entityMapper;
    @Autowired
    Repo repo;
    @Autowired
    BdcYgMapper bdcYgMapper;


    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        set.add(new BdcYgDO().getClass().getSimpleName());
        set.add("96");
        /**
         * 增加ydya是为了区分预告预抵押合并流程在更新登簿信息时，
         * 将权利区分以后，保证预抵押也能正常执行到预告的实现类
         */
        set.add(CommonConstantUtils.YGDJ_YDY);
        return set;
    }

    /**
     * @param bdcdyh
     * @param qsztList
     * @return List<BdcYgDO> 不动产预告集合
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取bdcdyh的所有预告, 根据djsj升序排序
     */
    @Override
    public List<BdcQl> queryListBdcQl(String bdcdyh, List qsztList) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Example example = new Example(BdcYgDO.class);
            example.setOrderByClause("djsj ASC,slbh ASC");

            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("bdcdyh", bdcdyh);
            if (CollectionUtils.isNotEmpty(qsztList)) {
                criteria.andIn("qszt", qsztList);
            }
            return entityMapper.selectByExampleNotNull(example);
        }
        return new ArrayList();
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
            bdcZxQlList = repo.selectList("listBdcZxYg",bdcdyh);
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
     * @description 获取当前单元号的预告的总量
     */
    @Override
    public BdcQlNumDTO countBdcQl(BdcQlNumDTO bdcQlNumDTO, String bdcdyh, List qsztList) {
        if (StringUtils.isNotBlank(bdcdyh)) {
            Example example = new Example(BdcYgDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("bdcdyh", bdcdyh);
            if (CollectionUtils.isNotEmpty(qsztList)) {
                criteria.andIn("qszt", qsztList);
            }
            bdcQlNumDTO.setYgNum(entityMapper.countByExample(BdcYgDO.class, example));
        }
        return bdcQlNumDTO;
    }

    /**
     * @param bdcQl 预告
     * @param bdcZxQO   注销信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新预告注销信息
     */
    @Override
    public int updateZxDbxx(BdcQl bdcQl, BdcZxQO bdcZxQO) {
        if (bdcQl instanceof BdcYgDO) {
            BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
            bdcYgDO.setQszt(bdcZxQO.getQszt());
            bdcYgDO.setZxygdbr(bdcZxQO.getZxdbr());
            bdcYgDO.setZxygdjsj(bdcZxQO.getZxdjsj());
            bdcYgDO.setZxygywh(bdcZxQO.getZxywh());
            bdcYgDO.setZxygyy(bdcZxQO.getZxyy());
            return entityMapper.updateByPrimaryKeyNull(bdcYgDO);
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
        // 预告
        BdcQlNumDTO bdcQlNumDTO = new BdcQlNumDTO();
        bdcQlNumDTO = countBdcQl(bdcQlNumDTO, bdcdyh, qsztList);
        if (bdcQlNumDTO.getYgNum() > 0) {
            bdcDjbQlDTO.setYg("yg");
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
        return repo.selectPaging("listYgByPage", map, pageable);
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
        return bdcYgMapper.updateQlGyqkPl(bdcGyqkDTO);
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
            throw new MissingArgumentException("缺失工作流实例ID参数");
        }
        return bdcYgMapper.udpateBdcQlDbxxAndQsztPl(dbxxQO);
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
            throw new MissingArgumentException("缺失工作流实例ID参数");
        }
        return bdcYgMapper.udpateBdcQlZxDbxxAndQsztPl(dbxxQO);
    }

    public int udpateYgYdyYqlZxDbxxAndQsztPl(DbxxQO dbxxQO) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID参数");
        }
        LOGGER.info("原权利为预告预抵押，更新状态为注销历史状态，对应信息：{}", JSON.toJSONString(dbxxQO));
        return bdcYgMapper.udpateYgYdyYqlZxDbxxAndQsztPl(dbxxQO);
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
        return bdcYgMapper.udpateBdcYgQsztPl(dbxxQO);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新预抵押的登簿信息和权属状态
     */
    public int updateYdyaQlDbxxAndQsztPl(DbxxQO dbxxQO) {
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID参数");
        }
        return bdcYgMapper.updateYdyaQlDbxxAndQsztPl(dbxxQO);
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
        return bdcYgMapper.updateBdcQlDbrPl(dbxxQO);

    }

    @Override
    public int updateZxQlDbrPl(DbxxQO dbxxQO){
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        return bdcYgMapper.updateZxQlDbrPl(dbxxQO);

    }
}
