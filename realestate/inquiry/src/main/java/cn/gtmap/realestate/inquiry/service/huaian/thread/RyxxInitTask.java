package cn.gtmap.realestate.inquiry.service.huaian.thread;

import cn.gtmap.gtc.clients.UserManagerClient;
import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcRyGzltjXmxxVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * 人员信息初始化任务
 *
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/6/28
 */
public class RyxxInitTask implements Callable<BdcRyGzltjXmxxVO> {

    private final UserManagerClient userManagerClient;

    private final UserDto userDto;

    public RyxxInitTask(UserManagerClient userManagerClient, UserDto userDto) {
        this.userManagerClient = userManagerClient;
        this.userDto = userDto;
    }

    @Override
    public BdcRyGzltjXmxxVO call(){
        if(null != userDto && StringUtils.isNotBlank(userDto.getId())){
            List<RoleDto> roleDtos =  userManagerClient.findRoles(userDto.getId());
            String roleName = "";
            if(CollectionUtils.isNotEmpty(roleDtos)){
                roleName = roleDtos.stream().map(RoleDto::getAlias).collect(Collectors.joining(","));
            }
            return new BdcRyGzltjXmxxVO(userDto.getAlias(), userDto.getId(), roleName);
        }
        return null;
    }
}
