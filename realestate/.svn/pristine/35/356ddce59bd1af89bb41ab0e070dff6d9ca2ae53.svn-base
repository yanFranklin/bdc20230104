package cn.gtmap.realestate.exchange.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.dto.OfficeExportDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.*;
import cn.gtmap.realestate.common.core.dto.register.BdcShxxPdfDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.qo.register.BdcShxxQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcBhFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcBzbZmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcNtFczmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcDjbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcShxxFeignService;
import cn.gtmap.realestate.common.core.support.pdf.PdfController;
import cn.gtmap.realestate.common.util.BeansResolveUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.exchange.core.annotation.OpenApi;
import cn.gtmap.realestate.exchange.core.annotation.OpenController;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfDaCheckLog;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.wwsq.WwsqResponse;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.dbxxcx.response.DbxxCxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.dbxxcx.response.DbxxCxResponseData;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.dbxxcx.response.DbxxCxResponseFwxx;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.dbxxcx.response.DbxxCxResponseQl;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.request.FwqlCxRequestQlr;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.FwqlCxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.FwqlCxResponseData;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.FwqlCxResponseFwxx;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.fwqlcx.response.FwqlCxResponseQl;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.request.ZfcxRequestDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.request.ZfcxRequestQlr;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.FwlbResponse;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.ZfcxResponseDTO;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.ZfcxResponseData;
import cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.response.ZfcxResponseFw;
import cn.gtmap.realestate.exchange.core.qo.BdcFwxxQO;
import cn.gtmap.realestate.exchange.service.impl.inf.nantong.ZfcxService;
import cn.gtmap.realestate.exchange.service.impl.national.dzzz.DzqzGmxaService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import cn.gtmap.realestate.exchange.util.constants.LogCzxxConstants;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-24
 * @description 标准版 自助查询机 有房无房查询服务
 */
