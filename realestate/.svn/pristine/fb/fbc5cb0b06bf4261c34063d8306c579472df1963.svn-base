package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.common.matcher.StorageClientMatcher;
import cn.gtmap.gtc.storage.domain.dto.MultipartDto;
import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.accept.ui.web.main.BaseController;
import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.domain.accept.*;
import cn.gtmap.realestate.common.core.dto.accept.BdcCdlcQzxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcCdlcxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJtcyQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSqrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcCdxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQllxFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcYwsjHxFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.util.BdcUploadFileUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 2020/09/24
 * @description 常州查档受理页面项目相关控制层
 */
@Controller
@Validated
@RequestMapping("/cdlc")
public class SlymCdLcController extends BaseController {

    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQllxFeignService bdcQllxFeignService;
    @Autowired
    BdcSlXmLsgxFeignService bdcSlXmLsgxFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;
    @Autowired
    BdcRyzdFeignService bdcRyzdFeignService;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    BdcSlCdBlxxFeignService bdcSlCdBlxxFeignService;
    @Autowired
    BdcYwsjHxFeignService bdcYwsjHxFeignService;
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    BdcSlJtcyFeignService bdcSlJtcyFeignService;
    @Autowired
    StorageClientMatcher storageClient;

    @Autowired
    BdcUploadFileUtils bdcUploadFileUtils;

    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;

    @Autowired
    BdcSlCdxxFeignService bdcSlCdxxFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;

    @Autowired
    BdcSlxxHxFeignService bdcSlxxHxFeignService;

    @Value("${cdlc.qlrcx.gzldyid:}")
    private String cdlcGzldyid;

    @Value("${cdym.sqlb:}")
    private String sqlb;


