package cn.gtmap.realestate.inquiry.ui.web.rest;

import cn.gtmap.realestate.common.core.annotations.LayuiPageable;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlJkDO;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlSjDO;
import cn.gtmap.realestate.common.core.domain.register.BdcGzlsjJkGxDO;
import cn.gtmap.realestate.common.core.dto.register.BdcGzlsjPlDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.register.BdcGzlQO;
import cn.gtmap.realestate.common.core.service.feign.register.BdcEntityFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcGzlsjFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.common.util.WorkFlowUtils;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @program: realestate
 * @description: 工作流事件配置controller
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-04-07 14:50
 **/
@RestController
@RequestMapping("/rest/v1.0/gzl")
public class BdcGzlSjPzController extends BaseController {

    @Autowired
    BdcGzlsjFeignService bdcGzlsjFeignService;
    @Autowired
    BdcEntityFeignService bdcEntityFeignService;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    WorkFlowUtils workFlowUtils;
    @Autowired
    UserManagerUtils userManagerUtils;

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询工作流接口
     * @date : 2022/4/7 15:04
     */
    @GetMapping("/jk/page")
    public Object listBdcGzlJkByPage(@LayuiPageable Pageable pageable, BdcGzlQO bdcGzlQO) {
        Page<BdcGzlJkDO> bdcGzlJkDOPage = bdcGzlsjFeignService.listBdcGzlJkByPage(pageable, JSON.toJSONString(bdcGzlQO));
        return super.addLayUiCode(bdcGzlJkDOPage);
    }

    /**
     * @param bdcGzlQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 分页查询工作流接口
     * @date : 2022/4/8 14:45
     */
    @GetMapping("/sj/page")
    public Object listBdcGzlSjByPage(@LayuiPageable Pageable pageable, BdcGzlQO bdcGzlQO) {
        Page<BdcGzlSjDO> bdcGzlSjDOPage = bdcGzlsjFeignService.listGzlsjByPage(pageable, JSON.toJSONString(bdcGzlQO));
        return super.addLayUiCode(bdcGzlSjDOPage);
    }

    /**
     * @param jkid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据工作流id查询接口信息
     * @date : 2022/4/7 16:30
     */
    @GetMapping("/jk/{jkid}")
    public Object queryGzljk(@PathVariable(value = "jkid") String jkid) {
        if (StringUtils.isBlank(jkid)) {
            return new BdcGzlJkDO();
        }
        BdcGzlQO bdcGzlQO = new BdcGzlQO();
        bdcGzlQO.setJkid(jkid);
        List<BdcGzlJkDO> bdcGzlJkDOList = bdcGzlsjFeignService.listBdcGzlJk(bdcGzlQO);
        return CollectionUtils.isNotEmpty(bdcGzlJkDOList) ? bdcGzlJkDOList.get(0) : new BdcGzlJkDO();
    }

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据事件id查询工作流事件
     * @date : 2022/4/11 14:37
     */
    @GetMapping("/sj")
    public Object queryBdcGzlsj(String sjid) {
        if (StringUtils.isBlank(sjid) || StringUtils.equals("null", sjid)) {
            return new BdcGzlJkDO();
        }
        BdcGzlQO bdcGzlQO = new BdcGzlQO();
        bdcGzlQO.setSjid(sjid);
        List<BdcGzlSjDO> bdcGzlSjDOList = bdcGzlsjFeignService.listBdcGzlSj(bdcGzlQO);
        return CollectionUtils.isNotEmpty(bdcGzlSjDOList) ? bdcGzlSjDOList.get(0) : new BdcGzlSjDO();
    }

    /**
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询工作流事件和工作流接口关系
     * @date : 2022/4/11 14:50
     */
    @GetMapping("/gx")
    public Object listGzlSjJkGx(String sjid) {
        if (StringUtils.isBlank(sjid)) {
            return Collections.emptyList();
        }
        BdcGzlQO bdcGzlQO = new BdcGzlQO();
        bdcGzlQO.setSjid(sjid);
        return bdcGzlsjFeignService.listBdcGzlsjJkGx(bdcGzlQO);
    }

    /**
     * @param jkid
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存工作流事件关系
     * @date : 2022/4/12 16:52
     */
    @PostMapping("/gx")
    public void saveGzlsjgx(String jkid, String sjid) {
        if (StringUtils.isNoneBlank(jkid, sjid)) {
            bdcGzlsjFeignService.saveBdcgzlsjGx(sjid, jkid);
        }
    }

