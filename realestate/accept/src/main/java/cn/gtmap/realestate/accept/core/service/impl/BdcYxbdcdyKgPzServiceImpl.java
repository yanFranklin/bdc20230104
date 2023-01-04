package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcYxbdcdyKgPzService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcYxbdcdyKgPzDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/2/28
 * @description 已选不动产单元开关配置
 */
@Service
public class BdcYxbdcdyKgPzServiceImpl implements BdcYxbdcdyKgPzService {
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    /**
     * @param gzldyid 工作流定义id
     * @return 已选不动产单元开关配置
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流定义id获取已选不动产单元开关配置
     */
    @Override
    public BdcYxbdcdyKgPzDO queryBdcYxbdcdyKgPzDOByGzldyid(String gzldyid) {
        BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO =new BdcYxbdcdyKgPzDO();
        if(StringUtils.isNotBlank(gzldyid)) {
            Example example = new Example(BdcYxbdcdyKgPzDO.class);
            example.createCriteria().andEqualTo("gzldyid", gzldyid);
            List<BdcYxbdcdyKgPzDO> bdcYxbdcdyKgPzDOList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcYxbdcdyKgPzDOList)){
                bdcYxbdcdyKgPzDO =bdcYxbdcdyKgPzDOList.get(0);
            }
        }
        return bdcYxbdcdyKgPzDO;
    }

    /**
     * @param bdcYxbdcdyKgPzDO 已选不动产单元开关配置
     * @return 修改数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存已选不动产单元开关配置
     */
    @Override
    public int saveBdcYxbdcdyKgPzDO(BdcYxbdcdyKgPzDO bdcYxbdcdyKgPzDO){
        if(bdcYxbdcdyKgPzDO == null){
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        if (StringUtils.isBlank(bdcYxbdcdyKgPzDO.getPzid())) {
            bdcYxbdcdyKgPzDO.setPzid(UUIDGenerator.generate16());
        }
        return entityMapper.saveOrUpdate(bdcYxbdcdyKgPzDO,bdcYxbdcdyKgPzDO.getPzid());
    }

    @Override
    public int deleteBdcYxbdcdyKgPzDOByGzldyid(String gzldyid){
        int result = 0;
        if (StringUtils.isNotBlank(gzldyid)) {
            Example example = new Example(BdcYxbdcdyKgPzDO.class);
            example.createCriteria().andEqualTo("gzldyid", gzldyid);
            result = entityMapper.deleteByExample(example);
        }
        return result;
    }

    /**
     * @param gzldyid 工作流定义id
     * @return 已选不动产单元开关配置
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据工作流定义id获取已选不动产单元开关配置
     *
     */
    /**为了改动尽量少的代码，方法和yxbdcdyhkgpz的属性尽量独立，做到最小耦合**/
    @Override
    public List<BdcYxbdcdyKgPzDO> queryBdcYxbdcdyKgPzDOListByGzldyid(String gzldyid) {
        if(StringUtils.isNotBlank(gzldyid)) {
            Example example = new Example(BdcYxbdcdyKgPzDO.class);
            example.createCriteria().andEqualTo("gzldyid", gzldyid);
            example.setOrderByClause("pzid asc");
            List<BdcYxbdcdyKgPzDO> bdcYxbdcdyKgPzDOList = entityMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(bdcYxbdcdyKgPzDOList)){
                return bdcYxbdcdyKgPzDOList;
            }
        }
        return new ArrayList<>();
    }
}
