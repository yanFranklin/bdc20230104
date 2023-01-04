package cn.gtmap.realestate.init.web.rest;

import cn.gtmap.realestate.common.core.service.rest.init.BdcRunSqlRestService;
import cn.gtmap.realestate.init.service.sendMsg.BdcRunSqlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author <a href ="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.1, 2019/7/24
 * @description sql运行
 */
@RestController
@Api(tags = "执行sql服务接口")
public class BdcRunSqlRestController implements BdcRunSqlRestService {
    @Autowired
    private BdcRunSqlService bdcRunSqlService;


    /**
     * @author <a href ="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @version 1.1, 2019/7/24
     * @description sql运行
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("sql运行")
    @ApiImplicitParams({@ApiImplicitParam(name = "sql", value = "sql运行", required = true, paramType = "String")})
    public List<HashMap> runSql(@RequestParam("sql") String sql) {
        return bdcRunSqlService.runSql(sql);
    }
}
