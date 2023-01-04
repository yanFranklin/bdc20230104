package cn.gtmap.realestate.common.core.support.gjgl;

import cn.gtmap.gtc.sso.domain.dto.UserDto;
import cn.gtmap.realestate.common.core.service.feign.init.BdcJsPzFeignService;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/7/17
 * @description 权籍管理
 */
@RestController
@RequestMapping("/rest/v1.0/qjgl")
public class QjglController {

    @Autowired
    UserManagerUtils userManagerUtils;

    @Autowired
    BdcJsPzFeignService bdcJsPzFeignService;

    @GetMapping("/list/qjgldm")
    public Object listQjgldm(){
        //获取用户关联权籍管理代码列表
        UserDto userDto = userManagerUtils.getCurrentUser();
        if(userDto != null){
            return bdcJsPzFeignService.listDistinctQjgldmByUserId(userDto.getId());
        }
        return null;

    }
}
