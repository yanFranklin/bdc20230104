package cn.gtmap.realestate.config.service.impl;

import cn.gtmap.realestate.common.core.domain.BdcXtQlqtzkFjPzDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.config.BdcXtQlqtzkFjPzQO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.repository.Repo;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.config.core.mapper.BdcXtQlqtzkFjPzMapper;
import cn.gtmap.realestate.config.service.BdcXtQlqtzkFjPzService;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/2/18
 * @description 权利其他状况附记配置业务类
 */
@Service
public class BdcXtQlqtzkFjPzServiceImpl implements BdcXtQlqtzkFjPzService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    private Repo repo;
    @Autowired
    BdcXtQlqtzkFjPzMapper bdcXtQlqtzkFjPzMapper;
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * @param pageable
     * @param bdcXtQlqtzkFjPzQO
     * @return 权利其他状况、附记配置分页
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取权利其他状况、附记配置
     */
    @Override
    public Page<BdcXtQlqtzkFjPzDO> listBdcXtQlqtzkFjPzByPage(Pageable pageable, BdcXtQlqtzkFjPzQO bdcXtQlqtzkFjPzQO) {
        return repo.selectPaging("listBdcXtQlqtzkFjPzByPage", bdcXtQlqtzkFjPzQO, pageable);
    }

    /**
     * @param bdcXtQlqtzkFjPzDO
     * @return 权利其他状况、附记配置保存
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 保存默认意见配置
     */
    @Override
    public int saveBdcXtQlqtzkFjPz(BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO) {
        if (bdcXtQlqtzkFjPzDO == null) {
            throw new AppException("保存的权利其他状况、附记不能为空！");
        }
        bdcXtQlqtzkFjPzDO.setPzid(UUIDGenerator.generate());
        return entityMapper.insertSelective(bdcXtQlqtzkFjPzDO);
    }

    /**
     * @param bdcXtQlqtzkFjPzDO
     * @return 权利其他状况、附记配置修改
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 修改默认意见配置
     */
    @Override
    public int updateBdcXtQlqtzkFjPz(BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO) {
        if (bdcXtQlqtzkFjPzDO == null) {
            throw new AppException("修改的权利其他状况、附记不能为空！");
        }
        return entityMapper.updateByPrimaryKeySelective(bdcXtQlqtzkFjPzDO);
    }

    /**
     * @param bdcXtQlqtzkFjPzDOS
     * @return 权利其他状况、附记配置删除
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 删除权利其他状况、附记配置
     */
    @Override
    public int deleteBdcXtQlqtzkFjPz(List<BdcXtQlqtzkFjPzDO> bdcXtQlqtzkFjPzDOS) {
        if (CollectionUtils.isEmpty(bdcXtQlqtzkFjPzDOS)) {
            throw new AppException("需要删除的权利其他状况、附记配置不能为空！");
        }
        return bdcXtQlqtzkFjPzDOS.stream().filter(bdcXtQlqtzkFjPzDO -> StringUtils.isNotBlank(bdcXtQlqtzkFjPzDO.getPzid())).mapToInt(bdcXtQlqtzkFjPzDO -> entityMapper.deleteByPrimaryKey(BdcXtQlqtzkFjPzDO.class, bdcXtQlqtzkFjPzDO.getPzid())).sum();
    }

    /**
     * @param sqlList
     * @param params
     * @return
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 验证sql是否可运行
     */
    @Override
    public boolean checkBdcXtQlqtzkFjPz(List<String> sqlList, Map params) {
        LOGGER.info("验证sql:{}",sqlList);
        if(CollectionUtils.isEmpty(sqlList)|| MapUtils.isEmpty(params)){
            throw new AppException("需要验证的配置sql不能为空！");
        }
        sqlList.forEach(sql->{
            params.put("sql",sql);
            bdcXtQlqtzkFjPzMapper.runConfigSql(params);
        });
        return true;
    }

    /**
     * @param bdcXtQlqtzkFjPzDO
     * @return List<BdcXtQlqtzkFjPzDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取 系统权利其他状况附记配置
     */
    @Override
    public List<BdcXtQlqtzkFjPzDO> listQlqtzkFjpz(BdcXtQlqtzkFjPzDO bdcXtQlqtzkFjPzDO) {
        if (bdcXtQlqtzkFjPzDO == null || StringUtils.isBlank(bdcXtQlqtzkFjPzDO.getDjxl())) {
            throw new AppException("权利其他状况、附记参数不能为空！");
        }
        return entityMapper.selectByObj(bdcXtQlqtzkFjPzDO);
    }
}