    /**
     * 根据gzlslid查询查档流程信息
     * ---2021年8月10号修改查档流程逻辑从受理库获取，不再获取登记库信息，只获取上一手信息
     *
     * @param gzlslid
     * @return 查档信息
     */
    @ResponseBody
    @GetMapping("/getCdxx/{gzlslid}")
    public Object getCdxx(@PathVariable(value = "gzlslid") String gzlslid) {
        List<BdcCdlcxxDTO> bdcCdlcxxDTOS = new ArrayList<>(2);
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            BdcSlJbxxDO bdcSlJbxxDO = bdcSlJbxxFeignService.queryBdcSlJbxxByGzlslid(gzlslid);
            Double jzmjzh = 0.0;
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                    BdcCdlcxxDTO bdcCdlcxxDTO = new BdcCdlcxxDTO();
                    bdcCdlcxxDTO.setBdcSlJbxxDO(bdcSlJbxxDO);
                    bdcCdlcxxDTO.setBdcXmDO(bdcSlXmDO);
                    String xmid = bdcSlXmDO.getXmid();
                    List<BdcSlSqrDO> listSqr = bdcSlSqrFeignService.listBdcSlSqrByXmid(xmid, "");
                    if (CollectionUtils.isNotEmpty(listSqr)) {
                        bdcCdlcxxDTO.setSqrid(listSqr.get(0).getSqrid());
                    }
                    // 查档信息
                    BdcCdxxQO bdcCdxxQO = new BdcCdxxQO();
                    bdcCdxxQO.setXmid(xmid);
                    BdcSlCdxxDO bdcSlCdxxDO = bdcSlCdxxFeignService.queryBdcCdxx(bdcCdxxQO);
                    if (StringUtils.isBlank(bdcSlCdxxDO.getCxsqlb())) {
                        bdcSlCdxxDO.setCxsqlb(sqlb);
                    }
                    bdcCdlcxxDTO.setBdcCdxxDO(bdcSlCdxxDO);

                    //历史关系
                    BdcXmLsgxQO bdcXmLsgxQO = new BdcXmLsgxQO();
                    bdcXmLsgxQO.setWlxm(CommonConstantUtils.SF_F_DM);
                    bdcXmLsgxQO.setXmid(xmid);
                    List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(xmid, "", CommonConstantUtils.SF_F_DM);
                    String yxmid = "";
                    if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                        //获取上一手的权利信息
                        yxmid = bdcSlXmLsgxDOList.get(0).getYxmid();
                        BdcQl bdcQl = bdcQllxFeignService.queryQlxx(yxmid);
                        if (bdcQl instanceof BdcFdcqDO) {
                            BdcFdcqDO fdcqDO = (BdcFdcqDO) bdcQl;
                            if(fdcqDO.getJzmj() != null){
                                jzmjzh = jzmjzh + fdcqDO.getJzmj();
                            }
                            bdcCdlcxxDTO.setBdcFdcqDO(fdcqDO);
                        }
                        BdcXmQO ybdcXmQO = new BdcXmQO();
                        ybdcXmQO.setXmid(yxmid);
                        List<BdcXmDO> yBdcXmDOList = bdcXmFeignService.listBdcXm(ybdcXmQO);
                        if (CollectionUtils.isNotEmpty(yBdcXmDOList)) {
                            BdcXmDO yBdcXmDO = yBdcXmDOList.get(0);
                            bdcCdlcxxDTO.setyBdcXmDO(yBdcXmDO);
                        }
                    }
                    //权利人数据
                    //首套房证明展现权利人数据
                    if (StringUtils.isNotBlank(xmid)) {
                        BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
                        bdcSlSqrQO.setXmid(xmid);
                        bdcSlSqrQO.setSqrlb(CommonConstantUtils.QLRLB_QLR);
                        List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrFeignService.listBdcSlSqrByBdcSlSqrQO(bdcSlSqrQO);
                        bdcCdlcxxDTO.setQlrList(bdcSlSqrDOList);
                    }
                    if (StringUtils.isNotBlank(yxmid)) {
                        //处理查档页面的共有人信息
                        BdcQlrQO bdcQlrQO = new BdcQlrQO();
                        bdcQlrQO.setXmid(yxmid);
                        bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
                        List<BdcQlrDO> listQlr = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                        if (CollectionUtils.isNotEmpty(listQlr)) {
                            dealQlr(bdcCdlcxxDTO, listQlr);
                        }
                    }
                    bdcCdlcxxDTO.setQlrcxGzldyid(cdlcGzldyid);
                    bdcCdlcxxDTOS.add(bdcCdlcxxDTO);
                }

                Double finalJzmjzh = jzmjzh;
                bdcCdlcxxDTOS.forEach(bdcCdlcxxDTO -> bdcCdlcxxDTO.setJzmjzh(finalJzmjzh));
                if (CollectionUtils.size(bdcCdlcxxDTOS) == 1) {
                    return bdcCdlcxxDTOS.get(0);
                }
                return bdcCdlcxxDTOS;
            } else {
                throw new AppException("根据工作实例id未查询到项目信息");
            }

        } else {
            throw new MissingArgumentException("查询查档信息缺失gzlslid");
        }
    }

    /**
     * @param json
     * @return 新的实体bdcCdxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新(插入)查档信息
     */
    @ResponseBody
    @PatchMapping("/saveBdcCdxx")
    public Object updateBdcCdxx(@RequestBody String json, String gzlslid) {
        BdcSlCdxxDO bdcSlCdxxDO = JSONObject.parseObject(json, BdcSlCdxxDO.class);
        JSONObject cdObj = JSONObject.parseObject(json);
        //批量保存
        if (StringUtils.isNotBlank(gzlslid)) {
            List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
            if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
                Set<String> xcxrSet = new HashSet<>(1);
                for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                    //根据xmid 找到对应的cdxx
                    BdcCdxxQO bdcCdxxQO = new BdcCdxxQO();
                    bdcCdxxQO.setXmid(bdcSlXmDO.getXmid());
                    BdcSlCdxxDO bdcSlCdxx = bdcSlCdxxFeignService.queryBdcCdxx(bdcCdxxQO);
                    if (Objects.nonNull(bdcSlCdxx) && StringUtils.isNotBlank(bdcSlCdxx.getCdxxid())) {
                        // 同步sqr
                        cdObj.put("cdxxid",bdcSlCdxx.getCdxxid());
                        cdObj.put("xmid",bdcSlCdxx.getXmid());
                        syncSqr(bdcSlCdxxDO);
                        bdcSlEntityFeignService.updateByJsonEntity(JSON.toJSONString(cdObj), BdcSlCdxxDO.class.getName());
                    } else {
                        //未找到对应的查档信息就新增
                        bdcSlCdxxFeignService.saveBdcCdxx(bdcSlCdxxDO);
                    }
                    if (StringUtils.isNotBlank(bdcSlCdxxDO.getXcxr())) {
                        xcxrSet.add(bdcSlCdxxDO.getXcxr());
                    }
                }
                if (CollectionUtils.isNotEmpty(xcxrSet)) {
                    Map zdyMap = new HashMap(1);
                    zdyMap.put("QLR", String.join(",", xcxrSet));
                    try {
                        bdcSlxxHxFeignService.hxBdcSlxxWithZdyxx(gzlslid, zdyMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bdcSlCdxxDO;
    }

    /**
     * @param json
     * @return 新的实体bdcCdxxDO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 更新(插入)查档补录信息
     */
    @ResponseBody
    @PatchMapping("/saveBdcCdblxx")
    public Object saveBdcCdblxx(@RequestBody String json) {
        BdcCdBlxxDO bdcCdBlxxDO = JSONObject.parseObject(json, BdcCdBlxxDO.class);
        if (StringUtils.isNotBlank(bdcCdBlxxDO.getXmid())) {

            return bdcSlCdBlxxFeignService.saveBdcBlxx(bdcCdBlxxDO);
        } else {
            throw new MissingArgumentException("保存查档信息缺失xmid");
        }
    }

    /**
     * 处理权利人与共有人
     *
     * @param bdcCdlcxxDTO
     * @param listQlr
     */
    private void dealQlr(BdcCdlcxxDTO bdcCdlcxxDTO, List<BdcQlrDO> listQlr) {
        //不区分共有人和产权人,统一为产权人
        bdcCdlcxxDTO.setQlr(StringToolUtils.resolveBeanToAppendStr(listQlr, "getQlrmc", "、"));
//        String gyr = "";
//        for (BdcQlrDO qlr : listQlr) {
//
//            //只展示顺序号为1 的作为权利人，其他为供共有人
//            if (Objects.nonNull(qlr.getSxh()) && qlr.getSxh() == 1) {
//                bdcCdlcxxDTO.setQlr(qlr.getQlrmc() + (StringUtils.isNotBlank(bdcCdlcxxDTO.getQlr()) ? "," + bdcCdlcxxDTO.getQlr() : ""));
//            } else {
//                gyr += qlr.getQlrmc() + ",";
//            }
//        }
//        if (StringUtils.isNotBlank(gyr)) {
//            gyr = gyr.substring(0, gyr.length() - 1);
//            bdcCdlcxxDTO.setGyr(gyr);
//        }
    }

    /**
     * 根据xmid更新备注
     *
     * @param
     * @param
     */
    @PatchMapping("/saveBz")
    public void saveBz(@RequestBody String json) {
        JSONObject bzObj = JSONObject.parseObject(json);
        if (bzObj != null &&StringUtils.isNoneBlank(bzObj.getString("jbxxid"))) {
            bdcSlEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(bzObj),BdcSlJbxxDO.class.getName());
        }
    }

    /**
     * @param json 前台传输权利人集合json
     * @return 修改数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 批量修改权利人
     */
    @ResponseBody
    @PatchMapping(value = "/list/qlr")
    public Integer updateZhBdcQlr(@RequestBody String json, String processInsId) {
        Integer count = 0;
        JSONArray jsonArray = JSONObject.parseArray(json);
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                BdcSlSqrDO bdcSlSqrDO = JSONObject.toJavaObject(obj, BdcSlSqrDO.class);
                if (StringUtils.isNotBlank(bdcSlSqrDO.getSqrid())) {
                    bdcSlSqrFeignService.updateBdcSlSqr(bdcSlSqrDO);
                } else {
                    bdcSlSqrFeignService.insertBdcSlSqr(bdcSlSqrDO);
                }
            }
        }
        if (StringUtils.isNotBlank(processInsId)) {
//            //更新权利人冗余字段
//            bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
//            //更新共有情况
//            bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);

            //回写信息到平台
            try {
                bdcSlxxHxFeignService.hxBdcSlxx(processInsId);
            } catch (Exception e) {
                LOGGER.error("回写信息到大云失败", e);
            }
        }
        return count;
    }

    /**
     * @param qlrid   权利人ID
     * @param processInsId 项目ID
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除权利人(用于简单流程)
     */
    @ResponseBody
    @DeleteMapping("/list/qlr")
    public void deleteBdcQlr(@RequestParam("qlrid") String qlrid, @RequestParam("processInsId") String processInsId) {
        if (StringUtils.isNotBlank(qlrid)) {
            bdcSlSqrFeignService.deleteBdcSlSqrBySqrid(qlrid);
            if (StringUtils.isNotBlank(processInsId)) {
//                bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
//                bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
                //回写信息到平台
                try {
                    bdcSlxxHxFeignService.hxBdcSlxx(processInsId);
                } catch (Exception e) {
                    LOGGER.error("回写信息到大云失败", e);
                }
            }
        }
    }

    /**
     * @param
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2020/9/28 14:59
     */
    @ResponseBody
    @GetMapping("/blxx")
    public Object queryCdBlxx(String blxxid) {
        if (StringUtils.isBlank(blxxid)) {
            return new BdcCdBlxxDO();
        }
        BdcCdBlxxDO bdcCdBlxxDO = new BdcCdBlxxDO();
        bdcCdBlxxDO.setBlxxid(blxxid);
        return bdcSlCdBlxxFeignService.queryBdcBlxx(bdcCdBlxxDO);
    }

    @ResponseBody
    @GetMapping("/delete")
    public void deleteBlxx(String blxxid) {
        if (StringUtils.isNotBlank(blxxid)) {
            bdcSlCdBlxxFeignService.deleteBdcBlxx(blxxid, "");
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存更新申请人信息
     */
    @ResponseBody
    @PatchMapping("/sqrxx/save")
    public String saveBatchSqrxx(@RequestBody BdcSlSqrDO bdcSlSqrDO) {
        if (StringUtils.isNotBlank(bdcSlSqrDO.getSqrid())) {
            bdcSlSqrFeignService.updateBdcSlSqr(bdcSlSqrDO);
            return bdcSlSqrDO.getSqrid();
        } else {
            bdcSlSqrFeignService.deleteBdcSlSqrByXmid(bdcSlSqrDO.getXmid());
            BdcSlSqrDO bdcSlSqrDOResult = bdcSlSqrFeignService.insertBdcSlSqr(bdcSlSqrDO);
            return bdcSlSqrDOResult.getSqrid();
        }

    }

    /**
     * @param json 页面传输被查询人Json
     * @return 修改数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 批量修改被查询人
     */
    @ResponseBody
    @PatchMapping(value = "/bcxr/list")
    public Integer updateBdcBcxr(@RequestBody String json) {
        int count = 0;
        for (BdcSlJtcyDO bdcSlJtcyDO : JSON.parseArray(json, BdcSlJtcyDO.class)) {
            if (StringUtils.isBlank(bdcSlJtcyDO.getJtcyid())) {
                bdcSlJtcyFeignService.insertBdcSlJtcy(bdcSlJtcyDO);
                count++;
            } else {
                count += bdcSlJtcyFeignService.updateBdcSlJtcy(bdcSlJtcyDO);
            }
        }
        return count;
    }

    /**
     * 同步cdxx cdr(申请人)sqr表和jtcy表
     *
     * @param bdcSlCdxxDO
     */
    public void syncSqr(BdcSlCdxxDO bdcSlCdxxDO) {
        String xmid = bdcSlCdxxDO.getXmid();
        List<BdcSlSqrDO> list = bdcSlSqrFeignService.listBdcSlSqrByXmid(xmid, "");
        if (CollectionUtils.isNotEmpty(list) &&StringUtils.isNotBlank(bdcSlCdxxDO.getCdr())) {
            BdcSlSqrDO bdcSlSqrDO = list.get(0);
            bdcSlSqrDO.setSqrmc(bdcSlCdxxDO.getCdr());
            bdcSlSqrDO.setZjh(bdcSlCdxxDO.getCdrzjh());
            bdcSlSqrDO.setZjzl(bdcSlCdxxDO.getCdrzjzl());
            bdcSlEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(bdcSlSqrDO),BdcSlSqrDO.class.getName());


            BdcSlJtcyQO bdcSlJtcyQO = new BdcSlJtcyQO();
            bdcSlJtcyQO.setSqrid(bdcSlSqrDO.getSqrid());
            bdcSlJtcyQO.setYsqrgx("本人");
            List<BdcSlJtcyDO> listJtcy = bdcSlJtcyFeignService.listBdcSlJtcy(bdcSlJtcyQO);
            if (CollectionUtils.isNotEmpty(listJtcy)) {
                BdcSlJtcyDO bdcSlJtcyDO = listJtcy.get(0);
                bdcSlJtcyDO.setJtcymc(bdcSlSqrDO.getSqrmc());
                bdcSlJtcyDO.setZjh(bdcSlSqrDO.getZjh());
                bdcSlJtcyDO.setZjzl(bdcSlSqrDO.getZjzl());
                bdcSlEntityFeignService.updateByJsonEntity(JSONObject.toJSONString(bdcSlJtcyDO),BdcSlJtcyDO.class.getName());
            }
        }
    }

    /**
     * @param bdcCdlcQzxxDTO
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 保存签字信息
     */
    @ResponseBody
    @PostMapping(value = "/sign/save")
    public void saveSignToWjzx(@RequestBody BdcCdlcQzxxDTO bdcCdlcQzxxDTO) throws IOException {
        if (StringUtils.isBlank(bdcCdlcQzxxDTO.getGzlslid()) || StringUtils.isBlank(bdcCdlcQzxxDTO.getSignStreamData())) {
            throw new AppException("参数为空");
        }
        String gzlslid = bdcCdlcQzxxDTO.getGzlslid();
        String base64 = bdcCdlcQzxxDTO.getSignStreamData();

        bdcUploadFileUtils.uploadBase64File(base64, gzlslid, "签名/盖章", "sign", ".jpg");
    }

    /**
     * 查询签名信息
     *
     * @param gzlslid
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/sign/query")
    public String querySign(@RequestParam(name = "gzlslid") String gzlslid) {
        if (StringUtils.isNotBlank(gzlslid)) {
            List<StorageDto> listFjcl = storageClient.listStoragesByName("clientId", gzlslid, null, "签名/盖章", 1, 0);
            if (CollectionUtils.isNotEmpty(listFjcl)) {
                StorageDto dto = listFjcl.get(0);
                String id = dto.getId();
                List<StorageDto> listFiles = storageClient.listAllSubsetStorages(id, null, null, null, null,null);
                if (CollectionUtils.isNotEmpty(listFiles)) {
                    MultipartDto multipartDto = storageClient.download(listFiles.get(0).getId());
                    if (null == multipartDto || null == multipartDto.getData()) {
                        return "";
                    }
                    return storageClient.downloadBase64(listFiles.get(0).getId()).getMsg();
                }
            }
        }
        return "";
    }

    @ResponseBody
    @GetMapping("/yxm")
    public Object listYxmxx(@NotBlank(message = "查询原项目信息缺失工作流实例id") String gzlslid) {
        List<BdcSlXmDO> bdcSlXmDOList = bdcSlXmFeignService.listBdcSlXmByGzlslid(gzlslid);
        if (CollectionUtils.isNotEmpty(bdcSlXmDOList)) {
            List<String> yxmidList = new ArrayList<>(CollectionUtils.size(bdcSlXmDOList));
            for (BdcSlXmDO bdcSlXmDO : bdcSlXmDOList) {
                List<BdcSlXmLsgxDO> bdcSlXmLsgxDOList = bdcSlXmLsgxFeignService.listBdcSlXmLsgx(bdcSlXmDO.getXmid(), "", CommonConstantUtils.SF_F_DM);
                if (CollectionUtils.isNotEmpty(bdcSlXmLsgxDOList)) {
                    yxmidList.add(bdcSlXmLsgxDOList.get(0).getYxmid());
                }
            }
            if (CollectionUtils.isNotEmpty(yxmidList)) {
                BdcXmQO bdcXmQO = new BdcXmQO();
                bdcXmQO.setXmidList(yxmidList);
                List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
                if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                    List<String> xmidList = bdcXmDOList.stream().map(BdcXmDO::getXmid).collect(Collectors.toList());
                    Map resultMap = new HashMap(1);
                    if (CollectionUtils.size(xmidList) > 1) {
                        resultMap.put("pllc", true);
                        resultMap.put("xmidList", xmidList);
                    } else {
                        resultMap.put("xmid", xmidList.get(0));
                        resultMap.put("gzlslid", bdcXmDOList.get(0).getGzlslid());
                        resultMap.put("xmly", bdcXmDOList.get(0).getXmly());
                    }
                    return resultMap;
                }
            }
        }
        return null;
    }

    /**
     * @param zl
     * @author <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhuang</a>
     * @description 根据坐落、证号查询产权
     * @date : 2021/12/7
     */
    @ResponseBody
    @GetMapping("/queryByZl")
    public Object queryFdcqByZl(String zl) {
        if (StringUtils.isBlank(zl)) {
            return new BdcCdBlxxDO();
        }
        return bdcQllxFeignService.queryFdcqByZl(zl);
    }

}
