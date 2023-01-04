package cn.gtmap.realestate.register.service.impl;


import cn.gtmap.realestate.common.core.domain.register.BdcQzxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.register.core.mapper.BdcQzxxMapper;
import cn.gtmap.realestate.register.service.BdcQzxxService;
import cn.gtmap.realestate.register.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/12/24 09:12
 * @description 评价器签字Impl
 */
@Service
public class BdcQzxxServiceImpl implements BdcQzxxService {

    protected final Logger LOGGER = LoggerFactory.getLogger(BdcQzxxServiceImpl.class);

    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private BdcQzxxMapper bdcQzxxMapper;
    /**
     * @param bdcQzxxDO bdcQzxxDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 保存签字信息
     */
    @Override
    public BdcQzxxDO insertBdcQzxx(BdcQzxxDO bdcQzxxDO) {
        if (bdcQzxxDO != null) {
            if (StringUtils.isBlank(bdcQzxxDO.getId())) {
                bdcQzxxDO.setId(UUIDGenerator.generate16());
            }
            entityMapper.insertSelective(bdcQzxxDO);
        }
        return bdcQzxxDO ;
    }

    /**
     * @param bdcQzxxDO 评价器签字Do
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 更新不动产评价器签字信息
     */
    @Override
    public Integer updateBdcQzxx(BdcQzxxDO bdcQzxxDO) {
        int result;
        if (bdcQzxxDO != null && StringUtils.isNotBlank(bdcQzxxDO.getId())) {
            result = entityMapper.updateByPrimaryKeySelective(bdcQzxxDO);
        } else {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return result;
    }

    /**
     * 查询签字信息
     *
     * @param bdcQzxxDO bdcQzxxDO
     * @return BdcQzxxDO
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    public List<BdcQzxxDO> queryBdcQzxx(BdcQzxxDO bdcQzxxDO) {
        if (null != bdcQzxxDO) {
            return bdcQzxxMapper.listBdcQzxx(bdcQzxxDO);
        }
        return new ArrayList<>();
    }
}
