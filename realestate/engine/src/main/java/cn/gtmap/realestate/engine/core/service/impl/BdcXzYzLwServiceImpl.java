package cn.gtmap.realestate.engine.core.service.impl;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzLwDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.core.vo.engine.ui.BdcGzLwVO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.engine.core.service.BdcXzYzLwService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/14,1.0
 * @description
 */
@Service
public class BdcXzYzLwServiceImpl implements BdcXzYzLwService {
    @Autowired
    private Repo repo;
    @Autowired
    EntityMapper entityMapper;


    /**
     * @param pageable
     * @param map
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询例外验证
     */
    @Override
    public Page<BdcGzLwVO> listBdcXzLwByPage(Pageable pageable, Map map) {
        return repo.selectPaging("listXzYzLwByPage", map, pageable);
    }

    /**
     * @param lwid 例外id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除例外
     */
    @Override
    public void deleteXzyzLw(String lwid) {
        if(StringUtils.isNotEmpty(lwid)){
            entityMapper.deleteByPrimaryKey(BdcGzLwDO.class,lwid);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLwDOList 例外规则集合
     * @return {int} 操作数据记录数
     * @description 新增验证例外规则
     */
    @Override
    public int addBdcGzLw(List<BdcGzLwDO> bdcGzLwDOList) {
        if(CollectionUtils.isEmpty(bdcGzLwDOList)){
            return 0;
        }

        for (BdcGzLwDO bdcGzLwDO : bdcGzLwDOList){
            if (StringUtils.isEmpty(bdcGzLwDO.getLwid())) {
                bdcGzLwDO.setLwid(UUIDGenerator.generate());
            }
            bdcGzLwDO.setCzsj(new Date());
        }
        return entityMapper.insertBatchSelective(bdcGzLwDOList);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzLwDOList 待删除例外规则集合
     * @return {int} 操作数据记录数
     * @description 批量删除已设置的验证例外规则
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteBdcGzLwList(List<BdcGzLwDO> bdcGzLwDOList) {
        if(CollectionUtils.isEmpty(bdcGzLwDOList)){
            return 0;
        }

        int count = 0;
        for (BdcGzLwDO bdcGzLwDO : bdcGzLwDOList){
            count += entityMapper.delete(bdcGzLwDO);
        }
        return count;
    }
}
