package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpMxtjQO;
import cn.gtmap.realestate.common.core.qo.inquiry.count.BdcDpTjQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcDpCxFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.PageUtils;
import cn.gtmap.realestate.common.util.RedisUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/04/07/9:38
 * @Description:
 */
@RestController
@RequestMapping(value="/rest/v1.0/bdcdp")
@Api(tags = "大屏统计")
public class BdcDpCxController extends BaseController {

    @Autowired
    BdcDpCxFeignService bdcDpCxFeignService;

    @Autowired
    RedisUtils redisUtils;

    public static final String EXPORT_TYPE_ALL = "exportAll";

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏登记类型接口
     */
    @PostMapping(value = "/getjrdjlx")
    public Object getjrdjlx(@RequestBody BdcDpTjQO bdcDpTjQO){
        String stringValue = redisUtils.getStringValue(CommonConstantUtils.REDIS_INQUIRY_JRDJLX);
        //查询条件中没传时间，则直接从redis中获取
        if(StringUtils.isNotBlank(stringValue) && StringUtils.isBlank(bdcDpTjQO.getKssj()) &&StringUtils.isBlank(bdcDpTjQO.getJssj())){
            return JSON.parseObject(stringValue, List.class);
        }
        return bdcDpCxFeignService.listJrdjlx(bdcDpTjQO);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 根据”流程名称/登记小类“统计
     */
    @GetMapping("/getLctjByDjlx")
    @ApiOperation(value = "今日登记类型穿透", tags = "", httpMethod = "GET")
    public Object getLctjByDjlx(@RequestParam(name = "djlx") String djlx,@RequestParam(name = "kssj",required = false) String kssj,
                                @RequestParam(name = "jssj",required = false) String jssj){
        if(StringUtils.isBlank(djlx)){
            throw new AppException("查询参数登记类型不能为空！");
        }
        BdcDpTjQO bdcDpTjQO = new BdcDpTjQO();
        Date date = new Date();
        bdcDpTjQO.setKssj(kssj);
        bdcDpTjQO.setJssj(jssj);
        bdcDpTjQO.setDjlx(djlx);
        if(StringUtils.isBlank(kssj)){
            bdcDpTjQO.setKssj(DateUtils.formateDateToString(date,DateUtils.DATE_FORMAT_2));
        }
        if(StringUtils.isBlank(jssj)){
            bdcDpTjQO.setJssj(DateUtils.formateDateToString(date,DateUtils.DATE_FORMAT_2));
        }
        return bdcDpCxFeignService.getLctjByDjlx(bdcDpTjQO);
    }



    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取发证数量统计接口
     */
    @PostMapping(value = "/getFzsltj")
    public Object getFzsltj(@RequestBody BdcDpTjQO bdcDpTjQO){
        return bdcDpCxFeignService.listFzslTj(bdcDpTjQO);
    }

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取抵押融资统计数据接口
     */
    @PostMapping(value = "/getTyrzTj")
    public BdcDyrzTjDTO getTyrzTj(@RequestBody BdcDpTjQO bdcDpTjQO){
        bdcDpTjQO.setQszt(CommonConstantUtils.QSZT_VALID.toString());
        bdcDpTjQO.setDybdclx(CommonConstantUtils.DYBDCLX_FDYT.toString());
        bdcDpTjQO.setDybdclxList(Arrays.asList(CommonConstantUtils.DYBDCLX_FDYT.toString(),CommonConstantUtils.DYBDCLX_CTD.toString()));
        return bdcDpCxFeignService.getTyrzTj(bdcDpTjQO);
    }

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取住宅统计数据接口
     */
    @PostMapping(value = "/getZztj")
    public BdcDpCxZzTjDTO getZztj(@RequestBody BdcDpTjQO bdcDpTjQO){
        //区县名称和统计时间不传参数，直接从redis获取数据
        if(StringUtils.isBlank(bdcDpTjQO.getJssj()) && StringUtils.isBlank(bdcDpTjQO.getKssj()) && StringUtils.isBlank(bdcDpTjQO.getQxmc())){
            String zztjValue = redisUtils.getStringValue(CommonConstantUtils.REDIS_INQUIRY_ZZTJ);
            if(StringUtils.isNotBlank(zztjValue)){
                BdcDpCxZzTjDTO dto = JSON.parseObject(zztjValue, BdcDpCxZzTjDTO.class);
                return dto;
            }
        }
        return bdcDpCxFeignService.getZzTj(bdcDpTjQO);
    }

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取共享次数统计数据接口
     */
    @PostMapping(value = "/getGxcsTj")
    public Object getGxcsTj(@RequestBody BdcDpTjQO bdcDpTjQO){
        return bdcDpCxFeignService.getGxcsTj(bdcDpTjQO);
    }

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取上报情况统计数据接口
     */
    @PostMapping(value="/getsbqk")
    public Object getSbqk(@RequestBody BdcDpTjQO bdcDpTjQO){
        return bdcDpCxFeignService.getSbqk(bdcDpTjQO);
    }

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取规则检查情况数据接口
     */
    @PostMapping(value="/getgzjcgk")
    public Object getGzjcgk(@RequestBody BdcDpTjQO bdcDpTjQO){
        return bdcDpCxFeignService.getGzjcgk(bdcDpTjQO);
    }

    /**
     * @param bdcDpTjQO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏获取本期得分情况数据接口
     */
    @PostMapping(value="/getBqdfqk")
    public Object getBqdfqk(@RequestBody BdcDpTjQO bdcDpTjQO){
        return bdcDpCxFeignService.getBqdfqk(bdcDpTjQO);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 登记类型统计明细
     */
    @GetMapping("/getDjlxMx")
    @ApiOperation(value = "登记类型统计明细表格查询", tags = "", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(name = "kssj", value = "开始时间", required = true, dataType = "String")})
    public Object getDjlxtjMx(@LayuiPageable Pageable pageable, BdcDpMxtjQO bdcDpMxtjQO){
        Page<BdcDpDjlxtjMxDTO> djlxtjMx = bdcDpCxFeignService.getDjlxtjMx(pageable, JSON.toJSONString(bdcDpMxtjQO));
        return super.addLayUiCode(djlxtjMx);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 登记类型统计图表
     */
    @PostMapping("/getDjlxtjTb")
    @ApiOperation(value = "登记类型统计明细图表", tags = "", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "kssj", value = "开始时间", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jssj", value = "结束时间", required = true, dataType = "String"),
            @ApiImplicitParam(name = "djlx", value = "登记类型", required = true, dataType = "String"),
            @ApiImplicitParam(name = "djxl", value = "登记小类", required = true, dataType = "String")})
    public List<BdcDpDjlxtjMxDTO> getDjlxTb(@RequestBody BdcDpMxtjQO bdcDpMxtjQO){
        return bdcDpCxFeignService.getDjlxTb(bdcDpMxtjQO);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 发证量统计图表
     */
    @GetMapping("/getFzltjMx")
    @ApiOperation(value = "发证量统计表格", tags = "", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(name = "zslx", value = "证书类型", required = true, dataType = "String")})
    public Object getFzltjMx(@LayuiPageable Pageable pageable, BdcDpMxtjQO bdcDpMxtjQO){
        Page<BdcDpFzltjMxDTO> fzltjMx = bdcDpCxFeignService.getFzltjMx(pageable, JSON.toJSONString(bdcDpMxtjQO));
        return super.addLayUiCode(fzltjMx);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 发证量统计明细
     */
    @PostMapping("/getFzltjTb")
    @ApiOperation(value = "发证量统计明细图表", tags = "", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "zslx", value = "证书类型", required = false, dataType = "String")})
    public List<BdcDpFzltjMxDTO> getFzltjTb(@RequestBody BdcDpMxtjQO bdcDpMxtjQO){
        return bdcDpCxFeignService.getFzltjTb(bdcDpMxtjQO);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 抵押金额统计明细
     */
    @GetMapping("/getDyjetjMx")
    @ApiOperation(value = "抵押金额统计明细表格", tags = "", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(name = "qxdm", value = "区县代码", required = false, dataType = "String")})
    public Object getDyjetjMx(@LayuiPageable Pageable pageable, BdcDpMxtjQO bdcDpMxtjQO){
        if(StringUtils.isNotBlank(bdcDpMxtjQO.getQxdm())){
            bdcDpMxtjQO.setQxdmList(Arrays.asList(bdcDpMxtjQO.getQxdm().split(CommonConstantUtils.ZF_YW_DH)));
        }
        Page<BdcDpDyjeMxDTO> dyjetjMx = bdcDpCxFeignService.getDyjetjMx(pageable, JSON.toJSONString(bdcDpMxtjQO));
        return super.addLayUiCode(dyjetjMx);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 抵押金额统计图表
     */
    @PostMapping("/getDyjetjTb")
    @ApiOperation(value = "抵押金额统计明细图表", tags = "", httpMethod = "POST")
    @ApiImplicitParams({@ApiImplicitParam(name = "qxdm", value = "区县代码", required = false, dataType = "String")})
    public Object getDyjetjTb(@RequestBody BdcDpTjQO bdcDpTjQO){
        bdcDpTjQO.setQszt(CommonConstantUtils.QSZT_VALID.toString());
        if(StringUtils.isNotBlank(bdcDpTjQO.getProcessDefKey())){
            bdcDpTjQO.setProcessDefKeyList(Arrays.asList(bdcDpTjQO.getProcessDefKey().split(CommonConstantUtils.ZF_YW_DH)));
        }
        if(StringUtils.isNotBlank(bdcDpTjQO.getQxdm())){
            bdcDpTjQO.setQxdmList(Arrays.asList(bdcDpTjQO.getQxdm().split(CommonConstantUtils.ZF_YW_DH)));
        }
        return bdcDpCxFeignService.getDyjetjTb(bdcDpTjQO);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 住宅统计明细
     */
    @PostMapping("/getZztjMx")
    @ApiOperation(value = "住宅统计明细表格", tags = "", httpMethod = "POST")
    public Object getZztjMx(@RequestBody BdcDpTjQO bdcDpTjQO){
        return bdcDpCxFeignService.getZztjMx(bdcDpTjQO);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 住宅统计图表
     */
    @PostMapping("/getZztjTb")
    @ApiOperation(value = "住宅统计明细统计图表", tags = "", httpMethod = "POST")
    public Object getZztjTb(@RequestBody BdcDpTjQO bdcDpTjQO){
        return bdcDpCxFeignService.getZztjTb(bdcDpTjQO);
    }

    /**
     * @param bdcDpTjQO 不动产大屏统计QO
     * @return BdcDpCxZzTjDTO 不动产大屏住宅统计DTO
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏区县住宅统计，根据区县查询
     */
    @PostMapping(value = "/getQxZztj")
    public BdcDpCxZzTjDTO getQxZztj(@RequestBody BdcDpTjQO bdcDpTjQO){
        return bdcDpCxFeignService.getZzTj(bdcDpTjQO);
    }

    /**
     * @param pageable 分页参数
     * @param bdcDpTjQO 不动产大屏统计QO
     * @return
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 大屏区县住宅统计，根据区县查询
     */
    @GetMapping(value = "/getQualityScore")
    @ApiOperation(value = "接入质量评分接口", tags = "", httpMethod = "GET")
    public Object getQualityScore(@LayuiPageable Pageable pageable, BdcDpTjQO bdcDpTjQO){
        if(StringUtils.isBlank(bdcDpTjQO.getKssj()) && StringUtils.isBlank(bdcDpTjQO.getJssj())){
            throw new AppException("查询时间为空");
        }

        List<SjzlpfResponseDTO> qualityScore = bdcDpCxFeignService.getQualityScore(pageable, JSON.toJSONString(bdcDpTjQO));
        if(EXPORT_TYPE_ALL.equals(bdcDpTjQO.getType())){
            return qualityScore;
        }
        Page page = PageUtils.convertListToPage(qualityScore, pageable);
        return super.addLayUiCode(page);
    }

}
