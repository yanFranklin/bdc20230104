package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyZdRestService;
import cn.gtmap.realestate.natural.service.ZrzyZdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/23
 * @description
 */
@RestController
@Api(tags = "自然资源字典项服务")
public class ZrzyZdRestController implements ZrzyZdRestService {

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description  字典服务
      */
    @Autowired
    private ZrzyZdService zrzyZdService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取所有字典项")
    public Map<String, List<Map>> listZrzyzd(){
        return zrzyZdService.listZrzyzd();

    }
}
