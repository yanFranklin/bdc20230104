package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXtMryjDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcXtMryjPzVO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.config.service.BdcXtMryjPzService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/29
 * @description 不动产默认意见业务类
 */
@Service
public class BdcXtMryjPzServiceImpl implements BdcXtMryjPzService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    private Repo repo;
    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * @param pageable
     * @param bdcXtMryjDO
     * @return 默认意见分页
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取默认意见分页数据
     */
    @Override
    public Page<BdcXtMryjPzVO> listBdcXtMryjByPage(Pageable pageable, BdcXtMryjDO bdcXtMryjDO) {
        return repo.selectPaging("listBdcXtMryjByPage", bdcXtMryjDO, pageable);
    }

    /**
     * @param bdcXtMryjDO
     * @return 保存的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存默认意见配置
     */
    @Override
    public int saveBdcXtMryj(BdcXtMryjDO bdcXtMryjDO) {
        if (bdcXtMryjDO == null) {
            throw new AppException("保存的默认意见不能为空！");
        }
        bdcXtMryjDO.setMryjid(UUIDGenerator.generate());
        bdcXtMryjDO.setCjrid(userManagerUtils.getCurrentUser().getId());
        return entityMapper.insertSelective(bdcXtMryjDO);
    }


    /**
     * @param bdcXtMryjDO
     * @return 修改的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改默认意见配置
     */
    @Override
    public int updateBdcXtMryj(BdcXtMryjDO bdcXtMryjDO) {
        if (bdcXtMryjDO == null) {
            throw new AppException("修改的默认意见不能为空！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcXtMryjDO);
    }

    /**
     * @param bdcXtMryjDOList
     * @return 删除的数据量
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除不动产默认意见配置
     */
    @Override
    public int deleteBdcXtMryj(List<BdcXtMryjDO> bdcXtMryjDOList) {
        if (CollectionUtils.isEmpty(bdcXtMryjDOList)) {
            throw new AppException("需要删除的默认意见不能为空！");
        }
        return bdcXtMryjDOList.stream().filter(bdcXtMryjDO -> StringUtils.isNotBlank(bdcXtMryjDO.getMryjid())).mapToInt(bdcXtMryjDO -> entityMapper.deleteByPrimaryKey(BdcXtMryjDO.class, bdcXtMryjDO.getMryjid())).sum();
    }

    /**
     * @param bdcXtMryjDO
     * @return List<BdcXtMryjDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 默认意见配置数据
     */
    @Override
    public List<BdcXtMryjDO> listMryjpz(BdcXtMryjDO bdcXtMryjDO) {
        if (bdcXtMryjDO == null || StringUtils.isBlank(bdcXtMryjDO.getGzldyid())) {
            throw new AppException("获取默认意见参数不能为空！");
        }
        return entityMapper.selectByObj(bdcXtMryjDO);
    }
}
