package cn.gtmap.realestate.natural.resource.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.naturalresource.JbzkDO;
import cn.gtmap.realestate.common.core.dto.naturalresource.JbzkDTO;
import cn.gtmap.realestate.common.core.qo.naturalresource.ZrzyJbzkQO;
import cn.gtmap.realestate.common.core.service.rest.naturalresource.NaturalJbzkRestService;
import cn.gtmap.realestate.natural.resource.service.impl.NaturalResourceJbzkServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-23
 * @description 为受理子系统提供
 * 根据BDCLX分页查询BDCDY服务
 */
@RestController
@Api(tags = "为自然资源子系统提供分页查询自然资源基本状况服务")
public class NaturalResourceJbzkRestController implements NaturalJbzkRestService {
    @Autowired
    NaturalResourceJbzkServiceImpl naturalResourceJbzkService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "自然资源基本状况分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ysdm", value = "要素代码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "zl", value = "坐落", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "数量", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "string", paramType = "query")})
    public Page<JbzkDO> listJbzkByPage(@LayuiPageable Pageable pageable,
                                       @RequestParam(name = "ysdm") String ysdm,
                                       @RequestParam(name = "zl") String zl,
                                       @RequestParam(name = "mc") String mc
                                       ) {
        ZrzyJbzkQO zrzyJbzkQO = new ZrzyJbzkQO();
        zrzyJbzkQO.setYsdm(ysdm);
        zrzyJbzkQO.setZl(zl);
        zrzyJbzkQO.setMc(mc);
        return naturalResourceJbzkService.listJbzkByPage(pageable, zrzyJbzkQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "自然资源基本状况详情查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zrzydjdyh", value = "自然资源登记单元号", dataType = "String", paramType = "query")
    })
    public JbzkDTO queryJbzkByZrzydjdyh(@PathVariable(name = "zrzydjdyh") String zrzydjdyh) {
        return naturalResourceJbzkService.queryJbzkByZrzydjdyh(zrzydjdyh);
    }
}