    /**
     * @param jkid
     * @param sjid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流事件关系
     * @date : 2022/4/12 16:57
     */
    @DeleteMapping("/gx")
    public void deleteGzlsjgx(String jkid, String sjid) {
        if (StringUtils.isNoneBlank(jkid, sjid)) {
            bdcGzlsjFeignService.deleteBdcGzlSjGx(sjid, jkid);
        }
    }


    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存工作流接口
     * @date : 2022/4/7 15:05
     */
    @PostMapping("/jk")
    public void saveGzljk(@RequestBody String json) {
        BdcGzlJkDO bdcGzlJkDO = JSON.parseObject(json, BdcGzlJkDO.class);
        bdcGzlJkDO.setCjr(userManagerUtils.getUserAlias());
        bdcGzlJkDO.setCjsj(new Date());
        if (StringUtils.isBlank(bdcGzlJkDO.getJkid())) {
            bdcGzlJkDO.setJkid(UUIDGenerator.generate16());
            bdcGzlsjFeignService.insertBdcGzlJk(bdcGzlJkDO);
        } else {
            bdcEntityFeignService.updateByJsonEntity(json, bdcGzlJkDO.getClass().getName());
        }
    }

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 保存工作流事件
     * @date : 2022/4/11 16:03
     */
    @PostMapping("/sj")
    public Object saveGzlSj(@RequestBody String json, String type) {
        BdcGzlSjDO bdcGzlSjDO = JSON.parseObject(json, BdcGzlSjDO.class);
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("jkidList");
        bdcGzlSjDO.setCjr(userManagerUtils.getUserAlias());
        bdcGzlSjDO.setCjsj(new Date());
        if (StringUtils.isNotBlank(type) && StringUtils.equals(CommonConstantUtils.FUN_INSERT, type)) {
            bdcGzlSjDO = bdcGzlsjFeignService.insertGzlsj(bdcGzlSjDO);
        } else {
            bdcEntityFeignService.updateByJsonEntity(json, bdcGzlSjDO.getClass().getName());
        }
        //同时保存关联接口的id
        // 先删除所有之前关联的接口
        bdcGzlsjFeignService.deleteBdcGzlSjGx(bdcGzlSjDO.getSjid());
        //新增关系
        for (Object o : jsonArray) {
            String jkid = o.toString();
            if (StringUtils.isNotBlank(jkid)) {
                saveGzlsjgx(jkid, bdcGzlSjDO.getSjid());
            }
        }
        return bdcGzlSjDO;
    }


    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 批量保存流程事件信息
     * 实现逻辑--1.根据入参的流程名称数量和节点名称数量进行批量操作
     * 2. 根据流程名称和节点名称先进行数据库查询，查询到更新，未查询到则新增
     * @date : 2022/4/21 14:06
     */
    @PostMapping("/sj/pl")
    public void batchSaveGzlsj(@RequestBody String json, String type) {
        BdcGzlsjPlDTO bdcGzlsjPlDTO = JSON.parseObject(json, BdcGzlsjPlDTO.class);
        if (CollectionUtils.isNotEmpty(bdcGzlsjPlDTO.getSjbsList())) {
            //事件标识和选中的节点名称去查询数据库数据。能查询到的就更新，查不到的新增
            bdcGzlsjFeignService.batchSaveGzlsj(bdcGzlsjPlDTO);
        }
    }

    /**
     * @param jkidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流接口
     * @date : 2022/4/7 15:06
     */
    @DeleteMapping("/jk")
    public void deleteGzljk(@RequestBody List<String> jkidList) {
        if (CollectionUtils.isNotEmpty(jkidList)) {
            for (String jkid : jkidList) {
                bdcGzlsjFeignService.deleteBdcGzlJk(jkid);
            }
        }
    }

    /**
     * @param sjidList
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 删除工作流事件，同时删除关联的接口
     * @date : 2022/4/13 16:36
     */
    @DeleteMapping("/sj")
    public void deleteGzlsj(@RequestBody List<String> sjidList) {
        if (CollectionUtils.isNotEmpty(sjidList)) {
            for (String sjid : sjidList) {
                bdcGzlsjFeignService.deleteGzlsj(sjid);
                bdcGzlsjFeignService.deleteBdcGzlSjGx(sjid);
            }
        }
    }


    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询注册中心应用名称
     * @date : 2022/4/8 15:08
     */
    @GetMapping("/app")
    public Object listAppName() {
        List<String> yymcList = discoveryClient.getServices();
        //去重
        yymcList = yymcList.stream().distinct().collect(Collectors.toList());
        return yymcList;
    }

