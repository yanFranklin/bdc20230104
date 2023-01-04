package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.common.core.domain.BdcLzrDO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlLzrDO;
import cn.gtmap.realestate.common.core.dto.init.BdcLzrDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcLzrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlLzrFeignService;
import cn.gtmap.realestate.common.core.service.feign.certificate.BdcZsFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcLzrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcLzrVO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/1/15.
 * @description 不动产受理领证人控制层
 */
@RestController
@RequestMapping("/slym/lzr")
@Slf4j
public class SlymLzrController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlymLzrController.class);

    @Autowired
    BdcSlLzrFeignService bdcSlLzrFeignService;
    //    获取登记库中的领证人信息
    @Autowired
    BdcLzrFeignService bdcLzrFeignService;
    @Autowired
    BdcXmFeignService bdcXmFeignService;
    @Autowired
    BdcQlrFeignService bdcQlrFeignService;

    @Autowired
    BdcZsFeignService bdcZsFeignService;

    /**
     * 组合流程不展示领证人信息的等级小类
     */
    @Value("${lzrxx.bzsdjxl:}")
    private String lzrBzsDjxl;

    @Value("${lzrxx.sfgxzs:false}")
    private boolean lzrgxzs;

    /**
     * 根据不动产项目ID获取领证人信息
     *
     * @param xmid 项目id
     * @return List<BdcSlSqr> 不动产受理领证人集合
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/lzrxx")
    public Object queryBdcSlLzr(@RequestParam("xmid") String xmid) {
        return this.bdcSlLzrFeignService.listBdcSlLzrByXmid(xmid);
    }

    /**
     * 保存受理领证人信息
     *
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param json 不动产受理领证人更新集合JSON
     * @return Integer
     */
    @PatchMapping("/lzrxx")
    public Integer updateBdcSlLzrDO(@RequestBody String json) throws Exception {
        int count = 0;
        int sxh = 0; // 领证人顺序号
        for (BdcSlLzrDO bdcSlLzrDO : JSON.parseArray(json, BdcSlLzrDO.class)) {
            String lzrmc = bdcSlLzrDO.getLzrmc();
            String lzrzjh = bdcSlLzrDO.getLzrzjh();
            String xmid = bdcSlLzrDO.getXmid();
            List<BdcSlLzrDO> list = bdcSlLzrFeignService.listBdcSlLzrByXmid(xmid);
            boolean insertFlag = false;
            if(CollectionUtils.isNotEmpty(list)){
                for(int i=0;i<list.size();i++){
                    // lzrmc lzrzjh mxid都一致 则不再插入
                    if( StringUtils.isNotEmpty(lzrzjh)&&  StringUtils.isNotEmpty(list.get(i).getLzrzjh())
                            && lzrmc.equals(list.get(i).getLzrmc()) && lzrzjh.equals(list.get(i).getLzrzjh())){
                        insertFlag = true;
                        break;
                    }
                }
            }
            if(insertFlag){
                break;
            }

            bdcSlLzrDO.setSxh(++sxh);
            if (StringUtils.isBlank(bdcSlLzrDO.getLzrid())) {
                this.bdcSlLzrFeignService.insertBdcSlLzr(bdcSlLzrDO);
                count++;
            } else {
                count += this.bdcSlLzrFeignService.updateBdcSlLzr(bdcSlLzrDO);
            }
        }
        return count;
    }

    /**
     * 删除受理领证人信息
     *
     * @param lzrid 领证人ID
     * @return void 无返回值
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @ResponseBody
    @DeleteMapping("/sqrxx")
    public void deleteBdcSlLzr(@RequestParam("lzrid") String lzrid) throws Exception {
        this.bdcSlLzrFeignService.deleteBdcSlLzrByLzrid(lzrid);
    }

    /**
     * 查询领证人信息--南通--所有流程直接根据xmid
     *
     * @param xmid 项目id
     * @return void 无返回值
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @GetMapping("/nt/lzrxx")
    public Object getLzrxx(String xmid) {
        if (StringUtils.isBlank(xmid)) {
            throw new AppException("获取领证人信息缺少xmid");
        }
        BdcLzrQO bdcLzrQO = new BdcLzrQO();
        bdcLzrQO.setXmid(xmid);
        return bdcLzrFeignService.listBdcLzr(bdcLzrQO);
    }

    /**
     * @param qlrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据qlrid 查询是否存在领证人
     * @date : 2022/2/8 13:54
     */
    @ResponseBody
    @GetMapping("/yc/lzrxx")
    public Object queryLzrxxByQlrid(String qlrid) {
        if (StringUtils.isBlank(qlrid)) {
            return null;
        }
        BdcLzrQO bdcLzrQO = new BdcLzrQO();
        bdcLzrQO.setQlrid(qlrid);
        return bdcLzrFeignService.listBdcLzr(bdcLzrQO);
    }

    /**
     * 查询领证人信息--南通-单个流程/组合流程直接根据xmid
     *
     * @param json 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @PostMapping("/nt/lzrxx/jdlc")
    public Object saveLzrxx(@RequestBody String json, @RequestParam(value = "gzlslid", required = false) String gzlslid) {
        BdcLzrDO bdcLzrDO = new BdcLzrDO();
        JSONArray jsonArray = JSONObject.parseArray(json);
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                BdcLzrDO bdcLzr = JSONObject.parseObject(JSONObject.toJSONString(obj), BdcLzrDO.class);
                //存在lzrid更新
                if (StringUtils.isNotBlank(bdcLzr.getLzrid())) {
                    bdcLzrFeignService.updateBdcLzr(bdcLzr);
                } else {
                    bdcLzrDO = bdcLzrFeignService.insertBdcLzr(bdcLzr);
                }
            }
            //保存完之后判断是否存在证书信息，有证书就取第一个领证人更新证书表，否则不更新
            if (StringUtils.isNotBlank(gzlslid)) {
                bdcLzrFeignService.hxLzrxxToZs(gzlslid);
            }
        }
        return bdcLzrDO;
    }

    /**
     * 查询领证人信息--南通-单个流程/组合流程直接根据xmid
     *
     * @param json 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @PostMapping("/nt/lzrxx/pllc")
    public Object saveLzrxxPllc(@RequestBody String json, String gzlslid, String xmid) throws Exception {
        if (StringUtils.isNotBlank(json) && StringUtils.isNotBlank(gzlslid)) {
            int count = bdcSlLzrFeignService.updatePlBdcLzr(json, gzlslid, "");
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
            return count;
        } else {
            throw new AppException("缺少更新数据");
        }
    }

    /**
     * 查询领证人信息--南通-单个流程/组合流程直接根据xmid
     *
     * @param json 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @PostMapping("/nt/lzrxx/plzh")
    public Object saveLzrxxPlzh(@RequestBody String json, String gzlslid, String djxl) throws Exception {
        int count = bdcSlLzrFeignService.updatePlBdcLzr(json, gzlslid, djxl);
        bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        return count;
    }

    /**
     * 删除领证人信息--南通-单个流程/组合流程根据lzrid删除
     *
     * @param lzrid 领证人id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @DeleteMapping("/nt/lzrdelte")
    public void deleteLzrxx(@RequestParam(name = "lzrid") String lzrid, @RequestParam(name = "gzlslid", required = false) String gzlslid) {
        if (StringUtils.isBlank(lzrid)) {
            throw new AppException("删除领证人缺失必要参数");
        }
        bdcLzrFeignService.deleteBdcLzr(lzrid);
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
    }

    /**
     * @return void
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [qlrid]
     * @description 简单流程和组合流程 qlrid 盐城
     */
    @ResponseBody
    @DeleteMapping("/yc/lzrdelete")
    public void deleteLzrxxYc(@RequestParam("qlrid") String qlrid, String lzrid, @RequestParam(value = "gzlslid", required = false) String gzlslid) {
        if (StringUtils.isNotBlank(lzrid)) {
            bdcLzrFeignService.deleteBdcLzr(lzrid);
        } else if (StringUtils.isNotBlank(qlrid)) {
            BdcLzrQO bdcLzrQO = new BdcLzrQO();
            bdcLzrQO.setQlrid(qlrid);
            List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
            if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
                for (BdcLzrDO lzr : bdcLzrDOList) {
                    bdcLzrFeignService.deleteBdcLzr(lzr.getLzrid());
                }
            }
        }
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [gzlslid, djxl]批量流程传工作流实例id，批量组合传工作流实例id和登记小类
     * @return void
     * @description 批量，批量组合流程 根据工作流实例id和登记小类删除对应项目的领证人
     */
    @ResponseBody
    @DeleteMapping("/yc/lzrdelete/pl")
    public void deleteLzrxxYcPl(String gzlslid, String djxl) {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("删除领证人缺失必要参数");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        bdcXmQO.setDjxl(djxl);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if (CollectionUtils.isEmpty(bdcXmDOList)) {
            LOGGER.error("根据工作流实例id和登记小类未查询到项目信息，工作流实例id：" + gzlslid + ",登记小类：" + djxl);
            throw new AppException("根据工作流实例id和登记小类未查询到项目信息");
        }
        List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.getBdcLzrDOByXm(bdcXmDOList);
        if (CollectionUtils.isEmpty(bdcLzrDOList)) {
            LOGGER.warn("未查到领证人信息");
            return;
        }
        bdcLzrFeignService.batchDeleteBdcLzr(bdcLzrDOList);
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
    }

    /**
     * 删除领证人信息--南通-批量流程根据领证人信息删除
     *
     * @param lzrid 领证人id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @DeleteMapping("/nt/lzrdelte/pllc")
    public void deleteLzrxxPllc(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "lzrid") String lzrid) {
        if (StringUtils.isBlank(lzrid)) {
            throw new AppException("删除领证人缺失必要参数");
        }
        BdcLzrQO bdcLzrQO = new BdcLzrQO();
        bdcLzrQO.setLzrid(lzrid);
        List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
        if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
            bdcLzrFeignService.deleteBdcLzrsByLzrxx(bdcLzrDOList.get(0).getLzrmc(), bdcLzrDOList.get(0).getLzrzjh(), gzlslid, "");
        }
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
    }

    /**
     * 删除领证人信息--南通-批量组合根据登记小类和领证人信息删除
     *
     * @param lzrid 领证人id
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @DeleteMapping("/nt/lzrdelte/plzh")
    public void deleteLzrxxPlzh(@RequestParam(name = "gzlslid") String gzlslid, @RequestParam(name = "lzrid") String lzrid, @RequestParam(name = "djxl") String djxl) {
        if (StringUtils.isBlank(lzrid)) {
            throw new AppException("删除领证人缺失必要参数");
        }
        BdcLzrQO bdcLzrQO = new BdcLzrQO();
        bdcLzrQO.setLzrid(lzrid);
        List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
        if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
            bdcLzrFeignService.deleteBdcLzrsByLzrxx(bdcLzrDOList.get(0).getLzrmc(), bdcLzrDOList.get(0).getLzrzjh(), gzlslid, djxl);
        }
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
    }

    /**
     * 新增领证人信息--合肥-单个流程/组合流程跟在权利人信息后面只会增加一个
     * 盐城 单个流程/组合流程 新增领证人复用此接口
     *
     * @param json 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @PostMapping("/hf/lzrxx")
    public Object insertLzrxxToHf(@RequestBody String json, @RequestParam(value = "gzlslid", required = false) String gzlslid) {
        log.info("lzrxx 合肥不动产受理领证人更新集合JSON,参数{}", json);
        BdcLzrDO bdcLzrDO = new BdcLzrDO();
        JSONArray jsonArray = JSONObject.parseArray(json);
        if (CollectionUtils.isNotEmpty(jsonArray)) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                BdcLzrDO bdcLzr = JSONObject.parseObject(JSONObject.toJSONString(obj), BdcLzrDO.class);
                bdcLzrDO = bdcLzrFeignService.insertBdcLzr(bdcLzr);
            }
        }
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
        return bdcLzrDO;
    }

    /**
     * 新增领证人信息--合肥-批量流程跟在权利人信息后面
     * 盐城 批量流程 新增领证人复用此接口
     * @param json 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @PostMapping("/hf/lzrxx/pllc")
    public Object insertLzrxxToHfPllc(@RequestBody String json, String gzlslid) throws Exception {
        log.info("pllc 合肥不动产受理领证人更新集合JSON,参数{},gzlslid{}", json, gzlslid);
        if (StringUtils.isBlank(json)) {
            throw new AppException("缺少更新数据");
        }
        int count = bdcSlLzrFeignService.updatePlBdcLzr(json, gzlslid, "");
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
        return count;
    }

    /**
     * 新增领证人信息--合肥-单个流程/组合流程跟在权利人信息后面只会增加一个
     * 盐城 批量组合流程 新增领证人复用此接口
     * @param json 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @PostMapping("/hf/lzrxx/plzh")
    public Object insertLzrxxToHfPlzh(@RequestBody String json, String gzlslid, String djxl) throws Exception {
        log.info("plzh 合肥不动产受理领证人更新集合JSON,参数{},gzlslid{}", json, gzlslid);
        int count = bdcSlLzrFeignService.updatePlBdcLzr(json, gzlslid, djxl);
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
        return count;
    }

    /**
     * 更新领证人信息--合肥-单个流程/组合流程跟在权利人信息后面只会增加一个
     *
     * @param json 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @PatchMapping("/hf/lzrxx")
    public Object updateLzrxxToHf(@RequestBody String json, @RequestParam("gzlslid") String gzlslid, @RequestParam("qlrid") String qlrid) {
        log.info("updateLzrxxToHf 合肥不动产受理领证人更新集合JSON,参数{},gzlslid{}", json, gzlslid);
        BdcLzrDTO bdcLzrDTO = new BdcLzrDTO();
        bdcLzrDTO.setJson(json);
        bdcLzrDTO.setGzlslid(gzlslid);
        bdcLzrDTO.setQlrid(qlrid);
        BdcLzrDO bdcLzrDO = bdcLzrFeignService.updateLzrxxToHfJdlc(bdcLzrDTO);
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
        return bdcLzrDO;
    }

    /**
     * @return void
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [json, gzlslid, qlrid, xmid]
     * @description 盐城 简单流程和组合流程 更新领证人
     */
    @ResponseBody
    @PatchMapping("/yc/lzrxx")
    public void updateLzrxxToYc(@RequestBody String json, @RequestParam("xmid") String xmid, @RequestParam(value = "gzlslid") String gzlslid) {
        if (StringUtils.isBlank(xmid) || StringUtils.isBlank(json)) {
            throw new AppException("更新领证人缺少必要参数");
        }
        BdcLzrQO bdcLzrQO = new BdcLzrQO();
        bdcLzrQO.setXmid(xmid);
        List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
        if (CollectionUtils.isEmpty(bdcLzrDOList)) {
            LOGGER.error("根据项目id未查询到领证人信息，项目id：" + xmid);
            throw new AppException("根据项目id未查询到领证人信息，项目id：" + xmid);
        }
        BdcLzrDO bdcLzr = JSONObject.parseObject(json, BdcLzrDO.class);
        //盐城 一个项目对应一个领证人
        bdcLzr.setLzrid(bdcLzrDOList.get(0).getLzrid());
        bdcLzrFeignService.updateBdcLzr(bdcLzr);
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
    }

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [json, gzlslid, qlrid, djxl]
     * @return void
     * @description 盐城 批量和批量组合 更新领证人
     */
    @ResponseBody
    @PatchMapping("/yc/lzrxx/pl")
    public void updateLzrxxToYcPl(@RequestBody String json, @RequestParam("gzlslid") String gzlslid, String djxl) {
        if (StringUtils.isBlank(gzlslid) || StringUtils.isBlank(json)) {
            throw new AppException("更新领证人缺少必要参数");
        }
        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        bdcXmQO.setDjxl(djxl);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            LOGGER.error("根据工作流实例id和登记小类未查询到项目信息，工作流实例id：" + gzlslid + ",登记小类：" + djxl);
            throw new AppException("根据工作流实例id和登记小类未查询到项目信息");
        }
        List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.getBdcLzrDOByXm(bdcXmDOList);
        if(CollectionUtils.isEmpty(bdcLzrDOList)){
            throw new AppException("未查询到领证人信息！");
        }

        Map<String, List<BdcLzrDO>> xmidToLzrList = bdcLzrDOList.stream().filter(Objects::nonNull)
                .filter(bdcLzrDO -> StringUtils.isNotBlank(bdcLzrDO.getXmid()))
                .collect(Collectors.groupingBy(BdcLzrDO::getXmid));

        BdcLzrDO bdcLzr = JSONObject.parseObject(json, BdcLzrDO.class);
        List<BdcLzrDO> updateBdcLzrList = new ArrayList<>();
        for(BdcXmDO bdcXmDO : bdcXmDOList){
            BdcLzrDO lzr = new BdcLzrDO();
            BeanUtils.copyProperties(bdcLzr,lzr);
            lzr.setXmid(bdcXmDO.getXmid());
            if(CollectionUtils.isEmpty(xmidToLzrList.get(bdcXmDO.getXmid()))){
                LOGGER.warn("批量流程更新领证人时，项目id未查询到领证人，项目id为：" + bdcXmDO.getXmid());
                throw new AppException("批量流程更新领证人时，项目id未查询到领证人!");
            }

            BdcQlrQO qlrQO= new BdcQlrQO();
            qlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            qlrQO.setXmid(bdcXmDO.getXmid());
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(qlrQO);
            if(CollectionUtils.isEmpty(bdcQlrDOList)){
                LOGGER.error("批量流程更新领证人时，当前项目未查到权利人信息，项目id为：" + bdcXmDO.getXmid());
                throw new AppException("批量流程更新领证人时，项目id未查询到权利人信息!");
            }
            bdcQlrDOList.forEach(bdcQlrDO -> {
                if (bdcQlrDO != null && StringUtils.equals(bdcQlrDO.getQlrmc(), lzr.getLzrmc()) && StringUtils.equals(bdcQlrDO.getZjh(), lzr.getLzrzjh())) {
                    lzr.setQlrid(bdcQlrDO.getQlrid());
                }
            });
            lzr.setLzrid(xmidToLzrList.get(bdcXmDO.getXmid()).get(0).getLzrid());
            updateBdcLzrList.add(lzr);
        }
        bdcLzrFeignService.batchUpdateBdcLzr(updateBdcLzrList);
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
    }


    /**
     * 更新领证人信息--合肥-批量流程跟在权利人信息后面
     *
     * @param json 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @PatchMapping("/hf/lzrxx/pllc")
    public Object updateLzrxxToHfPllc(@RequestBody String json, @RequestParam("gzlslid") String gzlslid, @RequestParam("xmid") String xmid, @RequestParam("qlrid") String qlrid) throws Exception {
        log.info("updateLzrxxToHfPllc 合肥不动产受理领证人更新集合JSON,参数{},gzlslid{}", json, gzlslid);
        BdcLzrDTO bdcLzrDTO = new BdcLzrDTO();
        bdcLzrDTO.setJson(json);
        bdcLzrDTO.setDjxl("");
        bdcLzrDTO.setGzlslid(gzlslid);
        bdcLzrDTO.setQlrid(qlrid);
        bdcLzrDTO.setXmid(xmid);
        int count = bdcLzrFeignService.updateLzrxxToHfPllc(bdcLzrDTO);
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
        return count;
    }

    /**
     * 更新领证人信息--合肥-批量组合流程跟在权利人信息后面
     *
     * @param json 领证人信息
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     */
    @ResponseBody
    @PatchMapping("/hf/lzrxx/plzh")
    public Object updateLzrxxToHfPlzh(@RequestBody String json, @RequestParam("gzlslid") String gzlslid, @RequestParam("qlrid") String qlrid, @RequestParam("djxl") String djxl, @RequestParam("xmid") String xmid) throws Exception {
        log.info("updateLzrxxToHfPlzh 合肥不动产受理领证人更新集合JSON,参数{},gzlslid{}", json, gzlslid);
        BdcLzrDTO bdcLzrDTO = new BdcLzrDTO();
        bdcLzrDTO.setJson(json);
        bdcLzrDTO.setDjxl(djxl);
        bdcLzrDTO.setGzlslid(gzlslid);
        bdcLzrDTO.setQlrid(qlrid);
        bdcLzrDTO.setXmid(xmid);
        int count = bdcLzrFeignService.updateLzrxxToHfPllc(bdcLzrDTO);
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
        return count;
    }

    @ResponseBody
    @DeleteMapping("/hf/delete")
    public void deleteLzrxxToHf(@RequestParam(required = false) String qlrid, @RequestParam String gzlslid, @RequestParam String djxl, String lzrid) {
        if (StringToolUtils.isAllNullOrEmpty(qlrid, lzrid)) {
            throw new AppException("删除领证人信息异常,缺失lzrid/qlrid");
        }
        BdcLzrQO bdcLzrQO = new BdcLzrQO();
        if (StringUtils.isNotBlank(lzrid)) {
            bdcLzrQO.setLzrid(lzrid);
        } else {
            bdcLzrQO.setQlrid(qlrid);
        }
        List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
        if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
            bdcLzrFeignService.deleteBdcLzrsByLzrxx(bdcLzrDOList.get(0).getLzrmc(), bdcLzrDOList.get(0).getLzrzjh(), gzlslid, djxl);
        }
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
    }

    /**
     * 根据登记小类判断，当前组合流程的领证人信息是否展示
     * @param xmid 项目ID
     * @return 是否展示 true 展示  false 不展示
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/sfzs")
    public boolean checkLzrxxDjxl(String xmid){
        boolean sfzs = true;
        if(StringUtils.isNotBlank(xmid) && StringUtils.isNotBlank(lzrBzsDjxl)){
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = this.bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                String djxl = bdcXmDOList.get(0).getDjxl();
                sfzs = lzrBzsDjxl.indexOf(djxl) == -1;
            }
        }
        return sfzs;
    }

    /**
     * @param lzrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 单个和组合流程使用
     * @date : 2022/3/25 10:14
     */
    @DeleteMapping("/yc/zhlc")
    public void deleteYcLzr(String lzrid, @RequestParam("gzlslid") String gzlslid) {
        if (StringUtils.isNotBlank(lzrid)) {
            bdcLzrFeignService.deleteBdcLzr(lzrid);
        }
        if (StringUtils.isNotBlank(gzlslid)) {
            bdcLzrFeignService.hxLzrxxToZs(gzlslid);
        }
    }

    /**
     * @param lzrid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量，批量组合时使用
     * @date : 2022/3/25 10:17
     */
    @DeleteMapping("/yc/pllc")
    public void deleteYcLzrPllc(String lzrid, String qlrid, String djxl, String gzlslid) {
        if (StringUtils.isNotBlank(lzrid)) {
            BdcLzrQO bdcLzrQO = new BdcLzrQO();
            bdcLzrQO.setLzrid(lzrid);
            List<BdcLzrDO> bdcLzrDOList = bdcLzrFeignService.listBdcLzr(bdcLzrQO);
            if (CollectionUtils.isNotEmpty(bdcLzrDOList)) {
                if (StringUtils.isNotBlank(qlrid)) {
                    //删除绑定相同权利人的领证人-根据权利人id 找到名称和证件号
                    BdcQlrQO bdcQlrQO = new BdcQlrQO();
                    bdcQlrQO.setQlrid(qlrid);
                    List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
                    if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                        //根据名称证件号和权利人类型找到所有的权利人
                        List<BdcQlrDO> bdcQlrDOS = bdcQlrFeignService.listAllBdcQlr(gzlslid, CommonConstantUtils.QLRLB_QLR, djxl);
                        if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
                            List<String> qlridList = new ArrayList<>(CollectionUtils.size(bdcQlrDOS));
                            for (BdcQlrDO bdcQlrDO : bdcQlrDOS) {
                                if (StringUtils.equals(bdcQlrDO.getQlrmc(), bdcQlrDOList.get(0).getQlrmc()) && StringUtils.equals(bdcQlrDO.getZjh(), bdcQlrDOList.get(0).getZjh())) {
                                    //名称和证件号相同是同一人，删除对应的领证人
                                    qlridList.add(bdcQlrDO.getQlrid());
                                }
                            }
                            BdcLzrVO bdcLzrVO = new BdcLzrVO();
                            bdcLzrVO.setGzlslid(gzlslid);
                            bdcLzrVO.setDjxl(djxl);
                            bdcLzrVO.setLzrmc(bdcLzrDOList.get(0).getLzrmc());
                            bdcLzrVO.setLzrzjh(bdcLzrDOList.get(0).getLzrzjh());
                            bdcLzrVO.setQlridList(qlridList);
                            bdcLzrFeignService.deleteLzrxx(JSON.toJSONString(bdcLzrVO));
                        }
                    }
                } else {
                    //没有绑定权利人就根据名称证件号删除
                    bdcLzrFeignService.deleteBdcLzrsByLzrxx(bdcLzrDOList.get(0).getLzrmc(), bdcLzrDOList.get(0).getLzrzjh(), gzlslid, djxl);
                }
                if (StringUtils.isNotBlank(gzlslid)) {
                    bdcLzrFeignService.hxLzrxxToZs(gzlslid);
                }
            }
        }
    }
}
