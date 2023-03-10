package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlWjscDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.yhcx.request.DianYhcxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.yhcx.response.HefeiDianYhcxResponseDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.request.HefeiShuifeicxRequestDTO;
import cn.gtmap.realestate.common.core.dto.exchange.sdqgh.shui.shuifeicx.response.HefeiShuifeicxResponseDTO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztRequestDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztUpdateHyDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqxxDTO;
import cn.gtmap.realestate.common.core.enums.BdcSdqEnum;
import cn.gtmap.realestate.common.core.enums.BdcSdqRqfwbsmEnum;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlJbxxFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSjclFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlSqrFeignService;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.exchange.*;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcSdqghFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcPrintFeignService;
import cn.gtmap.realestate.common.core.service.rest.accept.BdcSlPrintRestService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 2019/11/21
 * @description ???????????????controller
 */
@Controller
@RequestMapping("/sdqgh")
public class BdcSdqghController extends BaseController {
    @Autowired
    private BdcSdqghFeignService bdcSdqghFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    private HefeiSdqFeignService hefeiSdqFeignService;
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    private BdcSlSjclFeignService bdcSlSjclFeignService;
    @Value("${url.storageUrl:}")
    protected String storageUrl;
    @Autowired
    BdcPrintFeignService bdcPrintFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    BdcSlPrintRestService bdcSlPrintRestService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;
    @Autowired
    private ShuChengShuiFeignService shuChengWaterFeignService;

    @Autowired
    NantongShuiFeignService nantongShuiFeignService;

    @Autowired
    DianFeignService dianFeignService;

    @Autowired
    NantongQiFeignService nantongQiFeignService;