    /**
     * @param gzldyid
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询流程节点名称
     * @date : 2022/4/8 15:09
     */
    @GetMapping("/jdmc")
    public Object listJdmc(String gzldyid) {
        if (StringUtils.isNotBlank(gzldyid)) {
            return workFlowUtils.getUserTasks(gzldyid);
        }
        return Collections.emptyList();
    }

    /**
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 后台生成一个uuid 主键
     * @date : 2022/4/13 16:16
     */
    @GetMapping("/uuid")
    public String getUuid() {
        return UUIDGenerator.generate16();
    }


    /**
     * @param sjid
     * @param jkid
     * @param type
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 改变顺序号
     * @date : 2022/4/14 14:33
     */
    @GetMapping("/sxh")
    public void changeSxh(String sjid, String jkid, String type) {
        if (StringUtils.isNoneBlank(sjid, jkid, type)) {
            //1. 查询当前所有关联的接口
            BdcGzlQO bdcGzlQO = new BdcGzlQO();
            bdcGzlQO.setSjid(sjid);
            List<BdcGzlsjJkGxDO> allBdcGzlsjJkGxDOList = bdcGzlsjFeignService.listBdcGzlsjJkGx(bdcGzlQO);
            bdcGzlQO.setJkid(jkid);
            List<BdcGzlsjJkGxDO> nowGzlsjgx = bdcGzlsjFeignService.listBdcGzlsjJkGx(bdcGzlQO);
            if (CollectionUtils.isNotEmpty(allBdcGzlsjJkGxDOList) && CollectionUtils.size(allBdcGzlsjJkGxDOList) > 1) {
                for (int i = 0; i < allBdcGzlsjJkGxDOList.size(); i++) {
                    BdcGzlsjJkGxDO bdcGzlsjJkGxDO = allBdcGzlsjJkGxDOList.get(i);
                    if (StringUtils.equals(bdcGzlsjJkGxDO.getJkid(), jkid)) {
                        List<BdcGzlsjJkGxDO> updateJkgxList = new ArrayList<>(2);
                        BdcGzlsjJkGxDO changeSxhSjgx = null;
                        //如果当前点击的是第一个并且顺序号向上的，提示已经是第一个了
                        if (CommonConstantUtils.SXH_UP.equals(type) && Objects.equals(1, nowGzlsjgx.get(0).getSxh())) {
                            throw new AppException("已经是第一个了");
                        }
                        if (CommonConstantUtils.SXH_DOWN.equals(type) && Objects.equals(allBdcGzlsjJkGxDOList.size(), nowGzlsjgx.get(0).getSxh())) {
                            throw new AppException("已经是最后一个了");
                        }
                        if (CommonConstantUtils.SXH_UP.equals(type)) {
                            changeSxhSjgx = allBdcGzlsjJkGxDOList.get(i - 1);
                        }
                        if (CommonConstantUtils.SXH_DOWN.equals(type)) {
                            changeSxhSjgx = allBdcGzlsjJkGxDOList.get(i + 1);
                        }
                        if (Objects.nonNull(changeSxhSjgx) && CollectionUtils.isNotEmpty(nowGzlsjgx)) {
                            //查询当前的点击的顺序号
                            BdcGzlsjJkGxDO nowGzlsjGx = nowGzlsjgx.get(0);
                            if (Objects.nonNull(changeSxhSjgx.getSxh()) && Objects.nonNull(nowGzlsjGx.getSxh())) {
                                int sxh1 = nowGzlsjGx.getSxh();
                                int sxh2 = changeSxhSjgx.getSxh();
                                nowGzlsjGx.setSxh(sxh2);
                                changeSxhSjgx.setSxh(sxh1);
                                updateJkgxList.add(nowGzlsjGx);
                                updateJkgxList.add(changeSxhSjgx);
                            }
                        }
                        if (CollectionUtils.isNotEmpty(updateJkgxList)) {
                            //更新接口关系数据
                            bdcGzlsjFeignService.batchUpdateGzlsjGx(updateJkgxList);
                        }
                        break;
                    }
                }
            }
        }
    }

}
