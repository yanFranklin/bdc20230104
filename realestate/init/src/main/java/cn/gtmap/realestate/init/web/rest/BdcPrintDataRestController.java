package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.dto.register.BdcDysjDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.rest.init.BdcPrintDataRestService;
import cn.gtmap.realestate.init.core.service.BdcPrintDataService;
import cn.gtmap.realestate.init.util.Constants;
import cn.gtmap.realestate.init.web.BaseController;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/6.
 * @description
 */
@RestController
@Api(tags = "不动产打印数据服务接口")
public class BdcPrintDataRestController extends BaseController implements BdcPrintDataRestService {
    @Autowired
    private BdcPrintDataService bdcPrintDataService;
    /**
     * 通过传入项目id集合以及工作流实例id去查询对应打印对象
     *
     * @param xmids
     * @param gzlslid
     * @param zsid
     * @return
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @ApiOperation(value = "通过传入项目id集合以及工作流实例id去查询对应打印对象")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求获取成功"), @ApiResponse(code = 500, message = "请求参数错误")})
    @ApiImplicitParams({@ApiImplicitParam(name = "xmids", value = "项目id集合", required = false, dataType = "List", paramType = "query"),
            @ApiImplicitParam(name = "gzlslid", value = "工作流实例id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "zsid", value = "证书id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hfwqd", value = "房屋清单（户）", required = false, dataType = "String", paramType = "query")})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<BdcDysjDTO> queryFdcqDzFwqdDysj(@RequestBody(required = false) List<String> xmids, @PathVariable("gzlslid") String gzlslid, @RequestParam(value = "zsid", required = false) String zsid,
                                                @RequestParam(value = "hfwqd", required = false) boolean hfwqd) throws Exception {
        //参数全是空的话抛出异常
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException(messageProvider.getMessage(Constants.MESSAGE_NOPARAMETER));
        }
        return bdcPrintDataService.queryFdcqDzFwqdDysj(xmids,gzlslid,zsid,hfwqd);
    }
}
