package cn.gtmap.realestate.accept.ui.web;

import cn.gtmap.realestate.accept.ui.utils.Constants;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlJtcyGroupDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSqrDTO;
import cn.gtmap.realestate.common.core.dto.accept.YcslYmxxDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJtcyQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSqrQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlXmQO;
import cn.gtmap.realestate.common.core.service.feign.accept.*;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/27
 * @description 受理申请人
 */
@Controller
@RequestMapping("/slym/sqr")
public class SlymSqrController {
    @Autowired
    BdcSlSqrFeignService bdcSlSqrFeignService;
    @Autowired
    BdcSlJtcyFeignService bdcSlJtcyFeignService;
    @Autowired
    BdcSlxxHxFeignService bdcSlxxHxFeignService;
    @Autowired
    BdcSlJbxxFeignService bdcSlJbxxFeignService;
    @Autowired
    BdcSlXmFeignService bdcSlXmFeignService;
    @Autowired
    BdcRyzdFeignService bdcRyzdFeignService;
    @Autowired
    BdcSlEntityFeignService bdcSlEntityFeignService;

    /**
     * @param json 申请人信息
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据申请人ID更新申请人信息（注意：该方法会将传过来的json属性值为null的更新）
     */
    @ResponseBody
    @PatchMapping("")
    public int updateBdcSlSqr(@RequestBody String json) throws Exception{
        return bdcSlEntityFeignService.updateByJsonEntity(json, BdcSlSqrDO.class.getName());
    }
    /**
     * @param json 集合
     * @return 更新数量
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 更新受理申请人信息(申请人及家庭成员)
     */
    @ResponseBody
    @PatchMapping("/sqrxx")
    public int updateBdcSlSqrDTO(@RequestBody String json, String gzlslid) throws Exception{
        int count = 0;
        for (BdcSlSqrDTO bdcSlSqrDTO : JSON.parseArray(json, BdcSlSqrDTO.class)) {
            //申请人保存
            BdcSlSqrDO bdcSlSqrDO = bdcSlSqrDTO.getBdcSlSqrDO();
            if (StringUtils.isBlank(bdcSlSqrDO.getSqrid())) {
                if(StringUtils.isBlank(bdcSlSqrDO.getXmid())) {
                    throw new AppException("新增申请人xmid为空:"+bdcSlSqrDO.getSqrmc());
                }
                bdcSlSqrDO = bdcSlSqrFeignService.insertBdcSlSqr(bdcSlSqrDO);

                count++;
            } else {
                count += bdcSlSqrFeignService.updateBdcSlSqr(bdcSlSqrDO);
            }
            //保存家庭成员
            if(CollectionUtils.isNotEmpty(bdcSlSqrDTO.getBdcSlJtcyDOList())){
                for(BdcSlJtcyDO bdcSlJtcyDO:bdcSlSqrDTO.getBdcSlJtcyDOList()){
                    if(StringUtils.isBlank(bdcSlJtcyDO.getSqrid())) {
                        bdcSlJtcyDO.setSqrid(bdcSlSqrDO.getSqrid());
                    }
                    if(StringUtils.isBlank(bdcSlJtcyDO.getJtcyid())) {
                        //根据名称和证件号查询当前家庭成员是否存在
                        BdcSlJtcyQO bdcSlJtcyQO =new BdcSlJtcyQO();
                        bdcSlJtcyQO.setZjh(bdcSlJtcyDO.getZjh());
                        bdcSlJtcyQO.setJtcymc(bdcSlJtcyDO.getJtcymc());
                        bdcSlJtcyQO.setSqrid(bdcSlSqrDO.getSqrid());
                        List<BdcSlJtcyDO> bdcSlJtcyDOList = bdcSlJtcyFeignService.listBdcSlJtcy(bdcSlJtcyQO);
                        if(CollectionUtils.isNotEmpty(bdcSlJtcyDOList)){
                            for(BdcSlJtcyDO jtcyDO:bdcSlJtcyDOList){
                                bdcSlJtcyDO.setJtcyid(jtcyDO.getJtcyid());
                                bdcSlJtcyFeignService.updateBdcSlJtcy(jtcyDO);
                            }
                        }else {
                            bdcSlJtcyFeignService.insertBdcSlJtcy(bdcSlJtcyDO);
                        }
                    }else{
                        bdcSlJtcyFeignService.updateBdcSlJtcy(bdcSlJtcyDO);
                    }
                }
                // 同步家庭成员信息至其他户室
                bdcSlJtcyFeignService.syncJtcyxx(bdcSlSqrDTO, gzlslid);
            }

            bdcSlxxHxFeignService.hxBdcSlxx(gzlslid);

        }
        return count;
    }



