package cn.gtmap.realestate.check.service.impl;

import cn.gtmap.realestate.check.service.XxSolveServer;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author lsg
 * @version 1.0, 2020-1-1
 * @description 解决信息更新
 */
@Service
public class XxSolveServerImpl  implements XxSolveServer {

    @Autowired
    private EntityMapper entityMapper;
    @Override
    public CheckGzjcLogDO getGzxxSolve(String logid){
        CheckGzjcLogDO gzjcLog = null;
        if (StringUtils.isNotBlank(logid)) {
            gzjcLog = entityMapper.selectByPrimaryKey(CheckGzjcLogDO.class,logid);
        }
        return gzjcLog;
    }
}
