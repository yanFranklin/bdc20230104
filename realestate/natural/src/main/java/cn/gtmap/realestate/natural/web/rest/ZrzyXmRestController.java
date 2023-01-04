package cn.gtmap.realestate.natural.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXmDO;
import cn.gtmap.realestate.common.core.dto.natural.ZrzyXmDTO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyXmListQO;
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

/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @version 1.0  2021/10/25
 * @description 自然资源单元信息接口
 */
@RestController
@Api(tags = "自然资源单元信息接口")
public class ZrzyXmRestController implements ZrzyXmRestService {

    /**
     * 日志处理
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZrzyXmRestController.class.getName());

    /**
     * 实体操作
     */
    @Autowired
    private ZrzyXmService zrzyXmService;

    @Autowired
    NaturalBeanFactory naturalBeanFactory;


    @Override
    public Page<ZrzyXmDTO> zrzyXmList(@LayuiPageable Pageable pageable,
                                      @RequestParam(name = "zrzydjdyh") String zrzydjdyh,
                                      @RequestParam(name = "zl") String zl,
                                      @RequestParam(name = "djyy") String djyy) {
        ZrzyXmListQO zrzyXmListQO = new ZrzyXmListQO();
        zrzyXmListQO.setDjyy(djyy);
        zrzyXmListQO.setZl(zl);
        zrzyXmListQO.setZrzydjdyh(zrzydjdyh);
        return zrzyXmService.zrzyXmList(pageable, zrzyXmListQO);
    }

    /**
     * @param gzlslid
     * @return
     */
    @Override
    public List<ZrzyXmDO> listZrzyXmByGzlslid(@PathVariable(value = "gzlslid") String gzlslid) {
        return zrzyXmService.queryAllZrzyXmByGzlslid(gzlslid);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "根据工作流实例ID删除项目信息", notes = "根据工作流实例ID删除项目信息")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public void deleteZrzyXm(@PathVariable(value = "gzlslid") String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            //项目信息
            ZrzyXmDO zrzyXm = zrzyXmService.queryZrzyXmByGzlslid(gzlslid);
            if (zrzyXm != null) {
                String xmid = zrzyXm.getXmid();
                //获取其他业务填报信息
                Set<ZrzyCommonService> zrzyYwxxServiceSet = naturalBeanFactory.getZrzyYwxxServiceSet();
                if (CollectionUtils.isNotEmpty(zrzyYwxxServiceSet)) {
                    for (ZrzyCommonService zrzyCommonService : zrzyYwxxServiceSet) {
                        zrzyCommonService.deleteYwxx(xmid);
                    }
                }
            }
        }
    }
}
