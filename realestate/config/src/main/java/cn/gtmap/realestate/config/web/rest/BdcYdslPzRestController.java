package cn.gtmap.realestate.config.web.rest;

import cn.gtmap.gtc.sso.domain.dto.RoleDto;
import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.config.BdcYdslPzRestService;
import cn.gtmap.realestate.common.util.StringUtil;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.configuration.BdcConfigUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href ="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.3, 2022/8/5
 * @description 不动产异地角色配置服务接口
 */

@RestController
@Api(tags = "不动产异地角色配置服务接口")
public class BdcYdslPzRestController implements BdcYdslPzRestService {

    private static Logger logger = LoggerFactory.getLogger(BdcYdslPzRestController.class);

    /**
     * 异地受理的角色
     */
    @Value("#{'${ydsl.jsdm:}'.split(',')}")
    private List<String> ydsljsdm;

    /**
     * 异地受理角色页面查询条件配置
     */
    @Value("#{${ydsl.ymcxtj: null}}")
    private Map<String, String> ymcxtj;

    @Autowired
    private UserManagerUtils userManagerUtils;

    /**
     * @param cxym
     * @return
     * @author <a href ="mailto:wutao@gtmap.cn">wutao</a>
     * @description 查询异地角色页面展示的查询条件
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取异地角色页面展示的查询条件")
    @ApiImplicitParam(name = "cxym", value = "查询页面", required = true, paramType = "String")
    public String listcxtj(@RequestParam(name = "cxym") String cxym) {

        if(StringUtil.isBlank(cxym)){
            throw new MissingArgumentException("缺失请求参数cxym");
        }
        UserDto currentUser = userManagerUtils.getCurrentUser();
        if(currentUser ==null){
            //用户信息为空
            logger.error("查询异地角色页面展示的查询条件,当前用户不存在");
            throw new AppException("当前用户不存在,无法查询数据");
        }
        //判断当前用户角色是异地受理角色
        List<String> roleRecordList =null;
        if(CollectionUtils.isNotEmpty(ydsljsdm) && CollectionUtils.isNotEmpty(currentUser.getRoleRecordList())){
            roleRecordList = currentUser.getRoleRecordList().stream()
                    .map(RoleDto::getName).collect(Collectors.toList());
            for (String role : ydsljsdm) {
                if (StringUtils.isNotBlank(role) &&roleRecordList.contains(role)) {
                    if(MapUtils.isNotEmpty(ymcxtj)){
                        return ymcxtj.get(cxym);
                    }
                }
            }
        }
        return null;
    }

}
