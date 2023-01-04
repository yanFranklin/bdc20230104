package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.service.rest.exchange.NantongShuiService;
import cn.gtmap.realestate.exchange.service.nantong.NantongSqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/6/10
 * @description 电过户
 */
@RestController
@Api(tags = "南通水相关服务接口")
public class NantongShuiController implements NantongShuiService {

    @Autowired
    private NantongSqService nantongSqService;

    /**
     * 根据gzlslid进行过户操作
     *
     * @param processInsId
     * @return
     * @Date 2021/6/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流主键进行过户操作")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "processInsId", value = "工作流主键", required = true, dataType = "String", paramType = "query")}
    )
    public void sgh(@RequestParam(name = "processInsId") String processInsId) {
        nantongSqService.sgh(processInsId);
    }


    /**
     * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description  查询水过户信息
     * @Date 2022/4/26
     * @Param
     * @return
     **/
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据工作流主键进行过户操作")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "processInsId", value = "工作流主键", required = true, dataType = "String", paramType = "query"),
                    @ApiImplicitParam(name = "consno", value = "户号", required = true, dataType = "String", paramType = "query"),
                    @ApiImplicitParam(name = "dwdm", value = "供水单位代码", required = true, dataType = "String", paramType = "query")
            }
    )
    public CommonResponse<Integer> sghjc(String gzlslid, String consno, String dwdm) {
        return nantongSqService.sghjc(gzlslid, consno, dwdm);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "推送登记数据至自来水公司")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "String", paramType = "query")}
    )
    public CommonResponse bdcsdqZlsTs(@RequestParam(name = "processInsId") String processInsId) {
        return this.nantongSqService.bdcSdqZlsTs(processInsId);
    }

}