    /**
     * @param sqrid 申请人ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 删除受理申请人信息(申请人及家庭成员)
     */
    @ResponseBody
    @DeleteMapping("/sqrxx")
    public void deleteBdcSlJtcy(@RequestParam("sqrid") String sqrid,@RequestParam("gzlslid") String gzlslid) throws Exception {
        BdcSlSqrDO bdcSlSqrDO = bdcSlSqrFeignService.queryBdcSlSqrBySqrid(sqrid);
        String xmid = bdcSlSqrDO.getXmid();
        String sqrlb = bdcSlSqrDO.getSqrlb();
        Integer sxh = bdcSlSqrDO.getSxh();
        bdcSlJtcyFeignService.deleteBdcSlJtcyBySqrid(sqrid);
        bdcSlSqrFeignService.deleteBdcSlSqrBySqrid(sqrid);
        changeSxhForDel(xmid, sqrlb, sxh, "");

        //回写信息
        bdcSlxxHxFeignService.hxBdcSlxx(gzlslid);
    }

    /**
     * 根据不动产项目ID与申请人类别获取申请人信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param xmid, 项目id
     * @param sqrlb 申请人类别
     * @return List<BdcSlSqrDO> 不动产受理申请人集合
     */
    @ResponseBody
    @GetMapping("/sqrxx")
    public Object queryBdcSlSqr(@RequestParam("xmid") String xmid, @RequestParam("sqrlb") String sqrlb){
        return this.bdcSlSqrFeignService.listBdcSlSqrByXmid(xmid, sqrlb);
    }

    /**
     * @param xmid 项目ID
     * @return 家庭分组申请人信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据家庭分组获取申请人信息
     */
    @ResponseBody
    @GetMapping("/getSqrxxGroupByJt")
    public YcslYmxxDTO getSqrxxGroupByJt(@RequestParam("xmid") String xmid) {
        if(StringUtils.isBlank(xmid)){
            return new YcslYmxxDTO();
        }
        return bdcSlSqrFeignService.getSqrxxGroupByJt(xmid);
    }

    /**
     * @param xmid 项目ID
     * @return 家庭分组申请人信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据家庭分组获取申请人信息
     */
    @ResponseBody
    @GetMapping("/getSqrxxGroupByJtWithSqrlb")
    public List<List<BdcSlJtcyGroupDTO>>  getSqrxxGroupByJtWithSqrlb(String xmid, String sqrlb, Boolean sfhb) {
        if(StringUtils.isBlank(xmid)){
            return new ArrayList<>();
        }
        return bdcSlSqrFeignService.getSqrxxGroupByJtWithSqrlb(xmid,sqrlb,sfhb);
    }



    /**
     * @param sqrid 申请人id
     * @return 申请人实体
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 根据申请人id查询申请人实体
     */
    @ResponseBody
    @GetMapping("/{sqrid}")
    public Object queryBdcSlSqr(@PathVariable("sqrid") String sqrid){
        return bdcSlSqrFeignService.queryBdcSlSqrBySqrid(sqrid);
    }

    /**
     * 根据jbxxid与申请人类别获取申请人信息
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param jbxxid 基本信息id
     * @param sqrlb 申请人类别
     * @return List<BdcSlSqrDO> 不动产受理申请人集合
     */
    @ResponseBody
    @GetMapping("/sqrxx/{jbxxid}/{sqrlb}")
    public Object queryBdcSlSqrByJbxxid(@PathVariable("jbxxid") String jbxxid, @PathVariable("sqrlb") String sqrlb){
        BdcSlXmQO bdcSlXmQO = new BdcSlXmQO();
        bdcSlXmQO.setJbxxid(jbxxid);
        List<BdcSlXmDO> list = bdcSlXmFeignService.listBdcSlXm(bdcSlXmQO);
        if(CollectionUtils.isNotEmpty(list)){
            return this.bdcSlSqrFeignService.listBdcSlSqrByXmid(list.get(0).getXmid(), sqrlb);
        }else{
            return new ArrayList();
        }
    }

    /**
     * @param xmid  项目id
     * @param qlrlb 申请人类别
     * @param sxh   顺序号
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除时修改顺序号
     */
    private void changeSxhForDel(String xmid, String qlrlb, Integer sxh, String processInsId) {
        if (sxh != null) {
            BdcSlSqrQO bdcSqrQO = new BdcSlSqrQO();
            bdcSqrQO.setXmid(xmid);
            bdcSqrQO.setSqrlb(qlrlb);
            List<BdcSlSqrDO> bdcSqrDOList = bdcSlSqrFeignService.listBdcSlSqrByBdcSlSqrQO(bdcSqrQO);
            if (CollectionUtils.isNotEmpty(bdcSqrDOList)) {
                for (BdcSlSqrDO bdcSlSqrDO : bdcSqrDOList) {
                    if (bdcSlSqrDO.getSxh() > sxh) {
                        bdcSlSqrDO.setSxh(bdcSlSqrDO.getSxh() - 1);
                        bdcSlSqrFeignService.updateBdcSlSqr(bdcSlSqrDO);
                    }
                }
            }
        }
    }

