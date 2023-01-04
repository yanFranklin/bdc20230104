package cn.gtmap.realestate.natural.service.impl;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcXtLscsDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtLscsDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.natural.service.ZrzyXtLscsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
 * @version 1.0, 2021/01/14
 * @description 临时参数表操作服务
 */
@Service
public class ZrzyXtLscsServiceImpl implements ZrzyXtLscsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyXtLscsServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;

    /**
     * 应用重启时候清空自定义查询临时参数数据
     */
    @PostConstruct
    public void init() {
        try {
            Example example = new Example(BdcXtLscsDO.class);
            example.createCriteria().andEqualTo("sfsc", 1);
            int count = entityMapper.deleteByExample(example);
            LOGGER.info("删除临时参数表BDC_XT_LSCS记录{}条", count);
        } catch (Exception e) {
            LOGGER.info("删除临时参数表BDC_XT_LSCS记录失败");
        }
    }


    /**
     * 批量添加临时参数数据
     *
     * @param lscsDOList 参数数据
     * @return {int} 新增记录数量
     * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addValues(List<ZrzyXtLscsDO> lscsDOList) {
        if (CollectionUtils.isEmpty(lscsDOList)) {
            throw new MissingArgumentException("添加临时参数操作传参为空");
        }

        return entityMapper.insertBatchSelective(lscsDOList);
    }

    /**
     * 批量删除临时参数数据
     *
     * @param lscsDOList 参数数据
     * @return {int} 删除记录数量
     * @author <a href="mailto:zhuyong@gtmap.cn>zhuyong</a>"
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteRecords(List<ZrzyXtLscsDO> lscsDOList) {
        if (CollectionUtils.isEmpty(lscsDOList)) {
            throw new MissingArgumentException("删除临时参数操作传参为空");
        }

        for (ZrzyXtLscsDO xtLscsDO : lscsDOList) {
            entityMapper.delete(xtLscsDO);
        }
        return lscsDOList.size();
    }
}