@OpenController(consumer = "标准版自助查询机有房无房查询服务")
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0")
@Api(tags = "标准版自助查询机有房无房查询服务")
public class StandardZfcxRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandardZfcxRestController.class);

    // 判断是否需要验证同名权利人证件号为空或不规则的情况（/searchFc/isExitHouse接口）
    @Value("${zzcxj.valid.qlrzjh:true}")
    private boolean validQlrzjh;

    // 判断是否需要验证同名权利人证件号为空或不规则的情况（/searchFc/houseStatusByQl接口）
    @Value("${zzcxj.valid.qlrzjh2:true}")
    private boolean validQlrzjh2;

    /**
     * 权利人名称和证件号必填验证（true 两个参数都要；fasle，允许只根据权利人证件号查询）
     */
    @Value("${zzcxj.valid.qlrmczjhbt:false}")
    private boolean validateQlrmczjh;

    // 默认打印地址
    @Value("${print.path:/usr/local/bdc3/print/}")
    private String printPath;

    /**
     * 有房无房证明是否生成pdf
     */
    @Value("${yfwfzm.scpdf:false}")
    private boolean yfwfzmscpdf;


    @Autowired
    private ZfcxService zfcxService;

    @Autowired
    private BdcDjbxxFeignService djbxxFeignService;

    @Autowired
    BdcNtFczmFeignService bdcNtFczmFeignService;

    @Autowired
    BdcZfxxCxFeignService bdcZfxxCxFeignService;

    @Autowired
    PdfController pdfController;

    @Autowired
    DzqzGmxaService dzqzGmxaService;

    @Autowired
    private BdcBzbZmFeignService bdcBzbZmFeignService;

    @Autowired
    private BdcShxxFeignService bdcShxxFeignService;

    /** 编号服务 */
    @Autowired
    private BdcBhFeignService bdcBhFeignService;
    /** 日期格式化 */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.CHINA);


    /**
     * @param areacode
     * @param zfcxRequestDTO
     * @return cn.gtmap.realestate.exchange.core.dto.nantong.zfcx.response.ZfcxResponseDTO
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 标准版自助查询机 有房无房查询
     */
    @OpenApi(apiDescription = "标准版自助查询机有房无房查询", apiName = "", openLog = false)
    @PostMapping(value = {"/searchFc/isExitHouse/{areacode}"})
    @DsfLog(logEventName = "标准版自助查询机有房无房查询", dsfFlag = "ZZCXJ", requester = "ZZCXJ", responser = "BDC")
    @DsfDaCheckLog(logEventName = "标准版自助查询机有房无房查询", dsfFlag = "ZZCXJ", requester = "ZZCXJ", responser = "BDC", interfaceFlagCode = "05", checkFlagIndex = 1, checkFlagClassName = "cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.request.ZfcxRequestDTO", checkFlagLever = "0",interfaceUrl = "/realestate-exchange/rest/v1.0/searchFc/isExitHouse/{areacode}")
    Object zfcx(@PathVariable(name = "areacode") String areacode,
                @RequestBody ZfcxRequestDTO zfcxRequestDTO) {
        long startTime = System.currentTimeMillis();
        ZfcxResponseDTO zfcxResponseDTO = new ZfcxResponseDTO();
        String cdsj = zfcxResponseDTO.getQqsj();
        zfcxResponseDTO.setData(new ZfcxResponseData());
        List<ZfcxRequestQlr> qlrList = zfcxRequestDTO.getQlrlist();
        if (CollectionUtils.isEmpty(qlrList)) {
            zfcxResponseDTO.getData().setIsSuccessful("查询失败");
            zfcxResponseDTO.setMessage("未找到查询参数");
            return zfcxResponseDTO;
        }

        // 验证权利人名称、证件号需要同时存在
        if (validateQlrmczjh) {
            for (ZfcxRequestQlr qlr : zfcxRequestDTO.getQlrlist()) {
                if (null == qlr || (StringUtils.isAnyBlank(qlr.getQlr(), qlr.getSfzh()))) {
                    zfcxResponseDTO.getData().setIsSuccessful("查询失败");
                    zfcxResponseDTO.setMessage("存在权利人名称或证件号为空情况");
                    return zfcxResponseDTO;
                }
            }
        }

        // 使用权利人姓名查询权利人 如果存在 证件号为空 或证件号 不足15位 18位 则返回异常
        if (validQlrzjh && zfcxService.checkBdcQlrHasZjhEx(qlrList)) {
            zfcxResponseDTO.getData().setIsSuccessful("查询失败");
            zfcxResponseDTO.setMessage("存在相同权利人名称但是证件号为空或不规范的情况，请去人工窗口查询");
            return zfcxResponseDTO;
        }

        try {
            List<ZfcxResponseFw> fwList = zfcxService.queryFwListByRequest(zfcxRequestDTO, cdsj, areacode);
            // 去重处理
            fwList = this.distinctZfxx(fwList);
            //查询各个限制状态
            queryXzzt(fwList);

            zfcxResponseDTO.getData().setFwlist(fwList);
            zfcxResponseDTO.getData().setIsSuccessful("查询成功");
            Map<String, Object> ywbhMap;
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(zfcxRequestDTO));
            jsonObject.put("cztype", LogCzxxConstants.CZTYPE_CJZM);
            if (CollectionUtils.isNotEmpty(fwList)) {
                ywbhMap = zfcxService.yfcxWebServiceQuery(
                        JSONObject.toJSONString(zfcxResponseDTO.getData()), jsonObject.toString());
            } else {
                ywbhMap = zfcxService.wfcxWebServiceQuery(
                        JSONObject.toJSONString(zfcxResponseDTO.getData()), jsonObject.toString());
            }
            if (MapUtils.isNotEmpty(ywbhMap)) {
                String ywbh = CommonUtil.ternaryOperator(ywbhMap.get("cxbh"));
                zfcxResponseDTO.getData().setYWBH(ywbh);
                zfcxResponseDTO.setSuccess(true);
            }
            zfcxResponseDTO.setTotal(org.apache.commons.collections4.CollectionUtils.size(fwList));
            zfcxResponseDTO.setRecords(org.apache.commons.collections4.CollectionUtils.size(fwList));
            zfcxResponseDTO.setPage(1);
            zfcxResponseDTO.setQqsj(DateUtils.getCurrentTimeStr());
            zfcxResponseDTO.setQtime(System.currentTimeMillis() - startTime);
            zfcxResponseDTO.setCxbh(UUID.randomUUID());
            zfcxResponseDTO.setSize(org.apache.commons.collections4.CollectionUtils.size(fwList));
        } catch (Exception e) {
            zfcxResponseDTO.setSuccess(false);
            zfcxResponseDTO.getData().setIsSuccessful("查询失败");
            LOGGER.error("自助查询机有房无房查询异常", e);
        }
        return zfcxResponseDTO;
    }


    /**
     * 标准版自助查询机 有房无房查询生成pdf
     *
     * @param areacode
     * @param zfcxRequestDTO
     * @return cn.gtmap.realestate.exchange.core.dto.nantong.zfcx.response.ZfcxResponseDTO
     * @author wangyinghao
     */
    @OpenApi(apiDescription = "标准版自助查询机有房无房查询生成pd", apiName = "", openLog = false)
    @PostMapping(value = {"/searchFc/isExitHouse/pdf/{areacode}"})
    @DsfLog(logEventName = "标准版自助查询机有房无房查询生成pdf", dsfFlag = "ZZCXJ", requester = "ZZCXJ", responser = "BDC")
    @DsfDaCheckLog(logEventName = "标准版自助查询机有房无房查询生成pd", dsfFlag = "ZZCXJ", requester = "ZZCXJ", responser = "BDC", interfaceFlagCode = "05", checkFlagIndex = 1, checkFlagClassName = "cn.gtmap.realestate.exchange.core.dto.zzcxj.zfcx.request.ZfcxRequestDTO", checkFlagLever = "0",interfaceUrl = "/realestate-exchange/rest/v1.0/searchFc/isExitHouse/{areacode}")
    Object zfcxPdf(@PathVariable(name = "areacode") String areacode,
                @RequestBody ZfcxRequestDTO zfcxRequestDTO) {
        ZfcxResponseDTO zfcxResponseDTO = new ZfcxResponseDTO();
        ZfcxResponseData zfcxResponseData = new ZfcxResponseData();
        zfcxResponseDTO.setData(zfcxResponseData);
        //生成查询人的有房无房证明pdf文件
        if(StrUtil.isNotBlank(zfcxRequestDTO.getClientusername())
                && StrUtil.isNotBlank(zfcxRequestDTO.getClientusercid())
                    && yfwfzmscpdf
        ) {
            try {
                String fileContent = generatrYfwfPdf(zfcxRequestDTO.getClientusername(),
                        zfcxRequestDTO.getClientusercid());
                if(StrUtil.isNotEmpty(fileContent)) {
                    zfcxResponseDTO.getData().setYfwfzmpdf(fileContent);
                }
                zfcxResponseDTO.setSuccess(true);
            }catch (Exception e){
                LOGGER.error("自助查询机有房无房查询生成PDF异常", e);
                zfcxResponseDTO.setSuccess(false);
                zfcxResponseDTO.getData().setIsSuccessful("查询失败");
                LOGGER.error("自助查询机有房无房查询异常", e);
            }
        }
        return zfcxResponseDTO;
    }

    /**
     * @param fwList 房屋信息集合
     * @return {List} 去重后房屋信息集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 去重住房信息（防止权利人共有情况下多个权利人查询多条记录重复）
     */
    private List<ZfcxResponseFw> distinctZfxx(List<ZfcxResponseFw> fwList) {
        if (CollectionUtils.isNotEmpty(fwList) && fwList.size() > 1) {
            // 顺序号先设置空，避免影响去重判断
            for (ZfcxResponseFw zfcxResponseFw : fwList) {
                if (null != zfcxResponseFw) {
                    zfcxResponseFw.setXh(null);
                }
            }

            // 去重处理
            fwList = (List<ZfcxResponseFw>) BeansResolveUtils.deDuplicateCollection(fwList);

            // 加上顺序号
            int index = 1;
            for (ZfcxResponseFw zfcxResponseFw : fwList) {
                if (null != zfcxResponseFw) {
                    zfcxResponseFw.setXh(String.valueOf(index++));
                }
            }
        }
        return fwList;
    }

    /**
      *  查询限制状态返回
      * @param fwList
      * @Date 2021/5/14
      * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
         */
    public void queryXzzt(List<ZfcxResponseFw> fwList) {
        for (ZfcxResponseFw zfcxResponseFw : fwList) {
            String bdcdyh = zfcxResponseFw.getBdcdyh();
            //如果查的是合同可能没有单元号信息
            if(StringUtils.isBlank(bdcdyh)){
                continue;
            }
            List<Integer> qsztList = new ArrayList<>();
            qsztList.add(Constants.QSZT_XS);
            //开始查询限制状态
            //查询抵押
            List<BdcQl> dyList = djbxxFeignService.listBdcQlxx(bdcdyh, CommonConstantUtils.QLLX_DYAQ_DM.toString(), qsztList);
            //查询查封
            List<BdcQl> cfList = djbxxFeignService.listBdcQlxx(bdcdyh, Constants.CF_QLLX_DM, qsztList);
            //查询预告
            List<BdcQl> ygList = djbxxFeignService.listBdcQlxx(bdcdyh, Constants.YG_QLLX_DM, qsztList);
            //查询异议
            List<BdcQl> yyList = djbxxFeignService.listBdcQlxx(bdcdyh, Constants.YY_QLLX_DM, qsztList);
            //查询居住权
            List<BdcQl> jzqList = djbxxFeignService.listBdcQlxx(bdcdyh, Constants.JZQ_QLLX_DM, qsztList);
            zfcxResponseFw.setDyzt(CollectionUtils.isEmpty(dyList) ? "0" : "1");
            zfcxResponseFw.setCfzt(CollectionUtils.isEmpty(cfList) ? "0" : "1");
            zfcxResponseFw.setYgzt(CollectionUtils.isEmpty(ygList) ? "0" : "1");
            zfcxResponseFw.setYyzt(CollectionUtils.isEmpty(yyList) ? "0" : "1");
            zfcxResponseFw.setJzqzt(CollectionUtils.isEmpty(jzqList) ? "0" : "1");
        }
    }

    /**
     * 生成有房无房pdf
     *
     * @param qlrmc
     * @param qlrzjh
     * @return
     */
    public String generatrYfwfPdf(String qlrmc,String qlrzjh){
        // 生成有房无房证明xml数据
        BdcFczmDTO bdcFczmDTO = new BdcFczmDTO();
        bdcFczmDTO.setCxsqr(qlrmc);
        bdcFczmDTO.setSfzh(qlrzjh);
        String xmlData = this.bdcNtFczmFeignService.generateYfwfzmXml(bdcFczmDTO);
        // 判断生成有房证明还是无房证明
        String dylx = this.getYfwfzmType(qlrmc, qlrzjh);

        // 生成PDF文件
        OfficeExportDTO pdfExportDTO = new OfficeExportDTO();
        pdfExportDTO.setModelName(printPath + dylx + CommonConstantUtils.WJHZ_DOCX);
        String filename = qlrmc + "有房无房证明";
        pdfExportDTO.setFileName(filename);
        pdfExportDTO.setXmlData(xmlData);
        //签章
        String fileContent = "";
        try {
            String pdfFilePath = pdfController.generatePdfFile(pdfExportDTO);
            fileContent = dzqzGmxaService.getKeySignDzqzGmxa(filename, pdfFilePath);
            LOGGER.info("权属状态pdf文件签章{}", JSON.toJSONString(fileContent));
        } catch (Exception e) {
            throw new AppException("pdf文件签章失败，错误信息为：" + e.getMessage());
        }
        return fileContent;
    }

    /**
     * 判断打印的有房无房证明类型
     */
    private String getYfwfzmType(String qlrmc, String qlrzjh){
        List<BdcQlrQO> qlrxxList = new ArrayList<>(2);
        BdcQlrQO qlrxx = new BdcQlrQO();
        qlrxx.setQlrmc(qlrmc);
        qlrxx.setZjh(qlrzjh);
        qlrxxList.add(qlrxx);
        BdcZfxxQO bdcZfxxQO = new BdcZfxxQO();
        bdcZfxxQO.setQlrxx(qlrxxList);
        bdcZfxxQO.setCxly("1");
        List<BdcZfxxDTO> zfxxDTOList = bdcZfxxCxFeignService.listBdcNtZfxxDTO(bdcZfxxQO);
        if (CollectionUtils.isNotEmpty(zfxxDTOList)) {
            return "yfzm";
        }
        return "wfzm";
    }


    /**
     * @param areacode
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自助查询机 房屋权属查询(以权利为主)
     */
    @OpenApi(apiDescription = "标准版自助查询机房屋权属查询(以权利为主)", apiName = "", openLog = false)
    @PostMapping("/searchFc/houseStatusByQl/{areacode}")
    @DsfLog(logEventName = "标准版自助查询机房屋权属查询(以权利为主)", dsfFlag = "ZZCXJ", requester = "ZZCXJ", responser = "BDC")
    FwqlCxResponseDTO houseStatusByQl(@PathVariable(name = "areacode") String areacode,
                                      @RequestBody FwqlCxRequestDTO fwqlCxRequestDTO) {
        long startTime = System.currentTimeMillis();
        FwqlCxResponseDTO fwqlCxResponseDTO = new FwqlCxResponseDTO();
        String cdsj = fwqlCxResponseDTO.getQqsj();
        fwqlCxResponseDTO.setData(new FwqlCxResponseData());
        List<FwqlCxRequestQlr> qlrList = fwqlCxRequestDTO.getQlrlist();
        if (CollectionUtils.isEmpty(qlrList)) {
            fwqlCxResponseDTO.getData().setIsSuccessful("查询失败");
            fwqlCxResponseDTO.setMessage("未找到查询参数");
            return fwqlCxResponseDTO;
        }

        // 使用权利人姓名查询权利人 如果存在 证件号为空 或证件号 不足15位 18位 则返回异常
        if (validQlrzjh2 && zfcxService.checkBdcQlrHasZjhExBycqzh(qlrList)) {
            fwqlCxResponseDTO.getData().setIsSuccessful("查询失败");
            fwqlCxResponseDTO.setMessage("存在相同权利人名称但是证件号为空或不规范的情况，请去人工窗口查询");
            return fwqlCxResponseDTO;
        }

        try {
            List<FwqlCxResponseQl> fwqlCxResponseQlList = zfcxService.queryFwQlListByRequest(fwqlCxRequestDTO, cdsj, areacode);
            fwqlCxResponseDTO.getData().setQllist(fwqlCxResponseQlList);
            fwqlCxResponseDTO.getData().setIsSuccessful("查询成功");
            fwqlCxResponseDTO.setTotal(org.apache.commons.collections4.CollectionUtils.size(fwqlCxResponseQlList));
            Map<String, Object> ywbhMap;
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(fwqlCxRequestDTO));
            jsonObject.put("cztype", LogCzxxConstants.CZTYPE_CJZM);
            ywbhMap = zfcxService.fwqsWebServiceQuery(
                    JSONObject.toJSONString(fwqlCxResponseDTO.getData()), jsonObject.toString());
            if (MapUtils.isNotEmpty(ywbhMap)) {
                String ywbh = CommonUtil.ternaryOperator(ywbhMap.get("cxbh"));
                fwqlCxResponseDTO.getData().setYWBH(ywbh);
                fwqlCxResponseDTO.setSuccess(true);
            }
            fwqlCxResponseDTO.setTotal(org.apache.commons.collections4.CollectionUtils.size(fwqlCxResponseQlList));
            fwqlCxResponseDTO.setRecords(org.apache.commons.collections4.CollectionUtils.size(fwqlCxResponseQlList));
            fwqlCxResponseDTO.setPage(1);
            fwqlCxResponseDTO.setQqsj(DateUtils.getCurrentTimeStr());
            fwqlCxResponseDTO.setQtime(System.currentTimeMillis() - startTime);
            fwqlCxResponseDTO.setCxbh(UUID.randomUUID());
            fwqlCxResponseDTO.setSize(org.apache.commons.collections4.CollectionUtils.size(fwqlCxResponseQlList));
        } catch (Exception e) {
            fwqlCxResponseDTO.setSuccess(false);
            fwqlCxResponseDTO.getData().setIsSuccessful("查询失败");
            LOGGER.error("自助查询机房屋权属查询(以权利为主)查询异常", e);
        }
        LOGGER.info("房屋权属查询返回结果:{}", JSONObject.toJSONString(fwqlCxResponseDTO));
        return fwqlCxResponseDTO;
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自助查询机 房屋权属查询(以权利为主)
     */
    @OpenApi(apiDescription = "标准版自助查询机房屋权属查询(以权利为主)-查询全区域", apiName = "", openLog = false)
    @PostMapping("/fwqscx")
    @DsfLog(logEventName = "标准版自助查询机房屋权属查询(以权利为主)", dsfFlag = "ZZCXJ", requester = "ZZCXJ", responser = "BDC")
    DbxxCxResponseDTO houseStatus(@RequestBody FwqlCxRequestDTO fwqlCxRequestDTO){
        return houseStatus(fwqlCxRequestDTO.getSelarea(),fwqlCxRequestDTO);
    }

    /**
     * @param areacode
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 自助查询机 房屋权属查询(以权利为主)
     */
    @OpenApi(apiDescription = "标准版自助查询机房屋权属查询(以权利为主)", apiName = "", openLog = false)
    @PostMapping("/fwqscx/{qxdm}")
    @DsfLog(logEventName = "标准版自助查询机房屋权属查询(以权利为主)", dsfFlag = "ZZCXJ", requester = "ZZCXJ", responser = "BDC")
    DbxxCxResponseDTO houseStatus(@PathVariable(name = "qxdm") String areacode,
                                  @RequestBody FwqlCxRequestDTO fwqlCxRequestDTO) {
        long startTime = System.currentTimeMillis();
        fwqlCxRequestDTO.setCxdz(true);
        FwqlCxResponseDTO fwqlCxResponseDTO = houseStatusByQl(areacode, fwqlCxRequestDTO);
        if(Objects.nonNull(fwqlCxResponseDTO)){
            //拷贝返回值
            DbxxCxResponseDTO dbxxCxResponse = new DbxxCxResponseDTO();
            BeanUtils.copyProperties(fwqlCxResponseDTO,dbxxCxResponse);
            DbxxCxResponseData dbxxCxResponseData = new DbxxCxResponseData();
            FwqlCxResponseData data = fwqlCxResponseDTO.getData();
            BeanUtils.copyProperties(data,dbxxCxResponseData);
            dbxxCxResponse.setData(dbxxCxResponseData);
            if(CollectionUtils.isNotEmpty(data.getQllist())){
                List<DbxxCxResponseQl> fwlist = new ArrayList<>();
                for (FwqlCxResponseQl fwqlCxResponseQl : data.getQllist()) {
                    DbxxCxResponseQl dbxxCxResponseQl = JSON.parseObject(JSON.toJSONString(fwqlCxResponseQl),DbxxCxResponseQl.class);
                    //处理不相同的字段
                    dbxxCxResponseQl.setSfzh(fwqlCxResponseQl.getQlrzjh());
                    if (CollectionUtils.isNotEmpty(fwqlCxResponseQl.getFwxxlist())) {
                        FwqlCxResponseFwxx fwqlCxResponseFwxx = fwqlCxResponseQl.getFwxxlist().get(0);
                        dbxxCxResponseQl.setJzmj(fwqlCxResponseFwxx.getJzmj());
                        dbxxCxResponseQl.setFwjg(fwqlCxResponseFwxx.getFwjg());
                        dbxxCxResponseQl.setFtmj(fwqlCxResponseFwxx.getFttdmj());
                        dbxxCxResponseQl.setDymj(fwqlCxResponseFwxx.getDytdmj());
                        dbxxCxResponseQl.setFwfzxxlist(JSON.parseArray(JSON.toJSONString(fwqlCxResponseQl.getFwxxlist()), DbxxCxResponseFwxx.class));
                    }
                    fwlist.add(dbxxCxResponseQl);
                }
                dbxxCxResponseData.setFwlist(fwlist);
            }
            fwqlCxResponseDTO.setTotal(org.apache.commons.collections4.CollectionUtils.size(data.getQllist()));
            fwqlCxResponseDTO.setRecords(org.apache.commons.collections4.CollectionUtils.size(data.getQllist()));
            fwqlCxResponseDTO.setPage(1);
            fwqlCxResponseDTO.setQqsj(DateUtils.getCurrentTimeStr());
            fwqlCxResponseDTO.setQtime(System.currentTimeMillis() - startTime);
            fwqlCxResponseDTO.setCxbh(UUID.randomUUID());
            fwqlCxResponseDTO.setSize(org.apache.commons.collections4.CollectionUtils.size(data.getQllist()));
            return dbxxCxResponse;
        }
        return null;
    }


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQlrQOList 权利人信息
     * @return WwsqResponse 房产信息
     * @description 查询权利人房产证明并生成PDF
     */
    @PostMapping("/fczm/pdf")
    @DsfLog(logEventName = "查询权利人房产证明并生成PDF",dsfFlag = "WWSQ",requester = "WWSQ",responser = "BDC")
    @ApiOperation("查询权利人房产证明并生成PDF")
    @ApiImplicitParam(name = "bdcQlrQOList", value = "权利人信息", required = true, paramType = "body")
    public WwsqResponse getZfxxPdf(@RequestBody List<BdcQlrQO> bdcQlrQOList) {
        if(CollectionUtils.isEmpty(bdcQlrQOList)) {
            return WwsqResponse.newResponse(Constants.CODE_SEARCH_ERROR, "缺失权利人名称、证件号参数", null);
        }

        for(BdcQlrQO bdcQlrQO : bdcQlrQOList) {
            if (null == bdcQlrQO || StringUtils.isAnyBlank(bdcQlrQO.getQlrmc(), bdcQlrQO.getZjh())) {
                return WwsqResponse.newResponse(Constants.CODE_SEARCH_ERROR, "缺失权利人名称、证件号参数", null);
            }
        }

        try {
            List<BdcFczmPdfDTO> fczmPdf = bdcBzbZmFeignService.getFczmPdf(bdcQlrQOList);
            return WwsqResponse.newResponse(Constants.CODE_SUCCESS, "成功", fczmPdf);
        } catch (Exception e) {
            LOGGER.error("生成房产证明PDF异常：", e);
            return WwsqResponse.newResponse(Constants.CODE_ERROR, "生成房产证明PDF异常", null);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcQszmQOList 权属证明参数
     * @return WwsqResponse 权属证明
     * @description 查询指定单元权属证明并生成PDF
     */
    @PostMapping("/dyqszm/pdf")
    @DsfLog(logEventName = "查询指定单元权属证明并生成PDF",dsfFlag = "WWSQ",requester = "WWSQ",responser = "BDC")
    @ApiOperation("查询指定单元权属证明并生成PDF")
    @ApiImplicitParam(name = "bdcQszmQOList", value = "权属证明参数", required = true, paramType = "body")
    public WwsqResponse getQszmPdf(@RequestBody List<BdcQszmQO> bdcQszmQOList) {
        if(CollectionUtils.isEmpty(bdcQszmQOList)) {
            return WwsqResponse.newResponse(Constants.CODE_SEARCH_ERROR, "缺失单元号参数", null);
        }

        for(BdcQszmQO bdcQszmQO : bdcQszmQOList) {
            if (null == bdcQszmQO || StringUtils.isAnyBlank(bdcQszmQO.getBdcdyh())) {
                return WwsqResponse.newResponse(Constants.CODE_SEARCH_ERROR, "缺失单元号参数", null);
            }

            if (null == bdcQszmQO.getBdclx()) {
                return WwsqResponse.newResponse(Constants.CODE_SEARCH_ERROR, "缺失bdclx参数", null);
            }
        }

        try {
            List<BdcQszmPdfDTO> fczmPdf = bdcBzbZmFeignService.getQszmPdf(bdcQszmQOList);
            return WwsqResponse.newResponse(Constants.CODE_SUCCESS, "成功", fczmPdf);
        } catch (Exception e) {
            LOGGER.error("生成房产证明PDF异常：", e);
            return WwsqResponse.newResponse(Constants.CODE_ERROR, "生成权属证明PDF异常", null);
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcShxxQOList 审核信息参数
     * @return WwsqResponse 审批表信息
     * @description 获取打印审批表PDF
     */
    @PostMapping("/bdcspb/pdf")
    @DsfLog(logEventName = "获取打印审批表PDF",dsfFlag = "WWSQ",requester = "WWSQ",responser = "BDC")
    @ApiOperation("获取打印审批表PDF")
    @ApiImplicitParam(name = "bdcShxxQOList", value = "审核信息参数", required = true, paramType = "body")
    public WwsqResponse getBdcSpbPdf(@RequestBody List<BdcShxxQO> bdcShxxQOList) {
        if(CollectionUtils.isEmpty(bdcShxxQOList)) {
            return WwsqResponse.newResponse(Constants.CODE_SEARCH_ERROR, "缺失受理编号参数", null);
        }

        for(BdcShxxQO bdcShxxQO : bdcShxxQOList) {
            if (null == bdcShxxQO || StringUtils.isBlank(bdcShxxQO.getSlbh())) {
                return WwsqResponse.newResponse(Constants.CODE_SEARCH_ERROR, "缺失受理编号参数", null);
            }
        }

        try {
            List<BdcShxxPdfDTO> shxxPdf = bdcShxxFeignService.getBdcSpbPdf(bdcShxxQOList);
            return WwsqResponse.newResponse(Constants.CODE_SUCCESS, "成功", shxxPdf);
        } catch (Exception e) {
            LOGGER.error("获取打印审批表PDF异常：", e);
            return WwsqResponse.newResponse(Constants.CODE_ERROR, "获取打印审批表PDF异常", null);
        }
    }


    /**
     * @description 房屋信息查询
     * @param bdcFwxxQOList
     * @return: java.util.Map
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/11/15 11:44
     */
    @PostMapping("/fwxxcx")
    @DsfLog(logEventName = "房屋信息查询",dsfFlag = "WWSQ",requester = "WWSQ",responser = "BDC")
    @ApiOperation("房屋信息查询")
    @ApiImplicitParam(name = "bdcFwxxQOList", value = "房屋信息参数", required = true, paramType = "body")
    public Map fwxxcx(@RequestBody List<BdcFwxxQO> bdcFwxxQOList) {
        Map map = new HashMap(7);
        //验证参数
        if (CollectionUtils.isEmpty(bdcFwxxQOList)) {
            map.put(Constants.CODE,Constants.FAIL_CODE_1);
            map.put(Constants.MSG,"缺失权利人名称、证件号参数");
            return map;
        }
        for (BdcFwxxQO bdcFwxxQO : bdcFwxxQOList) {
            if (null == bdcFwxxQO || StringUtils.isAnyBlank(bdcFwxxQO.getQlrmc(), bdcFwxxQO.getQlrzjhm())) {
                map.put(Constants.CODE,Constants.FAIL_CODE_1);
                map.put(Constants.MSG,"缺失权利人名称、证件号参数");
                return map;
            }
        }
        try {
            //查询编号
            Integer lsh = bdcBhFeignService.queryLsh("fwxxcx", "DAY");
            String cxbh = DateUtils.date2LocalDateTime(new Date()).format(DATE_FORMATTER) + StringToolUtils.appendZero(String.valueOf(lsh), 4);
            //查询房屋信息
            List<FwlbResponse> returnList = zfcxService.getFwxxcx(bdcFwxxQOList);
            if(CollectionUtils.isEmpty(returnList)){
                returnList = new ArrayList<>(1);
            }
            //返回data
            Map datamap = new HashMap(2);
            datamap.put(Constants.CXBH,cxbh);
            datamap.put(Constants.FWLB,returnList);

            map.put(Constants.CODE,Constants.SUCCESS_CODE_0);
            map.put(Constants.MSG,"查询成功");
            map.put(Constants.KEY_DATA,datamap);
            return map;
        } catch (Exception e) {
            LOGGER.error("查询房屋信息异常", e);
            map.put(Constants.CODE,Constants.FAIL_CODE_1);
            map.put(Constants.MSG,"查询房屋信息异常");
            return map;
        }
    }
}