    /**
     * @return ??? ??????????????????????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @ResponseBody
    @GetMapping("/shui")
    public Object getShuiZt(@RequestParam("consNo") String consNo,
                            @RequestParam("processInsId") String gzlslid,
                            @RequestParam(value = "rqlx",required = false) String dsffwbs

    ) {
        HefeiShuifeicxRequestDTO dto = new HefeiShuifeicxRequestDTO();
        dto.setYhdm(consNo);
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        // ????????????
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            dto.setSlbh(bdcXmDTOList.get(0).getSlbh());
            dto.setXmid(bdcXmDTOList.get(0).getXmid());
            dto.setGzlslid(bdcXmDTOList.get(0).getGzlslid());
        }
        dto.setDsffwbs(dsffwbs);
        HefeiShuifeicxResponseDTO resDto = hefeiSdqFeignService.shuifeiCx(dto);
//        HefeiShuifeicxResponseDTO resDto = new HefeiShuifeicxResponseDTO();
//        resDto.setXym("004");
        return resDto;
    }

    /**
     * @return ??? ??????????????????????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @ResponseBody
    @GetMapping("/dian")
    public Object getDianZt(@RequestParam("consNo") String consNo,
                            @RequestParam("processInsId") String gzlslid) {
        DianYhcxRequestDTO dto = new DianYhcxRequestDTO();
        dto.setConsNo(consNo);
        List<BdcXmDTO> bdcXmDTOList =bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
        // ????????????
        if (CollectionUtils.isNotEmpty(bdcXmDTOList)) {
            dto.setSlbh(bdcXmDTOList.get(0).getSlbh());
            dto.setXmid(bdcXmDTOList.get(0).getXmid());
        }
        HefeiDianYhcxResponseDTO resDto = hefeiSdqFeignService.dianGhcx(dto);
        // ?????????????????????????????????????????????
        if("0000".equals(resDto.getResultCode())){
            BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
            bdcSdqywQO.setGzlslid(gzlslid);
            bdcSdqywQO.setConsno(consNo);
            bdcSdqywQO.setYwlx(2);
            List<BdcSdqghDO> bdcSdqghDOS = bdcSdqghFeignService.querySdqyw(bdcSdqywQO);
            if (CollectionUtils.isNotEmpty(bdcSdqghDOS)){
                BdcSdqghDO bdcSdqghDO = bdcSdqghDOS.get(0);
                BdcSdqBlztUpdateHyDTO bdcSdqBlztUpdateHyDTO = new BdcSdqBlztUpdateHyDTO();
                bdcSdqBlztUpdateHyDTO.setId(bdcSdqghDO.getId());
                bdcSdqBlztUpdateHyDTO.setHyjg(1);
                bdcSdqghFeignService.updateSdqghhy(bdcSdqBlztUpdateHyDTO);
            }
        }
        return resDto;
    }

    /**
     * @return ??? ??????????????????????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @ResponseBody
    @GetMapping("/qi")
    public Object getQiZt(@RequestParam("consNo") String consNo,
                          @RequestParam("processInsId") String gzlslid,
                          @RequestParam("rqlx") String rqlx
    ) {
        if (StringUtils.isAnyBlank(consNo, gzlslid, rqlx)) {
            throw new MissingArgumentException("consNo, gzlslid, rqlx");
        }
        BdcSdqywQO bdcSdqywQO = new BdcSdqywQO();
        bdcSdqywQO.setConsno(consNo);
        bdcSdqywQO.setGzlslid(gzlslid);
        bdcSdqywQO.setRqlx(rqlx);
        return bdcSdqghFeignService.getQiZt(bdcSdqywQO);
    }


    /**
     * ???????????????????????????3.0????????????
     *
     * @param bdcSdqywQO ???????????????????????????????????????QO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveData")
    public Object saveData(@RequestBody BdcSdqywQO bdcSdqywQO) {
        return bdcSdqghFeignService.saveData(bdcSdqywQO);
    }


    /**
     * ????????????????????????????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @return
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping("/querySdqyw/{gzlslid}")
    @ResponseBody
    public List<BdcSdqghDO> querySdqyw(@PathVariable("gzlslid") String gzlslid) {
        BdcSdqywQO bdcSdqghQO = new BdcSdqywQO();
        bdcSdqghQO.setGzlslid(gzlslid);
        return bdcSdqghFeignService.querySdqywOrder(bdcSdqghQO);
    }




    /**
     * ????????????????????????????????????????????????
     *
     * @param gzlslid ???????????????ID
     * @return
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping("/getQlrlist/{gzlslid}/{isOneWebSource}")
    @ResponseBody
    public List<BdcQlrDO> getQlrlist(@PathVariable("gzlslid") String gzlslid
            , @PathVariable("isOneWebSource") String isOneWebSource) {
        if ("true".equals(isOneWebSource)) {// ????????????????????????????????????
            List<BdcQlrDO> list = new ArrayList<>();
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            String jbxxid = bdcSlJbxxDO.getJbxxid();
            BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
            bdcSlXmQO.setJbxxid(jbxxid);
            List<BdcSlXmDO> listSlxm = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
            for (int i = 0; i < listSlxm.size(); i++) {
                String xmid = listSlxm.get(i).getXmid();
                List<BdcSlSqrDO> listSqr = bdcSlSqrFeignService.listBdcSlSqrByXmid(xmid, CommonConstantUtils.QLRLB_QLR);
                List<BdcQlrDO> listQlrDo = new ArrayList<>();
                for (int j = 0; j < listSqr.size(); j++) {
                    BdcQlrDO qlrDo = new BdcQlrDO();
                    BdcSlSqrDO slQlrDo = listSqr.get(j);
                    qlrDo.setQlrmc(slQlrDo.getSqrmc());
                    qlrDo.setZjh(slQlrDo.getZjh());
                    qlrDo.setZjzl(slQlrDo.getZjzl());
                    listQlrDo.add(qlrDo);
                    list.addAll(listQlrDo);
                }
            }
            return list;
        } else {// ??????????????????
            List<BdcQlrDO> list = new ArrayList<>();
            List<BdcXmDTO> bdcXmDTOList = bdcXmFeignService.listBdcXmBfxxByGzlslid(gzlslid);
            for (int i = 0; i < bdcXmDTOList.size(); i++) {
                BdcQlrQO bdcQlrQO = new BdcQlrQO();
                bdcQlrQO.setXmid(bdcXmDTOList.get(i).getXmid());
                bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                    list.addAll(bdcQlrDOList);
                }
            }
            return list;
        }

    }




    /**
     * ????????????????????????????????????????????????(??????)
     *
     * @param gzlslid ???????????????ID
     * @param type    ???????????? 1?????? 2?????? 3??????
     * @return ????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping("/tsGh/{gzlslid}/{type}/{isOneWebSource}")
    @ResponseBody
    public boolean tsgh(@PathVariable("gzlslid") String gzlslid,
                        @PathVariable("type") String type,
                        @PathVariable("isOneWebSource") String isOneWebSource) {
        boolean flag = true;
        try {
            if (CommonConstantUtils.SDQYWLX_S.equals(type)) {
                flag = bdcSdqghFeignService.shuigh(gzlslid, isOneWebSource);
            } else if (CommonConstantUtils.SDQYWLX_D.equals(type)) {
                flag = bdcSdqghFeignService.diangh(gzlslid, isOneWebSource);
            } else {
                flag = bdcSdqghFeignService.qigh(gzlslid, isOneWebSource);
            }
            return flag;
        } catch (Exception e) {
            LOGGER.error("????????????", e);
            return false;
        }
    }

    /**
     * ?????????????????????
     *
     * @param processInsId
     * @return
     */
    @ResponseBody
    @GetMapping("/sjcl")
    public Object listSjclPl(String processInsId) {
        List<BdcSlSjclDO> list = bdcSlSjclFeignService.listBdcSlSjclByGzlslid(processInsId);
        List<BdcSlSjclDO> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String clmc = list.get(i).getClmc();
            boolean isSdqCl = isSdqCl(clmc);
            if (isSdqCl) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    /**
     * @param json ????????????????????????Json
     * @return ????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ??????????????????????????????????????????????????????????????????????????????
     */
    @PostMapping(value = "/sjcl/list")
    @ResponseBody
    public Integer updateBdcSlSjcl(@RequestBody String json) {
        return bdcSlSjclFeignService.updateBdcSlSjcl(json);
    }

    /**
     * @param processInsId ???????????????ID
     * @param clmc         ????????????
     * @param sjclxmid     ??????????????????ID
     * @return ??????????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @GetMapping("/bdcSlWjscDTO")
    public Object bdcSlWjscDTO(String processInsId, String sjclxmid, String clmc) {
        BdcSlWjscDTO bdcSlWjscDTO = new BdcSlWjscDTO();
        bdcSlWjscDTO.setToken(queryToken());
        bdcSlWjscDTO.setClientId(CommonConstantUtils.WJZX_CLIENTID);
        if (StringUtils.isNotBlank(clmc)) {
            List<BdcSlSjclDO> bdcSlSjclDOList = bdcSlSjclFeignService.listBdcSlSjclByClmc(processInsId, clmc);
            if (CollectionUtils.isNotEmpty(bdcSlSjclDOList)) {
                bdcSlWjscDTO.setNodeId(bdcSlSjclDOList.get(0).getWjzxid());
            }
        }
        return bdcSlWjscDTO;

    }

    /**
     * @return ????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ??????????????????
     */
    @ResponseBody
    @GetMapping("/url")
    public Object querySlymUrl() {
        BdcUrlDTO bdcSlUrlDTO = new BdcUrlDTO();
        bdcSlUrlDTO.setStorageUrl(storageUrl);

        return bdcSlUrlDTO;
    }

    /**
     * @param gzlslid ???????????????id
     * @param xmid    ??????id
     * @return ??????????????????
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @PatchMapping("/ys")
    public Integer updateBdcSjclYs(String gzlslid, String xmid) {
        return bdcSlSjclFeignService.updateSjclYs(gzlslid, xmid);
    }

    /**
     * ????????????????????????????????????
     *
     * @param clmc
     * @return
     */
    private static boolean isSdqCl(String clmc) {
        if (StringUtils.isEmpty(clmc)) {
            return false;
        }
        if (clmc.indexOf(CommonConstantUtils.CLMC_DIAN) != -1) {
            return true;
        }
        if (clmc.indexOf(CommonConstantUtils.CLMC_SHUI1) != -1) {
            return true;
        }
        if (clmc.indexOf(CommonConstantUtils.CLMC_SHUI2) != -1) {
            return true;
        }
        if (clmc.indexOf(CommonConstantUtils.CLMC_QI) != -1) {
            return true;
        }
        return false;
    }

    @ResponseBody
    @GetMapping("/print/{processInsId}/{isOneWebSource}")
    public Object printSqd(@PathVariable("processInsId") String gzlslid,
                           @PathVariable("isOneWebSource") String isOneWebSource) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new MissingArgumentException("???????????????????????????????????????");
        }
        if ("true".equals(isOneWebSource)) {
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            String jbxxid = bdcSlJbxxDO.getJbxxid();
            BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
            bdcSlXmQO.setJbxxid(jbxxid);
            String xmid = "";
            List<BdcSlXmDO> list = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
            if (!list.isEmpty()) {
                xmid = list.get(0).getXmid();
            }
            return bdcSlPrintRestService.packPrintDatas(gzlslid, "ycsdqsqb", CommonConstantUtils.QLRLB_QLR, xmid, "");

        } else {
            Map<String, List<Map>> paramMap = new HashMap();
            List<Map> maps = new ArrayList();
            Map<String, String> map = new HashMap();
            map.put("gzlslid", gzlslid);
            maps.add(map);
            paramMap.put("sdqsqb", maps);
            return bdcPrintFeignService.print(paramMap);
        }

    }

    /**
     * ?????????????????????????????????????????????
     *
     * @return
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping("/ztcx/{consno}/{gzlslid}")
    @ResponseBody
    public boolean tsgh(@PathVariable("consno") String consno, @PathVariable("gzlslid") String gzlslid) {
        if (StringUtils.isBlank(consno) || StringUtils.isBlank(gzlslid)) {
            throw new AppException("?????????????????????????????????????????????");
        }
        LOGGER.info("????????????????????????????????????{}???{}", consno, gzlslid);
        boolean flag = false;
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> list = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isNotEmpty(list)) {
            Date date = list.get(0).getDjsj();
            if (null != date) {
                String beginTime = DateUtils.formateYmdhms(date);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, 1);
                Date endDate = c.getTime();
                String endTime = DateUtils.formateYmdhms(endDate);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("consNo", consno);
                jsonObject.put("endDate", endTime);
                jsonObject.put("bgnDate", beginTime);
                LOGGER.info("??????????????????????????????exchange???????????????{}", jsonObject);

           /*     JSONObject test = new JSONObject();
                List<Map> listTest = new ArrayList<>();
                Map map = new HashMap();
                map.put("status","0");
                map.put("consNo","555882852");
                listTest.add(map);
                Map map2 = new HashMap();
                map2.put("status","1");
                map2.put("consNo","555882852");
                listTest.add(map2);

                test.put("Data",listTest);
                test.put("timeStamp","1513397438451");
                test.put("resultCode","0000");

                Object obj = test;*/
                Object obj = exchangeInterfaceFeignService.requestInterface("dian_ghqkcx", jsonObject);

                if (null != obj) {
                    JSONObject jsonObjectResult = JSONObject.parseObject(JSONObject.toJSONString(obj));
                    LOGGER.info("?????????????????????jsonObjectResult:{}", jsonObjectResult);
                    if (jsonObjectResult.containsKey("resultCode") && "0000".equals(jsonObjectResult.get("resultCode").toString())) {
                        if (jsonObjectResult.containsKey("Data")) {
                            String js = JSONObject.toJSONString(jsonObjectResult.get("Data"));
                            if (StringUtils.isNotBlank(js)) {
                                JSONArray result = JSON.parseArray(js);
                                if (CollectionUtils.isNotEmpty(result)) {
                                    boolean hasSuccess = false;
                                    for (int i = 0; i < result.size(); i++) {
                                        JSONObject job = result.getJSONObject(i);
                                        if (null != job && job.containsKey("status")) {
                                            String status = job.get("status").toString();
                                            if ("1".equals(status)) {
                                                hasSuccess = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (hasSuccess) {
                                        BdcSdqBlztRequestDTO bdcSdqBlztRequestDTO = new BdcSdqBlztRequestDTO();
                                        bdcSdqBlztRequestDTO.setYwlx(2);
                                        bdcSdqBlztRequestDTO.setConsno(consno);
                                        bdcSdqBlztRequestDTO.setBlzt(3);
                                        bdcSdqghFeignService.updateSdqBlzt(bdcSdqBlztRequestDTO);
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }

            } else {
                throw new AppException("??????gzlslid???????????????????????????gzlslid:" + gzlslid);
            }
        }
        return flag;
    }

    /**
     * @param gzlslid ???????????????ID
     * @param qlrxxsjly ??????????????????????????? ???????????????????????? 1:??????????????????????????? 2??????????????????????????????
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????
     */
    @PostMapping("/dian/tsgh/{gzlslid}")
    @ResponseBody
    public Object commonDiangh(@PathVariable("gzlslid") String gzlslid,@RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,@RequestParam(name = "appendSign", required = false) String appendSign) {
        return bdcSdqghFeignService.commonDiangh(gzlslid,qlrxxsjly,appendSign,0);
    }

    /**
     * @param gzlslid ???????????????ID
     * @param qlrxxsjly ??????????????????????????? ???????????????????????? 1:??????????????????????????? 2??????????????????????????????
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????
     */
    @PostMapping("/qi/tsgh/{gzlslid}")
    @ResponseBody
    public Object commonQigh(@PathVariable("gzlslid") String gzlslid,@RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,@RequestParam(name = "appendSign", required = false) String appendSign) {
        return bdcSdqghFeignService.commonQigh(gzlslid,qlrxxsjly,appendSign,0);
    }

    /**
     * @param gzlslid ???????????????ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ???????????????????????????(??????????????????????????????????????????)
     */
    @PostMapping("/dian/tsgh/bc/{gzlslid}")
    @ResponseBody
    public Object commonDianghBc(@PathVariable("gzlslid") String gzlslid,@RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,@RequestParam(name = "appendSign", required = false) String appendSign) {
        //?????????
        CommonResponse commonResponse =bdcSdqghFeignService.commonDianghCancel(gzlslid);
        if(commonResponse.isSuccess()){
            //????????????+??????
            return bdcSdqghFeignService.commonDianghWorkflow(gzlslid,qlrxxsjly,appendSign);
        }
        return commonResponse;

    }

    /**
     * @param gzlslid ???????????????ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????
     */
    @PostMapping("/dian/tsgh/cancel/{gzlslid}")
    @ResponseBody
    public Object commonDianghCancel(@PathVariable("gzlslid") String gzlslid) {
        return bdcSdqghFeignService.commonDianghCancel(gzlslid);
    }

    /**
     * @param gzlslid ???????????????ID
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ?????????????????????
     */
    @PostMapping("/qi/tsgh/cancel/{gzlslid}")
    @ResponseBody
    public Object commonQighCancel(@PathVariable("gzlslid") String gzlslid) {
        return bdcSdqghFeignService.commonQighCancel(gzlslid);
    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description ??????????????????????????????????????????
      */
    @ResponseBody
    @GetMapping("/dian/zt/{gzlslid}/{consNo}")
    public Object commonDianZt(@PathVariable("gzlslid") String gzlslid,
                            @PathVariable("consNo") String consNo) {
        return bdcSdqghFeignService.commonDianZt(gzlslid,consNo);

    }

    /**
     * @param gzlslid ???????????????id
     * @param userId ??????
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //?????????????????????????????????????????????
     * @Date 2022/5/11 9:18
     **/
    @ResponseBody
    @GetMapping("/shui/check/shucheng")
    public CommonResponse<Boolean> sghjc(@RequestParam(name="gzlslid") String gzlslid, @RequestParam(name="userId")String userId){
        if(StringUtils.isAnyBlank(gzlslid, userId)){
            throw new AppException(ErrorCode.MISSING_ARG, "???????????????????????????ID?????????????????????");
        }
        return  shuChengWaterFeignService.sghjc(gzlslid, userId,"");
    }

    /**
     * @param gzlslid ???????????????id
     * @param userId ??????
     * @Author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @Description ????????????????????????
     **/
    @ResponseBody
    @GetMapping("/shui/tsgh/shucheng")
    public void tsShuiGh(@RequestParam(name="gzlslid") String gzlslid, @RequestParam(name="userId")String userId){
        if(StringUtils.isAnyBlank(gzlslid, userId)){
            throw new AppException(ErrorCode.MISSING_ARG, "???????????????????????????ID?????????????????????");
        }
        shuChengWaterFeignService.sgh(gzlslid,userId,"");
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description ????????????????????????
     */
    @ResponseBody
    @GetMapping("/dian/ghblzt/{gzlslid}/{consNo}")
    public Object commonDianGhblzt(@PathVariable("gzlslid") String gzlslid,
                                   @PathVariable("consNo") String consNo) {
        return bdcSdqghFeignService.commonDianGhblzt(gzlslid,consNo);

    }

    /**
     * @param
     * @param gzlslid
     * @param bdcSdqghDOS
     * @param needHz ?????????????????? ????????????
     * @return void
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/7/22 9:14
     * @description ????????????????????????
     **/
    @ResponseBody
    @RequestMapping(value = "/saveSdq/{gzlslid}")
    public void saveSdq(@PathVariable("gzlslid") String gzlslid,
                        @RequestBody List<BdcSdqghDO> bdcSdqghDOS,
                        @RequestParam(value = "needHz", required = false) Boolean needHz) {
        bdcSdqghFeignService.saveSdq(gzlslid, bdcSdqghDOS, needHz);
    }

    @ResponseBody
    @RequestMapping(value = "/sdqxx/{gzlslid}")
    public BdcSdqxxDTO querySdqxx(@PathVariable("gzlslid") String gzlslid) {
        return bdcSdqghFeignService.querySdqxx(gzlslid);
    }

    /**
     * @param gzlslid ???????????????ID
     * @param qlrxxsjly ??????????????????????????? ???????????????????????? 1:??????????????????????????? 2??????????????????????????????
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description ???????????????
     */
    @PostMapping("/shui/tsgh/{gzlslid}")
    @ResponseBody
    public Object commonShuigh(@PathVariable("gzlslid") String gzlslid,@RequestParam(name = "qlrxxsjly", required = false) Integer qlrxxsjly,@RequestParam(name = "appendSign", required = false) String appendSign) {
        return bdcSdqghFeignService.commonShuigh(gzlslid,qlrxxsjly,appendSign,0);
    }

    /**
     * @param gzlslid ???????????????ID
     * @return
     * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
     * @description ?????????????????????
     */
    @PostMapping("/shui/tsgh/cancel/{gzlslid}")
    @ResponseBody
    public Object commonShuighCancel(@PathVariable("gzlslid") String gzlslid) {
        return bdcSdqghFeignService.commonShuighCancel(gzlslid);
    }


    /**
     * @return ??? ??????????????????????????????
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxxinyyu</a>
     */
    @ResponseBody
    @GetMapping("/nantong/sdq")
    public Object sdqTs(@RequestParam("gzlslid") String gzlslid,
                          @RequestParam("ywlx") Integer ywlx) {
        if (StringUtils.isAnyBlank(gzlslid, ywlx.toString())) {
            throw new MissingArgumentException("gzlslid, ywlx");
        }
        try {
            if (ywlx.equals(BdcSdqEnum.S.key())) {
                nantongShuiFeignService.sgh(gzlslid);
            } else if (ywlx.equals(BdcSdqEnum.D.key())) {
                dianFeignService.sqElectricGh(gzlslid, null, null);
            } else if (ywlx.equals(BdcSdqEnum.Q.key())) {
                nantongQiFeignService.qgh(gzlslid);
            }
        } catch (Exception e) {
            // ???????????????????????????????????????
            BdcSdqghDO bdcSdqghDO = queryBdcSdqgh(gzlslid,ywlx);
            if (bdcSdqghDO!=null && bdcSdqghDO.getBlzt()==null){
                updateFailRecord(bdcSdqghDO,ywlx);
            }
            LOGGER.error("????????????", e);
            e.printStackTrace();
        }
        return queryBdcSdqgh(gzlslid, ywlx);
    }

    /**
     * ????????????????????????????????????
     */
    private BdcSdqghDO queryBdcSdqgh(String gzlslid, Integer ywlx){
        BdcSdqywQO bdcSdqghQO = new BdcSdqywQO();
        bdcSdqghQO.setGzlslid(gzlslid);
        if(null != ywlx){
            bdcSdqghQO.setYwlx(ywlx);
        }
        List<BdcSdqghDO> sdqghDOList = bdcSdqghFeignService.querySdqyw(bdcSdqghQO);
        if (CollectionUtils.isNotEmpty(sdqghDOList)) {
            return sdqghDOList.get(0);
        }
        return null;
    }

    /**
     * @return
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description ????????????????????????
     * @Date 2022/4/26
     * @Param bdcSdqghDO
     * @Param ywlx
     **/
    private void updateFailRecord(BdcSdqghDO bdcSdqghDO, Integer ywlx) {
        BdcSdqBlztRequestDTO sdqBlztRequestDTO = new BdcSdqBlztRequestDTO();
        sdqBlztRequestDTO.setConsno(bdcSdqghDO.getConsno());
        sdqBlztRequestDTO.setGzlslid(bdcSdqghDO.getGzlslid());
        sdqBlztRequestDTO.setYwlx(ywlx);
        sdqBlztRequestDTO.setBlzt(4);
        sdqBlztRequestDTO.setSdqshyj("????????????");
        sdqBlztRequestDTO.setSqsj(new Date());
        if (Objects.nonNull(bdcSdqghDO.getTscs())) {
            sdqBlztRequestDTO.setTscs(bdcSdqghDO.getTscs() + 1);
        } else {
            sdqBlztRequestDTO.setTscs(1);
        }
        bdcSdqghFeignService.updateSdqBlzt(sdqBlztRequestDTO);
    }

}
