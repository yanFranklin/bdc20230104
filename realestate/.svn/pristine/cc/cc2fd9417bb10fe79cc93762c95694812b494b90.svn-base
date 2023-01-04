package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcYhyjTjDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcYhyjTjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcYhyjTjRestService;
import cn.gtmap.realestate.inquiry.service.BdcYhyjTjService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/7/28.
 * @description 银行月结统计
 */
@RestController
@Api(tags = "银行月结统计")
public class BdcYhyjTjRestController implements BdcYhyjTjRestService {

    @Autowired
    private BdcYhyjTjService bdcYhyjTjService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "不动产银行月结统计查询")
    @ApiImplicitParam(name = "bdcYhyjTjQO", value = "月结统计分页查询对象", dataType = "String", paramType = "body")
    public List<BdcYhyjTjDTO> listBdcYhyjTj(@RequestBody BdcYhyjTjQO bdcYhyjTjQO) {
        return bdcYhyjTjService.listBdcYhyjTj(bdcYhyjTjQO);
    }
}
