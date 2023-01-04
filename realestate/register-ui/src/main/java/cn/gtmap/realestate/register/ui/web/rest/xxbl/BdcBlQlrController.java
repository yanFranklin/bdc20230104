package cn.gtmap.realestate.register.ui.web.rest.xxbl;

import cn.gtmap.gtc.clients.LogMessageClient;
import cn.gtmap.gtc.sso.domain.dto.LogMsgDTO;
import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.init.BdcXmDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcYwxxDTO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcInitFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcRyzdFeignService;
import cn.gtmap.realestate.common.matcher.ZipkinAuditEventRepositoryMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.LogCompareUtils;
import cn.gtmap.realestate.common.util.RSAEncryptUtils;
import cn.gtmap.realestate.register.ui.util.Constants;
import cn.gtmap.realestate.register.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 信息补录权利人相关控制器
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 2:52 下午 2020/3/27
 */
@RestController
@RequestMapping("/rest/v1.0/blxx/qlr")
public class BdcBlQlrController extends BaseController {

    /**
     * 权利人服务
     */
    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    /**
     * 受理权利人服务
     */
    @Autowired
    private BdcSlQlrFeignService bdcSlQlrFeignService;
    /**
     * 初始化项目服务
     */
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    /**
     * 冗余字段服务
     */
    @Autowired
    private BdcRyzdFeignService bdcRyzdFeignService;
    /**
     * 实体公共操作服务
     */
    @Autowired
    private BdcEntityFeignService bdcEntityFeignService;

    /**
     * 业务初始化服务
     */
    @Autowired
    private BdcInitFeignService bdcInitFeignService;

    @Autowired
    LogMessageClient logMessageClient;
    

