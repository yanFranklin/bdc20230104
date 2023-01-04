package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.domain.natural.ZrzyGgmbDo;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZrzk;
import cn.gtmap.realestate.common.core.dto.natural.ZrzySlymYwxxDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyXmDTO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyZrzkYwxxDTO;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyYwxxRestService;
import cn.gtmap.realestate.natural.core.service.zrzkxx.ZrzyZrzkService;
import cn.gtmap.realestate.natural.service.ZrzyYwxxService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源业务填报信息服务
 */
@RestController
@Api(tags = "自然资源业务填报信息服务")
public class ZrzyYwxxRestController implements ZrzyYwxxRestService {

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 业务信息服务
     */
    @Autowired
    private ZrzyYwxxService zrzyYwxxService;

    @Autowired
    private ZrzyZrzkService zrzyZrzkService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取自然资源业务填报信息", notes = "根据工作流实例ID获取自然资源业务填报信息服务")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public ZrzySlymYwxxDTO queryZrzySlymYwxxDTO(@PathVariable(value = "gzlslid") String gzlslid) {
        return zrzyYwxxService.queryZrzySlymYwxxDTO(gzlslid);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据主键获取自然状况信息", notes = "根据主键获取自然状况信息服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "zkxxid", value = "状况信息ID", required = true, paramType = "path"),
            @ApiImplicitParam(name = "zrzklx", value = "自然状况类型", required = false, paramType = "path")})
    public ZrzyZrzk queryZrzyZrzk(@PathVariable(value = "zkxxid") String zkxxid, @PathVariable(value = "zrzklx") String zrzklx) {
        return zrzyZrzkService.queryZrzk(zkxxid, zrzklx);

    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "根据工作流实例ID获取自然资源自然状况详细信息", notes = "根据工作流实例ID获取自然资源自然状况详细信息")
    public ZrzyZrzkYwxxDTO queryZrzyZrzkYwxxDTO(@PathVariable(name = "gzlslid") String gzlslid) {
        return zrzyYwxxService.queryZrzyZrzkYwxxDTO(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获取公告模板信息", notes = "获取公告模板信息")
    public Object queryZrzyGgmbxxDTO(@PathVariable(name = "gzlslid") String gzlslid) {
        List<ZrzyGgmbDo> zrzyGgmbDos = zrzyYwxxService.queryZrzyGgmbxx(gzlslid);
        //解析模板信息
        if (CollectionUtils.isNotEmpty(zrzyGgmbDos)) {
            ZrzyGgmbDo zrzyGgmbDo = zrzyGgmbDos.get(0);
            if (StringUtils.isNotBlank(zrzyGgmbDo.getMbnr())) {
                return JSON.parseObject(zrzyGgmbDo.getMbnr());
            }
        }
        return zrzyGgmbDos;
    }


}
