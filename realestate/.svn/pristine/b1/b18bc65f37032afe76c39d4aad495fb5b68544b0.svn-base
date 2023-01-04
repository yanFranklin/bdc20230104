package cn.gtmap.realestate.register.service.workflow.impl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlsjLogDO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlSjJkDTO;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.register.service.workflow.BdcGzlsjLogService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @program: realestate
 * @description: 工作流事件执行日志记录实现类
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-04-18 17:18
 **/
@Service
public class BdcGzlsjLogServiceImpl implements BdcGzlsjLogService {
    @Autowired
    EntityMapper entityMapper;
    @Autowired
    UserManagerUtils userManagerUtils;


    /**
     * @param bdcGzlsjLogDO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新增执行日志
     * @date : 2022/4/18 16:40
     */
    @Override
    @Async
    public void saveBdcGzlsjLog(BdcGzlSjJkDTO bdcGzlSjJkDTO, Map paramMap, Object result) {
        UserDto userDto = userManagerUtils.getUserByName(MapUtils.getString(paramMap, "currentUserName", ""));
        //插入数据库日志记录异步执行
        BdcGzlsjLogDO bdcGzlsjLogDO = new BdcGzlsjLogDO();
        bdcGzlsjLogDO.setRzid(UUIDGenerator.generate16());
        bdcGzlsjLogDO.setQqsj(new Date());
        bdcGzlsjLogDO.setQqdz(bdcGzlSjJkDTO.getJkmc());
        bdcGzlsjLogDO.setQqfs(bdcGzlSjJkDTO.getQqfs());
        bdcGzlsjLogDO.setYhxx(Objects.nonNull(userDto) ? userDto.getAlias() : "");
        bdcGzlsjLogDO.setYcxx(JSON.toJSONString(Objects.isNull(result) ? "" : result, SerializerFeature.WriteNullStringAsEmpty));
        entityMapper.insertSelective(bdcGzlsjLogDO);
    }
}
