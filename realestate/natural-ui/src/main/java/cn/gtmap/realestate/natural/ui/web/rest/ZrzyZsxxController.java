package cn.gtmap.realestate.natural.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyZsDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyZsQO;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyZsFeignService;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyZsVO;
import cn.gtmap.realestate.natural.ui.web.main.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/11/5 10:35
 */
@RestController
@RequestMapping("/rest/v1.0")
public class ZrzyZsxxController extends BaseController {
    @Autowired
    ZrzyZsFeignService zrzyZsFeignService;


    /**
     * @param zsid 证书ID
     * @return 项目的证书信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取证书信息
     */
    @GetMapping(value = "/zsxx/{zsid}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "zsid", value = "证书ID", dataType = "string", paramType = "path", required = true)})
    public ZrzyZsVO queryZsxx(@PathVariable(name = "zsid") String zsid) {
        // 获取证书信息
        return zrzyZsFeignService.queryZrzyZs(zsid);
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return BdcZsVO 证书信息VO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据工作流实例ID查询单个证书信息
     */
    @GetMapping(value = "/zsxx/{gzlslid}/single")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", dataType = "string", paramType = "path", required = true)})
    public ZrzyZsDO queryGzlZsxx(@PathVariable(name = "gzlslid") String gzlslid) throws Exception {

        ZrzyZsQO zrzyZsQO = new ZrzyZsQO();
        zrzyZsQO.setGzlslid(gzlslid);
        List<ZrzyZsDO> zrzyZsDOS = zrzyZsFeignService.listZrzyZs(zrzyZsQO);
        if (CollectionUtils.isNotEmpty(zrzyZsDOS)) {
            return zrzyZsDOS.get(0);
        }
        return null;
    }


    /**
     * 获取项目下的证书
     *
     * @param pageable
     * @param zrzyZsQO
     * @return
     */
    @GetMapping(value = "/zsxx/list/xm/{xmid}")
    @ResponseStatus(HttpStatus.OK)
    public Object zslistByXmid(@LayuiPageable Pageable pageable, ZrzyZsQO zrzyZsQO) {
        if (zrzyZsQO == null || StringUtils.isEmpty(zrzyZsQO.getXmid())) {
            throw new MissingArgumentException("xmid");
        }
        Page<ZrzyZsVO> zrzyZsVOS = zrzyZsFeignService.zrzyZsByPageJson(pageable.getPageNumber(),
                pageable.getPageSize(), pageable.getSort(), zrzyZsQO);

        return super.addLayUiCode(zrzyZsVOS);
    }

    /**
     * @param zrzyZsDO 更新的证书信息
     * @return int 更新的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新证书信息
     */
    @PatchMapping(value = "/zsxx")
    @ResponseStatus(HttpStatus.OK)
    public int updateZrzyZs(@RequestBody ZrzyZsDO zrzyZsDO) {
        if (StringUtils.isBlank(zrzyZsDO.getZsid())) {
            throw new MissingArgumentException("缺失zsid！");
        }

        return zrzyZsFeignService.updateZrzyZs(zrzyZsDO);
    }
}
