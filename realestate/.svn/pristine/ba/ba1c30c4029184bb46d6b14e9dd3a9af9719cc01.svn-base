package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.qo.inquiry.count.BjsctjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcBjscTjRestService;
import cn.gtmap.realestate.inquiry.service.BdcBjscTjService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/5/15
 * @description  查询子系统：办件时长统计
 */
@RestController
@Api(tags = "不动产办件时长统计服务接口")
public class BdcBjscTjRestController implements BdcBjscTjRestService {
    @Autowired
    private BdcBjscTjService bdcBjscTjService;
    @Value("${bjsctjsjd:<3,3-5,>5}")
    private String period;

    /**
     * @author <a href="mailto:chenyuccheng@gtmap.cn">chenyucheng</a>
     * @param bjsctjParamJson 查询条件
     * @return  办件时长统计集合
     * @description 办件时长统计
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "办件时长统计参数JSON", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", required = true, paramType = "body"),
            @ApiImplicitParam(name = "gzltjParamJson", value = "办件时长统计参数JSON", required = false, paramType = "query")})
    public List<Map> listBjscCount(@RequestParam(name = "bjsctjParamJson", required = false) String
                                                      bjsctjParamJson) {
        return bdcBjscTjService.listBjscCount(JSON.parseObject(bjsctjParamJson, BjsctjQO.class));
    }

    /**
     * @author <a href="mailto:chenyuccheng@gtmap.cn">chenyucheng</a>
     * @return  时间段配置
     * @description 时间段配置
     */
    @Override
    public String sjdpz() {
        return period;
    }

}