    /**
     * @param xmid  项目id
     * @param qlrlb 权利人类别
     * @return {List<BdcQlrDO> } 权利人集合
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 查询权利人信息 <br>
     * 1. 加载页面时检查所有的共有方式是否正确<br>
     * 2. 设置权利人顺序号<br>
     * <strong>只针对于简单流程的处理</strong>
     */
    @GetMapping
    public Object listBdcQlr(String xmid, String qlrlb, @RequestParam(name = "gzlslid", required = false) String gzlslid) throws Exception {
        List<BdcQlrDO> bdcQlrDOList = Lists.newArrayList();
        if (StringUtils.isNotBlank(xmid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(qlrlb);
            bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            bdcQlrDOList = changeGyfs(bdcQlrDOList, xmid);
            if (StringUtils.isNotBlank(gzlslid)) {
                bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(gzlslid);
                bdcRyzdFeignService.updateGyqkWithGzlslid(gzlslid);
            }
            setSxh(bdcQlrDOList);
        }
        return bdcQlrDOList;
    }

    /**
     * 删除权利人
     *
     * @param qlrid        权利人ID
     * @param xmid         项目ID
     * @param qlrlb        权利人类别
     * @param sxh          顺序号
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @DeleteMapping
    public void deleteBdcQlr(@RequestParam("qlrid") String qlrid, @RequestParam("xmid") String xmid, @RequestParam("qlrlb") String qlrlb, @RequestParam("sxh") Integer sxh, @RequestParam("processInsId") String processInsId) throws Exception{
        // 查询出修改前的业务信息
        BdcYwxxDTO bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(xmid);
        //删除权利人
        //bdcQlrFeignService.deleteBdcQlr(qlrid);

        BdcQlrQO bdcQlrQO = new BdcQlrQO();
        bdcQlrQO.setQlrid(qlrid);
        List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            BdcQlrDO bdcQlrDO = bdcQlrDOList.get(0);
            //更新第三权利人
            bdcQlrFeignService.deleteBdcQlrsByQlrxx(bdcQlrDO.getQlrmc(), bdcQlrDO.getZjh(), processInsId, bdcQlrDO.getQlrlb(), "", new ArrayList<>());
            changeSxhForDel(xmid, qlrlb, sxh, "");
            if (StringUtils.isNotBlank(processInsId)) {
                bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
                bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
            }
            // 查询过修改后的结果
            BdcYwxxDTO bdcYwxxDTOAfter = bdcInitFeignService.queryYwxx(xmid);
            Map<String, String> data = LogCompareUtils.initDataString(xmid, bdcYwxxDTOBefore, bdcYwxxDTOAfter);
            if (StringUtils.isNotBlank(RSAEncryptUtils.decrypt(data.get("change").toString()))) {
                LogMsgDTO logMsgDTO = new LogMsgDTO();
                logMsgDTO.setPrincipal(userManagerUtils.getCurrentUserName());
                logMsgDTO.setTags(data);
                logMsgDTO.setEvent(CommonConstantUtils.XXBL);
                logMessageClient.save(logMsgDTO);
            }
        }

    }

    /**
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     * @description 加载页面时检查所有的共有方式是否正确，不正确默认修改为共同共有
     */
    private List<BdcQlrDO> changeGyfs(List<BdcQlrDO> bdcQlrDOList, String xmid) throws Exception {
        List<BdcQlrDO> newBdcQlrList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            BdcXmQO bdcXmQO = new BdcXmQO();
            bdcXmQO.setXmid(xmid);
            List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);
            if (CollectionUtils.isNotEmpty(bdcXmDOList)) {
                //各流程对应的更新方法不同
                List<BdcQlrDO> bdcQlrList = new ArrayList<>();
                List<BdcQlrDO> bdcYwrList = new ArrayList<>();
                //取出所有的权利人
                for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    if (StringUtils.equals(bdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_QLR)) {
                        bdcQlrList.add(bdcQlrDO);
                    } else {
                        bdcYwrList.add(bdcQlrDO);
                    }
                }
                bdcQlrList = bdcSlQlrFeignService.updateBdcQlrGyfs(bdcQlrList, bdcXmDOList.get(0).getGzlslid(), xmid, "zhlc");
                newBdcQlrList.addAll(bdcQlrList);
                newBdcQlrList.addAll(bdcYwrList);
            }
        }
        return newBdcQlrList;
    }

    /**
     * 设置权利人顺序号
     *
     * @param bdcQlrDOList 权利人列表
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private void setSxh(List<BdcQlrDO> bdcQlrDOList) {
        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
            int qlrsxh = 0;
            int ywrsxh = 0;
            for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                int sxh = 0;
                if (StringUtils.equals(bdcQlrDO.getQlrlb(), "1")) {
                    qlrsxh++;
                    sxh = qlrsxh;
                }
                if (StringUtils.equals(bdcQlrDO.getQlrlb(), "2")) {
                    ywrsxh++;
                    sxh = ywrsxh;
                }
                if (bdcQlrDO.getSxh() == null || bdcQlrDO.getSxh() == 0) {
                    bdcQlrDO.setSxh(sxh);
                    bdcQlrFeignService.updateBdcQlr(bdcQlrDO);
                }
            }
        }
    }

    /**
     * 修改权利人
     *
     * @param json         前台传输权利人集合Json
     * @param processInsId 工作流实例 ID
     * @return 修改数量
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @PatchMapping
    public Integer updateBdcQlr(@RequestBody String json, String processInsId) throws Exception {
        List<BdcXmDTO> bdcXmDTOS = bdcXmFeignService.listBdcXmBfxxByGzlslid(processInsId);
        if (CollectionUtils.isEmpty(bdcXmDTOS)) {
            throw new AppException("未能查到对应项目数据");
        }
        String xmid = bdcXmDTOS.get(0).getXmid();
        // 查询出修改前的业务信息
        BdcYwxxDTO bdcYwxxDTOBefore = bdcInitFeignService.queryYwxx(xmid);
        Integer count = this.bdcSlQlrFeignService.updatePlBdcQlr(json, processInsId, xmid);
        if (StringUtils.isNotBlank(processInsId)) {
            //更新权利人冗余字段
            bdcRyzdFeignService.updateRyzdQlrWithProcessInstId(processInsId);
            bdcRyzdFeignService.updateGyqkWithGzlslid(processInsId);
        }
        // 查询过修改后的结果
        BdcYwxxDTO bdcYwxxDTOAfter = bdcInitFeignService.queryYwxx(xmid);
        Map<String, String> data = LogCompareUtils.initDataString(xmid, bdcYwxxDTOBefore, bdcYwxxDTOAfter);
        if (StringUtils.isNotBlank(RSAEncryptUtils.decrypt(data.get("change").toString()))) {
            LogMsgDTO logMsgDTO = new LogMsgDTO();
            logMsgDTO.setPrincipal(userManagerUtils.getCurrentUserName());
            logMsgDTO.setTags(data);
            logMsgDTO.setEvent(CommonConstantUtils.XXBL);
            logMessageClient.save(logMsgDTO);
        }
        return count;
    }

    /**
     * 修改权利人顺序号（用于简单流程、组合流程、批量流程）
     *
     * @param qlrid 权利人ID
     * @param czlx  操作类型
     * @return {Integer} 修改数量
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    @GetMapping(value = "/sxh")
    public Integer changeQlrSxh(String qlrid, String czlx) {
        Integer count = 0;
        List<BdcQlrDO> bdcQlrDOS = new ArrayList<>();
        BdcQlrDO bdcQlrDO = new BdcQlrDO();
        if (StringUtils.isNotBlank(qlrid)) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setQlrid(qlrid);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                bdcQlrDO = bdcQlrDOList.get(0);
            }
        }
        if (bdcQlrDO != null) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcQlrDO.getXmid());
            bdcQlrQO.setQlrlb(bdcQlrDO.getQlrlb());
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList) && bdcQlrDOList.size() > 1) {
                for (int i = 0; i < bdcQlrDOList.size(); i++) {
                    BdcQlrDO bdcQlr = bdcQlrDOList.get(i);
                    if (StringUtils.equals(bdcQlr.getQlrid(), qlrid)) {
                        BdcQlrDO changeBdcQlr = null;
                        if (StringUtils.equals(czlx, "up") && i != 0) {
                            changeBdcQlr = bdcQlrDOList.get(i - 1);
                        }
                        if (StringUtils.equals(czlx, "up") && i != (bdcQlrDOList.size() - 1)) {
                            changeBdcQlr = bdcQlrDOList.get(i + 1);
                        }
                        if (changeBdcQlr != null) {
                            int sxh1 = bdcQlrDO.getSxh();
                            int sxh2 = changeBdcQlr.getSxh();
                            bdcQlrDO.setSxh(sxh2);
                            changeBdcQlr.setSxh(sxh1);
                            bdcQlrDOS.add(bdcQlrDO);
                            bdcQlrDOS.add(changeBdcQlr);
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(bdcQlrDOS)) {
            for (BdcQlrDO bdcQlr : bdcQlrDOS) {
                count += bdcQlrFeignService.updateBdcQlr(bdcQlr);
            }
        }
        return count;
    }

    /**
     * 删除时修改顺序号
     *
     * @param xmid  项目id
     * @param qlrlb 权利人类别
     * @param sxh   顺序号
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    private void changeSxhForDel(String xmid, String qlrlb, Integer sxh, String processInsId) {
        if (sxh != null) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(xmid);
            bdcQlrQO.setQlrlb(qlrlb);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    if (bdcQlrDO.getSxh() > sxh) {
                        bdcQlrDO.setSxh(bdcQlrDO.getSxh() - 1);
                        if (StringUtils.isNotBlank(processInsId)) {
                            bdcQlrFeignService.updateBatchBdcQlr(bdcQlrDO, processInsId, "");
                        } else {
                            bdcQlrFeignService.updateBdcQlr(bdcQlrDO);
                        }
                    }
                }
            }
        }
    }
}
