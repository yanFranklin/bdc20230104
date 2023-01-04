package cn.gtmap.realestate.accept.core.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlYcslDjywDzbService;
import cn.gtmap.realestate.accept.util.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYcslDjywDzbDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.support.i18n.MessageProvider;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CheckParameter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/12/5
 * @description
 */
@Service
public class BdcSlYcslDjywDzbServiceImpl implements BdcSlYcslDjywDzbService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    MessageProvider messageProvider;

    @Override
    public BdcSlYcslDjywDzbDO queryYcslDjywDzb(BdcSlYcslDjywDzbDO bdcSlYcslDjywDzbDO){
        if (!CheckParameter.checkAnyParameter(bdcSlYcslDjywDzbDO)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        List<BdcSlYcslDjywDzbDO> bdcSlYcslDjywDzbDOList =entityMapper.selectByObj(bdcSlYcslDjywDzbDO);
        if (CollectionUtils.isEmpty(bdcSlYcslDjywDzbDOList)) {
            return null;
        }
        return bdcSlYcslDjywDzbDOList.get(0);

    }

    @Override
    public Boolean checkIsYcslLc(String gzldyid){
        BdcSlYcslDjywDzbDO bdcSlYcslDjywDzbQO =new BdcSlYcslDjywDzbDO();
        bdcSlYcslDjywDzbQO.setYcslgzldyid(gzldyid);
        BdcSlYcslDjywDzbDO  bdcSlYcslDjywDzbDO=queryYcslDjywDzb(bdcSlYcslDjywDzbQO);
        return bdcSlYcslDjywDzbDO != null;
    }
}
