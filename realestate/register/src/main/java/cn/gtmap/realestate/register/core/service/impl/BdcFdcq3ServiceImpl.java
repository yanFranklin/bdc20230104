package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFdcq3DO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcFdcq3QO;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.BdcdyhToolUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.dto.BdcQlNumDTO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.mapper.BdcFdcq3Mapper;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import cn.gtmap.realestate.register.core.service.BdcFdcq3GyxxService;
import cn.gtmap.realestate.register.core.service.BdcFdcq3Service;
import cn.gtmap.realestate.register.core.service.BdcQlService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/11
 * @description 建筑物区分所有权共有部分
 */
@Service
public class BdcFdcq3ServiceImpl implements BdcFdcq3Service, BdcQlService {
    @Autowired
    BdcFdcq3GyxxService bdcFdcq3GyxxService;
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    BdcFdcq3Mapper bdcFdcq3Mapper;
    @Autowired
    Repo repo;


    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(2);
        set.add(BdcFdcq3DO.class.getSimpleName());
        set.add("94");
        return set;
    }


    /**
     * @param bdcQl   地役权
     * @param bdcZxQO 注销登簿人
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新Fdcq3注销信息
     */
    @Override
    public int updateZxDbxx(BdcQl bdcQl, BdcZxQO bdcZxQO) {
        if (bdcQl instanceof BdcFdcq3DO) {
            BdcFdcq3DO bdcFdcq3DO = (BdcFdcq3DO) bdcQl;
            bdcFdcq3DO.setQszt(bdcZxQO.getQszt());
            bdcFdcq3DO.setZxdbr(bdcZxQO.getZxdbr());
            bdcFdcq3DO.setZxdjsj(bdcZxQO.getZxdjsj());
            bdcFdcq3DO.setZxywh(bdcZxQO.getZxywh());
            bdcFdcq3DO.setZxyy(bdcZxQO.getZxyy());
            return entityMapper.updateByPrimaryKeyNull(bdcFdcq3DO);
        }
        return -1;
    }

    /**
     * @param bdcdyh 不动产单元
     * @param qsztList
     * @return List<BdcFdcqDTO>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 建筑物区分所有权业主共有部分登记信息, 包含共有信息
     */
    @Override
    public List<BdcQl> queryListBdcQl(String bdcdyh, List qsztList) {
        if (StringUtils.equals(BdcdyhToolUtils.getDzwTzm(bdcdyh), CommonConstantUtils.BDCLX_TZM_F)) {
            Example example = new Example(BdcFdcq3DO.class);
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
            bdcZxQlList = repo.selectList("listBdcZxFdcq3",bdcdyh);
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
     * @description 查询和单元号相关的所有共有信息的总量
     */
    @Override
    public BdcQlNumDTO countBdcQl(BdcQlNumDTO bdcQlNumDTO, String bdcdyh, List qsztList) {
        List<BdcQl> bdcQlList = queryListBdcQl(bdcdyh, qsztList);
        if (CollectionUtils.isNotEmpty(bdcQlList)) {
            bdcQlNumDTO.setCqNum(CollectionUtils.size(bdcQlList));
        }
        return bdcQlNumDTO;
    }

    /**
     * @param xmid 项目ID
     * @return BdcFdcq3DO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询建筑物区分所有权业主共有部分登记信息
     */
    @Override
    public List<BdcFdcq3DO> queryListBdcFdcq3(String xmid) {
        if (StringUtils.isNotBlank(xmid)) {
            Example example = new Example(BdcFdcq3DO.class);
            example.createCriteria().andEqualTo("xmid", xmid);
            return entityMapper.selectByExampleNotNull(example);
        }
        return new ArrayList();
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
        return null;
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
        return bdcFdcq3Mapper.updateQlGyqkPl(bdcGyqkDTO);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量流程更新项目生成的权利的登簿信息和权属状态
     */
    @Override
    public int udpateBdcQlDbxxAndQsztPl(DbxxQO dbxxQO) {
        return bdcFdcq3Mapper.udpateBdcQlDbxxAndQsztPl(dbxxQO);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量流程，更新上一手权利的权属状态（注销类限制权利还要更新注销登簿信息）
     */
    @Override
    public int udpateYqlZxDbxxAndQsztPl(DbxxQO dbxxQO) {
        return bdcFdcq3Mapper.udpateBdcQlZxDbxxAndQsztPl(dbxxQO);
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
        return bdcFdcq3Mapper.udpateBdcFdcq3QsztPl(dbxxQO);
    }


    /**
     * @param bdcFdcq3QO 建筑物所有权及业主共有信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 建筑物所有权及业主共有信息更新权利人
     */
    @Override
    public int updateFdcq3Qlr(BdcFdcq3QO bdcFdcq3QO) {
        return bdcFdcq3Mapper.updateFdcq3Qlr(bdcFdcq3QO);
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
        return bdcFdcq3Mapper.updateBdcQlDbrPl(dbxxQO);

    }

    @Override
    public int updateZxQlDbrPl(DbxxQO dbxxQO){
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        return bdcFdcq3Mapper.updateZxQlDbrPl(dbxxQO);

    }
}
