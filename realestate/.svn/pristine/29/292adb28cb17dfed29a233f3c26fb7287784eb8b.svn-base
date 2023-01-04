package cn.gtmap.realestate.inquiry.web.rest.huaian;

import cn.gtmap.realestate.common.core.qo.inquiry.count.GzltjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.huaian.BdcGzlTjXmxxRestService;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcCstjxxVO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcRyGzltjXmxxVO;
import cn.gtmap.realestate.common.core.vo.inquiry.count.BdcZqxGzltjXmxxVO;
import cn.gtmap.realestate.inquiry.service.huaian.BdcGzltjXmxxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/6/27
 * @description  工作量统计RestController, 根据项目表信息统计工作量
 */
@RestController
@Api(tags = "工作量统计服务接口")
public class BdcGzltjXmxxRestController implements BdcGzlTjXmxxRestService {

    @Autowired
    BdcGzltjXmxxService bdcGzltjXmxxService;

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("统计人员工作量信息（依据登记项目、证书等表数据）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzltjQO", value = "工作量统计qo对象", required = false, paramType = "body"),
    })
    public List<BdcRyGzltjXmxxVO> listRyGzltjByBdcxx(@RequestBody GzltjQO gzltjQO) {
        return bdcGzltjXmxxService.listRyGzltjByBdcxx(gzltjQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("周期性统计工作量信息（依据登记项目、证书等表数据）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gzltjQO", value = "工作量统计qo对象", required = false, paramType = "body"),
    })
    public List<BdcZqxGzltjXmxxVO> listZqxGzltjByBdcxx(@RequestBody GzltjQO gzltjQO) {
        return bdcGzltjXmxxService.listZqxGzltjByBdcxx(gzltjQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("流程超期统计信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "conditionStr", value = "请求条件字符串", required = false, paramType = "body"),
    })
    public List<BdcCstjxxVO> listBdcCstjxx(@RequestBody String conditionStr) {
        return bdcGzltjXmxxService.listBdcCstjxx(conditionStr);
    }
}
