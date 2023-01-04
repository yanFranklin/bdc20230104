package cn.gtmap.realestate.inquiry.web.rest.jsc;

import cn.gtmap.realestate.common.core.dto.inquiry.jsc.*;
import cn.gtmap.realestate.common.core.qo.inquiry.jsc.JscCommomQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.jsc.BdcXuanchengJscRestService;
import cn.gtmap.realestate.inquiry.service.jsc.BdcXuanchengJscService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:zhongjinpe@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/6 09:40
 * @description 舒城共享接口请求
 */
@RestController
@Api(tags = "宣城图表接口")
public class BdcXuanchengJscRestController implements BdcXuanchengJscRestService {
    @Autowired
    BdcXuanchengJscService bdcXuanchengJscService;

    /**
     * 林权登记总面积
     *
     * @param jscCommomQO
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "林权登记总面积", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "jscCommomQO", value = "请求参数", required = true, paramType = "body")})
    public List<Integer> queryJscDjZmj(@RequestBody JscCommomQO jscCommomQO) {
        return bdcXuanchengJscService.queryJscDjZmj(jscCommomQO);
    }

    /**
     * 登记数量图表汇总
     *
     * @param jscCommomQO
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登记数量图表汇总", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "jscCommomQO", value = "请求参数", required = true, paramType = "body")})
    public Map<String, List<JscDjslDTO>> queryJscDjslList(@RequestBody JscCommomQO jscCommomQO) {
        return bdcXuanchengJscService.queryJscDjslList(jscCommomQO);
    }

    /**
     * 驾驶舱 概况
     *
     * @param jscCommomQO@return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "概况", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "jscCommomQO", value = "请求参数", required = true, paramType = "body")})
    public List<Integer> queryJscSummary(@RequestBody JscCommomQO jscCommomQO) {
        return bdcXuanchengJscService.queryJscSummary(jscCommomQO);
    }

    /**
     * 驾驶舱 林权权利
     *
     * @param jscCommomQO@return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "林权权利", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "jscCommomQO", value = "请求参数", required = true, paramType = "body")})
    public Map<String, List<JscLqqlDTO>> queryJscQl(@RequestBody JscCommomQO jscCommomQO) {
        return bdcXuanchengJscService.queryJscQl(jscCommomQO);
    }

    /**
     * 驾驶舱 登记趋势
     *
     * @param jscCommomQO@return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登记趋势", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "jscCommomQO", value = "请求参数", required = true, paramType = "body")})
    public JscLqqsDTO queryJscTrend(@RequestBody JscCommomQO jscCommomQO) {
        return bdcXuanchengJscService.queryJscTrend(jscCommomQO);
    }

    /**
     * 驾驶舱 交易情况（转移登记）
     *
     * @param jscCommomQO@return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "交易情况", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "jscCommomQO", value = "请求参数", required = true, paramType = "body")})
    public List<JscLqjyDTO> queryJscTransaction(@RequestBody JscCommomQO jscCommomQO) {
        return bdcXuanchengJscService.queryJscTransaction(jscCommomQO);
    }


    /**
     * 登记数量 地图
     *
     * @param jscCommomQO
     * @return
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "登记数量 地图", extensions = @Extension(properties = @ExtensionProperty(name = "saveLog", value = "false")))
    @ApiImplicitParams({@ApiImplicitParam(name = "jscCommomQO", value = "请求参数", required = true, paramType = "body")})
    public Map<String, JscLqjyDTO> queryJscDjslMap(@RequestBody JscCommomQO jscCommomQO) {
        return bdcXuanchengJscService.queryJscDjslMap(jscCommomQO);
    }

    /**
     * 历史遗留问题柱图
     *
     * @param jscCommomQO@return
     */
    @Override
    public Map<String, List<JscLsylwtDTO>> queryLsylwt(@RequestBody JscCommomQO jscCommomQO) {
        return bdcXuanchengJscService.queryLsytwt(jscCommomQO);
    }

    /**
     * 未解决问题概况区县占比图
     *
     * @param jscCommomQO@return
     */
    @Override
    public List<JscLsylwtDTO> queryLsylwtWjjQxzb(@RequestBody JscCommomQO jscCommomQO) {
        return bdcXuanchengJscService.queryLsylwtWjjQxzb(jscCommomQO);
    }

    /**
     * 区县中未解决问题柱图（要按照问题类型进行补充）
     *
     * @param jscCommomQO @return
     */
    @Override
    public List<JscLsylwtDTO> queryLsylwtQxWtlxzb(@RequestBody JscCommomQO jscCommomQO) {
        return bdcXuanchengJscService.queryLsylwtQxWtlxzb(jscCommomQO);
    }
}
