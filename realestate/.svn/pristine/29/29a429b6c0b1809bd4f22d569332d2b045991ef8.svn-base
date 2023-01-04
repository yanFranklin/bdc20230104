package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcWtsDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.dto.BdcWtsDTO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcWtsRestService;
import cn.gtmap.realestate.inquiry.service.BdcWtsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/10/31
 * @description
 */
@RestController
@Api(value = "出具委托书")
public class BdcWtsRestController implements BdcWtsRestService {

    @Autowired
    private BdcWtsService bdcWtsService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("保存委托书")
    @ApiImplicitParam(name = "bdcWtsDO",value = "委托书实体",required = true,paramType = "body")
    public int saveWts(@RequestBody BdcWtsDTO bdcWtsDTO) {
        return bdcWtsService.saveWts(bdcWtsDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("生成委托书数据")
    public Object getWtsDatas() {
        return bdcWtsService.getWtsDatas();
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("更新委托书状态")
    @ApiImplicitParam(name = "wtsbh",value = "委托书编号",required = true)
    public Object updateWts(@RequestParam(value = "wtsbh") String wtsbh) {
        if (StringUtils.isBlank(wtsbh)) {
            throw new NullPointerException("委托书编号不可为空！");
        }
        return bdcWtsService.updateWts(wtsbh);
    }

}
