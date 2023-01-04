package cn.gtmap.realestate.natural.ui.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcShxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyShxxDO;
import cn.gtmap.realestate.common.core.domain.natural.ZrzyXtMryjDO;
import cn.gtmap.realestate.common.core.qo.natural.ZrzyShxxQO;
import cn.gtmap.realestate.common.core.service.feign.natural.ZrzyShxxFeignService;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyMutilShxxVO;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyShxxVO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import cn.gtmap.realestate.natural.ui.web.main.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/11/5 10:35
 */
@RestController
@RequestMapping("/rest/v1.0")
public class ZrzyShxxController extends BaseController {
    @Autowired
    ZrzyShxxFeignService zrzyShxxFeignService;

    /**
     * 签名图片地址
     */
    @Value("${url.sign.image:/storage/rest/files/download/}")
    protected String signImageUrl;

    /**
     * @param zrzyShxxDO 审核信息实体类
     * @return int 返回操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新指定节点的审核信息
     */
    @RequestMapping(value = "/shxx", method = RequestMethod.PUT)
    int updateZrzyShxx(@RequestBody ZrzyShxxDO zrzyShxxDO) {
        return zrzyShxxFeignService.updateZrzyShxx(zrzyShxxDO);
    }

    /**
     * @param zrzyShxxDO 审核信息实体类
     * @return int 操作数量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据主键查询shxx，没有则保存，有则更新
     */
    @PostMapping(value = "/shxx")
    int saveOrUpdateZrzyShxx(@RequestBody ZrzyShxxDO zrzyShxxDO) {
        return zrzyShxxFeignService.saveOrUpdateZrzyShxx(zrzyShxxDO);
    }

    @GetMapping(value = "/shxx/{shxxid}")
    ZrzyShxxDO queryZrzyShxxById(@PathVariable(name = "shxxid") String shxxid) {
        return zrzyShxxFeignService.queryZrzyShxxById(shxxid);
    }

    /**
     * @param zrzyShxxQO
     * @return List<zrzyShxxDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取审核信息接口
     */
    @GetMapping(value = "/shxx/list")
    List<ZrzyMutilShxxVO> queryZrzyShxx(@RequestParam(name = "gzlslid") String gzlslid,
                                        @RequestParam(name = "taskId") String taskId,
                                        @RequestParam(name = "currentJdmc") String currentJdmc,
                                        @RequestParam(name = "onlyCurrentJd") Boolean onlyCurrentJd) {
        List<ZrzyMutilShxxVO> shxx = new ArrayList<>();
        ZrzyShxxQO zrzyShxxQO = new ZrzyShxxQO();
        zrzyShxxQO.setJdmc(currentJdmc);
        zrzyShxxQO.setGzlslid(gzlslid);
        zrzyShxxQO.setShxxid(taskId);
        zrzyShxxQO.setOnlyCurrentJd(onlyCurrentJd);
        zrzyShxxQO.setSignImageUrl(signImageUrl);
        List<ZrzyShxxVO> zrzyShxxVOS = zrzyShxxFeignService.queryShJdxx(zrzyShxxQO);
        if (CollectionUtils.isNotEmpty(zrzyShxxVOS)) {
            //会有平行网关的情况,按照节点名称汇总数据
            LinkedHashMap<String, List<ZrzyShxxVO>> collect = new LinkedHashMap<>();
            for (ZrzyShxxVO zrzyShxxVO : zrzyShxxVOS) {
                String[] jdmcArray = zrzyShxxVO.getJdmc().split("-");
                String jdmc = jdmcArray[0];
                if (collect.containsKey(jdmc)){
                    collect.get(jdmc).add(zrzyShxxVO);
                }else {
                    List<ZrzyShxxVO> shList = new ArrayList<>();
                    shList.add(zrzyShxxVO);
                    collect.put(jdmc,shList);
                }
            }
            for (Map.Entry<String, List<ZrzyShxxVO>> stringListEntry : collect.entrySet()) {
                ZrzyMutilShxxVO zrzyMutilShxxVO = new ZrzyMutilShxxVO();
                zrzyMutilShxxVO.setJdmc(stringListEntry.getKey());
                zrzyMutilShxxVO.setZrzyShxxs(stringListEntry.getValue());
                shxx.add(zrzyMutilShxxVO);
            }
        }

        return shxx;
    }

    /**
     * @param zrzyShxxQO
     * @return List<zrzyShxxDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取审核信息接口
     */
    @GetMapping(value = "/shxx/list/signal")
    List<ZrzyShxxVO> queryZrzyShxxSignal(@RequestParam(name = "gzlslid") String gzlslid,
                                   @RequestParam(name = "taskId") String taskId,
                                   @RequestParam(name = "currentJdmc") String currentJdmc,
                                   @RequestParam(name = "onlyCurrentJd") Boolean onlyCurrentJd) {
        ZrzyShxxQO zrzyShxxQO = new ZrzyShxxQO();
        zrzyShxxQO.setJdmc(currentJdmc);
        zrzyShxxQO.setGzlslid(gzlslid);
        zrzyShxxQO.setShxxid(taskId);
        zrzyShxxQO.setOnlyCurrentJd(onlyCurrentJd);
        zrzyShxxQO.setSignImageUrl(signImageUrl);
        return zrzyShxxFeignService.queryShJdxx(zrzyShxxQO);
    }

