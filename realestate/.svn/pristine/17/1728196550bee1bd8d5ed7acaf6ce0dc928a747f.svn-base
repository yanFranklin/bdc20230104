package cn.gtmap.realestate.register.core.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcFwzlDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.dto.register.BdcDjbQlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcSyqxDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.register.BdcZxQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.register.core.dto.BdcGyqkDTO;
import cn.gtmap.realestate.register.core.dto.BdcQlNumDTO;
import cn.gtmap.realestate.register.core.dto.BdcRyzdDTO;
import cn.gtmap.realestate.register.core.mapper.BdcFwzlMapper;
import cn.gtmap.realestate.register.core.qo.DbxxQO;
import cn.gtmap.realestate.register.core.service.BdcXzQlService;
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
 * @description 房屋租赁
 */
@Service
public class BdcFwzlServiceImpl implements BdcXzQlService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    Repo repo;
    @Autowired
    BdcFwzlMapper bdcFwzlMapper;


    /**
     * @return 接口标识码，同一接口中的标识码不能出现重复
     * @author <a href ="mailto:zhaodongdong@gtmap.cn">zhaodongdong</a>
     * @description 获取实现类的标识码
     */
    @Override
    public Set<String> getInterfaceCode() {
        Set<String> set = new LinkedHashSet<>(1);
        set.add(BdcFwzlDO.class.getClass().getSimpleName());
        return set;
    }

    /**
     * @param bdcQl 房屋租赁
     * @param bdcZxQO     注销登簿人
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新房屋租赁权注销信息
     */
    @Override
    public int updateZxDbxx(BdcQl bdcQl, BdcZxQO bdcZxQO) {
        if (bdcQl instanceof BdcFwzlDO) {
            BdcFwzlDO bdcFwzlDO = (BdcFwzlDO) bdcQl;
            bdcFwzlDO.setQszt(bdcZxQO.getQszt());
            bdcFwzlDO.setZxfwzldbr(bdcZxQO.getZxdbr());
            bdcFwzlDO.setZxfwzldjsj(bdcZxQO.getZxdjsj());
            bdcFwzlDO.setZxfwzlywh(bdcZxQO.getZxywh());
            bdcFwzlDO.setZxfwzlyy(bdcZxQO.getZxyy());
            return entityMapper.updateByPrimaryKeyNull(bdcFwzlDO);
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
        // nothing to do
        return bdcDjbQlDTO;
    }

    /**
     * @param bdcdyh 不动产单元
     * @param qsztList
     * @return List<BdcQl>
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 不动产权利信息，按照登记时间升序排序
     */
    @Override
    public List<BdcQl> queryListBdcQl(String bdcdyh, List qsztList) {
        // nothing to do
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
            bdcZxQlList = repo.selectList("listBdcZxFwzl",bdcdyh);
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
     * @description 查询单元号办理该权利的总量
     */
    @Override
    public BdcQlNumDTO countBdcQl(BdcQlNumDTO bdcQlNumDTO, String bdcdyh, List qsztList) {
        // nothing to do
        return bdcQlNumDTO;
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
        // nothing to do
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
        return bdcFwzlMapper.updateQlGyqkPl(bdcGyqkDTO);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量流程更新项目生成的权利的登簿信息和权属状态
     */
    @Override
    public int udpateBdcQlDbxxAndQsztPl(DbxxQO dbxxQO) {
        return bdcFwzlMapper.udpateBdcQlDbxxAndQsztPl(dbxxQO);
    }

    /**
     * @param dbxxQO 登簿信息QO
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量流程，更新上一手权利的权属状态（注销类限制权利还要更新注销登簿信息）
     */
    @Override
    public int udpateYqlZxDbxxAndQsztPl(DbxxQO dbxxQO) {
        return bdcFwzlMapper.udpateBdcQlZxDbxxAndQsztPl(dbxxQO);
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
        return bdcFwzlMapper.udpateBdcFwzlQsztPl(dbxxQO);
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
        return bdcFwzlMapper.updateBdcQlDbrPl(dbxxQO);

    }

    @Override
    public int updateZxQlDbrPl(DbxxQO dbxxQO){
        if (StringUtils.isBlank(dbxxQO.getGzlslid())) {
            throw new MissingArgumentException("缺失工作流实例ID！");
        }
        return bdcFwzlMapper.updateZxQlDbrPl(dbxxQO);

    }
}
