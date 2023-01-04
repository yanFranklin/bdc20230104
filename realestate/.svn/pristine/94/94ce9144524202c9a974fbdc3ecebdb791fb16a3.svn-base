package cn.gtmap.realestate.engine.core.service.impl;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBmdDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcBmdLoginDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.UserInformationAccessException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.engine.core.service.BdcGzBmdService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 2019/03/04,1.0
 * @description 白名单人员添加，修改，删除，查询服务
 */
@Service
public class BdcGzBmdServiceImpl implements BdcGzBmdService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    private Repo repo;

    /**
     * @param czry   白名单人员
     * @param czrymm 白名单人员密码
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据项目ID删除不动产受理项目历史关系
     */
    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @Override
    public BdcBmdLoginDTO checkIsLogin(String czry, String czrymm) {
        BdcBmdLoginDTO bdcBmdLoginDTO = new BdcBmdLoginDTO();

        Example bmdExample = new Example(BdcGzBmdDO.class);
        bmdExample.createCriteria().andEqualTo("czry", czry);
        List<BdcGzBmdDO> bdcGzBmdDOList = entityMapper.selectByExample(bmdExample);
        if (CollectionUtils.isEmpty(bdcGzBmdDOList)) {
            throw new UserInformationAccessException("用户信息获取失败");
        }
        Example bdcLwryExample = new Example(BdcGzBmdDO.class);
        bdcLwryExample.createCriteria().andEqualTo("czry", czry).andEqualTo("czrymm", czrymm);
        List<BdcGzBmdDO> bdcLwRyList = entityMapper.selectByExample(bdcLwryExample);
        if (CollectionUtils.isNotEmpty(bdcLwRyList)) {
            bdcBmdLoginDTO.setIslogin("true");
            bdcBmdLoginDTO.setCzryid(bdcLwRyList.get(0).getCzryid());
        } else {
            bdcBmdLoginDTO.setIslogin("false");
        }
        return bdcBmdLoginDTO;
    }

    /**
     * @param pageable
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 分页查询白名单人员列表
     */
    @Override
    public Page<BdcGzBmdDO> listBdcBmdByPage(Pageable pageable, Map map) {
        return repo.selectPaging("listBdcBmdByPage", map, pageable);
    }

    /**
     * @param bmdDOList
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 删除白名单
     */
    @Override
    public int deleteBdcBmd(List<BdcGzBmdDO> bmdDOList) {
        if (CollectionUtils.isEmpty(bmdDOList)) {
            throw new AppException("需要删除的人员不能为空！");
        }
        return bmdDOList.stream().filter(bdcGzBmdDO -> StringUtils.isNotBlank(bdcGzBmdDO.getCzryid())).mapToInt(bdcGzBmdDO -> entityMapper.deleteByPrimaryKey(BdcGzBmdDO.class, bdcGzBmdDO.getCzryid())).sum();
    }

    /**
     * @param bdcGzBmdDO
     * @return 保存的数据量
     * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 保存人员信息
     */
    @Override
    public int insertBdcBmd(BdcGzBmdDO bdcGzBmdDO) {
        if (bdcGzBmdDO == null) {
            throw new AppException("保存的人员不能为空！");
        }
        bdcGzBmdDO.setCzryid(UUIDGenerator.generate());
        return entityMapper.insertSelective(bdcGzBmdDO);
    }

    /**
     * @param bdcGzBmdDO
     * @return 修改的数据量
     * @author <a href ="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 修改人员信息
     */
    @Override
    public int updateBdcBmd(BdcGzBmdDO bdcGzBmdDO) {
        if (bdcGzBmdDO == null) {
            throw new AppException("修改的人员信息不能为空！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcGzBmdDO);
    }

}
