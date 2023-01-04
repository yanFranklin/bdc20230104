package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJyxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.dto.accept.FcjyClfHtxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSjclDzzzFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJyxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.ShuChengFcjyViewFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

/**
 * @program: realestate
 * @description: 受理交易信息查询获取
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-01-18 17:00
 **/

@RestController
@RequestMapping("/slym/jyxx")
public class BdcSlJyxxController extends BaseController {

    @Autowired
    ShuChengFcjyViewFeignService shuChengFcjyViewFeignService;
    @Autowired
    BdcSlJyxxFeignService bdcSlJyxxFeignService;
    @Autowired
    SlymQlrController slymQlrController;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    SlymXmController slymXmController;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;
    @Autowired
    BdcSjclDzzzFeignService bdcSjclDzzzFeignService;
    @Value("${jydzht.clmcdz:}")
    private String dzhtClmc;


    /**
     * @param htbh， xmid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 舒城查询交易信息
     * @date : 2022/1/18 17:01
     */

    @GetMapping("/shucheng")
    public void queryShuchengJyxx(String htbh, String xmid, String gzlslid, String fwlx) throws Exception {
        if (StringUtils.isBlank(htbh)) {
            throw new AppException("查询交易信息合同编号不可为空");
        }
        String beanName ="";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("htbh",htbh);
        if (CommonConstantUtils.FCJY_TYPE_SPF.equals(fwlx)){
            beanName = "fgj_GetNewContract";
        }
        if (CommonConstantUtils.FCJY_TYPE_CLF.equals(fwlx)){
            beanName = "fgj_GetContract";
        }
        Object response = exchangeInterfaceFeignService.requestInterface(beanName, paramMap);
        LOGGER.info("当前合同编号{},查询舒城交易信息数据{}", htbh, JSON.toJSONString(paramMap));
        if (Objects.nonNull(response)) {
            LOGGER.info("合同编号:{},查询舒城合同信息接口调用成功，响应内容：{}", htbh,response);
            FcjyClfHtxxDTO fcjyClfHtxx = JSONObject.parseObject(JSON.toJSONString(response), FcjyClfHtxxDTO.class);
            if (Objects.nonNull(fcjyClfHtxx) && ("0".equals(fcjyClfHtxx.getCode())||"200".equals(fcjyClfHtxx.getCode()))) {
                // 用xmid 查当前jyxx
                List<BdcSlJyxxDO> bdcSlJyxxDOList = bdcSlJyxxFeignService.listBdcSlJyxxByXmid(xmid);
                if (CollectionUtils.isNotEmpty(bdcSlJyxxDOList)) {
                    //删除原有信息，插入新的交易信息数据
                    bdcSlJyxxFeignService.deleteBdcSlJyxxByXmid(xmid);
                }
                BdcSlJyxxDO bdcSlJyxxDO = fcjyClfHtxx.getBdcSlJyxx();
                bdcSlJyxxDO.setXmid(xmid);
                bdcSlJyxxFeignService.insertBdcSlJyxx(bdcSlJyxxDO);
                //更新项目表 的jyhth
                BdcXmQO bdcXmQO = new BdcXmQO(xmid);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    BdcXmDO bdcXmDO = bdcXmDOList.get(0);
                    bdcXmDO.setJyhth(bdcSlJyxxDO.getHtbh());
                    slymXmController.updateBdcXm(JSON.toJSONString(bdcXmDO), gzlslid, xmid, bdcXmDO.getDjxl());
                    //更新权利表jyjg
                    BdcQl bdcQl = bdcQllxFeignService.queryQlxx(xmid);
                    if (Objects.nonNull(bdcQl) && bdcQl instanceof BdcFdcqDO) {
                        BdcFdcqDO bdcFdcqDO = (BdcFdcqDO) bdcQl;
                        bdcFdcqDO.setJyjg(bdcSlJyxxDO.getJyje());
                        bdcQllxFeignService.updateFdcq(bdcFdcqDO);
                    }
                }
                // 处理权利人信息-新增权利人，根据流程类型，分别调用组合的，批量，批量组合处理接口
                int lclx = bdcXmFeignService.makeSureBdcXmLx(gzlslid);
                List<BdcQlrDO> bdcQlr = fcjyClfHtxx.getBdcQlr();
                if (CollectionUtils.isNotEmpty(bdcQlr)) {
                    //xmid 查当前权利人顺序号的最大值
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setXmid(xmid);
                    bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                    List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    int sxh = 0;
                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                        //权利人根据sxh 排序了，取最后一个
                        sxh = bdcQlrDOList.get(CollectionUtils.size(bdcQlrDOList) - 1).getSxh();
                    }
                    for (BdcQlrDO bdcQlrDO : bdcQlr) {
                        bdcQlrDO.setXmid(xmid);
                        bdcQlrDO.setQlrlx(CommonConstantUtils.QLRLX_GR);
                        bdcQlrDO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                        bdcQlrDO.setSxh(++sxh);
                        // 对权利人信息做去空格操作
                        if(StringUtils.isNotBlank(bdcQlrDO.getQlrmc())){
                            bdcQlrDO.setQlrmc(StringUtils.deleteWhitespace(bdcQlrDO.getQlrmc()));
                        }
                        if(StringUtils.isNotBlank(bdcQlrDO.getZjh())){
                            bdcQlrDO.setZjh(StringUtils.deleteWhitespace(bdcQlrDO.getZjh()));
                        }
                        switch (lclx) {
                            case 1:
                            case 2:
                                //组合流程，单个流程相同接口
                                slymQlrController.insertZhBdcQlr(JSON.toJSONString(bdcQlrDO), gzlslid);
                                break;
                            case 3:
                                //批量流程
                                slymQlrController.insertPlBdcQlr(bdcQlrDO, gzlslid);
                                break;
                            case 4:
                                //批量组合新增
                                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                                    slymQlrController.insertPlzhBdcQlr(JSON.toJSONString(bdcQlrDO), gzlslid, bdcXmDOList.get(0).getDjxl());
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     * @param htbh    合同编号
     * @param gzlslid 流程实例id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 获取电子合同并上传附件
     * @date : 2022/4/13 9:50
     */
    @GetMapping("/dzht")
    public void queryJydzht(String htbh, String gzlslid, String type) throws IOException {
        if (StringUtils.isNotBlank(htbh) && StringUtils.isNotBlank(gzlslid) && StringUtils.isNotBlank(type)) {
            //调用接口获取电子合同信息
            Map paramMap = new HashMap(1);
            paramMap.put("htbh", htbh);
            //判断存量房还是商品房
            paramMap.put("type", type);
            LOGGER.warn("获取交易电子合同，合同编号{}，type{}", htbh, type);
            Object response = exchangeInterfaceFeignService.requestInterface("fgj_getContractImage", paramMap);
            if (Objects.nonNull(response)) {
                Map map = (Map) response;
                LOGGER.warn("合同编号{},获取电子交易合同结果code={}msg={}", htbh, map.get("code"), map.get("msg"));
                if (CommonConstantUtils.STATUS_SUCCESS.equals(MapUtils.getString(map, "code", ""))) {
                    //code=0 是获取成功
                    //获取附件信息
                    JSONArray fjArray = JSONObject.parseArray(JSON.toJSONString(map.get("data")));
                    if (CollectionUtils.isNotEmpty(fjArray)) {
                        for (Object fjcl : fjArray) {
                            Map fjclMap = (Map) fjcl;
                            //根据type查询对照表的文件夹名称
                            String fjtype = MapUtils.getString(fjclMap, "type", "1");
                            String wjjmc = "交易电子合同";
                            if (StringUtils.isNotBlank(dzhtClmc)) {
                                Map dzhtClmcMap = JSON.parseObject(dzhtClmc, Map.class);
                                wjjmc = MapUtils.getString(dzhtClmcMap, fjtype, "交易电子合同");
                            }
                            bdcUploadFileUtils.uploadBase64File(CommonConstantUtils.BASE64_QZ_PDF + fjclMap.get("file"), gzlslid, wjjmc, "电子合同" + UUIDGenerator.generate16(), CommonConstantUtils.WJHZ_PDF);
                        }
                    }
                }
            }
        }
    }
}