    /**
     * @param paramList 审核信息集合
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 依据主键更新多条审核信息数据
     */
    @PatchMapping(value = "/shxx/shxxList")
    int updateShxxList(@RequestBody List<ZrzyShxxDO> paramList) {
        if (CollectionUtils.isNotEmpty(paramList)) {
            return zrzyShxxFeignService.updateShxxList(paramList);
        }
        return -1;
    }

    /**
     * @param bdcShxxDO
     * @return signid
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取sign id
     */
    @PutMapping(value = "/shxx/sign")
    public ZrzyShxxVO sign(@RequestBody ZrzyShxxDO zrzyShxxDO) {
        ZrzyShxxVO zrzyShxxVO = zrzyShxxFeignService.getShxxSign(zrzyShxxDO);
        zrzyShxxVO.setQmtpdz(signImageUrl + zrzyShxxVO.getQmid());
        return zrzyShxxVO;
    }

    /**
     * @param shxxid
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 删除审核意见和签名信息
     */
    @RequestMapping(value = "/shxx/sign/{shxxid}", method = RequestMethod.DELETE)
    int deleteShxxSign(@PathVariable(name = "shxxid") String shxxid) {
        return zrzyShxxFeignService.deleteShxxSign(shxxid);
    }

    /**
     * @param shxxidList
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 批量删除审核意见和签名信息
     */
    @RequestMapping(value = "/shxx/sign", method = RequestMethod.DELETE)
    int deleteShxxSign(@RequestBody List<String> shxxidList) {
        return zrzyShxxFeignService.deleteShxxSign(shxxidList);
    }

    /**
     * @param zrzyShxxDO
     * @return signid
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 获取sign id
     */
    @RequestMapping(value = "/shxx/sign", method = RequestMethod.POST)
    ZrzyShxxVO getShxxSign(@RequestBody ZrzyShxxDO zrzyShxxDO) {
        return zrzyShxxFeignService.getShxxSign(zrzyShxxDO);
    }

    /**
     * @param taskId 当前任务ID
     * @return 更新数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 流程退回删除审核意见和签名信息，并保存审核结束时间
     */
    @RequestMapping(value = "/workflow/sign/{taskId}", method = RequestMethod.DELETE)
    int deleteSignAndSaveShjssj(@PathVariable(name = "taskId") String taskId) {
        return zrzyShxxFeignService.deleteSignAndSaveShjssj(taskId);
    }

    /**
     * @param taskId 当前任务ID
     * @return int 操作的数据量
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新审核结束时间（taskId和shxxid一致）
     */
    @RequestMapping(value = "/shjssj/{taskId}", method = RequestMethod.PUT)
    int updateShjssj(@PathVariable(name = "taskId") String taskId) {
        return zrzyShxxFeignService.updateShjssj(taskId);
    }

    @RequestMapping(value = "/mryj/sql", method = RequestMethod.POST)
    String generateMryjBySql(@RequestParam(name = "gzlslid") String gzlslid, @RequestBody ZrzyXtMryjDO zrzyXtMryjDO) {
        return zrzyShxxFeignService.generateMryjBySql(gzlslid, zrzyXtMryjDO);
    }

    /**
     * @param zrzyShxxQO 审核信息查询对象
     * @return 返回审核节点信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取签名意见，调用平台服务获取当前工作流配置的审核节点信息（出现异常则生成默认的初审，复审，核定节点信息），根据节点信息获取审核信息
     */
    @RequestMapping(value = "/shxx/jdxx", method = RequestMethod.POST)
    List<ZrzyShxxVO> queryShJdxx(@RequestBody ZrzyShxxQO zrzyShxxQO) {
        return zrzyShxxFeignService.queryShJdxx(zrzyShxxQO);
    }

    /**
     * @param shxxid 任务Id
     * @return zrzyShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程节点，最新的审核信息以及默认意见
     */
    @RequestMapping(value = "/shxx/mryj/{shxxid}", method = RequestMethod.GET)
    ZrzyShxxVO queryMryj(@PathVariable(value = "shxxid") String shxxid) {
        return zrzyShxxFeignService.queryMryj(shxxid);
    }

    /**
     * @param taskId 任务Id
     * @return zrzyShxxDO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取当前流程节点，最新的审核信息以及默认意见
     */
    @RequestMapping(value = "/shxx/kxyj", method = RequestMethod.GET)
    ZrzyShxxVO queryKxyj(@RequestParam(value = "taskId") String taskId) {
        return null;
    }

    /**
     * @param gzlslid 工作流实例ID
     * @return {List} 生成的审核信息
     * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 生成流程项目所有节点审核信息，意见内容采用默认意见
     */
    @PostMapping("/{gzlslid}/shxx")
    List<ZrzyShxxDO> generateShxxOfProInsId(@PathVariable("gzlslid") String gzlslid) {
        return zrzyShxxFeignService.generateShxxOfProInsId(gzlslid);
    }


    /**
     * @param processInsId
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 删除审核意见和签名信息
     */
    @DeleteMapping("/shxx/deleteShxxPdf/{processInsId}")
    void deleteShxxPdf(@PathVariable(value = "processInsId") String processInsId) {
        zrzyShxxFeignService.deleteShxxPdf(processInsId);
    }

}
