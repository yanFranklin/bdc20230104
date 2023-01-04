package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.dto.natural.ZrzyCshDTO;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyInitRestService;
import cn.gtmap.realestate.natural.service.ZrzyInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/11/1
 * @description
 */
@RestController
@Api(tags = "自然资源初始化服务")
public class ZrzyInitRestController implements ZrzyInitRestService {

    @Autowired
    private ZrzyInitService zrzyInitService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "自然资源业务信息初始化", notes = "自然资源业务信息初始化")
    @ApiImplicitParam(name = "zrzyCshDTO", value = "初始化选择对象", required = true, dataType = "ZrzyCshDTO", paramType = "body")
    public String ywxxCsh(@RequestBody ZrzyCshDTO zrzyCshDTO){
        return zrzyInitService.ywxxCsh(zrzyCshDTO);
    }
}
