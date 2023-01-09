package cn.gtmap.realestate.accept.web.rest;

import cn.gtmap.realestate.accept.service.BdcRzdbService;
import cn.gtmap.realestate.accept.service.BdcSlCountService;
import cn.gtmap.realestate.accept.service.BdcSlPrintService;
import cn.gtmap.realestate.accept.web.BaseController;
import cn.gtmap.realestate.common.core.annotations.LogNote;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.BdcDysjPzService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcQzxxFeginService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlPrintRestService;
import cn.gtmap.realestate.common.util.*;
import com.google.zxing.BarcodeFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/12/12,1.0
 * @description
 */
@RestController
@Api(tags = "不动产受理打印服务")
public class BdcSlPrintRestController extends BaseController implements BdcSlPrintRestService {
    @Autowired
    BdcSlPrintService bdcSlPrintService;
    @Autowired
    BdcSlCountService bdcSlCountService;
    @Autowired
    BdcQzxxFeginService bdcQzxxFeginService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcRzdbService bdcRzdbService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    BdcDysjPzService bdcDysjPzService;

    /**
     * @param bdcSlPrintDTO 打印数据传参
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 南通打印根据打印类型获取打印地址
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据打印类型打印服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlPrintDTO", value = "打印数据传参", required = true, dataType = "BdcSlPrintDTO")})
    @Override
    public String print(@RequestBody BdcSlPrintDTO bdcSlPrintDTO) {
        return bdcSlPrintService.getFr3Url(bdcSlPrintDTO);
    }

    /**
     * @param bdcSlPrintDTO 打印传参
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 合肥打印根据流程类型判断获取打印地址
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("根据打印类型打印服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlPrintDTO", value = "打印数据传参", required = true, dataType = "BdcSlPrintDTO")})
    @Override
    public String printByLclx(@RequestBody BdcSlPrintDTO bdcSlPrintDTO) {
        return bdcSlPrintService.getFr3UrlByLclx(bdcSlPrintDTO);
    }

    /**
     * @param processInsId 工作流实例id
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取打印xml(主要用于打印申请书, 从登记库获取数据_合肥)
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取表单打印XMl服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "sjd/sqs", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sjclids", value = "收件材料ids", required = false, dataType = "String", paramType = "query")})
    @Override
    public String packPrintXml(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "zxlc") String zxlc, @RequestParam(name = "sjclids", required = false) String sjclids, @RequestParam(name = "userName", required = false) String userName) {
        return bdcSlPrintService.packPrintXml(processInsId, dylx, zxlc, sjclids, userName);
    }

    /**
     * @param gzlslids 工作流实例id
     * @return String  获取打印xml
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 获取批量打印xml批量
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取表单批量打印XMl服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslids", value = "工作流实例ids", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "sjd/sqs", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sjclids", value = "收件材料ids", required = false, dataType = "String", paramType = "query")})
    @Override
    public String packPrintXmlPl(@PathVariable(name = "gzlslids") String gzlslids, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "zxlc", required = false) String zxlc, @RequestParam(name = "sjclids", required = false) String sjclids, @RequestParam(name = "userName", required = false) String userName) {
        return bdcSlPrintService.packPrintXmlPl(gzlslids, dylx, zxlc, sjclids, userName);
    }

    /**
     * @param processInsId 工作流实例id
     * @param dylx         sqs
     * @param zxlc
     * @param sjclids
     * @param userName
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 相同数据源下获取打印的xml文件---蚌埠申请书打印分产权和抵押--合肥存量房买卖合同只展示产权的
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取表单打印XMl服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "sjd/sqs", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sjclids", value = "收件材料ids", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "xmid", value = "项目id", required = false, dataType = "String", paramType = "query")})
    @Override
    public String packPrintXmlToXmid(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "zxlc") String zxlc, @RequestParam(name = "sjclids", required = false) String sjclids, @RequestParam(name = "userName", required = false) String userName, @RequestParam(name = "xmid", required = false) String xmid) {
        return bdcSlPrintService.packPrintXmlToXmid(processInsId, dylx, zxlc, sjclids, userName, xmid);
    }

    /**
     * @param processInsId 工作流实例ID
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取打印xml(打印的是从受理库获取的内容, 但是需要获取部分登记数据_合肥)
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取表单打印XMl服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "sjd/sqs", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qlrlb", value = "qlr,ywr,all", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "xmid", value = "项目id", required = false, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sjclids", value = "收件材料ids", required = false, dataType = "String", paramType = "query")})
    @Override
    public String packPrintDatas(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx
            , @PathVariable(name = "qlrlb") String qlrlb, @PathVariable(name = "xmid") String xmid, @RequestParam(name = "sjclids", required = false) String sjclids) {
        return bdcSlPrintService.generatePrintData(processInsId, dylx, qlrlb, xmid, sjclids);
    }

    @Override
    public String packPrintPlDatas(@PathVariable(name = "gzlslids") String gzlslids, @PathVariable(name = "dylx") String dylx,
                                   @RequestParam(name = "qlrlb", required = false) String qlrlb, @RequestParam(name = "xmid", required = false) String xmid, @RequestParam(name = "sjclids", required = false) String sjclids) {
        return bdcSlPrintService.generatePrintPlData(gzlslids, dylx, qlrlb, xmid, sjclids);
    }

    /**
     * @param ewmnr 二维码内容
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取二维码信息
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取二维码信息")
    @ApiImplicitParam(name = "ewmnr", value = "二维码内容", required = true, dataType = "string", paramType = "path")
    @Override
    public void packEwmPic(@PathVariable(name = "ewmnr") String ewmnr,
                           HttpServletResponse response,
                           @RequestParam(name = "ewmurl", required = false) String ewmurl,
                           @RequestParam(name = "tmlx", required = false) String tmlx
    ) {
        response.setContentType("image/jpg;charset=utf-8");
        response.addHeader("Content-Disposition", "attachment; filename=test.jpg");
        if(StringUtils.isBlank(tmlx) || "ewm".equals(tmlx)) {
            //二维码
            QRcodeUtils.encoderQRCode(StringUtils.isNotBlank(ewmurl) ? ewmurl : ewmnr, null, response);
        }else if("txm".startsWith(tmlx)){
            //条形码
            QRcodeUtils.encoderCode(StringUtils.isNotBlank(ewmurl) ? ewmurl : ewmnr, null, BarcodeFormat.CODE_128,response);
        }
    }


    /**
     * @param gzlslid 工作流实例ID
     * @return 面积计算结果
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 面积计算
     */
    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "面积计算", notes = "面积计算服务")
    @ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "String", paramType = "path")
    public BdcMjDTO countMj(@PathVariable(value = "gzlslid") String gzlslid) {
        return bdcSlCountService.countMj(gzlslid);
    }

    /**
     * @param processInsId
     * @param dylx
     * @param zxlc
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 相同数据源下获取打印的xml文件(从登记获取数据打印_南通)
     */
    @Override
    public String generateSlXmlToNt(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "zxlc", required = false) String zxlc) {
        return bdcSlPrintService.packPrintXmlToNt(processInsId, dylx, zxlc);
    }

    /**
     * @param processInsId
     * @param dylx
     * @param zxlc
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 申请书只需打印一份，从bdc_djxl_pz 表获取配置的打印类型
     * @date : 2021/7/23 9:50
     */
    @Override
    public String generateDjXmlFromDypz(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "zxlc", required = false) String zxlc) {
        return bdcSlPrintService.generateXmlFromDypz(processInsId, dylx, zxlc);
    }

    /**
     * @param processInsId
     * @param dylx
     * @param xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/7/23 10:26
     */
    @Override
    public String generateSlXmlFromDypz(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "qlrlb") String qlrlb, @PathVariable(name = "xmid") String xmid, @RequestParam(name = "sjclids", required = false) String sjclids) {
        return bdcSlPrintService.generateSlXmlFromDypz(processInsId, dylx, qlrlb, xmid, sjclids);
    }

    /**
     * @param processInsId 工作流实例ID
     * @param dylx         sjd
     * @param qlrlb
     * @param xmid
     * @return String  获取打印xml
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取打印xml（需要从受理库中获取数据,南通接口处理）
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取表单打印XMl服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "sjd/sqs", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qlrlb", value = "qlr,ywr,all", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "xmid", value = "项目id", required = false, dataType = "String", paramType = "path")
    })
    @Override
    public String packPrintDatasToNt(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "qlrlb") String qlrlb, @PathVariable(name = "xmid") String xmid, @RequestParam(name = "userName", required = false) String userName) {
        return bdcSlPrintService.generatePrintDataToNt(processInsId, dylx, qlrlb, xmid,userName);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取表单打印XMl服务")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "sjd/sqs", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sfxxid", value = "收费信息ID", required = false, dataType = "String", paramType = "path")
    })
    @Override
    public String packPrintDatasWithSfxxId(@PathVariable(name = "processInsId") String processInsId,
                                           @PathVariable(name = "dylx") String dylx, @PathVariable(name = "sfxxid") String sfxxid) throws UnsupportedEncodingException {
        return bdcSlPrintService.generatePrintDataWithSfxxid(processInsId, dylx, sfxxid);
    }

    /**
     * @param processInsId 流程实例id
     * @param dylx         打印类型
     * @param qlrlb        权利人类别
     * @param xmid         项目id
     * @param sjclids      收件材料ID
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return: String 打印的项目xml数据
     * @description 受理数据源下获取打印的xml数据，将受理查询出来的字典项dm数据替换为字典项名称数据
     * <p>sql语句查询时，使用字符串拼接的方式将字典项字段对应的值，以（字典名称@字典dm值）fwlx@1的形式进行返回，在生成xml数据将此类型的数据进行替换为字典项的名称</p>
     * <p>例如： select id,name,value,concat('fwlx@',dzwyt) as dzwyt from a </p>
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取表单打印XMl服务，受理数据源并将字典项code替换为登记的字典项名称")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "sjd/sqs", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "qlrlb", value = "qlr,ywr,all", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "xmid", value = "项目id", required = false, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sjclids", value = "收件材料ids", required = false, dataType = "String", paramType = "query")})
    public String packPrintDatasAndReplaceZd(@PathVariable(name = "processInsId") String processInsId, @PathVariable(name = "dylx") String dylx, @PathVariable(name = "qlrlb") String qlrlb, @PathVariable(name = "xmid") String xmid, @RequestParam(name = "sjclids", required = false) String sjclids) {
        return bdcSlPrintService.generatePrintDataAndReplaceZd(processInsId, dylx, qlrlb, xmid, sjclids);
    }

    /**
     * @param bdlx  表单类型
     * @param qzrlb 签字人类别
     * @param xmid  项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/1/10 11:13
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取签名信息")
    public void queryQmxxImage(HttpServletResponse response, @RequestParam(name = "bdlx", required = false) Integer bdlx, @RequestParam(name = "qzrlb", required = false) Integer qzrlb, @RequestParam(name = "xmid", required = false) String xmid) {
        bdcSlPrintService.queryQmxx(bdlx, qzrlb, xmid, response);
    }

    /**
     * @param response
     * @param fjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取电子签名
     * @date : 2022/3/16 11:16
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取电子签名信息")
    public void queryDzqm(HttpServletResponse response, @RequestParam(name = "fjid", required = true) String fjid) {
        if (StringUtils.isNotBlank(fjid)) {
            bdcSlPrintService.queryDzqm(fjid, response);
        }
    }

    /**
     * @param configParam 打印配置参数
     * @return List<Map> 执行结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 执行打印配置sql信息，获取执行结果
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("执行打印配置sql信息，获取执行结果")
    @ApiImplicitParams({@ApiImplicitParam(name = "configParam", value = "打印配置信息", required = true, dataType = "Map", paramType = "body")})

    public List<Map> executeConfigSql(@RequestBody Map configParam) {
        return bdcSlPrintService.executeConfigSql(configParam);
    }

    /**
     * @param bdcSlPrintDTO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 新模式获取打印生成xml数据源地址
     * @date : 2020/4/16 17:00
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("新模式下组织打印参数")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlPrintDTO", value = "打印数据传参", required = true, dataType = "BdcSlPrintDTO")})
    public BdcNewPrintDTO generatePrintDTO(@RequestBody BdcSlPrintDTO bdcSlPrintDTO) {
        return bdcSlPrintService.generatePrintDTO(bdcSlPrintDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("新模式批量下组织打印参数")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlPrintDTO", value = "批量打印数据传参", required = true, dataType = "BdcSlPrintDTO")})
    public BdcNewPrintDTO generatePrintPlDTO(@RequestBody BdcSlPrintDTO bdcSlPrintDTO) {
        return bdcSlPrintService.generatePrintPlDTO(bdcSlPrintDTO);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取特殊打印参数信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "bdcSlPrintDTO", value = "打印数据传参", required = true, dataType = "BdcSlPrintDTO")})
    public BdcSlDysjDTO getTsDycs(@RequestBody BdcSlPrintDTO bdcSlPrintDTO) {
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(bdcSlPrintDTO.getGzlslid());
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            throw new AppException("获取打印参数没有找到对应的项目信息");
        }
        int lclx = bdcXmFeignService.makeSureBdcXmLx(bdcSlPrintDTO.getGzlslid());
        return bdcSlPrintService.generateDylxAndSl(bdcXmDOList.get(0), bdcSlPrintDTO.getDylx(), lclx);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("特殊业务打印需求服务（蚌埠）：获取申请人信息并调蚌埠大数据接口获取人员信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "processInsId", value = "工作流实例ID", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "qlrlb", value = "qlr,ywr,all", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "xmid", value = "项目id", required = false, dataType = "String", paramType = "path")})
    @Override
    public Object packPrintQlrDatasForBb(@PathVariable(name = "processInsId") String processInsId,
                                         @PathVariable(name = "qlrlb") String qlrlb,
                                         @PathVariable(name = "xmid") String xmid,
                                         @PathVariable(name = "userName") String userName) {
        return bdcSlPrintService.getQlrPrintDataForBb(processInsId, qlrlb, xmid, userName);
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取redis中存储的base64code字符转换后的文件流图片")
    @ApiImplicitParams({@ApiImplicitParam(name = "rediskey", value = "redis键", required = true, dataType = "string", paramType = "path")})
    public void getPrintZp(@PathVariable(name = "rediskey") String rediskey, HttpServletResponse response) {
        String imageBase64String = redisUtils.getStringValue(rediskey);
        if (StringUtils.isNotBlank(imageBase64String)) {
            Base64Utils.changeBase64ToImageAndZoom(imageBase64String, rediskey + ".jpg", "JPG", 0.18F, response);
        }
    }

    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取redis中存储的base64code字符转换后的文件流图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rediskey", value = "redis键", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "文件类型", required = false, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "scale", value = "缩放大小", required = false, dataType = "float", paramType = "path")
    })
    public void getPrintZp(@PathVariable(name = "rediskey") String rediskey, @PathVariable(name = "type") String type,
                           @PathVariable(name = "scale") float scale, HttpServletResponse response) {
        String imageBase64String = redisUtils.getStringValue(rediskey);
        if(StringUtils.isNotBlank(imageBase64String)){
            if(StringUtils.isBlank(type)){
                type = "PNG";
            }
            if(Objects.isNull(scale)){
                scale = 1F;
            }
            Base64Utils.changeBase64ToImageAndZoom(imageBase64String, rediskey + CommonConstantUtils.ZF_YW_JH + type, type, scale, response);
        }
    }


    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("特殊业务打印需求服务（蚌埠）：获取人像信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "gzlslid", value = "工作流实例ID", required = true, dataType = "string", paramType = "path")})
    @Override
    public Object packPrintRxtpDatasForBb(@PathVariable(name = "gzlslid") String gzlslid) {
        return bdcSlPrintService.packPrintRxtpDatasForBb(gzlslid);
    }

    /**
     * @param response
     * @param xmid     项目id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州查档打印获取校验查询二维码
     * @date : 2020/1/10 11:13
     */
    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("常州查档打印获取校验查询二维码")
    @ApiImplicitParams({@ApiImplicitParam(name = "xmid", value = "项目id", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "dylx", value = "打印类型", required = false, dataType = "string", paramType = "query")})
    @Override
    public void queryJycxImage(@PathVariable(value = "xmid") String xmid,@RequestParam(value = "dylx",required = false)String dylx, HttpServletResponse response) throws Exception {
        bdcSlPrintService.queryJycx(xmid,dylx,response);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("组装打印参数并添加自定义xml数据参数")
    @ApiImplicitParams({@ApiImplicitParam(name = "map", value = "map", required = true, dataType = "java.util.map", paramType = "param"),
            @ApiImplicitParam(name = "dylx", value = "打印类型", required = true, dataType = "string", paramType = "path")})
    @Override
    public String packPrintDataWithZdyData(@RequestParam Map map, @PathVariable(name = "dylx") String dylx) {
        return this.bdcSlPrintService.generatePrintDataWithZdyData(map,dylx);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("生成PDF文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "slbh", value = "受理编号", required = true, dataType = "string", paramType = "path"),
            @ApiImplicitParam(name = "gzlslid", value = "工作实例ID", required = true, dataType = "string", paramType = "path")
    })
    @Override
    public String scrzdb(@PathVariable(name = "slbh") String slbh, @PathVariable(name = "gzlslid") String gzlslid){
        return bdcRzdbService.scrzdbPdf(slbh, gzlslid);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("获取打印模板目录下，正确与错误的图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "图片类型", required = true, dataType = "string", paramType = "path")})
    @Override
    public void getSuccessOrErrorImage(@PathVariable(name = "type") String type, HttpServletResponse response) {
        this.bdcRzdbService.generateSuccessOrErrorImage(type, response);
    }

    /**
     * 非税开电子发票图片流
     *
     * @param id
     * @param response
     * @return
     */
    @Override
    public void fskpImg(@PathVariable("id") String id, HttpServletResponse response) {
        bdcSlPrintService.fskpImg(id,response);
    }

    /**
     * 查档流程签字图片流
     *
     * @param gzlslid
     * @param response
     * @return
     */
    @Override
    public void cdxxSignImg(@PathVariable("gzlslid")String gzlslid, HttpServletResponse response) {
        bdcSlPrintService.cdxxSignImg(gzlslid,response);
    }


    @ResponseStatus(code = HttpStatus.OK)
    @ApiOperation("组装打印参数，添加审核信息xml信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "map", value = "map", required = true, dataType = "java.util.map", paramType = "param"),
            @ApiImplicitParam(name = "dylx", value = "打印类型", required = true, dataType = "string", paramType = "path")})
    @Override
    public String packPrintDataWithShxxData(@RequestParam Map map, @PathVariable(name = "dylx") String dylx) {
        return this.bdcSlPrintService.generatePrintDataWithShxxData(map, dylx);
    }

    /**
     * @param gzlslid
     * @param dylx
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 常州批量收费缴费书打印接口
     * @date : 2021/11/8 20:53
     */
    @Override
    public String getCzJfsXml(@PathVariable("gzlslid") String gzlslid, @PathVariable("dylx") String dylx) {
        return bdcSlPrintService.queryJfsXml(gzlslid, dylx);
    }

    /**
     * @param bdcSlZbDataDTO@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 子表打印数据源查询结果
     * @date : 2021/11/16 10:39
     */
    @Override
    public List<Map> queryZbData(@RequestBody BdcSlZbDataDTO bdcSlZbDataDTO) {
        return bdcSlPrintService.executSql(bdcSlZbDataDTO);
    }
    /**
     * @param zjh 证件号
     * @param xm 姓名
     * @param dylx 打印类型
     * @author <a href="mailto:gaolining@gtmap.cn">sunyuzhuang</a>
     * @description 常州查档生成PDF接口获取校验查询二维码
     * @date : 2020/1/10 11:13
     */
    @Override
    public void cdxxscpdfJycxImage(@RequestParam(name = "zjh") String zjh, @RequestParam(name = "xm") String xm,
                                   @RequestParam(name = "dylx" ,required = false) String dylx, HttpServletResponse response) throws Exception {
        bdcSlPrintService.cdxxscpdfJycxImage(zjh,xm,dylx,response);
    }

    /**
     * @param gzlslid
     * @author <a href="mailto:duwei@gtmap.cn">duwei</a>
     * @description 连云港申请书等附件组织打印xml
     * @date : 2022/12/12 10:14
     */
    @Override
    @ResponseStatus(code = HttpStatus.OK)
    @LogNote(value = "组装打印参数，生成xml信息", action = LogActionConstans.OTHER)
    public Map generateSlMkXml(@RequestParam("gzlslid") String gzlslid,  @RequestParam(name = "djxl") String djxl, @RequestParam("zxlc") String zxlc) {
        return bdcSlPrintService.generateSlMkXml(gzlslid, djxl, zxlc);
    }
}