    /**
     * @param sqrid        权利人ID
     * @param czlx         操作类型
     * @param processInsId 工作流实例ID
     * @return 修改数量
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 修改申请人顺序号
     */
    @ResponseBody
    @GetMapping(value = "/sxh")
    public Integer changeQlrSxh(@RequestParam("sqrid") String sqrid, @RequestParam("czlx")String czlx,
                                @RequestParam("gzlslid")String processInsId) throws Exception{
        Integer count = 0;
        List<BdcSlSqrDO> bdcSqrDOS = new ArrayList<>();
        BdcSlSqrDO bdcSlSqrDO = (BdcSlSqrDO)queryBdcSlSqr(sqrid);
        if (bdcSlSqrDO != null) {
            BdcSlSqrQO bdcSlSqrQO = new BdcSlSqrQO();
            bdcSlSqrQO.setXmid(bdcSlSqrDO.getXmid());
            bdcSlSqrQO.setSqrlb(bdcSlSqrDO.getSqrlb());
            List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrFeignService.listBdcSlSqrByBdcSlSqrQO(bdcSlSqrQO);
            if (CollectionUtils.isNotEmpty(bdcSlSqrDOList) && bdcSlSqrDOList.size() > 1) {
                for (int i = 0; i < bdcSlSqrDOList.size(); i++) {
                    BdcSlSqrDO sqrDO = bdcSlSqrDOList.get(i);
                    if (StringUtils.equals(sqrDO.getSqrid(), sqrid)) {
                        BdcSlSqrDO changeBdcSqr = null;
                        if (StringUtils.equals(czlx, Constants.SXH_UP) && i != 0) {
                            changeBdcSqr = bdcSlSqrDOList.get(i - 1);
                        }
                        if (StringUtils.equals(czlx, Constants.SXH_DOWN) && i != (bdcSlSqrDOList.size() - 1)) {
                            changeBdcSqr = bdcSlSqrDOList.get(i + 1);
                        }
                        if (changeBdcSqr != null) {
                            int sxh1 = bdcSlSqrDO.getSxh();
                            int sxh2 = changeBdcSqr.getSxh();
                            bdcSlSqrDO.setSxh(sxh2);
                            changeBdcSqr.setSxh(sxh1);
                            bdcSqrDOS.add(bdcSlSqrDO);
                            bdcSqrDOS.add(changeBdcSqr);
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcSqrDOS)) {
            for (BdcSlSqrDO bdcSqr : bdcSqrDOS) {
                count += bdcSlSqrFeignService.updateBdcSlSqr(bdcSqr);
            }
        }
        return count;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存更新申请人信息
     */
    @ResponseBody
    @PatchMapping("/sqrxx/save")
    public int saveBatchSqrxx(@RequestBody String json, String gzlslid, String qllx, String djxl) throws Exception {
        if (StringUtils.isBlank(gzlslid)) {
            throw new AppException("缺失参数工作流实例ID");
        }
        int count = 0;
        for (Object qlrObj : JSON.parseArray(json, BdcSlSqrDO.class)) {
            List<BdcSlSqrDO> bdcSlSqrDOList = bdcSlSqrFeignService.saveBatchBdcSlSqrWithZh(JSON.toJSONString(qlrObj), gzlslid, qllx, djxl);
            if (CollectionUtils.isNotEmpty(bdcSlSqrDOList)) {
                count += bdcSlSqrDOList.size();
            }
        }
        bdcSlxxHxFeignService.hxBdcSlxx(gzlslid);
        return count;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量删除申请人信息
     */
    @ResponseBody
    @DeleteMapping("/sqrxx/pl")
    public void deletePlSqrxx(@RequestParam("sqrid") String sqrid, @RequestParam("gzlslid") String gzlslid, @RequestParam("qllx") String qllx, String djxl) throws Exception {
        if (StringUtils.isNotBlank(sqrid)) {

            bdcSlSqrFeignService.deleteBdcSqrsBySqrxxWithZh(sqrid, gzlslid, qllx, djxl);

            bdcSlxxHxFeignService.hxBdcSlxx(gzlslid);

        }

    }


}
