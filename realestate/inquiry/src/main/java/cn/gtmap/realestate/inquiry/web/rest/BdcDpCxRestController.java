package cn.gtmap.realestate.inquiry.web.rest;

import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpMxtjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpTjQO;
import cn.gtmap.realestate.common.core.service.rest.inquiry.BdcDpCxRestService;
import cn.gtmap.realestate.inquiry.service.BdcDpCxService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.List;


/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/04/07/14:18
 * @Description:
 */
@RestController
@Api(tags = "不动产大屏查询服务接口")
public class BdcDpCxRestController implements BdcDpCxRestService {

    @Autowired
    BdcDpCxService bdcDpCxService;
    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏今日登记类型数据接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏今日登记类型数据接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpTjQO", value = "不动产大屏统计QO", dataType = "BdcDpTjQO")})
    public Object listJrdjlx(@RequestBody BdcDpTjQO bdcDpTjQO) {
        return bdcDpCxService.listJrdjlx(bdcDpTjQO);
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏发证量数据接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏发证量数据接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpTjQO", value = "不动产大屏统计QO", dataType = "BdcDpTjQO")})
    public Object listFzslTj(@RequestBody BdcDpTjQO bdcDpTjQO) {
        return bdcDpCxService.listFzslTj(bdcDpTjQO);
    }
    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏抵押融资数据接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏抵押融资数据接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpTjQO", value = "不动产大屏统计QO", dataType = "BdcDpTjQO")})
    public BdcDyrzTjDTO getTyrzTj(@RequestBody BdcDpTjQO bdcDpTjQO) {
        BdcDyrzTjDTO tyrzTj = bdcDpCxService.getTyrzTj(bdcDpTjQO);
        return tyrzTj;
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取住宅统计数据接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏获取住宅统计数据接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpTjQO", value = "不动产大屏统计QO", dataType = "BdcDpTjQO")})
    public BdcDpCxZzTjDTO getZzTj(@RequestBody BdcDpTjQO bdcDpTjQO) {
        return  bdcDpCxService.getZzTj(bdcDpTjQO);
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取共享次数情况接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏获取共享次数情况接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpTjQO", value = "不动产大屏统计QO", dataType = "BdcDpTjQO")})
    public Object getGxcsTj(@RequestBody BdcDpTjQO bdcDpTjQO) {
        return bdcDpCxService.getGxcsTj(bdcDpTjQO);
    }
    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取上报情况接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏获取上报情况接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpTjQO", value = "不动产大屏统计QO", dataType = "BdcDpTjQO")})
    public Object getSbqk(@RequestBody BdcDpTjQO bdcDpTjQO) {
        return bdcDpCxService.getSbqk(bdcDpTjQO);
    }
    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取规则检查情况接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏获取上报情况接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpTjQO", value = "不动产大屏统计QO", dataType = "BdcDpTjQO")})
    public Object getGzjcgk(@RequestBody BdcDpTjQO bdcDpTjQO) {
        return bdcDpCxService.getGzjcgk(bdcDpTjQO);
    }

    /**
     * @param bdcDpTjQO 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取本期得分情况接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏获取本期得分情况接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpTjQO", value = "不动产大屏统计QO", dataType = "BdcDpTjQO")})
    public Object getBqdfqk(@RequestBody BdcDpTjQO bdcDpTjQO) {
        return bdcDpCxService.getBqdfqk(bdcDpTjQO);
    }

    /**
     * @param paramJson 查询参数
     * @param pageable 分页信息
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏登记类型详细台账获取表格信息接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏登记类型详细台账获取表格信息接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", dataType = "Pageable"),
            @ApiImplicitParam(name = "paramJson", value = "查询参数", dataType = "String")})
    public Page<BdcDpDjlxtjMxDTO>  getDjlxtjMx(Pageable pageable, @RequestParam(name = "paramJson",required = false) String paramJson) {
        BdcDpMxtjQO bdcDpMxtjQO = new BdcDpMxtjQO();
        if(StringUtils.isNotBlank(paramJson)){
            bdcDpMxtjQO = JSONObject.parseObject(paramJson,BdcDpMxtjQO.class);
            if(StringUtils.isNotBlank(bdcDpMxtjQO.getProcessDefKey())){
                bdcDpMxtjQO.setProcessDefKeyList(Arrays.asList(StringUtils.split(bdcDpMxtjQO.getProcessDefKey())));
            }
        }
        Page<BdcDpDjlxtjMxDTO> bdcDpDjlxtjMxDTOS = bdcDpCxService.getDjlxtjMx(pageable ,bdcDpMxtjQO);
        return bdcDpDjlxtjMxDTOS;
    }
    /**
     * @param bdcDpMxtjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏登记类型详细台账获取图表信息接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏登记类型详细台账获取图表信息接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpMxtjQO", value = "不动产大屏明细统计QO", dataType = "BdcDpMxtjQO")})
    public List<BdcDpDjlxtjMxDTO>  getDjlxTb(@RequestBody BdcDpMxtjQO bdcDpMxtjQO) {
        if(StringUtils.isNotBlank(bdcDpMxtjQO.getProcessDefKey())){
            bdcDpMxtjQO.setProcessDefKeyList(Arrays.asList(StringUtils.split(bdcDpMxtjQO.getProcessDefKey())));
        }
        List<BdcDpDjlxtjMxDTO> djlxtjTb = bdcDpCxService.getDjlxtjTb(bdcDpMxtjQO);
        return djlxtjTb;
    }

    /**
     * @param paramJson 查询参数
     * @param pageable 分页信息
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏发证量详细台账获取表格信息接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏发证量详细台账获取表格信息接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", dataType = "Pageable"),
            @ApiImplicitParam(name = "paramJson", value = "查询参数", dataType = "String")})
    public Page<BdcDpFzltjMxDTO>  getFzltjMx(Pageable pageable, @RequestParam(name = "paramJson",required = false) String paramJson) {
        BdcDpMxtjQO bdcDpMxtjQO = new BdcDpMxtjQO();
        if(StringUtils.isNotBlank(paramJson)){
            bdcDpMxtjQO = JSONObject.parseObject(paramJson,BdcDpMxtjQO.class);
            if(StringUtils.isNotBlank(bdcDpMxtjQO.getProcessDefKey())){
                bdcDpMxtjQO.setProcessDefKeyList(Arrays.asList(StringUtils.split(bdcDpMxtjQO.getProcessDefKey())));
            }
        }
        Page<BdcDpFzltjMxDTO> bdcDpFzltjMxDTOS = bdcDpCxService.getFztjMx(pageable,bdcDpMxtjQO);
        return bdcDpFzltjMxDTOS;
    }

    /**
     * @param bdcDpMxtjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏发证量详细台账获取图表信息接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏发证量详细台账获取图表信息接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpMxtjQO", value = "不动产大屏明细统计QO", dataType = "BdcDpMxtjQO")})
    public List<BdcDpFzltjMxDTO> getFzltjTb(@RequestBody BdcDpMxtjQO bdcDpMxtjQO) {
        if(StringUtils.isNotBlank(bdcDpMxtjQO.getProcessDefKey())){
            bdcDpMxtjQO.setProcessDefKeyList(Arrays.asList(StringUtils.split(bdcDpMxtjQO.getProcessDefKey())));
        }
        List<BdcDpFzltjMxDTO> fzltjTb = bdcDpCxService.getFzltjTb(bdcDpMxtjQO);
        return fzltjTb;
    }

    /**
     * @param paramJson 查询参数
     * @param pageable 分页信息
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏抵押金额详细台账获取表格信息接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏抵押金额详细台账获取表格信息接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", dataType = "Pageable"),
            @ApiImplicitParam(name = "paramJson", value = "查询参数", dataType = "String")})
    public Page<BdcDpDyjeMxDTO> getDyjetjMx(Pageable pageable, @RequestParam(name = "paramJson",required = false) String paramJson) {
        BdcDpMxtjQO bdcDpMxtjQO = new BdcDpMxtjQO();
        if(StringUtils.isNotBlank(paramJson)){
            bdcDpMxtjQO = JSONObject.parseObject(paramJson,BdcDpMxtjQO.class);
            if(StringUtils.isNotBlank(bdcDpMxtjQO.getProcessDefKey())){
                bdcDpMxtjQO.setProcessDefKeyList(Arrays.asList(StringUtils.split(bdcDpMxtjQO.getProcessDefKey())));
            }
        }
        Page<BdcDpDyjeMxDTO> bdcDpDyjeMxDTOS = bdcDpCxService.getDyjetjMx(pageable,bdcDpMxtjQO);

        return bdcDpDyjeMxDTOS;
    }

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏抵押金额详细台账获取图表信息接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "大屏抵押金额详细台账获取图表信息接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpTjQO", value = "不动产大屏统计QO", dataType = "BdcDpTjQO")})
    public Object getDyjetjTb(@RequestBody BdcDpTjQO bdcDpTjQO) {
        return bdcDpCxService.getDyjetjTb(bdcDpTjQO);
    }

    @Override
    public Object getZztjMx(@RequestBody BdcDpTjQO bdcDpTjQO) {
        return null;
    }

    @Override
    public Object getZztjTb(@RequestBody BdcDpTjQO bdcDpTjQO) {
        return null;
    }

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 根据”流程名称/登记小类“统计
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "根据”流程名称/登记小类“统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcDpTjQO", value = "不动产大屏统计QO", dataType = "BdcDpTjQO")})
    public Object getLctjByDjlx(@RequestBody BdcDpTjQO bdcDpTjQO) {
        return bdcDpCxService.getLctjByDjlx(bdcDpTjQO);
    }

    /**
     * @param pageable 分页参数
     * @param paramJson 查询参数
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 获取省级接入质量评分接口
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取省级接入质量评分接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageable", value = "分页参数", dataType = "Pageable"),
            @ApiImplicitParam(name = "paramJson", value = "查询参数", dataType = "String")})
    public List<SjzlpfResponseDTO> getQualityScore(Pageable pageable, @RequestParam(name = "paramJson",required = false) String paramJson) {
        BdcDpTjQO bdcDpTjQO = new BdcDpTjQO();
        if(StringUtils.isNotBlank(paramJson)){
            bdcDpTjQO = JSONObject.parseObject(paramJson,BdcDpTjQO.class);
        }
        return bdcDpCxService.getQualityScore(bdcDpTjQO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation(value = "获取大屏查询统计配置信息")
    public Object getBdcDpConfig() {
        return bdcDpCxService.getBdcDpConfig();
    }


}
