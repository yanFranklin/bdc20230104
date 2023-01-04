package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyXmDTO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyXmListQO;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyGgRestService;
import cn.gtmap.realestate.common.core.service.rest.natural.ZrzyXmRestService;
import cn.gtmap.realestate.natural.config.NaturalBeanFactory;
import cn.gtmap.realestate.natural.core.service.ZrzyCommonService;
import cn.gtmap.realestate.natural.core.service.ZrzyXmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @version 1.0  2021/10/25
 * @description 自然资源单元信息接口
 */
@RestController
@Api(tags = "自然资源公告信息接口")
public class ZrzyGgRestController implements ZrzyGgRestService {
    @Autowired
    private ZrzyXmService zrzyXmService;

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "新增公告信息", notes = "新增公告信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public void insertZrzyGgmb(@PathVariable(value = "xmid") String xmid,
                               @PathVariable(value = "mbnr") String mbnr) {
        String mbxxid = UUID.randomUUID().toString();
        zrzyXmService.addGgmb(xmid, mbxxid, mbnr);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "更新公告信息", notes = "更新公告信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public void updateZrzyGgmb(@PathVariable(value = "xmid") String xmid,
                               @PathVariable(value = "mbnr") String mbnr) {
        zrzyXmService.updateGgmbByXmid(xmid, mbnr);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "删除公告信息", notes = "删除公告信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public void deleteZrzyGgmb(@PathVariable(value = "xmid") String xmid) {
        return;
    }
}
