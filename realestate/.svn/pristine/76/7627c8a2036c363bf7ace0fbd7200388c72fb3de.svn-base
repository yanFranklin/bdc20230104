package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlPjqDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlPjqDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlPjqQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlPjqFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlZdFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/11/28
 * @description 评价统计查询
 */
@RestController
@RequestMapping("/rest/v1.0/pjqcx")
public class BdcPjTjCxController extends BaseController {

    @Autowired
    BdcSlPjqFeignService bdcSlPjqFeignService;
    @Autowired
    BdcSlZdFeignService bdcSlZdFeignService;

    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: java.lang.Object
     * @description 获取评价器不满意原因字段项
     */
    @GetMapping("/zd/bmyyy")
    @ApiOperation(value = "获取评价器不满意原因字典项")
    @ResponseStatus(HttpStatus.OK)
    public Object getPjqBmyyy(){
        List<Map> list = this.bdcSlZdFeignService.queryBdcSlzd("bmyyy");
        return list;
    }

    @GetMapping("/page")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页查询评价统计信息")
    public Object queryPjTjByPage(@LayuiPageable Pageable pageable, BdcSlPjqDTO bdcSlPjqDTO) {
        Page<BdcSlPjqDO> bdcSlPjqDOPage = bdcSlPjqFeignService.listBdcSlPjTjByPage(pageable.getPageSize(),pageable.getPageNumber(),bdcSlPjqDTO);
        return super.addLayUiCode(bdcSlPjqDOPage);
    }

    @GetMapping("/group/page")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分页分组查询评价统计信息")
    public Object groupQueryPjTjByPage(@LayuiPageable Pageable pageable, BdcSlPjqQO bdcSlPjqQO) {
        if(StringUtils.isBlank(bdcSlPjqQO.getTjwd())){
            throw new NullPointerException("未获取到统计维度信息。");
        }
        Page<BdcSlPjqDTO> bdcSlPjqDTOPage = bdcSlPjqFeignService.listGroupBdcSlPjTjByPage(pageable.getPageSize(),pageable.getPageNumber(), bdcSlPjqQO);
        return super.addLayUiCode(bdcSlPjqDTOPage);
    }

    @GetMapping("/group/list")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("分组查询评价统计信息")
    public Object groupQueryPjTj(BdcSlPjqQO bdcSlPjqQO) {
        if(StringUtils.isBlank(bdcSlPjqQO.getTjwd())){
            throw new NullPointerException("未获取到统计维度信息。");
        }
        List<BdcSlPjqDTO> bdcSlPjqDTOList = bdcSlPjqFeignService.listGroupBdcSlPjTj(bdcSlPjqQO);
        return bdcSlPjqDTOList;
    }

}
